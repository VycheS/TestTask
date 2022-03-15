package home.vs.testtask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import home.vs.testtask.model.Permission;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // кастомная реализация http
        http
            .csrf().disable() // отключаем защиту csrf угрозы
            .authorizeRequests() // АВТОРИЗАЦИЯ ЗАПРОСОВ СЛЕДУЮЩИМ ОБРАЗОМ
                .antMatchers("/").permitAll() // имеет доступ кто угодно
                .antMatchers("/registration").not().fullyAuthenticated() // доступ для незарегистрированных пользователей
                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.USERS_READ.getPermission()) // доступ на чтение имеют кто угодно
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.USERS_WRITE.getPermission()) // доступ на создание имеет админ
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(Permission.USERS_WRITE.getPermission()) // доступ на изменение имеет админ
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.USERS_WRITE.getPermission()) // доступ на удаление имеет админ
                .anyRequest().authenticated() // должен быть аутентифицирован
            // .and().httpBasic();
            .and().formLogin().loginPage("/auth/login").permitAll().defaultSuccessUrl("/auth/success") // устанавливаем логин страницу и в случае успеха перенавляем
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST")) // в случае выхода из авторизации,
                .invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JESSIONID").logoutSuccessUrl("/auth/login"); // и очищаем данные авторизации и перенаправляем на страницу авторизации
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder((this.passwordEncoder()));
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    
}

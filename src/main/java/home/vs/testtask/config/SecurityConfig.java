package home.vs.testtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import home.vs.testtask.model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // кастомная реализация http
        http
            .csrf().disable() // отключаем защиту csrf угрозы
            .authorizeRequests() // АВТОРИЗАЦИЯ ЗАПРОСОВ СЛЕДУЮЩИМ ОБРАЗОМ
                .antMatchers("/", "/resources/**").permitAll() // имеет доступ кто угодно
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name()) // доступ на чтение имеют кто угодно
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name()) // доступ на создание имеет админ
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role.ADMIN.name()) // доступ на изменение имеет админ
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name()) // доступ на удаление имеет админ
                .anyRequest().authenticated() // долже быть аутентифицирован
            // .and().httpBasic()
            .and().formLogin().loginPage("/auth/login").permitAll().defaultSuccessUrl("/auth/success") // устанавливаем логин страницу и в случае успеха перенавляем
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST")) // в случае выхода из авторизации,
                .invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JESSIONID").logoutSuccessUrl("/auth/login"); // и очищаем данные авторизации и перенаправляем на страницу авторизации
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.builder() // для авторизации админа
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.name())
                .build(),
            User.builder() // для авторизации обычного пользователя
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Role.USER.name())
                .build()
        );
    }
    // Base64
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
}

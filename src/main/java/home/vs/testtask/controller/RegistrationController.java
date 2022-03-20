package home.vs.testtask.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import home.vs.testtask.model.Role;
import home.vs.testtask.model.User;
import home.vs.testtask.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addUserAccount(
        @RequestParam Map<String, String> data) {
            if (userService.getByLogin(data.get("username")) == null) {
                User user = new User(null, data.get("username"), data.get("password"), Role.USER, "new user");
                userService.create(user);
                return "successfulRegistration";
            }
            return "unsuccessfulRegistration";
    }
}

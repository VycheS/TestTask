package home.vs.testtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/successfulAuthorization")
    public String getSuccessPage() {
        return "successfulAuthorization";
    }
    
}

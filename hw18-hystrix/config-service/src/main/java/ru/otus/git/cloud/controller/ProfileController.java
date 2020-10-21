package ru.otus.git.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Value("${app.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String getDataBaseConnectionDetails() {
        return welcomeMessage;
    }
}

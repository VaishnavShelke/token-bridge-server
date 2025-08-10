package com.monolith.shared.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sandbox controller for development and testing environment
 */
@Controller
public class SandboxController {

    /**
     * Token Bridge Sandbox endpoint
     * @param model Spring MVC model
     * @return view name for sandbox page
     */
    @GetMapping("/sandbox")
    public String sandbox(Model model) {
        model.addAttribute("serverName", "Token Bridge Sandbox");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        model.addAttribute("version", "1.0.0");
        model.addAttribute("environment", "Sandbox");
        model.addAttribute("description", "Development & Testing Environment");
        return "tokenbank/sandbox/sandbox";
    }
}

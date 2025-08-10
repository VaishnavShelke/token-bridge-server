package com.monolith.shared.controller;

import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.service.TokenBankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Authentication controller for login, signup functionality, and main application endpoints
 */
@Controller
public class AuthController implements ErrorController {

    @Autowired
    private TokenBankUserService tokenBankUserService;

    /**
     * Root endpoint - welcome page
     * @param model Spring MVC model
     * @return view name for welcome page
     */
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("serverName", "Token Bridge Server");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        model.addAttribute("version", "1.0.0");
        model.addAttribute("jspStatus", "Enabled");
        return "welcome";
    }

    /**
     * Error fallback endpoint - redirects unknown URLs to welcome page
     * @param model Spring MVC model
     * @return view name for welcome page
     */
    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("serverName", "Token Bridge Server");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        model.addAttribute("version", "1.0.0");
        model.addAttribute("jspStatus", "Enabled");
        model.addAttribute("errorMessage", "The requested page was not found. Welcome to the Token Bridge Server!");
        return "welcome";
    }

    /**
     * Show signup page
     * @param model Spring MVC model
     * @return view name for signup page
     */
    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("serverName", "TokenMint Signup");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return "tokenbank/user/signup";
    }

    /**
     * Handle login form submission
     * @param username Username from form
     * @param password Password from form
     * @param redirectAttributes Redirect attributes for flash messages
     * @return redirect to appropriate page
     */
    @PostMapping("/auth/login")
    public String handleLogin(@RequestParam String username, 
                            @RequestParam String password, 
                            RedirectAttributes redirectAttributes) {
        
        // For now, just redirect with a message - actual authentication would be implemented here
        // This would typically call the TokenBank API for authentication
        
        if (username != null && !username.trim().isEmpty() && 
            password != null && !password.trim().isEmpty()) {
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Login functionality is being processed. You would be authenticated with TokenMint.");
            redirectAttributes.addFlashAttribute("username", username);
            
            // In a real implementation, you would:
            // 1. Call the TokenBank API to authenticate
            // 2. Create a session
            // 3. Redirect to dashboard/main app
            
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Please provide both username and password.");
            return "redirect:/";
        }
    }

    /**
     * Handle signup form submission - calls TokenBank user service
     * @param username Username from form
     * @param password Password from form
     * @param email Email from form (optional)
     * @param redirectAttributes Redirect attributes for flash messages
     * @return redirect to appropriate page
     */
    @PostMapping("/auth/signup")
    public String handleSignup(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam(required = false) String email,
                             RedirectAttributes redirectAttributes) {
        
        // Validate required fields
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Username and password are required for signup.");
            return "redirect:/signup";
        }
        
        try {
            // Create the request object
            TokenBankCreateUserRequest createUserRequest = new TokenBankCreateUserRequest();
            createUserRequest.setUsername(username.trim());
            createUserRequest.setPassword(password);
            
            // Call the TokenBank user service
            TokenBankCreateUserResponse response = tokenBankUserService.createUser(createUserRequest);
            
            // Handle the response based on status code
            if ("000".equals(response.getStatusCode())) {
                // Success
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Account created successfully for user: " + username);
                redirectAttributes.addFlashAttribute("username", username);
                return "redirect:/";
            } else if ("002".equals(response.getStatusCode())) {
                // User already exists
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "User with username '" + username + "' already exists. Please choose a different username.");
                return "redirect:/signup";
            } else {
                // Other error
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Account creation failed: " + response.getMessage());
                return "redirect:/signup";
            }
            
        } catch (Exception ex) {
            // Handle service call exceptions
            redirectAttributes.addFlashAttribute("errorMessage", 
                "An error occurred during account creation. Please try again later.");
            return "redirect:/signup";
        }
    }

    /**
     * Show login page (same as welcome for now)
     * @param model Spring MVC model
     * @return redirect to welcome page
     */
    @GetMapping("/login")
    public String showLogin(Model model) {
        return "redirect:/";
    }
}

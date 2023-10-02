package com.alex.zanchenko.web.controller;

import com.alex.zanchenko.web.dto.LoginDTO;
import com.alex.zanchenko.web.dto.RegistrationDTO;
import com.alex.zanchenko.web.model.UserEntity;
import com.alex.zanchenko.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registration")
    public RegistrationDTO getRegisterForm(){
        RegistrationDTO user = new RegistrationDTO();
        return user;
    }

//    @GetMapping("/login")
//    public String loginPage(){
//        return "login";
//    }
//
//    @PostMapping("/register/save")
//    public String register(@Valid @ModelAttribute("user") RegistrationDTO user,
//                               BindingResult result, Model model){
//        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
//        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
////            result.rejectValue("email", "There is already a user with this email/username");
//            return "redirect:/register?fail";
//        }
//        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
//        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
////            result.rejectValue("email", "There is already a user with this email/username");
//            return "redirect:/register?fail";
//        }
//        if(result.hasErrors()){
//            model.addAttribute("user", user);
//            return "register";
//        }
//        userService.saveUser(user);
//        return "redirect:/clubs?success";
//    }

    @PostMapping("/login")
    public ResponseEntity<String> loginPage(@RequestBody LoginDTO dto) {
        // Return appropriate response, such as a message or status code
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        return ResponseEntity.ok("Login page");
    }
    @PostMapping("/register/save")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO user, BindingResult result) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        }

        if (result.hasErrors()) {
            // Handle validation errors and return appropriate response, e.g., error messages
            return ResponseEntity.badRequest().body("Validation errors");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}

//package com.cms.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cms.demo.model.User;
//import com.cms.demo.repository.UserRepository;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public String register(@RequestParam String username,
//                           @RequestParam String password,
//                           @RequestParam String role) {
//        if (userRepository.findByUsername(username) != null) {
//            return "User already exists!";
//        }
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase());
//
//        userRepository.save(user);
//
//        return "User registered successfully!";
//    }
//}
//

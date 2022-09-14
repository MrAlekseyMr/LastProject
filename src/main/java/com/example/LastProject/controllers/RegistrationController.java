package com.example.LastProject.controllers;

import com.example.LastProject.AuthenticationSystem;
import com.example.LastProject.models.Role;
import com.example.LastProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.LastProject.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.HashSet;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage()
    {
        if (!AuthenticationSystem.isLogged())
            return "login";
        return "home";
    }

    @GetMapping("registration")
    public String reg_view(Model model)
    {
        return "registration";
    }

    @PostMapping("registration")
    public String reg_action(User user, Model model)
    {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой пользователь уже существует");
            return "registration";
        }
        HashSet<Role> countryHashSet = new HashSet<>();
        countryHashSet.add(Role.USER);
        user.setRoles(countryHashSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}

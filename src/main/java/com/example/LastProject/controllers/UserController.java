package com.example.LastProject.controllers;

import com.example.LastProject.models.Role;
import com.example.LastProject.models.Specialnosti;
import com.example.LastProject.repositories.SpecialnostiRepository;
import com.example.LastProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.LastProject.models.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpecialnostiRepository specialnostiRepository;

    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @GetMapping("/")
    public String list_Users(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "users/listUsers";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<User> usersList = userRepository.findByUsernameContains(title);
        model.addAttribute("users",usersList);
        return "users/listUsers";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<User> user = userRepository.findById(id);
            ArrayList<User> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new User());
            model.addAttribute("users", arrayList);
            model.addAttribute("roles", Role.values());
        }
        else
        {
            model.addAttribute("roles", Role.values());
            model.addAttribute("users", new User());
        }
        Iterable<Specialnosti> speci = specialnostiRepository.findAll();
        model.addAttribute("specialnost", speci);
        model.addAttribute("idid", id);
        return "users/AddEditUser";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid User newUser, BindingResult result,
            @RequestParam(name = "roles[]", required = false) String[] roles,
            Model model)
    {
        User userFromDB = userRepository.findByUsername(newUser.getUsername());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой пользователь уже существует");
            Iterable<Specialnosti> speci = specialnostiRepository.findAll();
            model.addAttribute("specialnost", speci);
            model.addAttribute("roles", Role.values());
            model.addAttribute("idid", 0);
            return "users/AddEditUser";
        }
        if(result.hasErrors()) {
            Iterable<Specialnosti> speci = specialnostiRepository.findAll();
            model.addAttribute("specialnost", speci);
            model.addAttribute("roles", Role.values());
            model.addAttribute("idid", 0);
            return "users/AddEditUser";
        }
        else {
            if(newUser.getRoles() != null) {
                newUser.getRoles().clear();
                if (roles != null) {
                    for (String role_name :
                            roles) {
                        newUser.getRoles().add(Role.valueOf(role_name));
                    }
                }
                newUser.getRoles().add(Role.USER);
            }
            else
                newUser.setRoles(Collections.singleton(Role.USER));
            userRepository.save(newUser);
            return "redirect:/user/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid User newUser, BindingResult result,
                        @RequestParam(name = "roles[]", required = false) String[] roles,
                        Model model) {

        Optional<User> user = userRepository.findById(id);
        ArrayList<User> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        Iterable<Specialnosti> speci = specialnostiRepository.findAll();
        model.addAttribute("specialnost", speci);
        model.addAttribute("idid", id);
        model.addAttribute("roles", Role.values());

        User userFromDB = userRepository.findByUsernameAndIdNot(newUser.getUsername(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой пользователь уже существует");
            return "users/AddEditUser";
        }

        if(result.hasErrors()) {
            return "users/AddEditUser";
        }
        else {
            User us = userRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            if(newUser.getRoles() != null) {
                newUser.getRoles().clear();
                if (roles != null) {
                    for (String role_name :
                            roles) {
                        newUser.getRoles().add(Role.valueOf(role_name));
                    }
                }
                newUser.getRoles().add(Role.USER);
            }
            else
                newUser.setRoles(Collections.singleton(Role.USER));
            newUser.setPassword(getPasswordEncoder().encode(newUser.getPassword()));
            userRepository.save(newUser);
            return "redirect:/user/";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        userRepository.deleteById(id);
        return "redirect:/user/";
    }
}

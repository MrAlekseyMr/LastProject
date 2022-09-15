package com.example.LastProject.controllers;

import com.example.LastProject.models.Grazhdanstav;
import com.example.LastProject.models.Specialnosti;
import com.example.LastProject.models.User;
import com.example.LastProject.repositories.GrazhdanstavRepository;
import com.example.LastProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grazh")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class GrazhController {
    @Autowired
    private GrazhdanstavRepository grazhdanstavRepository;

    @GetMapping("/")
    public String list_Users(Model model)
    {
        Iterable<Grazhdanstav> users = grazhdanstavRepository.findAll();
        model.addAttribute("users",users);
        return "grazh/listGrazh";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<Grazhdanstav> usersList = grazhdanstavRepository.findByNamegrazhdanstvoContains(title);
        model.addAttribute("users",usersList);
        return "grazh/listGrazh";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        grazhdanstavRepository.deleteById(id);
        return "redirect:/grazh/";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<Grazhdanstav> user = grazhdanstavRepository.findById(id);
            ArrayList<Grazhdanstav> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Grazhdanstav());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Grazhdanstav());
        }
        model.addAttribute("idid", id);
        return "grazh/AddEditGrazh";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Grazhdanstav newUser, BindingResult result,
            Model model)
    {
        Grazhdanstav userFromDB = grazhdanstavRepository.findByNamegrazhdanstvo(newUser.getNamegrazhdanstvo());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такое гражданство уже существует");
            model.addAttribute("idid", 0);
            return "grazh/AddEditGrazh";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "grazh/AddEditGrazh";
        }
        else {
            grazhdanstavRepository.save(newUser);
            return "redirect:/grazh/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Grazhdanstav newUser, BindingResult result,
                        Model model) {

        Optional<Grazhdanstav> user = grazhdanstavRepository.findById(id);
        ArrayList<Grazhdanstav> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Grazhdanstav userFromDB = grazhdanstavRepository.findByNamegrazhdanstvoAndIdNot(newUser.getNamegrazhdanstvo(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такое гражданство уже существует");
            return "grazh/AddEditGrazh";
        }

        if(result.hasErrors()) {
            return "grazh/AddEditGrazh";
        }
        else {
            Grazhdanstav us = grazhdanstavRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            grazhdanstavRepository.save(newUser);
            return "redirect:/grazh/";
        }
    }
}

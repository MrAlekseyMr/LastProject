package com.example.LastProject.controllers;

import com.example.LastProject.models.Basedisciplin;
import com.example.LastProject.models.Fakulteti;
import com.example.LastProject.repositories.BasedisciplinRepository;
import com.example.LastProject.repositories.FakultetiRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/disciplins")
public class DisciplinsController {
    @Autowired
    private BasedisciplinRepository basedisciplinRepository;

    @GetMapping("/")
    public String list_Users(Model model) {
        Iterable<Basedisciplin> users = basedisciplinRepository.findAll();
        model.addAttribute("users", users);
        return "disciplins/listDisciplins";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model) {
        List<Basedisciplin> usersList = basedisciplinRepository.findByNamedisciplinsContains(title);
        model.addAttribute("users", usersList);
        return "disciplins/listDisciplins";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        basedisciplinRepository.deleteById(id);
        return "redirect:/disciplins/";
    }

    @GetMapping("/{id}")
    public String addedit(@PathVariable("id") Integer id, Model model) {
        if (id != 0) {
            Optional<Basedisciplin> user = basedisciplinRepository.findById(id);
            ArrayList<Basedisciplin> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Basedisciplin());
            model.addAttribute("users", arrayList);
        } else {
            model.addAttribute("users", new Basedisciplin());
        }
        model.addAttribute("idid", id);
        return "disciplins/AddEditDisciplin";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Basedisciplin newUser, BindingResult result,
            Model model) {

        Basedisciplin userFromDB = basedisciplinRepository.findByNamedisciplins(newUser.getNamedisciplins());
        if (userFromDB != null) {
            model.addAttribute("error", "Такая дисциплина уже существует");
            model.addAttribute("idid", 0);
            return "disciplins/AddEditDisciplin";
        }
        if (result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "disciplins/AddEditDisciplin";
        } else {
            basedisciplinRepository.save(newUser);
            return "redirect:/disciplins/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @ModelAttribute("users2") @Valid Basedisciplin newUser, BindingResult result,
                       Model model) {

        Optional<Basedisciplin> user = basedisciplinRepository.findById(id);
        ArrayList<Basedisciplin> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Basedisciplin userFromDB = basedisciplinRepository.findByNamedisciplinsAndIdNot(newUser.getNamedisciplins(), id);
        if (userFromDB != null) {
            model.addAttribute("error", "Такая дисциплина уже существует");
            return "disciplins/AddEditDisciplin";
        }

        if (result.hasErrors()) {
            return "disciplins/AddEditDisciplin";
        } else {
            Basedisciplin us = basedisciplinRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            basedisciplinRepository.save(newUser);
            return "redirect:/disciplins/";
        }
    }
}

package com.example.LastProject.controllers;

import com.example.LastProject.models.*;
import com.example.LastProject.repositories.BasedisciplinRepository;
import com.example.LastProject.repositories.FormsobuchRepository;
import com.example.LastProject.repositories.PlannaborRepository;
import com.example.LastProject.repositories.SpecialnostiRepository;
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
@RequestMapping("/plans")
@PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
public class PlansController {
    @Autowired
    private PlannaborRepository plannaborRepository;
    @Autowired
    private SpecialnostiRepository specialnostiRepository;
    @Autowired
    private FormsobuchRepository formsobuchRepository;

    @GetMapping("/")
    public String list_Users(Model model) {
        Iterable<Plannabor> users = plannaborRepository.findAll();
        model.addAttribute("users", users);
        return "plans/listPlans";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        plannaborRepository.deleteById(id);
        return "redirect:/plans/";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        Iterable<Specialnosti> specialnostis = specialnostiRepository.findAll();
        Iterable<Formsobuch> formsobuches = formsobuchRepository.findAll();
        model.addAttribute("formsobuch", formsobuches);
        model.addAttribute("specialnosts", specialnostis);

        if(id != 0) {
            Optional<Plannabor> user = plannaborRepository.findById(id);
            ArrayList<Plannabor> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Plannabor());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Plannabor());
        }
        model.addAttribute("idid", id);
        return "plans/AddEditPlan";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Plannabor newUser, BindingResult result,
            Model model)
    {
        Iterable<Specialnosti> specialnostis = specialnostiRepository.findAll();
        Iterable<Formsobuch> formsobuches = formsobuchRepository.findAll();
        model.addAttribute("formsobuch", formsobuches);
        model.addAttribute("specialnosts", specialnostis);

        Plannabor userFromDB = plannaborRepository.findByspecforms(newUser.getSpecialnostid(), newUser.getFormsobucid());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой план с указанной специальностью и формой обучения уже существует");
            model.addAttribute("idid", 0);
            return "plans/AddEditPlan";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "plans/AddEditPlan";
        }
        else {
            plannaborRepository.save(newUser);
            return "redirect:/plans/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Plannabor newUser, BindingResult result,
                        Model model) {
        Iterable<Specialnosti> specialnostis = specialnostiRepository.findAll();
        Iterable<Formsobuch> formsobuches = formsobuchRepository.findAll();
        model.addAttribute("formsobuch", formsobuches);
        model.addAttribute("specialnosts", specialnostis);

        Optional<Plannabor> user = plannaborRepository.findById(id);
        ArrayList<Plannabor> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Plannabor userFromDB = plannaborRepository.findBySpecialnostidAndFormsobucidAndAndIdNot(newUser.getSpecialnostid(), newUser.getFormsobucid(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой план с указанной специальностью и формой обучения уже существует");
            return "plans/AddEditPlan";
        }

        if(result.hasErrors()) {
            return "plans/AddEditPlan";
        }
        else {
            Plannabor us = plannaborRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            plannaborRepository.save(newUser);
            return "redirect:/plans/";
        }
    }
}

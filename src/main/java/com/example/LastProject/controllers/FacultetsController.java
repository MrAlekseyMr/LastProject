package com.example.LastProject.controllers;

import com.example.LastProject.models.Fakulteti;
import com.example.LastProject.models.Formsobuch;
import com.example.LastProject.repositories.FakultetiRepository;
import com.example.LastProject.repositories.FormsobuchRepository;
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
@RequestMapping("/facultets")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class FacultetsController {
    @Autowired
    private FakultetiRepository fakultetiRepository;

    @GetMapping("/")
    public String list_Users(Model model)
    {
        Iterable<Fakulteti> users = fakultetiRepository.findAll();
        model.addAttribute("users",users);
        return "facultets/listFacultets";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<Fakulteti> usersList = fakultetiRepository.findByNamefakultetContains(title);
        model.addAttribute("users",usersList);
        return "facultets/listFacultets";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        fakultetiRepository.deleteById(id);
        return "redirect:/facultets/";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<Fakulteti> user = fakultetiRepository.findById(id);
            ArrayList<Fakulteti> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Fakulteti());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Fakulteti());
        }
        model.addAttribute("idid", id);
        return "facultets/AddEditFacultet";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Fakulteti newUser, BindingResult result,
            Model model)
    {
        Fakulteti userFromDB = fakultetiRepository.findByNamefakultet(newUser.getNamefakultet());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой факультет уже существует");
            model.addAttribute("idid", 0);
            return "facultets/AddEditFacultet";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "facultets/AddEditFacultet";
        }
        else {
            fakultetiRepository.save(newUser);
            return "redirect:/facultets/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Fakulteti newUser, BindingResult result,
                        Model model) {

        Optional<Fakulteti> user = fakultetiRepository.findById(id);
        ArrayList<Fakulteti> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Fakulteti userFromDB = fakultetiRepository.findByNamefakultetAndIdNot(newUser.getNamefakultet(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой факультет уже существует");
            return "facultets/AddEditFacultet";
        }

        if(result.hasErrors()) {
            return "facultets/AddEditFacultet";
        }
        else {
            Fakulteti us = fakultetiRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            fakultetiRepository.save(newUser);
            return "redirect:/facultets/";
        }
    }
}

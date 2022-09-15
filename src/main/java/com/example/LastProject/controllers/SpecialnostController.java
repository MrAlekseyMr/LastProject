package com.example.LastProject.controllers;

import com.example.LastProject.models.Fakulteti;
import com.example.LastProject.models.Specialnosti;
import com.example.LastProject.repositories.FakultetiRepository;
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
@RequestMapping("/specialnost")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class SpecialnostController {
    @Autowired
    private FakultetiRepository fakultetiRepository;

    @Autowired
    private SpecialnostiRepository specialnostiRepository;

    @GetMapping("/")
    public String list_Users(Model model)
    {
        Iterable<Specialnosti> users = specialnostiRepository.findAll();
        model.addAttribute("users",users);
        return "specialnost/listSpecialnost";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<Specialnosti> usersList = specialnostiRepository.findByNamespecialnostContains(title);
        model.addAttribute("users",usersList);
        return "specialnost/listSpecialnost";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        specialnostiRepository.deleteById(id);
        return "redirect:/specialnost/";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        Iterable<Fakulteti> fa = fakultetiRepository.findAll();
        model.addAttribute("facultets", fa);
        if(id != 0) {
            Optional<Specialnosti> user = specialnostiRepository.findById(id);
            ArrayList<Specialnosti> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Specialnosti());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Specialnosti());
        }
        model.addAttribute("idid", id);
        return "specialnost/AddEditSpecialnost";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Specialnosti newUser, BindingResult result,
            Model model)
    {
        Iterable<Fakulteti> fa = fakultetiRepository.findAll();
        model.addAttribute("facultets", fa);

        Specialnosti userFromDB = specialnostiRepository.findByNamespecialnost(newUser.getNamespecialnost());
        if(userFromDB != null)
        {
            model.addAttribute("error1","Такое название специальности уже существует");
            model.addAttribute("idid", 0);
            return "specialnost/AddEditSpecialnost";
        }
        userFromDB = specialnostiRepository.findByKodpospo(newUser.getKodpospo());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой код специальности уже существует");
            model.addAttribute("idid", 0);
            return "specialnost/AddEditSpecialnost";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "specialnost/AddEditSpecialnost";
        }
        else {
            specialnostiRepository.save(newUser);
            return "redirect:/specialnost/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Specialnosti newUser, BindingResult result,
                        Model model) {

        Iterable<Fakulteti> fa = fakultetiRepository.findAll();
        model.addAttribute("facultets", fa);

        Optional<Specialnosti> user = specialnostiRepository.findById(id);
        ArrayList<Specialnosti> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Specialnosti userFromDB = specialnostiRepository.findByKodpospoAndIdNot(newUser.getKodpospo(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой код специальности уже существует");
            return "specialnost/AddEditSpecialnost";
        }
        userFromDB = specialnostiRepository.findByNamespecialnostAndIdNot(newUser.getNamespecialnost(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error1","Такая специальность уже существует");
            return "specialnost/AddEditSpecialnost";
        }

        if(result.hasErrors()) {
            return "specialnost/AddEditSpecialnost";
        }
        else {
            Specialnosti us = specialnostiRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            specialnostiRepository.save(newUser);
            return "redirect:/specialnost/";
        }
    }
}

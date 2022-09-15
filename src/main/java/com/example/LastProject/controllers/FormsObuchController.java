package com.example.LastProject.controllers;

import com.example.LastProject.models.Formsobuch;
import com.example.LastProject.models.Grazhdanstav;
import com.example.LastProject.models.User;
import com.example.LastProject.repositories.FormsobuchRepository;
import com.example.LastProject.repositories.GrazhdanstavRepository;
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
@RequestMapping("/formsobuch")
public class FormsObuchController {
    @Autowired
    private FormsobuchRepository formsobuchRepository;

    @GetMapping("/")
    public String list_Users(Model model)
    {
        Iterable<Formsobuch> users = formsobuchRepository.findAll();
        model.addAttribute("users",users);
        return "formsobuch/listForms";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<Formsobuch> usersList = formsobuchRepository.findByNameformobuchContains(title);
        model.addAttribute("users",usersList);
        return "formsobuch/listForms";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        formsobuchRepository.deleteById(id);
        return "redirect:/formsobuch/";
    }

    @GetMapping("/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<Formsobuch> user = formsobuchRepository.findById(id);
            ArrayList<Formsobuch> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Formsobuch());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Formsobuch());
        }
        model.addAttribute("idid", id);
        return "formsobuch/AddEditForms";
    }

    @PostMapping("/add")
    public String AddPost(
            @ModelAttribute("users") @Valid Formsobuch newUser, BindingResult result,
            Model model)
    {
        Formsobuch userFromDB = formsobuchRepository.findByNameformobuch(newUser.getNameformobuch());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такая форма обучения уже существует");
            model.addAttribute("idid", 0);
            return "formsobuch/AddEditForms";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "formsobuch/AddEditForms";
        }
        else {
            formsobuchRepository.save(newUser);
            return "redirect:/formsobuch/";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Formsobuch newUser, BindingResult result,
                        Model model) {

        Optional<Formsobuch> user = formsobuchRepository.findById(id);
        ArrayList<Formsobuch> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Formsobuch userFromDB = formsobuchRepository.findByNameformobuchAndAndIdNot(newUser.getNameformobuch(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такая форма обучения уже существует");
            return "formsobuch/AddEditForms";
        }

        if(result.hasErrors()) {
            return "formsobuch/AddEditForms";
        }
        else {
            Formsobuch us = formsobuchRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            formsobuchRepository.save(newUser);
            return "redirect:/formsobuch/";
        }
    }

}

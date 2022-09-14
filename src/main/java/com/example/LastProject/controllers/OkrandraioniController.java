package com.example.LastProject.controllers;

import com.example.LastProject.models.Grazhdanstav;
import com.example.LastProject.models.Okrug;
import com.example.LastProject.models.Specialnosti;
import com.example.LastProject.models.User;
import com.example.LastProject.repositories.GrazhdanstavRepository;
import com.example.LastProject.repositories.OkrugRepository;
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
@RequestMapping("/okrandraioni")
public class OkrandraioniController {
    @Autowired
    private OkrugRepository okrugRepository;

    @GetMapping("/listokrugs")
    public String list_Users(Model model)
    {
        Iterable<Okrug> users = okrugRepository.findAll();
        model.addAttribute("users",users);
        return "okrandraioni/listOkrugov";
    }

    @GetMapping("/listokrugs/search")
    public String search(
            @RequestParam("title") String title,
            Model model)
    {
        List<Okrug> usersList = okrugRepository.findByNameokrugContains(title);
        model.addAttribute("users",usersList);
        return "okrandraioni/listOkrugov";
    }

    @GetMapping("/listokrugs/{id}")
    public String addedit (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<Okrug> user = okrugRepository.findById(id);
            ArrayList<Okrug> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Okrug());
            model.addAttribute("users", arrayList);
        }
        else
        {
            model.addAttribute("users", new Okrug());
        }
        model.addAttribute("idid", id);
        return "okrandraioni/AddEditOkrug";
    }

    @PostMapping("/addOkrug")
    public String AddPost(
            @ModelAttribute("users") @Valid Okrug newUser, BindingResult result,
            Model model)
    {
        Okrug userFromDB = okrugRepository.findByNameokrug(newUser.getNameokrug());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой округ уже существует");
            model.addAttribute("idid", 0);
            return "okrandraioni/AddEditOkrug";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "okrandraioni/AddEditOkrug";
        }
        else {
            okrugRepository.save(newUser);
            return "redirect:/okrandraioni/listokrugs";
        }
    }

    @PostMapping("/editOkrug/{id}")
    public String edit (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Okrug newUser, BindingResult result,
                        Model model) {

        Optional<Okrug> user = okrugRepository.findById(id);
        ArrayList<Okrug> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Okrug userFromDB = okrugRepository.findByNameokrugAndIdNot(newUser.getNameokrug(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой округ уже существует");
            return "okrandraioni/AddEditOkrug";
        }

        if(result.hasErrors()) {
            return "okrandraioni/AddEditOkrug";
        }
        else {
            Okrug us = okrugRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            okrugRepository.save(newUser);
            return "redirect:/okrandraioni/listokrugs";
        }
    }

    @GetMapping("/deleteOkrug/{id}")
    public String delete (@PathVariable("id") Integer id, Model model) {
        okrugRepository.deleteById(id);
        return "redirect:/okrandraioni/listokrugs";
    }
}

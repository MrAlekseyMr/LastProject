package com.example.LastProject.controllers;

import com.example.LastProject.models.*;
import com.example.LastProject.repositories.GrazhdanstavRepository;
import com.example.LastProject.repositories.OkrugRepository;
import com.example.LastProject.repositories.RaioniRepository;
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

    @Autowired
    private RaioniRepository raioniRepository;

    @GetMapping("/listoraioni")
    public String list_Users1(Model model)
    {
        Iterable<Raioni> users = raioniRepository.findAll();
        model.addAttribute("users",users);
        return "okrandraioni/listRaioni";
    }

    @GetMapping("/listoraioni/search")
    public String search1(
            @RequestParam("title") String title,
            Model model)
    {
        List<Raioni> usersList = raioniRepository.findByNameraionContains(title);
        model.addAttribute("users",usersList);
        return "okrandraioni/listRaioni";
    }

    @GetMapping("/deleteRaion/{id}")
    public String delete1 (@PathVariable("id") Integer id, Model model) {
        raioniRepository.deleteById(id);
        return "redirect:/okrandraioni/listoraioni";
    }

    @GetMapping("/listoraioni/{id}")
    public String addedit1 (@PathVariable("id") Integer id, Model model) {
        if(id != 0) {
            Optional<Raioni> user = raioniRepository.findById(id);
            ArrayList<Raioni> arrayList = new ArrayList<>();
            Iterable<Okrug> okrugs = okrugRepository.findAll();
            user.ifPresent(arrayList::add);
            model.addAttribute("users2", new Raioni());
            model.addAttribute("users", arrayList);
            model.addAttribute("okrugs", okrugs);
        }
        else
        {
            Iterable<Okrug> okrugs = okrugRepository.findAll();
            model.addAttribute("users", new Raioni());
            model.addAttribute("okrugs", okrugs);
        }
        model.addAttribute("idid", id);
        return "okrandraioni/AddEditRaion";
    }

    @PostMapping("/addRaion")
    public String AddPost1(
            @ModelAttribute("users") @Valid Raioni newUser, BindingResult result,
            Model model)
    {
        Iterable<Okrug> okrugs = okrugRepository.findAll();
        model.addAttribute("okrugs", okrugs);
        Raioni userFromDB = raioniRepository.findByNameraion(newUser.getNameraion());
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой район уже существует");
            model.addAttribute("idid", 0);
            return "okrandraioni/AddEditRaion";
        }
        if(result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "okrandraioni/AddEditRaion";
        }
        else {
            raioniRepository.save(newUser);
            return "redirect:/okrandraioni/listoraioni";
        }
    }

    @PostMapping("/editRaion/{id}")
    public String edit1 (@PathVariable("id") Integer id,
                        @ModelAttribute("users2") @Valid Raioni newUser, BindingResult result,
                        Model model) {

        Optional<Raioni> user = raioniRepository.findById(id);
        ArrayList<Raioni> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        Iterable<Okrug> okrugs = okrugRepository.findAll();
        model.addAttribute("okrugs", okrugs);
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);

        Raioni userFromDB = raioniRepository.findByNameraionAndIdNot(newUser.getNameraion(),id);
        if(userFromDB != null)
        {
            model.addAttribute("error","Такой район уже существует");
            return "okrandraioni/AddEditRaion";
        }

        if(result.hasErrors()) {
            return "okrandraioni/AddEditRaion";
        }
        else {
            Raioni us = raioniRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            raioniRepository.save(newUser);
            return "redirect:/okrandraioni/listoraioni";
        }
    }
}

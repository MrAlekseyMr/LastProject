package com.example.LastProject.controllers;

import com.example.LastProject.models.*;
import com.example.LastProject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/abiturients")
public class AbiturController {
    @Autowired
    private AbiturientRepository abiturientRepository;
    @Autowired
    private DisciplinsAbiturientRepository disciplinsAbiturientRepository;
    @Autowired
    private FormsobuchSpecialnostRepository formsobuchSpecialnostRepository;
    @Autowired
    private RaioniRepository raioniRepository;
    @Autowired
    private GrazhdanstavRepository grazhdanstavRepository;
    @Autowired
    private PlannaborRepository plannaborRepository;
    public List<CustomAbitur> convertation(Iterable<Abiturient> abiturients){
        List<CustomAbitur> models = new ArrayList<CustomAbitur>();
        Iterable<Abiturient> users = abiturients;
        List<Abiturient> target = new ArrayList<Abiturient>();
        users.forEach(target::add);
        for(int i=0;i<target.size();i++)
        {
            CustomAbitur h = new CustomAbitur();
            h.setId(target.get(i).getId());
            h.setFamilia(target.get(i).getFamilia());
            h.setIma(target.get(i).getIma());
            h.setSnils(target.get(i).getSnils());
            h.setSpecialnost(formsobuchSpecialnostRepository.findByAbiturientid_Id(h.getId()).getPlannaboraid().getSpecialnostid().getKodpospo()+" "+
                    formsobuchSpecialnostRepository.findByAbiturientid_Id(h.getId()).getPlannaboraid().getSpecialnostid().getNamespecialnost()+" - "+
                    formsobuchSpecialnostRepository.findByAbiturientid_Id(h.getId()).getPlannaboraid().getFormsobucid().getNameformobuch());
            h.setList_discp(disciplinsAbiturientRepository.findByAbiturientid_Id(h.getId()));
            int res = 0;
            for(int j = 0;j<disciplinsAbiturientRepository.findByAbiturientid_Id(h.getId()).size();j++){
                res+=disciplinsAbiturientRepository.findByAbiturientid_Id(h.getId()).get(j).getMark();
            }
            int size = disciplinsAbiturientRepository.findByAbiturientid_Id(h.getId()).size();
            Double result = (double)res/(double)size;
            h.setSrball(result);
            models.add(h);
        }
        return models;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("/")
    public String list_Users(Model model) {
        List<CustomAbitur> models = convertation(abiturientRepository.findAll());
        model.addAttribute("users", models);
        return "abiturients/listAbitur";
    }

    @GetMapping("/konkurs/")
    public String list_Usersf(Model model) {
        model.addAttribute("spisok", plannaborRepository.findAll());
        return "abiturients/KonkursList";
    }

    @GetMapping("/konkurs/{id}")
    public String list_Usersff(Model model,@PathVariable("id") Integer id) {
        Plannabor f = plannaborRepository.findById(id).get();
        model.addAttribute("spec",f.getSpecialnostid().getKodpospo()+" "+f.getSpecialnostid().getNamespecialnost());
        List<FormsobuchSpecialnost> ff = formsobuchSpecialnostRepository.findByPlannaboraid(f);
        List<ModelKonkurs> list = new ArrayList<>();
        for(int i = 0; i<ff.size(); i++){
            ModelKonkurs h = new ModelKonkurs();
            h.setFi(ff.get(i).getAbiturientid().getFamilia()+" "+ff.get(i).getAbiturientid().getIma());
            h.setSnils(ff.get(i).getAbiturientid().getSnils());
            int res = 0;
            for(int j = 0;j<disciplinsAbiturientRepository.findByAbiturientid_Id(ff.get(i).getAbiturientid().getId()).size();j++){
                res+=disciplinsAbiturientRepository.findByAbiturientid_Id(ff.get(i).getAbiturientid().getId()).get(j).getMark();
            }
            h.setSrball((double)res/(double)disciplinsAbiturientRepository.findByAbiturientid_Id(ff.get(i).getAbiturientid().getId()).size());
            list.add(h);
        }
        list.sort((o1, o2) -> Double.compare(o2.srball, o1.srball));
        model.addAttribute("users",list);
        return "abiturients/AbiturKonkurs";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("/search")
    public String search(
            @RequestParam("title") String title,
            Model model) {
        List<CustomAbitur> models = convertation(abiturientRepository.findByFamiliaContainsOrImaContainsOrSnilsContains(title,title,title));
        model.addAttribute("users", models);
        return "abiturients/listAbitur";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        abiturientRepository.deleteById(id);
        return "redirect:/abiturients/";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("/{id}")
    public String addedit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("raions",raioniRepository.findAll());
        model.addAttribute("grazhdanstva",grazhdanstavRepository.findAll());
        model.addAttribute("specialnosti",plannaborRepository.findAll());
        if (id != 0) {
            Optional<Abiturient> user = abiturientRepository.findById(id);
            ArrayList<Abiturient> arrayList = new ArrayList<>();
            user.ifPresent(arrayList::add);
            FormsobuchSpecialnost f = formsobuchSpecialnostRepository.findByAbiturientid_Id(id);
            model.addAttribute("users2", new Abiturient());
            model.addAttribute("users", arrayList);
            model.addAttribute("speciselected", f.getPlannaboraid().getId());
        } else {
            model.addAttribute("users", new Abiturient());
        }
        model.addAttribute("idid", id);
        return "abiturients/AddEditAbitur";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @PostMapping("/add")
    public String AddPost(
            @RequestParam(name = "specid", required = false) Integer idSpecialnostPlanNabor,
            @ModelAttribute("users") @Valid Abiturient newUser,
            BindingResult result,
            Model model) {

        model.addAttribute("raions",raioniRepository.findAll());
        model.addAttribute("grazhdanstva",grazhdanstavRepository.findAll());
        model.addAttribute("specialnosti",plannaborRepository.findAll());
        Abiturient userFromDB = abiturientRepository.findBySnils(newUser.getSnils());
        if (userFromDB != null) {
            model.addAttribute("errorSnils", "Такой СНИЛС уже существует");
            model.addAttribute("idid", 0);
            return "abiturients/AddEditAbitur";
        }
        userFromDB = abiturientRepository.findBySeriapasportaAndNomerpasporta(newUser.getSeriapasporta(),newUser.getNomerpasporta());
        if (userFromDB != null) {
            model.addAttribute("errorPasport", "Такой паспорта уже существует");
            model.addAttribute("idid", 0);
            return "abiturients/AddEditAbitur";
        }
        if (result.hasErrors()) {
            model.addAttribute("idid", 0);
            return "abiturients/AddEditAbitur";
        } else {
            abiturientRepository.save(newUser);
            FormsobuchSpecialnost f = new FormsobuchSpecialnost();
            f.setAbiturientid(newUser);
            Optional<Plannabor> g = plannaborRepository.findById(idSpecialnostPlanNabor);
            f.setPlannaboraid(g.get());
            formsobuchSpecialnostRepository.save(f);
            return "redirect:/abiturients/";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @RequestParam(name = "specid", required = false) Integer idSpecialnostPlanNabor,
                       @ModelAttribute("users2") @Valid Abiturient newUser, BindingResult result,
                       Model model) {

        model.addAttribute("raions",raioniRepository.findAll());
        model.addAttribute("grazhdanstva",grazhdanstavRepository.findAll());
        model.addAttribute("specialnosti",plannaborRepository.findAll());
        Optional<Abiturient> user = abiturientRepository.findById(id);
        ArrayList<Abiturient> arrayList = new ArrayList<>();
        user.ifPresent(arrayList::add);
        FormsobuchSpecialnost fg = formsobuchSpecialnostRepository.findByAbiturientid_Id(id);
/*        model.addAttribute("users2", new Abiturient());*/
        model.addAttribute("users", arrayList);
        model.addAttribute("idid", id);
        model.addAttribute("speciselected", fg.getPlannaboraid().getId());

        if (result.hasErrors()) {
            return "abiturients/AddEditAbitur";
        }

        Abiturient userFromDB = abiturientRepository.findBySnilsAndAndIdNot(newUser.getSnils(),id);
        if (userFromDB != null) {
            model.addAttribute("errorSnils", "Такой СНИЛС уже существует");
            return "abiturients/AddEditAbitur";
        }
        userFromDB = abiturientRepository.findBySeriapasportaAndNomerpasportaAndIdNot(newUser.getSeriapasporta(),newUser.getNomerpasporta(),id);
        if (userFromDB != null) {
            model.addAttribute("errorPasport", "Такой паспорта уже существует");
            return "abiturients/AddEditAbitur";
        }

        if (result.hasErrors()) {
            return "abiturients/AddEditAbitur";
        } else {
            Abiturient us = abiturientRepository.findById(id).orElseThrow();
            newUser.setId(us.getId());
            abiturientRepository.save(newUser);

            FormsobuchSpecialnost f = new FormsobuchSpecialnost();
            f.setId(formsobuchSpecialnostRepository.findByAbiturientid_Id(id).getId());
            f.setPlannaboraid(plannaborRepository.findById(idSpecialnostPlanNabor).get());
            f.setAbiturientid(newUser);
            formsobuchSpecialnostRepository.save(f);

            return "redirect:/abiturients/";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("EditMarks/{id}")
    public String addeditMarks(@PathVariable("id") Integer id, Model model) {
        List<DisciplinsAbiturient> list = disciplinsAbiturientRepository.findByAbiturientid_Id(id);
        model.addAttribute("users2",list);
        model.addAttribute("list",list);
        model.addAttribute("id",id);
        model.addAttribute("abit",abiturientRepository.findById(id).get().getFamilia()+" "+abiturientRepository.findById(id).get().getIma());
        return "abiturients/EditMarks";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @GetMapping("EditMark/{id}")
    public String addeditMarkget(@PathVariable("id") Integer id, Model model) {
        DisciplinsAbiturient disciplinsAbiturient = disciplinsAbiturientRepository.findById(id).get();
        model.addAttribute("predmet",disciplinsAbiturient.getDisciplinid().getNamedisciplins());
        model.addAttribute("mark",disciplinsAbiturient.getMark());
        model.addAttribute("abit",abiturientRepository.findById(disciplinsAbiturient.getAbiturientid().getId()).get().getFamilia()+" "+abiturientRepository.findById(disciplinsAbiturient.getAbiturientid().getId()).get().getIma());
        model.addAttribute("userid",disciplinsAbiturient.getAbiturientid().getId());
        return "abiturients/EditMark";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN') || hasAnyAuthority('SOTRUDNIKPK')")
    @PostMapping("EditMark/{id}")
    public String addeditMarkpost(@PathVariable("id") Integer id,
                                  @RequestParam(name = "mark") Integer newMark,
                                  Model model) {
        DisciplinsAbiturient disciplinsAbiturient = disciplinsAbiturientRepository.findById(id).get();
        disciplinsAbiturient.setMark(newMark);
        disciplinsAbiturientRepository.save(disciplinsAbiturient);
        return "redirect:/abiturients/EditMarks/"+disciplinsAbiturient.getAbiturientid().getId();
    }
}

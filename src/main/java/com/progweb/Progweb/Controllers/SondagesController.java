package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Repository.SondagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/sondage")
public class SondagesController {

    @Autowired
    private SondagesRepository sondagesRepository;

    @GetMapping("/accueil")
    public String index () {
        return "Page_accueil";
    }

    @GetMapping("/gestion")
    public String showGestionSondages(){
        return "Page_gestionSondages";
    }

    @PostMapping("/add")
    public String addSondage(Sondages sondage, Model model){
        Sondages s = new Sondages(sondage.getLibeller(), sondage.getDateRDV(), sondage.getLieuRDV(),1);
        sondagesRepository.save(s);
        model.addAttribute("message", "Le sondage a bien été ajouter");
        model.addAttribute("sondages", sondagesRepository.findAll());
        return "Page_accueil";
    }

}

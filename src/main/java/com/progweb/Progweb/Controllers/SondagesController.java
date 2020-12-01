package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path="/sondage")
public class SondagesController {

    @Autowired
    private SondagesRepository sondagesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/accueil")
    public String index (Model model) {
        Users userModel = (Users)model.getAttribute("user");
        model.addAttribute("user",userModel);
        return "Page_accueil";
    }

    @GetMapping("/gestion/{id}")
    public String showGestionSondages(@PathVariable int id,Model model)
    {
        Users user = usersRepository.findById(id).get();
        String messageModel = (String) model.getAttribute("message");
        model.addAttribute("message",messageModel);
        model.addAttribute("alertClass", "alert-success");
        model.addAttribute("user",user);
        return "Page_gestionSondages";

    }

    @PostMapping("/add")
    public String addSondage(Sondages sondage, RedirectAttributes attributes){

        Sondages s = new Sondages(sondage.getLibeller(), sondage.getDateRDV(), sondage.getLieuRDV(),sondage.getUser_fk());
        Users user = usersRepository.findById(s.getUser_fk()).get();
        user.getSondages().add(s);
        usersRepository.save(user);
        attributes.addFlashAttribute("message", "Le sondage a bien été ajouter");
        return "redirect:/sondage/gestion/"+user.getId();
    }

    @GetMapping("/delete/{id}/{idUser}")
    public String deleteSondage(@PathVariable int id,@PathVariable int idUser, RedirectAttributes attributes)
    {
        sondagesRepository.deleteById(id);
        attributes.addFlashAttribute("message","Le sondage à bien été supprimé");
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/sondage/gestion/"+idUser;

    }

}

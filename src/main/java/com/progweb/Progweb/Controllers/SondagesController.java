package com.progweb.Progweb.Controllers;
import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import io.netty.handler.codec.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/sondage")
public class SondagesController {

    @Autowired
    private SondagesRepository sondagesRepository;
    @Autowired
    private UsersRepository usersRepository;

    //private Cookie cookie;

    //Action qui affiche la page d'accueil
    //@GetMapping("/accueil")
    @GetMapping({"/accueil", "/accueil/{id}"})
    public String index (Model model, @PathVariable("id")Optional<Integer> id) {
        //Si l'id est null c'est que nous affichons la page d'accueil depuis la page de connexion
        if(!id.isPresent()){
            Users userModel = (Users)model.getAttribute("user");
            model.addAttribute("user",userModel);
        }
        //Si l'id n'est pas null c'est que nous somme déja connecté
        else
        {
            Users user = usersRepository.findById(id.get()).get();
            model.addAttribute("user",user);
        }
        //On récupère la liste des sondages
        Iterable<Sondages> sondages = sondagesRepository.findAll();
        model.addAttribute("sondages",sondages);
        return "Page_accueil";
    }
    //Action qui affiche la page de gestion des sondages
    @GetMapping("/gestion/{id}")
    public String showGestionSondages(@PathVariable int id,Model model)
    {
        //Rêquete qui récupère l'utilisateur et tous ses sondages pour les afficher dans la page de gestion de sondage
        Users user = usersRepository.findById(id).get();
        String messageModel = (String) model.getAttribute("message");
        model.addAttribute("message",messageModel);
        model.addAttribute("alertClass", "alert-success");
        model.addAttribute("user",user);
        return "Page_gestionSondages";

    }
    //Action pour ajouter un nouveau sondage
    @PostMapping("/add")
    public String addSondage(Sondages sondage, RedirectAttributes attributes){

        Sondages s = new Sondages(sondage.getLibeller(), sondage.getDateRDV(), sondage.getLieuRDV(),sondage.getUser_fk());
        Users user = usersRepository.findById(s.getUser_fk()).get();
        user.getSondages().add(s);
        usersRepository.save(user);
        attributes.addFlashAttribute("message", "Le sondage a bien été ajouter");
        return "redirect:/sondage/gestion/"+user.getId();
    }
    //Action pour supprimer un sondage
    @GetMapping("/delete/{id}/{idUser}")
    public String deleteSondage(@PathVariable int id,@PathVariable int idUser, RedirectAttributes attributes)
    {
        sondagesRepository.deleteById(id);
        attributes.addFlashAttribute("message","Le sondage à bien été supprimé");
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/sondage/gestion/"+idUser;

    }
    //Action pour afficher la page de modification d'un sondage
    @GetMapping("/showPageUpdate/{id}/{idUser}")
    public String showUpdateSondage(@PathVariable int id,@PathVariable int idUser, Model model)
    {
        Sondages sondage = sondagesRepository.findById(id).get();
        model.addAttribute("sondage", sondage);
        model.addAttribute("idUser", idUser);
        return "Page_gestionSondagesUpdate";

    }
    //Action pour modifier un sondage
    @PostMapping("/update")
    public String updateSondage(Sondages sondage, RedirectAttributes attributes){
        Sondages s = sondagesRepository.findById(sondage.getIdSondage()).get();
        s.setDateRDV(sondage.getDateRDV());
        s.setLibeller(sondage.getLibeller());
        s.setLieuRDV(sondage.getLieuRDV());
        sondagesRepository.save(s);
        attributes.addFlashAttribute("message", "Le sondage a bien été mis à jour");
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/sondage/gestion/"+sondage.getUser_fk();
    }


}

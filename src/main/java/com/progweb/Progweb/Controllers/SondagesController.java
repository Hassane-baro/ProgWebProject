package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Token;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.TokensRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/sondage")
public class SondagesController {

    @Autowired
    private SondagesRepository sondagesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TokensRepository tokensRepository;


    //Action qui affiche la page d'accueil
    //@GetMapping("/accueil")
    @GetMapping({"/accueil", "/accueil/{id}"})
    public String index (Model model, @PathVariable("id")Optional<Integer> id,RedirectAttributes attributes) {
        //Si l'id est null c'est que nous affichons la page d'accueil depuis la page de connexion
            List<Sondages> sondages = new ArrayList<Sondages>();
            if(!id.isPresent()){
                Users userModel = (Users)model.getAttribute("user");
                sondages = sondagesRepository.AllSpecial(userModel.getId());
                model.addAttribute("user",userModel);
            }
            //Si l'id n'est pas null c'est que nous somme déja connecté
            else
            {
                if(TokenExist(id.get())){
                    Users user = usersRepository.findById(id.get()).get();
                    sondages = sondagesRepository.AllSpecial(id.get());
                    model.addAttribute("user",user);
                }
                else{
                    attributes.addFlashAttribute("message","Veuillez vous connecter ");
                    attributes.addFlashAttribute("alertClass", "alert-danger");
                    return "redirect:/user/index";
                }

            }
            //On récupère la liste des sondages
            model.addAttribute("sondages",sondages);
            return "Page_accueil";

    }
    //Action qui affiche la page de gestion des sondages
    @GetMapping("/gestion/{id}")
    public String showGestionSondages(@PathVariable int id,Model model,RedirectAttributes attributes)
    {
        if(TokenExist(id)){
            //Rêquete qui récupère l'utilisateur et tous ses sondages pour les afficher dans la page de gestion de sondage
            Users user = usersRepository.findById(id).get();
            String messageModel = (String) model.getAttribute("message");
            model.addAttribute("message",messageModel);
            model.addAttribute("alertClass", "alert-success");
            model.addAttribute("user",user);
            return "Page_gestionSondages";
        }
        else {
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }


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
        if (TokenExist(idUser)){
            sondagesRepository.deleteById(id);
            attributes.addFlashAttribute("message","Le sondage à bien été supprimé");
            attributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/sondage/gestion/"+idUser;
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }


    }
    //Action pour afficher la page de modification d'un sondage
    @GetMapping("/showPageUpdate/{id}/{idUser}")
    public String showUpdateSondage(@PathVariable int id,@PathVariable int idUser, Model model,RedirectAttributes attributes)
    {
        if(TokenExist(idUser)){
            Sondages sondage = sondagesRepository.findById(id).get();
            model.addAttribute("sondage", sondage);
            model.addAttribute("idUser", idUser);
            return "Page_gestionSondagesUpdate";
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }

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

    public Boolean TokenExist(Integer idUser){
        Token token = tokensRepository.GetToken(idUser);
        if(token == null){

            return false;
        }
        else{
            return true;
        }
    }


}

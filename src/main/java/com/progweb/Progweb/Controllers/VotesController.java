package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Models.Votes;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import com.progweb.Progweb.Repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path="/vote")
public class VotesController {

    @Autowired
    private SondagesRepository sondagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private VotesRepository votesRepository;

    @GetMapping("/showGestionVote/{id}")
    public String showGestionVote(@PathVariable int id, Model model)
    {
        //Récupère les données utilisateur en base pour les afficher dans les input à l'affichage de la page
        Users user  = usersRepository.findById(id).get();
        List<Sondages> sondages = sondagesRepository.AllSondageVoter(id);
        model.addAttribute("user", user);
        model.addAttribute("sondages",sondages);
        return "Page_gestionVotes";
    }

    @GetMapping("/addVote/{id}/{idUser}")
    public String addVote(@PathVariable int id, @PathVariable int idUser, RedirectAttributes attributes){

        Votes vote = new Votes(idUser,id,true);
        votesRepository.save(vote);
        attributes.addFlashAttribute("message","Le vote à bien été pris en compte");
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/sondage/accueil/"+idUser;
    }

    @GetMapping("/cancelVote/{id}/{idUser}")
    public String cancelVote(@PathVariable int id, @PathVariable int idUser, RedirectAttributes attributes){
        votesRepository.DeleteVote(id,idUser);
        //votesRepository.DeleteVote(id);
        attributes.addFlashAttribute("alertClass" ,"alert-success");
        attributes.addFlashAttribute("message","Le vote à bien été supprimé");
        return "redirect:/vote/showGestionVote/"+idUser;
    }



}

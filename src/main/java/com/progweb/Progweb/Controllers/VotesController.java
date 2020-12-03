package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import com.progweb.Progweb.Repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("user", user);
        return "Page_gestionVotes";
    }



}

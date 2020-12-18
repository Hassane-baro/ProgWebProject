package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Token;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Models.Votes;
import com.progweb.Progweb.Repository.SondagesRepository;
import com.progweb.Progweb.Repository.TokensRepository;
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

    @Autowired
    private TokensRepository tokensRepository;

    @GetMapping("/showGestionVote/{id}")
    public String showGestionVote(@PathVariable int id, Model model,RedirectAttributes attributes)
    {
        if(TokenExist(id)){
            //Récupère les données utilisateur en base pour les afficher dans les input à l'affichage de la page
            Users user  = usersRepository.findById(id).get();
            List<Sondages> sondages = sondagesRepository.AllSondageVoter(id);
            model.addAttribute("user", user);
            model.addAttribute("sondages",sondages);
            return "Page_gestionVotes";
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }


    }

    @GetMapping("/addVote/{id}/{idUser}")
    public String addVote(@PathVariable int id, @PathVariable int idUser, RedirectAttributes attributes){
        if(TokenExist(idUser)){
            Votes vote = new Votes(idUser,id,true);
            votesRepository.save(vote);
            attributes.addFlashAttribute("message","Le vote à bien été pris en compte");
            attributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/sondage/accueil/"+idUser;
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }

    }

    @GetMapping("/cancelVote/{id}/{idUser}")
    public String cancelVote(@PathVariable int id, @PathVariable int idUser, RedirectAttributes attributes){
        if(TokenExist(idUser)){
            votesRepository.DeleteVote(id,idUser);
            attributes.addFlashAttribute("alertClass" ,"alert-success");
            attributes.addFlashAttribute("message","Le vote à bien été supprimé");
            return "redirect:/vote/showGestionVote/"+idUser;
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }

    }

    public Boolean TokenExist(Integer idUser){
        List<Token> tokenList = tokensRepository.userTokens(idUser);

        if(tokenList.isEmpty()){

            return false;
        }
        else{
            return true;
        }
    }



}

package com.progweb.Progweb.Controllers;
import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Token;
import com.progweb.Progweb.Models.TokenGen;
import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.TokensRepository;
import com.progweb.Progweb.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//Controller qui ne concerne que les actions sur l'utilisateur
@Controller
@RequestMapping(path="/user")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TokensRepository tokensRepository;

    //Affichage de la page de connexion
    @GetMapping("/index")
    public String index () {
        return "index";
    }

    //Affichage de la page d'inscription
    @GetMapping("/showPage_inscription")
    public String ShowPage_inscription(){
        return "Page_inscription";
    }

    //Action qui gère la connexion
    @PostMapping("/connexion")
    public String Connexion(Users user, Model model, RedirectAttributes attributes, HttpServletResponse response){
       Users u = usersRepository.findByEmail(user.getEmail());
       BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
       //Vérifie si il existe en base avec le retour de l'objet
       if(u != null){
           // Vérifie si le mot de passe est similaire à celui en base si oui on se connecte
           if(bcrypt.matches(user.getPassword(),u.getPassword())){
               TokenGen tokenGen = new TokenGen();
               String tokenString = tokenGen.CreateToken();
               Token token = new Token(tokenString,u.getId());
               tokensRepository.save(token);
               attributes.addFlashAttribute("user", u);
               return "redirect:/sondage/accueil";
           }
           else{
               //Renvoi un message d'erreur si le mot de passe est incorrect
               model.addAttribute("message", "Identifiant ou mot de passe incorrect");
               model.addAttribute("alertClass", "alert-danger");
               return "index";
           }

       }
       else{
           //Renvoi un message d'erreur car il n'existe pas en base
           model.addAttribute("message", "Identifiant ou mot de passe incorrect");
           model.addAttribute("alertClass", "alert-danger");
           return "index";
       }

    }

    //Action pour ajouter un utilisateur en base (Inscription)
    @PostMapping("/add") // Map ONLY POST Requests
    public String addNewUser (Users user, Model model) {

        //Vérifie si un email du même nom n'existe pas déjà
        if(usersRepository.existsByEmail(user.getEmail())){
            //Si oui on envoi un message d'erreur
            model.addAttribute("message", "Email existant ");
            model.addAttribute("alertClass", "alert-danger");
            return "Page_inscription";
        }
        //Sinon on hash le mot de passe et on créer l'utilisateur
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hash = passwordEncoder.encode(user.getPassword());
        Users n = new Users(user.getNom(),user.getPrenom(),user.getEmail(),hash,user.getAdresse(),user.getDateNaiss(),user.getNumMobile());
        usersRepository.save(n);
        return "redirect:/user/index";
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    //Action qui affiche la page de modification du profil utilisateur
    @GetMapping("/showUserUpdate/{id}")
    public String showUserUpdate(@PathVariable int id, Model model, RedirectAttributes attributes)
    {
        if(TokenExist(id)){
            Users user  = usersRepository.findById(id).get();
            model.addAttribute("user", user);
            return "Page_userUpdate";
        }
        else{
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }
        //Récupère les données utilisateur en base pour les afficher dans les input à l'affichage de la page
    }

    //Action qui modifie les données utilisateur dans la page de modification du profil
    @PostMapping(path="/update") // Map ONLY POST Requests
    public String updateUser (Users user, RedirectAttributes attributes,Model model) {
        if(TokenExist(user.getId())){
            Users n = usersRepository.findById(user.getId()).get();
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            String hash = bcrypt.encode(user.getPassword());
            // Je vérifie si l'utilisateur a laisser le même email en saisie
            if(user.getEmail().equals(n.getEmail())){
                //Si oui je n'update pas l'email
            }
            else {
                // Si l'utilisateur a saisie un nouvel email je vérifie s'il n'existe pas un compte avec le même email
                if(usersRepository.existsByEmail(user.getEmail())){
                    //Je renvoi une erreur si un email du même nom existe en base
                    model.addAttribute("message","Cette email existe déjà ");
                    model.addAttribute("alertClass", "alert-danger");
                    model.addAttribute("user",n);
                    return "Page_userUpdate";

                }
                else{
                    //Si le nouvel email n'existe pas je peux effectué la modification avec l'ancien email
                    n.setEmail(user.getEmail());
                }
            }
            // Je vérifie si le nouveau mot de passe est le même que l'ancien si oui je renvoi une erreur
            if(bcrypt.matches(user.getPassword(),n.getPassword())){
                model.addAttribute("message", "Mot de passe indentique à l'ancien ");
                model.addAttribute("alertClass", "alert-danger");
                model.addAttribute("user",n);
                return "Page_userUpdate";
            }
            else{
                //Sinon j'effectue toute les modification sur tous les champs modifié
                n.setPrenom(user.getPrenom());
                n.setNom(user.getNom());
                n.setDateNaiss(user.getDateNaiss());
                n.setAdresse(user.getAdresse());
                n.setNumMobile(user.getNumMobile());
                n.setPassword(hash);
                usersRepository.save(n);
                attributes.addFlashAttribute("user",user);
                return "redirect:/sondage/accueil";
            }
        }
        else {
            attributes.addFlashAttribute("message","Veuillez vous connecter ");
            attributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/user/index";
        }

    }

    @GetMapping("/deconnexion/{id}")
    public String deconnexion(@PathVariable Integer id){
        tokensRepository.DeleteToken(id);
        return "redirect:/user/index";
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


    @GetMapping(path="/delete/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteUser (@PathVariable int id) {

        usersRepository.deleteById(id);
        return "Delete";
    }

    



}

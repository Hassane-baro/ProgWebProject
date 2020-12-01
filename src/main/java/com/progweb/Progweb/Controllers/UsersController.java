package com.progweb.Progweb.Controllers;
import com.progweb.Progweb.Models.Sondages;
import com.progweb.Progweb.Models.Users;
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

import java.util.Map;

@Controller
@RequestMapping(path="/user")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/index")
    public String index () {
        return "index";
    }

    @GetMapping("/showPage_inscription")
    public String ShowPage_inscription(@ModelAttribute Users user, Model model){
        model.addAttribute("user",user);
        return "Page_inscription";
    }

    @PostMapping("/connexion")
    public String Connexion(Users user, Model model, RedirectAttributes attributes){
       Users u = usersRepository.findByEmail(user.getEmail());
       BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
       if(u != null){
           if(bcrypt.matches(user.getPassword(),u.getPassword())){
               attributes.addFlashAttribute("user", u);
               return "redirect:/sondage/accueil";
           }
           else{
               model.addAttribute("message", "Identifiant ou mot de passe incorrect");
               model.addAttribute("alertClass", "alert-danger");
               return "index";
           }

       }
       else{
           model.addAttribute("message", "Identifiant ou mot de passe incorrect");
           model.addAttribute("alertClass", "alert-danger");
           return "index";
       }

    }

    @PostMapping("/add") // Map ONLY POST Requests
    public String addNewUser (Users user, Model model) {

        if(usersRepository.existsByEmail(user.getEmail())){

            model.addAttribute("message", "Email existant ");
            model.addAttribute("alertClass", "alert-danger");
            return "Page_inscription";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hash = passwordEncoder.encode(user.getPassword());
        Users n = new Users(user.getNom(),user.getPrenom(),user.getEmail(),hash,user.getAdresse(),user.getDateNaiss(),user.getNumMobile());
        usersRepository.save(n);
        return "redirect:/user/index";
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        // This returns a JSON or XML with the users
        return usersRepository.findAll();
    }

    @GetMapping("/showUserUpdate/{id}")
    public String showUserUpdate(@PathVariable int id, Model model)
    {
        Users user  = usersRepository.findById(id).get();
        model.addAttribute("user", user);
        return "Page_userUpdate";

    }


    @PostMapping(path="/update") // Map ONLY POST Requests
    public String updateUser (Users user, RedirectAttributes attributes,Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Users n = usersRepository.findById(user.getId()).get();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hash = bcrypt.encode(user.getPassword());
        if(user.getEmail().equals(n.getEmail())){

        }
        else {

            if(usersRepository.existsByEmail(user.getEmail())){
                model.addAttribute("message","Cette email existe déjà ");
                model.addAttribute("alertClass", "alert-danger");
                model.addAttribute("user",n);
                return "Page_userUpdate";

            }
            else{
                n.setEmail(user.getEmail());
            }
        }

        if(bcrypt.matches(user.getPassword(),n.getPassword())){
            model.addAttribute("message", "Mot de passe indentique à l'ancien ");
            model.addAttribute("alertClass", "alert-danger");
            model.addAttribute("user",n);
            return "Page_userUpdate";
        }
        else{

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

    @GetMapping(path="/delete/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteUser (@PathVariable int id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        usersRepository.deleteById(id);
        return "Delete";
    }



}

package com.progweb.Progweb.Controllers;

import com.progweb.Progweb.Models.Users;
import com.progweb.Progweb.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/user")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/index")
    public String index () {
        return "index";
    }

    @GetMapping("/Page_inscription")
    public String Page_inscription(@ModelAttribute Users user, Model model){
        model.addAttribute("user",user);
        return "Page_inscription";
    }

    @PostMapping("/add") // Map ONLY POST Requests
    public String addNewUser (Users user) {

        Users n = new Users(user.getNom(),user.getPrenom(),user.getEmail(),user.getPassword(),user.getAdresse(),user.getDateNaiss(),user.getNumMobile());
        usersRepository.save(n);
        return "redirect:/user/index";
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        // This returns a JSON or XML with the users
        return usersRepository.findAll();
    }

    @GetMapping(path="/update/{id}") // Map ONLY POST Requests
    public @ResponseBody String updateUser (@PathVariable int id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Users n = usersRepository.findById(id).get();
        n.setPrenom("Samba");
        n.setNom("Aladj");
        usersRepository.save(n);
        return "Update";
    }

    @GetMapping(path="/delete/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteUser (@PathVariable int id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        usersRepository.deleteById(id);
        return "Delete";
    }



}

package com.example.salary_accountment.controllers;

import com.example.salary_accountment.models.*;
import com.example.salary_accountment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

public class AdminController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private TimeSheetRepository timeSheetRepository;
    @Autowired
    private DateRepository dateRepository;

    @GetMapping("/admin/{id}")
    public String admin(@PathVariable(value = "id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "adminMain";
    }
    @PostMapping("/admin/{id}")
    public String changeData(@PathVariable(value = "id") Integer id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String patronymic, @RequestParam String mail, @RequestParam String phone, @RequestParam String birthday, @RequestParam String startDate, @RequestParam String post,@RequestParam String login, @RequestParam String password, @RequestParam String newPassword, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        if(Objects.equals(password, "") || Objects.equals(newPassword, "")){
            User user1 = new User(id,firstname,lastname,patronymic,mail,phone,birthday,startDate);
            userRepository.save(user1);
        }else{
            if(user.get().getAuthorizationData().getPassword().equals(password)){
                User user1 = new User(id,firstname,lastname,patronymic,mail,phone,birthday,startDate);
                userRepository.save(user1);
            }
        }
        return "redirect:/admin/{id}";
    }
}

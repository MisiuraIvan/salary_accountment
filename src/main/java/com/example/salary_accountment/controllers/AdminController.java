package com.example.salary_accountment.controllers;

import com.example.salary_accountment.models.*;
import com.example.salary_accountment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Controller
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
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ParametersRepository parametersRepository;
    @Autowired
    private AuthorizationRepository authorizationDataRepository;

    public static int uid;

    @GetMapping("/admin/{id}")
    public String admin(@PathVariable(value = "id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        uid=user.get().getUserid();
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "admin";
    }
    @PostMapping("/admin/{id}")
    public String changeData(@PathVariable(value = "id") Integer id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String patronymic, @RequestParam String mail, @RequestParam String phone, @RequestParam String birthday, @RequestParam String startDate, @RequestParam String post,@RequestParam String login, @RequestParam String password, Model model) {
        Optional<User> user = userRepository.findById(id);
        Optional<Post> post1 = postRepository.findByName(post);
        model.addAttribute("user", user.get());
        User user1 = new User(id,firstname,lastname,patronymic,mail,phone,birthday,startDate,post1.get(),user.get().getAuthorizationData());
        userRepository.save(user1);
        return "redirect:/admin/{id}";
    }
    @GetMapping("/admin/employees")
    public String employees(Model model) {
        Iterable<User> employees = userRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("id", uid);
        return "employees";
    }
    @GetMapping("/admin/posts")
    public String posts(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("id", uid);
        return "posts";
    }
    @GetMapping("/admin/timesheets")
    public String timesheets(Model model) {
        Iterable<TimeSheet> timeSheets = timeSheetRepository.findAll();
        model.addAttribute("timeSheets", timeSheets);
        model.addAttribute("id", uid);
        return "timesheeets";
    }
    @GetMapping("/admin/salaries")
    public String salaries(Model model) {
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        model.addAttribute("id", uid);
        return "salaries";
    }
    @GetMapping("/admin/activity")
    public String activity(Model model) {
        Iterable<Activity> activity = activityRepository.findAll();
        model.addAttribute("activity", activity);
        model.addAttribute("id", uid);
        return "activity";
    }
    @GetMapping("/admin/dates")
    public String dates(Model model) {
        Iterable<Date> dates = dateRepository.findAll();
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "dates";
    }
}

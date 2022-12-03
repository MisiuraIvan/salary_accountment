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
        User user1 = new User(id,lastname,firstname,patronymic,mail,phone,birthday,startDate,post1.get(),user.get().getAuthorizationData());
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
    @GetMapping("/admin/employees/details/{id}")
    public String empDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "empDetails";
    }
    @GetMapping("/admin/employees/add")
    public String employeesAdd(Model model) {
        model.addAttribute("id", uid);
        return "employeesAdd";
    }
    @PostMapping(path="/admin/employees",params ="operation=Find")
    public String employeesFind(Model model, @RequestParam String lastName,@RequestParam String post) {
        Iterable<User> employees=null;
        if (!lastName.equals("") && !post.equals("")) {
            employees = userRepository.findByLastNameAndPost(lastName,post);
        }else{
            if(!lastName.equals("")){
                employees = userRepository.findByLastName(lastName);
            }
            else{
                if(!post.equals("")){
                    employees = userRepository.findByPost(post);
                }
                else return "redirect:/admin/employees";
            }
        }
        model.addAttribute("employees", employees);
        model.addAttribute("id", uid);
        return "employees";
    }
    @PostMapping("/admin/employees/add")
    public String employeeAdd(Model model,@RequestParam String firstname, @RequestParam String lastname, @RequestParam String patronymic, @RequestParam String mail, @RequestParam String phone, @RequestParam String birthday, @RequestParam String startDate, @RequestParam String post,@RequestParam String login, @RequestParam String password) {
        int id=userRepository.findTheBiggestId();
        Optional<Post> post1 = postRepository.findByName(post);
        int aid=authorizationDataRepository.findTheBiggestSort();
        AuthorizationData authorizationData=new AuthorizationData(aid+1,login, password);
        authorizationDataRepository.save(authorizationData);
        User user = new User(id+1,firstname,lastname,patronymic,mail,phone,birthday,startDate,post1.get(),authorizationData);
        userRepository.save(user);
        return "redirect:/admin/employees";
    }
    @GetMapping("/admin/posts")
    public String posts(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("id", uid);
        return "posts";
    }
    @PostMapping(path="/admin/employees/details/{id}",params = "operation=Edit")
    public String employeesEdit(@PathVariable(value = "id") Integer id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String patronymic, @RequestParam String mail, @RequestParam String phone, @RequestParam String birthday, @RequestParam String startDate, @RequestParam String post,@RequestParam String login, @RequestParam String password, Model model) {
        Optional<User> user = userRepository.findById(id);
        Optional<Post> post1 = postRepository.findByName(post);
        User user1 = new User(id,lastname,firstname,patronymic,mail,phone,birthday,startDate,post1.get(),user.get().getAuthorizationData());
        userRepository.save(user1);
        return "redirect:/admin/employees/details/{id}";
    }
    @PostMapping(path="/admin/employees/details/{id}",params = "operation=Delete")
    public String employeesDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
        return "redirect:/admin/employees";
    }
    @PostMapping(path="/admin/posts",params ="operation=Find")
    public String postsFind(Model model, @RequestParam String post) {
        Optional<Post> posts = postRepository.findByName(post);
        model.addAttribute("posts", posts.get());
        model.addAttribute("id", uid);
        return "posts";
    }
    @GetMapping("/admin/posts/details/{id}")
    public String postsDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());
        model.addAttribute("id", uid);
        return "postsDetails";
    }
    @GetMapping("/admin/posts/add")
    public String postsAdd(Model model) {
        model.addAttribute("id", uid);
        return "postAdd";
    }
    @PostMapping("/admin/posts/add")
    public String postAdd(Model model,@RequestParam String post,@RequestParam int wages) {
        int id = postRepository.findTheBiggestId();
        Post p = new Post(id+1,post,wages);
        postRepository.save(p);
        return "redirect:/admin/posts";
    }
    @PostMapping(path="/admin/posts/details/{id}",params = "operation=Edit")
    public String postEdit(@PathVariable(value = "id") Integer id,@RequestParam String post,@RequestParam int wages, Model model) {
        Post p = new Post(id,post,wages);
        postRepository.save(p);
        return "redirect:/admin/posts/details/{id}";
    }
    @PostMapping(path="/admin/posts/details/{id}",params = "operation=Delete")
    public String postDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Post> post1 = postRepository.findById(id);
        postRepository.delete(post1.get());
        return "redirect:/admin/posts";
    }
    @GetMapping("/admin/timesheets")
    public String timesheets(Model model) {
        Iterable<TimeSheet> timeSheets = timeSheetRepository.findAll();
        model.addAttribute("timeSheets", timeSheets);
        model.addAttribute("id", uid);
        return "timesheets";
    }
    @GetMapping("/admin/timesheets/details/{id}")
    public String timesheetsDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<TimeSheet> timeSheet = timeSheetRepository.findById(id);
        model.addAttribute("timeSheet", timeSheet.get());
        model.addAttribute("id", uid);
        return "timesheetsDetails";
    }
    @GetMapping("/admin/salaries")
    public String salaries(Model model) {
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        model.addAttribute("id", uid);
        return "salaries";
    }
    @GetMapping("/admin/salaries/details/{id}")
    public String salariesDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Salary> salary = salaryRepository.findById(id);
        model.addAttribute("salary", salary.get());
        model.addAttribute("id", uid);
        return "salariesDetails";
    }
    @GetMapping("/admin/activity")
    public String activity(Model model) {
        Iterable<Activity> activity = activityRepository.findAll();
        model.addAttribute("activity", activity);
        model.addAttribute("id", uid);
        return "activity";
    }
    @GetMapping("/admin/activity/details/{id}")
    public String activityDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Activity> activity = activityRepository.findById(id);
        model.addAttribute("activity", activity.get());
        model.addAttribute("id", uid);
        return "activityDetails";
    }
    @PostMapping(path="/admin/activity",params ="operation=Find")
    public String activityFind(Model model,@RequestParam String lastName, @RequestParam String month,@RequestParam int year) {
        Iterable<Activity> activity=null;
        if (!month.equals("") && year!=0 && !lastName.equals("") ) {
            activity = activityRepository.findByLastNameAndMonthAndYear(lastName,month,year);
        }else{
            if(!month.equals("") && year!=0){
                activity = activityRepository.findByMonthAndYear(month,year);
            }
            else {
                if (!lastName.equals("") && year != 0) {
                    activity = activityRepository.findByLastNameAndYear(lastName, year);
                } else {
                    if (!month.equals("") && !lastName.equals("")) {
                        activity = activityRepository.findByLastNameAndMonth(lastName, month);
                    } else {
                            if(!month.equals("")){
                                activity = activityRepository.findByMonth(month);
                            }
                            else{
                                if (year!=0){
                                    activity = activityRepository.findByYear(year);
                                }
                                else{
                                    if(!lastName.equals("")){
                                        activity = activityRepository.findByLastName(lastName);
                                    }
                                    else
                                        return "redirect:/admin/dates";
                                }
                            }
                    }
                }
            }
        }
        model.addAttribute("activity", activity);
        model.addAttribute("id", uid);
        return "activity";
    }
    @GetMapping("/admin/activity/add/{id}")
    public String activityAddShow(@PathVariable(value = "id") Integer id,Model model) {
        Optional user=userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "activityAdd";
    }
    @PostMapping("/admin/activity/add/{id}")
    public String activityAdd(@PathVariable(value="id") Integer id,@RequestParam String month,@RequestParam int year, @RequestParam int badHabits, @RequestParam int bonus,@RequestParam int culturalEvents,@RequestParam int delay,@RequestParam int respect,@RequestParam int timeliness,@RequestParam int uniform, Model model) {
        int aid=activityRepository.findTheBiggestId();
        Optional<User> user=userRepository.findById(id);
        Iterable<Date> d=dateRepository.findByMonthAndYear(month, year);
        Activity activity = new Activity(aid+1,badHabits,bonus,culturalEvents,delay,respect,timeliness,uniform,d.iterator().next(),user.get());
        activityRepository.save(activity);
        return "redirect:/admin/activity";
    }
    @PostMapping(path="/admin/activity/details/{id}",params = "operation=Edit")
    public String dateEdit(@PathVariable(value = "id") Integer id,@RequestParam String emp,@RequestParam String date, @RequestParam int badHabits, @RequestParam int bonus,@RequestParam int culturalEvents,@RequestParam int delay,@RequestParam int respect,@RequestParam int timeliness,@RequestParam int uniform, Model model) {
        Optional<User> user=userRepository.findByNameAndLastName(emp.split(" ")[0],emp.split(" ")[1]);
        Iterable<Date> d=dateRepository.findByMonthAndYear(date.split(" ")[0], Integer.parseInt(date.split(" ")[1]));
        Activity activity = new Activity(id,badHabits,bonus,culturalEvents,delay,respect,timeliness,uniform,d.iterator().next(),user.get());
        activityRepository.save(activity);
        return "redirect:/admin/activity/details/{id}";
    }
    @PostMapping(path="/admin/activity/details/{id}",params = "operation=Delete")
    public String activityDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Activity> activity = activityRepository.findById(id);
        activityRepository.delete(activity.get());
        return "redirect:/admin/activity";
    }

    @GetMapping("/admin/dates")
    public String dates(Model model) {
        Iterable<Date> dates = dateRepository.findAll();
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "dates";
    }
    @PostMapping(path="/admin/dates",params ="operation=Find")
    public String datesFind(Model model, @RequestParam String month,@RequestParam int year) {
        Iterable<Date> dates=null;
        if (!month.equals("") && year!=0) {
            dates = dateRepository.findByMonthAndYear(month,year);
        }else{
            if(!month.equals("")){
                dates = dateRepository.findByMonth(month);
            }
            else{
                if(year!=0){
                    dates = dateRepository.findByYear(year);
                }
                else return "redirect:/admin/dates";
            }
        }
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "dates";
    }
    @PostMapping("/admin/dates/add")
    public String datesAdd(Model model,@RequestParam String month, @RequestParam int year, @RequestParam int workHours) {
        int id=dateRepository.findTheBiggestId();
        Date date = new Date(id+1,month,year,workHours);
        dateRepository.save(date);
        return "redirect:/admin/dates";
    }
    @PostMapping(path="/admin/dates/details/{id}",params = "operation=Edit")
    public String dateEdit(@PathVariable(value = "id") Integer id,@RequestParam String month,@RequestParam int year, @RequestParam int workHours, Model model) {
        Date d = new Date(id,month,year,workHours);
        dateRepository.save(d);
        return "redirect:/admin/dates/details/{id}";
    }
    @PostMapping(path="/admin/dates/details/{id}",params = "operation=Delete")
    public String dateDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Date> date = dateRepository.findById(id);
        dateRepository.delete(date.get());
        return "redirect:/admin/dates";
    }
    @GetMapping("/admin/dates/details/{id}")
    public String datesDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Date> date = dateRepository.findById(id);
        model.addAttribute("date", date.get());
        model.addAttribute("id", uid);
        return "datesDetails";
    }

    @GetMapping("/admin/posts/find")
    public String postsFind(@RequestParam String post, Model model) {
        Optional<Post> posts = postRepository.findByName(post);
        model.addAttribute("posts", posts);
        model.addAttribute("id", uid);
        return "redirect:/admin/posts";
    }
}

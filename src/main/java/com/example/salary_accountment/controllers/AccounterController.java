package com.example.salary_accountment.controllers;

import com.example.salary_accountment.models.*;
import com.example.salary_accountment.repository.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AccounterController {
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

    @GetMapping("/accounter/{id}")
    public String accounter(@PathVariable(value = "id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        uid=user.get().getUserid();
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "accounter";
    }
    @PostMapping("/accounter/{id}")
    public String changeData(@PathVariable(value = "id") Integer id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String patronymic, @RequestParam String mail, @RequestParam String phone, @RequestParam String birthday, @RequestParam String startDate, @RequestParam String post, @RequestParam String login, @RequestParam String password, Model model) {
        Optional<User> user = userRepository.findById(id);
        Optional<Post> post1 = postRepository.findByName(post);
        model.addAttribute("user", user.get());
        User user1 = new User(id,lastname,firstname,patronymic,mail,phone,birthday,startDate,post1.get(),user.get().getAuthorizationData());
        userRepository.save(user1);
        return "redirect:/accounter/{id}";
    }
    @GetMapping("/accounter/employees")
    public String employees(Model model) {
        Iterable<User> employees = userRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("id", uid);
        return "acemployees";
    }
    @PostMapping(path="/accounter/employees",params ="operation=Find")
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
                else return "redirect:/accounter/employees";
            }
        }
        model.addAttribute("employees", employees);
        model.addAttribute("id", uid);
        return "acemployees";
    }
    @GetMapping("/accounter/posts")
    public String posts(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("id", uid);
        return "acposts";
    }
    @PostMapping(path="/accounter/posts",params ="operation=Find")
    public String postsFind(Model model, @RequestParam String post) {
        Optional<Post> posts = postRepository.findByName(post);
        model.addAttribute("posts", posts.get());
        model.addAttribute("id", uid);
        return "acposts";
    }
    @GetMapping("/accounter/timesheets")
    public String timesheets(Model model) {
        Iterable<TimeSheet> timeSheets = timeSheetRepository.findAll();
        model.addAttribute("timeSheets", timeSheets);
        model.addAttribute("id", uid);
        return "actimesheets";
    }
    @GetMapping("/accounter/timesheets/details/{id}")
    public String timesheetsDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<TimeSheet> timeSheet = timeSheetRepository.findById(id);
        model.addAttribute("timeSheet", timeSheet.get());
        model.addAttribute("id", uid);
        return "actimesheetsDetails";
    }
    @GetMapping("/accounter/timesheets/add/{id}")
    public String timesheetsAddShow(@PathVariable(value = "id") Integer id,Model model) {
        Optional user=userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "actimesheetsAdd";
    }
    @PostMapping("/accounter/timesheets/add/{id}")
    public String timesheetsAdd(@PathVariable(value="id") Integer id,@RequestParam String month,@RequestParam int year, @RequestParam int absenteeism, @RequestParam int holiday,@RequestParam int overtime,@RequestParam int sickLeave,@RequestParam int workTime, Model model) {
        int tid=timeSheetRepository.findTheBiggestId();
        Optional<User> user=userRepository.findById(id);
        Optional<Date> d=dateRepository.findByMonthAndYear(month, year);
        TimeSheet timeSheet = new TimeSheet(tid+1,absenteeism,holiday,overtime,sickLeave,workTime,d.get(),user.get());
        timeSheetRepository.save(timeSheet);
        return "redirect:/accounter/timesheets";
    }
    @PostMapping(path="/accounter/timesheets/details/{id}",params = "operation=Edit")
    public String timeSheetEdit(@PathVariable(value = "id") Integer id,@RequestParam String user,@RequestParam String date, @RequestParam int workTime, @RequestParam int holiday,@RequestParam int sickLeave,@RequestParam int overtime,@RequestParam int absenteeism, Model model) {
        Optional<User> u=userRepository.findByNameAndLastName(user.split(" ")[0],user.split(" ")[1]);
        Optional<Date> d=dateRepository.findByMonthAndYear(date.split(" ")[0], Integer.parseInt(date.split(" ")[1]));
        TimeSheet timeSheet = new TimeSheet(id,absenteeism,holiday,overtime,sickLeave,workTime,d.get(),u.get());
        timeSheetRepository.save(timeSheet);
        return "redirect:/accounter/timesheets/details/{id}";
    }
    @PostMapping(path="/accounter/timesheets/details/{id}",params = "operation=Delete")
    public String timeSheetsDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<TimeSheet> timeSheet = timeSheetRepository.findById(id);
        timeSheetRepository.delete(timeSheet.get());
        return "redirect:/accounter/timesheets";
    }
    @PostMapping(path="/accounter/timesheets",params ="operation=Find")
    public String timeSheetFind(Model model,@RequestParam String lastName, @RequestParam String month,@RequestParam int year) {
        Iterable<TimeSheet> timeSheets=null;
        if (!month.equals("") && year!=0 && !lastName.equals("") ) {
            timeSheets = timeSheetRepository.findByLastNameAndMonthAndYear(lastName,month,year);
        }else{
            if(!month.equals("") && year!=0){
                timeSheets = timeSheetRepository.findByMonthAndYear(month,year);
            }
            else {
                if (!lastName.equals("") && year != 0) {
                    timeSheets = timeSheetRepository.findByLastNameAndYear(lastName, year);
                } else {
                    if (!month.equals("") && !lastName.equals("")) {
                        timeSheets = timeSheetRepository.findByLastNameAndMonth(lastName, month);
                    } else {
                        if(!month.equals("")){
                            timeSheets = timeSheetRepository.findByMonth(month);
                        }
                        else{
                            if (year!=0){
                                timeSheets = timeSheetRepository.findByYear(year);
                            }
                            else{
                                if(!lastName.equals("")){
                                    timeSheets = timeSheetRepository.findByLastName(lastName);
                                }
                                else
                                    return "redirect:/accounter/dates";
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("timeSheets", timeSheets);
        model.addAttribute("id", uid);
        return "actimesheets";
    }
    @GetMapping("/accounter/salaries")
    public String salaries(Model model) {
        Iterable<Salary> salaries = salaryRepository.findAll();
        model.addAttribute("salaries", salaries);
        model.addAttribute("id", uid);
        return "acsalaries";
    }
    @GetMapping("/accounter/salaries/details/{id}")
    public String salariesDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Salary> salary = salaryRepository.findById(id);
        model.addAttribute("salary", salary.get());
        model.addAttribute("id", uid);
        return "acsalariesDetails";
    }
    @PostMapping(path="/accounter/salaries/details/{id}",params = "operation=Delete")
    public String salariesDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Salary> salary = salaryRepository.findById(id);
        salaryRepository.delete(salary.get());
        Optional<Parameters> parameters = parametersRepository.findById(id);
        parametersRepository.delete(parameters.get());
        return "redirect:/admin/salaries";
    }
    @PostMapping(path="/accounter/salaries",params ="operation=Find")
    public String salaryFind(Model model,@RequestParam String lastName, @RequestParam String month,@RequestParam int year) {
        Iterable<Salary> salaries=null;
        if (!month.equals("") && year!=0 && !lastName.equals("") ) {
            salaries = salaryRepository.findByLastNameAndMonthAndYear(lastName,month,year);
        }else{
            if(!month.equals("") && year!=0){
                salaries = salaryRepository.findByMonthAndYear(month,year);
            }
            else {
                if (!lastName.equals("") && year != 0) {
                    salaries = salaryRepository.findByLastNameAndYear(lastName, year);
                } else {
                    if (!month.equals("") && !lastName.equals("")) {
                        salaries = salaryRepository.findByLastNameAndMonth(lastName, month);
                    } else {
                        if(!month.equals("")){
                            salaries = salaryRepository.findByMonth(month);
                        }
                        else{
                            if (year!=0){
                                salaries = salaryRepository.findByYear(year);
                            }
                            else{
                                if(!lastName.equals("")){
                                    salaries = salaryRepository.findByLastName(lastName);
                                }
                                else
                                    return "redirect:/accounter/salaries";
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("salaries", salaries);
        model.addAttribute("id", uid);
        return "acsalaries";
    }
    @GetMapping("/accounter/activity")
    public String activity(Model model) {
        Iterable<Activity> activity = activityRepository.findAll();
        model.addAttribute("activity", activity);
        model.addAttribute("id", uid);
        return "acactivity";
    }
    @GetMapping("/accounter/activity/details/{id}")
    public String activityDetails(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Activity> activity = activityRepository.findById(id);
        model.addAttribute("activity", activity.get());
        model.addAttribute("id", uid);
        return "acactivityDetails";
    }
    @PostMapping(path="/accounter/activity",params ="operation=Find")
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
                                    return "redirect:/accounter/dates";
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("activity", activity);
        model.addAttribute("id", uid);
        return "acactivity";
    }
    @GetMapping("/accounter/activity/add/{id}")
    public String activityAddShow(@PathVariable(value = "id") Integer id,Model model) {
        Optional user=userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("id", uid);
        return "acactivityAdd";
    }
    @PostMapping("/accounter/activity/add/{id}")
    public String activityAdd(@PathVariable(value="id") Integer id,@RequestParam String month,@RequestParam int year, @RequestParam int badHabits, @RequestParam int bonus,@RequestParam int culturalEvents,@RequestParam int delay,@RequestParam int respect,@RequestParam int timeliness,@RequestParam int uniform, Model model) {
        int aid=activityRepository.findTheBiggestId();
        Optional<User> user=userRepository.findById(id);
        Optional<Date> d=dateRepository.findByMonthAndYear(month, year);
        Activity activity = new Activity(aid+1,badHabits,bonus,culturalEvents,delay,respect,timeliness,uniform,d.get(),user.get());
        activityRepository.save(activity);
        return "redirect:/accounter/activity";
    }
    @PostMapping(path="/accounter/activity/details/{id}",params = "operation=Edit")
    public String dateEdit(@PathVariable(value = "id") Integer id,@RequestParam String emp,@RequestParam String date, @RequestParam int badHabits, @RequestParam int bonus,@RequestParam int culturalEvents,@RequestParam int delay,@RequestParam int respect,@RequestParam int timeliness,@RequestParam int uniform, Model model) {
        Optional<User> user=userRepository.findByNameAndLastName(emp.split(" ")[0],emp.split(" ")[1]);
        Optional<Date> d=dateRepository.findByMonthAndYear(date.split(" ")[0], Integer.parseInt(date.split(" ")[1]));
        Activity activity = new Activity(id,badHabits,bonus,culturalEvents,delay,respect,timeliness,uniform,d.get(),user.get());
        activityRepository.save(activity);
        return "redirect:/accounter/activity/details/{id}";
    }
    @PostMapping(path="/accounter/activity/details/{id}",params = "operation=Delete")
    public String activityDelete(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Activity> activity = activityRepository.findById(id);
        activityRepository.delete(activity.get());
        return "redirect:/accounter/activity";
    }

    @GetMapping("/accounter/dates")
    public String dates(Model model) {
        Iterable<Date> dates = dateRepository.findAll();
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "acdates";
    }
    @PostMapping(path="/accounter/dates",params ="operation=Find")
    public String datesFind(Model model, @RequestParam String month,@RequestParam int year) {
        Optional<Date> dates=null;
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
                else return "redirect:/accounter/dates";
            }
        }
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "acdates";
    }
    @GetMapping("/accounter/salaryAccount")
    public String salaryAccount(Model model) {
        Iterable<TimeSheet> timesheets = timeSheetRepository.findAll();
        Iterable<Date> dates = dateRepository.findAll();
        Iterable<User> users=userRepository.findAll();
        Iterable<Activity> activity=activityRepository.findAll();
        model.addAttribute("dates", dates);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("activity", activity);
        model.addAttribute("users", users);
        model.addAttribute("id", uid);
        return "salaryAcc";
    }
    @PostMapping(path="/accounter/salaryAccount",params ="operation=Save")
    public String salaryAcc(@RequestParam double netSalary,@RequestParam double salary,@RequestParam double pension, double fszn,@RequestParam double tax,@RequestParam float award,@RequestParam int timesheetid,@RequestParam int activityid, Model model) {
        Optional<TimeSheet> timeSheet=timeSheetRepository.findById(timesheetid);
        Optional<Activity> activity=activityRepository.findById(activityid);
        Optional<Salary> sal=salaryRepository.findByActivityAndTimeSheet(activity.get(),timeSheet.get());
        if(sal.isPresent()){
            Parameters parameters = new Parameters(sal.get().getSalaryId(), netSalary, pension, fszn, tax);
            parametersRepository.save(parameters);
            Salary salary1 = sal.get();
            salary1.setSalary(salary);
            salary1.setTimeSheet(timeSheet.get());
            salary1.setActivity(activity.get());
            salary1.setAward(award);
            salary1.setParameters(parameters);
            salaryRepository.save(salary1);
        }else {
            Parameters parameters = new Parameters((parametersRepository.BiggestId() + 1), netSalary, pension, fszn, tax);
            parametersRepository.save(parameters);
            Salary salary1 = new Salary((salaryRepository.BiggestId() + 1), award, salary, timeSheet.get(), activity.get(), parameters);
            salaryRepository.save(salary1);
        }
        return "redirect:/accounter/salaryAccount";
    }

    @PostMapping(path="/accounter/salaryAccount",params ="operation=Prepayment")
    public String prepayment(@RequestParam String date, Model model) {
        Optional<Date> d=dateRepository.findByMonthAndYear(date.split(" ")[0], Integer.parseInt(date.split(" ")[1]));
        Iterable<User> users=userRepository.findAll();
        for (User el:users) {
            Optional<Salary> salary=salaryRepository.findByUserIdAndDateId(el.getUserid(),d.get().getDateId());
            if(salary.isPresent()){
                Salary sal=salary.get();
                sal.setPrepayment(true);
                sal.setPaidSalary(el.getPost().getWages()*0.6);
                salaryRepository.save(sal);
            }else{
                Salary sal=new Salary(salaryRepository.BiggestId()+1,0,0,timeSheetRepository.findByUserIdAndMonthAndYear(el.getUserid(),date.split(" ")[0], Integer.parseInt(date.split(" ")[1])).get(),activityRepository.findByUserIdAndMonthAndYear(el.getUserid(),date.split(" ")[0], Integer.parseInt(date.split(" ")[1])).get(),true,el.getPost().getWages()*0.6);
                salaryRepository.save(sal);
            }
        }
        return "redirect:/accounter/salaryAccount";
    }

    @PostMapping(path="/accounter/salaryAccount",params ="operation=Salary")
    public String salary(@RequestParam String date, Model model) {
        Optional<Date> d=dateRepository.findByMonthAndYear(date.split(" ")[0], Integer.parseInt(date.split(" ")[1]));
        Iterable<User> users=userRepository.findAll();
        for (User el:users) {
            Optional<Salary> salary=salaryRepository.findByUserIdAndDateId(el.getUserid(),d.get().getDateId());
            if(salary.isPresent()){
                Salary sal=salary.get();
                sal.setPaidSalary(sal.getSalary());
                salaryRepository.save(sal);
            }
        }
        return "redirect:/accounter/salaryAccount";
    }

    @GetMapping("/accounter/analystics")
    public String analistics(Model model) {
        Iterable<Date> dates = dateRepository.findAll();
        Iterable<User> users = userRepository.findAll();
        ArrayList<Integer> sum = new ArrayList<>();
        int i = 0;
        for (Date el : dates) {
            sum.add(i, salaryRepository.SumByDateId(el.getDateId()));
            i++;
        }
        Integer[][] userSum = new Integer[(int) users.spliterator().getExactSizeIfKnown()][((int) dates.spliterator().getExactSizeIfKnown())];
        int j = 0;
        for (User u : users) {
            i = 0;
            for (Date el : dates) {
                userSum[j][i] = salaryRepository.SumByDateIdAndUserId(el.getDateId(), u.getUserid());
                i++;
            }
            j++;
        }
        model.addAttribute("users", users);
        model.addAttribute("usersum", userSum);
        model.addAttribute("sum", sum);
        model.addAttribute("dates", dates);
        model.addAttribute("id", uid);
        return "acanalystics";
    }
    @PostMapping("/accounter/analystics")
    public String createReport(@RequestParam String start, @RequestParam String end, Model model) {
        createRep(start, end);
        return "redirect:/accounter/analystics";
    }
    public void createRep(String start, String end) {
        XWPFDocument document = new XWPFDocument();
        try {
            FileOutputStream out = new FileOutputStream(new File(start + "-" + end + ".docx"));
            document.createParagraph().createRun().setText("   ?????????? ?????????????? ???????????????????? ?????????? ???? ????????????: " + start + " ???? " + end);
            document.createParagraph();
            XWPFTable table = document.createTable();
            XWPFTableRow tableRowOne = table.getRow(0);
            document.createParagraph();
            tableRowOne.getCell(0).setText("   ??????????????   ");
            tableRowOne.addNewTableCell().setText("   ??????   ");
            tableRowOne.addNewTableCell().setText("   ??????????????????   ");
            tableRowOne.addNewTableCell().setText("   ????????   ");
            tableRowOne.addNewTableCell().setText("   ????   ");
            tableRowOne.addNewTableCell().setText("   ???????????? ????   ");
            tableRowOne.addNewTableCell().setText("   ????????  ");
            int k = 0;
            Optional<Date> startid = dateRepository.findByMonthAndYear(start.split(" ")[0], Integer.parseInt(start.split(" ")[1]));
            Optional<Date> endid = dateRepository.findByMonthAndYear(end.split(" ")[0], Integer.parseInt(end.split(" ")[1]));
            Iterable<Salary> salary = salaryRepository.findByDate(startid.get().getDateId(), endid.get().getDateId());
            for (Salary el : salary) {
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(el.getTimeSheet().getUser().getFirstName());
                tableRowTwo.getCell(1).setText(el.getTimeSheet().getUser().getLastName());
                tableRowTwo.getCell(2).setText(el.getTimeSheet().getUser().getPost().getPost());
                tableRowTwo.getCell(3).setText(el.getTimeSheet().getDate().getMonth() + " " + el.getTimeSheet().getDate().getYear());
                tableRowTwo.getCell(4).setText(String.valueOf(el.getSalary()));
                tableRowTwo.getCell(5).setText(String.valueOf(el.getParameters().getNetSalary()));
                tableRowTwo.getCell(6).setText(String.valueOf(el.getParameters().getFszn()));
            }

            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

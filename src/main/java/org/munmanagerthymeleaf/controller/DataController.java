package org.munmanagerthymeleaf.controller;

import org.munmanagerthymeleaf.databaseManager.NullCheck;
import org.munmanagerthymeleaf.service.API;
import org.munmanagerthymeleaf.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudents());
        model.addAttribute("studentConferences", dataService.getStudentConferences());
        model.addAttribute("studentAssignments", dataService.getStudentAssignments());

        return "index";
    }

    @GetMapping({"/c/{confID}", "/c/{confID}/"})
    public String indexWithConf(Model model, @PathVariable(value = "confID") Integer confID) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudentsByConferenceId(confID));
        model.addAttribute("studentConferences", dataService.getStudentConferences());
        model.addAttribute("studentAssignments", dataService.getStudentAssignments());

        return "index";
    }

    @GetMapping({"/c/{confID}/a/{assignID}", "/c/{confID}/a/{assignID}/"})
    public String indexWithIds(Model model, @PathVariable(value = "confID") Integer confID, @PathVariable(value = "assignID") Integer assignID) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudentsByConferenceIdAndAssignmentId(confID, assignID));
        model.addAttribute("studentConferences", dataService.getStudentConferences());
        model.addAttribute("studentAssignments", dataService.getStudentAssignments());

        return "index";
    }

    @GetMapping("/studentView/{studentId}")
    public String studentView(Model model, @PathVariable(value = "studentId") int studentId) {
        API api = new API(dataService);
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudents());
        model.addAttribute("studentConferences", dataService.getStudentConferences());
        model.addAttribute("studentAssignments", dataService.getStudentAssignments());
        model.addAttribute("studentAssignmentsList", api.getAssignmentsByStudent(studentId));
        return "studentView";
    }

    @GetMapping("/NULLCHECK")
    public String nullCheck() {
        new NullCheck(dataService).checkForNulls();
        return "redirect:/";
    }
}


package org.munmanagerthymeleaf.controller;

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
}


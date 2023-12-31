package org.munmanagerthymeleaf.controller;

import org.munmanagerthymeleaf.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudents());
        return "index";
    }
}


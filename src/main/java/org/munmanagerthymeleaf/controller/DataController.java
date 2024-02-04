package org.munmanagerthymeleaf.controller;

import com.opencsv.CSVReader;
import org.munmanagerthymeleaf.databaseManager.NullCheck;
import org.munmanagerthymeleaf.model.Student;
import org.munmanagerthymeleaf.service.API;
import org.munmanagerthymeleaf.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
        model.addAttribute("confName", null);

        return "index";
    }

    @GetMapping({"/c", "/c/"})
    public String emptyConf() {
        return "redirect";
    }

    @GetMapping({"/c/{confID}", "/c/{confID}/"})
    public String indexWithConf(Model model, @PathVariable(value = "confID") Integer confID) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("assignments", dataService.getAssignments());
        model.addAttribute("students", dataService.getStudentsByConferenceId(confID));
        model.addAttribute("studentConferences", dataService.getStudentConferences());
        model.addAttribute("studentAssignments", dataService.getStudentAssignments());
        model.addAttribute("confName", dataService.getConferenceById(confID).getConferenceName());

        return "index";
    }

    @GetMapping({"/c/{confID}/a/{assignID}", "/c/{confID}/a/{assignID}/"})
    public String indexWithIds(Model model, @PathVariable(value = "confID") Integer confID, @PathVariable(value = "assignID") Integer assignID) {
        model.addAttribute("conferences", dataService.getConferences());
        model.addAttribute("students", dataService.getStudentsByConferenceIdAndAssignmentId(confID, assignID));
        model.addAttribute("allStudents", dataService.getStudents());
        var studentAssignments = dataService.getStudentAssignmentsByAssignmentId(assignID);
        model.addAttribute("studentAssignments", studentAssignments);
        model.addAttribute("confName", dataService.getConferenceById(confID).getConferenceName());
        model.addAttribute("assignName", dataService.getAssignmentById(assignID).getAssignmentName());

        return "assignmentIndex";
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
        model.addAttribute("studentName", dataService.getStudentById(studentId));

        return "studentView";
    }

    @GetMapping("/uploadCSV")
    public String uploadCSV() {
        return "uploadCSV";
    }

    @PostMapping("/api/uploadStudentCSV")
    public String uploadCSV(Model model, @RequestParam("file") MultipartFile csv) {
        List<Student> students = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(csv.getInputStream()));
            String[] nextLine;
            boolean first = false;
            while ((nextLine = reader.readNext()) != null) {
                if (!first) {
                    first = true;
                    continue;
                }
                Student student = new Student();
                student.setStudentName(nextLine[0]);
                student.setStudentEmail(nextLine[1]);
                students.add(student);
            }
            reader.close();
        } catch (Exception e) {
            Logger.getLogger("API").warning("Error while uploading CSV: " + e.getMessage());
        }
        for (Student student : students) {
            this.dataService.addStudent(student);
        }
        return "redirect";
    }

    @GetMapping("/NULLCHECK")
    public String nullCheck(Model model) {
        new NullCheck(dataService).checkForNulls();
        return "redirect";
    }
}


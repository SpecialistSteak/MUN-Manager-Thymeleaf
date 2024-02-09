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

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * This is the controller class for all the data. It will do all the processing, then send it to thymeleaf to be used within the html.
 */
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

    /**
     * This is made to handle errors, for example if the user tries to access nonexistent conference.
     * @return Redirects to the main page.
     */
    @GetMapping({"/c", "/c/"})
    public String emptyConf() {
        return "redirect";
    }

    /**
     * The conference page, with the conference ID attached. It returns index but with the updated data for it.
     * @param model --.
     * @param confID The conference ID the user is trying to access.
     * @return The index page with the modified data for the conference.
     */
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

    /**
     * @see DataController#indexWithConf(Model, Integer)
     */
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

    /**
     * Returns the student view page, with the student ID attached.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    @GetMapping("/studentView/{studentId}")
    public String studentView(Model model, @PathVariable(value = "studentId") int studentId) throws GeneralSecurityException, IOException {
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

    /**
     * @return the uploadCSV page.
     */
    @GetMapping("/uploadCSV")
    public String uploadCSV() {
        return "uploadCSV";
    }

    /**
     * This is the method that handles the CSV upload. It will read the CSV, then add the students to the database.
     * This method must be in the same class as the uploadCSV() method, as it is the POST method for it.
     * @param model --.
     * @param csv The CSV file that the user is trying to upload.
     * @return Redirects to the main page.
     */
    @PostMapping("/api/uploadStudentCSV")
    public String uploadCSV(Model model, @RequestParam("file") MultipartFile csv) {
        List<Student> students = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(csv.getInputStream()));
            String[] nextLine;
            boolean first = false;
            while ((nextLine = reader.readNext()) != null) {
//                skip first line which contains column names
                if (!first) {
                    first = true;
                    continue;
                }
//                add each student to the list of students
                Student student = new Student();
                student.setStudentName(nextLine[0]);
                student.setStudentEmail(nextLine[1]);
                students.add(student);
            }
            reader.close();
        } catch (Exception e) {
            Logger.getLogger("API").warning("Error while uploading CSV: " + e.getMessage());
        }
//        add all students to the database
        for (Student student : students) {
            this.dataService.addStudent(student);
        }
        return "redirect";
    }

    /**
     * Admin method to print out all null values in the database.
     * @param model --.
     * @return Redirects to the main page.
     */
    @GetMapping("/NULLCHECK")
    public String nullCheck(Model model) {
        new NullCheck(dataService).checkForNulls();
        return "redirect";
    }
}


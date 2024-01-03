package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.model.Assignment;
import org.munmanagerthymeleaf.model.StudentAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class API {

    private final DataService dataService;

    @Autowired
    public API(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/api/getAssignmentsByConference")
    public List<Assignment> getAssignmentsByConference(@RequestParam("confId") int confId) {
        List<Assignment> assignments = dataService.getAssignments();
        List<Assignment> validAssignments = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if (assignment.getConferenceId().getConferenceId() == confId) {
                validAssignments.add(assignment);
            }
        }
        return validAssignments;
    }

    @GetMapping("/api/getStudentAssignmentsByStudent")
    public List<StudentAssignment> getStudentAssignmentsByStudent(@RequestParam("studentId") int studentId) {
        List<StudentAssignment> studentAssignments = dataService.getStudentAssignments();
        List<Assignment> assignments = dataService.getAssignments();
        List<StudentAssignment> validStudentAssignments = new ArrayList<>();
        for (StudentAssignment studentAssignment : studentAssignments) {
            if (studentAssignment.getStudent().getStudentId() == studentId) {
                for (Assignment assignment : assignments) {
                    if (assignment.getAssignmentId() == studentAssignment.getAssignment().getAssignmentId()) {
                        validStudentAssignments.add(studentAssignment);
                    }
                }
            }
        }
        return validStudentAssignments;
    }

    @GetMapping("/api/getAssignmentsByStudent")
    public List<Assignment> getAssignmentsByStudent(@RequestParam("studentId") int studentId) {
        List<StudentAssignment> studentAssignments = dataService.getStudentAssignments();
        List<Assignment> assignments = dataService.getAssignments();
        List<Assignment> validAssignments = new ArrayList<>();
        for (StudentAssignment studentAssignment : studentAssignments) {
            if (studentAssignment.getStudent().getStudentId() == studentId) {
                for (Assignment assignment : assignments) {
                    if (assignment.getAssignmentId() == studentAssignment.getAssignment().getAssignmentId()) {
                        validAssignments.add(assignment);
                    }
                }
            }
        }
        return validAssignments;
    }
}

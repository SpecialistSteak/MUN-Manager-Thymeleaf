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

    public static int lastStudentId = 0;

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

    @GetMapping("/api/getAssignmentsByStudent")
    public List<StudentAssignment> getAssignmentsByStudent(@RequestParam("studentId") int studentId) {
        List<StudentAssignment> studentAssignments = dataService.getStudentAssignments();
        List<Assignment> assignments = dataService.getAssignments();
        List<StudentAssignment> validAssignments = new ArrayList<>();
        for (StudentAssignment studentAssignment : studentAssignments) {
            if (studentAssignment.getStudent().getStudentId() == studentId) {
                for (Assignment assignment : assignments) {
                    if (assignment.getAssignmentId() == studentAssignment.getAssignment().getAssignmentId()) {
                        validAssignments.add(studentAssignment);
                    }
                }
            }
        }
        return validAssignments;
    }

    @GetMapping("/api/newStudentURL")
    public int newStudentURL(@RequestParam("studentId") int studentId, @RequestParam("requestType") int requestType) {
        if(requestType != 1 && requestType != -1) {
            return -1;
        }
        int maxStudentId = 0;
        for (int i = 0; i < dataService.getStudents().size(); i++) {
            if (dataService.getStudents().get(i).getStudentId() > maxStudentId) {
                maxStudentId = dataService.getStudents().get(i).getStudentId();
            }
        }
        if (requestType == 1) {
            if (studentId == maxStudentId) {
                return 1;
            } else {
                return studentId + 1;
            }
        } else {
            if (studentId == 1) {
                return maxStudentId;
            } else {
                return studentId - 1;
            }
        }
    }
}

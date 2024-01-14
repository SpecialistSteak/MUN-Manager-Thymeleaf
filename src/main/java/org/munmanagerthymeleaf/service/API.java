package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.model.Assignment;
import org.munmanagerthymeleaf.model.Conference;
import org.munmanagerthymeleaf.model.StudentAssignment;
import org.munmanagerthymeleaf.model.StudentConference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
            if (assignment.getConference() != null) {
                if (assignment.getConference().getConferenceId() == confId) {
                    validAssignments.add(assignment);
                }
            } else {
                Logger.getLogger("API").warning("Conference is null");
            }
        }
        return validAssignments;
    }

//    ISSUE WITH RETURNING INDEX AND RESUBMITTING FORM

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
        if (requestType != 1 && requestType != -1) {
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

    @GetMapping("/api/conference/{id}")
    public Conference getConferenceById(@PathVariable("id") int id) {
        return dataService.getConferenceById(id);
    }

    @GetMapping("/api/conferenceName/{id}")
    public String getConferenceNameById(@PathVariable("id") int id) {
        return dataService.getConferenceById(id).getConferenceName();
    }

    @PostMapping("/api/newConference")
    public void newConference(@RequestParam("conferenceName") String conferenceName, @RequestParam("excludedStudents") List<Integer> excludedStudents) {
        Conference conference = new Conference();
        conference.setConferenceName(conferenceName);
        List<StudentConference> studentConferences = new ArrayList<>();
        for (int i = 0; i < dataService.getStudents().size(); i++) {
            if (!excludedStudents.contains(dataService.getStudents().get(i).getStudentId())) {
                StudentConference studentConference = new StudentConference();
                studentConference.setStudent(dataService.getStudents().get(i));
                studentConference.setConference(conference);
                studentConference.setDelegation("Delegation " + (i + 1));
                studentConferences.add(studentConference);
            }
        }
        dataService.addConference(conference);
        for (StudentConference studentConference : studentConferences) {
            dataService.addStudentConference(studentConference);
        }
    }

    @PostMapping("/api/newAssignment")
    public void newAssignment(@RequestParam("assignmentName") String assignmentName,
                              @RequestParam("conferenceId") int conferenceId,
                              @RequestParam("dueDate") Date dueDate,
                              @RequestParam("assignmentDescription") String assignmentDescription) {
        dataService.addAssignment(
                new Assignment(assignmentName,
                        dataService.getConferenceById(conferenceId),
                        dueDate.toString(),
                        assignmentDescription));
    }
}

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

    @PostMapping("/api/toggleFlagged")
    public void toggleFlagged(@RequestParam("submissionId") int submissionId) {
        StudentAssignment studentAssignment = dataService.getStudentAssignmentById(submissionId);
        studentAssignment.setFlagged(!studentAssignment.isFlagged());
        dataService.addStudentAssignment(studentAssignment);
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
        Assignment assignment = new Assignment(assignmentName,
                dataService.getConferenceById(conferenceId),
                dueDate.toString(),
                assignmentDescription);
        int assignmentId = assignment.getAssignmentId();
        dataService.addAssignment(assignment);
        // this if-else is here to ensure the assignment is identical to the one in the database, to prevent errors
        if (dataService.getAssignmentById(assignmentId) == null) {
            throw new NullPointerException("Assignment is null. Check the database.");
        } else {
            assignment = dataService.getAssignmentById(assignmentId);
        }
        List<StudentAssignment> studentAssignments = new ArrayList<>();
        for (StudentConference studentConference : dataService.getStudentConferences()) {
            StudentAssignment studentAssignment = new StudentAssignment();
            studentAssignment.setStudent(studentConference.getStudent());
            // setting the assignment to the one just created
            studentAssignment.setAssignment(assignment);
            studentAssignment.setComplete(false);
            studentAssignment.setFlagged(false);
            studentAssignment.setTurnitin_score(0);
            studentAssignment.setWord_count(0);
            studentAssignments.add(studentAssignment);
        }
        for (StudentAssignment studentAssignment : studentAssignments) {
            dataService.addStudentAssignment(studentAssignment);
        }
    }
}

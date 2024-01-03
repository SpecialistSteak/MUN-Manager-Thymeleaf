package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.model.Assignment;
import org.munmanagerthymeleaf.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class API {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/api/getAssignments")
    public List<Assignment> getAssignments(@RequestParam("confId") int confId) {
        List<Assignment> assignments = assignmentRepository.findAll();
        List<Assignment> validAssignments = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if (assignment.getConferenceId().getConferenceId() == confId) {
                validAssignments.add(assignment);
            }
        }
        return validAssignments;
    }
}

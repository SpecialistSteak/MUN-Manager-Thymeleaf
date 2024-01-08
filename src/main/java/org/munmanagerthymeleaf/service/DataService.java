package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.model.*;
import org.munmanagerthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final ConferenceRepository conferenceRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final StudentConferenceRepository studentConferenceRepository;
    private final StudentAssignmentRepository studentAssignmentRepository;

    @Autowired
    public DataService(ConferenceRepository conferenceRepository, AssignmentRepository assignmentRepository, StudentRepository studentRepository, StudentConferenceRepository studentConferenceRepository, StudentAssignmentRepository studentAssignmentRepository) {
        this.conferenceRepository = conferenceRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
        this.studentConferenceRepository = studentConferenceRepository;
        this.studentAssignmentRepository = studentAssignmentRepository;
    }

    public List<Conference> getConferences() {
        return conferenceRepository.findAll();
    }

    public List<Assignment> getAssignments() {
        return assignmentRepository.findAll();
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<StudentConference> getStudentConferences() {
        return studentConferenceRepository.findAll();
    }

    public List<StudentAssignment> getStudentAssignments() {
        return studentAssignmentRepository.findAll();
    }
    
    public Conference getConferenceById(int id) {
        return conferenceRepository.findById(id).orElse(null);
    }
}
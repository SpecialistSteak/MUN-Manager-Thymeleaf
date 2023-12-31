package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.controller.AssignmentRepository;
import org.munmanagerthymeleaf.controller.ConferenceRepository;
import org.munmanagerthymeleaf.controller.StudentConferenceRepository;
import org.munmanagerthymeleaf.controller.StudentRepository;
import org.munmanagerthymeleaf.model.Assignment;
import org.munmanagerthymeleaf.model.Conference;
import org.munmanagerthymeleaf.model.Student;
import org.munmanagerthymeleaf.model.StudentConference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final ConferenceRepository conferenceRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final StudentConferenceRepository studentConferenceRepository;

    @Autowired
    public DataService(ConferenceRepository conferenceRepository, AssignmentRepository assignmentRepository, StudentRepository studentRepository, StudentConferenceRepository studentConferenceRepository) {
        this.conferenceRepository = conferenceRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
        this.studentConferenceRepository = studentConferenceRepository;
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

    public Conference updateConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public StudentConference updateStudentConference(StudentConference studentConference) {
        return studentConference;
    }
}

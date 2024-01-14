package org.munmanagerthymeleaf.service;

import org.munmanagerthymeleaf.model.*;
import org.munmanagerthymeleaf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

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

    public List<Student> getStudentsByConferenceId(int confID) {
        List<StudentConference> studentConferences = studentConferenceRepository.findAll();
        List<Student> students = new ArrayList<>();
        for (StudentConference studentConference : studentConferences) {
            if (studentConference.getConference().getConferenceId() == confID) {
                students.add(studentConference.getStudent());
            }
        }
        return students;
    }

    public List<Student> getStudentsByConferenceIdAndAssignmentId(int confID, int assignID) {
        List<Student> students = new ArrayList<>();
        List<StudentConference> studentConferences = studentConferenceRepository.findAll();
        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findAll();
        for (StudentConference studentConference : studentConferences) {
            if (studentConference.getConference().getConferenceId() == confID) {
                for (StudentAssignment studentAssignment : studentAssignments) {
                    if (studentAssignment.getAssignment().getAssignmentId() == assignID
                            && (studentAssignment.getStudent().getStudentId() == studentConference.getStudent().getStudentId())) {
                        students.add(studentConference.getStudent());

                    }
                }
            }
        }
        return students;
    }

    public boolean addStudent(Student student) {
        studentRepository.save(student);
        return true;
    }

    public boolean addConference(Conference conference) {
        conferenceRepository.save(conference);
        return true;
    }

    public boolean addAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
        return true;
    }

    public boolean addStudentConference(StudentConference studentConference) {
        studentConferenceRepository.save(studentConference);
        return true;
    }

    public boolean addStudentAssignment(StudentAssignment studentAssignment) {
        studentAssignmentRepository.save(studentAssignment);
        return true;
    }

    public String getStudentById(int studentId) {
        return requireNonNull(studentRepository.findById(studentId).orElse(null)).getStudentName();
    }

    public Conference getConferenceById(int id) {
        return conferenceRepository.findById(id).orElse(null);
    }
}
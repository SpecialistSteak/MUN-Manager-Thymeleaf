package org.munmanagerthymeleaf.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.munmanagerthymeleaf.model.Assignment;
import org.munmanagerthymeleaf.model.Conference;
import org.munmanagerthymeleaf.model.StudentAssignment;
import org.munmanagerthymeleaf.model.StudentConference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.munmanagerthymeleaf.drive.DocsService.*;
import static org.munmanagerthymeleaf.drive.DriveService.*;

@RestController
public class API {

    private final DataService dataService;
    protected Drive driveService = getDriveService(GoogleNetHttpTransport.newTrustedTransport());
    protected Docs docsService = getDocsService(GoogleNetHttpTransport.newTrustedTransport());

    @Autowired
    public API(DataService dataService) throws GeneralSecurityException, IOException {
        this.dataService = dataService;
    }

    /**
     * This method will get all assignments from a conference and return them as a list.
     * @param confId the id of the conference to get the assignments from.
     * @return the list of assignments in confId.
     */
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

    /**
     * This method gets a list of assignments for each student, it's used in the student view.
     * @param studentId the id of the student to get the assignments for.
     * @return the list of assignments for studentId.
     */
    @GetMapping("/api/getAssignmentsByStudent")
    public List<StudentAssignment> getAssignmentsByStudent(@RequestParam("studentId") int studentId) {
        List<StudentAssignment> studentAssignments = dataService.getStudentAssignments();
        List<Assignment> assignments = dataService.getAssignments();
        List<StudentAssignment> validAssignments = new ArrayList<>();
        // loop through the student assignments, if the id is valid, then loop through assignments and add all valid ones
        // to the list.
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

    /**
     * This method returns an int that is the next or previous student id, depending on the request type.
     * It is used for the next and previous buttons to ensure that students are looked at by database id.
     * @param studentId the id of the student to get the next or previous student id for.
     * @param requestType the type of request, 1 for next, -1 for previous.
     * @return the next or previous student id.
     */
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

    @GetMapping("/api/getStudentAssignmentContentBodyById")
    public String getStudentAssignmentContentBodyById(@RequestParam("submissionId") int submissionId) {
        String contentBody = dataService.getStudentAssignmentById(submissionId).getContent_body();
        return Objects.requireNonNullElse(contentBody, "");
    }

    @GetMapping("/api/conferenceName/{id}")
    public String getConferenceNameById(@PathVariable("id") int id) {
        return dataService.getConferenceById(id).getConferenceName();
    }

    /**
     * this will make a new conference with a name. The excluded students will not be added to the conference.
     * I opted for this method as it's less data intensive than adding all students to the conference and then removing them.
     * @param conferenceName the name of the conference.
     * @param excludedStudents the students to exclude from the conference.
     */
    @PostMapping("/api/newConference")
    public void newConference(@RequestParam("conferenceName") String conferenceName, @RequestParam("excludedStudents") List<Integer> excludedStudents) throws IOException {
        Conference conference = new Conference();
        conference.setConferenceName(conferenceName);
        conference.setGoogleDriveFolderId(createNestedFolder("1iXrtaYFPvstjLWbeCzKCa4Lo8sNfabdC", driveService, conferenceName));
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

    /**
     * This will make a new assignment with a name, conference id, due date, and description. It will then create the file
     * in the google drive folder for the conference, and then create a folder for each student in the conference.
     * @param assignmentName
     * @param conferenceId
     * @param dueDate
     * @param assignmentDescription
     * @throws IOException
     */
    @PostMapping("/api/newAssignment")
    public void newAssignment(@RequestParam("assignmentName") String assignmentName, @RequestParam("conferenceId") int conferenceId, @RequestParam("dueDate") Date dueDate, @RequestParam("assignmentDescription") String assignmentDescription) throws IOException {
        Assignment assignment = new Assignment(assignmentName, dataService.getConferenceById(conferenceId), dueDate.toString(), assignmentDescription);
        dataService.addAssignment(assignment);
        String assignmentFolderId = createNestedFolder(dataService.getConferenceById(conferenceId).getGoogleDriveFolderId(), driveService, assignmentName);
        int assignmentId = assignment.getAssignmentId();
        String confName = dataService.getConferenceById(conferenceId).getConferenceName();
        // this if-else is here to ensure the assignment is identical to the one in the database, to prevent errors
        if (dataService.getAssignmentById(assignmentId) == null) {
            throw new NullPointerException("Assignment is null. Check the database.");
        } else {
            assignment = dataService.getAssignmentById(assignmentId);
        }
        List<StudentAssignment> studentAssignments = new ArrayList<>();
        // create a new student assignment for each student, leaving it empty to show that it's not complete
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

        // make a list of valid students, then create and share a folder with each of them.
        List<Integer> validStudents = new ArrayList<>();
        List<StudentAssignment> validStudentAssignments = new ArrayList<>();
        for (StudentConference studentConference : dataService.getStudentConferences()) {
            if (studentConference.getConference().getConferenceId() == conferenceId) {
                validStudents.add(studentConference.getStudent().getStudentId());
            }
        }
        for (StudentAssignment studentAssignment : studentAssignments) {
            if (validStudents.contains(studentAssignment.getStudent().getStudentId())) {
                validStudentAssignments.add(studentAssignment);
            }
        }
        for (StudentAssignment studentAssignment : validStudentAssignments) {
            String parentFolderId = createNestedFolder(assignmentFolderId, driveService,
                    studentAssignment.getStudent().getStudentName() + " - " + confName + " " + assignmentName);
            studentAssignment.setAssignment_parent_folder_id(parentFolderId);
            shareFile(driveService, parentFolderId, studentAssignment.getStudent().getStudentEmail().trim());
            dataService.addStudentAssignment(studentAssignment);
        }
    }

    /**
     * This will go through the list of files for each student individually, and process them.
     * It will then add the student assignment to the database provided it is not already there, and make any additional
     * student assignments if there are more than one file in the folder.
     */
    @GetMapping("/api/checkForSubmissions")
    public void checkForSubmissions() throws IOException {
        List<StudentAssignment> studentAssignments = dataService.getStudentAssignments();
        List<StudentAssignment> additionalAssignments = new ArrayList<>();
        for (StudentAssignment studentAssignment : studentAssignments) {
            List<File> files = getFilesInFolder(driveService, studentAssignment.getAssignment_parent_folder_id());
            if (!files.isEmpty()) {
                studentAssignment.setComplete(true);
                for (int i = 0; i < files.size(); i++) {
                    processFile(files.get(i), i, studentAssignment, additionalAssignments);
                }
            }
        }
        for (StudentAssignment additionalAssignment : additionalAssignments) {
            dataService.addStudentAssignment(additionalAssignment);
        }
        System.out.println("Checked for submissions");
    }

    /**
     * Helper method to process the file and add it to the database.
     * @param file the file to process.
     * @param index the index of the file in the list of files.
     * @param studentAssignment the student assignment to add the file to.
     * @param additionalAssignments the list of additional assignments to add to the database.
     */
    private void processFile(File file, int index, StudentAssignment studentAssignment, List<StudentAssignment> additionalAssignments) throws IOException {
        if (index == 0) {
            studentAssignment.setDate_submitted(new Date(file.getModifiedTime().getValue()));
//            it should be a document, if it is then get the word count and set the body content to the database
            if (file.getMimeType().equals("application/vnd.google-apps.document")) {
                String docBodyToString = getDocumentBodyContent(file.getId(), docsService).toString();
                studentAssignment.setWord_count(getWordCountOfGetBodyGetContentToString(docBodyToString));
                studentAssignment.setContent_body(getContentBodyOfContentString(docBodyToString));
            }
            dataService.addStudentAssignment(studentAssignment);
        } else {
//            make an additional assignment for each file in the folder
            StudentAssignment additionalAssignment = new StudentAssignment();
            additionalAssignment.setStudent(studentAssignment.getStudent());
            additionalAssignment.setAssignment(studentAssignment.getAssignment());
            additionalAssignment.setComplete(true);
            additionalAssignment.setTurnitin_score(0);
            additionalAssignment.setWord_count(0);
            additionalAssignment.setAssignment_parent_folder_id(studentAssignment.getAssignment_parent_folder_id());
            additionalAssignment.setDate_submitted(new Date(file.getModifiedTime().getValue()));
            if (file.getMimeType().equals("application/vnd.google-apps.document")) {
                String docBodyToString = getDocumentBodyContent(file.getId(), docsService).toString();
                additionalAssignment.setWord_count(getWordCountOfGetBodyGetContentToString(docBodyToString));
                additionalAssignment.setContent_body(getContentBodyOfContentString(docBodyToString));
            }
            additionalAssignments.add(additionalAssignment);
        }
    }
}

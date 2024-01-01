package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_assignments")
public class StudentAssignment {
    @EmbeddedId
    private StudentAssignmentId id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("assignmentId")
    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}

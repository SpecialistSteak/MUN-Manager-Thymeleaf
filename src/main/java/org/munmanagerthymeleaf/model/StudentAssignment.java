package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

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

    @Column(name = "date_submitted")
    private Date date_submitted;

    @Column(name = "turnitin_score")
    private int turnitin_score;
}

package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "student_assignments")
public class StudentAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private int submissionId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @Column(name = "date_submitted")
    private Date date_submitted;

    @Column(name = "turnitin_score")
    private int turnitin_score;
}

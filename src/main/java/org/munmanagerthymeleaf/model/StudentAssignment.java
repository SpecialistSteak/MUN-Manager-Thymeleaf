package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@Accessors(chain = true)
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

    @Column(name = "word_count")
    private int word_count;

    @Column(name = "flagged")
    private boolean flagged;

    @Column(name = "complete")
    private boolean complete;

    @Column(name = "assignment_parent_folder_id")
    private String assignment_parent_folder_id;

    @Column(name = "content_body")
    private String content_body;

    public StudentAssignment() {
    }
}

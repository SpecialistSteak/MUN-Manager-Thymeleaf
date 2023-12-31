package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    private int assignmentId;

    @Column(name = "assignment_name")
    private String assignmentName;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "assignment_description")
    private String assignmentDescription;
}

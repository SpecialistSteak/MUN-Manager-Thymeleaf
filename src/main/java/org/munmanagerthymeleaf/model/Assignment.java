package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {
    public Assignment (String assignmentName, Conference conference, String dueDate, String assignmentDescription) {
        this.assignmentName = assignmentName;
        this.conference = conference;
        this.dueDate = dueDate;
        this.assignmentDescription = assignmentDescription;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

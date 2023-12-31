package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @Column(name = "AssignmentId")
    private int assignmentId;

    @Column(name = "AssignmentName")
    private String assignmentName;

    @ManyToOne
    @JoinColumn(name = "ConferenceId")
    private Conference conference;
}

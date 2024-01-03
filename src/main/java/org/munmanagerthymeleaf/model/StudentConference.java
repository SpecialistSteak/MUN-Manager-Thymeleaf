package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_conferences")
public class StudentConference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private int participationId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Column(name = "delegation")
    private String delegation;
}

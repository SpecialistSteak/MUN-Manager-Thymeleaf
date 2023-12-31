package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_conferences")
public class StudentConference {
    @Id
    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "ConferenceId")
    private Conference conference;
}

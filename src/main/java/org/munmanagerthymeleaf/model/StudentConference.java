package org.munmanagerthymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_conferences")
public class StudentConference {
    @EmbeddedId
    private StudentConferenceId id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("conferenceId")
    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;
}

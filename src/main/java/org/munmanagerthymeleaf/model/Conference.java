package org.munmanagerthymeleaf.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "conferences")
public class Conference {
    @Id
    @Column(name = "conference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int conferenceId;

    @Column(name = "conference_name")
    private String conferenceName;
}

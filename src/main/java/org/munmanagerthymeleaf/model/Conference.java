package org.munmanagerthymeleaf.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "conferences")
public class Conference {
    @Id
    @Column(name = "ConferenceId")
    private int conferenceId;

    @Column(name = "ConferenceName")
    private String conferenceName;
}

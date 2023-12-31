package org.munmanagerthymeleaf.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class StudentConferenceId implements Serializable {
    private Long studentId;
    private Long conferenceId;
}

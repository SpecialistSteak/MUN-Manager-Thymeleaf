package org.munmanagerthymeleaf.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class StudentAssignmentId implements Serializable {
    private int studentId;
    private int assignmentId;
}

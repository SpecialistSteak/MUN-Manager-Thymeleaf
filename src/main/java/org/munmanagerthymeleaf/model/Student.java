package org.munmanagerthymeleaf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "student_name")
    private String studentName;
}
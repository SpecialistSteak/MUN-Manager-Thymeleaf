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
    @Column(name = "StudentId")
    private int studentId;

    @Column(name = "StudentName")
    private String studentName;

    @Column(name = "Delegation")
    private String delegation;

    @Column(name = "TurnitinScore")
    private int turnitinScore;

    @Column(name = "DateSubmitted")
    private Date dateSubmitted;
}

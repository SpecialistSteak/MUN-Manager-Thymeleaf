package org.munmanagerthymeleaf.controller;

import org.munmanagerthymeleaf.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

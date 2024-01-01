package org.munmanagerthymeleaf.repository;

import org.munmanagerthymeleaf.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

package org.munmanagerthymeleaf.repository;

import org.munmanagerthymeleaf.model.StudentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, Integer> {
}

package org.munmanagerthymeleaf.repository;

import org.munmanagerthymeleaf.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
}

package org.munmanagerthymeleaf.controller;

import org.munmanagerthymeleaf.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
}

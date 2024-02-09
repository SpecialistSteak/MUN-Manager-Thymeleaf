package org.munmanagerthymeleaf.repository;

import org.munmanagerthymeleaf.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is the repository for the assignments. It is made to handle all the database operations for the assignments.
 * Spring JPA will automatically use this to create all the necessary methods for the database operations (like save, delete, etc).
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
}

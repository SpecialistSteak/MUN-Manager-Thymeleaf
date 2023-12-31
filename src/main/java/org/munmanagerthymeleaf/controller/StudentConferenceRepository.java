package org.munmanagerthymeleaf.controller;

import org.munmanagerthymeleaf.model.StudentConference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentConferenceRepository extends JpaRepository<StudentConference, Integer> {
}

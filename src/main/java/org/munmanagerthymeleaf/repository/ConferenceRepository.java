package org.munmanagerthymeleaf.repository;

import org.munmanagerthymeleaf.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
}

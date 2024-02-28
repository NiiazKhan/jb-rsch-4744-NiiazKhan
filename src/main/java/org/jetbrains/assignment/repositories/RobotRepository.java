package org.jetbrains.assignment.repositories;

import org.jetbrains.assignment.entities.CoordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository extends JpaRepository<CoordinateEntity, Long> {
}

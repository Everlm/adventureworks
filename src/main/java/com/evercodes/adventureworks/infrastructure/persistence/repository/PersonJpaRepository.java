package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.infrastructure.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, Integer> {
}

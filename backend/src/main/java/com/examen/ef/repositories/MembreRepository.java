package com.examen.ef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.ef.entities.Membre;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
}

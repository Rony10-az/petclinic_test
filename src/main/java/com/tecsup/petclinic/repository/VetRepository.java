// src/main/java/com/tecsup/petclinic/repository/VetRepository.java
package com.tecsup.petclinic.repository;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
// src/main/java/com/tecsup/petclinic/service/VetService.java
package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;

import java.util.List;

public interface VetService {
    Vet createVet(Vet vet);
    Vet findVetById(Long id);
    Vet updateVet(Long id, Vet vet);
    Vet deactivateVet(Long id);
    Vet reactivateVet(Long id);
    List<Vet> findActiveVets();
}

// src/main/java/com/tecsup/petclinic/service/VetService.java
package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import java.util.List;

public interface VetService {

    // =========================
    // 🔹 CRUD PRINCIPAL
    // =========================

    Vet createVet(Vet vet);

    Vet findVetById(Long id);

    Vet updateVet(Long id, Vet vet);

    // =========================
    // 🔹 ESTADO (ACTIVE / INACTIVE)
    // =========================

    Vet deactivateVet(Long id);

    void activateVet(Long id);

    List<Vet> findActiveVets();

    List<Vet> findInactiveVets();

    // =========================
    // 🔹 LISTADO Y ELIMINACIÓN
    // =========================

    List<Vet> findAllVets();

    void deleteVet(Long id);

    Vet reactivateVet(Long id);

}
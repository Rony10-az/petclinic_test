// src/main/java/com/tecsup/petclinic/service/VetServiceImpl.java
package com.tecsup.petclinic.service;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServiceImpl implements VetService {
    // src/main/java/com/tecsup/petclinic/service/VetServiceImpl.java
// src/main/java/com/tecsup/petclinic/service/VetServiceImpl.java
    @Autowired
    private VetMapper vetMapper;

    // Método create con transformación DTO --> Entidad
    public Vet createVet(VetDTO vetDTO) {
        Vet vet = vetMapper.toEntity(vetDTO);
        return vetRepository.save(vet);
    }

    // Método find Duck to DTO
    public VetDTO findVetByIdDTO(Long id) {
        Vet vet = findVetById(id);
        return vetMapper.toDTO(vet);
    }

    @Autowired
    private VetRepository vetRepository;

    @Override
    public Vet createVet(Vet vet) {
        if (vet == null || vet.getEmail() == null) {
            throw new IllegalArgumentException("Vet object or email cannot be null!");
        }
        return vetRepository.save(vet);
    }


    @Override
    public Vet updateVet(Long id, Vet vet) {
        return vetRepository.findById(id)
                .map(existingVet -> {
                    existingVet.setFirstName(vet.getFirstName());
                    existingVet.setLastName(vet.getLastName());
                    existingVet.setEmail(vet.getEmail());
                    existingVet.setPhone(vet.getPhone());
                    existingVet.setActive(vet.getActive());
                    return vetRepository.save(existingVet);
                })
                .orElseThrow(() -> new RuntimeException("Vet with id " + id + " not found!"));
    }

    @Override
    public Vet findVetById(Long id) {
        return vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet with ID " + id + " not found"));
    }

    @Override
    public void deactivateVet(Long id) {
        Vet vet = findVetById(id); // Reutiliza el método anterior para lanzar la excepción
        vet.setActive(false);
        vetRepository.save(vet);
    }

    @Override
    public List<Vet> findInactiveVets() {
        return vetRepository.findByActiveFalse();
    }
}
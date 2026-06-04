package com.tecsup.petclinic.service;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    // =========================
    // DTO METHODS (NO INTERFACE)
    // =========================

    public Vet createVetFromDTO(VetDTO vetDTO) {
        Vet vet = vetMapper.toEntity(vetDTO);
        vet.setActive(true);
        return vetRepository.save(vet);
    }

    public VetDTO findVetDTOById(Long id) {
        Vet vet = findVetById(id);
        return vetMapper.toDTO(vet);
    }

    // =========================
    // CRUD PRINCIPAL (INTERFACE)
    // =========================

    @Override
    public Vet createVet(Vet vet) {
        if (vet == null) {
            throw new IllegalArgumentException("Vet cannot be null");
        }

        vet.setActive(true);
        return vetRepository.save(vet);
    }

    @Override
    public Vet findVetById(Long id) {
        return vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException(id.toString()));
    }

    @Override
    public Vet updateVet(Long id, Vet vet) {
        Vet existing = findVetById(id);

        existing.setFirstName(vet.getFirstName());
        existing.setLastName(vet.getLastName());
        existing.setEmail(vet.getEmail());
        existing.setPhone(vet.getPhone());

        return vetRepository.save(existing);
    }

    // =========================
    // STATE MANAGEMENT
    // =========================

    @Override
    public Vet deactivateVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(false);
        return vetRepository.save(vet);
    }

    @Override
    public void activateVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(true);
        vetRepository.save(vet);
    }

    @Override
    public Vet reactivateVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(true);
        return vetRepository.save(vet);
    }

    // =========================
    // FILTERS
    // =========================

    @Override
    public List<Vet> findActiveVets() {
        return vetRepository.findByActiveTrue();
    }

    @Override
    public List<Vet> findInactiveVets() {
        return vetRepository.findByActiveFalse();
    }

    @Override
    public List<Vet> findAllVets() {
        return vetRepository.findAll();
    }

    // =========================
    // DELETE (SOFT DELETE RECOMENDADO)
    // =========================

    @Override
    public void deleteVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(false);
        vetRepository.save(vet);
    }
}
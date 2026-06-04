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

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private VetMapper vetMapper;

    // =========================
    // 🔹 MÉTODOS EXTRA (DTO)
    // =========================

    public Vet createVet(VetDTO vetDTO) {
        Vet vet = vetMapper.toEntity(vetDTO);
        return vetRepository.save(vet);
    }

    public VetDTO findVetByIdDTO(Long id) {
        Vet vet = findVetById(id);
        return vetMapper.toDTO(vet);
    }

    // =========================
    // 🔹 CRUD PRINCIPAL
    // =========================

    @Override
    public Vet createVet(Vet vet) {
        if (vet == null || vet.getEmail() == null) {
            throw new IllegalArgumentException("Vet or email cannot be null");
        }
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
        existing.setActive(vet.getActive());

        return vetRepository.save(existing);
    }

    // =========================
    // 🔹 ESTADO
    // =========================

    @Override
    public void deactivateVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(false);
        vetRepository.save(vet);
    }

    @Override
    public void activateVet(Long id) {
        Vet vet = findVetById(id);
        vet.setActive(true);
        vetRepository.save(vet);
    }

    @Override
    public List<Vet> findActiveVets() {
        return vetRepository.findByActiveTrue();
    }

    @Override
    public List<Vet> findInactiveVets() {
        return vetRepository.findByActiveFalse();
    }

    // =========================
    // 🔹 LISTADO Y ELIMINACIÓN
    // =========================

    @Override
    public List<Vet> findAllVets() {
        return vetRepository.findAll();
    }

    @Override
    public void deleteVet(Long id) {
        if (!vetRepository.existsById(id)) {
            throw new VetNotFoundException(id.toString());
        }
        vetRepository.deleteById(id);
    }
}
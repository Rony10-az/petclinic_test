package com.tecsup.petclinic.web;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.service.VetService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;
    private final VetMapper mapper;

    public VetController(VetService vetService, VetMapper mapper) {
        this.vetService = vetService;
        this.mapper = mapper;
    }

    // =========================
    // GET ALL + FILTER (ACTIVE / INACTIVE)
    // =========================
    @GetMapping
    public ResponseEntity<List<VetDTO>> findAll(
            @RequestParam(required = false) Boolean active) {

        List<Vet> vets;

        if (active == null) {
            vets = vetService.findAllVets();
        } else if (active) {
            vets = vetService.findActiveVets();
        } else {
            vets = vetService.findInactiveVets();
        }

        List<VetDTO> result = vets.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) {
        Vet createdVet = vetService.createVet(vet);
        return ResponseEntity
                .created(URI.create("/vets/" + createdVet.getId()))
                .body(createdVet);
    }

    // =========================
    // DEACTIVATE
    // =========================
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVet(@PathVariable Long id) {
        vetService.deactivateVet(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // REACTIVATE
    // =========================
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<Vet> reactivateVet(@PathVariable Long id) {
        Vet vet = vetService.reactivateVet(id);
        return ResponseEntity.ok(vet);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vetService.deleteVet(id);
            return ResponseEntity.noContent().build();
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

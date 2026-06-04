package com.tecsup.petclinic.web;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.service.VetService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // 🔹 GET ALL + FILTER (ACTIVE / INACTIVE)
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
    // 🔹 DELETE (HARD DELETE)
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vetService.deleteVet(id);
            return ResponseEntity.noContent().build(); // 204 OK
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
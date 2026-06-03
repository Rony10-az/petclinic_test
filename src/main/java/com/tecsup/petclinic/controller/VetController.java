package com.tecsup.petclinic.controller;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.service.VetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) {
        Vet createdVet = vetService.createVet(vet);
        return ResponseEntity
                .created(URI.create("/vets/" + createdVet.getId()))
                .body(createdVet);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Vet> deactivateVet(@PathVariable Long id) {
        return ResponseEntity.ok(vetService.deactivateVet(id));
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<Vet> reactivateVet(@PathVariable Long id) {
        return ResponseEntity.ok(vetService.reactivateVet(id));
    }
}

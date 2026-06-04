package com.tecsup.petclinic.web;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.service.VetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping
    public ResponseEntity<List<Vet>> findAll(
            @RequestParam(required = false) Boolean active) {
        List<Vet> vets;
        if (active == null) {
            vets = vetService.findAllVets();
        } else if (active) {
            vets = vetService.findActiveVets();
        } else {
            vets = vetService.findInactiveVets();
        }
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vet> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vetService.findVetById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Vet> create(@RequestBody Vet vet) {
        Vet created = vetService.createVet(vet);
        return ResponseEntity.created(URI.create("/vets/" + created.getId())).body(created);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Vet> deactivate(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vetService.deactivateVet(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<Vet> reactivate(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vetService.reactivateVet(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vetService.deleteVet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

// src/main/java/com/tecsup/petclinic/exception/VetNotFoundException.java
package com.tecsup.petclinic.exceptions;

public class VetNotFoundException extends RuntimeException {
    public VetNotFoundException(Long id) {
        super("Veterinario not found with ID: " + id);
    }
}
// src/main/java/com/tecsup/petclinic/entities/Vet.java
package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vets")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Boolean active;

    // Método utilitario para obtener el nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Boolean isActive() {
        return active;
    }
}
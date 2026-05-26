// src/main/java/com/tecsup/petclinic/dtos/VetDTO.java
package com.tecsup.petclinic.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean isActive;
}
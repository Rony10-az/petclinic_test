package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Vet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	// =========================
	// PET
	// =========================

	public static Pet getPet() {
		return Pet.builder()
				.id(1)
				.name("Leo")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static Pet newPet() {
		return Pet.builder()
				.name("Punky")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static Pet newPetCreated() {
		return Pet.builder()
				.id(1000)
				.name("Punky")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static PetDTO newPetDTO() {
		PetDTO dto = new PetDTO();
		dto.setName("Punky");
		dto.setTypeId(1);
		dto.setOwnerId(1);
		return dto;
	}

	public static PetDTO newPetDTOCreated() {
		PetDTO dto = newPetDTO();
		dto.setId(1000); // ✔ Integer
		return dto;
	}

	public static Pet newPetForUpdate() {
		return Pet.builder()
				.id(1)
				.name("Bear")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static Pet newPetCreatedForUpdate() {
		return Pet.builder()
				.id(4000)
				.name("Bear")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static Pet newPetForDelete() {
		return Pet.builder()
				.id(1)
				.name("Bird")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static Pet newPetCreatedForDelete() {
		return Pet.builder()
				.id(2000)
				.name("Bird")
				.typeId(1)
				.ownerId(1)
				.build();
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets = new ArrayList<>();
		pets.add(getPet());
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets = new ArrayList<>();

		pets.add(Pet.builder()
				.id(9)
				.name("Lucky")
				.typeId(5)
				.ownerId(7)
				.build());

		pets.add(Pet.builder()
				.id(11)
				.name("Freddy")
				.typeId(5)
				.ownerId(9)
				.build());

		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets = new ArrayList<>();

		pets.add(Pet.builder()
				.id(12)
				.name("Lucky")
				.typeId(2)
				.ownerId(10)
				.build());

		pets.add(Pet.builder()
				.id(13)
				.name("Sly")
				.typeId(1)
				.ownerId(10)
				.build());

		return pets;
	}

	// =========================
	// VET
	// =========================

	public static Vet newVet() {
		Vet vet = new Vet();
		vet.setFirstName("Ana");
		vet.setLastName("Lopez");
		vet.setEmail("ana.lopez@test.com");
		vet.setPhone("55510001");
		vet.setActive(true);
		return vet;
	}

	public static Vet newVetCreated() {
		Vet vet = newVet();
		vet.setId(7L); // ✔ Long
		return vet;
	}

	public static Vet deactivatedVet() {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setFirstName("James");
		vet.setLastName("Carter");
		vet.setEmail("james.carter@petclinic.com");
		vet.setPhone("6085551234");
		vet.setActive(false);
		return vet;
	}

	public static Vet reactivatedVet() {
		Vet vet = new Vet();
		vet.setId(6L);
		vet.setFirstName("Sharon");
		vet.setLastName("Jenkins");
		vet.setEmail("sharon.jenkins@petclinic.com");
		vet.setPhone("6085556789");
		vet.setActive(true);
		return vet;
	}
}
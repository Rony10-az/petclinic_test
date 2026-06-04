package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

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
		PetDTO newDTO = new PetDTO();
		newDTO.setName("Punky");
		newDTO.setTypeId(1);
		newDTO.setOwnerId(1);
		return newDTO;
	}

	public static PetDTO newPetDTOCreated() {
		PetDTO petDTO = newPetDTO();
		petDTO.setId(1000);
		return petDTO;
	}

	public static Pet newPetForUpdate() {
		return Pet.builder()
				.id(0)
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
				.id(0)
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
}
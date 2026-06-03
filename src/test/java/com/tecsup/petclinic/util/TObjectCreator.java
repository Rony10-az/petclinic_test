package com.tecsup.petclinic.util;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Vet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	public static Pet newPet() {
		Pet newEntity = new Pet();
		newEntity.setName("Punky");
		newEntity.setTypeId(1);
		newEntity.setOwnerId(1);
		return newEntity;
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static Pet newPetForUpdate() {
		return new Pet(0,"Bear",1,1,null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0,"Bird",1,1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(1,"Leo",1,1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(9,"Lucky",5,7, null));
		pets.add(new Pet(11,"Freddy",5,9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(12,"Lucky",2,10, null));
		pets.add(new Pet(13,"Sly",1,10, null));
		return pets;
	}

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
		vet.setId(7L);
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

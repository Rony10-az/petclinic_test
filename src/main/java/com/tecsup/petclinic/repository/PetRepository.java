package com.tecsup.petclinic.repository;

import com.tecsup.petclinic.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface PetRepository 
	extends JpaRepository<Pet, Integer> {

	// Fetch pets by name
	List<Pet> findByName(String name);

	// Fetch pets by typeId
	List<Pet> findByTypeId(int typeId);

	// Fetch pets by ownerId
	List<Pet> findByOwnerId(int ownerId);

	@Override
	List<Pet> findAll();

}

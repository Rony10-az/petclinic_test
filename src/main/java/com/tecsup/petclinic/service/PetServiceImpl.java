package com.tecsup.petclinic.service;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.mappers.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.mappers.PetMapper;
import com.tecsup.petclinic.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.tecsup.petclinic.repository.PetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author jgomezm
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;


	/**
	 * 
	 * @param petDTO
	 * @return
	 */
	@Override
	public PetDTO create(PetDTO petDTO) {

		Pet newPet = petRepository.save(petMapper.mapToEntity(petDTO));

		return petMapper.mapToDto(newPet);
	}

	/**
	 * 
	 * @param petDTO
	 * @return
	 */
	@Override
	public PetDTO update(PetDTO petDTO) {

		Pet newPet = petRepository.save(petMapper.mapToEntity(petDTO));

		return petMapper.mapToDto(newPet);

	}


	/**
	 * 
	 * @param id
	 * @throws PetNotFoundException
	 */
	@Override
	public void delete(Integer id) throws PetNotFoundException{

		PetDTO pet = findById(id);

		petRepository.delete(this.petMapper.mapToEntity(pet));

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public PetDTO findById(Integer id) throws PetNotFoundException {

		Optional<Pet> pet = petRepository.findById(id);

		if ( !pet.isPresent())
			throw new PetNotFoundException("Record not found...!");

		return this.petMapper.mapToDto(pet.get());
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public List<PetDTO> findByName(String name) {

		List<Pet> pets = petRepository.findByName(name);

		pets.forEach(pet -> log.info("{}", pet));

		return pets
				.stream()
				.map(this.petMapper::mapToDto)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param typeId
	 * @return
	 */
	@Override
	public List<Pet> findByTypeId(int typeId) {

		List<Pet> pets = petRepository.findByTypeId(typeId);

		pets.forEach(pet -> log.info("{}", pet));

		return pets;
	}

	/**
	 * 
	 * @param ownerId
	 * @return
	 */
	@Override
	public List<Pet> findByOwnerId(int ownerId) {

		List<Pet> pets = petRepository.findByOwnerId(ownerId);

		pets.forEach(pet -> log.info("{}", pet));

		return pets;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public List<Pet> findAll() {
		//
		return petRepository.findAll();

	}
}
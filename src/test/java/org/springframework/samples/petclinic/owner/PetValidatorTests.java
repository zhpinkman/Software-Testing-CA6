package org.springframework.samples.petclinic.owner;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class PetValidatorTests {

	private PetValidator petValidator = new PetValidator();
	private static final String REQUIRED = "required";


	@Test
	public void noPropSetTest() {
		Pet pet = new Pet();
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verify(mockError).rejectValue("name", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("type", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	public void nameSetTest() {
		Pet pet = new Pet();
		pet.setName("lucky");
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verify(mockError).rejectValue("type", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	public void typeSetTest() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verify(mockError).rejectValue("name", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("birthDate", REQUIRED, REQUIRED);
		verifyNoMoreInteractions(mockError);
	}

	@Test
	public void bdSetTest() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(1998, 10, 07));
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verify(mockError).rejectValue("type", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("name", REQUIRED, REQUIRED);
		verifyNoMoreInteractions(mockError);
	}

	@Test
	public void validateTypeNotNew() {
		Pet pet = new Pet();
		pet.setId(22);
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verify(mockError).rejectValue("name", REQUIRED, REQUIRED);
		verify(mockError).rejectValue("birthDate", REQUIRED, REQUIRED);
		verifyNoMoreInteractions(mockError);
	}

	@Test
	public void allPropSetTest() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(1998, 10, 07));
		pet.setName("lucky");
		pet.setType(new PetType());
		Errors mockError = mock(Errors.class);
		petValidator.validate(pet, mockError);
		verifyNoInteractions(mockError);
	}

	@Test
	public void supportsTest() {
		Pet pet = new Pet();
		Assert.assertEquals(petValidator.supports(Pet.class), true);
	}

	@Test
	public void supportsTestFail() {
		Pet pet = new Pet();
		Assert.assertEquals(petValidator.supports(Visit.class), false);
		Assert.assertNotNull(petValidator.supports(Pet.class));
	}


}

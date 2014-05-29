package io.core9.plugin.test;

import javax.ws.rs.core.Response;

import io.core9.plugin.rest.api.PetResource;
import io.core9.plugin.rest.api.exceptions.NotFoundException;

import org.junit.Test;

public class TestPetResource {

	
	@Test
	public void test() throws NotFoundException{
		
		PetResource petResource = new PetResource();
		
		Response pet = petResource.getPetById("1");
		
		System.out.println(pet.getStatus());

		System.out.println(pet.getEntity());
	}
	
	
}

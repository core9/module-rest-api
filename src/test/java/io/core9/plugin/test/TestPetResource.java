package io.core9.plugin.test;

import java.io.IOException;

import javax.ws.rs.core.Response;

import io.core9.plugin.rest.api.PetResource;
import io.core9.plugin.rest.api.exceptions.NotFoundException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class TestPetResource {

	
	@Test
	public void test() throws NotFoundException, JsonMappingException, JsonGenerationException, IOException{
		
		PetResource petResource = new PetResource();
		
		Response pet = petResource.getPetById("1");
		
		System.out.println(pet.getStatus());

		Object entity = pet.getEntity();
		
		String pojoAsString = PojoMapper.toJson(entity, true);
		
		System.out.println(pojoAsString);


	}
	
	
}

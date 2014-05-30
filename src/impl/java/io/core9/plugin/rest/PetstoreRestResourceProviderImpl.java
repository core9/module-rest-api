package io.core9.plugin.rest;

import io.core9.plugin.petstore.api.PetResource;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;


@PluginImplementation
public class PetstoreRestResourceProviderImpl implements RestResourceProvider {

	@Override
	public Map<String, Object> getResources() {

		
		Map<String, Object> resourceMap = new HashMap<>();
		
		resourceMap.put(PetResource.class.getName(), new PetResource());
		

		
		return resourceMap;
	}

}

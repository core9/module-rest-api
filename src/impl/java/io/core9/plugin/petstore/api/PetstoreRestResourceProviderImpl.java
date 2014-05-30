package io.core9.plugin.petstore.api;

import io.core9.plugin.rest.RestResourceProvider;
import io.core9.plugin.rest.RestUtils;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;


@PluginImplementation
public class PetstoreRestResourceProviderImpl implements RestResourceProvider {

	private Map<String, RestResource> resourceMap = new HashMap<>();
	
	@Override
	public Map<String, RestResource> getResources() {
		//resourceMap.put(RestUtils.getResourcePath(PetResource.class), new PetResource());
		
		
		RestResource restResource = new RestResourceImpl();
		resourceMap.put(RestUtils.getResourcePath(PetResource.class), restResource );
		
		
		return resourceMap;
	}



}

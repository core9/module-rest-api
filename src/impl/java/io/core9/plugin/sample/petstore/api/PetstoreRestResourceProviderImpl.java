package io.core9.plugin.sample.petstore.api;

import io.core9.plugin.rest.RestResourceProvider;
import io.core9.plugin.rest.RestUtils;

import java.util.HashMap;
import java.util.Map;

import com.wordnik.swagger.config.SwaggerConfig;

import net.xeoh.plugins.base.annotations.PluginImplementation;


@PluginImplementation
public class PetstoreRestResourceProviderImpl implements RestResourceProvider {

	private Map<String, RestResource> resourceMap = new HashMap<>();
	
	@Override
	public Map<String, RestResource> getResources() {

		RestResource restResource = new RestResourceImpl();
		
	    SwaggerConfig config = new SwaggerConfig();
	    config.setApiVersion("1.0.1");
	    config.setBasePath("http://localhost:8080/api");
		
		restResource.setResourceObject(config, new PetResource());
		resourceMap.put(RestUtils.getResourcePath(PetResource.class), restResource );
		
		
		return resourceMap;
	}



}

package com.wordnik.swagger.sample.core9.connector;

import io.core9.plugin.rest.RestResource;
import io.core9.plugin.rest.RestResourceProvider;
import io.core9.plugin.rest.RestUtils;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.sample.resource.PetResource;
import com.wordnik.swagger.sample.resource.PetStoreResource;


@PluginImplementation
public class PetstoreRestResourceProviderImpl implements RestResourceProvider {

	private Map<String, RestResource> resourceMap = new HashMap<>();
	
	@Override
	public Map<String, RestResource> getResources() {

		
		
	    SwaggerConfig config = new SwaggerConfig();
	    config.setApiVersion("1.0.1");
	    config.setBasePath("http://localhost:8080/api");
		

		
		resourceMap.putAll(RestUtils.addRestResource(config, new PetResource()));
		
		resourceMap.putAll(RestUtils.addRestResource(config, new PetStoreResource()));
		
		
		return resourceMap;
	}





}

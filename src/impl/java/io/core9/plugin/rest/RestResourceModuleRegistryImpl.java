package io.core9.plugin.rest;

import io.core9.plugin.sample.petstore.api.RestResource;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RestResourceModuleRegistryImpl implements
		RestResourceModuleRegistry {

	private Map<String, RestResource> resources = new HashMap<>();

	@Override
	public void setResources(Map<String, RestResource> resources) {
		if (resources != null) {
			this.resources.putAll(resources);
		}
	}

	@Override
	public RestResource getResource(String className) {
		return resources.get(className);
	}

}

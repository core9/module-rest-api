package io.core9.plugin.rest;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RestResourceModuleRegistryImpl implements RestResourceModuleRegistry {

	private Map<String, Object> resources = new HashMap<>();



	@Override
	public void setResources(Map<String, Object> resources) {
		if (resources != null) {
			this.resources.putAll(resources);
		}
	}

	@Override
	public Object getResource(String className) {
		return resources.get(className);
	}



}

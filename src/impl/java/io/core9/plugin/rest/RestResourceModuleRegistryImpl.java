package io.core9.plugin.rest;

import io.core9.plugin.petstore.api.RestResource;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RestResourceModuleRegistryImpl implements RestResourceModuleRegistry {

	private Map<String, RestResource> resources = new HashMap<>();
	
	private JSONObject jsonApi;



	@Override
	public void setResources(Map<String, RestResource> resources) {
		if (resources != null) {
			this.resources.putAll(resources);
		}
	}

	@Override
	public Object getResource(String className) {
		return resources.get(className);
	}

	public JSONObject getJsonApi() {
		return jsonApi;
	}

	public void setJsonApi(JSONObject jsonApi) {
		this.jsonApi = jsonApi;
	}



}

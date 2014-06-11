package io.core9.plugin.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.jersey.api.uri.UriTemplate;
import com.sun.jersey.api.uri.UriTemplateParser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RestResourceModuleRegistryImpl implements RestResourceModuleRegistry {

	private Map<String, RestResource> resources = new HashMap<>();
	private Map<String, List<RestResource>> indexedResources = new HashMap<>();

	@Override
	public void setResources(Map<String, RestResource> resources) {
		if (resources != null) {
			for (Entry<String, RestResource> resource : resources.entrySet()) {
				this.resources.put(resource.getKey(), resource.getValue());
				indexResource(resource.getKey(), resource.getValue());
			}

		}
	}

	private void indexResource(String key, RestResource restResource) {
		JSONArray resources = (JSONArray) restResource.getApi().get("apis");
		for (Object resource : resources) {
			JSONObject jsonObj = (JSONObject) resource;

			processRequest(jsonObj);
		}
	}

	private void processRequest(JSONObject jsonObj) {

		
		JSONArray operations = (JSONArray) jsonObj.get("operations");

			for(Object operation : operations){
				
				System.out.println("");
				String path = (String) jsonObj.get("path");
				System.out.println("path : " + path);
				
				
				JSONObject op = (JSONObject)operation;

				String nickname = (String)op.get("nickname");
				System.out.println("nickname : " + nickname);
				
				String method = (String)op.get("method");
				System.out.println("method : " + method);
						
				UriTemplateParser parser = new UriTemplateParser(path);
				String template = parser.getNormalizedTemplate();
				
				System.out.println("normalized template : " + template);
				
				Map<String, String> map = new HashMap<String, String>();
				UriTemplate uriTemplate = new UriTemplate(template);
				if( uriTemplate.match(path, map) ) {
				    System.out.println("Matched, " + map);
				} else {
				    System.out.println("Not matched, " + map);
				} 
				
				String hash = path.split("/")[1] + method + (path.split("/").length - 1);
				System.out.println("Hash : " + hash);
		}

	}

	@Override
	public RestResource getResource(String resourceName) {
		return resources.get(resourceName);
	}

}

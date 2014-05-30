package io.core9.plugin.rest;

import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import io.core9.plugin.petstore.api.RestResource;
import io.core9.plugin.server.request.Request;


@PluginImplementation
public class RestRouterImpl implements RestRouter {
	
	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;

	@Override
	public void getResponse(String basePath, Request request) {

		//{controller=pet, id=null, type=findByTags, tags=test}
		
		String apiPath = "/" + (String) request.getParams().get("controller");
		
		RestResource apiResource = restResourceModuleRegistry.getResource(apiPath);
		
		JSONObject apiJson = apiResource.getApi();
		
		Object apiObject = apiResource.getResourceObject();
		
		System.out.println(request);
		
	}
	



}

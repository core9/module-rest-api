package io.core9.plugin.rest;

import net.minidev.json.JSONArray;
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
		
		String apiPath = "/" + (String) request.getParams().get("api");
		
		RestResource apiResource = restResourceModuleRegistry.getResource(apiPath);
		
		JSONObject apiJson = apiResource.getApi();
		
		JSONArray apis = (JSONArray) apiJson.get("apis");
		
		for(Object api : apis){
			
			JSONObject jsonObj = (JSONObject)api;

			System.out.println(((JSONObject)((JSONArray)jsonObj.get("operations")).get(0)).get("method"));
			String method = (String) ((JSONObject)((JSONArray)jsonObj.get("operations")).get(0)).get("nickname");
			System.out.println(method);
			System.out.println(jsonObj.get("path"));

			String path = (String) jsonObj.get("path");
			
			String[] pathParts = path.split("\\{");
			
			if(pathParts.length > 1 || request.getMethod().equals("GET")){
				// arg 1 is id
				
				System.out.println("Method is : " + method);
				System.out.println("executing..");
			}
			
			
		}
		
		Object apiObject = apiResource.getResourceObject();
		
		System.out.println(request);
		
	}
	



}

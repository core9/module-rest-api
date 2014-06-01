package io.core9.plugin.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import io.core9.plugin.sample.petstore.api.RestResource;
import io.core9.plugin.server.request.Request;


@PluginImplementation
public class RestRouterImpl implements RestRouter {
	
	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;

	@Override
	public JSONObject getResponse(String basePath, Request request) {

		JSONObject result = new JSONObject();
		//{controller=pet, id=null, type=findByTags, tags=test}
		
		String apiPath = "/" + (String) request.getParams().get("api");
		
		
		
	
		RestResource apiResource;
		JSONObject apiJson;
		if(ifApiRequest(apiPath)){
			apiResource = restResourceModuleRegistry.getResource(getApiPath(apiPath));
			
			apiJson = apiResource.getApi();
			return apiJson;
		}
		
		apiResource = restResourceModuleRegistry.getResource(apiPath);
		
		apiJson = apiResource.getApi();
		
		JSONArray apis = (JSONArray) apiJson.get("apis");
		
		Object apiObject = apiResource.getResourceObject();
		
		for(Object api : apis){
			
			JSONObject jsonObj = (JSONObject)api;

			System.out.println(((JSONObject)((JSONArray)jsonObj.get("operations")).get(0)).get("method"));
			String method = (String) ((JSONObject)((JSONArray)jsonObj.get("operations")).get(0)).get("nickname");
			System.out.println(method);
			System.out.println(jsonObj.get("path"));

			String path = (String) jsonObj.get("path");
			String arg1 = (String) request.getParams().get("arg1");
			String arg2 = (String) request.getParams().get("arg2");
			
			String[] pathParts = path.split("\\{");
			
			if(pathParts.length > 1 || request.getMethod().equals("GET")){
				// arg 1 is id
				
				System.out.println("Method is : " + method);
				System.out.println("executing..");
				
				try {
					 result = RestUtils.getResultFromRequest(apiObject, method, arg1);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(method.equals(arg1)){
				try {
					result = RestUtils.getResultFromRequest(apiObject, method, arg2);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		
		System.out.println(request);
		return result;
	}

	private String getApiPath(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		return apiRequest[0];
	}

	private boolean ifApiRequest(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		if(apiRequest.length == 1){
			return false;
		}
		if("docs".equals(apiRequest[1])){
			return true;
		}
		
		return false;
	}
	



}

package io.core9.plugin.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import scala.Option;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.core.util.JsonSerializer;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.model.ApiListing;


public class RestUtils {
	
	
	public static Map<String, RestResource> addRestResource(SwaggerConfig config, Object resourceObject) {
		Map<String, RestResource> resourceMap = new HashMap<>();
		RestResource restResource = new RestResourceImpl();
		restResource.setResourceObject(config, resourceObject);
		resourceMap.put(RestUtils.getResourcePath(resourceObject.getClass()), restResource );
		return resourceMap;
	}
	
	
	public static JSONObject getResultFromRequest(Object obj, String method, String arg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, JsonMappingException, JsonGenerationException, IOException{
		//PetResource petResource = new PetResource();
		Object methodObj = null;

		methodObj = obj.getClass().getMethod(method, String.class);

		Response response = (Response) ((Method) methodObj).invoke(obj, arg);

		System.out.println(response.getStatus());

		Object entity = response.getEntity();

		String pojoAsString = PojoMapper.toJson(entity, true);

		System.out.println(pojoAsString);
		return (JSONObject) JSONValue.parse(pojoAsString);
	}
	
	
	public static JSONObject getApiFromResource(String apiVersion, String basePath, String apiPath, Class<?> clazz){
		
	    DefaultJaxrsApiReader reader = new DefaultJaxrsApiReader();
	    SwaggerConfig config = new SwaggerConfig();
	    config.setApiVersion(apiVersion);
	    config.setBasePath(basePath);
	    
	    Option<ApiListing> apiResource = reader.read(apiPath, clazz, config);
	    

	    String json = JsonSerializer.asJson(apiResource);
	    System.out.println(json);
	    System.out.println("end");
		return (JSONObject) JSONValue.parse(json);
		
	}
	

	public static String getResourcePath(Class<?> clazz) {
		return clazz.getAnnotation(javax.ws.rs.Path.class).value();
	}
	
}

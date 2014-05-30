package io.core9.plugin.rest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import scala.Option;

import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.core.util.JsonSerializer;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.model.ApiListing;

import io.core9.plugin.petstore.api.PetResource;

public class RestUtils {
	
	
	public static JSONObject getApiFromResource(String apiVersion, String basePath, String rootDir, Class<?> clazz){
		
	    DefaultJaxrsApiReader reader = new DefaultJaxrsApiReader();
	    SwaggerConfig config = new SwaggerConfig();
	    config.setApiVersion(apiVersion);
	    config.setBasePath(basePath);
	    
	    Option<ApiListing> apiResource = reader.read(rootDir, clazz, config);
	    

	    String json = JsonSerializer.asJson(apiResource);
	    System.out.println(json);
	    System.out.println("end");
		return (JSONObject) JSONValue.parse(json);
		
	}
	

	public static String getResourcePath(Class<PetResource> clazz) {
		return clazz.getAnnotation(javax.ws.rs.Path.class).value();
	}
	
}

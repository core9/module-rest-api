package io.core9.plugin.rest;

import io.core9.plugin.server.request.Request;

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
		resourceMap.put(RestUtils.getResourcePath(resourceObject.getClass()), restResource);
		return resourceMap;
	}

	public static JSONObject getResultFromRequest(Object obj, String method, String arg) {

		Object methodObj = null;
		try {
			methodObj = obj.getClass().getMethod(method, String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Response response = null;
		try {
			response = (Response) ((Method) methodObj).invoke(obj, arg);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object entity = response.getEntity();
		String jsonString = null;
		try {
			jsonString = PojoMapper.toJson(entity, true);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject result = (JSONObject) JSONValue.parse(jsonString);
		
		return result;
	}

	public static JSONObject getApiFromResource(String apiVersion, String basePath, String apiPath, Class<?> clazz) {

		DefaultJaxrsApiReader reader = new DefaultJaxrsApiReader();
		SwaggerConfig config = new SwaggerConfig();
		config.setApiVersion(apiVersion);
		config.setBasePath(basePath);

		Option<ApiListing> apiResource = reader.read(apiPath, clazz, config);

		return (JSONObject) JSONValue.parse(JsonSerializer.asJson(apiResource));

	}

	public static String getResourcePath(Class<?> clazz) {
		return clazz.getAnnotation(javax.ws.rs.Path.class).value();
	}

	public static RestRequest convertServerRequestToRestRequest(String basePath, Request request) {

		RestRequest req = new RestRequestImpl();
		req.setMethod(request.getMethod());
		req.setBasePath(basePath);
		req.setPath(request.getPath());
		req.setParams(request.getParams());
		req.setVirtualHost(request.getVirtualHost());
		return req;
	}

	public static String getApiPath(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		return apiRequest[0];
	}

	public static boolean ifApiRequest(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		if (apiRequest.length == 1) {
			return false;
		}
		if ("docs".equals(apiRequest[1])) {
			return true;
		}

		return false;
	}
	
}

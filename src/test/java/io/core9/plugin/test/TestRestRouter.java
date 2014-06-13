package io.core9.plugin.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.plugin.rest.RestRequest;
import io.core9.plugin.rest.RestRequestImpl;
import io.core9.plugin.rest.RestRouter;
import io.core9.plugin.rest.RestRouterImpl;
import io.core9.plugin.server.request.Method;

import java.util.HashMap;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class TestRestRouter {

	private static PluginRegistry registry;
	private static RestRouter restRouter;

	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		restRouter = (RestRouter) registry.getPlugin(RestRouterImpl.class);

		assertNotNull(restRouter);
	}

	@SuppressWarnings("unchecked")
	public void restRouterGetApiForPet() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet-docs");

		Object response = restRouter.getResponse(request);

		assertTrue(((HashMap<String, Object>) response).get("resourcePath").equals("/pet"));
	}

	public void restRouterGetPetById() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/1");

		Object response = restRouter.getResponse(request);
		JSONObject jsonResponse = (JSONObject) JSONValue.parse(response.toString());
		assertTrue("Cat 1".equals(jsonResponse.get("name")));
		System.out.println("");}

	public void restRouterGetfindByTags() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/findByTags?tags=tag1");

		JSONArray response = (JSONArray) JSONValue.parse(restRouter.getResponse(request).toString());
		
	
		
		assertTrue("Cat 1".equals(((JSONObject)response.get(0)).get("name")));

		System.out.println(response);
	}

	public static void main(String[] args) {

		TestRestRouter routerTest = new TestRestRouter();
		routerTest.setUp();
		routerTest.restRouterGetPetById();
		routerTest.restRouterGetfindByTags();
		
		System.exit(0); 
	}

}

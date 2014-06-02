package io.core9.plugin.test;

import static org.junit.Assert.*;
import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.plugin.rest.RestRouter;
import io.core9.plugin.rest.RestRouterImpl;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.RequestImpl;
import net.minidev.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

@SuppressWarnings("unused")
public class TestRestRouter {

	private PluginRegistry registry;
	private RestRouter restRouter;
	private Request request = mock(RequestImpl.class);

	private String basePath = "/api";

	@Before
	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		restRouter = (RestRouter) registry
				.getPlugin(RestRouterImpl.class);
		
		assertNotNull(restRouter);
	}

	@Test
	public void restRouterGetApiForPet() {

		String requestPath = "/pet-docs";
		String requestMethod = "GET";
		String arg1 = "1";
		String arg2 = "";
		JSONObject response = restRouter.getResponse(basePath, requestPath, requestMethod, arg1, arg2);

		assertTrue(response.get("resourcePath").equals("/pet"));
	}
	
	@Test
	public void restRouterGetPetById() {

		String requestPath = "/pet";
		String requestMethod = "GET";
		String arg1 = "1";
		String arg2 = "";
		JSONObject response = restRouter.getResponse(basePath, requestPath, requestMethod, arg1, arg2);

		assertTrue(response.toString().replace("\"", "'").equals("{'photoUrls':['url1','url2'],'name':'Cat 1','id':1,'category':{'name':'Cats','id':2},'tags':[{'name':'tag1','id':1},{'name':'tag2','id':2}],'status':'available'}"));
	}

}

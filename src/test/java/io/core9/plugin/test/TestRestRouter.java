package io.core9.plugin.test;

import static org.junit.Assert.*;
import net.minidev.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.plugin.rest.RestRequest;
import io.core9.plugin.rest.RestRequestImpl;
import io.core9.plugin.rest.RestRouter;
import io.core9.plugin.rest.RestRouterImpl;
import io.core9.plugin.server.request.Method;


@SuppressWarnings("unused")
public class TestRestRouter {

	private PluginRegistry registry;
	private RestRouter restRouter;


	private String basePath = "/api";

	// these test will generate a perm gen problem in travis
	//@Before
	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		restRouter = (RestRouter) registry 
				.getPlugin(RestRouterImpl.class);
		
		assertNotNull(restRouter);
	}

	//@Test
	public void restRouterGetApiForPet() {


		RestRequest request = new RestRequestImpl();
		
		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/pet-docs");
		
		
		JSONObject response = restRouter.getResponse(request );

		assertTrue(response.get("resourcePath").equals("/pet"));
	}
	
	//@Test
	public void restRouterGetPetById() {


		
		RestRequest request = new RestRequestImpl();
		
		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/pet/1");
		
		
		JSONObject response = restRouter.getResponse(request);

		assertTrue(response.toString().replace("\"", "'").equals("{'photoUrls':['url1','url2'],'name':'Cat 1','id':1,'category':{'name':'Cats','id':2},'tags':[{'name':'tag1','id':1},{'name':'tag2','id':2}],'status':'available'}"));
	}

}

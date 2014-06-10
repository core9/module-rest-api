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
import net.minidev.json.JSONObject;

import org.junit.BeforeClass;
import org.junit.Test;


public class TestRestRouter {

	private static PluginRegistry registry;
	private static RestRouter restRouter;



	// these test will generate a perm gen problem in travis
	//@BeforeClass
	public static void setUp() {
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
		request.setPath("/api/pet-docs");
		
		
		JSONObject response = restRouter.getResponse(request );

		assertTrue(response.get("resourcePath").equals("/pet"));
	}
	
	//@Test
	public void restRouterGetPetById() {


		
		RestRequest request = new RestRequestImpl();
		
		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/1");
		
		
		JSONObject response = restRouter.getResponse(request);

		assertTrue(response.toString().replace("\"", "'").equals("{'photoUrls':['url1','url2'],'name':'Cat 1','id':1,'category':{'name':'Cats','id':2},'tags':[{'name':'tag1','id':1},{'name':'tag2','id':2}],'status':'available'}"));
	}

	//@Test
	public void restRouterGetfindByTags() {


		
		RestRequest request = new RestRequestImpl();
		
		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/findByTags?tags=tag1%2Ctag2%2Cpet");
		
		
		JSONObject response = restRouter.getResponse(request);

		System.out.println(response);
	}

	
}

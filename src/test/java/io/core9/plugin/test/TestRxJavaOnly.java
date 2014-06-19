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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class TestRxJavaOnly {

	private static PluginRegistry registry;
	private static RestRouter restRouter;


	public void testParallel() {

		final RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet-docs");
		request.setRxJavaVarName("var");
		
		final RestRequest request1 = new RestRequestImpl();

		request1.setBasePath("/api");
		request1.setMethod(Method.GET);
		request1.setPath("/api/pet-docs");
		request1.setRxJavaVarName("var1");
		
		long t1 = System.nanoTime();
		
		Observable.from((Object) request, (Object) request1).parallel(new Func1<Observable<Object>, Observable<String>>() {

			@Override
			public Observable<String> call(Observable<Object> t1) {
				return t1.map(new Func1<Object, String>() {

					@Override
					public String call(Object t1) {
						try {
		                      // randomize to try and force non-determinism
		                      // if we see these tests fail randomly then we have a problem with merging it all back together
		                      int sec = (int) (Math.random() * 3000);
							  System.out.println("Sleeping for : " + Integer.toString(sec));
							  System.out.println(Thread.currentThread());
							  Thread.sleep(sec);
		                  } catch (InterruptedException e) {
		                      System.out.println("*********** error!!!!!!!");
		                      e.printStackTrace();
		                      // TODO why is this exception not being thrown?
		                      throw new RuntimeException(e);
		                  }
						
						
						RestRequest req = (RestRequest) t1;
						Object response = restRouter.getResponse(req);
						
						JSONObject jsonResponse = new JSONObject();
						jsonResponse.put(req.getRxJavaVarName(), response);
						
						return jsonResponse.toString();
					}
					
				});
				
				  
			}

		}).toBlocking().forEach(new Action1<String>() {

			@Override
			public void call(String thingy) {
//				RestRequest obj = (RestRequest) t1;
				
//				System.out.println(obj.toJson());
				System.out.println(thingy);
			}

		});
		System.out.println("parallel test completed ----------");
		long t2 = System.nanoTime();
	    System.out.println("Execution time: " + ((t2 - t1) * 1e-6) + " milliseconds");

	}

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
		System.out.println("");
	}

	public void restRouterGetfindByTags() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/findByTags?tags=tag1");

		JSONArray response = (JSONArray) JSONValue.parse(restRouter.getResponse(request).toString());

		assertTrue("Cat 1".equals(((JSONObject) response.get(0)).get("name")));

		System.out.println(response);
	}

	public void restRouterGetOwnerOfPet() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet/1/owner");

		JSONObject response = (JSONObject) JSONValue.parse(restRouter.getResponse(request).toString());

		assertTrue("Tony".equals(response.get("name")));

		System.out.println(response);
	}

	public void restRouterPutPet() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.PUT);
		request.setPath("/api/pet");

		JSONObject body = (JSONObject) JSONValue.parse(readFile("TestRestRouter.restRouterPostPet.json"));

		request.setBody(body.toString());

		String response = (String) restRouter.getResponse(request);

		assertTrue("\"SUCCESS\"".equals(response));

		System.out.println(response);
	}

	public void restRouterPostPet() {

		RestRequest request = new RestRequestImpl();

		request.setBasePath("/api");
		request.setMethod(Method.POST);
		request.setPath("/api/pet");

		JSONObject body = (JSONObject) JSONValue.parse(readFile("TestRestRouter.restRouterPostPet.json"));

		request.setBody(body.toString());

		String response = (String) restRouter.getResponse(request);

		assertTrue("\"SUCCESS\"".equals(response));

		System.out.println(response);
	}

	public static void main(String[] args) {

		TestRxJavaOnly routerTest = new TestRxJavaOnly();
		routerTest.setUp();
		long start = System.currentTimeMillis();
/*		routerTest.restRouterGetApiForPet();
		routerTest.restRouterGetPetById();
		routerTest.restRouterGetfindByTags();
		routerTest.restRouterGetOwnerOfPet();
		routerTest.restRouterPostPet();
		routerTest.restRouterPutPet();*/
		
		routerTest.testParallel();

		long elapsed = System.currentTimeMillis() - start;
		System.out.println("elapsed time = " + elapsed + "ms");
		System.out.println((elapsed * 1000.0) / 1000000 + " microseconds per execution");

		System.exit(0);
	}

	static String readFile(String file) {
		URL main = TestRxJavaOnly.class.getResource(file);
		File path = new File(main.getPath());
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, StandardCharsets.UTF_8);
	}

}

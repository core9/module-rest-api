package io.core9.plugin.test;

import static org.junit.Assert.assertNotNull;
import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.plugin.rest.RestRequest;
import io.core9.plugin.rest.RestRequestImpl;
import io.core9.plugin.rest.RestRouter;
import io.core9.plugin.rest.RestRouterImpl;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Method;

import java.util.Map;

public class TestRxJavaRequest {

	
	private  PluginRegistry registry;
	private  RestRouter restRouter;

	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		restRouter = (RestRouter) registry.getPlugin(RestRouterImpl.class);

		assertNotNull(restRouter);
	}
	
	
	//FIXME test this!!
	public void sendRequest(){

		RxJavaRequestCollector collector = new RxJavaRequestCollectorImpl();
		RestRequest request = new RestRequestImpl();

		
		request.setVirtualHost(new VirtualHost("localhost"));
		request.setBasePath("/api");
		request.setMethod(Method.GET);
		request.setPath("/api/pet-docs");
		request.setRxJavaMethod("parallel");
		request.setRxJavaVarName("petApi");

		

		collector.setRequest(request);
		collector.setRequest(request);
		Map<String, Map<String, RestRequest>> collection = collector.getRequests();
		
		@SuppressWarnings("unused")
		Map<String, Object> result = EventBusUtils.getInstance().procesRequest(collection);
		
		
		 
	}
	
	
	
	public static void main(String[] args){
		
		TestRxJavaRequest testRxJavaRequest = new TestRxJavaRequest();
		
		testRxJavaRequest.setUp();
		testRxJavaRequest.sendRequest();
	}
	
	
	
	
}

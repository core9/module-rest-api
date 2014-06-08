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

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.spi.cluster.ClusterManagerFactory;
import org.vertx.java.spi.cluster.impl.hazelcast.HazelcastClusterManagerFactory;

//import org.vertx.java.spi.cluster.impl.hazelcast.HazelcastClusterManagerFactory;



public class TestRequestConverter {
	
	
	private PluginRegistry registry;
	private RestRouter restRouter;

	private String basePath = "/api";


	@Before
	public void setUp() {
		
		//org.vertx.java.spi.cluster.impl.hazelcast.HazelcastClusterManagerFactory
		
		//System.setProperty("vertx.clusterManagerFactory", HazelcastClusterManagerFactory.class.getCanonicalName());
		
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		restRouter = (RestRouter) registry 
				.getPlugin(RestRouterImpl.class);
		
		//assertNotNull(restRouter);
	}

	@Test
	public void createRequest() {

		RestRequest req = new RestRequestImpl();
		VirtualHost virtualHost = new VirtualHost("localhost");
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("user", "user1");
		
		req.setMethod(Method.GET);
		req.setBasePath(basePath);
		req.setPath("/test");
		req.setParams(params);
		req.setVirtualHost(virtualHost);

		JSONObject jsonRequest = req.toJson();

		System.out.println(jsonRequest);
	}

}

package io.core9.plugin.test;

import static org.junit.Assert.assertNotNull;
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
	public void restRouterGetApiForUser() {

		JSONObject response = restRouter.getResponse(basePath , request);

		
	}

}

package io.core9.plugin.test;

import static org.junit.Assert.assertNotNull;
import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.plugin.rest.RestRouter;
import io.core9.plugin.rest.RestRouterImpl;

import org.junit.Before;
import org.junit.Test;

public class TestRestResourceProvider {

	private PluginRegistry registry;

	@Before
	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();

	}

	@Test
	public void testRestRouter() {

		RestRouter restRouter = (RestRouter) registry
				.getPlugin(RestRouterImpl.class);

		assertNotNull(restRouter);
	}

}

package io.core9.plugin.test;

import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;

import java.util.List;

import net.xeoh.plugins.base.Plugin;

import org.apache.commons.lang3.ClassUtils;
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
	public void loopPlugins() {

		for (Plugin plugin : registry.getPlugins()) {
			List<Class<?>> interfaces = ClassUtils.getAllInterfaces(plugin
					.getClass());

			
			
		}
	}

}

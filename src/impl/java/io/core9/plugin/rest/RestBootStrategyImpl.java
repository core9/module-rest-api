package io.core9.plugin.rest;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

import io.core9.core.boot.CoreBootStrategy;
import io.core9.plugin.petstore.api.RestResource;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class RestBootStrategyImpl extends CoreBootStrategy
		implements RestBootStrategy {

	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;


	@Override
	public void processPlugins() {
		for (Plugin plugin : this.registry.getPlugins()) {
			
			
			List<Class<?>> interfaces = ClassUtils.getAllInterfaces(plugin
					.getClass()); 
			if (interfaces.contains(RestResourceProvider.class)) {
				
				Map<String, RestResource> resources = ((RestResourceProvider) plugin)
						.getResources();
				
				restResourceModuleRegistry.setResources(resources);
			}
			
			
			
		}
	}

	@Override
	public Integer getPriority() {
		return 600;
	}

}

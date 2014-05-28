package io.core9.plugin.rest;


import io.core9.core.boot.CoreBootStrategy;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RestBootStrategyImpl extends CoreBootStrategy
		implements RestBootStrategy {



	@Override
	public void processPlugins() {
		for (Plugin plugin : this.registry.getPlugins()) {
			
		}
	}

	@Override
	public Integer getPriority() {
		return 600;
	}

}

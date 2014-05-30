package io.core9.plugin.rest;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import io.core9.plugin.server.request.Request;


@PluginImplementation
public class RestRouterImpl implements RestRouter {
	
	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;

	@Override
	public void getResponse(String basePath, Request request) {

		//{controller=pet, id=null, type=findByTags, tags=test}
		System.out.println(request);
		
	}
	



}

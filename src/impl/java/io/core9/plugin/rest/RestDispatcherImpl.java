package io.core9.plugin.rest;

import io.core9.core.boot.CoreBootStrategy;
import io.core9.module.auth.AuthenticationPlugin;
import io.core9.module.auth.User;
import io.core9.plugin.server.HostManager;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.apache.commons.lang3.ClassUtils;

@PluginImplementation
public class RestDispatcherImpl extends CoreBootStrategy implements RestDispatcher {

	private static final Map<String, Object> adminplugins = new HashMap<String, Object>();

	@InjectPlugin
	private VertxServer server;
	
	@InjectPlugin
	private HostManager manager;

	@InjectPlugin
	private AuthenticationPlugin authentication;

	@Override
	public Integer getPriority() {
		return 720;
	}

	@Override
	public void processPlugins() {
		for (Plugin plugin : this.registry.getPlugins()) {
			List<Class<?>> interfaces = ClassUtils.getAllInterfaces(plugin.getClass());
			if (interfaces.contains(RestResourceProvider.class)) {

			}
		}
	}

	@Override
	public void execute() {
		server.use("/api/:request", new Middleware() {
			@Override
			public void handle(Request request) {

				System.out.println("Getting rest request : " + request.getPath());
				
				/*				User user = authentication.getUser(request);
				if (user.isPermitted("dashboard:access") || (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equals("true"))) {
					//adminplugins.get(request.getParams().get("controller")).handle(request);
				} else {
					request.getResponse().setStatusCode(401);
					request.getResponse().setStatusMessage("You are not authorized to view admin data");
				}*/
			}
		});
		//FIXME Only make install available when a key is presented
		//server.use("/install", manager.getInstallationProcedure());
	}
}

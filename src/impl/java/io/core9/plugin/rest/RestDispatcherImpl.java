package io.core9.plugin.rest;

import io.core9.module.auth.AuthenticationPlugin;
import io.core9.plugin.server.HostManager;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Method;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;


@PluginImplementation
public class RestDispatcherImpl  implements RestDispatcher {


	@InjectPlugin
	private VertxServer server;
	
	@InjectPlugin
	private HostManager manager;

	@InjectPlugin
	private AuthenticationPlugin authentication;
	
	@InjectPlugin
	private RestRouter restRouter;



	@Override
	public void execute() {

		//FIXME authorization ??
		server.use("/api/:api((/:arg1)(/:arg2)?)?", new Middleware() {
			@Override
			public void handle(Request request) {

				System.out.println("Getting rest request : " + request.getPath());
				
				
				String apiPath = "/" + (String) request.getParams().get("api");
				String arg1 = (String) request.getParams().get("arg1");
				String arg2 = (String) request.getParams().get("arg2");
				Method method = request.getMethod();
				String requestMethod = method.name();
				JSONObject result = restRouter.getResponse("/api", apiPath, requestMethod, arg1, arg2, request);
				
				System.out.println("Result is : " + result);
				
				request.getResponse().sendJsonMap(result);
			}
		});
	}
}

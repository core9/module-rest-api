package io.core9.plugin.rest;

import net.minidev.json.JSONObject;
import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.request.Method;
import io.core9.plugin.server.request.Request;

public interface RestRouter extends Core9Plugin {

	JSONObject getResponse(String basePath, String apiPath, String requestMethod, String arg1, String arg2, Request request);

}

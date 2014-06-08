package io.core9.plugin.rest;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.request.Request;
import net.minidev.json.JSONObject;

public interface RestRouter extends Core9Plugin {

	//JSONObject getResponse(String basePath, String apiPath, String requestMethod, String arg1, String arg2);

	JSONObject getResponse(RestRequest req);

	JSONObject getResponse(Request request);

}

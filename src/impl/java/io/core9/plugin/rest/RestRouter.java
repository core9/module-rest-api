package io.core9.plugin.rest;

import io.core9.core.plugin.Core9Plugin;
import net.minidev.json.JSONObject;

public interface RestRouter extends Core9Plugin {

	JSONObject getResponse(String basePath, String apiPath, String requestMethod, String arg1, String arg2);

}

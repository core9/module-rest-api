package io.core9.plugin.rest;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.request.Request;

public interface RestRouter extends Core9Plugin {

	void getResponse(String basePath, Request request);

}

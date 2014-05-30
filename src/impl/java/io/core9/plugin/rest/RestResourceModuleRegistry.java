package io.core9.plugin.rest;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.petstore.api.RestResource;

import java.util.Map;

public interface RestResourceModuleRegistry extends Core9Plugin{


	void setResources(Map<String, RestResource> resources);

	RestResource getResource(String apiPath);

}

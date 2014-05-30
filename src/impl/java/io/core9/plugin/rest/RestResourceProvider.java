package io.core9.plugin.rest;

import java.util.Map;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.petstore.api.RestResource;

public interface RestResourceProvider extends Core9Plugin {

	
	Map<String, RestResource> getResources();
	
}

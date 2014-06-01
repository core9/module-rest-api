package io.core9.plugin.rest;

import java.util.Map;

import io.core9.core.plugin.Core9Plugin;

public interface RestResourceProvider extends Core9Plugin {

	
	Map<String, RestResource> getResources();
	
}

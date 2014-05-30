package io.core9.plugin.rest;

import io.core9.core.plugin.Core9Plugin;

import java.util.Map;

public interface RestResourceModuleRegistry extends Core9Plugin{


	void setResources(Map<String, Object> resources);

	Object getResource(String className);

}

package io.core9.plugin.petstore.api;

import net.minidev.json.JSONObject;

import com.wordnik.swagger.config.SwaggerConfig;

public interface RestResource {




	Object getResourceObject();

	void setResourceObject(SwaggerConfig config, Object resourceObject);

	JSONObject getApi();



}

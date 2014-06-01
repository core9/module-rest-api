package io.core9.plugin.sample.petstore.api;

import io.core9.plugin.rest.RestUtils;
import net.minidev.json.JSONObject;

import com.wordnik.swagger.config.SwaggerConfig;

public class RestResourceImpl implements RestResource {

	private JSONObject api;

	private Object resourceObject;

	@Override
	public Object getResourceObject() {
		return resourceObject;
	}

	@Override
	public void setResourceObject(SwaggerConfig config, Object resourceObject) {
		this.resourceObject = resourceObject;
		setApi(RestUtils.getApiFromResource(config.getApiVersion(),
				config.getBasePath(), config.getApiPath(),
				resourceObject.getClass()));
	}

	@Override
	public JSONObject getApi() {
		return api;
	}

	private void setApi(JSONObject api) {
		this.api = api;
	}

}

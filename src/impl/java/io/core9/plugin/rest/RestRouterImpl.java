package io.core9.plugin.rest;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class RestRouterImpl implements RestRouter {

	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;
	@SuppressWarnings("unused")
	private RestRequest restRequest;

	private JSONObject getResponse(RestRequest request, String basePath, String requestPath, String requestMethod) {

		JSONObject result = new JSONObject();

		RestResource apiResource;
		JSONObject apiJson;
		if (ifApiRequest(request.getPath())) {
			apiResource = restResourceModuleRegistry.getResource(getApiPath(request.getPath()));
			apiJson = apiResource.getApi();
			return apiJson;
		}

		String resource = "/" + request.getPathPart(0);
		// currently does not support sub resources only /api/param eq (/pet/1)
		apiResource = restResourceModuleRegistry.getResource(resource);
		apiJson = apiResource.getApi();
		JSONArray apis = (JSONArray) apiJson.get("apis");
		Object apiObject = apiResource.getResourceObject();

		// FIXME this loop should not exist make a map at boot time!!
		for (Object api : apis) {

			JSONObject jsonObj = (JSONObject) api;
			String method = (String) ((JSONObject) ((JSONArray) jsonObj.get("operations")).get(0)).get("nickname");

			switch (request.getMethod()) {
			case DELETE:
				break;
			case PUT:
				break;
			case GET:
				return handleGet(request, result, apiObject, method);
			case POST:
				break;
			case HEAD:
				break;
			case OPTIONS:
				break;
			default:
				System.out.println("method not catched");
			}

		}

		return result;
	}

	private JSONObject handleGet(RestRequest request, JSONObject result, Object apiObject, String method) {
		if (request.getPathPartNr() > 1) {
			result = RestUtils.getResultFromRequest(apiObject, method, request.getPathPart(1));
		} else if (method.equals(request.getPathPart(1))) {
			result = RestUtils.getResultFromRequest(apiObject, method, request.getPathPart(2));
		}
		return result;
	}

	private String getApiPath(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		return apiRequest[0];
	}

	private boolean ifApiRequest(String apiPath) {
		String[] apiRequest = apiPath.split("-");
		if (apiRequest.length == 1) {
			return false;
		}
		if ("docs".equals(apiRequest[1])) {
			return true;
		}

		return false;
	}

	@Override
	public JSONObject getResponse(RestRequest req) {
		this.restRequest = req;

		return getResponse(req, req.getBasePath(), req.getPath(), req.getMethod().name());

	}

}

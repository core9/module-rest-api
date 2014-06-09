package io.core9.plugin.rest;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class RestRouterImpl implements RestRouter {

	@InjectPlugin
	private RestResourceModuleRegistry restResourceModuleRegistry;



	public JSONObject getResponse(RestRequest request) {

		if (RestUtils.ifApiRequest(request.getPath())) {
			return restResourceModuleRegistry.getResource(RestUtils.getApiPath(request.getPath())).getApi();
		}
		
		JSONObject result = new JSONObject();

		// currently does not support sub resources only /api/param eq (/pet/1)
		// use this to fix : https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#definitionResource

		RestResource apiResource = restResourceModuleRegistry.getResource("/" + request.getPathPart(0));
		JSONArray resources = (JSONArray) apiResource.getApi().get("apis");
		Object apiObject = apiResource.getResourceObject();

		// FIXME this loop should not exist make a map at boot time!!
		for (Object resource : resources) {

			JSONObject jsonObj = (JSONObject) resource;
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





}

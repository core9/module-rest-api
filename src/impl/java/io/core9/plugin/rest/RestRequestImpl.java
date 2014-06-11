package io.core9.plugin.rest;

import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Method;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.request.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public class RestRequestImpl implements RestRequest {
	private static HashMap<String, Method> methods = new HashMap<String, Method>();
	{
		methods.put("GET", Method.GET);
		methods.put("PUT", Method.PUT);
		methods.put("POST", Method.POST);
		methods.put("DELETE", Method.DELETE);
		methods.put("OPTIONS", Method.OPTIONS);
		methods.put("HEAD", Method.HEAD);
	}

	Request request;
	private VirtualHost vhost;
	private Response response;
	private String path;
	private Method type;
	private Map<String, Object> context;
	private Map<String, Object> params;
	private String body;
	private List<Cookie> cookies;
	private Map<String, Object> bodyAsMap;
	private List<Object> bodyAsList;
	private String basePath;
	private String[] pathParts;

	@Override
	public List<Cookie> getAllCookies(String name) {
		List<Cookie> foundCookies = new ArrayList<>();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName())) {
					foundCookies.add(c);
				}
			}
		}
		return foundCookies;
	}

	@Override
	public String getBasePath() {
		return basePath;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public List<Object> getBodyAsList() {

		if (bodyAsList != null) {
			return bodyAsList;
		}
		return Arrays.asList(new JsonArray(body).toArray());
	}

	@Override
	public Map<String, Object> getBodyAsMap() {

		if (bodyAsMap != null) {
			return bodyAsMap;
		}
		return new JsonObject(body).toMap();
	}

	@Override
	public Map<String, Object> getContext() {
		return this.context;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R getContext(String name) {
		return (R) this.context.get(name);
	}

	@Override
	public <R> R getContext(String name, R defaultValue) {
		if (context.containsKey(name)) {
			return getContext(name);
		} else {
			return defaultValue;
		}
	}

	@Override
	public Cookie getCookie(String name) {
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName()) && ("/".equals(c.getPath()) || c.getPath() == null)) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public List<Cookie> getCookies() {
		return cookies;
	}

	@Override
	public String getHostname() {
		return request.getHostname();
	}

	@Override
	public Method getMethod() {
		return type;
	}

	@Override
	public Map<String, Object> getParams() {
		return this.params;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getPathPart(int part) {
		return pathParts[part];
	}

	@Override
	public int getPathPartNr() {
		return pathParts.length;
	}

	@Override
	public Response getResponse() {
		return response;
	}

	@Override
	public VirtualHost getVirtualHost() {
		return this.vhost;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R putContext(String name, R value) {
		return (R) this.context.put(name, value);
	}

	@Override
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public void setBodyAsList(List<Object> bodyAsList) {
		this.bodyAsList = bodyAsList;
	}

	@Override
	public void setBodyAsMap(Map<String, Object> bodyAsMap) {
		this.bodyAsMap = bodyAsMap;
	}

	@Override
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	@Override
	public void setMethod(Method method) {
		type = method;
	}

	@Override
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public void setPath(String path) {
		String[] tmpPathParts = path.split("/");
		String[] basePathParts = basePath.split("/");
		pathParts = Arrays.copyOfRange(tmpPathParts, basePathParts.length, tmpPathParts.length);
		this.path = path.substring(basePath.length());
	}

	@Override
	public void setVirtualHost(VirtualHost virtualHost) {
		this.vhost = virtualHost;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();

		json.put("method", type.name());
		json.put("path", path);
		json.put("params", new JSONObject(params));
		json.put("host", vhost.getHostname());

		return json;
	}

	@Override
	public String getHash(){
		return path.split("/")[1] + type.name() + pathParts.length;//petPUT2
	}

	@Override
	public String getApi() {
		return "/" + pathParts[0];
	}
	
}
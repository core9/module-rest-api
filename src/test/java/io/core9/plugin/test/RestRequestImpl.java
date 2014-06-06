package io.core9.plugin.test;

import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Method;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.request.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Response getResponse() {
		return response;
	}

	public RestRequestImpl(Request request) {
		this.request = request;
		this.context = new HashMap<String, Object>();
		this.path = request.getPath();
		this.params = new HashMap<String, Object>();
		for (Entry<String, Object> entry : request.getParams().entrySet()) {
			this.params.put(entry.getKey(), entry.getValue());
		}
		this.type = methods.get(request.getMethod());
		this.response = request.getResponse();
	}

	public RestRequestImpl() {

	}

	@Override
	public Method getMethod() {
		return type;
	}







	@Override
	public Map<String, Object> getParams() {
		return this.params;
	}

	@SuppressWarnings("unchecked")
    private static Map<String, Object> splitQuery(String query) {
		if(query == null || query.length() == 0) return new HashMap<String, Object>();
		String[] pairs = query.split("&");
		Map<String, Object> query_pairs = new HashMap<String, Object>();
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			try {
				String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
				String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
				Set<String> queryKeys = query_pairs.keySet();
				
				if(!queryKeys.isEmpty() && queryKeys.contains(key)){
					ArrayList<String> paramList = new ArrayList<>();
					Object list = query_pairs.get(key);
					if(list instanceof String){
						paramList.add(value);
					}else if(list instanceof ArrayList){
						paramList.addAll((Collection<? extends String>) query_pairs.get(key));
						paramList.add(value);
					} 
					query_pairs.put(key, paramList);
				}else{
					query_pairs.put(key,value);
				}
				
				
			} catch (UnsupportedEncodingException e) {
				return new HashMap<>();
			}
		}
		return query_pairs;
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

	@SuppressWarnings("unchecked")
	@Override
	public <R> R putContext(String name, R value) {
		return (R) this.context.put(name, value);
	}

	@Override
	public Map<String, Object> getContext() {
		return this.context;
	}

	@Override
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public List<Object> getBodyAsList() {
		return Arrays.asList(new JsonArray(body).toArray());
	}

	@Override
	public Map<String, Object> getBodyAsMap() {
		if(this.context.containsKey("is_json_object") && (boolean) this.context.get("is_json_object")) {
			return new JsonObject(body).toMap();
		} else {
			return splitQuery(body);
		}
	}

/*	public void setVirtualHost(VirtualHost vhost) {
		this.vhost = vhost;
		this.response.setVirtualHost(vhost);
	}*/

	@Override
	public VirtualHost getVirtualHost() {
		return this.vhost;
	}

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
	public Cookie getCookie(String name) {
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName()) && ( "/".equals(c.getPath()) || c.getPath() == null )) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
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
	public void setPath(String path) {

		
	}

	@Override
	public void setParams(Map<String, Object> params) {

		
	}

	@Override
	public void setMethod(Method method) {

		
	}

	@Override
	public void setBodyAsMap(Map<String, Object> bodyAsMap) {

		
	}

	@Override
	public void setBodyAsList(List<Object> bodyAsList) {

		
	}

	@Override
	public void setVirtualHost(VirtualHost virtualHost) {

	}

	@Override
	public JSONObject toJson() {
		return null;
	}

}
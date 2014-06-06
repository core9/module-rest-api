package io.core9.plugin.test;

import io.core9.plugin.rest.RestRequest;
import io.core9.plugin.rest.RestRequestImpl;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Method;

import java.util.Map;

import net.minidev.json.JSONObject;

import org.junit.Test;

public class TestRequestConverter {

	@Test
	public void createRequest() {

		RestRequest req = new RestRequestImpl();
		VirtualHost virtualHost = new VirtualHost("localhost");
		Map<String, Object> params = null;
		
		req.setMethod(Method.GET);
		req.setPath("/test");
		req.setParams(params);
		req.setVirtualHost(virtualHost );

		JSONObject jsonRequest = req.toJson();

		System.out.println(jsonRequest);
	}

}

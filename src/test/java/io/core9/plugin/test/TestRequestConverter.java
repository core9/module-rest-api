package io.core9.plugin.test;

import org.junit.Test;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.RequestImpl;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TestRequestConverter {

	
	
	@Test
	public void createRequest(){
		
		
		 Request request = mock(RequestImpl.class);
		 
		 when(request.getBody()).thenReturn("body");
		 
		 assertTrue(request.getBody().equals("body"));
		 
		 
		 RestRequest req = new RestRequestImpl();
		
		
	}
	
	
}

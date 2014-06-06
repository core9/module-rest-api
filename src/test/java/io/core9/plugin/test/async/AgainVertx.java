package io.core9.plugin.test.async;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

public class AgainVertx {

	private Vertx vertx = VertxFactory.newVertx();

	private EventBus eb = vertx.eventBus();

	@SuppressWarnings("rawtypes")
	@Test
	public void testVertx() {

		Handler<Message> myHandler = new Handler<Message>() {
		    public void handle(Message message) {
		        System.out.println("I received a message " + message.body());
		    }
		};

		eb.registerHandler("test.address", myHandler);
		
		eb.publish("test.address", "hello world");
		
		System.out.print("pause" + "\n");

	}
}

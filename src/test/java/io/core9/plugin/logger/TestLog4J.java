package io.core9.plugin.logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TestLog4J {

	private static final Logger log4j = LogManager.getLogger(TestLog4J.class
			.getName());

	public static void main(String[] args) {

		TestLog4J log4j = new TestLog4J();
		log4j.run();

	}

	private void run() {

		log4j.trace("This is a trace message."); 
		log4j.debug("This is  a debug message."); 
		log4j.info("This is an info message."); 
		log4j.error("This is an error message");
		
		
		
	}
}

package io.core9.plugin.test;

import java.lang.reflect.Method;

import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class TestParanamer {

	public static void main(String... args) throws NoSuchMethodException, SecurityException {

		// MySomethingOrOther.java**
		Method method = ExampleMethods.class.getMethod("simpleMethod", String.class, int.class);

		Paranamer paranamer = new CachingParanamer();

		 String[] parameterNames = paranamer.lookupParameterNames(method); //
		// throws ParameterNamesNotFoundException if not found

		// or:

		//String[] parameterNames = paranamer.lookupParameterNames(method, false); // will
																					// return
																					// null
																					// if
																					// not
																					// found

		System.out.println(parameterNames);
	}

}

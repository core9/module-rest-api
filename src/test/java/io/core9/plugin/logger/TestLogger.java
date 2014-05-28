package io.core9.plugin.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;
import io.core9.core.proxy.DefaultInvocationHandler;
import net.xeoh.plugins.base.Plugin;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;

public class TestLogger {

	private PluginRegistry registry;
	private Object orgObj;
	private List<String> constants = new ArrayList<String>();
	
	
	
	@Before
	public void setUp() {
		BootstrapFramework.run();
		registry = PluginRegistryImpl.getInstance();
		
		createConstantClass();
		
	}

	private void createConstantClass() {

		
	}

	@Test
	public void loopPlugins() {

		for (Plugin plugin : registry.getPlugins()) {
			List<Class<?>> interfaces = ClassUtils.getAllInterfaces(plugin
					.getClass());

			String constant = "";
			
			System.out.println("");
			System.out.println("");

			InvocationHandler inv = Proxy.getInvocationHandler(plugin);

			if (inv instanceof DefaultInvocationHandler) {
				orgObj = ((DefaultInvocationHandler) inv).getOriginalObject();
				
				System.out.println(ReflectionToStringBuilder.toString(orgObj,ToStringStyle.MULTI_LINE_STYLE));
				
				System.out.println(ReflectionToStringBuilder.toString(orgObj, new RecursiveObjectPrinter(2)));
				
			}

			System.out.println("");
			System.out.println("");
			System.out.println(plugin.getClass().getCanonicalName()
					+ " contains : ");
			System.out.println("");
			System.out.println(" original class : "
					+ orgObj.getClass().getCanonicalName());
			
			constant += orgObj.getClass().getCanonicalName();
			
			System.out.println("");
			System.out.println(" and contains the folowing interfaces : ");
			System.out.println("");

			for (Class<?> iface : interfaces) {
				System.out.println(iface.getName());
				System.out.println("");
				for (Method method : iface.getDeclaredMethods()) {

					System.out.println(" method : " + method.getName());
					
					constants.add(constant + "." + method.getName() + ".pre");
					constants.add(constant + "." + method.getName() + ".post");
					
					if (method.getParameterTypes().length > 0) {
						System.out.println("  the parameters are : ");
						for (Class<?> parameter : method.getParameterTypes()) {
							System.out.println("  " + parameter.getName());
						}
					}

				}
				System.out.println("");
				System.out.println("");
			}

			System.out.println("");
			
			

		}
		System.out.println("");
	}

}

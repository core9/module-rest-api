package io.core9.plugin.logger;

import java.util.Collection;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//System.out.println(ReflectionToStringBuilder.toString(schema, new RecursiveToStringStyle(5)));

public class RecursiveObjectPrinter extends ToStringStyle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int INFINITE_DEPTH = -1;

	/**
	 * Setting {@link #maxDepth} to 0 will have the same effect as using
	 * original {@link #ToStringStyle}: it will print all 1st level values
	 * without traversing into them. Setting to 1 will traverse up to 2nd level
	 * and so on.
	 */
	private int maxDepth;

	private int depth;

	public RecursiveObjectPrinter() {
		this(INFINITE_DEPTH);
	}

	public RecursiveObjectPrinter(int maxDepth) {
		setUseShortClassName(true);
		setUseIdentityHashCode(false);

		this.maxDepth = maxDepth;
	}

	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			Object value) {
		
		if(fieldName.equals("logger")){
			return;
		}
		
		if (value.getClass().getName().startsWith("java.lang.")
				|| (maxDepth != INFINITE_DEPTH && depth >= maxDepth)) {
			buffer.append(value);
			buffer.append("\n");
		} else {
			depth++;
			buffer.append(ReflectionToStringBuilder.toString(value, this));
			depth--;
		}
	}

	// another helpful method
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			Collection<?> coll) {
		depth++;
		buffer.append(ReflectionToStringBuilder.toString(coll.toArray(), this,
				true, true));
		depth--;
	}
}

package io.core9.plugin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTester {

	//@Test
	public void getRegexFromUrl() {

		String s = "/pet/{petId : [0-9]}/order";
		Pattern p = Pattern.compile("\\{([^}]*)\\}");

		String regex = getRegex(s,p);
		
		System.out.println(regex);


		
	}
	
	
	//@Test 
	public void getValueOfRegexMatch(){
		
		String newString = "/pet/1/order";
		String regex = "[0-9]";
		String pat = "/pet/"+regex+"/order";
		Pattern newPattern = Pattern.compile(pat);
		
		boolean value = getMatches(newString, newPattern);
		
		System.out.println(value);
		
	}

	private boolean getMatches(String s, Pattern p) {
		Matcher m = p.matcher(s);
		
		
		
		return m.matches();
	}
	
	private String getRegex(String s, Pattern p) {
		Matcher m = p.matcher(s);
		while (m.find()) {
			String[] regex = m.group(1).split(":");
			return regex[1].trim();
		}
		return null;
	}

}
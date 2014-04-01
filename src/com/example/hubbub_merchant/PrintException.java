package com.example.hubbub_merchant;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PrintException {

	public static void print(Exception e) {
		StringWriter sw = new StringWriter();
    	e.printStackTrace(new PrintWriter(sw));
    	String exceptionAsString = sw.toString();
    	System.out.println("Exception Occurred:\n" + exceptionAsString);
	}
	
}

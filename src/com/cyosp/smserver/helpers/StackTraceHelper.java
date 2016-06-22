package com.cyosp.smserver.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * This class is an Helper to manage stack traces
 * 
 * @author CYOSP
 *
 */
public class StackTraceHelper
{
	//
	// Static methods
	//
	
	/**
	 * This method returns in a string details of a throwable object
	 * @param throwable object for whom details in string are expected
	 * @return A string which details the throwable object
	 */
	public static String getStackTrace( final Throwable throwable )
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw =  new PrintWriter( sw , true );
		throwable.printStackTrace( pw );

		return sw.getBuffer().toString();
	}
}

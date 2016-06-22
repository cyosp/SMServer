package com.cyosp.smserver.api;

import java.io.IOException;
import java.util.Map;

import com.cyosp.smserver.helpers.FileHelper;
import com.cyosp.smserver.helpers.LogHelper;
import com.cyosp.smserver.helpers.StackTraceHelper;

import fi.iki.elonen.NanoHTTPD;

/**
 * 
 * This class is in charge of the HTTP API
 * 
 * @author CYOSP
 *
 */
public class HTTP extends NanoHTTPD
{
	private static final String ROOT_URL = "/smserver";
	
	/**
	 * @param port listen by the HTTP server
	 */
	public HTTP( int port )
	{
		super( port );
	}
	
	@Override
	public void start() throws IOException
	{
		super.start();
		
		LogHelper.getInstance().info( "HTTP server is running!" );
	}

	@Override
	public Response serve( IHTTPSession session )
	{
		String requestReceived = " request received";
		
		String uri = session.getUri();
		String msg = "<html>";
	
		if( uri.equals( ROOT_URL + "/sms/send" ) )
		{
			LogHelper.getInstance().info( "SMS" + requestReceived );
			
			Map<String, String> parms = session.getParms();
			String phoneNumber = parms.get("nbr");
			String smsContent = parms.get("content");
			
			LogHelper.getInstance().info( "Get: " + phoneNumber + "=" + smsContent  );

			msg = "<body><h2>Request managed:</h2>\n";
			
			SMS smsHelper = null;
			try
			{
				smsHelper = new SMS( phoneNumber, smsContent );
				
				phoneNumber = smsHelper.getPhoneNumber();
				smsContent = smsHelper.getSmsContent();
				
				LogHelper.getInstance().info( "Real: " + phoneNumber + "=" + smsContent  );
				smsHelper.sendSMS();
				LogHelper.getInstance().info( "End send SMS function"  );
				
				msg += "<h1><p>Phone number: " + phoneNumber + "</p></h1>";
				msg += "<h1><p>SMS: " + smsContent + "</p></h1>";
			}
			catch( Exception e )
			{
				// Get stack trace as a string
				String stacktrace = StackTraceHelper.getStackTrace( e );
				// Format stack trace to be HTML compliant
				stacktrace = stacktrace.replaceAll( "\n" , "<br/>" );
				stacktrace = stacktrace.replaceAll( "\t" , "&nbsp;&nbsp;&nbsp;" );
				
				// Define HTML error message
				msg += "<h1><p>ERROR: </p></h1>";
				msg += "<h2><p>" + stacktrace + "</p></h2>";
			}
			
			msg += "</body>";
		}
		else if( uri.equals( ROOT_URL + "/log/display" ) )
		{
			LogHelper.getInstance().info( "Log" + requestReceived );
			
			msg += "<body><p>";
			msg += FileHelper.readFile( LogHelper.getInstance().getLogFilePath() ).replaceAll( "\\n" , "<br/>" );
			msg += "</p></body>";
		}
		else
		{
			LogHelper.getInstance().info( "Unknown" + requestReceived );
			
			msg += "<body><h1>404 File Not Found</h1></body>";
		}
		
		msg += "</html>\n";

		return newFixedLengthResponse(msg);
	}
}
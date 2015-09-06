package com.cyosp.smserver.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * This class is an Helper to manage logs
 * 
 * @author cyosp
 *
 */
public class LogHelper
{
	//
	// Static members
	//
	private static LogHelper instance = null;
	
	//
	// Members
	//
	private File logFilePath = null;
	
	//
	// Private constructor
	//

	private LogHelper()
	{
		super();
	}
	
	private LogHelper( String logFilePath )
	{
		this.logFilePath = new File( logFilePath );
		
		//
		// Create log file if needed
		//
	   if( ! getLogFilePath().exists() )
	   {
	      try
	      {
	      	getLogFilePath().createNewFile();
	      } 
	      catch( IOException e )
	      {
	         e.printStackTrace();
	      }
	   }
	}
	
	//
	// Static methods
	//
	
	/**
	 * This Method must be first and only once
	 * It allows to get a Log Helper instance
	 * @param logFilePath Log file path
	 * @return A LogHelper instance
	 */
	public static LogHelper createInstance( String logFilePath )
	{
		return instance = new LogHelper( logFilePath );
	}
	
	/**
	 * Get a Log Helper instance instance
	 * Must be called after createInstance
	 * @return The LogHelper instance
	 */
	public static LogHelper getInstance()
	{	
		return instance;
	}
	
	//
	// Private method
	//
	
	/**
	 * Define the general log function
	 * @param level of log: [INFO], [ERROR]...
	 * @param msg Message to log
	 */
	private void log( String level , String msg)
	{
		//Log.d("", msg);
		
		// Get date of log
		Date date = new Date();
		// Specify date log format
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy hh:mm:ss" , Locale.getDefault() );
		
	   try
	   {
	      BufferedWriter bw = new BufferedWriter( new FileWriter( getLogFilePath() , true ) );
	      bw.append( level + " " + sdf.format( date ) + " - " + msg );
	      bw.newLine();
	      bw.close();
	   }
	   catch (IOException e)
	   {
	      e.printStackTrace();
	   }
	}
	
	//
	// Public methods
	//
	
	public void info( String text)
	{
		log( "[INFO]" , text );
	}
	
	public void error( String text)
	{
		log( "[ERROR]" , text );
	}
		
	//
	// Getter
	//
	public File getLogFilePath()
	{
		return logFilePath;
	}
}

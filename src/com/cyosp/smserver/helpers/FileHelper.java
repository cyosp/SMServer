package com.cyosp.smserver.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * This class is an Helper of File class
 * 
 * @author cyosp
 *
 */
public class FileHelper
{
	/**
	 * Return a File and return content as a String
	 * @param filePath to read
	 * @return File transformed as String
	 */
	public static String readFile( File filePath )
	{
		StringBuffer sb = new StringBuffer();

		try
		{
			String line;
			BufferedReader br = new BufferedReader( new FileReader( filePath ) );
			while( ( line = br.readLine() ) != null ) sb.append( line ).append( '\n' );
			br.close();
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

		return sb.toString();
	}
}

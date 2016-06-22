package com.cyosp.smserver;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.cyosp.smserver.helpers.LogHelper;

/**
 * App activity
 *
 * @author CYOSP
 *
 */
public class AppActivity extends Activity
{	
	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView(R.layout.main);
		
		//
		// Manage logger: Activity startup VS Service at Android boot
		//
		LogHelper logger = LogHelper.getInstance();
		if( logger == null )
		{
			logger = LogHelper.createInstance( Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name) + ".log" );
			logger.getLogFilePath().delete();
		}
		logger.info( "Activity started" );
			
		// Start service
		startService( new Intent( this , AppService.class ) );
	}
}
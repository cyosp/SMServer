package com.cyosp.smserver;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;

import com.cyosp.smserver.broadcastreceiver.SMSBroadcastReceiver;
import com.cyosp.smserver.helpers.LogHelper;

/**
 * App activity
 *
 * @author cyosp
 *
 */
public class AppActivity extends Activity
{
	public static String checkSMSsentIntent = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView(R.layout.main);
		
		checkSMSsentIntent = getString(R.string.app_name) + "_CHECK_SMS_SENT";
		
		LogHelper logger = LogHelper.createInstance( Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name) + ".log" );
		logger.getLogFilePath().delete();		
		
		logger.info( "Activity started" );
			
		// Register broadcast receiver
		// Receiver will receive only "onReceive" intent register with the IntentFilter 
		registerReceiver(SMSBroadcastReceiver.createInstance( this ), new IntentFilter( checkSMSsentIntent ) );
		
		// Start service
		startService( new Intent( this , AppService.class ) );
	}
}
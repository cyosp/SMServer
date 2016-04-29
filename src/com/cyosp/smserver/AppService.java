package com.cyosp.smserver;

import java.io.File;
import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.cyosp.smserver.api.HTTP;
import com.cyosp.smserver.helpers.LogHelper;

/**
 * App foreground service
 *
 * @author CYOSP
 *
 */
public class AppService extends Service
{
	/** Called when the service is first created. */
	@Override
	public void onCreate()
	{
		//
		// Manage logger: Activity startup VS Service at Android boot
		//
		LogHelper logger = LogHelper.getInstance();
		if( logger == null )
		{
			logger = LogHelper.createInstance( Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name) + ".log" );
			logger.getLogFilePath().delete();
		}
		logger.info( "Service started" );
		
		try
		{
			HTTP http = new HTTP( 8080 );
			http.start();
			
			PowerManager mgr = (PowerManager)getSystemService(Context.POWER_SERVICE);
			WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
			wakeLock.acquire();
			//wakeLock.release();	
		}
		catch( IOException ioe )
		{
			LogHelper.getInstance().error( "Couldn't start server:\n" + ioe);
		}
	};

	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{
		Notification notification = new Notification(R.drawable.ic_launcher, getString( R.string.app_name ) + " is starting", System.currentTimeMillis());
		
		Intent i = new Intent(this, AppActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
		
		//
		// Compute version label
		//
		String versionlabel = "";
		try
		{
			versionlabel = "v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		}
		catch( NameNotFoundException e )
		{
			// Version won't be displayed
		}
		
		notification.setLatestEventInfo( this , getString( R.string.app_name ) , "Started " + versionlabel , pendingIntent );
		notification.flags |= Notification.FLAG_NO_CLEAR;
		
		// Transform background service as a foreground service
		startForeground(32, notification);
		
		LogHelper.getInstance().info("Service transformed as foreground");

		return START_STICKY;
	}

	@Override
	public IBinder onBind( Intent intent )
	{
		// This service doesn't interact with another
		return null;
	}
}

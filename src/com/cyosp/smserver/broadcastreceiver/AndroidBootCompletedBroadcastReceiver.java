package com.cyosp.smserver.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cyosp.smserver.AppService;

/**
 * This class is in charge of start SMServer service at the end of Android boot phase
 * 
 * @author CYOSP
 *
 */
public class AndroidBootCompletedBroadcastReceiver extends BroadcastReceiver
{ 
	@Override
	public void onReceive( Context context , Intent intent )
	{
		// Manage only boot completed intent
		if( Intent.ACTION_BOOT_COMPLETED.equals( intent.getAction() ) )
		{
			// Start SMServer service
			context.startService( new Intent( context , AppService.class ) );
		}
	}
}
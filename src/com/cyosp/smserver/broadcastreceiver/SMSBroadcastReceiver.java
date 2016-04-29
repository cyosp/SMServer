package com.cyosp.smserver.broadcastreceiver;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cyosp.smserver.helpers.LogHelper;

/**
 * This class is in charge to manage SMS send status
 * 
 * @author CYOSP
 *
 */
public class SMSBroadcastReceiver extends BroadcastReceiver
{
	//
	// Static members
	//
	private final static int NULL_SMS_PART_SIZE = 0;
	private final static int SMS_PART_SIZE_DEFAULT_VALUE = -1;
	private final static boolean SMS_DEFAULT_SENT_STATUS = true;
	
	private static SMSBroadcastReceiver instance = null;
	
	//
	// Members
	//
	
	// Allow to store application context
	private Context context = null;
	
	// Map between a SMS id and the part number of this SMS 
	private Map<String, Integer> sendMap = null;
	// Map between a SMS id and SMS status to define if SMS has been sent
	private Map<String, Boolean> sendStatus = null;
	
	//
	// Private constructors
	//
	private SMSBroadcastReceiver()
	{
		super();
		
		sendMap = new HashMap<String, Integer>();
		sendStatus = new HashMap<String, Boolean>();
	}
	
	private SMSBroadcastReceiver( Context context )
	{
		this();
		
		this.context = context;
	}
	
	//
	// Static methods
	//
	
	/**
	 * This Methode must be first and only once
	 * It allows to get a SMS Broadcast Receiver instance
	 * @param context Application context
	 * @return A SMSBroadcastReceiver instance
	 */
	public static SMSBroadcastReceiver createInstance( Context context)
	{
		return instance = new SMSBroadcastReceiver( context );
	}
	
	/**
	 * Get a SMS Broadcast Receiver instance
	 * Must be called after createInstance
	 * @return The SMSBroadcastReceiver instance
	 */
	public static SMSBroadcastReceiver getInstance()
	{	
		return instance;
	}
	
	//
	// Public methods
	//
	
	/**
	 * Used to create a PendingIntent using input Intent
	 * It stores also SMS information for onReceive callback:
	 *  - SMS id
	 *  - SMS part number
	 * @param intent to use for the onReceive callback
	 * @return A PendingIntent using input Intent
	 */
	public PendingIntent getBroadcast( Intent intent )
	{
		// Get SMS id
		String smsId = intent.getStringExtra("id");	
		
		// Add/update map with SMS id and SMS part number
		getSendMap().put( smsId , intent.getIntExtra( "smsPartNbr" , SMS_PART_SIZE_DEFAULT_VALUE ) );
		// Add/update map with SMS id and status
		// By default a SMS is assumed sent
		getSendStatus().put( smsId , SMS_DEFAULT_SENT_STATUS );
		
		return PendingIntent.getBroadcast( getContext() , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT );
	}
	
	@Override
	public void onReceive( Context context , Intent intent )
	{
		// Get SMS id
		String smsId = intent.getStringExtra("id");
		
		// Retrieve number of SMS part in waiting of feedback
		int smsPartSize = getSendMap().get( smsId );
		
		// On part less to manage 
		smsPartSize--;
		// Update value in map
		getSendMap().put(smsId, smsPartSize);

		// Update SMS sent status
		if( getResultCode() != Activity.RESULT_OK )	getSendStatus().put( smsId , false );

		// Check if it's the last SMS part
		if( smsPartSize == NULL_SMS_PART_SIZE )
		{	
			if( getSendStatus().get(smsId) )	LogHelper.getInstance().info( "SMS sent" );
			else										LogHelper.getInstance().info( "SMS has failed to be send" );
		}
		
		// Clean maps if needed
		if( smsPartSize <= NULL_SMS_PART_SIZE )
		{	
			getSendMap().remove(smsId);
			getSendStatus().remove(smsId);
		}
	}

	//
	// Getters and setters
	//
	
	private Context getContext()
	{
		return context;
	}

	private Map<String, Integer> getSendMap()
	{
		return sendMap;
	}
	
	private Map<String, Boolean> getSendStatus()
	{
		return sendStatus;
	}
}
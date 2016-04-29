package com.cyosp.smserver.api;

import java.util.ArrayList;

import com.cyosp.smserver.AppActivity;
import com.cyosp.smserver.broadcastreceiver.SMSBroadcastReceiver;
import com.cyosp.smserver.helpers.LogHelper;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * 
 * This class is in charge of the SMS API
 * 
 * @author CYOSP
 *
 */
public class SMS
{	//
	// Static members
	//
	private static int countAsId = 0;
	
	//
	// Members
	//
	private String phoneNumber = "";
	private String smsContent = "";

	//
	// Constructor
	//
	public SMS( String phoneNumber, String smsContent ) throws Exception
	{
		if( phoneNumber != null && ! phoneNumber.equals("") )	this.phoneNumber = phoneNumber;
		else throw new Exception( "Phone number must not be empty" );
		if( smsContent != null && ! smsContent.equals("") )		this.smsContent = smsContent;
		else throw new Exception( "SMS content must not be empty" );
	}

	/**
	 * This method is in charge to send the SMS
	 */
	public void sendSMS()
	{
		// Update Id which will be associated to the phone number in order to create
		//  an uniq id for the call back to check if SMS has been sent of not
		countAsId++;
		
		// Get SMS manager
		SmsManager smsManager = SmsManager.getDefault();
		LogHelper.getInstance().info( "SMS manager retrieved" );

		// Get content to send in multiple SMS
		ArrayList<String> smsMultipart = smsManager.divideMessage(getSmsContent());
		LogHelper.getInstance().info( "SMS devided into " + smsMultipart.size() );
		
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
		// Add a send intent per SMS part
		for( int i = 0; i < smsMultipart.size(); i++ )
		{
			Intent intent = new Intent( AppActivity.checkSMSsentIntent );
			intent.putExtra( "id" , getPhoneNumber() + "#" + countAsId );
			intent.putExtra( "smsPartNbr" , smsMultipart.size() );
			
			// Transform Intent in PendingIntent
			// And register it for callback in order to know if SMS has been sent
			sentIntents.add( SMSBroadcastReceiver.getInstance().getBroadcast( intent ) );
		}
		
		// Send SMS
		smsManager.sendMultipartTextMessage( getPhoneNumber() , null , smsMultipart , sentIntents , null );
		LogHelper.getInstance().info( "End sendSMS()" );
	}
	
	//
	// Getters
	//
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getSmsContent()
	{
		return smsContent;
	}
}
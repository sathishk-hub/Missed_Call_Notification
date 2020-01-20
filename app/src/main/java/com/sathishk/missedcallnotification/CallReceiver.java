package com.sathishk.missedcallnotification;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    static boolean ring=false;
    static boolean callReceived=false;
    static  String callerPhoneNumber=null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state == null)
            return;
        // If phone state "Ringing"
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            ring = true;
            // Get the Caller's Phone Number
            Bundle CallBundle = intent.getExtras();
            callerPhoneNumber = CallBundle.getString("incoming_number");
        }
        // If incoming call is received
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            ring=false;
            callReceived = true;
        }
        // If phone is Idle
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            // If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
            if (ring==true  && callReceived==false ) {
               Toast.makeText(App.getContext(), "MISSED CALL from : "+callerPhoneNumber, Toast.LENGTH_LONG).show();
            }
            if(callReceived){
                callReceived=false;
            }
        }

    }


}


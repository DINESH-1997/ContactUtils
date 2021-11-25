package com.example.contactutils.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.contactutils.repository.ContactsRepo;
import com.example.contactutils.view.NotificationActivity;

public class PhoneStateReceiver extends BroadcastReceiver {

    private ContactsRepo contactsRepo;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String callerNumber = Utils.formatNumber(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
                //Toast.makeText(context,"Calling : " + callerNumber,Toast.LENGTH_SHORT).show();
                if(contactsRepo == null)
                    contactsRepo = ContactsRepo.getInstance();

                if(contactsRepo.isValidDialer(context, callerNumber)){
                    Intent dialogueIntent = new Intent(context, NotificationActivity.class);
                    dialogueIntent.putExtra(GlobalValues.DIALER_NUMBER, callerNumber);
                    context.startActivity(dialogueIntent);
                }
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                //Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

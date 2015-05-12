package com.example.harpreetsingh.messagereciever;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver br = new SmsReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SEND);
        registerReceiver(br, filter);
    }

    public class SmsReceiver extends BroadcastReceiver {

        private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_RECEIVED)) {
                //abortBroadcast();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    // get sms objects
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus.length == 0) {
                        return;
                    }
                    // large message might be broken into many
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        sb.append(messages[i].getMessageBody());
                    }
                    String sender = messages[0].getOriginatingAddress();
                    String message = sb.toString();
                    Toast.makeText(context, "Harpreet: "+message, Toast.LENGTH_LONG).show();

                    SmsManager sms = SmsManager.getDefault();
                    //TODO: get phone number automatically from device instead of hard coding
                   //sms.sendTextMessage("6472440866", null, message, null, null);//phone number will be your number.
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

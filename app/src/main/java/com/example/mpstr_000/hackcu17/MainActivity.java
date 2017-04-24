package com.example.mpstr_000.hackcu17;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    Button sendBtn;
    EditText txtPhoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    CheckBox RoadBlockCheckBox,AssaultCheckBox;
    Button addToString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sendBtn = (Button) findViewById(R.id.btnSendSMS);
        this.txtPhoneNo = (EditText) findViewById(R.id.editText);
        this.txtMessage = (EditText) findViewById(R.id.editText2);

        RoadBlockCheckBox = (CheckBox) findViewById(R.id.RoadBlockCheckBox);
        AssaultCheckBox = (CheckBox) findViewById(R.id.AssaultCheckBox);
        addToString = (Button) findViewById(R.id.addToString);


        this.sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
        addToString.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                StringBuffer result = new StringBuffer();
                result.append("Thanks : ").append(RoadBlockCheckBox.isChecked());
                result.append("\nThanks: ").append(AssaultCheckBox.isChecked());
                Toast.makeText(MainActivity.this, result.toString(),
                        Toast.LENGTH_LONG).show();}
                //message = txtMessage.getText().toString();

                /*if (((CheckBox)v).isChecked()){
                    message = message + "1";
                    EditText(message);
                    //txtMessage.setText(message.getText().toString()+"\n1");
                }*/


        });


    }



    protected void sendSMSMessage() {

        phoneNo = txtPhoneNo.getText().toString();
        message = txtMessage.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

}
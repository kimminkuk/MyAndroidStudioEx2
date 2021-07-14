package com.techcom.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static final  String TAG = "SmsReceiver";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //getApplicationContext -> OnReceive 안에서만 사용가능하다 (여기서 호출받아서)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() 호출됨.");

        //텔레포니 모듈에서 intent 넣어줌
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);
        if(messages.length > 0) {
            String sender = messages[0].getOriginatingAddress(); // 발신 번호
            Log.d(TAG,"sender: " + sender);

            String contents = messages[0].getMessageBody().toString();
            Log.d(TAG, "contents : " + contents);

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "received date : " + receivedDate);

            SendToActivity(context, sender, contents, receivedDate);
        }
    }

    private void SendToActivity(Context context, String sender, String contents, Date receivedDate) {
        //인텐트에 넣어서 보내야죠?
        Intent intent = new Intent(context,SmsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        |Intent.FLAG_ACTIVITY_SINGLE_TOP
                        |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("receivedDate", format.format(receivedDate));


        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus"); // SMS 처리하는 국제 표준 프로토콜?
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //마시멜로 버전
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }

}
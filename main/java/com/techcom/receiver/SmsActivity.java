package com.techcom.receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        editText1 = (EditText) findViewById(R.id.editTextTextPersonName);
        editText2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editText3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent passedIntent = getIntent();
        processCommand(passedIntent);
    }

    //intent가 있을때는 이쪽에서 정의된다.
    @Override
    protected void onNewIntent(Intent intent) {
        processCommand(intent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent passedIntent) {
        if(passedIntent != null) {
            String sender = passedIntent.getStringExtra("sender");
            String contents = passedIntent.getStringExtra("contents");
            String receivedDate = passedIntent.getStringExtra("receivedDate");

            editText1.setText(sender);
            editText2.setText(contents);
            editText3.setText(receivedDate);
        }
    }
}
package com.maya.androidtutorial.smartwatchtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IncomingMessageActivity extends Activity {

    private TextView mTextView;

    private EditText editText;
    public static final int INTENT_GETTEXT = 10;
    public static final String INTENT_STRING_RESULT = "resultString";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_message);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                editText = (EditText) findViewById(R.id.editTextResult);
            }
        });


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    public void answer(View view) {


        Intent startTexting = new Intent(this, MainActivity.class);
        startActivityForResult(startTexting, INTENT_GETTEXT);

    }

    public void sent(View view) {
        Toast toast = Toast.makeText(this, "Text gesendet", Toast.LENGTH_SHORT);
        toast.show();
        editText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_GETTEXT) {
            String text = data.getStringExtra(INTENT_STRING_RESULT);
            editText.setText(text);
        }
    }
}

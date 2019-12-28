package com.example.iot;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String lblVal;
    Handler handler=new Handler();
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.lbl);
        handler.post(update);
    }

    Runnable update=new Runnable(){
        public void run() {Thread thrd = thr();
            thrd.start();
            try{
                thrd.join();
            } catch (Exception ex) {
                Log.d("",ex.getMessage());
            }
            mTextView.setText(lblVal);
            handler.postDelayed(this, 500);
        }
    };

    public Thread thr(){
        return new Thread() {
            @Override
            public void run() {
                connect();
            }
        };
    }

    public void connect() {
        SyncHttpClient client = new SyncHttpClient();

        client.get("https://api.thingspeak.com/channels/947424/fields/field1/last?api_key=AEYFL163IH57ZV06", null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    lblVal = new String(responseBody, "UTF-8");
                    Log.d("",new String(responseBody, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.d("",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.d("",new String(responseBody, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.d("",e.getMessage());
                }
            }
        });
    }
}

package com.example.iot;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String lblVal;
    Handler handler=new Handler(Looper.getMainLooper());
    TextView mTextView;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.image);
        img.setImageResource(R.drawable.celsius);
        mTextView=findViewById(R.id.lbl);
        handler.post(update);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    Runnable update = new Runnable() {
        @Override
        public void run() {
            connect();
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
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://api.thingspeak.com/channels/947424/fields/field1/last?api_key=AEYFL163IH57ZV06", null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    lblVal = new String(responseBody, "UTF-8");
                    Log.d("",new String(responseBody, "UTF-8"));
                } catch (Exception e) {
                    Log.d("",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.d("",new String(responseBody, "UTF-8"));
                } catch (Exception e) {
                    Log.d("",e.getMessage());
                }
            }
        });
    }
}

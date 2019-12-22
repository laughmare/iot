package com.example.iot;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    String lblVal;
    Handler handler=new Handler();
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=(TextView)findViewById(R.id.lbl);
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
            handler.postDelayed(this, 5000);
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
        String url = "jdbc:jtds:sqlserver://iot39536.database.windows.net:1433;databasename=IOT;user=laughmare@iot39536;password=..2707Ts;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        String driver = "net.sourceforge.jtds.jdbc.Driver";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
            String SQL = "SELECT top(1) val FROM [dbo].[iot] order by id desc";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);


            while (rs.next()) {
                Log.d("",rs.getString("val"));
                lblVal = rs.getString("val");
            }
        } catch (Exception ex) {
            Log.d("",ex.getMessage());
        }
    }
}

package com.mozz.remoteview;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mozz.remoteview.json.RVJInstance;
import com.mozz.remoteview.json.parser.Parser;
import com.mozz.remoteview.json.parser.SyntaxTree;
import com.mozz.remoteview.json.parser.SytaxError;
import com.mozz.remoteview.json.parser.reader.StringCodeReader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.58.107.21/testLayout.xml")
                        .build();

                Response r = null;
                try {
                    r = okHttpClient.newCall(request).execute();
                    String code = r.body().string();
                    Log.d("TestDemo", code);

                    long time1 = SystemClock.uptimeMillis();
                    
                    Parser p = new Parser(new StringCodeReader(code));
                    SyntaxTree tree = p.process();

                    Log.d("TestDemo", "spend " + (SystemClock.uptimeMillis() - time1));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SytaxError sytaxError) {
                    sytaxError.printStackTrace();
                }

            }
        });

        t.start();
    }
}
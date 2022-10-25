package com.example.webapi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView txt01;
    Button btn01;
    Button btn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt01 = findViewById(R.id.txt01);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    httpRequest("http://al18011.php.xdomain.jp/json.php?id=1");
                }catch (Exception e){
                }
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    httpRequest("http://al18011.php.xdomain.jp/json.php?id=2");
                }catch (Exception e){
                }
            }
        });
    }

    private void httpRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Hoge", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String jsonStr = response.body().string();
                Log.d("Hoge", "jsonStr=" + jsonStr);

                try {
                    JSONObject json = new JSONObject(jsonStr);
                    final String status = json.getString("id");
                    final String message = json.getString("name");
                    CharSequence text = message;
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            txt01.setText(status + ',' + message);
                            //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                        }
                    });


                }catch (Exception e){
                    Log.e("Hoge", e.getMessage());
                }
            }
        });
    }
}
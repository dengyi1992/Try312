package com.dy.try312;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    final String URLbase = "http://120.27.41.245:3000/";
    private EditText mIfEditText;
    private EditText mTableEditText;
    private EditText mToutiaonumEditText;
    private EditText mPageEditText;
    private Button mButtonButton;
    HttpURLConnection urlConn = null;
    private String toutiaourl;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIfEditText = (EditText) findViewById(R.id.et_if);
        mTableEditText = (EditText) findViewById(R.id.et_table);
        mToutiaonumEditText = (EditText) findViewById(R.id.et_toutiaonum);
        mPageEditText = (EditText) findViewById(R.id.et_page);
        mButtonButton = (Button) findViewById(R.id.button);
        mButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mif = mIfEditText.getText().toString();
                String mta = mTableEditText.getText().toString();
                String mpa = mPageEditText.getText().toString();
                String mtn = mToutiaonumEditText.getText().toString();

                if (TextUtils.isEmpty(mif)||TextUtils.isEmpty(mta)||TextUtils.isEmpty(mpa)||TextUtils.isEmpty(mtn))
                {
                    Toast.makeText(MainActivity.this,"sorry",Toast.LENGTH_SHORT).show();
                }else {
                    toutiaourl=URLbase+mif+"?"+"toutiaonum="+mtn+"&pagenum="+mpa+"&tablename="+mta;
                }
                HttpURL();
            }
        });


    }

    private void HttpURL() {
        new Thread() {
            public void run() {
                HttpURLConnection_Get();
            }
        }.start();
    }

    private void HttpURLConnection_Get() {
        try {
//            isPost = false;
            //通过openConnection 连接
            url =new URL(toutiaourl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            if (urlConnection.getResponseCode()==200){
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while ((len = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,len);
                }
                inputStream.close();
                byteArrayOutputStream.close();
                String result = new String(byteArrayOutputStream.toByteArray());
                System.out.println(result);
            }else {
                System.out.println("链接失败");
            }
        } catch (Exception e) {

        }
    }

}

package com.example.network;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.network.net.OkHttpUtils;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    String url="http://www.weather.com.cn/data/sk/101210101.html";
    private Button mbtn;
    private TextView mTv;
    private Handler handler=new Handler(Looper.getMainLooper());
    String ress="{\"weatherinfo\":{\"city\":\"杭州\",\"cityid\":\"101210101\",\"temp\":\"24.8\",\"WD\":\"东北风\",\"WS\":\"小于3级\",\"SD\":\"81%\",\"AP\":\"1000.3hPa\",\"njd\":\"暂无实况\",\"WSE\":\"<3\",\"time\":\"17:50\",\"sm\":\"2.1\",\"isRadar\":\"1\",\"Radar\":\"JC_RADAR_AZ9571_JB\"}}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initView();
        initEvent();
    }
    public void initEvent(){
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"ready0",Toast.LENGTH_LONG).show();
                mTv.setText("to be start thread");
                new Thread(){
                    @Override
                    public void run(){
                        try {
//                              System.out.println(ress);
                            String contextt=OkHttpUtils.getInstance().doGet(url);
//                            StrtoJson(contextt);
//                            System.out.println("cnmsb "+contextt);
//                            mTv.setText(contextt);
                              StrtoJson(ress);
                            //下两处toast无权限更改UI
//                            Toast.makeText(MainActivity.this,contextt,Toast.LENGTH_LONG).show();
//                            Toast.makeText(MainActivity.this,"ready1",Toast.LENGTH_LONG).show();
                            //以下post代码无效
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"start",Toast.LENGTH_LONG).show();
//                                    mTv.setText("contexttt:"+contextt);
//                                    Toast.makeText(MainActivity.this,contextt, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
//                mTv.setText("to be start thread");
//                mTv.setText("finish thread");
            }
        });
    }

    public void initView(){
        mbtn=findViewById(R.id.btn);
        mTv=findViewById(R.id.tv);
    }

    public void StrtoJson(String s){
//        Gson gson=new Gson();
//        gson.toJson(s);
        Object weatherinfo = JSON.parseObject(s).getString("weatherinfo");
        // 城市 温度 风力 风向 湿度
        String city=JSON.parseObject(weatherinfo.toString()).getString("city");
        String temp=JSON.parseObject(weatherinfo.toString()).getString("city");
        String ws=JSON.parseObject(weatherinfo.toString()).getString("WS");
        String wd=JSON.parseObject(weatherinfo.toString()).getString("WD");
        String sd=JSON.parseObject(weatherinfo.toString()).getString("SD");
        mTv.setText(String.format("城市：%s\n温度：%s\n风向：%s\n风力：%s\n湿度：%s\n",
                city,temp,wd,ws,sd
                ));
//        System.out.println("string:"+weatherinfo);
//        System.out.println("city:"+city);
//        System.out.println("gson:"+gson);

    }

}

// Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

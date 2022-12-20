package com.example.network;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.example.network.net.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    String url="http://www.weather.com.cn/data/sk/101210101.html";
    private Button mbtn;
    private TextView mTv;

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
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            String contextt=OkHttpUtils.getInstance().doGet(url);
                            StrtoJson(contextt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    public void initView(){
        mbtn=findViewById(R.id.btn);
        mTv=findViewById(R.id.tv);
    }

    public void StrtoJson(String s){
        Object weatherinfo = JSON.parseObject(s).getString("weatherinfo");
        // 城市 温度 风力 风向 湿度
        String city=JSON.parseObject(weatherinfo.toString()).getString("city");
        String temp=JSON.parseObject(weatherinfo.toString()).getString("temp");
        String ws=JSON.parseObject(weatherinfo.toString()).getString("WS");
        String wd=JSON.parseObject(weatherinfo.toString()).getString("WD");
        String sd=JSON.parseObject(weatherinfo.toString()).getString("SD");
        mTv.setText(String.format("城市：%s\n温度：%s\n风向：%s\n风力：%s\n湿度：%s\n",
                city,temp+"°",wd,ws,sd
                ));
    }
}

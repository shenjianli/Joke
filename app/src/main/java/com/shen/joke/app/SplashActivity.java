package com.shen.joke.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.shen.joke.R;
import com.shen.joke.core.CountTimer;
import com.shen.netclient.util.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity implements CountTimer.CountDownTimerListener {


    @Bind(R.id.joke_enter_time_tv)
    TextView jokeEnterTimeTv;

    private CountTimer countTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        countTimer = new CountTimer(6 * 1000, 1000);
        countTimer.setCountDownTimerListener(this);
        countTimer.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void updateCountDownTime(String day, String hour, String min, String second) {
        LogUtils.i("倒计时：" + day + ":" + hour + ":" + min + ":" + second);
        if (null != jokeEnterTimeTv) {
            jokeEnterTimeTv.setText(second);
        }
        if( TextUtils.equals("1",second)){
            enterMainPage();
        }
    }

    private void enterMainPage() {
        if(null != countTimer){
            countTimer.setCountDownTimerListener(null);
            countTimer.cancel();
        }
        Intent intent = new Intent(this,JokeCardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void countDownTimerFinish() {

    }

    @OnClick(R.id.joke_enter_time_tv)
    public void onViewClicked() {
        enterMainPage();
    }
}

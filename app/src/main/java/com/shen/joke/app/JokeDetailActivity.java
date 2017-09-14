package com.shen.joke.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shen.joke.R;
import com.shen.joke.model.Joke;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JokeDetailActivity extends AppCompatActivity {

    @Bind(R.id.joke_last)
    ImageView mJokeLast;
    @Bind(R.id.info)
    ImageView mInfo;
    @Bind(R.id.joke_next)
    ImageView mJokeNext;
    @Bind(R.id.joke_detail_content)
    TextView mJokeDetailContent;
    @Bind(R.id.joke_id)
    TextView mJokeId;


    private int postion;
    private List<Joke> jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if(null != intent){
            String jokeId = intent.getStringExtra("joke_id");
            jokes = JokeDataManager.getKeepDataManager().getJokeList();
            if(null != jokes && jokes.size() > 0 ){
                Joke joke;
                for (int i = 0 ;i < jokes.size() ;i ++){
                     joke = jokes.get(i);
                    if(TextUtils.equals(jokeId,joke.getId().toString())){
                        mJokeDetailContent.setText(joke.getContent());
                        postion = i;
//                        if(postion == 0){
//                            mJokeLast.setVisibility(View.INVISIBLE);
//                        }
//                        if(postion == (jokes.size() - 1)){
//                            mJokeNext.setVisibility(View.INVISIBLE);
//                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.joke_last, R.id.info, R.id.joke_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joke_last:
                postion --;
                if(postion == -1){
                    postion = jokes.size() -1;
                }
                Joke joke = jokes.get(postion);
                if(null != joke){
                    mJokeDetailContent.setText(joke.getContent());
                    mJokeId.setText(joke.getId().toString());
                }
//                if(postion == 0){
//                    mJokeLast.setVisibility(View.INVISIBLE);
//                }
//                if(postion == (jokes.size() - 2)){
//                    mJokeNext.setVisibility(View.VISIBLE);
//                }
                break;
            case R.id.info:
                break;
            case R.id.joke_next:
                postion ++;
                if(postion == jokes.size()){
                    postion = 0;
                }
                Joke joke1 = jokes.get(postion);
                if(null != joke1){
                    mJokeId.setText(joke1.getId().toString());
                    mJokeDetailContent.setText(joke1.getContent());
                }
//                if(postion == (jokes.size()-1)){
//                    mJokeNext.setVisibility(View.INVISIBLE);
//                }
//                if(postion == 1){
//                    mJokeLast.setVisibility(View.VISIBLE);
//                }
                break;
        }
    }
}

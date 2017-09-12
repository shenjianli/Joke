package com.shen.joke.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.shen.joke.R;
import com.shen.joke.app.adapter.JokeAdapter;
import com.shen.joke.app.db.JokeDao;
import com.shen.joke.engine.JokePresenter;
import com.shen.joke.engine.JokeView;
import com.shen.joke.model.Joke;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class MainActivity extends AppCompatActivity implements JokeView{

    @Bind(R.id.test)
    Button test;
    @Bind(R.id.joke_rv)
    RecyclerView jokeRv;

    private JokePresenter jokePresenter;
    private JokeAdapter jokeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        jokePresenter = new JokePresenter(this);
        jokePresenter.attachView(this);

        jokeAdapter = new JokeAdapter(this);

        JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
        List<Joke> jokes = jokeDao.queryBuilder().list();
        jokeAdapter.fillList(jokes);


        jokeRv.setLayoutManager(new LinearLayoutManager(this));
        jokeRv.setAdapter(jokeAdapter);

        JokeDataManager.getKeepDataManager().startUpdateData();
    }

    @OnClick(R.id.test)
    public void onClick(View view){

        //jokePresenter.loadQuoteInfoFromNet();
        Intent intent = new Intent(this,JokeCardActivity.class);
        startActivity(intent);
//        JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
//        List<Joke> jokes = jokeDao.queryBuilder().list();
//        jokeAdapter.fillList(jokes);
//        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        jokePresenter.detachView();
    }


    @Override
    public void startLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void updateJokeView(List<Joke> datas) {
        if(null != datas && datas.size() > 0){
            jokeAdapter.fillList(datas);
            jokeAdapter.notifyDataSetChanged();
        }
    }
}

package com.shen.joke.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shen.joke.R;
import com.shen.joke.app.adapter.CardJokeAdapter;
import com.shen.joke.app.db.JokeDao;
import com.shen.joke.app.flingswipe.SwipeFlingAdapterView;
import com.shen.joke.model.Joke;
import com.shen.netclient.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JokeCardActivity extends AppCompatActivity {


    @Bind(R.id.left)
    ImageView left;
    @Bind(R.id.info)
    ImageView info;
    @Bind(R.id.right)
    ImageView right;
    @Bind(R.id.frame)
    SwipeFlingAdapterView frame;

    private ArrayList<Joke> jokes = new ArrayList<>();
    private CardJokeAdapter adapter;
    private JokeDao jokeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        ButterKnife.bind(this);

        jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();

        jokes.addAll(JokeDataManager.getKeepDataManager().getJokeList());

        adapter = new CardJokeAdapter(this, jokes);

        frame.setAdapter(adapter);
        frame.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                jokes.remove(0);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onLeftCardExit(Object dataObject) {
                //makeToast(JokeCardActivity.this, "不喜欢");
                if(dataObject instanceof Joke){
                    Joke joke = (Joke)dataObject;
                    joke.setNum(joke.getNum() + 1);
                    jokeDao.update(joke);
                    LogUtils.i(joke.toString());
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //makeToast(JokeCardActivity.this, "喜欢");
                if(dataObject instanceof Joke){
                    Joke joke = (Joke)dataObject;
                    joke.setNum(joke.getNum() + 1);
                    jokeDao.update(joke);
                    LogUtils.i(joke.toString());
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
                List<Joke> jokeLists = jokeDao.queryBuilder().list();
                jokes.addAll(jokeLists);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = frame.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        frame.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(JokeCardActivity.this, "点击图片");
                if(dataObject instanceof Joke){
                    Joke joke = (Joke) dataObject;
                    Intent intent = new Intent(JokeCardActivity.this,JokeDetailActivity.class);
                    intent.putExtra("joke_id",String.valueOf(joke.getId()));
                    startActivity(intent);
                }
            }
        });
    }


    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.left, R.id.info, R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left:
                frame.getTopCardListener().selectLeft();
                break;
            case R.id.info:
                break;
            case R.id.right:
                frame.getTopCardListener().selectRight();
                break;
        }
    }
}

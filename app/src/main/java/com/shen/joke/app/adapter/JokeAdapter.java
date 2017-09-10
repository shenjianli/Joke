package com.shen.joke.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.shen.joke.R;
import com.shen.joke.app.holder.JokeHolder;
import com.shen.joke.core.adapter.BaseAdapter;
import com.shen.joke.model.Joke;


import java.util.List;

/**
 * Created by shenjianli on 17/5/20.
 */
public class JokeAdapter extends BaseAdapter<Joke,JokeHolder> {

    public JokeAdapter(Context context) {
        super(context);
    }

    public JokeAdapter(Context context, List<Joke> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public JokeHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new JokeHolder(parent, R.layout.item_joke_layout);
    }

    @Override
    public void bindCustomViewHolder(JokeHolder holder, int position) {
        Joke quote = getItem(position);
        if(null != quote){
            if(null != holder){
                if(position % 2 == 0){
                    holder.jokeItemLayout.setBackgroundColor(Color.parseColor("#e67e22"));
                }
                else {
                    holder.jokeItemLayout.setBackgroundColor(Color.parseColor("#9b59b6"));
                }

                holder.jokeIdTv.setText(String.valueOf(quote.getId()));

                holder.jokeContentTv.setText(quote.getContent());
                holder.jokeDateTv.setText(quote.getDate());
            }
        }

    }
}

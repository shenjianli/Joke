package com.shen.joke.app.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.joke.R;
import com.shen.joke.core.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 17/5/20.
 */
public class JokeHolder extends BaseHolder {


    @Bind(R.id.joke_id_tv)
    public TextView jokeIdTv;
    @Bind(R.id.joke_content_tv)
    public TextView jokeContentTv;
    @Bind(R.id.joke_date_tv)
    public TextView jokeDateTv;
    @Bind(R.id.joke_item_layout)
    public LinearLayout jokeItemLayout;

    public JokeHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public JokeHolder(View view) {
        super(view);
    }

}

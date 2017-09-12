package com.shen.joke.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shen.joke.R;
import com.shen.joke.app.CardMode;
import com.shen.joke.model.Joke;

import java.util.List;

/**
 * Created by Shall on 2015-06-23.
 */
public class CardJokeAdapter extends BaseAdapter {

    private Context mContext;
    private List<Joke> mCardList;

    public CardJokeAdapter(Context mContext, List<Joke> mCardList) {
        this.mContext = mContext;
        this.mCardList = mCardList;
    }

    @Override
    public int getCount() {
        return mCardList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_card_layout, parent, false);
            holder = new ViewHolder();

            holder.jokeContentTV = (TextView) convertView.findViewById(R.id.joke_content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


//        Glide.with(mContext)
//                .load(mCardList.get(position).getImages().get(0))
//                .into(holder.mCardImageView);

        holder.jokeContentTV.setText(mCardList.get(position).getContent());

        return convertView;
    }

    class ViewHolder {
        TextView jokeContentTV;
    }
}

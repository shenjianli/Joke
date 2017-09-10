package com.shen.joke.engine;


import com.shen.joke.core.base.MvpView;
import com.shen.joke.model.Joke;

import java.util.List;


public interface JokeView extends MvpView {

    void updateJokeView(List<Joke> data);
}

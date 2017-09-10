package com.shen.joke.app;

import com.shen.joke.engine.JokePresenter;

/**
 * Created by edianzu on 2017/5/31.
 */
public class JokeDataManager {

    private static final JokeDataManager keepDataManager = new JokeDataManager();

    private JokeDataManager(){
    }

    public static JokeDataManager getKeepDataManager(){
        return keepDataManager;
    }

    public void startUpdateData(){

        JokePresenter quotePresenter = new JokePresenter(JokeApp.getAppInstance());
        quotePresenter.loadQuoteInfoFromNet();
    }

}

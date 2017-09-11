package com.shen.joke.app;

import com.shen.joke.engine.JokePresenter;

import java.text.SimpleDateFormat;

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
//        HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
//        if (null != historyDao) {
//            List<History> update = historyDao.queryBuilder().list();
//        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sDateFormat.format(new java.util.Date());
        quotePresenter.updateJokeInfo(nowDate);
    }

}

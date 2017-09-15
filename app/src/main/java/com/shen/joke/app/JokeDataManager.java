package com.shen.joke.app;

import android.text.TextUtils;

import com.shen.joke.app.db.HistoryDao;
import com.shen.joke.app.db.JokeDao;
import com.shen.joke.core.util.SharedPreUtils;
import com.shen.joke.engine.JokePresenter;
import com.shen.joke.model.History;
import com.shen.joke.model.Joke;
import com.shen.netclient.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edianzu on 2017/5/31.
 */
public class JokeDataManager {

    private static final JokeDataManager keepDataManager = new JokeDataManager();

    private JokeDataManager(){
        init();
    }

    private void init() {
        if(null == jokes){
            jokes = new ArrayList<>();
        }
        JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
        List<Joke> jokeLists = jokeDao.queryBuilder().orderAsc(JokeDao.Properties.Num).orderDesc(JokeDao.Properties.Id).where(JokeDao.Properties.Num.eq(0)).list();
        jokes.addAll(jokeLists);
    }

    public static JokeDataManager getKeepDataManager(){
        return keepDataManager;
    }

    private List<Joke> jokes;
    private int jokeNums;

    public int getJokeNum() {
        return jokeNums;
    }

    public List<Joke> getJokeList(){
        if(null == jokes || jokes.size() <=0){
            init();
        }
        return  jokes;
    }

    public void startUpdateData(){


        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sDateFormat.format(new java.util.Date());

        String lastUpdateDate = SharedPreUtils.get(JokeApp.getAppInstance(),Constant.DATA_UPDATE_DATE,"");
        if(!TextUtils.isEmpty(lastUpdateDate) && TextUtils.equals(lastUpdateDate,nowDate)){
            LogUtils.i("已经更新过了" + lastUpdateDate);
            return ;
        }
        else if(TextUtils.isEmpty(lastUpdateDate)){

            HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
            if (null != historyDao) {
                List<History> update = historyDao.queryBuilder().list();
                for(History history : update){
                    LogUtils.i( "更新历史：" + history.getUpDate());
                    String updateDate = history.getUpDate();
                    if(TextUtils.equals(updateDate,nowDate)){
                        LogUtils.i(nowDate + " 已经更新过了");
                        return;
                    }
                }
            }

        }
        LogUtils.i("请求的日期为：" + nowDate);
        JokePresenter quotePresenter = new JokePresenter(JokeApp.getAppInstance());
        quotePresenter.updateJokeInfo(nowDate);
    }

    public void startUpdateJokeData(){


        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sDateFormat.format(new java.util.Date());

        String lastUpdateDate = SharedPreUtils.get(JokeApp.getAppInstance(),Constant.DATA_UPDATE_DATE,"");
        if(!TextUtils.isEmpty(lastUpdateDate) && TextUtils.equals(lastUpdateDate,nowDate)){
            LogUtils.i("今天已经更新过了" + lastUpdateDate);
            return ;
        }
        else if(TextUtils.isEmpty(lastUpdateDate)){

            HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
            if (null != historyDao) {
                List<History> update = historyDao.queryBuilder().orderDesc(HistoryDao.Properties.Id).list();
                for(History history : update){
                    LogUtils.i( "更新历史：" + history.getUpDate());
                    String historyDate = history.getHisDate();
                    if(TextUtils.equals(historyDate,nowDate)){
                        LogUtils.i(nowDate + " 已经更新过了");
                        return;
                    }
                }
            }

        }

        String lastId = SharedPreUtils.get(JokeApp.getAppInstance(),Constant.DATA_UPDATE_ID,"0");
        if(TextUtils.equals(lastId,"0")){
            HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
            if (null != historyDao) {
                List<History> update = historyDao.queryBuilder().orderDesc(HistoryDao.Properties.Id).list();
                if(null != update && update.size() > 0){
                    History lastHis = update.get(0);
                    lastId = lastHis.getUpdateId();
                }
            }
        }

        LogUtils.i("请求的日期为：" + nowDate);
        JokePresenter quotePresenter = new JokePresenter(JokeApp.getAppInstance());
        quotePresenter.updateJokeDataInfo(lastId,"10");
    }

    public void updateJokeList(){
        if(null == jokes){
            jokes = new ArrayList<>();
        }
        jokes.clear();
        JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
        List<Joke> jokeLists = jokeDao.queryBuilder().orderAsc(JokeDao.Properties.Num).orderDesc(JokeDao.Properties.Id).where(JokeDao.Properties.Num.eq(0)).list();
        jokes.addAll(jokeLists);
    }
}

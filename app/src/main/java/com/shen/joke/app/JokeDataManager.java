package com.shen.joke.app;

import android.text.TextUtils;

import com.shen.joke.app.db.HistoryDao;
import com.shen.joke.core.util.SharedPreUtils;
import com.shen.joke.engine.JokePresenter;
import com.shen.joke.model.History;
import com.shen.netclient.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.List;

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


        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sDateFormat.format(new java.util.Date());

        String lastUpdateDate = SharedPreUtils.get(JokeApp.getAppInstance(),Constant.DATA_UPDATE_DATE,"");
        if(!TextUtils.isEmpty(lastUpdateDate) && TextUtils.equals(lastUpdateDate,nowDate)){
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

}

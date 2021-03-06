package com.shen.joke.engine;

import android.content.Context;
import android.text.TextUtils;

import com.shen.joke.app.Constant;
import com.shen.joke.app.JokeApp;
import com.shen.joke.app.JokeDataManager;
import com.shen.joke.app.db.HistoryDao;
import com.shen.joke.app.db.JokeDao;
import com.shen.joke.core.HttpResultFunc;
import com.shen.joke.core.api.JokeApi;
import com.shen.joke.core.base.BasePresenter;
import com.shen.joke.core.util.SharedPreUtils;
import com.shen.joke.model.History;
import com.shen.joke.model.Joke;
import com.shen.netclient.NetClient;
import com.shen.netclient.util.LogUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/9/12.
 */
public class JokePresenter extends BasePresenter<JokeView> {

    Context context;
    private Subscriber<List<Joke>> jokeSubscriber;

    public JokePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(JokeView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        jokeSubscriber = null;
    }

    public void loadJokeInfo() {

        if (null != mMvpView) {
            JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
            if (null != jokeDao) {
                LogUtils.i("加载数据" +
                        jokeDao.count() +
                        jokeDao.getAllColumns() +
                        jokeDao.getDatabase() +
                        jokeDao.getTablename() +
                        jokeDao.loadAll() +
                        jokeDao.queryBuilder());

                QueryBuilder<Joke> qb = jokeDao.queryBuilder().orderDesc(JokeDao.Properties.Id);
                List<Joke> list = qb.list();
                if (null != list && list.size() > 0) {
                    mMvpView.updateJokeView(list);
                    return;
                }
            }
            mMvpView.updateJokeView(null);
        }
    }

    public void loadQuoteInfoFromNet() {

        if (null != mMvpView) {
            jokeSubscriber = new Subscriber<List<Joke>>() {
                @Override
                public void onCompleted() {
                    LogUtils.i("表求完成");
                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.i(e.getStackTrace().toString());
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }

                @Override
                public void onNext(List<Joke> jokes) {
                    LogUtils.i("获取数据成功");
                    mMvpView.updateJokeView(jokes);
                    mMvpView.hideLoading();
                }
            };
            JokeApi quoteApi = NetClient.retrofit().create(JokeApi.class);
            quoteApi.queryJokeInfo().map(new HttpResultFunc<List<Joke>>())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jokeSubscriber);
        }
    }


    //
    public void updateJokeDataInfo(String lastId, String num) {


        Subscriber<Boolean> jokeSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                LogUtils.i("表求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(e.getStackTrace().toString());
                if(null != mMvpView){
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onNext(Boolean success) {
                LogUtils.i("获取数据成功" + success);
                Thread thread = Thread.currentThread();
                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                if(success){
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String nowDate = sDateFormat.format(new java.util.Date());
                    String lastDate = SharedPreUtils.get(context,Constant.DATA_UPDATE_DATE,nowDate);
                    if(TextUtils.equals(nowDate,lastDate)){
                        JokeDataManager.getKeepDataManager().updateJokeList();
                    }
                }
                if(null != mMvpView){
                    mMvpView.hideLoading();
                }
            }
        };
        JokeApi jokeApi = NetClient.retrofit().create(JokeApi.class);
        jokeApi.updateJokeBaseInfo(lastId,num).map(new HttpResultFunc<List<Joke>>())
                .map(new Func1<List<Joke>, Boolean>() {
                    @Override
                    public Boolean call(List<Joke> jokes) {
                        if (null != jokes && jokes.size() > 0) {
                            JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
                            if (null != jokeDao) {

                                for (Joke joke:jokes) {
                                    LogUtils.i("收到的笑话数据：" + joke.toString());
                                    jokeDao.insertWithoutSettingPk(joke);
                                }

                                Joke update = jokes.get(0);
                                long updateId = update.getId();
                                SharedPreUtils.put(context,Constant.DATA_UPDATE_ID,String.valueOf(updateId));

                                HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
                                History history = new History();
                                history.setUpDate(update.getDate());
                                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String nowDate = sDateFormat.format(new java.util.Date());
                                history.setHisDate(nowDate);
                                historyDao.insertWithoutSettingPk(history);
                                SharedPreUtils.put(context, Constant.DATA_UPDATE_DATE,nowDate);


                                Thread thread = Thread.currentThread();
                                LogUtils.i("当前线程名：" + thread.getName());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jokeSubscriber);

    }

    private void updateJokeBaseInfo( String num) {


        Subscriber<Boolean> jokeSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                LogUtils.i("表求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(e.getStackTrace().toString());
                if(null != mMvpView){
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onNext(Boolean data) {
                LogUtils.i("获取数据成功");
                Thread thread = Thread.currentThread();
                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                if(null != mMvpView){
                    mMvpView.hideLoading();
                }
            }
        };
        JokeApi jokeApi = NetClient.retrofit().create(JokeApi.class);
        jokeApi.queryJokeBaseInfo(num).map(new HttpResultFunc<List<Joke>>())
                .map(new Func1<List<Joke>, Boolean>() {
                    @Override
                    public Boolean call(List<Joke> jokes) {
                        if (null != jokes && jokes.size() > 0) {
                            JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
                            if (null != jokeDao) {

                                for (Joke joke:jokes) {
                                    LogUtils.i("收到的笑话数据：" + joke.toString());
                                    jokeDao.insertWithoutSettingPk(joke);
                                }
                                Thread thread = Thread.currentThread();
                                LogUtils.i("当前线程名：" + thread.getName());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jokeSubscriber);

    }

    public void updateJokeInfo(final String date) {


        Subscriber<Boolean> jokeSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                LogUtils.i("表求完成");

//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                Calendar c = Calendar.getInstance();
//                c.add(Calendar.DAY_OF_MONTH, -6);
//                String jokeDate = sf.format(c.getTime());
//                System.out.println("前6天日期"+ sf.format(c.getTime()));
//                updateJokeInfo(jokeDate);


                String lastUpdateDate = SharedPreUtils.get(JokeApp.getAppInstance(),Constant.DATA_UPDATE_DATE,"");
                if(TextUtils.isEmpty(lastUpdateDate)){

                    HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
                    if (null != historyDao) {
                        List<History> update = historyDao.queryBuilder().list();
                        if(null != update && update.size() > 0){
                            return ;
                        }
                    }
                    updateJokeBaseInfo("100");
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(e.getStackTrace().toString());
                if(null != mMvpView){
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onNext(Boolean data) {
                LogUtils.i("获取数据成功");
                Thread thread = Thread.currentThread();
                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                if(null != mMvpView){
                    mMvpView.hideLoading();
                }
            }
        };
        JokeApi jokeApi = NetClient.retrofit().create(JokeApi.class);
        jokeApi.queryJokeInfoByDate(date).map(new HttpResultFunc<List<Joke>>())
                .map(new Func1<List<Joke>, Boolean>() {
                    @Override
                    public Boolean call(List<Joke> jokes) {
                        if (null != jokes && jokes.size() > 0) {
                            JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
                            if (null != jokeDao) {

                                for (Joke joke:jokes) {
                                    LogUtils.i("收到的笑话数据：" + joke.toString());
                                    jokeDao.insertWithoutSettingPk(joke);
                                }

                                SharedPreUtils.put(context, Constant.DATA_UPDATE_DATE,date);

                                HistoryDao historyDao = JokeApp.getAppInstance().getDaoSession().getHistoryDao();
                                History history = new History();
                                history.setUpDate(date);
                                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                String nowDate = sDateFormat.format(new java.util.Date());
                                history.setHisDate(nowDate);
                                historyDao.insertWithoutSettingPk(history);


                                Thread thread = Thread.currentThread();
                                LogUtils.i("当前线程名：" + thread.getName());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jokeSubscriber);

    }


    public void updateJokeInfoNew() {


        Subscriber<Boolean> jokeSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                LogUtils.i("表求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(e.getStackTrace().toString());
                if(null != mMvpView){
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onNext(Boolean data) {
                LogUtils.i("获取数据成功");
                Thread thread = Thread.currentThread();
                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                if(null != mMvpView){
                    mMvpView.hideLoading();
                }
            }
        };
        JokeApi jokeApi = NetClient.retrofit().create(JokeApi.class);
        jokeApi.queryJokeInfo().map(new HttpResultFunc<List<Joke>>())
                .map(new Func1<List<Joke>, Boolean>() {
                    @Override
                    public Boolean call(List<Joke> jokeData) {
                        if (null != jokeData) {
                            LogUtils.i("从网络上接收到数据" + jokeData.toString());
                            JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
                            if (null != jokeDao) {
                                for (Joke joke:jokeData) {
                                    LogUtils.i("收到的笑话数据：" + joke.toString());
                                    jokeDao.insertWithoutSettingPk(joke);
                                }
                                Thread thread = Thread.currentThread();
                                LogUtils.i("当前线程名：" + thread.getName());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jokeSubscriber);

    }

    /**
     * 查询用户列表
     */
    public List<Joke> queryUserList() {

        JokeDao JokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
        QueryBuilder<Joke> qb = JokeDao.queryBuilder();
        List<Joke> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<Joke> queryUserList(String site) {
        JokeDao jokeDao = JokeApp.getAppInstance().getDaoSession().getJokeDao();
        QueryBuilder<Joke> qb = jokeDao.queryBuilder();
        qb.where(JokeDao.Properties.Site.gt(site)).orderAsc(JokeDao.Properties.Id);
        List<Joke> list = qb.list();

        return list;
    }
}

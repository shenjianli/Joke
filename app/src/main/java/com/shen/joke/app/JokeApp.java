package com.shen.joke.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.shen.joke.BuildConfig;
import com.shen.joke.app.db.DaoMaster;
import com.shen.joke.app.db.DaoSession;
import com.shen.netclient.NetClient;
import com.shen.netclient.engine.NetClientLib;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.rx.RxTransaction;

/**
 * Created by ljq on 2017/9/9.
 */

public class JokeApp extends Application{

    private static JokeApp jokeApp;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        jokeApp = this;
        initNetClient();
        initGreenDao();
        initStetho();
        initMemLeak();
        initByGradleFile();
    }

    private void initNetClient() {
        NetClientLib.getLibInstance().setMobileContext(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
        NetClient.addNetworkInterceptor(new StethoInterceptor());
    }

    /*
    根据主项目中的gradle配置文件开初始化不同的开发模式
   */
    private void initByGradleFile() {
        NetClientLib.getLibInstance().setLogEnable(BuildConfig.logEnable);
        NetClientLib.getLibInstance().setServerBaseUrl(BuildConfig.serverUrl);
    }

    private void initMemLeak() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initGreenDao() {

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "joke-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static JokeApp getAppInstance() {
        return jokeApp;
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}

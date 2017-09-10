package com.shen.joke.app.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.shen.joke.model.Joke;
import com.shen.joke.model.User;

import com.shen.joke.app.db.JokeDao;
import com.shen.joke.app.db.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig jokeDaoConfig;
    private final DaoConfig userDaoConfig;

    private final JokeDao jokeDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        jokeDaoConfig = daoConfigMap.get(JokeDao.class).clone();
        jokeDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        jokeDao = new JokeDao(jokeDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Joke.class, jokeDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        jokeDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public JokeDao getJokeDao() {
        return jokeDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}

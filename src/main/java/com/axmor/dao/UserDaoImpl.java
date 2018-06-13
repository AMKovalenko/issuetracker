package com.axmor.dao;

import com.axmor.entity.User;
import com.axmor.util.DBService;

import com.axmor.util.LoggingUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao{

    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User getUserByUsername(String name) throws Exception {

        LoggingUtils.startMethodDebug(logger, "getUserByUsername", name);

        Query query = DBService.getSessionFactory().getCurrentSession().createQuery("from User where username = :username");
        query.setParameter("username", name);
        List<User> userList = query.list();
        if (userList == null || userList.size() == 0){
            logger.debug("There is no one registered user for given username - " + name);
            return null;
        }else {
            LoggingUtils.finishMethodDebug(logger, "getUserByUsername", userList.get(0));
            return userList.get(0);
        }
    }

    @Override
    public Integer createUser(User user) throws Exception {

        LoggingUtils.startMethodDebug(logger, "createUser", user.toString());

        Integer userId = (Integer) DBService.getSessionFactory().getCurrentSession().save(user);
        LoggingUtils.finishMethodDebug(logger, "createUser", "New user has been saved with ID = " + userId);
        return userId;
    }


}

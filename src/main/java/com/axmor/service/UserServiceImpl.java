package com.axmor.service;

import com.axmor.dao.UserDaoImpl;
import com.axmor.util.DBService;
import com.axmor.dao.UserDao;
import com.axmor.entity.User;
import com.axmor.util.LoggingUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;

public class UserServiceImpl implements UserService{

    private UserDao userDao = new UserDaoImpl();

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public boolean authenticateUser(String username, String password) throws Exception {

        LoggingUtils.startMethodDebug(logger, "authenticateUser", String.format("Username - %s, password - %s", username, password));

        checkUserParams(username, password);
        Transaction transaction = null;
        User user;

        try {
            transaction = DBService.getTransaction();
            user = userDao.getUserByUsername(username);
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to get user by username [ %s ] due to database connection problem.\n", username);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("Failed attempt to get user by username [ %s ]. There is no one user with given username in the database.", username);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to get user by username [ %s ].", username);
            logger.error(message, e);
            throw new Exception(message);
        }

        if (user == null){
            return Boolean.FALSE;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        boolean isUserAuthenticated = hashedPassword.equals(user.getHashedPassword());

        LoggingUtils.finishMethodDebug(logger, "authenticateUser", isUserAuthenticated);

        return isUserAuthenticated;
    }

    @Override
    public Integer createUser(String username, String password) throws Exception {

        LoggingUtils.startMethodDebug(logger, "createUser", String.format("Username - %s, password - %s", username, password));

        checkUserParams(username, password);

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            if (userDao.getUserByUsername(username) != null){
                throw new IllegalArgumentException("User with given userName [" + username + "] already exist. Please, try again with another userName.");
            }
            String salt = BCrypt.gensalt();
            String hash = BCrypt.hashpw(password, salt);
            User user = new User(username, hash, salt);
            Integer id = userDao.createUser(user);
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "createUser", id);
            return id;
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to create new user with username [%s] due to database problem.\n", username);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            DBService.transactionRollback(transaction);
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            String message = String.format("Failed attempt to create new user with username [%s].\n", username);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    private void checkUserParams(String username, String password){
        if (StringUtils.isBlank(username)){
            throw new IllegalArgumentException("Given username is empty or consist of whitespaces!");
        }
        if (StringUtils.isBlank(password)){
            throw new IllegalArgumentException("Given password is empty or consist of whitespaces!");
        }

    }
}

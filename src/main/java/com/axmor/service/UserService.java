package com.axmor.service;


public interface UserService {
    /**
     * Checking if user handed over correct username and password.
     * If given parameters are correct - returns "TRUE", otherwise - "FALSE".
     *
     * @param username - username for searching user in database
     * @param password - password for checking if user, received by username has the same password
     * @throws Exception
     */
    boolean authenticateUser(String username, String password) throws Exception;

    /**
     * Method for registering (creation) a new user with given params.
     *
     * @param username - username for new user.
     * @param password - password for new user.
     * @return - created user identifier.
     * @throws Exception
     */
    Integer createUser(String username, String password) throws Exception;

}

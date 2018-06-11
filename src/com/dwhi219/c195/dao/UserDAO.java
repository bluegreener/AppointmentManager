package com.dwhi219.c195.dao;

import com.dwhi219.c195.dao.exceptions.InvalidCredentialsException;
import com.dwhi219.c195.model.User;
import javafx.collections.ObservableList;

/**
 * Interface for DAOs accessing user data
 *
 * @author Daniel White
 */
public interface UserDAO {

    User getUser(String username, String password) throws InvalidCredentialsException;

    ObservableList<User> getActiveUsers();
}

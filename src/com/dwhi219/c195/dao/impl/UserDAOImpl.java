package com.dwhi219.c195.dao.impl;

import com.dwhi219.c195.dao.DbConnectionManager;
import com.dwhi219.c195.dao.UserDAO;
import com.dwhi219.c195.dao.exceptions.InvalidCredentialsException;
import com.dwhi219.c195.model.User;
import com.dwhi219.c195.util.AccessLogger;
import com.dwhi219.c195.util.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * MySQL implementation of UserDAO interface. Provides methods for manipulating
 * user data.
 *
 * @author Daniel White
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public User getUser(String username, String password) throws InvalidCredentialsException {
        User result = new User();
        String query = "select userid, userName, password, active, createdBy, createDate, lastUpdate, lastUpdateBy from "
                + Constants.DBUSER
                + ".user where userName = ? and password = ?";

        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                AccessLogger.logAccessAttempt(username, false);
                throw new InvalidCredentialsException();
            } else {
                rs.first();
                if (rs.getInt("active") == 1) {
                    result.setUserId(rs.getInt("userid"));
                    result.setUserName(rs.getString("userName"));
                    result.setPassword(rs.getString("password"));
                    result.setActive(rs.getInt("active"));
                    result.setCreatedBy(rs.getString("createdBy"));
                    result.setCreateDate(rs.getDate("createDate").toLocalDate());
                    result.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                    result.setLastUpdateBy(rs.getString("lastUpdateBy"));
                } else {
                    AccessLogger.logAccessAttempt(username, false);
                    throw new InvalidCredentialsException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public ObservableList<User> getActiveUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "select userid, userName, password, active, createdBy, createDate, lastUpdate, lastUpdateBy "
                + "from " + Constants.DBUSER + ".user where active = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userid"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getInt("active"));
                user.setCreatedBy(rs.getString("createdBy"));
                user.setCreateDate(rs.getDate("createDate").toLocalDate());
                user.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                user.setLastUpdateBy(rs.getString("lastUpdateBy"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return users;
    }

}

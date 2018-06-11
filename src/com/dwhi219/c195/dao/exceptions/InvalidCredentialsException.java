package com.dwhi219.c195.dao.exceptions;

/**
 * Special exception for when a user provides bad credentials for logging in
 * Part of REQUIREMENT F
 *
 * @author Daniel White
 */
public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(Exception e) {
        super(e);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}

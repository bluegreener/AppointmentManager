package com.dwhi219.c195.dao;

import com.dwhi219.c195.model.Customer;
import javafx.collections.ObservableList;

/**
 * Interface for DAOs accessing customer data
 *
 * @author Daniel White
 */
public interface CustomerDAO {

    ObservableList<Customer> getActiveCustomers();

    ObservableList<Customer> getAllCustomers();

    boolean deleteCustomer(Customer cust);

    boolean addCustomer(Customer cust);

    boolean updateCustomer(Customer cust);
}

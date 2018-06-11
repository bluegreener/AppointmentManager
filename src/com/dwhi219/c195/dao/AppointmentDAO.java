package com.dwhi219.c195.dao;

import com.dwhi219.c195.model.Appointment;
import com.dwhi219.c195.model.Customer;
import com.dwhi219.c195.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 * Interface for DAOs accessing appointment data
 *
 * @author Daniel White
 */
public interface AppointmentDAO {

    ObservableList<Appointment> getAppointmentsByMonth(LocalDate date);

    ObservableList<Appointment> getAppointmentsByWeek(LocalDate date);

    ObservableList<Appointment> getAppointmentsByDay(LocalDate date);

    ObservableList<Appointment> getAppointmentsByConsultant(User con);

    ObservableList<Appointment> getAppointmentsByCustomer(Customer cust);

    ArrayList<ObservableList> getAppointmentTypesByMonth(ArrayList<ObservableList> dataList, String year);

    boolean updateAppointment(Appointment apt);

    boolean deleteAppointment(Appointment apt);

    boolean addAppointment(Appointment apt);

    ObservableList<String> getYearList();
}

package com.dwhi219.c195.view;

import com.dwhi219.c195.MainApp;
import com.dwhi219.c195.dao.CustomerDAO;
import com.dwhi219.c195.dao.impl.CustomerDAOImpl;
import com.dwhi219.c195.model.Customer;
import com.dwhi219.c195.util.AccessLogger;
import com.dwhi219.c195.util.Constants;
import com.dwhi219.c195.util.Constants.Mode;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for viewing all customers
 *
 * @author Daniel White
 */
public class CustomerViewController {

    @FXML
    private Button newCustomerButton;

    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> cityColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, Integer> activeColumn;
    @FXML
    private TableColumn<Customer, Boolean> actionColumn;

    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;

    private MainApp mainApp;
    private CustomerDAO dao;

    public CustomerViewController() {
    }

    @FXML
    public void initialize() {
        newCustomerButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.ADD_ICON))));
        logoutButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.LOGOUT_ICON))));
        dao = new CustomerDAOImpl();
        customers = dao.getAllCustomers();
        customerTable.setItems(customers);
        // REQUIREMENT G - Use lambdas to efficiently set column cell values
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddressLine1() + " " + cellData.getValue().getAddress().getAddressLine2()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCityName()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCountry().getCountry()));
        postalCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
        activeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getActive()).asObject());
        // set up active column to translate flag into a string
        activeColumn.setCellFactory(col -> new TableCell<Customer, Integer>() {
            @Override
            public void updateItem(Integer activeFlag, boolean empty) {
                super.updateItem(activeFlag, empty);
                if (empty) {
                    setText(null);
                } else {
                    if (activeFlag == 1) {
                        setText("Yes");
                    } else {
                        setText("No");
                    }
                }
            }
        });
        // only show buttons for rows with data
        actionColumn.setCellValueFactory((TableColumn.CellDataFeatures<Customer, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        // add buttons to cell
        actionColumn.setCellFactory((TableColumn<Customer, Boolean> p) -> new EditDeleteCustomer(mainApp, customerTable));
        actionColumn.setSortable(false);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void handleAddButton() {
        Customer tempCust = null;
        mainApp.showAddEditCustomer(Mode.ADD, tempCust);
    }

    public void handleAppointmentsButton() {
        mainApp.showAppointmentScreen();
    }

    public void handleReportsButton() {
        mainApp.showReportScreen();
    }

    public void handleLogoutButton() {
        boolean logout = mainApp.throwConfirmation("log out");
        if (logout) {
            AccessLogger.logSignOut(mainApp.getSessionUser().getUserName());
            mainApp.showLoginScreen();
        }
    }

}

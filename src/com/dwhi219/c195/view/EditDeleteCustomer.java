package com.dwhi219.c195.view;

import com.dwhi219.c195.MainApp;
import com.dwhi219.c195.dao.CustomerDAO;
import com.dwhi219.c195.dao.impl.CustomerDAOImpl;
import com.dwhi219.c195.model.Customer;
import com.dwhi219.c195.util.Constants;
import com.dwhi219.c195.util.Constants.Mode;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Customized TableCell that includes an edit and delete button for customers.
 * Part of REQUIREMENT C.
 *
 * @author Daniel White
 */
public class EditDeleteCustomer extends TableCell<Customer, Boolean> {

    final Button editButton = new Button();
    final Button deleteButton = new Button();
    final HBox container = new HBox();
    private CustomerDAO dao;
    private MainApp mainApp;

    public EditDeleteCustomer(MainApp mainApp, final TableView table) {
        this.mainApp = mainApp;
        dao = new CustomerDAOImpl();
        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.EDIT_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        editButton.setOnAction((ActionEvent actionEvent) -> {
            Customer cust = getTableView().getItems().get(getIndex());
            mainApp.showAddEditCustomer(Mode.EDIT, cust);
        });
        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.DELETE_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        deleteButton.setOnAction((ActionEvent actionEvent) -> {
            Customer cust = getTableView().getItems().get(getIndex());
            boolean proceed = mainApp.throwConfirmation("delete");
            if (proceed) {
                boolean result = dao.deleteCustomer(cust);
                if (result) {
                    table.getItems().remove(cust);
                }
            }
        });
        container.getChildren().addAll(editButton, deleteButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, true);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(container);
        } else {
            setGraphic(null);
        }
    }

}

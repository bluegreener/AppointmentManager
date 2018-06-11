package com.dwhi219.c195.view;

import com.dwhi219.c195.MainApp;
import com.dwhi219.c195.dao.AppointmentDAO;
import com.dwhi219.c195.dao.impl.AppointmentDAOImpl;
import com.dwhi219.c195.model.Appointment;
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
 * Customized TableCell that includes an edit and delete button for appointments. Part of REQUIREMENT C.
 *
 * @author Daniel White
 */
public class EditDeleteAppointment extends TableCell<Appointment, Boolean> {

    final Button editButton = new Button();
    final Button deleteButton = new Button();
    final HBox container = new HBox();
    private AppointmentDAO dao;
    private MainApp mainApp;

    public EditDeleteAppointment(MainApp mainApp, final TableView table) {
        this.mainApp = mainApp;
        dao = new AppointmentDAOImpl();
        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.EDIT_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        editButton.setOnAction((ActionEvent actionEvent) -> {
            Appointment apt = getTableView().getItems().get(getIndex());
            mainApp.showAddEditAppointment(Mode.EDIT, apt);
        });
        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.DELETE_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        deleteButton.setOnAction((ActionEvent actionEvent) -> {
            Appointment apt = getTableView().getItems().get(getIndex());
            boolean proceed = mainApp.throwConfirmation("delete");
            if (proceed) {
                boolean result = dao.deleteAppointment(apt);
                if (result) {
                    table.getItems().remove(apt);
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

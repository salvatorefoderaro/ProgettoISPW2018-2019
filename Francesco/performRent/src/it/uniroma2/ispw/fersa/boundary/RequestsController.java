package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantRequestHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestsController {

    @FXML
    private BorderPane window;

    @FXML
    private GridPane gridPane;

    private List<Label> requestIds;

    private List<Button> viewRequests;

    private TenantRequestHandlerSession control;


    private void getAllRequest() {

        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();
        try {

            requestLabelBeans = control.getAllContractRequest();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
        }

        int row = 1;

        for (RequestLabelBean requestLabelBean : requestLabelBeans) {
            Label requestId = new Label(Integer.toString(requestLabelBean.getContractRequestId()));

            Button viewRequest = new Button("Visualizza");

            this.requestIds.add(requestId);
            this.viewRequests.add(viewRequest);


            gridPane.addRow(row, requestId, new Label(requestLabelBean.getNickname()),
                    new Label(requestLabelBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getTotalPrice() + " €"),
                    new Label(requestLabelBean.getState().getTenantState(), viewRequest));

            row++;
        }


    }





}

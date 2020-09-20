package components.showOption.shopMap;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import logic.BusinessLogic;
import logic.Customer;
import logic.Store;

public class MapGridPane {

    BusinessLogic businessLogic;
    GridPane gridPane;

    public MapGridPane(BusinessLogic businessLogic, HBox detailsOnSDMHbox)
    {
        gridPane = new GridPane();
        setBusinessLogic(businessLogic);
        setGridPaneColumnsAndRow(detailsOnSDMHbox);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    void settingCustomersOnMapAndDetialsOnCustomer(HBox hboxDetails)
    {
        for(Customer customer : businessLogic.getUsersList()) {
            final Label label = new Label();
            label.setText("Type: customer\n" + "Serial ID: " + customer.getSerialNumber() + "\nName: " + customer.getName() +
                    "\nLocation:" + "(" + customer.getLocation().getX() + "," +
                    customer.getLocation().getY() + ")");
            CellInMap cellInMap = new CellInMap(label, hboxDetails, true, false);
            gridPane.add(cellInMap.getHbox(), customer.getLocation().getX(), customer.getLocation().getY());
        }
    }

    public void gridPaneForceColsAndRows(int numCols, int numRows) {
        gridPane.setGridLinesVisible(true);
        double widht=100.0;
        double hight=100.0;
        for (int i = 0; i <= numCols+1; i++)
        {

            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(widht / numCols);
            if(i==0 || i==numCols+1) {
            }
            else {  }
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i <= numRows+1; i++) {
            if(i==0 || i==numCols+1) {}
            else {}
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(hight / numRows);
            gridPane.getRowConstraints().add(rowConst);
        }
    }

    void settingStoresOnMapAndHBoxDetailsOnSDM(HBox hboxDetails)
    {
        for(Store store : businessLogic.getStoresList())
        {
            final Label label = new Label();
            label.setText("Type: store\n" + "Serial ID: " + store.getSerialNumber() + "\nName: " + store.getName() +
                    "\nLocation:" + "(" + store.getLocation().getX() + "," +
                    store.getLocation().getY() + ")");
            CellInMap cellInMap = new CellInMap(label, hboxDetails, false, true);
            gridPane.add(cellInMap.getHbox(), store.getLocation().getX(), store.getLocation().getY());
        }
    }

    void settingColumnsScaleMark(int maxCoordinateX)
    {

        for(Integer i=1;i<=maxCoordinateX;i++)
        {
            gridPane.add(new Label(i.toString()),i,0);
        }
    }

    void settingRowsScaleMark(int maxCoordinateY)
    {
        for(Integer i=1;i<=maxCoordinateY;i++)
        {
            gridPane.add(new Label(i.toString()),0,i);
        }

    }

    public void setBusinessLogic(BusinessLogic businessLogic)
    {
        this.businessLogic = businessLogic;
    }


    public void setGridPaneColumnsAndRow(HBox detailsOnSDMHbox)
    {
        int maxCoordinateX = businessLogic.getMaxCoordinateXOfLocationOfUsersAndStores();
        int maxCoordinateY = businessLogic.getMaxCoordinateYOfLocationOfUsersAndStores();
        gridPaneForceColsAndRows(maxCoordinateX, maxCoordinateY);
        settingColumnsScaleMark(maxCoordinateX);
        settingRowsScaleMark(maxCoordinateY);
        settingDetailsOnMapAndSetTriggerForUpdatingDetailsOnSDM(detailsOnSDMHbox);
    }

    public void settingDetailsOnMapAndSetTriggerForUpdatingDetailsOnSDM(HBox detailsOnSDMHbox)
    {
        settingCustomersOnMapAndDetialsOnCustomer(detailsOnSDMHbox);
        settingStoresOnMapAndHBoxDetailsOnSDM(detailsOnSDMHbox);
    }
}

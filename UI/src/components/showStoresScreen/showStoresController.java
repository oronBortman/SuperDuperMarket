package components.showStoresScreen;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class showStoresController {

    @FXML
    GridPane showStoresGridPane;
    ComboBox comboBoxStores;
    Label serialNumber;
    @FXML
    void chooseStore(ActionEvent event) {

        comboBoxStores = (ComboBox) showStoresGridPane.lookup("#comboBoxStores");
        serialNumber = (Label) showStoresGridPane.lookup("#serialNumberValue");
        System.out.println(comboBoxStores.getSelectionModel().getSelectedItem().toString());
        serialNumber.setText("1111");
        //comboBoxStores.setItems(FXCollections.observableArrayList(storeNames));
       // System.out.println("Hello");
    }

}

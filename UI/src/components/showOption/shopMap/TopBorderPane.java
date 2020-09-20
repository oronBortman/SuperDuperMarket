package components.showOption.shopMap;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TopBorderPane {

    HBox hboxHeadlineOfDetailsOnSDM;
    BorderPane topBorderPane;
    HBox hboxDetailsOnSDM;

    public TopBorderPane()
    {
        hboxHeadlineOfDetailsOnSDM = new HBox();
        hboxDetailsOnSDM = new HBox();
        topBorderPane = new BorderPane();
        settingTopBorderPaneWithHBoxAndUpdateHeadline();
    }

    public BorderPane getTopBorderPane() {
        return topBorderPane;
    }

    public HBox getHboxHeadlineOfDetailsOnSDM() {
        return hboxHeadlineOfDetailsOnSDM;
    }

    public HBox getHboxDetailsOnSDM() {
        return hboxDetailsOnSDM;
    }

    public void settingTopBorderPaneWithHBoxAndUpdateHeadline()
    {
        Label labelDetailsOnSDM = new Label("Details On Super Duper Market Customeres and Stores");
        setHBoxHeadlineOfDetailsOnSDM(hboxHeadlineOfDetailsOnSDM, labelDetailsOnSDM);
        topBorderPane.setTop(hboxHeadlineOfDetailsOnSDM);
        topBorderPane.setCenter(hboxDetailsOnSDM);
    }


    public void setHBoxHeadlineOfDetailsOnSDM(HBox hbox, Label label)
    {
        hbox.setMinWidth(200);
        hbox.setMinHeight(200);
        hbox.setPrefHeight(200);
        hbox.setPrefWidth(200);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().setAll(label);
    }
}

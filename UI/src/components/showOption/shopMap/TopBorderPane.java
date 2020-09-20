package components.showOption.shopMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TopBorderPane {

    HBox hboxHeadlineOfDetailsOnSDM;
    BorderPane topBorderPane;
    HBox hboxDetailsOnSDM;

    public TopBorderPane()
    {
        hboxHeadlineOfDetailsOnSDM = new HBox();
        hboxDetailsOnSDM = new HBox();
        topBorderPane = new BorderPane();
        setHboxDetailsOnSDM();
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
        labelDetailsOnSDM.setFont(new Font("Arial", 28));
        setHBoxHeadlineOfDetailsOnSDM(labelDetailsOnSDM);
        topBorderPane.setTop(hboxHeadlineOfDetailsOnSDM);
        topBorderPane.setCenter(hboxDetailsOnSDM);
    }


    public void setHBoxHeadlineOfDetailsOnSDM(Label label)
    {
        hboxHeadlineOfDetailsOnSDM.setPadding(new Insets(20, 0, 0, 0));
        hboxHeadlineOfDetailsOnSDM.setMinWidth(200);
        hboxHeadlineOfDetailsOnSDM.setMinHeight(40);
        hboxHeadlineOfDetailsOnSDM.setPrefHeight(40);
        hboxHeadlineOfDetailsOnSDM.setPrefWidth(200);
        hboxHeadlineOfDetailsOnSDM.setAlignment(Pos.CENTER);
        hboxHeadlineOfDetailsOnSDM.getChildren().setAll(label);
    }

    public void setHboxDetailsOnSDM()
    {
        hboxDetailsOnSDM.setMinWidth(200);
        hboxDetailsOnSDM.setMinHeight(200);
        hboxDetailsOnSDM.setPrefHeight(200);
        hboxDetailsOnSDM.setPrefWidth(200);
        hboxDetailsOnSDM.setAlignment(Pos.CENTER);
    }
}

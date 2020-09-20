package components.showOption.shopMap;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ShowMapMainBorderPane {
    BorderPane showMapBorderPane;
    ScrollPane scrollPane;

    public ShowMapMainBorderPane()
    {
        showMapBorderPane = new BorderPane();
        scrollPane = new ScrollPane();
        setScrollPaneOfMap();
        scrollPane.setContent(showMapBorderPane);

    }

    public BorderPane getShowMapBorderPane() {
        return showMapBorderPane;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setMainBorderPaneWithComponents(BorderPane topBorderPane, GridPane gridPane)
    {
        showMapBorderPane.setTop(topBorderPane);
        showMapBorderPane.setCenter(gridPane);
    }

    void setScrollPaneOfMap()
    {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
}

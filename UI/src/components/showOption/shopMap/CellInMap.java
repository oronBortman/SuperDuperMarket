package components.showOption.shopMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CellInMap {

    ImageView imageView;
    HBox hbox;
    Boolean isCustomer;
    Boolean isStore;

    public CellInMap(Label label, HBox detailsOnSDMHbox, Boolean isCustomer, Boolean isStore)
    {
        this.hbox = new HBox();
        this.isCustomer = isCustomer;
        this.isStore = isStore;

        if(isCustomer)
        {
            imageView = generateCustomerImage();
        }
        else if(isStore)
        {
            imageView = generateStoreImage();
        }
        setHbox();
        updateHboxWithImage();
        setImageTriggerWithLabel(detailsOnSDMHbox, label);
    }

    public HBox getHbox() {
        return hbox;
    }

    public void setImageTriggerWithLabel(HBox detailsOnSDMHbox, Label label) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                detailsOnSDMHbox.getChildren().setAll(label);
            }
        });
    }

    ImageView generateCustomerImage()
    {
        ImageView userImage = new ImageView("/components/user.png");
        userImage.setFitHeight(40);
        userImage.setFitWidth(40);
        return userImage;
    }

    ImageView generateStoreImage()
    {
        ImageView storeImage = new ImageView("/components/shop.png");
        storeImage.setFitHeight(40);
        storeImage.setFitWidth(40);
        return storeImage;
    }

    public void setHbox() {
        hbox.setMinHeight(40);
        hbox.setPrefHeight(40);
        hbox.setMinWidth(40);
        hbox.setPrefHeight(40);
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setHgrow(imageView, Priority.ALWAYS);
    }

    public void updateHboxWithImage()
    {
        imageView.fitWidthProperty().bind(hbox.widthProperty());
        imageView.fitHeightProperty().bind(hbox.heightProperty());

    }
}

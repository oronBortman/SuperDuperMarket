package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty serialNumber;
    private SimpleStringProperty name;
    private SDMLocation location;


    public User(Integer serialNumber, String name, SDMLocation location)
    {
        this.serialNumber = new SimpleIntegerProperty(serialNumber);
        this.name = new SimpleStringProperty(name);
        this.location = location;
    }

    public String getName()
    {
        return name.get();
    }

    public Integer getSerialNumber()
    {
        return serialNumber.get();
    }

    /*
    public User(jaxb.schema.generated.SDMItem item)
    {
        this.serialNumber = new SimpleIntegerProperty(item.getId());
        this.name = new SimpleStringProperty(item.getName());
        String itemPurchaseCategoryStr = item.getPurchaseCategory();
        Item.TypeOfMeasure itemPurchaseCategory = Item.TypeOfMeasure.convertStringToEnum(itemPurchaseCategoryStr);
        this.typeToMeasureBy = itemPurchaseCategory;
    }*/

}

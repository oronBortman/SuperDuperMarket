package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty serialNumber;
    private SimpleStringProperty name;
    private SDMLocation location;


    public Customer(Integer serialNumber, String name, SDMLocation location)
    {
        this.serialNumber = new SimpleIntegerProperty(serialNumber);
        this.name = new SimpleStringProperty(name);
        this.location = location;
    }

    public Customer(jaxb.schema.generated.SDMCustomer user)
    {
        this.serialNumber = new SimpleIntegerProperty(user.getId());
        this.name = new SimpleStringProperty(user.getName());
        this.location = new SDMLocation(user.getLocation());
    }

    public String getName()
    {
        return name.get();
    }

    public Integer getSerialNumber()
    {
        return serialNumber.get();
    }

    public SDMLocation getLocation()
    {
        return location;
    }
    //TODO
    public Integer getAmountOfOrdersCol()
    {
        return 0;
    }
    //TODO
    public Double getAverageOrderPriceCol()
    {
        return 0.0;
    }
    //TODO
    public Double getAverageDeliveryPrice()
    {
        return 0.0;
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

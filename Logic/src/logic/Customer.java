package logic;

public class Customer extends SDMObjectWithUniqueLocationAndUniqueSerialID {

    public Customer(Integer serialNumber, String name, SDMLocation location)
    {
        super(serialNumber, name, location);
    }

    public Customer(jaxb.schema.generated.SDMCustomer user)
    {
        super(user.getId(), user.getName(), new SDMLocation(user.getLocation()));
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

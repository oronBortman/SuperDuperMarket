package logic.discount;

import jaxb.schema.generated.IfYouBuy;

import javax.xml.bind.annotation.XmlAttribute;

public class IfYouBuySDM {
    private double quantity;
    private int itemId;

    public IfYouBuySDM(IfYouBuy ifYouBuy)
    {
        this.quantity = ifYouBuy.getQuantity();
        this.itemId = ifYouBuy.getItemId();
    }

}

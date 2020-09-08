package logic.discount;

import jaxb.schema.generated.SDMOffer;

import javax.xml.bind.annotation.XmlAttribute;

public class Offer {

    private double quantity;
    private int itemId;
    private int forAdditional;

    public Offer(SDMOffer sdmOffer)
    {
        this.quantity = sdmOffer.getQuantity();
        this.itemId = sdmOffer.getItemId();
        this.forAdditional = sdmOffer.getForAdditional();
    }
}

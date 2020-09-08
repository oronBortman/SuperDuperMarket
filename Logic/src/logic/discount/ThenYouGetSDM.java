package logic.discount;

import jaxb.schema.generated.SDMOffer;
import jaxb.schema.generated.ThenYouGet;

import java.util.ArrayList;
import java.util.List;

public class ThenYouGetSDM {

    private List<Offer> sdmOfferList;
    private String operator;

    public ThenYouGetSDM(ThenYouGet thenYouGet)
    {
        this.operator = thenYouGet.getOperator();
        sdmOfferList = new ArrayList<Offer>();
    }

    public void addOfferToListFromSDMOffer(SDMOffer sdmOffer)
    {
        System.out.println("Inside addOfferToListFromSDMOffer");

        Offer sdmOfferToAdd = new Offer(sdmOffer);
        sdmOfferList.add(sdmOfferToAdd);
    }


}
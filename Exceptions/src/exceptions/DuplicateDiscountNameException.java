package exceptions;

import javafx.beans.property.StringProperty;

public class DuplicateDiscountNameException extends Exception{
    String discountName=null;
    String storeName=null;

    public DuplicateDiscountNameException(String discountName, String storeName)
    {
        this.discountName = discountName;
        this.storeName = storeName;
    }

    public String getDiscountName() {
        return discountName;
    }
    public String getStoreName() { return storeName;}
}

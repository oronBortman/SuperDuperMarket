package exceptions;

public class InvalidCoordinateYOfCustomerException extends InvalidCoordinateInCustomerException {

    public InvalidCoordinateYOfCustomerException(int coord, String name, Integer serialID)
    {
        super(coord, name, serialID);
    }
    public InvalidCoordinateYOfCustomerException(int coord)
    {
        super(coord);
    }
}

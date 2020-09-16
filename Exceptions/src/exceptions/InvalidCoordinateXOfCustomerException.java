package exceptions;

public class InvalidCoordinateXOfCustomerException extends InvalidCoordinateInCustomerException {
    public InvalidCoordinateXOfCustomerException(int coord, String name, Integer serialID)
    {
        super(coord, name, serialID);
    }
    public InvalidCoordinateXOfCustomerException(int coord)
    {
        super(coord);
    }
}

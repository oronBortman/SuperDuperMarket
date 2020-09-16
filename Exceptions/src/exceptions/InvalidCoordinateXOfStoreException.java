package exceptions;

public class InvalidCoordinateXOfStoreException extends InvalidCoordinateInStoreException {

    public InvalidCoordinateXOfStoreException(int coord, String name, Integer serialID)
    {
        super(coord, name, serialID);
    }
    public InvalidCoordinateXOfStoreException(int coord)
    {
        super(coord);
    }
}

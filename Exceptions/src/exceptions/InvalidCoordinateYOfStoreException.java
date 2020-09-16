package exceptions;

public class InvalidCoordinateYOfStoreException extends InvalidCoordinateInStoreException {

    public InvalidCoordinateYOfStoreException(int coord, String name, Integer serialID)
    {
        super(coord, name, serialID);
    }
    public InvalidCoordinateYOfStoreException(int coord)
    {
        super(coord);
    }
}

package exceptions;

public class InvalidCoordinateXException extends InvalidCoordinateException {

    public InvalidCoordinateXException(int coord, String name)
    {
        super(coord, name);
    }
    public InvalidCoordinateXException(int coord)
    {
        super(coord);
    }
}

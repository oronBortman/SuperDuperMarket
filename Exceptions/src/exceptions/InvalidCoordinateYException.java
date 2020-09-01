package exceptions;

public class InvalidCoordinateYException extends InvalidCoordinateException {

    public InvalidCoordinateYException(int coord, String name)
    {
        super(coord, name);
    }
    public InvalidCoordinateYException(int coord)
    {
        super(coord);
    }
}

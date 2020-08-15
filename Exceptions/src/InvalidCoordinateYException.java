public class InvalidCoordinateYException extends InvalidCoordinateException {

    InvalidCoordinateYException(int coord, String name)
    {
        super(coord, name);
    }
    InvalidCoordinateYException(int coord)
    {
        super(coord);
    }
}

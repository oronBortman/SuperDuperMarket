public class InvalidCoordinateXException extends InvalidCoordinateException {

    InvalidCoordinateXException(int coord, String name)
    {
        super(coord, name);
    }
    InvalidCoordinateXException(int coord)
    {
        super(coord);
    }
}

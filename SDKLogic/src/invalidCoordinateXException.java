public class invalidCoordinateXException extends invalidCoordinateException{

    invalidCoordinateXException(int coord, String name)
    {
        super(coord, name);
    }
    invalidCoordinateXException(int coord)
    {
        super(coord);
    }
}

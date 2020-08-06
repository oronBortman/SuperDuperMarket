public class invalidCoordinateYException extends invalidCoordinateException{

    invalidCoordinateYException(int coord, String name)
    {
        super(coord, name);
    }
    invalidCoordinateYException(int coord)
    {
        super(coord);
    }
}

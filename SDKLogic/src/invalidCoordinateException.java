public class invalidCoordinateException extends Exception{
    String name=null;
    int coord;
    invalidCoordinateException(int coord, String name)
    {
        this.coord = coord;
        this.name = name;
    }
    invalidCoordinateException(int coord)
    {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public int getCoord() {
        return coord;
    }
}

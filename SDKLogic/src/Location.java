public class Location {
    private int x;
    private int y;
    
    private boolean checkCoordinateIsInRange(int coordinate)
    {
        return(coordinate >= 1 && coordinate <= 50);
    }
    
    public int getX(){ 
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}

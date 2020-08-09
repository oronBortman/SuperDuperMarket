public class LocationIsSameAsStoreException extends Exception {

    SDMLocation userLocation;
    SDMLocation storeLocation;
    LocationIsSameAsStoreException(SDMLocation userLocation, SDMLocation storeLocation)
    {
        this.userLocation = userLocation;
        this.storeLocation = storeLocation;
    }

    public SDMLocation getStoreLocation() {
        return storeLocation;
    }

    public SDMLocation getUserLocation() {
        return userLocation;
    }
}

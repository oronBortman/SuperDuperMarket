package common;

import java.net.URL;

public class SuperDuperMarketConstants {
   // private static final String BASE_PACKAGE = "/examples/basic/tasks";
    public static final  String SHOW_ITEMS_FXML_RESOURCE_IDENTIFIER = "/components/showItemsScreen/showItemsScreen.fxml";
    public static final  String SHOW_STORES_FXML_RESOURCE_IDENTIFIER = "/components/showStoresScreen/showStoresScreen.fxml";

    //public static final String MAIN_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/components/mainScreen/showStoresScreen.fxml";
    public static final String MAIN_FXML_RESOURCE_IDENTIFIER = "/components/mainScreen/mainScreen.fxml";
    public static final String XML_PATH = "/Users/oronbortman/Projects/Java/SuperDuperMarket/UserInterfaceConsole/ex1-small.xml";
    public static final URL MAIN_FXML_RESOURCE = SuperDuperMarketConstants.class.getResource(SuperDuperMarketConstants.SHOW_ITEMS_FXML_RESOURCE_IDENTIFIER);
}

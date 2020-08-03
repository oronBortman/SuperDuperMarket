import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeneralMethods {
    
    public static <K, V> Set<K> getSetOfDictionary(Map<K, V> mapToCreateSetFrom)
    {
        Set<K> setOfKeys = new HashSet<K>();
        for(K key : mapToCreateSetFrom.keySet())
        {
            setOfKeys.add(key);
        }
        return setOfKeys;
    }
}

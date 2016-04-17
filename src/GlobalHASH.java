import java.util.HashMap;

/**
 * Created by Gautam on 4/17/2016.
 */
public class GlobalHASH {
    private static HashMap<String,LangID> map = new HashMap<String, LangID>();

    public static void setGlobalHash()
    {
        map.put("C",LangID.C);
        map.put("C++14",LangID.CPP14);
        map.put("C++ 5.1",LangID.CPP5_1);

    }

    public static LangID getGlobalHash(String key)
    {
        return map.get(key);
    }

}

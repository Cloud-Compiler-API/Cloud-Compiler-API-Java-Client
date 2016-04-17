/**
 * Created by Gautam on 4/17/2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class Languages {

        private int withVersion=0;
        private int onlyPopular=0;
        private int withPopular=0;
        private int onlyUnpopular=0;
        private int withFileExt=0;
        private String version=null;
        private String FileExt=null;
        private String name=null;
        private int id=0;
       private List<LangSet> langList = new ArrayList<LangSet>();
    private Map<LangID, Integer> map = new HashMap<LangID, Integer>();

    private static final Languages INSTANCE = new Languages();//dt belonging to class

    private static Languages getWithVersion()
    {
        INSTANCE.withVersion=1;
        return INSTANCE;
    }

   private static Languages getWithFileExt()
    {
        INSTANCE.withFileExt=1;
        return INSTANCE;
    }

    private static Languages getWithPopular()
    {
        INSTANCE.onlyPopular=1;
        return INSTANCE;
    }

    private static Languages getWithUnPopular()
    {
        INSTANCE.onlyUnpopular=1;
        return INSTANCE;
    }

    public static Languages setLanguages() {
        GlobalHASH.setGlobalHash();
        //to be called first to fetch languages
        // apache httpclient vs urlconnection classes
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://cloudcompiler.esy.es/api/languages");
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the response
        BufferedReader rd = null;
        try {
            rd = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        StringBuffer b = new StringBuffer();
        try {
            while ((line = rd.readLine()) != null) {
                b.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(b.toString());

        JSONArray array = null;
        try {
            array = new JSONArray(b.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<LangSet> list = new ArrayList<LangSet>();

        for (int i = 0; i < array.length(); i++) {
            LangSet langSet = new LangSet();
            String poplular = null, version = null, fileExt = null;
            try {
                int id = array.getJSONObject(i).getInt("id");
                String name = array.getJSONObject(i).getString("name");
                if (INSTANCE.withPopular != 0) {
                    poplular = array.getJSONObject(i).getString("popular");
                }
                if (INSTANCE.withVersion != 0) {
                    version = array.getJSONObject(i).getString("version");
                }
                if (INSTANCE.withFileExt != 0) {
                    fileExt = array.getJSONObject(i).getString("fileExt");
                }

                langSet.name = name;
                langSet.id = id;
                langSet.version = version;
                langSet.popular = poplular;
                langSet.fileExt = fileExt;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            list.add(langSet);
            INSTANCE.map.put(GlobalHASH.getGlobalHash(langSet.name),langSet.id);
        }
        INSTANCE.langList = list;
        return INSTANCE;
    }

    public static String getLanguageID(LangID key)
    {
        return Integer.toString(INSTANCE.map.get(key));
    }

}

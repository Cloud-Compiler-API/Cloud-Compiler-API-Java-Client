import org.apache.commons.codec.language.bm.Lang;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gautam on 4/17/2016.
 */
public class Submissions {

    private String sourceCode = null;//could be static fields
    private String URL = "http://cloudcompiler.esy.es/api/submissions/?";
    private String langId = null;
    private String stdin = null;
    private String timeLimit = null;
    private String id=null;

    private static final Submissions INSTANCE = new Submissions();
    private String SUBMITURL ="http://cloudcompiler.esy.es/api/submissions/";

    private static Submissions setSourceCode(String sourceCode) {
        INSTANCE.sourceCode = sourceCode;
        return INSTANCE;
    }

    public String getLangId() {
        return langId;
    }

    public static Submissions setLangId(String langId) {
        INSTANCE.langId = langId;
        return INSTANCE;
    }

    public static Submissions setTimeLimit(String timeLimit) {
        INSTANCE.timeLimit = timeLimit;
        return INSTANCE;
    }

    public static Submissions setStdin(String stdin) {
        INSTANCE.stdin = stdin;
        return INSTANCE;
    }

    public static Submissions getSample(String id)
    {
        String URL="http://cloudcompiler.esy.es/api/languages/sample/"+id;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = null;
        request = new HttpGet(URL);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
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


        return INSTANCE;
    }

    public static Submissions getTemplate(String id)
    {
        String URL="http://cloudcompiler.esy.es/api/languages/template/"+id;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = null;
        request = new HttpGet(URL);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        return INSTANCE;
    }

    public static Submissions submit()
    {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = null;
        try {
            request = new HttpGet(INSTANCE.SUBMITURL+(new JSONObject(INSTANCE.id)).getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return INSTANCE;

    }

    public static Submissions init() {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(INSTANCE.URL);
        HttpResponse response = null;
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            if (INSTANCE.sourceCode != null) {
                nameValuePairs.add(new BasicNameValuePair("sourceCode", INSTANCE.sourceCode));
            }
            if (INSTANCE.langId != null) {
                nameValuePairs.add(new BasicNameValuePair("langId", INSTANCE.langId));
            }
            if (INSTANCE.timeLimit != null) {
                nameValuePairs.add(new BasicNameValuePair("timeLimit", INSTANCE.timeLimit));
            }

            nameValuePairs.add(new BasicNameValuePair("stdin",
                    INSTANCE.stdin));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
        INSTANCE.id=b.toString();

        return INSTANCE;
    }


    public static void main(String[] args) {

        String code="#include <stdio.h>\n" +
                "\n" +
                "main( ) \n" +
                "{\n" +
                "        printf(\"hello, world\\n\");\n" +
                "}" ;

        String a= Languages.setLanguages().getLanguageID(LangID.C);

        Submissions ob= Submissions.setSourceCode(code).setStdin("1").setLangId(a).setTimeLimit("0").init();
        try {
            Thread.sleep(2000);
            ob.submit().getTemplate(Integer.toString(55)).getSample(Integer.toString(55));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


package domain.util;

/**
 * Created by Matheus on 29/05/2017.
 */

public class HttpHelper {
/*
    public static String getRequest(String Url){
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);

        try {
            HttpResponse response = client.execute(request);
            sret = request(response);
        } catch (Exception ex){

        }

        return sret;
    }


    public static String request(HttpResponse response){
        String result = "";

        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        } catch (Exception ex){
            result = "Erro";
        }

        return result;
    }

*/
}

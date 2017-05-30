package domain.util;

/**
 * Created by Matheus on 29/05/2017.
 */

public class JSONfunctions {
/*
    public static JSONObject getJSONfromURL(String url){
        InputStream is = null;
        String result = "0";
        JSONObject jArray = null;

        try{
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 60000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e){
            Log.e("log_tag", "Error intimeout http connection " + e.toString());
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch(Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
            result = "{\"errorcode\": \"0\"}";
        }

        try{
            jArray = new JSONObject(result);
        } catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
            result = "{\"errorcode\": \"0\"}";

            try{
                jArray = new JSONObject(result);
            } catch(JSONException el){
                el.printStackTrace();
            }
        }

        return jArray;
    }

    public static String getStringJSONfromURL (String url){
        InputStream is = null;
        String result = "0";

        //http post
        try{
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 60000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response .getEntity();
            is = entity.getContent();
        } catch (Exception e){
            Log.e("log_tag", "Error intimeout http connection " + e.toString());
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
            result = "{\"errorcode\": \"0\"}";
        }

        return result;

    }

*/
}

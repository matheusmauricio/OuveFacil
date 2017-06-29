package domain.controller;

/**
 * Created by Matheus on 28/06/2017.
 */

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConexaoHttpClient {
    public static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient httpClient;

    private static HttpClient getHttpClient(){

        if(httpClient == null){
            httpClient = new DefaultHttpClient();
            final HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);//Parametros de tempo de conexão
            HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);

        }
        return httpClient;//retorna cliente de http
    }

    public static String executaHttpPost (String url, ArrayList<NameValuePair> parametrosPost) throws Exception {
        BufferedReader buffereredReader = null;

        try{                                                    //testa a conexão com o servidor
            HttpClient Client  = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parametrosPost);
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = Client.execute(httpPost);
            buffereredReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LS = System.getProperty("line.separator");
            while ((line = buffereredReader.readLine()) != null){
                stringBuffer.append(line + LS);
            }
            buffereredReader.close();
            String resultado = stringBuffer.toString();
            return resultado; // resultado retorna 1 se for cadastrado, ou 0 se não for
        }finally{
            if(buffereredReader != null){
                try{
                    buffereredReader.close();
                }catch (IOException e){         //se da algum problema retorna um erro
                    e.printStackTrace();
                }
            }
        }

    }
}
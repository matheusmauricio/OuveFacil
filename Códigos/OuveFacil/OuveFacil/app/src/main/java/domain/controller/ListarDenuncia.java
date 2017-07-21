package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mm.ouvefacil.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Denuncia;

public class ListarDenuncia extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();
    private String descricao;
    private double latitude;
    private double longitude;
    private boolean anonimato;
    private String complementoStatus;
    private String nomeUsuario;
    private String nomeAdministrador;
    private String nomeBairro;
    private String nomeCategoria;
    private String nomeStatus;
    private String urlFotoVideo;
    private ArrayList<Denuncia> param = new ArrayList<Denuncia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_denuncia);
    }


    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarDenuncia.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarDenuncia.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarDenuncia.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarDenuncia.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = br.readLine()) != null){
                    sb.append(line+"\n");
                }
                is.close();
                result = sb.toString();

            }catch(Exception e){
                Log.e("log_tag", "Erro ao converter o resultado " + e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void v){

            try {
                JSONArray Jarray = new JSONArray(result);

                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = Jarray.getJSONObject(i);

                    // output na tela
                    //String codAdministrador = jsonObject.getString("codAdministrador");

                    descricao = jsonObject.getString("descricao");
                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");
                    anonimato = jsonObject.getBoolean("anonimato");
                    complementoStatus = jsonObject.getString("complementoStatus");
                    nomeUsuario = jsonObject.getString("nomeUsuario");
                    nomeAdministrador = jsonObject.getString("nomeAdministrador");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    nomeCategoria = jsonObject.getString("nomeCategoria");
                    nomeStatus = jsonObject.getString("nomeStatus");
                    urlFotoVideo = jsonObject.getString("urlFotoVideo");


                    Denuncia denuncia = new Denuncia();

                    denuncia.setDescricao(descricao);
                    denuncia.setLatitude(latitude);
                    denuncia.setLongitude(longitude);
                    denuncia.setAnonimato(anonimato);
                    denuncia.setComplementoStatus(complementoStatus);
                    /*denuncia.setUsuario(nomeUsuario);
                    denuncia.setAdministrador(nomeAdministrador);
                    denuncia.setBairro(nomeBairro);
                    denuncia.setCategoria(nomeCategoria);
                    denuncia.setStatus(nomeStatus);
                    denuncia.setFotoEOuVideo(urlFotoVideo);*/

                    param.add(denuncia);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }


}

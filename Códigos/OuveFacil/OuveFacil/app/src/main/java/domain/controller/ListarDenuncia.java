package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mm.ouvefacil.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Denuncia;
import domain.model.UF;

public class ListarDenuncia extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();
    private Integer codDenuncia;
    private String descricao;
    private double latitude;
    private double longitude;
    private boolean anonimato;
    private Integer auxAnonimato;
    private String complementoStatus;
    private String nomeUsuario;
    private String nomeAdministrador;
    private String nomeBairro;
    private String nomeCategoria;
    private String nomeStatus;
    private String urlFotoVideo;
    private ArrayList<String> param = new ArrayList<String>();
    private double auxPosicaoLat;
    private double auxPosicaoLong;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_denuncia);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewDenuncia);


        Intent it = getIntent();
        auxPosicaoLat = it.getDoubleExtra("DenunciaLat", 0);
        auxPosicaoLong = it.getDoubleExtra("DenunciaLong", 0);

        ListarDenuncia.Task task = new ListarDenuncia.Task();
        task.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                /*Denuncia denuncia = new Denuncia();
                denuncia = (Denuncia) listView.getItemAtPosition(position);


                Toast.makeText(ListarDenuncia.this, "Item selecionado "+ denuncia, Toast.LENGTH_SHORT).show();*/

            }
        });

    }

    public void colaborarNeg(View view){

    }

    public void colaborarPos(View view){

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

            String url = ipServidor.getIpServidor()+"/listarDenunciaCompleta.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("lat", String.valueOf(auxPosicaoLat)));
            valores.add(new BasicNameValuePair("lng", String.valueOf(auxPosicaoLong)));


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                final HttpResponse httpResponse = httpClient.execute(httpPost);

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

                    codDenuncia = jsonObject.getInt("codDenuncia");
                    descricao = jsonObject.getString("descricao");
                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");
                    auxAnonimato = jsonObject.getInt("anonimato");
                    complementoStatus = jsonObject.getString("complementoStatus");
                    nomeUsuario = jsonObject.getString("nomeUsuario");
                    nomeAdministrador = jsonObject.getString("nomeAdministrador");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    nomeCategoria = jsonObject.getString("nomeCategoria");
                    nomeStatus = jsonObject.getString("nomeStatus");
                    urlFotoVideo = jsonObject.getString("urlFotoVideo");

                    if (auxAnonimato == 0){
                        anonimato = false;
                        param.add("Código da Denúncia: " + codDenuncia + "\nCategoria: " + nomeCategoria + "\nDescrição: " + descricao
                                + "\nStatus: " + nomeStatus + "\nUsuário que criou: " + nomeUsuario + "\nAdministrador Responsável: " +
                                nomeAdministrador + "\nObservação do Administrador: " + complementoStatus);
                    } else{
                        anonimato = true;
                        param.add("Código da Denúncia: " + codDenuncia + "\nCategoria: " + nomeCategoria + "\nDescrição: " + descricao
                                + "\nStatus: " + nomeStatus + "\nUsuário Anônimo" + "\nAdministrador Responsável: " +
                                nomeAdministrador + "\nObservação do Administrador: " + complementoStatus);
                    }


                    ArrayAdapter<String> ad = new ArrayAdapter<String>(ListarDenuncia.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }


}

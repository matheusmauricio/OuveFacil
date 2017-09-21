package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.maps.model.Marker;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import domain.model.Administrador;
import domain.model.Bairro;
import domain.model.Categoria;
import domain.model.Denuncia;
import domain.model.Status;
import domain.model.UF;
import domain.model.Usuario;

public class MinhasDenuncias extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();
    private Logado logado = new Logado();

    private Address endereco;

    private ArrayList<String> param = new ArrayList<String>();
    private ListView listView;

    public static Integer codDenuncia;
    public Integer codUsuario2;
    private String descricao;
    private double latitude;
    private double longitude;
    private static double auxLatitude;
    private static double auxLongitude;
    private boolean anonimato;
    private Integer auxAnonimato;
    private String complementoStatus;
    private String nomeUsuario;
    private String nomeAdministrador;
    private String nomeBairro;
    private String nomeCategoria;
    private String nomeStatus;
    private String data;
    private String hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_denuncias);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewDenuncia);

        MinhasDenuncias.Task task = new MinhasDenuncias.Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String aux = listView.getItemAtPosition(position).toString();

                auxLatitude = Double.parseDouble(aux.substring(aux.indexOf("Latitude") + 10, aux.indexOf("Longitude") -1));
                auxLongitude = Double.parseDouble(aux.substring(aux.indexOf("Longitude") + 11, aux.indexOf("Administrador") -1));

                mudarActivity(auxLatitude, auxLongitude);
            }
        });

    }

    public void mudarActivity(Double latitude, Double longitude){
        Intent irParaTelaDenunciaCompleta = new Intent(this, ListarDenuncia.class);

        irParaTelaDenunciaCompleta.putExtra("DenunciaLat", latitude);
        irParaTelaDenunciaCompleta.putExtra("DenunciaLong", longitude);
        startActivity(irParaTelaDenunciaCompleta);
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(MinhasDenuncias.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    MinhasDenuncias.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarMinhasDenuncias.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(logado.getUsuario().getCodUsuario())));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                final HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(MinhasDenuncias.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    data = jsonObject.getString("data");
                    hora = jsonObject.getString("hora");
                    codUsuario2 = jsonObject.getInt("codUsuario");

                    if (auxAnonimato == 0) {
                        anonimato = false;
                    } else{
                        anonimato = true;
                    }

                    pegaEndereco(latitude, longitude);
                    String auxNumero;

                    if(endereco.getSubThoroughfare() == null){
                        auxNumero = ", s/nº";
                    } else{
                        auxNumero = ", nº " + endereco.getSubThoroughfare();
                    }

                    param.add("Código da Denúncia: " + codDenuncia + "\nCategoria: " + nomeCategoria + "\nDescrição: " + descricao
                            + "\nStatus: " + nomeStatus + "\nData: " + data + "\nHora: " + hora + "\nEndereço: " +
                            endereco.getThoroughfare()+ auxNumero + "\nBairro: " + nomeBairro + "\nLatitude: " + latitude
                            + "\nLongitude: " + longitude + "\nAdministrador Responsável: " + nomeAdministrador +
                            "\nObservação do Administrador: " + complementoStatus);

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(MinhasDenuncias.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

    public void pegaEndereco(double latitude, double longitude){
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        try{
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0){
                endereco = addresses.get(0);
            }
        }catch (IOException e){

        }
    }


}

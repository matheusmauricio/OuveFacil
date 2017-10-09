package domain.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.maps.model.LatLng;
import com.mm.ouvefacil.Manifest;
import com.mm.ouvefacil.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Handler;

import domain.model.Denuncia;
import domain.model.UF;
import domain.model.Usuario;
import domain.model.UsuarioDenuncia;

public class ListarDenuncia extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();
    private Logado logado = new Logado();

    public static Integer codDenuncia;
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
    public static String nomeStatus;
    private String midia1;
    private String midia2;
    private String midia3;
    private String midia4;
    private String data;
    private String hora;
    public static Integer existe;
    public static Integer naoExiste;
    private ArrayList<String> param = new ArrayList<String>();
    private double auxPosicaoLat;
    private double auxPosicaoLong;
    public static Integer codUsuario2;
    public static Integer codDenuncia2;
    public static Integer codUsuarioDenuncia;
    public static String colaboracao;
    public static boolean permitir;
    public static Integer codUsuario3;

    private ListView listView;
    private ImageView imageView;
    private Button buttonExiste;
    private Button buttonNaoExiste;

    private android.os.Handler handler = new android.os.Handler();
    public static String auxUrl;
    private Address endereco;

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
        buttonExiste = (Button) findViewById(R.id.buttonColaborarPos);
        buttonNaoExiste = (Button) findViewById(R.id.buttonColaborarNeg);

        Intent it = getIntent();
        auxPosicaoLat = it.getDoubleExtra("DenunciaLat", 0);
        auxPosicaoLong = it.getDoubleExtra("DenunciaLong", 0);

        ListarDenuncia.Task task = new ListarDenuncia.Task();
        task.execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    public void desabilitarBotoes(){
        buttonExiste.setEnabled(false);
        buttonNaoExiste.setEnabled(false);
    }

    public void verificaUsuario(){
        //Verifica se o usuário que criou a denúncia é o mesmo usuário que está logado
        if(codUsuario3 != logado.getUsuario().getCodUsuario()) {
            ListarDenuncia.Task2 task2 = new ListarDenuncia.Task2();
            task2.execute();
        } else{
            desabilitarBotoes();
        }
    }

    public void pegaEndereco(){
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        try{
            addresses = geocoder.getFromLocation(auxPosicaoLat, auxPosicaoLong, 1);
            if (addresses.size() > 0){
                endereco = addresses.get(0);
            }
        }catch (IOException e){

        }
    }

    public void verificaStatus(){
        if(Objects.equals(nomeStatus, "Concluída") || Objects.equals(nomeStatus, "Concluída por Negação Popular")
                || Objects.equals(nomeStatus, "Em Análise") || Objects.equals(nomeStatus, "Em Fase de Correção")){
            desabilitarBotoes();
        }
    }

    public void carregarImagem(){
        new Thread(){
            public void run(){
                Bitmap img = null;
                try{
                    URL url = new URL(ipServidor.getIpServidor() + auxUrl);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                }catch (IOException e){

                }

                final Bitmap imgAux = img;
                handler.post(new Runnable(){
                    public void run(){
                        /*ImageView iv = new ImageView(getBaseContext());
                        iv.setImageBitmap(imgAux);*/

                        imageView = (ImageView) findViewById(R.id.imageViewActivity);
                        imageView.setImageBitmap(imgAux);
                    }
                });
            }
        }.start();
    }

    public void colaborarNeg(View view){

        new Thread(){
            public void run() {
                colaborarNegativamente();
            }
        }.start();

        naoExiste++;
        Integer total = naoExiste + existe;

        if(Objects.equals(nomeStatus, "Não Concluída") && total > 15 && ((Double.valueOf(naoExiste)/Double.valueOf(total)) >= 0.75)){
            new Thread(){
                public void run() {
                    alterarStatus();
                }
            }.start();
        }

        desabilitarBotoes();

        Toast.makeText(ListarDenuncia.this, "Colaboração realizada com sucesso! Nós agradecemos pela ajuda!!", Toast.LENGTH_LONG).show();
    }

    public void colaborarPos(View view){

        existe++;

        new Thread(){
            public void run() {
                colaborarPositivamente();
            }
        }.start();

        desabilitarBotoes();

        Toast.makeText(ListarDenuncia.this, "Colaboração realizada com sucesso! Nós agradecemos pela ajuda!!", Toast.LENGTH_LONG).show();
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
                    midia1 = jsonObject.getString("midia1");
                    midia2 = jsonObject.getString("midia2");
                    midia3 = jsonObject.getString("midia3");
                    midia4 = jsonObject.getString("midia4");
                    data = jsonObject.getString("data");
                    hora = jsonObject.getString("hora");
                    existe = jsonObject.getInt("existe");
                    naoExiste = jsonObject.getInt("naoExiste");
                    codUsuario3 = jsonObject.getInt("codUsuario");

                    auxUrl = midia1;

                    verificaStatus();
                    pegaEndereco();

                    String auxBairro;
                    String auxNumero;

                    if(endereco.getSubLocality() == null){
                        auxBairro = "Nome indisponível";
                    } else{
                        auxBairro = endereco.getSubLocality();
                    }

                    if(endereco.getSubThoroughfare() == null){
                        auxNumero = ", s/nº";
                    } else{
                        auxNumero = ", nº " + endereco.getSubThoroughfare();
                    }


                    if (auxAnonimato == 0){
                        anonimato = false;
                        param.add("Código da Denúncia: " + codDenuncia + "\nCategoria: " + nomeCategoria + "\nDescrição: " + descricao
                                + "\nStatus: " + nomeStatus + "\nUsuário que criou: " + nomeUsuario + "\nData: " + data + "\nHora: " +
                                hora + "\nEndereço: " + endereco.getThoroughfare()+ auxNumero + "\nBairro: " + auxBairro
                                + "\nUsuários que confirmaram a existência: " + existe + "\nUsuários que negaram a existência: " +
                                naoExiste + "\nAdministrador Responsável: " + nomeAdministrador + "\nObservação do Administrador: "
                                + complementoStatus);
                    } else{
                        anonimato = true;
                        param.add("Código da Denúncia: " + codDenuncia + "\nCategoria: " + nomeCategoria + "\nDescrição: " + descricao
                                + "\nStatus: " + nomeStatus + "\nUsuário Anônimo" + "\nData: " + data + "\nHora: " + hora
                                + "\nEndereço: " + endereco.getThoroughfare() + auxNumero + "\nBairro: " +auxBairro +
                                "\nUsuários que confirmaram a existência: " + existe + "\nUsuários que negaram a existência: " +
                                naoExiste + "\nAdministrador Responsável: " + nomeAdministrador + "\nObservação do Administrador: " +
                                complementoStatus);
                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(ListarDenuncia.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

                    carregarImagem();
                    verificaUsuario();

                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

    public void colaborarPositivamente(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/colaborarPositivamente.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codDenuncia", String.valueOf(codDenuncia)));
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(logado.getUsuario().getCodUsuario())));
            valores.add(new BasicNameValuePair("colaboracao", "Existe"));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT);
                    } catch(ParseException e){
                        e.printStackTrace();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch(ClientProtocolException e){

        } catch(IOException e){

        }
    }

    public void colaborarNegativamente(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/colaborarNegativamente.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codDenuncia", String.valueOf(codDenuncia)));
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(logado.getUsuario().getCodUsuario())));
            valores.add(new BasicNameValuePair("colaboracao", "Não Existe"));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT);
                    } catch(ParseException e){
                        e.printStackTrace();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch(ClientProtocolException e){

        } catch(IOException e){

        }
    }

    public void alterarStatus(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/denunciaNegadaPelaPopulacao.php");

        try{

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codDenuncia", String.valueOf(codDenuncia)));


            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT);
                    } catch(ParseException e){
                        e.printStackTrace();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch(ClientProtocolException e){

        } catch(IOException e){

        }
    }


    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarDenuncia.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };

            handler.postDelayed(progressRunnable, 4000);

            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarDenuncia.Task2.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarUsuarioDenuncia.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(logado.getUsuario().getCodUsuario())));
            valores.add(new BasicNameValuePair("codDenuncia", String.valueOf(codDenuncia)));


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
                    codUsuarioDenuncia = jsonObject.getInt("codUsuarioDenuncia");
                    codUsuario2 = jsonObject.getInt("codUsuario");
                    codDenuncia2 = jsonObject.getInt("codDenuncia");
                    colaboracao = jsonObject.getString("colaboracao");

                    Usuario usuario = new Usuario();
                    usuario.setCodUsuario(codUsuario2);

                    Denuncia denuncia = new Denuncia();
                    denuncia.setCodDenuncia(codDenuncia2);

                    UsuarioDenuncia usuarioDenuncia = new UsuarioDenuncia();
                    usuarioDenuncia.setCodUsuarioDenuncia(codUsuarioDenuncia);
                    usuarioDenuncia.setColaboracao(colaboracao);
                    usuarioDenuncia.setUsuario(usuario);
                    usuarioDenuncia.setDenuncia(denuncia);

                    if(codUsuarioDenuncia != null){
                        desabilitarBotoes();
                    }

                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

}

package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Categoria;
import domain.model.SubCategoria;

public class ListarCategoria extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private Spinner spinnerSubCategoria;
    private String nome;
    private String subCategoriaNome;
    private ArrayList<Categoria> param = new ArrayList<Categoria>();
    private ArrayList<SubCategoria> arraySubCategoria = new ArrayList<SubCategoria>();
    private ArrayAdapter<SubCategoria> adapterSubCategoria;
    private Integer codCat;
    private Integer codSubCat;
    private IpServidor ipServidor = new IpServidor();
    private String aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categoria);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        //Toast.makeText(this, "Teste de Mensagem", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewCategoria);

        editNome = (EditText) findViewById(R.id.editTextNome);
        spinnerSubCategoria = (Spinner) findViewById(R.id.spinnerActivitySubCategoria);



        ListarCategoria.Task2 task2 = new ListarCategoria.Task2();
        task2.execute();

        //listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Categoria categoria = new Categoria();
                categoria = (Categoria) listView.getItemAtPosition(position);

                Toast.makeText(ListarCategoria.this, "Item selecionado "+ categoria, Toast.LENGTH_SHORT).show();

                codCat = categoria.getCodCategoria();

                editNome.setText(categoria.getNome());
            }
        });

        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubCategoria subCategoria = new SubCategoria();
                subCategoria = (SubCategoria) spinnerSubCategoria.getItemAtPosition(position);

                aux = subCategoria.getNome().toString();

                codSubCat = subCategoria.getCodSubCategoria();
                Toast.makeText(ListarCategoria.this, "Item selecionado "+ subCategoria, Toast.LENGTH_SHORT).show();

                param.clear();

                ListarCategoria.Task task = new ListarCategoria.Task();
                task.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarCategoria.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarCategoria.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCategoria.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("subCategoria", aux));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarCategoria.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codCat = jsonObject.getInt("codCategoria");
                    nome = jsonObject.getString("nomeCategoria");
                    subCategoriaNome = jsonObject.getString("nomeSubCategoria");

                    Categoria categoria = new Categoria();
                    //SubCategoria subCategoria = new SubCategoria();


                    //subCategoria.setNome(subCategoriaNome);
                    categoria.setCodCategoria(codCat);
                    categoria.setNome(nome);
                    categoria.setSubCategoria(arraySubCategoria);

                    //arraySubCategoria.add(subCategoria);
                    param.add(categoria);

                    ArrayAdapter<Categoria> ad = new ArrayAdapter<Categoria>(ListarCategoria.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

                    //ArrayAdapter<SubCategoria> ad1 = new ArrayAdapter<SubCategoria>(ListarCategoria.this, android.R.layout.simple_spinner_dropdown_item, arraySubCategoria);
                    //spinnerSubCategoria.setAdapter(ad1);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarCategoria.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarCategoria.Task2.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarSubCategoria.php";
            //String url = "http://192.168.52.4/OuveFacil/listarSubCategoria.php";

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
                Toast.makeText(ListarCategoria.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codSubCat = jsonObject.getInt("codSubCategoria");
                    subCategoriaNome = jsonObject.getString("nome");

                    SubCategoria subCategoria = new SubCategoria();
                    subCategoria.setNome(subCategoriaNome);
                    subCategoria.setCodSubCategoria(codSubCat);

                    arraySubCategoria.add(subCategoria);

                    ArrayAdapter<SubCategoria> ad1 = new ArrayAdapter<SubCategoria>(ListarCategoria.this, android.R.layout.simple_spinner_dropdown_item, arraySubCategoria);
                    spinnerSubCategoria.setAdapter(ad1);

                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

    public void alterarDados(View view){
        new Thread(){
            public void run(){
                postHttp(codCat, editNome.getText().toString(), codSubCat);
            }
        }.start();

        finish();
    }

    public void postHttp(Integer codCat, String nome, final Integer codSubCat){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/alterarCategoria.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Categoria categoria = new Categoria();
                    categoria = (Categoria) listView.getItemAtPosition(position);

                    SubCategoria subCategoria = new SubCategoria();
                    subCategoria = (SubCategoria) spinnerSubCategoria.getItemAtPosition(position);

                    // Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();
                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codCategoria", String.valueOf(codCat)));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("codSubCategoria", String.valueOf(codSubCat)));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarCategoria.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

    public void excluirCategoria(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(codCat);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(Integer codCategoria){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/excluirCategoria.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Categoria categoria = new Categoria();

                    categoria = (Categoria) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codCategoria", String.valueOf(codCategoria)));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarCategoria.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

}

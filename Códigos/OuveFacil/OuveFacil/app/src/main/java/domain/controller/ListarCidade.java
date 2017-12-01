package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import domain.model.Cidade;
import domain.model.UF;

public class ListarCidade extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private Spinner spinnerUf;
    private String nome;
    private String ufNome;
    private ArrayList<Cidade> param = new ArrayList<Cidade>();
    private ArrayList<UF> arrayUf = new ArrayList<UF>();
    private ArrayAdapter<UF> adapterUf;
    private Integer codCid;
    private String siglaUf;
    private IpServidor ipServidor = new IpServidor();
    private String aux;
    private Integer auxPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cidade);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        Intent it = getIntent();
        auxPosicao = it.getIntExtra("Uf", 0);

        listView = (ListView) findViewById(R.id.listViewCidade);

        editNome = (EditText) findViewById(R.id.editTextNome);
        spinnerUf = (Spinner) findViewById(R.id.spinnerActivityUf);



        ListarCidade.Task2 task2 = new ListarCidade.Task2();
        task2.execute();

        //listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Cidade cidade = new Cidade();
                cidade = (Cidade) listView.getItemAtPosition(position);

                Toast.makeText(ListarCidade.this, "Item selecionado "+ cidade, Toast.LENGTH_SHORT).show();

                codCid = cidade.getCodCidade();

                editNome.setText(cidade.getNome());



            }
        });

        spinnerUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UF uf = new UF();
                uf = (UF) spinnerUf.getItemAtPosition(position);

                aux = uf.getSigla().toString();

                siglaUf = uf.getSigla();
                Toast.makeText(ListarCidade.this, "Item selecionado "+ uf, Toast.LENGTH_SHORT).show();

                param.clear();

                ListarCidade.Task task = new ListarCidade.Task();
                task.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarCidade.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarCidade.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCidade2.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("uf", aux));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarCidade.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codCid = jsonObject.getInt("codCidade");
                    nome = jsonObject.getString("nomeCidade");
                    ufNome = jsonObject.getString("nomeUf");

                    Cidade cidade = new Cidade();

                    cidade.setCodCidade(codCid);
                    cidade.setNome(nome);
                    cidade.setUf(arrayUf);

                    param.add(cidade);

                    ArrayAdapter<Cidade> ad = new ArrayAdapter<Cidade>(ListarCidade.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarCidade.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarCidade.Task2.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarUf.php";

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
                Toast.makeText(ListarCidade.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    siglaUf = jsonObject.getString("sigla");
                    ufNome = jsonObject.getString("nome");

                    UF uf = new UF();
                    uf.setNome(ufNome);
                    uf.setSigla(siglaUf);

                    arrayUf.add(uf);

                    ArrayAdapter<UF> ad = new ArrayAdapter<UF>(ListarCidade.this, android.R.layout.simple_spinner_dropdown_item, arrayUf);
                    spinnerUf.setAdapter(ad);
                    spinnerUf.setSelection(auxPosicao);

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
                postHttp(codCid, editNome.getText().toString(), siglaUf);
            }
        }.start();

        finish();
    }

    public void postHttp(Integer codCid, String nome, final String siglaUf){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/alterarCidade.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Cidade cidade = new Cidade();
                    cidade = (Cidade) listView.getItemAtPosition(position);

                    UF uf = new UF();
                    uf = (UF) spinnerUf.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codCidade", String.valueOf(codCid)));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("sigla", siglaUf));

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

    public void excluirCidade(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(codCid);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(Integer codCidade){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/excluirCidade.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Cidade cidade = new Cidade();

                    cidade = (Cidade) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codCidade", String.valueOf(codCidade)));

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

}

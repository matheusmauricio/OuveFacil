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

import domain.model.Bairro;
import domain.model.Cidade;

public class ListarBairro extends AppCompatActivity {

    private android.os.Handler handler = new android.os.Handler();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private Spinner spinnerCidade;
    private String nome;
    private String cidadeNome;
    private ArrayList<Bairro> param = new ArrayList<Bairro>();
    private ArrayList<Cidade> arrayCidade = new ArrayList<Cidade>();
    private ArrayAdapter<Cidade> adapterCidade;
    private Integer codBai;
    private Integer codCid;
    private IpServidor ipServidor = new IpServidor();
    private String aux;
    private Integer auxPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_bairro);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        Intent it = getIntent();
        auxPosicao = it.getIntExtra("Cidade", 0);

        listView = (ListView) findViewById(R.id.listViewBairro);

        editNome = (EditText) findViewById(R.id.editTextNome);
        spinnerCidade = (Spinner) findViewById(R.id.spinnerActivityCidade);

        ListarBairro.Task2 task2 = new ListarBairro.Task2();
        task2.execute();

        //listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Bairro bairro = new Bairro();

                bairro = (Bairro) listView.getItemAtPosition(position);

                Toast.makeText(ListarBairro.this, "Item selecionado "+ bairro, Toast.LENGTH_SHORT).show();

                codBai = bairro.getCodBairro();

                editNome.setText(bairro.getNome());

            }
        });

        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidade = new Cidade();
                cidade = (Cidade) spinnerCidade.getItemAtPosition(position);

                aux = cidade.getNome().toString();

                codCid = cidade.getCodCidade();
                Toast.makeText(ListarBairro.this, "Item selecionado "+ cidade, Toast.LENGTH_SHORT).show();

                param.clear();

                ListarBairro.Task task = new ListarBairro.Task();
                task.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };
            handler.postDelayed(progressRunnable, 8000);

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Listando Itens...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarBairro.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarBairro2.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("cidade", aux));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codBai = jsonObject.getInt("codBairro");
                    nome = jsonObject.getString("nomeBairro");
                    cidadeNome = jsonObject.getString("nomeCidade");

                    Bairro bairro = new Bairro();

                    bairro.setNome(nome);
                    bairro.setCodBairro(codBai);
                    bairro.setCidade(arrayCidade);

                    //arraySubCategoria.add(subCategoria);
                    param.add(bairro);

                    ArrayAdapter<Bairro> ad = new ArrayAdapter<Bairro>(ListarBairro.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

                    //ArrayAdapter<SubCategoria> ad1 = new ArrayAdapter<SubCategoria>(ListarCidade.this, android.R.layout.simple_spinner_dropdown_item, arraySubCategoria);
                    //spinnerSubCategoria.setAdapter(ad1);
                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };
            handler.postDelayed(progressRunnable, 8000);

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Listando Itens...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarBairro.Task2.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCidade.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    cidadeNome = jsonObject.getString("nomeCidade");
                    codCid = jsonObject.getInt("codCidade");

                    Cidade cidade = new Cidade();
                    cidade.setNome(cidadeNome);
                    cidade.setCodCidade(codCid);

                    arrayCidade.add(cidade);

                    ArrayAdapter<Cidade> ad = new ArrayAdapter<Cidade>(ListarBairro.this, android.R.layout.simple_spinner_dropdown_item, arrayCidade);
                    spinnerCidade.setAdapter(ad);
                    spinnerCidade.setSelection(auxPosicao);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Falha ao carregar, por favor tente novamente mais tarde", Toast.LENGTH_LONG).show();
                Log.e("log_tag", "Error parsing data "+e.toString());
                this.progressDialog.dismiss();
                finish();
            }
        }
    }

    public void alterarDados(View view){
        new Thread(){
            public void run(){
                postHttp(codBai, editNome.getText().toString(), codCid);
            }
        }.start();

        finish();
    }

    public void postHttp(Integer codBai, String nome, final Integer codCid){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/alterarBairro.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Bairro bairro = new Bairro();
                    bairro = (Bairro) listView.getItemAtPosition(position);

                    Cidade cidade = new Cidade();
                    cidade = (Cidade) spinnerCidade.getItemAtPosition(position);

                    // Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();
                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codBairro", String.valueOf(codBai)));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("codCidade", String.valueOf(codCid)));

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

    public void excluirBairro(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(codBai);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(Integer codBairro){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/excluirBairro.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Bairro bairro = new Bairro();

                    bairro = (Bairro) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codBairro", String.valueOf(codBairro)));

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

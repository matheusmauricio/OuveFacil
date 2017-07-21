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

import domain.model.UF;

public class ListarUf extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private EditText editSigla;
    private String nome;
    private String sigla;
    private ArrayList<UF> param = new ArrayList<UF>();
    private IpServidor ipServidor = new IpServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_uf);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        Intent it = getIntent();
        Integer auxxx = it.getIntExtra("Denuncia", 0);
        Toast.makeText(this, "A" + auxxx, Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewUf);

        editSigla = (EditText) findViewById(R.id.editTextSigla);
        editNome = (EditText) findViewById(R.id.editTextNome);

        ListarUf.Task task = new ListarUf.Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                UF uf = new UF();
                uf = (UF) listView.getItemAtPosition(position);

                Toast.makeText(ListarUf.this, "Item selecionado "+ uf, Toast.LENGTH_SHORT).show();

                sigla = uf.getSigla();
                editSigla.setText(uf.getSigla());
                editNome.setText(uf.getNome());

            }
        });
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarUf.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarUf.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarUf.php";
            //String url = "http://192.168.52.4/OuveFacil/listarUf.php";

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
                Toast.makeText(ListarUf.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    sigla = jsonObject.getString("sigla");
                    nome = jsonObject.getString("nome");

                    UF uf = new UF();

                    uf.setSigla(sigla);
                    uf.setNome(nome);

                    param.add(uf);

                    ArrayAdapter<UF> ad = new ArrayAdapter<UF>(ListarUf.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
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

                postHttp(editSigla.getText().toString(), editNome.getText().toString(), sigla);
            }
        }.start();
        finish();
    }

    public void postHttp(String novaSigla, String nome, String antigaSigla){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/alterarUf.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    UF uf = new UF();
                    uf = (UF) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("sigla", novaSigla));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("antigaSigla", antigaSigla));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarUf.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

    public void excluirUf(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(sigla);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(String sigla){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/excluirUf.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    UF uf = new UF();
                    uf = (UF) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("sigla", sigla));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarUf.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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


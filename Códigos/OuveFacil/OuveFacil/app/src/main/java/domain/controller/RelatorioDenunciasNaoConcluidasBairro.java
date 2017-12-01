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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

import domain.model.Bairro;

public class RelatorioDenunciasNaoConcluidasBairro extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> param = new ArrayList<String>();
    private ArrayList<Bairro> param2 = new ArrayList<Bairro>();
    private IpServidor ipServidor = new IpServidor();
    private Integer codDenuncia;
    private Integer codBairro;
    private String nomeCategoria;
    private String nomeBairro;
    private String nomeCidade;
    private String sigla;
    private Spinner spinnerBairro;
    private ArrayList<String> arrayBairro = new ArrayList<String>();
    private String aux;
    private String aux2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_denuncias_nao_concluidas_bairro);

        listView = (ListView) findViewById(R.id.listViewRelatorio);
        spinnerBairro = (Spinner) findViewById(R.id.spinnerBairroActivity);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        RelatorioDenunciasNaoConcluidasBairro.Task2 task2 = new RelatorioDenunciasNaoConcluidasBairro.Task2();
        task2.execute();




        spinnerBairro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //aux = (String) spinnerBairro.getItemAtPosition(position);

                Bairro bairro = new Bairro();
                bairro = (Bairro) spinnerBairro.getItemAtPosition(position);

                Toast.makeText(RelatorioDenunciasNaoConcluidasBairro.this, "Bairro escolhido: "+ bairro.getNome().toString(), Toast.LENGTH_SHORT).show();

                aux = bairro.getNome().toString();

                param.clear();
                RelatorioDenunciasNaoConcluidasBairro.Task task = new RelatorioDenunciasNaoConcluidasBairro.Task();
                task.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                aux2 = (String) listView.getItemAtPosition(position);
                Toast.makeText(RelatorioDenunciasNaoConcluidasBairro.this, "Denúncia selecionada "+ aux2, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioDenunciasNaoConcluidasBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioDenunciasNaoConcluidasBairro.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/relatorioDenunciasNaoConcluidasBairro.php");


            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("bairro", aux));

            InputStream is = null;

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                final HttpResponse resposta = httpClient.execute(httpPost);

                HttpEntity httpEntity = resposta.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(RelatorioDenunciasNaoConcluidasBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try {
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
                    nomeCategoria = jsonObject.getString("nomeCategoria");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    nomeCidade = jsonObject.getString("nomeCidade");
                    sigla = jsonObject.getString("sigla");


                    param.add("Código da Denúncia: " + codDenuncia.toString() + "\nCategoria: " + nomeCategoria +
                            "\nBairro: " + nomeBairro + "\nCidade: " + nomeCidade + "\nUF: " + sigla);

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(RelatorioDenunciasNaoConcluidasBairro.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioDenunciasNaoConcluidasBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioDenunciasNaoConcluidasBairro.Task2.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarBairro.php";

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
                Toast.makeText(RelatorioDenunciasNaoConcluidasBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codBairro = jsonObject.getInt("codBairro");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    //cidadeNome = jsonObject.getString("nomeCidade");

                    Bairro bairro = new Bairro();

                    bairro.setNome(nomeBairro);
                    bairro.setCodBairro(codBairro);
                    //bairro.setCidade(arrayCidade);

                    //arraySubCategoria.add(subCategoria);
                    param2.add(bairro);

                    ArrayAdapter<Bairro> ad2 = new ArrayAdapter<Bairro>(RelatorioDenunciasNaoConcluidasBairro.this, android.R.layout.simple_spinner_dropdown_item, param2);
                    spinnerBairro.setAdapter(ad2);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

}

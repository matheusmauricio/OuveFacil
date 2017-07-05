package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RelatorioAvaliacaoPorBairro extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();
    private ListView listView;
    private Spinner spinnerBairro;
    private String aux;
    private String aux2;
    private ArrayList<String> param = new ArrayList<String>();
    private ArrayList<Bairro> param2 = new ArrayList<Bairro>();
    private Integer count;
    private Integer count2;
    private Integer codBairro;
    private String nomeBairro;
    private Integer quantConcluida;
    private Integer quantTotal;
    private Integer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_avaliacao_por_bairro);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewRelatorio);
        spinnerBairro = (Spinner) findViewById(R.id.spinnerBairroActivity);


        RelatorioAvaliacaoPorBairro.Task2 task2 = new RelatorioAvaliacaoPorBairro.Task2();
        task2.execute();

        spinnerBairro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //aux = (String) spinnerBairro.getItemAtPosition(position);

                Bairro bairro = new Bairro();
                bairro = (Bairro) spinnerBairro.getItemAtPosition(position);

                Toast.makeText(RelatorioAvaliacaoPorBairro.this, "Bairro escolhido: "+ bairro.getNome().toString(), Toast.LENGTH_SHORT).show();
                param.clear();

                aux = bairro.getNome().toString();


                RelatorioAvaliacaoPorBairro.Task task = new RelatorioAvaliacaoPorBairro.Task();
                task.execute();

                RelatorioAvaliacaoPorBairro.Task3 task3 = new RelatorioAvaliacaoPorBairro.Task3();
                task3.execute();

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
                Toast.makeText(RelatorioAvaliacaoPorBairro.this, ""+ aux2, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioAvaliacaoPorBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioAvaliacaoPorBairro.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/relatorioAvaliacaoBairro.php");


            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();

            valores.add(new BasicNameValuePair("status", "Concluida"));
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
                Toast.makeText(RelatorioAvaliacaoPorBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    count = jsonObject.getInt("COUNT(*)");



                    //ArrayAdapter<String> ad = new ArrayAdapter<String>(RelatorioAvaliacaoPorBairro.this, android.R.layout.simple_list_item_1, param);
                    //listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

    public class Task2 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioAvaliacaoPorBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioAvaliacaoPorBairro.Task2.this.cancel(true);
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
                Toast.makeText(RelatorioAvaliacaoPorBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

                    ArrayAdapter<Bairro> ad2 = new ArrayAdapter<Bairro>(RelatorioAvaliacaoPorBairro.this, android.R.layout.simple_spinner_dropdown_item, param2);
                    spinnerBairro.setAdapter(ad2);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

    public class Task3 extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioAvaliacaoPorBairro.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioAvaliacaoPorBairro.Task3.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/relatorioAvaliacaoBairro2.php");


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
                Toast.makeText(RelatorioAvaliacaoPorBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    count2 = jsonObject.getInt("COUNT(*)");


                    quantTotal = count2;

                    media = quantConcluida/quantTotal;

                    if(media >= 0.75){
                        param.add("Classificação: 5 estrelas");
                    } else if (media >= 0.6 && media < 0.75){
                        param.add("Classificação: 4 estrelas");
                    } else if (media >= 0.5 && media < 0.6) {
                        param.add("Classificação: 3 estrelas");
                    } else if (media >= 0.3 && media < 0.5) {
                        param.add("Classificação: 2 estrelas");
                    }else{
                        param.add("Classificação: 1 estrela");
                    }
                    //pegar o total de denúncias. Estou pegando somente as concluídas e não concluídas

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(RelatorioAvaliacaoPorBairro.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }


}


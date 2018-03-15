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

public class RelatorioDenunciasConcluidasENaoConcluidas extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> param = new ArrayList<String>();
    private IpServidor ipServidor = new IpServidor();
    private ArrayList<String> arrayStatus = new ArrayList<String>();
    private Spinner spinnerStatus;
    private String aux;
    private String aux2;
    private Integer count;
    private String nome;
    private ArrayAdapter<String> ad;
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_denuncias_concluidas_enao_concluidas);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewRelatorio);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatusActivity);

        tipoStatus();

        ArrayAdapter<String> ad = new ArrayAdapter<String>(RelatorioDenunciasConcluidasENaoConcluidas.this, android.R.layout.simple_spinner_dropdown_item, arrayStatus);
        spinnerStatus.setAdapter(ad);

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                aux = (String) spinnerStatus.getItemAtPosition(position);

                Toast.makeText(RelatorioDenunciasConcluidasENaoConcluidas.this, "Tipo escolhido: "+ aux, Toast.LENGTH_SHORT).show();

                param.clear();
                RelatorioDenunciasConcluidasENaoConcluidas.Task task = new RelatorioDenunciasConcluidasENaoConcluidas.Task();
                task.execute();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                aux2 = (String) listView.getItemAtPosition(position);
                Toast.makeText(RelatorioDenunciasConcluidasENaoConcluidas.this, "Item selecionado "+ aux2, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void tipoStatus(){
        arrayStatus.add("Concluida");
        arrayStatus.add("Nao Concluida");
        arrayStatus.add("Concluida por Negacao Popular");
        arrayStatus.add("Em analise");
        arrayStatus.add("Em Fase de Correcao");
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioDenunciasConcluidasENaoConcluidas.this);

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
                    RelatorioDenunciasConcluidasENaoConcluidas.Task.this.cancel(true);
                }
            });
        }

        ;

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ipServidor.getIpServidor() + "/relatorioQuantidadeDenuncias.php");


            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("status", aux));

            //valores.add(new BasicNameValuePair("senha", senha));
            InputStream is = null;
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                final HttpResponse resposta = httpClient.execute(httpPost);

                HttpEntity httpEntity = resposta.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(RelatorioDenunciasConcluidasENaoConcluidas.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Erro ao converter o resultado " + e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            try {
                JSONArray Jarray = new JSONArray(result);

                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = Jarray.getJSONObject(i);

                    // output na tela
                    count = jsonObject.getInt("COUNT(*)");
                    nome = jsonObject.getString("nome");

                    if(count > 1){
                        param.add(count + " Denúncias" + "\nBairro: " + nome);
                    } else{
                        param.add(count + " Denúncia" + "\nBairro: " + nome);
                    }


                    ad = new ArrayAdapter<String>(RelatorioDenunciasConcluidasENaoConcluidas.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);

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


}

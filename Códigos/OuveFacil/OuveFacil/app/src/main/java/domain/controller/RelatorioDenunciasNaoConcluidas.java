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
import android.widget.Toast;

import com.mm.ouvefacil.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RelatorioDenunciasNaoConcluidas extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> param = new ArrayList<String>();
    private IpServidor ipServidor = new IpServidor();
    private Integer codDenuncia;
    private String nomeCategoria;
    private String nomeBairro;
    private String nomeCidade;
    private String sigla;
    private String aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_denuncias_nao_concluidas);

        listView = (ListView) findViewById(R.id.listViewRelatorio);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });


        RelatorioDenunciasNaoConcluidas.Task task = new RelatorioDenunciasNaoConcluidas.Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                aux = (String) listView.getItemAtPosition(position);

                Toast.makeText(RelatorioDenunciasNaoConcluidas.this, "Denúncia selecionada "+ aux, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(RelatorioDenunciasNaoConcluidas.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    RelatorioDenunciasNaoConcluidas.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/relatorioDenunciasNaoConcluidas.php";

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
                Toast.makeText(RelatorioDenunciasNaoConcluidas.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    nomeCategoria = jsonObject.getString("nomeCategoria");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    nomeCidade = jsonObject.getString("nomeCidade");
                    sigla = jsonObject.getString("sigla");


                    param.add("Código da Denúncia: " + codDenuncia.toString() + "\nCategoria: " + nomeCategoria +
                            "\nBairro: " + nomeBairro + "\nCidade: " + nomeCidade + "\nUF: " + sigla);

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(RelatorioDenunciasNaoConcluidas.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

}

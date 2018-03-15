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

import domain.model.UF;

public class InserirCidade extends AppCompatActivity {

    private ArrayList<UF> arrayUf = new ArrayList<UF>();
    private String nome;
    private Spinner spinnerUf;
    private String siglaUf;
    private IpServidor ipServidor = new IpServidor();
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cidade);

        Button buttonCancelar = (Button) findViewById(R.id.buttonCancelarCidade);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });

        spinnerUf = (Spinner) findViewById(R.id.spinnerActivityUf);

        InserirCidade.Task task = new InserirCidade.Task();
        task.execute();

        spinnerUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UF uf = new UF();
                uf = (UF) spinnerUf.getItemAtPosition(position);

                siglaUf = uf.getSigla();
                Toast.makeText(InserirCidade.this, "Item selecionado "+ uf, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(InserirCidade.this);

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
                    InserirCidade.Task.this.cancel(true);
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
                Toast.makeText(InserirCidade.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    nome = jsonObject.getString("nome");
                    siglaUf = jsonObject.getString("sigla");

                    UF uf = new UF();
                    uf.setNome(nome);
                    uf.setSigla(siglaUf);

                    arrayUf.add(uf);

                    ArrayAdapter<UF> ad = new ArrayAdapter<UF>(InserirCidade.this, android.R.layout.simple_spinner_dropdown_item, arrayUf);
                    spinnerUf.setAdapter(ad);
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

    public void enviarDados(View view){

        new Thread(){
            public void run(){
                EditText editNome = (EditText) findViewById(R.id.editTextNome);
                Spinner spinnerUf = (Spinner) findViewById(R.id.spinnerActivityUf);
                postHttp(editNome.getText().toString(), siglaUf);
            }
        }.start();

        finish();
        Toast.makeText(this, "Cidade Inserida", Toast.LENGTH_SHORT).show();

    }

    public void postHttp(String nome, String siglaUf){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/insertCidade.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
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


    public void listarCidade(View view){
        Intent IntentListarCidade = new Intent(this, ListarCidade.class);
        IntentListarCidade.putExtra("Uf", spinnerUf.getSelectedItemPosition()); //passa a posição da uf selecionada como parâmetro
        startActivity(IntentListarCidade);
    }


}

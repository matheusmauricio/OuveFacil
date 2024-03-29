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

import domain.model.Cidade;

public class InserirBairro extends AppCompatActivity {

    private ArrayList<Cidade> arrayCidade = new ArrayList<Cidade>();
    private String cidadeNome;
    private Spinner spinnerCidade;
    private Integer codCid;
    private IpServidor ipServidor = new IpServidor();
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_bairro);
        Button buttonCancelar = (Button) findViewById(R.id.buttonCancelarBairro);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });

        spinnerCidade = (Spinner) findViewById(R.id.spinnerActivityCidade);

        InserirBairro.Task task = new InserirBairro.Task();
        task.execute();

        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidade = new Cidade();
                cidade = (Cidade) spinnerCidade.getItemAtPosition(position);

                codCid = cidade.getCodCidade();
                Toast.makeText(InserirBairro.this, "Item selecionado "+ cidade, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(InserirBairro.this);

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
                    InserirBairro.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCidade.php";
            //String url = "http://192.168.52.4/OuveFacil/listarCidade.php";

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
                Toast.makeText(InserirBairro.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

                    ArrayAdapter<Cidade> ad = new ArrayAdapter<Cidade>(InserirBairro.this, android.R.layout.simple_spinner_dropdown_item, arrayCidade);
                    spinnerCidade.setAdapter(ad);
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
                Spinner spinnerCidade = (Spinner) findViewById(R.id.spinnerActivityCidade);

                postHttp(editNome.getText().toString(), codCid);
            }
        }.start();

        finish();
        Toast.makeText(this, "Bairro Inserido", Toast.LENGTH_SHORT).show();

    }

    public void postHttp(String nome, Integer codCid){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/insertBairro.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
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


    public void listarBairro(View view){
        Intent IntentListarBairro = new Intent(this, ListarBairro.class);
        IntentListarBairro.putExtra("Cidade", spinnerCidade.getSelectedItemPosition()); //passa a posição da cidade selecionada como parâmetro
        startActivity(IntentListarBairro);
    }

}

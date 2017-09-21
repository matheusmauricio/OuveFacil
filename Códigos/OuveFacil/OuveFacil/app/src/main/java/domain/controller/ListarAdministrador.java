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

import domain.model.Administrador;

public class ListarAdministrador extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editNome;
    private EditText editLogin;
    private EditText editSenha;
    private EditText editCpfCnpj;
    private String nome;
    private String login;
    private String senha;
    private String cpfCnpj;
    private Integer codAdm;
    private ArrayList<Administrador> param = new ArrayList<Administrador>();
    private IpServidor ipServidor = new IpServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_administrador);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        //Toast.makeText(this, "Teste de Mensagem", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewAdministrador);

        editNome = (EditText) findViewById(R.id.editTextNome);
        editLogin = (EditText) findViewById(R.id.editTextLogin);
        editSenha = (EditText) findViewById(R.id.editTextSenha);
        editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);


        Task task = new Task();
        task.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Administrador administrador = new Administrador();

                administrador = (Administrador) listView.getItemAtPosition(position);
                Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();

               // administrador = (Administrador) listView.getItemAtPosition(position);
                codAdm = administrador.getCodAdministrador();

                editNome.setText(administrador.getNome());
                editLogin.setText(administrador.getLogin());
                editSenha.setText("");
                editCpfCnpj.setText(administrador.getCpfCnpj());

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarAdministrador.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            //String url = "http://192.168.1.105/OuveFacil/listarAdministrador.php";
            String url = ipServidor.getIpServidor()+"/listarAdministrador.php";

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
                Toast.makeText(ListarAdministrador.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codAdm = jsonObject.getInt("codAdministrador");
                    nome = jsonObject.getString("nome");
                    login = jsonObject.getString("login");
                    senha = jsonObject.getString("senha");
                    cpfCnpj = jsonObject.getString("cpfCnpj");
                    Administrador administrador = new Administrador();

                    administrador.setCodAdministrador(codAdm);
                    administrador.setNome(nome);
                    administrador.setLogin(login);
                    administrador.setSenha(senha);
                    administrador.setCpfCnpj(cpfCnpj);

                    param.add(administrador);
                    //param.add(new BasicNameValuePair("login", login));
                    //param.add(new BasicNameValuePair("senha", senha));
                    //param.add(cpfCnpj);

                    ArrayAdapter<Administrador> ad = new ArrayAdapter<Administrador>(ListarAdministrador.this, android.R.layout.simple_list_item_1, param);
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
                postHttp(codAdm, editNome.getText().toString(), editLogin.getText().toString(), editSenha.getText().toString(), editCpfCnpj.getText().toString());
            }
        }.start();

        finish();

    }

    public void postHttp(Integer codAdministrador, String nome, String login, String senha, String cpfCnpj){
        HttpClient httpClient = new DefaultHttpClient();
        //HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/alterarAdministrador.php");
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/alterarAdministrador.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Administrador administrador = new Administrador();

                    administrador = (Administrador) listView.getItemAtPosition(position);
                    // Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codAdministrador", String.valueOf(codAdministrador)));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("login", login));
            valores.add(new BasicNameValuePair("senha", senha));
            valores.add(new BasicNameValuePair("cpfCnpj", cpfCnpj));


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

    public void excluirAdministrador(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(codAdm);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(Integer codAdministrador){
        HttpClient httpClient = new DefaultHttpClient();
        //HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/excluirAdministrador.php");
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/excluirAdministrador.php");


        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Administrador administrador = new Administrador();

                    administrador = (Administrador) listView.getItemAtPosition(position);
                    // Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codAdministrador", String.valueOf(codAdministrador)));

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

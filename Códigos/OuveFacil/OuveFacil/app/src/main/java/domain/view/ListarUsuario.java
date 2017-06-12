package domain.view;

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

import domain.model.Usuario;

public class ListarUsuario extends AppCompatActivity {

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
    private Integer codUsu;
    private ArrayList<Usuario> param = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuario);


        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });

        //Toast.makeText(this, "Teste de Mensagem", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewUsuario);

        editNome = (EditText) findViewById(R.id.editTextNome);
        editLogin = (EditText) findViewById(R.id.editTextLogin);
        editSenha = (EditText) findViewById(R.id.editTextSenha);
        editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);


        ListarUsuario.Task task = new ListarUsuario.Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Usuario usuario = new Usuario();
                usuario = (Usuario) listView.getItemAtPosition(position);
                Toast.makeText(ListarUsuario.this, "Item selecionado "+ usuario, Toast.LENGTH_SHORT).show();


                codUsu = usuario.getCodUsuario();

                editNome.setText(usuario.getNome());
                editLogin.setText(usuario.getLogin());
                editSenha.setText(usuario.getSenha());
                editCpfCnpj.setText(usuario.getCpfCnpj());

            }
        });

    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarUsuario.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarUsuario.Task.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = "http://192.168.1.105/OuveFacil/listarUsuario.php";
            //String url = "http://192.168.52.4/OuveFacil/listarUsuario.php";

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
                Toast.makeText(ListarUsuario.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codUsu = jsonObject.getInt("codUsuario");
                    nome = jsonObject.getString("nome");
                    login = jsonObject.getString("login");
                    senha = jsonObject.getString("senha");
                    cpfCnpj = jsonObject.getString("cpfCnpj");

                    Usuario usuario = new Usuario();

                    usuario.setCodUsuario(codUsu);
                    usuario.setNome(nome);
                    usuario.setLogin(login);
                    usuario.setSenha(senha);
                    usuario.setCpfCnpj(cpfCnpj);

                    param.add(usuario);
                    //param.add(new BasicNameValuePair("login", login));
                    //param.add(new BasicNameValuePair("senha", senha));
                    //param.add(cpfCnpj);

                    ArrayAdapter<Usuario> ad = new ArrayAdapter<Usuario>(ListarUsuario.this, android.R.layout.simple_list_item_1, param);
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
                /*EditText editNome = (EditText) findViewById(R.id.editTextNome);
                EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
                EditText editSenha = (EditText) findViewById(R.id.editTextSenha);
                EditText editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);*/
                postHttp(codUsu, editNome.getText().toString(), editLogin.getText().toString(), editSenha.getText().toString(), editCpfCnpj.getText().toString());
            }
        }.start();

        finish();

    }

    public void postHttp(Integer codUsuario, String nome, String login, String senha, String cpfCnpj){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/alterarUsuario.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Usuario usuario = new Usuario();

                    usuario = (Usuario) listView.getItemAtPosition(position);
                    // Toast.makeText(ListarAdministrador.this, "Item selecionado "+ administrador, Toast.LENGTH_SHORT).show();
                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(codUsuario)));
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("login", login));
            valores.add(new BasicNameValuePair("senha", senha));
            valores.add(new BasicNameValuePair("cpfCnpj", cpfCnpj));


            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarUsuario.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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

    public void excluirUsuario(View view){
        new Thread(){
            public void run(){
                postHttpExcluir(codUsu);
            }
        }.start();

        finish();
    }

    public void postHttpExcluir(Integer codUsuario){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/excluirUsuario.php");

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Usuario usuario = new Usuario();

                    usuario = (Usuario) listView.getItemAtPosition(position);

                }
            });

            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(codUsuario)));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(ListarUsuario.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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


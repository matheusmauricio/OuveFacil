package domain.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.ouvefacil.MainActivity;
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

import domain.controller.InserirUsuario;
import domain.controller.IpServidor;
import domain.controller.Logado;
import domain.model.Usuario;

public class TelaLogin extends AppCompatActivity {

    private EditText editLogin;
    private EditText editSenha;
    private IpServidor ipServidor = new IpServidor();
    private ArrayList<Usuario> param = new ArrayList<Usuario>();
    private String login;
    private String senha;
    private String nome;
    private String cpfCnpj;
    private Integer codUsuario;
    private Usuario usuario = new Usuario();
    private String log;
    String result = "";
    private String loginAux;
    private String senhaAux;
    private Logado logado = new Logado();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        editLogin = (EditText) findViewById(R.id.editTextLogin);
        editSenha = (EditText) findViewById(R.id.editTextSenha);

    }

    public void entrar(View view) {


        loginAux = editLogin.getText().toString();
        senhaAux = editSenha.getText().toString();
        TelaLogin.Task task = new TelaLogin.Task();
        task.execute();
        //Toast.makeText(TelaLogin.this, usuario.getNome() , Toast.LENGTH_SHORT).show();
        //finish();

    }


    public void isLogado() {
        //Toast.makeText(TelaLogin.this, param.get(0).getNome(), Toast.LENGTH_LONG).show();

        //for (Usuario aux : param) {
            if (logado.isEstaLogado() == true) {

                Intent irParaMenu = new Intent(this, MainActivity.class);
                startActivity(irParaMenu);
                finish();
            } else {
                Toast.makeText(TelaLogin.this, "Erro ao conectar. Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        //}
    }

    public void sair(View view) {
        finish();
    }

    public void cadastrar(View view) {
        /*Intent irParaMenu = new Intent(this, MainActivity.class);
        startActivity(irParaMenu);*/

        Intent irParaMenuCadastrar = new Intent(this, InserirUsuario.class);
        startActivity(irParaMenuCadastrar);
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(TelaLogin.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    TelaLogin.Task.this.cancel(true);
                }
            });
        }

        ;

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ipServidor.getIpServidor() + "/login.php");


            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("login", loginAux));
            valores.add(new BasicNameValuePair("senha", senhaAux));

            InputStream is = null;
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                final HttpResponse resposta = httpClient.execute(httpPost);

                HttpEntity httpEntity = resposta.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(TelaLogin.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    nome = jsonObject.getString("nome");
                    login = jsonObject.getString("login");
                    senha = jsonObject.getString("senha");
                    cpfCnpj = jsonObject.getString("cpfCnpj");
                    codUsuario = jsonObject.getInt("codUsuario");

                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setLogin(login);
                    usuario.setSenha(senha);
                    usuario.setCpfCnpj(cpfCnpj);
                    usuario.setCodUsuario(codUsuario);

                    param.add(usuario);

                    if(param != null){

                        logado.setEstaLogado(true);
                        logado.setUsuario(usuario);
                    }
                    Toast.makeText(TelaLogin.this, "Bem vindo(a) " + logado.getUsuario() , Toast.LENGTH_SHORT).show();

                    isLogado();
                }
                this.progressDialog.dismiss();
            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }


        }
    }
}

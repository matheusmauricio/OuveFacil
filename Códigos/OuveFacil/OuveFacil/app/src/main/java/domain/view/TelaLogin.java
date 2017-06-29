package domain.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.ouvefacil.MainActivity;
import com.mm.ouvefacil.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import domain.controller.ConexaoHttpClient;
import domain.controller.IpServidor;
import domain.model.Usuario;

public class TelaLogin extends AppCompatActivity {

    //private EditText editLogin;
    //private EditText editSenha;
    private IpServidor ipServidor = new IpServidor();
    private ArrayList<Usuario> param = new ArrayList<Usuario>();
    private String login;
    private String senha;
    private String nome;
    private String cpfCnpj;
    private boolean logado = false;
    private Usuario usuario = new Usuario();
    private String log;
    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        final EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
        final EditText editSenha = (EditText) findViewById(R.id.editTextSenha);

        Button buttonLogar = (Button) findViewById(R.id.buttonLogarActivity);

        buttonLogar.setOnClickListener(new OnClickListener() {      //botão de conexão online
            @Override
            public void onClick(View v) {
                String urlPost = ipServidor.getIpServidor() + "/login.php"; //url de request
                ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();

                //Parametros dos Edit
                parametrosPost.add(new BasicNameValuePair("login",editLogin.getText().toString()));
                //parametrosPost.add(new BasicNameValuePair("senha",editSenha.getText().toString()));

                String respostaRetornada = null;
                try{
                    respostaRetornada = ConexaoHttpClient.executaHttpPost(urlPost, parametrosPost); //
                    String resposta = respostaRetornada.toString();
                    resposta = resposta.replaceAll("\\s+", "");

                    if(resposta.equals("1")){       //se o usuario esta cadastrado exibe a mensagem
                        dialogo(".::Nic Sistemas::.","Seja Bem-Vindo ao Sistema.\n.::"+editLogin.getText().toString().toUpperCase()+"::.");
                        Toast.makeText(TelaLogin.this, "Logou", Toast.LENGTH_SHORT).show();
                        //Intent irParaMenu = new Intent(this, MainActivity.class);
                        //startActivity(irParaMenu);


                    }else{  //senao pede voltar a inserir o usuario
                        dialogo(".::Nic Sistemas::.",editLogin.getText().toString()+"Não é um usuario cadastrado.\nPor favor insira os seus dados novamente.");
                        Toast.makeText(TelaLogin.this, "Login errado", Toast.LENGTH_SHORT).show();
                    }


                }catch(Exception erro){

                    Toast.makeText(TelaLogin.this,"Erro.:"+erro, Toast.LENGTH_LONG);
                }

            }
        });

    }

    public void entrar(View view) {

        //login = editLogin.getText().toString();
        //senha = editSenha.getText().toString();
        //TelaLogin.Task task = new TelaLogin.Task();
        //task.execute();
        //Toast.makeText(TelaLogin.this, usuario.getLogin(), Toast.LENGTH_SHORT).show();
        //finish();

        //isLogado();



    }

    public void dialogo(String titulo, String texto) {  //declaração da mensagem de alerta

        AlertDialog.Builder dialog = new AlertDialog.Builder(TelaLogin.this);
        dialog.setTitle(titulo);
        dialog.setMessage(texto);
        dialog.setNeutralButton("OK", null);
        dialog.show();
    }

    public void isLogado() {
        //Toast.makeText(TelaLogin.this, param.get(0).getNome(), Toast.LENGTH_LONG).show();

        for (Usuario aux : param) {
            if (aux.getLogin() == login && aux.getLogin() != null) {
                Intent irParaMenu = new Intent(this, MainActivity.class);
                startActivity(irParaMenu);
            } else {
                Toast.makeText(TelaLogin.this, "Erro ao conectar. Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sair(View view) {
        finish();
    }

    public void cadastrar(View view) {
        Intent irParaMenu = new Intent(this, MainActivity.class);
        startActivity(irParaMenu);
    }
/*
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
            valores.add(new BasicNameValuePair("login", login));

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

                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setLogin(login);
                    usuario.setSenha(senha);
                    usuario.setCpfCnpj(cpfCnpj);


                    param.add(usuario);

                }
                this.progressDialog.dismiss();
            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }


        }
    }
    */
}

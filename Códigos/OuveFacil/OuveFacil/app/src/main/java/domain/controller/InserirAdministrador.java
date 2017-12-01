package domain.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.ouvefacil.R;

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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Matheus on 16/05/2017.
 */
public class InserirAdministrador extends Activity {

    private IpServidor ipServidor = new IpServidor();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_administrador);

        Button buttonCadastrar = (Button) findViewById(R.id.buttonCadastrarAdministrador);

        Button buttonCancelar = (Button) findViewById(R.id.buttonCancelarAdministrador);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });
    }

    public void enviarDados(View view){

        new Thread(){
            public void run(){
                EditText editNome = (EditText) findViewById(R.id.editTextNome);
                EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
                EditText editSenha = (EditText) findViewById(R.id.editTextSenha);
                EditText editCpfCnpj = (EditText) findViewById(R.id.editTextCpfCnpj);
                postHttp(editNome.getText().toString(), editLogin.getText().toString(), editSenha.getText().toString(), editCpfCnpj.getText().toString());
            }
        }.start();

        finish();
        Toast.makeText(this, "Administrador Inserido", Toast.LENGTH_SHORT).show();

    }

    public void postHttp(String nome, String login, String senha, String cpfCnpj){
        HttpClient httpClient = new DefaultHttpClient();
        //HttpPost httpPost = new HttpPost("http://192.168.1.105/OuveFacil/insertAdministrador.php");
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/insertAdministrador.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("login", login));
            valores.add(new BasicNameValuePair("senha", senha));
            valores.add(new BasicNameValuePair("cpfCnpj", cpfCnpj));

            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);
            Toast.makeText(InserirAdministrador.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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


    public void listarAdministrador(View view){
        Intent IntentListarAdministrador = new Intent(this, ListarAdministrador.class);

        startActivity(IntentListarAdministrador);
    }

}

package domain.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class InserirUf extends AppCompatActivity {

    private IpServidor ipServidor = new IpServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_uf);

        Button buttonCancelar = (Button) findViewById(R.id.buttonCancelarUf);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });
    }

    public void enviarDados(View view){

        new Thread(){
            public void run(){
                EditText editSigla = (EditText) findViewById(R.id.editTextSigla);
                EditText editNome = (EditText) findViewById(R.id.editTextNome);
                postHttp(editSigla.getText().toString(), editNome.getText().toString());
            }
        }.start();

        finish();

    }

    public void postHttp(String sigla, String nome){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor()+"/insertUf.php");

        try{
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("sigla", sigla));
            valores.add(new BasicNameValuePair("nome", nome));

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

    public void listarUf(View view){
        Intent IntentListarUf = new Intent(this, ListarUf.class);

        startActivity(IntentListarUf);
    }

}

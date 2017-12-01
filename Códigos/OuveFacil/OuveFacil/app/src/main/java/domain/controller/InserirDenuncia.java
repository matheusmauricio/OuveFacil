package domain.controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import domain.model.Administrador;
import domain.model.Bairro;
import domain.model.Categoria;
import domain.model.Cidade;
import domain.model.Denuncia;
import domain.model.UF;
import domain.util.Image;

public class InserirDenuncia extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    private IpServidor ipServidor = new IpServidor();
    private Spinner spinnerCategoria;
    private CheckBox checkBoxAnonimato1;
    private EditText editDescricao;
    private ArrayList<Bairro> arrayListBairro = new ArrayList<Bairro>();
    private ArrayList<Categoria> arrayListCategoria = new ArrayList<Categoria>();

    private GoogleApiClient mGoogleApiClient;
    private Location location;
    private Address endereco;

    private String nomeBairro;
    private Integer codBairro;
    private String nomeCategoria;
    private Integer codCategoria;
    private String nomeAdministrador;
    private Integer codAdministrador;
    private String cpfCnpjAdministrador;
    private Integer auxCheckBox;
    private Denuncia denuncia = new Denuncia();
    public static Image imagemAux = new Image();
    public static double auxLatitude;
    public static double auxLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_denuncia);


        //File file = new File(android.os.Environment.getExternalStorageDirectory(), "img.png");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 0);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this).
                addConnectionCallbacks(this).addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap imagem = (Bitmap) bundle.get("data");

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(imagem);

                imagemAux.setBitmap(imagem);
                imagemAux.setMime("png");
                denuncia.setImage(imagemAux);


                spinnerCategoria = (Spinner) findViewById(R.id.spinnerActivityCategoria);
                checkBoxAnonimato1 = (CheckBox) findViewById(R.id.checkBoxAnonimato);

                carregaSpinners();

                spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Categoria categoria = new Categoria();
                        categoria = (Categoria) spinnerCategoria.getItemAtPosition(position);

                        codCategoria = categoria.getCodCategoria();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                carregarPosicao();

                auxLatitude = location.getLatitude();
                auxLongitude = location.getLongitude();

                //Toast.makeText(this, "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                /*File file = null;
                file = new File(android.os.Environment.getExternalStorageDirectory(), "img.png");
                if(file != null){
                    denuncia.getImage().setResizedBitmap(file, 300, 300);
                    denuncia.getImage().setMimeFromImgPath(file.getPath());
                    //Toast.makeText(this, "AAAA" , Toast.LENGTH_SHORT).show();
                }*/

            }
        }else{
            finish();
        }
    }

    public void voltar(View view) {
        finish();
    }

    public void carregaSpinners() {
        InserirDenuncia.TaskCategoria taskCategoria = new InserirDenuncia.TaskCategoria();
        taskCategoria.execute();

    }

    public void pegaEndereco(){
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());


        try{
            addresses = geocoder.getFromLocation(auxLatitude, auxLongitude, 1);
            if (addresses.size() > 0){
                endereco = addresses.get(0);
            }
        }catch (IOException e){

        }
    }

    public void enviarDados(View view) {
        //Toast.makeText(this, "Chegou ", Toast.LENGTH_SHORT).show();
        new Thread() {
            public void run() {
                EditText editDesc = (EditText) findViewById(R.id.editTextDescricao);
                if(checkBoxAnonimato1.isChecked()){
                    auxCheckBox = 1;
                } else {
                    auxCheckBox = 0;
                }
                postHttp(codCategoria, editDesc.getText().toString(), auxCheckBox);
            }
        }.start();

        finish();
        Toast.makeText(this, "Denúncia Inserida", Toast.LENGTH_SHORT).show();
    }

    public void postHttp(Integer codigoCategoria, String descricao, Integer anonimato) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ipServidor.getIpServidor() + "/insertDenuncia.php");

        Logado logado = new Logado();

        pegaEndereco();

        String auxBairro;
        String auxNumero;
        String enderecoCompleto;

        if(endereco.getSubLocality() == null){
            auxBairro = "Nome indisponível";
        } else{
            auxBairro = endereco.getSubLocality();
        }

        if(endereco.getSubThoroughfare() == null){
            auxNumero = ", s/nº";
        } else{
            auxNumero = ", nº " + endereco.getSubThoroughfare();
        }

        enderecoCompleto = endereco.getThoroughfare()+ auxNumero;
        //Toast.makeText(InserirDenuncia.this, "end: " + enderecoCompleto + ", bairro " + auxBairro, Toast.LENGTH_LONG).show();

        try {
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            //valores.add(new BasicNameValuePair("codBairro", String.valueOf(codigoBairro)));
            valores.add(new BasicNameValuePair("bairro", auxBairro));
            valores.add(new BasicNameValuePair("endereco", enderecoCompleto));
            valores.add(new BasicNameValuePair("codCategoria", String.valueOf(codigoCategoria)));
            valores.add(new BasicNameValuePair("codUsuario", String.valueOf(logado.getUsuario().getCodUsuario())));
            valores.add(new BasicNameValuePair("descricao", descricao));
            valores.add(new BasicNameValuePair("latitude", String.valueOf(auxLatitude)));
            valores.add(new BasicNameValuePair("longitude", String.valueOf(auxLongitude)));
            valores.add(new BasicNameValuePair("anonimato", String.valueOf(anonimato)));
            valores.add(new BasicNameValuePair("complementoStatus", "Complemento Status")); // complemento status escrito pelo adm
            valores.add(new BasicNameValuePair("midia1", "/midias/semaforo1.jpg")); // url da foto
            valores.add(new BasicNameValuePair("codStatus", String.valueOf(6))); // pré-setado como Aguardando Aprovação
            valores.add(new BasicNameValuePair("img-mime", denuncia.getImage().getMime())); //formato da imagem
            valores.add(new BasicNameValuePair("img-image", denuncia.getImage().getBitmapBase64()));
            //valores.add(new BasicNameValuePair("img-image", imagemAux.getBitmapBase64()));


            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            final HttpResponse resposta = httpClient.execute(httpPost);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
    }

    public void carregarPosicao() {

        try{
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }catch(SecurityException ex) {

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public class TaskCategoria extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(InserirDenuncia.this);

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    InserirDenuncia.TaskCategoria.this.cancel(true);
                }
            });
        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCategoria2.php";

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
                Toast.makeText(InserirDenuncia.this, "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    codCategoria = jsonObject.getInt("codCategoria");
                    nomeCategoria = jsonObject.getString("nomeCategoria");

                    Categoria categoria = new Categoria();
                    categoria.setCodCategoria(codCategoria);
                    categoria.setNome(nomeCategoria);

                    arrayListCategoria.add(categoria);

                    ArrayAdapter<Categoria> ad = new ArrayAdapter<Categoria>(InserirDenuncia.this, android.R.layout.simple_list_item_1, arrayListCategoria);
                    spinnerCategoria.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }
    }

}

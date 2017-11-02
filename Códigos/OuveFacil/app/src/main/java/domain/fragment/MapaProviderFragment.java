package domain.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.instantapps.PackageManagerCompat;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.Objects;

import domain.controller.IpServidor;
import domain.controller.ListarDenuncia;
import domain.controller.ListarUf;
import domain.controller.Logado;
import domain.controller.TipoMapa;
import domain.model.Administrador;
import domain.model.Bairro;
import domain.model.Categoria;
import domain.model.Denuncia;
import domain.model.FotoEOuVideo;
import domain.model.Usuario;



public class MapaProviderFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
            GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.InfoWindowAdapter{

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "ExemploProvFragmentV1";
    private GoogleApiClient mGoogleApiClient;
    public static GoogleMap googleMapAux;

    private Location location;
    private TipoMapa tipoMapa = new TipoMapa();
    private IpServidor ipServidor = new IpServidor();

    private Integer codDenuncia;
    private String descricao;
    private double latitude;
    private double longitude;
    private boolean anonimato;
    private Integer auxAnonimato;
    private String complementoStatus;
    private String nomeUsuario;
    private String nomeAdministrador;
    private String nomeBairro;
    private String nomeCategoria;
    private String nomeStatus;
    private String midia1;
    private String midia2;
    private String midia3;
    private String midia4;
    private Integer existe;
    private Integer naoExiste;
    public static ArrayList<Denuncia> param = new ArrayList<Denuncia>();
    public static double auxCodDenuncia;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        sharedPreferences = getContext().getSharedPreferences(getString(R.string.tipoMapa), Context.MODE_PRIVATE);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addOnConnectionFailedListener(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMapAux = googleMap;

        //Toast.makeText(getContext(), "A", Toast.LENGTH_SHORT).show();
        MapaProviderFragment.Task task = new MapaProviderFragment.Task();
        task.execute();

        //espera 2 segundos (pra dar tempo dos marcadores serem carregados e listados no mapa)
        try {
            new Thread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carregarInformacoes(googleMapAux);
        
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void carregarInformacoes(GoogleMap googleMap){

        try {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            //Toast.makeText(getContext(), "Provider: " + provider, Toast.LENGTH_LONG).show();

            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true); //botão pra aumentar ou diminuir o zoom que fica no canto inferior direito
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true); //habilita a bússola (fica visível somente quando muda a direção do mapa)

            for (Denuncia denuncia : param){
                //Toast.makeText(getContext(), "Foi" + denuncia.getLatitude(), Toast.LENGTH_SHORT).show();

                //LatLng sydney = new LatLng(-20.831404, -41.174105);
                LatLng coordenada = new LatLng(denuncia.getLatitude(), denuncia.getLongitude());

                MarkerOptions marker = new MarkerOptions();

                marker.position(coordenada);
                marker.title(denuncia.getCategoria().toString()); // nome do marcador
                marker.snippet(denuncia.getDescricao()); //descrição do marcador

                //muda a cor do marcador de acordo com a situação do status
                if(Objects.equals(denuncia.getStatus().getNome(), "Não Concluída")){
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                } else if(Objects.equals(denuncia.getStatus().getNome(), "Concluída")){
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if(Objects.equals(denuncia.getStatus().getNome(), "Concluída por Negação Popular")){
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                } else if(Objects.equals(denuncia.getStatus().getNome(), "Em Análise")){
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                } else if(Objects.equals(denuncia.getStatus().getNome(), "Em Fase de Correção")){
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }

                auxCodDenuncia = denuncia.getLatitude();


                //Abre uma nova intent quando clica no balão de informações do marcador
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                    mudarActivity(marker);

                    }
                });

                mMap.addMarker(marker);


            }

        }catch(SecurityException ex){
            Log.e(TAG, "Error ", ex);
        }

    }

    public void mudarActivity(Marker marker){
        Intent irParaTelaDenunciaCompleta = new Intent(getContext(), ListarDenuncia.class); // ele só ta passando o código da última denuncia

        irParaTelaDenunciaCompleta.putExtra("DenunciaLat", marker.getPosition().latitude); //passa a latitude da denúncia como parâmetro
        irParaTelaDenunciaCompleta.putExtra("DenunciaLong", marker.getPosition().longitude); //passa a longitude da denúncia como parâmetro
        startActivity(irParaTelaDenunciaCompleta);
    }

    public void carregarMapa(){
        try{

            //pega a localização atual e exibe (através da bolinha azul)
            Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            LatLng minhaPosicao = new LatLng(l.getLatitude(), l.getLongitude());

            String aux = sharedPreferences.getString(getString(R.string.tipoMapa), "Null");

            if(Objects.equals(aux, "Normal")){
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else if(Objects.equals(aux, "Satélite")){
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if(Objects.equals(aux, "Híbrido")){
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }


            mMap.moveCamera(CameraUpdateFactory.newLatLng(minhaPosicao)); //muda o foco do mapa para o marcador
            mMap.moveCamera(CameraUpdateFactory.zoomTo(16)); //muda o nível do zoom pra 16 (um pouco mais próximo "do chão")

            if(l != null){
                Log.i("LOG", "Latitude: "+ l.getLatitude());
                Log.i("LOG", "Longitude: "+ l.getLongitude());

            }

        }catch(SecurityException ex) {

        }
    }

    //LISTENER
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle + ")");


        carregarMapa();
        if(googleMapAux != null){
            carregarInformacoes(googleMapAux);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public View getInfoWindow(Marker marker) {

        Toast.makeText(getContext(), "Window", Toast.LENGTH_SHORT).show();

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        Toast.makeText(getContext(), "Contents", Toast.LENGTH_SHORT).show();

        return null;
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(getContext());

        InputStream is = null;
        String result = "";

        protected void onPreExecute() {

            progressDialog.setMessage("Listando Items...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    MapaProviderFragment.Task.this.cancel(true);
                }
            });

        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarDenuncia.php";

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
                Toast.makeText(getContext(), "Tente novamente.", Toast.LENGTH_LONG).show();
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
                    descricao = jsonObject.getString("descricao");
                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");
                    //auxAnonimato = jsonObject.getInt("anonimato");
                    complementoStatus = jsonObject.getString("complementoStatus");
                    nomeUsuario = jsonObject.getString("nomeUsuario");
                    nomeAdministrador = jsonObject.getString("nomeAdministrador");
                    nomeBairro = jsonObject.getString("nomeBairro");
                    nomeCategoria = jsonObject.getString("nomeCategoria");
                    nomeStatus = jsonObject.getString("nomeStatus");
                    midia1 = jsonObject.getString("midia1");
                    midia2 = jsonObject.getString("midia2");
                    midia3 = jsonObject.getString("midia3");
                    midia4 = jsonObject.getString("midia4");
                    existe = jsonObject.getInt("existe");
                    naoExiste = jsonObject.getInt("naoExiste");

                    Categoria categoria = new Categoria();
                    categoria.setNome(nomeCategoria);

                    domain.model.Status status = new domain.model.Status();
                    status.setNome(nomeStatus);

                    Bairro bairro = new Bairro();
                    bairro.setNome(nomeBairro);

                    Administrador administrador = new Administrador();
                    administrador.setNome(nomeAdministrador);

                    Usuario usuario = new Usuario();
                    usuario.setNome(nomeUsuario);

                    Denuncia denuncia = new Denuncia();
                    denuncia.setCodDenuncia(codDenuncia);
                    denuncia.setDescricao(descricao);
                    denuncia.setLatitude(latitude);
                    denuncia.setLongitude(longitude);
                    denuncia.setComplementoStatus(complementoStatus);
                    denuncia.setMidia1(midia1);
                    denuncia.setMidia1(midia2);
                    denuncia.setMidia1(midia3);
                    denuncia.setMidia1(midia4);
                    denuncia.setExiste(existe);
                    denuncia.setNaoExiste(naoExiste);
                    denuncia.setUsuario(usuario);
                    denuncia.setAdministrador(administrador);
                    denuncia.setBairro(bairro);
                    denuncia.setCategoria(categoria);
                    denuncia.setStatus(status);

                    /*if (auxAnonimato == 1){
                        anonimato = true;
                    } else{
                        anonimato = false;
                    }

                    denuncia.setAnonimato(anonimato);*/

                    param.add(denuncia);

                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
        }

    }

}

package domain.fragment;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import domain.controller.ListarDenuncia;
import domain.controller.TipoMapa;


public class MapaProviderFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
            GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

        private GoogleMap mMap;
        private LocationManager locationManager;
        private static final String TAG = "ExemploProvFragmentV1";
        private GoogleApiClient mGoogleApiClient;
        private Location location;
        private TipoMapa tipoMapa = new TipoMapa();
        private ListarDenuncia listarDenuncia = new ListarDenuncia();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getMapAsync(this);

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

            try {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                Criteria criteria = new Criteria();

                String provider = locationManager.getBestProvider(criteria, true);

                //Toast.makeText(getActivity(), "Provider: " + provider, Toast.LENGTH_LONG).show();

                mMap = googleMap;
                mMap.getUiSettings().setZoomControlsEnabled(true); //botão pra aumentar ou diminuir o zoom que fica no canto inferior direito
                mMap.setMyLocationEnabled(true);

                //location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


                /*LatLng sydney = new LatLng(-34, 151);

                MarkerOptions marker = new MarkerOptions();

                marker.position(sydney);
                marker.title("Marcador em Sydney");

                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
            }catch(SecurityException ex){
                Log.e(TAG, "Error ", ex);
            }

            // Add a marker in Sydney and move the camera

        }

        @Override
        public void onMapClick(LatLng latLng) {

        }

        @Override
        public void onResume(){
            super.onResume();


        }

        public void carregarMapa(){
            try{

                //pega a localização atual e marca no mapa

                Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                LatLng sydney = new LatLng(l.getLatitude(), l.getLongitude());

                MarkerOptions marker = new MarkerOptions();

                marker.position(sydney);
                marker.title("Marcador");

                //Toast.makeText(getContext(), "Estilo de mapa selecionado: "+ configuracoes.getItemEscolhido(), Toast.LENGTH_SHORT).show();

                if(tipoMapa.getTipoMapa() == "Normal" || tipoMapa.getTipoMapa() == null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                } else if(tipoMapa.getTipoMapa() == "Satélite"){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else if(tipoMapa.getTipoMapa() == "Híbrido"){
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }

                //mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); //muda o foco do mapa para o marcador
                mMap.moveCamera(CameraUpdateFactory.zoomTo(16)); //muda o nível do zoom pra 16 (um pouco mais próximo "do chão"

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

        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.i("LOG", "onConnectionSuspended(" + i + ")");
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    }

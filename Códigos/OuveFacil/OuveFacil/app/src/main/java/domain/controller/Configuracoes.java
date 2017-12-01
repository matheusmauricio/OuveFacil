package domain.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mm.ouvefacil.R;

import java.util.ArrayList;

public class Configuracoes extends AppCompatActivity {

    private Spinner spinnerMapa;
    private ArrayList<String> arrayMapa = new ArrayList<String>();
    //public static final TipoMapa tipoMapa = new TipoMapa();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        spinnerMapa = (Spinner) findViewById(R.id.spinnerTipoMapa);

        sharedPreferences = this.getSharedPreferences(getString(R.string.tipoMapa), Context.MODE_PRIVATE);

        estilosDeMapa();

        ArrayAdapter<String> ad = new ArrayAdapter<String>(Configuracoes.this, android.R.layout.simple_spinner_dropdown_item, arrayMapa);
        spinnerMapa.setAdapter(ad);

        spinnerMapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //tipoMapa.setTipoMapa((String) spinnerMapa.getItemAtPosition(position));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.tipoMapa), (String) spinnerMapa.getItemAtPosition(position));
                editor.commit();

                //Toast.makeText(Configuracoes.this, "Estilo de mapa selecionado: "+ tipoMapa.getTipoMapa(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });
    }

    public void estilosDeMapa(){
        arrayMapa.add("Normal");
        arrayMapa.add("Satélite");
        arrayMapa.add("Híbrido");
    }

}

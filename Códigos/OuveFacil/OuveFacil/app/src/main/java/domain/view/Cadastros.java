package domain.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mm.ouvefacil.R;

public class Cadastros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);


        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });

    }


    public void cadastrarAdministrador(View view){
        Intent irParaTelaListaAdministrador = new Intent(this, FormularioAdministrador.class);

        startActivity(irParaTelaListaAdministrador);
    }

    public void cadastrarUsuario(View view){
        Intent irParaTelaListaUsuario = new Intent(this, FormularioUsuario.class);

        startActivity(irParaTelaListaUsuario);
    }

    public void cadastrarUf(View view){
        Intent irParaTelaListaUf = new Intent(this, FormularioUf.class);

        startActivity(irParaTelaListaUf);
    }

    public void cadastrarCidade(View view){
        Intent irParaTelaListaCidade = new Intent(this, FormularioCidade.class);

        startActivity(irParaTelaListaCidade);
    }

    public void cadastrarBairro(View view){
        Intent irParaTelaListaBairro = new Intent(this, FormularioBairro.class);

        startActivity(irParaTelaListaBairro);
    }

    public void cadastrarCategoria(View view){
        Intent irParaTelaListaCategoria = new Intent(this, FormularioCategoria.class);

        startActivity(irParaTelaListaCategoria);
    }

    public void cadastrarSubCategoria(View view){
        Intent irParaTelaListaSubCategoria = new Intent(this, FormularioSubCategoria.class);

        startActivity(irParaTelaListaSubCategoria);
    }

}

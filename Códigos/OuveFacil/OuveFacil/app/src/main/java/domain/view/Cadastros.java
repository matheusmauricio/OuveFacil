package domain.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mm.ouvefacil.R;

import domain.controller.InserirAdministrador;
import domain.controller.InserirBairro;
import domain.controller.InserirCategoria;
import domain.controller.InserirCidade;
import domain.controller.InserirSubCategoria;
import domain.controller.InserirUf;
import domain.controller.InserirUsuario;

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
        Intent irParaTelaListaAdministrador = new Intent(this, InserirAdministrador.class);

        startActivity(irParaTelaListaAdministrador);
    }

    public void cadastrarUsuario(View view){
        Intent irParaTelaListaUsuario = new Intent(this, InserirUsuario.class);

        startActivity(irParaTelaListaUsuario);
    }

    public void cadastrarUf(View view){
        Intent irParaTelaListaUf = new Intent(this, InserirUf.class);

        startActivity(irParaTelaListaUf);
    }

    public void cadastrarCidade(View view){
        Intent irParaTelaListaCidade = new Intent(this, InserirCidade.class);

        startActivity(irParaTelaListaCidade);
    }

    public void cadastrarBairro(View view){
        Intent irParaTelaListaBairro = new Intent(this, InserirBairro.class);

        startActivity(irParaTelaListaBairro);
    }

    public void cadastrarCategoria(View view){
        Intent irParaTelaListaCategoria = new Intent(this, InserirCategoria.class);

        startActivity(irParaTelaListaCategoria);
    }

    public void cadastrarSubCategoria(View view){
        Intent irParaTelaListaSubCategoria = new Intent(this, InserirSubCategoria.class);

        startActivity(irParaTelaListaSubCategoria);
    }

}

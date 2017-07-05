package domain.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mm.ouvefacil.R;

import domain.controller.RelatorioAvaliacaoPorBairro;
import domain.controller.RelatorioDenunciasNaoConcluidasBairro;
import domain.controller.RelatorioDenunciasConcluidasENaoConcluidas;
import domain.controller.RelatorioDenunciasNaoConcluidas;

public class Relatorios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }

        });
    }

    public void avaliacaoPorBairro(View view){
        Intent irParaTelaRelatorioAvaliacaoPorBairro = new Intent(this, RelatorioAvaliacaoPorBairro.class);

        startActivity(irParaTelaRelatorioAvaliacaoPorBairro);
    }

    public void denunciasNaoConcluidas (View view){
        Intent irParaTelaRelatorioDenunciasNaoConcluidas = new Intent(this, RelatorioDenunciasNaoConcluidas.class);

        startActivity(irParaTelaRelatorioDenunciasNaoConcluidas);
    }

    public void denunciasNaoConcluidasBairro (View view){
        Intent irParaTelaRelatorioDenunciasNaoConcluidasBairro = new Intent(this, RelatorioDenunciasNaoConcluidasBairro.class);

        startActivity(irParaTelaRelatorioDenunciasNaoConcluidasBairro);
    }

    public void denunciasConcluidasENaoConcluidas (View view){
        Intent irParaTelaRelatorioDenunciasConcluidasENaoConcluidas = new Intent(this, RelatorioDenunciasConcluidasENaoConcluidas.class);

        startActivity(irParaTelaRelatorioDenunciasConcluidasENaoConcluidas);
    }
}

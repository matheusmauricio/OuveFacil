package com.mm.ouvefacil;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import domain.controller.Configuracoes;
import domain.controller.InserirDenuncia;
import domain.controller.MinhasDenuncias;
import domain.fragment.MapaProviderFragment;
import domain.view.Cadastros;
import domain.view.Relatorios;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private static int auxPermissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent irParaConfiguracoes = new Intent(this, Configuracoes.class);
            startActivity(irParaConfiguracoes);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment, String name){
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, fragment, name);

        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // executa alguma ação quando o usuário clica em dar a permissão
                    if(auxPermissao == 1){//auxPermissao e uma variável private static int (private static int auxPermissao;)
                        showFragment(new MapaProviderFragment(), "MapaProviderFragment");
                    } else{
                        Intent irParaTelaInserirDenuncia = new Intent(this, InserirDenuncia.class);
                        startActivity(irParaTelaInserirDenuncia);
                    }

                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mapa) {
            boolean permissionGranted = android.support.v4.app.ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if(permissionGranted) {
                showFragment(new MapaProviderFragment(), "MapaProviderFragment");
            } else {
                auxPermissao = 1;
                android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION }, 200);
            }

        } else if (id == R.id.nav_denuncia) {
            boolean permissionGranted = android.support.v4.app.ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if(permissionGranted) {
                Intent irParaTelaInserirDenuncia = new Intent(this, InserirDenuncia.class);
                startActivity(irParaTelaInserirDenuncia);
            } else {
                auxPermissao = 2;
                android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION }, 200);
            }

        } else if (id == R.id.nav_minhas_denuncias) {
            boolean permissionGranted = android.support.v4.app.ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if(permissionGranted) {
                Intent irParaTelaMinhasDenuncias = new Intent(this, MinhasDenuncias.class);
                startActivity(irParaTelaMinhasDenuncias);
            } else {
                auxPermissao = 2;
                android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }

        } else if (id == R.id.nav_manage) {
            Intent irPaginaInternet = new Intent(Intent.ACTION_VIEW);

            Uri localSite = Uri.parse("http://google.com.br");

            irPaginaInternet.setData(localSite);

            startActivity(irPaginaInternet);

        } else if (id == R.id.nav_cadastros) {
            Intent irParaTelaCadastros = new Intent(this, Cadastros.class);
            startActivity(irParaTelaCadastros);

        } else if (id == R.id.nav_relatorios) {
            Intent irParaTelaRelatorios = new Intent(this, Relatorios.class);
            startActivity(irParaTelaRelatorios);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

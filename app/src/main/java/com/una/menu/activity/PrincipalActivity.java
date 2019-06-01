package com.una.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.una.menu.R;
import com.una.menu.fragment.BuscaFragment;
import com.una.menu.fragment.LanchonetesFragment;
import com.una.menu.fragment.ProdutosFragment;
import com.una.menu.fragment.ProdutosViewFragment;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    // Variaveis
    private FrameLayout frameContainer;
    private TextView textNomeUsuario;
    private TextView textEmailUsuario;


    // 2019 - Login Google ------------------------------------------------------------------------------------------------
    private GoogleApiClient googleApiClient;
    private ImageView imageViewFotoUsuario;
    private TextView textViewNomeUsuario;
    private  TextView textViewEmailUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // 2019 - Login Google ------------------------------------------------------------------------------------------------
        imageViewFotoUsuario = findViewById(R.id.imageViewFotoUsuario);
        textNomeUsuario = findViewById(R.id.textViewNomeUsuario);
        textViewEmailUsuario = findViewById(R.id.textViewEmailUsuario);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // CÓDIGO ANTIGO (Primeira Versão do Projeto)----------------------------------------------------------------------------------------------------------------

        // Seta título para Activity
        this.setTitle("Menu");

        // Variaveis
        frameContainer = findViewById(R.id.frameContainer);
        //textNomeUsuario = findViewById(R.id.textNomeUsuario);
        //textEmailUsuario = findViewById(R.id.textEmailUsuario);


        // Chama o Fragment de Busca
        BuscaFragment buscaFragment = new BuscaFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, buscaFragment);
        fragmentTransaction.commit();

        // Recebe id e nome do Usuario
        int idUsuario = getIntent().getExtras().getInt("id_usuario");
        String nomeUsuario = getIntent().getExtras().getString("nome_usuario");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* BOTÃO FLUTUANTE
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
        getMenuInflater().inflate(R.menu.principal, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } */

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_lanchonete) {

            /*Intent abreCadastroLanch = new Intent(getApplicationContext(), CadLanchActivity.class);
            startActivity(abreCadastroLanch);*/

            LanchonetesFragment lanchonetesFragment = new LanchonetesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, lanchonetesFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_produto) {

            /*Intent abreCadastroLanch = new Intent(getApplicationContext(), CadLanchActivity.class);
            startActivity(abreCadastroLanch);*/

            ProdutosViewFragment produtosViewFragment = new ProdutosViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, produtosViewFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_busca) {

            BuscaFragment buscaFragment = new BuscaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, buscaFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_logOut) {
            logOut();

        } else if(id == R.id.nav_revoke) {
            revoke();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 2019 - Login Google ------------------------------------------------------------------------------------------------
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void logOut() {

    }

    private void revoke() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            textViewEmailUsuario.setText(account.getDisplayName());
            textViewEmailUsuario.setText(account.getEmail());
        } else {
            //goLogInScreem();
        }
    }

    private void goLogInScreem() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

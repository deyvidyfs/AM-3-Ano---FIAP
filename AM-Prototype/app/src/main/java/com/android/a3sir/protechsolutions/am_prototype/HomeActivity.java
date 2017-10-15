package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private TextView txtNomeHome;
    private TextView txtSaldoHome;
    private TextView txtInvestHome;

    private Usuario usuario;
    private Usuario usuarioFirebaseDB;
    private String usuarioSrc;
    private UsuarioDAO dao;

    private boolean localUser;
    private boolean usuarioSincronizado;
    private int idUsuarioFirebase;
    private int databaseLength;
    private boolean novoUsuario;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Home" ;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNomeHome = (TextView) findViewById(R.id.txtNomeHome);
        txtSaldoHome = (TextView) findViewById(R.id.txtSaldoHome);
        txtInvestHome = (TextView) findViewById(R.id.txtInvestHome);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        //chamarAlerta("Usuario Autenticado: ",mAuth.getCurrentUser().getEmail());

        mDatabase = FirebaseDatabase.getInstance().getReference();


        Intent i = getIntent();
        usuarioSrc = i.getStringExtra("emailUsuario");
        dao = new UsuarioDAO(this);

        usuarioSincronizado = false;

        buscarUsuario();


    }

    @Override
    protected void onRestart(){
        super.onRestart();
        usuario = dao.buscarUsuario(usuarioSrc);

        txtNomeHome.setText(usuario.getNomeUsuario());
        txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
        txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());
    }

    @Override
    protected void onDestroy(){


        mAuth.signOut();

        super.onDestroy();
    }


    protected void investir (View v){
        Intent intent = new Intent(this, InvestirActivity.class);
        intent.putExtra("saldo",usuario.getSaldoUsuario());
        intent.putExtra("idUsuario",usuario.getIdUsuario());
        startActivity(intent);
    }

    protected void resgatarInvestimentos (View v){
        Intent intent = new Intent(this, ResgatarInvestimentosActivity.class);
        startActivity(intent);
    }

    protected void meusInvestimentos (View v){
        Intent intent = new Intent(this, InvestimentosActivity.class);
        intent.putExtra("saldo",usuario.getSaldoUsuario());
        intent.putExtra("idUsuario",usuario.getIdUsuario());
        startActivity(intent);
    }

    protected void acessarChatbot (View v){

        if(idUsuarioFirebase == 0){
            usuario.setIdUsuario(databaseLength);
            dao.atualizar(usuario);
            mDatabase.child("usuarios").child(Integer.toString(usuario.getIdUsuario())).setValue(usuario);
        }else{
            if(validarUsuarios() == false){

                usuario.setIdUsuario(idUsuarioFirebase);
                dao.atualizar(usuario);

                //chamarAlerta("USER ID",usuario.getIdFirebaseUsuario());
                Map<String,Object> usuarioValues = usuario.toMap();

                Map<String,Object> childUpdate = new HashMap<String,Object>();
                childUpdate.put("/usuarios/" + Integer.toString(usuario.getIdUsuario()),usuarioValues);

                mDatabase.updateChildren(childUpdate);
            }
        }


        /*
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
        */
    }

    protected void acessarCadastro (View v){
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra("origem","Menu");
        startActivity(intent);
    }

    protected void carregarSaldo (View v){
        Intent intent = new Intent(this, SaldoActivity.class);
        intent.putExtra("emailUsuario",usuario.getEmailUsuario());
        startActivity(intent);
    }

    protected void buscarUsuario(){

        RecuperarUsuarioTask task = new RecuperarUsuarioTask();

        try {
            //chamarAlerta("test","after here");
            usuario = dao.buscarUsuario(usuarioSrc);
            localUser = true;


            if(usuario.getIdFirebaseUsuario()== null) {
                usuario.setIdFirebaseUsuario(mAuth.getCurrentUser().getUid().toString());
                dao.atualizar(usuario);
                usuarioFirebaseDB = usuario;
                novoUsuario = true;
                txtNomeHome.setText(usuario.getNomeUsuario());
                txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
                txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());

                task.execute();

            }else{

                if(usuarioSincronizado == false){
                    novoUsuario = false;
                    task.execute();

                }else{
                    //chamarAlerta("Found", "User: " + usuario.toString());
                    txtNomeHome.setText(usuario.getNomeUsuario());
                    txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
                    txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());
                }

            }

        }catch(CursorIndexOutOfBoundsException e){
            //chamarAlerta("Not Found", e.toString());
            localUser = false;
            novoUsuario = false;

            task.execute();



        }catch(Exception e){
            chamarAlerta("Erro", e.toString());
        }
    }

    protected boolean validarUsuarios(){
        if(!usuario.getCpfUsuario().equals(usuarioFirebaseDB.getCpfUsuario()) ||
                !usuario.getEmailUsuario().equals(usuarioFirebaseDB.getEmailUsuario()) ||
                !usuario.getIdFirebaseUsuario().equals(usuarioFirebaseDB.getIdFirebaseUsuario()) ||
                usuario.getInvestimentoUsuario() != (usuarioFirebaseDB.getInvestimentoUsuario()) ||
                !usuario.getNomeUsuario().equals(usuarioFirebaseDB.getNomeUsuario()) ||
                usuario.getSaldoUsuario() != (usuarioFirebaseDB.getSaldoUsuario())) {

                return false;
        }else{
            return true;
        }
    }
    protected void chamarAlerta(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.show();
    }

    private class RecuperarUsuarioTask extends AsyncTask<Void,Void,String>{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeActivity.this,
                    "Aguarde","Comunicando com o servidor...");
        }

        @Override
        protected String doInBackground(Void...params){
            try{
            URL url = new URL("https://fiap-am-prototype-3ano-dlfs.firebaseio.com/usuarios.json");
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");

                if (connection.getResponseCode() == 200){
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(
                                    connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String linha;
                    while ((linha = reader.readLine()) != null){
                        builder.append(linha);
                    }
                    connection.disconnect();
                    return builder.toString();
                }

            }catch (Exception e){
                chamarAlerta("Erro", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            progressDialog.dismiss();
            String emailUsuario;
            if(s != null){
                try{
                    JSONArray json = new JSONArray(s);

                    databaseLength = json.length();

                    if(novoUsuario != true){
                        for (int i = (databaseLength-1); i > 0; i--){
                            Object item = json.get(i);

                            if(item != JSONObject.NULL){
                                if(item instanceof JSONObject){

                                    try {
                                        emailUsuario = ((JSONObject) item).getString("emailUsuario");

                                        //chamarAlerta("Nome item",((JSONObject) item).getString("nome"));
                                        if(emailUsuario.equals(usuarioSrc)){
                                            usuarioFirebaseDB = new Usuario();
                                            usuarioFirebaseDB.setEmailUsuario(emailUsuario);
                                            usuarioFirebaseDB.setNomeUsuario(((JSONObject) item).getString("nomeUsuario"));
                                            usuarioFirebaseDB.setCpfUsuario(((JSONObject) item).getString("cpfUsuario"));
                                            usuarioFirebaseDB.setSaldoUsuario(((JSONObject) item).getLong("saldoUsuario"));
                                            usuarioFirebaseDB.setInvestimentoUsuario(((JSONObject) item).getLong("investimentoUsuario"));
                                            usuarioFirebaseDB.setIdFirebaseUsuario(((JSONObject) item).getString("idFirebaseUsuario"));
                                            usuarioFirebaseDB.setIdUsuario(i);
                                            idUsuarioFirebase = i;
                                            break;

                                        }
                                    }catch (Exception e){
                                        chamarAlerta("Erro", e.toString());
                                    }

                                }
                            }
                        }

                        if(localUser == false){
                            dao.adicionarUsuario(usuarioFirebaseDB);

                        }else{
                            if(validarUsuarios() == false){
                                dao.atualizar(usuarioFirebaseDB);
                            }
                        }

                        buscarUsuario();
                    }else{
                       // mDatabase.child("usuarios").child(Integer.toString(databaseLength)).setValue(usuario);
                        novoUsuario = false;
                    }


                    usuarioSincronizado = true;




                    //
                }catch(Exception e){
                    chamarAlerta("Erro", e.toString());
                }
            }



        }
    }



}

//Old Code

//mAuth.getCurrentUser().getEmail();
        /*
        Intent i = getIntent();
        usuarioSrc = i.getStringExtra("usuario");
        dao = new UsuarioDAO(this);

        usuario = dao.buscarUsuario(usuarioSrc);


        txtNomeHome.setText(usuario.getNomeUsuario());
        txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
        txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());
        */

            /*
    private class GravarUsuarioTask extends AsyncTask<String,Void,Integer>{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
     //       progressDialog = ProgressDialog.show(HomeActivity.this,
        //            "Aguarde","Enviando dados..");
        }

        protected Integer doInBackground(String...params){

            try{
                URL url = new URL("https://fiap-am-prototype-3ano-dlfs.firebaseio.com/usuarios.json");
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");

                JSONStringer json = new JSONStringer();
                json.object();
                json.key("cpf").value(params[0]);
                json.key("email").value(params[1]);
                json.key("idfirebaseuser").value(params[2]);
                json.key("investimentos").value(params[3]);
                json.key("nome").value(params[4]);
                json.key("saldo").value(params[5]);
                json.endObject();

                //Enviar os dados para o servidor
                OutputStreamWriter writer =
                        new OutputStreamWriter(connection.getOutputStream());
                writer.write(json.toString());
                writer.close();

                //Retorna o status HTTP
               chamarAlerta("Response!", "Cod: " + connection.getResponseCode());
                return connection.getResponseCode();


            }catch (Exception e){
                e.printStackTrace();
            }

            return 0;

        }

        @Override
        protected void onPostExecute(Integer status) {
        //    progressDialog.dismiss();
            //valida  status do HTTP (201 Created)

            if (status == 200){
                Toast.makeText(HomeActivity.this,"Sucesso!",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(HomeActivity.this,"Erro!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    */

                    /* USAR PRA TRANSAÇÃO
        if(validarUsuarios() == false){
            mDatabase.child("usuarios").child(Integer.toString(databaseLength)).setValue(usuario);
        }

        */
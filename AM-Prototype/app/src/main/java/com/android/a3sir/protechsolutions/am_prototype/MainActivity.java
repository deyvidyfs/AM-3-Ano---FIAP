package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    private EditText edtEmailUsuario;
    private EditText edtSenhaUsuario;

    private ProgressDialog progress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailUsuario = (EditText) findViewById(R.id.edtEmailMain);
        edtSenhaUsuario = (EditText) findViewById(R.id.edtSenhaMain);

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
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    protected void novoCadastro(View v){
        Intent intent = new Intent(this,CadastroActivity.class);
        intent.putExtra("origem","Main");
        startActivity(intent);
    }

    protected void login (View v){

        String emailUsuario = edtEmailUsuario.getText().toString();
        String senhaUsuario = edtSenhaUsuario.getText().toString();
        UsuarioDAO dao = new UsuarioDAO(this);
        Usuario usuario;


        if(validarCampos(emailUsuario,senhaUsuario)){

            try{
                progress = ProgressDialog.show(this,"Aguarde","Chamando o servidor");

                mAuth.signInWithEmailAndPassword(emailUsuario, senhaUsuario)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(i);

                                }else{
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());

                                    try
                                    {
                                        throw task.getException();
                                    }

                                    // if user enters wrong password.
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d(TAG, "onComplete: malformed_email");

                                        // TODO: Take your action
                                        chamarAlerta("Erro!", "E-mail ou Senha Inválidos");

                                    }
                                    catch (FirebaseAuthInvalidUserException unexistEmail)
                                    {
                                        Log.d(TAG, "onComplete: unexist_email");

                                        // TODO: Take your action
                                        chamarAlerta("Erro!", "Usuario não cadastrado! Por favor cadastre-se antes de entrar!");

                                    }
                                    catch (Exception e)
                                    {
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                    }
                                }

                                // ...
                            }
                        });

                        progress.dismiss();
            }catch (Exception e){

                chamarAlerta("Erro!", e.toString());
            }
        }
    }

    protected void esqueciSenha (View v){
        Intent intent = new Intent (this, EsqueciActivity.class);
        startActivity(intent);
    }


    public void chamarAlerta(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.show();
    }

    protected boolean validarCampos(String emailUsuario, String senhaUsuario){
        if(emailUsuario.equals("")|| senhaUsuario.equals("")) {
            chamarAlerta("Erro!", "Por favor digite um usuário e senha");
            return false;
        }else{
            if(!emailUsuario.contains("@")) {
                chamarAlerta("Erro!", "Por favor digite um e-mail válido");
                return false;

            }else{
                if(senhaUsuario.length() < 6){
                    chamarAlerta("Erro!", "Por favor digite uma senha válida");
                    return false;
                }else{
                    return true;
                }
            }
        }
    }
}

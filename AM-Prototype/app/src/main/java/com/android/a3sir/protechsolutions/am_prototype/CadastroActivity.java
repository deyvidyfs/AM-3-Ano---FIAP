package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeCadastro;
    private EditText edtEmailCadastro;
    private EditText edtCpfCadastro;
    private EditText edtSenhaCadastro;
    private TextView txtTituloCadasto;
    private Button btnCadastrarCadastro;

    private static final String TAG = "CadastroActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNomeCadastro = (EditText) findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = (EditText) findViewById(R.id.edtEmailCadastro);
        edtCpfCadastro = (EditText) findViewById(R.id.edtCPFCadastro);
        edtSenhaCadastro = (EditText) findViewById(R.id.edtSenhaCadastro);
        txtTituloCadasto = (TextView) findViewById(R.id.txtTituloCadastro) ;
        btnCadastrarCadastro = (Button) findViewById(R.id.btnCadastrarCadastro);

        Intent i = getIntent();
        String origem = i.getStringExtra("origem");

        colocarNomesDinamicos(origem);

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

    protected void cadastrarUsuario(View v){

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(edtNomeCadastro.getText().toString());
        usuario.setEmailUsuario(edtEmailCadastro.getText().toString());
        usuario.setCpfUsuario(edtCpfCadastro.getText().toString());
        usuario.setSenhaUsuario(edtSenhaCadastro.getText().toString());

        UsuarioDAO dao = new UsuarioDAO(this);
        long linhaUsuario;


        if(validarCampos(usuario)){
            try{


                /*linhaUsuario = dao.adicionarUsuario(usuario);

                if(linhaUsuario == -1)
                {
                    chamarAlerta("Erro!","Usuário já cadastrado!");
                }else{
                */
                    mAuth.createUserWithEmailAndPassword(usuario.getEmailUsuario(),usuario.getSenhaUsuario())
                        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    chamarAlerta("Sucesso!","Usuário adicionado com sucesso!");
                                }else{
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    chamarAlerta("Erro!","Usuário já cadastrado!");
                                }

                                // ...
                            }
                        });


                   // chamarAlerta("Sucesso!","Usuário adicionado com sucesso!");
                //}

                //this.finish();
            }catch (Exception e){
            //chamarAlerta("Erro!", "Ocorreu um erro" + e.toString());
        }
        }


    }

    protected boolean validarCampos(Usuario usuario){
        if(usuario.getNomeUsuario().equals("") && usuario.getEmailUsuario().equals("") && usuario.getCpfUsuario().equals("") && usuario.getSenhaUsuario().equals("")){
            chamarAlerta("Erro!","Por favor preencha todos os campos");
        }else{
            if(usuario.getNomeUsuario().equals("")) {
                chamarAlerta("Erro!", "Por favor digite um nome!");
            }else{
                if(usuario.getEmailUsuario().equals("")){
                    chamarAlerta("Erro!", "Por favor digite um email!");
                }else{
                    if(usuario.getCpfUsuario().equals("")){
                        chamarAlerta("Erro!", "Por favor digite um CPF!");
                    }else{
                        if(usuario.getSenhaUsuario().equals("")) {
                            chamarAlerta("Erro!", "Por favor digite uma senha!");
                        }else {
                            if (!usuario.getEmailUsuario().contains("@")) {
                                chamarAlerta("Erro!", "Por favor digite um e-mail válido");
                            } else {
                                if (usuario.getSenhaUsuario().length() < 6) {
                                    chamarAlerta("Erro!", "Por favor digite uma senha com mais de 6 caracteres");
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private void chamarAlerta(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(true);

        if(titulo.equals("Sucesso!")){
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                            finish();
                        }
                    });
        }else{
            builder.setPositiveButton("OK",null);
        }

        builder.show();
    }

    private void colocarNomesDinamicos(String origem){
        if(origem.equals("Main")){
            txtTituloCadasto.setText("Novo Cadastro:");
            btnCadastrarCadastro.setText("Cadastrar");
        }else{
            if(origem.equals("Menu")){
                txtTituloCadasto.setText("Editar Cadastro:");
                btnCadastrarCadastro.setText("Atualizar");

                edtCpfCadastro.setFocusable(false);
                edtCpfCadastro.setInputType(InputType.TYPE_NULL);
                edtCpfCadastro.setFocusable(false);
                edtCpfCadastro.setInputType(InputType.TYPE_NULL);
            }
        }
    }
}

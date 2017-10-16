package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imgUserCadastro;

    private static final String TAG = "CadastroActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private UsuarioDAO dao;
    private Usuario usuario;
    private String usuarioSrc;

    private String origem;

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
        imgUserCadastro = (ImageView) findViewById(R.id.imgUserCadastro);

        Intent i = getIntent();
        origem = i.getStringExtra("origem");
        usuarioSrc = i.getStringExtra("emailUsuario");

        colocarNomesDinamicos();

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

        if(origem.equals("Menu")){
            editarDados();
        }

    }

    protected void cadastrarUsuario(View v){

        if(origem.equals("Main")){
            String nomeUsuario = edtNomeCadastro.getText().toString();
            String emailUsuario = edtEmailCadastro.getText().toString();
            String cpfUsuario = edtCpfCadastro.getText().toString();
            String senhaUsuario = edtSenhaCadastro.getText().toString();

            usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setEmailUsuario(emailUsuario);
            usuario.setCpfUsuario(cpfUsuario);

            dao = new UsuarioDAO(this);

            if(validarCampos(usuario,senhaUsuario)){
                try{
                    mAuth.createUserWithEmailAndPassword(emailUsuario,senhaUsuario)
                            .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                        chamarAlerta("Sucesso!","Usuário adicionado com sucesso!");
                                        dao.adicionarUsuario(usuario);
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




                }catch (Exception e){

                }
            }
        }else{
            this.finish();
        }


    }

    private void editarDados(){
        UsuarioDAO dao = new UsuarioDAO(this);
        usuario = dao.buscarUsuario(usuarioSrc);

        edtNomeCadastro.setText(usuario.getNomeUsuario());
        edtNomeCadastro.setFocusable(false);

        edtEmailCadastro.setText(usuario.getEmailUsuario());
        edtEmailCadastro.setFocusable(false);

        edtCpfCadastro.setText(usuario.getCpfUsuario());
        edtCpfCadastro.setFocusable(false);

        edtSenhaCadastro.setText("");
        edtSenhaCadastro.setFocusable(false);

        imgUserCadastro.setVisibility(View.VISIBLE);
    }

    protected boolean validarCampos(Usuario usuario, String senhaUsuario){
        if(usuario.getNomeUsuario().equals("") && usuario.getEmailUsuario().equals("") && usuario.getCpfUsuario().equals("") && senhaUsuario.equals("")){
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
                        if(senhaUsuario.equals("")) {
                            chamarAlerta("Erro!", "Por favor digite uma senha!");
                        }else {
                            if (!usuario.getEmailUsuario().contains("@")) {
                                chamarAlerta("Erro!", "Por favor digite um e-mail válido");
                            } else {
                                if (senhaUsuario.length() < 6) {
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

    private void colocarNomesDinamicos(){
        if(origem.equals("Main")){
            txtTituloCadasto.setText("Novo Cadastro:");
            btnCadastrarCadastro.setText("Cadastrar");
        }else{
            if(origem.equals("Menu")){
                txtTituloCadasto.setText("Editar Cadastro:");
                btnCadastrarCadastro.setText("Sair");

                edtCpfCadastro.setFocusable(false);
                edtCpfCadastro.setInputType(InputType.TYPE_NULL);
                edtCpfCadastro.setFocusable(false);
                edtCpfCadastro.setInputType(InputType.TYPE_NULL);
            }
        }
    }
}

//OLD CODE

//chamarAlerta("Erro!", "Ocorreu um erro" + e.toString());

// chamarAlerta("Sucesso!","Usuário adicionado com sucesso!");
//}

//this.finish();

                /*linhaUsuario = dao.adicionarUsuario(usuario);

                if(linhaUsuario == -1)
                {
                    chamarAlerta("Erro!","Usuário já cadastrado!");
                }else{
                */
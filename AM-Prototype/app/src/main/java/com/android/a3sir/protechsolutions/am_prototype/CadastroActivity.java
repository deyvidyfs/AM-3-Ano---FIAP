package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNomeCadastro;
    private EditText edtEmailCadastro;
    private EditText edtCpfCadastro;
    private EditText edtSenhaCadastro;
    private TextView txtTituloCadasto;
    private Button btnCadastrarCadastro;

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


    }

    protected void cadastrarUsuario(View v){
        String nomeCadastro = edtNomeCadastro.getText().toString();
        String emailCadastro = edtEmailCadastro.getText().toString();
        String cpfCadastro = edtCpfCadastro.getText().toString();
        String senhaCadastro = edtSenhaCadastro.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(nomeCadastro);
        usuario.setEmailUsuario(emailCadastro);
        usuario.setCpfUsuario(cpfCadastro);
        usuario.setSenhaUsuario(senhaCadastro);

        boolean validarVazio = validarVazio(usuario);

        UsuarioDAO dao = new UsuarioDAO(this);


        if(validarVazio){
            try{
                dao.adicionarUsuario(usuario);
                chamarAlerta("Sucesso!","Usuário adicionado com sucesso!");
                //this.finish();
            }catch (Exception e){
                chamarAlerta("Erro!", "Ocorreu um erro");
            }
        }


    }

    protected boolean validarVazio(Usuario usuario){
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
                        if(usuario.getSenhaUsuario().equals("")){
                            chamarAlerta("Erro!", "Por favor digite uma senha!");
                        }else{
                            return true;
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

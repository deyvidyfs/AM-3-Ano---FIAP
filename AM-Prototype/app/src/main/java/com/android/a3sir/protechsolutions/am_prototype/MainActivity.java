package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmailUsuario;
    private EditText edtSenhaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailUsuario = (EditText) findViewById(R.id.edtEmailMain);
        edtSenhaUsuario = (EditText) findViewById(R.id.edtSenhaMain);
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

        boolean validarVazio = validarVazio(emailUsuario,senhaUsuario);

        if(validarVazio){

            try{
                usuario = dao.buscarUsuario(emailUsuario);

                if(usuario.senhaUsuario.equals(senhaUsuario)){
                    Intent intent = new Intent (this,HomeActivity.class);
                    intent.putExtra("usuario",usuario.getEmailUsuario());
                    startActivity(intent);
                }else{
                    chamarAlerta("Erro!","Usuário ou senha incorretos.");
                }

            }catch (Exception e){
                chamarAlerta("Erro!", "Usuario não cadastrado! Por favor cadastre-se antes de entrar!");
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

    protected boolean validarVazio(String emailUsuario, String senhaUsuario){
        if(emailUsuario.equals("")|| senhaUsuario.equals("")){
            chamarAlerta("Erro!","Por favor digite um usuário e senha");
            return false;
        }else{
            return true;
        }
    }
}

package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        startActivity(intent);
    }

    protected void login (View v){

        String emailUsuario = edtEmailUsuario.getText().toString();
        String senhaUsuario = edtSenhaUsuario.getText().toString();

        /*
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Erro!");
        builder1.setMessage("A = " + emailUsuario + ".\nB = " + senhaUsuario);
        builder1.setCancelable(true);
        builder1.show();
        */


        if(emailUsuario.equals("")|| senhaUsuario.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro!");
            builder.setMessage("Por favor digite um usuário e senha.");
            builder.setCancelable(true);
            builder.show();
        }else{
            Intent intent = new Intent (this,HomeActivity.class);
            startActivity(intent);
        }
    }

    protected void esqueciSenha (View v){
        Intent intent = new Intent (this, EsqueciActivity.class);
        startActivity(intent);
    }
}

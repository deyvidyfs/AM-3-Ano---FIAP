package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

public class SaldoActivity extends AppCompatActivity {

    private Button btnCredito;
    private Button btnTransferencia;
    private Button btnBoleto;
    private TextView txtSaldo;
    private Usuario usuario;
    private UsuarioDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        btnCredito = (Button) findViewById(R.id.btnCartaoSaldo);
        btnTransferencia = (Button) findViewById(R.id.btnTransferenciaSaldo);
        btnBoleto = (Button) findViewById(R.id.btnBoletoSaldo);
        txtSaldo = (TextView) findViewById(R.id.txtSaldoSaldo);

        Intent i = getIntent();
        String usuarioSrc = i.getStringExtra("emailUsuario");
        dao = new UsuarioDAO(this);

        usuario = dao.buscarUsuario(usuarioSrc);

        txtSaldo.setText("RS " + usuario.getSaldoUsuario());
    }

    protected void recarregarSaldo(View v){



        //Montar Alerta:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o valor desejado");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                atualizarSaldo(input);
            }
        });
        builder.show();



    }

    protected void voltar(View v){

        this.finish();
    }

    protected void atualizarSaldo(EditText input){
        long saldoAdicionado;
        long saldoAntigo;
        long saldoNovo;
        saldoAdicionado = Long.parseLong(input.getText().toString());
        saldoAntigo = usuario.getSaldoUsuario();

        //RecarregarSaldo
        saldoNovo = saldoAntigo + saldoAdicionado;


        usuario.setSaldoUsuario(saldoNovo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("USERID");
        builder.setMessage(usuario.getIdFirebaseUsuario());
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.show();



        dao.atualizar(usuario);

        Toast.makeText(this,"Saldo atualizado com sucesso",Toast.LENGTH_LONG);

        txtSaldo.setText("RS " + saldoNovo);

    }
}

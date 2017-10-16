package com.android.a3sir.protechsolutions.am_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

public class InvestimentosActivity extends AppCompatActivity {

    protected TextView txtSaldoInvestimentos;
    protected TextView txtSaldoPoupancaInvestimentos;
    protected TextView txtSaldoTesouroInvestimentos;
    protected TextView txtSaldoCdbInvestimentos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);

        Usuario usuario;
        UsuarioDAO dao = new UsuarioDAO(this);;

        txtSaldoInvestimentos = (TextView) findViewById(R.id.txtSaldoInvestimentos);
        txtSaldoPoupancaInvestimentos = (TextView) findViewById(R.id.txtSaldoPoupancaInvestimentos);
        txtSaldoTesouroInvestimentos = (TextView) findViewById(R.id.txtSaldoTesouroInvestimentos);
        txtSaldoCdbInvestimentos = (TextView) findViewById(R.id.txtSaldoCdbInvestimentos);

        Intent i = getIntent();
        String usuarioSrc = i.getStringExtra("emailUsuario");
        usuario = dao.buscarUsuario(usuarioSrc);

        txtSaldoInvestimentos.setText("R$ " + usuario.getSaldoUsuario());
        txtSaldoPoupancaInvestimentos.setText("R$ " + usuario.getSaldoPoupancaUsuario());
        txtSaldoTesouroInvestimentos.setText("R$ " + usuario.getSaldoTesouroUsuario());
        txtSaldoCdbInvestimentos.setText("R$ " + usuario.getSaldoCdbUsuario());

    }

    protected void voltar(View v){

        this.finish();
    }
}

package com.android.a3sir.protechsolutions.am_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;
import com.android.a3sir.protechsolutions.am_prototype.dao.UsuarioDAO;

public class HomeActivity extends AppCompatActivity {

    private TextView txtNomeHome;
    private TextView txtSaldoHome;
    private TextView txtInvestHome;

    private Usuario usuario;
    private String usuarioSrc;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNomeHome = (TextView) findViewById(R.id.txtNomeHome);
        txtSaldoHome = (TextView) findViewById(R.id.txtSaldoHome);
        txtInvestHome = (TextView) findViewById(R.id.txtInvestHome);

        /*
        Intent i = getIntent();
        usuarioSrc = i.getStringExtra("usuario");
        dao = new UsuarioDAO(this);

        usuario = dao.buscarUsuario(usuarioSrc);


        txtNomeHome.setText(usuario.getNomeUsuario());
        txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
        txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());
        */
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        usuario = dao.buscarUsuario(usuarioSrc);

        txtNomeHome.setText(usuario.getNomeUsuario());
        txtSaldoHome.setText("Saldo Disponível: R$ " + usuario.getSaldoUsuario());
        txtInvestHome.setText("Saldo Investido Total: R$ " + usuario.getInvestimentoUsuario());
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
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
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

}

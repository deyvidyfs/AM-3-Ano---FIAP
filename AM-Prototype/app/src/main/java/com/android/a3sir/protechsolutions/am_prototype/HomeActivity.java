package com.android.a3sir.protechsolutions.am_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    protected void investir (View v){
        Intent intent = new Intent(this, InvestirActivity.class);
        startActivity(intent);
    }

    protected void resgatarInvestimentos (View v){
        Intent intent = new Intent(this, ResgatarInvestimentosActivity.class);
        startActivity(intent);
    }

    protected void meusInvestimentos (View v){
        Intent intent = new Intent(this, InvestimentosActivity.class);
        startActivity(intent);
    }

    protected void acessarChatbot (View v){
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
    }

    protected void acessarCadastro (View v){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    protected void carregarSaldo (View v){
        Intent intent = new Intent(this, SaldoActivity.class);
        startActivity(intent);
    }

}

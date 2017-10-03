package com.android.a3sir.protechsolutions.am_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InvestimentosActivity extends AppCompatActivity {

    protected TextView txtInvestimentosSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);

        txtInvestimentosSaldo = (TextView) findViewById(R.id.txtSaldoInvestimentos);

        Intent i = getIntent();
        Long saldoUsuario = i.getLongExtra("saldo",0);

        txtInvestimentosSaldo.setText("R$ " + saldoUsuario);
    }

    protected void voltar(View v){

        this.finish();
    }
}

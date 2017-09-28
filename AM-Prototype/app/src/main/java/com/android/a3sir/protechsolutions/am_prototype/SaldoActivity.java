package com.android.a3sir.protechsolutions.am_prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SaldoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
    }

    protected void voltar(View v){

        this.finish();
    }
}

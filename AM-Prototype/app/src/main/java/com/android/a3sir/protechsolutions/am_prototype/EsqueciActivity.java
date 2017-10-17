package com.android.a3sir.protechsolutions.am_prototype;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EsqueciActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Essa funcionalidade será implementada em breve!");
        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.show();
    }

    protected void resetarSenha (View v){
        this.finish();
    }
}

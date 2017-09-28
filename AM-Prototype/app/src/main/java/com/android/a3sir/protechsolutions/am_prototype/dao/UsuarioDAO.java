package com.android.a3sir.protechsolutions.am_prototype.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;

/**
 * Created by dlfs on 9/26/2017.
 */

public class UsuarioDAO extends SQLiteOpenHelper{

    private static final String BANCO = "usuarios";
    private static final int VERSAO = 1;

    public UsuarioDAO (Context context){
        super(context,BANCO,null,VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TB_USUARIO(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, EMAIL TEXT, CPF TEXT, SENHA TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE TB_USUARIO IF EXISTS";
        db.execSQL(sql);
        onCreate(db);
    }

    public void adicionarCliente (Usuario usuario){

        ContentValues valores = new ContentValues();
        getWritableDatabase().insert("TB_USUARIO",valores,null);
    }

    @NonNull
    private ContentValues buildContentValues(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("NOME",usuario.getNomeUsuario());
        valores.put("EMAIL",usuario.getEmailUsuario());
        valores.put("CPF",usuario.getCpfUsuario());
        valores.put("SENHA",usuario.getSenhaUsuario());
        return valores;
    }
}

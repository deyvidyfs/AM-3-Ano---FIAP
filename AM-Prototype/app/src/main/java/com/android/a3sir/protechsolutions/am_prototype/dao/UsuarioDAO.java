package com.android.a3sir.protechsolutions.am_prototype.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

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
        String sql = "CREATE TABLE TB_USUARIO(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, EMAIL TEXT UNIQUE, CPF TEXT UNIQUE, SENHA TEXT, SALDO NUMBER, INVESTIMENTOS NUMBER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE TB_USUARIO IF EXISTS";
        db.execSQL(sql);
        onCreate(db);
    }


    public void adicionarUsuario (Usuario usuario){

        ContentValues valores = buildContentValues(usuario);
        getWritableDatabase().insert("TB_USUARIO",null,valores);
    }

    public void apagar (int codigo){
        getWritableDatabase().delete("TB_USUARIO", "ID = ?", new String[]{String.valueOf(codigo)});
    }

    public Usuario buscarUsuario(String email){

        /*
        Cursor cursor = getReadableDatabase().query("TB_USUARIO",null,"EMAIL = ?",String[]{String.valueOf(email)},null,null,null);

        int codigo = cursor.getInt(0);
        String nome = cursor.getString(1);
        String cpf = cursor.getString(3);
        String senha = cursor.getString(4);

        Usuario usuario = new Usuario(codigo,nome,email,cpf,senha);
        return (usuario);

        */




        Cursor cursor = getReadableDatabase().query("TB_USUARIO",null,null,null,null,null,null);

        while(cursor.moveToNext()){
            String emailUsuario = cursor.getString(2);

            if(email.equals(emailUsuario)){
                int codigo = cursor.getInt(0);
                String nome = cursor.getString(1);
                String cpf = cursor.getString(3);
                String senha = cursor.getString(4);
                Long saldo = cursor.getLong(5);
                Long investimentos = cursor.getLong(6);
                Usuario usuario = new Usuario(codigo,nome,email,cpf,senha,saldo,investimentos);
                return(usuario);
            }



        }

        return null;



        /*
        Cursor cursor = getReadableDatabase().query("TB_USUARIO", null, null, null, null, null, null);

        List <Usuario> lista = new ArrayList<>();
        while(cursor.moveToNext()){
            int codigo = cursor.getInt(0);
            String nome = cursor.getString(1);
            String email = cursor.getString(2);
            String cpf = cursor.getString(3);
            String senha = cursor.getString(4);
            Usuario usuario = new Usuario(codigo,nome,email,cpf,senha);
            lista.add(usuario);
        }

        return lista;

        */
    }

    @NonNull
    private ContentValues buildContentValues(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("NOME",usuario.getNomeUsuario());
        valores.put("EMAIL",usuario.getEmailUsuario());
        valores.put("CPF",usuario.getCpfUsuario());
        valores.put("SENHA",usuario.getSenhaUsuario());
        valores.put("SALDO", 0);
        valores.put("INVESTIMENTOS",0);
        return valores;
    }
}

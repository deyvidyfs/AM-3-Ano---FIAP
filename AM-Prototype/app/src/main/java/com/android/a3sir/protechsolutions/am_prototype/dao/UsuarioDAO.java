package com.android.a3sir.protechsolutions.am_prototype.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.a3sir.protechsolutions.am_prototype.MainActivity;
import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlfs on 9/26/2017.
 */

public class UsuarioDAO extends SQLiteOpenHelper{

    private static final String BANCO = "usuarios";
    private static final int VERSAO = 6;

    public UsuarioDAO (Context context){
        super(context,BANCO,null,VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TB_USUARIO(ID INTEGER PRIMARY KEY AUTOINCREMENT , NOME TEXT, EMAIL TEXT UNIQUE , CPF TEXT UNIQUE, SALDO NUMBER, INVESTIMENTOS NUMBER, IDFIREBASEUSER STRING, SALDOPOUPANCA NUMBER, SALDOTESOURO NUMBER, SALDOCDB NUMBER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS TB_USUARIO" ;
        db.execSQL(sql);
        onCreate(db);
    }




    public long adicionarUsuario (Usuario usuario){

        ContentValues valores = buildContentValues(usuario);
        long insert = getWritableDatabase().insert("TB_USUARIO", null, valores);


        return insert;
    }

    public void apagar (String email){
        getWritableDatabase().delete("TB_USUARIO", "EMAIL = ?", new String[]{String.valueOf(email)});
    }

    public Usuario buscarUsuario(String email){


        Cursor cursor = getReadableDatabase().query("TB_USUARIO",null,"EMAIL = ?",new String[]{email},null,null,null);
        cursor.moveToNext();
        int codigo = cursor.getInt(0);
        String nome = cursor.getString(1);
        String cpf = cursor.getString(3);
        Long saldo = cursor.getLong(4);
        Long investimentos = cursor.getLong(5);
        String idFirebaseUsuario = cursor.getString(6);
        Long saldoPoupanca = cursor.getLong(7);
        Long saldoTesouro = cursor.getLong(8);
        Long saldoCdb = cursor.getLong(9);

        Usuario usuario = new Usuario(codigo,nome,email,cpf,saldo,investimentos,idFirebaseUsuario, saldoPoupanca, saldoTesouro, saldoCdb);
        return (usuario);

        }

        public void atualizar (Usuario usuario){
            ContentValues valores = buildContentValues(usuario);
            getWritableDatabase().update("TB_USUARIO",valores,"EMAIL = ?",new String[]{String.valueOf(usuario.getEmailUsuario())});
        }


    @NonNull
    private ContentValues buildContentValues(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("NOME",usuario.getNomeUsuario());
        valores.put("EMAIL",usuario.getEmailUsuario());
        valores.put("CPF",usuario.getCpfUsuario());
        valores.put("SALDO", usuario.getSaldoUsuario());
        valores.put("INVESTIMENTOS",usuario.getInvestimentoUsuario());
        valores.put("IDFIREBASEUSER",usuario.getIdFirebaseUsuario());
        valores.put("ID",usuario.getIdUsuario());
        valores.put("SALDOPOUPANCA",usuario.getSaldoPoupancaUsuario());
        valores.put("SALDOTESOURO",usuario.getSaldoTesouroUsuario());
        valores.put("SALDOCDB",usuario.getSaldoCdbUsuario());
        return valores;
    }
}


//OLD CODE:


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
        /*

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

            */

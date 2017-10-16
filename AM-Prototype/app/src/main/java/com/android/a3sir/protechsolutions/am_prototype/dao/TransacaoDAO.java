package com.android.a3sir.protechsolutions.am_prototype.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.android.a3sir.protechsolutions.am_prototype.Models.Transacao;
import com.android.a3sir.protechsolutions.am_prototype.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlfs on 10/15/2017.
 */

public class TransacaoDAO extends SQLiteOpenHelper {
    private static final String BANCO = "transacoes";
    private static final int VERSAO = 1;

    public TransacaoDAO (Context context){
        super(context,BANCO,null,VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TB_TRANSACAO(ID INTEGER PRIMARY KEY AUTOINCREMENT , TIPO TEXT, NOMEINVESTIMENTO TEXT, IDUSUARIO NUMBER, VALOR NUMBER, DATA TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS TB_TRANSACAO" ;
        db.execSQL(sql);
        onCreate(db);
    }




    public long adicionarTransacao (Transacao transacao){

        ContentValues valores = buildContentValues(transacao);
        long insert = getWritableDatabase().insert("TB_TRANSACAO", null, valores);


        return insert;
    }

    public void apagar (int id){
        getWritableDatabase().delete("TB_TRANSACAO", "ID = ?", new String[]{String.valueOf(id)});
    }

    public List<Transacao> buscarTransacoesUsuario(int idUsuario){


        Cursor cursor = getReadableDatabase().query("TB_TRANSACAO",null,"IDUSUARIO = ?",new String[]{Integer.toString(idUsuario)},null,null,null);

        List<Transacao> lista = new ArrayList<>();
        while(cursor.moveToNext()){
            int codigo = cursor.getInt(0);
            String tipo = cursor.getString(1);
            String nomeInvestimento = cursor.getString(2);
            long valor = cursor.getLong(3);
            String data = cursor.getString(4);

            Transacao transacao = new Transacao(codigo,tipo,nomeInvestimento,idUsuario,valor,data);
            lista.add(transacao);
        }

        return lista;

    }

    public void atualizar (Transacao transacao){
        ContentValues valores = buildContentValues(transacao);
        getWritableDatabase().update("TB_TRANSACAO",valores,"ID = ?",new String[]{String.valueOf(transacao.getIdTransacao())});
    }


    @NonNull
    private ContentValues buildContentValues(Transacao transacao) {
        ContentValues valores = new ContentValues();
        //valores.put("ID",transacao.getIdTransacao());
        valores.put("TIPO",transacao.getTipoTransacao());
        valores.put("NOMEINVESTIMENTO",transacao.getNomeInvestimentoTransacao());
        valores.put("IDUSUARIO",transacao.getIdUsuarioTransacao());
        valores.put("DATA",transacao.getDataTransacao());
        return valores;
    }
}

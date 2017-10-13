package com.android.a3sir.protechsolutions.am_prototype.Models;

/**
 * Created by dlfs on 10/12/2017.
 */

public class Transacao {

    public int idTransacao;
    public String tipoTransacao;
    public int idInvestimentoTransacao;
    public int idUsuarioTransacao;
    public long valor;

    public Transacao(){}

    public Transacao(int idTransacao, String tipoTransacao, int idInvestimentoTransacao, int idUsuarioTransacao, long valor) {
        this.idTransacao = idTransacao;
        this.tipoTransacao = tipoTransacao;
        this.idInvestimentoTransacao = idInvestimentoTransacao;
        this.idUsuarioTransacao = idUsuarioTransacao;
        this.valor = valor;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public int getIdInvestimentoTransacao() {
        return idInvestimentoTransacao;
    }

    public void setIdInvestimentoTransacao(int idInvestimentoTransacao) {
        this.idInvestimentoTransacao = idInvestimentoTransacao;
    }

    public int getIdUsuarioTransacao() {
        return idUsuarioTransacao;
    }

    public void setIdUsuarioTransacao(int idUsuarioTransacao) {
        this.idUsuarioTransacao = idUsuarioTransacao;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }
}

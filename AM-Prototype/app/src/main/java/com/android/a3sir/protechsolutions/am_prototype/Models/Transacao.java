package com.android.a3sir.protechsolutions.am_prototype.Models;

/**
 * Created by dlfs on 10/12/2017.
 */

public class Transacao {

    public int idTransacao;
    public String tipoTransacao;
    public String nomeInvestimentoTransacao;
    public int idUsuarioTransacao;
    public long valorTransacao;
    public String dataTransacao;

    public Transacao(){}

    public Transacao(int idTransacao, String tipoTransacao, String nomeInvestimentoTransacao, int idUsuarioTransacao, long valorTransacao, String dataTransacao) {
        this.idTransacao = idTransacao;
        this.tipoTransacao = tipoTransacao;
        this.nomeInvestimentoTransacao = nomeInvestimentoTransacao;
        this.idUsuarioTransacao = idUsuarioTransacao;
        this.valorTransacao = valorTransacao;
        this.dataTransacao = dataTransacao;
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

    public String getNomeInvestimentoTransacao() {
        return nomeInvestimentoTransacao;
    }

    public void setNomeInvestimentoTransacao(String nomeInvestimentoTransacao) {
        this.nomeInvestimentoTransacao = nomeInvestimentoTransacao;
    }

    public int getIdUsuarioTransacao() {
        return idUsuarioTransacao;
    }

    public void setIdUsuarioTransacao(int idUsuarioTransacao) {
        this.idUsuarioTransacao = idUsuarioTransacao;
    }

    public long getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(long valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

}

package com.android.a3sir.protechsolutions.am_prototype.Models;

/**
 * Created by dlfs on 10/12/2017.
 */

public class Investimento {
    public int idInvestimento;
    public String tipoInvestimento;
    public String categoriaInvestimento;
    public String nomeInvestimento;
    public String rendimentoInvestimento;
    public long investimentoMinimo;
    public String vencimentoInvestimento;

    public Investimento(){}

    public Investimento(int idInvestimento, String tipoInvestimento, String categoriaInvestimento, String nomeInvestimento, String rendimentoInvestimento, long investimentoMinimo, String vencimentoInvestimento) {
        this.idInvestimento = idInvestimento;
        this.tipoInvestimento = tipoInvestimento;
        this.categoriaInvestimento = categoriaInvestimento;
        this.nomeInvestimento = nomeInvestimento;
        this.rendimentoInvestimento = rendimentoInvestimento;
        this.investimentoMinimo = investimentoMinimo;
        this.vencimentoInvestimento = vencimentoInvestimento;
    }

    public int getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public String getTipoInvestimento() {
        return tipoInvestimento;
    }

    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    public String getCategoriaInvestimento() {
        return categoriaInvestimento;
    }

    public void setCategoriaInvestimento(String categoriaInvestimento) {
        this.categoriaInvestimento = categoriaInvestimento;
    }

    public String getNomeInvestimento() {
        return nomeInvestimento;
    }

    public void setNomeInvestimento(String nomeInvestimento) {
        this.nomeInvestimento = nomeInvestimento;
    }

    public String getRendimentoInvestimento() {
        return rendimentoInvestimento;
    }

    public void setRendimentoInvestimento(String rendimentoInvestimento) {
        this.rendimentoInvestimento = rendimentoInvestimento;
    }

    public long getInvestimentoMinimo() {
        return investimentoMinimo;
    }

    public void setInvestimentoMinimo(long investimentoMinimo) {
        this.investimentoMinimo = investimentoMinimo;
    }

    public String getVencimentoInvestimento() {
        return vencimentoInvestimento;
    }

    public void setVencimentoInvestimento(String vencimentoInvestimento) {
        this.vencimentoInvestimento = vencimentoInvestimento;
    }
}

package com.android.a3sir.protechsolutions.am_prototype.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dlfs on 9/26/2017.
 */

public class Usuario {
    public int idUsuario;
    public String nomeUsuario;
    public String emailUsuario;
    public String cpfUsuario;
    public long saldoUsuario;
    public long investimentoUsuario;
    public String idFirebaseUsuario;

    public Usuario(){

    }

    public Usuario(int idUsuario, String nomeUsuario, String emailUsuario, String cpfUsuario, long saldoUsuario, long investimentoUsuario, String idFirebaseUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.cpfUsuario = cpfUsuario;
        this.saldoUsuario = saldoUsuario;
        this.investimentoUsuario = investimentoUsuario;
        this.idFirebaseUsuario = idFirebaseUsuario;
    }

    public String getIdFirebaseUsuario() {
        return idFirebaseUsuario;
    }

    public void setIdFirebaseUsuario(String idFirebaseUsuario) {
        this.idFirebaseUsuario = idFirebaseUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public long getSaldoUsuario() {
        return saldoUsuario;
    }

    public void setSaldoUsuario(long saldoUsuario) {
        this.saldoUsuario = saldoUsuario;
    }

    public long getInvestimentoUsuario() {
        return investimentoUsuario;
    }

    public void setInvestimentoUsuario(long investimentoUsuario) {
        this.investimentoUsuario = investimentoUsuario;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("cpfUsuario", cpfUsuario);
        result.put("emailUsuario",emailUsuario);
        result.put("idFirebaseUsuario",idFirebaseUsuario);
        result.put("idUsuario",idUsuario);
        result.put("investimentoUsuario",investimentoUsuario);
        result.put("nomeUsuario",nomeUsuario);
        result.put("saldoUsuario",saldoUsuario);

        return result;
    }
}

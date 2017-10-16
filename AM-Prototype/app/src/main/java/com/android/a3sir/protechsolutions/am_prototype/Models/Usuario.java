package com.android.a3sir.protechsolutions.am_prototype.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dlfs on 9/26/2017.
 */

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String cpfUsuario;
    private long saldoUsuario;
    private long investimentoUsuario;
    private String idFirebaseUsuario;
    private long saldoPoupancaUsuario;
    private long saldoTesouroUsuario;
    private long saldoCdbUsuario;

    public Usuario(){

    }

    public Usuario(int idUsuario, String nomeUsuario, String emailUsuario, String cpfUsuario, long saldoUsuario, long investimentoUsuario, String idFirebaseUsuario, long saldoPoupancaUsuario, long saldoTesouroUsuario, long saldoCdbUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.cpfUsuario = cpfUsuario;
        this.saldoUsuario = saldoUsuario;
        this.investimentoUsuario = investimentoUsuario;
        this.idFirebaseUsuario = idFirebaseUsuario;
        this.saldoPoupancaUsuario = saldoPoupancaUsuario;
        this.saldoTesouroUsuario = saldoTesouroUsuario;
        this.saldoCdbUsuario = saldoCdbUsuario;
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

    public long getSaldoPoupancaUsuario() {
        return saldoPoupancaUsuario;
    }

    public void setSaldoPoupancaUsuario(long saldoPoupancaUsuario) {
        this.saldoPoupancaUsuario = saldoPoupancaUsuario;
    }

    public long getSaldoTesouroUsuario() {
        return saldoTesouroUsuario;
    }

    public void setSaldoTesouroUsuario(long saldoTesouroUsuario) {
        this.saldoTesouroUsuario = saldoTesouroUsuario;
    }

    public long getSaldoCdbUsuario() {
        return saldoCdbUsuario;
    }

    public void setSaldoCdbUsuario(long saldoCdbUsuario) {
        this.saldoCdbUsuario = saldoCdbUsuario;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("cpfUsuario", cpfUsuario);
        result.put("emailUsuario",emailUsuario);
        result.put("idFirebaseUsuario",idFirebaseUsuario);
        result.put("idUsuario",idUsuario);
        result.put("investimentoUsuario",investimentoUsuario);
        result.put("nomeUsuario",nomeUsuario);
        result.put("saldoCdbUsuario",saldoCdbUsuario);
        result.put("saldoPoupancaUsuario",saldoPoupancaUsuario);
        result.put("saldoTesouroUsuario",saldoTesouroUsuario);
        result.put("saldoUsuario",saldoUsuario);

        return result;
    }
}

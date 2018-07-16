/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.modelos;

/**
 *
 * @author jerick.gs
 */
public class Classificada {
    
    private int idC;
    private int idR;
    private String tipoDeSuporte;
    private String grupoSindromico;
    private String nivelGravidade;

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public String getTipoDeSuporte() {
        return tipoDeSuporte;
    }

    public void setTipoDeSuporte(String tipoDeSuporte) {
        this.tipoDeSuporte = tipoDeSuporte;
    }

    public String getGrupoSindromico() {
        return grupoSindromico;
    }

    public void setGrupoSindromico(String grupoSindromico) {
        this.grupoSindromico = grupoSindromico;
    }

    public String getNivelGravidade() {
        return nivelGravidade;
    }

    public void setNivelGravidade(String nivelGravidade) {
        this.nivelGravidade = nivelGravidade;
    }            
    
}

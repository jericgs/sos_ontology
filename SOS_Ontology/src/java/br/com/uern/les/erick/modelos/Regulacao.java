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
public class Regulacao {

    private int idR;
    private int idRC;
    private int ge;
    private int gs;
    private int atencao;
    private int social;
    private int tempo;
    private int gu;
    private String status;
    private String tipoDeCaso;
    private String mensagem;

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getIdRC() {
        return idRC;
    }

    public void setIdRC(int idRC) {
        this.idRC = idRC;
    }

    public int getGe() {
        return ge;
    }

    public void setGe(int ge) {
        this.ge = ge;
    }

    public int getGs() {
        return gs;
    }

    public void setGs(int gs) {
        this.gs = gs;
    }

    public int getAtencao() {
        return atencao;
    }

    public void setAtencao(int atencao) {
        this.atencao = atencao;
    }

    public int getSocial() {
        return social;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getGu() {
        return gu;
    }

    public void setGu(int gu) {
        this.gu = gu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoDeCaso() {
        return tipoDeCaso;
    }

    public void setTipoDeCaso(String tipoDeCaso) {
        this.tipoDeCaso = tipoDeCaso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    

    @Override
    public String toString() {
        return "\nTabela Regulacao\n\n" + " ID = " + idR + "\n IDRC = " + idRC + "\n GE = " + ge + "\n GS = " + gs + "\n Atencão = " + atencao + "\n Valor Social = " + social + "\n Tempo = " + tempo + "\n GU = " + gu + "\n Status = " + status + "\n Tipo De Caso = " + tipoDeCaso + "\n Mensagem = " + mensagem +"\n";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstruturaDeDados;

/**
 *
 * @author jerick.gs
 */
public class Regulacao {

    private int idRegulacao;
    private String dataDeRegistro;
    private double GE;
    private double GS;
    private double atencao;
    private double VSocial;
    private int Tempo;
    private double GU;
    private String Status;

    public int getIdRegulacao() {
        return idRegulacao;
    }

    public void setIdRegulacao(int idRegulacao) {
        this.idRegulacao = idRegulacao;
    }

    public String getDataDeRegistro() {
        return dataDeRegistro;
    }

    public void setDataDeRegistro(String dataDeRegistro) {
        this.dataDeRegistro = dataDeRegistro;
    }

    public double getGE() {
        return GE;
    }

    public void setGE(double GE) {
        this.GE = GE;
    }

    public double getGS() {
        return GS;
    }

    public void setGS(double GS) {
        this.GS = GS;
    }

    public double getAtencao() {
        return atencao;
    }

    public void setAtencao(double atencao) {
        this.atencao = atencao;
    }

    public double getVSocial() {
        return VSocial;
    }

    public void setVSocial(double VSocial) {
        this.VSocial = VSocial;
    }

    public int getTempo() {
        return Tempo;
    }

    public void setTempo(int Tempo) {
        this.Tempo = Tempo;
    }

    public double getGU() {
        return GU;
    }

    public void setGU(double GU) {
        this.GU = GU;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        return "\nTabela Regulacao\n\n" + " ID = " + idRegulacao + "\n Data De Registro = " + dataDeRegistro + "\n GE = " + GE + "\n GS = " + GS + "\n Atencão = " + atencao + "\n Valor Social = " + VSocial + "\n Tempo = " + Tempo + "\n GU = " + GU + "\n Status = " + Status + "\n";
    }

}

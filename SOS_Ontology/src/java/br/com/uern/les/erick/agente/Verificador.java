/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.agente;

import br.com.uern.les.erick.conexao.ConexaoBD;
import br.com.uern.les.erick.daos.ClassificadaDAO;
import br.com.uern.les.erick.daos.RegulacaoDAO;
import br.com.uern.les.erick.modelos.Classificada;
import br.com.uern.les.erick.modelos.Regulacao;
import br.com.uern.les.erick.ontology.OntologyManipulation;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerick.gs
 */
public class Verificador implements Runnable {

    private int sindromeGupo;
    private int fc;
    private double sat;

    @Override
    public void run() {

        try {

            Connection connection = new ConexaoBD().getConnection();
            RegulacaoDAO regulacaoDAO = new RegulacaoDAO(connection);
            List<Regulacao> regulacoesEmAndamento = regulacaoDAO.getRegulacaoAndamento();

            if (!regulacoesEmAndamento.isEmpty()) {

                for (int i = 0; i < regulacoesEmAndamento.size(); i++) {

                    //CHAMANDO INTELIGÊNCIA SOS_ONTOLOGY
                    OntologyManipulation ont = new OntologyManipulation("src//ontology//SOS_Ontology.owl", "src//ontology//Rules.txt");

                    if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("Andamento")) {

                        //ETIOLOGICO
                        if ((regulacoesEmAndamento.get(i).getGe() < regulacoesEmAndamento.get(i).getGs())
                                && (regulacoesEmAndamento.get(i).getGs() > regulacoesEmAndamento.get(i).getGe())) {

                            if ((regulacoesEmAndamento.get(i).getGe() < regulacoesEmAndamento.get(i).getSocial())
                                    && (regulacoesEmAndamento.get(i).getSocial() > regulacoesEmAndamento.get(i).getGe())) {

                                this.sindromeGupo = 1;

                            }

                        } else {

                            //SEMIOLOGICO
                            if ((regulacoesEmAndamento.get(i).getGs() < regulacoesEmAndamento.get(i).getGe())
                                    && (regulacoesEmAndamento.get(i).getGe() > regulacoesEmAndamento.get(i).getGs())) {

                                if ((regulacoesEmAndamento.get(i).getGs() < regulacoesEmAndamento.get(i).getSocial())
                                        && (regulacoesEmAndamento.get(i).getSocial() > regulacoesEmAndamento.get(i).getGs())) {

                                    this.sindromeGupo = 2;

                                }

                            } else {

                                //VELÊNCIA
                                if ((regulacoesEmAndamento.get(i).getSocial() < regulacoesEmAndamento.get(i).getGe())
                                        && (regulacoesEmAndamento.get(i).getGe() > regulacoesEmAndamento.get(i).getSocial())) {

                                    if ((regulacoesEmAndamento.get(i).getSocial() < regulacoesEmAndamento.get(i).getGs())
                                            && (regulacoesEmAndamento.get(i).getGs() > regulacoesEmAndamento.get(i).getSocial())) {

                                        this.sindromeGupo = 3;

                                    }

                                } else {
                                    this.sindromeGupo = 0;
                                }

                            }

                        }

                        this.fc = 200;
                        this.sat = 0.95;

                    }

                    if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoOB")) {

                    }

                    if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoOA")) {

                    }

                    if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoTroca")) {

                    }

                    //INFERINDO CONHECIMENTO
                    ont.inserindoDadosOntology(regulacoesEmAndamento.get(i).getIdR(), this.sindromeGupo, regulacoesEmAndamento.get(i).getGu(), this.fc, this.sat);

                    ont.processJenaRules();

                    String tipoDeUrgency = ont.inferencia("Urgency");
                    System.out.println("Tipo De Urgency: " + tipoDeUrgency);

                    String tipoDeGSyndrome = ont.inferencia("Syndrome");
                    System.out.println("Sindrome: " + tipoDeGSyndrome);

                    List<String> vitalSigns = ont.sinaisVitais("VitalSigns");
                    System.out.println("Sinais Vitais1: " + vitalSigns.get(0));
                    System.out.println("Sinais Vitais2: " + vitalSigns.get(1));

                    String tipoDeSuporte = ont.inferencia("Occurrence");
                    System.out.println("Tipo de Suporte: " + tipoDeSuporte);
                    
                    //MODELO CLASSIFICADA
                    Classificada classificada = new Classificada();
                    classificada.setIdR(regulacoesEmAndamento.get(i).getIdR());
                    classificada.setTipoDeSuporte(tipoDeSuporte);
                    classificada.setGrupoSindromico(tipoDeGSyndrome);
                    classificada.setNivelGravidade(tipoDeUrgency);
                    
                    //VERIFICAR SE A INFERENCIA DA REGULAÇÃO JÁ EXISTE NO BD
                    ClassificadaDAO classificadaDAO = new ClassificadaDAO(connection);
                    int idClassificacao = classificadaDAO.buscandoClassificacao(regulacoesEmAndamento.get(i).getIdR());
                    
                    if(idClassificacao == 0){
                        //INSERE A CLASSIFICAÇÃO
                        classificadaDAO.inserindoRegulacao(classificada);
                        
                    }
                    
                    if(idClassificacao != 0){
                        //ATUALIZA A CLASSIFICAÇÃO
                        classificadaDAO.atualizarClassificacao(classificada);
                    }
                    
                    //PAREI AQUI

                }

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verificador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

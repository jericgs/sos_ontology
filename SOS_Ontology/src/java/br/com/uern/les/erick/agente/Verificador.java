/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.agente;

import br.com.uern.les.erick.conexao.ConexaoBD;
import br.com.uern.les.erick.daos.ClassificadaDAO;
import br.com.uern.les.erick.daos.OcorrenciasaDAO;
import br.com.uern.les.erick.daos.OcorrenciasbDAO;
import br.com.uern.les.erick.daos.RegulacaoDAO;
import br.com.uern.les.erick.daos.SinaisVitaisDAO;
import br.com.uern.les.erick.modelos.Classificada;
import br.com.uern.les.erick.modelos.Regulacao;
import br.com.uern.les.erick.modelos.SinaisVitais;
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

    int sindromeGupo;
    private int fc;
    private double sat;

    private Thread thread;
    private String nome;
    private boolean logico;

    private Connection connection;
    private OntologyManipulation ont;

    public Verificador(String nome) {

        this.nome = nome;
        thread = new Thread(this, "Verificando");
        try {
            connection = new ConexaoBD().getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verificador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start(boolean comando) {
        this.thread.start();
        this.logico = comando;
    }

    public void stop(boolean comando) {
        this.thread.stop();
        this.logico = comando;
    }

    @Override
    public void run() {

        while (logico) {

            RegulacaoDAO regulacaoDAO = new RegulacaoDAO(connection);
            List<Regulacao> regulacoesEmAndamento = regulacaoDAO.getRegulacaoAndamento();

            try {

                Thread.sleep(1000);

                if (!regulacoesEmAndamento.isEmpty()) {

                    for (int i = 0; i < regulacoesEmAndamento.size(); i++) {

                        //CHAMANDO INTELIGÊNCIA SOS_ONTOLOGY
                        ont = new OntologyManipulation("C://xampp//htdocs//sos_ontology//SOS_Ontology//src//ontology//SOS_Ontology.owl", "C://xampp//htdocs//sos_ontology//SOS_Ontology//src//ontology//Rules.txt");

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

                            //INSERINDO DADOS
                            ont.inserindoDadosOntology(regulacoesEmAndamento.get(i).getIdR(), this.sindromeGupo, regulacoesEmAndamento.get(i).getGu(), this.fc, this.sat);

                        }

                        if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoOB")) {

                            this.sindromeGupo = 0;

                            OcorrenciasbDAO ocorrenciasbDAO = new OcorrenciasbDAO(connection);
                            int idOB = ocorrenciasbDAO.buscarOcorrenciaSB(regulacoesEmAndamento.get(i).getIdR());

                            int idV = ocorrenciasbDAO.SinaisVitatisOB(idOB);
                            SinaisVitaisDAO sinaisVitaisDAO = new SinaisVitaisDAO(connection);
                            SinaisVitais sv = sinaisVitaisDAO.getSinaisVitais(idV);

                            if (sv.getIdV() != 0) {
                                //INFERINDO CONHECIMENTO
                                int freqCardiaca = Integer.parseInt(sv.getFc());
                                int satO = Integer.parseInt(sv.getSatComSuport());
                                double satOxigenio = satO / 100;

                                //INSERINDO DADOS
                                ont.inserindoDadosOntology(regulacoesEmAndamento.get(i).getIdR(), this.sindromeGupo, regulacoesEmAndamento.get(i).getGu(), freqCardiaca, satOxigenio);

                            }

                        }

                        if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoOA")) {

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

                            OcorrenciasaDAO ocorrenciasaDAO = new OcorrenciasaDAO(connection);
                            int idOA = ocorrenciasaDAO.buscarOcorrenciaSA(regulacoesEmAndamento.get(i).getIdR());

                            int idV = ocorrenciasaDAO.SinaisVitatisOA(idOA);
                            SinaisVitaisDAO svdao = new SinaisVitaisDAO(connection);
                            SinaisVitais sinaisVitais = svdao.getSinaisVitais(idV);

                            if (sinaisVitais.getIdV() != 0) {
                                //INFERINDO CONHECIMENTO
                                int freqCardiaca = Integer.parseInt(sinaisVitais.getFc());
                                int satO = Integer.parseInt(sinaisVitais.getSatComSuport());
                                double satOxigenio = satO / 100;

                                //INSERINDO DADOS
                                ont.inserindoDadosOntology(regulacoesEmAndamento.get(i).getIdR(), this.sindromeGupo, regulacoesEmAndamento.get(i).getGu(), freqCardiaca, satOxigenio);

                            }

                        }

                        if (regulacoesEmAndamento.get(i).getStatus().equalsIgnoreCase("AndamentoTroca")) {

                            this.sindromeGupo = 0;

                            OcorrenciasbDAO ocorrenciasbDAO = new OcorrenciasbDAO(connection);
                            int idOB = ocorrenciasbDAO.buscarOcorrenciaSB(regulacoesEmAndamento.get(i).getIdR());

                            int idV = ocorrenciasbDAO.SinaisVitatisOB(idOB);
                            SinaisVitaisDAO sinaisVitaisDAO = new SinaisVitaisDAO(connection);
                            SinaisVitais sv = sinaisVitaisDAO.getSinaisVitais(idV);

                            if (sv.getIdV() != 0) {
                                //INFERINDO CONHECIMENTO
                                int freqCardiaca = Integer.parseInt(sv.getFc());
                                int satO = Integer.parseInt(sv.getSatComSuport());
                                double satOxigenio = satO / 100;

                                //INSERINDO DADOS                                
                                ont.inserindoDadosOntology(regulacoesEmAndamento.get(i).getIdR(), this.sindromeGupo, regulacoesEmAndamento.get(i).getGu(), freqCardiaca, satOxigenio);

                            }

                        }

                        //INFERINDO CONHECIMENTO
                        ont.processJenaRules();

                        String tipoDeSuporte = ont.inferencia("Occurrence");
                        System.out.println("Tipo de Suporte: " + tipoDeSuporte);

                        String tipoDeUrgency = ont.inferencia("Urgency");
                        System.out.println("Tipo De Urgency: " + tipoDeUrgency);

                        String tipoDeGSyndrome = ont.inferencia("Syndrome");
                        System.out.println("Sindrome: " + tipoDeGSyndrome);

                        List<String> vitalSigns = ont.sinaisVitais("VitalSigns");
                        System.out.println("Sinais Vitais1: " + vitalSigns.get(0));
                        System.out.println("Sinais Vitais2: " + vitalSigns.get(1));

                        //MODELO CLASSIFICADA
                        Classificada classificada = new Classificada();
                        classificada.setIdR(regulacoesEmAndamento.get(i).getIdR());
                        classificada.setTipoDeSuporte(tipoDeSuporte);
                        classificada.setGrupoSindromico(tipoDeUrgency);
                        classificada.setNivelGravidade(tipoDeGSyndrome);

                        //VERIFICAR SE A INFERENCIA DA REGULAÇÃO JÁ EXISTE NO BD
                        ClassificadaDAO classificadaDAO = new ClassificadaDAO(connection);
                        int idClassificacao = classificadaDAO.buscandoClassificacao(regulacoesEmAndamento.get(i).getIdR());

                        if (idClassificacao == 0) {
                            //INSERE A CLASSIFICAÇÃO
                            classificada.setIdC(idClassificacao);
                            classificadaDAO.inserindoRegulacao(classificada);

                        }

                        if (idClassificacao != 0) {
                            //ATUALIZA A CLASSIFICAÇÃO
                            classificada.setIdC(idClassificacao);
                            classificadaDAO.atualizarClassificacao(classificada);

                        }

                        System.out.println("------ Repetindo-----");

                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Verificador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}

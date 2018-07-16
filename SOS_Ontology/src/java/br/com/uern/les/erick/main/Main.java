/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.main;

import br.com.uern.les.erick.ontology.OntologyManipulation;
import java.util.List;

/**
 *
 * @author jerick.gs
 */
public class Main {
    
    public static void main(String[] args) {
        
        OntologyManipulation ont = new OntologyManipulation("src//ontology//SOS_Ontology.owl", "src//ontology//Rules.txt");
               
        
        int ID = 1;
        int SG = 0;
        int GU = 45;
        int FC = 210;
        double SAT = 0.95;
        
        ont.inserindoDadosOntology(ID, SG, GU, FC, SAT);

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
        
    }
    
}

package br.com.uern.les.erick.ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import jdk.nashorn.internal.objects.DataPropertyDescriptor;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecException;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.ReasonerVocabulary;
import org.apache.jena.vocabulary.XSD;

//esse  código é para a manipulação dos dados - inseridos, pesquisa etc
//na outra classe deu erro por causa do ontoModel
public class OntologyManipulation {

    private OntModel ontoModel; // modelo que armazena a estrutura da ontologia
    private InfModel infeReadModel; // modelo inferido da ontologia
    private String jenaRulesFilePath; // Pasta onde se encontra o arquivo txt com as regras
    private String URI;
    private String filePath;

    //public Ontologia() {
    // this.filePath = "ontology\\OntologiaDisfagia.owl";
    //loadOntology(filePath);
    // }
    // public OntologyManipulation(String filePath) {
    // this.filePath = filePath;
    // loadOntology(filePath);
    // }
    public OntologyManipulation(String filePath, String jenaRulesFilePath) {
        this.filePath = filePath;
        this.jenaRulesFilePath = jenaRulesFilePath;
        // this.learningStrategyFusekiQuery =
        // "http://localhost:3030/learning_strategy/sparql";
        loadOntology(filePath);
    }

    public void executeReasoner() {
        Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
        infeReadModel = ModelFactory.createInfModel(reasoner, ontoModel);
        // printOntModel(infeReadModel);
    }

    // Método que lê as regras jena e insere os resultados no modelo inferido
    public void processJenaRules() {
        // create a simple model; create a resource and add rules from a file
        System.out.println("Executing jean rules processing...");

        try {
            Model m = ModelFactory.createDefaultModel();
            Resource configuration = m.createResource();
            configuration.addProperty(ReasonerVocabulary.PROPruleSet, jenaRulesFilePath);

            // Create an instance of a reasoner
            Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);

            // Now with the rawdata model & the reasoner, create an InfModel
            infeReadModel = ModelFactory.createInfModel(reasoner, ontoModel);
            validateModel(infeReadModel);
        } catch (Exception e) {
            System.out.println("Error while running jena rules...");
            e.printStackTrace();
        }
    }

    // Método que valida o modelo inferido
    public boolean validateModel(InfModel infeReadModel) {
        boolean consistent = false;
        System.out.print("Validating Inferred Model: ");
        ValidityReport validity = infeReadModel.validate();
        if (validity.isValid()) {
            System.out.println("Consistent...");
            consistent = true;
        } else {
            System.out.println("Conflicts...");
            for (Iterator i = validity.getReports(); i.hasNext();) {
                System.out.println(" - " + i.next());
            }
        }
        return consistent;
    }

    public void loadOntology(String file) {
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        System.out.println("Loading Ontology " + filePath + " ...");
        try {
            InputStream in = FileManager.get().open(file);
            try {
                ontoModel.read(in, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Iterator iter = ontoModel.listOntologies();
            if (iter.hasNext()) {
                Ontology onto = (Ontology) iter.next();
                URI = onto.getURI();
            }
            //System.out.println(URI);
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
            System.exit(0);
        }
    }

    //Inserino dados na SOS Ontology
    public void inserindoDadosOntology(int id, int sindromeGupo, int grauUgencia, int freqCardiaca, double satOxigenio) {                                          

        String occurrence = "Occurrence";
        occurrence = URI + "#" + occurrence;
        OntClass medicalRegulationClass = ontoModel.getOntClass(URI + "#Medical_Regulation");
        Individual occurrenceInd = ontoModel.createIndividual(occurrence, medicalRegulationClass);
        
        DatatypeProperty hasID = ontoModel.createDatatypeProperty(URI + "#hasID");
        ObjectProperty isCompost = ontoModel.createObjectProperty(URI + "#isCompost");
        occurrenceInd.addProperty(hasID, ontoModel.createTypedLiteral(id));
        
        String urgency = "Urgency";
        urgency = URI + "#" + urgency;
        OntClass urgencyLevelClass = ontoModel.getOntClass(URI + "#Urgency_Level");
        Individual urgencyInd = ontoModel.createIndividual(urgency, urgencyLevelClass);
        
        DatatypeProperty hasValueGU = ontoModel.createDatatypeProperty(URI + "#hasValueGU");
        ObjectProperty hasSyndrome = ontoModel.createObjectProperty(URI + "#hasSyndrome");
        ObjectProperty hasSigns = ontoModel.createObjectProperty(URI + "#hasSigns");
        urgencyInd.addProperty(hasValueGU, ontoModel.createTypedLiteral(grauUgencia));
        
        String syndrome = "Syndrome";
        syndrome = URI + "#" + syndrome;
        OntClass sindromicGroupClass = ontoModel.getOntClass(URI + "#Sindromic_Group");
        Individual syndromeInd = ontoModel.createIndividual(syndrome, sindromicGroupClass);
        
        DatatypeProperty hasValueSG = ontoModel.createDatatypeProperty(URI + "#hasValueSG");
        syndromeInd.addProperty(hasValueSG, ontoModel.createTypedLiteral(sindromeGupo));
        
        String vitalSigns = "VitalSigns";
        vitalSigns = URI + "#" + vitalSigns;
        OntClass vitalSignsClass = ontoModel.getOntClass(URI + "#Vital_Signs");
        Individual vitalSignsInd = ontoModel.createIndividual(vitalSigns, vitalSignsClass);
        
        DatatypeProperty hasValueSAT = ontoModel.createDatatypeProperty(URI + "#hasValueSAT");
        DatatypeProperty hasValueFC = ontoModel.createDatatypeProperty(URI + "#hasValueFC");
        vitalSignsInd.addProperty(hasValueSAT, ontoModel.createTypedLiteral(satOxigenio));
        vitalSignsInd.addProperty(hasValueFC, ontoModel.createTypedLiteral(freqCardiaca));
        
        occurrenceInd.addProperty(isCompost, urgencyInd);               
        urgencyInd.addProperty(hasSyndrome, syndromeInd);
        urgencyInd.addProperty(hasSigns, vitalSignsInd);

    }   

    public String formPrefixQuery(String URI, String prefix) {
        return "PREFIX " + prefix + ": <" + URI + "#>";
    }

    public void saveOntology() {
        StringWriter sw = new StringWriter();
        ontoModel.write(sw, "RDF/XML");
        String owlCode = sw.toString();
        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(owlCode);
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Imprime toda a estrutura da ontologia */
    public void printOntModel(InfModel model) {
        System.out.println("Printing Model...");
        model.write(System.out, "TTL");
        // model.write(System.out);
    }

    /* Imprime toda a estrutura da ontologia */
    public void printOntModel() {
        System.out.println("Printing Model...");
        ontoModel.write(System.out);
        ontoModel.write(System.out, "TTL");
    }

    
    // método que retorna o resultado da classificação
    public String inferencia(String individual) {

        String a = null;
        // Buscando nível do indivíduo
        String queryString = "PREFIX ont: <" + URI + "#>\n"
                + "PREFIX rdf: <" + RDF.getURI() + ">\n"
                + "SELECT ?type "
                + "WHERE {\n"
                + "ont:" + individual + " rdf:type ?type .\n"
                + "}\n";

        //System.out.println(queryString);
        Query query = QueryFactory.create(queryString);

        ResultSet results = null;
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infeReadModel)) {
            results = qexec.execSelect();

            //for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Resource resource = soln.getResource("?type");
            //System.out.println(resource.toString());
            //imprime somente a classificação
            String result = resource.toString();
            String[] classificacao = result.split("#");
            a = classificacao[1];
            //System.out.print(classificacao[1]+"\n");
            //}

            qexec.close();
        } catch (QueryExecException e) {
            System.err.println("ERROR" + e.getMessage());
            e.printStackTrace();
            // System.exit(0);
        }
        return a;
    }

    public List<String> sinaisVitais(String individual) {

        List<String> sinaisVitais = new ArrayList<String>();      

        // Buscando nível do indivíduo
        String queryString = "PREFIX ont: <" + URI + "#>\n"
                + "PREFIX rdf: <" + RDF.getURI() + ">\n"
                + "SELECT ?type "
                + "WHERE {\n"
                + "ont:" + individual + " rdf:type ?type .\n"
                + "}\n";

        //System.out.println(queryString);
        Query query = QueryFactory.create(queryString);

        ResultSet results = null;
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infeReadModel)) {
            results = qexec.execSelect();            
                        
            for (int i = 0; results.hasNext(); i++) {
                QuerySolution soln = results.nextSolution();
                Resource resource = soln.getResource("?type");
            
            
                String result = resource.toString();
                String[] classificacao = result.split("#");                
                sinaisVitais.add(classificacao[1]);
                
            }
            //ajeita o n�vel de disfagia aqui
            qexec.close();
        } catch (QueryExecException e) {
            System.err.println("ERROR" + e.getMessage());
            e.printStackTrace();
            // System.exit(0);
        }
        return sinaisVitais;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agente;

import EstruturaDeDados.Regulacao;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jerick.gs
 */
public class Verificador implements Runnable{

    private ArrayList<Regulacao> aListRegulacao = new ArrayList<>();

    public ArrayList<Regulacao> getaListRegulacao() {
        return aListRegulacao;
    }


    @Override
    public void run() {
        
        try {
            
            DataBaseConnection dbc = new DataBaseConnection();
                dbc.connectDataBase();

                if (dbc.isConnected()) {

                    String queryRegulacao = "SELECT * FROM regulacao WHERE Status = 'Andamento'";
                    ResultSet resultSet = dbc.executeQuery(queryRegulacao);

                    try {

                        while (resultSet.next()) {

                            Regulacao regulacao = new Regulacao();

                            regulacao.setIdRegulacao(resultSet.getInt("IdR"));                            
                            regulacao.setDataDeRegistro(resultSet.getString("DataDeRegistro"));
                            regulacao.setGE(resultSet.getDouble("GE"));
                            regulacao.setGS(resultSet.getDouble("GS"));
                            regulacao.setAtencao(resultSet.getDouble("Atencao"));
                            regulacao.setVSocial(resultSet.getDouble("Social"));
                            regulacao.setTempo(resultSet.getInt("Tempo"));
                            regulacao.setGU(resultSet.getDouble("GU"));
                            regulacao.setStatus(resultSet.getString("Status"));

                            aListRegulacao.add(regulacao);
                            
                            System.out.println("passou");

                        }

                    } catch (SQLException e) {

                        System.out.println("SQLException: " + e);
                    }
                }
            
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        
    }
    
}

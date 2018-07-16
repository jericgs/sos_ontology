/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.daos;

import br.com.uern.les.erick.modelos.Regulacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerick.gs
 */
public class RegulacaoDAO {

    private Connection connection;

    public RegulacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Regulacao> getRegulacaoAndamento() {

        List<Regulacao> regulacoesEmAndamento = new ArrayList<>();

        try {

            String sql = "SELECT * FROM regulacao WHERE Status = 'Andamento' OR Status = 'AndamentoTroca' OR Status = 'AndamentoOB' OR Status = 'AndamentoOA'";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // criando o objeto Regulação
                Regulacao regulacao = new Regulacao();

                regulacao.setIdR(rs.getInt("IdR"));
                regulacao.setIdRC(rs.getInt("IdRC"));
                regulacao.setGe(rs.getInt("GE"));
                regulacao.setGs(rs.getInt("GS"));
                regulacao.setAtencao(rs.getInt("Atencao"));
                regulacao.setSocial(rs.getInt("Social"));
                regulacao.setTempo(rs.getInt("Tempo"));
                regulacao.setGu(rs.getInt("GU"));
                regulacao.setStatus(rs.getString("Status"));
                regulacao.setTipoDeCaso(rs.getString("TipoDeCaso"));
                regulacao.setMensagem(rs.getString("Mensagem"));

                // adicionando o objeto à lista
                regulacoesEmAndamento.add(regulacao);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RegulacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return regulacoesEmAndamento;
    }

}

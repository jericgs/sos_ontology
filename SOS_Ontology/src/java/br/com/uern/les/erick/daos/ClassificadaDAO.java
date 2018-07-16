/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.daos;

import br.com.uern.les.erick.modelos.Classificada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerick.gs
 */
public class ClassificadaDAO {

    private Connection connection;

    public ClassificadaDAO(Connection connection) {
        this.connection = connection;
    }

    public int buscandoClassificacao(int idR) {
        int idC = 0;

        try {

            String sql = "SELECT * FROM classificada WHERE IdR = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idR);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idC = rs.getInt("IdC");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ClassificadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idC;
    }

    public int atualizarClassificacao(Classificada classificada) {
        int confirmacao = 0;

        try {

            String sql = "UPDATE classificada SET TipoDeSuporte = ?, GrupoSindromico = ?, NivelGravidade = ? "
                    + "WHERE IdC = ?";
            
            try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
                stmt.setString(1, classificada.getTipoDeSuporte());
                stmt.setString(2, classificada.getGrupoSindromico());
                stmt.setString(3, classificada.getNivelGravidade());
                stmt.setInt(4, classificada.getIdC());
                
                stmt.execute();
                confirmacao++;
            }            

        } catch (SQLException ex) {
            Logger.getLogger(ClassificadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return confirmacao;
    }
    
    public int inserindoRegulacao(Classificada classificada) {
        int idC = 0;

        try {

            String sql1 = "INSERT INTO classificada "
                    + "(IdR,TipoDeSuporte,GrupoSindromico,NivelGravidade)"
                    + " values (?,?,?,?)";

            try (PreparedStatement stmt = this.connection.prepareStatement(sql1)) {
                stmt.setInt(1, classificada.getIdR());
                stmt.setString(2, classificada.getTipoDeSuporte());
                stmt.setString(3, classificada.getGrupoSindromico());
                stmt.setString(4, classificada.getNivelGravidade());

                stmt.execute();
                
                String sql2 = "SELECT LAST_INSERT_ID() FROM classificada";
                
                PreparedStatement ps = this.connection.prepareStatement(sql2);
                
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    idC = rs.getInt("LAST_INSERT_ID()");
                }
            }
                       
        } catch (SQLException ex) {
            Logger.getLogger(ClassificadaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idC;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.daos;

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
public class OcorrenciasaDAO {

    private Connection connection;

    public OcorrenciasaDAO(Connection connection) {
        this.connection = connection;
    }

    public int buscarOcorrenciaSA(int idR) {

        int idOA = 0;

        try {

            String sql = "SELECT IdOA FROM ocorrenciasa WHERE IdR = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idR);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idOA = rs.getInt("IdOA");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(OcorrenciasaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idOA;
    }
    
    public int SinaisVitatisOA(int idOA) {
        int idV = 0;

        try {

            String sql = "SELECT IdV FROM ocorrenciasa WHERE IdOA = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idOA);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idV = rs.getInt("IdV");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(OcorrenciasaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idV;
    }
    
}

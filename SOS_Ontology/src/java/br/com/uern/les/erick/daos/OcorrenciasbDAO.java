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
public class OcorrenciasbDAO {

    private Connection connection;

    public OcorrenciasbDAO(Connection connection) {
        this.connection = connection;
    }

    public int buscarOcorrenciaSB(int idR) {

        int idOB = 0;

        try {

            String sql = "SELECT IdOB FROM ocorrenciasb WHERE IdR = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idR);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idOB = rs.getInt("IdOB");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(OcorrenciasbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idOB;
    }

    public int SinaisVitatisOB(int idOB) {
        int idV = 0;

        try {

            String sql = "SELECT IdV FROM ocorrenciasb WHERE IdOB = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idOB);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    idV = rs.getInt("IdV");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(OcorrenciasbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idV;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.daos;

import br.com.uern.les.erick.modelos.SinaisVitais;
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
public class SinaisVitaisDAO {

    private Connection connection;

    public SinaisVitaisDAO(Connection connection) {
        this.connection = connection;
    }

    public SinaisVitais getSinaisVitais(int idV) {

        SinaisVitais sv = new SinaisVitais();

        try {

            String sql = "SELECT * FROM sinaisvitais WHERE IdV = ?";
            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

                ps.setInt(1, idV);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    
                    sv.setIdV(rs.getInt("IdV"));
                    sv.setPa(rs.getString("Pa"));
                    sv.setFc(rs.getString("Fc"));
                    sv.setFr(rs.getString("Fr"));
                    sv.setSatSemSuport(rs.getString("SatSemSuport"));
                    sv.setSatComSuport(rs.getString("SatComSuport"));
                    sv.setTemperatura(rs.getString("Temperatura"));
                    sv.setHgt(rs.getString("HGT"));
                    sv.setGlasgow(rs.getString("Glasgow"));
                    
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(SinaisVitaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sv;
    }

}

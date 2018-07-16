package br.com.uern.les.erick.agente;

import br.com.uern.les.erick.conexao.ConexaoBD;
import br.com.uern.les.erick.daos.RegulacaoDAO;
import br.com.uern.les.erick.modelos.Regulacao;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

public class teste {

    public static void main(String[] args) throws ClassNotFoundException {

//        ArrayList<Regulacao> regulacoes;
//        regulacoes = new ArrayList<>();
//        Verificador verificador = new Verificador();
//        verificador.run();
//        regulacoes = verificador.getaListRegulacao();
//
//        for (Regulacao regulacoe : regulacoes) {
//            System.out.println(regulacoes.toString());
//        }
        
        Connection connection = new ConexaoBD().getConnection();
        RegulacaoDAO regulacaoDAO = new RegulacaoDAO(connection);
        List<Regulacao> regulacoesEmAndamento = regulacaoDAO.getRegulacaoAndamento();
        
        regulacoesEmAndamento.forEach((_item) -> {
            System.out.println(regulacoesEmAndamento.toString());
        });
        
        
        
    }

}

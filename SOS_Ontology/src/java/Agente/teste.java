package Agente;

import EstruturaDeDados.Regulacao;


import java.util.ArrayList;

public class teste {       
	
	public static void main(String[] args) {
            
            ArrayList<Regulacao> regulacoes;
            regulacoes = new ArrayList<>();
            Verificador verificador = new Verificador();
            verificador.run();
            regulacoes = verificador.getaListRegulacao();
            
            
            for (Regulacao regulacoe : regulacoes) {
                System.out.println(regulacoes.toString());                
            }
//		UsuarioDAO dao = new UsuarioDAO();
//		List<Usuario> usuario = dao.getLista("8");
//		Usuario userr = new Usuario();
//		
//		System.out.println("oi");
//		
//		for(Usuario user:usuario){
//			System.out.println("oi");
//			System.out.println("ID: "+ user.getID());
//			System.out.println("Nome: "+ user.getNome());
//			System.out.println("Tipo de Alimento: "+ user.getTipoALimento());
//			System.out.println("Nível de Disfagia: "+ user.getNiveldDisfagia());
//			System.out.println("Duração: "+ user.getDuracao());
//			System.out.println("Data e Hora: "+ user.getDataHora() + "\n");
//			
//		}
//		
//		dao.remove("9");
	}

}

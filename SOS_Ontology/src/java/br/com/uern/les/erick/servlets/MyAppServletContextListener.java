/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uern.les.erick.servlets;

import br.com.uern.les.erick.agente.Verificador;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author jerick.gs
 */
@WebListener
public class MyAppServletContextListener implements ServletContextListener {

    private Verificador runnableVerificador = new Verificador("ProcessoInteligente");

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //INICIA A THREAD         
        runnableVerificador.start(true);
        System.out.println("----ServletContextListener started----");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        //PARA A THREAD 
        runnableVerificador.stop(false);

        System.out.println("----ServletContextListener destroyed----");
    }

}

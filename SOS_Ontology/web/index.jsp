<%-- 
    Document   : index
    Created on : 21/05/2018, 22:24:25
    Author     : jerick.gs
--%>

<%@page import="Agente.Verificador"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="EstruturaDeDados.Regulacao"%>

<%
    ArrayList<Regulacao> regulacoes;
    regulacoes = new ArrayList<Regulacao>();
    Verificador verificador = new Verificador();
    verificador.run();
    regulacoes = verificador.getaListRegulacao();
%>
<!DOCTYPE html>
<html>
    <head>

        <title>SOS_Ontology</title>
    </head>
    <body>
        <h1><% for (Regulacao regulacoe : regulacoes) {
                out.println(regulacoes.toString());
            }%></h1>
    </body>
</html>

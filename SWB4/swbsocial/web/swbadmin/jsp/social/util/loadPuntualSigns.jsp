<%-- 
    Document   : loadPuntualSigns
    Created on : 22-may-2012, 17:31:57
    Author     : jorge.jimenez
--%>

<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

Inicia...
<%
    //WebSite wsite=paramRequest.getWebPage().getWebSite();
    WebSite wsite=WebSite.ClassMgr.getWebSite("swbsocial");
    //Elimina todos los objetos(instancias) de la clase PunctuationSign
    Iterator <PunctuationSign> itPuntSigns=PunctuationSign.ClassMgr.listPunctuationSigns(wsite);
    while(itPuntSigns.hasNext())
    {
        PunctuationSign puntSign=itPuntSigns.next();
        puntSign.remove();
    }

    //Inserta detos en la clase SentimentWords
    BufferedReader bf = null;
    try {
        bf = new BufferedReader(new FileReader("C:\\proyectos\\SSMCC\\Clasificacion\\puntuationSigns.csv"));
    } catch (FileNotFoundException e) {
    e.printStackTrace();
    }
    String line = null;
    try {
        while ((line = bf.readLine())!=null) {
               System.out.println("line a insertar:"+line);
               PunctuationSign puntSign=PunctuationSign.ClassMgr.createPunctuationSign(wsite);
               puntSign.setPuntuationSign(SWBUtils.TEXT.encode(line,"UTF-8"));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    //Lee todos los valores de la clase sentimentWord
    int cont=0;
    itPuntSigns=PunctuationSign.ClassMgr.listPunctuationSigns(wsite);
    while(itPuntSigns.hasNext())
    {
        cont++;
        PunctuationSign puntSign=itPuntSigns.next();
        System.out.println("PuntSign:"+puntSign.getPuntuationSign());
    }
    System.out.println("cont:"+cont);

%>

Termina...
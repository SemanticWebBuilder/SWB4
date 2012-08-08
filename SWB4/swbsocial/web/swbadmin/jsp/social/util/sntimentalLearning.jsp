<%-- 
    Document   : sntimentalLearning
    Created on : 11-jun-2012, 15:12:37
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
    int cont=0;
    WebSite wsite=paramRequest.getWebPage().getWebSite();
    //Crea
    /*
    SentimentalLearningPhrase newSLP=SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(wsite);
    newSLP.setPhrase("super bien");
    newSLP.setSentimentType(1);
    newSLP.setIntensityType(2);
*/
    //Lista
    Iterator<SentimentalLearningPhrase> itSntLPs=SentimentalLearningPhrase.ClassMgr.listSentimentalLearningPhrases(wsite);
    while(itSntLPs.hasNext())
    {
        cont++;
        SentimentalLearningPhrase sntlp=itSntLPs.next();
        System.out.println("sntlp, Id():"+sntlp.getId()+", phrase:"+sntlp.getPhrase()+", SentimentType:"+sntlp.getSentimentType()+", IntensityType:"+sntlp.getIntensityType());
        //if(sntlp.getId().equals("26")) {
        //    sntlp.remove();
        //}
    }
    System.out.println("SentimentalLearningPhrase totales:"+cont);
%>
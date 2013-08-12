<%-- 
    Document   : LearningPhrases
    Created on : 12-ago-2013, 11:40:43
    Author     : jorge.jimenez
--%>

<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%> 
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
Entra...
<%
    Iterator <SentimentalLearningPhrase> itSentLP=SentimentalLearningPhrase.ClassMgr.listSentimentalLearningPhrases(SWBContext.getAdminWebSite());
    while(itSentLP.hasNext())
    {
        SentimentalLearningPhrase sentLP=itSentLP.next();
        out.println("sentLP existente:"+sentLP+", OriginalP:"+sentLP.getOriginalPhrase()+", Phrase:"+sentLP.getPhrase());
        //sentLP.remove(); 
    }    
%>
Termina....
<%-- 
    Document   : normalizePhrases
    Created on : 15-jun-2012, 10:27:08
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.util.*,org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

Inicia...
<%
    WebSite wsite=paramRequest.getWebPage().getWebSite();
    String phrase="Chinga tu Madre";
    //Normalizo la frase
    phrase=SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
    System.out.println("phrase-0:"+phrase);

    phrase=SWBSocialUtil.Classifier.getRootWord(phrase);
    System.out.println("phrase:"+phrase);
    if(phrase!=null && phrase.isEmpty())
    {
        phrase=SWBSocialUtil.Classifier.phonematize(phrase);
    }
    System.out.println("phrase_Final:"+phrase);

    SentimentalLearningPhrase newSLP=SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(wsite);
    newSLP.setPhrase(phrase);
    newSLP.setSentimentType(2);
    newSLP.setIntensityType(1);
   
%>
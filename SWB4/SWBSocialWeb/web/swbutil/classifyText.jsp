<%-- 
    Document   : classifyText
    Created on : 21-jul-2014, 18:03:46
    Author     : jorge.jimenez
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="org.json.*"%>

    <%
    String text="sufrir es algo que no se esperar nada, zacatecassss puto que la chingada";
    HashMap hmapValues = SWBSocialUtil.Classifier.classifyText(text);
    float promSentimentalValue = ((Float) hmapValues.get("promSentimentalValue")).floatValue();
    int sentimentalTweetValueType = ((Integer) hmapValues.get("sentimentalTweetValueType")).intValue();
    float promIntensityValue = ((Float) hmapValues.get("promIntensityValue")).floatValue();
    int intensityTweetValueType = ((Integer) hmapValues.get("intensityTweetValueType")).intValue();
    String lang=(String)hmapValues.get("msg_lang");  
    %>
    <%if(sentimentalTweetValueType==0){%>Sentimiento:Neutro<%}%><br/>
    <%if(sentimentalTweetValueType==1){%>Sentimiento:Positivo<%}%><br/>
    <%if(sentimentalTweetValueType==2){%>Sentimiento:Negativo<%}%><br/>
    <!--Intensidad-->
    <%if(intensityTweetValueType==0){%>Intensidad:Baja<%}%><br/>
    <%if(intensityTweetValueType==1){%>Intensidad:Media<%}%><br/>
    <%if(intensityTweetValueType==2){%>Intensidad:Alta<%}%><br/>
    
    

    <br/><br/><br/><br/><br/>
    promSentimentalValue=<%=promSentimentalValue%><br/>
    sentimentalTweetValueType=<%=sentimentalTweetValueType%><br/>
    promIntensityValue=<%=promIntensityValue%><br/>
    intensityTweetValueType=<%=intensityTweetValueType%><br/>
    lang=<%=lang%><br/>
    
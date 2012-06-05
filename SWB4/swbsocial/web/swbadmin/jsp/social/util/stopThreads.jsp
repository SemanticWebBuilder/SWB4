<%-- 
    Document   : stopThreads
    Created on : 04-jun-2012, 15:38:27
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
    if(wsite instanceof SocialSite)
    {
       Iterator<SocialNetwork> itSocialNetWorks=SocialNetwork.ClassMgr.listSocialNetworks(wsite);
        while(itSocialNetWorks.hasNext())
        {
            SocialNetwork socialNet=itSocialNetWorks.next();
            if(socialNet instanceof KeepAliveListenerable)
            {
                KeepAliveListenerable keepAliveListenerable=(KeepAliveListenerable)socialNet;
                if(keepAliveListenerable.isIsKeepingConnection()) //Tiene la propiedad de mantener la conexión en true, por lo tanto no enviar a timer
                {
                    keepAliveListenerable.stopListenAlive();    //Detiene
                    //keepAliveListenerable.listenAlive(wsite);
                    System.out.println("Se detuvieron las conexiones a Redes Sociales...");
                }
            }
        }
    }
 %>
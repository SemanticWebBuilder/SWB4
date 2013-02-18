<%@page import="org.semanticwb.social.SocialNetworkUser"%>
<%@page import="org.semanticwb.social.SocialPost"%>
<%@page import="org.semanticwb.social.MessageIn"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/> 
<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%@page import="org.semanticwb.base.util.URLEncoder"%>
Inicia...
<%
    response.setContentType("text/html; charset=ISO-8859-1");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    //WebSite wsite=paramRequest.getWebPage().getWebSite();
    
    WebSite wsite=WebSite.ClassMgr.getWebSite("WebBuider");

    //Elimina todas las instancias de la clase MessageIn
    Iterator <MessageIn> itMi=MessageIn.ClassMgr.listMessageIns(wsite);
    System.out.println("J1:"+itMi.hasNext());
    while(itMi.hasNext())
    {
        MessageIn mi=itMi.next();
        mi.remove();
    }
    
    //Elimina todas las instancias de la clase SocialPost
    Iterator <SocialPost> itSp=SocialPost.ClassMgr.listSocialPosts(wsite); 
    System.out.println("J2:"+itSp.hasNext());
    while(itSp.hasNext())
    {
        SocialPost sp=itSp.next();
        sp.remove();
    }
    
    //Elimina todas las instancias de la clase SocialNetworkUser
    Iterator <SocialNetworkUser> itSnu=SocialNetworkUser.ClassMgr.listSocialNetworkUsers(wsite); 
    System.out.println("J3:"+itSnu.hasNext());
    while(itSnu.hasNext())
    {
        SocialNetworkUser snu=itSnu.next();
        snu.remove();
    }
%>
Termina...
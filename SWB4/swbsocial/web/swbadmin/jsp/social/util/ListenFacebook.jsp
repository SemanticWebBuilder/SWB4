<%-- 
    Document   : ListenFacebook
    Created on : 11/01/2013, 12:03:00 PM
    Author     : jose.jimenez
--%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.*"%>

<%
    Iterator itFacebookAccs = Facebook.ClassMgr.listFacebooks();
    Facebook facebookInst = null;
    Stream stream = null;
    
    while (itFacebookAccs.hasNext()) {
        facebookInst = (Facebook) itFacebookAccs.next();
        if (facebookInst.getAccessToken() != null) {
            break;
        }
    }
    Iterator itStream = Stream.ClassMgr.listStreams();
    while (itStream.hasNext()) {
        stream = (Stream) itStream.next();
        if (stream.getPhrase() != null) {
            break;
        }
    }
    
    out.println("Inicia extracción de datos de Facebook<br>");
    if (facebookInst != null && stream != null && stream.getPhrase() != null) {
        out.print("AccessToken: " + facebookInst.getAccessToken());
        out.print("Trabajar con las frases: " + stream.getPhrase());
        String[] phrase = stream.getPhrase().split(":");
        
        for (int i = 0; i < phrase.length; i++) {
            out.print("<p>Frase " + i + ": " + phrase[i]);
        }
        System.out.println("\nAntes de mostrar datos\nfrases:" + stream.getPhrase());
        
        //facebookInst.removeProperty(Facebook.social_nextDatetoSearch.getName());
        out.println("Busca información con los criterios: " + facebookInst.getNextDatetoSearch());
        facebookInst.listen(stream);
        System.out.println("\nDespues de listen\n");
        
        out.println("<br>Termina extracción de datos de Facebook");
    }
%>
<%-- 
    Document   : nextDate
    Created on : 5/09/2013, 04:10:45 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.social.Twitter"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.social.SocialNetStreamSearch"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>
<%
        WebSite wsite=WebSite.ClassMgr.getWebSite("Infotec");
        out.println("wsite:"+ wsite + "</br>");
        Stream stream=Stream.ClassMgr.getStream("2", wsite);
        out.println("stream:"+ stream + "</br>");
        /*
        Twitter socialNet1=Twitter.ClassMgr.getTwitter("5", wsite);
        out.println("TWITTER:"+ socialNet1.getTitle() + "</br>");
        
        Facebook face=Facebook.ClassMgr.getFacebook("6", wsite);
        out.println("FACE:"+ face.getTitle() + "</br>");
        
        SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, socialNet1);
        SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, face);
        
        Iterator<SocialNetwork> socialNet=SocialNetwork.ClassMgr.listSocialNetworks(wsite);
        while(socialNet.hasNext()){
            SocialNetwork net = socialNet.next();
            out.println("net:"+ net + "</br>");
        }
      Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, wsite);
       
        while(itSocialNetStreamSearch.hasNext())
        {
            SocialNetStreamSearch socialStreamSerch=itSocialNetStreamSearch.next();
            out.print("..." + socialStreamSerch.getClass().getName() + "-" +socialStreamSerch.getURI() + "</br>");
            out.println("IDSocialNet:" + socialStreamSerch.getSocialNetwork().getURI()+ "</br>");
            out.println("IDSocialStream:" + socialStreamSerch.getStream().getId()+ "</br>");
            out.println("IDSocialNet:" + socialStreamSerch.getSocialNetwork().getId()+ "</br>");
            if(socialStreamSerch.getSocialNetwork().getId().equals(socialNet1.getId()))
            {
                out.println("ENCONTRADO!!" + socialStreamSerch.getStream().getTitle() + "--" + socialStreamSerch.getSocialNetwork().getTitle());
                
                
            }            
        }      
      */
        Iterator <SocialNetStreamSearch> itSocialNetStreamSearch1=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
        out.println("Social Site st :" + stream.getSocialSite()+ "</br>");
        int i = 0;
        while(itSocialNetStreamSearch1.hasNext())
        {
            out.println("Registro JH" + ++i + "</br>");
            SocialNetStreamSearch socialStreamSerch=itSocialNetStreamSearch1.next();
            out.println("ID STREAM-->" + socialStreamSerch.getStream().getURI() + "</br>");
            out.println("ID RED-->" + socialStreamSerch.getSocialNetwork().getURI() + "</br>");
            out.println("ID SocialNetStreamSearch-->" + socialStreamSerch.getURI() + "</br>");
        }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=x-iso-8859-11">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>

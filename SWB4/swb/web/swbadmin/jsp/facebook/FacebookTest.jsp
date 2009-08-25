 <%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
 <%@page import="com.google.code.facebookapi.FacebookXmlRestClient"%>
 <%@page import="com.google.code.facebookapi.IFacebookRestClient"%>
 <%@page import="com.google.code.facebookapi.TemplatizedAction"%>
 <%@page import="com.google.code.facebookapi.Permission"%>
 <%@page import="org.w3c.dom.Document"%>
<%@page import="java.util.*"%>

 <%
     IFacebookRestClient<Document> userClient = getUserClient(session);
     if (userClient != null) {
        String api_key=userClient.getApiKey();
        String secret_key=userClient.getSecret();
        if(userClient.users_hasAppPermission(Permission.STATUS_UPDATE))
        {
            System.out.println("entra a mandar");
            HashMap map = new HashMap();
            map.put("commentamelo","Prueba de comentario de hoy");
            userClient.feed_publishUserAction(new Long("137386010688"),map,null,null);
            //Página para dar de alta templates:http://developers.facebook.com/tools.php?feed
        }
     }

  %>


<%!
     static FacebookXmlRestClient getUserClient(HttpSession session) {
            return (FacebookXmlRestClient) session.getAttribute("facebook.user.client");
     }
%>




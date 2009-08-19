<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPlatform"%>


<%
   String imgPath=SWBPlatform.getContextPath()+"/swbadmin/images/";
   SWBResourceURL url = paramRequest.getActionUrl();
   Twitter twitter = new Twitter("george190475@hotmail.com","george24");
   Iterator<Status> itStatuses = (twitter.getFriendsTimeline()).iterator();
%>
    <a href="http://twitter.com"><img src="<%=imgPath%>twitter_logo.png" valign="top"/></a>
    <form action="<%=url.toString()%>">
    <table>
        <tr>
            <td>¿Que estas haciendo ahora?</td>
        </tr>
        <tr>
            <td><textarea name="status" cols="50" rows="5"></textarea></td>
        </tr>
        <tr>
            <td><input type="submit" value="enviar"></td>
        </tr>
    </table>
    </form>

    <h2 class="titulo">Mi Twitter</h2>
    <%
        int cont=0;
        while(itStatuses.hasNext()){
            Status twitt=itStatuses.next();
            String timeAgo=SWBUtils.TEXT.getTimeAgo(twitt.getCreatedAt(), "es");
            %>
             <p class="addOn">
                <img src="<%=imgPath%>twitter.png" valign="top" width="20" height="20"/><%=twitt.getText()%><br><%=timeAgo%><br/>
             </p>
            <%
            cont++;
            if(cont>=10) break;
        }
    %>
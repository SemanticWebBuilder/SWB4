<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBPlatform"%>


<%
   String imgPath=SWBPlatform.getContextPath()+"/swbadmin/images/";
   SWBResourceURL url = paramRequest.getActionUrl();
   User user=paramRequest.getUser();
   Resource base=paramRequest.getResourceBase();
   if(base.getData(user)!=null){
       String userData=base.getData(user);
       int pos=userData.indexOf("|");
       if(pos>-1){
       String userLogin=userData.substring(0,pos);
       String userPass=userData.substring(pos+1);
       Twitter twitter = new Twitter(userLogin, userPass);
       Iterator<Status> itStatuses = (twitter.getFriendsTimeline()).iterator();
       url.setAction("send2Twitter");       
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
         }
     }else { //Forma para que el usuario proporcione login y password de twitter
       url.setAction("saveUserData");
       %>
       <form action="<%=url.toString()%>" method="post">
        <table>
            <tr>
                <td colspan="2" align="center">Proporciona tus datos de tu cuenta de twitter</td>
            </tr>
            <tr>
                <td>Login:</td><td><input type="text" name="twitterLogin" size="25"/></td>
            </tr>
            <tr>
                <td>Password:</td><td><input type="password" name="twitterPass" size="25"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="enviar"></td>
            </tr>
        </table>
        </form>
      <%
     }
     %>
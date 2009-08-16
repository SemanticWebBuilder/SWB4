<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>

     <%
        User user=paramRequest.getUser();
        if(request.getParameter("user")!=null) user=paramRequest.getWebPage().getWebSite().getUserRepository().getUser(request.getParameter("user"));
        WebPage wpage=paramRequest.getWebPage();
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
     %>
         <table>
             <%
             boolean flag=false;
             int cont=0;
             int contTot=0;
             Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user,wpage.getWebSite());
             while(itMyFriends.hasNext()){
                 Friendship friendShip=itMyFriends.next();
                 Iterator<User> itfriendUser=friendShip.listFriends();
                 while(itfriendUser.hasNext()){
                     User friendUser=itfriendUser.next();
                     if(!friendUser.getId().equals(user.getId()))
                     {
                         if(!flag && cont==0) out.println("<tr>");
                         cont++;
                         contTot++;
                         %>
                         <td>
                            <a href="<%=wpage.getUrl()%>?user=<%=friendUser.getId()%>"><img src="<%=friendUser.getPhoto()%>"></a>
                         </td>
                         <%
                         if(cont==4) {
                            out.println("</tr>");
                            cont=0;
                            flag=false;
                         }
                         if(contTot==12) break;
                     }
                 }
             }
             %>
         </table>
     <%
     }else{
     %>
        <table>
             <%
             Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user, wpage.getWebSite());
             while(itMyFriends.hasNext())
             {
                 Friendship friendShip=itMyFriends.next();
                 User friendUser=friendShip.getFriend();
                 %>
                 <tr>
                     <td>
                        <a href="/swb/Ciudad_Digital/Perfil?user=<%=friendUser.getId()%>"><img src="<%=friendUser.getPhoto()%>"></a>
                    </td>
                 </tr>
                 <%
             }
             %>
         </table>
     <%
     }
     %>
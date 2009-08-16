<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>

     <%
        User user=paramRequest.getUser();
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!user.isRegistered()) return;
        WebPage wpage=paramRequest.getWebPage();
        String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
        String imgSize="";
        boolean isStrategy=false;
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) 
        {
            isStrategy=true;
            imgSize="width=\"40\" height=\"40\"";
        }
     %>
         <table>
             <%
             String firstName="", lastName="";

             boolean flag=false;
             int cont=0;
             int contTot=0;
             Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user,wpage.getWebSite());
             while(itMyFriends.hasNext()){
                 Friendship friendShip=itMyFriends.next();
                 Iterator<User> itfriendUser=friendShip.listFriends();
                 while(itfriendUser.hasNext()){
                     User friendUser=itfriendUser.next();
                     if(!friendUser.getURI().equals(user.getURI()))
                     {
                         if(friendUser.getPhoto()!=null) photo=friendUser.getPhoto();
                         if(friendUser.getFirstName()!=null) firstName=friendUser.getFirstName();
                         if(friendUser.getLastName()!=null) lastName=friendUser.getLastName();

                         if(!flag && cont==0) out.println("<tr>");
                         cont++;
                         contTot++;
                         %>
                         <td align="right">
                            <a href="<%=wpage.getParent().getUrl()%>?user=<%=friendUser.getEncodedURI()%>"><img src="<%=photo%>" <%=imgSize%> title="<%=firstName%> <%=lastName%>">
                            <%if(!isStrategy){%>
                                <br>
                                <%=firstName%>
                                <%=lastName%>
                            <%}%>
                            </a>
                         </td>
                         <%
                         if(cont==4) {
                            out.println("</tr>");
                            cont=0;
                            flag=false;
                         }
                         if(isStrategy && contTot==12) break;
                     }
                 }
             }
             %>
         </table>
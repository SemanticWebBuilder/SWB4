<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>

     <%
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        WebPage wpage=paramRequest.getWebPage();
        if(!owner.isRegistered() || !user.isRegistered()) return;

        if(paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
          if(!owner.getURI().equals(user.getURI())){ //Si el usuario que esta en session(owner) es diferente que el que vino por parametro (user)
              %>
                <a href="<%=wpage.getUrl()%>?remFriendRelship=<%=user.getEncodedURI()%>&user=<%=owner.getEncodedURI()%>">Eliminar como amigo</a>
              <%
          }
        }else {
         String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
         if(user.getPhoto()!=null) photo=user.getPhoto();
         String userFirstName="", userLastName="", secondName="";

         if(user.getFirstName()!=null) userFirstName=user.getFirstName();
         if(user.getLastName()!=null) userLastName=user.getLastName();
         if(user.getSecondLastName()!=null) secondName=user.getSecondLastName();

     %>

     <table>
         <tr>
             <td>
                 <img src="<%=photo%>" valign="top"/>
             </td>
             <td>
                 <table>
                 <tr><td>
                 <%=userFirstName%> <%=userLastName%> <%=secondName%><br/>
                 </td></tr>
                 </table>
             </td>
         </tr>
     </table>

     <%
      }
      if(request.getParameter("remFriendRelship")!=null)
      {
         SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("remFriendRelship"));
         User user2rem=(User)semObj.createGenericInstance();

         Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(owner,wpage.getWebSite());
         while(itMyFriends.hasNext()){
             Friendship friendShip=itMyFriends.next();
             Iterator<User> itfriendUser=friendShip.listFriends();
             while(itfriendUser.hasNext()){
                 User friendUser=itfriendUser.next();
                 if(friendUser.getURI().equals(user2rem.getURI()))
                 {
                     friendShip.remove();
                 }
              }
          }
      }
     %>


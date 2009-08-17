<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

     <%
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        Resource base=paramRequest.getResourceBase();
        String perfilPath=base.getAttribute("perfilPath","/swb/Ciudad_Digital/Perfil");
        String friendsPath=base.getAttribute("friendsPath","/swb/Ciudad_Digital/Mis_amigos");
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
            imgSize="width=\"39\" height=\"39\"";
        }
     %>
          <div class="miembros">
          <h2 class="titulo">Mis Amigos</h2>
             <%
             String firstName="", lastName="";
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
                         %>
                            <div class="moreUser">
                            <a href="<%=perfilPath%>?user=<%=friendUser.getEncodedURI()%>"><img src="<%=photo%>" <%=imgSize%> title="<%=firstName%> <%=lastName%>">
                            <%if(!isStrategy){%>
                                <br>
                                <%=firstName%>
                                <%=lastName%>
                            <%}%>
                            </a>
                         </div>
                         <%
                         contTot++;
                         if(isStrategy && contTot==12) break;
                     }
                 }
             }
             if(isStrategy && contTot>=12){%>
                 <div class="clear">
                    <p class="vermas"><a href="<%=friendsPath%>" >Ver todos</a></p>
                 </div>
             <%}else if(contTot==0){%>
               <div class="clear">
                <p class="vermas">Aún no tienes amigos &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
               </div>
             <%}%>
        </div>
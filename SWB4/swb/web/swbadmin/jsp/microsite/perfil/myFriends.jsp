<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
     <%
        String perfilPath=paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
        String friendsPath=paramRequest.getWebPage().getWebSite().getWebPage("Amigos").getUrl();
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!user.isRegistered()) return;
        WebPage wpage=paramRequest.getWebPage();
        String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
        String imgSize="width=\"80\" height=\"70\"";;
        boolean isStrategy=false;
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) 
        {
            isStrategy=true;
            imgSize="width=\"39\" height=\"39\"";            
        }
/*
        if(isStrategy){
            System.out.println("user2Show-Estrategia:"+user2Show.getFullName());
        }else{
            System.out.println("user2Show-Contenido:"+user2Show.getFullName());
        }
*/
     %>
          <div class="miembros">
          <%if(owner==user){%>
            <h2>Mis Amigos</h2>
          <%}else{%> <h2>Amigos de <%=user.getFirstName()%></h2> <%}%>

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
                            <a href="<%=perfilPath%>?user=<%=friendUser.getEncodedURI()%>"><img src="<%=SWBPlatform.getWebWorkPath()+photo%>" <%=imgSize%> title="<%=firstName%> <%=lastName%>">
                            <%if(!isStrategy){%>
                                <br>
                                <%=firstName%>
                                <%=lastName%>
                            <%}%>
                            </a>
                         </div>
                         <%
                         contTot++;
                         if(isStrategy && contTot==18) break;
                     }
                 }
             }
             if(isStrategy && contTot>=18){%>
                 <div class="clear">
                    <p class="vermas"><a href="<%=friendsPath%>" >Ver todos</a></p>
                 </div>
             <%}else if(contTot==0){%>
               <div class="clear">
                <p class="titulo">Aún no tienes amigos &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
               </div>
             <%}%>
             <div class="clear"></div>
          </div>

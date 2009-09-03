<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
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
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
        String imgSize="width=\"80\" height=\"70\"";
        
        boolean isStrategy=false;
        isStrategy=true;
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) 
        {            
            imgSize="width=\"39\" height=\"39\"";            
        }
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
                         %>
                            <div class="moreUser">
                            <a href="<%=perfilPath%>?user=<%=friendUser.getEncodedURI()%>"><img src="<%=SWBPlatform.getWebWorkPath()+photo%>" <%=imgSize%> alt="Foto de <%=friendUser.getFullName()%>" title="<%=friendUser.getFullName()%>">
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

<%
}
else
{
            %>
            <div class="miembros">
            <%
            
            HashMap<String,SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
            Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
            while(list.hasNext())
            {
                SemanticProperty prop=list.next();
                mapa.put(prop.getName(), prop);
            }
            Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user,wpage.getWebSite());
             while(itMyFriends.hasNext())
             {
                 Friendship friendShip=itMyFriends.next();
                 Iterator<User> itfriendUser=friendShip.listFriends();
                 while(itfriendUser.hasNext())
                 {
                     User friendUser=itfriendUser.next();
                     if(!friendUser.getURI().equals(user.getURI()))
                     {
                         String usr_sex = (String)friendUser.getExtendedAttribute(mapa.get("userSex"));
                         Object usr_age = (Object)friendUser.getExtendedAttribute(mapa.get("userAge"));
                         if (null==usr_age) usr_age = "";
                         if ("M".equals(usr_sex)) usr_sex = "Hombre";
                         if ("F".equals(usr_sex)) usr_sex = "Mujer";
                         String usr_status = (String)friendUser.getExtendedAttribute(mapa.get("userStatus"));
                         if (null==usr_status) usr_status = "";
                         if(friendUser.getPhoto()!=null) photo=friendUser.getPhoto();
                         %>
                         
                        <div class="profilePic" onMouseOver="this.className='profilePicHover'" onMouseOut="this.className='profilePic'">
                    <img src="<%=SWBPlatform.getWebWorkPath()+photo%>" width="150" height="150" alt="Foto de <%=friendUser.getFullName()%>" />
                    <p><a class="contactos_nombre" href="/swb/Ciudad_Digital/perfil?user=http%3A%2F%2Fuser.Ciudad_Digital.swb%23swb_User%3A5" alt="Ir al perfil de <%=friendUser.getFullName()%>" ><%=friendUser.getFullName()%></a></p>
                    <p>Edad: <%=usr_age%></p>
                    <p>Sexo: <%=usr_sex%></p>
                    <p>Tipo: <%=usr_status%></p>
                    </div>
                        
                         <%
                     }
                 }
             }
    %>
            </div>
            <%

}
%>
             
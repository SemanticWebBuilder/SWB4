<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


     <%
        boolean areFriends=false;
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!owner.isRegistered() || !user.isRegistered()) return;
        if(!owner.getURI().equals(user.getURI())
                && Friendship.areFriends(owner, user, paramRequest.getWebPage().getWebSite())) areFriends=true;


        if(paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
          if(areFriends){ //Si el usuario que esta en session(owner) es diferente que el que vino por parametro (user)
              urlAction.setAction("remFriendRelship");
              urlAction.setParameter("user", user.getURI());
              %>
                    <a href="<%=urlAction%>">Eliminar como amigo</a>
              <%
          }
        }else {
             String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
             if(user.getPhoto()!=null) photo=user.getPhoto();
             String userFirstName="", userLastName="", secondName="", email="";

             if(user.getFirstName()!=null) userFirstName=user.getFirstName();
             if(user.getLastName()!=null) userLastName=user.getLastName();
             if(user.getSecondLastName()!=null) secondName=user.getSecondLastName();
             if(user.getEmail()!=null) email=user.getEmail();
             %>

             <table>
                 <tr>
                     <td>
                         <img src="<%=photo%>" valign="top"/><br>
                         <%=userFirstName%> <%=userLastName%> <%=secondName%><br/>
                     </td>
                 </tr>
                 <%
                    if(owner==user || areFriends){ //Agregar datos privados (email, sexo, fotos, etc)
                        %>
                            <tr>
                                <td>
                                    <%=email%>
                                </td>
                            </tr>
                        <%
                    }
                 %>
              </table>
              <%
            }
        %>
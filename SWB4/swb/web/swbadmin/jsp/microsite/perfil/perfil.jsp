<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.community.util.CommUtil"%>

     <%
        CommUtil commUtil=new CommUtil();
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!owner.isRegistered() || !user.isRegistered()) return;
        if(paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
          if(!owner.getURI().equals(user.getURI())){ //Si el usuario que esta en session(owner) es diferente que el que vino por parametro (user)
              urlAction.setAction("remFriendRelship");
              urlAction.setParameter("user", user.getURI());
              if(commUtil.areFriends(owner, user, paramRequest.getWebPage().getWebSite())){
              %>
                    <a href="<%=urlAction%>">Eliminar como amigo</a>
              <%
              }
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
         <%
            if(commUtil.areFriends(owner, user, paramRequest.getWebPage().getWebSite())){ //Agregar datos privados (email, sexo, fotos, etc)
                %>
                    <tr>
                        <td>
                            email:<%=user.getEmail()%>
                        </td>
                    </tr>
                <%
            }
         %>
     </table>
     <%
     }
    %>

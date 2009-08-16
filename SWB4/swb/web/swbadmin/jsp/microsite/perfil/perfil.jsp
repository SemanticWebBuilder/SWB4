<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>

     <%
        User user=paramRequest.getUser();
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        WebPage wpage=paramRequest.getWebPage();
        if(user!=null && user.isRegistered()){
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
     %>


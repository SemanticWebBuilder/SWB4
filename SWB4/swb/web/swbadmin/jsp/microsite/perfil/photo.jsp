<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


<%
User owner=paramRequest.getUser();
User user=owner;
if(request.getParameter("user")!=null)
{
    SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
    user=(User)semObj.createGenericInstance();
}
if(!owner.isRegistered() || !user.isRegistered()) return;
Resource base=paramRequest.getResourceBase();
String registryPath=base.getAttribute("registryPath","");
String attributes=base.getAttribute("attributes","");

 String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
 if(user.getPhoto()!=null) photo=user.getPhoto();
 %>
 <img src="<%=photo%>" valign="top"/><br>
 <a href="<%=registryPath%>" <%=attributes%>>Cambiar imagen</a>
                      
          
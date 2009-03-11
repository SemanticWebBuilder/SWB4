<%@page import="org.semanticwb.model.GenericFormElement"
%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"
/><jsp:useBean id="url" scope="request" class="java.lang.String"
/><jsp:useBean id="query" scope="request" class="java.lang.String"
/><%
  if (!paramRequest.getUser().isSigned())
        {
%><form action="<%=url+"?"+query%>" method="post">
<fieldset><label>Usuario:</label><input type="text" id="wb_username" name="wb_username" /><br />
<label>Contrase&ntilde;a:</label><input type="password" id="wb_password" name="wb_password" /><br />
<input type="submit" value="Enviar" /></fieldset></form>
<% } else {
%><a href="<%=url%>?wb_logout=true" >Logout</a>
<% }%>

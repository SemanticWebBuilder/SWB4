<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<div>
  <div>
    <p><strong>Tu cuenta a sido activada  satisfacoriamente</strong></p>
    <p><a href="/swb/<%=paramRequest.getWebPage().getWebSite().getId()%>/">Continuar...</a></p>
  </div>
</div>

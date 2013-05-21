<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    Resource base=paramRequest.getResourceBase();
    String msg = base.getAttribute("page_activation");
%>
<div>
    <div>
        <p><strong><%=msg%></strong></p>
    </div>
</div>

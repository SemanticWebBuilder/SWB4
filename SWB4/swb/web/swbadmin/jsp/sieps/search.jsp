<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
	SWBResourceURL url = paramRequest.getRenderUrl().setParameter("act", "results");
	String query = request.getParameter("query") == null ? "" : request.getParameter("query") ;
%>
    <form id="frmSearch" name="frmSearch" method="post" action="<%=url%>">
      <p>
        <label for="query">Buscar</label>
        <input type="text" name="query" id="query" value="<%=query%>" />
        <label for="btnSearch">Buscar</label>
        <input type="submit" name="btnSearch" id="btnSearch" value="Buscar" onclick="javascript:this.form.submit();"/>
      </p>
      <p class="ejemplo">Ejemplo: Constructoras, materiales, Querétaro, San Juan del Río, Código 236221</p>
    </form>
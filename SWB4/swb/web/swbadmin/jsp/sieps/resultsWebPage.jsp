
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%
	String query = request.getParameter("query");
	SWBResourceURL urlDetail = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	List<WebPage> empresas = (List<WebPage>)request.getAttribute("results");
	boolean isResultados   = (empresas != null &&  !empresas.isEmpty());
%>

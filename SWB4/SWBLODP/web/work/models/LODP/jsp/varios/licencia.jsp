<%-- 
    Document   : panel
    Created on : 18/06/2013, 02:13:59 PM
    Author     : juan.fernandez
--%>

<%@page import="com.infotec.lodp.swb.LicenseType"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="org.semanticwb.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<div class="license">
<%
    
    String suri = request.getParameter("suri");
    if(null!=suri&&suri.trim().length()>0){
        SemanticObject so = SemanticObject.getSemanticObject(SemanticObject.shortToFullURI(suri));
        if(null!=so&&so.createGenericInstance() instanceof LicenseType){
            LicenseType lic = (LicenseType) so.createGenericInstance();
%>
        <h2><%=lic.getLicenseTitle()!=null?lic.getLicenseTitle():"--"%></h2>
        <p><%=lic.getLicenseDescription()!=null?lic.getLicenseDescription():"--"%></p>
        <%
        } else {
            %>
        <h3>No se encontró información de licencia con los parámetros recibidos.</h3>
        <%
        }
    } else {
        %>
         <h3>No se encontró información de licencia con los parámetros recibidos.</h3>
        <%
    }
    
%>
</div>

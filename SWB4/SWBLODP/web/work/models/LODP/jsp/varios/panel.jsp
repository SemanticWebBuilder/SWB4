<%-- 
    Document   : panel
    Created on : 18/06/2013, 02:13:59 PM
    Author     : juan.fernandez
--%>

<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="org.semanticwb.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
User usr = paramRequest.getUser();
Publisher pub = LODPUtils.getPublisher(usr);

if(pub!=null){
%>

    <div id="menu" class="menuadmin">
      <ul>
        <li><a href="/es/LODP/home" class="m-inicio">Inicio</a></li>
        <li><a href="/es/LODP/Acerca_del_Sitio" class="m-acerca">Acerca del Sitio</a></li>
        <li><a href="/es/LODP/Datos" class="m-datos">Datos</a></li>
        <li><a href="/es/LODP/Aplicaciones" class="m-apps">Aplicaciones</a></li>
        <li><a href="/es/LODP/Publicadores?act=arregloLetras" class="m-pub">Publicadores</a></li>
        <!-- li><a href="/es/LODP/Foro" class="m-foro">Foro</a></li>
        <li><a href="/es/LODP/Participa" class="m-participa">Participa </a></li -->
        <li><a href="/es/LODP/Panel_de_control" class="m-panel">Administración</a></li>
      </ul>
    </div>
<%
} else {
%>
    <div id="menu" class="menuusr">
      <ul>
        <li><a href="/es/LODP/home" class="m-inicio">Inicio</a></li>
        <li><a href="/es/LODP/Acerca_del_Sitio" class="m-acerca">Acerca del Sitio</a></li>
        <li><a href="/es/LODP/Datos" class="m-datos">Datos</a></li>
        <li><a href="/es/LODP/Aplicaciones" class="m-apps">Aplicaciones</a></li>
        <li><a href="/es/LODP/Publicadores?act=arregloLetras" class="m-pub">Publicadores</a></li>
        <li><a href="/es/LODP/Foro" class="m-foro">Foro</a></li>
        <li><a href="/es/LODP/Participa" class="m-participa">Participa </a></li>
      </ul>
    </div>
<%
}
%>


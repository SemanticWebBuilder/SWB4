<%-- 
    Document   : updateRepository
    Created on : 8/04/2013, 05:45:13 PM
    Author     : juan.fernandez
--%>

<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String act = request.getParameter("act");             
            String siteid = request.getParameter("site");
            if (null == act) {
                act = "";
            }

        %>
        <h1>Actualiza Web-Pages del Recurso Repository</h1>

        <form name="fstep2" id="fstep2" method="post" action="" >
            <input type="hidden" name="act" value="step1" />

            <fieldset>
                <legend>Selecciona el sitio a consultar:</legend>
                <ul>
                    <%

                        boolean radioChecked = false;
                        String strChecked = "checked";
                        //Lista de sitios existentes
                        Iterator<WebSite> ittm = WebSite.ClassMgr.listWebSites();
                        while (ittm.hasNext()) {
                            WebSite tm = ittm.next();
                                String activo = "<font color=\"green\">Sitio activo</font>";
                                if (!tm.isActive()) {
                                    activo = "<font color=\"red\">Sitio inactivo</font>";
                                }

                                if (!radioChecked && siteid == null) {
                                    strChecked = "";
                                } else {
                                    strChecked = siteid != null && siteid.equals(tm.getId()) ? "checked" : "";
                                }

                    %>
                    <li><input type="radio" id="<%=tm.getId()%>" name="site" value="<%=tm.getId()%>" <%= strChecked%> ><label for="<%=tm.getId()%>"><%=tm.getTitle()%>&nbsp;<i><%=activo%></i></label></li>
                        <%
                                    if (!radioChecked) {
                                        radioChecked = true;
                                        strChecked = "";
                                    }
                                
                            }
                        %>

                </ul>
            </fieldset>
            <fieldset>
                <button type="submit" name="bsend" id="bsend">Enviar</button>
            </fieldset>
        </form>
        <%
            if ("step1".equals(act) && siteid != null) {

                WebSite tm = WebSite.ClassMgr.getWebSite(siteid);
                String idHomeRepo = "CNFWB_Rep";
                int finded = 0;
        %>
        <fieldset ><legend>Resultado...</legend>
            <br/><br/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Home Repository</th>
                        <th>Carpetas</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Iterator<WebPage> iTp = tm.listWebPages();
                        while (iTp.hasNext()) {
                            WebPage tp_child = iTp.next();
                            if (tp_child.getId().startsWith(idHomeRepo)) {
                                finded++;
                    %>
                    <tr>
                        <td><%=tp_child.getId()%></td>
                        <td>WebPage Home del repositorio</td>
                    </tr>
                    <%
                                // llamado a hijos recursivo
                                revisaHijos(tp_child);
                            }
                        }
                        if (finded > 0) {
                    %>
                    <tr><td colspan="2"><strong>Se encontraron <%=finded%> recursos de tipo Repositorio.</strong></td></tr>
                    <%        }


                        if (finded == 0) {
                    %>
                    <tr><td colspan="2"><strong>No se encontraron recursos de tipo Repositorio.</strong></td></tr>
                    <%        }
                        }
                    %>
                </tbody>
            </table>
        </fieldset>
    </body>
</html>

<%!
    public void revisaHijos(WebPage wp) {

        Iterator<WebPage> it_child = wp.listChilds();
        while (it_child.hasNext()) {  // carpetas del recurso repository
            WebPage wps = it_child.next();
            wps.removeAllVirtualParent();
            wps.addVirtualParent(wp);
            if (wps.listChilds().hasNext()) {
                revisaHijos(wps);
            }
        }
    }

%>
<%-- 
    Document   : graphBarFilter
    Created on : 16/09/2013, 07:31:57 PM
    Author     : gabriela.rosales
--%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<!DOCTYPE html>

<%
    SWBResourceURL urlRender = paramRequest.getRenderUrl();
    String selected = request.getParameter("selected");
    String suri = request.getParameter("suri");

    if (suri == null) {
        return;
    }
    
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    String args = "?objUri=" + semObj.getEncodedURI();
    String lang = paramRequest.getUser().getLanguage();
    args += "&lang=" + lang;


    String anioSelected = "";
    ArrayList listMeses = new ArrayList();
    listMeses.add("Enero");
    listMeses.add("Febrero");
    listMeses.add("Marzo");
    listMeses.add("Abril");
    listMeses.add("Mayo");
    listMeses.add("Junio");
    listMeses.add("Julio");
    listMeses.add("Agosto");
    listMeses.add("Septiembre");
    listMeses.add("Octubre");
    listMeses.add("Noviembre");
    listMeses.add("Diciembre");
    Iterator iMeses = listMeses.iterator();

    ArrayList listAnio = new ArrayList();
    listAnio.add("2013");
    listAnio.add("2012");

    Iterator iAnio = listAnio.iterator();
    SWBResourceURL url = paramRequest.getRenderUrl();

%>
<html>
    <head>
       <script type="text/javascript" id="appends">
    appendHtmlAt = function(url, tagid, location){
   
    }
</script>
    </head>
    
    <body>

        <div class="pub-redes">
            <p class="titulo">Seleccione:</p> 


            <%
                if (selected.equals("1")) {
                    
            %>
            <div id="graphBardivd" >
                <form name="formgraphBarAnio" id="formgraphBarAnio" dojoType="dijit.form.Form" method="post" enctype="multipart/form-data" action="">
                    <table>
                        <tr>
                        <select name="selectAnio" id="selectAnio">
                            <option value=""><---Seleccione el año----></option>
                            <%
                                while (iAnio.hasNext()) {
                                    String anio = (String) iAnio.next();
                            %>
                            <option value="<%=anio%>"><%=anio%></option>
                            <%}%>                            
                        </select>
                        </tr>
                        <tr>
                        <input type="button" value="Mostrar" onclick="javascript:appendHtmlAt();postHtml('<%=urlRender.setMode("showGraphBar")%>?selectedAnio='+escape(document.formgraphBarAnio.selectAnio[document.formgraphBarAnio.selectAnio.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>', 'showgraphBar');">
                        </tr>
                    </table>
                </form>
            </div>
        </div>
                        
        <%
        } else if (selected.equals("2")) {
        %>           

        <div id="graphBardivd" >
            <form type="dijit.form.Form" id="createPost" name="createPost" action="<%=urlRender.setMode("showGraphBar")%>" method="post" onsubmit="javascript:postHtml('<%=urlRender.setMode("showGraphBar")%>?selectAnio='+escape(document.createPost.selectAnio2[document.createPost.selectAnio2.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%> ', 'showgraphBar');">
                <input type="hidden" id="suri" name="suri" value="<%=URLEncoder.encode(suri)%>" >
                <table>
                    <tr>
                        <td>
                            <select name="selectAnio2" id="selectAnio2">
                                <option value=""><---Seleccione el año----></option>
                                <%
                                    while (iAnio.hasNext()) {
                                        String anio = (String) iAnio.next();
                                %>
                                <option value="<%=anio%>"><%=anio%></option>
                                <%}%>                            
                            </select>
                        </td>
                        <td>
                            <select name="selectMes" id="selectMes">
                                <option value=""><---Seleccione el mes----></option>
                                <%
                                    int valuemonth = 1;
                                    while (iMeses.hasNext()) {
                                        String mes = (String) iMeses.next();
                                %>
                                <option value="<%=valuemonth%>"><%=mes%></option>
                                <%
                                        valuemonth++;
                                    }
                                %>                            
                            </select>
                        </td>
                        <td></td>
                        <td>
                            <input type="button" value="Mostrar" onclick="javascript:postHtml('<%=urlRender.setMode("showGraphBar")%>?selectAnio='+escape(document.createPost.selectAnio2[document.createPost.selectAnio2.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>&selectMes='+escape(document.createPost.selectMes[document.createPost.selectMes.selectedIndex].value) +'', 'showgraphBar');">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <%
        }
    %>

    <div id="showgraphBar" dojoType="dijit.layout.ContentPane">
    </div>
</body>
</html>


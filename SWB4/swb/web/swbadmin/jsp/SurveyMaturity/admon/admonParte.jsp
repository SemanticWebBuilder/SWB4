<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.questionnaire.Part"%>
<%@page import="org.semanticwb.model.WebSite"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<script type="text/javascript">
    function showAdmonParte()
    {
        resetEditor('tituloparteditor');
        resetEditor('descriptionparteditor');
        dijit.byId("dialogAdmonParte").show();
    }
</script>
<h1>Administraci&oacute;n de partes de un cuestionario</h1><br>
<table width="100%">
    <th>Parte</th>
    <th>Acci&oacute;n</th>
    <%
                WebSite site = paramRequest.getWebPage().getWebSite();
                Iterator<Part> parts = Part.ClassMgr.listParts(site);
                while (parts.hasNext())
                {
                    Part part = parts.next();
                    String title = part.getTitle();
    %>
    <tr>
        <td>
            <p><%=title%></p>
        </td>
        <td>

        </td>
    </tr>
    <%

                }
    %>

    <tr>
        <td colspan="2">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="button" value="Agregar Parte" onclick="showAdmonParte();">
        </td>
    </tr>
</table>



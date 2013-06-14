<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.questionnaire.Part"%>
<%@page import="org.semanticwb.model.WebSite"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    public String encode(String text)
    {
        text = text.replace("á", "&aacute;");
        text = text.replace("é", "&eacute;");
        text = text.replace("í", "&iacute;");
        text = text.replace("ó", "&oacute;");
        text = text.replace("ú", "&uacute;");
        text = text.replace("Á", "&Aacute;");
        text = text.replace("É", "&Eacute;");
        text = text.replace("Í", "&Iacute;");
        text = text.replace("Ó", "&Oacute;");
        text = text.replace("Ú", "&Uacute;");

        return text;
    }
%>

<script type="text/javascript">
    function showAdmonParte()
    {
        resetEditor('tituloparteditor');
        resetEditor('descriptionparteditor');
        dijit.byId("dialogAdmonParte").show();
    }
    function deletePart(url)
    {
        
    }
</script>
<h1>Administraci&oacute;n de partes de un cuestionario</h1><br>
<input type="button" value="Agregar Parte" onclick="showAdmonParte();"><br><br>
<table width="100%" cellpadding="2" cellspacing="2" border="1">
    <th>Parte</th>
    <th>Acci&oacute;n</th>    
    <%
                SWBResourceURL urlAction = paramRequest.getActionUrl();
                WebSite site = paramRequest.getWebPage().getWebSite();
                Iterator<Part> parts = Part.ClassMgr.listParts(site);
                while (parts.hasNext())
                {
                    Part part = parts.next();
                    String name = part.getNamePart();
                    if (name == null)
                    {
                        name = "";
                    }
                    name = encode(name);
                    String uri = part.getURI();
    %>
    <tr>
        <td>
            <p><%=name%></p>
        </td>
        <td style="text-align: center;" class="tban-tarea">
            <a href="#" title="Editar" onclick="showEditPart('<%=uri%>');"><img alt="editar"  src="/swbadmin/jsp/SurveyMaturity/images/edit.png"></a>
            <a href="#" title="Eliminar" onclick="deletePart('<%=urlAction.setAction(SWBResourceURL.Action_REMOVE)%>');"><img alt="eliminar"  src="/swbadmin/jsp/SurveyMaturity/images/delete.png"></a>
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



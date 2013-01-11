<%@page import="org.semanticwb.process.resources.ProcessForm"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.resources.FormsBuilderResource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.Resource"%>
<%
User user=SWBContext.getAdminUser();
if(user==null)
{
    response.sendError(403);
    return;
}

response.setContentType("text/html; charset=ISO-8859-1");
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(FormsBuilderResource.ATT_PARAMREQUEST);
Resource base = (Resource) request.getAttribute(FormsBuilderResource.ATT_RBASE);
String propIdx = request.getParameter(FormsBuilderResource.PARAM_PROPIDX);
String lang = "es";
if (paramRequest.getUser() != null && paramRequest.getUser().getLanguage() != null) {
    lang = paramRequest.getUser().getLanguage();
}
String str = base.getAttribute("prop" + propIdx, "");
HashMap<String, String> propsMap = ProcessForm.getPropertiesMap(str);

SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propsMap.get("propId"));
if (prop != null) {
    SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(FormsBuilderResource.ACT_UPDPROP);
    %>
    <script type="text/javascript">
        dojo.require('dojox.form.DropDownSelect');
    </script>
    <form class="swbform" action="<%=actUrl%>" method="post" dojoType="dijit.form.Form" id="<%=prop.getURI()+"/form"%>" onsubmit="submitForm('<%=prop.getURI()+"/form"%>');return false;">
        <input type="hidden" name="<%=FormsBuilderResource.PARAM_PROPIDX%>" value="<%=propIdx%>"/>
        <fieldset>
            <%=propsMap.get("varName")+"."+prop.getName()%>
        </fieldset>
        <fieldset>
        <table>
            <tr>
                <td><label>Etiqueta: </label></td>
                <td>
                    <input dojoType="dijit.form.TextBox" type="text" name="<%=FormsBuilderResource.PARAM_PROPLBL%>" style="width:300px;" value="<%=propsMap.get("label")==null?prop.getDisplayName(lang):propsMap.get("label")%>"/>
                </td>
            </tr>
            <tr>
                <td><label>FormElement: </label></td>
                <td>
                    <select name="<%=FormsBuilderResource.PARAM_PROPFE%>" style="width:300px;">
                        <%=FormsBuilderResource.getFESelect(propsMap.get("fe"), paramRequest, prop) %>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>Modo: </label></td>
                <td>
                    <input type="radio" <%=propsMap.get("mode").equals("view")?"checked":""%> name="<%=FormsBuilderResource.PARAM_PROPMODE%>" value="view"/><label>Vista</label>
                    <input type="radio" <%=propsMap.get("mode").equals("edit")?"checked":""%> name="<%=FormsBuilderResource.PARAM_PROPMODE%>" value="edit"/><label>Edición</label>
                </td>
            </tr>
        </table>
        </fieldset>
        <fieldset>
            <button type="submit" dojoType="dijit.form.Button">Aceptar</button>
            <button dojoType="dijit.form.Button" onclick="hideDialog('configDialog');">Cancelar</button>
        </fieldset>
    </form>
    <!--a href="#" onclick="submitUrl('<%=actUrl%>',this); hideDialog('editDialog'); return false;">Ir a procesar</a-->
    <%
} else {
    %>Ha ocurrido un error al obtener la propiedad.<%
}
%>
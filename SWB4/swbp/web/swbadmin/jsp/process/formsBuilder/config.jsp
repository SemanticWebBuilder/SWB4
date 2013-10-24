<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.resources.ProcessForm"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
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
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(ProcessForm.ATT_PARAMREQUEST);
Resource base = (Resource) request.getAttribute(ProcessForm.ATT_RBASE);

WebSite site = paramRequest.getWebPage().getWebSite();
String action = paramRequest.getAction();
String lang = "es";
if (paramRequest.getUser() != null && paramRequest.getUser().getLanguage() != null) {
    lang = paramRequest.getUser().getLanguage();
}

if (ProcessForm.ACT_UPDPROP.equals(action)) {
    String propIdx = request.getParameter(ProcessForm.PARAM_PROPIDX);
    String str = base.getAttribute("prop" + propIdx, "");
    HashMap<String, String> propsMap = ProcessForm.getPropertiesMap(str);

    SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propsMap.get("propId"));
    if (prop != null) {
        SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDPROP);
        %>
        <form class="swbform" action="<%=actUrl%>" method="post" dojoType="dijit.form.Form" id="<%=prop.getURI()+"/form"%>" onsubmit="submitForm('<%=prop.getURI()+"/form"%>');return false;">
            <input type="hidden" name="<%=ProcessForm.PARAM_PROPIDX%>" value="<%=propIdx%>"/>
            <fieldset>
                <%=propsMap.get("varName")+"."+prop.getName()%>
            </fieldset>
            <fieldset>
            <table>
                <tr>
                    <td><label><%=paramRequest.getLocaleString("lblLabel")%>: </label></td>
                    <td>
                        <input dojoType="dijit.form.TextBox" type="text" name="<%=ProcessForm.PARAM_PROPLBL%>" style="width:300px;" value="<%=propsMap.get("label")==null?prop.getDisplayName(lang):propsMap.get("label")%>"/>
                    </td>
                </tr>
                <tr>
                    <td><label><%=paramRequest.getLocaleString("lblFE")%>: </label></td>
                    <td>
                        <select name="<%=ProcessForm.PARAM_PROPFE%>" style="width:300px;">
                            <%=ProcessForm.getFESelect(propsMap.get("fe"), paramRequest, prop) %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label><%=paramRequest.getLocaleString("lblMode")%>: </label></td>
                    <td>
                        <input type="radio" <%=propsMap.get("mode").equals("view")?"checked":""%> name="<%=ProcessForm.PARAM_PROPMODE%>" value="view"/><label><%=paramRequest.getLocaleString("lblView")%></label>
                        <input type="radio" <%=propsMap.get("mode").equals("edit")?"checked":""%> name="<%=ProcessForm.PARAM_PROPMODE%>" value="edit"/><label><%=paramRequest.getLocaleString("lblEdit")%></label>
                    </td>
                </tr>
                <!--tr>
                    <td><label><%=paramRequest.getLocaleString("lblRoles")%>: </label></td>
                    <td>
                        <select multiple name="<%=ProcessForm.PARAM_ROLES%>" style="width:300px;">
                            <%--
                            //TODO: Obtener roles y marcar los seleccionados
                            String roleList = propsMap.get("roles");
                            ArrayList<String> _roles = new ArrayList<String>();
                            if (roleList != null) {
                                StringTokenizer stk = new StringTokenizer(roleList, ":");
                                while(stk.hasMoreTokens()) {
                                    String rN = stk.nextToken();
                                    _roles.add(rN);
                                }
                            }
                            Iterator<Role> roles = SWBComparator.sortByDisplayName(site.getUserRepository().listRoles(), lang);
                            while(roles.hasNext()) {
                                Role role = roles.next();
                                String selected = _roles.contains(role.getId())?"selected":"";
                                --%>
                                <option <%=selected%> value="<%=role.getId()%>"><%=role.getDisplayTitle(lang)%></option>
                                <%--
                            }
                            --%>
                        </select>
                    </td>
                </tr-->
            </table>
            </fieldset>
            <fieldset>
                <button type="submit" dojoType="dijit.form.Button"><%=paramRequest.getLocaleString("accept") %></button>
                <button dojoType="dijit.form.Button" onclick="hideDialog('configDialog');"><%=paramRequest.getLocaleString("cancel")%></button>
            </fieldset>
        </form>
        <!--a href="#" onclick="submitUrl('<%=actUrl%>',this); hideDialog('editDialog'); return false;">Ir a procesar</a-->
        <%
    } else {
        %><%=paramRequest.getLocaleString("msgNoPropDef")%><%
    }
} else if (ProcessForm.ACT_UPDBTNLABEL.equals(action)) {
    String btn = request.getParameter(ProcessForm.PARAM_BTNID);
    if (btn != null && btn.trim().length() > 0) {
        SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDBTNLABEL);
        String btnLabel = base.getAttribute(btn+"Label", "");
        %>
        <form class="swbform" action="<%=actUrl%>" method="post" dojoType="dijit.form.Form" id="<%=btn+"/form"%>" onsubmit="submitForm('<%=btn+"/form"%>');return false;">
            <input type="hidden" name="<%=ProcessForm.PARAM_BTNID%>" value="<%=btn%>" />
            <fieldset>
                <table>
                    <tr>
                        <td><label>Etiqueta <em>*</em>: </label></td>
                        <td><input dojoType="dijit.form.TextBox" type="text" name="btnLabel" value="<%=btnLabel%>" required="true"/></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset>
                <button type="submit" dojoType="dijit.form.Button"><%=paramRequest.getLocaleString("accept")%></button>
                <button dojoType="dijit.form.Button" onclick="hideDialog('configDialog');"><%=paramRequest.getLocaleString("cancel")%></button>
            </fieldset>
        </form>
        <%
    }
}
%>
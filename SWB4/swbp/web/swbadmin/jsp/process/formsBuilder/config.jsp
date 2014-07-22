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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
User user=SWBContext.getAdminUser();
if(user==null)
{
    response.sendError(403);
    return;
}

SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute(ProcessForm.ATT_PARAMREQUEST);
Resource base = (Resource) request.getAttribute(ProcessForm.ATT_RBASE);

String action = paramRequest.getAction();
String lang = "es";
String title = paramRequest.getLocaleString("lblEditTitle");
if (ProcessForm.ACT_UPDBTNLABEL.equals(action)) title = paramRequest.getLocaleString("lblEdit");

if (paramRequest.getUser() != null && paramRequest.getUser().getLanguage() != null) {
    lang = paramRequest.getUser().getLanguage();
}
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4><%=title%></h4>
        </div>
        <%
        if (ProcessForm.ACT_UPDPROP.equals(action)) {
            String propIdx = request.getParameter(ProcessForm.PARAM_PROPIDX);
            String str = base.getAttribute("prop" + propIdx, "");
            HashMap<String, String> propsMap = ProcessForm.getPropertiesMap(str);

            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propsMap.get("propId"));
            if (prop != null) {
                SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDPROP);
                %>
                <form class="form-horizontal" action="<%=actUrl%>" method="post" id="formEdit">
                    <div class="modal-body">
                        <input type="hidden" name="<%=ProcessForm.PARAM_PROPIDX%>" value="<%=propIdx%>"/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Propiedad:</label>
                            <div class="col-sm-9">
                                <p class="form-control-static"><%=propsMap.get("varName")+"."+prop.getName()%></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <%=paramRequest.getLocaleString("lblLabel")%>:
                            </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="<%=ProcessForm.PARAM_PROPLBL%>" style="width:300px;" value="<%=propsMap.get("label")==null?prop.getDisplayName(lang):propsMap.get("label")%>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <%=paramRequest.getLocaleString("lblFE")%>:
                            </label>
                            <div class="col-sm-9">
                                <select name="<%=ProcessForm.PARAM_PROPFE%>" class="form-control" style="width:300px;">
                                    <%=ProcessForm.getFESelect(propsMap.get("fe"), paramRequest, prop) %>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                <%=paramRequest.getLocaleString("lblMode")%>:
                            </label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" <%=propsMap.get("mode").equals("view")?"checked":""%> name="<%=ProcessForm.PARAM_PROPMODE%>" value="view"/>&nbsp;<%=paramRequest.getLocaleString("lblView")%>
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" <%=propsMap.get("mode").equals("edit")?"checked":""%> name="<%=ProcessForm.PARAM_PROPMODE%>" value="edit"/>&nbsp;<%=paramRequest.getLocaleString("lblModeEdit")%>
                                </label>
                            </div>
                        </div>
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
                                            <option <%--selected%> value="<%=role.getId()%>"><%=role.getDisplayTitle(lang)%></option>
                                            <%--
                                        }
                                        --%>
                                    </select>
                                </td>
                            </tr-->
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><%=paramRequest.getLocaleString("accept") %></button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("cancel")%></button>
                    </div>
                </form>
            <%
            } else {
                %>
                <div class="modal-body">
                    <%=paramRequest.getLocaleString("msgNoPropDef")%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"><%=paramRequest.getLocaleString("cancel")%></button>
                </div>
                <%
            }
        } else if (ProcessForm.ACT_UPDBTNLABEL.equals(action)) {
            String btn = request.getParameter(ProcessForm.PARAM_BTNID);
            if (btn != null && btn.trim().length() > 0) {
                SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDBTNLABEL);
                String btnLabel = base.getAttribute(btn+"Label", "");
                %>
                <form class="swbform" action="<%=actUrl%>" method="post" id="formEdit">
                    <div class="modal-body">
                        <input type="hidden" name="<%=ProcessForm.PARAM_BTNID%>" value="<%=btn%>" />
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Etiqueta:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="btnLabel" value="<%=btnLabel%>"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"><%=paramRequest.getLocaleString("accept")%></button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("cancel")%></button>
                    </div>
                </form>
                <%
            }
        }
        %>
    </div>
</div>
<script type="text/javascript">
    if ($("#formEdit").length() != 0) {
        $("#formEdit").submit(function(e) {
            $("#modalDialog").modal('hide');
            var postData = $(this).serializeArray();
            var formURL = $(this).attr("action");
            $.ajax({
                url : formURL,
                type: "POST",
                data : postData,
                success:function(data, textStatus, jqXHR)  {
                    <%
                    SWBResourceURL admUrl = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_ADMIN);
                    %>
                    window.location='<%=admUrl%>';
                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
            });
            e.preventDefault(); //STOP default action
            //e.unbind(); //unbind. to stop multiple form submit.
        });
    }
</script>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.process.resources.ProcessForm"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
HashMap<String, SemanticProperty> allProps = (HashMap<String, SemanticProperty>) request.getAttribute(ProcessForm.ATT_PROPMAP);
Resource base = (Resource) paramRequest.getResourceBase();
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4><%=paramRequest.getLocaleString("addDlgTitle")%></h4>
        </div>
        <%
        if (allProps != null && !allProps.isEmpty()) {
            ArrayList<String> list = new ArrayList(allProps.keySet());

            Collections.sort(list);
            Iterator<String> its = list.iterator();

            SWBResourceURL urladd = paramRequest.getActionUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setAction(ProcessForm.ACT_ADDPROPS);
            %>
            <form id="formAdd" action="<%=urladd%>" method="post">
                <div class="modal-body">
                    <select multiple class="form-control" size="10" name="properties">
                        <%
                        while (its.hasNext()) {
                            String str = its.next();
                            String varName = "";
                            StringTokenizer stoken = new StringTokenizer(str, "|");
                            if (stoken.hasMoreTokens()) {
                                varName = stoken.nextToken();
                            }
                            SemanticProperty sp = allProps.get(str);
                            %>
                            <option value="<%=str%>"><%=varName%>.<%=sp.getPropertyCodeName()%></option>
                            <%
                        }
                        %>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary"><%=paramRequest.getLocaleString("addSelected")%></button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("cancel")%></button>
                </div>
            </form>
            <%
        } else {
            %>
            <div class="modal-body">
                <div class="text-center"><%=paramRequest.getLocaleString("msgNoProps")%></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("cancel")%></button>
            </div>
            <%
        }
        %>
    </div>
</div>
<script type="text/javascript">
    $("#formAdd").submit(function(e) {
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
</script>
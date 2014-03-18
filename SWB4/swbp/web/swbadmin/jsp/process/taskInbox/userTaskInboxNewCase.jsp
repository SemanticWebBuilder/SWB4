<%-- 
    Document   : userTaskInboxNewCase
    Created on : 3/09/2013, 11:41:28 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
String lang = "es";
        
if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

response.setCharacterEncoding("utf-8");

Map<String, ArrayList<Process>> groups = new TreeMap<String, ArrayList<Process>>();
SWBResourceURL createUrl = paramRequest.getActionUrl().setAction(UserTaskInboxResource.ACT_CREATE);
ArrayList<Process> pccs = null;

//Obtener los eventos de inicio
Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(paramRequest.getWebPage().getWebSite());
while(startEvents.hasNext()) {
    StartEvent sevt = startEvents.next();
    //Si el usuario tiene permisos en el evento
    if (sevt.getContainer() != null && sevt.getContainer() instanceof Process && user.haveAccess(sevt)) {
        Process itp = sevt.getProcess();
        //Si el proceso al que pertenece el evento y es válido
        if (itp != null && itp.isValid()) {
            if(itp.getProcessGroup() != null) {
                String pg = itp.getProcessGroup().getDisplayTitle(lang);
                //Si ya existe el grupo de procesos en el treemap
                if(groups.get(pg) != null) {
                    pccs = groups.get(pg);
                    if (!pccs.contains(itp)) {
                        pccs.add(itp);
                    }
                    groups.put(pg, pccs);
                } else { //Si no existe el grupo de procesos en el treemap
                    pccs = new ArrayList<Process>();
                    pccs.add(itp);
                    groups.put(pg, pccs);
                }
            }
        }
    }
}
Iterator<String> keys = groups.keySet().iterator();
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4><%=paramRequest.getLocaleString("createCase")%></h4>
        </div>
        <%if (keys.hasNext()) {%>
            <form method="post" action="<%=createUrl%>">
                <div class="modal-body">
                    <label for="pid"><%=paramRequest.getLocaleString("promptCreate")%></label>
                    <select class="form-control" name="pid">
                    <%while(keys.hasNext()) {
                        String key = keys.next();
                    %>
                        <optgroup label="<%=key%>">
                        <%Iterator<Process> it_pccs = SWBComparator.sortByDisplayName(groups.get(key).iterator(), lang);
                        while(it_pccs.hasNext()) {
                            Process pcc = it_pccs.next();
                        %>
                            <option value="<%=pcc.getId()%>"><%=pcc.getDisplayTitle(lang)%></option>
                        <%
                        }
                        %>
                        </optgroup>
                    <%
                    }
                    %>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("btnCancel")%></button>
                    <button type="submit" class="btn btn-success"><%=paramRequest.getLocaleString("btnOk")%></button>
                </div>
            </form>
        <%
        } else {
        %>
            <div class="modal-body">
                <div class="text-center"><%=paramRequest.getLocaleString("msgNoProcess")%></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("btnCancel")%></button>
            </div>
        <%
        }
        %>
    </div>
</div>
<%-- 
    Document   : userTaskInboxFwd
    Created on : 4/09/2013, 08:15:19 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.model.UserRepository"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();
String suri = request.getParameter("suri");

Iterator<User> tPartners = null;
FlowNodeInstance fni = null;

SemanticObject sobj = SemanticObject.createSemanticObject(suri);
if (sobj != null) {
    fni = (FlowNodeInstance) sobj.createGenericInstance();
}

if (fni != null) {
    User owner = fni.getAssignedto();
    if (owner.equals(user)) {

        UserRepository ur = site.getUserRepository();
        UserTask task = (UserTask) fni.getFlowNodeType();
        ArrayList<Role> taskRoles = new ArrayList<Role>();

        Iterator<RoleRef> refs = task.listRoleRefs();
        while (refs.hasNext()) {
            RoleRef roleRef = refs.next();
            if (roleRef.getRole() != null && roleRef.isActive()) {
                taskRoles.add(roleRef.getRole());
            }
        }

        if (taskRoles.isEmpty()) {
            tPartners = ur.listUsers();
        } else {
            ArrayList<User> _users = new ArrayList<User>();
            Iterator<Role> tRoles = taskRoles.iterator();
            while (tRoles.hasNext()) {
                Role role = tRoles.next();
                Iterator<User> users = User.ClassMgr.listUserByRole(role);
                while (users.hasNext()) {
                    User user1 = users.next();
                    if (!_users.contains(user1) && !user1.equals(owner)) {
                        _users.add(user1);
                    }
                }
            }
            tPartners = _users.iterator();
        }
    }
}

SWBResourceURL forward = paramRequest.getActionUrl().setAction(UserTaskInboxResource.MODE_FWD);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4><%=paramRequest.getLocaleString("actFwd")%></h4>
        </div>
        <%if (tPartners != null && tPartners.hasNext()) {%>
            <form method="post" action="<%=forward%>" onsubmit="showWaitDialog('<%=paramRequest.getLocaleString("actFwd")%>','<%=paramRequest.getLocaleString("msgForwarding")%>'); return true;">
                <input type="hidden" name="suri" value="<%=suri%>"/>
                <div class="modal-body">
                    <label for="pid"><%=paramRequest.getLocaleString("promptFwd")%></label>
                    <select class="form-control" name="owner">
                        <option value="--"><%=paramRequest.getLocaleString("freeTask")%></option>
                        <%while(tPartners.hasNext()) {
                            User _user = tPartners.next();
                            if (!_user.equals(fni.getAssignedto())) {
                                %>
                                <option value="<%=_user.getId()%>"><%=(_user.getFullName()==null||_user.getFullName().trim().equals(""))?_user.getId():_user.getFullName()%></option>
                                <%
                            }
                        }
                    %>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("btnCancel")%></button>
                    <button type="submit" class="btn btn-primary"><%=paramRequest.getLocaleString("btnOk")%></button>
                </div>
            </form>
        <%
        } else {
        %>
            <div class="modal-body">
                <div class="text-center"><%=paramRequest.getLocaleString("msgFwdFail")%></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=paramRequest.getLocaleString("btnCancel")%></button>
            </div>
        <%
        }
        %>
    </div>
</div>
<%-- 
    Document   : businessControlPanelFiles
    Created on : 30/11/2011, 11:04:29 AM
    Author     : Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
--%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.RepositoryFile"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
ArrayList<RepositoryFile> docs = (ArrayList<RepositoryFile>)request.getAttribute("docs");
String pName = (String) request.getAttribute("pName");
String lang = "es";
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();
        
if (pName == null) {
    pName = "";
}

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
viewUrl.setParameter("gF", request.getParameter("gF"));
viewUrl.setParameter("sF", request.getParameter("sF"));
viewUrl.setParameter("sort", request.getParameter("sort"));
%>
<h2><a class="btn" data-toggle="tooltip" data-placement="bottom" title="Back to monitor" href="<%=viewUrl%>"><span class="fa fa-reply"></span></a><%=paramRequest.getLocaleString("viewDocsTitle")%> <%=pName%></h2>
<%
if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><span class="fa fa-ban"></span> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
    if (docs != null && !docs.isEmpty()) {
        %>
        <div class="table-responsive">
            <table class="table table-hover swbp-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Archivo</th>
                        <th>Modificado</th>
                        <th>Modificado por</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    Iterator<RepositoryFile> files = docs.iterator();
                    while(files.hasNext()) {
                        RepositoryFile file = files.next();
                        String Id = file.getId();
                        String name = file.getTitle();
                        String pCreated = SWBUtils.TEXT.getStrDate(file.getLastVersion().getCreated(), lang, "dd/mm/yy - hh:mm");
                        String pModifiedBy = "--";
                        if (file.getLastVersion().getCreator() != null) {
                            pModifiedBy = file.getLastVersion().getCreator().getFullName();
                        }
                        String url = null;
                        if (file.getRepositoryDirectory() != null) {
                            url = file.getRepositoryDirectory().getUrl() + "?act=detail&back=history&fid=" + file.getEncodedURI();
                        }
                        %>
                        <tr>
                            <td><%=Id%></td>
                            <%
                            if (url != null) {
                                %><td><a href="<%=url%>"><%=name%></a></td><%
                            } else {
                                %><td><%=name%></td><%
                            }
                            %>
                            <td><%=pCreated%></td>
                            <td><%=pModifiedBy%></td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div>
        <%
    } else {
        %>
        <div class="alert alert-warning">
            <span class="fa fa-exclamation-triangle"></span> <strong><%=paramRequest.getLocaleString("msgNoDocs")%></strong>
        </div>
        <%
    }
}
%>
<%-- 
    Document   : repositoryFileVersions
    Created on : 12/09/2013, 08:54:35 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.process.model.RepositoryURL"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.process.model.RepositoryElement"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.process.model.RepositoryFile"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
List<VersionInfo> files = (List<VersionInfo>) request.getAttribute("versionList");
RepositoryElement rf = (RepositoryElement) request.getAttribute("element");
int luser = (Integer) request.getAttribute("luser");
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
String lang = "es";

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}
SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy - hh:mm");

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
    %>
    <h2><a class="btn" data-toggle="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("btnBack")%>" href="<%=paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW)%>"><span class="fa fa-reply"></span></a><%=paramRequest.getLocaleString("msgVerHistoryTitle")%></h2>
    <%
    if (!files.isEmpty()) {
        Iterator<VersionInfo> it = files.iterator();
        if (it.hasNext()) {
            %>
            <div class="table-responsive">
                <table class="table table-hover swbp-table">
                    <thead>
                        <tr>
                            <th><%=paramRequest.getLocaleString("msgVersion")%></th>
                            <th><%=paramRequest.getLocaleString("msgCreationDate")%></th>
                            <th><%=paramRequest.getLocaleString("msgVersionUser")%></th>
                            <th><%=paramRequest.getLocaleString("msgComments")%></th>
                            <th><%=paramRequest.getLocaleString("msgTHStatus")%></th>
                            <th><%=paramRequest.getLocaleString("msgTHAction")%></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    while (it.hasNext()) {
                        VersionInfo vi = it.next();
                        String modifier = "--";
                        if (vi.getCreator() != null && vi.getCreator().getFullName().length() > 0) modifier = vi.getCreator().getFullName();

                        SWBResourceURL propsUrl = paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT);
                        propsUrl.setMode(ProcessFileRepository.MODE_PROPS);
                        propsUrl.setParameter("fid", rf.getId());
                        propsUrl.setParameter("type", (rf instanceof RepositoryURL)?paramRequest.getLocaleString("msgDocLink"):paramRequest.getLocaleString("msgFile"));
                        propsUrl.setParameter("verNum", String.valueOf(vi.getVersionNumber()));
                        %>
                        <tr>
                            <td><%=vi.getVersionValue()%></td>
                            <td><%=format.format(vi.getCreated())%></td>
                            <td><%=modifier%></td>
                            <td><%=vi.getVersionComment()==null?"--":vi.getVersionComment()%></td>
                            <td><%=rf.getStatus()==null?"--":rf.getStatus().getDisplayTitle(lang)%></td>
                            <td class="swbp-actions">
                                <a href="<%=propsUrl%>" title="<%=paramRequest.getLocaleString("msgInfo")%>" class="btn btn-default" data-toggle="modal" data-target="#modalDialog"><span class="fa fa-info-circle"></span></a>
                                <%
                                if (luser > 0) {
                                    SWBResourceURL urlDownload = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
                                    urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                    urlDownload.setParameter("fid", rf.getId());
                                    urlDownload.setParameter("verNum", "" + vi.getVersionNumber());
                                    %>
                                    <a href="<%=rf instanceof RepositoryFile?urlDownload:vi.getVersionFile()%>" title="<%=rf instanceof RepositoryFile?paramRequest.getLocaleString("msgDownload"):paramRequest.getLocaleString("msgGoLink")%>" class="btn btn-default"><span class="fa <%=rf instanceof RepositoryFile?"fa-cloud-download":"fa-external-link"%>"></span></a>
                                <%}
                                %>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                    </tbody>
                </table>
            </div>
            <%
        }
    }
}
%>
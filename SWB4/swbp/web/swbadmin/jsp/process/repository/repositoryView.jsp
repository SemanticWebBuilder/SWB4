<%-- 
    Document   : repositoryView
    Created on : 10/09/2013, 10:18:34 AM
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
List<RepositoryElement> files = (List<RepositoryElement>) request.getAttribute("files");
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
            <h4><i class="icon-ban-circle"></i> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
    SWBResourceURL addUrl = paramRequest.getRenderUrl().setMode(ProcessFileRepository.MODE_ADDFILE);
    %>
    <h2>Repositorio de documentos</h2>
    <ul class="list-unstyled list-inline">
        <li>
            <a href="<%=addUrl%>" class="btn btn-default"><i class="icon-plus"></i> Agregar documento</a>
        </li>
    </ul>
    <%
    if (!files.isEmpty()) {
        SWBResourceURL urlOrder = paramRequest.getRenderUrl();

        Iterator<RepositoryElement> it = files.iterator();
        if (it.hasNext()) {
            %>
            <div class="table-responsive">
                <table class="table table-hover swbp-table">
                    <thead>
                        <tr>
                            <th><a href="<%=urlOrder.setParameter("sort", "title")%>"><i class="icon-sort"></i></a> Nombre</th>
                            <th><a href="<%=urlOrder.setParameter("sort", "type")%>"><i class="icon-sort"></i></a> Tipo</th>
                            <th>Versión</th>
                            <th><a href="<%=urlOrder.setParameter("sort", "date")%>"><i class="icon-sort"></i></a> Modificado</th>
                            <th><a href="<%=urlOrder.setParameter("sort", "usr")%>"><i class="icon-sort"></i></a> Modificado por</th>
                            <th><a href="<%=urlOrder.setParameter("sort", "gpousr")%>"><i class="icon-sort"></i></a> Área</th>
                            <th><a href="<%=urlOrder.setParameter("sort", "status")%>"><i class="icon-sort"></i></a> Status</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    while (it.hasNext()) {
                        RepositoryElement rf = it.next();
                        VersionInfo vi = rf.getLastVersion();
                        String urlIcon = SWBPlatform.getContextPath()+"/swbadmin/jsp/process/repository/css/images/url.png";
                        String type = ProcessFileRepository.getFileType(vi.getVersionFile());
                        String modifier = "--";
                        if (vi.getModifiedBy() != null && vi.getModifiedBy().getFullName().length() > 0) modifier = vi.getModifiedBy().getFullName();

                        if (!(rf instanceof RepositoryURL)) {
                            urlIcon = ProcessFileRepository.getFileIcon(vi.getVersionFile());
                        }

                        SWBResourceURL propsUrl = paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT);
                        propsUrl.setMode(ProcessFileRepository.MODE_PROPS);
                        propsUrl.setParameter("fid", rf.getId());
                        propsUrl.setParameter("type", (rf instanceof RepositoryURL)?"url":"file");
                        %>
                        <tr>
                            <td><img src="<%=urlIcon%>" alt="<%=type%>" title="<%=rf.getDisplayTitle(lang)%>" /><%=rf.getDisplayTitle(lang)%></td>
                            <td><%=type%></td>
                            <td><%=vi.getVersionValue()%></td>
                            <td><%=vi.getUpdated()==null?"--":format.format(vi.getUpdated())%></td>
                            <td><%=modifier%></td>
                            <td><%=rf.getOwnerUserGroup()==null?"--":rf.getOwnerUserGroup().getDisplayTitle(lang)%></td>
                            <td><%=rf.getStatus()==null?"--":rf.getStatus().getDisplayTitle(lang)%></td>
                            <td class="swbp-actions">
                                <a href="<%=propsUrl%>" title="Propiedades" class="btn btn-default" data-toggle="modal" data-target="#modalDialog"><i class="icon-info-sign"></i></a>
                                <%if (luser == 3 || (vi.getCreator() != null && vi.getCreator().equals(user) && luser > 1)) {
                                    SWBResourceURL urlremove = paramRequest.getActionUrl();
                                    urlremove.setAction("removefile");
                                    urlremove.setParameter("act", "remove");
                                    urlremove.setParameter("fid", rf.getURI());
                                    
                                    addUrl.setParameter("fid", rf.getId());
                                    addUrl.setParameter("type", (rf instanceof RepositoryURL?"url":"file"));
                                    %>
                                    <a href="<%=urlremove%>" onclick="if (!confirm('<%=paramRequest.getLocaleString("msgAlertConfirmRemoveFile") + " " + rf.getTitle() + "?"%>')) return false;" title="Eliminar" class="btn btn-default"><i class="icon-trash"></i></a>
                                    <a href="<%=addUrl%>" title="Agregar nueva versión" class="btn btn-default"><i class="icon-upload-alt"></i></a>
                                    <%
                                }
                                if (luser > 0) {
                                    SWBResourceURL urlDownload = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
                                    urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                    urlDownload.setParameter("fid", rf.getId());
                                    urlDownload.setParameter("verNum", "" + vi.getVersionNumber());
                                    %>
                                    <a href="<%=rf instanceof RepositoryFile?urlDownload:vi.getVersionFile()%>" title="<%=rf instanceof RepositoryFile?"Descargar":"Ir al enlace"%>" class="btn btn-default"><i class="<%=rf instanceof RepositoryFile?"icon-download-alt":"icon-external-link"%>"></i></a>
                                <%}
                                if (vi.getPreviousVersion() != null) {
                                    SWBResourceURL historyUrl = paramRequest.getRenderUrl().setMode(ProcessFileRepository.MODE_HISTORY);
                                    historyUrl.setParameter("fid", rf.getId());
                                    historyUrl.setParameter("type", (rf instanceof RepositoryURL?"url":"file"));
                                    %>
                                    <a href="<%=historyUrl%>" title="Historial de versiones" class="btn btn-default"><i class="icon-archive"></i></a>
                                    <%
                                }%>
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
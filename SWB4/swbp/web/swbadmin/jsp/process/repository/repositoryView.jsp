<%-- 
    Document   : repositoryView
    Created on : 10/09/2013, 10:18:34 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="org.semanticwb.model.Traceable"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
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
List<GenericObject> files = (List<GenericObject>) request.getAttribute("files");
String path = (String) request.getAttribute("path");
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
    SWBResourceURL addUrl = paramRequest.getRenderUrl().setMode(ProcessFileRepository.MODE_ADDFILE);
    SWBResourceURL addFolderUrl = paramRequest.getRenderUrl().setMode(ProcessFileRepository.MODE_ADDFOLDER);
    %>
    <ul class="list-unstyled list-inline">
        <li>
            <a href="<%=addUrl%>" class="btn btn-default"><span class="fa fa-plus"></span> <%=paramRequest.getLocaleString("addFile")%></a>
        </li>
        <li>
            <a href="<%=addFolderUrl%>" class="btn btn-default"><span class="fa fa-plus"></span> <%=paramRequest.getLocaleString("msgCreateDirectory")%></a>
        </li>
    </ul>
    <%if (path != null) {%>
        <ol class="breadcrumb swbp-breadcrumb">
            <li>
                <span class="swbp-breadcrumb-title"><%=paramRequest.getLocaleString("msgLocation")%>:</span>
            </li>
            <%
            if (path.indexOf("|") != -1) {
                String [] pathelems = path.split("\\|");
                for (int i = 0; i < pathelems.length; i++) {
                    WebPage wp = site.getWebPage(pathelems[i]);
                    
                    if (wp.equals(paramRequest.getWebPage())) {
                        %>
                        <li class="active"><%=wp.getDisplayTitle(lang)%></li>
                        <%
                    } else {
                        %>
                        <li><a href="<%=wp.getUrl(lang)%>"><%=wp.getDisplayTitle(lang)%></a></li>
                        <%
                    }
                }
            } else {
                WebPage wp = site.getWebPage(path);
                %>
                <li><a href="<%=wp.getUrl(lang)%>"><%=wp.getDisplayTitle(lang)%></a></li>
                <%
            }
            %>
        </ol>
    <%
    }
        
    if (!files.isEmpty()) {
        SWBResourceURL urlOrder = paramRequest.getRenderUrl();

        Iterator<GenericObject> it = files.iterator();
        if (it.hasNext()) {
            %>
            <div class="table-responsive">
                <table class="table table-hover swbp-table">
                    <thead>
                        <tr>
                            <th><%=paramRequest.getLocaleString("msgTHFileName")%> <a href="<%=urlOrder.setParameter("sort", "title")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgType")%> <a href="<%=urlOrder.setParameter("sort", "type")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgVersion")%></th>
                            <th><%=paramRequest.getLocaleString("msgLastDateModification")%> <a href="<%=urlOrder.setParameter("sort", "date")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgVersionUser")%> <a href="<%=urlOrder.setParameter("sort", "usr")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgTHArea")%> <a href="<%=urlOrder.setParameter("sort", "gpousr")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgTHStatus")%> <a href="<%=urlOrder.setParameter("sort", "status")%>"><span class="fa fa-sort-asc"></span></a></th>
                            <th><%=paramRequest.getLocaleString("msgTHAction")%></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    while (it.hasNext()) {
                        GenericObject go = it.next();
                        
                        String urlIcon = SWBPlatform.getContextPath()+"/swbadmin/jsp/process/repository/css/images/url.png";
                        String title = ((Descriptiveable)go).getDisplayTitle(lang);
                        String type = paramRequest.getLocaleString("lblFileTypeFolder");
                        String _type = "directory";
                        String modifier = "--";
                        String version = "--";
                        String lastUpdated = "--";
                        String status = "--";
                        String ownerGroup = "--";
                        
                        if (go instanceof RepositoryElement) {
                            System.out.println("---1");
                            RepositoryElement re = (RepositoryElement)go;
                            
                            if (re == null) System.out.println("---re nulo");
                            
                            VersionInfo vi = re.getLastVersion();
                            if (vi != null) {
                                type = ProcessFileRepository.getFileType(vi.getVersionFile(), lang);
                            }
                            _type = (go instanceof RepositoryURL)?"url":"file";
                            System.out.println("---2");
                            if (vi != null && vi.getModifiedBy() != null && vi.getModifiedBy().getFullName().length() > 0) modifier = vi.getModifiedBy().getFullName();
                            if (vi != null && !(re instanceof RepositoryURL)) {
                                urlIcon = ProcessFileRepository.getFileIcon(vi.getVersionFile());
                            }
                            
                            version = (vi != null)?vi.getVersionValue():"--";
                            if (vi != null && vi.getUpdated() != null) {
                                lastUpdated = format.format(vi.getUpdated());
                            }
                            System.out.println("---3");
                            
                            if (re.getStatus() != null) {
                                status = re.getStatus().getDisplayTitle(lang);
                            }
                            
                            if (re.getOwnerUserGroup() != null) {
                                ownerGroup = re.getOwnerUserGroup().getDisplayTitle(lang);
                            }
                            System.out.println("---4");
                        } else if (go instanceof RepositoryDirectory) {
                            Traceable tr = (Traceable)go;
                            if (tr.getModifiedBy() != null) {
                                modifier = tr.getModifiedBy().getFullName();
                            }
                            urlIcon = SWBPlatform.getContextPath()+"/swbadmin/jsp/process/repository/css/images/folder.png";
                            if (tr.getUpdated() != null) {
                                lastUpdated = format.format(tr.getUpdated());
                            }
                        }
                        
                        SWBResourceURL propsUrl = paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT);
                        propsUrl.setMode(ProcessFileRepository.MODE_PROPS);
                        propsUrl.setParameter("fid", go.getId());
                        propsUrl.setParameter("type", _type);
                        %>
                        <tr>
                            <td>
                                <img src="<%=urlIcon%>" alt="<%=type%>" title="<%=title%>"/>
                                <%if (go instanceof RepositoryElement) {
                                    RepositoryElement re = (RepositoryElement)go;
                                    VersionInfo vi = re.getLastVersion();
                                    if (vi != null) {
                                        if (luser > 0) {
                                            SWBResourceURL urlDownload = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
                                            urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                            urlDownload.setParameter("fid", go.getId());
                                            urlDownload.setParameter("verNum", "" + vi.getVersionNumber());
                                            %>
                                            <a <%=re instanceof RepositoryURL?"target=\"_blank\"":""%> href="<%=re instanceof RepositoryFile?urlDownload:vi.getVersionFile()%>"><%=title%></a>
                                            <%
                                        }
                                    }
                                } else if (go instanceof RepositoryDirectory) {
                                    %>
                                    <a href="<%=((RepositoryDirectory)go).getUrl()%>" title="<%=paramRequest.getLocaleString("msgOpenFolder")%>"><%=title%></a>
                                    <%
                                }%>
                            </td>
                            <td><%=type%></td>
                            <td><%=version%></td>
                            <td><%=lastUpdated%></td>
                            <td><%=modifier%></td>
                            <td><%=ownerGroup%></td>
                            <td><%=status%></td>
                            <td class="swbp-actions">
                                <a href="<%=propsUrl%>" title="<%=paramRequest.getLocaleString("msgInfo")%>" class="btn btn-default" data-toggle="modal" data-target="#modalDialog"><span class="fa fa-exclamation-circle"></span></a>
                                <%if (luser == 3 || (((Traceable)go).getCreator() != null && ((Traceable)go).getCreator().equals(user) && luser > 1)) {
                                    boolean canDelete = true;
                                    if (go instanceof RepositoryDirectory) {
                                        RepositoryDirectory rdir = (RepositoryDirectory)go;
                                        if (rdir.listRepositoryElements().hasNext()) {
                                            canDelete = false;
                                        }
                                        
                                        if (rdir.listChilds().hasNext()) {
                                            canDelete = false;
                                        }
                                    }
                                    
                                    if (canDelete) {
                                        SWBResourceURL urlremove = paramRequest.getActionUrl();
                                        urlremove.setAction("removefile");
                                        urlremove.setParameter("act", "remove");
                                        urlremove.setParameter("fid", go.getURI());
                                        %>
                                        <a href="<%=urlremove%>" onclick="if (!confirm('<%=paramRequest.getLocaleString("msgAlertConfirmRemoveFile") + " " + title + "?"%>')) return false;" title="<%=paramRequest.getLocaleString("msgAltDelete")%>" class="btn btn-default"><span class="fa fa-trash-o"></span></a>
                                        <%
                                    }
                                    if (go instanceof RepositoryElement) {
                                        addUrl.setParameter("fid", go.getId());
                                        addUrl.setParameter("type", _type);
                                        %>
                                        <a href="<%=addUrl%>" title="<%=paramRequest.getLocaleString("msgAddVersion")%>" class="btn btn-default"><span class="fa fa-cloud-upload"></span></a>
                                        <%
                                    }
                                }
                                
                                if (go instanceof RepositoryElement) {
                                    RepositoryElement re = (RepositoryElement)go;
                                    VersionInfo vi = re.getLastVersion();
                                    if (vi != null) {
                                        if (vi.getPreviousVersion() != null) {
                                            SWBResourceURL historyUrl = paramRequest.getRenderUrl().setMode(ProcessFileRepository.MODE_HISTORY);
                                            historyUrl.setParameter("fid", go.getId());
                                            historyUrl.setParameter("type", (re instanceof RepositoryURL?"url":"file"));
                                            %>
                                            <a href="<%=historyUrl%>" title="<%=paramRequest.getLocaleString("msgViewVersionHistory")%>" class="btn btn-default"><span class="fa fa-archive"></span></a>
                                            <%
                                        }
                                    }
                                }
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
    } else {
    %>
    <div class="alert alert-block alert-warning">
        <p><%=paramRequest.getLocaleString("msgNoFiles")%></p>
    </div>
    <%
    }
}
%>
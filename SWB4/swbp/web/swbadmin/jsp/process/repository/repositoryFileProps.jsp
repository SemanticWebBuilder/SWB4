<%-- 
    Document   : repositoryFileProps
    Created on : 3/09/2013, 11:41:28 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.process.model.RepositoryURL"%>
<%@page import="org.semanticwb.process.model.RepositoryFile"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.RepositoryElement"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();
SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy - hh:mm");
String lang = "es";
        
if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

String fid = request.getParameter("fid");
String type = request.getParameter("type");
RepositoryElement re = null;

if (fid != null && type != null) {
    if ("file".equals(type)) {
        re = RepositoryFile.ClassMgr.getRepositoryFile(fid, site);
    } else {
        re = RepositoryURL.ClassMgr.getRepositoryURL(fid, site);
    }
}

String verNumber = request.getParameter("verNum");
int intVer = 1;
if (verNumber != null) {
    intVer = Integer.parseInt(verNumber);
}
VersionInfo ver = null;
VersionInfo vl = re.getLastVersion();
if (null != vl) {
    ver = vl;
    while (ver.getPreviousVersion() != null) {
        if (ver.getVersionNumber() == intVer) {
            break;
        }
        ver = ver.getPreviousVersion();
    }
}

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
    %>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4><%=paramRequest.getLocaleString("msgDocProperties")%></h4>
            </div>
            <div class="modal-body">
                <%
                if (re == null) {
                    %><%
                } else {
                    VersionInfo vi = re.getLastVersion();
                %>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=paramRequest.getLocaleString("msgTitle")%></label>
                      <div class="col-lg-7">
                        <p class="form-control-static"><%=re.getDisplayTitle(lang)%></p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=re instanceof RepositoryFile?paramRequest.getLocaleString("msgFile"):paramRequest.getLocaleString("msgDocLink")%></label>
                      <div class="col-lg-7">
                          <p class="form-control-static">
                              <%
                              if (ver != null) {
                                  %><%=ver.getVersionFile()%><%
                              } else if (vi != null) {
                                  %><%=vi.getVersionFile()%><%
                              } else {
                                  %>--<%
                              }
                              %>
                        </p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=paramRequest.getLocaleString("msgDescription")%></label>
                      <div class="col-lg-7">
                        <p class="form-control-static"><%=re.getDisplayDescription(lang)%></p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=paramRequest.getLocaleString("msgComments")%></label>
                      <div class="col-lg-7">
                        <p class="form-control-static"><%=vi!=null?vi.getVersionComment():"--"%></p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=paramRequest.getLocaleString("msgVersionUser")%></label>
                      <div class="col-lg-7">
                        <p class="form-control-static"><%=re.getCreator()==null?"--":re.getCreator().getFullName()%></p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-lg-5 control-label"><%=paramRequest.getLocaleString("msgLastDateModification")%></label>
                      <div class="col-lg-7">
                        <p class="form-control-static"><%=re.getCreated()==null?"--":format.format(re.getCreated())%></p>
                      </div>
                    </div>
                  </form>
                <%
                }
                %>
            </div>
        </div>
    </div>
<%
}
%>
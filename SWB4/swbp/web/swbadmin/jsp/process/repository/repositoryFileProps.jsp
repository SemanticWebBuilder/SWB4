<%-- 
    Document   : userTaskInboxNewCase
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
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4>Propiedades del documento</h4>
        </div>
        <div class="modal-body">
            <%
            if (re == null) {
                %>No hay archivo<%
            } else {
                VersionInfo vi = re.getLastVersion();
            %>
            <form class="form-horizontal" role="form">
                <div class="form-group">
                  <label class="col-lg-5 control-label">Título</label>
                  <div class="col-lg-7">
                    <p class="form-control-static"><%=re.getDisplayTitle(lang)%></p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-5 control-label"><%=re instanceof RepositoryFile?"Nombre del archivo":"URL del enlace"%></label>
                  <div class="col-lg-7">
                      <p class="form-control-static"><%=vi==null?"--":vi.getVersionFile()%>
                    </p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-5 control-label">Descripción</label>
                  <div class="col-lg-7">
                    <p class="form-control-static"><%=re.getDisplayDescription(lang)%></p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-5 control-label">Usuario creador</label>
                  <div class="col-lg-7">
                    <p class="form-control-static"><%=re.getCreator()==null?"--":re.getCreator().getFullName()%></p>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-5 control-label">Fecha de creación</label>
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
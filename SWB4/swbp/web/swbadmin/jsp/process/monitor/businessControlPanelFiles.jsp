<%-- 
    Document   : businessControlPanelFiles
    Created on : 30/11/2011, 11:04:29 AM
    Author     : hasdai
--%>
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
        
if (pName == null) {
    pName = "";
}

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}
%>

<h2>Archivos relacionados con el proceso <%=pName%></h2>
<%
if (docs != null && !docs.isEmpty()) {
    %>
    <table class="tabla-bandeja">
            <thead>
                <tr>
                    <th class="tban-id">ID</th>
                    <th class="tban-proces">Archivo</th>
                    <th class="tban-proces">Modificado</th>
                    <th class="tban-proces">Modificado por</th>
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
                        <td class="tban-inicia"><%=Id%></td>
                        <%
                        if (url != null) {
                            %><td class="tban-inicia"><a href="<%=url%>"><%=name%></a></td><%
                        } else {
                            %><td class="tban-inicia"><%=name%></td><%
                        }
                        %>
                        <td class="tban-inicia"><%=pCreated%></td>
                        <td class="tban-inicia"><%=pModifiedBy%></td>
                    </tr>
                    <%
                }
                %>
            </tbody>
        </table>
        <%
} else {
    %><p>No existen archivos relacionados con el proceso</p><%
}

SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
viewUrl.setParameter("gFilter", request.getParameter("gFilter"));
viewUrl.setParameter("sFilter", request.getParameter("sFilter"));
viewUrl.setParameter("sort", request.getParameter("sort"));
%>
<br/>
<input type="button" value="Regresar al monitor" onclick="window.location='<%=viewUrl%>'"/>
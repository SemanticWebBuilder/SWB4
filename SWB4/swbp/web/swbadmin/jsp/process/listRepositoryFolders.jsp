<%-- 
    Document   : listRepositoryFolders
    Created on : 11/10/2011, 11:10:19 AM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.Iterator"%>

<%!
private String printNodes(SWBParamRequest paramRequest, String frameId, RepositoryDirectory root, boolean includeRoot, int maxTitleLength) {
    String ret = "";
    User user = paramRequest.getUser();
    String lang = "es";
    
    if (user.getLanguage() != null) {
        lang = user.getLanguage();
    }    

    if (root != null && user.haveAccess(root) && root.isActive()) {
        if (includeRoot) {
            boolean hasContent = false;
            Iterator<Resource> resources = root.listResources();
            while (resources.hasNext() && !hasContent) {
                Resource res = resources.next();
                if (res.getResourceType().getTitle().equals("ProcessFileRepository")) {
                    hasContent=true;
                }
            }
            ret += "<li>\n";
            if (hasContent) {
                if (!frameId.trim().equals("")) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl();
                    viewUrl.setParameter("sf", root.getId());
                    //ret += "<a href=\"" + viewUrl.toString() + "\" title=\"" + root.getTitle() + "\" onclick=\"setFrameSrc('"+ frameId +"','"+ root.getUrl() +"');\">" + trimToLength(root.getTitle(), maxTitleLength) + "</a>\n";
                    ret += "<a href=\"#\" title=\"" + root.getTitle() + "\" onclick=\"setFrameSrc('"+ frameId +"','"+ root.getUrl() +"');\">" + trimToLength(root.getTitle(), maxTitleLength) + "</a>\n";
                } else {
                    ret += "<a title=\"" + root.getTitle() +"\" href=\"" + root.getUrl() + "\">" + trimToLength(root.getTitle(), maxTitleLength) + "</a>\n";
                }
            } else {
                ret += trimToLength(root.getTitle(), maxTitleLength);
            }
            ret += "</li>\n";
        }
        
        if (root.listChilds().hasNext()) {
            ret += "<ul>";
            Iterator<WebPage> childs = SWBComparator.sortByDisplayName(root.listChilds(), lang);
            while (childs.hasNext()) {
                WebPage child = childs.next();
                if (child instanceof RepositoryDirectory) {
                    RepositoryDirectory repChild = (RepositoryDirectory) child;
                    ret += printNodes(paramRequest, frameId, repChild, true, maxTitleLength);
                }
            }
            ret += "</ul>";
        }
    }
    
    return ret;
}

private String printDojoNodes(SWBParamRequest paramRequest, String frameId, RepositoryDirectory root, boolean includeRoot, int maxTitleLength) {
    String ret = "";
    User user = paramRequest.getUser();
    String lang = "es";
    
    if (user.getLanguage() != null) {
        lang = user.getLanguage();
    }    

    if (root != null && user.haveAccess(root) && root.isActive()) {
        if (includeRoot) {
            boolean hasContent = false;
            Iterator<Resource> resources = root.listResources();
            while (resources.hasNext() && !hasContent) {
                Resource res = resources.next();
                if (res.getResourceType().getTitle().equals("ProcessFileRepository")) {
                    hasContent=true;
                }
            }
            
            ret += "{\n";
            if (hasContent) {
                ret += "  label: '<a href=\"#\" onclick=\"setFrameSrc('"+ frameId +"','"+ root.getUrl() +"');\">" + trimToLength(root.getTitle(), maxTitleLength) + "</a>'\n";
            } else {
                ret += "  label: '" + trimToLength(root.getTitle(), maxTitleLength) + "'\n";
            }
            ret += "  id: '" + root.getId() + "'";
        }
        
        if (root.listChilds().hasNext() && includeRoot) {
            ret += "  children: [\n";
            Iterator<WebPage> childs = SWBComparator.sortByDisplayName(root.listChilds(), lang);
            while (childs.hasNext()) {
                WebPage child = childs.next();
                if (child instanceof RepositoryDirectory) {
                    RepositoryDirectory repChild = (RepositoryDirectory) child;
                    ret += printNodes(paramRequest, frameId, repChild, true, maxTitleLength);
                }
            }
            ret += "  ]\n";
        }
        ret += "},\n";
    }
    
    return ret;
}

private String trimToLength(String text, int maxLength) {
    String ret = text;
    
    if (text.length() > maxLength) {
        ret = ret.substring(0, maxLength) + "...";
    }
    return ret;
}
%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebPage startWp = (WebPage) request.getAttribute("startWp");
String iFrameId = (String) request.getAttribute("iFrameId");
String strMaxLength = (String) request.getAttribute("maxLength");
int maxLength = 15;
User user = paramRequest.getUser();

String id = "Activos_de_Pocesos";

if (iFrameId == null || iFrameId.trim().equals("")) {
    iFrameId = "iframecontentswb";
}

if(startWp == null) {
    startWp = paramRequest.getWebPage().getWebSite().getWebPage(id);
}

if (strMaxLength != null && !strMaxLength.trim().equals("")) {
    try {
        maxLength = Integer.valueOf(strMaxLength);
    } catch (NumberFormatException e) {
        e.printStackTrace();
    }
}

if (startWp != null && user != null && startWp instanceof RepositoryDirectory) {
    RepositoryDirectory root = (RepositoryDirectory) startWp;
    %>
    <script type="text/javascript">
        function setFrameSrc(fid, url) {
            if (fid != null && fid != "") {
                var objFrm = document.getElementById(fid);
                if (objFrm != null) {
                    objFrm.src = url;
                }
            }
        }
    </script>
    <%
    if (root.listChilds().hasNext()) {
        String d = printNodes(paramRequest, iFrameId, root, false, maxLength);
        out.println(d);
        d = printDojoNodes(paramRequest, iFrameId, root, false, maxLength);
        System.out.println(":::\n"+d);
    }
    
}
%>
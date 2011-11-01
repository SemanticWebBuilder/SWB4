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
private String replaceAccents(String text) {
    String ret = text;
    
    return ret;
}

private String printDojoNodes(SWBParamRequest paramRequest, String frameId, RepositoryDirectory root, boolean includeRoot, int maxTitleLength) {
    String ret = "";
    User user = paramRequest.getUser();
    String lang = "es";
    if (user != null && user.getLanguage() != null) {
        lang = user.getLanguage();
    }
    
    if (root != null /*&& user.haveAccess(root)*/) {
        if (includeRoot) {
            ret += "{\n"
                 + "  name:'"+ replaceAccents(root.getDisplayTitle(lang)) +"',\n"
                 + "  url:'"+ root.getUrl() +"',\n"
                 + "  type:'directory',\n"
                 + "  id:'" + root.getURI() + "'";
        }
        Iterator<WebPage> childs = root.listChilds();
        if (childs.hasNext()) {
            if (includeRoot) {
                ret += ",\n"
                    + "  children: [\n";
            }
            while(childs.hasNext()) {
                WebPage wp = childs.next();
                if (wp instanceof RepositoryDirectory) {
                    RepositoryDirectory dir = (RepositoryDirectory)wp;
                    ret += printDojoNodes(paramRequest, frameId, dir, true, maxTitleLength);
                    if (childs.hasNext()) {
                        ret += ",";
                    }
                }
            }
            if (includeRoot) {
                ret += "]\n";
            }
        } else {
            ret += "\n";
        }
        if (includeRoot) {
            ret += "}\n";
        }
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

if (paramRequest.getCallMethod() == paramRequest.Call_DIRECT) {
    RepositoryDirectory root = (RepositoryDirectory) startWp;
    String ret = "{"
            + "identifier:'id',"
            + "label:'name',"
            + "items:["
            + printDojoNodes(paramRequest, iFrameId, root, false, maxLength)
            + "],"
            + "}";
    String utf8ret = new String(ret.getBytes("UTF-8"), "ISO-8859-1");
    out.println(utf8ret);
} else {
    if (startWp != null && user != null && startWp instanceof RepositoryDirectory) {
        SWBResourceURL directUrl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT);
        %>
        <script type="text/javascript">
            function setFrameSrc(url) {
                var objFrm = document.getElementById('iframecontentswb');
                if (objFrm != null) {
                    objFrm.src = url;
                }
            }
        </script>
        <script type="text/javascript">
            dojo.require("dojo.data.ItemFileReadStore");
            dojo.require("dijit.Tree");
            dojo.require("dijit.layout.ContentPane");
            dojo.require("dijit.layout.BorderContainer");
        </script>

        <div dojoType="dojo.data.ItemFileReadStore" jsId="directoryStore" url="<%=directUrl%>"></div>
            <div dojoType="dijit.tree.ForestStoreModel" jsId="directoryModel" store="directoryStore"
            query="{type:'directory'}" rootId="directoryRoot" rootLabel="Repositorio"
            childrenAttrs="children">
            </div>
            <div dojoType="dijit.Tree" id="mytree" model="directoryModel" openOnClick="false" showRoot="false>
                <script type="dojo/method" event="onClick" args="item">
                    setFrameSrc(directoryStore.getValue(item, "url"));
                </script>
            </div>
        <%
    }
}
%>
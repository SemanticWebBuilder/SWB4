<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*,org.semanticwb.portal.api.*"%><%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    Portlet obj=(Portlet)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(id);
    System.out.println("suri:"+obj.getSemanticObject().getEncodedURI());
    String url="/swb/swb/SWBAdmin/ob_PortletConfig/_vtp/"+obj.getWebSiteId()+"/"+obj.getWebSite().getHomePage().getId()+"/_rid/"+obj.getId()+"/_mod/admin/_wst/maximized";
%><iframe dojoType_="dijit.layout.ContentPane" src="<%=url%>" width="100%" height="100%" frameborder="0" scrolling="yes"></iframe>
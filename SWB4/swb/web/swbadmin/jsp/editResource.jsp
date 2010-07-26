<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*,org.semanticwb.portal.api.*"%><%
    User user=SWBContext.getAdminUser();
    if(user==null)
    {
        response.sendError(403);
        return;
    }
    String lang=user.getLanguage();
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    Object obj1=SWBPlatform.getSemanticMgr().getOntology().getGenericObject(id);
    if(obj1 instanceof Resource)
        {
    Resource obj=(Resource)obj1;
    boolean sem=SWBPortal.getResourceMgr().getResource(obj) instanceof GenericSemResource;
    //System.out.println("suri:"+obj.getSemanticObject().getEncodedURI());
    if(sem)
    {
    String url=SWBPlatform.getContextPath()+"/swb/SWBAdmin/bh_Information/_vtp/"+obj.getWebSiteId()+"/"+obj.getWebSite().getHomePage().getId()+"/_rid/"+obj.getId()+"/_mod/admin/_wst/maximized";
%><div dojoType="dijit.layout.ContentPane" href="<%=url%>" style="border:0px; width:100%; height:100%"></div>
<%
    }else
    {
    String url=SWBPlatform.getContextPath()+"/swb/SWBAdmin/bh_AdminPorltet/_vtp/"+obj.getWebSiteId()+"/"+obj.getWebSite().getHomePage().getId()+"/_rid/"+obj.getId()+"/_mod/admin/_wst/maximized";
%><iframe dojoType_="dijit.layout.ContentPane" src="<%=url%>" style="border:0px; width:100%; height:100%" frameborder="0" scrolling="yes"></iframe>
<%
    }
}
%>
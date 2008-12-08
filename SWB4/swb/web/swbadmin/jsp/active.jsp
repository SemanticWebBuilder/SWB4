<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String act=request.getParameter("act");
    //System.out.println("suri:"+suri);
    if(suri==null)
    {
        String code=SWBUtils.IO.readInputStream(request.getInputStream());
        System.out.println(code);
        String uri=SWBContext.getAdminWebSite().getHomePage().getEncodedURI();
%>
    Error params not found...
    <a href="?suri=<%=uri%>">edit</a>
<%
        return;
    }

    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(suri);

    if(obj!=null && act!=null)
    {
        if(act.equals("active"))
        {
            obj.setBooleanProperty(SWBContext.getVocabulary().swb_active, true);
            out.println(obj.getSemanticClass().getDisplayName(lang)+" fue activado...");
        }else
        {
            obj.setBooleanProperty(SWBContext.getVocabulary().swb_active, false);
            out.println(obj.getSemanticClass().getDisplayName(lang)+" fue desactivado...");
        }
    }
    out.println("<script type=\"text/javascript\">updateTreeNode();</script>");
    //out.println(obj.getDisplayName(lang)+" "+act);
%>
<!-- a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a -->
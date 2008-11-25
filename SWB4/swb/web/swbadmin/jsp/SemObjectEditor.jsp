<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String scls=request.getParameter("scls");
    String sref=request.getParameter("sref");
    
    if(suri==null && scls==null)
    {
        String code=SWBUtils.IO.readInputStream(request.getInputStream());
        System.out.println(code);
        String uri=SWBContext.getAdminWebSite().getHomePage().getEncodedURI();
        String cls=SWBContext.getAdminWebSite().getHomePage().getSemanticObject().getSemanticClass().getEncodedURI();
%>
        <a href="?suri=<%=uri%>">edit</a>
        <a href="?scls=<%=cls%>&sref=<%=uri%>">create</a>
<%
        return;
    }

    String smode=request.getParameter("smode");
    out.println(smode);
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    if(suri==null) //es una creacion
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(scls);
        SemanticObject ref=SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(sref);
        SWBFormMgr frm=new SWBFormMgr(cls,ref,null);
        
        SemanticObject obj=frm.processForm(request, response);
        if(obj!=null)
        {
            if(smode.equals(SWBFormMgr.MODE_CREATE))
            {
                GenericObject gobj=cls.newGenericInstance(obj);
                if(gobj instanceof WebPage)
                {
                    ((WebPage)gobj).setParent((WebPage)cls.newGenericInstance(ref));
                }
                out.println("<script type=\"text/javascript\">");
                out.println("dijit.byId('swbDialog').hide();");
                out.println("addNewTab('"+obj.getURI()+"','"+obj.getDisplayName()+"','"+"swbIcon"+cls.getName()+"');");
                out.println("</script>");
            }
        }
        frm.setAction("/swb/swbadmin/jsp/SemObjectEditor.jsp");
        out.println(frm.renderForm());
    }else
    {
        SemanticObject obj=ont.getSemanticObject(suri);
        SWBFormMgr frm=new SWBFormMgr(obj, null,SWBFormMgr.MODE_EDIT);
        frm.processForm(request, response);
        frm.setAction("/swb/swbadmin/jsp/SemObjectEditor.jsp");
        out.println(frm.renderForm());
     }
%>
<!-- a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a -->
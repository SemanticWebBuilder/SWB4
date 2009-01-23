<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String scls=request.getParameter("scls");
    String sref=request.getParameter("sref");
    String sprop=request.getParameter("sprop");
    //System.out.println("SemObjectEditor suri:"+suri+" scls:"+scls+" sref:"+sref+" sprop:"+sprop);
    
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
    //out.println(smode+" "+Thread.currentThread().getName());
try
{
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    if(suri==null) //es una creacion
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(scls);
        SemanticObject ref=SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(sref);
        SWBFormMgr frm=new SWBFormMgr(cls,ref,null);
        frm.addHiddenParameter("sprop", sprop);

        SemanticObject obj=frm.processForm(request);
        if(obj!=null)
        {
            if(smode.equals(SWBFormMgr.MODE_CREATE))
            {
                SemanticProperty prop=null;
                if(sprop!=null)prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);

                if(prop!=null && prop.hasInverse())
                {
                    //System.out.println("prop:"+prop.getURI()+" "+prop.hasInverse()+" "+prop.getInverse());
                    obj.setObjectProperty(prop.getInverse(), ref);
                }
                //GenericObject gobj=cls.newGenericInstance(obj);
                //if(gobj instanceof WebPage)
                //{
                //    ((WebPage)gobj).setParent((WebPage)cls.newGenericInstance(ref));
                //}
                
                out.println("<script type=\"text/javascript\">");
                out.println("dijit.byId('swbDialog').hide();");
                out.println("reloadTreeNode();");
                out.println("showStatus('"+obj.getSemanticClass().getDisplayName(lang)+" creado');");
                out.println("addNewTab('"+obj.getURI()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp"+"','"+obj.getDisplayName(lang)+"');");
                out.println("</script>");
            }
        }
        frm.setAction("/swb/swbadmin/jsp/SemObjectEditor.jsp");
        out.println(frm.renderForm());
    }else
    {
        SemanticObject obj=ont.getSemanticObject(suri);
        String mode=SWBFormMgr.MODE_EDIT;
        if(obj.instanceOf(Unmodifiable.swb_Unmodifiable))
        {
            boolean read=obj.getBooleanProperty(Unmodifiable.swb_readOnly);
            if(read)mode=SWBFormMgr.MODE_VIEW;
        }
        SWBFormMgr frm=new SWBFormMgr(obj, null,mode);
        frm.setLang(lang);
        if(smode!=null)
        {
            frm.processForm(request);
            out.println("<script type=\"text/javascript\">");
            //out.println("alert('mtreeStore:'+mtreeStore);");
            out.println("updateTreeNodeByURI('"+obj.getURI()+"');");

            String icon=SWBContext.UTILS.getIconClass(obj);
            out.println("setTabTitle('"+obj.getURI()+"','"+obj.getDisplayName(lang)+"','"+icon+"');");
            Iterator<SemanticObject> it2=obj.listRelatedObjects();
            while(it2.hasNext())
            {
                SemanticObject aux=it2.next();
                out.println("reloadTab('"+aux.getURI()+"');");
            }
            out.println("showStatus('"+obj.getSemanticClass().getDisplayName(lang)+" actualizado');");
            out.println("</script>");
        }
        frm.setAction("/swb/swbadmin/jsp/SemObjectEditor.jsp");
        out.println(frm.renderForm());
     }
}catch(Exception e){e.printStackTrace();}
%>
<!-- a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a -->
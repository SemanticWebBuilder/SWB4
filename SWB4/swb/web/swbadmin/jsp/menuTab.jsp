<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%><%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    GenericObject obj=ont.getGenericObject(id);
    org.semanticwb.model.MenuItem mnu=null;
    //System.out.println("id:"+id+" "+obj.getClass());
    if(obj==null)return;
    if(obj instanceof org.semanticwb.model.MenuItem)
    {
        mnu=(MenuItem)obj;
    }
    //System.out.println("mnu:"+mnu);
    if(mnu.getShowAs()==null || mnu.getShowAs().equals("CONTENT"))
    {
        out.println("<div dojoType=\"dijit.layout.ContentPane\" title=\""+mnu.getDisplayName(lang)+"\" refreshOnShow=\""+"false"+"\" href=\""+mnu.getUrl()+"\" loadingMessage=\"<center><img src='/swb/swbadmin/images/loading.gif'/><center>\" _onLoad=\"alert('test');\">");
        out.println("</div>");
    }else if(mnu.getShowAs().equals("IFRAME"))
    {
        out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\""+mnu.getUrl()+"\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"yes\"></iframe>");
    }
    //System.out.println(obj.getUrl());
    //String title=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().title);

    //out.println("<div id=\""+obj.getURI()+"/menu"+"\" dojoType=\"dijit.layout.ContentPane\" title=\""+obj.getDisplayName(lang)+"\" refreshOnShow=\""+"false"+"\" href=\""+obj.getUrl()+"\" _onLoad=\"alert('test');\">");
    //out.println("</div>");

%>

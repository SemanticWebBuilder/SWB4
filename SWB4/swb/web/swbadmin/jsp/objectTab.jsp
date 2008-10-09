<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("id");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    com.hp.hpl.jena.rdf.model.Resource res=ont.getResource(id);
    if(res==null)return;
    SemanticClass cls=ont.getSemanticObjectClass(res);
    GenericObject obj=ont.getGenericObject(id,cls);
    String title=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().title);
    
    //Validar menu principal
    WebPage menup=SWBContext.getAdminWebSite().getWebPage("WBAd_mnu_Main");
    if(obj instanceof WebPage && ((WebPage)obj).isChildof(menup))
    {
%>
        <iframe src ="<%=((WebPage)obj).getUrl()%>" width="100%" height="100%" frameborder="0"></iframe>
<%
    }else
    //Otro        
    {
%>
<div dojoType="dijit.layout.TabContainer" id_="bottomTabs" tabPosition_="bottom" selectedChild_="btab1">
    <div dojoType="dijit.layout.ContentPane" title="Información" style=" padding:10px;">
        <iframe src ="/swb/swbadmin/jsp/SemObjectEditor.jsp?suri=<%=URLEncoder.encode(obj.getURI())%>" width="100%" height="100%" frameborder="0"></iframe>
    </div><!-- end:info btab1 -->    
<%        
        String auxid=null;
        if(cls.getName().equals("Template"))auxid="WBAd_sys_Templates";
        if(cls.getName().equals("Portlet"))auxid="WBAd_sys_Resources";
        if(cls.getName().equals("PortletType"))auxid="WBAd_sys_ResourceType";
        if(cls.getName().equals("WebPage"))auxid="WBAd_sys_Topics";
        if(cls.getName().equals("WebSite"))auxid="WBAd_sys_TopicMaps";
        if(auxid!=null)
        {
            WebPage aux=SWBContext.getAdminWebSite().getWebPage(auxid);
            if(aux!=null)
            {
                Iterator<WebPage> it=aux.listVisibleChilds(null);
                while(it.hasNext())
                {
                    WebPage tab=it.next();
                    System.out.println("tab:"+tab);
%>
    <div id_="btab1" dojoType="dijit.layout.ContentPane" title="<%=tab.getTitle()%>" style=" padding:10px; ">
        <iframe src ="<%=tab.getUrl()%>?tm=<%=obj.getSemanticObject().getModel().getName()%>&tp=<%=obj.getId()%>&id=<%=obj.getId()%>&act=edit" width="100%" height="100%" frameborder="0"></iframe>
    </div><!-- end:info btab1 -->
<%
                
                }
            }
        }
%>
</div><!-- end Bottom TabContainer -->
<%
    }
%>

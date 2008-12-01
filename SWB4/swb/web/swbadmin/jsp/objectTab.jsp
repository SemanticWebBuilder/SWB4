<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    com.hp.hpl.jena.rdf.model.Resource res=ont.getResource(id);
    if(res==null)return;
    SemanticClass cls=ont.getSemanticObjectClass(res);
    GenericObject obj=ont.getGenericObject(id,cls);
    //String title=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().title);
    
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
        out.println("<div dojoType=\"dijit.layout.TabContainer\" id=\""+id+"/tab2\" _tabPosition=\"bottom\" _selectedChild=\"btab1\">");

        Iterator<ObjectBehavior> obit=SWBComparator.sortSortableObject(SWBContext.getVocabulary().swbxf_ObjectBehavior.listGenericInstances(true));
        while(obit.hasNext())
        {
            ObjectBehavior ob=obit.next();
            String title=ob.getTitle(lang);
            DisplayObject dpobj=ob.getDisplayObject();
            SemanticObject interf=ob.getInterface();
            boolean refresh=ob.isRefreshOnShow();
            String url=ob.getURL();
            System.out.println("ob:"+ob.getTitle(lang)+" "+ob.getDisplayObject()+" "+ob.getInterface()+" "+ob.getURL());

            String params="suri="+URLEncoder.encode(obj.getURI());
            Iterator<ResourceParameter> prmit=ob.listParams();
            while(prmit.hasNext())
            {
                ResourceParameter rp=prmit.next();
                params+="&"+rp.getName()+"="+rp.getValue().getEncodedURI();
            }
            System.out.println("params:"+params);
            //Genericos
            if(dpobj==null)
            {
                if(interf==null)
                {
                    out.println("<div dojoType=\"dijit.layout.ContentPane\" title=\""+title+"\" style=\"padding:10px;\" refreshOnShow=\""+refresh+"\" href=\""+url+"?"+params+"\"></div>");
                }else
                {
                    SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(interf.getURI());
                    if(scls!=null)
                    {
                        if(scls.getObjectClass().isInstance(obj))
                        {
                            out.println("<div dojoType=\"dijit.layout.ContentPane\" title=\""+title+"\" style=\"padding:10px;\" refreshOnShow=\""+refresh+"\" href=\""+url+"?"+params+"\"></div>");
                        }
                    }
                }
            }
        }
%>

<%        
        String buri="/swb/swb/SWBAdmin/WBAd_Home/_rid/1/_mto/3";
        buri+="?suri="+obj.getSemanticObject().getEncodedURI();
        SWBVocabulary voc=SWBContext.getVocabulary();
        
        if(obj instanceof PortletRefable)
        {
            String auri=buri;
            auri+="&sprop="+voc.swb_hasPortletRef.getEncodedURI();
            auri+="&spropref="+voc.swb_portlet.getEncodedURI();
            //System.out.println("auri:"+auri);            
%>
<div dojoType="dijit.layout.ContentPane" title="Contenidos" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }
        
        if(obj instanceof TemplateRefable)
        {
            String auri=buri;
            auri+="&sprop="+voc.swb_hasTemplateRef.getEncodedURI();
            auri+="&spropref="+voc.swb_template.getEncodedURI();
            //System.out.println("auri:"+auri);
%>
<div dojoType="dijit.layout.ContentPane" title="Plantillas" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }
        
        if(obj instanceof PFlowRefable)
        {
            String auri=buri;
            auri+="&sprop="+voc.swb_hasPFlowRef.getEncodedURI();
            auri+="&spropref="+voc.swb_pflow.getEncodedURI();
%>
<div dojoType="dijit.layout.ContentPane" title="Flujos de Publicación" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }
        
        if(obj instanceof Calendarable)
        {
            String auri="/swb/swb/SWBAdmin/WBAd_Home/_rid/55/_mto/3";
            auri+="?suri="+obj.getSemanticObject().getEncodedURI();
            auri+="&sprop="+voc.swb_hasCalendar.getEncodedURI();
            System.out.println(auri);
            //auri+="&spropref="+voc.pflow.getEncodedURI();            
%>
<div dojoType="dijit.layout.ContentPane" title="Calendarización" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }
        
        if(obj instanceof RoleRefable)
        {
            String auri=buri;
            auri+="&sprop="+voc.swb_hasRoleRef.getEncodedURI();
            auri+="&spropref="+voc.swb_role.getEncodedURI();
%>
<div dojoType="dijit.layout.ContentPane" title="Roles" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }
        
        if(obj instanceof RuleRefable)
        {
            String auri=buri;
            auri+="&sprop="+voc.swb_hasRuleRef.getEncodedURI();
            auri+="&spropref="+voc.swb_rule.getEncodedURI();
%>
<div dojoType="dijit.layout.ContentPane" title="Reglas" style=" padding:10px;" refreshOnShow="false" href="<%=auri%>"></div>
<%            
        }

        {
%>
<div dojoType="dijit.layout.ContentPane" title="Bitácora" style=" padding:10px;" refreshOnShow="false" href=""></div>
<%            
        }
        
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

    <div id_="btab1" dojoType="dijit.layout.ContentPane" title="<%=tab.getTitle()%>" style=" padding:10px; " refreshOnShow="false" _href="/swb/swbadmin/jsp/SemObjectEditor.jsp?suri=<%=URLEncoder.encode(obj.getURI())%>">
<!--    
        <iframe src ="<%=tab.getUrl()%>?tm=<%=obj.getSemanticObject().getModel().getName()%>&tp=<%=obj.getId()%>&id=<%=obj.getId()%>&act=edit" width="100%" height="100%" frameborder="0"></iframe>
-->        
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

<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.json.*,org.semanticwb.*,org.semanticwb.model.*,org.semanticwb.platform.*,java.util.*"%>
<%!
    int nullnode=0;
    String lang="es";

    public JSONObject getEvent(String name, String action, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("action", action);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    public JSONObject getNode(String id, String title, String type, String icon) throws JSONException
    {
        if(title==null)title="Topic";
        JSONObject obj=new JSONObject();
        obj.put("id", id);
        obj.put("title",title);
        obj.put("type",type);
        obj.put("icon",icon);
        return obj;
    }

    public JSONObject getDummy() throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("title", "Dummy");
        return obj;
    }
    
    public void addWebSites(JSONArray arr)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<WebSite> it=SWBComparator.sortSermanticObjects(SWBContext.listWebSites());
        while(it.hasNext())
        {
            WebSite site=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            site=SWBContext.getWebSite(site.getURI());
            addWebSite(arr, site);
        }                 
    }
   
    public void addWebSite(JSONArray arr, WebSite site) throws JSONException
    {
        //System.out.println("addWebSite");
        String type="WebSite";
        String icon="swbIcon"+type;
        if(!site.isActive())icon=icon+"U";
        JSONObject obj=getNode(site.getId(), site.getDisplayTitle(lang), type,icon);
        arr.put(obj);
        
        JSONArray childs=new JSONArray();
        obj.putOpt("children", childs);
        
        //addTemplates(childs,site);
        //addPortlets(childs,site);

        addHerarquicalNodes(childs, site.getSemanticObject());
        
        WebPage home=site.getHomePage();
        if(home!=null)
        {
            addWebPage(childs, home);
        }
    }

    public void addHerarquicalNodes(JSONArray arr, SemanticObject model) throws JSONException
    {
        Iterator<SemanticObject> it=model.getSemanticClass().listHerarquicalNodes();
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(arr,node,model);
        }
    }

    public void addHerarquicalNode(JSONArray arr, HerarquicalNode node, SemanticObject model) throws JSONException
    {
        JSONObject obj=getNode(model.getURI()+"/HerarquicalNode#"+node.getId(), node.getDisplayTitle(lang), "HerarquicalNode", node.getIconClass());
        arr.put(obj);

        JSONArray childs=new JSONArray();
        Iterator<SemanticObject> it=model.getModel().listInstancesOfClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI()));
        if(it.hasNext())
        {
            obj.putOpt("children", childs);
            //childs.put(getDummy());
            //JSONArray events=new JSONArray();
            //obj.putOpt("events", events);
            //events.put(getEvent("onOpen","reload",null,null));
        }
        while(it.hasNext())
        {
            SemanticObject so=it.next();
            addSemanticObject(childs, so);
        }

    }

    public void addSemanticObject(JSONArray arr, SemanticObject obj) throws JSONException
    {
        JSONObject jobj=getNode(obj.getURI(), obj.getDisplayName(lang), obj.getSemanticClass().getName(), "swbIcon"+obj.getSemanticClass().getName());
        arr.put(jobj);

        System.out.println("obj:"+obj.getId());
        SemanticClass cls=obj.getSemanticClass();
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);
        Iterator<SemanticProperty> it=cls.listHerarquicalProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            System.out.println("prop:"+prop.getName());
            Iterator<SemanticObject> it2=obj.listObjectProperties(prop);
            if(it2.hasNext())
            {
                //childs.put(getDummy());
                //JSONArray events=new JSONArray();
                //obj.putOpt("events", events);
                //events.put(getEvent("onOpen","reload",null,null));
            }
            while(it2.hasNext())
            {
                SemanticObject ch=it2.next();
                addSemanticObject(childs, ch);
            }
        }
    }


    public void addWebPage(JSONArray arr, WebPage page) throws JSONException
    {
        //System.out.println("addWebPage");
        String type="WebPage";
        String icon="swbIcon"+type;
        if(!page.isActive())icon=icon+"U";
        JSONObject obj=getNode(page.getURI(), page.getTitle(), type, icon);
        arr.put(obj);
        
        //if(page.getId().length()>3)return;
        JSONArray childs=new JSONArray();

        Iterator<WebPage> it=page.listChilds(null,null,false,null,null);
        if(it.hasNext())
        {
            obj.putOpt("children", childs);
            childs.put(getDummy());
            JSONArray events=new JSONArray();
            obj.putOpt("events", events);
            events.put(getEvent("onOpen","reload",null,null));
        }
        //while(it.hasNext())
        //{
        //    WebPage wp=it.next();
        //    addWebPage(childs, wp);
        //}
    }
    
    public void addTemplates(JSONArray arr, WebSite site)  throws JSONException
    {
        //System.out.println("addTemplates");
        String type="Templates";
        String icon="swbIcon"+type;
        JSONObject obj=getNode(site.getId()+"."+"Templates", "Plantillas", type, icon);
        arr.put(obj);
        
        //if(page.getId().length()>3)return;
        JSONArray childs=new JSONArray();
        obj.putOpt("children", childs);
        
        Iterator<Template> it=SWBComparator.sortSermanticObjects(site.listTemplates());
        while(it.hasNext())
        {
            Template aux=it.next();
            addTemplate(childs, aux);
        }
    }
    
    public void addTemplate(JSONArray arr, Template template) throws JSONException
    {
        //System.out.println("addTemplate");
        String type="Template";
        String icon="swbIcon"+type;
        if(!template.isActive())icon=icon+"U";
        
        JSONObject obj=getNode(template.getURI(), template.getTitle(), type, icon);
        arr.put(obj);
    }
    
    public void addPortlets(JSONArray arr, WebSite site)  throws JSONException
    {
        //System.out.println("addPortlets");
        String type="Portlets";
        String icon="swbIcon"+type;
        
        JSONObject obj=getNode(site.getId()+"."+"Portlets", "Portlets", type,icon);
        arr.put(obj);
        
        //if(page.getId().length()>3)return;
        JSONArray childs=new JSONArray();
        obj.putOpt("children", childs);
        
        Iterator<PortletType> it=SWBComparator.sortSermanticObjects(site.listPortletTypes());
        while(it.hasNext())
        {
            PortletType aux=it.next();
            addPortletType(childs, aux);
        }
    }
    
    public void addPortletType(JSONArray arr, PortletType aux)  throws JSONException
    {
        //System.out.println("addPortletType");
        String type="PortletType";
        String icon="swbIcon"+type;
        
        JSONObject obj=getNode(aux.getURI(), aux.getTitle(), type, icon);
        arr.put(obj);
        
        //if(page.getId().length()>3)return;
        JSONArray childs=new JSONArray();
        obj.putOpt("children", childs);
        
        Iterator<Portlet> it=SWBComparator.sortSermanticObjects(aux.listPortlets());
        while(it.hasNext())
        {
            Portlet aux2=it.next();
            addPortlet(childs, aux2);
        }
    }    
    
    public void addPortlet(JSONArray arr, Portlet aux) throws JSONException
    {
        //System.out.println("addPortlet");
        String type="Portlet";
        String icon="swbIcon"+type;
        if(!aux.isActive())icon=icon+"U";
        
        JSONObject obj=getNode(aux.getURI(), aux.getTitle(), type, icon);
        arr.put(obj);
    }    
    
%><%
    System.out.println("Tree1");
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    
    String suri=request.getParameter("suri");
    System.out.println("suri:"+suri);
    if(suri==null)
    {
        JSONObject obj=new JSONObject();
        obj.put("identifier", "id");
        obj.put("label","title");
        JSONArray items=new JSONArray();
        obj.putOpt("items", items);
        addWebSites(items);
        out.print(obj.toString());
        //System.out.print(obj.toString());
    }else
    {
        JSONArray items=new JSONArray();
        GenericObject sobj=null;
        sobj=SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
        if(sobj!=null)
        {
            if(sobj instanceof WebSite)
            {
                addWebSite(items,(WebSite)sobj);
            }else if(sobj instanceof WebPage)
            {
                Iterator<WebPage> it=((WebPage)sobj).listChilds();
                while(it.hasNext())
                {
                    WebPage p=it.next();
                    //addWebPage(items,page);
                    String type="WebPage";
                    String icon="swbIcon"+type;
                    if(!p.isActive())icon=icon+"U";
                    JSONObject obj=getNode(p.getURI(), p.getTitle(), type, icon);
                    items.put(obj);

                    Iterator<WebPage> it2=p.listChilds(null,null,false,null,null);
                    if(it2.hasNext())
                    {
                        obj.put("hasChilds", "true");
                        JSONArray events=new JSONArray();
                        obj.putOpt("events", events);
                        events.put(getEvent("onOpen","reload",null,null));
                    }
                }
            }
            out.println(items.toString());
            //System.out.println(items.toString());
        }
    }    

    /*
{ identifier: 'name',
  label: 'name',
  items: [
     { name:'Africa', type:'continent', children:[{ name:'Kenya', type:'country'}, {_reference:'Sudan'}] },
     { name:'Nairobi', type:'city' },
     { name:'Mombasa', type:'city' },
     { name:'Sudan', type:'country', children:{_reference:'Khartoum'} },
     { name:'Khartoum', type:'city' },
     { name:'Argentina', type:'country'}
]}
    */
    System.out.println("Tree2");
%>

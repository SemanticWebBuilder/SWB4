<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.json.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!
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
        JSONObject obj=getNode(site.getId(), site.getTitle(), type,icon);
        arr.put(obj);
        
        JSONArray childs=new JSONArray();
        obj.putOpt("children", childs);
        
        addTemplates(childs,site);
        addPortlets(childs,site);
        
        WebPage home=site.getHomePage();
        if(home!=null)
        {
            addWebPage(childs, home);
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
        obj.putOpt("children", childs);

        Iterator<WebPage> it=page.listChilds(null,null,false,null,null);
        while(it.hasNext())
        {
            WebPage wp=it.next();
            addWebPage(childs, wp);
        }
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
    GenericObject sobj=null;

    JSONObject obj=new JSONObject();
    obj.put("identifier", "id");
    obj.put("label","title");
    JSONArray items=new JSONArray();
    obj.putOpt("items", items);

    if(suri!=null)
    {
        sobj=SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
        if(sobj!=null)
        {
            if(sobj instanceof WebSite)
            {
                addWebSite(items,(WebSite)sobj);
            }else if(sobj instanceof WebPage)
            {
                addWebPage(items,(WebPage)sobj);
            }
        }
    }else
    {
        addWebSites(items);
    }

    out.print(obj.toString());
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

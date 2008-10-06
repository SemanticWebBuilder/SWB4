<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.json.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!
    public JSONObject getNode(String id, String title) throws JSONException
    {
        if(title==null)title="Topic";
        JSONObject obj=new JSONObject();
        obj.put("id", id);
        obj.put("name",title);
        return obj;
    }
    
    public void addWebSites(JSONArray arr)  throws JSONException
    {
        Iterator<WebSite> it=SWBContext.listWebSites();
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
        String type="WebSite";
        String icon="swbIcon"+type;
        if(!site.isActive())icon=icon+"U";
        JSONObject obj=getNode(site.getId(), site.getTitle());
        arr.put(obj);
    }
    
%><%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    JSONObject obj=new JSONObject();
    obj.put("identifier", "id");
    //obj.put("label","title");
    JSONArray items=new JSONArray();
    obj.putOpt("items", items);
    
    addWebSites(items);

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
%>

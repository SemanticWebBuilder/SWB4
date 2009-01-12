<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.json.*,org.semanticwb.*,org.semanticwb.model.*,org.semanticwb.platform.*,java.util.*"%>
<%!
    int nullnode=0;
    String lang="es";

    public JSONObject getAction(String name, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    public JSONObject getEvent(String name, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("action", action);
        return obj;
    }

    public JSONObject getMenuItem(String title, String icon, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("title", title);
        obj.put("icon", icon);
        obj.put("action", action);
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

    public JSONObject getReloadAction() throws JSONException
    {
        return getAction("reload",null,null);
    }

    public JSONObject getNewTabAction() throws JSONException
    {
        return getAction("newTab",SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp",null);
    }

    public JSONObject getMenuSeparator() throws JSONException
    {
        return getMenuItem("_",null, null);
    }
    
    public void addWebSites(JSONArray arr)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<WebSite> it=SWBComparator.sortSermanticObjects(SWBContext.listWebSites(),lang);
        while(it.hasNext())
        {
            WebSite site=it.next();
            //System.out.println("site:"+site);
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //site=SWBContext.getWebSite(site.getURI());
            addSemanticObject(arr, site.getSemanticObject(),false,true);
            //addWebSite(arr, site);
        }                 
    }

    public void addUserReps(JSONArray arr)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<UserRepository> it=SWBComparator.sortSermanticObjects(SWBContext.listUserRepositorys(),lang);
        while(it.hasNext())
        {
            UserRepository rep=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //rep=SWBContext.getUserRepository(rep.getURI());
            addSemanticObject(arr, rep.getSemanticObject(),false,true);
            //addWebSite(arr, site);
        }
    }

    public void addFavorites(JSONArray arr)  throws JSONException
    {
        //TODO:
        User user=SWBContext.getDefaultRepository().getUserByLogin("admin");
        UserFavorites fav=user.getUserFavorites();
        if(fav!=null)
        {
            Iterator<SemanticObject> it=SWBComparator.sortSermanticObjects(fav.listObjects());
            while(it.hasNext())
            {
                SemanticObject obj=it.next();
                addSemanticObject(arr, obj,false,true);
            }
        }
    }

    public boolean hasHerarquicalNodes(SemanticObject obj) throws JSONException
    {
        boolean ret=false;
        Iterator<SemanticObject> it=obj.getSemanticClass().listHerarquicalNodes();
        if(it.hasNext())
        {
            ret=true;
        }
        return ret;
    }


    public void addHerarquicalNodes(JSONArray arr, SemanticObject obj) throws JSONException
    {
        Iterator<SemanticObject> it=SWBComparator.sortSortableObject(obj.getSemanticClass().listHerarquicalNodes());
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(arr,node,obj,false);
        }
    }

    public void addHerarquicalNode(JSONArray arr, HerarquicalNode node, SemanticObject obj, boolean addChilds) throws JSONException
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        JSONObject jobj=getNode("HN|"+obj.getURI()+"|"+node.getURI(), node.getDisplayTitle(lang), "HerarquicalNode", node.getIconClass());
        arr.put(jobj);

        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);
        Iterator<SemanticObject> it=SWBComparator.sortSermanticObjects(obj.getModel().listInstancesOfClass(cls),lang);

        //System.out.println("obj:"+obj.getId());
        //drop acceptance
        JSONArray dropacc=new JSONArray();
        jobj.putOpt("dropacc", dropacc);

        //Menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);
        String url=SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+cls.getEncodedURI()+"&sref="+obj.getEncodedURI();
        menus.put(getMenuItem("Agregar "+cls.getDisplayName(lang), "dijitEditorIcon dijitEditorIconCut",getAction("showDialog", url,null)));
        dropacc.put(cls.getClassID());
        //Iterator<SemanticClass> it2=cls.listSubClasses();
        //while(it2.hasNext())
        //{
        //    SemanticClass scls=it2.next();
        //    menus.put(getMenuItem("Agregar "+scls.getDisplayName(lang), "dijitEditorIcon dijitEditorIconCut",getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+scls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),null)));
        //    dropacc.put(scls.getClassID());
        //}

        menus.put(getMenuSeparator());
        menus.put(getMenuItem("Recargar", "dijitEditorIcon dijitEditorIconCut", getReloadAction()));

        SemanticProperty herarprop=null;   //Herarquical property;
        Iterator<SemanticProperty> hprops=cls.listInverseHerarquicalProperties();
        if(hprops.hasNext())herarprop=hprops.next();

        //System.out.println("herarprop:"+herarprop);

        if(addChilds)
        {
            while(it.hasNext())
            {
                SemanticObject so=it.next();
                if(herarprop!=null)
                {
                    if(so.getObjectProperty(herarprop)==null)
                    {
                        addSemanticObject(childs, so,false);
                    }
                }else
                {
                    addSemanticObject(childs, so,false);
                }
            }
        }else
        {
            if(it.hasNext())
            {
                jobj.put("hasChilds", "true");
                JSONArray events=new JSONArray();
                jobj.putOpt("events", events);
                events.put(getEvent("onOpen", getReloadAction()));
            }
        }
    }

    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds) throws JSONException
    {
        addSemanticObject(arr, obj, addChilds, false);
    }


    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, boolean addDummy) throws JSONException
    {
        boolean hasChilds=false;
        SemanticClass cls=obj.getSemanticClass();
        String type=cls.getClassID();

        //Active
        boolean active=false;
        SemanticProperty activeprop=cls.getProperty("active");
        if(activeprop!=null)
        {
            active=obj.getBooleanProperty(activeprop);
        }

        String icon=SWBContext.UTILS.getIconClass(obj);
        JSONObject jobj=getNode(obj.getURI(), obj.getDisplayName(lang), type, icon);
        arr.put(jobj);

        //System.out.println("obj:"+obj.getId());
        //drop acceptance
        JSONArray dropacc=new JSONArray();
        jobj.putOpt("dropacc", dropacc);

        //menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);

        Iterator<SemanticProperty> pit=cls.listHerarquicalProperties();
        while(pit.hasNext())
        {
            SemanticProperty prop=pit.next();
            SemanticClass rcls=prop.getRangeClass();
            menus.put(getMenuItem("Agregar "+rcls.getDisplayName(lang), "dijitEditorIcon dijitEditorIconCut",getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+rcls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),null)));
            dropacc.put(rcls.getClassID());
            Iterator<SemanticClass> it=rcls.listSubClasses();
            while(it.hasNext())
            {
                SemanticClass scls=it.next();
                menus.put(getMenuItem("Agregar "+scls.getDisplayName(lang), "dijitEditorIcon dijitEditorIconCut",getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+scls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),null)));
                dropacc.put(scls.getClassID());
            }
        }
        menus.put(getMenuSeparator());

        //Active
        if(activeprop!=null)
        {
            if(!active)
            {
                menus.put(getMenuItem("Activar", "dijitEditorIcon dijitEditorIconCut", getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
            }else
            {
                menus.put(getMenuItem("Desactivar", "dijitEditorIcon dijitEditorIconCut", getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
            }
        }

        menus.put(getMenuItem("Editar", "dijitEditorIcon dijitEditorIconCut", getNewTabAction()));
        menus.put(getMenuItem("Eliminar", "dijitEditorIcon dijitEditorIconCut", getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/delete.jsp?suri="+obj.getEncodedURI(),null)));
        menus.put(getMenuSeparator());

        //TODO:
        User user=SWBContext.getDefaultRepository().getUserByLogin("admin");
        boolean isfavo=user.hasFavorite(obj);
        if(!isfavo)
        {
            menus.put(getMenuItem("Agragar a Favoritos", "dijitEditorIcon dijitEditorIconCut", getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
        }else
        {
            menus.put(getMenuItem("Eliminar de Favoritos", "dijitEditorIcon dijitEditorIconCut", getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
        }
        menus.put(getMenuItem("Recargar", "dijitEditorIcon dijitEditorIconCut", getReloadAction()));

        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        events.put(getEvent("onDblClick", getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp", null)));
        events.put(getEvent("onClick", getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        hasChilds=hasHerarquicalNodes(obj);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(childs, obj);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSermanticObjects(it,lang);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    addSemanticObject(childs, ch,false);
                }
            }else
            {
                if(it.hasNext())
                {
                    hasChilds=true;
                }
            }
        }
        if(hasChilds && !addChilds)
        {
            if (addDummy) {
                childs.put(getNode("_NOID_" + (nullnode++), "DUMMY", "DUMMY", "DUMMY"));
            } else {
                jobj.put("hasChilds", "true");
            }
            events.put(getEvent("onOpen", getReloadAction()));
        }
    }

    
%><%
    String id=request.getParameter("id");
    if(id==null)id="mtree";
    //System.out.println(new Date()+" Tree1");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();

    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    
    String suri=request.getParameter("suri");
    //System.out.println("suri:"+suri);
    if(suri==null)
    {
        JSONObject obj=new JSONObject();
        obj.put("identifier", "id");
        obj.put("label","title");
        JSONArray items=new JSONArray();
        obj.putOpt("items", items);
        if(id.equals("mtree"))addWebSites(items);
        if(id.equals("muser"))addUserReps(items);
        if(id.equals("mfavo"))addFavorites(items);
        out.print(obj.toString());
        //System.out.print(obj.toString());
    }else
    {
        boolean addChilds=true;
        String childs=request.getParameter("childs");
        if(childs!=null && childs.equals("false"))addChilds=false;

        JSONArray items=new JSONArray();
        if(suri.startsWith("HN|"))
        {
            StringTokenizer st=new StringTokenizer(suri,"|");
            String aux=st.nextToken();
            String ouri=st.nextToken();
            String nuri=st.nextToken();
            //System.out.println("aux:"+aux+" ouri:"+ouri+" nuri:"+nuri);
            if(ouri!=null && nuri!=null)
            {
                SemanticObject obj=ont.getSemanticObject(ouri);
                SemanticObject nobj=ont.getSemanticObject(nuri);
                //System.out.println("obj:"+obj+" node:"+nobj);
                HerarquicalNode node=new HerarquicalNode(nobj);
                addHerarquicalNode(items,node,obj,addChilds);
            }
        }else
        {
            SemanticObject sobj=ont.getSemanticObject(suri);
            if(sobj!=null)
            {
                addSemanticObject(items, sobj,addChilds);
                Iterator<SemanticObject> it=sobj.listHerarquicalParents();
                if(it.hasNext())
                {
                    JSONObject obj=items.getJSONObject(0);
                    obj.put("parent", it.next().getURI());
                }
            }
        }
        out.print(items.toString());
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
    //System.out.println(new Date()+" Tree2");
%>

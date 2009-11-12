<%@page import="org.json.*,org.semanticwb.*,org.semanticwb.model.*,org.semanticwb.platform.*,java.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.*,com.hp.hpl.jena.util.*,com.hp.hpl.jena.rdf.model.Model" %>
<%@page contentType="text/html" %><%@page pageEncoding="UTF-8" %>
<%!
    int nullnode=0;

    public String getUserLanguage()
    {
        String lang="es";
        User user=SWBContext.getSessionUser();
        if(user!=null)lang=user.getLanguage();
        return lang;
    }

    public String getLocaleString(String key, String lang)
    {
        String ret="";
        if(lang==null)
        {
            ret=SWBUtils.TEXT.getLocaleString("locale_swb_admin", key);
        }else
        {
            ret=SWBUtils.TEXT.getLocaleString("locale_swb_admin", key, new Locale(lang));
        }
        //System.out.println(key+" "+lang+" "+ret);
        return ret;
    }

    public JSONObject getAction(String name, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", getID());
        obj.put("name", name);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    public String getID()
    {
        return "_NOID_"+(nullnode++);
    }

    public JSONObject getEvent(String name, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", getID());
        obj.put("name", name);
        obj.put("action", action);
        return obj;
    }

    public JSONObject getMenuItem(String title, String icon, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", getID());
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

    public JSONObject getDummyNode() throws JSONException
    {
        return getNode(getID(), "DUMMY", "DUMMY", "DUMMY");
    }

    public JSONObject getMenuNode() throws JSONException
    {
        return getNode(getID(), "SubMenus", "Group", "swbIconTemplateGroup");
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

    public JSONObject getMenuReload(String lang) throws JSONException
    {
        return getMenuItem(getLocaleString("reload",lang), getLocaleString("icon_reload",null), getReloadAction());
    }

    public void addWebSites(JSONArray arr, String lang)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<WebSite> it=SWBComparator.sortSermanticObjects(lang, SWBContext.listWebSites());
        while(it.hasNext())
        {
            WebSite site=it.next();
            //System.out.println("site:"+site);
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //site=SWBContext.getWebSite(site.getURI());
            addSemanticObject(arr, site.getSemanticObject(),false,true,lang);
            //addWebSite(arr, site);
        }

    }

    public void addUserReps(JSONArray arr, String lang)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<UserRepository> it=SWBComparator.sortSermanticObjects(lang, SWBContext.listUserRepositories());
        while(it.hasNext())
        {
            UserRepository rep=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //rep=SWBContext.getUserRepository(rep.getURI());
            addSemanticObject(arr, rep.getSemanticObject(),false,true,lang);
            //addWebSite(arr, site);
        }
    }

    public void addDocRepositories(JSONArray arr, String lang)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<org.semanticwb.repository.Workspace> it=SWBComparator.sortSermanticObjects(lang, SWBContext.listWorkspaces());
        while(it.hasNext())
        {
            org.semanticwb.repository.Workspace rep=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //rep=SWBContext.getUserRepository(rep.getURI());
            addSemanticObject(arr, rep.getSemanticObject(),false,true,lang);
            //addWebSite(arr, site);
        }
    }

    public boolean hasHerarquicalNodes(SemanticObject obj, String lang) throws JSONException
    {
        boolean ret=false;
        Iterator<SemanticObject> it=obj.getSemanticClass().listHerarquicalNodes();
        if(it.hasNext())
        {
            ret=true;
        }
        return ret;
    }


    public void addHerarquicalNodes(JSONArray arr, SemanticObject obj, String lang) throws JSONException
    {
        Iterator<SemanticObject> it=SWBComparator.sortSortableObject(obj.getSemanticClass().listHerarquicalNodes());
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(arr,node,obj,false,lang);
        }
    }

    public void addHerarquicalNode(JSONArray arr, HerarquicalNode node, SemanticObject obj, boolean addChilds, String lang) throws JSONException
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        String pf=node.getPropertyFilter();
        JSONObject jobj=getNode("HN|"+obj.getURI()+"|"+node.getURI(), node.getDisplayTitle(lang), "HerarquicalNode", node.getIconClass());
        arr.put(jobj);

        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        if(cls.isSubClass(Filterable.swb_Filterable))
        {
            Iterator<SemanticObject> it=SWBObjectFilter.filter(SWBComparator.sortSermanticObjects(lang, obj.getModel().listInstancesOfClass(cls)),pf);
            if(addChilds)
            {
                //Menus
                //JSONObject menu=getMenuNode();
                //childs.put(menu);
                //JSONArray menus=new JSONArray();
                //menu.putOpt("children", menus);
                //menus.put(getNode(getID(), getLocaleString("add",lang), "add", getLocaleString("icon_add",null)));

                while(it.hasNext())
                {
                    SemanticObject so=it.next();
                    addSemanticObject(childs, so,false,lang);
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
    }

    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, String lang) throws JSONException
    {
        addSemanticObject(arr, obj, addChilds, false,lang);
    }

    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, boolean addDummy, String lang) throws JSONException
    {
        boolean hasChilds=false;
        SemanticClass cls=obj.getSemanticClass();
        SemanticObject dispobj=cls.getDisplayObject();
        String type=cls.getClassId();

        //Active
        boolean active=false;
        SemanticProperty activeprop=cls.getProperty("active");
        if(activeprop!=null)
        {
            active=obj.getBooleanProperty(activeprop);
        }

        String uriext="";
        String icon=SWBContext.UTILS.getIconClass(obj);
        JSONObject jobj=getNode(obj.getURI()+uriext, obj.getDisplayName(lang), type, icon);
        arr.put(jobj);
/*
        //menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);

        //TODO:separar treeController
        if(!cls.equals(WebSite.ClassMgr.sclass))
        {
            //menus creacion
            Iterator<SemanticProperty> pit=cls.listHerarquicalProperties();
            while(pit.hasNext())
            {
                SemanticProperty prop=pit.next();
                SemanticClass rcls=prop.getRangeClass();
                menus.put(getMenuItem(getLocaleString("add",lang)+" "+rcls.getDisplayName(lang), getLocaleString("icon_add",null),getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+rcls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),getLocaleString("add",lang)+" "+rcls.getDisplayName(lang))));
                //add subclasess

                Iterator<SemanticClass> it=rcls.listSubClasses();
                while(it.hasNext())
                {
                    SemanticClass scls=it.next();
                    menus.put(getMenuItem(getLocaleString("add",lang)+" "+scls.getDisplayName(lang), getLocaleString("icon_add",null),getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+scls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),getLocaleString("add",lang)+" "+scls.getDisplayName(lang))));
                }

            }
        }

        if(obj.instanceOf(WebPage.ClassMgr.sclass))
        {
            WebPage page=(WebPage)obj.createGenericInstance();
            menus.put(getMenuItem(getLocaleString("preview",lang), getLocaleString("icon_preview",null), getAction("showPreviewURL",page.getUrl(),null)));
        }

        if(menus.length()>0)
            menus.put(getMenuSeparator());

        //Active
        if(activeprop!=null)
        {
            if(!active)
            {
                menus.put(getMenuItem(getLocaleString("active",lang), getLocaleString("icon_active",null), getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
            }else
            {
                menus.put(getMenuItem(getLocaleString("unactive",lang), getLocaleString("icon_unactive",null), getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
            }
        }

        menus.put(getMenuItem(getLocaleString("edit",lang), getLocaleString("icon_edit",null), getNewTabAction()));
        //menus.put(getMenuItem(getLocaleString("clone",lang), getLocaleString("icon_clone",null), getAction("showStatusURLConfirm",SWBPlatform.getContextPath()+"/swbadmin/jsp/clone.jsp?suri="+obj.getEncodedURI(),getLocaleString("clone",lang)+" "+cls.getDisplayName(lang))));
        //menu remove
        if(!obj.instanceOf(Undeleteable.swb_Undeleteable) ||  (obj.instanceOf(Undeleteable.swb_Undeleteable) && obj.getBooleanProperty(Undeleteable.swb_undeleteable)==false))
        {
            menus.put(getMenuItem(getLocaleString("delete",lang), getLocaleString("icon_delete",null), getAction("showStatusURLConfirm",SWBPlatform.getContextPath()+"/swbadmin/jsp/delete.jsp?suri="+obj.getEncodedURI(),getLocaleString("delete",lang)+" "+cls.getDisplayName(lang))));
        }
        menus.put(getMenuSeparator());

        //menu favoritos
        User user=SWBContext.getSessionUser();
        boolean isfavo=user.hasFavorite(obj);
        if(!isfavo)
        {
            menus.put(getMenuItem(getLocaleString("addFavorites",lang), getLocaleString("icon_addFavorites",null), getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
        }else
        {
            menus.put(getMenuItem(getLocaleString("deleteFavorites",lang), getLocaleString("icon_deleteFavorites",null), getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
        }

        //menu recargar
        //TODO:validar recarga de virtual
        menus.put(getMenuReload(lang));
*/
        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        //events.put(getEvent("onDblClick", getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp", null)));
        //events.put(getEvent("onClick", getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        hasChilds=hasHerarquicalNodes(obj,lang);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(childs, obj,lang);

            boolean isWebPage=obj.instanceOf(WebPage.sclass);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSermanticObjects(lang,it);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    if(ch.instanceOf(Filterable.swb_Filterable))
                        addSemanticObject(childs, ch, false, false,lang);
                }
            }else
            {
                while(it.hasNext())
                {
                    SemanticObject ch=it.next();
                    if(ch.instanceOf(Filterable.swb_Filterable))
                    {
                        hasChilds=true;
                        break;
                    }
                }
            }
        }
        if(hasChilds && !addChilds)
        {
            if (addDummy) {
                childs.put(getDummyNode());
            } else {
                jobj.put("hasChilds", "true");
            }
            events.put(getEvent("onOpen", getReloadAction()));
        }
    }

%><%
    String lang=getUserLanguage();

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
        //if(id.equals("mtree"))
            addWebSites(items,lang);
        //if(id.equals("muser"))addUserReps(items,lang);
        //if(id.equals("mfavo"))addFavorites(items,lang);
        //if(id.equals("mtra"))addWebSitesTrash(items,lang);
        //if(id.equals("mdoc"))addDocRepositories(items,lang);
        out.print(obj.toString());
        //System.out.print(id);
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
                addHerarquicalNode(items,node,obj,addChilds,lang);
            }
        }else
        {
            SemanticObject sobj=ont.getSemanticObject(suri);
            if(sobj!=null)
            {
                addSemanticObject(items, sobj,addChilds,lang);
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

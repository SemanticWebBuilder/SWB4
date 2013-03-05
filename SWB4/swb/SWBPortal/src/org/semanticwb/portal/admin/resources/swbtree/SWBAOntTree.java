/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.admin.resources.swbtree;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAOntTree.
 * 
 * @author javier.solis
 */
public class SWBAOntTree extends GenericResource
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBATree.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {

    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equals("json"))
        {
            doJSON(request, response, paramRequest);
        }else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/treeWidget.jsp";
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (dispatcher != null)
            {
                    dispatcher.include(request, response);
            }
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Do json.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doJSON(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //contentType="text/html" %><%@page pageEncoding="UTF-8" %>
        response.setContentType("text/html");
        try
        {
            PrintWriter out=response.getWriter();
            HttpSession session=request.getSession();
            SemanticOntology sont=(SemanticOntology)session.getAttribute("ontology");
            if(sont==null)
            {
                OntDocumentManager mgr=com.hp.hpl.jena.ontology.OntDocumentManager.getInstance();
                Model m=SWBPlatform.getSemanticMgr().loadRDFFileModel("file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl");
                mgr.addModel("http://www.semanticwebbuilder.org/swb4/ontology", m);

                //mgr.addAltEntry("http://www.semanticwebbuilder.org/swb4/ontology", "file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl");
                mgr.addAltEntry("http://www.semanticwb.org/catalogs", "file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/cat.owl");

                //String swbowl="file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/cat.owl";
                //java.io.File owlf=new java.io.File(swbowl);
                //SemanticModel base=SWBPlatform.getSemanticMgr().readRDFFile(owlf.getName(),swbowl);
                //sont=new SemanticOntology("", com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF,base.getRDFModel()));
                //sont=new SemanticOntology("", mgr.getOntology("http://www.semanticwb.org/catalogs", OntModelSpec.OWL_MEM_RDFS_INF));
                sont=new SemanticOntology("", mgr.getOntology("http://www.semanticwebbuilder.org/swb4/ontology", OntModelSpec.OWL_MEM_RDFS_INF));
        /*
                swbowl="file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl";
                owlf=new java.io.File(swbowl);
                SemanticModel model=SWBPlatform.getSemanticMgr().readRDFFile(owlf.getName(),swbowl);
                sont.addSubModel(model, false);
        */
                session.setAttribute("ontology", sont);

                //System.out.println("Base:"+base.getRDFModel().size());
                //System.out.println("SWB:"+model.getRDFModel().size());
                //System.out.println("Ont:"+sont.getRDFOntModel().size());
            }

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
                if(id.equals("mtree"))addWebSites(items,paramRequest);
                if(id.equals("muser"))addUserReps(items,paramRequest);
                if(id.equals("mfavo"))addFavorites(items,paramRequest);
                if(id.equals("mtra")) addWebSitesTrash(items,paramRequest);
                if(id.equals("mdoc")) addDocRepositories(items,paramRequest);
                if(id.equals("mclass")) addClasses(items,sont,paramRequest);
                if(id.equals("mprop")) addProperties(items,sont,paramRequest);
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
                        addHerarquicalNode(items,node,obj,addChilds,paramRequest);
                    }
                }else
                {
                    SemanticObject sobj=ont.getSemanticObject(suri);
                    if(sobj!=null)
                    {
                        addSemanticObject(items, sobj,addChilds,paramRequest);
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
        }catch(JSONException e)
        {
            log.error(e);
        }
    }

    /**
     * Adds the web sites.
     * 
     * @param arr the arr
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addWebSites(JSONArray arr, SWBParamRequest paramRequest)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<WebSite> it=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), SWBContext.listWebSites());
        while(it.hasNext())
        {
            WebSite site=it.next();
            //System.out.println("site:"+site);
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //site=SWBContext.getWebSite(site.getURI());
            addSemanticObject(arr, site.getSemanticObject(),false,true,paramRequest);
            //addWebSite(arr, site);
        }
    }

    /**
     * Adds the user reps.
     * 
     * @param arr the arr
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addUserReps(JSONArray arr, SWBParamRequest paramRequest)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<UserRepository> it=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), SWBContext.listUserRepositories());
        while(it.hasNext())
        {
            UserRepository rep=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //rep=SWBContext.getUserRepository(rep.getURI());
            addSemanticObject(arr, rep.getSemanticObject(),false,true,paramRequest);
            //addWebSite(arr, site);
        }
    }

    /**
     * Adds the doc repositories.
     * 
     * @param arr the arr
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addDocRepositories(JSONArray arr, SWBParamRequest paramRequest)  throws JSONException
    {
        //System.out.println("addWebSites");
        Iterator<org.semanticwb.repository.Workspace> it=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), SWBContext.listWorkspaces());
        while(it.hasNext())
        {
            org.semanticwb.repository.Workspace rep=it.next();
            //TODO: arreglar lista de sitios en SWBContext (estal ligados a ontologia)
            //rep=SWBContext.getUserRepository(rep.getURI());
            addSemanticObject(arr, rep.getSemanticObject(),false,true,paramRequest);
            //addWebSite(arr, site);
        }
    }

    /**
     * Adds the favorites.
     * 
     * @param arr the arr
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addFavorites(JSONArray arr, SWBParamRequest paramRequest)  throws JSONException
    {
        User user=SWBContext.getSessionUser();
        //System.out.println("user uri:"+user.getURI());
        if(user!=null && user.getURI()!=null)
        {
            UserFavorite fav=user.getUserFavorite();
            if(fav!=null)
            {
                //System.out.println("user fav:"+user.getURI());
                Iterator<SemanticObject> it=SWBComparator.sortSemanticObjects(fav.listObjects());
                while(it.hasNext())
                {
                    SemanticObject obj=it.next();
                    addSemanticObject(arr, obj,false,true,paramRequest);
                }
            }
        }
    }

    /**
     * Adds the web sites trash.
     * 
     * @param arr the arr
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addWebSitesTrash(JSONArray arr, SWBParamRequest paramRequest)  throws JSONException
    {
        Iterator<WebSite> it=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), SWBContext.listWebSites());
        while(it.hasNext())
        {
            WebSite site=it.next();
            if(!site.isDeleted())
            {
                Iterator<Resource> itp = site.listResources();
                while(itp.hasNext())
                {
                    Resource por = itp.next();
                    if(por.isDeleted())
                    {
                        addSemanticObject(arr, por.getSemanticObject(),false,true,paramRequest);
                    }
                }
                Iterator<WebPage> itwp = site.listWebPages();
                while(itwp.hasNext())
                {
                    WebPage wp = itwp.next();
                    //if(wp.getId().equals("y")) wp.setDeleted(true);
                    if(wp.isDeleted())  //wp.isDeleted()
                    {
                        addSemanticObject(arr, wp.getSemanticObject(),false,true,paramRequest);
                    }
                }
            }
        }

        it=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), SWBContext.listWebSites());
        while(it.hasNext())
        {
            WebSite site=it.next();
            if(site.isDeleted())
            {
                addSemanticObject(arr, site.getSemanticObject(),false,true,paramRequest);
            }
        }
    }

    /**
     * Checks for herarquical nodes.
     * 
     * @param obj the obj
     * @return true, if successful
     * @throws JSONException the jSON exception
     */
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


    /**
     * Adds the herarquical nodes.
     * 
     * @param arr the arr
     * @param obj the obj
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addHerarquicalNodes(JSONArray arr, SemanticObject obj, SWBParamRequest paramRequest) throws JSONException
    {
        Iterator<SemanticObject> it=SWBComparator.sortSortableObject(obj.getSemanticClass().listHerarquicalNodes());
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(arr,node,obj,false, paramRequest);
        }
    }

    /**
     * Adds the herarquical node.
     * 
     * @param arr the arr
     * @param node the node
     * @param obj the obj
     * @param addChilds the add childs
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addHerarquicalNode(JSONArray arr, HerarquicalNode node, SemanticObject obj, boolean addChilds, SWBParamRequest paramRequest) throws JSONException
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        String pf=node.getPropertyFilter();
        JSONObject jobj=TreeUtils.getNode("HN|"+obj.getURI()+"|"+node.getURI(), node.getDisplayTitle(paramRequest.getUser().getLanguage()), "HerarquicalNode", node.getIconClass());
        arr.put(jobj);

        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);
        Iterator<SemanticObject> it=SWBObjectFilter.filter(SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(), obj.getModel().listInstancesOfClass(cls)),pf);

        //System.out.println("obj:"+obj.getId());
        //drop acceptance
        JSONArray dropacc=new JSONArray();
        jobj.putOpt("dropacc", dropacc);

        //Menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);
        String url=SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+cls.getEncodedURI()+"&sref="+obj.getEncodedURI();
        if(pf!=null)url+="&"+pf;
        menus.put(TreeUtils.getMenuItem("Agregar "+cls.getDisplayName(paramRequest.getUser().getLanguage()), "dijitEditorIcon dijitEditorIconCut",TreeUtils.getAction("showDialog", url,"Agregar "+cls.getDisplayName(paramRequest.getUser().getLanguage()))));
        dropacc.put(cls.getClassId());
        //Iterator<SemanticClass> it2=cls.listSubClasses();
        //while(it2.hasNext())
        //{
        //    SemanticClass scls=it2.next();
        //    menus.put(getMenuItem("Agregar "+scls.getDisplayName(paramRequest.getUser().getLanguage()), "dijitEditorIcon dijitEditorIconCut",getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+scls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),null)));
        //    dropacc.put(scls.getClassID());
        //}

        menus.put(TreeUtils.getMenuSeparator());
        menus.put(TreeUtils.getMenuItem("Recargar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getReloadAction()));

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
                        addSemanticObject(childs, so,false,paramRequest);
                    }
                }else
                {
                    addSemanticObject(childs, so,false,paramRequest);
                }
            }
        }else
        {
            if(it.hasNext())
            {
                jobj.put("hasChilds", "true");
                JSONArray events=new JSONArray();
                jobj.putOpt("events", events);
                events.put(TreeUtils.getEvent("onOpen", TreeUtils.getReloadAction()));
            }
        }
    }

    //TODO:Separar en una clase treeController
    /**
     * Adds the resource type.
     * 
     * @param arr the arr
     * @param obj the obj
     * @param addChilds the add childs
     * @param addDummy the add dummy
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addResourceType(JSONArray arr, SemanticObject obj, boolean addChilds, boolean addDummy, SWBParamRequest paramRequest) throws JSONException
    {
        boolean hasChilds=false;
        SemanticClass cls=obj.getSemanticClass();
        SemanticObject dispobj=cls.getDisplayObject();
        String type=cls.getClassId();
        ResourceType restype=(ResourceType)obj.createGenericInstance();

        //Active
        boolean active=false;
        SemanticProperty activeprop=cls.getProperty("active");
        if(activeprop!=null)
        {
            active=obj.getBooleanProperty(activeprop);
        }

        String icon=SWBContext.UTILS.getIconClass(obj);
        JSONObject jobj=TreeUtils.getNode(obj.getURI(), obj.getDisplayName(paramRequest.getUser().getLanguage()), type, icon);
        arr.put(jobj);

        //dragSupport
        if(dispobj!=null)
        {
            DisplayObject dp=(DisplayObject)dispobj.createGenericInstance();
            jobj.put("dragSupport", dp.isDragSupport());
            jobj.put("dropMatchLevel", dp.getDropMatchLevel());
        }

        //System.out.println("obj:"+obj.getId());
        //drop acceptance
        JSONArray dropacc=new JSONArray();
        jobj.putOpt("dropacc", dropacc);

        //menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);

        if(restype.getResourceMode()==2)
        {
            Iterator<SemanticProperty> pit=cls.listHerarquicalProperties();
            while(pit.hasNext())
            {
                SemanticProperty prop=pit.next();
                SemanticClass rcls=prop.getRangeClass();
                menus.put(TreeUtils.getMenuItem("Agregar "+rcls.getDisplayName(paramRequest.getUser().getLanguage()), "dijitEditorIcon dijitEditorIconCut",TreeUtils.getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+rcls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),"Agregar "+rcls.getDisplayName(paramRequest.getUser().getLanguage()))));
                dropacc.put(rcls.getClassId());
            }
            menus.put(TreeUtils.getMenuSeparator());
        }

        //Active
        if(activeprop!=null)
        {
            if(!active)
            {
                menus.put(TreeUtils.getMenuItem("Activar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
            }else
            {
                menus.put(TreeUtils.getMenuItem("Desactivar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
            }
        }

        menus.put(TreeUtils.getMenuItem("Editar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getNewTabAction()));
        if(!obj.instanceOf(Undeleteable.swb_Undeleteable) ||  (obj.instanceOf(Undeleteable.swb_Undeleteable) && obj.getBooleanProperty(Undeleteable.swb_undeleteable)==false))
        {
            menus.put(TreeUtils.getMenuItem("Eliminar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/delete.jsp?suri="+obj.getEncodedURI(),null)));
        }
        menus.put(TreeUtils.getMenuSeparator());

        User user=SWBContext.getSessionUser();
        boolean isfavo=user.hasFavorite(obj);
        if(!isfavo)
        {
            menus.put(TreeUtils.getMenuItem("Agregar a Favoritos", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
        }else
        {
            menus.put(TreeUtils.getMenuItem("Eliminar de Favoritos", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
        }
        menus.put(TreeUtils.getMenuItem("Recargar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getReloadAction()));

        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        events.put(TreeUtils.getEvent("onDblClick", TreeUtils.getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp", null)));
        events.put(TreeUtils.getEvent("onClick", TreeUtils.getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        hasChilds=hasHerarquicalNodes(obj);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(childs, obj,paramRequest);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(),it);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    boolean add=true;
                    if(ch.instanceOf(Resource.sclass))
                    {
                        Resource res=(Resource)ch.createGenericInstance();
                        if(res.getResourceSubType()!=null)add=false;
                    }
                    if(add)addSemanticObject(childs, ch,false,paramRequest);
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
                childs.put(TreeUtils.getDummy());
            } else {
                jobj.put("hasChilds", "true");
            }
            events.put(TreeUtils.getEvent("onOpen", TreeUtils.getReloadAction()));
        }
    }


    /**
     * Adds the semantic object.
     * 
     * @param arr the arr
     * @param obj the obj
     * @param addChilds the add childs
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, SWBParamRequest paramRequest) throws JSONException
    {
        addSemanticObject(arr, obj, addChilds, false,paramRequest);
    }

    /**
     * Adds the semantic object.
     * 
     * @param arr the arr
     * @param obj the obj
     * @param addChilds the add childs
     * @param addDummy the add dummy
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, boolean addDummy, SWBParamRequest paramRequest) throws JSONException
    {
        boolean hasChilds=false;
        SemanticClass cls=obj.getSemanticClass();
        SemanticObject dispobj=cls.getDisplayObject();
        String type=cls.getClassId();

        //TODO:validar treeController
        //System.out.println("type:"+type);
        if(cls.equals(ResourceType.sclass))
        {
            addResourceType(arr,obj,addChilds,addDummy,paramRequest);
            return;
        }
        //Active
        boolean active=false;
        SemanticProperty activeprop=cls.getProperty("active");
        if(activeprop!=null)
        {
            active=obj.getBooleanProperty(activeprop);
        }

        String icon=SWBContext.UTILS.getIconClass(obj);
        JSONObject jobj=TreeUtils.getNode(obj.getURI(), obj.getDisplayName(paramRequest.getUser().getLanguage()), type, icon);
        arr.put(jobj);

        //dragSupport
        if(dispobj!=null)
        {
            DisplayObject dp=(DisplayObject)dispobj.createGenericInstance();
            jobj.put("dragSupport", dp.isDragSupport());
            jobj.put("dropMatchLevel", dp.getDropMatchLevel());
        }

        //System.out.println("obj:"+obj.getId());
        //drop acceptance
        JSONArray dropacc=new JSONArray();
        jobj.putOpt("dropacc", dropacc);

        //menus
        JSONArray menus=new JSONArray();
        jobj.putOpt("menus", menus);

        //TODO:separar treeController
        if(!cls.equals(WebSite.sclass))
        {
            //menus creacion
            Iterator<SemanticProperty> pit=cls.listHerarquicalProperties();
            while(pit.hasNext())
            {
                SemanticProperty prop=pit.next();
                SemanticClass rcls=prop.getRangeClass();
                menus.put(TreeUtils.getMenuItem("Agregar "+rcls.getDisplayName(paramRequest.getUser().getLanguage()), "dijitEditorIcon dijitEditorIconCut",TreeUtils.getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+rcls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),"Agregar "+rcls.getDisplayName(paramRequest.getUser().getLanguage()))));
                dropacc.put(rcls.getClassId());
                //add subclasess
                Iterator<SemanticClass> it=rcls.listSubClasses();
                while(it.hasNext())
                {
                    SemanticClass scls=it.next();
                    menus.put(TreeUtils.getMenuItem("Agregar "+scls.getDisplayName(paramRequest.getUser().getLanguage()), "dijitEditorIcon dijitEditorIconCut",TreeUtils.getAction("showDialog", SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp?scls="+scls.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI(),"Agregar "+scls.getDisplayName(paramRequest.getUser().getLanguage()))));
                    dropacc.put(scls.getClassId());
                }
            }
        }

        if(menus.length()>0)
            menus.put(TreeUtils.getMenuSeparator());

        //Active
        if(activeprop!=null)
        {
            if(!active)
            {
                menus.put(TreeUtils.getMenuItem("Activar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
            }else
            {
                menus.put(TreeUtils.getMenuItem("Desactivar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/active.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
            }
        }

        //menu remove
        menus.put(TreeUtils.getMenuItem("Editar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getNewTabAction()));
        if(!obj.instanceOf(Undeleteable.swb_Undeleteable) ||  (obj.instanceOf(Undeleteable.swb_Undeleteable) && obj.getBooleanProperty(Undeleteable.swb_undeleteable)==false))
        {
            menus.put(TreeUtils.getMenuItem("Eliminar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/delete.jsp?suri="+obj.getEncodedURI(),null)));
        }
        menus.put(TreeUtils.getMenuSeparator());

        //menu favoritos
        User user=SWBContext.getSessionUser();
        boolean isfavo=user.hasFavorite(obj);
        if(!isfavo)
        {
            menus.put(TreeUtils.getMenuItem("Agregar a Favoritos", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=active",null)));
        }else
        {
            menus.put(TreeUtils.getMenuItem("Eliminar de Favoritos", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getAction("showStatusURL",SWBPlatform.getContextPath()+"/swbadmin/jsp/favorites.jsp?suri="+obj.getEncodedURI()+"&act=unactive",null)));
        }
        //menu recargar
        menus.put(TreeUtils.getMenuItem("Recargar", "dijitEditorIcon dijitEditorIconCut", TreeUtils.getReloadAction()));

        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        events.put(TreeUtils.getEvent("onDblClick", TreeUtils.getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp", null)));
        events.put(TreeUtils.getEvent("onClick", TreeUtils.getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        hasChilds=hasHerarquicalNodes(obj);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(childs, obj,paramRequest);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSemanticObjects(paramRequest.getUser().getLanguage(),it);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    addSemanticObject(childs, ch,false,paramRequest);
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
                childs.put(TreeUtils.getDummy());
            } else {
                jobj.put("hasChilds", "true");
            }
            events.put(TreeUtils.getEvent("onOpen", TreeUtils.getReloadAction()));
        }
    }

/**
 * *************************************************************************.
 * 
 * @param arr the arr
 * @param ont the ont
 * @param paramRequest the param request
 * @throws JSONException the jSON exception
 */

    public void addClasses(JSONArray arr, SemanticOntology ont, SWBParamRequest paramRequest)  throws JSONException
    {
        Iterator<OntClass> it=ont.getRDFOntModel().listHierarchyRootClasses();
        while(it.hasNext())
        {
            OntClass cls=it.next();
            addClass(arr, cls, ont);
        }
        addClass(arr, ont.getRDFOntModel().getOntClass(SemanticVocabulary.RDF_PROPERTY), ont);
        addClass(arr, ont.getRDFOntModel().getOntClass(SemanticVocabulary.OWL_CLASS), ont);
    }

    /**
     * Adds the class.
     * 
     * @param arr the arr
     * @param cls the cls
     * @param ont the ont
     * @throws JSONException the jSON exception
     */
    public void addClass(JSONArray arr, OntClass cls, SemanticOntology ont) throws JSONException
    {
        boolean base=SWBPlatform.JENA_UTIL.isInBaseModel(cls, ont.getRDFOntModel());
        String icon="swbIconClass";
        if(!base)icon+="U";
        JSONObject jobj=TreeUtils.getDNode(cls.getURI(), SWBPlatform.JENA_UTIL.getId(cls), "Class", icon);
        arr.put(jobj);

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        events.put(TreeUtils.getEvent("onDblClick", TreeUtils.getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp", null)));
        //events.put(getEvent("onClick", getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        Iterator<OntClass> it=cls.listSubClasses(true);
        while(it.hasNext())
        {
            OntClass ccls=it.next();
            addClass(childs, ccls,ont);
        }
    }

    /**
     * Adds the properties.
     * 
     * @param arr the arr
     * @param ont the ont
     * @param paramRequest the param request
     * @throws JSONException the jSON exception
     */
    public void addProperties(JSONArray arr, SemanticOntology ont, SWBParamRequest paramRequest)  throws JSONException
    {
        Iterator<OntProperty> it=ont.getRDFOntModel().listOntProperties();
        while(it.hasNext())
        {
            OntProperty prop=it.next();
            addProperty(arr, prop, ont);
        }
    }

    /**
     * Adds the property.
     * 
     * @param arr the arr
     * @param prop the prop
     * @param ont the ont
     * @throws JSONException the jSON exception
     */
    public void addProperty(JSONArray arr, OntProperty prop, SemanticOntology ont) throws JSONException
    {
        boolean base=SWBPlatform.JENA_UTIL.isInBaseModel(prop, ont.getRDFOntModel());
                //isInBaseModel(prop);
        String icon="swbIconClass";
        if(!base)icon+="U";
        JSONObject jobj=TreeUtils.getDNode(prop.getURI(), SWBPlatform.JENA_UTIL.getId(prop), "Property", icon);
        arr.put(jobj);

        //hijos
        JSONArray childs=new JSONArray();
        jobj.putOpt("children", childs);

        //eventos
        JSONArray events=new JSONArray();
        jobj.putOpt("events", events);
        events.put(TreeUtils.getEvent("onDblClick", TreeUtils.getAction("newTab", SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp", null)));
        //events.put(getEvent("onClick", getAction("getHtml", SWBPlatform.getContextPath()+"/swbadmin/jsp/viewProps.jsp?id="+obj.getEncodedURI(), "vprop")));

        Iterator it=prop.listSubProperties(true);
        while(it.hasNext())
        {
            OntProperty cprop=(OntProperty)it.next();
            addProperty(childs, cprop,ont);
        }
    }


}

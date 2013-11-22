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
package org.semanticwb.portal.admin.resources;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.*;
import java.util.*;
import java.util.ArrayList;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

import org.semanticwb.model.AdminFilter;
import org.semanticwb.model.FilterableClass;
import org.semanticwb.model.FilterableNode;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.HerarquicalNode;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SWBObjectFilter;
import org.semanticwb.platform.SemanticClass;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeExt;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/** Recurso de administraci�n de WebBuilder que permite agregar filtros, editarlos,
 * actualizarlos o eliminarlos seg�n sea el caso.
 *
 * Resource of administration of WebBuilder that allows to add filters, to publish
 * them, to update them or to eliminate them according to is the case.
 * @author Victor Lorenzana
 */
public class SWBAFilters extends SWBATree {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAFilters.class);
    
    /** The Constant pathValids. */
    static final String[] pathValids = {"getSemanticObject","getGlobal", "getTemplates", "getServer", "getResources", "getResourceTypes", "getSysResources", "getTopic", "getTemplateGroup", "getUserRep", "getRules", "getPFlows", "getLanguages", "getDevices", "getMDTables", "getDnss", "getTopicMap", "getUserReps", "getCamps", "getCamp", "getCntResources","getSemanticClass"};
    
    /** The Constant namevalids. */
    static final String[] namevalids = {"node", "config", "icons", "icon", "res", "events", "willExpand", "Template"};
    
    /** The hmclass. */
    private HashMap hmclass = null;
    
    /** The jsondom. */
    private Document jsondom = null;

    /**
     * Creates a new instance of WBAFilters.
     */
    public SWBAFilters() {
    }

    /**
     * Gets the locale string.
     * 
     * @param key the key
     * @param lang the lang
     * @return the locale string
     */
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
        return ret;
    }

    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {        
        if (paramRequest.getMode().equals("gateway")) {
            doGateway(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.SWBATree#getDocument(org.semanticwb.model.User, org.w3c.dom.Document, java.lang.String)
     */
    @Override
    public Document getDocument(User user, Document src, String act)
    {        
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);

            String cmd = null;
            String id = null;
            int ind = act.indexOf('.');
            if (ind > 0) {
                cmd = act.substring(0, ind);
                id = act.substring(ind + 1);            
            } else {
                cmd = act;
            }            
            if (cmd.equals("getServer")) {
                addServer(user, res);
            } 
            else if (cmd.equals("getDirectories")) {
                getDirectories(user, res,src);
            }
            else if (cmd.equals("getGlobal")) {
                addGlobal(user, res, PARCIAL_ACCESS);
            } else if (cmd.equals("getTopicMap")) {
                WebSite tm = SWBContext.getWebSite(id);
                addTopicMap(user, tm, res, PARCIAL_ACCESS);
            } else if (cmd.equals("getTopic")) {
                String tmid = id.substring(0, id.indexOf('.'));
                String tpid = id.substring(id.indexOf('.') + 1);
                WebPage tp = SWBContext.getWebSite(tmid).getWebPage(tpid);
                addTopic(user, tp, res);
            } else if (cmd.equals("getSemanticObject")) 
            {                
                if(id.startsWith("HN|"))
                {
                    StringTokenizer st=new StringTokenizer(id,"|");
                    st.nextToken();
                    String ouri=st.nextToken();
                    String nuri=st.nextToken();                    
                    if(ouri!=null && nuri!=null)
                    {
                        SemanticObject obj=SemanticObject.createSemanticObject(ouri);
                        SemanticObject nobj=SemanticObject.createSemanticObject(nuri);
                        HerarquicalNode node=new HerarquicalNode(nobj);
                        addHerarquicalNodeFilter(user,node,obj,res);
                    }
                }
                else
                {
                    SemanticObject obj=SemanticObject.createSemanticObject(id);
                    Element node=addSemanticObjectFilter(user, obj, res, null);
                    
                    Iterator<SemanticObject> it=obj.listHerarquicalChilds();

                    Iterator<SemanticObject> it2=SWBComparator.sortSemanticObjects(user.getLanguage(),it);
                    while(it2.hasNext())
                    {
                        SemanticObject ch=it2.next();
                        //String icon=SWBContext.UTILS.getIconClass(ch);
                        Element childElement = addNode("node", ch.getURI(), ch.getDisplayName(user.getLanguage()), node);
                        childElement.setAttribute("reload", "getSemanticObject." + ch.getURI());
                        //childElement.setAttribute("icon", icon);
                        Iterator<SemanticObject> childs=ch.listHerarquicalChilds();
                        if(childs.hasNext())
                        {
                            Element events = addNode("events", "events", "Events", childElement);
                            Element event = addNode("willExpand", "willExpand", "WillExpand", events);
                            event.setAttribute("action", "reload");
                        }
                    }
                }
                
            } else if (cmd.equals("getSemanticClass")) 
            {
                SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(id);                
                addSemanticClass(user, scls, res, true);
            } else
            {
                boolean ret = false;
                Iterator itex = ext.iterator();
                while (itex.hasNext()) {
                    SWBTreeExt e = (SWBTreeExt) itex.next();
                    ret = e.executeCommand(user, res, cmd, id);
                    if (ret) {
                        break;
                    }
                }
                if (!ret) {
                    return getError(2);
                }
            }
        } catch (Exception e) {
            log.error(e);
            return getError(3);
        }
        //RevisaNodo(dom.getFirstChild());
        return dom;
    }


    /**
     * Adds the herarquical node filter.
     * 
     * @param user the user
     * @param node the node
     * @param obj the obj
     * @param ele the ele
     */
    public void addHerarquicalNodeFilter(User user, HerarquicalNode node, SemanticObject obj, Element ele)
    {        
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        String pf=node.getPropertyFilter();

        Element jobj = addNode("node", node.getURI(), node.getDisplayTitle(user.getLanguage()), ele);
        jobj.setAttribute("reload", "getSemanticObject." + "HN|"+obj.getURI()+"|"+node.getURI());
        jobj.setAttribute("icon", node.getIconClass());       

        if(cls.isSubClass(FilterableNode.swb_FilterableNode))
        {
            Iterator<SemanticObject> it=SWBObjectFilter.filter(SWBComparator.sortSemanticObjects(user.getLanguage(), obj.getModel().listInstancesOfClass(cls)),pf);
           
                while(it.hasNext())
                {
                    SemanticObject so=it.next();
                    try
                    {
                        addSemanticObjectFilter(user,so,jobj,null);
                    }
                    catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            
        }
    }
    /**
     * Gets the menus.
     * 
     * @param map the map
     * @param etopic the etopic
     * @param root the root
     * @param user the user
     * @return the menus
     */
    public void getMenus(WebSite map, Element etopic, WebPage root, User user)
    {        

        if("WBAd_mnu_PopUp".equals(root.getId()))
        {            
            getSubMenus(map, etopic, root, user);
        }
        else
        {
            Iterator<WebPage> childs = root.listChilds(user.getLanguage(),true,false,false,null); //getSortChild();

            while (childs.hasNext()) {
                WebPage topic = childs.next();                
                if (user.haveAccess(topic)&&topic.isActive()) {
                    Element etp = addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), etopic);
                    etp.setAttribute("topicmap", map.getId());

                    //TODO: AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);

                    boolean canModify = true; //AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);
                    etp.setAttribute("canModify", String.valueOf(canModify));
                    etp.setAttribute("reload", "getTopic." + map.getId() + "." + topic.getId());
                    etp.setAttribute("icon", "hijov");

                    getMenus(map, etp, topic, user);
                }
            }
        }
    }

    /**
     * Gets the menus.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the menus
     * @return
     */
    public Document getMenus(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        WebSite map = SWBContext.getAdminWebSite();
        Document docres = null;
        try {
            docres = SWBUtils.XML.getNewDocument();
            Element res = docres.createElement("res");
            docres.appendChild(res);
            WebPage topic = map.getWebPage("WBAd_Menus");
            if (user.haveAccess(topic)&&topic.isActive()) {
                Element etopic = addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), res);
                etopic.setAttribute("topicmap", map.getId());

                //TODO: AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);

                boolean canModify = true; //AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);
                etopic.setAttribute("canModify", String.valueOf(canModify));
                etopic.setAttribute("reload", "getTopic." + map.getId() + "." + topic.getId());
                etopic.setAttribute("icon", "hijov");

                getMenus(map, etopic, topic, user);

            }
        } catch (Exception e) {            
            log.error(e);
        }
        return docres;
    }

    /**
     * Gets the sub menus.
     * 
     * @param map the map
     * @param etopic the etopic
     * @param root the root
     * @param user the user
     * @return the sub menus
     */
    public void getSubMenus(WebSite map, Element etopic, WebPage root, User user)
    {
        
        String lang = user.getLanguage();
        Iterator<SemanticClass> it=FilterableClass.swb_FilterableClass.listSubClasses(true);
        while(it.hasNext())
        {
            SemanticClass cls2 = (SemanticClass)it.next();
            {
                addSemanticClass(user, cls2, etopic, true);                
            }
        }
    }

    /**
     * Gets the elements.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the elements
     * @return
     */
    public Document getElements(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {

        WebSite map = SWBContext.getAdminWebSite();
        Document docres = null;
        try {
            docres = SWBUtils.XML.getNewDocument();
            Element res = docres.createElement("res");
            docres.appendChild(res);
            WebPage topic = map.getWebPage("ObjectBehavior");
            if (user.haveAccess(topic)&&topic.isActive()) {
                Element etopic = this.addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), res);
                etopic.setAttribute("topicmap", map.getId());
                etopic.setAttribute("reload", "getTopicMap." + map.getId());
                etopic.setAttribute("access", "2"); // todo:
                boolean canModify = true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                etopic.setAttribute("canModify", String.valueOf(canModify));
                etopic.setAttribute("reload", "getTopic." + map.getId() + "." + topic.getId());
                etopic.setAttribute("icon", "folder");
                getMenus(map, etopic, topic, user); // carga la lista de comportamientos definidos en el sitio de administración
            }
        } catch (Exception e) {
            log.error(e);
        }
        return docres;
    }

    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    @Override
    public Document initTree(User user, Document src)
    {        
        Document doc = initTree(user, src, false);        
        return doc;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.SWBATree#initTree(org.semanticwb.model.User, org.w3c.dom.Document, boolean)
     */
    @Override
    public Document initTree(User user, Document src, boolean isFilter)
    {        
        Document dom = null;
        try {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            //config
            Element config = addNode("config", "config", "Config", res);

            Element icons = addNode("icons", "icons", "Icons", config);

            /*Element icon = addNode("icon", "sitev", "Site", icons);
            icon.setAttribute("path", "images/icon-sitioa.gif");
            icon = addNode("icon", "siter", "Site", icons);
            icon.setAttribute("path", "images/icon-sitior.gif");
            icon = addNode("icon", "hijor", "Topic", icons);
            icon.setAttribute("path", "images/icon-pagwebr.gif");
            icon = addNode("icon", "hijov", "Topic", icons);
            icon.setAttribute("path", "images/icon-pagweba.gif"); //icon-foldera.gif
            //icon.setAttribute("path","images/icon-foldera.gif");
            icon = addNode("icon", "homer", "Topic", icons);
            icon.setAttribute("path", "images/i_home_rojo.gif");
            icon = addNode("icon", "homev", "Topic", icons);
            icon.setAttribute("path", "images/i_home_verde.gif");
            icon = addNode("icon", "virtual", "Topic", icons);
            icon.setAttribute("path", "images/ico_virtual.gif");

            icon = addNode("icon", "folder", "Folder", icons);
            icon.setAttribute("path", "images/icon-foldera.gif");
            icon = addNode("icon", "root", "Root", icons);
            icon.setAttribute("path", "images/icon-foldera.gif");
            icon = addNode("icon", "menu", "Menu", icons);
            icon.setAttribute("path", "images/icon-pagweba.gif");//
            icon = addNode("icon", "global", "Global", icons);
            icon.setAttribute("path", "images/icon-servera.gif");
            ///////
            icon = addNode("icon", "devices", "Devices", icons);
            icon.setAttribute("path", "images/f_dispositivos.gif");
            icon = addNode("icon", "device", "Device", icons);
            icon.setAttribute("path", "images/i_dispositivo.gif");
            icon = addNode("icon", "dnss", "DNS", icons);
            icon.setAttribute("path", "images/f_dns.gif");
            icon = addNode("icon", "dns", "DNS", icons);
            icon.setAttribute("path", "images/i_dns.gif");
            icon = addNode("icon", "resources", "Resources", icons);
            icon.setAttribute("path", "images/f_estrategias.gif");
            icon = addNode("icon", "resourcetype", "ResourceType", icons);
            icon.setAttribute("path", "images/f_resourcetype.gif");
            icon = addNode("icon", "sysresources", "SysResources", icons);
            icon.setAttribute("path", "images/f_sistema.gif");
            icon = addNode("icon", "resourcer", "Resource", icons);
            icon.setAttribute("path", "images/i_recurso_rojo.gif");
            icon = addNode("icon", "resourcev", "Resource", icons);
            icon.setAttribute("path", "images/i_recurso_verde.gif");

            ////////////////////

            icon = addNode("icon", "flows", "Flows", icons);
            icon.setAttribute("path", "images/f_flujos.gif");
            icon = addNode("icon", "flow", "Flow", icons);
            icon.setAttribute("path", "images/i_flujo.gif");

            /////////////////

            icon = addNode("icon", "languages", "Languages", icons);
            icon.setAttribute("path", "images/f_idioma.gif");
            icon = addNode("icon", "language", "Language", icons);
            icon.setAttribute("path", "images/i_idioma.gif");
            icon = addNode("icon", "metadatas", "Metadatas", icons);
            icon.setAttribute("path", "images/f_metadatos.gif");
            icon = addNode("icon", "metadata", "Metadata", icons);
            icon.setAttribute("path", "images/i_metadata.gif");
            icon = addNode("icon", "camps", "Camps", icons);
            icon.setAttribute("path", "images/f_camp.gif");
            icon = addNode("icon", "campv", "Camp", icons);
            icon.setAttribute("path", "images/i_camp.gif");
            icon = addNode("icon", "campr", "Camp", icons);
            icon.setAttribute("path", "images/i_camp_r.gif");
            icon = addNode("icon", "templates", "Templates", icons);
            icon.setAttribute("path", "images/f_plantillas.gif");
            icon = addNode("icon", "templater", "Template", icons);
            icon.setAttribute("path", "images/i_plantilla_rojo.gif");
            icon = addNode("icon", "templatev", "Template", icons);
            icon.setAttribute("path", "images/i_plantilla_verde.gif");
            icon = addNode("icon", "rules", "Rules", icons);
            icon.setAttribute("path", "images/f_reglas.gif");
            icon = addNode("icon", "rule", "Rule", icons);
            icon.setAttribute("path", "images/i_regla.gif");
            icon = addNode("icon", "userreps", "UserReps", icons);
            icon.setAttribute("path", "images/f_usuarios.gif");
            icon = addNode("icon", "userrep", "UserRep", icons);
            icon.setAttribute("path", "images/i_repositoriousuarios.gif");
            icon = addNode("icon", "role", "Role", icons);
            icon.setAttribute("path", "images/i_rol.gif");

            ///////////////////////////

            //menus
            icon = addNode("icon", "trans", "Transparent", icons);
            icon.setAttribute("path", "images/trans.gif");

            //////////////////

            icon = addNode("icon", "refresh", "Refresh", icons);
            icon.setAttribute("path", "images/refresh.gif");
            icon = addNode("icon", "edit", "Edit", icons);
            icon.setAttribute("path", "images/edit.gif");
            icon = addNode("icon", "remove", "Remove", icons);
            icon.setAttribute("path", "images/remove.gif");
            icon = addNode("icon", "add", "Add", icons);
            icon.setAttribute("path", "images/add.gif");
            icon = addNode("icon", "active", "Active", icons);
            icon.setAttribute("path", "images/active.gif");
            icon = addNode("icon", "unactive", "Unactive", icons);
            icon.setAttribute("path", "images/unactive.gif");
            icon = addNode("icon", "trash", "Trash", icons);
            icon.setAttribute("path", "images/papelera.gif");
            icon = addNode("icon", "catalog", "Catalog", icons);
            icon.setAttribute("path", "images/catalogo.gif");//icon-pagweba.gif*/
            
            Set<SemanticObject> nodesFilter=new HashSet<SemanticObject>();




            NodeList ids=src.getElementsByTagName("id");
            if(ids.getLength()==1)
            {
                String id=((Element)ids.item(0)).getTextContent();                
                String tm=((Element)src.getElementsByTagName("tm").item(0)).getTextContent();
                UserRepository site=UserRepository.ClassMgr.getUserRepository(tm);
                if(site!=null && id!=null)
                {
                    nodesFilter=getNodesInFilter(id,site);
                }
            }




            Iterator it = ext.iterator();
            while (it.hasNext()) {
                SWBTreeExt e = (SWBTreeExt) it.next();
                e.initTree(user, res, isFilter);
            }

            addServerFilter(user, res, isFilter,getAllNodes(nodesFilter));


        } catch (Exception e) {
            log.error(e);
            return getError(3);
        }
        return dom;
    }
    
    /**
     * Gets the all nodes.
     * 
     * @param nodesFilter the nodes filter
     * @return the all nodes
     */
    private Set<String> getAllNodes(Set<SemanticObject> nodesFilter)
    {
        Set<String> getAllNodes=new HashSet<String>();
        for(SemanticObject obj : nodesFilter)
        {
            Iterator<SemanticObject> parents=obj.listHerarquicalParents();
            while(parents.hasNext())
            {
                SemanticObject parent=parents.next();                
                getAllNodes.add(parent.getURI());
                HashSet<SemanticObject> parentToFind=new HashSet<SemanticObject>();
                parentToFind.add(parent);
                Set<String> newparents=getAllNodes(parentToFind);
                if(!newparents.isEmpty())
                {
                    getAllNodes.addAll(newparents);
                }

            }
        }
        return getAllNodes;
    }
    
    /**
     * Gets the nodes in filter.
     * 
     * @param id the id
     * @param map the map
     * @return the nodes in filter
     */
    private Set<SemanticObject> getNodesInFilter(String id,UserRepository map)
    {
        Set<SemanticObject> getNodesInFilter=new HashSet<SemanticObject>();
        AdminFilter admfilter = AdminFilter.ClassMgr.getAdminFilter(id, map);
        if(admfilter!=null && admfilter.getXml()!=null)
        {
            Document exmlfilter = SWBUtils.XML.xmlToDom(admfilter.getXml());
            if(exmlfilter!=null)
            {
                NodeList nodes=exmlfilter.getElementsByTagName("sites");
                for(int i=0;i<nodes.getLength();i++)
                {
                    Element sites=(Element)nodes.item(i);
                    NodeList nodesFilter=sites.getElementsByTagName("node");
                    for(int j=0;j<nodesFilter.getLength();j++)
                    {
                        Element obj=(Element)nodesFilter.item(j);
                        String idObj=obj.getAttribute("id");
                        if(idObj!=null)
                        {
                            SemanticObject objFilter=SemanticObject.createSemanticObject(idObj);
                            if(objFilter!=null)
                            {
                                getNodesInFilter.add(objFilter);
                            }
                        }

                    }
                }
            }
        }
        return getNodesInFilter;
    }
    /**
     * Inits the tree filter.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    @Override
    public Document initTreeFilter(User user, Document src)
    {        
        Document doc = initTree(user, src);        
        return doc;
    }

    /**
     * Checks if is name valid.
     * 
     * @param e the e
     * @return true, if is name valid
     * @return
     */
    public boolean isNameValid(Element e)
    {

        for (int i = 0; i < namevalids.length; i++) {
            if (e.getNodeName().equals(namevalids[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Revisa nodo.
     * 
     * @param ele the ele
     */
    public void RevisaNodo(Node ele) {
        ArrayList<Node> vnodes = new ArrayList<Node>();
        NodeList nodes = ele.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            vnodes.add(nodes.item(i));
        }
        for (int i = 0; i < vnodes.size(); i++) {
            if (vnodes.get(i) instanceof Element) {
                Element e = (Element) vnodes.get(i);
                if (!isNameValid(e) || !isValid(e.getAttribute("reload"))) {
                    ele.removeChild((Node) vnodes.get(i));
                } else {
                    RevisaNodo(e);
                }
            } else {
                RevisaNodo((Node) vnodes.get(i));
            }
        }
    }

    /**
     * Checks if is valid.
     * 
     * @param path the path
     * @return true, if is valid
     * @return
     */
    public boolean isValid(String path) {
        if (path == null) {

            return true;
        }
        StringTokenizer st = new StringTokenizer(path, ".");
        if (st.countTokens() > 0) {
            String pathinit = st.nextToken();
            for (int i = 0; i < pathValids.length; i++) {
                if (pathinit.equals(pathValids[i])) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * Do gateway.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {        
        PrintWriter out = response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req")) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0) {
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
        }

        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret = "";
        
        try {
            Document res = null;
            if (cmd.equals("update")) {
                res = updateFilter(cmd, dom, paramRequest.getUser(), request, response);
            } else if (cmd.equals("getElements")) {
                res = getElements(cmd, dom, paramRequest.getUser(), request, response);
            } else if (cmd.equals("getMenus")) {
                res = getMenus(cmd, dom, paramRequest.getUser(), request, response);
            } else if (cmd.equals("getFilter")) {
                res = getFilter(cmd, dom, paramRequest.getUser(), request, response);
            } else {
                res = getService(cmd, dom, paramRequest.getUser(), request, response);
            }
            if (res == null) {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else {
                ret = SWBUtils.XML.domToXml(res, true);
            }

        } catch (Exception e) {
            log.error(e);
        }
        out.print(new String(ret.getBytes()));

    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.SWBATree#getService(java.lang.String, org.w3c.dom.Document, org.semanticwb.model.User, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {        
        if (cmd.equals("initTree")) {
            return initTree(user, src);
        } else if (cmd.equals("initTreeFilter")) {
            return initTreeFilter(user, src);
        } else if (cmd.startsWith("getPath.")) {
            return getPath(user, src, cmd.substring("getPath.".length()));
        } else {
            return getDocument(user, src, cmd);
        }
    }

    /**
     * Adds the.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the document
     * @return
     */
    public Document add(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        //SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        Document doc = null;
        try {
            doc = SWBUtils.XML.getNewDocument();
            Element res = doc.createElement("res");
            doc.appendChild(res);
            if (src.getElementsByTagName("filter").getLength() > 0) {
                Element efilter = (Element) src.getElementsByTagName("filter").item(0);
                String description = "";
                if (efilter.getElementsByTagName("description").getLength() > 0) {
                    Element edescription = (Element) efilter.getElementsByTagName("description").item(0);
                    org.w3c.dom.Text etext = (org.w3c.dom.Text) edescription.getFirstChild();
                    description = etext.getNodeValue();
                }
                String name = efilter.getAttribute("name");
                UserRepository aws = SWBContext.getAdminRepository();
                AdminFilter filter = AdminFilter.ClassMgr.createAdminFilter(aws);
                filter.setTitle(name);
                filter.setDescription(description);

                Document xmlfilter = SWBUtils.XML.getNewDocument();
                Element newnode = (Element) xmlfilter.importNode(efilter, true);
                xmlfilter.appendChild(newnode);
                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                try {
                    //filter.create();
                    newnode.setAttribute("id", String.valueOf(filter.getId()));
                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                    //filter.update();
                    addElement("filter", String.valueOf(filter.getId()), res);
                    return doc;
                } catch (Exception afe) {                    
                    addElement("err", afe.getMessage(), res);
                    log.error(afe);
                }
            } else {
                addElement("err", "The element filter was not found", res);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return doc;
    }

    /**
     * Update.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the document
     * @return
     */
    public Document update(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response) {
        UserRepository aws = SWBContext.getAdminRepository();
        Document doc = null;
        try {
            doc = SWBUtils.XML.getNewDocument();
            Element res = doc.createElement("res");
            doc.appendChild(res);
            if (src.getElementsByTagName("filter").getLength() > 0) {
                Element efilter = (Element) src.getElementsByTagName("filter").item(0);
//                String description = "";
//                if (efilter.getElementsByTagName("description").getLength() > 0) {
//                    Element edescription = (Element) efilter.getElementsByTagName("description").item(0);
//                    org.w3c.dom.Text etext = (org.w3c.dom.Text) edescription.getFirstChild();
//                    description = etext.getNodeValue();
//                }
//                String name = efilter.getAttribute("name");
                //AdminFilter filter=AdminFilter.getAdminFilter(efilter.getAttribute("id"),efilter.getAttribute("topicmap"));
                AdminFilter filter = AdminFilter.ClassMgr.getAdminFilter(efilter.getAttribute("id"), aws);
//                filter.setTitle(name);
//                filter.setDescription(description);
                //filter.setTopicMapId(efilter.getAttribute("topicmap"));
                Document xmlfilter = SWBUtils.XML.getNewDocument();

                Element newnode = (Element) xmlfilter.importNode(efilter, true);
                xmlfilter.appendChild(newnode);

                
                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                try {
                    newnode.setAttribute("id", String.valueOf(filter.getId()));
                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                    filter.update();
                    addElement("filter", String.valueOf(filter.getId()), res);
                } catch (Exception afe) {
                    
                    addElement("err", afe.getMessage(), res);
                    log.error(afe);
                }
            } else {
                addElement("err", "The element filter was not found", res);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return doc;
    }
    
    /**
     * Gets the path.
     * 
     * @param obj the obj
     * @return the path
     */
    private String getPath(SemanticObject obj)
    {
        String getPath="";
        Iterator<SemanticObject> parents=obj.listHerarquicalParents();
        if(parents.hasNext())
        {
            SemanticObject parent=parents.next();
            String pathnew=getPath(parent);
            if(pathnew.equals(""))
            {
                getPath = parent.getURI();
            }
            else
            {
                getPath = pathnew + "|" + parent.getURI();
            }
        }
        return getPath;

    }
    /**
     * Gets the filter.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the filter
     * @return
     */
    public Document getFilter(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {        

        UserRepository map = SWBContext.getAdminRepository();
        Document docres = null;
        try {
            docres = SWBUtils.XML.getNewDocument();
            Element res = docres.createElement("res");
            docres.appendChild(res);
            if (src.getElementsByTagName("id").getLength() > 0) {
                Element eid = (Element) src.getElementsByTagName("id").item(0);
                org.w3c.dom.Text etext = (org.w3c.dom.Text) eid.getFirstChild();
                String id = etext.getNodeValue();
                AdminFilter filter = AdminFilter.ClassMgr.getAdminFilter(id, map);
                Document exmlfilter = SWBUtils.XML.xmlToDom(filter.getXml());
                if(exmlfilter!=null)
                {
                    Node node = docres.importNode(exmlfilter.getFirstChild(), true);
                    res.appendChild(node);
                    NodeList nodes = docres.getElementsByTagName("node");
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element enode = (Element) nodes.item(i);
                        String topicid = enode.getAttribute("id");
                        String path = topicid;
                        //String topicmap = enode.getAttribute("topicmap");

                        SemanticObject obj=SemanticObject.createSemanticObject(topicid);
                        if(obj!=null)
                        {
                            String newpath=getPath(obj);
                            if(!newpath.equals(""))
                            {
                                path=getPath(obj)+"|"+path;
                            }
                        }
                        

                        /*SemanticObject objModel=SemanticObject.createSemanticObject(topicmap);
                        if(objModel!=null)
                        {
                            GenericObject gomodel=objModel.createGenericInstance();
                            if(gomodel instanceof SWBModel)
                            {
                                SemanticObject obj=SemanticObject.createSemanticObject(id);
                                Iter obj.listHerarquicalParents();
                            }
                            
                        }*/
                        /*
                        WebSite topicMap = SWBContext.getWebSite(topicmap);
                        if (topicMap != null) {
                            WebPage topic = topicMap.getWebPage(topicid);
                            if (topic != null) {
                                while (topic.getParent() != null) {
                                    path = topic.getParent().getId() + "|" + path;
                                    topic = topic.getParent();
                                }
                            }
                        }*/


                        enode.setAttribute("path", path);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return docres;
    }

    /**
     * Add or update the filter resource configuration.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return return an updated dom document
     */
    public Document updateFilter(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        if (src.getElementsByTagName("filter").getLength() > 0) {
            Element efilter = (Element) src.getElementsByTagName("filter").item(0);
            if (efilter.getAttribute("id") == null || efilter.getAttribute("id").equals("")) {
                return add(cmd, src, user, request, response);
            } else {
                return update(cmd, src, user, request, response);
            }
        }
        return null;
    }

    /**
     * User View of the SWBAFilters Resource; it shows a resource filter configuration,
     * configure in wich webpage it shows, in the Semantic WebBuilder application.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException, a Resource Exc
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        UserRepository map = SWBContext.getAdminRepository();
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        
        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dijit.layout.SplitContainer\");");
        out.println("  dojo.require(\"dijit.layout.ContentPane\");");
        out.println("</script>");

        String act = "view";
        if (request.getParameter("act") != null) {
            act = request.getParameter("act");
        }

        AdminFilter admfilter = null;
        String id=null;
        id = request.getParameter("id");
        String suri = request.getParameter("suri");
        if(null!=suri){
            act="edit";
            GenericObject go = SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if(go instanceof AdminFilter){
                admfilter = (AdminFilter)go;
                id=admfilter.getId();
            }
        }

        if (act.equals("remove") && id != null) {
            //  TODO:
            // Borrar filtros aplicados a los usuarios
            //WebSite mapadmin=SWBContext.getAdminWebSite();
//            UserRepository repository=mapadmin.getUserRepository();
//            Iterator<User> users=repository.listUsers();
//            while(users.hasNext())
//            {
//                User recuser=users.next();
//
//            }
            
            AdminFilter filter = AdminFilter.ClassMgr.getAdminFilter(id, map);
            filter.remove();
            act = "view";
        } else if (act.equals("add")) {

            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<div class=\"applet\">");
            out.println("<applet id=\"editfilter\" name=\"editfilter\" code=\"applets.filters.EditFilter.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplEditFilters.jar, swbadmin/lib/SWBAplCommons.jar\" WIDTH=\"100%\" HEIGHT=\"450\">");
            out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
            out.println("<param name =\"cgipath\" value=\""+url+"\">");
            out.println("<param name =\"locale\" value=\""+user.getLanguage()+"\">");
            out.println("<param name =\"tm\" value=\""+map.getId()+"\">");
            url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);
            out.println("<param name =\"location\" value=\""+url+"\">");
            out.println("</applet>");
            out.println("</div>");
            out.println("</fieldset>");
//            out.println("<fieldset>");
//            SWBResourceURL urlb = paramRequest.getRenderUrl();
//            urlb.setParameter("act", "view");
//            out.println("<input type=\"button\" name=\"bckButton\" onclick=\"submitUrl('" + urlb + "',this); return false;\" value=\"" + paramRequest.getLocaleString("btnCancel") + "\">");
//            out.println("</fieldset>");
            out.println("</div>");
            out.println("\r\n<script>\r\n");
            out.println("\r\nfunction doView(){\r\n");
            url = paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);
            out.println("location='" + url + "';\r\n");
            out.println("\r\n}\r\n");
            out.println("</script>\r\n");
        } else if (act.equals("edit") && id != null) {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<div class=\"applet\">");
            out.println("<applet id=\"editfilter\" name=\"editfilter\" code=\"applets.filters.EditFilter.class\" codebase=\"" + SWBPlatform.getContextPath() + "/\" ARCHIVE=\"swbadmin/lib/SWBAplEditFilters.jar, swbadmin/lib/SWBAplCommons.jar\" WIDTH=\"100%\" HEIGHT=\"450\">");
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
            out.println("<param name =\"idfilter\" value=\"" + id + "\">");
            out.println("<param name =\"cgipath\" value=\"" + url + "\">");
            out.println("<param name =\"locale\" value=\"" + user.getLanguage() + "\">");
            out.println("<param name =\"tm\" value=\"" + map.getId() + "\">");
            url = paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);
            out.println("<param name =\"location\" value=\"" + url + "\">");
            out.println("</applet>");
            out.println("</div>");
            out.println("</fieldset>");
//            out.println("<fieldset>");
//            SWBResourceURL urlb = paramRequest.getRenderUrl();
//            urlb.setParameter("act", "view");
//            out.println("<input type=\"button\" name=\"bckButton\" onclick=\"submitUrl('" + urlb + "',this); return false;\" value=\"" + paramRequest.getLocaleString("btnCancel") + "\">");
//            out.println("</fieldset>");
            out.println("</div>");
            out.println("\r\n<script>\r\n");
            out.println("\r\nfunction doView(){\r\n");
            url = paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);
            out.println("location='" + url + "';\r\n");
            out.println("\r\n}\r\n");
            out.println("</script>\r\n");
        }
        if (act.equals("view")) {
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);
            //url.setCallMethod(url.Call_DIRECT);   
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table width=\"100%\" cellpadding=\"10\" cellspacing=\"0\">");
            out.println("<tr>");

            out.println("<th colspan=\"2\" align=\"center\">");
            out.println(paramRequest.getLocaleString("msgAction"));
            out.println("</th>");

            out.println("<th>");
            out.println(paramRequest.getLocaleString("msgIdentifier"));
            out.println("</th>");

            out.println("<th >");
            out.println(paramRequest.getLocaleString("msgFilter"));
            out.println("</th>");

            out.println("<th >");
            out.println(paramRequest.getLocaleString("msgDescription"));
            out.println("</th>");

            out.println("</tr>");

//            String rowColor="";
//            boolean cambiaColor = true;

            Iterator<AdminFilter> filters = AdminFilter.ClassMgr.listAdminFilters(map);
            while (filters.hasNext()) {
                AdminFilter filter = filters.next();

                out.println("<tr >");     //bgcolor=\""+rowColor+"\"

                out.println("<td  colspan=\"2\" align=\"center\">");

                SWBResourceURL urlRemove = paramRequest.getRenderUrl();
                urlRemove.setParameter("act", "remove");
                urlRemove.setParameter("id", filter.getId());
                out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("msgAlertShureRemoveFilter") + "?') ) submitUrl('" + urlRemove.toString() + "',this);return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgLinkRemove") + "\"></a>&nbsp;");

                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                urlEdit.setParameter("act", "edit");
                urlEdit.setParameter("id", filter.getId());
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + filter.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + filter.getDisplayTitle(user.getLanguage()) + "');return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgLinkEdit") + "\"></a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                //out.println("<a href=\"#\" onclick=\"submitUrl('" + urlEdit.toString() + "',this); return false;\" ><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgLinkEdit") + "\"></a>");

                out.println("</td>");

                out.println("<td >");
                out.println(filter.getId());
                out.println("</td>");

                out.println("<td >");
                out.println(filter.getDisplayTitle(user.getLanguage()));
                out.println("</td>");

                out.println("<td >");
                out.println(filter.getDisplayDescription(user.getLanguage())!=null?filter.getDisplayDescription(user.getLanguage()):"");
                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            Resource base = getResourceBase();
//            out.println("<form id=\"" + base.getId() + "/addAdminFilter\" action=\"" + url + "\">");
//            out.println("<button dojoType=\"dijit.form.Button\" name=\"op\" onclick=\"submitForm('" + getResourceBase().getId() + "/addAdminFilter'); return false;\">" + paramRequest.getLocaleString("msgBtnAdd") + "</button>");
//            //out.println("<input type=\"submit\" name=\"op\" value=\""+paramRequest.getLocaleString("msgBtnAdd")+"\">");
//            out.println("<input type=\"hidden\" name=\"act\" value=\"add\">");
//            out.println("</form>");
            String urlAddNew = SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp";
            urlAddNew+="?scls="+AdminFilter.sclass.getEncodedURI()+"&sref="+map.getEncodedURI()+"&reloadTab=true";
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"showDialog('" + urlAddNew + "',' "+AdminFilter.sclass.getDisplayName(user.getLanguage())+"'); reloadTab('"+base.getURI()+"'); return false;\">" + paramRequest.getLocaleString("msgBtnAdd") + "</button>");
            out.println("</fieldset>");
            out.println("</div>");
        }
    }


    /**
     * Get true if a Semantic objects have Herarquical Nodes.
     * 
     * @param obj the obj
     * @return boolean, true if semantic object have Herarquical nodes or False.
     */
    public boolean hasHerarquicalNodes(SemanticObject obj)
    {        
        boolean ret = false;
        Iterator<SemanticObject> it = obj.getSemanticClass().listHerarquicalNodes();
        if (it.hasNext()) {
            ret = true;
        }

        return ret;
    }

    /**
     * Gets the directories.
     * 
     * @param user the user
     * @param res the res
     * @param src the src
     * @return the directories
     */
    public void getDirectories(User user, Element res,Document src)
    {
        String path=SWBUtils.getApplicationPath();
        if(src.getElementsByTagName("path").getLength()>0)
        {
            Element epath=(Element)src.getElementsByTagName("path").item(0);
            Text etext=(Text)epath.getFirstChild();
            path=etext.getNodeValue();
            path=SWBUtils.getApplicationPath()+path;
            path=path.replace("//", "/");
        }
        File apppath=new File(path);
        if(apppath.isDirectory() && apppath.exists())
        {
            Element dir=addNode("dir", "", apppath.getName(), res);
            String startPath=new File(SWBUtils.getApplicationPath()).getAbsolutePath();
            path=apppath.getAbsolutePath().substring(startPath.length());
            path=path.replace('\\','/');
            path=path.replace("//", "/");
            dir.setAttribute("path",path);
            dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(apppath)));
            getDirectories(dir,apppath);
        }
    }

    /**
     * Checks for subdirectories.
     * 
     * @param fdir the fdir
     * @return true, if successful
     */
    public boolean hasSubdirectories(File fdir)
    {
        File[] dirs=fdir.listFiles();
        for(int i=0;i<dirs.length;i++)
        {
            File file=dirs[i];
            if(file.isDirectory())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the directories.
     *
     * @param edir the edir
     * @param fdir the fdir
     * @return the directories
     */
    public void getDirectories(Element edir,File fdir)
    {
        File[] dirs=fdir.listFiles();
        Arrays.sort(dirs, new FileComprator());
        for(int i=0;i<dirs.length;i++)
        {
            File file=dirs[i];
            if(file.isDirectory())
            {
                Element dir=addNode("dir", "", file.getName(), edir);
                String startPath=new File(SWBUtils.getApplicationPath()).getAbsolutePath();
                String path=file.getAbsolutePath().substring(startPath.length());
                path=path.replace("//","/");
                path=path.replace('\\','/');
                dir.setAttribute("path",path);
                dir.setAttribute("hasChild",String.valueOf(hasSubdirectories(file)));
            }
        }
    }



    /**
     * Adds the server filter.
     * 
     * @param user the user
     * @param res the res
     * @param isFilter the is filter
     * @param objfilters the objfilters
     */
    protected void addServerFilter(User user, Element res, boolean isFilter,Set<String> objfilters)
    {

        int access = 2;
        hmclass = new HashMap();
        //tree nodes
        Element root = addNode("node", "server", "Server", res);
        root.setAttribute("reload", "getServer");
        root.setAttribute("icon", "global");
        root.setAttribute("access", "" + access);



        //WebSites
        Iterator<WebSite> it = sortIterator(SWBContext.listWebSites());
        while (it.hasNext())
        {
            WebSite tm = it.next();
            if (!tm.isDeleted())
            {
                try
                {
                    addSemanticObjectFilter(user, tm.getSemanticObject(), root,objfilters);
                }
                catch(Exception e)
                {
                    log.error(e);
                }
            }
        }

        Iterator<UserRepository> it2 = SWBContext.listUserRepositories();
        while (it2.hasNext())
        {
            UserRepository tm = it2.next();
            if (tm.getParentWebSite()==null)
            {
                try
                {
                    addSemanticObjectFilter(user, tm.getSemanticObject(), root,objfilters);
                }
                catch(Exception e)
                {
                    log.error(e);
                }
            }
        }

        

    }
    /**
     * Add server to dom document.
     * 
     * @param user the user
     * @param res the res
     * @param isFilter the is filter
     */
    @Override
    protected void addServer(User user, Element res, boolean isFilter)
    {
        
        int access = 2; 
        hmclass = new HashMap();
        //tree nodes
        Element root = addNode("node", "server", "Server", res); //Servr por tma.getWebPage("WBAd_sys_Server").getDisplayName(user.getLanguage())
        root.setAttribute("reload", "getServer");
        root.setAttribute("icon", "global");
        root.setAttribute("access", "" + access);

        

        //WebSites
        Iterator<WebSite> it = sortIterator(SWBContext.listWebSites());
        while (it.hasNext())
        {
            //topicmap
            WebSite tm = it.next();
            if (!tm.isDeleted()) 
            {
                try
                {
                    addSemanticObject(user, tm.getSemanticObject(), root, false);
                }
                catch(Exception e)
                {
                    log.error(e);
                }
                //addTopicMap(user, tm, root, access, false, isFilter);
            }
        }

        Iterator<UserRepository> it2 = SWBContext.listUserRepositories();
        while (it2.hasNext())
        {
            UserRepository tm = it2.next();
            if (tm.getParentWebSite()==null)
            {
                try
                {
                    addSemanticObject(user, tm.getSemanticObject(), root, false);
                }
                catch(Exception e)
                {
                    log.error(e);
                }
            }
        }

//        Iterator itex = ext.iterator();
//        while (itex.hasNext()) {
//            SWBTreeExt e = (SWBTreeExt) itex.next();
//            e.addServer(user, root, isFilter);
//        }
    }



    /**
     * Adds the herarquical nodes.
     * 
     * @param user the user
     * @param obj the obj
     * @param ele the ele
     */
    public void addHerarquicalNodes(User user, SemanticObject obj, Element ele)
    {
        Iterator<SemanticObject> it=SWBComparator.sortSortableObject(obj.getSemanticClass().listHerarquicalNodes());
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(user,node,obj,ele,false);
        }
    }

    /**
     * Adds the herarquical node.
     * 
     * @param user the user
     * @param node the node
     * @param obj the obj
     * @param ele the ele
     * @param addChilds the add childs
     */
    public void addHerarquicalNode(User user, HerarquicalNode node, SemanticObject obj, Element ele, boolean addChilds)
    {
        addChilds=true;
        SemanticClass cls=null;
        if(node.getHClass()!=null)
        {
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        }
        String pf=node.getPropertyFilter();

        Element jobj = addNode("node", node.getURI(), node.getDisplayTitle(user.getLanguage()), ele);
        jobj.setAttribute("reload", "getSemanticObject." + "HN|"+obj.getURI()+"|"+node.getURI());
        jobj.setAttribute("icon", node.getIconClass());
        //jobj.setAttribute("icon", "homev");

        if(cls!=null && cls.isSubClass(FilterableNode.swb_FilterableNode))
        {
            Iterator<SemanticObject> it=SWBObjectFilter.filter(SWBComparator.sortSemanticObjects(user.getLanguage(), obj.getModel().listInstancesOfClass(cls)),pf);
            if(addChilds)
            {
                while(it.hasNext())
                {
                    SemanticObject so=it.next();
                    try
                    {
                        addSemanticObject(user,so,jobj,false);
                    }
                    catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }
        
        if(cls==null)
        {
            Iterator<HerarquicalNode> it=node.listHerarquicalNodes();
            while (it.hasNext()) {
                HerarquicalNode node2 = it.next();
                addHerarquicalNode(user,node2,obj,jobj,false);
            }
        }
    }
    
    /**
     * Gets the path.
     * 
     * @param child the child
     * @param parent the parent
     * @return the path
     */
    private List<SemanticObject> getPath(SemanticObject child,SemanticObject parent)
    {        
        ArrayList<SemanticObject> getPath=new ArrayList<SemanticObject>();        
        if(child.hasHerarquicalParents())
        {
            Iterator<SemanticObject> hps=child.listHerarquicalParents();

            while(hps.hasNext())
            {
                SemanticObject hp=hps.next();

                if(hp.getURI().equals(parent.getURI()))
                {

                    getPath.add(parent);
                }
                else
                {
                    List<SemanticObject> path=getPath(hp, parent);
                    if(path!=null && !path.isEmpty())
                    {                        
                        getPath.add(hp);
                        getPath.addAll(path);
                    }
                }
            }
        }
        
        return getPath;
    }   
    
    /**
     * Adds the semantic object filter.
     * 
     * @param user the user
     * @param obj the obj
     * @param node the node
     * @param nodesInFilter the nodes in filter
     * @return the element
     */
    protected Element addSemanticObjectFilter(User user, SemanticObject obj, Element node,Set<String> nodesInFilter)
    {

        Element events = null;
        Element event = null;                
        Element jobj = addNode("node", obj.getURI(), obj.getDisplayName(user.getLanguage()), node);
        jobj.setAttribute("reload", "getSemanticObject." + obj.getURI());                
        if(hasHerarquicalNodes(obj))
        {                        
            addHerarquicalNodes(user, obj, jobj);

        }
        if((nodesInFilter!=null && nodesInFilter.contains(obj.getURI())) || obj.instanceOf(WebSite.sclass))
        {            
            //Agrega todos los nodos hijos
            if(nodesInFilter!=null && nodesInFilter.contains(obj.getURI()))
            {                
                nodesInFilter.remove(obj.getURI());
            }

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            Iterator<SemanticObject> it2=SWBComparator.sortSemanticObjects(user.getLanguage(),it);
            while(it2.hasNext())
            {
                SemanticObject ch=it2.next();
                if(ch.instanceOf(FilterableNode.swb_FilterableNode))
                {
                    try
                    {
                        addSemanticObjectFilter(user,ch,jobj,nodesInFilter);
                    }
                    catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }
        else
        {
           
            events = addNode("events", "events", "Events", jobj);
            event = addNode("willExpand", "willExpand", "WillExpand", events);
            event.setAttribute("action", "reload");
            
        }
        return jobj;

    }
     /**
      * Add web site to dom document.
      * 
      * @param user the user
      * @param obj the obj
      * @param node the node
      * @param addChilds the add childs
      */
    protected void addSemanticObject(User user, SemanticObject obj, Element node, boolean addChilds)    
    {
        addChilds=true;                             //siempre agrega hijos
        //System.out.println("addSemanticObject");
        Element events = null;
        Element event = null;
        SemanticObject aux=null;

        boolean hasChilds=false;
        SemanticClass cls=obj.getSemanticClass();

        //Active
        boolean active=false;
        SemanticProperty activeprop=cls.getProperty("active");
        if(activeprop!=null)
        {
            active=obj.getBooleanProperty(activeprop);
        }

        //String icon=SWBContext.UTILS.getIconClass(obj);

        Element jobj = addNode("node", obj.getURI(), obj.getDisplayName(user.getLanguage()), node);
        jobj.setAttribute("reload", "getSemanticObject." + obj.getURI());
        //jobj.setAttribute("icon", icon);
        //jobj.setAttribute("icon", "homev");


        hasChilds=hasHerarquicalNodes(obj);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(user, obj, jobj);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSemanticObjects(user.getLanguage(),it);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    if(ch.instanceOf(FilterableNode.swb_FilterableNode))
                    {
                        try
                        {
                            addSemanticObject(user,ch,jobj,false);
                        }
                        catch(Exception e)
                        {
                            log.error(e);                            
                        }
                    }
                }
            }else
            {
                while(it.hasNext())
                {
                    SemanticObject ch=it.next();
                    if(ch.instanceOf(FilterableNode.swb_FilterableNode))
                    {
                        hasChilds=true;
                        aux=ch;
                        break;
                    }
                }
            }
        }
        if(hasChilds && !addChilds)
        {
            if(aux!=null)
            {
                Element child = addNode("node", aux.getId(), aux.getDisplayName(user.getLanguage()), jobj);
                child.setAttribute("icon", "menu");
            }

            events = addNode("events", "events", "Events", jobj);
            event = addNode("willExpand", "willExpand", "WillExpand", events);
            event.setAttribute("action", "reload");
        }
    }


     /**
      * Add web site to dom document.
      * 
      * @param user the user
      * @param sc the sc
      * @param node the node
      * @param addChilds the add childs
      */
    protected void addSemanticClass(User user, SemanticClass sc, Element node, boolean addChilds)
    //public void addSemanticObject(JSONArray arr, SemanticObject obj, boolean addChilds, boolean addDummy, String lang) throws JSONException
    {
        //System.out.println("addSemanticClass:"+sc+" "+node);
        String lang=user.getLanguage();

        Element classele = addNode("topic", sc.getClassId(), sc.getDisplayName(user.getLanguage()), node);
        classele.setAttribute("reload", "getTopic.SC|" + sc.getClassId());
        classele.setAttribute("icon", "sitev");
        classele.setAttribute("topicmap", "SWBAdmin");

        Element etp = this.addNode("topic", sc.getClassId() + ";"+"add", getLocaleString("add", lang), classele);
        etp.setAttribute("reload", "getTopic.SCA|" + sc.getClassId() + "|" + "add");
        etp.setAttribute("topicmap", "SWBAdmin");
        //etp.setAttribute("icon", "menu");

        etp = this.addNode("topic", sc.getClassId() + ";"+"edit", getLocaleString("edit", lang), classele);
        etp.setAttribute("reload", "getTopic.SCA|" + sc.getClassId() + "|" + "edit");
        etp.setAttribute("topicmap", "SWBAdmin");
        //etp.setAttribute("icon", "menu");

        etp = this.addNode("topic", sc.getClassId() + ";"+"delete", getLocaleString("delete", lang), classele);
        etp.setAttribute("reload", "getTopic.SCA|" + sc.getClassId() + "|" + "delete");
        etp.setAttribute("topicmap", "SWBAdmin");
        //etp.setAttribute("icon", "menu");

        if(sc.isSubClass(org.semanticwb.model.Activeable.swb_Activeable ))
        {
            Element etp4 = this.addNode("topic", sc.getClassId() + ";"+"active", getLocaleString("active",lang)+"/"+getLocaleString("unactive",lang), classele);
            etp4.setAttribute("reload", "getTopic.SCA|" + sc.getClassId() + "|" + "active");
            etp4.setAttribute("topicmap", "SWBAdmin");
            //etp4.setAttribute("icon", "menu");
        }
    }


     /**
      * Add web site to dom document.
      * 
      * @param user the user
      * @param tm the tm
      * @param root the root
      * @param access the access
      * @param loadChild the load child
      * @param isFilter the is filter
      */
    @Override
    protected void addTopicMap(User user, WebSite tm, Element root, int access, boolean loadChild, boolean isFilter)
    {
        //System.out.println("addTopicMap");
//        if (access != FULL_ACCESS) {
//            access = 2; //AdmFilterMgr.getInstance().haveAccess2TopicMap(user, tm.getId());
//            if (access == NO_ACCESS) {
//                return;
//            }
//        }

        Element topicmap = addNode("node", tm.getId(), tm.getDisplayTitle(user.getLanguage()), root);
        topicmap.setAttribute("reload", "getTopicMap." + tm.getId());
        topicmap.setAttribute("access", "" + access);
        if (tm.isActive()) {
            topicmap.setAttribute("icon", "sitev");
        } else {
            topicmap.setAttribute("icon", "siter");
        }

        Iterator<String> its = hmclass.keySet().iterator();
        while (its.hasNext()) {
            String sclass = its.next();
            if (!sclass.equals("WebSite") && !sclass.equals("WebPage")) {
                SemanticClass sc = (SemanticClass) hmclass.get(sclass);

                Element classele = this.addNode("node", sc.getClassId(), sc.getName(), topicmap);
                classele.setAttribute("topicmap", tm.getId());
                classele.setAttribute("reload", "getTopic." + tm.getId() + "." + sc.getClassId());
                classele.setAttribute("access", "" + access);
                boolean canModify = true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                classele.setAttribute("canModify", String.valueOf(canModify));
                classele.setAttribute("icon", "hijov");

                Iterator<SemanticObject> itso = tm.getSemanticObject().getModel().listInstancesOfClass(sc);
                while (itso.hasNext()) {
                    SemanticObject so = itso.next();
                    Element ele = addNode("node", so.getId(), so.getDisplayName(user.getLanguage()), classele);
                    ele.setAttribute("topicmap", sc.getClassId());
                    ele.setAttribute("reload", "getTopic." + tm.getId() + "." + sc.getClassId() + "." + so.getId());
                    ele.setAttribute("access", "" + access);
                    canModify = true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                    ele.setAttribute("canModify", String.valueOf(canModify));
                    ele.setAttribute("icon", "hijov");

                //System.out.println("---"+so.getDisplayName(user.getLanguage()));
                }
            }
        }
        if(loadChild)
        {
            Iterator itex = ext.iterator();
            while (itex.hasNext()) {
                SWBTreeExt e = (SWBTreeExt) itex.next();
                e.addTopicMap(user, topicmap, tm, access, isFilter);
            }

            // Para cargar Home y sus WebPages
            if (!tm.getId().equals(SWBContext.WEBSITE_GLOBAL)) {
                WebPage tp = tm.getHomePage();
                try {
                    addTopic(user, tp, topicmap);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * Add web pages to dom document.
     * 
     * @param user the user
     * @param tp the tp
     * @param res the res
     */
    @Override
    protected void addTopic(User user, WebPage tp, Element res)
    {        
        Element events = null;
        Element event = null;

        Element topic = addNode("node", tp.getId(), tp.getDisplayName(user.getLanguage()), res);
        topic.setAttribute("reload", "getTopic." + tp.getWebSiteId() + "." + tp.getId());
        if (tp.isActive()) {
            if (tp == tp.getWebSite().getHomePage()) {
                topic.setAttribute("icon", "homev");
            } else {
                topic.setAttribute("icon", "hijov");
            }
        } else {
            if (tp == tp.getWebSite().getHomePage()) {
                topic.setAttribute("icon", "homer");
            } else {
                topic.setAttribute("icon", "hijor");
            }
        }

        //child
        Iterator it = tp.listChilds(user.getLanguage(), true, false, false, null); //getSortChild(false);
        while (it.hasNext()) {
            WebPage tp2 = (WebPage) it.next();
            Element child = addNode("node", tp2.getId(), tp2.getDisplayName(user.getLanguage()), topic);
            child.setAttribute("reload", "getTopic." + tp2.getWebSiteId() + "." + tp2.getId());

            //revisar; se agregó para ver lo de los iconos
            child.setAttribute("access", "2");
            child.setAttribute("canModify", "true");
            ///
            if (!tp2.getParent().getId().equals(tp.getId())) //virtual
            {
                child.setAttribute("icon", "virtual");
                child.setAttribute("alt", "Virtual Section");
            } else {
                if (tp2.isActive()) {
                    child.setAttribute("icon", "hijov");
                } else {
                    child.setAttribute("icon", "hijor");
                }

                //have child
                Iterator it2 = tp2.listChilds(user.getLanguage(), true, false, false, null); //getSortChild(false);
                if (it2.hasNext()) {
                    WebPage tp3 = (WebPage) it2.next();
                    Element child2 = addNode("node", tp3.getId(), tp3.getDisplayName(user.getLanguage()), child);
                    child2.setAttribute("icon", "menu");
                    //TODO: Se puso para ver si se mostraban los iconos
                    child2.setAttribute("access", "2");
                    child2.setAttribute("canModify", "true");
                    child.appendChild(child2);
                } else {
                    child.setAttribute("action", "showurl=" + tp2.getUrl());
                //child.setAttribute("target","work");
                }
                //events
                events = addNode("events", "events", "Events", child);
                event = addNode("willExpand", "willExpand", "WillExpand", events);
                event.setAttribute("action", "reload");
            }

        }
    }
}

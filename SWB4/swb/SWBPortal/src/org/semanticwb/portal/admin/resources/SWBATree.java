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
import org.w3c.dom.*;

import java.util.*;
import org.semanticwb.*;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeExt;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeUtil;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * Recurso para la administración de WebBuilder que muestra el árbol principal de
 * la administración con todos los elementos que existen en la aplicación.
 *
 * Principal Tree of WebBuilder Administration that shows the existing elements in
 * the aplication.
 * @author Javier Solis Gonzalez
 */
public class SWBATree extends GenericResource
{
    
    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBATree.class);
    
    /** The ext. */
    ArrayList ext=new ArrayList();

    /** The agzip. */
    boolean agzip=true;

    //TODO:Provicional hasta que este AdmFilterMgr
    /** The Constant NO_ACCESS. */
    public static final int NO_ACCESS = 0;
    
    /** The Constant PARCIAL_ACCESS. */
    public static final int PARCIAL_ACCESS = 1;
    
    /** The Constant FULL_ACCESS. */
    public static final int FULL_ACCESS = 2;
    //public static final String WBGLOBAL="WBGlobal";
    /** The Constant WBADMIN. */
    public static final String WBADMIN="WBAdmin";

    /**
     * Creates a new instance of WBTree.
     */
    public SWBATree()
    {
        agzip = SWBPlatform.getEnv("wb/responseGZIPEncoding","true").equalsIgnoreCase("true");
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/org/semanticwb/portal/admin/resources/SWBATree.properties")));
            String str=null;
            while((str=in.readLine())!=null)
            {
                try
                {
                    if(!str.startsWith("#"))
                    {
                        Class cls=Class.forName(str);
                        ext.add(cls.newInstance());
                    }
                }catch(Exception e){log.error(e);}
            }
        }catch(Exception e){log.error(e);}

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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }else super.processRequest(request,response,paramRequest);
    }


    /**
     * Gets the service.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @return the service
     * @return
     */
    public Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {
        //System.out.println("Service mi entrada:"+cmd);
        if (cmd.equals("initTree"))
        {
            return initTree(user, src);
        } else if (cmd.equals("initTreeFilter"))
        {
            return initTreeFilter(user, src);
        } else if (cmd.startsWith("getPath."))
        {
            return getPath(user, src,cmd.substring("getPath.".length()));
        }  else
        {
            return getDocument(user, src, cmd);
        }
    }

    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document initTree(User user, Document src)
    {
        return initTree(user, src, false);
    }

    /**
     * Inits the tree.
     * 
     * @param user the user
     * @param src the src
     * @param isFilter the is filter
     * @return the document
     * @return
     */
    public Document initTree(User user, Document src, boolean isFilter)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            //config
            Element config=addNode("config","config","Config",res);

            Element icons=addNode("icons","icons","Icons",config);

            Element icon=addNode("icon","sitev","Site",icons);
            icon.setAttribute("path","images/icon-sitioa.gif");
            icon=addNode("icon","siter","Site",icons);
            icon.setAttribute("path","images/icon-sitior.gif");
            icon=addNode("icon","hijor","Topic",icons);
            icon.setAttribute("path","images/icon-pagwebr.gif");
            icon=addNode("icon","hijov","Topic",icons);
            icon.setAttribute("path","images/icon-pagweba.gif");
            icon=addNode("icon","homer","Topic",icons);
            icon.setAttribute("path","images/i_home_rojo.gif");
            icon=addNode("icon","homev","Topic",icons);
            icon.setAttribute("path","images/i_home_verde.gif");
            icon=addNode("icon","virtual","Topic",icons);
            icon.setAttribute("path","images/ico_virtual.gif");

            icon=addNode("icon","folder","Folder",icons);
            icon.setAttribute("path","images/icon-foldera.gif");
            icon=addNode("icon","root","Root",icons);
            icon.setAttribute("path","images/icon-servera.gif");
            icon=addNode("icon","global","Global",icons);
            icon.setAttribute("path","images/icon-servera.gif");
            ///////
            icon=addNode("icon","devices","Devices",icons);
            icon.setAttribute("path","images/f_dispositivos.gif");
            icon=addNode("icon","device","Device",icons);
            icon.setAttribute("path","images/i_dispositivo.gif");
            icon=addNode("icon","dnss","DNS",icons);
            icon.setAttribute("path","images/f_dns.gif");
            icon=addNode("icon","dns","DNS",icons);
            icon.setAttribute("path","images/i_dns.gif");
            icon=addNode("icon","resources","Resources",icons);
            icon.setAttribute("path","images/f_estrategias.gif");
            icon=addNode("icon","resourcetype","ResourceType",icons);
            icon.setAttribute("path","images/f_resourcetype.gif");
            icon=addNode("icon","sysresources","SysResources",icons);
            icon.setAttribute("path","images/f_sistema.gif");
            icon=addNode("icon","resourcer","Resource",icons);
            icon.setAttribute("path","images/i_recurso_rojo.gif");
            icon=addNode("icon","resourcev","Resource",icons);
            icon.setAttribute("path","images/i_recurso_verde.gif");

            ////////////////////

            icon=addNode("icon","flows","Flows",icons);
            icon.setAttribute("path","images/f_flujos.gif");
            icon=addNode("icon","flow","Flow",icons);
            icon.setAttribute("path","images/i_flujo.gif");

            /////////////////

            icon=addNode("icon","languages","Languages",icons);
            icon.setAttribute("path","images/f_idioma.gif");
            icon=addNode("icon","language","Language",icons);
            icon.setAttribute("path","images/i_idioma.gif");
            icon=addNode("icon","metadatas","Metadatas",icons);
            icon.setAttribute("path","images/f_metadatos.gif");
            icon=addNode("icon","metadata","Metadata",icons);
            icon.setAttribute("path","images/i_metadata.gif");
            icon=addNode("icon","camps","Camps",icons);
            icon.setAttribute("path","images/f_camp.gif");
            icon=addNode("icon","campv","Camp",icons);
            icon.setAttribute("path","images/i_camp.gif");
            icon=addNode("icon","campr","Camp",icons);
            icon.setAttribute("path","images/i_camp_r.gif");
            icon=addNode("icon","templates","Templates",icons);
            icon.setAttribute("path","images/f_plantillas.gif");
            icon=addNode("icon","templater","Template",icons);
            icon.setAttribute("path","images/i_plantilla_rojo.gif");
            icon=addNode("icon","templatev","Template",icons);
            icon.setAttribute("path","images/i_plantilla_verde.gif");
            icon=addNode("icon","rules","Rules",icons);
            icon.setAttribute("path","images/f_reglas.gif");
            icon=addNode("icon","rule","Rule",icons);
            icon.setAttribute("path","images/i_regla.gif");
            icon=addNode("icon","userreps","UserReps",icons);
            icon.setAttribute("path","images/f_usuarios.gif");
            icon=addNode("icon","userrep","UserRep",icons);
            icon.setAttribute("path","images/i_repositoriousuarios.gif");
            icon=addNode("icon","role","Role",icons);
            icon.setAttribute("path","images/i_rol.gif");

            ///////////////////////////

            //menus
            icon=addNode("icon","trans","Transparent",icons);
            icon.setAttribute("path","images/trans.gif");

            //////////////////

            icon=addNode("icon","refresh","Refresh",icons);
            icon.setAttribute("path","images/refresh.gif");
            icon=addNode("icon","edit","Edit",icons);
            icon.setAttribute("path","images/edit.gif");
            icon=addNode("icon","remove","Remove",icons);
            icon.setAttribute("path","images/remove.gif");
            icon=addNode("icon","add","Add",icons);
            icon.setAttribute("path","images/add.gif");
            icon=addNode("icon","active","Active",icons);
            icon.setAttribute("path","images/active.gif");
            icon=addNode("icon","unactive","Unactive",icons);
            icon.setAttribute("path","images/unactive.gif");
            icon=addNode("icon","trash","Trash",icons);
            icon.setAttribute("path","images/papelera.gif");
            icon=addNode("icon","catalog","Catalog",icons);
            icon.setAttribute("path","images/catalogo.gif");

            Iterator it=ext.iterator();
            while(it.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)it.next();
                e.initTree(user, res, isFilter);
            }

            addServer(user, res, isFilter);

        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     * Inits the tree filter.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document initTreeFilter(User user, Document src)
    {
        return initTree(user, src,true);
    }

    /**
     * Gets the document.
     * 
     * @param user the user
     * @param src the src
     * @param act the act
     * @return the document
     * @return
     */
    public Document getDocument(User user, Document src, String act)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);

            String cmd=null;
            String id=null;
            int ind=act.indexOf('.');
            if(ind>0)
            {
                cmd=act.substring(0,ind);
                id=act.substring(ind+1);
                //System.out.println(cmd+":"+id);
            }else cmd=act;

            if(cmd.equals("getServer"))
            {
                addServer(user, res);
            }
            else if (cmd.equals("getGlobal"))
            {
                addGlobal(user, res, PARCIAL_ACCESS);
            } else if (cmd.equals("getTopicMap"))
            {
                WebSite tm=SWBContext.getWebSite(id);
                addTopicMap(user, tm, res, PARCIAL_ACCESS);
            } else if (cmd.equals("getTopic"))
            {
                String tmid=id.substring(0,id.indexOf('.'));
                String tpid=id.substring(id.indexOf('.')+1);
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                if(tp!=null)
                {
                    addTopic(user, tp, res);
                }else
                {
                    log.error("SWBAtree: not found "+id);
                    return getError(3);
                }                    
            }
            else
            {
                boolean ret=false;
                Iterator itex=ext.iterator();
                while(itex.hasNext())
                {
                    SWBTreeExt e=(SWBTreeExt)itex.next();
                    ret=e.executeCommand(user, res, cmd, id);
                    if(ret)break;
                }
                if(!ret)return getError(2);
            }
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     * Adds the opt refresh.
     * 
     * @param menu the menu
     * @param user the user
     */
    private void addOptRefresh(Element menu, User user)
    {
        SWBTreeUtil.addOptRefresh(menu,user);
    }

    /**
     * Adds the separator.
     * 
     * @param menu the menu
     */
    private void addSeparator(Element menu)
    {
        SWBTreeUtil.addSeparator(menu);
    }

    /**
     * Gets the path.
     * 
     * @param user the user
     * @param src the src
     * @param action the action
     * @return the path
     * @return
     */
    public Document getPath(User user, Document src, String action)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);

            StringTokenizer st=new StringTokenizer(action,".");
            String cmd=st.nextToken();

            if(cmd.equals("topic"))
            {
                String tmid=st.nextToken();
                String tpid=st.nextToken();

                StringBuffer ret=new StringBuffer();
                WebPage tp=SWBContext.getWebSite(tmid).getWebPage(tpid);
                ret.append(tp.getId());
                ArrayList arr=(ArrayList)SWBContext.getWebSite(tmid).listWebPages(); //ArrayList AdmFilterMgr.getInstance().getTopics(user, tmid);
                while(!arr.contains(tp))
                {
                    tp=tp.getParent(); //getType()
                    ret.insert(0,tp.getId()+".");
                }
                ret.insert(0,tmid+".");
                res.appendChild(dom.createTextNode(ret.toString()));
            }
        } catch (Exception e)
        {
            log.error(e);
            return getError(3);
        }
        return dom;
    }

    /**
     * Adds the server.
     * 
     * @param user the user
     * @param res the res
     */
    protected void addServer(User user, Element res)
    {
        addServer(user, res, false);
    }

    /**
     * Adds the server.
     * 
     * @param user the user
     * @param res the res
     * @param isFilter the is filter
     */
    protected void addServer(User user, Element res, boolean isFilter)
    {
        int access=2; //AdmFilterMgr.getInstance().haveAccess2Server(user);

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        //tree nodes
        Element root=addNode("node","server","Server",res); //Servr por tma.getWebPage("WBAd_sys_Server").getDisplayName(user.getLanguage())
        root.setAttribute("reload","getServer");
        root.setAttribute("icon","root");
        root.setAttribute("access",""+access);
        menu=addNode("menu","menu","Menu",root);

        //option=MenuElement.add(menu,"WBAd_mnui_TopicMapCreate","WBAd_sysi_TopicMapsInfo",null,user,null,null);
        //option=addNode("option","create_site",tma.getWebPage("WBAd_mnui_TopicMapCreate").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicMapsInfo").getUrl()+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
        //option.setAttribute("target","work");
        //addSeparator(menu);

        //addOptRefresh(menu,user);

        addGlobal(user, root, access, isFilter);

        //Topic maps
        Iterator<WebSite> it=sortIterator(SWBContext.listWebSites());
        while(it.hasNext())
        {
            //topicmap
            WebSite tm=it.next();
            if(!tm.isDeleted() && !tm.getId().equals(SWBContext.WEBSITE_GLOBAL))
            {
                addTopicMap(user, tm, root, access,isFilter,isFilter);
            }
        }

        Iterator itex=ext.iterator();
        while(itex.hasNext())
        {
            SWBTreeExt e=(SWBTreeExt)itex.next();
            e.addServer(user, root, isFilter);
        }

    }

    /**
     * Adds the global.
     * 
     * @param user the user
     * @param root the root
     * @param access the access
     */
    protected void addGlobal(User user, Element root, int access)
    {
        addGlobal(user, root, access, false);
    }

    /**
     * Adds the global.
     * 
     * @param user the user
     * @param root the root
     * @param access the access
     * @param isFilter the is filter
     */
    protected void addGlobal(User user, Element root, int access, boolean isFilter)
    {
        if(access!=FULL_ACCESS)
        {
            access=2; //AdmFilterMgr.getInstance().haveAccess2TMGlobal(user);
            if(access==NO_ACCESS)return;
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;

        WebSite tm=SWBContext.getGlobalWebSite();

        Element ele=addNode("node",tm.getId(),tm.getTitle(),root);
        //ele.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_ResourcesInfo").getUrl()+"?id="+rec.getId()+"&stype="+rec.getIdSubType());
        //ele.setAttribute("target","work");
        ele.setAttribute("reload","getGlobal");
        //ele.setAttribute("view","showurl="+tma.getWebPage("WBAd_infoi_ResourceInfo").getUrl()+"?id="+rec.getId());
        //ele.setAttribute("vtarget","info");
        ele.setAttribute("icon","global");
        ele.setAttribute("access",""+access);

        //menu
        menu=addNode("menu","menu","Menu",ele);
        //option=MenuElement.addOption(menu,"WBAd_mnui_Trash","WBAd_sysi_TrashTopics",null,user, null, tm.getId(),null);
        //if(option!=null)option.setAttribute("icon","trash");
        //option=addNode("option","trash",tma.getWebPage("WBAd_sys_Trash").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TrashTopics").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        //option=MenuElement.addOption(menu,"WBAd_mnui_ResourceTypes","WBAd_sys_ResourceCatalogInfo",null,user, null, tm.getId(), null);
        //if(option!=null)option.setAttribute("icon","catalog");
        //option=addNode("option","resourceTypes",tma.getWebPage("WBAd_sys_ResourceCatalog").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sys_ResourceCatalog").getUrl()+"?tm="+tm.getId());
        //option.setAttribute("target","work");
        //addSeparator(menu);
        //addOptRefresh(menu,user);



        Iterator itex=ext.iterator();
        while(itex.hasNext())
        {
            SWBTreeExt e=(SWBTreeExt)itex.next();
            e.addGlobal(user, ele, access, isFilter);
        }
    }

    /**
     * Adds the topic map.
     * 
     * @param user the user
     * @param tm the tm
     * @param root the root
     * @param access the access
     */
    protected void addTopicMap(User user, WebSite tm, Element root, int access)
    {
        addTopicMap(user, tm, root, access, true, false);
    }

    /**
     * Adds the topic map.
     * 
     * @param user the user
     * @param tm the tm
     * @param root the root
     * @param access the access
     * @param loadChild the load child
     * @param isFilter the is filter
     */
    protected void addTopicMap(User user, WebSite tm, Element root, int access, boolean loadChild, boolean isFilter)
    {
        if(access!=FULL_ACCESS)
        {
            access=2; //AdmFilterMgr.getInstance().haveAccess2TopicMap(user, tm.getId());
            if(access==NO_ACCESS)return;
        }

        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        Element topicmap=addNode("node",tm.getId(),tm.getTitle(),root);
        topicmap.setAttribute("action","showurl="+tma.getWebPage("bh_Information").getUrl()+"?tm="+tm.getId());
        topicmap.setAttribute("target","work");
        topicmap.setAttribute("reload","getTopicMap."+tm.getId());
        topicmap.setAttribute("view","showurl="+tma.getWebPage("bh_Information").getUrl()+"?tm="+tm.getId());
        topicmap.setAttribute("vtarget","info");
        topicmap.setAttribute("access",""+access);


        menu=addNode("menu","menu","Menu",topicmap);
        //option=MenuElement.edit(menu,"WBAd_mnui_TopicMapEdit","WBAd_sysi_TopicMapsInfo",null,user,tm.getId(),null);
        //option=addNode("option","edit_site",tma.getWebPage("WBAd_mnui_TopicMapEdit").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicMapsInfo").getUrl()+"?tm="+tm.getId()+"&act=edit");
        //option.setAttribute("target","work");
        if(tm.isActive())
        {
            topicmap.setAttribute("icon","sitev");
            //option=MenuElement.unactive(menu,"WBAd_mnui_TopicMapActive","WBAd_inti_StatusTopicMap",null,user,tm.getId(),null);
            //option=addNode("option","deactivate_site",getVariantName("WBAd_mnui_TopicMapActive",user),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicMapsInfo").getUrl()+"?tm="+tm.getId()+"&act=unactive&status=true");
            //option.setAttribute("target","status");
        }
        else
        {
            topicmap.setAttribute("icon","siter");
            //option=MenuElement.active(menu,"WBAd_mnui_TopicMapActive","WBAd_inti_StatusTopicMap",null,user,tm.getId(),null);
            //option=addNode("option","activate_site",tma.getWebPage("WBAd_mnui_TopicMapActive").getDisplayName(user.getLanguage()),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicMapsInfo").getUrl()+"?tm="+tm.getId()+"&act=active&status=true");
            //option.setAttribute("target","status");
        }

        if(loadChild)
        {


            Iterator itex=ext.iterator();
            while(itex.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)itex.next();
                e.addTopicMap(user, topicmap, tm, access, isFilter);
            }

            //Home
//            Iterator it=tm.getHomePage();//AdmFilterMgr.getInstance().getTopics(user, tm.getId()).iterator();
//            while(it.hasNext())
//            {
//                WebPage tp=(WebPage)it.next();
                WebPage tp=tm.getHomePage();
                try
                {
                    addTopic(user, tp, topicmap);
                }catch(Exception e){log.error(e);}
//            }
        }else
        {
                Element dummy=addNode("node","dummy","dummy",topicmap);
                dummy.setAttribute("reload","getResourceSubType");

                //events
                events=addNode("events","events","Events",topicmap);
                event=addNode("willExpand","willExpand","WillExpand",events);
                event.setAttribute("action","reload");
        }

    }

    /**
     * Adds the topic.
     * 
     * @param user the user
     * @param tp the tp
     * @param res the res
     */
    protected void addTopic(User user, WebPage tp, Element res)
    {
        if(tp==null)return;
        WebSite tma=SWBContext.getAdminWebSite();
        Element menu=null;
        Element option=null;
        Element events=null;
        Element event=null;

        Element topic=addNode("node",tp.getId(),tp.getDisplayName(user.getLanguage()),res);
        topic.setAttribute("action","showurl="+tp.getUrl());
        topic.setAttribute("target","work");
        topic.setAttribute("reload","getTopic."+tp.getWebSiteId()+"."+tp.getId());
        topic.setAttribute("view","showurl="+tp.getUrl());
        topic.setAttribute("vtarget","info");
        topic.setAttribute("dragEnabled","true");
        topic.setAttribute("dragValue","<a href=\""+tp.getUrl()+"\">"+tp.getDisplayName(user.getLanguage())+"</a>");

        //Menu
        menu=addNode("menu","menu","Menu",topic);
        //option=MenuElement.add(menu,"WBAd_mnui_TopicCreate","WBAd_sysi_TopicsInfo",tp,user,null,null);
        //option=addNode("option","add_topic",tma.getWebPage("WBAd_mnui_TopicCreate").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp)+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
        //option.setAttribute("target","work");
        //addSeparator(menu);
        //option=MenuElement.edit(menu,"WBAd_mnui_TopicEdit","WBAd_sysi_TopicsInfo",tp,user,null,null);
        //option=addNode("option","edit_topic",tma.getWebPage("WBAd_mnui_TopicEdit").getDisplayName(user.getLanguage()),menu);
        //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp)+"?act=edit");
        //option.setAttribute("target","work");
        if(tp.isActive())
        {
            //option=MenuElement.unactive(menu,"WBAd_mnui_TopicActive","WBAd_inti_StatusTopic",tp,user,null,null);
            //option=addNode("option","unactive_topic",getVariantName("WBAd_mnui_TopicActive",user),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp)+"?act=unactive&status=true");
            //option.setAttribute("target","status");
            if(tp==tp.getWebSite().getHomePage())
            {
                topic.setAttribute("icon","homev");
            }
            else
            {
                topic.setAttribute("icon","hijov");
            }
        }
        else
        {
            //option=MenuElement.active(menu,"WBAd_mnui_TopicActive","WBAd_inti_StatusTopic",tp,user,null,null);
            //option=addNode("option","active_topic",tma.getWebPage("WBAd_mnui_TopicActive").getDisplayName(user.getLanguage()),menu);
            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp)+"?act=active&status=true");
            //option.setAttribute("target","status");
            if(tp==tp.getWebSite().getHomePage())
            {
                topic.setAttribute("icon","homer");
            }
            else
            {
                topic.setAttribute("icon","hijor");
            }
        }

//        if(tp!=tp.getWebSite().getHomePage())
//        {
//            //addSeparator(menu);
//            //option=MenuElement.copy(menu,"WBAd_mnui_TopicCopy","WBAd_sysi_TopicsInfo",tp,user,"WBAd_mnui_Topic",null,null,null,"work");
//            //addSeparator(menu);
//            //option=MenuElement.remove(menu,"WBAd_mnui_TopicRemove","WBAd_inti_StatusTopic",tp,user,"WBAd_mnui_Topic",null,null);
//            //option=addNode("option","remove_section",tma.getWebPage("WBAd_mnui_TopicRemove").getDisplayName(user.getLanguage()),menu);
//            //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp)+"?act=remove&status=true");
//            //option.setAttribute("target","status");
//            //option.setAttribute("confirm",getConfirm("WBAd_mnui_Topic",user));
//            //option.setAttribute("icon","sitev");
//            //option.setAttribute("shortCut","DELETE");
//        }

        //addSeparator(menu);
        //addOptRefresh(menu,user);

        //child
        Iterator it=tp.listChilds(user.getLanguage(), null, false, null, null); //getSortChild(false);
        while(it.hasNext())
        {
            WebPage tp2=(WebPage)it.next();
            Element child=addNode("node",tp2.getId(),tp2.getDisplayName(user.getLanguage()),topic);
            child.setAttribute("reload","getTopic."+tp2.getWebSiteId()+"."+tp2.getId());
            child.setAttribute("view","showurl="+tp2.getUrl());
            child.setAttribute("vtarget","info");
            child.setAttribute("dragEnabled","true");
            child.setAttribute("dragValue","<a href=\""+tp2.getUrl()+"\">"+tp2.getDisplayName(user.getLanguage())+"</a>");

            if(!tp2.getParent().equals(tp)) //virtual
            {
                child.setAttribute("action","gotonode.topic."+tp2.getWebSiteId()+"."+tp2.getId());
                child.setAttribute("icon","virtual");
                child.setAttribute("alt","Virtual Section");
            }else
            {
                //Menu
                menu=addNode("menu","menu","Menu",child);
                //option=MenuElement.add(menu,"WBAd_mnui_TopicCreate","WBAd_sysi_TopicsInfo",tp2,user,null,null);
                //option=addNode("option","add_topic",tma.getWebPage("WBAd_mnui_TopicCreate").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp2)+"?title="+URLEncoder.encode(option.getAttribute("name"))+"&act=add");
                //option.setAttribute("target","work");
                //addSeparator(menu);
                //option=MenuElement.edit(menu,"WBAd_mnui_TopicEdit","WBAd_sysi_TopicsInfo",tp2,user,null,null);
                //option=addNode("option","edit_topic",tma.getWebPage("WBAd_mnui_TopicEdit").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp2)+"?act=edit");
                //option.setAttribute("target","work");
                if(tp2.isActive())
                {
                    child.setAttribute("icon","hijov");
                    //option=MenuElement.unactive(menu,"WBAd_mnui_TopicActive","WBAd_inti_StatusTopic",tp2,user,null,null);
                    //option=addNode("option","unactive_topic",getVariantName("WBAd_mnui_TopicActive",user),menu);
                    //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp2)+"?act=unactive&status=true");
                    //option.setAttribute("target","status");
                }
                else
                {
                    child.setAttribute("icon","hijor");
                    //option=MenuElement.active(menu,"WBAd_mnui_TopicActive","WBAd_inti_StatusTopic",tp2,user,null,null);
                    //option=addNode("option","active_topic",tma.getWebPage("WBAd_mnui_TopicActive").getDisplayName(user.getLanguage()),menu);
                    //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp2)+"?act=active&status=true");
                    //option.setAttribute("target","status");
                }
                //addSeparator(menu);
                //option=MenuElement.copy(menu,"WBAd_mnui_TopicCopy","WBAd_sysi_TopicsInfo",tp2,user,"WBAd_mnui_Topic",null,null,null,"work");
                //addSeparator(menu);
                //option=MenuElement.remove(menu,"WBAd_mnui_TopicRemove","WBAd_inti_StatusTopic",tp2,user,"WBAd_mnui_Topic",null,null);
                //option=addNode("option","remove_section",tma.getWebPage("WBAd_mnui_TopicRemove").getDisplayName(user.getLanguage()),menu);
                //option.setAttribute("action","showurl="+tma.getWebPage("WBAd_sysi_TopicsInfo").getUrl(tp2)+"?act=remove&status=true");
                //option.setAttribute("target","status");
                //option.setAttribute("confirm",getConfirm("WBAd_mnui_Topic",user));
                //option.setAttribute("shortCut","DELETE");
                //addSeparator(menu);
                //addOptRefresh(menu,user);

                //have child
                Iterator it2=tp2.listChilds(user.getLanguage(), null, false, null, null); //getSortChild(false);
                if(it2.hasNext())
                {
                    WebPage tp3=(WebPage)it2.next();
                    Element child2=addNode("node",tp3.getId(),tp3.getDisplayName(user.getLanguage()),child);
                }else
                {
                    child.setAttribute("action","showurl="+tp2.getUrl());
                    child.setAttribute("target","work");
                }

                //events
                events=addNode("events","events","Events",child);
                event=addNode("willExpand","willExpand","WillExpand",events);
                event.setAttribute("action","reload");
            }

        }
    }




    /**
     * Adds the node.
     * 
     * @param node the node
     * @param id the id
     * @param name the name
     * @param parent the parent
     * @return the element
     * @return
     */
    protected Element addNode(String node, String id, String name, Element parent)
    {
        return SWBTreeUtil.addNode(node,id,name,parent);
    }

    /**
     * Adds the element.
     * 
     * @param name the name
     * @param value the value
     * @param parent the parent
     * @return the element
     * @return
     */
    protected Element addElement(String name, String value, Element parent)
    {
        return SWBTreeUtil.addElement(name,value,parent);
    }

    /**
     * Gets the error.
     * 
     * @param id the id
     * @return the error
     * @return
     */
    protected Document getError(int id)
    {
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Element err = dom.createElement("err");
            res.appendChild(err);
            addElement("id", "" + id, err);
            if (id == 0)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_loginfail") + "...", err);
            } else if (id == 1)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_nouser") + "...", err);
            } else if (id == 2)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noservice") + "...", err);
            } else if (id == 3)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_serviceprocessfail") + "...", err);
            } else if (id == 4)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_parametersprocessfail") + "...", err);
            } else if (id == 5)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopicmap") + "...", err);
            } else if (id == 6)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noTopic") + "...", err);
            } else if (id == 7)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_usernopermiss") + "...", err);
            } else if (id == 8)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicAlreadyexist") + "...", err);
            } else if (id == 9)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_byImplement") + "...", err);
            } else if (id == 10)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_TopicMapAlreadyExist") + "...", err);
            } else if (id == 11)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_FileNotFound") + "...", err);
            } else if (id == 12)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_noversions") + "...", err);
            } else if (id == 13)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_xmlinconsistencyversion") + "...", err);
            } else if (id == 14)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noResourcesinMemory") + "...", err);
            } else if (id == 15)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_noTemplatesinMemory") + "...", err);
            } else if (id == 16)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_TemplatenotRemovedfromFileSystem") + "...", err);
            } else if (id == 17)
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getError_adminUsernotCreated") + "...", err);
            } else
            {
                addElement("message", SWBUtils.TEXT.getLocaleString("locale_Gateway", "usrmsg_Gateway_getService_errornotfound") + "...", err);
            }
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_documentError") + "...", e);
        }

        return dom;
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
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        boolean gzip = false;
        if(agzip)
        {
            if(request.getHeader("Via")!=null
            || request.getHeader("X-Forwarded-For")!=null
            || request.getHeader("Cache-Control")!=null)
            {
                //using proxy -> no zip
            }else
            {
                String accept = request.getHeader("Accept-Encoding");
                if (accept != null && accept.toLowerCase().indexOf("gzip") != -1)
                {
                    gzip = true;
                }
            }
        }

        java.util.zip.GZIPOutputStream garr = null;
        PrintWriter out = null;

        if (gzip)
        {
            garr = new java.util.zip.GZIPOutputStream(response.getOutputStream());
            out = new PrintWriter(garr, true);
            response.setHeader("Content-Encoding", "gzip");
        } else
        {
            out = response.getWriter();
        }

        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req"))
        {
            response.sendError(404, request.getRequestURI());
            return;
        }

        //System.out.println(AFUtils.getInstance().DomtoXml(dom));

        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();

        if (cmd == null)
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret="";
        try
        {
            Document res = getService(cmd, dom, paramRequest.getUser(), request, response);
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}
        out.print(new String(ret.getBytes()));
        out.flush();
        out.close();
        //System.out.println(new String(ret.getBytes()));
    }


    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("<div class=\"applet\">");
        out.println("<applet id=\"apptree\" name=\"apptree\" code=\"applets.generictree.GenericTree.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplGenericTree.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"100%\" height=\"100%\">");
        SWBResourceURL url=paramRequest.getRenderUrl();
        url.setMode("gateway");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<param name =\"cgipath\" value=\""+url+"\">");
        out.println("<param name =\"jsess\" value=\""+request.getSession().getId()+"\">");
        out.println("</applet>");
        out.println("</div>");
    }

    /**
     * Sort iterator.
     * 
     * @param it the it
     * @return the iterator
     * @return
     */
    public Iterator sortIterator(Iterator it)
    {
        return SWBTreeUtil.sortIterator(it);
    }

    /**
     * Sort enumeration.
     * 
     * @param en the en
     * @return the iterator
     * @return
     */
    public Iterator sortEnumeration(Enumeration en)
    {
        return SWBTreeUtil.sortEnumeration(en);
    }

    /**
     * Sort collection.
     * 
     * @param collection the collection
     * @return the iterator
     * @return
     */
    public Iterator sortCollection(Collection collection)
    {
        return SWBTreeUtil.sortCollection(collection);
    }

}

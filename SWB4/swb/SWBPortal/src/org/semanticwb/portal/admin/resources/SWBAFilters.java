/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * SWBAFilters.java
 *
 * Created on 23 de noviembre de 2004, 12:49 PM
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.XML;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AdminFilter;

import org.semanticwb.model.HerarquicalNode;
import org.semanticwb.model.ObjectBehavior;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.admin.resources.wbtree.SWBTreeExt;
import org.semanticwb.portal.api.SWBResourceURL;
/** Recurso de administraci�n de WebBuilder que permite agregar filtros, editarlos,
 * actualizarlos o eliminarlos seg�n sea el caso.
 *
 * Resource of administration of WebBuilder that allows to add filters, to publish
 * them, to update them or to eliminate them according to is the case.
 * @author Victor Lorenzana
 */
public class SWBAFilters extends SWBATree {

    private Logger log = SWBUtils.getLogger(SWBAFilters.class);
    
    static final String[] pathValids={"getGlobal","getTemplates","getServer","getPortlets","getPortletTypes","getSysResources","getTopic","getTemplateGroup","getUserRep","getRules","getPFlows","getLanguages","getDevices","getMDTables","getDnss","getTopicMap","getUserReps","getCamps","getCamp","getCntResources"};
    static final String[] namevalids={"node","config","icons","icon","res","events","willExpand","Template"};
    private HashMap hmclass = null;
    private Document jsondom = null;
    /** Creates a new instance of WBAFilters */
    public SWBAFilters() {
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }        
        else super.processRequest(request,response,paramRequest);
    }

    @Override
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
                //System.out.println("getDocument........."+cmd+":"+id);
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
                addTopic(user, tp, res);
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
        RevisaNodo(dom.getFirstChild());
        return dom;
    }


    /**
     * @param map
     * @param etopic
     * @param root
     * @param user
     */    
    public void getMenus(WebSite map,Element etopic,WebPage root,User user)
    {
        
        Iterator<WebPage> childs=root.listChilds(); //getSortChild();
        while(childs.hasNext())
        {
            WebPage topic=childs.next();
            //System.out.println("getMenus..."+topic.getDisplayName());
            if(user.haveAccess(topic))
            {
                Element etp=addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), etopic);
                etp.setAttribute("topicmap",map.getId());
                etp.setAttribute("icon","root");

                //TODO: AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);

                boolean canModify=true; //AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);
                etp.setAttribute("canModify",String.valueOf(canModify));
                etp.setAttribute("reload","getTopic."+map.getId()+"."+topic.getId());
                
                getMenus(map,etp,topic,user);
            }
        }
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document getMenus(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        
        WebSite map=SWBContext.getAdminWebSite();
        Document docres=null;
        try
        {   
            docres=SWBUtils.XML.getNewDocument();
            Element res=docres.createElement("res");
            docres.appendChild(res);
            WebPage topic=map.getWebPage("WBAd_Menus");
            if(user.haveAccess(topic))
            {                
                Element etopic=addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), res);
                etopic.setAttribute("topicmap",map.getId());
                etopic.setAttribute("icon", "root");
                //TODO: AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);

                boolean canModify=true; //AdmFilterMgr.getInstance().haveAccess2Menu4Filter(user, topic);
                etopic.setAttribute("canModify",String.valueOf(canModify));
                etopic.setAttribute("reload","getTopic."+map.getId()+"."+topic.getId());
                
                getMenus(map,etopic,topic,user);
            }
        }
        catch(Exception e)
        {
            //e.printStackTrace(System.out);
            log.error(e);
        }
        return docres;
    }

    /**
     * @param map
     * @param etopic
     * @param root
     * @param user
     */
    public void getElements(WebSite map,Element etopic,WebPage root,User user)
    {

        String lang = user.getLanguage();
        Iterator<String> its = hmclass.keySet().iterator();
        //System.out.println("Lista de clases...."+hmclass.keySet().size());
        while(its.hasNext())
        {
            String sclass = its.next();
//            if(!sclass.equals("WebSite")&&!sclass.equals("WebPage"))
//            {
                SemanticClass sc = (SemanticClass)hmclass.get(sclass);
                Element classele=this.addNode("topic",sc.getClassId(),sc.getName(),etopic);
                classele.setAttribute("topicmap",sc.getClassId());
                classele.setAttribute("reload","getTopic."+map.getId()+"."+sc.getClassId());
                boolean canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                classele.setAttribute("canModify",String.valueOf(canModify));
                classele.setAttribute("icon","sitev");


                Iterator<ObjectBehavior> obit=SWBComparator.sortSermanticObjects(ObjectBehavior.swbxf_ObjectBehavior.listGenericInstances(true));
                while(obit.hasNext())
                {
                    ObjectBehavior ob=obit.next();
                    if(!ob.isVisible())continue;

                    String title=ob.getDisplayName(lang);

                    SemanticObject interf=ob.getInterface();
                    if(null==interf)
                    {
                        Element etp=this.addNode("topic", ob.getId(), title, classele);
                        etp.setAttribute("topicmap",sc.getClassId());
                        etp.setAttribute("icon", "hijov");
                        canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                        etp.setAttribute("canModify",String.valueOf(canModify));
                        etp.setAttribute("reload","getTopic."+sc.getClassId()+"."+ob.getId());
                    }
                    else
                    {
                        SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(interf.getURI());
                        if(scls!=null)
                        {
                            if(scls.isSuperClass(sc))
                            {
                                Element etp=this.addNode("topic", ob.getId(), title, classele);
                                etp.setAttribute("topicmap",sc.getClassId());
                                etp.setAttribute("icon", "hijov");
                                canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                                etp.setAttribute("canModify",String.valueOf(canModify));
                                etp.setAttribute("reload","getTopic."+sc.getClassId()+"."+ob.getId());
                            }
                        }

                    }
                }
        }
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */
    public Document getElements(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {

        WebSite map=SWBContext.getAdminWebSite();
        Document docres=null;
        try
        {
            docres=SWBUtils.XML.getNewDocument();
            Element res=docres.createElement("res");
            docres.appendChild(res);
            WebPage topic=map.getWebPage("ObjectBehavior");
            if(user.haveAccess(topic))
            {
                Element etopic=this.addNode("topic", topic.getId(), topic.getDisplayName(user.getLanguage()), res);
                etopic.setAttribute("topicmap",map.getId());
                etopic.setAttribute("reload","getTopicMap."+map.getId());
                etopic.setAttribute("icon", "hijov");
                boolean canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                etopic.setAttribute("canModify",String.valueOf(canModify));
                etopic.setAttribute("reload","getTopic."+map.getId()+"."+topic.getId());
                
                if(hmclass==null) loadSemClass(user);
                getElements(map,etopic,topic,user);
            }
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return docres;
    }
    /**
     * @param user
     * @param src
     * @return
     */    
    @Override
    public Document initTree(User user, Document src)
    {
        Document doc=initTree(user,src,false);
        RevisaNodo(doc.getFirstChild());
        return doc;
    }


    @Override
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
     * @param user
     * @param src
     * @return
     */    
    @Override
    public Document initTreeFilter(User user, Document src)
    {
        Document doc=initTree(user,src);                
        //RevisaNodo(doc.getFirstChild());
        return doc;
    }
    /**
     * @param e
     * @return
     */    
    public boolean isNameValid(Element e)
    {       
        
        for(int i=0;i<namevalids.length;i++)
        {
            if(e.getNodeName().equals(namevalids[i]))
            {
                return true;
            }
        }                
        return false;
    }
    /**
     * @param ele
     */    
    public void RevisaNodo(Node ele)
    {        
        Vector vnodes=new Vector();
        NodeList nodes=ele.getChildNodes();
        for(int i=0;i<nodes.getLength();i++)
        {
            vnodes.add(nodes.item(i));
        }
        for(int i=0;i<vnodes.size();i++)
        {            
            if(vnodes.elementAt(i) instanceof Element)
            {
                Element e=(Element)vnodes.elementAt(i);
                if(!isNameValid(e) || !isValid(e.getAttribute("reload")))
                {                     
                    ele.removeChild((Node)vnodes.elementAt(i));
                }         
                else
                {
                    RevisaNodo(e);                
                }
            }
            else
            {
                RevisaNodo((Node)vnodes.elementAt(i));
            }
        }     
    }
    /**
     * @param path
     * @return
     */    
    public boolean isValid(String path)
    {       
        if(path==null)
        {
            
            return true;
        }
        StringTokenizer st=new StringTokenizer(path,".");
        if(st.countTokens()>0)
        {
            String pathinit=st.nextToken();            
            for(int i=0;i<pathValids.length;i++)
            {            
                if(pathinit.equals(pathValids[i]))
                {                    
                    return true;
                }
            }
        }
        else
        {            
            return true;
        }
        return false;
    }       
    
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req"))
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();

        if (cmd == null)
        {
            response.sendError(404, request.getRequestURI());
            return;            
        }                
        String ret="";

        //System.out.println("doGateWay: "+cmd);
        try
        {
            Document res=null;
            if(cmd.equals("update"))
            {
                res = updateFilter(cmd, dom, paramRequest.getUser(), request, response);
            }
            
            else if(cmd.equals("getElements"))
            {
                res = getElements(cmd, dom, paramRequest.getUser(), request, response);
            }
            else if(cmd.equals("getMenus"))
            {
                res = getMenus(cmd, dom, paramRequest.getUser(), request, response);
            }
            else if(cmd.equals("getFilter"))
            {
                res = getFilter(cmd, dom, paramRequest.getUser(), request, response);   
            }
            else
            {
                res = getService(cmd, dom, paramRequest.getUser(), request, response);
            }
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}

        //System.out.print("XML("+cmd+"):"+ret);

        out.print(new String(ret.getBytes()));
        
    }

    @Override
    public Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response)
    {

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
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document add(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        Document doc=null;
        try
        {
            doc=SWBUtils.XML.getNewDocument();
            Element res=doc.createElement("res");
            doc.appendChild(res);
            if(src.getElementsByTagName("filter").getLength()>0)
            {            
                Element efilter=(Element)src.getElementsByTagName("filter").item(0);
                String description="";
                if(efilter.getElementsByTagName("description").getLength()>0)
                {
                    Element edescription=(Element)efilter.getElementsByTagName("description").item(0);
                    Text etext=(Text)edescription.getFirstChild();
                    description=etext.getNodeValue();
                }
                String name=efilter.getAttribute("name");
                WebSite aws = SWBContext.getAdminWebSite();

                AdminFilter filter= AdminFilter.createAdminFilter(aws);
                filter.setTitle(name);
                filter.setDescription(description);
                
                Document xmlfilter=SWBUtils.XML.getNewDocument();
                Element newnode=(Element)xmlfilter.importNode(efilter, true);
                xmlfilter.appendChild(newnode);
                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                try
                {
                    //filter.create();
                    newnode.setAttribute("id",String.valueOf(filter.getId()));
                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                    //filter.update();
                    addElement("filter",String.valueOf(filter.getId()) , res);                    
                    return doc;
                }
                catch(Exception afe)
                {
                    //afe.printStackTrace(System.out);
                    addElement("err", afe.getMessage(), res);
                    log.error(afe);
                }
            }
            else
            {
                addElement("err","The element filter was not found", res);
            }
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return doc;
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document update(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
       WebSite aws = SWBContext.getAdminWebSite();
       Document doc=null;
        try
        {
            doc=SWBUtils.XML.getNewDocument();
            Element res=doc.createElement("res");
            doc.appendChild(res);
            if(src.getElementsByTagName("filter").getLength()>0)
            {            
                Element efilter=(Element)src.getElementsByTagName("filter").item(0);
                String description="";
                if(efilter.getElementsByTagName("description").getLength()>0)
                {
                    Element edescription=(Element)efilter.getElementsByTagName("description").item(0);
                    Text etext=(Text)edescription.getFirstChild();
                    description=etext.getNodeValue();
                }
                String name=efilter.getAttribute("name");
                //AdminFilter filter=AdminFilter.getAdminFilter(efilter.getAttribute("id"),efilter.getAttribute("topicmap"));
                AdminFilter filter=AdminFilter.getAdminFilter(efilter.getAttribute("id"),aws);
                filter.setTitle(name);
                filter.setDescription(description);
                //filter.setTopicMapId(efilter.getAttribute("topicmap"));
                Document xmlfilter=SWBUtils.XML.getNewDocument();

                Element newnode=(Element)xmlfilter.importNode(efilter, true);
                xmlfilter.appendChild(newnode);

                //System.out.println("XML Applet: "+SWBUtils.XML.domToXml(xmlfilter,true));

                filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
                try
                {                    
                    newnode.setAttribute("id",String.valueOf(filter.getId()));
                    filter.setXml(SWBUtils.XML.domToXml(xmlfilter));
//                    filter.update();
                    addElement("filter",String.valueOf(filter.getId()) , res);
                }
                catch(Exception afe)
                {
                    //afe.printStackTrace(System.out);
                    addElement("err", afe.getMessage(), res);
                    log.error(afe);
                }
            }
            else
            {
                addElement("err","The element filter was not found", res);
            }
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return doc;
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document getFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        WebSite map=SWBContext.getAdminWebSite();
        Document docres=null;
        try
        {   
            docres=SWBUtils.XML.getNewDocument();
            Element res=docres.createElement("res");
            docres.appendChild(res);
            if(src.getElementsByTagName("id").getLength()>0)
            {
                Element eid=(Element)src.getElementsByTagName("id").item(0);
                Text etext=(Text)eid.getFirstChild();
                String id=etext.getNodeValue();
                AdminFilter filter=AdminFilter.getAdminFilter(id, map);
                Document exmlfilter=SWBUtils.XML.xmlToDom(filter.getXml());
                Node node=docres.importNode(exmlfilter.getFirstChild(),true);
                res.appendChild(node);
                NodeList nodes=docres.getElementsByTagName("node");
                for(int i=0;i<nodes.getLength();i++)
                {
                    Element enode=(Element)nodes.item(i);
                    String topicid=enode.getAttribute("id");
                    String path=topicid;
                    String topicmap=enode.getAttribute("topicmap");                    
                    WebSite topicMap=SWBContext.getWebSite(topicmap);
                    if(topicMap!=null)
                    {
                        WebPage topic=topicMap.getWebPage(topicid);
                        if(topic!=null)
                        {
                            while(topic.getParent()!=null)
                            {
                                path=topic.getParent().getId()+"|"+path;
                                topic=topic.getParent();
                            }                                            
                        }
                    }
                    enode.setAttribute("path",path);
                }                
            }            
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return docres;
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document updateFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        if(src.getElementsByTagName("filter").getLength()>0)
        {
            Element efilter=(Element)src.getElementsByTagName("filter").item(0);
            if(efilter.getAttribute("id")==null || efilter.getAttribute("id").equals(""))
            {
                return add(cmd, src, user, request, response);
            }
            else
            {
                return update(cmd, src, user, request, response);
            }
        }
        return null;
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        WebSite map=SWBContext.getAdminWebSite();

        System.out.println("Cargando Json....");
        try
        {
            String urldatos = "http://localhost:8080/swb/swbadmin/jsp/Tree.jsp?childs=true";

//            if(jsondom==null)
            {
                System.out.println("path: "+urldatos);

                URL pagina = new URL(urldatos);
                URLConnection conex = null;

                try { conex=pagina.openConnection(); } catch(Exception nexc){conex=null;}
                if(conex!=null)
                {
                    JSONObject obj=new JSONObject(SWBUtils.IO.readInputStream(conex.getInputStream()));
                    String xml = "<json>"+XML.toString(obj)+"</json>";
                    jsondom = SWBUtils.XML.xmlToDom(xml);
                    System.out.println("XML(json): "+SWBUtils.XML.domToXml(jsondom, true));
    //                JSONArray jarr = obj.names();
    //                for(int i = 0; i<jarr.length(); i++)
    //                {
    //                    System.out.println("name("+i+") "+jarr.get(i));
    //                }
                }
            }
        }
        catch(Exception noe)
        {
            System.out.println("No se pudo cargar el JSon....");
        }



        User user=paramRequest.getUser();
        PrintWriter out=response.getWriter();        
        String act="view";
        if(request.getParameter("act")!=null)
        {
            act=request.getParameter("act");
        }
        if(act.equals("remove") && request.getParameter("id")!=null)
        {
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
            String id=request.getParameter("id");
            AdminFilter filter=AdminFilter.getAdminFilter(id,map);
            filter.remove();
            act="view";       
        }
        else if(act.equals("add"))
        {
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");        
            url.setCallMethod(url.Call_DIRECT);
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<APPLET id=\"editfilter\" name=\"editfilter\" code=\"applets.filters.EditFilter.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/EditFilters.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");            
            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");            
            out.println("<PARAM NAME =\"tm\" VALUE=\""+map.getId()+"\">");            
            url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);              
            out.println("<PARAM NAME =\"location\" VALUE=\""+url+"\">");            
            out.println("</APPLET>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setParameter("act", "view");
            out.println("<input type=\"button\" name=\"bckButton\" onclick=\"submitUrl('" + urlb+ "',this); return false;\" value=\""+paramRequest.getLocaleString("btnCancel")+"\">");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("\r\n<script>\r\n");            
            out.println("\r\nfunction doView(){\r\n");  
            url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);            
            out.println("location='"+ url   +"';\r\n");            
            out.println("\r\n}\r\n");            
            out.println("</script>\r\n");
        }      
        else if(act.equals("edit") && request.getParameter("id")!=null)
        {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<APPLET id=\"editfilter\" name=\"editfilter\" code=\"applets.filters.EditFilter.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/EditFilters.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<PARAM NAME =\"idfilter\" VALUE=\""+request.getParameter("id")+"\">");
            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");            
            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");            
            out.println("<PARAM NAME =\"tm\" VALUE=\""+map.getId()+"\">");
            url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);              
            out.println("<PARAM NAME =\"location\" VALUE=\""+url+"\">");    
            out.println("</APPLET>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setParameter("act", "view");
            out.println("<input type=\"button\" name=\"bckButton\" onclick=\"submitUrl('" + urlb+ "',this); return false;\" value=\""+paramRequest.getLocaleString("btnCancel")+"\">");
            out.println("</fieldset>");
            out.println("</div>");
            out.println("\r\n<script>\r\n");            
            out.println("\r\nfunction doView(){\r\n");             
            url=paramRequest.getRenderUrl();
            url.setMode(url.Mode_VIEW);            
            out.println("location='"+ url   +"';\r\n");            
            out.println("\r\n}\r\n");            
            out.println("</script>\r\n");
        } 
        if(act.equals("view"))
        {
            SWBResourceURL url=paramRequest.getRenderUrl();
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
            
            Iterator<AdminFilter> filters=AdminFilter.listAdminFilters(map);
            while(filters.hasNext())
            {
                AdminFilter filter=filters.next();

                out.println("<tr >");     //bgcolor=\""+rowColor+"\"
            
                out.println("<td  colspan=\"2\" align=\"center\">");
                
                SWBResourceURL urlRemove = paramRequest.getRenderUrl();
                urlRemove.setParameter("act","remove");
                urlRemove.setParameter("id",filter.getId());
                out.println("<a href=\"#\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgAlertShureRemoveFilter")+"?') ) submitUrl('"+urlRemove.toString()+"',this);return false;\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/remove.gif\" border=\"0\" title=\""+paramRequest.getLocaleString("msgLinkRemove")+"\"></a>&nbsp;");

                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                urlEdit.setParameter("act","edit");
                urlEdit.setParameter("id",filter.getId());
                out.println("<a href=\"#\" onclick=\"submitUrl('"+urlEdit.toString()+"',this); return false;\" ><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/Portlet.png\" border=\"0\" title=\""+paramRequest.getLocaleString("msgLinkEdit")+"\"></a>");
                
                out.println("</td>");
                
                out.println("<td >");     
                out.println(filter.getId());     
                out.println("</td>");     

                out.println("<td >");     
                out.println(filter.getTitle());
                out.println("</td>");   

                out.println("<td >");     
                out.println(filter.getDescription());     
                out.println("</td>");   

                out.println("</tr>");   
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            Portlet base = getResourceBase();
            out.println("<form id=\""+base.getId()+"/addAdminFilter\" action=\""+ url  +"\">");
            out.println("<button dojoType=\"dijit.form.Button\" name=\"op\" onclick=\"submitForm('" + getResourceBase().getId() + "/addAdminFilter'); return false;\">"+paramRequest.getLocaleString("msgBtnAdd")+"</button>");
            //out.println("<input type=\"submit\" name=\"op\" value=\""+paramRequest.getLocaleString("msgBtnAdd")+"\">");
            out.println("<input type=\"hidden\" name=\"act\" value=\"add\">");
            out.println("</form>");   
            out.println("</fieldset>");
            out.println("</div>");
        }        
    }


    public void loadSemClass(User user)
    {
        if(hmclass==null) hmclass=new HashMap();
        Iterator<WebSite> it=SWBComparator.sortSermanticObjects(SWBContext.listWebSites(),user.getLanguage());
        while(it.hasNext())
        {
            WebSite site=it.next();
            addSemanticObject(user, site.getSemanticObject(),true,false);//, ele);
        }
    }

    public boolean hasHerarquicalNodes(SemanticObject obj)
    {
        boolean ret=false;
        Iterator<SemanticObject> it=obj.getSemanticClass().listHerarquicalNodes();
        if(it.hasNext())
        {
            ret=true;
        }
        return ret;
    }


    public void addHerarquicalNodes(User user, SemanticObject obj) //, Element ele)
    {
        Iterator<SemanticObject> it=SWBComparator.sortSortableObject(obj.getSemanticClass().listHerarquicalNodes());
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(user,node,obj,true); //, ele);
        }
    }

    public void addHerarquicalNode(User user, HerarquicalNode node, SemanticObject obj, boolean addChilds) //, Element ele)
    {
        SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        hmclass.put(cls.getName(), cls);
        Iterator<SemanticObject> it=SWBComparator.sortSermanticObjects(obj.getModel().listInstancesOfClass(cls),user.getLanguage());

        SemanticProperty herarprop=null;   //Herarquical property;
        Iterator<SemanticProperty> hprops=cls.listInverseHerarquicalProperties();
        if(hprops.hasNext())herarprop=hprops.next();

        if(addChilds)
        {
            while(it.hasNext())
            {
                SemanticObject so=it.next();
                hmclass.put(so.getSemanticClass().getName(),so.getSemanticClass());
                if(herarprop!=null)
                {
                    if(so.getObjectProperty(herarprop)==null)
                    {
                        addSemanticObject(user, so,true);//,ele);
                    }
                }else
                {
                    addSemanticObject(user, so,true);//,ele);
                }
            }
        }

    }

    public void addSemanticObject(User user, SemanticObject obj, boolean addChilds)//, Element ele)
    {
        addSemanticObject(user, obj, addChilds, false);//, ele);
    }


    public void addSemanticObject(User user, SemanticObject obj, boolean addChilds, boolean addDummy)//, Element ele)
    {
        boolean hasChilds=false;

        SemanticClass cls=obj.getSemanticClass();
        hmclass.put(cls.getName(), cls);

        hasChilds=hasHerarquicalNodes(obj);
        if(addChilds || !hasChilds)
        {
            addHerarquicalNodes(user, obj);//, etp);

            Iterator<SemanticObject> it=obj.listHerarquicalChilds();
            if(addChilds)
            {
                Iterator<SemanticObject> it2=SWBComparator.sortSermanticObjects(it,user.getLanguage());
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    addSemanticObject(user, ch,false);//,etp);
                }
            }else
            {
                if(it.hasNext())
                {
                    hasChilds=true;
                }
            }
        }
    }

    @Override
    protected void addServer(User user, Element res, boolean isFilter)
    {
        int access=2; //AdmFilterMgr.getInstance().haveAccess2Server(user);
        hmclass = new HashMap();
        //tree nodes
        Element root=addNode("node","server","Server",res); //Servr por tma.getTopic("WBAd_sys_Server").getDisplayName(user.getLanguage())
        root.setAttribute("reload","getServer");
        root.setAttribute("icon","global");
        root.setAttribute("access",""+access);

        //addGlobal(user, root, access, isFilter);

        loadSemClass(user);


        //WebSites
        Iterator<WebSite> it=sortIterator(SWBContext.listWebSites());
        while(it.hasNext())
        {
            //topicmap
            WebSite tm=it.next();
            if(!tm.isDeleted() )
            {
                addTopicMap(user, tm, root, access,false,isFilter);
            }
        }

        Iterator itex=ext.iterator();
        while(itex.hasNext())
        {
            SWBTreeExt e=(SWBTreeExt)itex.next();
            e.addServer(user, root, isFilter);
        }        
    }

    @Override
    protected void addTopicMap(User user, WebSite tm, Element root, int access, boolean loadChild, boolean isFilter)
    {
        if(access!=FULL_ACCESS)
        {
            access=2; //AdmFilterMgr.getInstance().haveAccess2TopicMap(user, tm.getId());
            if(access==NO_ACCESS)return;
        }

        Element topicmap=addNode("node",tm.getId(),tm.getTitle(),root);
        topicmap.setAttribute("reload","getTopicMap."+tm.getId());
        topicmap.setAttribute("access",""+access);
        if(tm.isActive())
        {
            topicmap.setAttribute("icon","sitev");
        }
        else
        {
            topicmap.setAttribute("icon","siter");
        }

        Iterator<String> its = hmclass.keySet().iterator();
        while(its.hasNext())
        {
            String sclass = its.next();
            if(!sclass.equals("WebSite")&&!sclass.equals("WebPage"))
            {
                SemanticClass sc = (SemanticClass)hmclass.get(sclass);

                Element classele=this.addNode("node",sc.getClassId(),sc.getName(),topicmap);
                classele.setAttribute("topicmap",tm.getId());
                classele.setAttribute("reload","getTopic."+tm.getId()+"."+sc.getClassId());
                classele.setAttribute("access",""+access);
                boolean canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                classele.setAttribute("canModify",String.valueOf(canModify));
                classele.setAttribute("icon","hijov");

                Iterator<SemanticObject> itso = tm.getSemanticObject().getModel().listInstancesOfClass(sc);
                while(itso.hasNext())
                {
                    SemanticObject so = itso.next();
                    Element ele=addNode("node",so.getId(),so.getDisplayName(user.getLanguage()),classele);
                    ele.setAttribute("topicmap",sc.getClassId());
                    ele.setAttribute("reload","getTopic."+tm.getId()+"."+sc.getClassId()+"."+so.getId());
                    ele.setAttribute("access",""+access);
                    canModify=true; //AdmFilterMgr.getInstance().haveAccess2System4Filter(user, topic);
                    ele.setAttribute("canModify",String.valueOf(canModify));
                    ele.setAttribute("icon","hijov");

                    //System.out.println("---"+so.getDisplayName(user.getLanguage()));
                }
            }
        }
//        if(loadChild)
        {
            Iterator itex=ext.iterator();
            while(itex.hasNext())
            {
                SWBTreeExt e=(SWBTreeExt)itex.next();
                e.addTopicMap(user, topicmap, tm, access, isFilter);
            }

            if(!tm.getId().equals(SWBContext.WEBSITE_GLOBAL))
            {
                WebPage tp=tm.getHomePage();
                try
                {
                    addTopic(user, tp, topicmap);
                }catch(Exception e){log.error(e);}
            }
        }
    }

    @Override
    protected void addTopic(User user, WebPage tp, Element res)
    {
        //WebSite tma=SWBContext.getAdminWebSite();
        Element events=null;
        Element event=null;

        Element topic=addNode("node",tp.getId(),tp.getDisplayName(user.getLanguage()),res);
        topic.setAttribute("reload","getTopic."+tp.getWebSiteId()+"."+tp.getId());
        if(tp.isActive())
        {
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
            if(tp==tp.getWebSite().getHomePage())
            {
                topic.setAttribute("icon","homer");
            }
            else
            {
                topic.setAttribute("icon","hijor");
            }
        }

        //child
        Iterator it=tp.listChilds(user.getLanguage(), true, false, false, false); //getSortChild(false);
        while(it.hasNext())
        {
            WebPage tp2=(WebPage)it.next();
            Element child=addNode("node",tp2.getId(),tp2.getDisplayName(user.getLanguage()),topic);
            child.setAttribute("reload","getTopic."+tp2.getWebSiteId()+"."+tp2.getId());
            if(!tp2.getParent().getId().equals(tp.getId())) //virtual
            {
                child.setAttribute("icon","virtual");
                child.setAttribute("alt","Virtual Section");
            }else
            {
                if(tp2.isActive())
                {
                    child.setAttribute("icon","hijov");
                }
                else
                {
                    child.setAttribute("icon","hijor");
                }

                //have child
                Iterator it2=tp2.listChilds(user.getLanguage(), true, false, false, false); //getSortChild(false);
                if(it2.hasNext())
                {
                    WebPage tp3=(WebPage)it2.next();
                    Element child2=addNode("node",tp3.getId(),tp3.getDisplayName(user.getLanguage()),child);
                    child.appendChild(child2);
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

}

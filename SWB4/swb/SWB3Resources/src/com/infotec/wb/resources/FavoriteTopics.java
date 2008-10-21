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
 * FavoriteTopics.java
 *
 * Created on 06 de julio de 2005, 19:48
 */

package com.infotec.wb.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBActionResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/** Objeto que se encarga de desplegar y administrar Mis Secciones Favoritas de los usuarios finales
 * bajo ciertos criterios(configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer My Favorite Topics of the end
 * users under certain criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 */

public class FavoriteTopics extends GenericAdmResource 
{
    private static Logger log = SWBUtils.getLogger(FavoriteTopics.class);
    
    javax.xml.transform.Templates tpl; 
    String webWorkPath = "/work"; 
    String path = SWBPlatform.getContextPath() +"swbadmin/xsl/FavoriteTopics/";
    
    /**
     * Creates a new instance of FavoriteTopics.
     */
    public FavoriteTopics() { 
    }
    
    /**
     * @param base
     */      
    public void setResourceBase(Portlet base)
    {
        try 
        { 
            super.setResourceBase(base); 
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBUtils.IO.getStreamFromString(SWBUtils.IO.getFileFromPath(base.getWorkPath()+"/"+ base.getAttribute("template").trim()))); 
                path=webWorkPath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/FavoriteTopics/FavoriteTopics.xslt"));} 
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    /*
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    {
        User user=paramRequest.getUser();
        //if(!user.isLoged()) return null; TODO: VER 4.0
        
        String action = null != request.getParameter("ftp_act") && !"".equals(request.getParameter("ftp_act").trim()) ? request.getParameter("ftp_act").trim() : "ftp_step1";
        Portlet base=getResourceBase();
        try
        {
            Document  dom = SWBUtils.XML.getNewDocument();
            if("ftp_step2".equals(action)) {
                dom=getDomFavoriteTopic(request, response, paramRequest); // Forma con el detalle del topico para editar
            }
            else
            {
                Element root = dom.createElement("resource");
                root.setAttribute("path", path);
                SWBResourceURL url = reqParams.getActionUrl();
                url.setAction("add");
                root.setAttribute("addURL", url.toString());
                url.setAction("remove");
                root.setAttribute("removeURL", url.toString());

                SWBResourceURLImp url2=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url2.setResourceBase(base);
                url2.setMode(url.Mode_VIEW);
                url2.setWindowState(url.WinState_MAXIMIZED);
                url2.setParameter("ftp_act","ftp_step2");
                url2.setTopic(paramRequest.getTopic());
                url2.setCallMethod(paramRequest.Call_DIRECT);     
                root.setAttribute("editURL", url2.toString());
                dom.appendChild(root);

                Document data=SWBUtils.XML.xmlToDom(base.ge);
                boolean bOk=false;
                if(data!=null)
                {
                    TopicMap tm = reqParams.getTopic().getMap();
                    NodeList topicmap=data.getElementsByTagName(tm.getId());
                    if(topicmap.getLength() > 0)
                    {
                        Topic lang = tm.getTopicLang(user.getLanguage());
                        NodeList favorites = topicmap.item(0).getChildNodes();
                        for(int i=0;  i < favorites.getLength(); i++)
                        {
                            if("favoritetopic".equals(favorites.item(i).getNodeName()))
                            {
                                Node node=dom.importNode(favorites.item(i), true);
                                Topic tp=tm.getTopic(node.getFirstChild().getNodeValue());
                                if(tp!=null)
                                {
                                    String desc=null;
                                    NodeList detail=node.getChildNodes();
                                    for(int j=0;  j < detail.getLength(); j++)
                                    {
                                        if("description".equals(detail.item(j).getNodeName()))
                                        {
                                            desc=detail.item(j).getFirstChild().getNodeValue();
                                        }
                                    }    
                                    if(desc==null)desc=tp.getDescription(lang);                                    
                                    addElem(dom, node, "name", tp.getDisplayName(lang));
                                    addElem(dom, node, "location", tp.getUrl());
                                    if(desc!=null && !"".equals(desc)) addElem(dom, node, "description", desc);
                                    addElem(dom, node, "lastupdate", WBUtils.dateFormat(tp.getDbdata().getLastupdate()));
                                    if(reqParams.getTopic().getId().equals(node.getFirstChild().getNodeValue())) bOk=true;
                                }
                                root.appendChild(node);

                            }
                        }
                    }
                }
                Element emn = null;
                if(!bOk)
                {
                    if (!"".equals(base.getAttribute("imgadd", "").trim()))
                    {
                        emn = dom.createElement("imgadd");
                        emn.setAttribute("src", webWorkPath +"/"+ base.getAttribute("imgadd").trim());
                        if (!"".equals(base.getAttribute("altadd", "").trim()))
                            emn.setAttribute("alt", base.getAttribute("altadd").trim());
                        else emn.setAttribute("alt", reqParams.getLocaleString("msgAdd"));
                    } 
                    else  
                    {
                        emn = dom.createElement("btnadd");
                        if (!"".equals(base.getAttribute("btnadd", "").trim()))
                            emn.appendChild(dom.createTextNode(base.getAttribute("btnadd").trim()));
                        else emn.appendChild(dom.createTextNode(reqParams.getLocaleString("msgAdd"))); 
                    }
                    root.appendChild(emn);            
                }
                if (!"".equals(base.getAttribute("imgedit", "").trim()))
                {
                    emn = dom.createElement("imgedit");
                    emn.setAttribute("src",  webWorkPath +"/"+ base.getAttribute("imgedit").trim());
                    if (!"".equals(base.getAttribute("altedit", "").trim()))
                        emn.setAttribute("alt", base.getAttribute("altedit").trim());
                    else emn.setAttribute("alt", reqParams.getLocaleString("msgEdit"));
                } 
                else 
                {
                    emn = dom.createElement("btnedit");
                    if (!"".equals(base.getAttribute("btnedit", "").trim()))
                        emn.appendChild(dom.createTextNode(base.getAttribute("btnedit").trim()));
                    else emn.appendChild(dom.createTextNode(reqParams.getLocaleString("msgEdit"))); 
                }
                root.appendChild(emn);
                if (!"".equals(base.getAttribute("imgremove", "").trim()))
                {
                    emn = dom.createElement("imgremove");
                    emn.setAttribute("src",  webWorkPath +"/"+ base.getAttribute("imgremove").trim());
                    if (!"".equals(base.getAttribute("altremove", "").trim()))
                        emn.setAttribute("alt", base.getAttribute("altremove").trim());
                    else emn.setAttribute("alt", reqParams.getLocaleString("msgRemove"));
                } 
                else 
                {
                    emn = dom.createElement("btnremove");
                    if (!"".equals(base.getAttribute("btnremove", "").trim()))
                        emn.appendChild(dom.createTextNode(base.getAttribute("btnremove").trim()));
                    else emn.appendChild(dom.createTextNode(reqParams.getLocaleString("msgRemove"))); 
                }
                root.appendChild(emn);
                addElem(dom, root, "msgadded", reqParams.getLocaleString("msgAdded"));
                addElem(dom, root, "msglastupdate", reqParams.getLocaleString("msgLastupdate"));
            }
            return dom;
        }
        catch (Exception e) { AFUtils.log(e, "Error while generating DOM in resource "+ base.getResourceType().getName() +" with identifier " + base.getId() + " - " + base.getTitle(), true); }
        return null;            
    }**/
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    /*
    public org.w3c.dom.Document getDomFavoriteTopic(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, com.infotec.wb.lib.WBParamRequest reqParams) throws com.infotec.appfw.exception.AFException, java.io.IOException
    {
        Portlet base=getResourceBase();
        try
        {
            Document  dom = AFUtils.getInstance().getNewDocument();
            Element root = dom.createElement("resource");
            root.setAttribute("path", path);
            WBResourceURL url = reqParams.getActionUrl();
            url.setAction("update");
            dom.appendChild(root);
            
            String id = null != request.getParameter("ftp_id") && !"".equals(request.getParameter("ftp_id").trim()) ? request.getParameter("ftp_id").trim() : "";
            WBUser user=reqParams.getUser();
            Document data=AFUtils.getInstance().XmltoDom(base.getData(user));
            if(data!=null)
            {
                TopicMap tm = reqParams.getTopic().getMap();
                NodeList topicmap=data.getElementsByTagName(tm.getId());
                if(topicmap.getLength() > 0)
                {
                    Topic lang = tm.getTopicLang(user.getLanguage());
                    NodeList favorites = topicmap.item(0).getChildNodes();
                    for(int i=0;  i < favorites.getLength(); i++)
                    {
                        if("favoritetopic".equals(favorites.item(i).getNodeName()))
                        {
                            Node node=dom.importNode(favorites.item(i), true);
                            if(id.equals(node.getFirstChild().getNodeValue()))
                            {
                                root.setAttribute("updateURL", url.toString()+"?ftp_id="+id);
                                Topic tp=tm.getTopic(node.getFirstChild().getNodeValue());
                                String desc=null;
                                NodeList detail=node.getChildNodes();
                                for(int j=0;  j < detail.getLength(); j++)
                                {
                                    if("description".equals(detail.item(j).getNodeName()))
                                    {
                                        desc=detail.item(j).getFirstChild().getNodeValue();
                                    }
                                }    
                                if(desc==null)desc=tp.getDescription(lang);
                                addElem(dom, node, "name", tp.getDisplayName(lang));
                                addElem(dom, node, "location", tp.getUrl());
                                if(desc!=null && !"".equals(desc)) addElem(dom, node, "description", desc);
                                addElem(dom, node, "lastupdate", WBUtils.dateFormat(tp.getDbdata().getLastupdate()));
                                root.appendChild(node);
                                
                                Element emn=null;
                                if (!"".equals(base.getAttribute("imgupdate", "").trim()))
                                {
                                    emn = dom.createElement("imgupdate");
                                    emn.setAttribute("src",  webWorkPath +"/"+ base.getAttribute("imgupdate").trim());
                                    if (!"".equals(base.getAttribute("altupdate", "").trim()))
                                        emn.setAttribute("alt", base.getAttribute("altupdate").trim());
                                    else emn.setAttribute("alt", reqParams.getLocaleString("msgUpdate"));
                                } 
                                else 
                                {
                                    emn = dom.createElement("btnupdate");
                                    if (!"".equals(base.getAttribute("btnupdate", "").trim()))
                                        emn.appendChild(dom.createTextNode(base.getAttribute("btnupdate").trim()));
                                    else emn.appendChild(dom.createTextNode(reqParams.getLocaleString("msgUpdate"))); 
                                }
                                root.appendChild(emn);                                
                                addElem(dom, root, "msgdescription", reqParams.getLocaleString("msgDescription"));
                                addElem(dom, root, "msgadded", reqParams.getLocaleString("msgAdded"));
                                addElem(dom, root, "msglastupdate", reqParams.getLocaleString("msgLastupdate"));
                                break;
                            }
                        }
                    }
                }
            }
            return dom;
        }
        catch (Exception e) { AFUtils.log(e, "Error while generating DOM in resource "+ base.getResourceType().getName() +" with identifier " + base.getId() + " - " + base.getTitle(), true); }
        return null;         
    }**/
    
    /**
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */      
    private void addElem(org.w3c.dom.Document doc, org.w3c.dom.Node parent, String elemName, String elemValue)
    {
        org.w3c.dom.Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }    

    /**
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */     
    private void addElem(org.w3c.dom.Document doc, org.w3c.dom.Element parent, String elemName, String elemValue)
    {
        org.w3c.dom.Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }    
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    /*
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            org.w3c.dom.Document dom=getDom(request, response, paramRequest);
            if(dom!=null) response.getWriter().println(SWBUtils.XML.domToXml(dom));
        }
        catch(Exception e){ log.error(e); }        
    }    
    **/
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    /*
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            Document dom =getDom(request, response, paramRequest);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));            
            if(dom != null)  {
                response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(e); }
    }
    **/
    /**
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */    
    /*
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        User user=response.getUser();
        WebPage topic=response.getTopic();
        Document dom = SWBUtils.XML.xmlToDom(base.getData(user));
        if(dom==null)
        {
            dom = AFUtils.getInstance().getNewDocument();
            dom.appendChild(dom.createElement("resource"));            
        }
        String action = response.getAction();
        if("add".equals(action)) 
        {
            Element elem=null;
            if(dom.getElementsByTagName(topic.getMap().getId()).getLength() < 1)
            {
                elem=dom.createElement(topic.getMap().getId());
                dom.getFirstChild().appendChild(elem);
            }
            elem = dom.createElement("favoritetopic");
            elem.appendChild(dom.createTextNode(topic.getId()));
            addElem(dom, elem, "added", WBUtils.dateFormat(new java.sql.Timestamp(System.currentTimeMillis())));
            dom.getElementsByTagName(topic.getMap().getId()).item(0).appendChild(elem);
        }
        else 
        {
            String id = null != request.getParameter("ftp_id") && !"".equals(request.getParameter("ftp_id").trim()) ? request.getParameter("ftp_id").trim() : "";
            if(!"".equals(id))
            {
                NodeList favorites = dom.getElementsByTagName(topic.getMap().getId()).item(0).getChildNodes();
                for(int i=0;  i < favorites.getLength(); i++)
                {
                    if("favoritetopic".equals(favorites.item(i).getNodeName()))
                    {
                        Node node=favorites.item(i);
                        if(id.equals(node.getFirstChild().getNodeValue()))
                        {
                            if("update".equals(action)) 
                            {
                                boolean bOk=false;
                                NodeList detail=node.getChildNodes();
                                for(int j=0;  j < detail.getLength(); j++)
                                {
                                    if("description".equals(detail.item(j).getNodeName()))
                                    {
                                        bOk=true;
                                        if(request.getParameter("description")!=null)
                                            detail.item(j).getFirstChild().setNodeValue(request.getParameter("description"));
                                    }
                                }  
                                if(!bOk && request.getParameter("description")!=null) 
                                {
                                    addElem(dom, node, "description", request.getParameter("description"));
                                }
                                response.setRenderParameter("ftp_act", "ftp_step2");
                                response.setRenderParameter("ftp_id", id);
                            }
                            else if("remove".equals(action)) 
                                node.getParentNode().removeChild(node);
                            break;
                        }
                    }
                }
            }
        }   
        base.setData(user, AFUtils.getInstance().DomtoXml(dom));
    }
     * */
}

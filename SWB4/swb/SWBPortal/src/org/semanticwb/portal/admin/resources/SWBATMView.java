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
import org.w3c.dom.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AssMember;
import org.semanticwb.model.Association;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * Recurso para la administraci�n de WebBuilder que llama al applet para que
 * muestre los t�picos que componen al mapa de t�picos seleccionado.
 *
 * WebBuilder administration resource that calls to applet that shows the related
 * topics of the selected topic map.
 * @author Javier Solis Gonzalez
 */
public class SWBATMView extends SWBATMAdmin
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBATMView.class);

    /**
     * Creates a new instance of WBTree.
     */
    public SWBATMView()
    {
    }
    
    /**
     * Servicio 180.
     * 
     * @param user the user
     * @param src the src
     * @return the topic map4 adm
     * @return
     */
    @Override
    public Document getTopicMap4Adm(User user, Document src)
    {
        Document dom = null;
        try
        {
            String topicmap = null;
            if (src.getElementsByTagName("topicmap").getLength() > 0)
                topicmap = src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue();

            if (topicmap != null)
            {
                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);

                //TODO:
                //WebSite tm = AdmFilterMgr.getInstance().getTopicMapFiltered(SWBContext.getWebSite(topicmap),user);
                WebSite tm = SWBContext.getWebSite(topicmap);
                if (tm == null) return getError(5);

                Element map = dom.createElement("map");
                res.appendChild(map);

                //*************************************************************
                ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
                PrintWriter ptr = new PrintWriter(sw);

                ptr.print("cgi\n");
                ptr.print(SWBPortal.getDistributorPath() + "/"+tm+"/\n");

                Iterator it;

                it = tm.listWebPages();
                while (it.hasNext())
                {
                    WebPage t1 = (WebPage) it.next();
                    if(!t1.isVisible())continue;
                    if (t1.getId() != null)
                    {
                        ptr.print("WebPage\n");
                        ptr.print("i:" + t1.getId() + "\n");
                        ptr.print("u:" + t1.getUrl() + "\n");
                        ptr.print("d:" + t1.getDisplayName(user.getLanguage()) + "\n");
                        ptr.print("a:" + (t1.isActive()?1:0) + "\n");
                        ptr.print("r:" + (t1.isDeleted()?1:0) + "\n");

                        //TODO:
//                        Iterator na = t1.getBaseNames().iterator();
//                        while (na.hasNext())
//                        {
//                            BaseName bn = (BaseName) na.next();
//                            ptr.print("n:" + bn.getBaseNameString() + "\n");
//                            if (bn.getScope() != null)
//                            {
//                                Iterator sit = bn.getScope().getTopicRefs().values().iterator();
//                                while (sit.hasNext())
//                                {
//                                    ptr.print("s:" + ((WebPage) (sit.next())).getId() + "\n");
//                                }
//                            }
//                            Iterator va = bn.getVariants().iterator();
//                            while (va.hasNext())
//                            {
//                                Variant v = (Variant) va.next();
//                                VariantName vn = v.getVariantName();
//                                ptr.print("v:" + vn.getResource() + "\n");
//                            }
//                        }
                    }
                }

                it = tm.listWebPages();
                while (it.hasNext())
                {
                    WebPage t1 = (WebPage) it.next();
                    if(!t1.isVisible())continue;
                    if (t1.getId() != null)
                    {
                        WebPage type = (WebPage) t1.getParent();
                        ptr.print("Association\n");
                        ptr.print("t:null\n");
                        ptr.print("n:null\n");
                        ptr.print("r:null\n");
                        ptr.print("p:" + type.getId() + "\n");
                        ptr.print("r:null\n");
                        ptr.print("p:" + t1.getId() + "\n");

                        Iterator itaux = t1.listVirtualParents();
                        while (itaux.hasNext())
                        {
                            type = (WebPage) itaux.next();
                            ptr.print("Association\n");
                            ptr.print("t:null\n");
                            ptr.print("n:null\n");
                            ptr.print("r:null\n");
                            ptr.print("p:" + type.getId() + "\n");
                            ptr.print("r:null\n");
                            ptr.print("p:" + t1.getId() + "\n");
                        }
                    }
                }

                it = tm.listAssociations();
                while (it.hasNext())
                {
                    Association ass = (Association) it.next();
                    if (ass != null)
                    {
                        if (ass.getType() != null)
                        {
                            ptr.print("Association\n");
                            ptr.print("t:" + ass.getType().getId() + "\n");
                            ptr.print("n:" + ass.getType().getDisplayTitle(user.getLanguage()) + "\n");

                            Iterator itaux = ass.listMembers();
                            while (itaux.hasNext())
                            {
                                AssMember mem = (AssMember) itaux.next();
                                if(mem.getRole()!=null)
                                {
                                    ptr.print("r:" + mem.getRole().getId() + "\n");
                                }else
                                {
                                    ptr.print("r:null");
                                }
//                                Iterator itpla = mem.getMember();
//                                while (itpla.hasNext())
//                                {
//                                    Topic tpla = (Topic) itpla.next();
//                                    //System.out.println("p:"+trim(topicmap.getSGMLId(tpla)));
                                    ptr.print("p:" + mem.getMember().getId() + "\n");
//                                }
                            }
                        } else
                        {
                            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "log_Gateway_getService_AssociationFoundwithoutType") + ":" + ass.getId());
                        }
                    }
                }
                ptr.flush();

                map.appendChild(dom.createCDATASection(sw.toString()));

            } else
                return getError(4);
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_getTopicServiceError"), e);
        }
        return dom;
    }    
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String act=request.getParameter("act");
        String tp=paramsRequest.getWebPage().getWebSite().getHomePage().getId();
        String tm=paramsRequest.getWebPage().getWebSiteId();
        String urlbase=null;
        String urlpost=null;
        if(request.getParameter("tm")!=null)tm=request.getParameter("tm");
        if(request.getParameter("tp")!=null)tp=request.getParameter("tp");
        if(request.getParameter("urlbase")!=null)urlbase=request.getParameter("urlbase");
        if(request.getParameter("urlpost")!=null)urlpost=request.getParameter("urlpost");

        out.println("<applet id=\"appttmadmin\" name=\"appttmadmin\" code=\"applets.mapsadm.TMWBApplet.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/TMWBView.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setMode("gateway");
        url.setCallMethod(url.Call_DIRECT);
        //url.setParameter("id",request.getParameter("id"));
        //url.setParameter("tp",paramsRequest.getTopic().getId());
        out.println("<param name =\"cgipath\" value=\""+url+"\">");
        if(urlbase!=null)out.println("<param name =\"urlbase\" value=\""+urlbase+"\">");
        if(urlpost!=null)out.println("<param name =\"urlpost\" value=\""+urlpost+"\">");
        out.println("<param name =\"jsess\" value=\""+request.getSession().getId()+"\">");
        out.println("<param name=\"foreground\" value=\"3f88b4\">");
        out.println("<param name=\"background\" value=\"edf2f3\">");
        out.println("<param name=\"foregroundSelection\" value=\"ffffff\">");
        out.println("<param name=\"backgroundSelection\" value=\"666699\">");
        out.println("<param name=\"locale\" value=\""+paramsRequest.getUser().getLanguage()+"\">");
        out.println("<param name=\"TM\" value=\""+tm+"\">");
        out.println("<param name=\"TP\" value=\""+tp+"\">");
/*        
        if(request.getParameter("tm")!=null)
            out.println("<PARAM NAME=\"TM\" VALUE=\""+request.getParameter("tm")+"\">");
        if(request.getParameter("tp")!=null)
            out.println("<PARAM NAME=\"TP\" VALUE=\""+request.getParameter("tp")+"\">");
*/
        out.println("</applet>");
    }
    
}

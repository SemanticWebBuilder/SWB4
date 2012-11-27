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

import com.hp.hpl.jena.rdf.model.Statement;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.w3c.dom.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AssMember;
import org.semanticwb.model.Association;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Topic;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * Recurso para la administraci�n de WebBuilder que llama al applet para
 * administrar los mapa de t�picos.
 *
 * WebBuilder administration resource that calls an applet for topic maps
 * administration.
 * @author Javier Solis Gonzalez
 */
public class SWBATMAdmin extends GenericResource
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBATMAdmin.class);
    
    /** The agzip. */
    boolean agzip=true;      
    
    /**
     * Creates a new instance of WBTree.
     */
    public SWBATMAdmin()
    {
        agzip = SWBPlatform.getEnv("swb/responseGZIPEncoding","true").equalsIgnoreCase("true");
    }
    
    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        if(paramsRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramsRequest);
        }else super.processRequest(request,response,paramsRequest);
    }
    
    
    /**
     * Gets the service.
     * 
     * @param cmd the cmd
     * @param src the src
     * @param user the user
     * @param request the request
     * @param response the response
     * @param tp the tp
     * @return the service
     */
    private Document getService(String cmd, Document src, User user, HttpServletRequest request, HttpServletResponse response, Topic tp)
    {
        System.out.println("Service mi entrada:"+cmd);
        if (cmd.equals("getTopicMaps"))
        {
            return getTopicMaps(user, src);
        }else if (cmd.equals("createTopic"))
        {
            return createTopic(user, src);
        }else if (cmd.equals("updateTopic"))
        {
            return updateTopic(user, src);
        }else if (cmd.equals("deleteTopic"))
        {
            return deleteTopic(user, src);
        }else if (cmd.equals("setStatusTopic"))
        {
            return setStatusTopic(user, src);
        }else if (cmd.equals("getLanguagesList"))
        {
            return getLanguagesList(user, src);
        }else if (cmd.equals("getTopicMap4Adm"))
        {
            return getTopicMap4Adm(user, src);
        }else if (cmd.equals("setTopicMap4Adm"))
        {
            return setTopicMap4Adm(user, src);
        } else
        {
            return getError(3);
        }
    }
    
    
    /**
     * Servicio 1.
     * 
     * @param user the user
     * @param src the src
     * @return the topic maps
     * @return
     */
    public Document getTopicMaps(User user, Document src)
    {
        Vector vect = new Vector();
        Document dom = null;
        try
        {
            dom = SWBUtils.XML.getNewDocument();
            Element res = dom.createElement("res");
            dom.appendChild(res);
            Iterator it = SWBContext.listWebSites();
            while (it.hasNext())
            {
                WebSite tm = (WebSite) it.next();
                //TODO:
                //if(tm==SWBContext.getGlobalWebSite() || !AdmFilterMgr.getInstance().haveAccess2Topic(user, tm.getHome()))continue;
                if(tm==SWBContext.getGlobalWebSite())continue;
                if (!tm.isDeleted())
                {
                    Element topicmap = dom.createElement("WebSite");
                    res.appendChild(topicmap);
                    addElement("id", tm.getId(), topicmap);
                    if(tm.getCreator()!=null)addElement("idadm", "" + tm.getCreator().getURI(), topicmap);
                    else addElement("idadm", "1", topicmap);
                    addElement("title", tm.getTitle(), topicmap);
                    if(tm.getHomePage()!=null)addElement("home", tm.getHomePage().getId(), topicmap);
                    addElement("description", tm.getDescription(), topicmap);
                    addElement("active", "" + (tm.isActive()?1:0), topicmap);
                    addElement("lastupdate", "" + tm.getUpdated(), topicmap);
                    addElement("created", "" + tm.getCreated(), topicmap);
                    if(tm.getLanguage()!=null)addElement("language", tm.getLanguage().getId(), topicmap);
                    else addElement("language", "es", topicmap);
                    //addElement("size",""+SWBContext.getWebSite(tm.getId()).getTopics().size(),topicmap);
                    addElement("size", "" + 0, topicmap);
                }
            }
        } catch (Exception e)
        {
            log.error("Error Gateway getTopicMaps", e);
        }
        return dom;
    }
    
    /**
     * Servicio 41.
     * 
     * @param user the user
     * @param src the src
     * @return the languages list
     * @return
     */
    public Document getLanguagesList(User user, Document src)
    {
        Document dom = null;
        try
        {
            String tm = src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue();
            Iterator listaTem = SWBContext.getWebSite(tm).listLanguages();
            if (listaTem.hasNext())
            {
                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);

                Element TempEle = dom.createElement("Lenguajes");
                res.appendChild(TempEle);
                while (listaTem.hasNext())
                {
                    Language templateactual = (Language) listaTem.next();

                    Element AdmUs = dom.createElement("Lenguaje");
                    addElement("id", templateactual.getId(), AdmUs);
                    addElement("name", templateactual.getDisplayTitle(user.getLanguage()), AdmUs);
                    TempEle.appendChild(AdmUs);
                }
            }
        } catch (Exception e)
        {
            log.error("Error Gateway getLanguagesListError" + "...", e);
        }
        return dom;
    }    
    
    /**
     * Servicio 71.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document createTopic(User user, Document src)
    {
        Document dom = null;
        String result = "0";
        try
        {
            WebSite tm = SWBContext.getWebSite(src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue());
            WebPage tp = tm.getWebPage(src.getElementsByTagName("topicid").item(0).getFirstChild().getNodeValue());
            String id = src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
            NodeList lista = src.getElementsByTagName("name");
            if (!tm.hasWebPage(id))
            {
                Element elm = (Element) lista.item(0);
                WebPage page=tm.createWebPage(id);
                page.setTitle(elm.getAttribute("value"),elm.getAttribute("scope"));
                page.setParent(tp);
                result = "1";
                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);

                addElement("TopicCreated", result, res);
            } else
                return getError(8);
        } catch (Exception e)
        {
            log.error("Error Gateway createTopic" + "...", e);
        }
        return dom;
    }    
    
    /**
     * Servicio 72.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document updateTopic(User user, Document src)
    {
        Document dom = null;
        String result = "0";
        try
        {
            WebSite tm = SWBContext.getWebSite(src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue());
            WebPage nuevo = tm.getWebPage(src.getElementsByTagName("topicid").item(0).getFirstChild().getNodeValue());
            //String id = src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
            NodeList lista = src.getElementsByTagName("name");
            if (nuevo != null)
            {
                //ArrayList basenames = new ArrayList();
                for (int i = 0; i < lista.getLength(); i++)
                {
                    Element elm = (Element) lista.item(i);
                    String title= elm.getAttribute("value");
                    String scope= elm.getAttribute("scope");
                    System.out.println("ut: title:"+title+" scope:"+scope);
                    if(scope!=null && !scope.equals("IDM_WB"))
                    {
                        if(scope.startsWith("IDM_WB"))scope=scope.substring(6);
                        nuevo.setTitle(title, scope);
                    }else
                    {
                        nuevo.setTitle(title);
                    }
                    //TODO:
                    /*
                    ArrayList variants = new ArrayList();
                    NodeList lvar = elm.getElementsByTagName("variant");
                    for (int j = 0; j < lvar.getLength(); j++)
                    {
                        Element var = (Element) lvar.item(j);
                        Variant vari = new Variant();
                        VariantName vn = new VariantName();
                        vn.setResourceData((String) var.getFirstChild().getNodeValue());
                        vari.setVariantName(vn);
                        variants.add(vari);
                    }
                    bn.setVariants(variants);
                    */

                }
                //nuevo.setBaseNames(basenames);

                //new TopicSrv().updateTopic(nuevo, user.getId());
                //nuevo.getDbdata().setIdAdm(user.getId());
                //tm.update2DB();
                //DBAdmLog.getInstance().saveTopicLog(user.getId(), tm.getId(), nuevo.getId(), "update", 0, SWBUtils.TEXT.getLocaleString("locale_Gateway", "admlog_Gateway_getService_TopicUpdated") + " " + nuevo.getId());
                result = "1";

                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);


                addElement("TopicUpdated", result, res);
            } else
                return getError(8);
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_TopicAttributesUpdateError") + "...", e);
        }
        return dom;
    }    
    
    /**
     * Servicio 73.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document deleteTopic(User user, Document src)
    {
        Document dom = null;
        String result = "0";
        try
        {

            WebSite tm = SWBContext.getWebSite(src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue());
            WebPage nuevo = tm.getWebPage(src.getElementsByTagName("topicid").item(0).getFirstChild().getNodeValue());
            boolean back = src.getElementsByTagName("back").getLength() > 0;
            if (nuevo != null)
            {
                if (back)
                {
                    nuevo.setDeleted(false);
                    result = "0";
                } else
                {
                    nuevo.setDeleted(true);
                    result = "1";
                }

                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);

                addElement("TopicDeleted", result, res);
            } else
                return getError(3);
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_TopicRemoveError") + "...", e);
            return getError(3);
        }
        return dom;
    }

    /**
     * Servicio 74.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document setStatusTopic(User user, Document src)
    {
        Document dom = null;
        String result = "0";
        try
        {

            WebSite tm = SWBContext.getWebSite(src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue());
            WebPage nuevo = tm.getWebPage(src.getElementsByTagName("topicid").item(0).getFirstChild().getNodeValue());
            int active = Integer.parseInt(src.getElementsByTagName("active").item(0).getFirstChild().getNodeValue());
            if (nuevo != null)
            {
                nuevo.setActive(active==1);
                result = "1";

                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);


                addElement("TopicStatusSeted", result, res);
            } else
                return getError(8);
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_TopicStatusChangeError") + "...", e);
        }
        return dom;
    }    
    
    /**
     * Servicio 180.
     * 
     * @param user the user
     * @param src the src
     * @return the topic map4 adm
     * @return
     */
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
                ptr.print(SWBPortal.getDistributorPath() + "/"+tm.getId()+"/\n");

                Iterator it;

                it = tm.listWebPages();
                while (it.hasNext())
                {
                    WebPage t1 = (WebPage) it.next();
                    if (t1.getId() != null)
                    {
                        ptr.print("Topic\n");
                        ptr.print("i:" + t1.getId() + "\n");
                        ptr.print("d:" + t1.getDisplayName(user.getLanguage()) + "\n");
                        ptr.print("a:" + (t1.isActive()?1:0) + "\n");
                        ptr.print("r:" + (t1.isDeleted()?1:0) + "\n");

                        Iterator<Statement> stit=t1.getSemanticObject().getRDFResource().listProperties(Descriptiveable.swb_title.getRDFProperty());
                        while(stit.hasNext())
                        {
                            Statement st=stit.next();
                            String title=st.getString();
                            String lang=st.getLanguage();
                            ptr.print("n:" + title + "\n");
                            if(lang!=null)ptr.print("s:IDM_WB" + lang + "\n");
                        }

                        //ptr.print("n:" + t1.getTitle() + "\n");
                        //TODO
                        /*
                        Iterator na = t1.getBaseNames().iterator();
                        while (na.hasNext())
                        {
                            BaseName bn = (BaseName) na.next();
                            ptr.print("n:" + bn.getBaseNameString() + "\n");
                            if (bn.getScope() != null)
                            {
                                Iterator sit = bn.getScope().getTopicRefs().values().iterator();
                                while (sit.hasNext())
                                {
                                    ptr.print("s:" + ((Topic) (sit.next())).getId() + "\n");
                                }
                            }
                            Iterator va = bn.getVariants().iterator();
                            while (va.hasNext())
                            {
                                Variant v = (Variant) va.next();
                                VariantName vn = v.getVariantName();
                                ptr.print("v:" + vn.getResource() + "\n");
                            }
                        }
                        */
                    }
                }

                it = tm.listWebPages();
                while (it.hasNext())
                {
                    WebPage t1 = (WebPage) it.next();
                    if (t1.getId() != null)
                    {
                        WebPage type = (WebPage) t1.getParent();
                        if(type!=null)
                        {
                            ptr.print("Association\n");
                            ptr.print("t:null\n");
                            ptr.print("n:null\n");
                            ptr.print("r:null\n");
                            ptr.print("p:" + type.getId() + "\n");
                            ptr.print("r:null\n");
                            ptr.print("p:" + t1.getId() + "\n");
                        }

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
     * Servicio 181.
     * 
     * @param user the user
     * @param src the src
     * @return the document
     * @return
     */
    public Document setTopicMap4Adm(User user, Document src)
    {
        Document dom = null;
        try
        {
            String topicmap = null;
            String data = null;
            if (src.getElementsByTagName("topicmap").getLength() > 0)
                topicmap = src.getElementsByTagName("topicmap").item(0).getFirstChild().getNodeValue();

            if (src.getElementsByTagName("data").getLength() > 0)
                data = src.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();

            System.out.println("data:"+data);

            if (topicmap != null && data != null)
            {
                dom = SWBUtils.XML.getNewDocument();
                Element res = dom.createElement("res");
                dom.appendChild(res);

                WebSite tm = SWBContext.getWebSite(topicmap);
                if (tm == null) return getError(5);

                Element map = dom.createElement("ok");
                res.appendChild(map);

                //System.out.println(data);

//                Topic tpl = tm.getWebPage("CNF_WBTemplate");
//                Topic rule = tm.getWebPage("CNF_WBRule");
//                Topic pflow = tm.getWebPage("CNF_WBPFlow");

                int type = 1;

                BufferedReader in = null;
                try
                {
                    in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));
                    if (in != null)
                    {
                        String aux;
                        boolean update = false;
                        boolean remove = false;
                        boolean removeR = false;
                        boolean add = false;
                        boolean topic = false;
                        boolean assoc = false;
                        boolean isnew = false;
                        boolean bad = false;
                        WebPage auxtopic = null;
                        int asstype = 0;

                        int nass = 0;
                        WebPage mem[] = new WebPage[2];
                        String assName = "";
                        String assTypeId = "";
                        String assRole[] = new String[2];

                        String auxname = null;

                        while ((aux = in.readLine()) != null)
                        {
                            //System.out.println(aux);
                            if (aux.startsWith("Type:"))
                            {
                                type = Integer.parseInt(aux.substring(5));
                            }
                            if (aux.equals("Add Topic"))
                            {
                                remove = false;
                                removeR = false;
                                update = false;
                                add = true;
                                topic = true;
                                assoc = false;
                                isnew = true;
                            }
                            if (aux.equals("Remove Topic"))
                            {
                                remove = true;
                                removeR = false;
                                update = false;
                                add = false;
                                topic = true;
                                assoc = false;
                                isnew = true;
                            }
                            if (aux.equals("RRemove Topic"))
                            {
                                remove = false;
                                removeR = true;
                                update = false;
                                add = false;
                                topic = true;
                                assoc = false;
                                isnew = true;
                            } else if (aux.equals("Update Topic"))
                            {
                                update = true;
                                remove = false;
                                removeR = false;
                                add = false;
                                topic = true;
                                assoc = false;
                                isnew = true;
                            } else if (aux.equals("Update Association"))
                            {
                                update = true;
                                remove = false;
                                removeR = false;
                                add = false;
                                topic = false;
                                assoc = true;
                                isnew = true;
                                assName = "";
                                assTypeId = "";
                            } else if (aux.equals("Topic"))
                            {
                                update = false;
                                remove = false;
                                removeR = false;
                                add = false;
                                topic = true;
                                assoc = false;
                                isnew = true;
                            } else if (aux.equals("Association"))
                            {
                                update = false;
                                remove = false;
                                removeR = false;
                                add = false;
                                topic = false;
                                assoc = true;
                                isnew = true;
                                assName = "";
                                assTypeId = "";
                            } else
                                isnew = false;

                            if (topic && (!isnew))
                            {
                                if (aux.startsWith("i:"))
                                {
                                    if (!(aux.substring(2).length() == 0))
                                    {
                                        bad = false;
                                        //System.out.println(aux.substring(2));
                                        if (remove) 
                                        {
                                            //TODO: validar no eliminar dependencias
                                            WebPage page=tm.getWebPage(aux.substring(2));
                                            if(page!=null)page.remove();
                                        }
                                        if (removeR)
                                        {
                                            WebPage page=tm.getWebPage(aux.substring(2));
                                            if(page!=null)page.remove();
                                        }
                                        if (add)
                                        {
                                            WebPage page=tm.createWebPage(aux.substring(2));
                                        }
                                        if (update)
                                        {
                                            auxtopic = tm.getWebPage(aux.substring(2));
                                            if (auxtopic == null)
                                            {
                                                auxtopic = tm.createWebPage(aux.substring(2));
                                            } else
                                            {
                                                //Eliminar titulos
                                                System.out.println("Eliminar Titulos...");
                                                auxtopic.setTitle(null);
                                                Iterator<Language> langit=auxtopic.getWebSite().listLanguages();
                                                while(langit.hasNext())
                                                {
                                                    auxtopic.setTitle(null,langit.next().getId());
                                                }
                                                auxtopic.getSemanticObject().removeProperty(Descriptiveable.swb_title);
                                                if (type > 1)
                                                {
                                                    auxtopic.removeParent();
                                                    auxtopic.removeAllVirtualParent();
                                                    Iterator<AssMember> it=auxtopic.listAssMembers();
                                                    while(it.hasNext())
                                                    {
                                                        it.next().remove();
                                                    }
                                                }
                                            }
                                        }
                                    } else
                                        bad = true;
                                } else if (aux.startsWith("n:") && !bad)
                                {
                                    if(auxname!=null)
                                    {
                                        System.out.println("setTitle:"+auxname);
                                        auxtopic.setTitle(auxname);    
                                    }
                                    auxname=aux.substring(2);
                                } else if (aux.startsWith("s:") && !bad)
                                {
                                    String lang=aux.substring(2);
                                    if(lang.startsWith("IDM_WB"))lang=lang.substring(6);
                                    if(lang.length()>0)
                                    {
                                        System.out.println("setTitle:"+auxname+" lang:"+lang);
                                        auxtopic.setTitle(auxname, lang);
                                        auxname=null;
                                    }
                                } else if (aux.startsWith("v:") && !bad)
                                {
                                    //TODO:
                                    //System.out.println("v:"+aux.substring(2));
                                    /*
                                    Variant v = new Variant();
                                    VariantName vn = new VariantName();
                                    vn.setResourceData(aux.substring(2));
                                    v.setVariantName(vn);
                                    auxname.getVariants().add(v);
                                    //System.out.println(aux.substring(2));
                                     */
                                }
                            }

                            if (assoc && (!isnew))
                            {
                                if (aux.startsWith("t:"))
                                {
                                    bad = false;
                                    nass = 0;
                                    if (!(aux.substring(2).length() == 0))
                                    {
                                        assTypeId = aux.substring(2);
                                        asstype = 3;
                                    } else
                                    {
                                        asstype = 0;
                                        assTypeId = "";
                                    }
                                }
                                if (aux.startsWith("n:"))
                                {
                                    if (!(aux.substring(2).length() == 0))
                                    {
                                        assName = aux.substring(2);
                                    } else
                                    {
                                        assName = "";
                                    }
                                } else if (aux.startsWith("r:"))
                                {
                                    if (!(aux.substring(2).length() == 0))
                                    {
                                        assRole[nass] = aux.substring(2);
                                    } else
                                        assRole[nass] = "";
                                } else if (aux.startsWith("p:"))
                                {
                                    mem[nass] = tm.getWebPage(aux.substring(2));
                                    if (mem[nass] == null) bad = true;
                                    nass++;
                                    if (nass == 2 && !bad)
                                    {
                                        if (asstype == 0)
                                        {
                                            //TopicImpl[] arr=new TopicImpl[1];
                                            //arr[0]=mem[0];
                                            //mem[1].setTypes(arr);
                                            if (!mem[1].isParentof(mem[0]))
                                            {
                                                if(mem[1].getParent()==null)
                                                    mem[1].setParent(mem[0]);
                                                else
                                                    mem[1].addVirtualParent(mem[0]);
                                            }
                                            //System.out.println("Addtype:"+mem[0].getDisplayName());
                                        } else
                                        {
                                            Association as=tm.createAssociation();
                                            as.setType(tm.getWebPage(assTypeId));
                                            
                                            AssMember me = AssMember.ClassMgr.createAssMember(tm);
                                            as.addMember(me);
                                            me.setMember(mem[0]);
                                            me.setRole(tm.getWebPage(assRole[0]));

                                            me = AssMember.ClassMgr.createAssMember(tm);
                                            as.addMember(me);
                                            me.setMember(mem[1]);
                                            me.setRole(tm.getWebPage(assRole[1]));

                                            //System.out.println("Addmember:"+mem[0].getDisplayName());
                                            //System.out.println("Addmember:"+mem[1].getDisplayName());
                                        }
                                    }
                                }
                            }
                        }
                        if(auxname!=null)
                        {
                            System.out.println("setTitle:"+auxname+" fin");
                            auxtopic.setTitle(auxname);
                        }                        
                    }
                } catch (Exception ex)
                {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_UpdateTopicError"), ex);
                    return getError(3);
                }
//                if (tm.isDBSyncronized())
//                    tm.update2DB();
//                else
//                    TopicMgr.getInstance().writeTopicMap(tm, "e:/default.xtm.xml");
            } else
                return getError(4);
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_Gateway", "error_Gateway_getService_UpdateTopicServiceError"), e);
            return getError(3);
        }
        return dom;
    }    
    
    
    /**
     * Adds the node.
     * 
     * @param node the node
     * @param id the id
     * @param name the name
     * @param parent the parent
     * @return the element
     */
    private Element addNode(String node, String id, String name, Element parent)
    {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        return ret;
    }

    /**
     * Adds the element.
     * 
     * @param name the name
     * @param value the value
     * @param parent the parent
     * @return the element
     */
    private Element addElement(String name, String value, Element parent)
    {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }    
    
 
    /**
     * Find vector.
     * 
     * @param vvector the vvector
     * @param id the id
     * @return true, if successful
     * @return
     */    
    public boolean FindVector(Vector vvector, String id)
    {
        boolean regresa = false;
        for (int i = 0; i < vvector.size(); i++)
        {
            if (id.equals(vvector.elementAt(i)))
            {
                regresa = true;
                break;
            }
        }
        return regresa;
    }    
    
    /**
     * Gets the error.
     * 
     * @param id the id
     * @return the error
     * @return
     */
    public Document getError(int id)
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
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
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

        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();

        if (cmd == null)
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String ret;
        
        //WebSite tm=SWBContext.getWebSite(request.getParameter("tm"));
        //if(tm!=null)
        {
            //Topic tp=tm.getWebPage(request.getParameter("tp"));
            Document res = getService(cmd, dom, paramsRequest.getUser(), request, response,null);
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
            out.print(new String(ret.getBytes()));
            out.flush();
            out.close();            
        }
        
    }
    
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String act=request.getParameter("act");
        //out.println("<table border=\"0\" cellspacing=\"0\" height=\"100%\" cellpadding=\"0\" width=\"100%\"><tr><td width=\"100%\" height=\"100%\">");
        out.println("<applet id=\"appttmadmin\" name=\"appttmadmin\" code=\"applets.mapsadm.TMWBAdmin.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplTMAdmin.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"100%\" height=\"100%\">");
        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setMode("gateway");
        url.setCallMethod(url.Call_DIRECT);
        //url.setParameter("id",request.getParameter("id"));
        //url.setParameter("tp",paramsRequest.getTopic().getId());
        out.println("<param name =\"cgipath\" value=\""+url+"\">");
        out.println("<param name =\"jsess\" value=\""+request.getSession().getId()+"\">");
        out.println("<param name=\"foreground\" value=\"3f88b4\">");
        out.println("<param name=\"background\" value=\"edf2f3\">");
        out.println("<param name=\"foregroundSelection\" value=\"ffffff\">");
        out.println("<param name=\"backgroundSelection\" value=\"666699\">");
        out.println("<param name=\"locale\" value=\""+paramsRequest.getUser().getLanguage()+"\">");
        if(request.getParameter("tm")!=null)
            out.println("<param name=\"TM\" value=\""+request.getParameter("tm")+"\">");
        if(request.getParameter("tp")!=null)
            out.println("<param name=\"TP\" value=\""+request.getParameter("tp")+"\">");
        out.println("</applet>");
        //out.println("</td></tr></table>");
    }
    
    
}

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
package org.semanticwb.portal.resources;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * Muestra un menu desde Home y se abre segun la navegación.
 * 
 * @author  Javier Solis
 * @modified by  Jorge Jiménez
 */
public class WBMenuMap extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBMenuMap.class);

    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The web work path. */
    String webWorkPath = "/work";
    
    /** The path. */
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBMenuMap/";
    //private int ancho=10;
    /** The nsup. */
    private int nsup=0;
    
    /** The ninf. */
    private int ninf=0;
    
    /** The nini. */
    private int nini=0;
    
    /** The bro. */
    private boolean bro=true;
    
    /** The bro4ch. */
    private boolean bro4ch=false;

    /**
     * Asigna la información de la base de datos al recurso.
     * 
     * @param base the new resource base
     */
    public void setResourceBase(Resource base)
    {
        //System.out.println("WBMenuMap");
        try
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }catch(Exception e)
        {
            log.debug("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        //System.out.println("WBMenuMap:"+base.getAttribute("template"));
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            //System.out.println("loading template...");
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path=webWorkPath + "/";
            }catch(Exception e)
            {
                log.debug("Error while loading resource template: "+base.getId(), e);
            }
        }
        if(tpl==null)
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBMenuMap/WBMenuMap.xslt"));
            }catch(Exception e)
            {
                log.debug("Error while loading default resource template: "+base.getId(), e);
            }
        }

        try
        {
            bro=Boolean.parseBoolean(base.getAttribute("bro","true"));
            bro4ch=Boolean.parseBoolean(base.getAttribute("bro4ch","false"));
            nsup=Integer.parseInt(base.getAttribute("nsup","-1"));
            ninf=Integer.parseInt(base.getAttribute("ninf","-1"));
            nini=Integer.parseInt(base.getAttribute("nini","0"));
        }catch(Exception e){log.debug(e);}
        //System.out.println("nsup:"+nsup);
        //System.out.println("ninf:"+ninf);
        //System.out.println("bro:"+bro);
        //System.out.println("bro4ch:"+bro4ch);
    }

    /**
     * Obtiene el resultado final del recurso en formato dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Resource base=paramRequest.getResourceBase();
        User user=paramRequest.getUser();
        WebPage basetp = paramRequest.getWebPage().getWebSite().getHomePage();
        WebPage topic = paramRequest.getWebPage();

        String basetopic = base.getAttribute("basetopic",null);
        
        if(basetopic!=null)
        {
            basetp = topic.getWebSite().getWebPage(basetopic);
        }

        if(basetp==null)
        {
            basetp = topic.getWebSite().getHomePage();
        }

        try
        {
            String lang = paramRequest.getUser().getLanguage();

            Document  dom = SWBUtils.XML.getNewDocument();
            Element el = dom.createElement("menu");
            el.setAttribute("path", path);
            dom.appendChild(el);

            int max=getLimits(basetp,topic,1,user);
            //System.out.println("max:"+max);

            getChilds(dom, el, basetp, topic, lang, 1, 1, user, max);

            return dom;
        }catch (Exception e)
        {
            log.debug("Error while generating DOM in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }

    /**
     * Gets the limits.
     * 
     * @param aux the aux
     * @param topic the topic
     * @param level the level
     * @param user the user
     * @return the limits
     */
    public int getLimits(WebPage aux, WebPage topic, int level, User user)
    {
        int max=level;
        Iterator <WebPage> it=aux.listChilds();  //CHECAR
        while(it.hasNext())
        {
            WebPage tp=it.next();
            if(tp.isActive() && (tp.isVisible() || tp.equals(topic) || tp.isParentof(topic)))
            {
                if(user.haveAccess(tp))
                {
                    if(tp.isParentof(topic) || tp.equals(topic))
                    {
                        return getLimits(tp, topic,level+1,user);
                    }else
                    {
                        max=level+1;
                    }
                }
            }
        }
        return max;
    }


    /**
     * Gets the childs.
     * 
     * @param dom the dom
     * @param nodo the nodo
     * @param aux the aux
     * @param topic the topic
     * @param lang the lang
     * @param level the level
     * @param rlevel the rlevel
     * @param user the user
     * @param max the max
     * @return the childs
     */
    public boolean getChilds(Document dom, Element nodo, WebPage aux, WebPage topic, String lang, int level, int rlevel, User user, int max)
    {
        //System.out.println("****************** enter"+level+":"+aux.getId()+" ***********************");
        boolean childs=false;
        Iterator <WebPage>it=aux.listVisibleChilds(lang);  //CHECAR
        while(it.hasNext())
        {
            WebPage tp=it.next();
            //if(tp.isActive() && (tp.isVisible() || tp==topic || tp.isParentof(topic)) && tp.checkSchedule(true))  //CHECAR
            {
                if(user.haveAccess(tp))
                {
                    childs=true;

                    String tpurl = tp.getUrl(lang,false);
                    Element ele = dom.createElement("node");
                    ele.setAttribute("id", tpurl);
                    ele.setAttribute("name", tp.getDisplayName(lang));
                    ele.setAttribute("path", tpurl);
                    ele.setAttribute("realLevel", ""+rlevel);
                    ele.setAttribute("level", ""+level);
                    ele.setAttribute("inPath", ""+tp.isParentof(topic));
                    if(tp.equals(topic)) ele.setAttribute("current", "true");
                    else ele.setAttribute("current", "false");
                    if (tpurl.startsWith("http://")||tpurl.startsWith("https://")) {
                        ele.setAttribute("target", "_blank");
                    } else if (tp.getTarget() != null && !"".equalsIgnoreCase(tp.getTarget())) {
                        ele.setAttribute("target", tp.getTarget());
                    } else ele.setAttribute("target", "_self");

                    int auxl=0;
                    if(((nsup<0 || rlevel<=nsup) || (ninf<0 || rlevel>=(max-ninf))))
                    {
                        if(!(topic.equals(tp) && tp.getParent()!=aux))
                        {
                            if(nini<=rlevel)
                            {
                                nodo.appendChild(ele);
                                auxl=1;
                                //System.out.println("node:"+tp.getId()+":"+level+":"+rlevel);
                            }else
                            {
                                ele=nodo;
                            }
                        }
                    }else
                    {
                        ele=nodo;
                    }

                    if(tp.isParentof(topic) || tp.equals(topic))
                    {
                        ele.setAttribute("open","true");
                        if(getChilds(dom,ele, tp, topic, lang, level+auxl, rlevel+1, user,max))
                        {
                            ele.setAttribute("childs","true");
                        }else
                        {
                            ele.setAttribute("childs","false");
                        }
                    }else
                    {
                        try
                        {
                            if(bro4ch)
                            {
                                //System.out.println("tp:"+tp.getId()+" topic:"+topic.getId()+":"+level+":"+rlevel+" max:"+max+" bro4ch");
                                if(!bro && (rlevel+1)<max && (!tp.isChildof(topic)||(tp.getParent()!=aux && (tp.getParent().equals(topic)||tp.equals(topic)))))nodo.removeChild(ele);
                            }else
                            {
                                //System.out.println("tp:"+tp.getId()+" topic:"+topic.getId()+":"+level+":"+rlevel+" max:"+max+" bro:"+bro);
                                if(!bro && (!tp.isChildof(topic)||(tp.getParent()!=aux && (tp.getParent().equals(topic)||tp.equals(topic)))))nodo.removeChild(ele);
                            }
                        }catch(Exception noe){}

                        ele.setAttribute("open","false");
                        if(haveChilds(aux, user))
                        {
                            ele.setAttribute("childs","true");
                        }else
                        {
                            ele.setAttribute("childs","false");
                        }
                    }
                }
            }
        }
        //System.out.println("****************** out"+level+":"+aux.getId()+" ***********************");
        return childs;
    }

    /**
     * Have childs.
     * 
     * @param aux the aux
     * @param user the user
     * @return true, if successful
     */
    public boolean haveChilds(WebPage aux, User user)
    {
        Iterator it=aux.listChilds();
        while(it.hasNext())
        {
            WebPage tp=(WebPage)it.next();
            if(user.haveAccess(tp))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el resultado final del recurso en formato xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            Document dom=getDom(request, response, paramRequest);
            if(dom!=null) response.getWriter().println(SWBUtils.XML.domToXml(dom));
        }
        catch(Exception e)
        {
            log.debug(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            Document dom=getDom(request, response, paramRequest);
//            System.out.println(AFUtils.getInstance().DomtoXml(dom));
//            if(dom != null)
//            {
//                System.out.println(AFUtils.getInstance().DomtoXml(dom));
//                System.out.println(AFUtils.getInstance().transformDom(tpl, dom));
//            }

            if(dom != null)  response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
        }
        catch(Exception e)
        { log.debug(e); }
    }
 }

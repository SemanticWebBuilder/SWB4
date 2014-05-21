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

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/** Esta clase se encarga de desplegar y administrar los indices tematicos bajo ciertos
 * criterios(configuraci�n de recurso), usa un archivo XSL. Es un recurso de contenidos que viene de la version 2 de Webbuilder.
 *
 * This class administrate and displays thematic index under criteria like
 * resource configuration, uses and XSL file. Is a content resource and comes from version 2 of
 * WebBuilder.
 *
 * @author :Jorge Alberto Jim�nez Sandoval (JAJS)
 */
public class TematicIndexXSLConfig extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(TematicIndexXSLConfig.class);

    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The workpath. */
    String workpath = "/work";
    
    /** The webpath. */
    String webpath = (String) SWBPlatform.getContextPath();
    
    /** The path. */
    String path =  webpath +"swbadmin/xsl/TematicIndexXSL/";
    
    /** The CF g_ webpage. */
    private static String CFG_WEBPAGE =  "inisection";
    
    /** The CF g_ sendparams. */
    private static String CFG_SENDPARAMS =  "sendparams";

    /**
     * Crea un nuevo objeto IndiceTematicoXSL.
     */
    public TematicIndexXSLConfig() {
    }

    /**
     * Asigna la informaci�n de la base de datos al recurso.
     * 
     * @param base the new resource base
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        try
        {
            super.setResourceBase(base);
            webpath=(String) (String) SWBPlatform.getContextPath();
            workpath=(String) SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path=workpath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/TematicIndexXSL/IndiceTematicoXSL.xslt"));
            }
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }

    /**
     * Obtiene el resultado final del recurso como dom-document.
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
        User user=paramRequest.getUser();
        Resource base=getResourceBase();
        WebPage wp = paramRequest.getWebPage();
        WebSite ws = wp.getWebSite();
        boolean sendparams = false;
        String querystring ="";
        if(base.getAttribute(CFG_WEBPAGE)!=null&&base.getAttribute(CFG_WEBPAGE).trim().length()>0)
        {
            try{
                wp=ws.getWebPage(base.getAttribute(CFG_WEBPAGE));
            }
            catch(Exception e)
            {
                log.error("Error al obtener la página "+base.getAttribute(CFG_WEBPAGE),e);
                wp=paramRequest.getWebPage();
            }
        }

        if(base.getAttribute(CFG_SENDPARAMS)!=null&&base.getAttribute(CFG_SENDPARAMS).trim().length()>0)
        {
            if(base.getAttribute(CFG_SENDPARAMS).equals("true")) sendparams=true;
        }

        if(sendparams)
        {
            if(request.getQueryString()!=null) querystring = "?"+request.getQueryString();
            //System.out.println("reenviar: "+request.getQueryString());
        }

        try
        {
            String usrlanguage=paramRequest.getUser().getLanguage();
            if(usrlanguage==null) usrlanguage="es";
            int ison = 0;
            int igrandson = 0;

            Document  dom = SWBUtils.XML.getNewDocument();
            Element out = dom.createElement("resource");
            dom.appendChild(out);
            Element father = dom.createElement("father");
            father.appendChild(dom.createTextNode(""));
            out.appendChild(father);
            Element fathertitle = dom.createElement("fathertitle");
            fathertitle.appendChild(dom.createTextNode(wp.getDisplayName(usrlanguage)));
            father.setAttribute("path", path);
            father.setAttribute("id", wp.getId());
            father.appendChild(fathertitle);

            if(usrlanguage!=null){
                father.setAttribute("hasfatherdescription","1");
                Element fatherdescription = dom.createElement("fatherdescription");
                fatherdescription.appendChild(dom.createTextNode(wp.getDisplayDescription(usrlanguage)));
                father.appendChild(fatherdescription);
            }else{
                Iterator <org.semanticwb.model.Language> itLang=wp.getWebSite().listLanguages();
                while(itLang.hasNext())
                {
                    org.semanticwb.model.Language lang=itLang.next();
                    String descr =wp.getDisplayDescription(lang.getId());
                    father.setAttribute("hasfatherdescription","0");
                    Element fatherlanguage = dom.createElement("fatherlanguage");
                    fatherlanguage.appendChild(dom.createTextNode(lang.getId()));
                    father.appendChild(fatherlanguage);
                    Element fatherdescription = dom.createElement("fatherdescription");
                    fatherdescription.appendChild(dom.createTextNode(descr));
                    father.appendChild(fatherdescription);
                }
            }

            Iterator <WebPage> hijos = wp.listChilds(usrlanguage, true, false, false, null);
            ison=0;
            while(hijos.hasNext())
            {
                WebPage hijo=hijos.next();
                //if (paramsRequest.getUser().haveAccess(hijo, false))
                {
                    ison++;
                    Element son = dom.createElement("son");
                    son.appendChild(dom.createTextNode(""));
                    //son.setAttribute("sonref",webpath+dist+"/"+tm.getId()+"/"+hijo.getId());
                    son.setAttribute("sonref",hijo.getUrl(user.getLanguage(),false)+querystring);
                    son.setAttribute("path", path);
                    son.setAttribute("id", hijo.getId());
                    Element sontitle = dom.createElement("sontitle");
                    sontitle.appendChild(dom.createTextNode(hijo.getDisplayName(usrlanguage)));
                    son.appendChild(sontitle);
                    father.appendChild(son);

                    if(usrlanguage!=null)
                    {
                        father.setAttribute("hassondescription","1");
                        Element fatherdescription = dom.createElement("sondescription");
                        fatherdescription.appendChild(dom.createTextNode(hijo.getDisplayDescription(usrlanguage)));
                        father.appendChild(fatherdescription);
                    }else{
                        Iterator <org.semanticwb.model.Language> itLang=wp.getWebSite().listLanguages();
                        while(itLang.hasNext())
                        {
                            org.semanticwb.model.Language lang=itLang.next();
                            String descr =wp.getDisplayDescription(lang.getId());
                            son.setAttribute("hassondescription","0");
                            Element sonlanguage = dom.createElement("sonlanguage");
                            sonlanguage.appendChild(dom.createTextNode(lang.getId()));
                            father.appendChild(sonlanguage);
                            Element sondescription = dom.createElement("sondescription");
                            sondescription.appendChild(dom.createTextNode(descr));
                            son.appendChild(sondescription);
                        }
                    }
                    Iterator <WebPage> nietos=hijo.listChilds(usrlanguage, true, false, false, null);
                    igrandson=0;
                    while(nietos.hasNext())
                    {
                        WebPage nieto= nietos.next();
                        //if (paramRequest.getUser().haveAccess(nieto, false))
                        {
                            igrandson++;
                            Element grandson = dom.createElement("grandson");
                            grandson.appendChild(dom.createTextNode(""));
                            //grandson.setAttribute("grandsonref",webpath+dist+"/"+tm.getId()+"/"+nieto.getId());
                            grandson.setAttribute("grandsonref",nieto.getUrl(user.getLanguage(),false)+querystring);
                            grandson.setAttribute("path", path);
                            grandson.setAttribute("id", nieto.getId());
                            Element grandsontitle = dom.createElement("grandsontitle");
                            grandsontitle.appendChild(dom.createTextNode(nieto.getDisplayName(usrlanguage)));
                            grandson.appendChild(grandsontitle);

                            if(usrlanguage!=null)
                            {
                                grandson.setAttribute("hasgrandsondescription","1");
                                Element grandsondescription = dom.createElement("grandsondescription");
                                grandsondescription.appendChild(dom.createTextNode(hijo.getDescription()));
                                grandson.appendChild(grandsondescription);
                            }else{
                                Iterator <org.semanticwb.model.Language> itLang=wp.getWebSite().listLanguages();
                                while(itLang.hasNext())
                                {
                                    org.semanticwb.model.Language lang=itLang.next();
                                    String descr =wp.getDisplayDescription(lang.getId());
                                    grandson.setAttribute("hasgrandsondescription","0");
                                    Element grandsonlanguage = dom.createElement("grandsonlanguage");
                                    grandsonlanguage.appendChild(dom.createTextNode(lang.getId()));
                                    grandson.appendChild(grandsonlanguage);
                                    Element grandsondescription = dom.createElement("grandsondescription");
                                    grandsondescription.appendChild(dom.createTextNode(descr));
                                    grandson.appendChild(grandsondescription);
                                }
                            }
                            son.appendChild(grandson);
                        }
                      }
                    son.setAttribute("totalgrandson",Integer.toString(igrandson));
                }
            }
            father.setAttribute("totalson",Integer.toString(ison));
            return dom;
        }
        catch (Exception e) { log.error("Error while generating the comments form in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
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
            org.w3c.dom.Document dom=getDom(request, response, paramRequest);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom,true));
            }
        }
        catch(Exception e){ log.error(e); }
    }

    /**
     * Obtiene la vista del recurso.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @exception com.infotec.appfw.exception.AFException
     * Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //System.out.println("Entra a TematicIndex/doView");
        try
        {
            Document dom =getDom(request, response, paramRequest);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));
            if(tpl==null) setResourceBase(getResourceBase());
            if(dom != null) {
                response.getWriter().println(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(paramRequest.getLocaleString("errormsg_IndiceTematicoXSL_doView_msgErrorDoView"), e); }
    }
}

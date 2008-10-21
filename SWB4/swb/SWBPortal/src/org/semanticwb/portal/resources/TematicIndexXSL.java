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


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Esta clase se encarga de desplegar y administrar los indices tematicos bajo ciertos
 * criterios(configuraci�n de recurso), usa un archivo XSL. Es un recurso de contenidos que viene de la version 2 de Webbuilder.
 *
 * This class administrate and displays thematic index under criteria like
 * resource configuration, uses and XSL file. Is a content resource and comes from version 2 of
 * WebBuilder.
 *
 * @author :Jorge Alberto Jim�nez Sandoval (JAJS)
 */
public class TematicIndexXSL extends GenericAdmResource 
{
    private static Logger log = SWBUtils.getLogger(TematicIndexXSL.class);
    
    javax.xml.transform.Templates tpl; 
    String workpath = "/work";
    String webpath = (String) SWBPlatform.getContextPath();
    String path =  webpath +"swbadmin/xsl/TematicIndexXSL/";

    /**
     * Crea un nuevo objeto IndiceTematicoXSL.
     */
    public TematicIndexXSL() {
    }
    
    /**
     * Asigna la informaci�n de la base de datos al recurso.
     *
     * @param     base  La informaci�n del recurso en memoria.
     * @throws AFException
     */
    @Override
    public void setResourceBase(Portlet base) throws SWBResourceException
    {
        try 
        {
            super.setResourceBase(base);
            webpath=(String) (String) SWBPlatform.getContextPath();
            workpath=(String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();     
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())); 
                path=workpath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/TematicIndexXSL/TematicIndexXSL.xslt")); } 
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        } 
    }
    
    /**
     * Obtiene el resultado final del recurso como dom-document
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        try
        {        
            String usrlanguage=paramRequest.getUser().getLanguage();
            int ison = 0;
            int igrandson = 0;
            
            Document  dom = SWBUtils.XML.getNewDocument();
            Element out = dom.createElement("resource");
            dom.appendChild(out);
            Element father = dom.createElement("father");
            father.appendChild(dom.createTextNode(""));
            out.appendChild(father);
            Element fathertitle = dom.createElement("fathertitle");
            fathertitle.appendChild(dom.createTextNode(paramRequest.getTopic().getTitle(usrlanguage)));
            father.setAttribute("path", path);
            father.appendChild(fathertitle);
            
            if(usrlanguage!=null){
                father.setAttribute("hasfatherdescription","1");
                Element fatherdescription = dom.createElement("fatherdescription");
                fatherdescription.appendChild(dom.createTextNode(paramRequest.getTopic().getDescription(usrlanguage)));
                father.appendChild(fatherdescription);
            }else{
                Iterator <org.semanticwb.model.Language> itLang=paramRequest.getTopic().getWebSite().listLanguages();
                while(itLang.hasNext())
                {
                    org.semanticwb.model.Language lang=itLang.next();
                    String descr =paramRequest.getTopic().getDescription(lang.getId());
                    father.setAttribute("hasfatherdescription","0");
                    Element fatherlanguage = dom.createElement("fatherlanguage");
                    fatherlanguage.appendChild(dom.createTextNode(lang.getId()));
                    father.appendChild(fatherlanguage);
                    Element fatherdescription = dom.createElement("fatherdescription");
                    fatherdescription.appendChild(dom.createTextNode(descr));
                    father.appendChild(fatherdescription);
                }
            }
            
            Iterator <WebPage> hijos = paramRequest.getTopic().listChilds(usrlanguage, true, false, false, null);
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
                    son.setAttribute("sonref",hijo.getUrl());
                    son.setAttribute("path", path);
                    Element sontitle = dom.createElement("sontitle");
                    sontitle.appendChild(dom.createTextNode(hijo.getTitle(usrlanguage)));
                    son.appendChild(sontitle);
                    father.appendChild(son);
                    
                    if(usrlanguage!=null)
                    {
                        father.setAttribute("hassondescription","1");
                        Element fatherdescription = dom.createElement("sondescription");
                        fatherdescription.appendChild(dom.createTextNode(hijo.getDescription(usrlanguage)));
                        father.appendChild(fatherdescription);
                    }else{
                        Iterator <org.semanticwb.model.Language> itLang=paramRequest.getTopic().getWebSite().listLanguages();
                        while(itLang.hasNext())
                        {
                            org.semanticwb.model.Language lang=itLang.next();
                            String descr =paramRequest.getTopic().getDescription(lang.getId());
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
                            grandson.setAttribute("grandsonref",nieto.getUrl());
                            grandson.setAttribute("path", path);
                            Element grandsontitle = dom.createElement("grandsontitle");
                            grandsontitle.appendChild(dom.createTextNode(nieto.getDisplayName(usrlanguage)));
                            grandson.appendChild(grandsontitle);
                            
                            if(usrlanguage!=null)
                            {
                                grandson.setAttribute("hasgrandsondescription","1");
                                Element grandsondescription = dom.createElement("grandsondescription");
                                grandsondescription.appendChild(dom.createTextNode(hijo.getDescription(usrlanguage)));
                                grandson.appendChild(grandsondescription);
                            }else{
                                Iterator <org.semanticwb.model.Language> itLang=paramRequest.getTopic().getWebSite().listLanguages();
                                while(itLang.hasNext())
                                {
                                    org.semanticwb.model.Language lang=itLang.next();
                                    String descr =paramRequest.getTopic().getDescription(lang.getId());
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
        catch (Exception e) { log.error("Error while generating the comments form in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }
    
    /**
     * Obtiene el resultado final del recurso en formato xml
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            org.w3c.dom.Document dom=getDom(request, response, paramRequest);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }
        catch(Exception e){ log.error(e); }        
    }    
    
    /**
     * Obtiene la vista del recurso.
     *
     * @param     request   El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al m�todo del servlet.
     * @param     response  El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al m�todo del servlet.
     * @param     paramsRequest Argumentos de la solicitud del recurso.
     * @exception com.infotec.appfw.exception.AFException
     *              Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("Entra a TematicIndex/doView");
        try
        {
            Document dom =getDom(request, response, paramRequest);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));
            if(dom != null) {
                response.getWriter().println(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(paramRequest.getLocaleString("errormsg_IndiceTematicoXSL_doView_msgErrorDoView"), e); }
    }
}
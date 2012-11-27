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
package org.semanticwb.portal.util;

import java.io.IOException;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectTree.
 */
public class SelectTree {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    
    /** The Constant pathImages. */
    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";

    /** The lang. */
    private String lang;

    /**
     * Instantiates a new select tree.
     */
    public SelectTree() {
        this("es");
    }

    /**
     * Instantiates a new select tree.
     * 
     * @param lang the lang
     */
    public SelectTree(String lang) {
        if(lang!=null) {
            this.lang = lang;
        }else {
            this.lang = "es";
        }
    }
       
    /**
     * Render xhtml.
     * 
     * @param site the site
     * @param request the request
     * @param url the url
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String renderXHTML(String site, HashMap request, String url) throws SWBResourceException, IOException {
        StringBuilder html = new StringBuilder();
        StringBuilder params = new StringBuilder("&site="+site);
        String whoOpen = "";        
        WebSite tm = null;

        try {
            WebPage tpsite=null, tpid=null;
            String style;
            
            if(request.containsKey("reptm") && request.get("reptm")!=null) {
                tm=SWBContext.getWebSite((String)request.get("reptm"));
                
                if(tm!=null) {                
                    tpsite=tm.getHomePage();

                    if(request.containsKey("reptp") && request.get("reptp")!=null && !((String)request.get("reptp")).trim().equals("")) {
                        if(tm.getWebPage((String)request.get("reptp"))!=null) {
                            tpid=tm.getWebPage((String)request.get("reptp"));
                        }
                    }
                }
            }
            
            WebSite tmit = SWBContext.getWebSite(site);            
            WebPage tmhome=tmit.getHomePage();
            
            boolean toggleopen = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
           
            html.append("<div class=\"sitesectiontree\" id=\"tree_"+site+"\">");
            html.append("<ul class=\"treeres\">");
            html.append("<li>");
            if(tmit.isActive())
                html.append("<img src=\""+pathImages+"/icon_siteb.gif\" alt=\"seleccionar sitio\" />");
            else
                html.append("<img src=\""+pathImages+"/icon_sitein.png\" alt=\"seleccionar sitio\" />");
            html.append("<span style=\"padding-left:5px\">"+tmit.getDisplayTitle(lang)+"</span>");
            html.append("<ul class=\"treeres\">");
            html.append("<li>");
            
            if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                style=" style=\"color:#FF6600; font-weight:bold\" ";                
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=0");
                    html.append("<img src=\""+pathImages+"/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                    toggleopen = false;
                }else {
                    params.append("&"+tmhome.getId()+"=1");
                    html.append("<img src=\""+pathImages+"/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                    toggleopen = true;
                }
            }else {
                style="";
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=1");
                    html.append("<img src=\""+pathImages+"/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                }else {
                    params.append("&"+tmhome.getId()+"=0");
                    html.append("<img src=\""+pathImages+"/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+site+"')\" />");
                }
            }
            
            html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+site+"')\" "+style+">");
            if(tmhome.isActive())
                html.append("<img src=\""+pathImages+"/icon_homeac.png\" alt=\"seleccionar inicio\" />");
            else
                html.append("<img src=\""+pathImages+"/icon_homein.png\" alt=\"seleccionar inicio\" />");
            html.append("<span style=\"padding-left:5px\">"+tmhome.getDisplayTitle(lang)+"</span>");
            html.append("</a>");
            
            if(tpsite!=null && toggleopen) {                
                html.append(addChild(request, tmit, tmhome, tpid, url, params));
            }
            html.append("</li>");
            html.append("</ul>");
            html.append("</div>");
            

        }
        catch(Exception e) {
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return html.toString();
    }

    /**
     * Adds the child.
     * 
     * @param request the request
     * @param tmit the tmit
     * @param pageroot the pageroot
     * @param tpid the tpid
     * @param url the url
     * @param params the params
     * @return the string
     */
    protected String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, String url, StringBuilder params) {
        String style;
        boolean toggleopen;
        
        StringBuilder html = new StringBuilder("<ul class=\"treeres\">");
        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId()!=null) {
                                
                toggleopen = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");
                
                if(webpage.listChilds().hasNext()) {
                    html.append("<li>");
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=0");
                            html.append("<img src=\""+pathImages+"/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                            toggleopen = false;
                        }else {
                            params.append("&"+webpage.getId()+"=1");
                            html.append("<img src=\""+pathImages+"/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                            toggleopen = true;
                        }
                    }else {
                        style="";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=1");
                            html.append("<img src=\""+pathImages+"/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                        }else {
                            params.append("&"+webpage.getId()+"=0");
                            html.append("<img src=\""+pathImages+"/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" />");
                        }                        
                    }                            

                    html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" "+style+">");
                    if(webpage.isActive())
                        html.append("<img src=\""+pathImages+"/icon_secac.png\" alt=\"seleccionar sección\" />");
                    else
                        html.append("<img src=\""+pathImages+"/icon_secin.png\" alt=\"seleccionar sección\" />");
                    html.append("<span style=\"padding-left:5px\">"+webpage.getDisplayTitle(lang)+"</span>");
                    html.append("</a>");

                    if(toggleopen) {
                        html.append(addChild(request, tmit, webpage, tpid, url, params));
                    }

                    html.append("</li>");
                }else {
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())){
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                    }else {
                        style="";
                    }
                    
                    html.append("<li>");
                    html.append("<img src=\""+pathImages+"/trans.gif\" />");
                    html.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\" "+style+">");
                    if(webpage.isActive())
                        html.append("<img src=\""+pathImages+"/icon_secac.png\" alt=\"seleccionar sección\" />");
                    else
                        html.append("<img src=\""+pathImages+"/icon_secin.png\" alt=\"seleccionar sección\" />");
                    html.append("<span style=\"padding-left:5px\">"+webpage.getDisplayTitle(lang)+"</span>");
                    html.append("</a>");
                    html.append("</li>");
                }
            }
        }        
        html.append("</ul>");
        
        return html.toString();
    }

}

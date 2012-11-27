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
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;


// TODO: Auto-generated Javadoc
/**
 * The Class WebSiteSectionTree.
 */
public class WebSiteSectionTree {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    
    /** The Constant pathImages. */
    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";
    
    /**
     * Render xhtml.
     * 
     * @param selectedsite the selectedsite
     * @param request the request
     * @param user the user
     * @param url the url
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String renderXHTML(String selectedsite, HttpServletRequest request, User user, String url) throws SWBResourceException, IOException {
        StringBuilder sb_ret = new StringBuilder();
        StringBuilder params = new StringBuilder("&site="+selectedsite);
        String whoOpen = "";        
        WebSite tm = null;

        try {
            WebPage tpsite=null, tpid=null;
            String style;
            
            if(request.getParameter("reptm")!=null) {
                tm=SWBContext.getWebSite(request.getParameter("reptm"));
                
                if(tm!=null) {                
                    tpsite=tm.getHomePage();

                    if(request.getParameter("reptp")!=null && !request.getParameter("reptp").trim().equals("")) {
                        if(tm.getWebPage(request.getParameter("reptp"))!=null) {
                            tpid=tm.getWebPage(request.getParameter("reptp"));
                        }
                    }
                }
            }
            
            WebSite tmit = SWBContext.getWebSite(selectedsite);            
            WebPage tmhome=tmit.getHomePage();
            
            boolean toggleopen = Boolean.parseBoolean(request.getParameter(tmhome.getId())==null?"false":request.getParameter(tmhome.getId()).equals("1")?"true":"false");
           
            sb_ret.append("<div class=\"sitesectiontree\">");
            sb_ret.append("<ul class=\"treeres\">");
            sb_ret.append("<li>");
            sb_ret.append("<img src=\""+pathImages+"/icon_sitea.gif\" />");
            sb_ret.append(tmit.getId());
            sb_ret.append("<ul class=\"treeres\">");
            sb_ret.append("<li>");
            
            if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                style=" style=\"color:#FF6600; font-weight:bold\" ";                
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=0");
                    sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                    toggleopen = false;
                }else {
                    params.append("&"+tmhome.getId()+"=1");
                    sb_ret.append("<img src=\""+pathImages+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                    toggleopen = true;
                }
            }else {
                style="";
                if(toggleopen) {
                    params.append("&"+tmhome.getId()+"=1");
                    sb_ret.append("<img src=\""+pathImages+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                }else {
                    params.append("&"+tmhome.getId()+"=0");
                    sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','slave')\" />");
                }
            }
            
            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','slave')\""+style+">");
            sb_ret.append("<img src=\""+pathImages+"/icon_homea.gif\" />");
            sb_ret.append(tmhome.getDisplayName());            
            sb_ret.append("</a>");
            
            if(tpsite!=null && toggleopen) {
                sb_ret.append("<ul class=\"treeres\">");
                
                Iterator<WebPage> childs=tpsite.listChilds();
                while(childs.hasNext()) {
                    WebPage webpage = childs.next();
                    if(webpage.getId()!=null) {                        
                        toggleopen = Boolean.parseBoolean(request.getParameter(webpage.getId())==null?"false":request.getParameter(webpage.getId()).equals("1")?"true":"false");
                        
                        if(webpage.listChilds().hasNext()) {
                            sb_ret.append("<li>");
                            if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                                style=" style=\"color:#FF6600; font-weight:bold\" ";
                                if(toggleopen) {
                                    params.append("&"+webpage.getId()+"=0");
                                    sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                                    toggleopen = false;
                                }else {
                                    params.append("&"+webpage.getId()+"=1");
                                    sb_ret.append("<img src=\""+pathImages+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                                    toggleopen = true;
                                }
                            }else {
                                style="";
                                if(toggleopen) {
                                    params.append("&"+webpage.getId()+"=1");
                                    sb_ret.append("<img src=\""+pathImages+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + webpage.getId()+params+"','slave')\" />");
                                }else {
                                    params.append("&"+webpage.getId()+"=0");
                                    sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + webpage.getId()+params+"','slave')\" />");
                                }                                
                            }                            
                            
                            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                            sb_ret.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                            sb_ret.append(webpage.getDisplayName());
                            sb_ret.append("</a>");
                            
                            if(toggleopen) {
                                sb_ret.append(addChild(request, tmit, webpage, tpid, url, params, user));
                            }
                            
                            sb_ret.append("</li>");
                        }else {
                            if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                                style=" style=\"color:#FF6600; font-weight:bold\" ";
                            }else {
                                style="";
                            }
                            
                            sb_ret.append("<li>");
                            sb_ret.append("<img src=\""+pathImages+"/trans.gif\" />");
                            sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                            sb_ret.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                            sb_ret.append(webpage.getDisplayName());
                            sb_ret.append("</a>");
                            sb_ret.append("</li>");
                        }
                    }                    
                }                
                sb_ret.append("</ul>");
            }
            sb_ret.append("</li>");
            sb_ret.append("</ul>");
            sb_ret.append("</div>");
            

        }
        catch(Exception e) {
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return sb_ret.toString();
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
     * @param user the user
     * @return the string
     */
    protected String addChild(HttpServletRequest request,WebSite tmit, WebPage pageroot, WebPage tpid, String url, StringBuilder params, User user) {
        StringBuilder sb_ret = new StringBuilder("<ul class=\"treeres\">");
        String style;
        boolean toggleopen;
        
                
        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId()!=null) {
                                
                toggleopen = Boolean.parseBoolean(request.getParameter(webpage.getId())==null?"false":request.getParameter(webpage.getId()).equals("1")?"true":"false");
                
                if(webpage.listChilds().hasNext()) {
                    sb_ret.append("<li>");
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=0");
                            sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                            toggleopen = false;
                        }else {
                            params.append("&"+webpage.getId()+"=1");
                            sb_ret.append("<img src=\""+pathImages+"/minus.gif\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                            toggleopen = true;
                        }
                    }else {
                        style="";
                        if(toggleopen) {
                            params.append("&"+webpage.getId()+"=1");
                            sb_ret.append("<img src=\""+pathImages+"/minus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                        }else {
                            params.append("&"+webpage.getId()+"=0");
                            sb_ret.append("<img src=\""+pathImages+"/plus.gif\"  onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\" />");
                        }                        
                    }                            

                    sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                    sb_ret.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                    sb_ret.append(webpage.getDisplayName());
                    sb_ret.append("</a>");

                    if(toggleopen) {
                        sb_ret.append(addChild(request, tmit, webpage, tpid, url, params, user));
                    }

                    sb_ret.append("</li>");
                }else {
                    if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())){
                        style=" style=\"color:#FF6600; font-weight:bold\" ";
                    }else {
                        style="";
                    }
                    
                    sb_ret.append("<li>");
                    sb_ret.append("<img src=\""+pathImages+"/trans.gif\" />");
                    sb_ret.append("<a class=\"treeres\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','slave')\""+style+">");
                    sb_ret.append("<img src=\""+pathImages+"/icon-section.gif\" />");
                    sb_ret.append(webpage.getDisplayName());
                    sb_ret.append("</a>");
                    sb_ret.append("</li>");
                }
            }
        }
        
        sb_ret.append("</ul>");
        return sb_ret.toString();
    }
}

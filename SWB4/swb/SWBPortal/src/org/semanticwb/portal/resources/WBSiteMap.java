/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci?n e integraci?n para Internet,
 * la cual, es una creaci?n original del Fondo de Informaci?n y Documentaci?n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P?blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi?n 1; No. 03-2003-012112473900 para la versi?n 2, y No. 03-2006-012012004000-01
 * para la versi?n 3, respectivamente.
 *
 * INFOTEC pone a su disposici?n la herramienta INFOTEC WebBuilder a trav?s de su licenciamiento abierto al p?blico (?open source?),
 * en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC lo ha dise?ado y puesto a su disposici?n;
 * aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t?rminos y condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant?a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl?cita ni expl?cita,
 * siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici?n la siguiente
 * direcci?n electr?nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
        

/** Esta clase muestra el mapa del sitio de acuerdo a un determinado TopicMap.
 *
 * This class displays a map site according with TopicMap.
 * @since : October 23th 2002, 19:11
 * @author : Infotec
 */
public class WBSiteMap extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(WBSiteMap.class);
    
    javax.xml.transform.Templates tpl; 
    String webWorkPath = "/work";  
    Vector vTopic = new Vector();
    int maxLevel;
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSiteMap/";

    public WBSiteMap() {
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Portlet base)
    {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();            
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath()+"/"+ base.getAttribute("template").trim())); 
                path=webWorkPath + "/";
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e); 
            }
        }
        if(tpl==null)
        {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBSiteMap/WBSiteMap.xslt")); 
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        } 
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equals("childs")){
            doChilds(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }    
        
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Portlet base=getResourceBase();
        StringBuffer data = new StringBuffer();
        try {
            WebPage tpsite = null;                        
            tpsite = paramRequest.getTopic().getWebSite().getWebPage(request.getParameter("wp").trim());
            if (tpsite == null) {
                throw new SWBResourceException("pagina desconocida");
            }
                        
            //if(user.haveAccess(tpsite)) TODO: VER4
            {
                String lang = paramRequest.getUser().getLanguage();                                
                Iterator <WebPage> it = tpsite.listChilds(lang, true, false, false, null);
                if(it.hasNext()) {
                    data.append("[");
                    boolean hasChilds;
                    while (it.hasNext())
                    {
                        WebPage tp = it.next();
                        //if(tp!=null && tp.getId()!=null && user.haveAccess(tp)) //TODO VER 4.0
                        if(tp!=null && tp.getId()!=null)
                        {
                            data.append("{");
                            data.append("id:'"+tp.getId()+"'");
                            data.append(",title:'"+tp.getTitle(lang)+"'");                            
                            hasChilds = tp.listChilds(lang, true, false, false, null).hasNext();                            
                            if(hasChilds) {
                                data.append(",isFolder:true");
                                    data.append(",isLazy:true");
                            }else{
                                data.append(",purl:'"+tp.getUrl()+"'");
                            }

                            if(it.hasNext()) {
                                data.append("},");
                            }else {
                                data.append("}");
                            }
                        }
                    }
                    data.append("]");
                }
            }
        }catch(Exception e) {
            log.error(e);
            data.append(e.toString());
        }
        out.println(data.toString());
    }
    
    /** Crea el inicio del objeto json del árbol del mapa del sitio eventualmente usando ajax.
     * @param webSite
     * @param user
     * @throws SWBResourceException
     * @throws IOException
     */
    public String getJSONDynamically(WebSite webSite, User user) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        StringBuffer data = new StringBuffer();
        int subLevel = 1;
        try {
            WebPage tpsite = null;
                        
            if (!"".equals(base.getAttribute("level", "").trim())) {
                try {
                    maxLevel = Integer.parseInt(base.getAttribute("level", "1").trim());
                    if(maxLevel<1) {
                        maxLevel = 1;
                    }                        
                }catch (Exception e) {
                    maxLevel = 1;
                }
            }
            subLevel = 1;
                        
            if(base.getAttribute("home") != null) {
                tpsite = webSite.getWebPage(base.getAttribute("home").trim());
            }            
            if (tpsite == null) {
                tpsite = webSite.getHomePage();
            }
                        
            //if (subLevel<=maxLevel && user.haveAccess(tpsite)) TODO: VER4
            if(subLevel<=maxLevel)
            {
                String lang = user.getLanguage();                                
                Iterator <WebPage> it = tpsite.listChilds(lang, true, false, false, null);
                if(it.hasNext()) {                    
                    data.append(",children:[");
                    boolean hasChilds;
                    while (it.hasNext())
                    {
                        WebPage tp = it.next();
                        //if(tp!=null && tp.getId()!=null && user.haveAccess(tp)) //TODO VER 4.0
                        if(tp!=null && tp.getId()!=null)
                        {
                            data.append("{");
                            data.append("id:'"+tp.getId()+"'");
                            data.append(",title:'"+tp.getTitle(lang)+"'");                            
                            hasChilds = tp.listChilds(lang, true, false, false, null).hasNext();                            
                            if(hasChilds) {
                                data.append(",isFolder:true");
                                if(subLevel < maxLevel) {
                                    data.append(",expand:true,children:[");
                                    data.append(getJSONDynamically(webSite, tp, user, subLevel+1));
                                    data.append("]");
                                }else {
                                    data.append(",isLazy:true");
                                }
                            }else{
                                data.append(",purl:'"+tp.getUrl()+"'");
                            }

                            if(it.hasNext()) {
                                data.append("},");
                            }else {
                                data.append("}");
                            }
                        }
                    }
                    data.append("]");
                }
            }
        }catch(Exception e) { 
            log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); 
            data.append(e.toString());
        }
        return data.toString();
    }
    
    /** Crea el cuerpo del objeto json del árbol del mapa del sitio eventualmente usando ajax.
     * @param webSite
     * @param user
     * @throws SWBResourceException
     * @throws IOException
     */
    public String getJSONDynamically(WebSite webSite, WebPage webPage, User user, int subLevel) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        StringBuffer data = new StringBuffer();
        try {
            WebPage tpsite = webPage;                        
            //if (user.haveAccess(tpsite)) TODO: VER4
            {
                String lang = user.getLanguage();
                Iterator <WebPage> it = tpsite.listChilds(lang, true, false, false, null);
                boolean hasChilds;
                while (it.hasNext()) {
                    WebPage tp = it.next();
                    //if(tp!=null && tp.getId()!=null && user.haveAccess(tp)) //TODO VER 4.0
                    if(tp!=null && tp.getId()!=null)
                    {
                        data.append("{");
                        data.append("id:'"+tp.getId()+"'");
                        data.append(",title:'"+tp.getTitle(lang)+"'");                            
                        hasChilds = tp.listChilds(lang, true, false, false, null).hasNext();                            
                        if(hasChilds) {
                            data.append(",isFolder:true");
                            if(subLevel < maxLevel) {
                                data.append(",expand:true,children:[");
                                data.append(getJSONDynamically(webSite, tp, user, subLevel+1));
                                data.append("]");
                            }else {
                                data.append(",isLazy:true");
                            }
                        }else{
                            data.append(",purl:'"+tp.getUrl()+"'");
                        }

                        if(it.hasNext()) {
                            data.append("},");
                        }else {
                            data.append("}");
                        }                        
                    }
                }
            }
            //return data.toString();
        }catch(Exception e) { 
            log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); 
            data.append(e.toString());
        }
        return data.toString();      
    }
    
    /** Crea el cuerpo del objeto json del árbol del mapa del sitio eventualmente usando ajax.
     * @param webSite
     * @param user
     * @throws SWBResourceException
     * @throws IOException
     */
    public String getJSONDynamically(WebSite webSite, WebPage webPage, User user) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        StringBuffer data = new StringBuffer();
        try {
            WebPage tpsite = webPage;                        
            //if (user.haveAccess(tpsite)) TODO: VER4
            {
                String lang = user.getLanguage();
                Iterator <WebPage> it = tpsite.listChilds(lang, true, false, false, null);
                boolean hasChilds;
                while (it.hasNext()) {
                    WebPage tp = it.next();
                    //if(tp!=null && tp.getId()!=null && user.haveAccess(tp)) //TODO VER 4.0
                    if(tp!=null && tp.getId()!=null)
                    {
                        data.append(" {");
                        data.append("id:'"+tp.getId()+"', ");
                        hasChilds = tp.listChilds(lang, true, false, false, null).hasNext();
                        if(hasChilds) {
                            data.append("carpeta:'true', loaded:'false', ");
                        }else {
                            data.append("carpeta:'false', loaded:'true', ");
                        }
                        data.append(" name:'"+tp.getTitle(lang)+"', ");
                        data.append(" tpurl:'"+tp.getUrl()+"', ");
                        data.append(" type:'webpage' ");
                        if(hasChilds) {
                                data.append(", children: []");
                        }                          
                        if(it.hasNext()) {
                            data.append("},");
                        }else {
                            data.append("}");
                        }                        
                    }
                }
            }
            //return data.toString();
        }catch(Exception e) { 
            log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); 
            data.append(e.toString());
        }
        return data.toString();      
    }
        
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        
        //if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY)
        if(paramRequest.getCallMethod()==100)
        {
            String surl="";
            if (!"".equals(base.getAttribute("url", "").trim())) {
                surl=base.getAttribute("url").trim();
            }
            else 
            {
                SWBResourceURL url=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
                url.setParameter("smp_act", "smp_step2");
                surl=url.toString();
            }
            if (!"".equals(base.getAttribute("img", "").trim()))
            {
                ret.append("\n<a href=\"" + surl +"\">");
                ret.append("<img src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                if (!"".equals(base.getAttribute("alt", "").trim())) {
                    ret.append(" alt=\"" + base.getAttribute("alt").trim() + "\"");
                }
                ret.append(" border=0></a>");
            } 
            else if (!"".equals(base.getAttribute("btntexto", "").trim()))
            {
                ret.append("\n<form name=frmWBSiteMap method=POST action=\"" + surl + "\">");
                ret.append("\n<input type=submit name=btnWBSiteMap value=");
                ret.append("\"" + base.getAttribute("btntexto").trim().replaceAll("\"","&#34;") + "\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                ret.append("></form>");
            } 
            else
            {
                ret.append("\n<a href=\"" + surl +"\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                ret.append(">");
                if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                    ret.append(base.getAttribute("lnktexto").trim());
                }
                else ret.append(paramRequest.getLocaleString("msgSiteMap"));
                ret.append("</a>");
            }
        }else {
            // Mapa de sitio
            try {
                SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode("childs");                
                url.setTopic(paramRequest.getTopic());
                url.setCallMethod(paramRequest.Call_DIRECT);
                
                ret.append("<div id=\"tree_sitemap_"+base.getId()+"\"> </div> \n");
                ret.append("<script type='text/javascript' src='/swb/swbadmin/js/jquery/jquery.dynatree.js'></script> \n");
                ret.append("<script type='text/javascript'> \n");
                ret.append("$(function(){ \n");                
                ret.append("    $(\"#tree_sitemap_"+base.getId()+"\").dynatree({ \n");
                ret.append("        onLazyRead: function(dtnode) { \n");
                ret.append("            dtnode.appendAjax({url:'"+url.toString()+"?wp='+dtnode.data.id, \n");
                ret.append("            cache: false \n");
                ret.append("            }); \n");
                ret.append("        } \n");
                ret.append("        ,autoCollapse:"+base.getAttribute("collapse","false")+" \n");
                ret.append("        ,title: '"+base.getAttribute("home","Home")+"' \n");
                ret.append("        ,rootVisible: "+base.getAttribute("showroot","false")+" \n");
                ret.append("        ,strings: { \n");
                ret.append("            loading: '"+base.getAttribute("loadmsg","Cargando&#8230;")+"' \n");
                ret.append("            ,loadError: '"+base.getAttribute("errormsg","¡Error, la p&aacute;gina no existe!")+"' \n");
                ret.append("        } \n");
                
                ret.append("        ,onSelect: function(dtnode) { \n");
                ret.append("            if(dtnode.data.purl) {");
                ret.append("                window.location=dtnode.data.purl");
                ret.append("            }");
                ret.append("        } \n");                
                ret.append(getJSONDynamically(paramRequest.getTopic().getWebSite(), paramRequest.getUser()));
                ret.append("    }); \n");
                ret.append("}); \n");
                ret.append("</script>\n");
            }
            catch(Exception e) {
                log.error(e);
            }
        }
        out.println(ret.toString());
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin sitemap</a>");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        String surl="";
        if(paramRequest.getTopic()!=paramRequest.getAdminTopic())
        {
            String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
            if("add".equals(action))
            {                
                SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(url.Mode_VIEW);
                url.setParameter("smp_act","smp_step2");
                url.setTopic(paramRequest.getTopic());
                surl=url.toString();
            }
            else {
                surl=base.getAttribute("url","");
            }
        }
        super.doAdmin(request, response, paramRequest);
        if(paramRequest.getTopic()!=paramRequest.getAdminTopic() && !"".equals(surl))
        {
            base.setAttribute("url", surl);
            try{
                base.updateAttributesToDB();
            }catch(Exception e){
                throw new SWBResourceException(e.getMessage());
            }
        }
    }
}

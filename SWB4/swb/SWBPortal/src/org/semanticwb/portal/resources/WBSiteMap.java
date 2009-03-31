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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
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
    
    String webWorkPath;  
    int maxLevel;
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSiteMap/";

    public WBSiteMap() {
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Resource base)
    {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();            
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
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
        if(paramRequest.getMode().equalsIgnoreCase("Json")) {
            doChilds(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }    
        
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();
        WebPage home = paramRequest.getTopic().getWebSite().getHomePage();

        StringBuilder json = new StringBuilder();
        try {
            json.append("{");
            json.append("identifier:'id',");
            json.append("label:'name',");
            json.append("items:[");

            json.append("{");
            json.append("name:'"+home.getDisplayName(lang)+"',");
            json.append("id:'"+home.getId()+"',");
            json.append("purl:'"+home.getUrl()+"',");
            json.append("type:'home'");
            if(home.listVisibleChilds(lang).hasNext()) {
                json.append(",children:[");
                json.append(addReferences(home, lang));
                json.append("]");
            }
            json.append("}");

            if(home.listVisibleChilds(lang).hasNext()) {
                json.append(",");
                json.append(addChilds(home, lang));
            }

            json.append("]");
            json.append("}");
        }catch(Exception e) {
            json.append(e);
        }
        out.print(json.toString());
        out.flush();
    }

    private String addReferences(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listVisibleChilds(lang);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{_reference:'"+wp.getDisplayName(lang)+"'}");
            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }

    private String addChilds(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listVisibleChilds(lang);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{");
            json.append("name:'"+wp.getDisplayName(lang)+"',");
            json.append("id:'"+wp.getId()+"',");
            json.append("purl:'"+wp.getUrl()+"'");
            if(wp.listVisibleChilds(lang).hasNext()) {
                json.append(",type:'chanel'");
                json.append(",children:[");
                json.append(addReferences(wp, lang));
                json.append("]");
            }
            json.append("}");

            if(wp.listVisibleChilds(lang).hasNext()) {
                json.append(",");
                json.append(addChilds(wp, lang));
            }

            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }
        
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();
        
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY) {        
            String surl="";

            if (!"".equals(base.getAttribute("url", "").trim())) {
                surl=base.getAttribute("url").trim();
            }else {
                surl=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW).toString();
            }

            if (!"".equals(base.getAttribute("img", "").trim())) {
                out.println("<a href=\"" + surl +"\">");
                out.println("<img src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                if (!"".equals(base.getAttribute("alt", "").trim())) {
                    out.println(" alt=\"" + base.getAttribute("alt").trim() + "\"");
                }
                out.println(" border=0></a>");
            }else if (!"".equals(base.getAttribute("btntexto", "").trim())) {
                out.println("<form name=frmWBSiteMap method=POST action=\"" + surl + "\">");
                out.println("<input type=submit name=btnWBSiteMap value=");
                out.println("\"" + base.getAttribute("btntexto").trim().replaceAll("\"","&#34;") + "\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println("></form>");
            }else {
                out.println("<a href=\"" + surl +"\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println(">");
                if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                    out.println(base.getAttribute("lnktexto").trim());
                }
                else out.println(paramRequest.getLocaleString("msgSiteMap"));
                out.println("</a>");
            }
        }else {
            // Mapa de sitio
            try {

                /*SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode("Json");
                url.setTopic(paramRequest.getTopic());
                url.setCallMethod(paramRequest.Call_DIRECT);*/
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setMode("Json");

                out.println("<script type='text/javascript'>");
                out.println("  var sitemaptree_"+base.getId()+" = null;");
                out.println("  var store_"+base.getId()+" = null;");
                out.println("  var model_"+base.getId()+" = null;");

                out.println("  function send(item) {");
                out.println("    window.location=store_"+base.getId()+".getValue(item, 'purl');");
                out.println("  }");

                out.println("  dojo.addOnLoad(function(){");
                out.println("    store_"+base.getId()+" = new dojo.data.ItemFileReadStore({ url: '"+url.toString()+"' });");

                out.println("    model_"+base.getId()+" = new dijit.tree.ForestStoreModel({");
                out.println("          id: 'treeModel',");
                out.println("          store: store_"+base.getId()+",");
                out.println("          query: {'type': 'home'},");
                out.println("          rootId: 'root_"+base.getId()+"',");
                out.println("          rootLabel: '"+base.getAttribute("title")!=null ? base.getAttribute("title") : paramRequest.getTopic().getWebSite().getTitle()+"',");
                out.println("          childrenAttrs: ['children'] });");

                out.println("    sitemaptree_"+base.getId()+" = new dijit.Tree({");
                out.println("          id: 'continentes',");
                out.println("          model: model_"+base.getId()+",");
                out.println("          class: 'soria',");
                out.println("          openOnClick: false }");
                out.println("      , 'sytemap_"+base.getId()+"');");

                out.println("    dojo.connect(dijit.byId('continentes'), 'onClick', send);");
                out.println("  });");


                out.println("</script>");
                out.println("<div id=\"sytemap_"+base.getId()+"\"></div>");
            }
            catch(Exception e) {
                log.error(e);
            }
        }
        out.flush();
    }
}

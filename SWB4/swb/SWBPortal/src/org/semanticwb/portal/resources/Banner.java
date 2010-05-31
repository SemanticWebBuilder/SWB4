/**  
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
**/ 
 


/*
 * Banner.java
 *
 * Created on 28 de octubre de 2004, 15:10
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;


// TODO: Auto-generated Javadoc
/**
 * Banner se encarga de desplegar y administrar un banner bajo ciertos
 * criterios(configuración de recurso).
 *
 * Banner is in charge to unfold and to administer a banner under certain
 * criteria (resource configuration).
 *
 * @author : Jorge Alberto Jiménez
 * @version 1.0
 */

public class Banner extends GenericAdmResource 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Banner.class);
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        Resource base = getResourceBase();
        try {
            String local = base.getAttribute("local", "0");
            String code =base.getAttribute("code");
            if( local.equals("0")||code==null ) {
                String img = base.getAttribute("img");
                if( img!=null ){
                    String wburl = paramRequest.getActionUrl().toString();
                    String url = base.getAttribute("url","");
                    String width = paramRequest.getArgument("width", base.getAttribute("width"));
                    String height = paramRequest.getArgument("height", base.getAttribute("height"));
                    String cssClass = base.getAttribute("cssClass");

                    if( url.toLowerCase().startsWith("mailto:") ) {
                        wburl = url.replaceAll("\"", "&#34;");
                    }

                    if(img.endsWith(".swf")) {
                        String schema = new URL(request.getRequestURL().toString()).getProtocol();

                        ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"" + schema + "://get.adobe.com/flashplayer/\"");
                        if( width!=null ) {
                            ret.append(" width=\""+width.replaceAll("\\D", "")+"\"");
                        }
                        if( height!=null ) {
                            ret.append(" height=\""+height.replaceAll("\\D", "")+"\"");
                        }
                        ret.append(">\n");
                        ret.append("<param name=movie value=\"" + SWBPortal.getWebWorkPath() + base.getWorkPath() + "/" + img + "\" />");
                        ret.append("<param name=\"quality\" value=\"high\" />");
                        ret.append("<param name=\"wmode\" value=\"transparent\" />");
                        ret.append("<param name=\"FlashVars\" value=\"liga="+wburl+"\" />");
                        ret.append("<embed id=\"bnr_"+base.getId()+"\" name=\"bnr_"+base.getId()+"\"");
                        ret.append(" src=\""+SWBPortal.getWebWorkPath()+base.getWorkPath()+"/"+img+"\"");
                        ret.append(" FlashVars=\"liga="+wburl+"\"");
                        ret.append(" quality=\"high\" pluginspage=\"http://get.adobe.com/flashplayer/\" type=\"application/x-shockwave-flash\" ");
                        if( width!=null ) {
                            ret.append(" width=\""+width.replaceAll("\\D", "")+"\"");
                        }
                        if( height!=null ) {
                            ret.append(" height=\""+height.replaceAll("\\D", "")+"\"");
                        }
                        ret.append(">");
                        ret.append("</embed>");
                        ret.append("</object>");
                    }else {
                        String action = base.getAttribute("axn");
                        String target = base.getAttribute("target", "0").trim();

                        if( !url.equals("")||action!=null ) {
                            ret.append("<a");
                            if( !url.equals("") )
                                ret.append(" href=\""+wburl+"\"");
                            if( action!=null )
                                ret.append(" onfocus=\""+action+"\"");
                            if( target.equals("1") )
                                ret.append(" target=\"_blank\"");
                            if( cssClass!=null )
                                ret.append(" class=\""+cssClass+"\"");
                            else
                                ret.append(" class=\"swb-banner\"");
                            ret.append(">");
                        }
                        String longdesc = base.getAttribute("longdesc");                        
                        ret.append("<img src=\"");
                        ret.append(SWBPortal.getWebWorkPath() + base.getWorkPath() + "/" + img + "\"");
                        ret.append(" alt=\"" + base.getAttribute("alt", paramRequest.getLocaleString("goto")+" "+url) + "\"");
                        if( width!=null )
                            ret.append(" width=\""+width.replaceAll("\\D", "")+"\"");
                        if( height!=null )
                            ret.append(" height=\""+height.replaceAll("\\D", "")+"\"");
                        if( longdesc!=null )
                            ret.append(" longdesc=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).toString()+"\"");
                        ret.append("/>");

                        if( !url.equals("")||action!=null ) {
                            ret.append("</a>");
                        }
                    }
                }
                String ld = base.getAttribute("longdesc");
                if( ld!=null ) {
                    ret.append("<a class=\"swb-banner-hlp\" href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).toString()+"\">"+paramRequest.getLocaleString("longDesc")+"</a>");
                }
            }else { //publicidad externa
                ret.append(code);
            }
        }catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
        PrintWriter out = response.getWriter();
        out.print(ret.toString());        
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        out.println("<div style=\"position:relative;"+(base.getAttribute("width")==null?"":"width:"+base.getAttribute("width")+";")+(base.getAttribute("height")==null?"":"height:"+base.getAttribute("height")+";")+"\">");
        out.println(base.getAttribute("longdesc", "Sin descripción"));
        out.println("<hr size=\"1\" noshade=\"noshade\" />");
        out.println("<a href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW).toString()+"\">Regresar</a>");
        out.println("</div>");
        out.flush();
        out.close();
    }

    /**
     * Replace tags.
     * 
     * @param str the str
     * @param request the request
     * @param user the user
     * @param webpage the webpage
     * @return the string
     */
    public String replaceTags(String str, HttpServletRequest request, User user,WebPage webpage) {
        if(str==null || str.trim().length()==0)
            return null;

        str=str.trim();

        Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,user,webpage)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,user,webpage)));
        }

        /*it=SWBUtils.TEXT.findInterStr(str, "{template.getArgument(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{template.getArgument(\""+s+"\")}", (String)response.getArgument(replaceTags(s,request,user,webpage)));
        }*/

        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,user,webpage)));
        }

        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", user.getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", user.getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", user.getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{websiteid}", webpage.getWebSiteId());
        return str;
    }
    
    /**
     * Metodo para hacer operaciones.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        base.addHit(request, response.getUser(), response.getWebPage());
        String url = base.getAttribute("url", "").trim();
        url=replaceTags(url, request, response.getUser(), response.getWebPage());
        if( url!=null )
            response.sendRedirect(url);
    }
}

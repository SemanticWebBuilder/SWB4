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


/*
 * RSSResource.java
 *
 * Created on 3 de julio de 2003, 11:32
 */

package com.infotec.wb.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/** Recurso de tipo contenido para incluir y tranformar un XML del tipo RSS Portlet
 * como recurso.
 *
 * Resource of content type to include and to tranform to XML of RSS Portlet type
 * like resource.
 *
 * @author Javier Solis Gonzalez
 */
public class RSSResource extends GenericAdmResource
{
    private javax.xml.transform.Templates tpl;
    private static Logger log = SWBUtils.getLogger(RSSResource.class);
    
    /** Creates a new instance of RSSResource */    
    public RSSResource() {
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Portlet base)
    {
        try { 
            super.setResourceBase(base); 
        }catch(Exception e) { 
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        System.out.println("******************************* veamos");
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            System.out.println("******************************* por el primer if");
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())); 
            }catch(Exception e) { 
                log.error("Error while loading resource template: "+base.getId() +"-"+ base.getTitle(), e);
            }
        }
        if(tpl==null)
        {
            System.out.println("******************************* por el segundo if");
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/RSSResource/RSSResource.xslt")); 
            } catch(Exception e) { 
                log.error("Error while loading default resource template: "+base.getId() +"-"+ base.getTitle(), e);
            }
        }
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        Portlet base = getResourceBase();
        try
        {
            URL url = new URL(base.getAttribute("url","").trim());
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();            
            Document dom = SWBUtils.XML.xmlToDom(is);        
            return dom;
        }
        catch (Exception e) { 
            log.error("Error while generating DOM in resource: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return null;
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        try
        {
            Document dom=getDom(request, response, paramReq);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }
        catch(Exception e){ 
            log.error(e);
        }        
    }  
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException 
    {
        PrintWriter out = response.getWriter();
        try
        {
            Document dom =getDom(request, response, paramReq);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));
            if(dom != null) {
                out.print(SWBUtils.XML.transformDom(tpl, dom));
            }            
        }
        catch (Exception e) { 
            log.error("Error while processing RSS for: "+getResourceBase().getAttribute("url"), e);
        }
        out.println("<br><a href=\"" + paramReq.getRenderUrl().setMode(paramReq.Mode_ADMIN) + "\">admin rss resource</a>");
    }
}

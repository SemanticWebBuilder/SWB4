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
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * RSSResource es un recurso de tipo contenido para incluir y tranformar un XML del tipo RSS Resource
 * como recurso.
 *
 * RSSResource is a resource of content type to include and to tranform to XML of RSS Resource type
 * like resource.
 *
 * @author Javier Solis Gonzalez
 */
public class RSSResource extends GenericAdmResource
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(RSSResource.class);
    /** The tpl. */
    private javax.xml.transform.Templates tpl;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            super.setResourceBase(base);
        }
        catch (Exception e)
        {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }

        if (!"".equals(base.getAttribute("template", "").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
            }
            catch (Exception e)
            {
                log.error("Error while loading resource template: " + base.getId() + "-" + base.getTitle(), e);
            }
        }
        if (tpl == null)
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/RSSResource/RSSResource.xslt"));
            }
            catch (Exception e)
            {
                log.error("Error while loading default resource template: " + base.getId() + "-" + base.getTitle(), e);
            }
        }
    }

    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramReq the param req
     * @return the dom
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        Resource base = getResourceBase();
        if (!base.getAttribute("url", "").trim().equals("")) // victor lorenzana bug
        {
            try
            {
                URL url = new URL(base.getAttribute("url", "").trim());
                URLConnection urlconn = url.openConnection();
                InputStream is = urlconn.getInputStream();
                String rss = SWBUtils.IO.readInputStream(is);
                Document dom = SWBUtils.XML.xmlToDom(rss);
                return dom;
            }
            catch (Exception e)
            {
                log.error("Error while generating DOM in resource: " + base.getId() + "-" + base.getTitle(), e);
            }
        }
        else
        {
            log.warn("El valor url no ha sido especificado para el recurso RSSResource con id: "+base.getId());
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doXML(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        try
        {
            Document dom = getDom(request, response, paramReq);
            if (dom != null)
            {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        try
        {
            Document dom = getDom(request, response, paramReq);
            if (dom != null)
            {
                String content = SWBUtils.XML.transformDom(tpl, dom);
                out.println(content);
            }
        }
        catch (Exception e)
        {
            log.error("Error while processing RSS for: " + getResourceBase().getAttribute("url"), e);
        }
    }
}

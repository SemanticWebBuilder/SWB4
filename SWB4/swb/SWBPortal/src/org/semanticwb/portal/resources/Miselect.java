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
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class Miselect.
 */
public class Miselect extends GenericAdmResource {

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(mode.equalsIgnoreCase("fillSelect")) {
            doRenderSelect(request,response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do render select.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        String sectId = request.getParameter("ef");
        WebPage wp = paramRequest.getWebPage().getWebSite().getWebPage(sectId);
        
        out.println("<select name=\"mun\" id=\"mun\" class=\"combos\">");
        if(wp!=null) {
            String lang = paramRequest.getUser().getLanguage();
            Iterator<WebPage> webpages = wp.listVisibleChilds(lang);
            while(webpages.hasNext()) {
                WebPage sect = webpages.next();
                out.println("<option value=\""+sect.getId()+"\">"+sect.getDisplayName(lang)+"</option>");
            }
        }else {
            out.println("<option value=\"\">Seleccione un Municipio</option>");
        }
        out.println("</select>");
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(paramRequest.Call_DIRECT).setMode("fillSelect").setParameter("c", Double.toString(Math.random()));
        out.println("<script type=\"text/javascript\">");
        //out.println("dojo.addOnLoad(function(){postHtml('"+url+"','munctnr')});");
        out.println("</script>");

        WebPage wp = paramRequest.getWebPage().getWebSite().getWebPage("MX");
        String lang = paramRequest.getUser().getLanguage();
        Iterator<WebPage> webpages = wp.listVisibleChilds(lang);

        out.println("<select name=\"entfed\" id=\"entfed\" onchange=\"postHtml('"+url+"'+'&ef='+this.value,'munctnr')\" class=\"combos\">");
        out.println("<option value=\"0\" selected=\"selected\">Seleccione un Estado</option>");
        while(webpages.hasNext()) {
        WebPage sect = webpages.next();
            out.println("<option value=\""+sect.getId()+"\">"+sect.getDisplayName(lang)+"</option>");
        }
        out.println("</select>");
        out.println("<div id=\"munctnr\">");
        out.println("<select name=\"mun\" id=\"mun\" class=\"combos\"><option value=\"\">Seleccione un Municipio</option></select>");
        out.println("</div>");
    }

}

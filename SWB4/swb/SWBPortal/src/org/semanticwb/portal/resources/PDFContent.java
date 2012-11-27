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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * PDFContent se encarga de desplegar y administrar un contenido de tipo remoto en un frame independiente
 * bajo ciertos criterios (configuraci�n de recurso).
 *
 * PDFContent is in charge to unfold and to administer a content of remote type in
 * independent frame under certain criteria (resource configuration). 
 *
 * @author  Vanessa Arredondo
 * @version 1.0
 */
public class PDFContent extends GenericAdmResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(PDFContent.class);

    /**
     * Instantiates a new pDF content.
     */
    public PDFContent() {
    }
    
    /**
     * Metodo que genera el html final del recurso.
     * 
     * @param request the request
     * @param response the response
     * @param paramReq the param req
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException {
        Resource base=paramReq.getResourceBase();
        String faux=base.getAttribute("archive");
        if(faux==null) {
            return;
        }
        String pdf = SWBPortal.getWorkPath() + base.getWorkPath() + "/"+ faux.trim();
    }

    /**
     * Metodo que genera el html final del recurso.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */        
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        
        String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind)) {
            Resource base = getResourceBase();
            String width = paramRequest.getArgument("width", base.getAttribute("width"));
            try {
                Integer.parseInt(width.replaceAll("\\D", ""));
            }catch(Exception e) {
                width = "250";
            }
            String height = paramRequest.getArgument("height", base.getAttribute("height"));
            try {
                Integer.parseInt( height.replaceAll("\\D", "") );
            }catch(Exception e) {
                height = "250";
            }
            
            try {
                StringBuilder htm = new StringBuilder();
                htm.append("<object type=\"application/pdf\" class=\"swb-pdfc\"");
                htm.append(" width=\""+width+"\"");
                htm.append(" height=\""+height+"\"");
                htm.append(" data=\""+SWBPortal.getWebWorkPath()+base.getWorkPath()+"/"+base.getAttribute("archive").trim()+"\">");
                htm.append(paramRequest.getLocaleString("msgRequiredInternetExplorer"));
                htm.append("</object>");
                out.println(htm.toString());
            }catch(Exception e) {
                out.println("<p>"+paramRequest.getLocaleString("msgWrnNoData")+"</p>");
            }
            
        }
        out.flush();
        out.close();
    }
}


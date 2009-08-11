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
 

package org.semanticwb.portal.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/** Objeto que se encarga de desplegar y administrar un contenido de tipo remoto en un frame independiente
 * bajo ciertos criterios (configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer a content of remote type in
 * independent frame under certain criteria (resource configuration). 
 *
 * @author : Vanessa Arredondo
 * @version 1.0
 */
public class PDFContent extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(PDFContent.class);

    public PDFContent() {
    }
    
    /**
     * Metodo que genera el html final del recurso
     * @param request
     * @param response
     * @param reqParams
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException {
        Resource base=paramReq.getResourceBase();
        String faux=base.getAttribute("archive");
        if(faux==null) {
            return;
        }
        String pdf = SWBPlatform.getWorkPath() + base.getWorkPath() + "/"+ faux.trim();
    }

    /**
     * Metodo que genera el html final del recurso
     * @param request
     * @param response
     * @param reqParams
     * @throws SWBResourceException
     * @throws IOException
     */        
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException {        
        Resource base=getResourceBase();
        StringBuffer ret = new StringBuffer("");        
        String ind = request.getParameter("WBIndexer");

        if (!"indexing".equalsIgnoreCase(ind)) {
            try {
                String align = base.getAttribute("align", "top").trim();
                if("center".equalsIgnoreCase(align)) {
                    ret.append("<p align=\"center\">");
                }
                ret.append("<iframe id=\"WBIFrame_"+base.getId()+"\" src=\""+ SWBPlatform.getWebWorkPath() + base.getWorkPath() +"/"+ base.getAttribute("archive").trim() + "\"");
                ret.append(" width=\""+base.getAttribute("width", "100%")+"\"");
                ret.append(" height=\""+base.getAttribute("height", "100%")+"\"");
                ret.append(" marginwidth=\""+base.getAttribute("marginwidth", "0")+"\"");
                ret.append(" marginheight=\""+base.getAttribute("marginheight", "0")+"\"");
                if(!"center".equalsIgnoreCase(align)) {
                    ret.append(" align=\""+ align +"\"");
                }
                ret.append(" scrolling=\""+base.getAttribute("scrollbars", "auto")+"\"");
                ret.append(" frameborder=\""+base.getAttribute("frameborder", "0")+"\"");
                if(!"".equalsIgnoreCase(base.getAttribute("style", ""))) {
                    ret.append(" style=\""+base.getAttribute("style")+"\"");
                }
                ret.append(">");
                ret.append(paramReq.getLocaleString("msgRequiredInternetExplorer"));
                ret.append("</iframe>");
                if("center".equalsIgnoreCase(align)){
                    ret.append("</p>");
                }
            } 
            catch (Exception e) { 
                log.error("Error in resource PDFContent while bringing HTML.", e);
            }            
        }
        response.getWriter().print(ret.toString());
    }
}


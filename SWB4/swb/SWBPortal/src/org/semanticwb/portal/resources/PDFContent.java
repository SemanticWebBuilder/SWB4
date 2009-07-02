
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


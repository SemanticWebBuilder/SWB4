
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

/** Recurso de tipo contenido para incluir y tranformar un XML del tipo RSS Resource
 * como recurso.
 *
 * Resource of content type to include and to tranform to XML of RSS Resource type
 * like resource.
 *
 * @author Javier Solis Gonzalez
 */
public class RSSResource extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(RSSResource.class);
    private javax.xml.transform.Templates tpl;
 
    @Override
    public void setResourceBase(Resource base) {
        try { 
            super.setResourceBase(base); 
        }catch(Exception e) { 
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }

        if(!"".equals(base.getAttribute("template","").trim())) {
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())); 
            }catch(Exception e) { 
                log.error("Error while loading resource template: "+base.getId() +"-"+ base.getTitle(), e);
            }
        }
        if(tpl==null) {
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/RSSResource/RSSResource.xslt")); 
            } catch(Exception e) { 
                log.error("Error while loading default resource template: "+base.getId() +"-"+ base.getTitle(), e);
            }
        }
    }

    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        try {
            URL url = new URL(base.getAttribute("url","").trim());            
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            String rss=SWBUtils.IO.readInputStream(is);
            Document dom = SWBUtils.XML.xmlToDom(rss);            
            return dom;
        }catch (Exception e) {
            log.error("Error while generating DOM in resource: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return null;
    }
  
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException {
        try {
            Document dom=getDom(request, response, paramReq);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }catch(Exception e){ 
            log.error(e);
        }        
    }  
      
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        try {
            Document dom = getDom(request, response, paramReq);
            if(dom != null) {                
                String content = SWBUtils.XML.transformDom(tpl, dom);
                out.println(content);
            }
        }catch (Exception e) { 
            log.error("Error while processing RSS for: "+getResourceBase().getAttribute("url"), e);
        }
    }
}

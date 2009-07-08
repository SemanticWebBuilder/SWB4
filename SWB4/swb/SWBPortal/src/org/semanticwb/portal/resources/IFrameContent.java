
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
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
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 */

public class IFrameContent extends GenericAdmResource 
{
    private static Logger log = SWBUtils.getLogger(IFrameContent.class);
    /** 
     * Creates a new instance of IFrameContent 
     */
    public IFrameContent() {
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
        Resource base=getResourceBase();
        if("".equals(base.getAttribute("url","").trim())) {          
            response.getWriter().print(""); return; 
        }

        StringBuffer ret = new StringBuffer("");        
        String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind))
        {
            try
            {
                if (paramRequest.getCallMethod()==paramRequest.Call_CONTENT)
                {
                    String align=base.getAttribute("align", "top").trim();
                    if("center".equals(align)) {
                        ret.append("<p align=center>");
                    }
                    ret.append("<iframe id=\"WBIFrame_\""+base.getId()+" src=\"" + base.getAttribute("url").trim());
                    Enumeration en = request.getParameterNames();
                    for (int i=0; en.hasMoreElements(); i++)
                    {
                        String param = en.nextElement().toString();
                        if (param.equals("x") || param.equals("y")) {
                            continue;
                        }
                        if (request.getParameter(param).trim().length() > 0)
                        {
                            if ( i > 0) {
                                ret.append("&");
                            }
                            else {
                                ret.append("?");
                            }
                            ret.append(param +"=" + request.getParameter(param));
                        }
                    }
                    ret.append("\" width=\""+base.getAttribute("width", "100%").trim() +"\"");
                    ret.append(" height=\""+base.getAttribute("height", "100%").trim() +"\"");
                    ret.append(" marginwidth=\""+base.getAttribute("marginwidth", "0").trim() +"\"");
                    ret.append(" marginheight=\""+base.getAttribute("marginheight", "0").trim() +"\"");
                    if(!"center".equals(align)) {
                        ret.append(" align=\""+ align +"\"");
                    }
                    ret.append(" scrolling=\""+base.getAttribute("scrollbars", "auto").trim() +"\"");
                    ret.append(" frameborder=\""+base.getAttribute("frameborder", "0").trim() +"\"");
                    if (!"".equals(base.getAttribute("style", "").trim())) {
                        ret.append(" style=\""+base.getAttribute("style").trim() +"\"");
                    }
                    ret.append(">");
                    ret.append(paramRequest.getLocaleString("msgRequiredInternetExplorer"));
                    ret.append("</iframe>");
                    if("center".equals(align)) {
                        ret.append("</p>");
                    }
                } 
                else
                {
                    URL page = new URL(base.getAttribute("url").trim());
                    URLConnection conn = page.openConnection();
                    InputStream in = conn.getInputStream();
                    ret.append(SWBUtils.IO.readInputStream(in));
                }
            } 
            catch (Exception e) { log.error("Error in resource IFrameContent while bringing HTML.", e); }            
        }
        else 
        {
            try
            {
                URL page = new URL(base.getAttribute("url").trim());
                URLConnection conn = page.openConnection();
                InputStream in = conn.getInputStream();
                ret.append(SWBUtils.IO.readInputStream(in));
            } 
            catch (Exception e) { log.error("Error in resource IFrameContent while bringing HTML.", e); }
        }
       PrintWriter out=response.getWriter();
       out.print(ret.toString());        
    }
}
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

/**
 * IFrameContent se encarga de desplegar y administrar un contenido de tipo remoto en un frame independiente
 * bajo ciertos criterios (configuraci�n de recurso).
 *
 * IFrameContent is in charge to unfold and to administer a content of remote type in
 * independent frame under certain criteria (resource configuration). 
 *
 * @author : Vanessa Arredondo Nunez
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
        /*if("".equals(base.getAttribute("url","").trim())) {
            response.getWriter().print(""); return; 
        }*/

        StringBuilder ret = new StringBuilder("");
        /*String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind))
        {*/
            /*try
            {*/
                if (paramRequest.getCallMethod()==paramRequest.Call_CONTENT) {

//                    ret.append("");
//                    ret.append("");

                    ret.append("<object type=\"text/html\" ");
                    String userAgent = request.getHeader("User-Agent").toLowerCase();
                    if( userAgent.indexOf("msie")!=-1 )
                        ret.append("classid=\"clsid:25336920-03F9-11CF-8FD0-00AA00686F13\" ");
                    ret.append("data=\"").append(base.getAttribute("url")).append("\" ");
                    ret.append("width=\"").append(base.getAttribute("width","100%")).append("\" ");
                    ret.append("height=\"").append(base.getAttribute("height","100%")).append("\"> ");

                    ret.append("<iframe ");
                    ret.append(" src=\"").append(base.getAttribute("url")).append("\" ");
                    ret.append(" width=\"").append(base.getAttribute("width","100%")).append("\" ");
                    ret.append(" height=\"").append(base.getAttribute("height","100%")).append("\"");
                    ret.append("</iframe>");

                    ret.append("</object>");

//                    String align=base.getAttribute("align", "top").trim();
//                    if("center".equals(align)) {
//                        ret.append("<p align=center>");
//                    }
//                    ret.append("<iframe id=\"WBIFrame_\""+base.getId()+" src=\"" + base.getAttribute("url").trim());
//                    /*Enumeration en = request.getParameterNames();
//                    for (int i=0; en.hasMoreElements(); i++)
//                    {
//                        String param = en.nextElement().toString();
//                        if (param.equals("x") || param.equals("y")) {
//                            continue;
//                        }
//                        if (request.getParameter(param).trim().length() > 0)
//                        {
//                            if ( i > 0) {
//                                ret.append("&");
//                            }
//                            else {
//                                ret.append("?");
//                            }
//                            ret.append(param +"=" + request.getParameter(param));
//                        }
//                    }*/
//                    ret.append("\" width=\""+base.getAttribute("width", "100%").trim() +"\"");
//                    ret.append(" height=\""+base.getAttribute("height", "100%").trim() +"\"");
//                    ret.append(" marginwidth=\""+base.getAttribute("marginwidth", "0").trim() +"\"");
//                    ret.append(" marginheight=\""+base.getAttribute("marginheight", "0").trim() +"\"");
//                    if(!"center".equals(align)) {
//                        ret.append(" align=\""+ align +"\"");
//                    }
//                    ret.append(" scrolling=\""+base.getAttribute("scrollbars", "auto").trim() +"\"");
//                    ret.append(" frameborder=\""+base.getAttribute("frameborder", "0").trim() +"\"");
//                    if (!"".equals(base.getAttribute("style", "").trim())) {
//                        ret.append(" style=\""+base.getAttribute("style").trim() +"\"");
//                    }
//                    ret.append(">");
//                    ret.append(paramRequest.getLocaleString("msgRequiredInternetExplorer"));
//                    ret.append("</iframe>");
//                    if("center".equals(align)) {
//                        ret.append("</p>");
//                    }
                }else {
                    URL page = new URL(base.getAttribute("url").trim());
                    URLConnection conn = page.openConnection();
                    InputStream in = conn.getInputStream();
                    ret.append(SWBUtils.IO.readInputStream(in));
                }
            /*}catch (Exception e) {
                log.error("Error in resource IFrameContent while bringing HTML.", e);
            }*/
        /*}
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
        }*/
       PrintWriter out=response.getWriter();
       out.print(ret.toString());
    }
}
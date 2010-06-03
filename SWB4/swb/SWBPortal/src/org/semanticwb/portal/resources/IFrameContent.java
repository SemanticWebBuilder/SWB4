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

// TODO: Auto-generated Javadoc
/**
 * IFrameContent se encarga de desplegar y administrar un contenido de tipo remoto en una etiqueta objetc
 * bajo ciertos criterios (configuración de recurso).
 *
 * IFrameContent is in charge to unfold and to administer a content of remote type in
 * object tag under certain criteria (resource configuration).
 *
 * @version 2.0
 */

public class IFrameContent extends GenericAdmResource 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(IFrameContent.class);
    
    /**
     * Creates a new instance of IFrameContent.
     */
    public IFrameContent() {
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        if("".equals(base.getAttribute("url","").trim())) {          
            response.getWriter().print(""); return; 
        }

        StringBuffer ret = new StringBuffer("");        
        String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind)) {
            if (paramRequest.getCallMethod()==paramRequest.Call_CONTENT) {
                System.out.println("\n\nmimetype="+base.getAttribute("mimetype"));
                /*String align = base.getAttribute("align", "top");
                if("center".equals(align)) {
                    ret.append("<p align=\"center\">");
                }*/
                ret.append("<object standby=\""+paramRequest.getLocaleString("standby")+"\"");
                ret.append(" data=\""+base.getAttribute("url"));
                Enumeration<String> params = request.getParameterNames();
                for(int i=0; params.hasMoreElements(); i++)
                {
                    String param = params.nextElement();
                    if( param.equals("x")||param.equals("y") ) {
                        continue;
                    }
                    if( !request.getParameter(param).equals("") ) {
                        if( i>0) {
                            ret.append("&");
                        }else {
                            ret.append("?");
                        }
                        ret.append(param+"="+request.getParameter(param));
                    }
                }
                ret.append("\"");
                ret.append(" type=\""+base.getAttribute("mimetype")+"\"");
                if( base.getAttribute("width")!=null )
                    ret.append(" width=\""+base.getAttribute("width")+"\"");
                if( base.getAttribute("height")!=null )
                    ret.append(" height=\""+base.getAttribute("height")+"\"");
                /*if( base.getAttribute("hspace")!=null )
                    ret.append(" hspace=\""+base.getAttribute("hspace")+"\"");
                if( base.getAttribute("vspace")!=null )
                    ret.append(" vspace=\""+base.getAttribute("vspace")+"\"");*/
                /*if(!"center".equals(align)) {
                    ret.append(" align=\""+align+"\"");
                }*/
                /*ret.append(" border=\""+base.getAttribute("border","0")+"\"");*/
                if( base.getAttribute("style")!=null ) {
                    ret.append(" style=\""+base.getAttribute("style")+"\"");
                }
                if( base.getAttribute("cssclass")!=null ) {
                    ret.append(" class=\""+base.getAttribute("cssclass")+"\"");
                }
                ret.append(">");
                ret.append(paramRequest.getLocaleString("browserNoSupport"));
                ret.append("<br /><a href=\""+base.getAttribute("url")+"\">"+paramRequest.getLocaleString("viewContent")+"</a>");
                ret.append("</object>");
                /*if("center".equals(align)) {
                    ret.append("</p>");
                }*/
            }
            else
            {
                URL page = new URL(base.getAttribute("url").trim());
                URLConnection conn = page.openConnection();
                InputStream in = conn.getInputStream();
                ret.append(SWBUtils.IO.readInputStream(in));
            }
        }else {
            try {
                URL page = new URL(base.getAttribute("url").trim());
                URLConnection conn = page.openConnection();
                InputStream in = conn.getInputStream();
                ret.append(SWBUtils.IO.readInputStream(in));
            }catch (Exception e) {
                log.error("Error in resource IFrameContent while bringing HTML.", e);
            }
        }
       PrintWriter out=response.getWriter();
       out.print(ret.toString());        
    }
}
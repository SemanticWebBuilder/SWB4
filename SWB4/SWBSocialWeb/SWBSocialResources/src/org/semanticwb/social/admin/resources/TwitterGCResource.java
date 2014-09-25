/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.TwitterGC;
import org.semanticwb.social.SocialAdmin;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class TwitterGCResource extends GenericResource{
    
    /*
    private static Logger log = SWBUtils.getLogger(TwitterGCResource.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String suri=request.getParameter("suri");
        
        out.println("<script type=\"javascript\">");
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        if (request.getParameter("reloadTap") != null) {
            out.println(" reloadTab('" + suri + "'); ");
        }
        out.println("</script>");

        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/oauth/twittergc.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }
    
      @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
          
        setProperties(request, response);

        response.setRenderParameter("statusMsg", response.getLocaleString("saveUserExtAttr"));
        response.setRenderParameter("suri", request.getParameter("suri"));
        response.setRenderParameter("reloadTap", request.getParameter("suri"));
        response.setMode(response.Mode_VIEW);
        //response.setCallMethod(response.Call_CONTENT);
    }
      
      
    private void setProperties(HttpServletRequest request, SWBActionResponse response) {
        //User user=response.getUser();
        //Tomando en cuenta que todos los usuarios que se modificaran en sus propiedades extendidas, sean usuarios del repositorio de Admin.
        SocialAdmin wsite=(SocialAdmin)SWBContext.getAdminWebSite(); 

        if(request.getParameter("appKey")!=null && !request.getParameter("appKey").isEmpty() && request.getParameter("secretKey")!=null && !request.getParameter("secretKey").isEmpty())
        {
           if(request.getParameter("twitterGCid")!=null)
           {
               TwitterGC twitterGC=TwitterGC.ClassMgr.getTwitterGC(request.getParameter("twitterGCid"), wsite);
               if(twitterGC!=null)
               {
                   wsite.setAdm_twittergc(null);
                   twitterGC.remove();
               }
           } 
           TwitterGC twitterGC=TwitterGC.ClassMgr.createTwitterGC(wsite);
           twitterGC.setAppKey(request.getParameter("appKey"));
           twitterGC.setSecretKey(request.getParameter("secretKey"));
           wsite.setAdm_twittergc(twitterGC);
        }else if(request.getParameter("twitterGCid")!=null) {
               TwitterGC twitterGC=TwitterGC.ClassMgr.getTwitterGC(request.getParameter("twitterGCid"), wsite);
               if(twitterGC!=null)
               {
                   wsite.setAdm_twittergc(null);
                   twitterGC.remove();
               }
        } 
    }  
    * */
}

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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 * 
 * Clase que se utiliza para publicar en las redes sociales de manera Generica desde el menu superior --"Publicar/En Redes Sociales"--
 * 
 */
public class CreateGenericPost extends CreatePost { 
    
    private static Logger log = SWBUtils.getLogger(CreateGenericPost.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out=response.getWriter();
        if(request.getParameter("statusMsg")!=null)
        {
            out.println("<script type=\"text/javascript\">");
            out.println("   showStatus('"+request.getParameter("statusMsg")+"');");
            /*
            if(request.getParameter("reloadTab")!=null)
            {
                out.println(" reloadTab('" + request.getParameter("reloadTab") + "');");//so
            } */           
            out.println("</script>");
            return;
        }
        
        String basePath=SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/";
        String jspResponse = basePath+"socialSites.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("afterChooseSite")) {
            doSocialTopics(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("genericPost")) {
            doCreateGenericPost(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }


     public void doSocialTopics(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/socialTopics.jsp");
        request.setAttribute("socialSite", request.getParameter("socialSite"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a socialTopis.jsp " + ex.getMessage());
        }
     }
     
     public void doCreateGenericPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {    
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/createPost.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("source", "RedSocial");
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a createPost.jsp " + ex.getMessage());
        }
     }
   
}

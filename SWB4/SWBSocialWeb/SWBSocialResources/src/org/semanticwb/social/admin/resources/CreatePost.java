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
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.Photo;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Video;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;

/**
 *
 * @author Jorge.Jimenez
 * 
 * Clase que se utiliza para publicar en las redes sociales desde un Tema.
 * 
 * @modified by Francisco.Jiménez
 * 
 */
public class CreatePost extends GenericResource {

    private static Logger log = SWBUtils.getLogger(CreatePost.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out=response.getWriter();
        
        if(request.getParameter("statusMsg")!=null)
        {
            out.println("<script type=\"text/javascript\">");
            out.println("   showStatus('"+request.getParameter("statusMsg")+"');");       
            if(request.getParameter("reloadTab")!=null)
            {
                out.println(" reloadTab('" + request.getParameter("reloadTab") + "');");//so
            }            
            out.println("</script>");
            //return;
        }
        if(request.getParameter("suri")!=null)
        {
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/createPost.jsp";
            if (request.getParameter("jspResponse") != null) {
                jspResponse = request.getParameter("jspResponse");
            }
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        try {
            ArrayList aSocialNets=new ArrayList();
            WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
            String objUri = request.getParameter("objUri");
            String action = response.getAction();
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            SocialTopic socialTopic = (SocialTopic) semanticObject.createGenericInstance();
            
            String toPost = request.getParameter("toPost");
            SocialPFlow spflow=null;
            //System.out.println("processA/socialFlow:"+request.getParameter("socialFlow"));
            if(request.getParameter("socialFlow")!=null && request.getParameter("socialFlow").trim().length()>0)
            {
                SemanticObject semObjSFlow=SemanticObject.getSemanticObject(request.getParameter("socialFlow"));
                spflow=(SocialPFlow)semObjSFlow.createGenericInstance();
                
                //Revisa si el flujo de publicación soporte el tipo de postOut, de lo contrario, asinga null a spflow, para que no 
                //asigne flujo al mensaje de salida., Esto también esta validado desde el jsp typeOfContent
                if((toPost.equals("msg") && !SocialLoader.getPFlowManager().isManagedByPflow(spflow, Message.sclass)) || 
                        (toPost.equals("photo") && !SocialLoader.getPFlowManager().isManagedByPflow(spflow, Photo.sclass)) ||
                        (toPost.equals("video") && !SocialLoader.getPFlowManager().isManagedByPflow(spflow, Video.sclass)))
                {
                    spflow=null;
                }
            }
            //System.out.println("CreatePost/ProcessA-2");
            String socialUri = "";
            int j = 0;
            Enumeration<String> enumParams = request.getParameterNames();
            while (enumParams.hasMoreElements()) {
                String paramName = enumParams.nextElement();
                if (paramName.startsWith("http://")) {
                    if (socialUri.trim().length() > 0) {
                        socialUri += "|";
                    }
                    socialUri += paramName;
                    j++;
                }
            }
            if (j != 0 && (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo"))) 
            {
                if (socialUri != null) // La publicación por lo menos se debe enviar a una red social
                {
                    String[] socialUris = socialUri.split("\\|");  //Dividir valores
                    for (int i = 0; i < socialUris.length; i++) {
                        String tmp_socialUri = socialUris[i];
                        SemanticObject semObject = SemanticObject.getSemanticObject(tmp_socialUri);
                        SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                        //Se agrega la red social de salida al post
                        aSocialNets.add(socialNet);
                    }
                    //SWBSocialUtil.PostOutUtil.publishPost(postOut, request, response);
                    System.out.println("Se publicaJ-1");
                    SWBSocialUtil.PostOutUtil.sendNewPost(null, socialTopic, spflow, aSocialNets, wsite, toPost, request, response);
                    response.setRenderParameter("statusMsg", SWBUtils.TEXT.encode(response.getLocaleLogString("postCreated"),"iso8859-1"));
                    response.setMode(SWBResourceURL.Mode_VIEW);
                 }else {    //Enviar a statusBar que no se publicó el mensaje en ninguna red social.
                    response.setMode(SWBResourceURL.Mode_VIEW);
                    response.setRenderParameter("statusMsg", response.getLocaleLogString("postNotCreatedNoNet"));
                    response.setRenderParameter("reloadTab", socialTopic.getURI());
                 }
            } else {
                response.setMode(SWBResourceURL.Mode_VIEW);
                response.setRenderParameter("statusMsg", response.getLocaleLogString("postTypeNotDefined"));
                response.setRenderParameter("reloadTab", socialTopic.getURI());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/typeOfContent.jsp");
        request.setAttribute("contentType", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        //request.setAttribute("action", request.getParameter("action"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.Messageable;
import org.semanticwb.social.Photo;
import org.semanticwb.social.Photoable;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Video;
import org.semanticwb.social.Videoable;
import org.semanticwb.social.util.PostableObj;
import org.semanticwb.social.util.SendPostThread;

/**
 *
 * @author Jorge.Jimenez
 * @modified by Francisco.Jiménez
 */
public class CreatePost extends GenericResource {

    private static Logger log = SWBUtils.getLogger(CreatePost.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/createPost.jsp";
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
            WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
            String objUri = request.getParameter("objUri");
            String action = response.getAction();
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            SocialTopic socialTopic = (SocialTopic) semanticObject.createGenericInstance();
            String toPost = request.getParameter("toPost");
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
            if (j != 0 && (action.equals("postMessage")
                    || action.equals("uploadPhoto")
                    || action.equals("uploadVideo"))) {
                if (socialUri != null) {
                    SWBFormMgr mgr = null;
                    if (toPost.equals("msg")) {
                        mgr = new SWBFormMgr(Message.sclass, wsite.getSemanticObject(), null);
                    } else if (toPost.equals("photo")) {
                        mgr = new SWBFormMgr(Photo.sclass, wsite.getSemanticObject(), null);
                    } else if (toPost.equals("video")) {
                        mgr = new SWBFormMgr(Video.sclass, wsite.getSemanticObject(), null);
                    }
                    if (mgr != null) {
                        mgr.setFilterRequired(false);
                        SemanticObject sobj = mgr.processForm(request);
                        org.semanticwb.social.Post post = (org.semanticwb.social.Post) sobj.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                        //Revisa las redes sociales a las cuales se tiene que enviar el Post
                        String[] socialUris = socialUri.split("\\|");  //Dividir valores
                        for (int i = 0; i < socialUris.length; i++) {
                            String tmp_socialUri = socialUris[i];
                            SemanticObject semObject = SemanticObject.createSemanticObject(tmp_socialUri, wsite.getSemanticModel());
                            SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                            if (toPost.equals("msg") && socialNet instanceof Messageable) {
                                System.out.println("MENSAJE!!");
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Messageable messageable = (Messageable) socialNet;
                                //messageable.postMsg((Message) post, request, response);
                                PostableObj postableObj = new PostableObj(messageable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            } else if (toPost.equals("photo") && socialNet instanceof Photoable) {
                                System.out.println("PHOTO!!");
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Photoable photoable = (Photoable) socialNet;
                                //photoable.postPhoto((Photo) post, request, response);
                                PostableObj postableObj = new PostableObj(photoable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            } else if (toPost.equals("video") && socialNet instanceof Videoable) {
                                System.out.println("VIDEO!!");
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Videoable videoable = (Videoable) socialNet;
                                //videoable.postVideo((Video) post, request, response);
                                PostableObj postableObj = new PostableObj(videoable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            }
                        }
                    }
                }
            } else {
                response.setMode(SWBResourceURL.Mode_EDIT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Error en process Action");
            e.printStackTrace();
        }
    }

    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        System.out.println("\ndoCreatePost");
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/typeOfContent.jsp");
        request.setAttribute("valor", request.getParameter("valor"));
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

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Post Creado Correctamente!");
        out.println("<script type=\"text/javascript\">");
        out.println("   showStatus('Post Creado Correctamente!');");            
        out.println("</script>");
    }
}

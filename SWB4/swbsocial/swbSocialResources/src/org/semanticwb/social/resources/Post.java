/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.*;
import org.semanticwb.social.components.resources.GenericSocialResource;
import org.semanticwb.social.util.PostableObj;
import org.semanticwb.social.util.SendPostThread;

/**
 *
 * @author jorge.jimenez
 */
public class Post extends GenericSocialResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(Post.class);


    /*
     * (non-Javadoc) @see
     * org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = "/swbadmin/jsp/social/post.jsp";
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
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        super.doEdit(request, response, paramRequest);
        PrintWriter out = response.getWriter();
        out.print("Se envió ...");
    }


    /*
     * (non-Javadoc) @see
     * org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest,
     * org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
        String socialUri = request.getParameter("socialUri");
        String toPost = request.getParameter("toPost");
        String objUri = request.getParameter("objUri");
        String action = response.getAction();

        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        SocialTopic socialTopic = (SocialTopic) semanticObject.createGenericInstance();


        if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
            try {
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
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Messageable messageable = (Messageable) socialNet;
                                messageable.postMsg((Message) post, request, response);
                                PostableObj postableObj = new PostableObj(messageable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            } else if (toPost.equals("photo") && socialNet instanceof Photoable) {
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Photoable photoable = (Photoable) socialNet;
                                photoable.postPhoto((Photo) post, request, response);
                                PostableObj postableObj = new PostableObj(photoable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            } else if (toPost.equals("video") && socialNet instanceof Videoable) {
                                //TODO: YO CREO QUE LO QUE TENGO QUE HACER AQUI, ES UN THREAD POR CADA UNA DE LAS REDES SOCIALES A LAS QUE SE ENVÍE UN POST
                                Videoable videoable = (Videoable) socialNet;
                                videoable.postVideo((Video) post, request, response);
                                PostableObj postableObj = new PostableObj(videoable, post, toPost, request, response);
                                SendPostThread sendPostThread = new SendPostThread();
                                sendPostThread.addPostAble(postableObj);
                                sendPostThread.start();
                                response.setMode(SWBResourceURL.Mode_EDIT);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}

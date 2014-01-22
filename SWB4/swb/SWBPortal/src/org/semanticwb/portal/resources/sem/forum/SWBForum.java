/*
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
 */
package org.semanticwb.portal.resources.sem.forum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.servlet.internal.UploadFormElement;

// TODO: Auto-generated Javadoc
/**
 * Resource that manage a forum.
 *
 * @author : Jorge Alberto Jiménez
 * @version 1.0
 */
public class SWBForum extends org.semanticwb.portal.resources.sem.forum.base.SWBForumBase {

    /**
     * Instantiates a new sWB forum.
     */
    public SWBForum() {
    }

    /**
     * Instantiates a new sWB forum.
     *
     * @param base the base
     */
    public SWBForum(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(SWBForum.class);
    /**
     * The lang.
     */
    private String lang = "es";
    public final static String ATT_THREADS = "threads";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("addThread")) {
            if (!isAcceptGuessUsers() && !paramRequest.getUser().isSigned()) {
                response.sendError(403);
            } else {
                doAddThread(request, response, paramRequest);
            }
        } else if (paramRequest.getMode().equals("editThread")) {
            if (!isAcceptGuessUsers() && !paramRequest.getUser().isSigned()) {
                response.sendError(403);
            } else {
                doEditThread(request, response, paramRequest);
            }
        } else if (paramRequest.getMode().equals("editPost")) {
            if (!isAcceptGuessUsers() && !paramRequest.getUser().isSigned()) {
                response.sendError(403);
            } else {
                doEditPost(request, response, paramRequest);
            }
        } else if (paramRequest.getMode().equals("replyPost")) {
            if (!isAcceptGuessUsers() && !paramRequest.getUser().isSigned()) {
                response.sendError(403);
            } else {
                doReplyPost(request, response, paramRequest);
            }
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = getJspView();
        if (path == null) {
            path = "/swbadmin/jsp/forum/swbForum.jsp";
//            path = SWBPlatform.getContextPath() + "/swbadmin/jsp/forum/swbForum.jsp";
        }
        try {
            request.setAttribute("acceptguesscomments", isAcceptGuessUsers());
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("flag", request.getParameter("flag"));
            request.setAttribute("isCaptcha", isCaptcha());
            request.setAttribute(ATT_THREADS, listThreads(request, paramRequest));
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error on doView, " + path + ", " + e.getMessage());
        }
    }

    /**
     * Do add thread.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAddThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
//        String path = SWBPlatform.getContextPath() + "/swbadmin/jsp/forum/swbForoEdit.jsp";
        String path = "/swbadmin/jsp/forum/swbForoEdit.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("isCaptcha", isCaptcha());
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (ServletException e) {
            log.error("Error on doAddThread, " + path + ", " + e.getMessage());
        }


//        PrintWriter out = response.getWriter();
//
//        //SWBFormMgr mgr = new SWBFormMgr(Thread.frm_Thread, paramRequest.getWebPage().getSemanticObject(), null);
//        SWBFormMgr mgr = new SWBFormMgr(Thread.sclass, paramRequest.getWebPage().getSemanticObject(), null);
////        Iterator<SemanticProperty> itsem = mgr.getProperties().iterator();
////        while(itsem.hasNext()){
////            SemanticProperty semprop = itsem.next();
////            System.out.println("getDisplayName : " + semprop.getDisplayName());
////            System.out.println("getName : " + semprop.getName());
////            System.out.println("getURI : " + semprop.getURI());
////            System.out.println("semprop : " + semprop);
////        }
//        mgr.clearProperties();
////        mgr.addProperty(Thread.swb_title);
//        /*
//         mgr.setType(mgr.TYPE_DOJO);
//         mgr.setCaptchaStatus(true);
//         if (paramRequest.getUser() != null) {
//         lang = paramRequest.getUser().getLanguage();
//         }
//         mgr.setLang(lang);
//         mgr.setSubmitByAjax(false);
//         SWBResourceURL url = paramRequest.getActionUrl();
//         url.setAction("addThread");
//         mgr.setAction(url.toString());
//        
//         mgr.hideProperty(Thread.frm_hasThAttachments);
//
//         request.setAttribute("formName", mgr.getFormName());
//         mgr.addButton(SWBFormButton.newSaveButton());
//         mgr.addButton(SWBFormButton.newBackButton());
//         out.println(mgr.renderForm(request));*/
////        out.print(mgr.renderForm(request));
//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setAction("addThread");
//        out.print("<div class=\"row\">"
//                + "     <div class=\"col-lg-6 col-lg-offset-3\">"
//                + "         <form class=\"form-horizontal\" method=\"post\" action=\"" + url + "\" id=\"saveTheme\">"
//                + "             <div class=\"panel panel-default swbp-panel\">\n"
//                + "                <div class=\"panel-heading swbp-panel-title\">\n"
//                + "                    <div class=\"panel-title\">\n"
//                + "                        <h1 class=\"panel-title\">"
//                + "                         <span class=\"fa fa-plus\"></span>"
//                + "                          Agregar tema </h1>"
//                + "                     </div>"
//                + "                 </div>"
//                + "                 <div class=\"panel-body\">");
//        out.print(mgr.getFormHiddens());
//        //For field title
//        String title = mgr.renderLabel(request, Thread.swb_title, SWBFormMgr.MODE_CREATE);
//        title = title.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String titleInput = mgr.renderElement(request, Thread.swb_title, SWBFormMgr.MODE_CREATE);
//        
//        titleInput = titleInput.replaceFirst(">", " required class=\"form-control\">");
//        titleInput = titleInput.replace(titleInput.substring(titleInput.indexOf("style"), (titleInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + Thread.swb_title.getName() + "\">");
//        out.print(title);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(titleInput);
//        out.print("                         </div>"
//                + "                     </div>");
//
//        //For field message
//        String message = mgr.renderLabel(request, Thread.frm_thBody, SWBFormMgr.MODE_CREATE);
//        message = message.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String messageInput = mgr.renderElement(request, Thread.frm_thBody, SWBFormMgr.MODE_CREATE);
//        messageInput = messageInput.replaceFirst(">", " required class=\"form-control\">");
//        messageInput = messageInput.replace(messageInput.substring(messageInput.indexOf("style"), (messageInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + Thread.frm_thBody.getName() + "\">");
//        out.print(message);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(messageInput);
//        out.print("                         </div>"
//                + "                     </div>");
//        
//        
//        out.print("                 </div>" //End panel-body
//                + "                 <div class=\"panel-footer text-right\">"
//                + "                     <a class=\"btn btn-default\" style=\"color: #fff;\"  href=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW) + "\">"
//                + "                         <span class=\"fa fa-mail-reply\"></span>"
//                + "                         Cancelar</a>"
//                + "                     <button class=\"btn btn-success\" type=\"submit\" id=\"btnTheme\">"
//                + "                         <span class=\"fa fa-save\"></span>"
//                + "                          Guardar</button>"
//                + "             </div>"//End panel
//                + "         </form>"
//                + "     </div>" // End col-lg-4
//                + "</div>"); // End row
//        out.print(getScriptValidator("btnTheme", "saveTheme"));

    }

    /**
     * Do reply post.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doReplyPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/swbadmin/jsp/forum/swbForoPostEdit.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("lblAction", "replyPost");
//        request.setAttribute("threadUri", request.getParameter("threadUri"));
//        request.setAttribute("postUri", request.getParameter("postUri"));
        request.setAttribute("isCaptcha", isCaptcha());
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (ServletException e) {
            log.error("Error on doEditPost, " + path + ", " + e.getMessage());
        }


//        PrintWriter out = response.getWriter();
//        WebSite website = paramRequest.getWebPage().getWebSite();
//        SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
//        Thread thread = Thread.ClassMgr.getThread(soThread.getId(), website);
//        SemanticObject soPost = null;
//        Post post = null;
//        if (request.getParameter("postUri") != null) {
//            soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
//            post = Post.ClassMgr.getPost(soPost.getId(), website);
//        }
//        SWBFormMgr mgr = null;
//        if (soPost != null) {
//            mgr = new SWBFormMgr(Post.frm_Post, soPost, null);
//        } else {
//            mgr = new SWBFormMgr(Post.frm_Post, soThread, null);
//        }
////        mgr.setCaptchaStatus(true);
////        mgr.hideProperty(Post.frm_hasAttachments);
//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setParameter("threadUri", request.getParameter("threadUri"));
//        url.setParameter("postUri", request.getParameter("postUri"));
//
//        if (paramRequest.getUser() != null) {
//            lang = paramRequest.getUser().getLanguage();
//        }
////        mgr.setLang(lang);
////        mgr.setSubmitByAjax(false);
////        mgr.setType(mgr.TYPE_DOJO);
//        url.setAction("replyPost");
//        mgr.setAction(url.toString());
//        /*
//         out.println("<link href=\"/swb/swbadmin/css/forum.css\" rel=\"stylesheet\" type=\"text/css\" />");
//         out.println("<table>");
//         out.println("<tr><td>" + paramRequest.getLocaleString("thread") + ":</td><td><b>" + thread.getTitle() + "</b></td></tr>");
//         if (post != null) {
//         out.println("<tr><td>" + paramRequest.getLocaleString("msg") + ":</td><td><b>" + post.getBody() + "</b></td></tr>");
//         } else {
//         out.println("<tr><td>" + paramRequest.getLocaleString("msg") + ":</td><td><b>" + thread.getBody() + "</b></td></tr>");
//         }
//         out.println("</table>");*/
//
////        mgr.addButton(SWBFormButton.newSaveButton());
////        mgr.addButton(SWBFormButton.newBackButton());
////        request.setAttribute("formName", mgr.getFormName());
////        out.println(mgr.renderForm(request));
//
//        out.print("<div class=\"row\">"
//                + "     <div class=\"col-lg-6 col-lg-offset-3\">"
//                + "         <form class=\"form-horizontal\" method=\"post\" action=\"" + url + "\" id=\"formRP\">"
//                + "             <div class=\"panel panel-default swbp-panel\">\n"
//                + "                <div class=\"panel-heading swbp-panel-title\">\n"
//                + "                    <div class=\"panel-title\">\n"
//                + "                        <h1 class=\"panel-title\">" + paramRequest.getLocaleString("thread") + ": " + thread.getTitle() + " </h1>"
//                + "                     </div>"
//                + "                 </div>"
//                + "                 <div class=\"panel-body\">");
//        out.print("                     <ul class=\"list-group\">\n");
//        if (post != null) {
//            out.print("                         <li class=\"list-group-item\"><span class=\"fa fa-comment fa-fw\"></span><strong> " + paramRequest.getLocaleString("msg") + ":</strong> " + post.getBody() + "</li>\n");
//        } else {
//            out.print("                         <li class=\"list-group-item\"><span class=\"fa fa-comment fa-fw\"></span><strong> " + paramRequest.getLocaleString("msg") + ":</strong> " + thread.getBody() + "</li>\n");
//        }
//        out.print("                     </ul>\n");
//        out.print(mgr.getFormHiddens());
//        //For field message
//        String message = mgr.renderLabel(request, Thread.frm_thBody, SWBFormMgr.MODE_EDIT);
//        if (soPost != null) {
//            message = mgr.renderLabel(request, Post.frm_pstBody, SWBFormMgr.MODE_EDIT);
//        }
//
//        message = message.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String name = Thread.frm_thBody.getName();
//        String messageInput = mgr.renderElement(request, Thread.frm_thBody, SWBFormMgr.MODE_EDIT);
//        if (soPost != null) {
//            messageInput = mgr.renderElement(request, Post.frm_pstBody, SWBFormMgr.MODE_EDIT);
//            name = Post.frm_pstBody.getName();
//        }
//        messageInput = messageInput.replaceFirst(">", " required class=\"form-control\">");
//        messageInput = messageInput.replace(messageInput.substring(messageInput.indexOf("style"), (messageInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + name + "\">");
//        out.print(message);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(messageInput);
//        out.print("                         </div>"
//                + "                     </div>");
//
//        SWBResourceURL urlViewPost = paramRequest.getRenderUrl();
//        urlViewPost.setMode(urlViewPost.Mode_VIEW);
//        urlViewPost.setAction("viewPost");
//        urlViewPost.setParameter("threadUri", thread.getURI());
//
//        out.print("                 </div>" //End panel-body
//                + "                 <div class=\"panel-footer text-right\">"
//                + "                     <a class=\"btn btn-default \" style=\"color: #fff;\"  href=\"" + urlViewPost + "\">"
//                + "                         <span class=\"fa fa-mail-reply\"></span>"
//                + "                         Cancelar</a>"
//                + "                     <button class=\"btn btn-success\" type=\"submit\" id=\"btnSaveRP\">"
//                + "                         <span class=\"fa fa-save\"></span>"
//                + "                          Guardar</button>"
//                + "             </div>"//End panel
//                + "         </form>"
//                + "     </div>" // End col-lg-4
//                + "</div>"); // End row
//        out.print(getScriptValidator("btnSaveRP", "formRP"));


    }

    /**
     * Do edit post.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/swbadmin/jsp/forum/swbForoPostEdit.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("isCaptcha", isCaptcha());
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (ServletException e) {
            log.error("Error on doEditPost, " + path + ", " + e.getMessage());
        }




//        PrintWriter out = response.getWriter();
//        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
//        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
//        if (paramRequest.getUser() != null) {
//            lang = paramRequest.getUser().getLanguage();
//        }
////        mgr.setLang(lang);
////        mgr.setSubmitByAjax(false);
////        mgr.setType(mgr.TYPE_XHTML);
//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setParameter("threadUri", request.getParameter("threadUri"));
//        url.setParameter("postUri", semObject.getURI());
//        url.setAction("editPost");
////        mgr.setAction(url.toString());
////
////        mgr.hideProperty(Post.swb_active);
////        mgr.hideProperty(Post.frm_hasAttachments);
//
////        out.print("on doEditPost.............");
//
//        /* Para mostrar attaches
//         Resource base = paramRequest.getResourceBase();
//         WebSite website = paramRequest.getWebPage().getWebSite();
//         Post post = Post.getPost(semObject.getId(), website);
//         String basepath = "";
//         int count = 0;
//         GenericIterator<Attachment> lAttchments = post.listAttachmentss();
//
//         url.setAction("removeAttach");
//         while (lAttchments.hasNext()) {
//         count++;
//         Attachment attch = lAttchments.next();
//         basepath = SWBPortal.getWebWorkPath() + "/models/" + website.getId() + "/Resource/" + base.getId() + "/replies/" + post.getId() + "/" + attch.getFileName();
//         request.setAttribute("attach_hasThAttachments_"+ count, basepath);
//         request.setAttribute("attachTarget_hasThAttachments_" +count, "blank");
//         url.setParameter("removeAttach", attch.getURI());
//         request.setAttribute("attachRemovePath_hasThAttachments_" + count, url.toString());
//         }
//         if (count > 0) {
//         request.setAttribute("attachCount_hasThAttachments", "" + count);
//         }
//         * */
////        mgr.addButton(SWBFormButton.newSaveButton());
////        mgr.addButton(SWBFormButton.newBackButton());
////        request.setAttribute("formName", mgr.getFormName());
////        out.println(mgr.renderForm(request));
//        out.print("<div class=\"row\">"
//                + "     <div class=\"col-lg-6 col-lg-offset-3\">"
//                + "         <form class=\"form-horizontal\" method=\"post\" action=\"" + url + "\" id=\"formEditPost\">"
//                + "             <div class=\"panel panel-default swbp-panel\">\n"
//                + "                <div class=\"panel-heading swbp-panel-title\">\n"
//                + "                    <div class=\"panel-title\">\n"
//                + "                        <h1 class=\"panel-title\">"
//                + "                             <span class=\"fa fa-pencil-square-o \"></span>"
//                + "                              " + paramRequest.getLocaleString("edit") + " " + paramRequest.getLocaleString("msg") + "</h1>"
//                + "                     </div>"
//                + "                 </div>"
//                + "                 <div class=\"panel-body\">");
//        out.print(mgr.getFormHiddens());
//
//        //For field message
//        String message = mgr.renderLabel(request, Post.frm_pstBody, SWBFormMgr.MODE_EDIT);
//        message = message.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String messageInput = mgr.renderElement(request, Post.frm_pstBody, SWBFormMgr.MODE_EDIT);
//        messageInput = messageInput.replaceFirst(">", " required class=\"form-control\">");
//        messageInput = messageInput.replace(messageInput.substring(messageInput.indexOf("style"), (messageInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + Post.frm_pstBody.getName() + "\">");
//        out.print(message);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(messageInput);
//        out.print("                         </div>"
//                + "                     </div>");
//
//        Post post = (Post) semObject.createGenericInstance();
//
//        out.print("                     <ul class=\"list-group\">\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-user fa-fw\"></span><strong>" + (post != null ? (post.getCreator() != null ? (post.getCreator() != null ? post.getCreator().getFullName() : "") : "--") : "") + "</strong></li>\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-calendar fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_created, SWBFormMgr.MODE_VIEW) + ":</strong> " + mgr.renderElement(request, Thread.swb_created, SWBFormMgr.MODE_VIEW) + "</li>\n"
//                + "                     </ul>\n"
//                + "\n"
//                + "                     <ul class=\"list-group\">\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-user fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_modifiedBy, SWBFormMgr.MODE_VIEW) + ":</strong> " + (post != null ? (post.getModifiedBy() != null ? (post.getModifiedBy() != null ? post.getModifiedBy().getFullName() : "") : "--") : "") + "</li>\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-calendar fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_updated, SWBFormMgr.MODE_VIEW) + ":</strong> " + mgr.renderElement(request, Thread.swb_updated, SWBFormMgr.MODE_VIEW) + "</li>\n"
//                + "                     </ul>");
//        SWBResourceURL urlViewPost = paramRequest.getRenderUrl();
//        urlViewPost.setMode(urlViewPost.Mode_VIEW);
//        urlViewPost.setAction("viewPost");
//        urlViewPost.setParameter("threadUri", post.getThread().getURI());
//
//        out.print("                 </div>" //End panel-body
//                + "                 <div class=\"panel-footer text-right\">"
//                + "                     <a class=\"btn btn-default\" style=\"color: #fff;\"  href=\"" + urlViewPost + "\">"
//                + "                         <span class=\"fa fa-mail-reply\"></span>"
//                + "                          Cancelar</a>"
//                + "                     <button class=\"btn btn-success\" type=\"submit\" id=\"btnSaveEdit\">"
//                + "                         <span class=\"fa fa-save\"></span>"
//                + "                          Guardar</button>"
//                + "             </div>"//End panel
//                + "         </form>"
//                + "     </div>" // End col-lg-4
//                + "</div>"); // End row
//
//        out.print(getScriptValidator("btnSaveEdit", "formEditPost"));

    }

    /**
     * Do edit thread.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/swbadmin/jsp/forum/swbForoEdit.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("isCaptcha", isCaptcha());
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (ServletException e) {
            log.error("Error on doEditThread, " + path + ", " + e.getMessage());
        }




//        PrintWriter out = response.getWriter();
//        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
//        Thread thread = (Thread) semObject.createGenericInstance();
//        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
//        if (paramRequest.getUser() != null) {
//            lang = paramRequest.getUser().getLanguage();
//        }
//        mgr.clearProperties();
//        mgr.addProperty(Thread.swb_title);
//        mgr.addProperty(Thread.frm_thBody);
////        Iterator<SemanticProperty> itSemProps = semObject.getSemanticClass().listProperties();
////        while (itSemProps.hasNext()) {
////            SemanticProperty semProp = itSemProps.next();
////            if (semProp != null) {
////                if (semProp != Thread.swb_title && semProp != Thread.frm_thBody //&& semProp != Thread.frm_hasThAttachments
////                        && semProp != Thread.swb_created && semProp != Thread.swb_creator && semProp != Thread.swb_updated
////                        && semProp != Thread.swb_modifiedBy) {
////                    mgr.hideProperty(semProp);
////                }
////            }
////        }
//
//
////        mgr.setLang(lang);
////        mgr.setSubmitByAjax(false);
////        mgr.setType(mgr.TYPE_XHTML);
//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setParameter("threadUri", semObject.getURI());
//        url.setAction("editThread");
////        mgr.setAction(url.toString());
//
//        /* Para mostrar attaches
//         Resource base = paramRequest.getResourceBase();
//         WebSite website = paramRequest.getWebPage().getWebSite();
//         Thread thread= Thread.getThread(semObject.getId(), website);
//         String basepath = "";
//         int count = 0;
//         GenericIterator<Attachment> lAttchments = thread.listAttachments();
//         url.setAction("removeAttach");
//         while (lAttchments.hasNext()) {
//         count++;
//         Attachment attch = lAttchments.next();
//         basepath = SWBPortal.getWebWorkPath() + "/models/" + website.getId() + "/Resource/" + base.getId() + "/threads/" + thread.getId() + "/" + attch.getFileName();
//         request.setAttribute("attach_hasThAttachments_" + count, basepath);
//         request.setAttribute("attachTarget_hasThAttachments_" + count, "blank");
//         url.setParameter("removeAttach", attch.getURI());
//         request.setAttribute("attachRemovePath_hasThAttachments_" + count, url.toString());
//         }
//         if (count > 0) {
//         request.setAttribute("attachCount_hasThAttachments", "" + count);
//         }
//         * */
////        mgr.addButton(SWBFormButton.newSaveButton());
////        mgr.addButton(SWBFormButton.newBackButton());
////        request.setAttribute("formName", mgr.getFormName());
////        out.println(mgr.renderForm(request));
//        out.print("<div class=\"row\">"
//                + "     <div class=\"col-lg-6 col-lg-offset-3\">"
//                + "         <form class=\"form-horizontal\" method=\"post\" action=\"" + url + "\" id=\"formEdit\">"
//                + "             <div class=\"panel panel-default swbp-panel\">\n"
//                + "                <div class=\"panel-heading swbp-panel-title\">\n"
//                + "                    <div class=\"panel-title\">\n"
//                + "                        <h1 class=\"panel-title\">"
//                + "                             <span class=\"fa fa-pencil-square-o\"></span>"
//                + "                                 " + paramRequest.getLocaleString("edit") + " " + paramRequest.getLocaleString("thread") + " </h1>"
//                + "                     </div>"
//                + "                 </div>"
//                + "                 <div class=\"panel-body\">");
//        out.print(mgr.getFormHiddens());
//        //For field title
//        String title = mgr.renderLabel(request, Thread.swb_title, SWBFormMgr.MODE_EDIT);
//        title = title.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String titleInput = mgr.renderElement(request, Thread.swb_title, SWBFormMgr.MODE_EDIT);
//        titleInput = titleInput.replaceFirst(">", " required class=\"form-control\">");
//        titleInput = titleInput.replace(titleInput.substring(titleInput.indexOf("style"), (titleInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + Thread.swb_title.getName() + "\">");
//        out.print(title);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(titleInput);
//        out.print("                         </div>"
//                + "                     </div>");
//
//        //For field message
//        String message = mgr.renderLabel(request, Thread.frm_thBody, SWBFormMgr.MODE_EDIT);
//        message = message.replaceFirst(">", " class=\"col-lg-2 control-label\">");
//        String messageInput = mgr.renderElement(request, Thread.frm_thBody, SWBFormMgr.MODE_EDIT);
//        messageInput = messageInput.replaceFirst(">", " required class=\"form-control\">");
//        messageInput = messageInput.replace(messageInput.substring(messageInput.indexOf("style"), (messageInput.indexOf("px;\"") + 4)), "");
//        out.print("                     <div class=\"form-group\" id=\"div" + Thread.frm_thBody.getName() + "\">");
//        out.print(message);
//        out.print("                         <div class=\"col-lg-10\">");
//        out.print(messageInput);
//        out.print("                         </div>"
//                + "                     </div>");
//        out.print("                     <ul class=\"list-group\">\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-user fa-fw\"></span><strong>"+ mgr.renderLabel(request, Thread.swb_creator, SWBFormMgr.MODE_VIEW)+ "</strong> " + (thread != null ? (thread.getCreator() != null ? (thread.getCreator() != null ? thread.getCreator().getFullName() : "") : "--") : "") + "</li>\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-calendar fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_created, SWBFormMgr.MODE_VIEW) + ":</strong> " + mgr.renderElement(request, Thread.swb_created, SWBFormMgr.MODE_VIEW) + "</li>\n"
//                + "                     </ul>\n"
//                + "\n"
//                + "                     <ul class=\"list-group\">\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-user fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_modifiedBy, SWBFormMgr.MODE_VIEW) + ":</strong> " + (thread != null ? (thread.getModifiedBy() != null ? (thread.getModifiedBy() != null ? thread.getModifiedBy().getFullName() : "") : "--") : "") + "</li>\n"
//                + "                         <li class=\"list-group-item\"><span class=\"fa fa-calendar fa-fw\"></span><strong>" + mgr.renderLabel(request, Thread.swb_updated, SWBFormMgr.MODE_VIEW) + ":</strong> " + mgr.renderElement(request, Thread.swb_updated, SWBFormMgr.MODE_VIEW) + "</li>\n"
//                + "                     </ul>");
//        SWBResourceURL urlViewPost = paramRequest.getRenderUrl();
//        urlViewPost.setMode(urlViewPost.Mode_VIEW);
//        urlViewPost.setAction("viewPost");
//        urlViewPost.setParameter("threadUri", semObject.getURI());
//        
//        out.print("                 </div>" //End panel-body
//                + "                 <div class=\"panel-footer text-right\">"
//                + "                     <a class=\"btn btn-default\" style=\"color: #fff;\"  href=\"" + urlViewPost + "\">"
//                + "                         <span class=\"fa fa-mail-reply\"></span>"
//                + "                          Cancelar</a>"
//                + "                     <button class=\"btn btn-success\" type=\"submit\" id=\"btnSave\">"
//                + "                         <span class=\"fa fa-save\"></span>"
//                + "                          Guardar</button>"
//                + "             </div>"//End panel
//                + "         </form>"
//                + "     </div>" // End col-lg-4
//                + "</div>"); // End row
//        out.print(getScriptValidator("btnSave", "formEdit"));

    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String flag = "";
        if (isAcceptGuessUsers() || response.getUser().isSigned()) {
            WebPage page = response.getWebPage();
            WebSite website = page.getWebSite();
            User user = response.getUser();
            boolean isAdmin = user.hasRole(getAdminRole());
            Resource base = response.getResourceBase();
            Date date = new Date();
            String action = response.getAction();
            if ((isAdmin || !isOnlyAdminCreateThreads()) && action.equals("addThread")) {
                SWBFormMgr mgr = new SWBFormMgr(Thread.frm_Thread, website.getSemanticObject(), null);
                mgr.clearProperties();
                mgr.addProperty(Thread.swb_title);
                mgr.addProperty(Thread.frm_thBody);
                //mgr.setCaptchaStatus(true);
                if (isCaptcha() && !request.getSession(true).getAttribute("cs").equals(request.getParameter("cmnt_seccode"))) {
                    flag = "lblErrorAddTheme";
                }
                if (flag.equals("")) {
                    try {
                        SemanticObject semObj = mgr.processForm(request);
                        Thread thread = (Thread) semObj.createGenericInstance();
                        //Thread thread = Thread.ClassMgr.getThread(th.getId(), website);
                        thread.setParent(page);
                        if (user != null && user.isSigned()) {
                            thread.setCreator(user);
                        }
                        thread.setForum(this);
                        Role admin = getAdminRole();
                        if (isNotifyThreadCreation() && admin != null) {
                            Iterator<User> it = User.ClassMgr.listUserByRole(admin, admin.getUserRepository());
                            while (it.hasNext()) {
                                User user1 = it.next();
                                String subject = "Se creó el comentario en el foro de título: " + thread.getTitle();
                                String body = "Usuario: " + thread.getCreator().getFullName() + "<br/>\n"
                                        + "Título: " + thread.getTitle() + "<br/>\n"
                                        + "Descripción: " + thread.getBody() + "<br/>\n";
                                SWBUtils.EMAIL.sendBGEmail(user1.getEmail(), subject, body);
                            }
                        }
                        flag = "lblNewTheme";
                        response.setMode(SWBResourceURL.Mode_VIEW);
                        response.setAction("viewPost");
                        response.setRenderParameter("flag", flag);
                        response.setRenderParameter("threadUri", thread.getURI());
                        //processFiles(request, response, thread.getSemanticObject());
                    } catch (FormValidateException e) {
                        //TODO:Validar
                        log.error(e);
                    }
                } else {
                    response.setRenderParameter("flag", flag);
                    response.setMode("addThread");
                }
//                response.setAction("viewThreads");
            } else if (action.equals("replyPost")) {
                SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread thread = Thread.ClassMgr.getThread(soThread.getId(), website);
                if (isCaptcha() && !request.getSession(true).getAttribute("cs").equals(request.getParameter("cmnt_seccode"))) {
                    flag = "lblErrorAddMessage";
                }
                if (flag.equals("")) {
                    if (request.getParameter("pstBody") != null && request.getParameter("pstBody").trim().length() > 0) {
                        SemanticObject soPost = null;
                        Post post = null;
                        if (request.getParameter("postUri") != null) {
                            soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                            post = Post.ClassMgr.getPost(soPost.getId(), website);
                        }
                        SWBFormMgr mgr = new SWBFormMgr(Post.frm_Post, page.getSemanticObject(), null);
                        //mgr.setCaptchaStatus(true);
                        mgr.clearProperties();
                        mgr.addProperty(Post.frm_pstBody);
                        try {
                            SemanticObject semObj = mgr.processForm(request);
                            Post newPost = Post.ClassMgr.getPost(semObj.getId(), website);
                            newPost.setThread(thread);
                            if (post != null) {
                                newPost.setParentPost(post);
                                flag = "lblNewMessage";
                            }
                            thread.setReplyCount(thread.getReplyCount() + 1);
                            thread.setLastPostDate(date);
                            if (user != null && user.isSigned()) {
                                thread.setLastPostMember(user);
                            }
                        } catch (FormValidateException e) {
                            //TODO:Validar
                            log.error(e);
                        }
                    }
                }
                if (request.getParameter("postUri") != null && flag.equals("lblErrorAddMessage")) {
                    response.setMode("replyPost");
                } else {
                    response.setMode(SWBResourceURL.Mode_VIEW);
                    response.setAction("viewPost");
                }

                response.setRenderParameter("postUri", request.getParameter("postUri"));
                response.setRenderParameter("threadUri", thread.getURI());
                response.setRenderParameter("flag", flag);
            } else if (action.equals("editPost")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                if (isCaptcha() && !request.getSession(true).getAttribute("cs").equals(request.getParameter("cmnt_seccode"))) {
                    flag = "lblErrorEditMessage";
                }
                if (flag.equals("")) {
                    try {
                        SemanticObject semObj = mgr.processForm(request);

                        Post newPost = Post.ClassMgr.getPost(semObj.getId(), website);

                        //processFiles(request, response, newPost.getSemanticObject());
                        flag = "lblEditMessage";
                        response.setMode(response.Mode_VIEW);
                        response.setAction("viewPost");
                    } catch (FormValidateException e) {
                        //TODO:Validar
                        log.error(e);
                    }
                } else {
                    response.setMode("editPost");
                    response.setRenderParameter("postUri", semObject.getURI());
                }
                response.setRenderParameter("threadUri", ((Post) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(request.getParameter("suri"))).getThread().getURI());
                response.setRenderParameter("flag", flag);
            } else if ((isAdmin || !isOnlyAdminCreateThreads()) && action.equals("removeThread")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread thread = Thread.ClassMgr.getThread(semObject.getId(), website);
                //int threadReplyCount = thread.getReplyCount();
                //Elimina fileSystem de post asociados
                GenericIterator<Post> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    Post post = itPost.next();
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath() + "/replies/" + post.getId() + "/");
                }
                //Elimina filesystem de thread
                SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath() + "/threads/" + thread.getId() + "/");
                semObject.remove();
                //Redirecciona
                response.setMode(response.Mode_VIEW);
            } else if ((isAdmin || !isOnlyAdminCreateThreads()) && action.equals("removePost")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));

                Post Post2remove = Post.ClassMgr.getPost(semObject.getId(), website);
                int childPost = 0;
                GenericIterator<Post> gitPost = Post2remove.listchildPosts();
                while (gitPost.hasNext()) {
                    childPost++;
                    childPost = getChilds2Remove(gitPost.next(), childPost);
                }
                semObject.remove();

                SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath() + "/replies/" + Post2remove.getId() + "/");

                //Resta el post al contador del thread
                SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread thread = Thread.ClassMgr.getThread(soThread.getId(), website);
                thread.setReplyCount(thread.getReplyCount() - (childPost + 1));
                //Forum forum = thread.getForum();
                //forum.setPostcount(forum.getPostcount() - (childPost + 1));
                //Redirecciona
                response.setRenderParameter("threadUri", thread.getURI());
                response.setMode(response.Mode_VIEW);
                response.setAction("viewPost");
            } else if (action.equals("addFavoriteThread")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread favThread = Thread.ClassMgr.getThread(semObject.getId(), page.getWebSite());
                UserFavThread frmUserThread = UserFavThread.ClassMgr.createUserFavThread(website);
                frmUserThread.setThread(favThread);
                if (user != null && user.isSigned()) {
                    frmUserThread.setUser(user);
                }
                response.setMode(response.Mode_VIEW);
                response.setAction("viewThreads");
            } else if ((isAdmin || !isOnlyAdminCreateThreads()) && action.equals("editThread")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                if (isCaptcha() && !request.getSession(true).getAttribute("cs").equals(request.getParameter("cmnt_seccode"))) {
                    flag = "lblErrorEditheme";
                }
                if (flag.equals("")) {
                    try {
                        SemanticObject semObj = mgr.processForm(request);
                        Thread thread = Thread.ClassMgr.getThread(semObj.getId(), website);

                        response.setMode(response.Mode_VIEW);
                        response.setAction("viewPost");
                        //processFiles(request, response, thread.getSemanticObject());
                    } catch (FormValidateException e) {
                        //TODO:Validar
                        log.error(e);
                    }
                } else {
                    response.setMode("editThread");
                }
                response.setRenderParameter("threadUri", request.getParameter("threadUri"));
                response.setRenderParameter("flag", flag);


            } else if (action.equals("removeAttach")) {
                if (request.getParameter("removeAttach") != null) {
                    String attachType = "threads";
                    if (request.getParameter("postUri") != null) {
                        attachType = "replies";
                    }

                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                    Thread thread = Thread.ClassMgr.getThread(semObject.getId(), website);

                    Post post = null;
                    if (attachType.equals("replies")) {
                        semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                        post = Post.ClassMgr.getPost(semObject.getId(), website);
                    }

                    GenericIterator<Attachment> lAttchments = thread.listAttachments();
                    if (post != null) {
                        lAttchments = post.listAttachmentses();
                    }

                    String attachUri = request.getParameter("removeAttach");
                    while (lAttchments.hasNext()) {
                        Attachment attch = lAttchments.next();
                        if (attch.getURI().equals(attachUri)) {
                            try {
                                File file = new File(SWBPortal.getWorkPath() + base.getWorkPath() + "/" + attachType + "/" + semObject.getId() + "/" + attch.getFileName());
                                file.delete();
                                attch.remove();
                            } catch (Exception e) {
                                log.debug(e);
                            }
                        }
                    }

                    response.setRenderParameter("threadUri", request.getParameter("threadUri"));
                    response.setRenderParameter("postUri", request.getParameter("postUri"));
                }
            } else {
                super.processAction(request, response);
            }
        }
    }

    /**
     * Gets the childs2 remove.
     *
     * @param post the post
     * @param childPost the child post
     * @return the childs2 remove
     */
    private int getChilds2Remove(Post post, int childPost) {
        GenericIterator<Post> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            childPost++;
            childPost = getChilds2Remove(gitPost.next(), childPost);
        }
        return childPost;
    }

    /**
     * Process files.
     *
     * @param request the request
     * @param response the response
     * @param sobj the sobj
     */
    private void processFiles(HttpServletRequest request, SWBActionResponse response, SemanticObject sobj) {
        Date date = new Date();
        Resource base = response.getResourceBase();
        User user = response.getUser();
        WebSite website = response.getWebPage().getWebSite();
        String basepath = null;
        if (sobj.instanceOf(Thread.sclass)) {
            basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/threads/" + sobj.getId() + "/";
        } else if (sobj.instanceOf(Post.sclass)) {
            basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/replies/" + sobj.getId() + "/";
        }
        if (request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED) != null) {
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();
            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    int fileSize = ((Long) item.getSize()).intValue();
                    String value = item.getName();
                    value = value.replace("\\", "/");
                    int pos = value.lastIndexOf("/");
                    if (pos > -1) {
                        value = value.substring(pos + 1);
                    }
                    File fichero = new File(basepath);
                    if (!fichero.exists()) {
                        fichero.mkdirs();
                    }
                    fichero = new File(basepath + value);
                    Attachment frmAttachment = Attachment.ClassMgr.createAttachment(website);
                    if (user != null && user.isSigned()) {
                        frmAttachment.setCreator(user);
                    }
                    frmAttachment.setCreated(date);
                    frmAttachment.setFileName(value);
                    frmAttachment.setFileSize(fileSize);
                    frmAttachment.setMimeType(item.getContentType());

                    if (sobj.instanceOf(Thread.sclass)) {
                        Thread thread = (Thread) sobj.createGenericInstance();
                        frmAttachment.setThread(thread);
                    } else if (sobj.instanceOf(Post.sclass)) {
                        Post post = (Post) sobj.createGenericInstance();
                        frmAttachment.setPost(post);
                    }
                    try {
                        item.write(fichero);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug(e);
                    }
                }
            }
            request.getSession().setAttribute(UploadFormElement.FILES_UPLOADED, null);
        }
    }

    public static String getScriptValidator(String idButton, String idForm) {
        String theScript = "<script>\n"
                + "    $('#" + idButton + "').click(function() {\n"
                + "                                var $inputs = $('#" + idForm + " :input');\n"
                + "                                var cont = 0;\n"
                + "                                var form = document.getElementById('" + idForm + "');\n"
                + "                                $inputs.each(function() {\n"
                + "                                    if (this.required) {\n"
                + "                                        var diverror = $('#div' + this.name);\n"
                + "                                        if ($(this).val().length === 0) {\n"
                + "                                            diverror.addClass('has-error');\n"
                + "                                            cont++;\n"
                + "                                        } else {\n"
                + "                                            diverror.removeClass('has-error');\n"
                + "                                        }\n"
                + "                                    }\n"
                + "                                });\n"
                + "                                if (cont === 0) {\n"
                + "                                   form.submit();\n"
                + "                                    return false;\n"
                + "                                }\n"
                + "                                return false;\n"
                + "                            });\n"
                + "</script>";

        return theScript;
    }

    private List<Thread> listThreads(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<Thread> unpaged = new ArrayList<Thread>();
        String lang = "es";
        User user = paramRequest.getUser();
        if (user != null && user.getLanguage() != null) {
            lang = user.getLanguage();
        }
        int page = 1;
        int itemsPerPage = getPagination() > 0 ? getPagination() : 10;
        Iterator<Thread> it = SWBComparator.sortByDisplayName(Thread.ClassMgr.listThreads(paramRequest.getWebPage().getWebSite()), lang);
        Resource base = paramRequest.getResourceBase();
        while (it.hasNext()) {
            Thread dt = it.next();
            SWBForum forum = dt.getForum();
            if (forum.getId().equals(base.getId())) {
                unpaged.add(dt);
            }
        }

        //Realizar paginado de instancias
        int maxPages = 1;
        if (request.getParameter("p") != null && !request.getParameter("p").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("p"));
            if (page < 0) {
                page = 1;
            }
        }

//            if (itemsPerPage < 10) {
//                itemsPerPage = 10;
//            }

        if (unpaged.size() >= itemsPerPage) {
            maxPages = (int) Math.ceil((double) unpaged.size() / itemsPerPage);
        }
        if (page > maxPages) {
            page = maxPages;
        }

        int sIndex = (page - 1) * itemsPerPage;
        if (unpaged.size() > itemsPerPage && sIndex > unpaged.size() - 1) {
            sIndex = unpaged.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= unpaged.size()) {
            eIndex = unpaged.size();
        }

        request.setAttribute("maxPages", maxPages);
        ArrayList<Thread> ret = new ArrayList<Thread>();
        for (int i = sIndex; i < eIndex; i++) {
            Thread dt = unpaged.get(i);
            ret.add(dt);
        }
        return ret;
    }
}

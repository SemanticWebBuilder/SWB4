/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.forum;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.forum.FrmAttachments;
import org.semanticwb.forum.FrmPost;
import org.semanticwb.forum.FrmThread;
import org.semanticwb.forum.FrmUserThread;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
 *
 * @author jorge.jimenez
 */
public class SWBForum1 extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBForum.class);
    private String lang = "es";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("addThread")) {
            doAddThread(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("replyPost")) {
            doReplyPost(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editPost")) {
            doEditPost(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editThread")) {
            doEditThread(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String action = paramRequest.getAction();
        try {
            if (action != null) {
                request.setAttribute("action", action);
            }
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/forum/swbForum1.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/forum/swbForumAdm1.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doAddThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String wsiteUri = paramRequest.getTopic().getWebSiteId();
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(wsiteUri);
        SWBFormMgr mgr = new SWBFormMgr(FrmThread.frm_FrmThread, semObject, null);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addThread");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm(request));
    }

    public void doReplyPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
        SemanticObject soPost = null;
        if (request.getParameter("postUri") != null) {
            soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
        }
        SWBFormMgr mgr = null;
        if (soPost != null) {
            mgr = new SWBFormMgr(FrmPost.frm_FrmPost, soPost, null);
        } else {
            mgr = new SWBFormMgr(FrmPost.frm_FrmPost, soThread, null);
        }
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", request.getParameter("postUri"));

        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setType(mgr.TYPE_XHTML);
        url.setAction("replyPost");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm(request));
    }

    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("postUri-J:" + request.getParameter("postUri"));
        System.out.println("threadUri-J:" + request.getParameter("threadUri"));
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", semObject.getURI());
        url.setAction("editPost");
        mgr.setAction(url.toString());

        Resource base = paramRequest.getResourceBase();
        WebSite website = paramRequest.getTopic().getWebSite();
        FrmPost post = FrmPost.getFrmPost(semObject.getId(), website);
        String basepath = "";
        int count = 0;
        GenericIterator<FrmAttachments> lAttchments = post.listAttachmentss();

        url.setAction("removeAttach");
        while (lAttchments.hasNext()) {
            count++;
            FrmAttachments attch = lAttchments.next();
            basepath = SWBPlatform.getWebWorkPath() + "/models/" + website.getId() + "/Resource/" + base.getId() + "/replies/" + post.getId() + "/" + attch.getFileName();
            request.setAttribute("attach_" + count, basepath);
            request.setAttribute("attachTarget_" + count, "blank");
            url.setParameter("removeAttach", attch.getURI());
            request.setAttribute("attachRemovePath_" + count, url.toString());
        }
        if (count > 0) {
            request.setAttribute("attachCount", "" + count);
        }
        out.println(mgr.renderForm(request));
    }

    public void doEditThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setAction("editThread");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("Entra a processAction");
        WebPage page = response.getTopic();
        WebSite website = page.getWebSite();
        User user = response.getUser();
        Resource base = response.getResourceBase();
        Date date = new Date();
        String action = response.getAction();
        if (action.equals("addThread")) {
            SWBFormMgr mgr = new SWBFormMgr(FrmThread.frm_FrmThread, website.getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);
            FrmThread thread = FrmThread.getFrmThread(semObj.getId(), website);


            thread.setParent(page);
            if (user != null && user.isSigned()) {
                thread.setCreator(user);
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
        } else if (action.equals("replyPost")) {
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread thread = FrmThread.getFrmThread(soThread.getId(), website);

            SemanticObject soPost = null;
            FrmPost post = null;
            if (request.getParameter("postUri") != null) {
                soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                post = FrmPost.getFrmPost(soPost.getId(), website);
            }

            SWBFormMgr mgr = new SWBFormMgr(FrmPost.frm_FrmPost, page.getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);

            FrmPost newPost = FrmPost.getFrmPost(semObj.getId(), website);
            //newPost.setForum(thread.getForum());
            newPost.setThread(thread);
            if (post != null) {
                newPost.setParentPost(post);
            }

            processFiles(request, response, newPost);

            thread.setReplyCount(thread.getReplyCount() + 1);
            thread.setLastpostdate(date);
            if (user != null && user.isSigned()) {
                thread.setLastpostmember(user);
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
            response.setRenderParameter("threadUri", thread.getURI());
        } else if (action.equals("editPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            SemanticObject semObj = mgr.processForm(request);

            FrmPost newPost = FrmPost.getFrmPost(semObj.getId(), website);

            processFiles(request, response, newPost);

            response.setMode(response.Mode_VIEW);
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        } else if (action.equals("removeThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            //FrmThread thread = FrmThread.getFrmThread(semObject.getId(), website);
            //int threadReplyCount = thread.getReplyCount();
            semObject.remove();
            //Redirecciona
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("removePost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));

            FrmPost Post2remove = FrmPost.getFrmPost(semObject.getId(), website);
            int childPost = 0;
            GenericIterator<FrmPost> gitPost = Post2remove.listchildPosts();
            while (gitPost.hasNext()) {
                childPost++;
                childPost = getChilds2Remove(gitPost.next(), childPost);
            }
            semObject.remove();
            //Resta el post al contador del thread
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread thread = FrmThread.getFrmThread(soThread.getId(), website);
            thread.setReplyCount(thread.getReplyCount() - (childPost + 1));
            //FrmForum forum = thread.getForum();
            //forum.setPostcount(forum.getPostcount() - (childPost + 1));
            //Redirecciona
            response.setRenderParameter("threadUri", thread.getURI());
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
        } else if (action.equals("addFavoriteThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread favThread = FrmThread.getFrmThread(semObject.getId(), page.getWebSite());
            FrmUserThread frmUserThread = FrmUserThread.createFrmUserThread(website);
            frmUserThread.setThread(favThread);
            if (user != null && user.isSigned()) {
                frmUserThread.setUser(user);
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
        } else if (action.equals("editThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_VIEW);
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        } else if (action.equals("removeAttach")) {
            System.out.println("Entra removeAttach-1");
            if (request.getParameter("removeAttach") != null) {
                System.out.println("Entra removeAttach-2:" + request.getParameter("removeAttach"));
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                FrmPost post = FrmPost.getFrmPost(semObject.getId(), website);

                String attachUri=request.getParameter("removeAttach");
                System.out.println("attachUri k llega:"+attachUri);

                GenericIterator<FrmAttachments> lAttchments = post.listAttachmentss();
                while (lAttchments.hasNext()) {
                    FrmAttachments attch = lAttchments.next();
                    if(attch.getURI().equals(attachUri)){
                        System.out.println("attch.getId():"+attch.getId());
                        System.out.println("attch.getURI():"+attch.getURI());
                        System.out.println("attch.getFileName():"+attch.getFileName());
                        try{
                            File file = new File(SWBPlatform.getWorkPath() + base.getWorkPath() + "/replies/" + post.getId() + "/" + attch.getFileName());
                            file.delete();
                            attch.remove();
                        }catch(Exception e){
                            log.debug(e);
                        }
                    }
                }

                System.out.println("Entra removeAttach-3");
                response.setRenderParameter("threadUri", request.getParameter("threadUri"));
                response.setRenderParameter("postUri", request.getParameter("postUri"));
            }
        }

    }

    private int getChilds2Remove(FrmPost post, int childPost) {
        GenericIterator<FrmPost> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            childPost++;
            childPost = getChilds2Remove(gitPost.next(), childPost);
        }
        return childPost;
    }

    private void processFiles(HttpServletRequest request, SWBActionResponse response, FrmPost post) {
        Date date = new Date();
        Resource base = response.getResourceBase();
        User user = response.getUser();
        WebSite website = response.getTopic().getWebSite();
        String basepath = SWBPlatform.getWorkPath() + base.getWorkPath() + "/replies/" + post.getId() + "/";
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
                    FrmAttachments frmAttachment = FrmAttachments.createFrmAttachments(website);
                    if (user != null && user.isSigned()) {
                        frmAttachment.setCreator(user);
                    }
                    frmAttachment.setCreated(date);
                    frmAttachment.setFileName(value);
                    frmAttachment.setFileSize(fileSize);
                    frmAttachment.setMimeType(item.getContentType());
                    frmAttachment.setPost(post);
                    try {
                        item.write(fichero);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug(e);
                    }
                }
            }
        }
    }
}

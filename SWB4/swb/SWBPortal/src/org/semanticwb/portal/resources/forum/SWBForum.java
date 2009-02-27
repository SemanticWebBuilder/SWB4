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
import org.semanticwb.forum.FrmCategory;
import org.semanticwb.forum.FrmForum;
import org.semanticwb.forum.FrmPost;
import org.semanticwb.forum.FrmThread;
import org.semanticwb.forum.FrmUserThread;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.resources.Banner;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
 *
 * @author jorge.jimenez
 */
public class SWBForum extends GenericResource {

    private static Logger log = SWBUtils.getLogger(Banner.class);

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("addCategory")) {
            doAddCategory(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editCategory")) {
            doEditCategory(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("addForum")) {
            doAddForum(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editForum")) {
            doEditForum(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("addThread")) {
            doAddThread(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("replyPost")) {
            doReplyPost(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editPost")) {
            doEditPost(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String action = paramRequest.getAction();
        try {
            if (action != null) {
                request.setAttribute("action", action);
            }
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/resources/jsp/forum/swbForum.jsp");
            rd.include(request, response);

            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode(url.Mode_ADMIN);
            out.println("<a href=\"" + url.toString() + "\">administrar foros</a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/resources/jsp/forum/swbForumAdm.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doAddCategory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBFormMgr mgr = new SWBFormMgr(FrmCategory.frm_FrmCategory, paramRequest.getTopic().getSemanticObject(), null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addCategory");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doAddForum(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = paramRequest.getTopic().getSemanticObject();
        String action = paramRequest.getAction();
        if (action.equals("newCatForum")) {
            semObject = SemanticObject.createSemanticObject(request.getParameter("caturi"));
        }
        SWBFormMgr mgr = new SWBFormMgr(FrmForum.frm_FrmForum, semObject, null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addForum");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doEditCategory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("categoryUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("categoryUri", semObject.getURI());
        url.setAction("editCategory");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doEditForum(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("forumUri", semObject.getURI());
        url.setAction("editForum");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doAddThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
        SWBFormMgr mgr = new SWBFormMgr(FrmThread.frm_FrmThread, semObject, null);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addThread");
        url.setParameter("forumUri", request.getParameter("forumUri"));
        mgr.setAction(url.toString());
        String path = SWBPlatform.getContextPath() + "/forum/images/emotion/";

//        out.println("<tr>");
//        out.println("  <td colspan=\"3\" align=\"center\" bgcolor=\"#F5F5F5\" style=\"BORDER-RIGHT: 1px inset; BORDER-TOP: 1px inset; BORDER-LEFT: 1px inset; BORDER-BOTTOM: 1px inset\">");
//        out.println("    <span class=\"portlet-font\">Showing: 12 of 60</span>");
//        out.println("    <table>");
//        out.println("      <tr id=\"showlink\">");
//        out.println("        <td align=\"center\"><a href=\"javascript:showMoreEmotion()\" class=\"messageTextBold\">Show More</a></td>");
//        out.println("      </tr>");
//        out.println("      <tr id=\"hidelink\" style=\"display: none;\">");
//        out.println("        <td align=\"center\"><a href=\"javascript:hideMoreEmotion()\" class=\"messageTextBold\">Hide More</a></td>");
//        out.println("      </tr>");
//        out.println("    </table>");
//        out.println("  </td>");
//        out.println("</tr>");

//        out.println("</table>");

        out.println(mgr.renderForm());
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

        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        url.setAction("replyPost");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang("es");
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", semObject.getURI());
        url.setAction("editPost");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm());
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User user = response.getUser();
        Portlet base=response.getResourceBase();
        WebSite website = response.getTopic().getWebSite();
        String action = response.getAction();
        String scls = request.getParameter("scls");
        if (action.equals("addCategory")) {
            SWBFormMgr mgr = new SWBFormMgr(FrmCategory.frm_FrmCategory, response.getTopic().getSemanticObject(), null);
            mgr.processForm(request);
            response.setMode(response.Mode_ADMIN);
        } else if (action.equals("editCategory")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("categoryUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_ADMIN);
        } else if (action.equals("addForum")) {
            SWBFormMgr mgr = new SWBFormMgr(FrmForum.frm_FrmForum, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);
            response.setMode("editForum");
            response.setRenderParameter("forumUri", semObj.getURI());
        } else if (action.equals("editForum")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_ADMIN);
        } else if (action.equals("addThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            FrmForum forum = FrmForum.getFrmForum(semObject.getId(), website);
            SWBFormMgr mgr = new SWBFormMgr(FrmThread.frm_FrmThread, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);
            FrmThread thread = FrmThread.getFrmThread(semObj.getId(), website);
            thread.setForum(forum);
            thread.setCreator(user);
            forum.setThreadcount(forum.getThreadcount() + 1);
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
            response.setRenderParameter("forumUri", forum.getURI());
        } else if (action.equals("replyPost")) {
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread thread = FrmThread.getFrmThread(soThread.getId(), website);

            SemanticObject soPost = null;
            FrmPost post = null;
            if (request.getParameter("postUri") != null) {
                soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                post = FrmPost.getFrmPost(soPost.getId(), website);
            }

            SWBFormMgr mgr = new SWBFormMgr(FrmPost.frm_FrmPost, response.getTopic().getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);

            FrmPost newPost = FrmPost.getFrmPost(semObj.getId(), website);
            newPost.setForum(thread.getForum());
            newPost.setThread(thread);
            if (post != null) {
                newPost.setParentPost(post);
            }

            //Procesa archivo attachado
            String name=null;
            String value=null;
            String workpath=SWBPlatform.getWorkPath();
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();
            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    value = item.getName();
                    value=value.replace("\\", "/");
                    int pos=value.lastIndexOf("/");
                    if(pos>-1){
                        value=value.substring(pos+1);
                    }
                    File fichero = new  File(SWBPlatform.getWorkPath()+base.getWorkPath()+"/replies/"+newPost.getId()+"/");
                    if(!fichero.exists()){
                        fichero.mkdirs();
                    }
                    fichero=new File(SWBPlatform.getWorkPath()+base.getWorkPath()+"/replies/"+newPost.getId()+"/"+value);
                    try{                        
                        item.write(fichero);
                    }catch(Exception e){
                        e.printStackTrace();
                        log.debug(e);
                    }
                } 
            }

            //Termina de procesar archivo attachado

            thread.setReplyCount(thread.getReplyCount() + 1);
            Date date = new Date();
            thread.setLastpostdate(date);
            thread.setLastpostmember(user);
            FrmForum forum = thread.getForum();
            forum.setPostcount(forum.getPostcount() + 1);
            forum.setLastpostdate(date);
            forum.setLastpostusr(user);
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
            response.setRenderParameter("threadUri", thread.getURI());
        } else if (action.equals("editPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
            response.setMode(response.Mode_VIEW);
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        } else if (action.equals("removeThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread thread = FrmThread.getFrmThread(semObject.getId(), website);
            int threadReplyCount = thread.getReplyCount();
            semObject.remove();
            //Resta los post del thread al foro
            SemanticObject semObjectForum = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            FrmForum forum = FrmForum.getFrmForum(semObjectForum.getId(), website);
            forum.setPostcount(forum.getPostcount() - threadReplyCount);
            forum.setThreadcount(forum.getThreadcount() - 1);
            //Redirecciona
            response.setRenderParameter("forumUri", request.getParameter("forumUri"));
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
            FrmForum forum = thread.getForum();
            forum.setPostcount(forum.getPostcount() - (childPost + 1));
            //Redirecciona
            response.setRenderParameter("threadUri", thread.getURI());
            response.setRenderParameter("forumUri", request.getParameter("forumUri"));
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
        } else if (action.equals("removeForum")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
            semObject.remove();
        } else if (action.equals("removeCategory")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("categoryUri"));
            semObject.remove();
        } else if (action.equals("addFavoriteThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            FrmThread favThread = FrmThread.getFrmThread(semObject.getId(), response.getTopic().getWebSite());
            FrmUserThread frmUserThread = FrmUserThread.createFrmUserThread(website);
            frmUserThread.setThread(favThread);
            frmUserThread.setUser(user);
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
            response.setRenderParameter("forumUri", request.getParameter("forumUri"));
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
}

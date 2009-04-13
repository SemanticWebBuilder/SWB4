package org.semanticwb.portal.resources.sem.forum;


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
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
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

public class SWBForum extends org.semanticwb.portal.resources.sem.forum.base.SWBForumBase 
{

    public SWBForum()
    {
    }

    public SWBForum(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/forum/swbForum.jsp");
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
        PrintWriter out = response.getWriter();
        SWBFormMgr mgr = new SWBFormMgr(Thread.frm_Thread, paramRequest.getTopic().getSemanticObject(), null);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addThread");
        mgr.setAction(url.toString());
        out.println(mgr.renderForm(request));
        System.out.println("entra a doAddThread-2");
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
            mgr = new SWBFormMgr(Post.frm_Post, soPost, null);
        } else {
            mgr = new SWBFormMgr(Post.frm_Post, soThread, null);
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
        Post post = Post.getPost(semObject.getId(), website);
        String basepath = "";
        int count = 0;
        GenericIterator<Attachment> lAttchments = post.listAttachmentss();

        url.setAction("removeAttach");
        while (lAttchments.hasNext()) {
            count++;
            Attachment attch = lAttchments.next();
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
            SWBFormMgr mgr = new SWBFormMgr(Thread.frm_Thread, website.getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);
            Thread th=(Thread)semObj.createGenericInstance();
            Thread thread = Thread.getThread(th.getId(), website);
            System.out.println("thread:"+thread.getURI());
            thread.setParent(page);
            if (user != null && user.isSigned()) {
                thread.setCreator(user);
            }

            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
        } else if (action.equals("replyPost")) {
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(soThread.getId(), website);

            SemanticObject soPost = null;
            Post post = null;
            if (request.getParameter("postUri") != null) {
                soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                post = Post.getPost(soPost.getId(), website);
            }

            SWBFormMgr mgr = new SWBFormMgr(Post.frm_Post, page.getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);

            Post newPost = Post.getPost(semObj.getId(), website);
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

            Post newPost = Post.getPost(semObj.getId(), website);

            processFiles(request, response, newPost);

            response.setMode(response.Mode_VIEW);
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        } else if (action.equals("removeThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(semObject.getId(), website);
            //int threadReplyCount = thread.getReplyCount();
            semObject.remove();
            //Redirecciona
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("removePost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));

            Post Post2remove = Post.getPost(semObject.getId(), website);
            int childPost = 0;
            GenericIterator<Post> gitPost = Post2remove.listchildPosts();
            while (gitPost.hasNext()) {
                childPost++;
                childPost = getChilds2Remove(gitPost.next(), childPost);
            }
            semObject.remove();
            //Resta el post al contador del thread
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(soThread.getId(), website);
            thread.setReplyCount(thread.getReplyCount() - (childPost + 1));
            //Forum forum = thread.getForum();
            //forum.setPostcount(forum.getPostcount() - (childPost + 1));
            //Redirecciona
            response.setRenderParameter("threadUri", thread.getURI());
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
        } else if (action.equals("addFavoriteThread")) {
            System.out.println("threadUri/addFavoriteThread:"+request.getParameter("threadUri"));
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread favThread = Thread.getThread(semObject.getId(), page.getWebSite());
            UserFavThread frmUserThread = UserFavThread.createUserFavThread(website);
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
                Post post = Post.getPost(semObject.getId(), website);

                String attachUri=request.getParameter("removeAttach");
                System.out.println("attachUri k llega:"+attachUri);

                GenericIterator<Attachment> lAttchments = post.listAttachmentss();
                while (lAttchments.hasNext()) {
                    Attachment attch = lAttchments.next();
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

    private int getChilds2Remove(Post post, int childPost) {
        GenericIterator<Post> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            childPost++;
            childPost = getChilds2Remove(gitPost.next(), childPost);
        }
        return childPost;
    }

    private void processFiles(HttpServletRequest request, SWBActionResponse response, Post post) {
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
                    Attachment frmAttachment = Attachment.createAttachment(website);
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

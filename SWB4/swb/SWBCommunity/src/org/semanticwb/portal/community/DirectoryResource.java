package org.semanticwb.portal.community;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.community.utilresources.ImageResizer;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
 * Manage any semantic Object defined, creates a catalog or directory from it, this object is selected in the resource admin
 * @author : Jorge Alberto Jiménez
 * @version 1.0
 */
public class DirectoryResource extends org.semanticwb.portal.community.base.DirectoryResourceBase {

    private static Logger log = SWBUtils.getLogger(ProductResource.class);

    public DirectoryResource() {
    }

    public DirectoryResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getAction().equals("excel")) {
            response.setContentType("application/vnd.ms-excel");

            
        }
        String act = request.getParameter("act");
        if (act == null) {
            act = "view";
        }

        String path = "/swbadmin/jsp/microsite/directory/directoryView.jsp";
        if (act.equals("view") && getListJsp() != null) {
            path = getListJsp();
        } else if (act.equals("add") && getAddJsp() == null) {
            path = "/swbadmin/jsp/microsite/directory/directoryAdd.jsp";
        } else if (act.equals("add") && getAddJsp() != null) {
            path = getAddJsp();
        } else if (act.equals("edit") && getEditJsp() == null) {
            path = "/swbadmin/jsp/microsite/directory/directoryEdit.jsp";
        } else if (act.equals("edit") && getEditJsp() != null) {
            path = getEditJsp();
        } else if (act.equals("detail") && getDetailJsp() == null) {
            path = "/swbadmin/jsp/microsite/directory/directoryDetail.jsp";
        } else if (act.equals("detail") && getDetailJsp() != null) {
            path = getDetailJsp();
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("itDirObjs", listDirectoryObjects());
            request.setAttribute("sobj", getDirectoryClass());
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();
        String action = response.getAction();
        String act = request.getParameter("act");
        User mem = response.getUser();

        try {
            if ("vote".equals(act)) {
                rank(request, response);
            } else if ("abuseReport".equals(act)) {
                abusedStateChange(request, response);
            } else if ("addComment".equals(act)) {
                addComment(request, response, mem);
            } else if ("spamReport".equals(act)) {
                spamStateChange(request, response);
            } else if (action.equals(response.Action_EDIT)) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try {
                    DirectoryObject dirObj = (DirectoryObject) semObject.createGenericInstance();
                    mgr.processForm(request);

                    String dirPhoto = request.getParameter("dirPhotoHidden");
                    if (dirPhoto != null) {
                        dirObj.setPhoto(dirPhoto);

                    }
                    String dirHasExtraPhotoHidden = request.getParameter("dirHasExtraPhotoHidden");
                    if (dirHasExtraPhotoHidden != null) {
                        dirObj.addExtraPhoto(dirHasExtraPhotoHidden);
                        
                    }
                    processFiles(request, dirObj.getSemanticObject(), dirPhoto);
                } catch (FormValidateException e) {
                    log.event(e);
                }
            } else if (action.equals(response.Action_REMOVE)) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                semObject.remove();
                SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath() + "/" + semObject.getId());
            } else if (action.equals(response.Action_ADD)) {
                SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(cls, response.getWebPage().getWebSite().getSemanticObject(), null);
                mgr.setFilterRequired(false);
                try {
                    SemanticObject sobj = mgr.processForm(request);
                    DirectoryObject dirObj = (DirectoryObject) sobj.createGenericInstance();
                    dirObj.setDirectoryResource(this);
                    dirObj.setWebPage(response.getWebPage());
                    processFiles(request, dirObj.getSemanticObject(), null);
                } catch (FormValidateException e) {
                    log.event(e);
                }
            } else if (action.equals("removeAttach")) {
                if (request.getParameter("removeAttach") != null) {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    DirectoryObject dirObj = (DirectoryObject) semObject.createGenericInstance();
                    System.out.println("photo 2 remove:" + request.getParameter("removeAttach"));
                    Iterator<String> itEPhotos = dirObj.listExtraPhotos();
                    while (itEPhotos.hasNext()) {
                        System.out.println("EPhoto:" + itEPhotos.next());
                    }
                    dirObj.removeExtraPhoto(request.getParameter("removeAttach"));
                    File file = new File(SWBPortal.getWorkPath() + base.getWorkPath() + "/" + semObject.getId() + "/" + request.getParameter("removeAttach"));
                    System.out.println("file 2 delete:" + file.getAbsolutePath());
                    file.delete();
                }
            }
            if (action.equals("admin_update")) {
                String editaccess = request.getParameter("editar");
                if (editaccess != null) {
                    base.setAttribute("editRole", editaccess);
                    base.updateAttributesToDB();
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        response.setMode(response.Mode_VIEW);
    }

    private void processFiles(HttpServletRequest request, SemanticObject sobj, String actualPhoto) {
        String basepath = SWBPortal.getWorkPath() + sobj.getWorkPath() + "/";
        if (request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED) != null) {
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();
            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    //int fileSize = ((Long) item.getSize()).intValue();
                    String value = item.getName();
                    if (value != null && value.trim().length() > 0) {
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

                        DirectoryObject dirObj = (DirectoryObject) sobj.createGenericInstance();
                        if (item.getFieldName().equals("dirPhoto")) {
                            dirObj.setPhoto(value);
                            if (actualPhoto != null) {
                                File file = new File(basepath + actualPhoto);
                                file.delete();
                            }
                        } else if (item.getFieldName().equals("dirHasExtraPhoto")) {
                            dirObj.addExtraPhoto(value);
                        }

                        String ext="";
                        pos=-1;
                        pos=value.indexOf(".");
                        if(pos>-1){
                            ext=value.substring(pos+1);
                        }
                        
                        try {
                            item.write(fichero);
                            ImageResizer.resizeCrop(fichero, 180, fichero, ext);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.debug(e);
                        }
                    }
                }
            }
            request.getSession().setAttribute(UploadFormElement.FILES_UPLOADED, null);
        }
    }

    private void rank(HttpServletRequest request, SWBActionResponse response) {

        String suri = request.getParameter("uri");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof DirectoryObject) {
            DirectoryObject mse = (DirectoryObject) so.getGenericInstance();
            int vote = 0;

            try {
                vote = Integer.parseInt(request.getParameter("value"));
            } catch (Exception ne) {
                log.error(ne);
            }

            double rank = mse.getRank();
            long rev = mse.getReviews();
            response.setRenderParameter("uri", suri);

            rank = rank * rev;
            rev++;
            rank = rank + vote;
            rank = rank / rev;

            //System.out.println("rank a almacenar:" + rank);
            mse.setRank(rank);
            mse.setReviews(rev);
        }
        response.setMode("returnRank");
    }

    private void abusedStateChange(HttpServletRequest request,
            SWBActionResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        SemanticObject so = null;

        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof DirectoryObject) {
            DirectoryObject mse = (DirectoryObject) so.getGenericInstance();
            if (mse.isAbused()) {
                mse.setAbused(false);
            } else {
                mse.setAbused(true);
            }
            message = Boolean.toString(mse.isAbused());
        }
        response.setMode("returnStateMessage");
        response.setRenderParameter("message",
                message != null ? message : "Not OK");
    }

    private void addComment(HttpServletRequest request,
            SWBActionResponse response, User mem) {

        String suri = request.getParameter("uri");
        String desc = request.getParameter("comentario");

        GenericObject gen = SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        if (desc == null) {
            desc = "";
        }
        if (gen != null && gen instanceof DirectoryObject) {
            DirectoryObject mse = (DirectoryObject) gen;
            if (mse.canComment(mem) && desc.length() > 0) {
                Comment comment = Comment.ClassMgr.createComment(response.getWebPage().getWebSite());
                comment.setDescription(desc);
                mse.addComment(comment);
            }
        }
        response.setRenderParameter("uri", suri);
        response.setRenderParameter("act", "detail");
        response.setMode(SWBParamRequest.Mode_VIEW);
    }

    private void spamStateChange(HttpServletRequest request,
            SWBActionResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        String commentId = request.getParameter("commentId");
        SemanticObject so = null;
        //System.out.println("suri:" + suri + ", id:" + commentId );
        if (commentId == null) {
            return;
        }
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof DirectoryObject) {
            DirectoryObject mse = (DirectoryObject) so.getGenericInstance();
            GenericIterator<Comment> iterator = mse.listComments();
            while (iterator.hasNext()) {
                Comment comment = iterator.next();
                //System.out.println("comment.Id:" + comment.getId() + ", comparacion:" + comment.getId().equals(commentId));
                if (comment.getId().equals(commentId)) {
                    if (comment.isSpam()) {
                        comment.setSpam(false);
                    } else {
                        comment.setSpam(true);
                    }
                    message = Boolean.toString(comment.isSpam());
                    //System.out.println("message:" + message);
                    break;
                }
            }
        }
        response.setMode("returnStateMessage");
        response.setRenderParameter("message",
                message != null || "".equals(message)
                ? message : "Not OK");
    }

    private void returnRank(HttpServletRequest request,
            HttpServletResponse response) {

        String message = null;
        String suri = request.getParameter("uri");
        SemanticObject so = null;
        if (null != suri) {
            so = SemanticObject.createSemanticObject(suri);
        }
        if (so.getGenericInstance() instanceof DirectoryObject) {
            DirectoryObject mse = (DirectoryObject) so.getGenericInstance();
            message = mse.getRank() + "|" + mse.getReviews();
        }
        try {
            //System.out.println("message:"+message);
            response.getWriter().print(message != null ? message : "Not OK");
        } catch (IOException ioe) {
        }
    }

    private void returnStateMessage(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest) {

        String message = request.getParameter("message");
        //System.out.println("message en returnStateMessage:" + message);
        try {
            response.getWriter().print(message != null ? message : "Not OK");
        } catch (IOException ioe) {
            log.error(ioe);
        }
    }
}

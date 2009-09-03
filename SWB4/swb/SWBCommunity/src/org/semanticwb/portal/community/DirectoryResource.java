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
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Resource;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.servlet.internal.UploadFormElement;

public class DirectoryResource extends org.semanticwb.portal.community.base.DirectoryResourceBase 
{

    private static Logger log = SWBUtils.getLogger(ProductResource.class);

    public DirectoryResource()
    {
    }

    public DirectoryResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getAction().equals("excel")) response.setContentType("application/vnd.ms-excel");
        
        String act = request.getParameter("act");
        if (act == null) {
            act = "view";
        }

        String path = "/swbadmin/jsp/microsite/directory/directoryView.jsp";
        if (act.equals("add") && getAddJsp()==null) {
            path = "/swbadmin/jsp/microsite/directory/directoryAdd.jsp";
        }else if (act.equals("add") && getAddJsp()!=null) {
            path = getAddJsp();
        }else if (act.equals("edit") && getEditJsp()==null) {
            path = "/swbadmin/jsp/microsite/directory/directoryEdit.jsp";
        }else if (act.equals("edit") && getEditJsp()!=null) {
            path=getEditJsp();
        }else if (act.equals("detail") && getDetailJsp()==null) {
            path = "/swbadmin/jsp/microsite/directory/directoryDetail.jsp";
        }else if (act.equals("detail") && getDetailJsp()!=null)
        {
            path=getDetailJsp();
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
        String action=response.getAction();
        if(action.equals(response.Action_EDIT)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            try
            {
                DirectoryObject dirObj=(DirectoryObject)semObject.createGenericInstance();
                mgr.processForm(request);
                
                String dirPhoto=request.getParameter("dirPhotoHidden");
                if(dirPhoto!=null) dirObj.setPhoto(dirPhoto);
                processFiles(request, response, dirObj.getSemanticObject(), dirPhoto);
            }catch(FormValidateException e)
            {
                log.event(e);
            }
        }else if(action.equals(response.Action_REMOVE)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            semObject.remove();
            SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + response.getResourceBase().getWorkPath() + "/" + semObject.getId());
        }else if(action.equals(response.Action_ADD)){
            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("uri"));
            SWBFormMgr mgr = new SWBFormMgr(cls, response.getWebPage().getWebSite().getSemanticObject(), null);
            try
            {
                SemanticObject sobj=mgr.processForm(request);
                DirectoryObject dirObj=(DirectoryObject)sobj.createGenericInstance();
                dirObj.setDirectoryResource(this);
                dirObj.setWebPage(response.getWebPage());
                processFiles(request, response, dirObj.getSemanticObject(), null);
            }catch(FormValidateException e)
            {
                log.event(e);
            }
        }
        response.setMode(response.Mode_VIEW);
    }


    private void processFiles(HttpServletRequest request, SWBActionResponse response, SemanticObject sobj, String actualPhoto) {
        Resource base = response.getResourceBase();
        String basepath= SWBPlatform.getWorkPath() + base.getWorkPath() + "/" +sobj.getId() + "/";
        if (request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED) != null) {
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();
            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    //int fileSize = ((Long) item.getSize()).intValue();
                    String value = item.getName();
                    if(value!=null && value.trim().length()>0)
                    {
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

                        DirectoryObject dirObj=(DirectoryObject)sobj.createGenericInstance();
                        if(item.getFieldName().equals("dirPhoto")){
                            dirObj.setPhoto(value);
                            if(actualPhoto!=null){
                                File file=new File(basepath+actualPhoto);
                                file.delete();
                            }
                        }else if(item.getFieldName().equals("dirHasExtraPhoto")){
                            dirObj.addExtraPhoto(value);
                        }

                        try {
                            item.write(fichero);
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

}

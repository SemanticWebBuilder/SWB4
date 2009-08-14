package org.semanticwb.portal.community;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.servlet.internal.UploadFormElement;
import org.apache.commons.fileupload.FileItem;

public class ProductResource extends org.semanticwb.portal.community.base.ProductResourceBase {

    private static Logger log = SWBUtils.getLogger(ProductResource.class);

    public ProductResource() {
    }

    public ProductResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String act = request.getParameter("act");
        if (act == null) {
            act = "view";
        }

        String path = "/swbadmin/jsp/microsite/products/productView.jsp";
        if (act.equals("add")) {
            path = "/swbadmin/jsp/microsite/products/productAdd.jsp";
        }
        if (act.equals("edit")) {
            path = "/swbadmin/jsp/microsite/products/productEdit.jsp";
        }
        if (act.equals("detail")) {
            path = "/swbadmin/jsp/microsite/products/productDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage page = response.getWebPage();
        WebSite website = page.getWebSite();
        Resource base=response.getResourceBase();
        Member mem = Member.getMember(response.getUser(), response.getWebPage());
        if (!mem.canView()) {
            return;                                       //si el usuario no pertenece a la red sale;
        }
        String action = request.getParameter("act");
        if ("add".equals(action) && mem.canAdd()) {
            SWBFormMgr mgr = new SWBFormMgr(ProductElement.swbcomm_ProductElement, website.getSemanticObject(), null);
            try {
                SemanticObject semObj = mgr.processForm(request);
                ProductElement th = (ProductElement) semObj.createGenericInstance();
                ProductElement productElement = ProductElement.getProductElement(th.getId(), website);
                productElement.setWebPage(page);
                response.setRenderParameter("act", "edit");
                response.setRenderParameter("uri", productElement.getURI());
            } catch (FormValidateException e) {
                //TODO:Validar
                log.error(e);
                response.setRenderParameter("act", "add");               //regresa a agregar codigo
                response.setRenderParameter("err", "true");              //envia parametro de error
            }
        } else if ("edit".equals(action)) {
            String uri = request.getParameter("uri");
            ProductElement rec = (ProductElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            if (rec != null && rec.canModify(mem)) {
                SemanticObject semObject = SemanticObject.createSemanticObject(uri);
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try {
                    mgr.processForm(request);

                    String smallPhotoHidden=request.getParameter("smallPhotoHidden");
                    if(smallPhotoHidden!=null) rec.setSmallPhoto(smallPhotoHidden);

                    processFiles(request, response, rec.getSemanticObject());
                } catch (FormValidateException e) {
                    log.error(e);
                }
            }
        } else if ("remove".equals(action)) {
            String uri = request.getParameter("uri");
            ProductElement rec = (ProductElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if (rec != null && rec.canModify(mem)) {
                rec.remove();                                       //elimina el registro
                 //Elimina filesystem de thread
                SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + base.getWorkPath() + "/products/" + rec.getId() + "/");
            }
        }
    }


    private void processFiles(HttpServletRequest request, SWBActionResponse response, SemanticObject sobj) {
        Date date = new Date();
        Resource base = response.getResourceBase();
        User user = response.getUser();
        WebPage page=response.getWebPage();
        WebSite website = page.getWebSite();
        String basepath=null;
        if(sobj.instanceOf(ProductElement.sclass)){
            basepath = SWBPlatform.getWorkPath() + base.getWorkPath() + "/products/" + sobj.getId() + "/";
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
                    if(sobj.instanceOf(ProductElement.sclass)){
                        ProductElement productElement=(ProductElement)sobj.createGenericInstance();
                        if(item.getFieldName().equals("smallPhoto")){
                            productElement.setSmallPhoto(value);
                        }else if(item.getFieldName().equals("bigPhoto")){
                            //productElement.setBigPhoto(value);
                        }
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

}

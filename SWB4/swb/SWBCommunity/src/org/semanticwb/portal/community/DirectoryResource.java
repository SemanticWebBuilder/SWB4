package org.semanticwb.portal.community;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

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
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            try
            {
                mgr.processForm(request);
            }catch(FormValidateException e)
            {
                log.event(e);
            }
        }else if(action.equals(response.Action_REMOVE)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
            semObject.remove();
        }else if(action.equals(response.Action_ADD)){
            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("uri"));
            SWBFormMgr mgr = new SWBFormMgr(cls, response.getWebPage().getWebSite().getSemanticObject(), null);
            try
            {
                SemanticObject sobj=mgr.processForm(request);
                DirectoryObject dirObj=(DirectoryObject)sobj.createGenericInstance();
                dirObj.setDirectoryResource(this);
                dirObj.setWebPage(response.getWebPage());
            }catch(FormValidateException e)
            {
                log.event(e);
                //TODO: Validar error
            }
        }
        response.setMode(response.Mode_VIEW);
    }

}

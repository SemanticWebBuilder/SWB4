package org.semanticwb.portal.resources.sem.directory;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class Directory extends org.semanticwb.portal.resources.sem.directory.base.DirectoryBase {

    private static Logger log = SWBUtils.getLogger(Directory.class);

    public Directory() {
    }

    public Directory(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("view2")) {
            doView2(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("add")) {
            doAdd(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getAction().equals("excel")) response.setContentType("application/vnd.ms-excel");
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("sobj", getClassBase());
            request.setAttribute("range", getRange());
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/directory/directory.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doView2(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
         String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(true);
        mgr.setType(mgr.TYPE_XHTML);
        if(paramRequest.getAction().equals(paramRequest.Action_REMOVE))
        {
            mgr.addButton(SWBFormButton.newDeleteButton());
            SWBResourceURL url = paramRequest.getActionUrl();
            url.setParameter("objInstUri", semObject.getURI());
            url.setAction(url.Action_REMOVE);
            mgr.setAction(url.toString());
        }
        mgr.addButton(SWBFormButton.newCancelButton());
        out.println(mgr.renderForm(request));
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(true);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("objInstUri", semObject.getURI());
        url.setAction(url.Action_EDIT);
        mgr.setAction(url.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        out.println(mgr.renderForm(request));
    }

    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite site=paramRequest.getTopic().getWebSite();
        PrintWriter out = response.getWriter();
        SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("objUri"));
        SWBFormMgr mgr = new SWBFormMgr(cls, site.getSemanticObject(), null);
        String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(true);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("objUri", cls.getURI());
        url.setAction(url.Action_ADD);
        mgr.setAction(url.toString());

        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        out.println(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        if(action.equals(response.Action_EDIT)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
        }else if(action.equals(response.Action_REMOVE)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
            semObject.remove();
        }else if(action.equals(response.Action_ADD)){
            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("objUri"));
            SWBFormMgr mgr = new SWBFormMgr(cls, response.getTopic().getWebSite().getSemanticObject(), null);
            mgr.processForm(request);
        }
        response.setMode(response.Mode_VIEW);
    }
   
}

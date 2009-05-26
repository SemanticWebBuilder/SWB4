package org.semanticwb.portal.resources.sem.events;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class Events extends org.semanticwb.portal.resources.sem.events.base.EventsBase 
{

    public Events()
    {
    }

    public Events(org.semanticwb.platform.SemanticObject base)
    {
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getAction().equals("excel")) response.setContentType("application/vnd.ms-excel");
        try {
            request.setAttribute("events", listEvents());
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/resources/Events.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
     public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        SWBFormMgr mgr = new SWBFormMgr(Event.eve_Event, paramRequest.getResourceBase().getSemanticObject(), null);
        String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(true);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(url.Action_ADD);
        mgr.setAction(url.toString());

        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newBackButton());

        out.println(mgr.renderForm(request));
    }

     public void doView2(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
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
            mgr.addButton(SWBFormButton.newDeleteButton().setAttribute("type", "submit"));
            SWBResourceURL url = paramRequest.getActionUrl();
            url.setParameter("EventUri", semObject.getURI());
            url.setAction(url.Action_REMOVE);
            mgr.setAction(url.toString());
        }
        if(request.getParameter("closewindow")!=null){
            mgr.addButton(SWBFormButton.newCloseButton());
        }else{
            mgr.addButton(SWBFormButton.newBackButton());
        }
        out.println(mgr.renderForm(request));
    }

     @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(true);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("EventUri", semObject.getURI());
        url.setAction(url.Action_EDIT);
        mgr.setAction(url.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newBackButton());
        out.println(mgr.renderForm(request));
    }


     @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        if(action.equals(response.Action_EDIT)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            mgr.processForm(request);
        }else if(action.equals(response.Action_REMOVE)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
            semObject.remove();
        }else if(action.equals(response.Action_ADD)){
            SWBFormMgr mgr = new SWBFormMgr(Event.eve_Event, response.getResourceBase().getSemanticObject(), null);
            SemanticObject semObj = mgr.processForm(request);
            Event newEvent = Event.getEvent(semObj.getId(), response.getTopic().getWebSite());
            newEvent.setEventResource(this);
        }
        response.setMode(response.Mode_VIEW);
    }

}

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
package org.semanticwb.portal.resources.sem.directory;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * Object that manage any semanticObject, creates a catalog or directory of the object.
 * 
 * @autor:Jorge Jiménez
 */
public class Directory extends org.semanticwb.portal.resources.sem.directory.base.DirectoryBase {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Directory.class);

    /**
     * Instantiates a new directory.
     */
    public Directory() {
    }

    /**
     * Instantiates a new directory.
     * 
     * @param base the base
     */
    public Directory(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getAction().equals("excel")) response.setContentType("application/vnd.ms-excel");
        try {
            request.setAttribute("scope", getScope());
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("sobj", getClassBase());
            request.setAttribute("props2display", getProperties2Display());
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/directory/directory.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            log.debug(e);
        }
    }

    /**
     * Do view2.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doView2(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
         String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(false);
        mgr.setType(mgr.TYPE_DOJO);
        if(paramRequest.getAction().equals(paramRequest.Action_REMOVE))
        {
            mgr.addButton(SWBFormButton.newDeleteButton().setAttribute("type", "submit"));
            SWBResourceURL url = paramRequest.getActionUrl();
            url.setParameter("objInstUri", semObject.getURI());
            url.setAction(url.Action_REMOVE);
            mgr.setAction(url.toString());
        }
        mgr.addButton(SWBFormButton.newBackButton());
        out.println(mgr.renderForm(request));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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
        mgr.setSubmitByAjax(false);
        mgr.setType(mgr.TYPE_DOJO);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("objInstUri", semObject.getURI());
        url.setAction(url.Action_EDIT);
        mgr.setAction(url.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newBackButton());
        out.println(mgr.renderForm(request));
    }

    /**
     * Do add.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite site=paramRequest.getWebPage().getWebSite();
        PrintWriter out = response.getWriter();
        SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("objUri"));
        SWBFormMgr mgr = new SWBFormMgr(cls, site.getSemanticObject(), null);
        String lang="";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(false);
        mgr.setType(mgr.TYPE_DOJO);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("objUri", cls.getURI());
        url.setAction(url.Action_ADD);
        mgr.setAction(url.toString());

        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newBackButton());
        out.println(mgr.renderForm(request));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
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
                //TODO: Validar error
            }
        }else if(action.equals(response.Action_REMOVE)){
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("objInstUri"));
            semObject.remove();
        }else if(action.equals(response.Action_ADD)){
            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("objUri"));
            SWBFormMgr mgr = new SWBFormMgr(cls, response.getWebPage().getWebSite().getSemanticObject(), null);
            try
            {
                mgr.processForm(request);
            }catch(FormValidateException e)
            {
                log.event(e);
                //TODO: Validar error
            }
        }
        response.setMode(response.Mode_VIEW);
    }
   
}

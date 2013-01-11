/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Stream;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author martha.jimenez
 */
public class StreamsResource extends GenericSocialResource{

    Logger log = SWBUtils.getLogger(StreamsResource.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       String action=(String)request.getAttribute("action");
  //     String objUri=(String)request.getAttribute("objUri");
       WebSite wsite=(WebSite)request.getAttribute("wsite");
       User user=paramRequest.getUser();
       PrintWriter out = response.getWriter();
       if(action == null){
           action = (String)request.getParameter("action");
       }
       if(wsite == null){
           String ws = request.getParameter("wsite");
           wsite = WebSite.ClassMgr.getWebSite(ws);
       }
       if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_ADD)) {
           SWBFormMgr mgr = new SWBFormMgr(Stream.sclass, wsite.getSemanticObject(), null);
           SWBResourceURL url = paramRequest.getActionUrl();
           //SWBResourceURL url = paramRequest.getRenderUrl().setParameter("cr", "create");

           mgr.setFilterRequired(false);
           String lang = "es";
           if(user != null) {
               lang = user.getLanguage();
           }
           mgr.setLang(lang);
           mgr.setSubmitByAjax(false);
           url.setParameter("wsite",wsite.getId());
           mgr.setAction(url.toString());
           SWBFormButton buttonAdd = SWBFormButton.newSaveButton();
           mgr.addButton(buttonAdd);
           out.println(mgr.renderForm(request));
       } else if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_EDIT)){
           doEdit(request, response,paramRequest);
       }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        User user = paramRequest.getUser();
        String uri = request.getParameter("objUri");
        if(uri != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(uri);

           SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
           //SWBResourceURL url = paramRequest.getActionUrl();
           SWBResourceURL url = paramRequest.getRenderUrl().setParameter("cr", "create");
           mgr.setFilterRequired(false);
           mgr.clearProperties();
           mgr.addProperty(Stream.swb_title);
           mgr.addProperty(Stream.swb_description);
           mgr.addProperty(Stream.swb_active);
           mgr.addProperty(Stream.social_stream_phrase);
           mgr.addProperty(Stream.social_hasStream_socialNetwork);
           mgr.addProperty(Stream.social_stream_PoolTime);
           String lang = "es";
           if(user != null) {
               lang = user.getLanguage();
           }
           mgr.setLang(lang);
           mgr.setSubmitByAjax(false);
           url.setParameter("wsite",wsite.getId());
           mgr.setAction(url.toString());
           SWBFormButton buttonAdd = SWBFormButton.newSaveButton();
           mgr.addButton(buttonAdd);
           out.println(mgr.renderForm(request));
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        String action=(String)request.getParameter("action");
        String objUri=(String)request.getParameter("sref");
//        String objUri2=(String)request.getParameter("scls");
        String wsite=(String)request.getParameter("wsite");
        if(wsite != null) {
            WebSite ws = WebSite.ClassMgr.getWebSite(wsite);
            SWBFormMgr mgr = new SWBFormMgr(Stream.sclass, ws.getSemanticObject(), null);
            mgr.clearProperties();
            mgr.addProperty(Stream.swb_active);
            mgr.addProperty(Stream.swb_title);
            mgr.addProperty(Stream.swb_description);
            mgr.addProperty(Stream.social_stream_phrase);
            mgr.addProperty(Stream.social_stream_PoolTime);
            mgr.addProperty(Stream.social_hasStream_socialNetwork);
            try {
                SemanticObject semObj  = mgr.processForm(request);
                SWBSocialResourceUtils.Components.updateTreeNodeAction(request, response);
                objUri = semObj.getURI();

            } catch(FormValidateException ex) {
                log.error("Error in: " + ex);
            }
        }

        response.setRenderParameter("action", "edit");
        response.setRenderParameter("objUri", objUri);
        response.setRenderParameter("wsite", wsite);
        //response.setRenderParameter("treeItem", treeItem);
        response.setMode(SWBResourceURL.Mode_EDIT);
    }


}

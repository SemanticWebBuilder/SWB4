/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import org.semanticwb.social.components.tree.ElementTreeNode;
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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Stream;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author martha.jimenez
 */
public class StreamsResourceEdit extends GenericSocialResource{
    Logger log = SWBUtils.getLogger(StreamsResource.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        User user = paramRequest.getUser();
        String uri = request.getParameter("objUri");
        String smode = request.getParameter("smode");
        if(uri != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(uri);
            SWBSocialResourceUtils.Components.updateTreeNode(request, paramRequest);
           SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
           SWBResourceURL url = paramRequest.getRenderUrl().setParameter("itemUri",request.getAttribute("treeItem")+"");
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

        if("edit".equals(smode)) {
            String objUri=(String)request.getParameter("suri");

            if(wsite != null) {
                SemanticObject semObj = SemanticObject.createSemanticObject(objUri);

                SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
                mgr.clearProperties();
                mgr.addProperty(Stream.swb_active);
                mgr.addProperty(Stream.swb_title);
                mgr.addProperty(Stream.swb_description);
                mgr.addProperty(Stream.social_stream_phrase);
                mgr.addProperty(Stream.social_stream_PoolTime);
                mgr.addProperty(Stream.social_hasStream_socialNetwork);
                try {
                    mgr.processForm(request);
                    SWBSocialResourceUtils.Components.updateTreeNode(request, paramRequest);

                } catch(FormValidateException ex) {
                    log.error("Error in: " + ex);
                }
            }
        }
    }


}

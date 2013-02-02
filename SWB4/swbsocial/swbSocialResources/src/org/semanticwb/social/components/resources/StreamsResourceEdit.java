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
 * Esta clase tiene la finalidad de poder editar un Stream, extiende de la clase
 * GenericSocialResource y no maneja modos u acciones de un recurso gen&eacute;rico
 * @author martha.jimenez
 *
 */
public class StreamsResourceEdit extends GenericSocialResource{
    Logger log = SWBUtils.getLogger(StreamsResource.class);

    /**
     *
     * Este m&eacute;todo se encarga de mostrar la vista de edición del Stream y una vez que
     * se ha modificado el Stream permite guardar los cambios y volver a mostrar la vista
     * del Stream
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     * 
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        User user = paramRequest.getUser();
        String uri = request.getParameter("objUri");
        if(uri == null) {
            uri = request.getParameter("suri");
        }
        /*Enumeration enum1 = request.getAttributeNames();
        while(enum1.hasMoreElements()){
           Object eleObj = enum1.nextElement();
           System.out.println("ele: " + eleObj + " value: " + request.getParameter(eleObj+""));
        }*/
        String smode = request.getParameter("smode");
        if(uri != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(uri);
            //SWBSocialResourceUtils.Resources.updateTreeNode(request, paramRequest);
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
            url.setParameter("treeItem", request.getParameter("treeItem"));
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
                    //------ to Do
                    //SWBSocialResourceUtils.Resources.updateTreeNode(request, paramRequest);
                    // --------
                } catch(FormValidateException ex) {
                    log.error("Error in: " + ex);
                }
            }
        }
    }
}

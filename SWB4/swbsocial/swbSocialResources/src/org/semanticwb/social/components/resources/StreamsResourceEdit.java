/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.social.components.tree.ElementTreeNode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
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
public class StreamsResourceEdit extends GenericSocialResource {

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
        WebSite wsite = (WebSite) request.getAttribute("wsite");
        User user = paramRequest.getUser();
        String uri = request.getParameter("objUri");
        if (uri == null) {
            uri = request.getParameter("suri");
        }
        String smode = request.getParameter("smode");
        if (uri != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(uri);
            if (semObj != null) {
                SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
                //SWBResourceURL url = paramRequest.getRenderUrl().setParameter("smode", "edit");
                SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_EDIT);
                mgr.setFilterRequired(false);
                String lang = "es";
                if (user != null) {
                    lang = user.getLanguage();
                }
                mgr.setLang(lang);
                mgr.setSubmitByAjax(false);
                url.setParameter("wsite", wsite.getId());
                url.setParameter("itemUri", request.getParameter("itemUri"));
                url.setParameter("action", request.getParameter("action"));
                out.println("<script type=\"text/javascript\">");
                out.println("function validateData() {");
                out.println("if(document.form." + Stream.swb_title.getName() + ".value == '') {");
                out.println("alert('El campo " + Stream.swb_title.getLabel(lang) + " es requerido');");
                out.println("document.form." + Stream.swb_title.getName() + ".focus()");
                out.println("return false;");
                out.println("}");
                out.println("if(document.form." + Stream.social_stream_phrase.getName() + ".value == '') {");
                out.println("alert('El campo " + Stream.social_stream_phrase.getLabel(lang) + " es requerido');");
                out.println("document.form." + Stream.social_stream_phrase.getName() + ".focus()");
                out.println("return false;");
                out.println("}");
                out.println("if(document.form." + Stream.social_hasStream_socialNetwork.getName() + ".value == '') {");
                out.println("alert('Selecciona por lo menos una(s) " + Stream.social_hasStream_socialNetwork.getLabel(lang) + "');");
                out.println("document.form." + Stream.social_hasStream_socialNetwork.getName() + ".focus()");
                out.println("return false;");
                out.println("}");
                out.println("document.form.submit();");
                out.println("}");
                out.println("</script>");
                //out.println("<h3>Editar Stream</h3>");
                out.println("<form id=\"" + Stream.sclass + "\" method=\"post\" action=\"" + url + "\" class=\"swbform\" name=\"form\">");
                out.println(mgr.getFormHiddens());
                //out.println("<input type=\"hidden\" name=\"treeItem\" value=\"" + request.getAttribute("treeItem")+""  + "\"");
                out.println("<fieldset>");
                out.println("<table>");
                out.println("<tbody>");
                out.println("<tr>");

                out.println("<td align=\"right\">" + mgr.renderLabel(request, Stream.swb_title, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_title, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request, Stream.swb_description, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_description, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request, Stream.swb_active, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_active, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\"><p>" + mgr.renderLabel(request, Stream.social_stream_phrase, SWBFormMgr.MODE_EDIT) + "<span>(Separa las frases por '|')</span></p></td>");
                out.println("<td>" + mgr.renderElement(request, Stream.social_stream_phrase, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request, Stream.social_hasStream_socialNetwork, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.social_hasStream_socialNetwork, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request, Stream.social_stream_PoolTime, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.social_stream_PoolTime, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"center\" colspan=\"2\">");
                out.println("<button type=\"button\" class=\"bnt1\" onclick=\"validateData()\">Guardar</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</form>");
            }
        }

        if ("edit".equals(smode)) {
            /*String objUri=(String)request.getParameter("suri");
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
            ElementTreeNode treeItem = null;
            Object  treeItemObj = (Object) request.getAttribute("treeItem");
            String action = (String) request.getAttribute("action");
            if(treeItemObj instanceof ElementTreeNode) {
            treeItem = (ElementTreeNode)treeItemObj;
            }
            try {
            SemanticObject semObj1 = mgr.processForm(request);
            Stream str = (Stream)semObj1.createGenericInstance();
            SWBSocialResourceUtils.Resources.refreshNodeTitle(request, treeItem, str.getTitle());
            SWBSocialResourceUtils.Resources.setStatusMessage(request, "Nodo actualizado");
            } catch(FormValidateException ex) {
            log.error("Error in: " + ex);
            }
            }*/
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //super.processAction(request, response);
        String action = response.getAction();
        String ws = request.getParameter("wsite");
        if (ws != null) {

            WebSite wsite = WebSite.ClassMgr.getWebSite(ws);
            if (SWBResourceURL.Action_EDIT.equals(action)) {
                String objUri = (String) request.getParameter("suri");
                if (wsite != null) {
                    SemanticObject semObj = SemanticObject.createSemanticObject(objUri);
                    SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
                    mgr.clearProperties();
                    mgr.addProperty(Stream.swb_active);
                    mgr.addProperty(Stream.swb_title);
                    mgr.addProperty(Stream.swb_description);
                    mgr.addProperty(Stream.social_stream_phrase);
                    mgr.addProperty(Stream.social_stream_PoolTime);
                    mgr.addProperty(Stream.social_hasStream_socialNetwork);
                    //ElementTreeNode treeItem = null;
                    //System.out.println("itemUri: " + request.getParameter("itemUri"));
                    //System.out.println("wsite: " + request.getParameter("wsite"));
                    ElementTreeNode treeItem = SWBSocialResourceUtils.Resources.getComponentbyUri(request);
                    //System.out.println("item: " + treeItem.getData().getName());
                    //Object  treeItemObj = (Object) request.getAttribute("treeItem");
                    //String action1 = (String) request.getParameter("action");
                    if (treeItem != null && action != null) {
                        try {
                            SemanticObject semObj1 = mgr.processForm(request);
                            Stream str = (Stream) semObj1.createGenericInstance();
                            SWBSocialResourceUtils.Resources.refreshNodeName(request, treeItem);
                            SWBSocialResourceUtils.Resources.setStatusMessage(request, "Nodo actualizado");
                        } catch (FormValidateException ex) {
                            log.error("Error in: " + ex);
                        }
                    }

                }

            }
        }
        response.setRenderParameter("objUri", request.getParameter("suri"));
        response.setRenderParameter("action", request.getParameter("action"));
        response.setRenderParameter("wsite", request.getParameter("wsite"));
        response.setRenderParameter("itemUri", request.getParameter("itemUri"));
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
}

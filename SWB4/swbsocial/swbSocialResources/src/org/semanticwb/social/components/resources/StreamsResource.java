/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.semanticwb.social.components.tree.ElementTreeNode;

/**
 * Esta clase se encarga de crear nuevos Streams. Utiliza los modos y acciones de un
 * recurso de SWB.
 * @author martha.jimenez
 */
public class StreamsResource extends GenericSocialResource{

    Logger log = SWBUtils.getLogger(StreamsResource.class);

    /**
     *
     * Este m&eacute;todo muestra en primera instancia la vista con los datos necesarios para
     * poder crear un Stream. Utiliza un FormManager para renderear la vista. En el caso
     * de que ya se haya guardado el Stream muestra la vista de edición del Stream creado
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     *
     */

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       String action=(String)request.getAttribute("action");
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
       Iterator itSocial = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
       if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_ADD) && itSocial.hasNext()) {
           SWBFormMgr mgr = new SWBFormMgr(Stream.sclass, wsite.getSemanticObject(), null);
           SWBResourceURL url = paramRequest.getActionUrl();

           mgr.setFilterRequired(false);
           String lang = "es";
           if(user != null) {
               lang = user.getLanguage();
           }
           mgr.setLang(lang);
           mgr.setSubmitByAjax(false);
           url.setParameter("wsite",wsite.getId());
           url.setParameter("treeItem", request.getParameter("treeItem"));
           request.setAttribute("action", SWBSocialResourceUtils.ACTION_ADD);

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
           out.println("alert('Stream creado');");
           out.println("}");
           out.println("</script>");
           out.println("<form id=\"" + Stream.sclass + "\" method=\"post\" action=\"" + url + "\" class=\"swbform\" name=\"form\">");
           out.println(mgr.getFormHiddens());
           
           out.println("<fieldset>");
           out.println("<table>");
           out.println("<tbody>");
           out.println("<tr>");
           out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.swb_title,SWBFormMgr.MODE_EDIT)+"</td>");
           out.println("<td>" + mgr.renderElement(request, Stream.swb_title, SWBFormMgr.MODE_EDIT) + "</td>");
           out.println("</tr>");
           out.println("<tr>");
           out.println("<td align=\"right\">" +mgr.renderLabel(request,Stream.swb_description,SWBFormMgr.MODE_EDIT)+"</td>");
           out.println("<td>" + mgr.renderElement(request, Stream.swb_description, SWBFormMgr.MODE_EDIT)+"</td>");
           out.println("</tr>");
           out.println("<tr>");
           out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.swb_active,SWBFormMgr.MODE_EDIT) + "</td>");
           out.println("<td>" + mgr.renderElement(request, Stream.swb_active, SWBFormMgr.MODE_EDIT) + "</td>");
           out.println("</tr>");
           out.println("<tr>");
           out.println("<td align=\"right\"><p>" + mgr.renderLabel(request,Stream.social_stream_phrase,SWBFormMgr.MODE_EDIT)+"<span>(Separa las frases por '|')</span></p></td>");
           out.println("<td>" + mgr.renderElement(request, Stream.social_stream_phrase, SWBFormMgr.MODE_EDIT)+ "</td>");
           out.println("</tr>");
           out.println("<tr>");
           out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.social_hasStream_socialNetwork,SWBFormMgr.MODE_EDIT) + "</td>");
           out.println("<td>" + mgr.renderElement(request, Stream.social_hasStream_socialNetwork, SWBFormMgr.MODE_EDIT)+"</td>");
           out.println("</tr>");
           out.println("<tr>");
           out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.social_stream_PoolTime,SWBFormMgr.MODE_EDIT)+"</td>");
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
       } else if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_EDIT) && itSocial.hasNext()){
           doEdit(request, response,paramRequest);
       } else if(!itSocial.hasNext()) {
            out.println("<p>No existen redes sociales para asociar. Para agregar una nueva red social, selecciona 'Redes Sociales en el árbol de navegación'</p>");
       }
    }

    /**
     *
     * Este m&eacute;todo se encarga de mostrar la vista del Stream que se cre&oacute; y permite
     * modificar sus propiedades.
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     *
     */

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        User user = paramRequest.getUser();
        String uri = request.getParameter("sobjUri");
        //SWBSocialResourceUtils.Resources.updateTreeNode(request, paramRequest);
        if(uri != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(uri);
            if(semObj != null) {
               SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
               SWBResourceURL url = paramRequest.getActionUrl().setParameter("action_", "edicion");
               mgr.setFilterRequired(false);
               String lang = "es";
               if(user != null) {
                   lang = user.getLanguage();
               }
               mgr.setLang(lang);
               mgr.setSubmitByAjax(false);
               url.setParameter("wsite",wsite.getId());
               url.setParameter("treeItem", request.getParameter("treeItem"));
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
                out.println("<h3>Editar Stream</h3>");
                out.println("<form id=\"" + Stream.sclass + "\" method=\"post\" action=\"" + url + "\" class=\"swbform\" name=\"form\">");
                out.println(mgr.getFormHiddens());
                out.println("<fieldset>");
                out.println("<table>");
                out.println("<tbody>");
                out.println("<tr>");

                out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.swb_title,SWBFormMgr.MODE_EDIT)+"</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_title, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" +mgr.renderLabel(request,Stream.swb_description,SWBFormMgr.MODE_EDIT)+"</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_description, SWBFormMgr.MODE_EDIT)+"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.swb_active,SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.swb_active, SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\"><p>" + mgr.renderLabel(request,Stream.social_stream_phrase,SWBFormMgr.MODE_EDIT)+"<span>(Separa las frases por '|')</span></p></td>");
                out.println("<td>" + mgr.renderElement(request, Stream.social_stream_phrase, SWBFormMgr.MODE_EDIT)+ "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.social_hasStream_socialNetwork,SWBFormMgr.MODE_EDIT) + "</td>");
                out.println("<td>" + mgr.renderElement(request, Stream.social_hasStream_socialNetwork, SWBFormMgr.MODE_EDIT)+"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">" + mgr.renderLabel(request,Stream.social_stream_PoolTime,SWBFormMgr.MODE_EDIT)+"</td>");
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
    }

    /**
     *
     * Este m&eacute;todo se encarga de procesar las acciones de agregar un nuevo Stream
     * o editar un Stream.
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     *
     */

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String objUri=(String)request.getParameter("sref");
        String wsite=(String)request.getParameter("wsite");
        String action = (String) request.getParameter("action_");
        Object  treeItemObj = (Object) request.getParameter("treeItem");
       // System.out.println("atribute action: " + request.getAttribute("action"));
        /*Enumeration enu2 = request.getAttributeNames();
        while(enu2.hasMoreElements()){
            System.out.println("atr: " + enu2.nextElement());
        }
        Enumeration enu3 = request.getParameterNames();
        while(enu3.hasMoreElements()){
            System.out.println("param: " + enu3.nextElement());
        }*/

        ElementTreeNode treeItem = null;
        if(treeItemObj instanceof ElementTreeNode) {
            treeItem = (ElementTreeNode)treeItemObj;
        }

        SemanticObject semObj = null;
        if("edicion".equals(action)) {
            String objUri1=(String)request.getParameter("suri");
            if(wsite != null) {
                SemanticObject semObj2 = SemanticObject.createSemanticObject(objUri1);
                if(semObj2 != null) {
                    SWBFormMgr mgr = new SWBFormMgr(semObj2, null, SWBFormMgr.MODE_EDIT);
                    mgr.clearProperties();
                    mgr.addProperty(Stream.swb_active);
                    mgr.addProperty(Stream.swb_title);
                    mgr.addProperty(Stream.swb_description);
                    mgr.addProperty(Stream.social_stream_phrase);
                    mgr.addProperty(Stream.social_stream_PoolTime);
                    mgr.addProperty(Stream.social_hasStream_socialNetwork);
                    try {
                       semObj = mgr.processForm(request);
                    } catch(FormValidateException ex) {
                        log.error("Error in: " + ex);
                    }
                }
            }
        } else if(wsite != null) {
            WebSite ws = WebSite.ClassMgr.getWebSite(wsite);
            SWBFormMgr mgr = new SWBFormMgr(Stream.sclass, ws.getSemanticObject(), null);
            mgr.clearProperties();
            mgr.addProperty(Stream.swb_active);
            mgr.addProperty(Stream.swb_title);
            mgr.addProperty(Stream.swb_description);
            mgr.addProperty(Stream.social_stream_phrase);
            mgr.addProperty(Stream.social_stream_PoolTime);
            mgr.addProperty(Stream.social_hasStream_socialNetwork);
            Stream str = null;
            try {
                semObj = mgr.processForm(request);
                if(treeItem != null) {
                    str = (Stream)semObj.createGenericInstance();
                    
                }
            } catch(FormValidateException ex) {
                log.error("Error in: " + ex);
            }
            //SWBParamRequest paramRequest = (SWBParamRequest)response;
            request.setAttribute("action", SWBSocialResourceUtils.ACTION_ADD);
          //  SWBSocialResourceUtils.Resources.insertTreeNodeAction(request, response.getWebPage(), treeItem, str);
          //  SWBSocialResourceUtils.Resources.setStatusMessageAction(request, response.getWebPage(), "Nodo insertado...");
            //SWBSocialResourceUtils.Zkoss.refreshNodeTitle(treeItem, str.getTitle());

        }
        //------ to Do UpdateTreeNode
        
        //-----------
        response.setRenderParameter("action", "edit");
        response.setRenderParameter("treeItem", treeItem+"");
        response.setRenderParameter("objUri", objUri);
        response.setRenderParameter("wsite", wsite);
        String semObjStr = semObj != null ? semObj.getURI() : null;
        response.setRenderParameter("sobjUri", semObjStr);
        response.setMode(SWBResourceURL.Mode_EDIT);
    }
}

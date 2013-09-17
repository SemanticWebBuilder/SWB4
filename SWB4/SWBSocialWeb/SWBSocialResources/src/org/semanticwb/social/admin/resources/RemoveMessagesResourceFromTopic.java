/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialTopic;

/**
 *
 * @author jorge.jimenez
 */
public class RemoveMessagesResourceFromTopic extends GenericResource {

    private static Logger log = SWBUtils.getLogger(RemoveMessagesResourceFromTopic.class);
    
    private static String Action_REMOVEWOTOPIC="removewotopic";
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        if(objUri!= null){
            SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            String wsiteId = socialTopic.getSemanticObject().getModel().getName();
            WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
            //Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, wsite);
            //long noOfMessages = SWBUtils.Collections.sizeOf(itPostIn);
            long SocialTopicPostIns = wsite.getSemanticModel().countStatements(null, PostIn.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostIn.sclass.getClassGroupId());
            out.println("<div class=\"swbform\">");
            //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
            out.println("<form type=\"dijit.form.Form\" id=\"del" + socialTopic.getId() +"\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('del" + socialTopic.getId() + "'); return false;}else{return false;}\">");            
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\">El Tema <b>" + socialTopic.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene en total <b>" + SocialTopicPostIns +  "</b> mensajes de entrada</td>");        
            out.println("   </tr>");
            if(SocialTopicPostIns >0L){
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">¿Eliminar mensajes de entrada?</td>");
                out.println("   </tr>");
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Eliminar</button></td>");
                //out.println("       <td style=\"text-align: center;\"><button onclick=\"delete()\">Eliminar</button></td>");
                //out.println("<button name=\"Delete\" value=\"Delete\" onClick=\"if(confirm('Deseas eliminar los mensajes?')){alert('Enviando'); document.getElementById('del').submit();}else{alert('NO enviando'); return false;}\">Eliminar</button>");
                out.println("   </tr>");
            }
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
            
            //Solo mensajes de salida en el tema
            
            long lSocialTopicPostOut = wsite.getSemanticModel().countStatements(null, PostOut.social_socialTopic.getRDFProperty(), socialTopic.getSemanticObject().getRDFResource(), PostOut.sclass.getClassGroupId());
            //Iterator <PostOut> itPostOutInTopic=PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, wsite);
            //long lSocialTopicPostOut = SWBUtils.Collections.sizeOf(itPostOutInTopic);
            
            out.println("<div class=\"swbform\">");
            //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
            out.println("<form type=\"dijit.form.Form\" id=\"delwotopic" + socialTopic.getId() + "\" action=\"" +  paramRequest.getActionUrl().setAction(Action_REMOVEWOTOPIC).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('delwotopic" + socialTopic.getId() + "'); return false;}else{return false;}\">");            
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\">El Tema <b>" + socialTopic.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene <b>" + lSocialTopicPostOut +  "</b> mensajes de salida</td>");        
            out.println("   </tr>");
            if(lSocialTopicPostOut >0L){
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">¿Eliminar todos los mensajes de salida?</td>");
                out.println("   </tr>");
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Eliminar</button></td>");
                //out.println("       <td style=\"text-align: center;\"><button onclick=\"delete()\">Eliminar</button></td>");
                //out.println("<button name=\"Delete\" value=\"Delete\" onClick=\"if(confirm('Deseas eliminar los mensajes?')){alert('Enviando'); document.getElementById('del').submit();}else{alert('NO enviando'); return false;}\">Eliminar</button>");
                out.println("   </tr>");
            }
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
            
            
            if(request.getParameter("deleted")!= null && request.getParameter("deleted").equals("ok")){
                out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Mensajes eliminados');");            
                out.println("</script>");
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String mode = response.getAction();
        String objUri = request.getParameter("suri");
        if(mode.equals(SWBResourceURL.Action_REMOVE)){
            if(objUri!= null){
                try{
                    SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                    String wsiteId = socialTopic.getSemanticObject().getModel().getName();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
                    Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, wsite);
                    while(itPostIn.hasNext()){
                        PostIn postIn=itPostIn.next();
                        postIn.remove();
                    }
                }catch(Exception e){
                    log.error(e.getMessage());
                }
            }
        }else if(mode.equals(Action_REMOVEWOTOPIC)) //Elimina PostIn que se encuentren en un cierto stream y que no tengan un SocialTopic asociado.
        {
            if(objUri!= null){
                try{
                    SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                    String wsiteId = socialTopic.getSemanticObject().getModel().getName();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
                    Iterator<PostOut> itPostOut = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, wsite);
                    while(itPostOut.hasNext()){
                        PostOut postOut=itPostOut.next();
                        postOut.remove();
                    }
                }catch(Exception e){
                    log.error(e.getMessage());
                }
            }
        }
        response.setRenderParameter("suri", objUri);
        response.setRenderParameter("deleted", "ok");
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
    
}
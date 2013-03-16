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
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;

/**
 *
 * @author francisco.jimenez
 */
public class RemoveMessagesResource extends GenericResource {

    private static Logger log = SWBUtils.getLogger(RemoveMessagesResource.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        if(objUri!= null){
            Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            String wsiteId = stream.getSemanticObject().getModel().getName();
            WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
            Iterator<MessageIn> itMess = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
            long noOfMessages = SWBUtils.Collections.sizeOf(itMess);
            out.println("<div class=\"swbform\">");
            out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\">El Stream <b>" + stream.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene <b>" + noOfMessages +  "</b> mensajes</td>");        
            out.println("   </tr>");
            if(noOfMessages >0L){
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">¿Eliminar todos los mensajes?</td>");
                out.println("   </tr>");
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Eliminar</button></td>");
                out.println("   </tr>");
            }
            out.println("</table>");
            out.println("</div>");
            if(request.getParameter("deleted")!= null && request.getParameter("deleted").equals("ok")){
                out.println("<script type=\"text/javascript\">");
                    log.debug("showStatus");
                    out.println("   showStatus('Mensajes eliminados');");            
                out.println("</script>");
            }
        }else{
            System.out.println("No suitable value for 'suri' in doView()");
        }
        /*
        out.println("Selecciona la red social: <br/>");
        out.println("<select name=\"socialNet\"  data-dojo-type=\"dijit.form.Select\">");
        while(socialNets.hasNext()){
            SocialNetwork socialNet = socialNets.next();            
            out.println("<option value\"" + socialNet.getURI() + "\">" + socialNet.getTitle() + "</option>");
        }
        out.println("</select>");
        */
        /*        
        Iterator<MessageIn> itposts = MessageIn.ClassMgr.listMessageIns(wsite);
        MessageIn.ClassMgr.listMessageInByPostInSocialNetwork(null);
        
        long el = SWBUtils.Collections.sizeOf(itposts);
        */

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String mode = response.getAction();
        String objUri = request.getParameter("suri");
        if(mode.equals(SWBResourceURL.Action_REMOVE)){            
            if(objUri!= null){
                try{
                    Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                    String wsiteId = stream.getSemanticObject().getModel().getName();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
                    Iterator<MessageIn> itMess = MessageIn.ClassMgr.listMessageInByPostInStream(stream, wsite);
                    int i =0 ;
                    while(itMess.hasNext()){
                        itMess.next().remove();
                        if(i%100 == 0){
                            System.out.println("\n\n Eliminados: " + i);
                        }
                        i++;
                    }
                }catch(Exception e){
                    log.error(e.getMessage());
                }
            }else{
                System.out.println("No suitable value for 'suri'");
            }
        }
        response.setRenderParameter("suri", objUri);
        response.setRenderParameter("deleted", "ok");
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
    
}

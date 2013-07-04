/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.semanticwb.social.Stream;

/**
 *
 * @author francisco.jimenez
 */
public class RemoveMessagesResource extends GenericResource {

    private static Logger log = SWBUtils.getLogger(RemoveMessagesResource.class);
    
    private static String Action_REMOVEWOTOPIC="removewotopic";
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        if(objUri!= null){
            Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            String wsiteId = stream.getSemanticObject().getModel().getName();
            WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
            Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
            long noOfMessages = SWBUtils.Collections.sizeOf(itPostIn);
            out.println("<div class=\"swbform\">");
            //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
            out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('del'); return false;}else{return false;}\">");            
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\">El Stream <b>" + stream.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene en total <b>" + noOfMessages +  "</b> mensajes</td>");        
            out.println("   </tr>");
            if(noOfMessages >0L){
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">¿Eliminar todos los mensajes?</td>");
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
            
            //Solo mensajes sin Tema
            ArrayList aList=new ArrayList();
            Iterator <PostIn> itPostInWOTopic=PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
            while(itPostInWOTopic.hasNext())
            {
                PostIn postIn=itPostInWOTopic.next();
                if(postIn.getSocialTopic()==null) aList.add(postIn.getURI());
            }
            
            out.println("<div class=\"swbform\">");
            //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
            out.println("<form type=\"dijit.form.Form\" id=\"delwotopic\" action=\"" +  paramRequest.getActionUrl().setAction(Action_REMOVEWOTOPIC).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('delwotopic'); return false;}else{return false;}\">");            
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\">El Stream <b>" + stream.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene <b>" + aList.size() +  "</b> mensajes sin Tema</td>");        
            out.println("   </tr>");
            if(noOfMessages >0L){
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">¿Eliminar todos los mensajes sin tema?</td>");
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
                    Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                    while(itPostIn.hasNext()){
                        PostIn postIn=itPostIn.next();
                        postIn.remove();
                    }
                }catch(Exception e){
                    log.error(e.getMessage());
                }
            }else{
                System.out.println("No suitable value for 'suri'");
            }
        }else if(mode.equals(Action_REMOVEWOTOPIC)) //Elimina PostIn que se encuentren en un cierto stream y que no tengan un SocialTopic asociado.
        {
            if(objUri!= null){
                try{
                    Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                    String wsiteId = stream.getSemanticObject().getModel().getName();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
                    Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                    while(itPostIn.hasNext()){
                        PostIn postIn=itPostIn.next();
                        if(postIn.getSocialTopic()==null) postIn.remove();
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
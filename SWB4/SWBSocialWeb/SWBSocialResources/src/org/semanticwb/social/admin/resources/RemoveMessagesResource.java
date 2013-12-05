/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.Stream;

/**
 *
 * @author francisco.jimenez
 */
public class RemoveMessagesResource extends GenericResource {

    private static Logger log = SWBUtils.getLogger(RemoveMessagesResource.class);
    
    private static String Action_REMOVEWOTOPIC="removewotopic";
    private static String Action_REMOVESINCEDATE="removesinceDate";
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        if(objUri!= null){
            try
            {
            
                User user=paramRequest.getUser();
                Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                String wsiteId = stream.getSemanticObject().getModel().getName();
                WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
                //Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                //long noOfMessages = SWBUtils.Collections.sizeOf(itPostIn);
                long StreamPostIns = wsite.getSemanticModel().countStatements(null, PostIn.social_postInStream.getRDFProperty(), stream.getSemanticObject().getRDFResource(), null);
                //out.println("<div class=\"swbform\">");
                //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
                /*
                out.println("<form type=\"dijit.form.Form\" id=\"del" + stream.getId() +"\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('del" + stream.getId() + "'); return false;}else{return false;}\">");            
                out.println("<table width=\"100%\" border=\"0px\">");            
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">El Stream <b>" + stream.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene en total <b>" + StreamPostIns +  "</b> mensajes</td>");        
                out.println("   </tr>");
                if(StreamPostIns >0L){
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
                * */
                out.println("<div id=\"strm-eliminar\">");
                out.println("<p>Mensajes del stream: <strong>"+stream.getDisplayTitle(user.getLanguage()) +"</strong></p>");


                if(StreamPostIns >0L){
                    out.println("<div class=\"eliminar-boton streamtot-on\">");
                        out.println("<a onclick=\"if(confirm('Desea eliminar todos los mensajes?'))" + "{ submitUrl('" + paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "',this); } else { return false;}\" title=\"Eliminar "+ StreamPostIns +" mensajes de entrada\">");
                        out.println("<strong>"+StreamPostIns+"</strong> <em>Mensajes de entrada</em>");
                    out.println("</a>");
                    out.println("</div>");
                }else{
                    out.println("<div class=\"eliminar-boton streamtot-off\">");
                    out.println("<span>");
                    out.println("<strong>0</strong> <em>Mensajes de entrada</em>");
                    out.println("</span>");
                    out.println("</div>");
                }

                //Solo mensajes sin Tema
                /*
                ArrayList aList=new ArrayList();
                Iterator <PostIn> itPostInWOTopic=PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                while(itPostInWOTopic.hasNext())
                {
                    PostIn postIn=itPostInWOTopic.next();
                    if(postIn.getSocialTopic()==null) aList.add(postIn.getURI());
                }*/
                ArrayList aList=new ArrayList();
                Iterator <PostIn> itPostInWOTopic=PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                while(itPostInWOTopic.hasNext())
                {
                    PostIn postIn=itPostInWOTopic.next();
                    if(postIn.getSocialTopic()==null) aList.add(postIn.getURI());
                }
                /*
                out.println("<div class=\"swbform\">");
                //out.println("<form type=\"dijit.form.Form\" id=\"del\" action=\"" +  paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"submitForm('del'); return false;\">");            
                out.println("<form type=\"dijit.form.Form\" id=\"delwotopic" + stream.getId() + "\" action=\"" +  paramRequest.getActionUrl().setAction(Action_REMOVEWOTOPIC).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes serán eliminados.')){submitForm('delwotopic" + stream.getId() + "'); return false;}else{return false;}\">");            
                out.println("<table width=\"100%\" border=\"0px\">");            
                out.println("   <tr>");
                out.println("       <td style=\"text-align: center;\">El Stream <b>" + stream.getDisplayTitle(paramRequest.getUser().getLanguage())  + "</b> actualmente contiene <b>" + aList.size() +  "</b> mensajes sin Tema</td>");        
                out.println("   </tr>");
                if(StreamPostIns >0L){
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
                **/

                if(aList.size() >0L){
                    out.println("<div class=\"eliminar-boton streamsin-on\">");
                        out.println("<a onclick=\"if(confirm('Desea eliminar todos los mensajes sin tema?'))" + "{ submitUrl('" + paramRequest.getActionUrl().setAction(Action_REMOVEWOTOPIC).setParameter("suri", objUri) + "',this); } else { return false;}\" title=\"Eliminar "+ StreamPostIns +" mensajes de entrada\">");
                        out.println("<strong>"+aList.size()+"</strong> <em>Mensajes de entrada</em>");
                    out.println("</a>");
                    out.println("</div>");
                }else{
                    out.println("<div class=\"eliminar-boton streamsin-off\">");
                    out.println("<span>");
                    out.println("<strong>0</strong> <em>Mensajes sin tema/em>");
                    out.println("</span>");
                    out.println("</div>");
                }


                out.println("<div class=\"swbform\">");
                out.println("<form type=\"dijit.form.Form\" id=\"delDate" + stream.getId() + "\" action=\"" +  paramRequest.getActionUrl().setAction(Action_REMOVESINCEDATE).setParameter("suri", objUri) + "\" method=\"post\" onsubmit=\"if(confirm('Los mensajes a partir de la facha seleccionada hacia atras serán eliminados.')){submitForm('delDate" + stream.getId() + "'); return false;}else{return false;}\">");            
                out.println("<div class=\"eliminar-date\">");
                out.println("<p>Eliminar mensajes apartir de una fecha hacia atras:</p>");
                out.println("<input type=\"text\" name=\"remSinceDate"+stream.getId()+"\" id=\"remSinceDate"+stream.getId()+"\" dojoType=\"dijit.form.DateTextBox\"  size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Eliminar</button>");
                out.println("</div>");
                out.println("</form>");
                out.println("</div>");


                out.println("</div>");

                if(request.getParameter("deleted")!= null && request.getParameter("deleted").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                        out.println("   showStatus('Mensajes eliminados');");            
                    out.println("</script>");
                }
                
            }catch(Exception e)
            {
                System.out.println(e.getMessage()); 
                log.error(e);
            }
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
        if(objUri!= null)
        {
            try
            {
                Stream stream = (Stream)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
                if(mode.equals(SWBResourceURL.Action_REMOVE))
                {
                    Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                    while(itPostIn.hasNext()){
                        PostIn postIn=itPostIn.next();
                        postIn.remove();
                    }
                }else if(mode.equals(Action_REMOVEWOTOPIC)) //Elimina PostIn que se encuentren en un cierto stream y que no tengan un SocialTopic asociado.
                {
                    Iterator<PostIn> itPostIn = PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                    while(itPostIn.hasNext()){
                        PostIn postIn=itPostIn.next();
                        if(postIn.getSocialTopic()==null) postIn.remove();
                    }
                }else if(mode.equals(Action_REMOVESINCEDATE))   //Elimina mensajes en el stream hacia atras, a partir de una fecha dada 
                {
                    String remSinceDate=request.getParameter("remSinceDate"+stream.getId());
                    //System.out.println("Entra a processAction/RemoveStream2:"+remSinceDate);
                    if(remSinceDate!=null && remSinceDate.trim().length()>0)
                    {
                        //System.out.println("remSinceDate-G2:"+remSinceDate);
                        Date date = null;
                        SimpleDateFormat formatoDelTexto=null;
                        try {
                            formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                            date = formatoDelTexto.parse(remSinceDate);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        //System.out.println("date JRemove:"+date);
                        if(date!=null)
                        {
                            Iterator<PostIn> itPostIns=PostIn.ClassMgr.listPostInByPostInStream(stream, wsite);
                            while(itPostIns.hasNext())
                            {
                                PostIn postIn=itPostIns.next();
                                if(postIn.getPi_created()!=null)
                                {
                                    if(postIn.getPi_created().compareTo(date)<0)
                                    {
                                        //System.out.println("postIn a eliminar:"+postIn.getCreated());
                                        postIn.remove();
                                    }
                                }
                            }
                        }
                    }
                }
            }catch(Exception e)
            {
                log.error(e);
            }
        }
        response.setRenderParameter("suri", objUri);
        response.setRenderParameter("deleted", "ok");
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
    
}
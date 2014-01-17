/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetworkUser;

/**
 *
 * @author jorge.jimenez
 */
public class StreamMap extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(StreamMap.class);
    public static final String Mode_SHOWMARKERDETAILS = "showMarkDet";
    private static String Mode_showMap="showMap";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_SHOWMARKERDETAILS.equals(mode)) {
            doShowDetails(request, response, paramRequest);
        }else if (Mode_showMap.equals(mode)) {
            doShowMap(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out = response.getWriter();
        String suri=request.getParameter("suri");
        SemanticObject semObj=SemanticObject.getSemanticObject(suri);
        String streamMapView="";
        if(request.getParameter("streamMapView")!=null) streamMapView=request.getParameter("streamMapView");
        String date=request.getParameter("mapSinceDate");
        
        out.println("<div class=\"divMap\">");
        out.println("<div class=\"swbform\">");
        out.println("<form type=\"dijit.form.Form\" id=\"showMap_"+semObj.getId()+"\" action=\"" +  paramRequest.getRenderUrl().setParameter("suri", suri) + "\" method=\"post\" onsubmit=\"submitForm('showMap_" + semObj.getId() + "'); return false;\">");            
        /*
        out.println("<table width=\"100%\" border=\"0px\">");            
        out.println("   <tr>");
        out.println("       <td style=\"text-align: center;\">Mostrar mapa con datos desde:</td>");        
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td style=\"text-align: center;\">");
        out.println("      <input type=\"text\" name=\"mapSinceDate\" id=\"mapSinceDate"+semObj.getId()+"\" dojoType=\"dijit.form.DateTextBox\"  value=\""+date+"\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Solo Contadores<input type=\"radio\" id=\"usedas0\" name=\"streamMapView\" label=\"Solo Contadores:\" value=\"1\"");
        if(streamMapView.equals("1")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Solo Mensajes<input type=\"radio\" id=\"usedas1\" name=\"streamMapView\" label=\"Solo Mensajes:\" value=\"2\" ");
        if(streamMapView.equals("2")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Mensajes y Contadores<input type=\"radio\" id=\"usedas2\" name=\"streamMapView\" label=\"Mensajes y Contadores:\" value=\"3\"");
        if(streamMapView.equals("3")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Mostrar solo positivos<input type=\"radio\" id=\"usedas4\" name=\"streamMapView\" label=\"Mostrar solo positivos:\" value=\"5\"");
        if(streamMapView.equals("5")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Mostrar solo negativos<input type=\"radio\" id=\"usedas5\" name=\"streamMapView\" label=\"Mostrar solo negativos:\" value=\"6\"");
        if(streamMapView.equals("6")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td>");
        out.println("      Mostrar todo<input type=\"radio\" id=\"usedas3\" name=\"streamMapView\" label=\"Mostrar todo:\" value=\"4\"");
        if(streamMapView.equals("4")) out.println(" checked=\"true\"");
        out.println(" />");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("    <td style=\"text-align: center;\">");
        out.println("      <button dojoType=\"dijit.form.Button\" type=\"submit\">Mostrar</button>");
        out.println("    </td>");
        out.println("   </tr>");
        out.println("</table>");
        * */
        
       String sCheck=""; 
       out.println("<label>Mostrar ");
       out.println("<select name=\"streamMapView\"> ");
       sCheck="";
       if(streamMapView.equals("4")) sCheck="selected";
       out.println("<option value=\"4\" " +sCheck+">Todo</option>");
       if(streamMapView.equals("1")) sCheck="selected";
       out.println("<option value=\"1\" " +sCheck+">Contadores</option>");
       sCheck="";
       if(streamMapView.equals("2")) sCheck="selected";
       out.println("<option value=\"2\"  " +sCheck+">Mensajes</option>");
       sCheck="";
       if(streamMapView.equals("3")) sCheck="selected";
       out.println("<option value=\"3\"  " +sCheck+">Mensajes y contadores</option>");
       sCheck="";
       if(streamMapView.equals("5")) sCheck="selected";
       out.println("<option value=\"5\"  " +sCheck+">Positivos</option>");
       sCheck="";
       if(streamMapView.equals("6")) sCheck="selected";
       out.println("<option value=\"6\"  " +sCheck+">Negativos</option>");
       out.println("</select>");
       out.println("</label>");
       out.println("<label>Desde el día<input type=\"text\" name=\"mapSinceDate\" id=\"mapSinceDate"+semObj.getId()+"\"  dojoType=\"dijit.form.DateTextBox\" hasDownArrow=\"true\" value=\""+date+"\" class=\"txtfld-calendar\"/></label>");
       out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Mostrar</button>");

        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        if(request.getParameter("mapSinceDate")!=null)
        {
            out.println("<div class=\"swbSocialMapIframe\">");
            out.println("   <iframe width=\"100%\" height=\"100%\" src=\""+paramRequest.getRenderUrl().setMode(Mode_showMap).setParameter("suri", request.getParameter("suri")).setParameter("mapSinceDate"+semObj.getId(), request.getParameter("mapSinceDate")).setParameter("streamMapView", request.getParameter("streamMapView")) +"\"></iframe> ");
            out.println("</div>");
        }
    }
     
     public void doShowMap(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
     {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamMap.jsp";
         RequestDispatcher dis = request.getRequestDispatcher(myPath);
         if (dis != null) {
             try {
                 request.setAttribute("paramRequest", paramRequest);
                 dis.include(request, response);
             } catch (Exception ex) {
                 log.error(ex);
             }
         }
     }
     
     

     
    /*
     * Muestra detalles de un marcador
     */ 
    public void doShowDetails(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       try {
           String postUri=request.getParameter("postUri");
           if(postUri==null) return;
           SemanticObject semObj=SemanticObject.getSemanticObject(postUri);
           PostIn postIn=(PostIn)semObj.getGenericInstance();
           SocialNetworkUser swbSocialUser=postIn.getPostInSocialNetworkUser();
           if(swbSocialUser==null) return;
           final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/userHistory.jsp";
           if (request != null) {
               RequestDispatcher dis = request.getRequestDispatcher(path);
               if (dis != null) {
                   try {
                       request.setAttribute("swbSocialUser", swbSocialUser.getSemanticObject());
                       //System.out.println("Map/doShowDetails:"+request.getParameter("suri"));
                       request.setAttribute("suri", request.getParameter("suri"));
                       request.setAttribute("paramRequest", paramRequest);
                       dis.include(request, response);
                   } catch (Exception e) {
                       log.error(e);
                       e.printStackTrace(System.out);
                   }
               }
           }

       } catch (Exception e) {
           log.error(e);
       }
   }
   
    
}

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
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetworkUser;

/**
 *
 * @author jorge.jimenez
 */
public class StreamMap extends GenericAdmResource{
    
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
        out.println("<div class=\"swbform\">");
        out.println("<form type=\"dijit.form.Form\" id=\"showMap_"+semObj.getId()+"\" action=\"" +  paramRequest.getRenderUrl().setParameter("suri", suri) + "\" method=\"post\" onsubmit=\"submitForm('showMap_" + semObj.getId() + "'); return false;\">");            
        out.println("<table width=\"100%\" border=\"0px\">");            
        out.println("   <tr>");
        out.println("       <td style=\"text-align: center;\">Mostrar mapa con datos desde:</td>");        
        out.println("   </tr>");
        out.println("   <tr>");
        out.println("       <td style=\"text-align: center;\">");
        out.println("           <input type=\"text\" name=\"mapSinceDate\" id=\"mapSinceDate"+semObj.getId()+"\" dojoType=\"dijit.form.DateTextBox\"  size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
        out.println("           <button dojoType=\"dijit.form.Button\" type=\"submit\">Mostrar</button>");
        out.println("       </td>");
        out.println("   </tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</div>");
        if(request.getParameter("mapSinceDate")!=null)
        {
            out.println("<div class=\"swbSocialMapIframe\">");
            out.println("   <iframe width=\"100%\" height=\"100%\" src=\""+paramRequest.getRenderUrl().setMode(Mode_showMap).setParameter("suri", request.getParameter("suri")).setParameter("mapSinceDate"+semObj.getId(), request.getParameter("mapSinceDate")) +"\"></iframe> ");
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
                       System.out.println("Map/doShowDetails:"+request.getParameter("suri"));
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

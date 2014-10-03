/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.Stream;
import org.semanticwb.social.SocialTopic;

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
        ////System.out.println("EWntra a StremMap-0");
        PrintWriter out = response.getWriter();
        User user=paramRequest.getUser();
        String suri=request.getParameter("suri");
        SemanticObject semObj=SemanticObject.getSemanticObject(suri);
        
        String streamMapView="";
        if(request.getParameter("streamMapView")!=null) streamMapView=request.getParameter("streamMapView");
        String date=request.getParameter("mapSinceDate");
        
        out.println("<div class=\"divMap\">");
        //out.println("<div class=\"swbform\">");
        out.println("<form type=\"dijit.form.Form\" id=\"showMap_"+semObj.getModel().getName() + semObj.getId() +"\" action=\"" +  paramRequest.getRenderUrl().setParameter("suri", suri) + "\" method=\"post\" onsubmit=\"submitForm('showMap_" + semObj.getModel().getName() + semObj.getId() + "'); return false;\">");            
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
       out.println("<label><div>Sentimientos:</div> ");
       out.println("<select name=\"streamMapView\"> ");
       sCheck="";
       if(streamMapView.equals("4")) sCheck="selected";
       out.println("<option value=\"4\" " +sCheck+">Todo</option>");
       sCheck="";
       if(streamMapView.equals("1")) sCheck="selected";
       out.println("<option value=\"1\" " +sCheck+">Por estado</option>");
       sCheck="";
       if(streamMapView.equals("2")) sCheck="selected";
       out.println("<option value=\"2\"  " +sCheck+">Mensajes</option>");
       sCheck="";
       /*
       if(streamMapView.equals("3")) sCheck="selected";
       out.println("<option value=\"3\"  " +sCheck+">Mensajes y contadores</option>");
       * */
       sCheck="";
       if(streamMapView.equals("5")) sCheck="selected";
       out.println("<option value=\"5\"  " +sCheck+">Positivos</option>");
       sCheck="";
       if(streamMapView.equals("6")) sCheck="selected";
       out.println("<option value=\"6\"  " +sCheck+">Negativos</option>");
       sCheck="";
       if(streamMapView.equals("7")) sCheck="selected";
       out.println("<option value=\"7\"  " +sCheck+">Neutros</option>");
       out.println("</select>");
       out.println("</label>");
        
       out.println("<label><div>Desde el día:</div><input type=\"text\" name=\"mapSinceDate\" id=\"mapSinceDate"+semObj.getModel().getName() + semObj.getId()+"\"  dojoType=\"dijit.form.DateTextBox\" hasDownArrow=\"true\" value=\""+date+"\" class=\"txtfld-calendar\"/></label>");
        
        ArrayList aNetsSelected=new ArrayList();
        String[] paramNet=request.getParameterValues("networks");
        if(paramNet!=null)
        {
            for(String net: paramNet){
                try{
                    aNetsSelected.add(net);
                }catch(Exception e){}
            }
        }
        ArrayList nets=new ArrayList();
        if(semObj.createGenericInstance() instanceof Stream)
        {
            Stream stream=(Stream)semObj.createGenericInstance();
            nets = SWBSocialUtil.sparql.getStreamSocialNetworks(stream);
        }else if(semObj.createGenericInstance() instanceof SocialTopic){
            SocialTopic socialTopic=(SocialTopic)semObj.createGenericInstance();
            nets = SWBSocialUtil.sparql.getSocialTopicSocialNetworks(socialTopic);
        }
       
        out.println("<div class=\"divMapRed\">");
        out.println("<label><div>Redes sociales:</div>");
        out.println("    <select name=\"networks\" multiple size=\"5\">");
        for(int i = 0; i < nets.size(); i++){
            SocialNetwork socialNet= (SocialNetwork)((SemanticObject)nets.get(i)).createGenericInstance();
            /*
            String iconClass ="";
            if(socialNet instanceof Twitter){
                iconClass = "swbIconTwitter";
            }else if (socialNet instanceof Facebook){
                iconClass = "swbIconFacebook";
            }else if( socialNet instanceof Youtube){
                iconClass = "swbIconYouTube";
            }*/
            String sSelected="";
            if(aNetsSelected.contains(socialNet.getURI())) sSelected="selected";
            out.println("  <option value=\"" + socialNet.getURI() +"\" "+sSelected+">"+ socialNet.getDisplayTitle(user.getLanguage()) + "</option>");
        }
        out.println("    </select>");
        out.println("</label>");
        out.println("    </div>");
        
       String showGeoProfile="";  
       if(request.getParameter("showGeoProfile")!=null && request.getParameter("showGeoProfile").equals("on"))
       {
           showGeoProfile="checked";
       }
       out.println("<div class=\"divMapUbica\">");
       out.println("<input type=\"checkbox\" name=\"showGeoProfile\" id=\"divMapUbica\" "+ showGeoProfile+" />");
       out.println("<label for=\"divMapUbica\" title=\"Muestra la ubicación registrada en el perfil de usuario\">Utilizar ubicación de perfil de usuario</label>");
       out.println("</div>");
       
       out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Mostrar mapa</button>");

        out.println("</form>");
        //out.println("</div>");
        out.println("</div>");
        if(request.getParameter("mapSinceDate")!=null)
        {
            ////System.out.println("mapSinceDate k LLega a Clase:"+request.getParameter("mapSinceDate"));
            out.println("<div class=\"swbSocialMapIframe\">");
            out.println("   <iframe width=\"100%\" height=\"100%\" src=\""+paramRequest.getRenderUrl().setMode(Mode_showMap).setParameter("suri", request.getParameter("suri")).setParameter("mapSinceDate"+semObj.getModel().getName() + semObj.getId(), request.getParameter("mapSinceDate")).setParameter("streamMapView", request.getParameter("streamMapView")).setParameter("networks", request.getParameterValues("networks")).setParameter("showGeoProfile", request.getParameter("showGeoProfile")) +"\"></iframe> ");
            out.println("</div>");
        }
    }
     
     public void doShowMap(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
     {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamMapQuery.jsp";
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
                       ////System.out.println("Map/doShowDetails:"+request.getParameter("suri"));
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

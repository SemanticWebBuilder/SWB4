/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.resources;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticw.social.YouTubeImp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.YouTube;


/**
 *
 * @author jorge.jimenez
 */
public class YouTubeResource extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(YouTubeResource.class);

  
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("/swbadmin/jsp/social/youtube/youtube.jsp");
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite wsite=response.getWebPage().getWebSite();
        String action = response.getAction();
        String socialID=request.getParameter("socialID");
        String entryUrl=request.getParameter("entryUrl");
        try{
            if(action.equals("uploadVideo"))
            {
                System.out.println("socialID en PA:"+socialID);
                YouTube youtube=YouTube.ClassMgr.getYouTube(socialID, wsite);
                System.out.println("youtube cta:"+youtube);
                youtube.postVideo(null, request, response);
            }else if(action.equals("setPlayList")){
                 Enumeration enParams=request.getParameterNames();
                 while(enParams.hasMoreElements()){
                     String pname=(String)enParams.nextElement();
                     System.out.println("parametro:"+pname+",value:"+request.getParameter(pname));
                 }
                 System.out.println("entra a setPlayList:"+entryUrl);
                 if(request.getParameter("id")!=null){
                     //SoluciÃ³n a realizar (TODO)
                     //Lo que se hace es que cuando se sube un video a youtube, youtube genera en ese momento (no antes) un
                     //id para el video subido, sin embargo, aun no se puede utilizar ese id, ya que aun (en ese momento) el
                     //video aun se encuentra en proceso de ser aceptado, es por ello que aqui(en este if) no se puede agregar
                     //el video subido a la lista de reproducciÃ³n que le toca a la secciÃ³n que se encuentra actualmente
                     //(secciÃ³n a la que pertenece la comunidad en la que se este), es por ello que tengo que guardar ese id en algÃºn
                     //lugar y despues asignarlo a dicha lista de reproducciÃ³n que le toque.
                     //Piendo que pudiera asignarse a la secciÃ³n con el Resource.setData(), sin embargo aqui ya PUDIERA existir datos
                     //del usuario y password de twitter, por lo que habria que TALVEZ reestructurar el setData de la pÃ¡gina para que
                     //guardara estos ids que se vayan generando en youtube.
                     //Ademas lo tengo que hacer porque tambien tengo de alguna manera que guardar el id de la Lista de ReproducciÃ³n que
                     //se genere para la secciÃ³n en la que este en el momento y tambiÃ©n la Categoria para la misma
                     //(por defecto estoy poniendo ahorita para pruebas la de "Autos"),y estaria
                     //bien que fuera de la misma forma (PIENSO YO).
                 }
            }
        }catch(Exception e){
            log.error(e);
        }
    }

}

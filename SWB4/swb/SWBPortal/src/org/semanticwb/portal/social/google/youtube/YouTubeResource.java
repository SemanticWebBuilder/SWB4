/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.google.youtube;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.ComplaintEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.util.AuthenticationException;
import java.io.IOException;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class YouTubeResource extends GenericResource {

    private static Logger log = SWBUtils.getLogger(YouTubeResource.class);
    String USERNAME = "george24Infotec@gmail.com";
    String PASSWORD = "george24";
    String DEVELOPERKEY = "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA";
    String GENERICFEEDURL = "http://gdata.youtube.com/feeds/api/users/";
    String FEEDUPLOADS = "/uploads";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (USERNAME == null || PASSWORD == null || DEVELOPERKEY == null) {
            return;
        }
        YouTubeService service = getYouTubeService();
        if (service != null) {
            RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/swbadmin/jsp/youtube/youtube.jsp");
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("service", service);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        YouTubeService service = getYouTubeService();
        if(service==null) return;
        String action = response.getAction();
        String entryUrl=request.getParameter("entryUrl");
        try{
            VideoEntry videoEntry = service.getEntry(new URL(entryUrl), VideoEntry.class);
            if (action.equals("comment")) { //Comenta un video
                String comment=request.getParameter("comment");
                if(comment!=null && comment.trim().length()>0)
                {
                    CommentEntry newComment = new CommentEntry();
                    newComment.setContent(new PlainTextConstruct(comment));
                    service.insert(new URL(videoEntry.getComments().getFeedLink().getHref()), newComment);
                }
            } else if (action.equals("spam")) { //Asigna el status de video no apropiado
                String complaintUrl = videoEntry.getComplaintsLink().getHref();
                ComplaintEntry complaintEntry = new ComplaintEntry();
                String comment=request.getParameter("comment");
                if(comment!=null && comment.trim().length()>0)
                {
                    complaintEntry.setComment(comment);
                }
                service.insert(new URL(complaintUrl), complaintEntry);
            } else if (action.equals("favorite")) { //asigna como favorito el video al usuario autenticado
                System.out.println("pone favorito");
                String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/favorites";
                service.insert(new URL(feedUrl), videoEntry);
            } else if (action.equals("unfavorite")) { //Elimina de la lista de favoritos de un usuario autenticado
                videoEntry.delete();
            } else if (action.equals("delete")) { //Elimina el video de la lista de un usuario autenticado
                videoEntry.delete();
            }
            response.setAction(response.Mode_VIEW);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e);
        }
    }

    private YouTubeService getYouTubeService() {
        YouTubeService service = new YouTubeService("SEMANTICWEBBUILDER", DEVELOPERKEY);
        try {
            service.setUserCredentials(USERNAME, PASSWORD);
        } catch (AuthenticationException e) {
            log.error("Invalid login credentials:", e);
        }
        return service;
    }
}

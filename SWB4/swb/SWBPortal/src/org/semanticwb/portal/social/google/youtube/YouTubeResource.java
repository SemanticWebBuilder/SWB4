/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.google.youtube;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class YouTubeResource extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(YouTubeResource.class);
    String USERNAME = "george24Infotec@gmail.com";
    String PASSWORD = "george24";
    String DEVELOPERKEY = "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA";
    String GENERICFEEDURL = "http://gdata.youtube.com/feeds/api/users/";
    String FEEDUPLOADS = "/uploads";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath()+"/swbadmin/jsp/youtube/youtube.jsp");
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }

//        if (USERNAME == null || USERNAME == null || DEVELOPERKEY == null) {
//            return;
//        }
//
//        YouTubeService service = new YouTubeService("SEMANTICWEBBUILDER", DEVELOPERKEY);
//
//        try {
//            service.setUserCredentials(USERNAME, PASSWORD);
//        } catch (AuthenticationException e) {
//            System.out.println("Invalid login credentials jorge.");
//        }
//
//        getAllFeeds(service,response.getWriter());
    }

    public String getAllFeeds(YouTubeService service, PrintWriter out) {
        System.out.println("entra a getAllFeeds");
        try {
            //String feedUrl = "http://gdata.youtube.com/feeds/api/users/GoogleDevelopers/uploads";
            String feedUrl = GENERICFEEDURL + "semanticweb1" + FEEDUPLOADS;
            VideoFeed videoFeed = service.getFeed(new URL(feedUrl), VideoFeed.class);
            for (VideoEntry entry : videoFeed.getEntries()) {
                System.out.println("Title: " + entry.getTitle().getPlainText());
                System.out.println(entry.getMediaGroup().getDescription().getPlainTextContent());
                System.out.println("entry.getVersionId():"+entry.getVersionId());
                System.out.println("id:"+entry.getId());
                out.println("<object width=\"425\" height=\"355\">");
                out.println("<param name=\"movie\" value=\"http://www.youtube.com/v/fs20OP-2bAc&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1\"></param>");
                out.println("<param name=\"wmode\" value=\"transparent\"></param>");
                out.println("<embed src=\"http://www.youtube.com/v/fs20OP-2bAc&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" width=\"425\" height=\"355\"></embed>");
                out.println("</object>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

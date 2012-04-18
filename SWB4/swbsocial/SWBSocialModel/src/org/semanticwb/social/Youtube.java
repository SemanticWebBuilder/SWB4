package org.semanticwb.social;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.FormUploadToken;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import java.net.URL;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBActionResponse;


public class Youtube extends org.semanticwb.social.base.YoutubeBase 
{
   private static Logger log = SWBUtils.getLogger(Youtube.class);


    public Youtube(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void postVideo(Video video, HttpServletRequest request, SWBActionResponse response)
    {
        System.out.println("Video K llega a Youtube:"+video);
        System.out.println("Video id:"+video.getId());
        System.out.println("Video title:"+video.getTitle());
        System.out.println("Video descr:"+video.getDescription());
        System.out.println("Video Tags:"+video.getTags());
        System.out.println("Video getVideo:"+video.getVideo());
        YouTubeService service = getYouTubeService();
        if (service == null) {
            return;
        }
        String action = response.getAction();
        try {
            if (action.equals("uploadVideo")) {
                VideoEntry newEntry = new VideoEntry();
                newEntry.setLocation("Mexico");
                YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
                //http://gdata.youtube.com/schemas/2007/categories.cat-->pienso que a una cirta comunidad se le deberÃ­a asignar una categoria en especifico
                //(de las del archivo de la mencionada url, ej. Autos) y serÃ­a con la que se subieran los nuevos videos y de esta manera
                //ya no le mostrarÃ­a un combo con todas las categorias para que el usuario final escogiera, porque en realidad en una comunidad se deberian
                //de subir videos con una cierta categoria solamente, que serÃ­a que tuviera relaciÃ³n con el tipo de comunidad en la que se esta.
                //***El tÃ­tulo, la categoria y por lo menos un keyword son requeridos.

                mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
                mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "xyzzy"));

                String title = request.getParameter("title");
                if (title != null && title.trim().length() > 0) {
                    mg.setTitle(new MediaTitle());
                    mg.getTitle().setPlainTextContent(title);
                }
                String keywords = request.getParameter("keywords");
                if (keywords != null && keywords.trim().length() > 0) {
                    mg.setKeywords(new MediaKeywords());
                    if (keywords.indexOf(",") > -1) {
                        StringTokenizer strTokens = new StringTokenizer(keywords, ",");
                        while (strTokens.hasMoreTokens()) {
                            String token = strTokens.nextToken();
                            mg.getKeywords().addKeyword(token);
                        }
                    } else {
                        mg.getKeywords().addKeyword(keywords);
                    }
                }
                String description = request.getParameter("description");
                if (description != null && description.trim().length() > 0) {
                    mg.setDescription(new MediaDescription());
                    mg.getDescription().setPlainTextContent(description);
                }
                //mg.setPrivate(false);
                URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
                FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);

                response.setRenderParameter("jspResponse", "/swbadmin/jsp/social/videoable/videoable.jsp");
                response.setRenderParameter("videoId", newEntry.getId());
                response.setRenderParameter("tokenUrl", token.getUrl());
                response.setRenderParameter("token", token.getToken());
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private YouTubeService getYouTubeService() {
        YouTubeService service = new YouTubeService("SEMANTICWEBBUILDER", "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA");
        try {
            service.setUserCredentials(getLogin(), getPassword());
        } catch (Exception e) {
            log.error("Invalid login credentials:", e);
        }
        return service;
    }
}
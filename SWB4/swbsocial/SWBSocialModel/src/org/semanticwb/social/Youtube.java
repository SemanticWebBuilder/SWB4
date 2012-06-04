package org.semanticwb.social;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.data.youtube.YtPublicationState;
import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.social.util.SWBSocialUtil;


public class Youtube extends org.semanticwb.social.base.YoutubeBase 
{
   private static Logger log = SWBUtils.getLogger(Youtube.class);

   String UPLOAD_URL = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

   
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

                WebSite wsite=response.getWebPage().getWebSite();
                
                VideoEntry newEntry = new VideoEntry();


                newEntry.setLocation("Mexico"); // Debe estar desde la configuración de la red social
                YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
                //http://gdata.youtube.com/schemas/2007/categories.cat-->pienso que a una cirta comunidad se le deberÃ­a asignar una categoria en especifico
                //(de las del archivo de la mencionada url, ej. Autos) y serÃ­a con la que se subieran los nuevos videos y de esta manera
                //ya no le mostrarÃ­a un combo con todas las categorias para que el usuario final escogiera, porque en realidad en una comunidad se deberian
                //de subir videos con una cierta categoria solamente, que serÃ­a que tuviera relaciÃ³n con el tipo de comunidad en la que se esta.
                //***El tÃ­tulo, la categoria y por lo menos un keyword son requeridos.

                mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));       // Debe estar desde la configuración de la red social
                mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "xyzzy"));  // Debe estar desde la configuración de la red social

                String title = video.getTitle();
                if (title != null && title.trim().length() > 0) {
                    mg.setTitle(new MediaTitle());
                    mg.getTitle().setPlainTextContent(title);
                }
                String keywords = video.getTags();
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
                String description = video.getDescription();
                if (description != null && description.trim().length() > 0) {
                    mg.setDescription(new MediaDescription());
                    mg.getDescription().setPlainTextContent(description);
                }
                //mg.setPrivate(false);
                //URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
                //FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);

                mg.setPrivate(false);

               
                newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));       //ver como puedo obtener estos datos (latitud y longitud) dinamicamente
                // alternatively, one could specify just a descriptive string
                // newEntry.setLocation("Mountain View, CA");

                String videoSend = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
                MediaFileSource ms = new MediaFileSource(new File(videoSend), "video/quicktime");
                newEntry.setMediaSource(ms);

                VideoEntry entry = service.insert(new URL(UPLOAD_URL), newEntry);
                System.out.println("createdEntry:"+entry);
                System.out.println("createdEntry:"+entry.getId());
                System.out.println("entry sefLink:"+entry.getSelfLink());
                System.out.println("entry getEtag:"+entry.getEtag());
                System.out.println("entry getKind:"+entry.getKind());
                System.out.println("entry getVersionId:"+entry.getVersionId());

                int post=-1;
                post=entry.getId().lastIndexOf(":");
                if(post>-1)
                {
                    String idEntry=entry.getId().substring(post+1);
                    System.out.println("idEntry********:"+idEntry);
                    //SWBSocialUtil.MONITOR.persistPost2Monitor(video, idEntry, this, wsite);
                    addSentPost(video, idEntry, this);
                }

                System.out.println("createdEntry:"+entry.getPublicationState().getState().name());

                //you upload a video using the direct upload method, then the Upload API response will contain a <link> tag for which the value of the rel attribute is self. To check the status of the uploaded video, send a GET request to the URL identified in this <link> tag.
                //<link rel='self' type='application/atom+xml' href='https://gdata.youtube.com/feeds/api/users/default/uploads/Video_ID'/>

                if(entry.isDraft()) {
                  System.out.println("Video is not live");
                  YtPublicationState pubState = entry.getPublicationState();
                  if(pubState.getState() == YtPublicationState.State.PROCESSING) {
                    System.out.println("Video is still being processed.");
                  }
                  else if(pubState.getState() == YtPublicationState.State.REJECTED) {
                    System.out.print("Video has been rejected because: ");
                    System.out.println(pubState.getDescription());
                    System.out.print("For help visit: ");
                    System.out.println(pubState.getHelpUrl());
                  }
                  else if(pubState.getState() == YtPublicationState.State.FAILED) {
                    System.out.print("Video failed uploading because: ");
                    System.out.println(pubState.getDescription());
                    System.out.print("For help visit: ");
                    System.out.println(pubState.getHelpUrl());
                  }
                }


                response.setRenderParameter("jspResponse", "/swbadmin/jsp/social/videoable/videoable.jsp");
                response.setRenderParameter("videoId", newEntry.getId());
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private YouTubeService getYouTubeService() {
        //YouTubeService service = new YouTubeService("SEMANTICWEBBUILDER", "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA");
        YouTubeService service = new YouTubeService(getAppKey(), getSecretKey());
        try {
            service.setUserCredentials(getLogin(), getPassword());
        } catch (Exception e) {
            log.error("Invalid login credentials:", e);
        }
        return service;
    }
}
/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.social.google.youtube;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.ComplaintEntry;
import com.google.gdata.data.youtube.FormUploadToken;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.AuthenticationException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
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

// TODO: Auto-generated Javadoc
/**
 * Resource that manage the integration between youtube and semanticwebbuilder (youtube java api)
 * and returns the user youtube videos, also the user can upload new videos, mark as favorites, etc.
 * 
 * @author jorge.jimenez
 */
public class YouTubeResource extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(YouTubeResource.class);
    
    /** The USERNAME. */
    String USERNAME = "george24Infotec@gmail.com";
    
    /** The PASSWORD. */
    String PASSWORD = "george24";
    
    /** The DEVELOPERKEY. */
    String DEVELOPERKEY = "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA";
    
    /** The GENERICFEEDURL. */
    String GENERICFEEDURL = "http://gdata.youtube.com/feeds/api/users/";
    
    /** The FEEDUPLOADS. */
    String FEEDUPLOADS = "/uploads";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (USERNAME == null || PASSWORD == null || DEVELOPERKEY == null) {
            return;
        }
        YouTubeService service = getYouTubeService();
        if (service != null) {
            RequestDispatcher dis = request.getRequestDispatcher("/swbadmin/jsp/youtube/youtube.jsp");
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("service", service);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        YouTubeService service = getYouTubeService();
        if(service==null) return;
        String action = response.getAction();
        String entryUrl=request.getParameter("entryUrl");
        try{
            if(action.equals("uploadVideo"))
            {
                System.out.println("entra a processA-1");
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

                String title=request.getParameter("title");
                if(title!=null && title.trim().length()>0)
                {
                    mg.setTitle(new MediaTitle());
                    mg.getTitle().setPlainTextContent(title);
                }
                String keywords=request.getParameter("keywords");
                if(keywords!=null && keywords.trim().length()>0)
                {
                    mg.setKeywords(new MediaKeywords());
                    if(keywords.indexOf(",")>-1){
                        StringTokenizer strTokens=new StringTokenizer(keywords,",");
                        while(strTokens.hasMoreTokens()){
                            String token=strTokens.nextToken();
                            mg.getKeywords().addKeyword(token);
                        }
                    }else{
                        mg.getKeywords().addKeyword(keywords);
                    }
                }
                String description=request.getParameter("description");
                if(description!=null && description.trim().length()>0)
                {
                    mg.setDescription(new MediaDescription());
                    mg.getDescription().setPlainTextContent(description);
                }
                //mg.setPrivate(false);
                URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
                FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);

                System.out.println("pross acc video que envia:"+newEntry.getId());
                //System.out.println("pross acc video que envia link:"+newEntry.getHtmlLink().getHref());
                response.setRenderParameter("videoId", newEntry.getId());
                response.setRenderParameter("tokenUrl", token.getUrl());
                response.setRenderParameter("token", token.getToken());
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
            }else {
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
                    String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/favorites";
                    service.insert(new URL(feedUrl), videoEntry);
                } else if (action.equals("unfavorite")) { //Elimina de la lista de favoritos de un usuario autenticado
                    videoEntry.delete();
                } else if (action.equals("delete")) { //Elimina el video de la lista de un usuario autenticado
                    videoEntry.delete();
                }
                response.setAction(response.Mode_VIEW);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e);
        }
    }

    /**
     * Gets the you tube service.
     * 
     * @return the you tube service
     */
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

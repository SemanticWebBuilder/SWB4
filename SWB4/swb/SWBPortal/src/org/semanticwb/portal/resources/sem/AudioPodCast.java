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
package org.semanticwb.portal.resources.sem;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class AudioPodCast extends org.semanticwb.portal.resources.sem.base.AudioPodCastBase 
{
    private static final Logger log = SWBUtils.getLogger(AudioPodCast.class);
    private static final int DEFAULT_BUFFER_SIZE = 2048; // 2KB.
    public static final String Mode_VOTE = "vote";
    public static final String Mode_PLAY = "play";
    public static final int LATEST = 4;
    public static final int PREVIOUS = 6;
    
    private final SimpleDateFormat ttb = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
    private final DecimalFormat df = new DecimalFormat("#.00");

    public AudioPodCast()
    {
    }

   /**
   * Constructs a AudioPodCast with a SemanticObject
   * @param base The SemanticObject with the properties for the AudioPodCast
   */
    public AudioPodCast(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_VOTE.equals(mode))
            doVote(request, response, paramRequest);
        else if(Mode_PLAY.equals(mode))
            doPlay(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        final Resource base = getResourceBase();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage();
        PrintWriter out =  response.getWriter();
        
        SWBResourceURL directURL = paramRequest.getRenderUrl().setMode(Mode_PLAY).setCallMethod(SWBParamRequest.Call_DIRECT);
        
        if(paramRequest.getCallMethod()==SWBParamRequest.Call_STRATEGY) {
            String surl = null;
            Iterator<Resourceable> res = base.listResourceables();
            while(res.hasNext()) {
                Resourceable re = res.next();
                if( re instanceof WebPage ) {
                    surl = ((WebPage)re).getUrl();
                    break;
                }
            }
            final String contentURL = surl;
            
            Iterator<AudioFile> resources = AudioFile.ClassMgr.listAudioFiles(base.getWebSite());
            resources = SWBComparator.sortByCreated(resources, false);
            if(resources.hasNext()) {
                File f;
                out.println("<div class=\"swb-podcast\">");
                out.println("<h2>"+(base.getDisplayTitle(lang)==null?base.getTitle():base.getDisplayTitle(lang))+"</h2>");
                out.println("<h3>"+paramRequest.getLocaleString("latest") +"</h3>");
                out.println("<ul class=\"swb-podcast-list\">");
                for(int i=0; i<LATEST && resources.hasNext(); i++) {
                    AudioFile audiofile = resources.next();
                    if(!audiofile.isValid() || !user.haveAccess(audiofile)) {
                        continue;
                    }
                    f = audiofile.getFile();
                    if(f!=null && f.isFile()) {
                        out.println("<li class=\"swb-podcast-item\">");
                        out.println(" <div class=\"swb-pdcst-caja\">");
                        out.println("  <h4>"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"</h4>");
                        out.println("  <div class=\"swb-pdcst-texto\"><p>"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p></div>");
                        out.println("  <div class=\"swb-pdcst-texto\">");
                        out.print("    <p class=\"swb-pdcst-escuchar\">");
                        out.print("     <a href=\""+contentURL+"?uri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                        try {
                            out.print("  &nbsp;<span class=\"swb-pdcst-ftime\">"+paramRequest.getLocaleString("duration")+":&nbsp;"+hhmm.format(ttb.parse(audiofile.getDuration()))+"</span>");
                        }catch(ParseException pe) {
                        }
                        out.print("  </p>");
                        out.print("  <p class=\"swb-pdcst-descargar\">");
                        out.print("   <a href=\""+directURL.setParameter("uri", audiofile.getEncodedURI())+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;[<span class=\"swb-pdcst-ffmt\">"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"]</span>");
                        out.print("  &nbsp;<span class=\"swb-pdcst-fsize\">["+df.format(f.length()/1048576.0)+" Mb]</span>");
                        out.println("  </p>");
                        out.println("  </div>");
                        out.println(" </div>");
                        out.println("</li>");
                    }
                }
                out.println("</ul>");
                if(resources.hasNext()) {
                    out.println("<h3>"+paramRequest.getLocaleString("previous")+"</h3>");
                    out.println("<ul id=\"swb-pdcst-listall\">");
                    for(int i=0; i<PREVIOUS && resources.hasNext(); i++) {
                        AudioFile audiofile = resources.next();
                        if(!audiofile.isValid() || !user.haveAccess(audiofile))
                            continue;
                        f = audiofile.getFile();
                        if(f!=null && f.isFile()) {
                            out.println("<li>");
                            out.println(" <div class=\"swb-pdcst-caja\">");
                            out.println("  <h4>"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"</h4>");
                            out.println("  <div class=\"swb-pdcst-texto\"><p>"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p></div>");
                            out.println("  <div class=\"swb-pdcst-texto\">");
                            out.print("    <p class=\"swb-pdcst-escuchar\">");
                            out.print("      <a href=\""+contentURL+"?uri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                            try {
                                out.print("  &nbsp;<span>"+paramRequest.getLocaleString("duration")+":&nbsp;"+hhmm.format(ttb.parse(audiofile.getDuration()))+"</span>");
                            }catch(ParseException pe) {
                            }
                            out.print("  </p>");
                            out.print("  <p class=\"swb-pdcst-descargar\">");
                            out.print("   <a href=\""+directURL.setParameter("uri", audiofile.getEncodedURI())+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;[<span class=\"swb-pdcst-ffmt\">"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"]</span>");
                            f = new File(SWBPortal.getWorkPath()+audiofile.getWorkPath()+"/"+audiofile.getFilename());
                            out.print("  &nbsp;<span class=\"swb-pdcst-fsize\">["+df.format(f.length()/1048576.0)+" Mb]</span>");
                            out.println("  </p>");
                            out.println("  </div>");
                            out.println(" </div>");
                            out.println("</li>");
                        }
                    }
                    out.println("</ul>");
                    out.println(" <p><a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(SWBResourceURL.Mode_INDEX)+"','swb-pdcst-listall')\">"+paramRequest.getLocaleString("seemore") +"&nbsp;&raquo;</a></p>");
                }
                out.println("</div>");
            }
        }else {
            String uri = request.getParameter("uri");
            if(uri==null)
                return;
            uri = URLDecoder.decode(uri, "UTF-8");
            AudioFile audiofile = null;
            try {
                audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
            }catch(Exception e) {
            }
            if(audiofile!=null && audiofile.isValid()) {
                synchronized(this) {
                    audiofile.setReviews(audiofile.getReviews()+1);
                }
                out.println("<div class=\"swb-podcast\">");
                out.println(" <h2 class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"</h2>");
                out.println(" <div class=\"swb-pdcst-autor\">");
                out.println(" <p>"+paramRequest.getLocaleString("by")+": "+(audiofile.getAuthor()==null?paramRequest.getLocaleString("anonymous"):audiofile.getAuthor())+"</p>");
                out.println(" <div class=\"swb-pdcst-player\">");
                String clsid, codebase;
                String resourceURL = SWBPortal.getWebWorkPath()+audiofile.getWorkPath()+"/"+audiofile.getFilename();
                boolean automatic = audiofile.isAutoplay();
                boolean controls = audiofile.isShowcontroller();
                if(audiofile.getFilename().endsWith(".au")) {
                    clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                    codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
                    out.println("<object id=\"audio_"+base.getId()+"\" class=\"swb-audio\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\"240\" height=\"48\">");
                    out.println("  <param name=\"src\" value=\""+resourceURL+"\">");
                    out.println("  <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("  <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    out.println("  <param name=\"controller\" value=\""+controls+"\">");
                    out.println("  <param name=\"pluginspage\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <object type=\"audio/x-mpeg\" data=\""+resourceURL+"\" width=\"240\" height=\"48\">");
                    out.println("    <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("    <param name=\"pluginurl\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    out.println("    <param name=\"controller\" value=\""+controls+"\">");
                    out.println("    </object>");
                    out.println("</object>");
                }else if( audiofile.getFilename().endsWith(".wav") ) {
                    clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                    codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
                    out.println("<object id=\"audio_"+base.getId()+"\" class=\"swb-audio\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\"240\" height=\"48\">");
                    out.println("  <param name=\"src\" value=\""+resourceURL+"\"/>");
                    out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                    out.println("  <param name=\"autostart\" value=\""+(automatic?1:0)+"\"/>");
                    out.println("  <param name=\"controller\" value=\""+controls+"\"/>");
                    out.println("  <param name=\"pluginspage\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <object type=\"audio/x-wav\" data=\""+resourceURL+"\" width=\"240\" height=\"48\">");
                    out.println("    <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("    <param name=\"pluginurl\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    out.println("    <param name=\"controller\" value=\""+controls+"\">");
                    out.println("    </object>");
                    out.println("</object>");
                }else if(audiofile.getFilename().endsWith(".ra")) {
                    out.print("El formato RA no es soportado.");
                }else if(audiofile.getFilename().endsWith(".mp3")) {
                    clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                    codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
                    out.println("<object id=\"audio_"+base.getId()+"\" class=\"swb-audio\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\"240\" height=\"24\" >");
                    out.println("  <param name=\"src\" value=\""+resourceURL+"\"/>");
                    out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                    out.println("  <param name=\"autostart\" value=\""+(automatic?1:0)+"\"/>");
                    out.println("  <param name=\"controller\" value=\""+controls+"\"/>");
                    out.println("  <param name=\"pluginspage\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <object type=\"audio/x-mpeg\" data=\""+resourceURL+"\" width=\"240\" height=\"24\">");
                    out.println("    <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("    <param name=\"pluginurl\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    out.println("    <param name=\"controller\" value=\""+controls+"\">");
                    out.println("    </object>");
                    out.println("</object>");
                }else if(audiofile.getFilename().endsWith(".wma")) {
                    clsid = "clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95";
                    codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,0,02,902";
                    out.println("<object id=\"audio_"+base.getId()+"\" class=\"swb-audio\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\"240\" height=\"48\">");
                    out.println("  <param name=\"src\" value=\""+resourceURL+"\"/>");
                    out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                    out.println("  <param name=\"autostart\" value=\""+(automatic?1:0)+"\"/>");
                    out.println("  <param name=\"controller\" value=\""+controls+"\"/>");
                    out.println("  <param name=\"pluginspage\" value=\"http://www.apple.com/quicktime/download/\"/>");
                    out.println("    <object type=\"audio/x-ms-wma\" data=\""+resourceURL+"\" width=\"240\" height=\"48\">");
                    out.println("    <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("    <param name=\"pluginurl\" value=\"http://www.apple.com/quicktime/download/\">");
                    out.println("    <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    out.println("    <param name=\"controller\" value=\""+controls+"\">");
                    out.println("    </object>");
                    out.println("  </embed>");
                    out.println("</object>");
                }else if(audiofile.getFilename().endsWith(".rm")) {
                    clsid = "clsid:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA";
                    out.println("<object id=\"RVOCX\" class=\"swb-audio\" classid=\""+clsid+"\" width=\"240\" height=\"48\">");
                    out.println("  <param name=\"src\" value=\""+resourceURL+"\"/>");
                    out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                    out.println("  <param name=\"autostart\" value=\""+(automatic?1:0)+"\"/>");
                    if(controls)
                        out.println("  <param name=\"controls\" value=\"ControlPanel\"/>");
                    out.println("    <object type=\"audio/x-pn-realaudio\" data=\""+resourceURL+"\" width=\"240\" height=\"48\">");
                    out.println("    <param name=\"autoplay\" value=\""+automatic+"\">");
                    out.println("    <param name=\"autostart\" value=\""+(automatic?1:0)+"\">");
                    if(controls)
                        out.println("    <param name=\"controls\" value=\"ControlPanel\">");
                    out.println("    </object>");
                    out.println("</object>");
                }else {
                    out.print("<p>Formato no soportado.</p>");
                }
                out.println(" </div>");
                out.println(" <div id=\"rank\">");
                HttpSession session = request.getSession(true);
                final String rid = base.getWebSiteId()+"_"+base.getId()+"_"+audiofile.getId();
                DecimalFormat decf = new DecimalFormat("###");
                if(session.getAttribute(rid)!=null)
                    out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
                else {
                    try {
                        out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(paramRequest.Call_DIRECT).setParameter("uri", audiofile.getEncodedURI())+"','rank')\" title=\""+paramRequest.getLocaleString("like")+"\" class=\"swb-pdcst-rank\">"+paramRequest.getLocaleString("like")+"</a></p>");
                    }catch(SWBResourceException swbe) {
                        out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;Me gusta&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(paramRequest.Call_DIRECT).setParameter("uri", audiofile.getEncodedURI())+"','rank')\" title=\"Me gusta\">Me gusta</a></p>");
                    }
                }
                out.println(" </div>");
                out.println(" <div id=\"descargar\">");
                out.println("  <p><img src=\"img/descargar.png\" width=\"21\" height=\"17\" align=\"left\" /></p>");
                out.println(" </div>");
                out.println(" <div class=\"swb-pdcst-descargar\">");
                out.println("  <input type=\"button\" value=\""+paramRequest.getLocaleString("download") +"\" onclick=\"location.href='"+directURL.setParameter("uri", audiofile.getEncodedURI())+"'\" />");
                out.println(" </div>");
                out.println(" </div>");
                out.println("</div>");
            }
            
        }
    }
    
    private void renderPodcastList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        PrintWriter out =  response.getWriter();
        Resource base = getResourceBase();
        final String lang = paramRequest.getUser().getLanguage();
        
        String surl = paramRequest.getWebPage().getUrl(lang);
        Iterator<Resourceable> res = base.listResourceables();
        while(res.hasNext()) {
            Resourceable re = res.next();
            if( re instanceof WebPage ) {
                surl = ((WebPage)re).getUrl(lang);
                break;
            }
        }
        final String contentURL = surl;
        
        SWBResourceURL directURL = paramRequest.getRenderUrl().setMode(Mode_PLAY).setCallMethod(paramRequest.Call_DIRECT);
        
        Iterator<AudioFile> resources = AudioFile.ClassMgr.listAudioFiles(base.getWebSite());
        resources = SWBComparator.sortByCreated(resources, false);
        for(int i=0; i<LATEST && resources.hasNext(); i++)
            resources.next();
        if(resources.hasNext()) {
            while(resources.hasNext()) {
                AudioFile audiofile = resources.next();          
                out.println("<li>");
                out.println(" <div class=\"swb-pdcst-caja\">");
                out.println("  <h4>"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"</h4>");
                out.println("  <div class=\"swb-pdcst-texto\"><p>"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p></div>");
                out.println("  <div class=\"swb-pdcst-texto\">");
                out.print("    <p class=\"swb-pdcst-escuchar\">");
                out.print("      <a href=\""+contentURL+"?uri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                try {
                    out.print("  &nbsp;<span>"+paramRequest.getLocaleString("duration")+":&nbsp;"+hhmm.format(ttb.parse(audiofile.getDuration()))+"</span>");
                }catch(ParseException pe) {
                    log.error(pe);
                }
                out.println("  </p>");
                out.println("  <p class=\"swb-pdcst-descargar\"><a href=\""+directURL.setParameter("uri", audiofile.getEncodedURI())+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a><span>"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"</span></p>");
                out.println("  </div>");
                out.println(" </div>");
                out.println("</li>");
            }
        }
    }
    
    public void doPlay(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String uri = request.getParameter("uri");
        uri = URLDecoder.decode(uri, "UTF-8");
        AudioFile audiofile = null;
        try {
            audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
        }catch(Exception e) {
            log.error(e);
        }
        if(audiofile!=null && audiofile.isValid()) {
            String filename = audiofile.getFilename();
            if(filename.endsWith(".htm"))
                response.setContentType("text/html");
    //        else if(filename.endsWith(".xml"))
    //            response.setContentType("text/xml");
    //        else if(filename.endsWith(".rtf"))
    //            response.setContentType("application/rtf");
    //        else if(filename.endsWith(".pdf"))
    //            response.setContentType("application/pdf");
    //        else if (filename.endsWith(".do"))
    //            response.setContentType("application/msword");
    //        else if(filename.endsWith(".xl"))
    //            response.setContentType("application/vnd.ms-excel");
    //        else if(filename.endsWith(".pp"))
    //            response.setContentType("application/vnd.ms-powerpoint");
    //        else if(filename.endsWith(".msg"))
    //            response.setContentType("application/vnd.ms-outlook");
    //        else if(filename.endsWith(".mpp"))
    //            response.setContentType("application/vnd.ms-project");
    //        else if(filename.endsWith(".iii"))
    //            response.setContentType("application/x-iphone");
    //        else if(filename.endsWith(".mdb"))
    //            response.setContentType("application/x-msaccess");
    //        else if(filename.endsWith(".pub"))
    //            response.setContentType("application/x-mspublisher");
    //        else if(filename.endsWith(".swf"))
    //            response.setContentType("application/x-shockwave-flash");
    //        else if(filename.endsWith(".text"))
    //            response.setContentType("application/x-tex");
            else if (filename.endsWith(".zip"))
                response.setContentType("application/zip");
    //        else if (filename.endsWith(".jp"))
    //            response.setContentType("image/jpeg");
    //        else if (filename.endsWith(".png"))
    //            response.setContentType("image/png");
    //        else if (filename.endsWith(".gif"))
    //            response.setContentType("image/gif");
    //        else if(filename.endsWith(".bmp"))
    //            response.setContentType("image/bmp");
    //        else if(filename.endsWith(".tif"))
    //            response.setContentType("image/tiff");
    //        else if(filename.endsWith(".ico"))
    //            response.setContentType("image/x-icon");
            else if (filename.endsWith(".mp3"))
                response.setContentType("audio/mpeg");
    //        else if(filename.endsWith(".mp"))
    //            response.setContentType("video/mpeg");
    //        else if(filename.endsWith(".mov"))
    //            response.setContentType("video/quicktime");
    //        else if(filename.endsWith(".qt"))
    //            response.setContentType("video/quicktime");
    //        else if(filename.endsWith(".as"))
    //            response.setContentType("video/x-ms-asf");
    //        else if(filename.endsWith(".avi"))
    //            response.setContentType("video/x-msvideo");
    //        else
    //            response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            String resourceURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+SWBPortal.getContextPath()+SWBPortal.getWebWorkPath()+audiofile.getWorkPath()+"/"+URLDecoder.decode(audiofile.getFilename(), "UTF-8");
            URL url = new URL(resourceURL);
            BufferedInputStream  bis = new BufferedInputStream(url.openStream());
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.flush();
            bos.close();
        }
    }
    
    public void doVote(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        
        HttpSession session = request.getSession(true);
        final String uri = request.getParameter("uri");
        AudioFile audiofile = null;
        try {
            audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
        }catch(Exception e) {
            log.error(e);
        }    
        if(audiofile!=null && audiofile.isValid()) {
            DecimalFormat decf = new DecimalFormat("###");
            final String rid = base.getWebSiteId()+"_"+base.getId()+"_"+audiofile.getId();
            if(session.getAttribute(rid)!=null)
               out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
            else {
                synchronized(this) {
                    audiofile.setRank(audiofile.getRank()+1);
                }
                session.setAttribute(rid,rid);
                try {
                    out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
                }catch(SWBResourceException swbe) {
                    out.println("  <p>"+decf.format(audiofile.getRank())+" Me gusta</p>");
                }
            }
        }
    }
    
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        
        renderPodcastList(request, response, paramRequest);
    }
}

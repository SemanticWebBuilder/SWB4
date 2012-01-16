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
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class AudioPodCast extends org.semanticwb.portal.resources.sem.base.AudioPodCastBase 
{
    private static final Logger log = SWBUtils.getLogger(AudioPodCast.class);
    private static final int DEFAULT_BUFFER_SIZE = 2048; // 2KB.
    public static final String Mode_VOTE = "vote";

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
        else
            super.processRequest(request, response, paramRequest);
    }


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        Resource base = getResourceBase();
        String lang = paramRequest.getUser().getLanguage();
        
        PrintWriter out =  response.getWriter();
        
        SWBResourceURL directURL = paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).setCallMethod(paramRequest.Call_DIRECT);
        
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY) {
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
            
            SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", new Locale(lang));
            
            Iterator<AudioFile> resources = AudioFile.ClassMgr.listAudioFiles(base.getWebSite());
            resources = SWBComparator.sortByCreated(resources, false);
            if(resources.hasNext()) {
                out.println("<div id=\"contenido_podcast\">");
                out.println("<h2>"+base.getDisplayTitle(lang)+"</h2>");
                out.println("<h3>"+paramRequest.getLocaleString("latest") +"</h3>");
                out.println("<ul>");
                for(int i=0; i<3&&resources.hasNext(); i++) {
                    AudioFile audiofile = resources.next();
//String resourceURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+SWBPortal.getContextPath()+SWBPortal.getWebWorkPath()+audiofile.getWorkPath()+"/"+URLDecoder.decode(audiofile.getFilename(), "UTF-8");
//URL url = new URL(resourceURL);
//File f=new File(url.getFile());
//System.out.println("file="+f+", espacio total="+f.getTotalSpace()+", espacio usado="+f.getUsableSpace());
                    out.println("<li>");
                    out.println(" <div class=\"caja\">");
                    out.println("  <h4>"+audiofile.getDisplayTitle(lang)+"</h4>");
                    out.println("  <div class=\"caja_texto\"><p>"+audiofile.getDisplayDescription(lang)+"</p></div>");
                    out.println("  <div class=\"caja_texto\">");
                    out.print("    <p class=\"escuchar_mp3\">");
                    out.print("      <a href=\""+contentURL+"?uri="+audiofile.getEncodedURI()+"\" title=\""+audiofile.getDisplayTitle(lang)+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                    try {
                        out.print("  &nbsp;<span>"+paramRequest.getLocaleString("duration")+":&nbsp;"+sdf.format(pd.parse(audiofile.getDuration()))+"</span>");
                    }catch(ParseException pe) {
                        log.error(pe);
                    }
                    out.println("  </p>");
                    out.println("  <p class=\"descargar_mp3\"><a href=\""+directURL.setParameter("uri", audiofile.getURI())+"\" title=\""+audiofile.getDisplayTitle(lang)+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;<span>"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getFilename().substring(audiofile.getFilename().lastIndexOf(".")+1)+"</span></p>");
                    out.println("  </div>");
                    out.println(" </div>");
                    out.println("</li>");
                }
                out.println("</ul>");
                if(resources.hasNext()) {
                    out.println("<h3>"+paramRequest.getLocaleString("previous") +"</h3>");
                    out.println("<ul>");
                    while(resources.hasNext()) {
                        AudioFile audiofile = resources.next();          
                        out.println("<li>");
                        out.println(" <div class=\"caja\">");
                        out.println("  <h4>"+audiofile.getDisplayTitle(lang)+"</h4>");
                        out.println("  <div class=\"caja_texto\"><p>"+audiofile.getDisplayDescription(lang)+"</p></div>");
                        out.println("  <div class=\"caja_texto\">");
                        out.print("    <p class=\"escuchar_mp3\">");
                        out.print("      <a href=\""+contentURL+"?uri="+audiofile.getEncodedURI()+"\" title=\""+audiofile.getDisplayTitle(lang)+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                        try {
                            out.print("  &nbsp;<span>"+paramRequest.getLocaleString("duration")+":&nbsp;"+sdf.format(pd.parse(audiofile.getDuration()))+"</span>");
                        }catch(ParseException pe) {
                            log.error(pe);
                        }
                        out.println("  </p>");
                        out.println("  <p class=\"descargar_mp3\"><a href=\""+directURL.setParameter("uri", audiofile.getURI())+"\" title=\""+audiofile.getDisplayTitle(lang)+"\">"+paramRequest.getLocaleString("download")+"</a><span>"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getFilename().substring(audiofile.getFilename().lastIndexOf(".")+1)+"</span></p>");
                        out.println("  </div>");
                        out.println(" </div>");
                        out.println("</li>");
                    }
                    out.println("</ul>");
                }
                out.println("</div>");
            }
        }else {
            String uri = request.getParameter("uri");
            if(uri!=null && !uri.isEmpty()){
                uri = URLDecoder.decode(uri, "UTF-8");
                AudioFile audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
                if(audiofile!=null && audiofile.isValid()) {
                    synchronized(this) {
                        audiofile.setReviews(audiofile.getReviews()+1);
                    }
                    out.println("<h2 class=\"title\">"+audiofile.getDisplayTitle(lang) +"</h2>");
                    out.println(" <div class=\"swb-comentario-sem-autor\">");
                    out.println(" <p>"+paramRequest.getLocaleString("by")+": "+(audiofile.getAuthor()==null?paramRequest.getLocaleString("anonymous"):audiofile.getAuthor())+"</p>");
                    out.println(" <div id=\"player\">");
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
    final String rid = base.getId();
    DecimalFormat decf = new DecimalFormat("###");
    if(session.getAttribute(rid)!=null)
        out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
    else {
        session.setAttribute(rid,rid);
        try {
            out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(paramRequest.Call_DIRECT).setParameter("uri", audiofile.getEncodedURI())+"','rank')\" title=\""+paramRequest.getLocaleString("like")+"\">"+paramRequest.getLocaleString("like")+"</a></p>");
        }catch(SWBResourceException swbe) {
            out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;Me gusta&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(paramRequest.Call_DIRECT).setParameter("uri", audiofile.getEncodedURI())+"','rank')\" title=\"Me gusta\">Me gusta</a></p>");
        }
    }
    out.println(" </div>");
    
    out.println(" <div id=\"descargar\">");
    out.println("  <p><img src=\"img/descargar.png\" width=\"21\" height=\"17\" align=\"left\" /></p>");
    out.println(" </div>");
    out.println(" <div class=\"swb-comentario-sem-boton\">");
    out.println("  <input type=\"button\" value=\"Descargar a tu PC\" onclick=\"location.href='"+directURL.setParameter("uri", audiofile.getURI())+"'\" />");
    out.println(" </div>");
    out.println("</div>");
                }/////////////////////////////////////////
            }
        }
    }
    
    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String uri = request.getParameter("uri");
        AudioFile audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(audiofile!=null) {
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
            URL url = new URL(resourceURL);File f=new File(url.getFile());
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
        
        HttpSession session = request.getSession(true);
        final String uri = request.getParameter("uri");
        final String rid = getResourceBase().getId();
        try {
            DecimalFormat decf = new DecimalFormat("###");
            AudioFile audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
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
        }catch(Exception e) {
        }
    }

//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        String uri = request.getParameter("uri");
//        AudioFile audiofile = (AudioFile)SemanticObject.createSemanticObject(uri).createGenericInstance();
//        if(audiofile!=null) {
//            response.setRenderParameter("uri", uri);
//            double rank = audiofile.getRank() + 1;
//            audiofile.setRank(rank);
//        }
//    }
}

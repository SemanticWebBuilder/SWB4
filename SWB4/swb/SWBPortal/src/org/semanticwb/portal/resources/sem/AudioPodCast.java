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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class AudioPodCast extends org.semanticwb.portal.resources.sem.base.AudioPodCastBase 
{
    private static final Logger log = SWBUtils.getLogger(AudioPodCast.class);
    private static final int DEFAULT_BUFFER_SIZE = 2048; // 2KB.
    public static final String Mode_VOTE = "vote";
    public static final String Mode_PLAY = "play";
    
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
            doDownload(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        final Resource base = getResourceBase();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage();
        PrintWriter out =  response.getWriter();
        SimpleDateFormat ttb = new SimpleDateFormat("dd/MMM/yyyy", new Locale(lang, user.getCountry()==null?"mx":user.getCountry()));
        
        SWBResourceURL directURL = paramRequest.getRenderUrl().setMode(Mode_PLAY).setCallMethod(SWBParamRequest.Call_DIRECT);
        
        if(paramRequest.getCallMethod()==SWBParamRequest.Call_STRATEGY)
        {
            final String sid = request.getParameter("sid");
            final String year = request.getParameter("yr");
           
            if(sid!=null)
            {
                Iterator<AudioFile> resources;
                AudioPodcastTheme theme = AudioPodcastTheme.ClassMgr.getAudioPodcastTheme(sid, base.getWebSite());
                try {
                    resources = theme.listAudioFiles();
                }catch(Exception e) {
                    resources = AudioFile.ClassMgr.listAudioFiles(base.getWebSite());
                }
                renderPodcastList(request, response, paramRequest, resources);
            }
            else if(year!=null)
            {
                SemanticModel model = base.getWebSite().getSemanticModel();
                List<AudioFile> audios = new ArrayList<AudioFile>();
                Iterator<SemanticObject> resources = model.listSubjects(AudioFile.audiopdcst_year, year);
                while(resources.hasNext()) {
                    SemanticObject sobj = resources.next();
                    if (sobj != null && sobj.instanceOf(AudioFile.sclass)) {
                        audios.add((AudioFile)sobj.createGenericInstance());
                    }
                }
                renderPodcastList(request, response, paramRequest, audios.iterator());
            }
            else
            {
                renderPodcastList(request, response, paramRequest, AudioFile.ClassMgr.listAudioFiles(base.getWebSite()));
            }
        }
        else
        {
            String suri = request.getParameter("suri");
            if(suri==null) {
                return;
            }
            try {
                suri = URLDecoder.decode(suri, "UTF-8");
            }catch(Exception unsage) {
                suri = null;
            }
            AudioFile audiofile = null;
            try {
                audiofile = (AudioFile)SemanticObject.createSemanticObject(suri).createGenericInstance();
            }catch(Exception e) {
                out.println("<p>"+paramRequest.getLocaleString("noElement")+"</p>");
                return;
            }
            if(audiofile!=null && audiofile.isValid() && user.haveAccess(audiofile)) {
                synchronized(this) {
                    audiofile.setReviews(audiofile.getReviews()+1);
                }
                
                out.println("<div class=\"swb-podcast\">");                
                File f;
                long duration;
                String size;
                try {
                    f = audiofile.getFile();
                    //AudioInputStream ais = new MpegAudioFileReader().getAudioInputStream(f);
                    AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(f);
                    Map properties = baseFileFormat.properties();
                    duration = (Long)properties.get("duration");
                    size = df.format(f.length()/1048576.0);
                }catch(Exception e) {
                    f = null;
                    duration = 0;
                    size = "0";
                }
                out.println(" <h2 class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null ? audiofile.getTitle() : audiofile.getDisplayTitle(lang))+"</h2>");
                out.println(" <div class=\"swb-pdcst-player\">");
                if(f!=null) {
                    String resourceURL = SWBPortal.getWebWorkPath()+audiofile.getWorkPath()+"/"+f.getName();
                    if(audiofile.getFilename().endsWith(".mp3"))
                    {
                        out.println("<object type=\"application/x-shockwave-flash\" data=\""+SWBPlatform.getContextPath()+"/swbadmin/player_mp3_maxi.swf\" width=\""+getWidth()+"\" height=\""+getHeight()+"\">");
                        out.println("<param name=\"movie\" value=\""+SWBPlatform.getContextPath()+"/swbadmin/player_mp3_maxi.swf\" />");
                        if(isTransparent()) {
                            out.println("<param name=\"wmode\" value=\"transparent\" />");
                        }
                        //out.println("<param name=\"title\" value=\""+audiofile.getFilename()+"\" />");
                        out.print("<param name=\"FlashVars\" value=\"mp3="+resourceURL+"");
                        out.print("&amp;title="+audiofile.getFilename());
                        out.print("&amp;autoplay="+(isAutoplay()?"1":"0"));
                        out.print("&amp;loop="+(isLoop()?"1":"0"));
                        out.print("&amp;autoload="+(isAutoload()?"1":"0"));                    
                        out.print("&amp;volume="+(getVolume()));
                        out.print("&amp;showstop="+(isShowStop()?"1":"0"));
                        out.print("&amp;showvolume="+(isShowVolume()?"1":"0"));
                        out.print("&amp;showslider="+(isShowSlider()?"1":"0"));
                        if(getShowLoading()!=null) {
                            out.print("&amp;showloading="+getShowLoading());
                        }
                        if(getLoadingColor()!=null) {
                            out.print("&amp;loadingcolor="+getShowLoading());
                        }
                        if(getTextcolor()!=null) {
                            out.print("&amp;textcolor="+getTextcolor());
                        }
                        if(!isTransparent() && getBgcolor()!=null) {
                            out.print("&amp;bgcolor="+getBgcolor());
                        }
                        if(getBgcolor1()!=null) {
                            out.print("&amp;bgcolor1="+getBgcolor1());
                        }
                        if(getBgcolor2()!=null) {
                            out.print("&amp;bgcolor2="+getBgcolor2());
                        }
                        if(getButtonColor()!=null) {
                            out.print("&amp;buttoncolor="+getButtonColor());
                        }
                        if(getButtonOvercolor()!=null) {
                            out.print("&amp;buttonovercolor="+getButtonOvercolor());
                        }
                        if(getSliderColor1()!=null) {
                            out.print("&amp;slidercolor1="+getSliderColor1());
                        }
                        if(getSliderColor2()!=null) {
                            out.print("&amp;slidercolor2="+getSliderColor2());
                        }
                        if(getSliderOvercolor()!=null) {
                            out.print("&amp;sliderovercolor="+getSliderOvercolor());
                        }
                        out.print("&amp;buttonwidth="+getButtonWidth());
                        out.print("&amp;sliderwidth="+getSliderWidth());
                        out.print("&amp;sliderheight="+getSliderHeight());
                        out.print("&amp;volumewidth="+getVolumeWidth());
                        out.print("&amp;volumeheight="+getVolumeHeight());
                        out.println("\" />");
                        out.println("</object>");
                    }
                    else
                    {
                        out.print("<p>Formato no soportado.</p>");
                    }
                }
                else
                {
                    out.println("<p>"+paramRequest.getLocaleString("noAudio")+"</p>");
                    return;
                }
                out.println(" </div>");
                
                out.println("<div class=\"swb-podcast-info\">");
                
                out.println(" <div class=\"swb-pdcst-box swb-pdcst-synps\"><h3>"+paramRequest.getLocaleString("synopsis")+"</h3></div>");
                
                out.println(" <div class=\"swb-pdcst-box swb-pdcst-det\">");
                out.println("  <p class=\"swb-pdcst-text swb-pdcst-pubdate\"><span class=\"swb-pdcst-pub\">"+paramRequest.getLocaleString("publicationDate")+":&nbsp;</span>"+ttb.format(audiofile.getCreated())+"</p>");
                if(duration>0)
                {
                    out.println("  <p class=\"swb-pdcst-text swb-pdcst-pub\">");
                    out.println("  <span class=\"swb-pdcst-fduration\">"+paramRequest.getLocaleString("duration")+":&nbsp;</span>");
                    out.print(duration/60000000);
                    out.print(":");
                    out.print(duration/1000000%60);
                    out.println("  </p>");
                }                
                if(audiofile.getAuthor()!=null) {
                    out.println(" <p class=\"swb-pdcst-text swb-pdcst-author\"><span class=\"swb-pdcst-by\">"+paramRequest.getLocaleString("by")+":&nbsp;</span>"+audiofile.getAuthor()+"</p>");
                }
                out.println("  <p class=\"swb-pdcst-text swb-pdcst-desc\">"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p>");
                out.println(" </div>");
                
                
                out.println(" <div class=\"swb-pdcst-box swb-pdcst-downl\">");
                out.println("  <p class=\"swb-pdcst-text\">");
                out.println("   <a class=\"swb-pdcst-lnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>");
                out.println("    <span class=\"swb-pdcst-ffmt\">"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"</span>");
                out.println("    <span class=\"swb-pdcst-fsize\">"+size+" Mb</span>");
                out.println("  </p>");
                out.println("  <a class=\"swb-pdcst-imglnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>"  );
                out.println(" </div>");
                
                
                out.println(" <div class=\"swb-pdcst-box swb-pdcst-rank\" id=\"rank\">");
                HttpSession session = request.getSession(true);
                final String rid = base.getWebSiteId()+"_"+base.getId()+"_"+audiofile.getId();
                DecimalFormat decf = new DecimalFormat("###");
                if(session.getAttribute(rid)!=null) {
                    out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
                }else {
                    try {
                        out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(SWBParamRequest.Call_DIRECT)+"?suri='+encodeURIComponent('"+audiofile.getURI()+"')"+",'rank')\" title=\""+paramRequest.getLocaleString("like")+"\" class=\"swb-pdcst-rank\">"+paramRequest.getLocaleString("like")+"</a></p>");
                    }catch(SWBResourceException swbe) {
                        out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;Me gusta&nbsp;<a href=\"javascript:postHtml('"+paramRequest.getRenderUrl().setMode(Mode_VOTE).setCallMethod(SWBParamRequest.Call_DIRECT)+"?suri='+encodeURIComponent('"+audiofile.getURI()+"')"+",'rank')\" title=\"Me gusta\">Me gusta</a></p>");
                    }
                }
                out.println(" </div>");
                out.println("</div>");
                out.println("</div>");
            }
        }
    }
    
    private void renderPodcastList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Iterator<AudioFile>resources) throws SWBResourceException, IOException
    {
        final Resource base = getResourceBase();
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage();
        PrintWriter out =  response.getWriter();
        SimpleDateFormat ttb = new SimpleDateFormat("dd/MMM/yyyy", new Locale(lang, user.getCountry()==null?"mx":user.getCountry()));
        final String sid = request.getParameter("sid");
        final String year = request.getParameter("yr");
        
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
        SWBResourceURL directURL = paramRequest.getRenderUrl().setMode(Mode_PLAY).setCallMethod(SWBParamRequest.Call_DIRECT);
        
        out.println("<div class=\"swb-podcast\">");
        if(resources.hasNext())
        {
            resources = SWBComparator.sortByCreated(resources, false);
            List<AudioFile> elements = SWBUtils.Collections.copyIterator(resources);
            //////////////////////
            int el = elements.size();
            int paginas = el/getPageSize();
            if(el%getPageSize() != 0) {
                paginas++;
            }
            int inicio = 0;
            int fin = getPageSize();
            int ipage;
            try {
                ipage = Integer.parseInt(request.getParameter("p"));
            }catch(NumberFormatException fne) {
                ipage = 1;
            }

            // Mantiene la consistencia al eliminar elementos
            if(ipage>paginas)
                ipage--;

            inicio = (ipage * getPageSize()) - getPageSize();
            fin = (ipage * getPageSize());

            if(ipage < 1 || ipage > paginas) {
                ipage = 1;
            }
            if(inicio < 0) {
                inicio = 0;
            }
            if(fin < 0) {
                fin = getPageSize();
            }
            if(fin > el) {
                fin = el;
            }
            if(inicio > fin) {
                inicio = 0;
                fin = getPageSize();
            }
            if( fin-inicio > getPageSize() ) {
                inicio = 0;
                fin = getPageSize();
            }
            //inicio++;
            //////////////////////

            File f;
            long duration;
            if(isDisplayTitle()) {
                out.println(" <p class=\"swb-podcast-title\">"+(base.getDisplayTitle(lang)==null?base.getTitle():base.getDisplayTitle(lang))+"</p>");
            }
            out.println(" <p class=\"swb-podcast-lat\">"+paramRequest.getLocaleString("latest") +"</p>");
            out.println(" <ul class=\"swb-pdcst-list\">");
            for(int i=inicio; i<fin; i++)
            {
                AudioFile audiofile = elements.get(i);
//                if(audiofile==null || !audiofile.isValid() || !user.haveAccess(audiofile)) {
//                    continue;
//                }
                out.println("  <li class=\"swb-pdcst-item\">");
                out.println("   <div class=\"swb-pdcst-box\">");
                try {
                    out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle().substring(0,getScTitle()) : audiofile.getDisplayTitle(lang).substring(0,getScTitle()))+"</p></div>");
                }catch(IndexOutOfBoundsException obe) {
                    out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle() :audiofile.getDisplayTitle(lang))+"</p></div>");
                }
                out.println("      <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-pubdate\"><span class=\"swb-pdcst-pub\">"+paramRequest.getLocaleString("publicationDate")+":</span> "+ttb.format(audiofile.getCreated()) +"</p></div>");
                if(audiofile.getAuthor()!=null) {
                    out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-author\"><span class=\"swb-pdcst-by\">"+paramRequest.getLocaleString("by")+":</span> "+audiofile.getAuthor()+"</p></div>");
                }
                try {
                    out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-desc\">"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription().substring(0,getScDescription()) : audiofile.getDisplayDescription(lang).substring(0,getScDescription()))+"</p></div>");
                }catch(IndexOutOfBoundsException obe) {
                    out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-desc\">"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p></div>");
                }
                out.println("    <div class=\"swb-pdcst-text\">");
                try {
                    f = audiofile.getFile();
                    try {
                        StringBuilder html = new StringBuilder();
                        //AudioInputStream ais = new MpegAudioFileReader().getAudioInputStream(f);
                        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(f);
                        Map properties = baseFileFormat.properties();
                        duration = (Long)properties.get("duration");
                        html.append("<p class=\"swb-pdcst-box-lstn\">");
                        html.append(" <a href=\""+contentURL+"?suri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                        if(duration>0)
                        {
                            html.append(" &nbsp;<span class=\"swb-pdcst-fduration\">"+paramRequest.getLocaleString("duration")+":&nbsp;");
                            html.append(duration/60000000);
                            html.append(":");
                            html.append(duration/1000000%60);
                            html.append(" </span>");
                        }
                        html.append("</p>");                        
                        html.append("<p class=\"swb-pdcst-box-dwln\">");
                        html.append(" <a class=\"swb-pdcst-lnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;");
                        html.append(" <span class=\"swb-pdcst-ffmt\">"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"</span>");
                        html.append(" <span class=\"swb-pdcst-fsize\">"+df.format(f.length()/1048576.0)+" Mb</span>");
                        html.append(" <a class=\"swb-pdcst-imglnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;");
                        html.append("</p>");
                        out.println(html.toString());
                    }catch(UnsupportedAudioFileException unsafe) {
                    }catch(Exception exc) {
                    }
                }catch(Exception e) {                    
                }
                out.println("    </div>");
                out.println("   </div>");
                out.println("  </li>");
                
                
//                try {
//                    f = audiofile.getFile();
//                }catch(Exception e) {
//                    continue;
//                }
//                if(f!=null && f.isFile())
//                {
//                    try {
//                        AudioInputStream ais = new MpegAudioFileReader().getAudioInputStream(f);
//                        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(f);
//                        Map properties = baseFileFormat.properties();
//                        duration = (Long)properties.get("duration");
//                    }catch(UnsupportedAudioFileException unsafe) {
//                        continue;
//                    }                        
//                    out.println("  <li class=\"swb-pdcst-item\">");
//                    out.println("   <div class=\"swb-pdcst-box\">");
//                    try {
//                        out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle().substring(0,getScTitle()) : audiofile.getDisplayTitle(lang).substring(0,getScTitle()))+"</p></div>");
//                    }catch(IndexOutOfBoundsException obe) {
//                        out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-title\">"+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle() :audiofile.getDisplayTitle(lang))+"</p></div>");
//                    }
//                    out.println("      <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-pubdate\"><span class=\"swb-pdcst-pub\">"+paramRequest.getLocaleString("publicationDate")+":</span> "+ttb.format(audiofile.getCreated()) +"</p></div>");
//                    if(audiofile.getAuthor()!=null) {
//                        out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-author\"><span class=\"swb-pdcst-by\">"+paramRequest.getLocaleString("by")+":</span> "+audiofile.getAuthor()+"</p></div>");
//                    }
//                    try {
//                        out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-desc\">"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription().substring(0,getScDescription()) : audiofile.getDisplayDescription(lang).substring(0,getScDescription()))+"</p></div>");
//                    }catch(IndexOutOfBoundsException obe) {
//                        out.println("  <div class=\"swb-pdcst-text\"><p class=\"swb-pdcst-desc\">"+(audiofile.getDisplayDescription(lang)==null?audiofile.getDescription():audiofile.getDisplayDescription(lang))+"</p></div>");
//                    }
//                    out.println("  <div class=\"swb-pdcst-text\">");
//                    out.print("     <p class=\"swb-pdcst-box-lstn\">");
//                    out.print("      <a href=\""+contentURL+"?suri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
//                    if(duration>0)
//                    {
//                        out.print("  &nbsp;<span class=\"swb-pdcst-fduration\">"+paramRequest.getLocaleString("duration")+":&nbsp;");
//                        out.print(duration/60000000);
//                        out.print(":");
//                        out.print(duration/1000000%60);
//                        out.print("</span>");
//                    }
//                    out.print("       </p>");                        
//                    out.print("       <p class=\"swb-pdcst-box-dwln\">");
//                    out.print("        <a class=\"swb-pdcst-lnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;");
//                    out.print("        <span class=\"swb-pdcst-ffmt\">"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"</span>");
//                    out.print("        <span class=\"swb-pdcst-fsize\">"+df.format(f.length()/1048576.0)+" Mb</span>");
//                    out.print("        <a class=\"swb-pdcst-imglnk\" href=\"#\" onclick=\"window.open('"+directURL+"?suri='+encodeURIComponent('"+audiofile.getURI()+"'))\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a>&nbsp;");
//                    out.println("     </p>");
//                    out.println("    </div>");
//                    out.println("   </div>");
//                    out.println("  </li>");
//                }
            }
            out.println(" </ul>");            

            ////////////////////// 
            SWBResourceURL pagerUrl = paramRequest.getRenderUrl();
            if(sid!=null) {
                pagerUrl.setParameter("sid", sid);
            }
            out.println("<div class=\"swb-pdcst-pager\">");
            SWBResourceURL nextURL = paramRequest.getRenderUrl();
            SWBResourceURL previusURL = paramRequest.getRenderUrl();
            if (ipage < paginas) {
                nextURL.setParameter("p", Integer.toString(ipage+1));
                if(sid!=null) {
                    nextURL.setParameter("sid", sid);
                }
                if(year!=null) {
                    nextURL.setParameter("yr", year);
                }
            }
            if (ipage > 1) {
                previusURL.setParameter("p", Integer.toString(ipage-1));
                if(sid!=null) {
                    previusURL.setParameter("sid", sid);
                }
                if(year!=null) {
                    previusURL.setParameter("yr", year);
                }
            }
            if (ipage > 1) {
                out.println("<a class=\"swb-pdcst-sk\" href=\""+previusURL+"\">Anterior</a>");
            }
            for (int i = 1; i <= paginas; i++)
            {
                if(i==ipage) {
                    out.println("<span class=\"swb-pdcst-cidx\">");
                }else {
                    if(sid!=null) {
                        pagerUrl.setParameter("sid", sid);
                    }
                    if(year!=null) {
                        pagerUrl.setParameter("yr", year);
                    }
                    out.println("<a class=\"swb-pdcst-idx\" href=\""+pagerUrl.setParameter("p", Integer.toString(i))+"\">");
                }
                out.println(i);
                if(i==ipage) {
                    out.println("</span>");
                }else {
                    out.println("</a>");
                }
            }
            if (ipage != paginas)
            {
                out.println("<a class=\"swb-pdcst-sk\" href=\""+nextURL+"\">Siguiente</a>");
            }
            out.println("</div>");
            //////////////////////
        }
        else
        {
            out.println("<p></p>");
        }
        out.println("</div>");
    }
    
    private void renderPodcastList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
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
        for(int i=0; i<getPageSize() && resources.hasNext(); i++)
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
                out.print("      <a href=\""+contentURL+"?suri="+audiofile.getEncodedURI()+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("listen")+"</a>");
                out.println("  </p>");
                out.println("  <p class=\"swb-pdcst-descargar\"><a href=\""+directURL.setParameter("suri", audiofile.getURI())+"\" title=\""+(audiofile.getDisplayTitle(lang)==null?audiofile.getTitle():audiofile.getDisplayTitle(lang))+"\">"+paramRequest.getLocaleString("download")+"</a><span>"+paramRequest.getLocaleString("format")+"&nbsp;"+audiofile.getExtension()+"</span></p>");
                out.println("  </div>");
                out.println(" </div>");
                out.println("</li>");
            }
        }
    }
    
    @Override
    public int getPageSize()
    {
        //int pageSize = getSemanticObject().getIntProperty(audiopdcst_pageSize);
        int pageSize = super.getPageSize();
        if(pageSize<1) {            
            //getSemanticObject().setIntProperty(audiopdcst_pageSize, 5);
            super.setPageSize(5);
        }
        //return getSemanticObject().getIntProperty(audiopdcst_pageSize);
        return super.getPageSize();
    }
    
    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        if(suri==null) {
            return;
        }
        try {
            suri = URLDecoder.decode(suri, "UTF-8");
        }catch(Exception unsage) {
            suri = null;
        }
        AudioFile audiofile = null;
        try {
            audiofile = (AudioFile)SemanticObject.createSemanticObject(suri).createGenericInstance();
        }catch(Exception e) {
            log.error(e);
            return;
        }
        if(audiofile.isValid()) {
            String filename = audiofile.getFilename();
            if(filename.endsWith(".htm")) {
                response.setContentType("text/html");
            }else if (filename.endsWith(".zip")) {
                response.setContentType("application/zip");
            }else if (filename.endsWith(".mp3")) {
                response.setContentType("audio/mpeg");
            }

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
        String suri = request.getParameter("suri");
        if(suri==null) {
            return;
        }
        try {
            suri = URLDecoder.decode(suri, "UTF-8");
        }catch(Exception e) {
            suri = null;
        }
        AudioFile audiofile = null;
        try {
            audiofile = (AudioFile)SemanticObject.createSemanticObject(suri).createGenericInstance();
        }catch(Exception e) {
            log.error(e);
            return;
        }    
        if(audiofile.isValid()) {
            DecimalFormat decf = new DecimalFormat("###");
            final String rid = base.getWebSiteId()+"_"+base.getId()+"_"+audiofile.getId();
//            if(session.getAttribute(rid)!=null)
//               out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
//            else {
                synchronized(audiofile) {
                    audiofile.setRank(audiofile.getRank()+1);
                }
                session.setAttribute(rid,rid);
                try {
                    out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;"+paramRequest.getLocaleString("like")+"</p>");
                }catch(SWBResourceException swbe) {
                    out.println("  <p>"+decf.format(audiofile.getRank())+"&nbsp;Me gusta</p>");
                }
//            }
            out.flush();
            out.close();
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

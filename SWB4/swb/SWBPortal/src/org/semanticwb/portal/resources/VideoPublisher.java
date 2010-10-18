/**  
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
 **/

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoPublisher.
 */
public class VideoPublisher extends GenericAdmResource {
    /** The log. */
    private static Logger log = SWBUtils.getLogger(VideoPublisher.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        String code = base.getAttribute("code");
        if( code!=null && !code.trim().equals("") ) {
            out.println(code);
        }else {
            final String webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";

            String clsid, codebase;
            String filename = webWorkPath + base.getAttribute("video");
            String width = base.getAttribute("width", "320");
            String height = base.getAttribute("height", "240");
            String align = base.getAttribute("align");
            String standby = base.getAttribute("standby", "Loading Microsoft Windows Media Player components...");
            String automatic = base.getAttribute("automatic");
            String controls = base.getAttribute("controls");
            String loop = base.getAttribute("loop");

            if(filename.endsWith(".flv")) {
                clsid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
                codebase = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0";
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" align=\""+align+"\">");
                out.println("  <param name=\"allowScriptAccess\" value=\"sameDomain\">");
                out.println("  <param name=\"movie\" value=\""+SWBPlatform.getContextPath()+"/swbutil/FlvPlayer.swf?&flv="+filename+"\">");
                out.println("  <param name=\"quality\" value=\"high\">");
                out.println("  <param name=\"bgcolor\" value=\"#ffffff\">");
                out.println("  <param name=\"allowFullScreen\" value=\"true\">");
                out.println("  <embed src=\""+SWBPlatform.getContextPath()+"/swbadmin/FlvPlayer.swf?&flv="+filename+"\" quality=\"high\" align=\""+align+"\" bgcolor=\"#ffffff\" width=\""+width+"\" height=\""+height+"\" allowFullScreen=\"true\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\">");
                out.println(" </embed>");
                out.println("</object>");
            }else if (filename.endsWith(".swf")) {
                clsid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
                codebase = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0";

                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" align=\""+align+"\">");
                out.println("  <param name=\"allowScriptAccess\" value=\"sameDomain\"/>");
                out.println("  <param name=\"movie\" value=\""+filename+"\"/>");
                out.println("  <param name=\"quality\" value=\"high\"/>");
                out.println("  <param name=\"bgcolor\" value=\"#ffffff\"/>");
                out.println("  <embed src=\""+filename+"\" quality=\"high\" bgcolor=\"#ffffff\" width=\""+width+"\" height=\""+height+"\" name=\""+base.getAttribute("video")+"\" align=\""+align+"\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\"></embed>");
                out.println("</object>");

            }else if (filename.endsWith(".swf")) {
                clsid = "";
                codebase = "";
            }else if (filename.endsWith(".avi")) {
                clsid = "clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95";
                codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" standby=\""+standby+"\" type=\"application/x-oleobject\">");
                out.println("  <param name=\"fileName\" value=\""+filename+"\"/>");
                out.println("  <param name=\"animationatStart\" value=\"true\"/>");
                out.println("  <param name=\"transparentatStart\" value=\"true\"/>");
                out.println("  <param name=\"autoStart\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"showControls\" value=\""+controls+"\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.print("  <embed type=\"application/x-mplayer2\" pluginspage=\"http://microsoft.com/windows/mediaplayer/en/download/\" id=\"mediaPlayer\" name=\"mediaPlayer\" ");
                out.print("        displaysize=\"4\" autosize=\"-1\" bgcolor=\"darkblue\" showcontrols=\""+controls+"\" showtracker=\"-1\" showdisplay=\"0\" showstatusbar=\"-1\" ");
                out.println("        videoborder3d=\"-1\" width=\""+width+"\" height=\""+height+"\" src=\""+filename+"\" autostart=\""+automatic+"\" designtimesp=\"5311\" loop=\""+loop+"\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                out.println("</table>");
            }else if (filename.endsWith(".wmv")) {
                clsid = "clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95";
                codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" standby=\""+standby+"\" type=\"application/x-oleobject\">");
                out.println("  <param name=\"fileName\" value=\""+filename+"\"/>");
                out.println("  <param name=\"animationatStart\" value=\"true\"/>");
                out.println("  <param name=\"transparentatStart\" value=\"true\"/>");
                out.println("  <param name=\"autoStart\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"showControls\" value=\""+controls+"\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.print("  <embed type=\"application/x-mplayer2\" pluginspage=\"http://microsoft.com/windows/mediaplayer/en/download/\" id=\"mediaPlayer\" name=\"mediaPlayer\" ");
                out.print("        displaysize=\"4\" autosize=\"-1\" bgcolor=\"darkblue\" showcontrols=\""+controls+"\" showtracker=\"-1\" showdisplay=\"0\" showstatusbar=\"-1\" ");
                out.println("        videoborder3d=\"-1\" width=\""+width+"\" height=\""+height+"\" src=\""+filename+"\" autostart=\""+automatic+"\" designtimesp=\"5311\" loop=\""+loop+"\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                out.println("</table>");
            }else if (filename.endsWith(".mov")) {
                clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\">");
                out.println("  <param name=\"src\" value=\""+filename+"\"/>");
                out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"controller\" value=\""+controls+"\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\""+height+"\" autoplay=\""+automatic+"\" controller=\""+controls+"\" loop=\""+loop+"\" pluginspage=\"http://www.apple.com/quicktime/download/\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                out.println("</table>");
            }else if (filename.endsWith(".rm")) {
                clsid = "clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA";
                codebase = "";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" width=\""+width+"\" height=\""+height+"\">");
                out.println("  <param name=\"src\" value=\""+filename+"\"/>");
                out.println("  <param name=\"autostart\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"controls\" value=\"imagewindow\"/>");
                out.println("  <param name=\"console\" value=\"video\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\""+height+"\" loop=\""+loop+"\" type=\"audio/x-pn-realaudio-plugin\" controls=\"imagewindow\" console=\"video\" autostart=\""+automatic+"\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                // control panel... begin
                out.println("<tr><td>");
                out.println("<object id=\"rmctrl_"+base.getId()+"\" classid=\""+clsid+"\" width=\""+width+"\" height=\"30\">");
                out.println("  <param name=\"src\" value=\""+filename+"\"/>");
                out.println("  <param name=\"autostart\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"controls\" value=\"ControlPanel\"/>");
                out.println("  <param name=\"console\" value=\"video\"/>");
                out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\"30\" controls=\"ControlPanel\" type=\"audio/x-pn-realaudio-plugin\" console=\"video\" autostart=\""+automatic+"\">");
                out.println("  </embed>");
                //<a href=\"Real_Media.rm\" style=\"font-size: 85%;\" target=\"_blank\">Launch in external player</a>
                out.println("</object>");
                out.println("</td></tr>");
                // control panel... end
                out.println("</table>");
            }else if (filename.endsWith(".mpg")) {
                clsid = "clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95";
                codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" standby=\""+standby+"\" type=\"application/x-oleobject\">");
                out.println("  <param name=\"fileName\" value=\""+filename+"\"/>");
                out.println("  <param name=\"animationatStart\" value=\"true\"/>");
                out.println("  <param name=\"transparentatStart\" value=\"true\"/>");
                out.println("  <param name=\"autoStart\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"showControls\" value=\""+controls+"\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.print("  <embed type=\"application/x-mplayer2\" pluginspage=\"http://microsoft.com/windows/mediaplayer/en/download/\" id=\"mediaPlayer\" name=\"mediaPlayer\" ");
                out.print("        displaysize=\"4\" autosize=\"-1\" bgcolor=\"darkblue\" showcontrols=\""+controls+"\" showtracker=\"-1\" showdisplay=\"0\" showstatusbar=\"-1\" ");
                out.println("        videoborder3d=\"-1\" width=\""+width+"\" height=\""+height+"\" src=\""+filename+"\" autostart=\""+automatic+"\" designtimesp=\"5311\" loop=\""+loop+"\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                out.println("</table>");
            }else if (filename.endsWith(".mp2")) {
                out.print("Formato no soportado.");
            }else if (filename.endsWith(".mp4")) {
                clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
                out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
                out.println("<tr><td>");
                out.println("<object id=\"video_"+base.getId()+"\" class=\"swb-video\" classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\">");
                out.println("  <param name=\"src\" value=\""+filename+"\"/>");
                out.println("  <param name=\"autoplay\" value=\""+automatic+"\"/>");
                out.println("  <param name=\"controller\" value=\""+controls+"\"/>");
                out.println("  <param name=\"loop\" value=\""+loop+"\"/>");
                out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\""+height+"\" autoplay=\""+automatic+"\" controller=\""+controls+"\" loop=\""+loop+"\" pluginspage=\"http://www.apple.com/quicktime/download/\">");
                out.println("  </embed>");
                out.println("</object>");
                out.println("</td></tr>");
                out.println("</table>");
            }else {
                out.print("Formato no soportado.");
            }
        }
        out.flush();
        out.close();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(HttpServletRequest, SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base  = getResourceBase();
        final String code = request.getParameter("video_code");
        base.setAttribute("code", code);
        base.setAttribute("preview", getPreview(code));
        System.out.println("code="+code);
        System.out.println("preview="+getPreview(code));
        try {
            base.updateAttributesToDB();
        }catch(Exception e) {
            log.error(e);
        }
    }

    /**
     * Gets the preview.
     * 
     * @param code the code
     * @return the preview
     */
    private String getPreview(final String code) {
        String ret = null;
        //******************  is YouTube  ***********************************
        final String pre = "http://www.youtube.com/v/";
        final String post = "\">";
        int s = code.indexOf(pre);
        if (s >= 0)
        {
            int f = code.indexOf(post, s);
            if (f > s)
            {
                ret = code.substring(s + pre.length(), f);
                int a = ret.indexOf('&');
                if (a > 0)
                {
                    ret = ret.substring(0, a);
                }
            }
        }
        if (ret != null)
        {
            ret = "http://i.ytimg.com/vi/" + ret + "/default.jpg";
        }
        return ret;
    }
}


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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;

public class VideoPublisher extends GenericAdmResource {
    
    private static Logger log = SWBUtils.getLogger(VideoPublisher.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
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

        if(filename.indexOf(".flv")>-1) {
            clsid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
            codebase = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0";
            out.println("<object classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" align=\""+align+"\">");
            out.println("  <param name=\"allowScriptAccess\" value=\"sameDomain\">");
            out.println("  <param name=\"movie\" value=\""+SWBPlatform.getContextPath()+"/swbutil/FlvPlayer.swf?&flv="+filename+"\">");
            out.println("  <param name=\"quality\" value=\"high\">");
            out.println("  <param name=\"bgcolor\" value=\"#ffffff\">");
            out.println("  <param name=\"allowFullScreen\" value=\"true\">");
            out.println("  <embed src=\""+SWBPlatform.getContextPath()+"/swbutil/FlvPlayer.swf?&flv="+filename+"\" quality=\"high\" align=\""+align+"\" bgcolor=\"#ffffff\" width=\""+width+"\" height=\""+height+"\" allowFullScreen=\"true\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\">");
            out.println(" </embed>");
            out.println("</object>");
        }else if (filename.indexOf(".swf")>-1) {
            clsid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
            codebase = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0";

            out.println("<object classid=\""+clsid+"\" codebase=\""+codebase+"\" width=\""+width+"\" height=\""+height+"\" id=\""+base.getAttribute("video")+"\" align=\""+align+"\">");
            out.println("  <param name=\"allowScriptAccess\" value=\"sameDomain\"/>");
            out.println("  <param name=\"movie\" value=\""+filename+"\"/>");
            out.println("  <param name=\"quality\" value=\"high\"/>");
            out.println("  <param name=\"bgcolor\" value=\"#ffffff\"/>");
            out.println("  <embed src=\""+filename+"\" quality=\"high\" bgcolor=\"#ffffff\" width=\""+width+"\" height=\""+height+"\" name=\""+base.getAttribute("video")+"\" align=\""+align+"\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\"></embed>");
            out.println("</object>");

        }else if (filename.indexOf(".swf")>-1) {
            clsid = "";
            codebase = "";
        }else if (filename.indexOf(".avi")>-1) {
            clsid = "clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95";
            codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
            //begin embedded AVI file... -->
            out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
            out.println("<tr><td>");
            out.println("<object id=\"mediaPlayer\" width=\""+width+"\" height=\""+height+"\" classid=\""+clsid+"\" codebase=\""+codebase+"\" standby=\""+standby+"\" type=\"application/x-oleobject\">");
            out.println("<param name=\"fileName\" value=\""+filename+"\"/>");
            out.println("<param name=\"animationatStart\" value=\"true\"/>");
            out.println("<param name=\"transparentatStart\" value=\"true\"/>");
            out.println("<param name=\"autoStart\" value=\"true\"/>");
            out.println("<param name=\"showControls\" value=\"true\"/>");
            out.println("<param name=\"loop\" value=\"true\"/>");
            out.print("<embed type=\"application/x-mplayer2\" pluginspage=\"http://microsoft.com/windows/mediaplayer/en/download/\" id=\"mediaPlayer\" name=\"mediaPlayer\" ");
            out.print("        displaysize=\"4\" autosize=\"-1\" bgcolor=\"darkblue\" showcontrols=\"true\" showtracker=\"-1\" showdisplay=\"0\" showstatusbar=\"-1\" ");
            out.println("        videoborder3d=\"-1\" width=\""+width+"\" height=\""+height+"\" src=\""+filename+"\" autostart=\"true\" designtimesp=\"5311\" loop=\"true\">");
            out.println("</embed>");
            out.println("</object>");
            out.println("</td></tr>");
            // end embedded WindowsMedia file -->
            out.println("</table>");
        }else if (filename.indexOf(".wmv")>-1) {
            clsid = "clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95";
            codebase = "http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
            //begin embedded AVI file... -->
            out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
            out.println("<tr><td>");
            out.println("<object id=\"mediaPlayer\" width=\""+width+"\" height=\""+height+"\" classid=\""+clsid+"\" codebase=\""+codebase+"\" standby=\""+standby+"\" type=\"application/x-oleobject\">");
            out.println("  <param name=\"fileName\" value=\""+filename+"\"/>");
            out.println("  <param name=\"animationatStart\" value=\"true\"/>");
            out.println("  <param name=\"transparentatStart\" value=\"true\"/>");
            out.println("  <param name=\"autoStart\" value=\"true\"/>");
            out.println("  <param name=\"showControls\" value=\"true\"/>");
            out.println("  <param name=\"loop\" value=\"true\"/>");
            out.print("  <embed type=\"application/x-mplayer2\" pluginspage=\"http://microsoft.com/windows/mediaplayer/en/download/\" id=\"mediaPlayer\" name=\"mediaPlayer\" ");
            out.print("        displaysize=\"4\" autosize=\"-1\" bgcolor=\"darkblue\" showcontrols=\"true\" showtracker=\"-1\" showdisplay=\"0\" showstatusbar=\"-1\" ");
            out.println("        videoborder3d=\"-1\" width=\""+width+"\" height=\""+height+"\" src=\""+filename+"\" autostart=\"true\" designtimesp=\"5311\" loop=\"true\">");
            out.println("  </embed>");
            out.println("</object>");
            out.println("</td></tr>");
            // end embedded WindowsMedia file -->
            out.println("</table>");
        }else if (filename.indexOf(".mov")>-1) {
            clsid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
            codebase = "http://www.apple.com/qtactivex/qtplugin.cab";
            // begin embedded QuickTime file... -->
            out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
            // begin video window... -->
            out.println("<tr><td>");
            out.println("<object classid=\""+clsid+"\" width=\"320\" height=\"255\" codebase=\""+codebase+"\">");
            out.println("  <param name=\"src\" value=\""+filename+"\"/>");
            out.println("  <param name=\"autoplay\" value=\"true\"/>");
            out.println("  <param name=\"controller\" value=\"true\"/>");
            out.println("  <param name=\"loop\" value=\"true\"/>");
            out.println("  <embed src=\""+filename+"\" width=\"320\" height=\"255\" autoplay=\"true\" controller=\"true\" loop=\"true\" pluginspage=\"http://www.apple.com/quicktime/download/\">");
            out.println("  </embed>");
            out.println("</object>");
            out.println("</td></tr>");
            // end embedded QuickTime file -->
            out.println("</table>");
        }else if (filename.indexOf(".rm")>-1) {
            clsid = "clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA";
            codebase = "";
            out.println("<table border=\"0\" cellpadding=\"0\" align=\"left\">");
            // begin video window... -->
            out.println("<tr><td>");
            out.println("<object id=\"rvocx\" classid=\""+clsid+"\" width=\""+width+"\" height=\""+height+"\">");
            out.println("  <param name=\"src\" value=\""+filename+"\"/>");
            out.println("  <param name=\"autostart\" value=\"true\"/>");
            out.println("  <param name=\"controls\" value=\"imagewindow\"/>");
            out.println("  <param name=\"console\" value=\"video\"/>");
            out.println("  <param name=\"loop\" value=\"true\"/>");
            out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\""+height+"\" loop=\"true\" type=\"audio/x-pn-realaudio-plugin\" controls=\"imagewindow\" console=\"video\" autostart=\"true\">");
            out.println("  </embed>");
            out.println("</object>");
            out.println("</td></tr>");
            // end video window -->
            // begin control panel... -->
            out.println("<tr><td>");
            out.println("<object id=\"rvocx\" classid=\""+clsid+"\" width=\""+width+"\" height=\"30\">");
            out.println("  <param name=\"src\" value=\""+filename+"\"/>");
            out.println("  <param name=\"autostart\" value=\"true\"/>");
            out.println("  <param name=\"controls\" value=\"ControlPanel\"/>");
            out.println("  <param name=\"console\" value=\"video\"/>");
            out.println("  <embed src=\""+filename+"\" width=\""+width+"\" height=\"30\" controls=\"ControlPanel\" type=\"audio/x-pn-realaudio-plugin\" console=\"video\" autostart=\"true\">");
            out.println("  </embed>");
            //<a href=\"Real_Media.rm\" style=\"font-size: 85%;\" target=\"_blank\">Launch in external player</a>
            out.println("</object>");
            out.println("</td></tr>");
            // end control panel -->
            // end embedded RealMedia file -->
            out.println("</table>");
        }else if (filename.indexOf(".mp1")>-1) {
            clsid = "";
            codebase = "";
        }else if (filename.indexOf(".mp2")>-1) {
            clsid = "";
            codebase = "";
        }else if (filename.indexOf(".mp4")>-1) {
            clsid = "";
            codebase = "";
        }else {
            clsid = "";
            codebase = "";
        }
    }

}

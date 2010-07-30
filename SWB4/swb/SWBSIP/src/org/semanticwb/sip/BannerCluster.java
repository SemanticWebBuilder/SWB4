package org.semanticwb.sip;

import java.io.*;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.model.base.ResourceBase;
import org.semanticwb.portal.api.*;

public class BannerCluster extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(BannerCluster.class);
    private static final String webWorkPath = SWBPortal.getWebWorkPath();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = paramRequest.getResourceBase();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();

        StringBuilder b = new StringBuilder();;
        int i = 0;
        int w;
        try {
            w = Integer.parseInt(base.getAttribute("width", "143"));
        }catch(NumberFormatException nfe) {
            w = 143;
        }
        int h;
        try {
            h = Integer.parseInt(base.getAttribute("height", "208 "));
        }catch(NumberFormatException nfe) {
            h = 208;
        }

        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("    dojo.require('dojox.fx');");
        out.println("    function expande(domId) {");
        out.println((new StringBuilder()).append(" var a=dojox.fx.wipeTo( {node:domId, duration:200, height:").append(h - 5).append("} );").toString());
        out.println("     a.play();");
        out.println("   }");
        out.println("    function collapse(domId) {");
        out.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:40} );");
        out.println("      a.play();");
        out.println("    }");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"swb-banner-cluster\">");
        //out.println("<div class=\"banner-cluster-hldr\" style=\"width:"+w+"px; height:"+h+"px;\">");

        String cluster = base.getAttribute("cluster", "carrusel");
        Iterator<ResourceType> itResourceTypes = paramRequest.getWebPage().getWebSite().listResourceTypes();
        while( itResourceTypes.hasNext() ) {
            ResourceType rt = itResourceTypes.next();
            if( rt.getId().equalsIgnoreCase("Banner") ) {
                Iterator<ResourceSubType> itResSubTypes = rt.listSubTypes();
                while( itResSubTypes.hasNext() ) {
                    ResourceSubType st = itResSubTypes.next();
                    if( st.getId().equalsIgnoreCase(cluster) ) {
                        Iterator<Resource> itRes = ResourceBase.ClassMgr.listResourceByResourceSubType(st, paramRequest.getWebPage().getWebSite());
                        while( itRes.hasNext() ) {
                            Resource r = itRes.next();
                            if( r.isActive()&&user.haveAccess(r) ) {
                                String title = r.getDisplayTitle(lang);
                                String desc = r.getDisplayDescription(lang);
                                String url = r.getAttribute("url");
                                String img = (new StringBuilder()).append(webWorkPath).append(r.getWorkPath()).append("/").append(r.getAttribute("img")).toString();
                                String alt = r.getAttribute("alt", title);
                                b.append("<div class=\"swb-banner-cluster-ci\" onclick=\"window.location.href='"+url+"'\" >");
                                b.append("  <div class=\"swb-cluster-img\"><img src=\""+img+"\" alt=\""+alt+"\" width=\""+w+"\" height=\""+h+"\" /></div>");
                                b.append("  <div class=\"swb-cluster-despliega\" id=\"r"+base.getId()+"_"+(i++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
                                b.append("    <p class=\"swb-cluster-titulo\">"+title+"</p>");
                                b.append("    <p>&nbsp;</p>");
                                b.append("    <p>"+desc+"</p>");
                                b.append("  </div>");
                                b.append("</div>");
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        System.out.println("i="+i);
        int wi = (w*i)+(i*13);
        System.out.println("width="+wi);
        out.println("<div class=\"banner-cluster-hldr\" style=\"width:"+wi+"px; height:"+h+"px;\">");
        
        out.println(b.toString());
        out.println("</div>\n");
        out.println("</div>\n");
        out.flush();
        out.close();
    }
}

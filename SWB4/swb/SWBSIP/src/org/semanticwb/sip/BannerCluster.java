// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BannerCluster.java

package org.semanticwb.sip;

import java.io.*;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.model.base.ResourceBase;
import org.semanticwb.portal.api.*;

// Referenced classes of package org.semanticwb.sip:
//            PromoCluster

public class BannerCluster extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(BannerCluster.class);
    private static final String webWorkPath = SWBPortal.getWebWorkPath();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder out;
        Resource base = paramRequest.getResourceBase();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        out = new StringBuilder();
        int i = 0;
        String width = base.getAttribute("width", "143px");
        String height = base.getAttribute("height", "208px");
        int h;
        try
        {
            h = Integer.parseInt(height.replaceAll("\\D", ""));
        }
        catch(NumberFormatException nfe)
        {
            h = 100;
        }
        out.append("<script type=\"text/javascript\">\n");
        out.append("    dojo.require('dojox.fx');\n");
        out.append("    function expande(domId) {\n");
        out.append((new StringBuilder()).append("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:").append(h - 5).append("} );\n").toString());
        out.append("     a.play();\n");
        out.append("   }\n");
        out.append("    function collapse(domId) {\n");
        out.append("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:60} );\n");
        out.append("      a.play();\n");
        out.append("    }\n");
        out.append("</script>\n");

        out.append("<div class=\"swb-promo-cluster\">\n");
        out.append("<div id=\"promoHolder\" style=\"width:auto; height:175px;\">\n");

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
                                out.append("<div class=\"swb-promo-cluster-ci\" style=\"background:url("+img+")\" onclick=\"window.location.href='"+url+"'\"  >\n");
                                out.append("  <div class=\"swb-cluster-despliega\" id=\"r"+base.getId()+"_"+(i++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
                                out.append("    <p class=\"swb-cluster-titulo\">"+title+"</p>\n");
                                out.append("    <br />\n");
                                out.append("    <p>"+desc+"</p>");
                                out.append("  </div>\n");
                                out.append("</div>\n");
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        out.append("</div>\n");
        out.append("</div>\n");

        PrintWriter pw = response.getWriter();
        pw.println(out);
        pw.flush();
    }
}

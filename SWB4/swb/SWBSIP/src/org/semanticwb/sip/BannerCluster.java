package org.semanticwb.sip;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
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

        StringBuilder b = new StringBuilder();
        int i = 0;

        String width = base.getAttribute("width");
        int w;
        try {
            w = Integer.parseInt( width.replaceAll("\\D", "") );
            //w = Integer.parseInt(base.getAttribute("width", "143"));
        }catch(Exception e) {
            w = 143;
            width = Integer.toString(w);
        }
        String height = base.getAttribute("height");
        int h;
        try {
            h = Integer.parseInt( height.replaceAll("\\D", "") );
            //h = Integer.parseInt(base.getAttribute("height", "208 "));
        }catch(Exception e) {
            h = 208;
            height = Integer.toString(h);
        }
        
        int theight;
        try {
            theight = Integer.parseInt(base.getAttribute("theight","40"));
        }catch(Exception e) {
            theight = 40;
        }

        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("    dojo.require('dojox.fx');");
        out.println("    function expande(domId) {");
        out.println((new StringBuilder()).append(" var a=dojox.fx.wipeTo( {node:domId, duration:200, height:").append(h - 5).append("} );").toString());
        out.println("     a.play();");
        out.println("   }");
        out.println("    function collapse(domId) {");
        //out.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:40} );");
        out.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:"+theight+"} );");
        out.println("      a.play();");
        out.println("    }");
        out.println("-->");
        out.println("</script>");
        out.println("<h2 class=\"tituloBloque\">"+base.getDisplayTitle(lang)+" <span class=\"span_tituloBloque\">"+base.getDisplayDescription(lang)+"</span></h2>");
        out.println("<div class=\"swb-banner-cluster\">");
        String cluster = base.getAttribute("cluster", "carrusel");

        //TreeMap<Integer,Resource>banners = new TreeMap<Integer,Resource>( new BannerSortComparator());
        TreeSet<Resource>banners = new TreeSet<Resource>( new BannerSortComparator() );

        ResourceType rt = ResourceType.ClassMgr.getResourceType("Banner", paramRequest.getWebPage().getWebSite());

//        Iterator<ResourceType> itResourceTypes = paramRequest.getWebPage().getWebSite().listResourceTypes();
//        while( itResourceTypes.hasNext() ) {
//            ResourceType rt = itResourceTypes.next();
//            if( rt.getId().equalsIgnoreCase("Banner") ) {
                Iterator<ResourceSubType> itResSubTypes = rt.listSubTypes();
                while( itResSubTypes.hasNext() ) {
                    ResourceSubType st = itResSubTypes.next();
                    if( st.getId().equalsIgnoreCase(cluster) ) {
                        //Iterator<Resource> itRes = ResourceBase.ClassMgr.listResourceByResourceSubType(st, paramRequest.getWebPage().getWebSite());
                        Iterator<Resource> itRes = st.listResources();
                        while( itRes.hasNext() ) {
                            Resource r = itRes.next();
                            if( r.isActive() && r.isValid() && user.haveAccess(r) ) {
                                try {
                                    Integer.parseInt(r.getAttribute("index"));
                                }catch(NumberFormatException nfe) {
                                    System.out.println("error  **** "+nfe);
                                    r.setAttribute("index","0");
                                    try {
                                        r.updateAttributesToDB();
                                    }catch(SWBException swbe) {
                                        continue;
                                    }
                                }
                                banners.add(r);
                            }
                        }
                        break;
                    }
                }
//                break;
//            }
//        }

        Iterator<Resource> it = banners.iterator();
        while(it.hasNext()) {
            Resource r = it.next();
            String title = r.getDisplayTitle(lang)==null?"":r.getDisplayTitle(lang);
            String desc = r.getDisplayDescription(lang)==null?"":r.getDisplayDescription(lang);
            String url = r.getAttribute("url","#");
            String img = (new StringBuilder()).append(webWorkPath).append(r.getWorkPath()).append("/").append(r.getAttribute("img")).toString();
            String alt = r.getAttribute("alt", title);
            b.append("<div class=\"swb-banner-cluster-ci\">");
            b.append("  <div class=\"swb-cluster-img\">");
            b.append("    <a href=\""+url+"\">");
            b.append("      <img src=\""+img+"\" alt=\""+alt+"\" width=\""+width+"\" height=\""+height+"\" />");
            b.append("    </a>");
            b.append("  </div>");
            b.append("  <div class=\"swb-cluster-despliega\" id=\"r"+base.getId()+"_"+(i++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
            b.append("    <p class=\"swb-cluster-titulo\"><a href=\""+url+"\">"+title+"</a></p>");
            b.append("    <p>&nbsp;</p>");
            b.append("    <p class=\"swb-cluster-desc\"><a href=\""+url+"\">"+desc+"</a></p>");
            b.append("  </div>");
            b.append("</div>");
        }

        int wi = (w*i)+(i*13);
        out.println("<div class=\"banner-cluster-hldr\" style=\"width:"+wi+"px; height:"+h+"px;\">");
        out.println(b.toString());
        out.println("</div>\n");
        out.println("</div>\n");
        out.flush();
        out.close();
    }
}

class BannerSortComparator implements Comparator {
    public int compare ( Object o1, Object o2 ) {
        Resource r1 = (Resource)o1;
        Resource r2 = (Resource)o2;
        int i1 = Integer.parseInt(r1.getAttribute("index"));
        int i2 = Integer.parseInt(r2.getAttribute("index"));
        if( i2>i1 )
            return -1;
        else
            return 1;
    }

//    @Override
//    public boolean equals(Object o)   {
//        return false;
//    }
  }

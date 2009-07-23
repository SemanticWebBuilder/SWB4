// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GMapMarker.java

package org.semanticwb.portal.resources.demo;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

public class GMapMarker extends GenericAdmResource
{
private static Logger log = SWBUtils.getLogger(GMapMarker.class);
    public GMapMarker()
    {
    }

    @Override
    public void processAction(HttpServletRequest httpservletrequest, SWBActionResponse wbactionresponse)
        throws SWBResourceException, IOException
    {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest)
        throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String mark_lat = paramsRequest.getResourceBase().getAttribute("mark_lat");
        String mark_lng = paramsRequest.getResourceBase().getAttribute("mark_lng");
        String title = paramsRequest.getResourceBase().getTitle();
        String description = paramsRequest.getResourceBase().getDescription();
        String detail = paramsRequest.getResourceBase().getAttribute("detail", "null");
        out.println("    <script type=\"text/javascript\">");
        out.println((new StringBuilder()).append("        document.write('<b>'+String.fromCharCode(\"A\".charCodeAt(0) + map_mark_index)+'</b> <a href=\"javascript:myclick(' + this.map_mark_index + ')\">' + '").append(title).append("' + '</a><br>');").toString());
        out.println((new StringBuilder()).append("\t addMarker(").append(mark_lat).append(", ").append(mark_lng).append(",'").append(title).append("','").append(description).append("<br><br><center><a href=\"javascript:map.setZoom(17);\">Vista Calle</a> <a href=\"javascript:map.setZoom(13);\">Vista Colonia</a> <a href=\"javascript:map.setZoom(11);\">Vista Municipio</a><br><a href=\"javascript:showMap('+map_mark_index+');\">Vista Mapa</a></center>',").append(detail).append(");").toString());
        out.println("    </script>");
    }
}

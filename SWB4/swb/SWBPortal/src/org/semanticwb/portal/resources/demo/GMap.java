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
 
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GMap.java

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

public class GMap extends GenericAdmResource
{
private static Logger log = SWBUtils.getLogger(GMap.class);
    public GMap()
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
        String map_key = paramsRequest.getResourceBase().getAttribute("map_key", null);
        String map_width = paramsRequest.getResourceBase().getAttribute("map_width", "550");
        String map_height = paramsRequest.getResourceBase().getAttribute("map_height", "400");
        String map_type = paramsRequest.getResourceBase().getAttribute("map_type", "G_NORMAL_MAP");
        String map_lat = paramsRequest.getResourceBase().getAttribute("map_lat", "19.49419516889648");
        String map_lng = paramsRequest.getResourceBase().getAttribute("map_lng", "-99.04441952705383");
        String map_zoom = paramsRequest.getResourceBase().getAttribute("map_zoom", "13");
        String glmcontrol = paramsRequest.getResourceBase().getAttribute("glmcontrol", "true");
        String gmtcontrol = paramsRequest.getResourceBase().getAttribute("gmtcontrol", "true");
        String overmap = paramsRequest.getResourceBase().getAttribute("overmap", "true");
        String overmap_x = paramsRequest.getResourceBase().getAttribute("overmap_x", null);
        String overmap_y = paramsRequest.getResourceBase().getAttribute("overmap_y", null);
        String overmap_width = paramsRequest.getResourceBase().getAttribute("overmap_width", "150");
        String overmap_height = paramsRequest.getResourceBase().getAttribute("overmap_height", "150");
        String overmap_type = paramsRequest.getResourceBase().getAttribute("overmap_type", "G_NORMAL_MAP");
        if(!map_width.endsWith("%") && !map_width.endsWith("px"))
            map_width = (new StringBuilder()).append(map_width).append("px").toString();
        if(!map_height.endsWith("%") && !map_height.endsWith("px"))
            map_height = (new StringBuilder()).append(map_height).append("px").toString();
        int owidth = 150;
        int oheight = 150;
        try
        {
            owidth = Integer.parseInt(overmap_width);
            oheight = Integer.parseInt(overmap_height);
        }
        catch(Exception e)
        {
            log.error(e);
        }
        out.println((new StringBuilder()).append("    <script src=\"http://maps.google.com/maps?file=api&amp;oe=ISO8859-1&amp;v=2&amp;key=").append(map_key).append("\" type=\"text/javascript\"></script>").toString());
        out.println("    <div id=\"map\" style=\"width: 0px; height: 0px\"></div>");
        out.println("    <script type=\"text/javascript\">");
        out.println("    //<![CDATA[");
        out.println("    var map;");
        out.println("    var ovmap;");
        out.println("    this.map_markers=[];");
        out.println("    this.map_mark_index=0;");
        out.println("    function load() {");
        out.println("      if (GBrowserIsCompatible()) {");
        out.println("        var m = document.getElementById(\"map\");");
        out.println((new StringBuilder()).append("        m.style.height = \"").append(map_height).append("\";").toString());
        out.println((new StringBuilder()).append("        m.style.width = \"").append(map_width).append("\";").toString());
        out.println("        map = new GMap2(document.getElementById(\"map\"));");
        if(glmcontrol.equals("true"))
            out.println("        map.addControl(new GLargeMapControl());");
        if(gmtcontrol.equals("true"))
            out.println("        map.addControl(new GMapTypeControl());");
        out.println((new StringBuilder()).append("        map.setCenter(new GLatLng(").append(map_lat).append(", ").append(map_lng).append("), ").append(map_zoom).append(");").toString());
        out.println((new StringBuilder()).append("        map.setMapType(").append(map_type).append(");").toString());
        out.println("        for(var x=0;x<this.map_mark_index;x++)");
        out.println("        {");
        out.println("           map.addOverlay(this.map_markers[x]);");
        out.println("        }");
        out.println("");
        if(overmap.equals("true"))
        {
            out.println((new StringBuilder()).append("        var ovcontrol = new GOverviewMapControl(new GSize(").append(owidth).append(",").append(oheight).append(")); ").toString());
            out.println("        map.addControl(ovcontrol);");
            out.println("        setTimeout(\"positionOverview()\",1);");
            out.println("        ovmap = ovcontrol.getOverviewMap();");
            out.println((new StringBuilder()).append("        setTimeout(\"ovmap.setMapType(").append(overmap_type).append(");\",1);").toString());
        }
        out.println("      }");
        out.println("    }");
        out.println("    window.onload=load;");
        out.println("    window.onunload=GUnload;");
        out.println("    function addMarker(lon, lat, name, html,infoUrl) {");
        out.println("        var point = new GLatLng(lon,lat);");
        out.println("        return addMarker2(point, name, html,infoUrl,false);");
        out.println("    }");
        out.println("    function addMarker2(point, name, html,infoUrl, drag) {");
        out.println("        var letter = String.fromCharCode(\"A\".charCodeAt(0) + map_mark_index);");
        out.println("        var myIcon = new GIcon(G_DEFAULT_ICON, \"http://www.google.com/mapfiles/marker\" + letter + \".png\");");
        out.println("        var marker = new GMarker(point, {draggable:drag,icon:myIcon,title:name});");
        out.println("        GEvent.addListener(marker, \"click\", function() {");
        out.println("\t      if(infoUrl!=null)");
        out.println("                 marker.openInfoWindowHtml(html,{maxUrl:infoUrl});");
        out.println("             else");
        out.println("                 marker.openInfoWindowHtml(html);");
        out.println("         });");
        out.println("        this.map_markers[this.map_mark_index] = marker;");
        out.println("        this.map_mark_index++;");
        out.println("        return marker;");
        out.println("    }");
        out.println("    function myclick(i) {");
        out.println("        GEvent.trigger(this.map_markers[i], \"click\");");
        out.println("    }");
        out.println("    function showMap(i) {");
        out.println("        this.map_markers[i].showMapBlowup(16, G_NORMAL_MAP);");
        out.println("    }");
        out.println("    function positionOverview() {");
        out.println("        var omap=document.getElementById(\"map_overview\");");
        if(overmap_x != null)
            out.println((new StringBuilder()).append("        omap.style.left = \"").append(overmap_x).append("px\";").toString());
        if(overmap_y != null)
            out.println((new StringBuilder()).append("        omap.style.top = \"").append(overmap_y).append("px\";").toString());
        out.println("        omap.firstChild.style.border = \"1px solid gray\";");
        out.println("        omap.firstChild.firstChild.style.left=\"3px\";");
        out.println("        omap.firstChild.firstChild.style.top=\"3px\";");
        out.println((new StringBuilder()).append("        omap.firstChild.firstChild.style.width=\"").append(owidth - 8).append("px\";").toString());
        out.println((new StringBuilder()).append("        omap.firstChild.firstChild.style.height=\"").append(oheight - 8).append("px\";").toString());
        out.println("    }");
        out.println("    //]]>");
        out.println("    </script>");
    }
}

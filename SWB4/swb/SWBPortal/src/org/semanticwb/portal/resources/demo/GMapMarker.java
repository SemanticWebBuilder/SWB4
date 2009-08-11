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

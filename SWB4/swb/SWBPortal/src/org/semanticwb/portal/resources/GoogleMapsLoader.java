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
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * Recurso administrador de google maps para WebBuilder.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class GoogleMapsLoader extends GenericAdmResource {

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Assert location info
        String info = request.getParameter("info");
        if (info == null || info.equals("")) {
            info = "";
        }

        //Assert location latitude
        String latitude = request.getParameter("lat");
        if (latitude == null || latitude.equals("") || latitude.equals("null")) {
            latitude = "19.432216";
        }

        //Assert location longitude
        String longitude = request.getParameter("long");
        if (longitude == null || longitude.equals("") || longitude.equals("null")) {
            longitude = "-99.131076";
        }

        //Assert location homepage
        String wikiUrl = request.getParameter("wikiUrl");
        if (wikiUrl == null || wikiUrl.equals("") || wikiUrl.equals("null")) {
            wikiUrl = "#";
        }

        sbf.append("<script src=\"http://maps.google.com/maps?file=api&v=2&sensor=false&key=" + getResourceBase().getAttribute("mapKey") + "\"" +
                  " type=\"text/javascript\"></script>");

        sbf.append("<table>\n" +
                   "  <tr>\n" +
                   "    <td width=" + getResourceBase().getAttribute("mapWidth","300") + ">\n" +
                   "      <div id=\"" + getResourceBase().getId() + "/map\" style=\"width:" + 
                   getResourceBase().getAttribute("mapWidth","300") + "px; height:" +
                   getResourceBase().getAttribute("mapHeight","400")+ "px;\"></div>\n" +
                   "    </td>\n" +
                   "  </tr>" +
                "</table>");
        
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  function load() {\n" +
                   "    if (GBrowserIsCompatible()) {\n" +
                   "      var map = new GMap2(document.getElementById('" + getResourceBase().getId() + "/map'));\n" +
                   "      map.setCenter(new GLatLng(" + latitude + ", "+ longitude +"), 11);\n" +                   
                   "      map.addControl(new GLargeMapControl());\n" +
                   "      map.addControl(new GScaleControl());\n" +
                   "      map.addControl(new GMapTypeControl());\n" +
                   "      var punto = new GLatLng("+ latitude + "," + longitude + ");\n" +
                   "      var marcador = new GMarker(punto);\n" +
                   "      map.addOverlay(marcador);\n");
                   if (!info.equals("")) {
                       if (!wikiUrl.equals("#")) {
                            sbf.append("      marcador.openInfoWindowHtml('<p>" + info + "</p><hr>" +
                            paramRequest.getLocaleString("msgWP") +" <a href=\"" + wikiUrl + "\">" + wikiUrl + "</a>');\n");
                       } else {
                           sbf.append("      marcador.openInfoWindowHtml('<p>" + info + "</p>');\n");
                       }                       
                   } else {
                        if (!wikiUrl.equals("#")) {
                            sbf.append("      marcador.openInfoWindowHtml('"+
                            paramRequest.getLocaleString("msgWP") +" <a href=\"" + wikiUrl + "\">" + wikiUrl + "</a>');\n");
                        }
                   }
                   sbf.append("    }\n"+
                   "  }\n" +
                   "  load();\n"+
                   "</script>\n\n");
        out.println(sbf.toString());
    }
}

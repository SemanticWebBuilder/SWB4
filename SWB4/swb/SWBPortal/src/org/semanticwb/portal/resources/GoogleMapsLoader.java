/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Recurso administrador de google maps para WebBuilder.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class GoogleMapsLoader extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String latitude = request.getParameter("lat");
        if (latitude == null || latitude.equals("")) {
            latitude = "19.432216";
        }

        String longitude = request.getParameter("long");
        if (longitude == null || longitude.equals("")) {
            longitude = "-99.131076";
        }

        sbf.append("<script src=\"http://maps.google.com/maps?file=api&v=2&sensor=false&key=ABQIAAAAOJNnlv7XtimNAEXtmyrRcBSvBiWx47Sg3GqjNr4y4ou_VmvayxTQVYbEJHhu9GaB8tmww2NjFuv0vA\"" +
                  " type=\"text/javascript\"></script>");

        sbf.append("<table>\n" +
                   "  <tr>\n" +
                   "    <td width=300>\n" +
                   "      <div id=\"" + getResourceBase().getId() + "/map\" style=\"width:400px; height:300px;\"></div>\n" +
                   "    </td>\n" +
                   "  </tr>" +
                "</table>");
        
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  function load() {\n" +
                   "    if (GBrowserIsCompatible()) {\n" +
                   "      var map = new GMap2(document.getElementById('" + getResourceBase().getId() + "/map'));\n" +
                   "      map.addControl(new GMapTypeControl());\n" +
                   "      map.addControl(new GLargeMapControl());\n" +
                        //"map.addControl(new GOverviewMapControl());\n" +
                   "      map.setCenter(new GLatLng(" + latitude + ", "+ longitude +"), 11);\n" +
                   "      map.setMapType(G_HYBRID_TYPE);\n" +

//                        "function addtag(point, address) {"+
//                        "var marker = new GMarker(point);"+
//                        "GEvent.addListener(marker, \"click\", function() { marker.openInfoWindowHtml(address); } );"+
//                        "return marker;" +
//                        "}" +

                   "      var point = map.getCenter();" +//new GPoint("+ latitude +","+ longitude +");" +
                        //"var address = '<a href=\"http://www.centrodemadagascar.com\">Web del Centro de Madagascar</a>';" +
                        //"var marker = addtag(point, address);" +
                   "      var marker = new GMarker(point);\n" +
                   "	  map.addOverlay(marker);\n" +
                   "    }\n"+
                   "  }\n" +
                   "  load();\n"+
                   "</script>\n\n");
        out.println(sbf.toString());
    }
}

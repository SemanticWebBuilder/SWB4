package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.http.HttpServletRequest;

public class GMap extends org.semanticwb.model.base.GMapBase {
    String key = SWBPlatform.getEnv("key/gmap", "");

    public GMap(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang) {
        SemanticProperty prop2  = Geolocalizable.swb_longitude;
        SemanticProperty prop3  = Geolocalizable.swb_geoStep;
        String           nombre = "";

        if (obj == null) {
            obj = new SemanticObject();
        } else {
            nombre = obj.getDisplayName(lang);
        }

//        boolean IPHONE = false;
//        boolean XHTML  = false;
//        boolean DOJO   = false;
        int     step   = obj.getIntProperty(prop3);

        if (0 == step) {
            step = 10;
        }

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else if (type.equals("dojo")) {
//            DOJO = true;
//        }

        StringBuffer   ret      = new StringBuffer();
//        String         name     = prop.getName();
//        String         label    = prop.getDisplayName(lang);
//        SemanticObject sobj     = prop.getDisplayProperty();
//        boolean        required = prop.isRequired();
//        String         pmsg     = null;
//        String         imsg     = null;
//        boolean        disabled = false;
//
//        if (sobj != null) {
//            DisplayProperty dobj = new DisplayProperty(sobj);
//            pmsg     = dobj.getPromptMessage();
//            imsg     = dobj.getInvalidMessage();
//            disabled = dobj.isDisabled();
//        }

        String value = request.getParameter(prop.getName());

        if (value == null) {
            value = obj.getProperty(prop);
        }

        if (value == null) {
            value = "" + getInitLatitude();
            step  = getInitStep();
        }

        String value2 = request.getParameter(prop2.getName());

        if (value2 == null) {
            value2 = obj.getProperty(prop2);
        }

        if (value2 == null) {
            value2 = "" + getInitLongitude();
        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input type=\"text\" size=\"60\" id =\"gmap_address\" value=\"Introduce Calle,"
                       + " Colonia y Estado\" onKeyPress=\"if (event.which==13) {search(); return false;} \"/>\n");
            ret.append("<button onclick=\"search(); return false;\"> buscar </button>\n");
            ret.append("<input type=\"hidden\" id=\"latitude\" name=\"latitude\" value=\"" + value + "\"/>\n");
            ret.append("<input type=\"hidden\" id=\"longitude\" name=\"longitude\" value=\"" + value2 + "\"/>\n");
            ret.append("<input type=\"hidden\" id=\"geoStep\" name=\"geoStep\" value=\"" + step + "\"/>\n");
            ret.append("<div id=\"map_canvas\" style=\"width: 500px; height: 300px\"></div>\n");
            ret.append("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=" + key + "\"\n");
            ret.append("      type=\"text/javascript\"></script>\n\n");
            ret.append("<script type=\"text/javascript\" language=\"javascript\">\n\n");
            ret.append("function initialize() {\n");
            ret.append("    if (GBrowserIsCompatible()) {\n");
            ret.append("        var map = new GMap2(document.getElementById(\"map_canvas\"));\n");
            ret.append("        map.addControl(new GSmallMapControl());\n");
            ret.append("        map.addControl(new GMapTypeControl());\n");
            ret.append("        var center = new GLatLng(" + value + ", " + value2 + ");\n");
            ret.append("        geocoder = new GClientGeocoder();\n");
            ret.append("        setUpMap(map, center, " + step + ");\n");
            ret.append("    }\n");
            ret.append("}\n\n");
            ret.append("function setUpMap(map, center, step){\n");
            ret.append("    map.setCenter(center, step);\n");
            ret.append("    var marker = new GMarker(center, {draggable: true});\n");
            ret.append("    map.addOverlay(marker);\n");
            ret.append("    GEvent.addListener(marker, \"dragend\", function() {\n");
            ret.append("        var point = marker.getPoint();\n");
            ret.append("        map.panTo(point);\n");
            ret.append("        document.getElementById(\"latitude\").value = center.lat().toFixed(7);\n");
            ret.append("        document.getElementById(\"longitude\").value = center.lng().toFixed(7);\n");
            ret.append("        document.getElementById(\"geoStep\").value = map.getZoom();\n");
            ret.append("    });\n");
            ret.append("    GEvent.addListener(map, \"moveend\", function() {\n");
            ret.append("        map.clearOverlays();\n");
            ret.append("        var center = map.getCenter();\n");
            ret.append("        var marker = new GMarker(center, {draggable: true});\n");
            ret.append("        map.addOverlay(marker);\n");
            ret.append("        document.getElementById(\"latitude\").value = center.lat().toFixed(7);\n");
            ret.append("        document.getElementById(\"longitude\").value = center.lng().toFixed(7);\n");
            ret.append("        document.getElementById(\"geoStep\").value = map.getZoom();\n");
            ret.append("        GEvent.addListener(marker, \"dragend\", function() {\n");
            ret.append("            var point = marker.getPoint();\n");
            ret.append("            map.panTo(point);\n");
            ret.append("            document.getElementById(\"latitude\").value = center.lat().toFixed(7);\n");
            ret.append("            document.getElementById(\"longitude\").value = center.lng().toFixed(7);\n");
            ret.append("            document.getElementById(\"geoStep\").value = map.getZoom();\n");
            ret.append("    });\n");
            ret.append("    });\n");
            ret.append("}\n\n");
            ret.append("function search() {\n");
            ret.append("    var address = document.getElementById(\"gmap_address\").value;\n");
            ret.append("    var map = new GMap2(document.getElementById(\"map_canvas\"));\n");
            ret.append("    map.addControl(new GSmallMapControl());\n");
            ret.append("    map.addControl(new GMapTypeControl());\n");
            ret.append("    if (geocoder) {\n");
            ret.append("        geocoder.getLatLng(\n");
            ret.append("            address,\n");
            ret.append("            function(point) {\n");
            ret.append("                if (!point) {\n");
            ret.append("                    alert(address + \" no encontrada\");\n");
            ret.append("                } else {\n");
            ret.append("                    document.getElementById(\"latitude\").value = point.lat().toFixed(7);\n");
            ret.append("                    document.getElementById(\"longitude\").value = point.lng().toFixed(7);\n");
            ret.append("                    document.getElementById(\"geoStep\").value = 14;\n");
            ret.append("                    map.clearOverlays();\n");
            ret.append("                    setUpMap(map, point, 14);\n");
            ret.append("                }\n");
            ret.append("            });\n");
            ret.append("    }\n");
            ret.append("}\n");
            ret.append("initialize();\n");
            ret.append("    </script>\n");
        } else if (mode.equals("view")) {
            ret.append("<div id=\"map_canvas\" style=\"width: 500px; height: 300px\"></div>\n");
            ret.append("            <script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=" + key
                       + "\"\n");
            ret.append("            type=\"text/javascript\"></script>\n");
            ret.append("    <script type=\"text/javascript\">\n");
            ret.append("    function initialize() {\n");
            ret.append("      if (GBrowserIsCompatible()) {\n");
            ret.append("        var map = new GMap2(document.getElementById(\"map_canvas\"));\n");
            ret.append("            map.addControl(new GSmallMapControl());\n");
            ret.append("            map.addControl(new GMapTypeControl());\n");
            ret.append("        var center = new GLatLng(" + value + ", " + value2 + ");\n");
            ret.append("        map.setCenter(center, " + step + ");\n");

            // Add 10 markers to the map at random locations
            ret.append("        var marker = new GMarker(center, {draggable: false});\n");
            ret.append("        map.addOverlay(marker);\n");
            ret.append("        marker.openInfoWindow(   \n");
            ret.append("        document.createTextNode(\"" + nombre + "\"));\n");
            ret.append("      }\n");
            ret.append("    }\n");
            ret.append("initialize();\n");
            ret.append("    </script>\n");
        }

        // fin
        return ret.toString();
    }
}

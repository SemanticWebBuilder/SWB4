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
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class GMap.
 */
public class GMap extends org.semanticwb.model.base.GMapBase {

    /** The key. */
    String key = SWBPlatform.getEnv("key/gmap", "");

    /**
     * Instantiates a new g map.
     *
     * @param base the base
     */
    public GMap(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Render element.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        GMap map = null;
        String nombre = "";
        StringBuffer ret = new StringBuffer();
        SemanticProperty prop2 = Geolocalizable.swb_longitude;
        SemanticProperty prop3 = Geolocalizable.swb_geoStep;
        if (obj == null)
            obj = new SemanticObject();
        else 
            nombre = obj.getDisplayName(lang);
        int step = obj.getIntProperty(prop3);
        if (0 == step)
            step = 10;
        String value = request.getParameter(prop.getName());
         /** Class Cast Exception in value = obj.getProperty(prop). */
        try {
            if (value == null)
                value = obj.getProperty(prop);
        }catch (Exception e) {
            map = (GMap)obj.getObjectProperty(prop).createGenericInstance();
        }
        if (value == null && null != map) {
            value = "" + map.getInitLatitude();
            step  = map.getInitStep();
        }else {
            value = "" + getInitLatitude();
            step  = getInitStep();
        }
        String value2 = request.getParameter(prop2.getName());
        if (value2 == null)
            value2 = obj.getProperty(prop2);
        if (value2 == null && null != map)
            value2 = "" + map.getInitLongitude();
        else
            value2 = "" + getInitLongitude();
         /** Class Cast Exception in value = obj.getProperty(prop). */
        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input type=\"text\" size=\"60\" id =\"gmap_address\" value=\"Introduce Calle,"
                       + " Colonia y Estado\" onKeyPress=\"if (event.which==13) {search(); return false;} \"/>\n");
            ret.append("<button onclick=\"search(); return false;\"> buscar </button>\n");
            ret.append("<input type=\"hidden\" id=\"latitude\" name=\"latitude\" value=\"" + value + "\"/>\n");
            ret.append("<input type=\"hidden\" id=\"longitude\" name=\"longitude\" value=\"" + value2 + "\"/>\n");
            ret.append("<input type=\"hidden\" id=\"geoStep\" name=\"geoStep\" value=\"" + step + "\"/>\n");
            ret.append("<div id=\"map_canvas\" style=\"width: 500px; height: 300px\"></div>\n");
            ret.append("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=" + key + "\"\n");
            ret.append("      type=\"text/javascript\" charset=\"utf-8\"></script>\n\n");
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
            ret.append("        document.getElementById(\"latitude\").value = point.lat().toFixed(7);\n");
            ret.append("        document.getElementById(\"longitude\").value = point.lng().toFixed(7);\n");
            ret.append("        document.getElementById(\"geoStep\").value = map.getZoom();\n");
            ret.append("    });\n");
            ret.append("    GEvent.addListener(map, \"moveend\", function() {\n");
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
        }else if (mode.equals("view")) {
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
            ret.append("        var marker = new GMarker(center, {draggable: false});\n");
            ret.append("        map.addOverlay(marker);\n");
            ret.append("        marker.openInfoWindow(   \n");
            ret.append("        document.createTextNode(\"" + nombre + "\"));\n");
            ret.append("      }\n");
            ret.append("    }\n");
            ret.append("initialize();\n");
            ret.append("    </script>\n");
        }
        return ret.toString();
    }
}
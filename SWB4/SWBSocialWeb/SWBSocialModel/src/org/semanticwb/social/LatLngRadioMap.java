package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * LatLngRadioMap 
   */
public class LatLngRadioMap extends org.semanticwb.social.base.LatLngRadioMapBase 
{
    public LatLngRadioMap(org.semanticwb.platform.SemanticObject base)
    {
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) 
    {
        StringBuilder         ret      = new StringBuilder();
        try
        {
            System.out.println("Entra a LatLngRadioMap/renderElement");

            if (obj == null) {
                obj = new SemanticObject();
            }        


            String         name     = propName;

            System.out.println("obj:"+obj);
            System.out.println("name:"+name);

            String value = request.getParameter(propName);

            System.out.println("value-1:"+value);

            if (value == null) {
                value = obj.getProperty(prop);
            }
            if (value == null) {
                value = "";
            }

            System.out.println("value-2:"+value);

            //ret.append("<script type=\"text/javascript\" src=\'http://maps.google.com/maps/api/js?sensor=false\'></script> \n");
            
            ret.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false&key=AIzaSyA_8bWaWXaKlJV2XgZt-RYwRAsp6S0J7iw\"></script>");
            ret.append("<script type=\"text/javascript\" src=\"/swbadmin/js/jquery/jquery-1.4.4.min.js\"></script> \n");

            ret.append("<script type=\"text/javascript\" language=\"javascript\"> \n");

            ret.append("$(document).ready(function(){ \n");

                ret.append("var Circle = null; \n");
                ret.append("var Radius = $(\"#radius\").val(); \n");

                ret.append("var StartPosition = new google.maps.LatLng(54.19335, -3.92695); \n");

                ret.append("function DrawCircle(Map, Center, Radius) { \n");

                    ret.append("if (Circle != null) { \n");
                        ret.append("Circle.setMap(null); \n");
                    ret.append("} \n");

                    ret.append("if(Radius > 0) { \n");
                        ret.append("Radius *= 1609.344; \n");
                        ret.append("Circle = new google.maps.Circle({ \n");
                            ret.append("center: Center, \n");
                            ret.append("radius: Radius, \n");
                            ret.append("strokeColor: \"#0000FF\", \n");
                            ret.append("strokeOpacity: 0.35, \n");
                            ret.append("strokeWeight: 2, \n");
                            ret.append("fillColor: \"#0000FF\", \n");
                            ret.append("fillOpacity: 0.20, \n");
                            ret.append("map: Map \n");
                        ret.append("}); \n");
                    ret.append("} \n");
                ret.append("} \n");

                ret.append("function SetPosition(Location, Viewport) { \n");
                    ret.append("Marker.setPosition(Location); \n");
                    ret.append("if(Viewport){ \n");
                        ret.append("Map.fitBounds(Viewport); \n");
                        ret.append("Map.setZoom(map.getZoom() + 2); \n");
                    ret.append("} \n");
                    ret.append("else { \n");
                        ret.append("Map.panTo(Location); \n");
                    ret.append("} \n");
                    ret.append("Radius = $(\"#radius\").val(); \n");
                    ret.append("DrawCircle(Map, Location, Radius); \n");
                    ret.append("$(\"#latitude\").val(Location.lat().toFixed(5)); \n");
                    ret.append("$(\"#longitude\").val(Location.lng().toFixed(5)); \n");
              ret.append("} \n");

                ret.append("var MapOptions = { \n");
                    ret.append("zoom: 5, \n");
                    ret.append("center: StartPosition, \n");
                    ret.append("mapTypeId: google.maps.MapTypeId.ROADMAP, \n");
                    ret.append("mapTypeControl: false, \n");
                    ret.append("disableDoubleClickZoom: true, \n");
                    ret.append("streetViewControl: false \n");
                ret.append("}; \n");

                ret.append("var MapView = $(\"#map\"); \n");
                ret.append("var Map = new google.maps.Map(MapView.get(0), MapOptions); \n");

                ret.append("var Marker = new google.maps.Marker({ \n");
                    ret.append("position: StartPosition, \n");
                    ret.append("map: Map, \n");
                    ret.append("title: \"Drag Me\", \n");
                    ret.append("draggable: true \n");
                ret.append("}); \n");

                ret.append("google.maps.event.addListener(Marker, \"dragend\", function(event) { \n");
                    ret.append("SetPosition(Marker.position); \n");
                ret.append("}); \n");

                ret.append("$(\"#radius\").keyup(function(){ \n");
                    ret.append("google.maps.event.trigger(Marker, \"dragend\"); \n");
                ret.append("}); \n");

                ret.append("DrawCircle(Map, StartPosition, Radius); \n");
                ret.append("SetPosition(Marker.position); \n");

            ret.append("}); \n");

            ret.append("</script> \n");

            if (mode.equals("edit") || mode.equals("create")) {
                ret.append("Radio<input type=\"text\" id=\"radius\" value=\"320\" /> \n");
            }else if (mode.equals("view")) {
                ret.append("<span _id=\"radius\" name=\"radius\">" + value + "</span> \n");
            }

            ret.append("HolasssLat<input type=\"text\" id=\"latitude\" /> \n");
            ret.append("<input type=\"text\" id=\"longitude\" /> \n");

            ret.append("<div id=\"map\"> \n");
            ret.append("</div> \n");
        
        }catch(Exception e)
        {
            System.out.println("ErrorJ:"+e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("HTML:"+ret.toString());
        
        return ret.toString();
    
    }
    
    
}

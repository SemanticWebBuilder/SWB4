package org.semanticwb.social;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormElementURL;
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
        
        if(request.getParameter("doView")==null)
        {
            FormElementURL formElementUrl=getRenderURL(obj, prop, type, mode, lang);
            
            ret.append("<input type=\"hidden\" id=\"geoCenterLatitude\" name=\"geoCenterLatitude\" value=\"\"/>");
            ret.append("<input type=\"hidden\" id=\"geoCenterLongitude\" name=\"geoCenterLongitude\" />");
            ret.append("<input type=\"hidden\" id=\"geoRadio\" name=\"geoRadio\" />");
            
            ret.append("<iframe width=\"700\" height=\"440\" src=\"" + formElementUrl.setParameter("doView", "1").setParameter("suri", obj.getURI()) + "\"></iframe> ");
            return ret.toString();
        }
        
        try
        {
            if (obj == null) {
                obj = new SemanticObject();
            }        

            Stream stream=(Stream)obj.createGenericInstance();
            //System.out.println("LatitudeInicial:"+stream.getGeoCenterLatitude());
            //System.out.println("LongitudInicial:"+stream.getGeoCenterLongitude());
            //System.out.println("RadioInicial:"+stream.getGeoRadio());
            
            
            String latitude="0";
            String longitude="0";
            String radio="0";
            if(stream.getGeoCenterLatitude()!=0)latitude=""+stream.getGeoCenterLatitude();
            if(stream.getGeoCenterLongitude()!=0)longitude=""+stream.getGeoCenterLongitude();
            if(stream.getGeoRadio()>0)radio=""+stream.getGeoRadio();
            

            ret.append("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?sensor=false\"></script>");
            ret.append("<script type=\"text/javascript\" src=\"/swbadmin/js/jquery/jquery-1.4.4.min.js\"></script>");
            ret.append("<script type=\"text/javascript\" language=\"javascript\">");
            
            ret.append("function setParentValue(elementname, value2set) {");
            //ret.append("alert('Hola');");
            //ret.append("alert('elementName2Change:'+elementname);");
            //ret.append("alert('valuetoset:'+value2set);");
            ret.append("parent.document.getElementById(''+elementname+'').value=''+value2set+'';");
            //ret.append("var geoLati=parent.document.getElementById('geoCenterLatitude').value;");
            //ret.append("alert('geoLati:'+geoLati);");
            //ret.append("alert('Valor Puesto:'+parent.document.getElementById('geoCenterLatitude'));");
            ret.append("return false;");
            ret.append("}");
            
            ret.append("$(document).ready(function(){");
            ret.append("var Circle = null;");
            ret.append("var Radius = $(\"#geoRadio\").val();");
            ret.append("var StartPosition = new google.maps.LatLng("+latitude+", "+longitude+");");
            ret.append("function DrawCircle(Map, Center, Radius) {");
            ret.append("if (Circle != null) {");
            ret.append("Circle.setMap(null);");
            ret.append("}");
            ret.append("if(Radius > 0) {");
            ret.append("Radius *= 1609.344;");
            ret.append("Circle = new google.maps.Circle({");
            ret.append("center: Center,");
            ret.append("radius: Radius,");
            ret.append("strokeColor: \"#0000FF\",");
            ret.append("strokeOpacity: 0.35,");
            ret.append("strokeWeight: 2,");
            ret.append("fillColor: \"#0000FF\",");
            ret.append("fillOpacity: 0.20,");
            ret.append("map: Map");
            ret.append("});");
            ret.append("}");
            ret.append("}");
            ret.append("function SetPosition(Location, Viewport) {");
            ret.append("Marker.setPosition(Location);");
            ret.append("if(Viewport){");
            ret.append("Map.fitBounds(Viewport);");
            ret.append("Map.setZoom(map.getZoom() + 2);");
            ret.append("}");
            ret.append("else {");
            ret.append("Map.panTo(Location);");
            ret.append("}");
            ret.append("Radius = $(\"#geoRadio\").val();");
            ret.append("DrawCircle(Map, Location, Radius);");
            ret.append("$(\"#geoCenterLatitude\").val(Location.lat().toFixed(5));");
            ret.append("$(\"#geoCenterLongitude\").val(Location.lng().toFixed(5));");
            ret.append("setParentValue('geoCenterLatitude', Location.lat().toFixed(5));");
            ret.append("setParentValue('geoCenterLongitude', Location.lng().toFixed(5));");
            ret.append("}");
            ret.append("var MapOptions = {");
            ret.append("zoom: 2,");
            ret.append("center: StartPosition,");
            ret.append("mapTypeId: google.maps.MapTypeId.ROADMAP,");
            ret.append("mapTypeControl: false,");
            ret.append("disableDoubleClickZoom: true,");
            ret.append("streetViewControl: false");
            ret.append("};");
            ret.append("var MapView = $(\"#map\");");
            ret.append("var Map = new google.maps.Map(MapView.get(0), MapOptions);");
            ret.append("var Marker = new google.maps.Marker({");
            ret.append("position: StartPosition,");
            ret.append("map: Map,");
            ret.append("title: \"Drag Me\",");
            ret.append("draggable: true");
            ret.append("});");
            ret.append("google.maps.event.addListener(Marker, \"dragend\", function(event) {");
            ret.append("SetPosition(Marker.position);");
            ret.append("});");
            ret.append("$(\"#geoRadio\").keyup(function(){");
            ret.append("google.maps.event.trigger(Marker, \"dragend\");");
            ret.append("});");
            ret.append("DrawCircle(Map, StartPosition, Radius);");
            ret.append("SetPosition(Marker.position);");
            ret.append("});");
            ret.append("</script>");
            ret.append("Lat<input type=\"text\" id=\"geoCenterLatitude\" name=\"geoCenterLatitude\" value=\""+latitude+"\" onchange=\"setParentValue('geoCenterLatitude', this.value);\"/>");
            ret.append("Lng<input type=\"text\" id=\"geoCenterLongitude\" name=\"geoCenterLongitude\" value=\""+longitude+"\" onchange=\"setParentValue('geoCenterLongitude', this.value);\"/>");
            ret.append("Radio<input type=\"text\" id=\"geoRadio\" value=\""+radio+"\" name=\"geoRadio\" onchange=\"setParentValue('geoRadio', this.value);\"/>");
            ret.append("<div id=\"map\" style=\"width:600px; height:400px; background-color:#000000;\">");
            ret.append("</div>");
        
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return ret.toString();
    
    }
    
    
     /**
     * Process.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {

        Stream stream=(Stream)obj.createGenericInstance();
        /*
        Enumeration<String> enParams=request.getParameterNames();
        while(enParams.hasMoreElements())
        {
            String sParam=enParams.nextElement();
            System.out.println("ParametroJJ:"+request.getParameter(sParam));
        }*/
        
        stream.setGeoCenterLatitude(0);
        if(request.getParameter("geoCenterLatitude")!=null)
        {
            try{                
                stream.setGeoCenterLatitude(Float.parseFloat(request.getParameter("geoCenterLatitude")));
            }catch(Exception e){stream.setGeoCenterLatitude(0);}
        }
        stream.setGeoCenterLongitude(0);
        if(request.getParameter("geoCenterLongitude")!=null)
        {
            try{                
                stream.setGeoCenterLongitude(Float.parseFloat(request.getParameter("geoCenterLongitude")));
            }catch(Exception e){stream.setGeoCenterLongitude(0);}
        }
        stream.setGeoRadio(0);
        if(request.getParameter("geoRadio")!=null)
        {
            try{                
                stream.setGeoRadio(Float.parseFloat(request.getParameter("geoRadio")));
            }catch(Exception e){stream.setGeoRadio(0);}
        }
        
    }

    
}

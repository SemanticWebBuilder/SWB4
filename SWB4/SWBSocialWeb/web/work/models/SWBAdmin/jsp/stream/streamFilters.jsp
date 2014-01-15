<%-- 
    Document   : streamFilters
    Created on : 13-ene-2014, 16:02:09
    Author     : jorge.jimenez
--%>


<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<!--Este Jsp no se utiliza actualmente, en algún momento se pudiera utilizar para manejar en conjunto con la clase 
org.semanticwb.social.admin.resources.StreamFilters los filtros de un stream, en este momento se estan manejando desde
la pestaña "Información" del mismo Stream.-->
<%
/*
    String apiKey=SWBSocialUtil.Util.getModelPropertyValue(SWBContext.getAdminWebSite(), "GoogleMapsApiKey");
    System.out.println("apiKey:"+apiKey);
    if(apiKey==null){
        out.println("Error:No se puede mostrar el mapa debido a que la llave de Google Maps no esta configurada(GoogleMapsApiKey), contactese con su administrador");
        return; 
    }
 * */
    String suri=request.getParameter("suri");
    if(suri==null) {
        return;
    }
    SemanticObject semObj=SemanticObject.getSemanticObject(suri);
    if(semObj==null) return;
    Stream stream=(Stream) semObj.createGenericInstance();
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    urlAction.setParameter("suri", stream.getURI());
    urlAction.setParameter("wsite", wsite.getId());
 
%>         

<form dojoType="dijit.form.Form" id="frmStreamFilters" action="<%=urlAction%>" method="post" onsubmit="submitForm('frmStreamFilters'); return false;">
            
<fieldset>
    <legend>Filtros para Mensajes</legend>
    <table>
        <tbody><tr><td width="200px" align="right"><label for="filterSentimentalPositive">Sentimiento Positivo &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_6"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterSentimentalPositive" value="on" tabindex="0" id="dijit_form_CheckBox_6" style="-moz-user-select: none;" <%=stream.isFilterSentimentalPositive()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="filterSentimentalNegative">Sentimiento Negativo &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_7"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterSentimentalNegative" value="on" tabindex="0" id="dijit_form_CheckBox_7" style="-moz-user-select: none;" <%=stream.isFilterSentimentalNegative()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="filterSentimentalNeutral">Sentimiento Neutral &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_8"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterSentimentalNeutral" value="on" tabindex="0" id="dijit_form_CheckBox_8" style="-moz-user-select: none;" <%=stream.isFilterSentimentalNeutral()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="filterIntensityHigh">Intesidad Alta &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_9"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterIntensityHigh" value="on" tabindex="0" id="dijit_form_CheckBox_9" style="-moz-user-select: none;" <%=stream.isFilterIntensityHigh()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="filterIntensityMedium">Intensidad media &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_10"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterIntensityMedium" value="on" tabindex="0" id="dijit_form_CheckBox_10" style="-moz-user-select: none;" <%=stream.isFilterIntensityMedium()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="filterIntensityLow">Intensidad baja &nbsp;</label></td><td><div role="presentation" class="dijit dijitReset dijitInline dijitCheckBox" widgetid="dijit_form_CheckBox_11"><input type="checkbox" data-dojo-attach-event="ondijitclick:_onClick" data-dojo-attach-point="focusNode" class="dijitReset dijitCheckBoxInput" aria-checked="false" role="checkbox" name="filterIntensityLow" value="on" tabindex="0" id="dijit_form_CheckBox_11" style="-moz-user-select: none;" <%=stream.isFilterIntensityLow()?"checked=\"true\"":""%>></div></td></tr>
        <tr><td width="200px" align="right"><label for="stream_KloutValue">Klout mayor a &nbsp;</label></td><td><div class="dijitReset dijitInputField dijitInputContainer"><input type="text" name="stream_KloutValue" autocomplete="off" data-dojo-attach-point="textbox,focusNode" class="dijitReset dijitInputInner" tabindex="0" id="dijit_form_ValidationTextBox_18" maxlength="3" size="30" value="<%=stream.getStream_KloutValue()>0?stream.getStream_KloutValue():""%>" aria-invalid="false"></div></div></td></tr> 
        <tr><td width="200px" align="right"><label for="geoLanguage">Idioma &nbsp;</label></td><td><div class="dijitReset dijitInputField dijitInputContainer"><input type="text" name="geoLanguage" autocomplete="off" data-dojo-attach-point="textbox,focusNode" class="dijitReset dijitInputInner" tabindex="0" id="dijit_form_ValidationTextBox_19" size="30" value="<%=stream.getGeoLanguage()!=null?stream.getGeoLanguage():""%>" aria-invalid="false"></div></div></td></tr>
        


<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="/swbadmin/js/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
var Circle = null;
var Radius = $("#geoRadio").val();
//var StartPosition = new google.maps.LatLng(19.43871, -99.12348);
var StartPosition = new google.maps.LatLng(<%=stream.getGeoCenterLatitude()!=0?stream.getGeoCenterLatitude():"19.43871"%>, <%=stream.getGeoCenterLongitude()!=0?stream.getGeoCenterLongitude():"-99.12348"%>); 

function DrawCircle(Map, Center, Radius) {
if (Circle != null) {
Circle.setMap(null);
}
if(Radius > 0) {
Radius *= 1609.344;
Circle = new google.maps.Circle({
center: Center,
radius: Radius,
strokeColor: "#0000FF",
strokeOpacity: 0.35,
strokeWeight: 2,
fillColor: "#0000FF",
fillOpacity: 0.20,
map: Map
});
}
}
function SetPosition(Location, Viewport) {
Marker.setPosition(Location);
if(Viewport){
Map.fitBounds(Viewport);
Map.setZoom(map.getZoom() + 2);
}
else {
Map.panTo(Location);
}
Radius = $("#geoRadio").val();
DrawCircle(Map, Location, Radius);
$("#geoCenterLatitude").val(Location.lat().toFixed(5));
$("#geoCenterLongitude").val(Location.lng().toFixed(5));
}
var MapOptions = {
zoom: 3,
center: StartPosition,
mapTypeId: google.maps.MapTypeId.ROADMAP,
mapTypeControl: false,
disableDoubleClickZoom: true,
streetViewControl: false
};
var MapView = $("#map");
var Map = new google.maps.Map(MapView.get(0), MapOptions);
var Marker = new google.maps.Marker({
position: StartPosition,
map: Map,
title: "Drag Me",
draggable: true
});
google.maps.event.addListener(Marker, "dragend", function(event) {
SetPosition(Marker.position);
});
$("#geoRadio").keyup(function(){
google.maps.event.trigger(Marker, "dragend");
});
DrawCircle(Map, StartPosition, Radius);
SetPosition(Marker.position);
});
</script>

<tr><td width="200px" align="right"><label for="geoCenterLatitude">Latitud &nbsp;</label></td><td><div class="dijitReset dijitInputField dijitInputContainer"><input type="text" name="geoCenterLatitude" id="geoCenterLatitude" autocomplete="off" data-dojo-attach-point="textbox,focusNode" class="dijitReset dijitInputInner" tabindex="0" id="dijit_form_ValidationTextBox_20" value="<%=stream.getGeoCenterLatitude()>0?stream.getGeoCenterLatitude():""%>" aria-invalid="false" readonly></div></td></tr>
<tr><td width="200px" align="right"><label for="geoCenterLongitude">Longitud &nbsp;</label></td><td><div class="dijitReset dijitInputField dijitInputContainer"><input type="text" name="geoCenterLongitude" id="geoCenterLongitude" autocomplete="off" data-dojo-attach-point="textbox,focusNode" class="dijitReset dijitInputInner" tabindex="0" id="dijit_form_ValidationTextBox_21" value="<%=stream.getGeoCenterLongitude()>0?stream.getGeoCenterLongitude():""%>" aria-invalid="false" readonly></div></td></tr>
<tr><td width="200px" align="right"><label for="geoRadio">Radio &nbsp;</label></td><td><div class="dijitReset dijitInputField dijitInputContainer"><input type="text" name="geoRadio" id="geoRadio" autocomplete="off" data-dojo-attach-point="textbox,focusNode" class="dijitReset dijitInputInner" tabindex="0" id="dijit_form_ValidationTextBox_22" value="<%=stream.getGeoRadio()>0?stream.getGeoRadio():""%>" aria-invalid="false"></div></td></tr>

<tr><td width="200px" align="right"><label for="geoRadio">Seleccione ubicación</label></td><td><div class="dijitReset dijitInputField dijitInputContainer">
<div id="map" style="width:600px; height:400px; background-color:#000000;">
</div>
</div></td></tr>       

<tr><td width="200px" align="right"><label for="geoDistanceUnit">Unidad de medida &nbsp;</label></td><td>
        <select name="geoDistanceUnit" id="geoDistanceUnit">
            <%
                if(stream.getGeoDistanceUnit()==null || stream.getGeoDistanceUnit().equals("KM"))
                {
            %>
                <option value="KM" selected="true">Kilometros</option>
                <%}else{%><option value="KM">Kilometros</option><%}%>
                <%if(stream.getGeoDistanceUnit()!=null && stream.getGeoDistanceUnit().equals("MI")){%>
                <option value="MI" selected="true">Millas</option>
                <%}else{%><option value="MI">Millas</option><%}%>
        </select>        
        
</td></tr>        

</tbody></table>
</fieldset>
        
        <button class="submit" type="" onclick="">Enviar</button>
</form>
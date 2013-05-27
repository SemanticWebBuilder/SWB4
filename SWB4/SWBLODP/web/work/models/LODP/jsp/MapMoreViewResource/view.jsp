<%-- 
    Document   : view
    Created on : 27/05/2013, 12:01:50 PM
    Author     : rene.jara
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="java.util.Set"%>
<%@page import="com.infotec.lodp.swb.resources.DataSetResource"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
<%
    WebSite wsite=paramRequest.getWebPage().getWebSite();
    Iterator<Dataset> itds=Dataset.ClassMgr.listDatasets(wsite);
    Iterator<Dataset> sds=DataSetResource.sortByViews(itds,false).iterator();
    while(sds.hasNext()){
        Dataset dataset=sds.next();
        if(dataset.getDatasetFormat().toLowerCase().equals("kml")){
            String path=dataset.getActualVersion().getFilePath();                    
%>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?v=3&amp;sensor=false&amp;language=es&amp;region=MX"></script>
<script type="text/javascript">
    //<!--
    var map;
    var arrMarkers = [];
    var arrInfoWindows = [];

    function initializeMap() {
        var divMap = document.getElementById("mapCanvas");
        divMap.style.width="200px";
        divMap.style.height="200px";
        var latlng = new google.maps.LatLng(22.99885, -101.77734);
        var myOptions = {
            zoom: 3,
            center: latlng,
            //scrollwheel: false,
            disableDefaultUI:true,
            backgroundColor: "black",
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
           var map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
<%
       if(path!=null&&!path.equals("")){
%>
          var ctaLayer = new google.maps.KmlLayer({
                url: 'http://200.38.186.24:8080/<%=path%>',
                preserveViewport: true
        });
        ctaLayer.setMap(map);
<%
    }
%>

        var marker = null;
        var infowindow = null;
        }
        //-->
</script>
<div id="mapCanvas" class="mapa" ></div>
<script type="text/javascript">
    initializeMap();
</script>
<%
       if(path!=null&&!path.equals("")){
        break;
       }
    }
}
%>
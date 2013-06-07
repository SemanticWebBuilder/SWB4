<%-- 
    Document   : view
    Created on : 05/06/2013, 5:44:01 PM
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
    Resource base=paramRequest.getResourceBase();

    String suri = request.getParameter("suri");
    Dataset dataset=null;
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();





    if(suri==null)suri="LODP:lodpcg_Dataset:1";





    if (suri != null && !suri.equals("")) {
        GenericObject gobj = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
        if (gobj != null && gobj instanceof Dataset) {
            dataset = (Dataset) gobj;
        }
    }
    String dsid=base.getAttribute("datosid");
    String serveraddr = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? (":" + request.getServerPort()) : "");
    if(dataset!=null){//&&
//            (dataset.getDatasetFormat().toLowerCase().equals("kml")||
//            dataset.getDatasetFormat().toLowerCase().equals("kmz"))){
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
        divMap.style.width="640px";
        divMap.style.height="480px";
        var latlng = new google.maps.LatLng(22.99885, -101.77734);
        var myOptions = {
            zoom: 5,
            center: latlng,
            //scrollwheel: false,
           // disableDefaultUI:true,
            backgroundColor: "black",
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
           var map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
<%
       if(path!=null&&!path.equals("")){
%>
          var ctaLayer = new google.maps.KmlLayer({
//                url: '<%=serveraddr%><%=path%>'
                url: 'http://200.38.186.24:8080/<%=path%>'
//                ,preserveViewport: true
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
<div class="mapa_titulo"><%=dataset.getDatasetTitle()%></div>
<div id="mapCanvas" class="mapa" ></div>
<script type="text/javascript">
    initializeMap();
</script>
<%
}
%>
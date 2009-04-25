    <script src="http://maps.google.com/maps?file=api&amp;oe=ISO8859-1&amp;v=2&amp;key=ABQIAAAAmLq_QWhsX0CVX9K0e5WhSBS3m1BKAXA0fNHX9r-IsaoxbigLrBRc55VBvq4HVg3X865IboRkk0e-_g"
      type="text/javascript"></script>

    <div id="map" style="width: 0px; height: 0px"></div>

    <script type="text/javascript">
    //<![CDATA[

    var map;
    var ovmap;

    // === The basis of the arrow icon information ===
    var arrowIcon = new GIcon();
    arrowIcon.iconSize = new GSize(24,24);
    arrowIcon.shadowSize = new GSize(1,1);
    arrowIcon.iconAnchor = new GPoint(12,12);
    arrowIcon.infoWindowAnchor = new GPoint(0,0);
      
    // === Returns the bearing in degrees between two points. ==
    // North = 0, East = 90, South = 180, West = 270.
    var degreesPerRadian = 180.0 / Math.PI;

    function bearing( from, to ) {
        // See T. Vincenty, Survey Review, 23, No 176, p 88-93,1975.
        // Convert to radians.
        var lat1 = from.latRadians();
        var lon1 = from.lngRadians();
        var lat2 = to.latRadians();
        var lon2 = to.lngRadians();

        // Compute the angle.
        var angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );
        if ( angle < 0.0 )
	 angle  += Math.PI * 2.0;

        // And convert result to degrees.
        angle = angle * degreesPerRadian;
        angle = angle.toFixed(1);

        return angle;
    }

    // === A function to create the arrow head at the end of the polyline ===
    function arrowHead(points) {
        // == obtain the bearing between the last two points
        var p1=points[points.length-1];
        var p2=points[points.length-2];
        var dir = bearing(p2,p1);
        // == round it to a multiple of 3 and cast out 120s
        var dir = Math.round(dir/3) * 3;
        while (dir >= 120) {dir -= 120;}
        // == use the corresponding triangle marker 
        arrowIcon.image = "http://www.google.com/intl/en_ALL/mapfiles/dir_"+dir+".png";
        map.addOverlay(new GMarker(p1, arrowIcon));
    }

    // === A function to put arrow heads at intermediate points
    function midArrows(points) {
        for (var i=1; i < points.length-1; i++) {  
          var p1=points[i-1];
          var p2=points[i+1];
          var dir = bearing(p1,p2);
          // == round it to a multiple of 3 and cast out 120s
          var dir = Math.round(dir/3) * 3;
          while (dir >= 120) {dir -= 120;}
          // == use the corresponding triangle marker 
          arrowIcon.image = "http://www.google.com/intl/en_ALL/mapfiles/dir_"+dir+".png";
          map.addOverlay(new GMarker(points[i], arrowIcon));
        }
    }


    this.map_markers=[];
    this.map_mark_index=0;

    function load() {
      if (GBrowserIsCompatible()) {

        var m = document.getElementById("map");
        m.style.height = "400px";
        m.style.width = "100%";

        map = new GMap2(document.getElementById("map"));
        map.addControl(new GLargeMapControl());
        map.addControl(new GMapTypeControl());
        map.setCenter(new GLatLng(19.49419516889648, -99.04441952705383), 13);
        map.setMapType(G_HYBRID_MAP);
        
        for(var x=0;x<this.map_mark_index;x++)
        {
           map.addOverlay(this.map_markers[x]);
        }

        GEvent.addListener(map, "click", function(overlay,point) {
            // == When the user clicks on a the map, get directiobns from that point to itself ==
            if (point) {
                map.addOverlay(addMarker2(point, 'New', 'Location:'+point,null,true));
            }
        });


        // === The array of points for the polyline ===
        //var points = [ new GLatLng(19.193951,-99.043814), new GLatLng(19.493951,-99.043814), new GLatLng(19.493951,-99.093814),new GLatLng(19.493951,-100.043814)];
        var points = [ new GLatLng(19.493951,-99.043814), new GLatLng(19.493951,-99.093814)];
        // === Create the polyline
        map.addOverlay(new GPolyline(points));
        // === add the arrow head
        arrowHead(points);
        //midArrows(points);


        //  ======== Add a map overview ==========
        var ovcontrol = new GOverviewMapControl(new GSize(150,150)); 
        map.addControl(ovcontrol);

        //  ======== Cause the overview to be positioned AFTER IE sets its initial position ======== 
        setTimeout("positionOverview(558,254)",1);

        // ======== get a reference to the GMap2 ===========
        ovmap = ovcontrol.getOverviewMap();
        GEvent.addListener(ovmap,"click",function() {alert("you clicked the overview");} );

        //  ======== change the overview map type AFTER the overview finisges initializing =====
        //setTimeout("ovmap.setMapType(G_SATELLITE_MAP);",1);
	 //setTimeout("ovmap.setMapType(G_HYBRID_MAP);",1);


      }

      //if(map_mark_index>0)setTimeout("myclick(0)",1);
    }

    window.onload=load;
    window.onunload=GUnload;

    // A function to create the marker and set up the event window
    // Dont try to unroll this function. It has to be here for the function closure
    // Each instance of the function preserves the contends of a different instance
    // of the "marker" and "html" variables which will be needed later when the event triggers.    

    function addMarker(lon, lat, name, html,infoUrl) {
        var point = new GLatLng(lon,lat);
        return addMarker2(point, name, html,infoUrl,false);
    }

    function addMarker2(point, name, html,infoUrl, drag) {
        
        var letter = String.fromCharCode("A".charCodeAt(0) + map_mark_index);
        var myIcon = new GIcon(G_DEFAULT_ICON, "http://www.google.com/mapfiles/marker" + letter + ".png");
        //myIcon.printImage = "http://maps.google.com/mapfiles/marker"+letter+"ie.gif"
        //myIcon.mozPrintImage = "http://maps.google.com/mapfiles/marker"+letter+"ff.gif"
 
        var marker = new GMarker(point, {draggable:drag,icon:myIcon,title:name});
        //var marker = new GMarker(point);

        GEvent.addListener(marker, "click", function() {
	      if(infoUrl!=null)
                 marker.openInfoWindowHtml(html,{maxUrl:infoUrl});
		   //marker.showMapBlowup({type:G_NORMAL_MAP,zoom:16,maxUrl:infoUrl});
             else
                 marker.openInfoWindowHtml(html);
		   //marker.showMapBlowup(16, G_NORMAL_MAP);
         });
         //map.addOverlay(marker);

        this.map_markers[this.map_mark_index] = marker;
        this.map_mark_index++;

        return marker;
    }

    // This function picks up the click and opens the corresponding info window
    function myclick(i) {
        GEvent.trigger(this.map_markers[i], "click");
    }

    // This function picks up the click and opens the corresponding info window
    function showMap(i) {
        this.map_markers[i].showMapBlowup(16, G_NORMAL_MAP);
    }


    //  ======== A function to adjust the positioning of the overview ========
    function positionOverview(x,y) {
        var omap=document.getElementById("map_overview");
        //omap.style.left = x+"px";
        //omap.style.top = y+"px";
        
        // == restyling ==
        omap.firstChild.style.border = "1px solid gray";
        omap.firstChild.firstChild.style.left="3px";
        omap.firstChild.firstChild.style.top="3px";
        omap.firstChild.firstChild.style.width="142px";
        omap.firstChild.firstChild.style.height="142px";
    }

    //]]>
    </script>

    <script type="text/javascript">
        document.write('<b>'+String.fromCharCode("A".charCodeAt(0) + map_mark_index)+'</b> <a href="javascript:myclick(' + this.map_mark_index + ')">' + 'Hola' + '</a><br>');
	 addMarker(19.49419516889648, -99.04441952705383,'Hola 1','H Casa de Tapajoz 1. Liga <a href="http://www.contactoweb.com.mx">Link</a> to my home page<br><br><center><a href="javascript:map.setZoom(17);">Vista Calle</a> <a href="javascript:map.setZoom(13);">Vista Colonia</a> <a href="javascript:map.setZoom(11);">Vista Municipio</a><br><a href="javascript:showMap('+map_mark_index+');">Vista Mapa</a></center>',null);
    </script>

    <script type="text/javascript">
        document.write('<b>'+String.fromCharCode("A".charCodeAt(0) + map_mark_index)+'</b> <a href="javascript:myclick(' + this.map_mark_index + ')">' + 'Hola' + '</a><br>');
	 addMarker(19.493951,-99.043814,'Hola 1','H Casa de Tapajoz 1. Liga <a href="http://www.contactoweb.com.mx">Link</a> to my home page','http://www.google.com');
    </script>

    <script type="text/javascript">
        document.write('<b>'+String.fromCharCode("A".charCodeAt(0) + map_mark_index)+'</b> <a href="javascript:myclick(' + this.map_mark_index + ')">' + 'Hola' + '</a><br>');
        addMarker(19.493951,-99.093814,'Hola 2','H Casa de Tapajoz 2. Liga <a href="http://www.contactoweb.com.mx">Link</a> to my home page','/wb');
    </script>


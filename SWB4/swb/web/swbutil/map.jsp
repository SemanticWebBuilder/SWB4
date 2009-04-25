<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Google Maps</title>
  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAmLq_QWhsX0CVX9K0e5WhSBS3m1BKAXA0fNHX9r-IsaoxbigLrBRc55VBvq4HVg3X865IboRkk0e-_g" type="text/javascript"></script>
</head>
  <body onunload="GUnload()">

    <div id="map" style="width: 550px; height: 450px"></div>
    <a href="basic1.htm">Back to the tutorial page</a>


    <noscript><b>JavaScript must be enabled in order for you to use Google Maps.</b> 
      However, it seems JavaScript is either disabled or not supported by your browser. 
      To view Google Maps, enable JavaScript by changing your browser options, and then 
      try again.
    </noscript>
 

    <script type="text/javascript">
    //<![CDATA[
    
    if (GBrowserIsCompatible()) { 

      // A function to create the marker and set up the event window
      // Dont try to unroll this function. It has to be here for the function closure
      // Each instance of the function preserves the contends of a different instance
      // of the "marker" and "html" variables which will be needed later when the event triggers.    
      function createMarker(point,html) {
        var marker = new GMarker(point);
        GEvent.addListener(marker, "click", function() {
          marker.openInfoWindowHtml(html);
        });
        return marker;
      }

      // Display the map, with some controls and set the initial location 
      var map = new GMap2(document.getElementById("map"));
      map.addControl(new GLargeMapControl());
      map.addControl(new GMapTypeControl());
      map.setCenter(new GLatLng(43.907787,-79.359741),8);
    
      // Set up three markers with info windows 
    
      var point = new GLatLng(43.65654,-79.90138);
      var marker = createMarker(point,'<div style="width:240px">Some stuff to display in the First Info Window. With a <a href="http://www.econym.demon.co.uk">Link</a> to my home page</div>')
      map.addOverlay(marker);

      var point = new GLatLng(43.91892,-78.89231);
      var marker = createMarker(point,'Some stuff to display in the<br>Second Info Window')
      map.addOverlay(marker);

      var point = new GLatLng(43.82589,-79.10040);
      var marker = createMarker(point,'Some stuff to display in the<br>Third Info Window')
      map.addOverlay(marker);

    }
    
    // display a warning if the browser was not compatible
    else {
      alert("Sorry, the Google Maps API is not compatible with this browser");
    }

    // This Javascript is based on code provided by the
    // Blackpool Community Church Javascript Team
    // http://www.commchurch.freeserve.co.uk/   
    // http://econym.googlepages.com/index.htm

    //]]>
    </script>
  </body>

</html>





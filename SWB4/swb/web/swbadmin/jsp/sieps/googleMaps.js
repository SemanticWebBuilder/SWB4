function initialize(latitude, longitude, geoStep) {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map_canvas"));
		map.addControl(new GSmallMapControl());
		map.addControl(new GMapTypeControl());
		var center = new GLatLng(latitude, longitude);
		geocoder = new GClientGeocoder();
		setUpMap(map, center, geoStep);
	}
}
function initializeEvents(latitude, longitude, geoStep, idEvent) {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map_canvas_" + idEvent));
		map.addControl(new GSmallMapControl());
		map.addControl(new GMapTypeControl());
		var center = new GLatLng(latitude, longitude);
		geocoder = new GClientGeocoder();
		setUpMap(map, center, geoStep);
	}
}
function initializeEvents(coordenadas) {
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById(coordenadas.containerId));
		map.addControl(new GSmallMapControl());
		map.addControl(new GMapTypeControl());
		var center = new GLatLng(coordenadas.latitud, coordenadas.longitud);
		geocoder = new GClientGeocoder();
		setUpMapEvents(map, center, coordenadas);
	}
}


function setUpMapEvents(map, center, coordenadas) {
	map.setCenter(center, coordenadas.geoStep);
	var marker = new GMarker(center, {
		draggable : true
	});
	map.addOverlay(marker);
	GEvent.addListener(marker, "dragend", function() {
		var point = marker.getPoint();
		map.panTo(point);
		coordenadas.latitud 	= center.lat().toFixed(7);
		coordenadas.longitud 	= center.lng().toFixed(7);
		coordenadas.geoStep		= map.getZoom();
	});
	GEvent.addListener(map, "moveend", function() {
		map.clearOverlays();
		var center = map.getCenter();
		var marker = new GMarker(center, {
			draggable : true
		});
		map.addOverlay(marker);
		coordenadas.latitud = center.lat().toFixed(7);
		coordenadas.longitud = center.lng().toFixed(7);
		coordenadas.geoStep = map.getZoom();
		GEvent.addListener(marker, "dragend",
				function() {
					var point = marker.getPoint();
					map.panTo(point);
					coordenadas.latitud = center.lat()
							.toFixed(7);
					coordenadas.longitud = center.lng()
							.toFixed(7);
					coordenadas.geoStep = map.getZoom();
				});
	});
}
function setUpMap(map, center, step) {
	map.setCenter(center, step);
	var marker = new GMarker(center, {
		draggable : true
	});
	map.addOverlay(marker);
	GEvent.addListener(marker, "dragend", function() {
		var point = marker.getPoint();
		map.panTo(point);
		document.getElementById("latitude").value = center.lat().toFixed(7);
		document.getElementById("longitude").value = center.lng().toFixed(7);
		document.getElementById("geoStep").value = map.getZoom();
	});
	GEvent.addListener(map, "moveend", function() {
		map.clearOverlays();
		var center = map.getCenter();
		var marker = new GMarker(center, {
			draggable : true
		});
		map.addOverlay(marker);
		document.getElementById("latitude").value = center.lat().toFixed(7);
		document.getElementById("longitude").value = center.lng().toFixed(7);
		document.getElementById("geoStep").value = map.getZoom();
		GEvent.addListener(marker, "dragend",
				function() {
					var point = marker.getPoint();
					map.panTo(point);
					document.getElementById("latitude").value = center.lat()
							.toFixed(7);
					document.getElementById("longitude").value = center.lng()
							.toFixed(7);
					document.getElementById("geoStep").value = map.getZoom();
				});
	});
}
function search() {
	var address = document.getElementById("gmap_address").value;
	var map = new GMap2(document.getElementById("map_canvas"));
	map.addControl(new GSmallMapControl());
	map.addControl(new GMapTypeControl());
	if (geocoder) {
		geocoder.getLatLng(address, function(point) {
			if (!point) {
				alert(address + " no encontrada");
			} else {
				document.getElementById("latitude").value = point.lat()
						.toFixed(7);
				document.getElementById("longitude").value = point.lng()
						.toFixed(7);
				document.getElementById("geoStep").value = 14;
				map.clearOverlays();
				setUpMap(map, point, 14);
			}
		});
	}
}

var arrCoordenadasEventos	=	new Array();
function CoordenadasMapa(){
	this.tabId				= "";
	this.containerId	= "";
	this.latitud 		= 0.0;
	this.longitud		= 0.0;
	this.geoStep		= 0.0;	
}
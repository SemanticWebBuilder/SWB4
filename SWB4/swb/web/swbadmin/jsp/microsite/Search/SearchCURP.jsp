<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticClass, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SemanticProperty swbcomm_dirPhoto = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    ArrayList<SemanticObject> results = (ArrayList<SemanticObject>) request.getAttribute("results");
    ArrayList<SemanticObject> allRes = (ArrayList<SemanticObject>) request.getAttribute("allRes");
    String searchUrl = (String) request.getAttribute("rUrl");
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("separator", "-");
%>

<%!
    class GeoLocation {
        private double latitude;
        private double longitude;
        int step;
        String name;
        public GeoLocation(double latitude, double longitude, int step, String name)
        {
            this.latitude = latitude;
            this.longitude = longitude;
            this.step = step;
            this.name = name;
        }
        public double getLatitude(){ return latitude;}
        public double getLongitude(){ return longitude;}
        public int getStep(){ return step;}
        public String getName(){ return name;}
    }
%>

<%
if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
%>
    <div id="busqueda">
        <h2>B&uacute;squeda</h2>
        <div class="clear">&nbsp;</div>
        <form id="busqueda_form" action="<%=searchUrl%>" method="get">
            <p>
                <input id="busqueda_input" type="text" name="q"/>
                <input id="busqueda_enviar" type="submit"/>
            </p>
        </form>
    </div>
<%
} else if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
    if (results != null && results.size() > 0) {

        //Get all DirectoryObject's GeoLocations as arrayList
        ArrayList<GeoLocation> objs = new ArrayList<GeoLocation>();
        Iterator<SemanticObject> soit = allRes.iterator();
        while (soit.hasNext()) {
            SemanticObject so = soit.next();
            DirectoryObject dob = (DirectoryObject) so.createGenericInstance();
            String html = "<b><font color=\"blue\">" + dob.getTitle() + "</font></b><br>" +
                    ((CURPModule)dob).getAddressString() +
                    "<br><b>Contacto:</b> " + ((CURPModule)dob).getContactName() +
                    "<br><b>Teléfono:</b> " + ((CURPModule)dob).getContactPhoneNumber();
            objs.add(new GeoLocation(
                 dob.getSemanticObject().getDoubleProperty(Geolocalizable.swb_latitude),
                 dob.getSemanticObject().getDoubleProperty(Geolocalizable.swb_longitude),
                 dob.getSemanticObject().getIntProperty(Geolocalizable.swb_geoStep),
                 html));
        }

        Iterator<SemanticObject> it = results.iterator();
        int total = (Integer) request.getAttribute("t");
        int maxr = Integer.valueOf(paramRequest.getResourceBase().getAttribute("maxResults", "10"));
        int pageNumber = 1;

        if (request.getParameter("p") != null)
            pageNumber = Integer.valueOf(request.getParameter("p"));

        int start = (pageNumber - 1) * maxr;
        int end = start + maxr - 1;
        if (end > total - 1) end = total - 1;

        String sliceUrl = paramRequest.getRenderUrl() + "?q=" + request.getParameter("q");
        SWBResourceURL byDate = paramRequest.getRenderUrl().setParameter("o", "1");
        byDate.setParameter("p", request.getParameter("p"));
        SWBResourceURL byName = paramRequest.getRenderUrl().setParameter("o", "2");
        byName.setParameter("p", request.getParameter("p"));
        String basePath = SWBPortal.getWebWorkPath()+"/models/" + paramRequest.getWebPage().getWebSite().getTitle()+"/images/";
        %>

        <script type="text/javascript">
            dojo.require("dojo.fx");
            dojo.require("dijit.ColorPalette");
            dojo.require("dijit.form.Button");            

            function expande(oId) {
                    var anim1 = dojo.fx.wipeIn( {node:oId, duration:500 });
                    var anim2 = dojo.fadeIn({node:oId, duration:500});
                    dojo.fx.combine([anim1,anim2]).play();
                    dojo.byId('toggle_link').innerHTML = "Ocultar mapa de distribuci&oacute;n";
            }

            function collapse(oId) {
                    var anim1 = dojo.fx.wipeOut( {node:oId, duration:500 });
                    var anim2 = dojo.fadeOut({node:oId, duration:600});
                    dojo.fx.combine([anim1, anim2]).play();
                    dojo.byId('toggle_link').innerHTML = "Mostrar mapa de distribuci&oacute;n";
            }

            function toggle(oId) {
                    var o = dojo.byId(oId);
                    if(o.style.display=='block' || o.style.display==''){
                            collapse(oId);
                    }else {
                            expande(oId);
                }
            }
        </script>
        <h2>M&oacute;dulos de la CURP en <i><%=request.getParameter("q")%></i></h2>
        <a id="toggle_link" href="#" onclick="toggle('map_container')">Ocultar Mapa de distribuci&oacute;n</a>
        <div id="map_container">
                <div id="map_canvas" style="border:1px solid black; width: 480px; height: 300px;"></div>
        </div>
        <br>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=SWBPortal.getEnv("key/gmap","")%>" type="text/javascript"></script>
        <script type="text/javascript">
            function createMarker(map, point, name, icon) {
                    var modulo = new GMarker(point, icon);
                    GEvent.addListener(modulo, "click", function() {
                        var myHtml = name;
                        map.openInfoWindowHtml(point, myHtml);
                 });
                 return modulo;
            }

            function initialize() {
                var myIcon = new GIcon();
                myIcon.image = '<%=basePath%>image.png';
                myIcon.printImage = '<%=basePath%>printImage.gif';
                myIcon.mozPrintImage = '<%=basePath%>mozPrintImage.gif';
                myIcon.iconSize = new GSize(25,25);
                myIcon.shadow = '<%=basePath%>shadow.png';
                myIcon.transparent = '<%=basePath%>transparent.png';
                myIcon.shadowSize = new GSize(38,25);
                myIcon.printShadow = '<%=basePath%>printShadow.gif';
                myIcon.iconAnchor = new GPoint(13,25);
                myIcon.infoWindowAnchor = new GPoint(13,0);
                myIcon.imageMap = [16,0,18,1,20,2,21,3,22,4,22,5,23,6,23,7,24,8,24,9,24,10,24,11,24,12,24,13,24,14,24,15,24,16,23,17,23,18,22,19,22,20,21,21,20,22,18,23,15,24,9,24,6,23,4,22,3,21,3,20,2,19,1,18,1,17,0,16,0,15,0,14,0,13,0,12,0,11,0,10,0,9,0,8,1,7,1,6,1,5,2,4,3,3,4,2,6,1,8,0];

                if (GBrowserIsCompatible()) {
                    var map = new GMap2(document.getElementById("map_canvas"));
                    map.addControl(new GSmallMapControl());
                    map.addControl(new GMapTypeControl());
                    var bounds = new GLatLngBounds();
                    <%
                    Iterator<GeoLocation> listit = objs.iterator();
                    while (listit.hasNext()) {
                        GeoLocation actual = listit.next();
                    %>
                        var pointer = new GLatLng(<%=actual.getLatitude()%>, <%=actual.getLongitude()%>);
                        bounds.extend(pointer);
                        map.addOverlay(createMarker(map, pointer, '<%=actual.getName()%>', myIcon));
                    <%
                    }
                %>
                }
                map.setCenter(bounds.getCenter());
                map.setZoom(map.getBoundsZoomLevel(bounds));
            }
            initialize();
    </script>
    <p>
        Mostrando resultados <b><%=start+1%></b> al <b><%=end+1%></b> de <b><%=total%></b>.
    <br>
    </p>
    <p>
        Ordenar por <a href="<%=byDate.setParameter("q", request.getParameter("q"))%>">fecha</a> | <a href="<%=byName.setParameter("q", request.getParameter("q"))%>">nombre</a>
    </p>
    <div class="entriesList">
    <%
        while(it.hasNext()) {
            SemanticObject obj = it.next();
            if (obj == null) continue;
            if (obj != null && obj.instanceOf(DirectoryObject.sclass)) {
                %>
                <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                <%
                        DirectoryObject c = (DirectoryObject) obj.createGenericInstance();
                        String title = obj.getProperty(DirectoryObject.swb_title);
                        String photo = obj.getProperty(swbcomm_dirPhoto);
                        String schedule = obj.getProperty(CURPModule.swbcomm_serviceHours);
                        String contact = obj.getProperty(CURPModule.swbcomm_contactName);
                        String tel = obj.getProperty(CURPModule.swbcomm_contactPhoneNumber);
                        if (title == null || title.equals("null")) title = "";
                        if (schedule == null || schedule.equals("null")) schedule = "";
                        if (contact == null || contact.equals("null")) contact = "";
                        if (tel == null || tel.equals("null")) tel = "";

                        if(photo != null && !photo.equals("null")) {
                        %>
                            <img height="90" width="90" src="<%=SWBPortal.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+photo%>">
                        <%
                        } else {
                        %>
                        <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/noDisponible.gif" width="95" height="95">
                        <%
                        }
                        %>
                        <div class="listEntryInfo">
                            <p class="tituloNaranja">
                                <%=title%>
                            </p>
                            <p>
                                <b>Horario: </b><%=schedule%>
                            </p>
                            <p>
                                <b>Direcci&oacute;n: </b><%=((CURPModule)c).getAddressString()%>
                            </p>
                            <p>
                                <b>Responsable: </b><%=contact%>
                            </p>
                            <p>
                                <b>Tel.: </b><%=tel%>
                            </p>
                            <br>
                            <p class="vermas"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>">Ver m&aacute;s</a></p>
                        </div>
                        <div class="clear"> </div>
                        <%
                    }
                    %>
                </div>
                <%
            }
            %>
            </div>
            <div class="listEntry"> </div>
            <p align="center">
            <%
                if (pageNumber - 1 >= 1) {
                    %>
                    <a href="<%=sliceUrl + "&p=" + (pageNumber - 1)%>">&lt;&nbsp;</a>
                    <%
                }
                double pages = Math.ceil((double) total / (double) maxr);
                    for (int i = 1; i <= pages; i++) {
                        start = maxr * (i - 1);
                        if ((start + maxr) - 1 > total - 1) {
                            end = total - 1;
                        } else {
                            end = (start + maxr) - 1;
                        }
                        if (pageNumber == i) {
                            %>
                            <span><font size="2.8"><b><%=i%></b></font></span>
                            <%
                        } else {
                            %>
                            <a href="<%=sliceUrl + "&p=" + i%>"><%=i%></a>
                            <%
                        }
                    }
                if (pageNumber + 1 <= pages) {
                    %>
                    <a href="<%=sliceUrl + "&p=" + (pageNumber + 1)%>">&nbsp;&gt;</a>
                    <%
                }
            %>
            </p>
    <%
    } else {
    %><h2>M&oacute;dulos de la CURP en <i><%=request.getParameter("q")%></i></h2>
        <br>
        <hr/>
        <p>
            No hay resultados.
        </p><%
    }
    %>
    <form action="/es/renapo3/ModulosCurp">
        <input type="submit" value="Regresar"/>
    </form>
    <%
}
%>
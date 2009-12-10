<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticClass, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SemanticProperty swbcomm_dirPhoto = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    ArrayList<SemanticObject> results = (ArrayList<SemanticObject>) request.getAttribute("results");
    ArrayList<SemanticObject> allRes = (ArrayList<SemanticObject>) request.getAttribute("allRes");
    String what = (String) request.getParameter("what");
    WebPage wpage = paramRequest.getWebPage();
    String lang = "es";
    if (paramRequest.getUser().getLanguage() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    String imgDefaultPath = "/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg";

    //System.out.println("what: " + what);
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

<script type="text/javascript">
    function setSearchCategory(what) {
        document.getElementById("what").value = what;
        var catLabel = "Todas";
        if (what == "Clasified") catLabel = "Clasificados";
        if (what == "All") catLabel = "Todas";
        if (what == "Place") catLabel = "Lugares";
        if (what == "Topic") catLabel = "Temas";
        if (what == "Member") catLabel = "Personas";
        if (what == "Service") catLabel = "Servicios";
        if (what == "Organization") catLabel = "Organizaciones";
        if (what == "Community") catLabel = "Comunidades";
        document.getElementById("catLabel").innerHTML = catLabel;
    }
</script>
<div class="twoColContent">
<div id="busquedaAvanzada">
      	<div class="buscador" id="busquedaPalabraClave">
            <form id="busquedaKeyWords" method="post" action="{topic@getUrl}/../Busqueda" >
          	    <div>
                <label for="buscadorKeywords">Busca por palabra clave</label>
                <input name="q" type="text" id="buscadorKeywords" value="B&uacute;squeda por palabra clave" >
                <label for="buscarKeywords">Buscar</label>
                <input type="submit" id="buscarKeywords" value="Buscar" />
                <input type="hidden" name="what" id="what" value="All"/>
              </div>
            </form>
          </div>
          <ul id="MenuBar3" class="MenuBarHorizontal">
	  <li class="selectTitle"><p id="catLabel">Categor&iacute;a</p>
              <ul>
	      <li><a onclick="setSearchCategory('All');">Todas</a></li>
	      <li><a onclick="setSearchCategory('Topic');">Temas</a></li>
	      <li><a onclick="setSearchCategory('Place');">Lugares</a></li>
              <li><a onclick="setSearchCategory('Member');">Personas</a></li>
              <li><a onclick="setSearchCategory('Service');" >Servicios</a></li>
              <li><a onclick="setSearchCategory('Organization');">Organizaciones</a></li>
              <li><a onclick="setSearchCategory('Clasified');">Clasificados</a></li>
              <li><a onclick="setSearchCategory('Community');">Comunidades</a></li>
              </ul>
	  </li>
	</ul>
        </div>
</div>
<script type="text/javascript">
    var MenuBar3 = new Spry.Widget.MenuBar("MenuBar3");
</script>
<br>
<%
if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
    if (results != null && results.size() > 0) {

        String resultType = "";
        //Get all DirectoryObject's GeoLocations as arrayList
        ArrayList<GeoLocation> objs = new ArrayList<GeoLocation>();
        Iterator<SemanticObject> soit = allRes.iterator();
        while (soit.hasNext()) {
            SemanticObject so = soit.next();
            if (so.getSemanticClass().isSubClass(Geolocalizable.swb_Geolocalizable)) {                
                DirectoryObject dob = (DirectoryObject) so.createGenericInstance();
                so.getProperty(Addressable.swbcomm_streetName);
                String html = "<b><font color=\"blue\">" + dob.getTitle() + "</font></b><br>" +
                    getAddressString(so) +
                    "<br><b>Contacto:</b> " + so.getProperty(Contactable.swbcomm_contactName) +
                    "<br><b>Teléfono:</b> " + so.getProperty(Contactable.swbcomm_contactPhoneNumber);
                //System.out.println(html);
                objs.add(new GeoLocation(
                    so.getDoubleProperty(Geolocalizable.swb_latitude),
                    so.getDoubleProperty(Geolocalizable.swb_longitude),
                    so.getIntProperty(Geolocalizable.swb_geoStep),
                    html));
            }
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

        SWBResourceURL sliceUrl = paramRequest.getRenderUrl();
        sliceUrl.setParameter("q", request.getParameter("q"));
        sliceUrl.setParameter("what", request.getParameter("what"));
        SWBResourceURL byDate = paramRequest.getRenderUrl().setParameter("o", "1");
        byDate.setParameter("p", request.getParameter("p"));
        byDate.setParameter("what", request.getParameter("what"));
        SWBResourceURL byName = paramRequest.getRenderUrl().setParameter("o", "2");
        byName.setParameter("p", request.getParameter("p"));
        byName.setParameter("what", request.getParameter("what"));
        SWBResourceURL byPrice = paramRequest.getRenderUrl().setParameter("o", "3");
        byPrice.setParameter("p", request.getParameter("p"));
        byPrice.setParameter("what", request.getParameter("what"));
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
                    } else {
                            expande(oId);
                }
            }
        </script>

        <h3>Resultados de la b&uacute;squeda <i><%=request.getParameter("q")%></i></h3>
        <%
        if (what != null && what.trim().equals("Organization")) {
            %>
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
                            map.addOverlay(createMarker(map, pointer, '<%=actual.getName()%>'/*, myIcon*/));
                        <%
                        }
                    %>
                    }
                    map.setCenter(bounds.getCenter());
                    map.setZoom(map.getBoundsZoomLevel(bounds));
                }
                initialize();
            </script>
        <%
        }
        %>
        <p>
            Mostrando resultados <b><%=start+1%></b> al <b><%=end+1%></b> de <b><%=total%></b>.
        </p>
        <p align="right">
            Ordenar por <a href="<%=byDate.setParameter("q", request.getParameter("q"))%>">fecha</a> | <a href="<%=byName.setParameter("q", request.getParameter("q"))%>">nombre</a>
        </p>
        <div class="entriesList">
        <%
            while(it.hasNext()) {
                SemanticObject obj = it.next();
                if (obj == null) continue;
                if (obj.instanceOf(MicroSite.sclass)) {
                    resultType = "Comunidad";
                    MicroSite site = (MicroSite)obj.createGenericInstance();
                    Iterator<Member> members = site.listMembers();
                    int count = 0;
                    while (members != null && members.hasNext()) {
                        count++;
                        members.next();
                    }
                    String imgPath = imgDefaultPath;
                    //String imgPath = "/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg";
                    if (site.getPhoto() != null)
                    {
                        imgPath = SWBPortal.getContextPath() + SWBPortal.getWebWorkPath() + site.getPhoto();
                    }
                    %>
                    <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                        <img alt="<%=site.getTitle()%>" src="<%=imgPath%>" height="95" width="95"/>
                        <div class="listEntryInfo">
                            <p class="tituloRojo">
                                <%=site.getTitle()%> (<%=resultType%>)
                            </p>
                            <p>
                                <%=site.getDescription()%>
                            </p>
                            <p>
                                <span class="itemTitle">Miembros: </span><%=count%>
                            </p>
                            <p class="vermas">
                                <a href="<%=site.getUrl()%>">Ver comunidad</a>
                            </p>
                        </div>
                        <div class="clear">&nbsp;</div>
                    </div>
                    <%
                } else if (obj.instanceOf(User.sclass)) {
                    resultType = "Persona";
                    User usr = (User)obj.createGenericInstance();
                    String photo = imgDefaultPath;//SWBPortal.getContextPath() + "/swbadmin/images/defaultPhoto.jpg";
                    if(usr.getPhoto() != null) photo = SWBPortal.getWebWorkPath() + usr.getPhoto();
                    HashMap<String, SemanticProperty> extProperties = new HashMap<String, SemanticProperty>();
                    Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
                    while (list != null && list.hasNext()) {
                        SemanticProperty sp = list.next();
                        extProperties.put(sp.getName(), sp);
                    }
                    String usr_sex = (String) usr.getExtendedAttribute(extProperties.get("userSex"));
                    int usr_age = (Integer) usr.getExtendedAttribute(extProperties.get("userAge"));
                    //if (null == usr_age) usr_age = "";
                    if (usr_sex.equals("M")) usr_sex = "Hombre";
                    if (usr_sex.equals("F")) usr_sex = "Mujer";

                    String perfilPath = wpage.getWebSite().getWebPage("perfil").getUrl();
                    String profile = "<a href=\"" + perfilPath + "?user=" + usr.getEncodedURI() + "\">Ver perfil</a>";
                %>
                    <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                    <img alt="<%=usr.getLogin()%>"src="<%=photo%>" height="95" width="95"/>
                    <div class="listEntryInfo">
                        <p class="tituloRojo">
                            <%=usr.getFullName()%>
                        </p>
                        <%
                        if (paramRequest.getUser().isRegistered() && paramRequest.getUser().isSigned()) {
                        %>
                            <p>
                                <%=usr.getFullName()%>
                            </p>
                            <p>
                                <span class="itemTitle">Sexo: </span><%=usr_sex%>
                            </p>
                            <p>
                                <span class="itemTitle">Edad: </span><%=usr_age%>
                            </p>
                            <p class="vermas">
                                <%=profile%>
                            </p>
                        <%
                        } else {
                        %>
                            <p>Registrese para ver los datos del usuario</p>
                        <%
                        }
                        %>
                    </div>
                    <div class="clear">&nbsp;</div>
                    </div>
                <%
                } else if (obj.instanceOf(DirectoryObject.sclass)) {
                    resultType = "Clasificado";
                    if (obj.instanceOf(Organization.sclass)) {
                        resultType = "Organizaci&oacute;n";
                    }
                    %>
                    <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                    <%
                    DirectoryObject c = (DirectoryObject) obj.createGenericInstance();
                    String photo = imgDefaultPath; //obj.getProperty(swbcomm_dirPhoto);
                    if (obj.getProperty(swbcomm_dirPhoto) != null) {
                        photo = SWBPortal.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+obj.getProperty(swbcomm_dirPhoto);
                    }
                    %>
                        <img height="95" alt="<%=c.getTitle()%>" width="95" src="<%=photo%>" />
                        <div class="listEntryInfo">
                        <p class="tituloRojo"><%=c.getTitle()%>&nbsp;(<%=resultType%>)</p>
                        <p>
                            <b><%=(c.getDescription()==null)?"":c.getDescription()%></b>
                        </p>
                        <%
                        if (obj.instanceOf(Addressable.swbcomm_Addressable)) {
                            %>
                            <p>
                                <%=(getAddressString(obj).trim().equals(""))?"":getAddressString(obj)%>
                            </p>
                            <%
                        }

                        if (obj.instanceOf(Contactable.swbcomm_Contactable)) {
                            SemanticClass obclass = obj.getSemanticClass();
                            String email = obj.getProperty(obclass.getProperty("contactEmail"));
                            String phone = obj.getProperty(obclass.getProperty("contactPhoneNumber"));
                            String name = obj.getProperty(obclass.getProperty("contactName"));
                            %>
                            <p>
                                <%=(phone==null)?"":"Tel.: " + phone%>
                            </p>
                            <p>
                                <%=(name==null)?"":"Contacto: " + name + ((email==null)?"":"[" + email + "]")%>
                            </p>
                            <%
                        }
                        %>
                        <p><%=(c.getTags()==null?"":"Palabras clave: " + c.getTags())%></p>
                       
                        <p class="vermas"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>">Ver mas</a></p>
                    </div>
                    <div class="clear"> </div>
                    </div>
                    <%
                }
                %>
            <%
        }
        %>
        </div>
        <div class="listEntry"> </div>
        <p align="center"> P&aacute;gina (
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
        )
        </p>
        <form action="#">
            <input type="submit" value="Regresar" onclick="history.go(-1)"/>
        </form>
        <%
    } else if (request.getParameter("q") != null && !request.getParameter("q").equals("")) {
        %><h3>Resultados de la b&uacute;squeda <i><%=request.getParameter("q")%></i></h3>
        <br>
        <hr/>
        <p>
            No hay resultados.
        </p><%
    }
    %>
    
    <%
}
%>

<%!
    private String getAddressString(SemanticObject o) {
        String streetName = o.getProperty(Addressable.swbcomm_streetName);
        if (streetName == null || streetName.equals("null")) {
            streetName = "";
        } else {
            streetName = streetName + " ";
        }
        String intNumber = o.getProperty(Addressable.swbcomm_intNumber);
        if (intNumber == null || intNumber.equals("null")) {
            intNumber = "";
        } else {
            intNumber = " interior " + intNumber + ", ";
        }

        String extNumber = o.getProperty(Addressable.swbcomm_extNumber);
        if (extNumber == null || extNumber.equals("null")) {
            extNumber = "";
        } else {
            extNumber = " exterior " + extNumber + ",<br>";
        }
        String council = o.getProperty(Addressable.swbcomm_cityCouncil);
        if (council == null || council.equals("null")) council = "";
        String city = o.getProperty(Addressable.swbcomm_city);
        if (city == null || city.equals("null")) {
            city = "";
        } else {
            city = ", " + city + " ";
        }
        String state = o.getProperty(Addressable.swbcomm_state);
        if (state == null || state.equals("null")) state = "";

        return streetName + intNumber + extNumber + council + city + state;
    }
%>
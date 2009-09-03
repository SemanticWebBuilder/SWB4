<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
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
     String perfilPath=paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
        String friendsPath=paramRequest.getWebPage().getWebSite().getWebPage("Amigos").getUrl();
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null)
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!user.isRegistered()) return;
        List<GeoLocation> lista = new ArrayList<GeoLocation>();
        WebPage wpage=paramRequest.getWebPage();
        String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
        {
        String imgSize="width=\"80\" height=\"70\"";
        
        boolean isStrategy=false;
        isStrategy=true;
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) 
        {            
            imgSize="width=\"39\" height=\"39\"";            
        }
     %>
          <div class="miembros">
          <%if(owner==user){%>
            <h2>Mis Amigos</h2>
          <%}else{%> <h2>Amigos de <%=user.getFirstName()%></h2> <%}%>

             <%
             String firstName="", lastName="";
             int contTot=0;
             Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user,wpage.getWebSite());
             while(itMyFriends.hasNext()){
                 Friendship friendShip=itMyFriends.next();
                 Iterator<User> itfriendUser=friendShip.listFriends();
                 while(itfriendUser.hasNext()){
                     User friendUser=itfriendUser.next();
                     if(!friendUser.getURI().equals(user.getURI()))
                     {
                         if(friendUser.getPhoto()!=null) photo=friendUser.getPhoto();                         
                         %>
                            <div class="moreUser">
                            <a href="<%=perfilPath%>?user=<%=friendUser.getEncodedURI()%>"><img alt="Foto de <%=friendUser.getFullName()%>" src="<%=SWBPlatform.getWebWorkPath()+photo%>" <%=imgSize%> title="<%=friendUser.getFullName()%>">
                            <%if(!isStrategy){%>
                                <br>
                                <%=firstName%>
                                <%=lastName%>
                            <%}%>
                            </a>
                         </div>
                         <%
                         contTot++;
                         if(isStrategy && contTot==18) break;
                     }
                 }
             }
             if(isStrategy && contTot>=18){%>
                 <div class="clear">
                    <p class="vermas"><a href="<%=friendsPath%>" >Ver todos</a></p>
                 </div>
             <%}else if(contTot==0){%>
               <div class="clear">
                <p class="titulo">Aún no tienes amigos &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
               </div>
             <%}%>
             <div class="clear"></div>
          </div>

<%
}
else
{
            %>
            <div class="miembros">
            <%
            GeoLocation userLoc = null;
            if (user.getSemanticObject().getDoubleProperty(Geolocalizable.swb_latitude)!=0D) {
                 userLoc = new GeoLocation(
                         user.getSemanticObject().getDoubleProperty(Geolocalizable.swb_latitude),
                         user.getSemanticObject().getDoubleProperty(Geolocalizable.swb_longitude),
                         user.getSemanticObject().getIntProperty(Geolocalizable.swb_geoStep),
                         user.getFullName());
                 } else {
                 userLoc = new GeoLocation(22.99885, -101.77734,4,user.getFullName());
                 }
            HashMap<String,SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
            Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
            while(list.hasNext())
            {
                SemanticProperty prop=list.next();
                mapa.put(prop.getName(), prop);
            }
            Iterator<Friendship> itMyFriends=Friendship.listFriendshipByFriend(user,wpage.getWebSite());
             while(itMyFriends.hasNext())
             {
                 Friendship friendShip=itMyFriends.next();
                 Iterator<User> itfriendUser=friendShip.listFriends();
                 while(itfriendUser.hasNext())
                 {
                     User friendUser=itfriendUser.next();
                     if(!friendUser.getURI().equals(user.getURI()))
                     {
                         String usr_sex = (String)friendUser.getExtendedAttribute(mapa.get("userSex"));
                         Object usr_age = (Object)friendUser.getExtendedAttribute(mapa.get("userAge"));
                         if (null==usr_age) usr_age = "";
                         if ("M".equals(usr_sex)) usr_sex = "Hombre";
                         if ("F".equals(usr_sex)) usr_sex = "Mujer";
                         String usr_status = (String)friendUser.getExtendedAttribute(mapa.get("userStatus"));
                         if (null==usr_status) usr_status = "";
                         if(friendUser.getPhoto()!=null) photo=friendUser.getPhoto();
                         if(friendUser.getSemanticObject().getDoubleProperty(Geolocalizable.swb_latitude)!=0D) {
                             lista.add(new GeoLocation(
                                     friendUser.getSemanticObject().getDoubleProperty(Geolocalizable.swb_latitude),
                                     friendUser.getSemanticObject().getDoubleProperty(Geolocalizable.swb_longitude),
                                     friendUser.getSemanticObject().getIntProperty(Geolocalizable.swb_geoStep),
                                     friendUser.getFullName()));
                         }
                         %>
                         
                        <div class="profilePic" onMouseOver="this.className='profilePicHover'" onMouseOut="this.className='profilePic'">
                    <img src="<%=SWBPlatform.getWebWorkPath()+photo%>" width="150" height="150" alt="Foto de <%=friendUser.getFullName()%>" />
                    <p><a class="contactos_nombre" href="/swb/Ciudad_Digital/perfil?user=http%3A%2F%2Fuser.Ciudad_Digital.swb%23swb_User%3A5"><%=friendUser.getFullName()%></a></p>
                    <p>Edad: <%=usr_age%></p>
                    <p>Sexo: <%=usr_sex%></p>
                    <p>Tipo: <%=usr_status%></p>
                    </div>
                        
                         <%
                     }
                 }
             }
    %>
            </div>
            <div id="map_canvas" style="width: 500px; height: 300px"></div>
            <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=SWBPlatform.getEnv("key/gmap","")%>"
            type="text/javascript"></script>
    <script type="text/javascript">
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
            map.addControl(new GSmallMapControl());
            map.addControl(new GMapTypeControl());
        var center = new GLatLng(<%=userLoc.getLatitude()%>, <%=userLoc.getLongitude()%>);
        map.setCenter(center, <%=userLoc.getStep()%>);
        var marker = new GMarker(center, {draggable: false});
        map.addOverlay(marker);
        marker.openInfoWindow(
        document.createTextNode("<%=userLoc.getName()%>"));
        <%
        Iterator<GeoLocation> listit = lista.iterator();
        while (listit.hasNext()){
            GeoLocation actual = listit.next();
            %>
            var point = new GLatLng(<%=actual.getLatitude()%>, <%=actual.getLongitude()%>);
            var marker = new GMarker(point, {draggable: false});
            map.addOverlay(marker);
            marker.openInfoWindow(
            document.createTextNode("<%=actual.getName()%>"));
            <%
        }
        %>
      }
    }
initialize();
    </script>
            <%

}
%>
             
<%-- 
    Document   : streamMapQuery
    Created on : 23-ene-2014, 11:40:32
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
<%!
    String DEFECT_DATE="2013-09-17";
%>


<%
try{
    //String apiKey=SWBSocialUtil.Util.getModelPropertyValue(SWBSocialUtil.getConfigWebSite(), "GoogleMapsApiKey");
    /*
    if(apiKey==null){
        out.println("Error:No se puede mostrar el mapa debido a que la llave de Google Maps no esta configurada(GoogleMapsApiKey), contactese con su administrador");
        return; 
    }*/
    String suri=request.getParameter("suri");
    //System.out.println("suri:"+suri);
    if(suri==null) {
        return;
    }
    SemanticObject semObj=SemanticObject.getSemanticObject(suri);
    if(semObj==null) return;
    User user=paramRequest.getUser(); 
    
    String stateMsg=SWBSocialResUtil.Util.getStringFromGenericLocale("state", user.getLanguage());
    String positivesMsg=SWBSocialResUtil.Util.getStringFromGenericLocale("positives", user.getLanguage());
    String negativesMsg=SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", user.getLanguage());
    String neutralMsg=SWBSocialResUtil.Util.getStringFromGenericLocale("neutrals", user.getLanguage());
    
    
    int streamMapView=1;
    //System.out.println("streamMapView:"+request.getParameter("streamMapView"));
    if(request.getParameter("streamMapView")!=null && !request.getParameter("streamMapView").isEmpty())
    {
            streamMapView=Integer.parseInt(request.getParameter("streamMapView"));
    }
    //System.out.println("streamMapView Final:"+streamMapView);
    
    SWBResourceURL urlDetails = paramRequest.getRenderUrl().setMode("showMarkDet");
    urlDetails.setCallMethod(SWBResourceURL.Call_DIRECT);
    
    Date date = null;
    String showSinceDate=DEFECT_DATE;  //Día en que estaba desarrollando esto. Nunca los post de los usuarios van a ser antes de esta fecha, ya que no se  ha liberado el producto aún
    //System.out.println("showSinceDate/1:"+showSinceDate+",param:"+request.getParameter("mapSinceDate"+semObj.getId()));  
    if(request.getParameter("mapSinceDate"+semObj.getId())!=null && request.getParameter("mapSinceDate"+semObj.getModel().getName() + semObj.getId()).trim().length()>0)
    {
        showSinceDate=request.getParameter("mapSinceDate"+semObj.getModel().getName() + semObj.getId())+"T00:00:00Z";        
    }
    //System.out.println("showSinceDate:"+showSinceDate);
    String showGeoProfile="off";
    if(request.getParameter("showGeoProfile")!=null) showGeoProfile=request.getParameter("showGeoProfile"); 
    System.out.println("showGeoProfile:"+showGeoProfile);
    
    //System.out.println("streamMapView K llega:"+streamMapView);
    //System.out.println("showGeoProfile K llega:"+showGeoProfile);
    /*
    SimpleDateFormat formatoDelTexto=null;
    try {
        formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        date = formatoDelTexto.parse(showSinceDate);
    } catch (ParseException ex) {
        ex.printStackTrace();
    } */   
   //System.out.println("dateG:"+date.toString());
    
    String[] selectedNetworks = request.getParameterValues("networks");
    ArrayList<String> networks = new ArrayList<String>();

    if(selectedNetworks != null && selectedNetworks.length > 0){
        for(String net: selectedNetworks){
            try{
                networks.add(net);
            }catch(Exception e){}
        }
    }    
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>SWBSocial Sentiment Analysis Map</title>
    <!--AIzaSyA_8bWaWXaKlJV2XgZt-RYwRAsp6S0J7iw-->
    <!--src="http://maps.googleapis.com/maps/api/js?sensor=false"+apiKey>  -->
    <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?sensor=false">
    </script>
    <script type="text/javascript">
      document.write('<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/js/markermanager.js"><' + '/script>');
    </script>
    <script type="text/javascript">
    //<![CDATA[

    var ICONS = [];
    var map = null;
    var mgr = null;
    var geocoder;
    
    function setupMap() {
      geocoder=new google.maps.Geocoder();   
      var myOptions = {
        zoom: 5,
        //center: new google.maps.LatLng(48.25, 11.00),
        center: new google.maps.LatLng(25, -99),
        mapTypeId: google.maps.MapTypeId.HYBRID
      };
      map = new google.maps.Map(document.getElementById('map<%=suri%>'), myOptions);

      showMarkers();
            
    }

    function getMarkers(n) {
      var batch = [];
      <%
        WebSite wsite=null;
        String query=null;
        String queryGeoProfile=null;
        ArrayList aPostInsNotInStates=new ArrayList();
        HashMap<String, HashMap> hmapPoints=new HashMap();
        if(semObj.getGenericInstance() instanceof Stream) 
        {
            //System.out.println("Map es Stream");
            Stream stream=(Stream)semObj.getGenericInstance();
            wsite=stream.getSocialSite();
            if(streamMapView==1) query=getStreamPI(stream, showSinceDate, true, false, -1); //Contadores
            else if(streamMapView==2){ //Mensajes
                query=getStreamPI(stream, showSinceDate, false, true, -1);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getStreamPIGeoProfile(stream, showSinceDate, -1);
            }else if(streamMapView==4){  //Todo
                query=getStreamPI(stream, showSinceDate, false, true, -1);
                //System.out.println("Fue 4, query:"+query+",showGeoProfile:"+showGeoProfile);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getStreamPIGeoProfile(stream, showSinceDate, -1);
            }else if(streamMapView==5){  //Positivos
                query=getStreamPI(stream, showSinceDate, false, true, 1);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getStreamPIGeoProfile(stream, showSinceDate, 1);
            }else if(streamMapView==6) { //Negativos 
                query=getStreamPI(stream, showSinceDate, false, true, 2);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getStreamPIGeoProfile(stream, showSinceDate, 2);
            }else if(streamMapView==7) { //Neutros 
                query=getStreamPI(stream, showSinceDate, false, true, 0);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getStreamPIGeoProfile(stream, showSinceDate, 0);
            }       
        }else if(semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic=(SocialTopic) semObj.getGenericInstance();
            wsite=socialTopic.getSocialSite();
            if(streamMapView==1)query=getTopicPI(socialTopic, showSinceDate, true, false, -1);    //Contadores
            else if(streamMapView==2){ //Mensajes
                query=getTopicPI(socialTopic, showSinceDate, false, true, -1);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getTopicPIGeoProfile(socialTopic, showSinceDate, -1);
            }
            else if(streamMapView==4){  //Todo
                query=getTopicPI(socialTopic, showSinceDate, false, true, -1);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getTopicPIGeoProfile(socialTopic, showSinceDate, -1);
            }     
            else if(streamMapView==5){  //Positivos
                query=getTopicPI(socialTopic, showSinceDate, false, true, 1);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getTopicPIGeoProfile(socialTopic, showSinceDate, 1);
            }      
            else if(streamMapView==6){  //Negativos 
                query=getTopicPI(socialTopic, showSinceDate, false, true, 2);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getTopicPIGeoProfile(socialTopic, showSinceDate, 2);
            }      
            else if(streamMapView==7){   //Negativos 
                query=getTopicPI(socialTopic, showSinceDate, false, true, 0);
                //GeoUser Location
                if(showGeoProfile.equals("on")) queryGeoProfile=getTopicPIGeoProfile(socialTopic, showSinceDate, 0);
            }     
        }
        //System.out.println("Aqui 1:"+query);
        HashMap postInsResult=new HashMap();
        ArrayList<PostIn> aPostIns=SWBSocial.executeQueryArray(query, wsite);
        ArrayList<PostIn> aPostInsGeoProfile=new ArrayList();
        if(queryGeoProfile!=null) aPostInsGeoProfile=SWBSocial.executeQueryArray(queryGeoProfile, wsite);
        //System.out.println("aPostInsGeoProfile Tamano:"+aPostInsGeoProfile.size());
        for (PostIn x : aPostInsGeoProfile){
            //System.out.println("X..Geo:"+x.getMsg_Text());
            if (!aPostIns.contains(x)){
               aPostIns.add(x);
            }
        }
        int cont1=0;
        int cont2=0;
        Iterator<PostIn> itPostIns=aPostIns.iterator();
        while(itPostIns.hasNext())
        {
            PostIn postIn=itPostIns.next(); 
            if(networks.isEmpty() || networks.contains(postIn.getPostInSocialNetwork().getURI()))
            {
                if(streamMapView==1 || streamMapView==4)
                {
                    //System.out.println("postIn a agregar a estados:"+postIn+",mSG:"+postIn.getMsg_Text()+"State:"+postIn.getGeoStateMap()); 
                    hmapPoints=getStateCounters(postIn, hmapPoints);
                    //System.out.println("hmapPoints:"+hmapPoints.size());
                }
                if(streamMapView==2 || streamMapView>=4)
                {
                    if(postIn.getLatitude()!=0 && postIn.getLongitude()!=0)
                    {
                        cont1++;
                        String msg=SWBSocialResUtil.Util.replaceSpecialCharacters(postIn.getMsg_Text().replaceAll("'", ""), false);
                        %>
                           var tmpIcon=null;
                        <%        
                        if(postIn.getPostSentimentalType()==1)
                        {
                            %>
                               tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/greenGMapMarker.png');
                            <%
                        }else if(postIn.getPostSentimentalType()==2)
                        { 
                            %>
                                tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/redGMapMarker.png');
                            <%
                        }else if(postIn.getPostSentimentalType()==0)
                        {
                            %>
                                tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/whiteGMapMarker.png');
                            <%
                        } 
                            %>
                            //alert("<%=postIn%>");
                            var marker = new google.maps.Marker({
                            position: new google.maps.LatLng(<%=postIn.getLatitude()%>,<%=postIn.getLongitude()%>),
                            icon: tmpIcon, 
                            title: '<%=msg%>',
                            map: map
                            });
                            associateInfoWindows(marker, '<%=postIn.getEncodedURI()%>', '<%=semObj.getEncodedURI()%>');
                            batch.push(marker);  
                        <%      
                    }else if(postIn.getPostInSocialNetworkUser()!=null && postIn.getPostInSocialNetworkUser().getSnu_profileGeoLocation()!=null && !postIn.getPostInSocialNetworkUser().getSnu_profileGeoLocation().isEmpty())
                    {    
                        //Pinta puntos por GeoUser Location
                        cont2++;
                        //System.out.println("Punto Pintado por GeoUser Location:"+postIn.getPostInSocialNetworkUser().getSnu_profileGeoLocation());
                        %>
                        var tmpIcon=null;
                        <%        
                        if(postIn.getPostSentimentalType()==1)
                        {
                            if(streamMapView!=6)
                            {
                            %>
                               tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/greenGMapMarker.png');
                            <%
                            }
                        }else if(postIn.getPostSentimentalType()==2)
                        {
                            if(streamMapView!=5)
                            {
                            %>
                                tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/redGMapMarker.png');
                            <%
                            }
                        }else if(postIn.getPostSentimentalType()==0)
                        {
                            if(streamMapView!=5 && streamMapView!=6)
                            {
                        %>
                                tmpIcon = new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/whiteGMapMarker.png');
                        <%
                            }
                        } 
                        %>
                        //alert("tmpIcon:"+tmpIcon);         
                        if(tmpIcon!=null)
                        {
                            var postLocation='<%=SWBSocialResUtil.Util.replaceSpecialCharacters(postIn.getPostInSocialNetworkUser().getSnu_profileGeoLocation().replaceAll("'", ""), false)%>';
                            var title='<%=postIn.getMsg_Text()!=null?SWBSocialResUtil.Util.replaceSpecialCharacters(postIn.getMsg_Text().replaceAll("'", ""), false):postIn.getTags()!=null?SWBSocialResUtil.Util.replaceSpecialCharacters(postIn.getTags().replaceAll("'", ""), false):"Sin Mensaje.."%>';
                            //alert("title:"+title);
                             geocoder.geocode( { 'address': postLocation}, function(results, status) { 
                                if(status==google.maps.GeocoderStatus.OK){
                                    var marker =new google.maps.Marker({
                                    map: map,
                                    position: new google.maps.LatLng(results[0].geometry.location.lat(),results[0].geometry.location.lng()),
                                    icon: tmpIcon,
                                    title: 'By GeoUser Profile:'+title
                                     });
                                    associateInfoWindows(marker, '<%=postIn.getEncodedURI()%>', '<%=semObj.getEncodedURI()%>');
                                    batch.push(marker);  
                                }//else{
                                   // alert("Esa dirección no existe:"+postLocation);
                                //}
                            });
                        }
                        <%
                    }                         
                }
            }
        }
        //System.out.println("cont1:"+cont1+",cont2:"+cont2);
        //System.out.println("Aqui 3");
        //Barre los postIns dentro de los estados
        if(!hmapPoints.isEmpty())
        {
            Iterator<String> itMapStates=hmapPoints.keySet().iterator();
            while(itMapStates.hasNext())
            {
                String state=itMapStates.next();
                CountryState countryState=CountryState.ClassMgr.getCountryState(state, SWBSocialUtil.getConfigWebSite()); 
                if(countryState!=null)
                {
                    HashMap<String, Integer> sentimentHash=hmapPoints.get(state); 

                    int positiveNumber=0;
                    int negativeNumber=0;
                    int neutralNumber=0;
                    if(sentimentHash.containsKey("P"))
                    {
                        positiveNumber=((Integer)sentimentHash.get("P")).intValue(); 
                    }
                    if(sentimentHash.containsKey("N"))
                    {
                        negativeNumber=((Integer)sentimentHash.get("N")).intValue();
                    }
                    if(sentimentHash.containsKey("Ne"))
                    {
                        neutralNumber=((Integer)sentimentHash.get("Ne")).intValue();
                    }
                    //System.out.println("countryState J-5/state:"+state+",positiveNumber:"+positiveNumber+",negativeNumber:"+negativeNumber+",neutralNumber:"+neutralNumber);


                    if((neutralNumber>positiveNumber && neutralNumber>negativeNumber) || (positiveNumber==negativeNumber))
                    {

                        %>
                        //var tmpIcon = getSentimentIcon("sad");
                        batch.push(new google.maps.Marker({
                            position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                            icon: new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/yellowPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                            title: '<%=stateMsg%>:<%=countryState.getDisplayTitle(user.getLanguage())%>, <%=positivesMsg%>:'+<%=positiveNumber%>+', <%=negativesMsg%>:<%=negativeNumber%>'+', <%=neutralMsg%>:<%=neutralNumber%>'
                        })
                        );
                        <%  
                    }else if(positiveNumber>negativeNumber)
                    {
                        %>
                        //var tmpIcon = new google.maps.MarkerImage('/images/glad.png');
                        //alert("tmpIcon:"+tmpIcon);
                        batch.push(new google.maps.Marker({
                            position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                            icon: new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/greenPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                            title: '<%=stateMsg%>:<%=countryState.getDisplayTitle(user.getLanguage())%>, <%=positivesMsg%>:'+<%=positiveNumber%>+', <%=negativesMsg%>:<%=negativeNumber%>'+', <%=neutralMsg%>:<%=neutralNumber%>'
                        })
                        );
                        <%
                    }else if(negativeNumber>positiveNumber)
                    {
                        %>
                        //var tmpIcon = getSentimentIcon("sad");
                        batch.push(new google.maps.Marker({
                            position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                            icon: new google.maps.MarkerImage('<%=SWBPortal.getContextPath()%>/swbadmin/css/images/redPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                            title: '<%=stateMsg%>:<%=countryState.getDisplayTitle(user.getLanguage())%>, <%=positivesMsg%>:'+<%=positiveNumber%>+', <%=negativesMsg%>:<%=negativeNumber%>'+', <%=neutralMsg%>:<%=neutralNumber%>'
                        })
                        );
                        <%
                     }
                }
            }
        }
      %>
       return batch;
    }          
    //Crea los escuchas de los eventos "click", para cada marcador del mapa
    function associateInfoWindows(marker, postInUri, suri) {
        google.maps.event.addListener(marker, 'click', function() {
            var infoWin = null;
            var request = false;
            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }
            var url = "<%=urlDetails%>";
            url += "?postUri=" + postInUri;
            url += "&suri=" + suri;
            request.open('POST', url, true);
            request.onreadystatechange = function() {
                if ((request.readyState == 4) && (request.status == 200)) {
                    infoWin = new google.maps.InfoWindow({
                        content: request.responseText
                    });
                    infoWin.open(map, marker);
                }
            }
            request.send();
        });

    }

    function showMarkers() {
      mgr = new MarkerManager(map);
      
      google.maps.event.addListener(mgr, 'loaded', function(){
          mgr.addMarkers(getMarkers(), 18);
          mgr.addMarkers(getMarkers(), 3);
          mgr.addMarkers(getMarkers(), 0);
          mgr.refresh();          
      });      
    }
    //]]>
    </script>
  </head>

    <body onload="setupMap()">
    <div id="map<%=suri%>" style="margin: 5px auto; width: 100%; height: 100%"></div>
    <div style="text-align: center; font-size: large;">
      SWBSocial Sentiment Analysis Map
    </div>
  </body>
</html>
            
<%
}catch(Exception e){System.out.println("Error:"+e.getMessage());e.printStackTrace();}
%>
            
            
<%!
    private String getStreamPI(Stream stream, String date, boolean checkGeoState, boolean checkLatLng, int sentiment2Check)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n" +
           "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
           "\n";
           query+="select * \n";    //Para Gena
           query+=
           "where { \n" +
           "  ?postUri social:postInStream <"+ stream.getURI()+">. \n";
           if(checkGeoState)  query+=" ?postUri social:geoStateMap ?geoState. \n";
           if(checkLatLng){
           query+=" ?postUri social:latitude ?latitude. \n" +
           " ?postUri social:longitude ?longitude. \n";
           }
           if(sentiment2Check>-1){
               query+=" ?postUri social:postSentimentalType ?sentType. \n" +
               " FILTER (?sentType="+sentiment2Check+"). \n"; 
           }
           
           query+=" ?postUri social:pi_created ?postInCreated. \n";
           if(!date.equals(DEFECT_DATE)) query+= "  FILTER(xsd:dateTime(?postInCreated) >= xsd:dateTime(\""+date+"\")) \n";
           query+="  }\n";
           
           //System.out.println("Query:"+query);
        return query;
    }
    
    private String getTopicPI(SocialTopic socialTopic, String date, boolean checkGeoState, boolean checkLatLng, int sentiment2Check)
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n" +
           "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
           "\n";
           query+="select * \n";    //Para Gena
           query+=
           "where { \n" +
           "  ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n";
           if(checkGeoState)  query+=" ?postUri social:geoStateMap ?geoState. \n";
           if(checkLatLng){
           query+=" ?postUri social:latitude ?latitude. \n" +
           " ?postUri social:longitude ?longitude. \n";
           }
           if(sentiment2Check>-1){
               query+=" ?postUri social:postSentimentalType ?sentType. \n" +
               " FILTER (?sentType="+sentiment2Check+"). \n"; 
           }
           
           query+=" ?postUri social:pi_created ?postInCreated. \n";
           //System.out.println("date a buscar:"+date);
           if(!date.equals(DEFECT_DATE)) query+= "  FILTER(xsd:dateTime(?postInCreated) >= xsd:dateTime(\""+date+"\")) \n";
           query+="  }\n";
        return query;
    }
    
    private String getStreamPIGeoProfile(Stream stream, String date, int sentiment2Check) 
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n" +
           "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
           "\n";
           query+="select * \n";    //Para Gena
           query+=
           "where { \n" +
           " ?postUri social:postInStream <"+ stream.getURI()+">. \n" +
           " ?postUri social:postInSocialNetworkUser ?socialNetUser. \n" +
           " ?socialNetUser social:snu_profileGeoLocation ?usrGeoLoc. \n";
           if(sentiment2Check>-1){
               query+=" ?postUri social:postSentimentalType ?sentType. \n" +
               " FILTER (?sentType="+sentiment2Check+"). \n"; 
           }
           
           query+=" ?postUri social:pi_created ?postInCreated. \n";
           if(!date.equals(DEFECT_DATE)) query+= "  FILTER(xsd:dateTime(?postInCreated) >= xsd:dateTime(\""+date+"\")) \n";
           query+="  }\n";
        return query;
    }
    
    
    private String getTopicPIGeoProfile(SocialTopic socialTopic, String date, int sentiment2Check) 
    {
        String query=
           "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
           "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#> \n" +
           "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
           "\n";
           query+="select * \n";    //Para Gena
           query+=
           "where { \n" +
           " ?postUri social:socialTopic <"+ socialTopic.getURI()+">. \n" +
           " ?postUri social:postInSocialNetworkUser ?socialNetUser. \n" +
           " ?socialNetUser social:snu_profileGeoLocation ?usrGeoLoc. \n";
           if(sentiment2Check>-1){
               query+=" ?postUri social:postSentimentalType ?sentType. \n" +
               " FILTER (?sentType="+sentiment2Check+"). \n"; 
           }
           
           query+=" ?postUri social:pi_created ?postInCreated. \n";
           if(!date.equals(DEFECT_DATE)) query+= "  FILTER(xsd:dateTime(?postInCreated) >= xsd:dateTime(\""+date+"\")) \n";
           query+="  }\n";
        return query;
    }

    
    private HashMap<String, HashMap> getStateCounters(PostIn postIn, HashMap<String, HashMap> hmapPoints)
    {
        if(postIn.getGeoStateMap()!=null)
        {
            if(!hmapPoints.containsKey(postIn.getGeoStateMap().getId())) 
            {
                //System.out.println("No en Hash:"+postIn+",GeoState:"+postIn.getGeoStateMap());  
                HashMap<String, Integer> sentimentHash=new HashMap();
                //System.out.println("Entra a hmapPoints-1.2:"+postIn.getPostSentimentalType());
                if(postIn.getPostSentimentalType()==1) {
                    sentimentHash.put("P", new Integer(1));
                }  //Con sentimiento Positivo
                else if(postIn.getPostSentimentalType()==2) {
                    sentimentHash.put("N", new Integer(1)); 
                } //Con sentimiento Negativo
                else if(postIn.getPostSentimentalType()==0) {
                    sentimentHash.put("Ne", new Integer(1)); 
                }
                if(!sentimentHash.isEmpty())
                {
                    //System.out.println("sentimentHash PRIMERO:"+sentimentHash);
                    hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    //System.out.println("Entra a hmapPoints-2:"+hmapPoints);
                }
            }else{  //Si ya contiene el estado en el HashMap
                //System.out.println("SI en Hash:"+postIn+",GeoState:"+postIn.getGeoStateMap());  
                HashMap sentimentHash=hmapPoints.get(postIn.getGeoStateMap().getId());
                if(postIn.getPostSentimentalType()==1)
                {
                    if(sentimentHash.containsKey("P"))
                    {
                        //System.out.println("Entra a hmapPoints-5.1:"+postIn.getMsg_Text()); 
                        //System.out.println("Valor:"+sentimentHash.get("P"));
                        int positiveSentimentNumber=((Integer)sentimentHash.get("P")).intValue()+1;
                        //System.out.println("Entra a hmapPoints-5.2:"+positiveSentimentNumber); 
                        sentimentHash.remove("P");
                        sentimentHash.put("P", new Integer(positiveSentimentNumber));
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);

                    }else{
                        sentimentHash.put("P", new Integer(1)); 
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    }
                }else if(postIn.getPostSentimentalType()==2) 
                {
                    if(sentimentHash.containsKey("N"))
                    {
                        int negativeSentimentNumber=((Integer)sentimentHash.get("N")).intValue()+1;
                        sentimentHash.remove("N");
                        sentimentHash.put("N", new Integer(negativeSentimentNumber));
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    }else{
                        sentimentHash.put("N", new Integer(1)); 
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    }
                }else if(postIn.getPostSentimentalType()==0) {
                    if(sentimentHash.containsKey("Ne"))
                    {
                        int neutralSentimentNumber=((Integer)sentimentHash.get("Ne")).intValue()+1;
                        sentimentHash.remove("Ne");
                        sentimentHash.put("Ne", new Integer(neutralSentimentNumber));
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    }else{
                        sentimentHash.put("Ne", new Integer(1)); 
                        hmapPoints.remove(postIn.getGeoStateMap().getId());
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                    }
                }
            }
        }
        return hmapPoints;
    }
%>
<%-- 
    Document   : streamMap
    Created on : 11-jul-2013, 16:25:27
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    System.out.println("SURI en StreamMap:"+request.getParameter("suri"));
    String suri=request.getParameter("suri");
    if(suri==null) return;
    SemanticObject semObj=SemanticObject.getSemanticObject(suri);
    if(semObj==null) return;
    Stream stream=(Stream)semObj.getGenericInstance();
    WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName()); 
    System.out.println("wsite en StreamMap:"+wsite);
    User user=paramRequest.getUser(); 
%>


<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Google Maps API Example - Random Weather Map</title>
    
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyA_8bWaWXaKlJV2XgZt-RYwRAsp6S0J7iw&sensor=false">
    </script>
    <script type="text/javascript">
      document.write('<script type="text/javascript" src="/images/markermanager.js"><' + '/script>');
    </script>
    <script type="text/javascript">
    //<![CDATA[

    //var IMAGES = [ 'sun', 'rain', 'snow', 'storm' ];
    var ICONS = [];
    var map = null;
    var mgr = null;

    
    function setupMap() {
      var myOptions = {
        zoom: 5,
        //center: new google.maps.LatLng(48.25, 11.00),
        center: new google.maps.LatLng(25, -99),
        mapTypeId: google.maps.MapTypeId.HYBRID
      };
      map = new google.maps.Map(document.getElementById('map'), myOptions);

      showMarkers();
            
    }

    function getMarkers(n) {
      var batch = [];
      <%
        ArrayList aPostInsNotInStates=new ArrayList();
        HashMap<String, HashMap> hmapPoints=new HashMap();
        Iterator <PostIn> itPostIns=stream.listPostInStreamInvs();  
        while(itPostIns.hasNext())
        {
            PostIn postIn=itPostIns.next();
            if(postIn.getGeoStateMap()!=null)
            {
                if(!hmapPoints.containsKey(postIn.getGeoStateMap().getId())) 
                {
                    //System.out.println("Entra a hmapPoints-1.1:"+postIn.getMsg_Text()); 
                    HashMap<String, Integer> sentimentHash=new HashMap();
                    //System.out.println("Entra a hmapPoints-1.2:"+postIn.getPostSentimentalType());
                    if(postIn.getPostSentimentalType()==1) {
                        sentimentHash.put("P", new Integer(1));
                    }  //Con sentimiento Positivo
                    else if(postIn.getPostSentimentalType()==2) {
                        sentimentHash.put("N", new Integer(1)); 
                    } //Con sentimiento Negativo
                    if(!sentimentHash.isEmpty())
                    {
                        
                        //System.out.println("sentimentHash PRIMERO:"+sentimentHash);
                        hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                        //System.out.println("Entra a hmapPoints-2:"+hmapPoints);
                    }
                }else{  //Si ya contiene el estado en el HashMap
                    HashMap sentimentHash=hmapPoints.get(postIn.getGeoStateMap().getId());
                    System.out.println("Entra a hmapPoints-5:"+postIn.getMsg_Text()+",sentimentHash:"+sentimentHash+", sentiment:"+postIn.getPostSentimentalType()); 
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
                            System.out.println("Entra a NEGATIVE hmapPoints-5.1:"+postIn.getMsg_Text()); 
                            System.out.println("Valor:"+sentimentHash.get("N"));
                            int negativeSentimentNumber=((Integer)sentimentHash.get("N")).intValue()+1;
                            System.out.println("Entra a NEGATIVE-hmapPoints-5.2:"+negativeSentimentNumber); 
                            sentimentHash.remove("N");
                            sentimentHash.put("N", new Integer(negativeSentimentNumber));
                            hmapPoints.remove(postIn.getGeoStateMap().getId());
                            hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                        }else{
                            sentimentHash.put("N", new Integer(1)); 
                            hmapPoints.remove(postIn.getGeoStateMap().getId());
                            hmapPoints.put(postIn.getGeoStateMap().getId(), sentimentHash);
                        }
                    }
                }
            }else{
                aPostInsNotInStates.add(postIn);
            }
        }
        Iterator<String> itMapStates=hmapPoints.keySet().iterator();
        while(itMapStates.hasNext())
        {
            String state=itMapStates.next();
            CountryState countryState=CountryState.ClassMgr.getCountryState(state, SWBContext.getAdminWebSite());
            //System.out.println("countryState J:"+countryState.getId());
            if(countryState!=null)
            {
                //System.out.println("countryState J-1/state:"+state);
                
                HashMap<String, Integer> sentimentHash=hmapPoints.get(state); 
                
                int positiveNumber=0;
                int negativeNumber=0;
                if(sentimentHash.containsKey("P"))
                {
                    positiveNumber=((Integer)sentimentHash.get("P")).intValue(); 
                }
                if(sentimentHash.containsKey("N"))
                {
                    negativeNumber=((Integer)sentimentHash.get("N")).intValue();
                }
                if(positiveNumber>negativeNumber)
                {
                    //System.out.println("countryState J/positiveNumber:"+positiveNumber);
                    %>
                    //var tmpIcon = new google.maps.MarkerImage('/images/glad.png');
                    //alert("tmpIcon:"+tmpIcon);
                    batch.push(new google.maps.Marker({
                        position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                        icon: new google.maps.MarkerImage('/images/greenPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                        title: 'Estado:<%=countryState.getDisplayTitle(user.getLanguage())%>, Positivos:'+<%=positiveNumber%>+', Negativos:<%=negativeNumber%>'
                    })
                    );
                    <%    
                }else if(negativeNumber>positiveNumber)
                {
                   System.out.println("countryState J/negativeNumber"+negativeNumber);
                    %>
                    //var tmpIcon = getSentimentIcon("sad");
                    batch.push(new google.maps.Marker({
                        position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                        icon: new google.maps.MarkerImage('/images/redPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                        title: 'Estado:<%=countryState.getDisplayTitle(user.getLanguage())%>, Positivos:'+<%=positiveNumber%>+', Negativos:<%=negativeNumber%>'
                    })
                    );
                    <%  
                 }else if(positiveNumber==negativeNumber)
                 {
                    %>
                    //var tmpIcon = getSentimentIcon("sad");
                    batch.push(new google.maps.Marker({
                        position: new google.maps.LatLng(<%=countryState.getCapitalLatitude()%>,<%=countryState.getCapitalLongitude()%>),
                        icon: new google.maps.MarkerImage('/images/yellowPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                        title: 'Estado:<%=countryState.getDisplayTitle(user.getLanguage())%>, Positivos:'+<%=positiveNumber%>+', Negativos:<%=negativeNumber%>'
                    })
                    );
                    <%  
                 }
            }
        }
      %>
      //alert("batch de regreso:"+batch.length);        
      
      //Traer el resto de PostIns, es decir, los que no tienen una instancia de CountryState asociada
      <%
        Iterator<PostIn> restOfPostIns=aPostInsNotInStates.iterator();
        while(restOfPostIns.hasNext())
        {
            PostIn postIn=restOfPostIns.next();
             //Para los PostIns que tienen un sentimiento positivo o negativo y ademas tienen latitud y longitud asociada
            if(postIn.getPostSentimentalType()>0 && postIn.getLatitude()!=0 && postIn.getLongitude()!=0)
            {
                %>
                       var tmpIcon;
                        <%        
                        if(postIn.getPostSentimentalType()==1)
                        {
                            %>
                               tmpIcon = new google.maps.MarkerImage('/images/greenPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                            <%
                        }else{
                            %>
                                tmpIcon = new google.maps.MarkerImage('/images/redPoint.png', new google.maps.Size(15, 15), new google.maps.Point(15,15)),
                            <%
                        }
                        %>
               
                       batch.push(new google.maps.Marker({
                        position: new google.maps.LatLng(<%=postIn.getLatitude()%>,<%=postIn.getLongitude()%>),
                        //shadow: tmpIcon.shadow,
                        icon: tmpIcon,
                        //shape: tmpIcon.shape,
                        title: '<%=postIn.getMsg_Text()%>'
                    })
                    );  
                <%         
            }
        }
      %>          
      //
      
      return batch;
    }

    function showMarkers() {
      mgr = new MarkerManager(map);
      
      google.maps.event.addListener(mgr, 'loaded', function(){
          mgr.addMarkers(getMarkers(), 3);
          mgr.refresh();          
      });      
    }
    //]]>
    </script>
  </head>

    <body onload="setupMap()">
    <div id="map" style="margin: 5px auto; width: 100%; height: 100%"></div>
    <div style="text-align: center; font-size: large;">
      Random Weather Map
    </div>
  </body>
</html>


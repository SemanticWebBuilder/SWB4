<%-- 
    Document   : streamComparator
    Created on : 18/08/2014, 12:10:26 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.social.CountryState"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.social.Country"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.semanticwb.social.SocialSite"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.apache.commons.validator.UrlValidator"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%
    String suri = request.getParameter("suri");
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    String lang = paramRequest.getUser().getLanguage();
%>
<head>
<style type="text/css">

        @import "/swbadmin/js/dojo/dojo/resources/dojo.css"; 
        @import "/swbadmin/js/dojo/dojo/resources/dnd.css";
        @import "/swbadmin/js/dojo/dijit/themes/soria/soria.css";
        @import "/swbadmin/js/dojo/dojox/layout/resources/ExpandoPane.css";
        @import "/swbadmin/js/dojo/dojox/form/resources/FileInput.css";
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>    
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>
    <script type="text/javascript">
        dojo.require("dijit.form.DateTextBox");
        dojo.require("dijit.form.Button");
    </script>
    
    
    <script>    
        function createPieChart(pieId, hrefAll, data, streamSuri, chartTitle){   
            document.getElementById(pieId).innerHTML="";
            document.getElementById(hrefAll).href= "<%=paramRequest.getRenderUrl().setMode("exportExcel").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("type","fullStream")%>"+"&suri="+streamSuri;
          
            var width = 200,
            height = 200,
            radius = Math.min(width, height) / 2;

            var pie = d3.layout.pie()      
            .value(function(d) { //console.log('gender valuepieCharts'+d.percent); 
                return d.percent; });        
    
            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
            
            
            //d3.json("<%//=SWBPlatform.getContextPath()%>/work/models/<%//=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieGender.jsp<%=""%>&filter="+parametro, function(error, data) {
                    
               
                var svg = d3.select('#'+pieId).append("svg")
                .data([data])
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
        
                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles


                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.3")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select('#' + pieId)
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')
                .html(function(d) {                
                    return "<strong>"+d.data.label+"</strong><br>"+d.data.noOfPosts+"/"+d.data.percent+"%";
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("click", function(d) {
                    if(d.data.noOfPosts > 0){
                        if(confirm('Desea exportar a excel?')){
                            var type = d.data.type;
                            var streamSuri = d.data.streamSuri;
                            var sentiment = "0";
                            if(d.data.sentiment){
                                sentiment = d.data.sentiment;
                            }
                            var suriSemObj = "";
                            if(d.data.suriSemObj){
                                suriSemObj = d.data.suriSemObj;
                            }
                            
                            var url = "<%=renderURL.setMode("exportExcel").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("lang", lang)%>"+"&type="+type+"&suri="+streamSuri+"&suriSemObj="+suriSemObj+"&sentiment="+ sentiment;
                            //console.log("url:" + url);
                            document.location.href = url;
                        }
                    }
                })
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                           
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });
                    
             
                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
                
                svg.append("text")
                .attr("x", 0)
                .attr("y", (height/2)-5)
                .style("text-anchor", "middle")
                .text(chartTitle);
            //});
                
        }
        
    </script>
</head>
<div dojoType="dijit.layout.ContentPane" style="height: 100%">
<%    
    SocialSite socialSite = (SocialSite) SemanticObject.createSemanticObject(suri).createGenericInstance();
    Iterator<Stream> streams = socialSite.listStreams();
    String[] selectedStreams = request.getParameterValues("streams");        
    
%>


<form name="compairStreams" id="compairStreams" action="<%=paramRequest.getRenderUrl().setParameter("suri", suri).setParameter("doView", "1")%>" onsubmit="submitForm('compairStreams'); try{document.getElementById('csLoadingStreams').style.display='inline';}catch(noe){}; return false;">
<table width="100%" border="0px">            
   <tr>
       <td colspan="3" style="text-align: center;" class="titulo">
           <div id="msj-eliminar">
                 <div class="bloque bloque2" style="margin-left: 38%;">
                    <p class="bloqtit">Streams de la marca</p>
                    <select name="streams" multiple size="5" class="bloqsel">
<%
                while(streams.hasNext()){
                    Stream st = streams.next();
                    String selected = "";
                    if(selectedStreams != null){
                        for(int i = 0; i < selectedStreams.length; i++){
                            if(selectedStreams[i].equalsIgnoreCase(st.getEncodedURI())){
                                selected = "selected";
                                break;
                            }
                        }
                    }
%>
                      <option <%=selected%> value="<%=st.getEncodedURI()%>"><%=st.getTitle()%></option>
<%
                    }
%>
                    </select>
                </div>                    
            </div>           
       </td>
   </tr>
</table>
<div style="width:100%;" align="center">
    <button dojoType="dijit.form.Button" type="submit">Comparar Streams</button>
</div>
</form>

<div align="center"><span id="csLoadingStreams" style="width: 100px; display: none" align="center"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/loading.gif"/></span></div>
<script type="text/javascript">
<%
if(selectedStreams != null && selectedStreams.length>0){
    streams = socialSite.listStreams();
    String colors[] = {"#838383","#008000","#FF0000"};
    String labels[] = {"Neutros","Positivos","Negativos"};
    DecimalFormat df = new DecimalFormat("#.00");
    HashMap<Stream, Integer[]> summaryStreamsData = new HashMap <Stream, Integer[]>();
            
    //Defining the empty data once
    JSONArray emptyData = new JSONArray();                
    JSONObject emptyNode = new JSONObject();
    emptyNode.put("noOfPosts", "0");
    emptyNode.put("color", "#EAE8E3");
    emptyNode.put("label", "Sin datos");
    emptyNode.put("percent", "100");
    emptyData.put(emptyNode);
    
    for(int i=0; i < selectedStreams.length; i++){
        if(SemanticObject.createSemanticObject(URLDecoder.decode(selectedStreams[i])).createGenericInstance() instanceof Stream){
            Stream stream = (Stream)SemanticObject.createSemanticObject(URLDecoder.decode(selectedStreams[i])).createGenericInstance();

            Iterator<PostIn> postIns = stream.listPostInStreamInvs();
            Integer bySentimentalType[] = new Integer[]{0,0,0};//Neutrals, positive, negative for the current stream
            SocialSite sSite = (SocialSite) SemanticObject.createSemanticObject(suri).createGenericInstance();
            
            HashMap<SocialNetwork, Integer[]> bySocialNetwork = new HashMap<SocialNetwork, Integer[]>();
            HashMap<CountryState, Integer[]> mxMap = new HashMap<CountryState, Integer[]>();
            HashMap<CountryState, Integer[]> usMap = new HashMap<CountryState, Integer[]>();
            Integer worldMap[] = new Integer[]{0,0,0};
            
            //HashMap <Country, ArrayList> countriesByStream = new HashMap<Country,ArrayList>();
            int totalPosts = 0;
            int totalMxPosts = 0;
            int totalUsPosts = 0;
            //if (c.getCountry().getId().equals("MX")) {
                //map.put(reemplazar(c.getTitle()), new ArrayList<PostIn>());
            //}
            //get all the social networks from the brand
            Iterator<SocialNetwork> socialNetsInStream = sSite.listSocialNetworks();
            while(socialNetsInStream.hasNext()){
                SocialNetwork snet = socialNetsInStream.next();
                bySocialNetwork.put(snet, new Integer[]{0,0,0});
            }
            
            Iterator<Country> itCountries = null;
            WebSite globalSite = SWBSocialUtil.getConfigWebSite();
            itCountries = Country.ClassMgr.listCountries(globalSite);

            while (itCountries.hasNext()) {
                Country country = itCountries.next();
                if(country.getId().equalsIgnoreCase("MX")){
                    Iterator<CountryState> mxStatesTmp = country.listStatesInvs();
                    while(mxStatesTmp.hasNext()){
                        CountryState state = mxStatesTmp.next();
                        mxMap.put(state, new Integer[]{0,0,0});
                    }
                }else if(country.getId().equalsIgnoreCase("US")){
                    Iterator<CountryState> usStatesTmp = country.listStatesInvs();
                    while(usStatesTmp.hasNext()){
                        CountryState state = usStatesTmp.next();
                        usMap.put(state, new Integer[]{0,0,0});
                    }
                }
            }
            
            //worldMap.put("No definido", new Integer[]{0,0,0});
            
            
            while(postIns.hasNext()){
                totalPosts++;
                PostIn postIn = postIns.next();
                int sentimentIndex = 0;
                //chart by sentiment
                if(postIn.getPostSentimentalType() == 0){
                    bySentimentalType[0]++;//neutrals
                }else if(postIn.getPostSentimentalType() == 1){
                    sentimentIndex = 1;
                    bySentimentalType[1]++;//positive                    
                }else if(postIn.getPostSentimentalType() == 2){
                    sentimentIndex = 2;
                    bySentimentalType[2]++;//negative
                }
                
                //chart by social network
                if(bySocialNetwork.get(postIn.getPostInSocialNetwork()) != null){//only the social networks with data in the stream will have a value > 0
                    Integer[] tmpInt = bySocialNetwork.get(postIn.getPostInSocialNetwork());
                    tmpInt[sentimentIndex]++;
                    bySocialNetwork.put(postIn.getPostInSocialNetwork(), tmpInt);
                }
                
                //chart for MX states
                if(mxMap.get(postIn.getGeoStateMap()) != null ){//This state exists in mexico
                    totalMxPosts++;
                    Integer[] tmpInt = mxMap.get(postIn.getGeoStateMap());
                    tmpInt[sentimentIndex]++;
                    mxMap.put(postIn.getGeoStateMap(), tmpInt);
                } else if(usMap.get(postIn.getGeoStateMap()) != null ){//map for us
                    totalUsPosts++;
                    Integer[] tmpInt = usMap.get(postIn.getGeoStateMap());
                    tmpInt[sentimentIndex]++;
                    usMap.put(postIn.getGeoStateMap(), tmpInt);
                }else{//the country is not defined, it migth have location or not.
                    worldMap[sentimentIndex]++;
                }
            }
            summaryStreamsData.put(stream, bySentimentalType);
            
            if(totalPosts == 0){//empty data in the current stream. Create empty charts
%>
                var emptyData = <%=emptyData%>;
                createPieChart('sentimentalPie<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyData, '<%=stream.getEncodedURI()%>', 'Por sentimiento');//create empty charts
                createPieChart('socialNetPie<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyData, '<%=stream.getEncodedURI()%>', 'Por red social');//create empty charts
                createPieChart('geoRefMx<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyData, '<%=stream.getEncodedURI()%>', 'Localizacion MX');//create empty charts
                createPieChart('geoRefOthers<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyData, '<%=stream.getEncodedURI()%>', 'Localizacion global');//create empty charts
<%
                continue;
            }
            
            //the chart by sentiment
            JSONArray dataBySent = new JSONArray();
            for (int bySent = 0; bySent < bySentimentalType.length; bySent++){
                JSONObject node = new JSONObject();
                node.put("noOfPosts", bySentimentalType[bySent]);
                node.put("color", colors[bySent]);
                node.put("label", labels[bySent] );
                node.put("percent", df.format((bySentimentalType[bySent]/(float)totalPosts)*100));
                node.put("type", "bySentiment");
                node.put("sentiment",bySent);
                node.put("streamSuri", stream.getEncodedURI());
                dataBySent.put(node);
            }
%>
            var dataBySent = <%=dataBySent%>;
            createPieChart('sentimentalPie<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', dataBySent, '<%=stream.getEncodedURI()%>', 'Por sentimiento');//create chart by sentiment
<%
            //the chart by social network
            Iterator itByNet = bySocialNetwork.entrySet().iterator();
            JSONArray dataByNet = new JSONArray();
            while(itByNet.hasNext()){
                Map.Entry<SocialNetwork, Integer[]> entry = (Map.Entry) itByNet.next();
                Integer[] tmpInt = entry.getValue();
                
                if(tmpInt[0] > 0 || tmpInt[1] > 0 || tmpInt[2] > 0){
                    JSONObject node = new JSONObject();
                    node.put("noOfPosts", tmpInt[0]+tmpInt[1]+tmpInt[2]);
                    if(tmpInt[1]> tmpInt[0] && tmpInt[1]> tmpInt[2]){
                        node.put("color", colors[1]);
                    } else if(tmpInt[2]> tmpInt[0] && tmpInt[2]> tmpInt[1]){
                        node.put("color", colors[2]);
                    } else{
                        node.put("color", colors[0]);
                    }
                    node.put("label", entry.getKey().getTitle());
                    node.put("percent", df.format(((tmpInt[0]+tmpInt[1]+tmpInt[2])/(float)totalPosts)*100));
                    node.put("type", "bySocialNet");
                    node.put("streamSuri", stream.getEncodedURI());
                    node.put("suriSemObj", entry.getKey().getEncodedURI());
                    dataByNet.put(node);
                }
            }
            
%>
            var dataByNet = <%=dataByNet%>;
            createPieChart('socialNetPie<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', dataByNet, '<%=stream.getEncodedURI()%>', 'Por red social');//creates chart by social network
<%
            //The chart for MX
            JSONArray dataByGeoMx = new JSONArray();
            if(totalMxPosts == 0){
%>
                var emptyDataMx = <%=emptyData%>;
                createPieChart('geoRefMx<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyDataMx, '<%=stream.getEncodedURI()%>', 'Localizacion MX');//create empty chart for mx
<%
            }else{
                Iterator itByGeoMX = mxMap.entrySet().iterator();
                while(itByGeoMX.hasNext()){
                    Map.Entry<CountryState, Integer[]> entry = (Map.Entry) itByGeoMX.next();
                    Integer[] tmpInt = entry.getValue();

                    if(tmpInt[0] > 0 || tmpInt[1] > 0 || tmpInt[2] > 0){
                        JSONObject node = new JSONObject();
                        node.put("noOfPosts", tmpInt[0]+tmpInt[1]+tmpInt[2]);
                        if(tmpInt[1]> tmpInt[0] && tmpInt[1]> tmpInt[2]){
                            node.put("color", colors[1]);
                        } else if(tmpInt[2]> tmpInt[0] && tmpInt[2]> tmpInt[1]){
                            node.put("color", colors[2]);
                        } else{
                            node.put("color", colors[0]);
                        }
                        node.put("label", entry.getKey().getTitle());
                        node.put("percent", df.format(((tmpInt[0]+tmpInt[1]+tmpInt[2])/(float)totalPosts)*100));
                        node.put("type", "byGeoRefMx");
                        node.put("streamSuri", stream.getEncodedURI());
                        node.put("suriSemObj", entry.getKey().getEncodedURI());
                        dataByGeoMx.put(node);
                    }
                }
           
%>
            var dataMx = <%=dataByGeoMx%>;
            createPieChart('geoRefMx<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', dataMx, '<%=stream.getEncodedURI()%>', 'Localizacion MX');//creates chart by social network
<%
            }
            
            //chart by country
            JSONArray dataByCountry = new JSONArray();
            if(totalMxPosts == 0 && totalUsPosts == 0 && totalPosts == 0){
%>
                var emptyDataCountries = <%=emptyData%>;
                createPieChart('geoRefOthers<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', emptyDataCountries, '<%=stream.getEncodedURI()%>', 'Localizacion global');//create empty chart for mx
<%
            }else{
                Iterator itByGeoMX = mxMap.entrySet().iterator();
                Integer mxGlobal[] = new Integer[]{0,0,0};
                while(itByGeoMX.hasNext()){//suma todos los posts de mexico
                    Map.Entry<CountryState, Integer[]> entry = (Map.Entry) itByGeoMX.next();
                    Integer[] tmpInt = entry.getValue();

                    if(tmpInt[0] > 0 || tmpInt[1] > 0 || tmpInt[2] > 0){
                        mxGlobal[0] = mxGlobal[0] + tmpInt[0];
                        mxGlobal[1] = mxGlobal[1] + tmpInt[1];
                        mxGlobal[2] = mxGlobal[2] + tmpInt[2];
                    }
                }
                
                Iterator itByGeoUs = usMap.entrySet().iterator();
                Integer usGlobal[] = new Integer[]{0,0,0};
                while(itByGeoUs.hasNext()){//suma todos los posts de us
                    Map.Entry<CountryState, Integer[]> entry = (Map.Entry) itByGeoUs.next();
                    Integer[] tmpInt = entry.getValue();

                    if(tmpInt[0] > 0 || tmpInt[1] > 0 || tmpInt[2] > 0){
                        usGlobal[0] = usGlobal[0] + tmpInt[0];
                        usGlobal[1] = usGlobal[1] + tmpInt[1];
                        usGlobal[2] = usGlobal[2] + tmpInt[2];
                    }
                }
                
                JSONObject mxNode = new JSONObject();                
                mxNode.put("noOfPosts", mxGlobal[0]+mxGlobal[1]+mxGlobal[2]);
                if(mxGlobal[1]> mxGlobal[0] && mxGlobal[1]> mxGlobal[2]){
                    mxNode.put("color", colors[1]);
                } else if(mxGlobal[2]> mxGlobal[0] && mxGlobal[2]> mxGlobal[1]){
                    mxNode.put("color", colors[2]);
                } else{
                    mxNode.put("color", colors[0]);
                }
                mxNode.put("label", "Mexico");
                mxNode.put("percent", df.format(((mxGlobal[0]+mxGlobal[1]+mxGlobal[2])/(float)totalPosts)*100));
                mxNode.put("type", "byGeoRefGlobal");
                mxNode.put("streamSuri", stream.getEncodedURI());
                mxNode.put("suriSemObj", Country.ClassMgr.getCountry("MX", SWBSocialUtil.getConfigWebSite()).getEncodedURI());
                dataByCountry.put(mxNode);
                
                JSONObject usNode = new JSONObject();                
                usNode.put("noOfPosts", usGlobal[0]+usGlobal[1]+usGlobal[2]);
                if(usGlobal[1]> usGlobal[0] && usGlobal[1]> usGlobal[2]){
                    usNode.put("color", colors[1]);
                } else if(usGlobal[2]> usGlobal[0] && usGlobal[2]> usGlobal[1]){
                    usNode.put("color", colors[2]);
                } else{
                    usNode.put("color", colors[0]);
                }
                usNode.put("label", "USA");
                usNode.put("percent", df.format(((usGlobal[0]+usGlobal[1]+usGlobal[2])/(float)totalPosts)*100));
                usNode.put("type", "byGeoRefGlobal");
                usNode.put("streamSuri", stream.getEncodedURI());
                usNode.put("suriSemObj", Country.ClassMgr.getCountry("US", SWBSocialUtil.getConfigWebSite()).getEncodedURI());
                dataByCountry.put(usNode);
                
                JSONObject noCountryNode = new JSONObject();                
                noCountryNode.put("noOfPosts", worldMap[0]+worldMap[1]+worldMap[2]);
                if(worldMap[1]> worldMap[0] && worldMap[1]> worldMap[2]){
                    noCountryNode.put("color", colors[1]);
                } else if(worldMap[2]> worldMap[0] && worldMap[2]> worldMap[1]){
                    noCountryNode.put("color", colors[2]);
                } else{
                    noCountryNode.put("color", colors[0]);
                }
                noCountryNode.put("label", "No definido");
                noCountryNode.put("percent", df.format(((worldMap[0]+worldMap[1]+worldMap[2])/(float)totalPosts)*100));
                noCountryNode.put("type", "byGeoRefGlobal");
                noCountryNode.put("streamSuri", stream.getEncodedURI());
                noCountryNode.put("suriSemObj", "all");
                dataByCountry.put(noCountryNode);
%>
                var dataCountries = <%=dataByCountry%>;
                createPieChart('geoRefOthers<%=stream.getId()%>', 'hrefAll<%=stream.getId()%>', dataCountries, '<%=stream.getEncodedURI()%>', 'Localizacion global');
<%
            }
        }
    }
    
%>
</script>

<!DOCTYPE html>
<meta charset="utf-8">
<head>        
    <style type="text/css">         
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>
</head>


<div id="graficador">
<%
    for(int i=0; i < selectedStreams.length; i++){
        if(SemanticObject.createSemanticObject(URLDecoder.decode(selectedStreams[i])).createGenericInstance() instanceof Stream){
            Stream stream = (Stream)SemanticObject.createSemanticObject(URLDecoder.decode(selectedStreams[i])).createGenericInstance();
            Integer tmpSummary[] = summaryStreamsData.get(stream);
%>
    <div id="pieGenderParent" style="width:99%; height: 250px;">
        <div class="grafTit">
            <h1><%="Estadisticas del stream " + stream.getTitle()%></h1>
            <a id="hrefAll<%=stream.getId()%>" href=""  onclick="return confirm('Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div>
        <div id="pieGenderComparator" style="display:inline-block; width:200px; text-align: match-parent;" >
            <div align="center"><img src="<%=SWBPlatform.getContextPath() +"/work/models/" + stream.getSemanticObject().getModel().getName() + "/social_Stream/" + stream.getId() + "/" + stream.getStream_logo()%>" width="100px" height="100"/></div>
            Mensajes Recibidos: <%=tmpSummary[0] + tmpSummary[1] + tmpSummary[2]%><br>
            Positivos: <%=tmpSummary[1]%><br>
            Negativos: <%=tmpSummary[2]%><br>
            Neutros: <%=tmpSummary[0]%><br>
        </div>
        <div id="sentimentalPie<%=stream.getId()%>" style="display:inline-block;">
        </div>
            
        <div id="socialNetPie<%=stream.getId()%>" style="display:inline-block;">
        </div>
        
        <div id="geoRefMx<%=stream.getId()%>" style="display:inline-block;">
        </div>
            
        <div id="geoRefOthers<%=stream.getId()%>" style="display:inline-block;">
        </div>
    </div>
<%
        }
    }
%>
</div>
            

<%
}
%>
</div><!--ContentPane div-->
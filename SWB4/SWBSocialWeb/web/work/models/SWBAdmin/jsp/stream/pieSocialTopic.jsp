<%-- 
    Document   : pieSocialTopic
    Created on : 28/03/2014, 12:55:46 PM
    Author     : gabriela.rosales
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<!DOCTYPE html>

<%
    SWBResourceURL urlRender = paramRequest.getRenderUrl();


    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    String args = "?objUri=" + semObj.getEncodedURI();
    String lang = paramRequest.getUser().getLanguage();
    String idModel = paramRequest.getWebPage().getWebSiteId();
    args += "&lang=" + lang;
    args += "&idModel=" + idModel;


    String title = "";
    SocialTopic socialTopic = null;
    ArrayList socialNetworks = new ArrayList();

    if (semObj.getGenericInstance() instanceof SocialTopic) {
        socialTopic = (SocialTopic) semObj.getGenericInstance();
        socialNetworks = SWBSocialUtil.sparql.getSocialTopicSocialNetworks(socialTopic);


    }
    String action = paramRequest.getAction();


    if (semObj.getGenericInstance() instanceof Descriptiveable) {
        title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(lang);
    }


%>


<style type="text/css">         
    @import "/swbadmin/css/swbsocial.css";          
    html, body, #main{
        overflow: auto;
    }
</style>
<script src="http://d3js.org/d3.v3.min.js"></script>   
<div id="graficador">

    <!--  grafica de red social--->
    <div id="pieRedesPostOut">
        <div class="grafTit">
            <h1>Redes Sociales </h1>
            <a id="hrefSocialTopicNetwork" href="<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSocialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>
        <div id="pieSocialTopicNetwork">
        </div>

        <div class="grafOptions">            
            <div>
                <input id="todoPostOutNet"  type="radio" name="postOutSocialNetwork" value="all" checked="">
                <label title="Todos" for="todoPostOutNet">Todos</label>
                <div id="todoSocialTopicNetworkDiv"></div>
            </div>


            <%
                ArrayList list = SWBSocialUtil.sparql.getSocialNetworkPostOut(socialTopic);

                Iterator i = list.iterator();
                while (i.hasNext()) {
                    Object c = i.next();
                    SemanticObject so = SemanticObject.getSemanticObject(c.toString());
                    SocialNetwork sn = (SocialNetwork) so.getGenericInstance();
                    //  if (c.getCountry().getId().equals("MX")) {
                    if (sn instanceof Facebook) {
            %>
            <div>
                <input type="radio" name="postOutSocialNetwork" id="grafFacebookPostOutNet" value="<%=sn.getTitle()%>" >
                <label title="FaceBook" for="grafFacebookPostOutNet"><%=sn.getTitle()%></label>
                <div id="<%=sn.getTitle()%>"></div>
                <%

                } else if (sn instanceof Twitter) {
                %>     
                <div>
                    <input type="radio" name="postOutSocialNetwork" id="grafTwitterPostOutNet" value="<%=sn.getTitle()%>" > 
                    <label title="Twitter" for="grafTwitterPostOutNet"><%=sn.getTitle()%></label>
                    <div id="<%=sn.getTitle()%>"></div>
                    <%
                    } else if (sn instanceof Youtube) {
                    %>
                    <div>
                        <input type="radio" name="postOutSocialNetwork" id="grafYoutubePostOutNet" value="<%=sn.getTitle()%>" > 
                        <label title="YouTube" for="grafYoutubePostOutNet"><%=sn.getTitle()%></label>
                        <div id="<%=sn.getTitle()%>"></div>
                        <%
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>
                <div class="clear"></div>   
            </div>

            <script>    
                function pieSocialTopicNetwork(parametro, cont){   
                    document.getElementById('pieSocialTopicNetwork').innerHTML="";
                    var val = document.querySelector('input[name="postOutSocialNetwork"]:checked').value;
                    var xArray = new Array();
                    var xArrayRedes = new Array();

                    document.getElementById("hrefSocialTopicNetwork").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSocialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
                    var opciones =  document.getElementsByName("postOutSocialNetwork");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
           
                    var width = 760,
                    height = 300,
                    offset = 20,
                    radius = Math.min(width, height) / 2;

                    var pie = d3.layout.pie()      
                    .value(function(d) { //console.log('gender valuepieCharts'+d.value2); 
                        return d.value2; });        
    
                    var arc = d3.svg.arc()
                    .outerRadius(radius - 20)
                    .innerRadius(radius - 100);

                    var arcOver = d3.svg.arc()
                    .outerRadius(radius - 10)
                    .innerRadius(0);
            
                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieSocialTopicNetwork.jsp<%=args%>&filter="+parametro, function(error, data) { 
                    
               
                        var svg = d3.select("#pieSocialTopicNetwork").append("svg")
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

                   
                        d3.selectAll("input[name=postOutSocialNetwork]")
                        .on("change", change);
      
                        function change() {
                            // console.log('entro a change');
                     
                            var opciones =  document.getElementsByName("postOutSocialNetwork");//.disabled=false;
                            for(var i=0; i<opciones.length; i++) {        
                                opciones[i].disabled = true;
                            }
                            pieSocialTopicNetwork(this.value, cont);
                            var value = this.value;
                            pie.value(function(d) { return d[value]; }); // change the value function
                            path = path.data(pie); // compute the new angles
                            path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                        }
  
                        function arcTween(a) {
                            var i = d3.interpolate(this._current, a);
                            this._current = i(0);
                            return function(t) {
                                return arc(i(t));
                            };}
      
                        var gl = svg.selectAll(".arcOver")
                        .data(pie(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.3")
                        .style("fill", function(d) { return d.data.color; });

                        var tooltips = svg.select("#pieSocialTopicNetwork")
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
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value1+"/"+d.data.value2+"%";
                        });       
        
        
                        var g = svg.selectAll(".arc")
                        .data(pie(data))
                        .enter().append("g")
                        .attr("class", "arc")
                        .on("click", function(d) {
                            var filter = d.data.label; 
                            var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSocialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                            document.location.href = url;
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

                        //Obtenemos los valores para los radios

        
                        var total;           
            
                        if(cont == 0){
                            
                            for (var i = 0; i < data.length; i++) {         
                                xArray.push(data[i].valor);                                            
                                xArrayRedes.push(data[i].label3);                                            
                            }  
                 
                            if(xArray.length!=1){                      
                   
                                for (var x = data.length-1; x < data.length; x++) {  
                                    var to;
                                    to = data[x].valor;
                  
                                    var paraPositives= document.createElement("p");   
                                    var paraNegatives= document.createElement("p");   
                                    var paraNeutrals= document.createElement("p");   

                                    var nodPositives = document.createTextNode(to.positivos);
                                    var nodNegatives= document.createTextNode(to.negativos);
                                    var nodNeutrals = document.createTextNode(to.neutros);
                            
                                    paraPositives.appendChild(nodPositives);
                                    paraNegatives.appendChild(nodNegatives);
                                    paraNeutrals.appendChild(nodNeutrals);
                        
                                    var element = document.getElementById("todoSocialTopicNetworkDiv");                            
                                    element.appendChild(paraPositives);
                                    element.appendChild(paraNegatives);
                                    element.appendChild(paraNeutrals);
                                    break;
                                    cont++;
                                } 
                                
                                for (var j = 0; j <xArrayRedes.length ; j++) {   
                                    var paraPositive = document.createElement("p");                                  
                                    var paraNegative = document.createElement("p");                                  
                                    var paraNeutrals = document.createElement("p");                      
                                    var myJSONObject = xArrayRedes[j];                               
                        
                                    var nodePositives = document.createTextNode(myJSONObject.positivos);
                                    var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                                    var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                                    paraPositive.appendChild(nodePositives);
                                    paraNegative.appendChild(nodeNegatives);
                                    paraNeutrals.appendChild(nodeNeutros);
                                    var element; 
                            
                            
                                    element =   document.getElementById(data[j].label);                            
                                    element.appendChild(paraPositive);
                                    element.appendChild(paraNegative);
                                    element.appendChild(paraNeutrals);
                                    
                        
                                }
                        
                            }      
                        }
                               
                        cont++;
                        var opciones =  document.getElementsByName("postOutSocialNetwork");//.disabled=false;
                        for(var i=0; i<opciones.length; i++) {
                            opciones[i].disabled = false;
                        }
         
                    });
                
                }
    
                pieSocialTopicNetwork('all', '0');
    
            </script>




        </div>

    </div>


    <!--grafica mesnajes enviados por usuario--->
    <div id="profileGeoLocationParent">
        <div class="grafTit">
            <h1>Mensajes enviados por usuarios</h1>
            <a id="hrefSocialTopicSendUser" href="<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSendUser").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>

        <div id="piePostOutSendUser">
        </div>

        <div class="grafOptions">          
            <div>
                <input id="todogeo"  type="radio" name="userPostOut" value="all" checked="">
                <label title="Todos" for="todogeo">Todos</label>
                <div id="todoSocialTopicSendUserDiv"></div>
            </div>


            <div class="geoselect">
                <%
                    ArrayList listUser = SWBSocialUtil.sparql.getUserPostOut(socialTopic);
                    i = listUser.iterator();
                    while (i.hasNext()) {
                        SemanticObject so = SemanticObject.createSemanticObject(i.next().toString());
                        User u = (User) so.createGenericInstance();

                %>
                <input id="bcn<%=u.getId()%>" type="radio" name="userPostOut" value="<%=u.getName()%>">
                <label for="bcn<%=u.getId()%>"><%=u.getName()%></label>

                <%
                    }
                %>
            </div>


            <script>    
                function piePostOutSendUser(parametro, cont){   
                    document.getElementById('piePostOutSendUser').innerHTML="";
                    var val = document.querySelector('input[name="userPostOut"]:checked').value;
                    var xArray = new Array();
                    document.getElementById("hrefSocialTopicSendUser").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSendUser").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
                    var opciones =  document.getElementsByName("userPostOut");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
           
                    var width = 760,
                    height = 300,
                    offset = 20,
                    radius = Math.min(width, height) / 2;

                    var pie = d3.layout.pie()      
                    .value(function(d) { //console.log('gender valuepieCharts'+d.value2); 
                        return d.value2; });        
    
                    var arc = d3.svg.arc()
                    .outerRadius(radius - 20)
                    .innerRadius(radius - 100);

                    var arcOver = d3.svg.arc()
                    .outerRadius(radius - 10)
                    .innerRadius(0);
            
                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/piePostOutSendUser.jsp<%=args%>&filter="+parametro, function(error, data) { 
                    
               
                        var svg = d3.select("#piePostOutSendUser").append("svg")
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

                   
                        d3.selectAll("input[name=userPostOut]")
                        .on("change", change);
      
                        function change() {
                            // console.log('entro a change');
                     
                            var opciones =  document.getElementsByName("userPostOut");//.disabled=false;
                            for(var i=0; i<opciones.length; i++) {        
                                opciones[i].disabled = true;
                            }
                            piePostOutSendUser(this.value, cont);
                            var value = this.value;
                            pie.value(function(d) { return d[value]; }); // change the value function
                            path = path.data(pie); // compute the new angles
                            path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                        }
  
                        function arcTween(a) {
                            var i = d3.interpolate(this._current, a);
                            this._current = i(0);
                            return function(t) {
                                return arc(i(t));
                            };}
      
                        var gl = svg.selectAll(".arcOver")
                        .data(pie(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.3")
                        .style("fill", function(d) { return d.data.color; });

                        var tooltips = svg.select("#piePostOutSendUser")
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
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value1+"/"+d.data.value2+"%";
                        });       
        
        
                        var g = svg.selectAll(".arc")
                        .data(pie(data))
                        .enter().append("g")
                        .attr("class", "arc")
                        .on("click", function(d) {
                            var filter = d.data.label; 
                            var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicSendUser").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                            document.location.href = url;
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

                        //Obtenemos los valores para los radios

        
                        for (var i = 0; i < data.length; i++) {               
                            xArray.push(data[i].label2);                                            
                        }  
        
                        var total;           
            
                        if(cont == 0){
                 
                            if(xArray.length!=1){                      
                   
                                for (var x = data.length-1; x < data.length; x++) {  
                                    var to;
                                    to = data[x].valor;
                  
                                    var paraPositives= document.createElement("p");   
                                    var paraNegatives= document.createElement("p");   
                                    var paraNeutrals= document.createElement("p");   

                                    var nodPositives = document.createTextNode(to.positivos);
                                    var nodNegatives= document.createTextNode(to.negativos);
                                    var nodNeutrals = document.createTextNode(to.neutros);
                            
                                    paraPositives.appendChild(nodPositives);
                                    paraNegatives.appendChild(nodNegatives);
                                    paraNeutrals.appendChild(nodNeutrals);
                        
                                    var element = document.getElementById("todoSocialTopicSendUserDiv");                            
                                    element.appendChild(paraPositives);
                                    element.appendChild(paraNegatives);
                                    element.appendChild(paraNeutrals);
                                    break;
                                    cont++;
                                }    
                            }
                   
                            cont++;
                        }       
                        var opciones =  document.getElementsByName("userPostOut");//.disabled=false;
                        for(var i=0; i<opciones.length; i++) {
                            opciones[i].disabled = false;
                        }
         
                    });
                
                }
    
                piePostOutSendUser('all', '0');
    
            </script>



        </div> <!--close de grafOptions-->



    </div><!--div de pieredes//fin mesnaje enviados po usuario-->

    <!--top ten postOut contestados-->
    <!--grafica mesnajes enviados por usuario--->
    <div id="profileGeoLocationParent">
        <div class="grafTit">
            <h1>Mejores contestados</h1>
            <a id="hrefSocialTopicTopten" href="<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopTen").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>

        <div id="pieTopTen">
        </div>       
        <div class="grafOptions">
            <div title="Positivos" class="grafSentiPos" id="grafSentiPos">
            </div>
            <div title="Negativos" class="grafSentiNeg" id="grafSentiNeg">
            </div>
            <div title="Neutros" class="grafSentiNeu" id="grafSentiNeu">
            </div>
        </div>
        <script>    
            function pieTopTen(parametro, cont){   
     
                document.getElementById("hrefSocialTopicTopten").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopTen").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral=all" ;
                var xArray = new Array();

                var width = 760,
                height = 300,
                offset = 20,
                radius = Math.min(width, height) / 2;

                var pie = d3.layout.pie()      
                .value(function(d) { //console.log('gender valuepieCharts'+d.value2); 
                    return d.value2; });        
    
                var arc = d3.svg.arc()
                .outerRadius(radius - 20)
                .innerRadius(radius - 100);

                var arcOver = d3.svg.arc()
                .outerRadius(radius - 10)
                .innerRadius(0);
            
                d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieTopTen.jsp<%=args%>&filter="+parametro, function(error, data) { 
                    
               
                    var svg = d3.select("#pieTopTen").append("svg")
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

                   
                    // d3.selectAll("input[name=userPostOut]")
                    //.on("change", change);
      
                    function change() {
                        // console.log('entro a change');
                     
                        // var opciones =  document.getElementsByName("userPostOut");//.disabled=false;
                        //for(var i=0; i<opciones.length; i++) {        
                        //  opciones[i].disabled = true;
                        //}
                        pieTopTen(this.value, cont);
                        var value = this.value;
                        pie.value(function(d) { return d[value]; }); // change the value function
                        path = path.data(pie); // compute the new angles
                        path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                    }
  
                    function arcTween(a) {
                        var i = d3.interpolate(this._current, a);
                        this._current = i(0);
                        return function(t) {
                            return arc(i(t));
                        };}
      
                    var gl = svg.selectAll(".arcOver")
                    .data(pie(data))
                    .enter().append("g")
                    .attr("class", "arcOver")
                    .style("visibility","hidden");
            
                    gl.append("path")
                    .attr("d", arcOver)
                    .style("fill-opacity", "0.3")
                    .style("fill", function(d) { return d.data.color; });

                    var tooltips = svg.select("#pieTopTen")
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
                        return "<strong>"+d.data.label+"</strong><br>Respuestas : "+d.data.value2;
                    });       
        
        
                    var g = svg.selectAll(".arc")
                    .data(pie(data))
                    .enter().append("g")
                    .attr("class", "arc")
                    .on("click", function(d) {     

                        var filter = d.data.label; 
                        for (var i=0; i<filter.length; i++) {
                            if (filter.charAt(i)=="á") filter = filter.replace(/á/,"a"); 
                            if (filter.charAt(i)=="é") filter = filter.replace(/é/,"e"); 
                            if (filter.charAt(i)=="í") filter = filter.replace(/í/,"i"); 
                            if (filter.charAt(i)=="ó") filter = filter.replace(/ó/,"o"); 
                            if (filter.charAt(i)=="ú") filter = filter.replace(/ú/,"u"); 
                        }
              
 
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopTen").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral=all";
                        document.location.href = url;
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

                    //Obtenemos los valores para los radios
           
                    if(cont == 0){
                        
                         if(xArray.length==1){
                            for (var x = data.length-1; x < data.length; x++) {  
                                var to;
                                to = data[x].valor;
                  
                                var paraPositives= document.createElement("p");   
                                var paraNegatives= document.createElement("p");   
                                var paraNeutrals= document.createElement("p");   

                                var nodPositives = document.createTextNode(to.positivos);
                                var nodNegatives= document.createTextNode(to.negativos);
                                var nodNeutrals = document.createTextNode(to.neutros);
                                
                                paraPositives.appendChild(nodPositives);
                                paraNegatives.appendChild(nodNegatives);
                                paraNeutrals.appendChild(nodNeutrals);

                                var element = document.getElementById("grafSentiPosBellow");                            
                                element.appendChild(paraPositives);
                                var element1 = document.getElementById("grafSentiNegBellow");                          
                                element1.appendChild(paraNegatives);
                                var element2 = document.getElementById("grafSentiNeuBellow");                            
                                element2.appendChild(paraNeutrals);
                                break;
                                cont++;
                            }  
                    
                        }
                 
                        if(xArray.length!=1){                      
                   
                            for (var x = data.length-1; x < data.length; x++) {  
                                var to;
                                to = data[x].valor;
                  
                                var paraPositives= document.createElement("p");   
                                var paraNegatives= document.createElement("p");   
                                var paraNeutrals= document.createElement("p");   

                                var nodPositives = document.createTextNode(to.positivos);
                                var nodNegatives= document.createTextNode(to.negativos);
                                var nodNeutrals = document.createTextNode(to.neutros);
                                
                                paraPositives.appendChild(nodPositives);
                                paraNegatives.appendChild(nodNegatives);
                                paraNeutrals.appendChild(nodNeutrals);

                                var element = document.getElementById("grafSentiPos");                            
                                element.appendChild(paraPositives);
                                var element1 = document.getElementById("grafSentiNeg");                     
                                element1.appendChild(paraNegatives);
                                var element2 = document.getElementById("grafSentiNeu");                     
                                element2.appendChild(paraNeutrals);
                                break;
                                cont++;
                            }    
                        }
                   
             
                    }       
                    var opciones =  document.getElementsByName("userPostOut");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {
                        console.log("los pone enfalse postout user");
                        opciones[i].disabled = false;
                    }
         
                });
                
            }
    
            pieTopTen('all', '0');
    
        </script>
    </div><!--div top ten-->




    <!--top ten postOut peores contestados-->

    <div id="profileGeoLocationParent">
        <div class="grafTit">
            <h1>Menos contestados</h1>
            <a id="hrefSocialTopicbellow" href="<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopBellow").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>

        <div id="pieBelow">
        </div>      
        <div class="grafOptions">
            <div title="Positivos" class="grafSentiPos" id="grafSentiPosBellow">
            </div>
            <div title="Negativos" class="grafSentiNeg" id="grafSentiNegBellow">
            </div>
            <div title="Neutros" class="grafSentiNeu" id="grafSentiNeuBellow">
            </div>
        </div>

        <script>    
            function pieBelow(parametro, cont){   
                document.getElementById("hrefSocialTopicbellow").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopBellow").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral=all" ;

           
                var width = 760,
                height = 300,
                offset = 20,
                radius = Math.min(width, height) / 2;

                var pie = d3.layout.pie()      
                .value(function(d) { //console.log('gender valuepieCharts'+d.value2); 
                    return d.value2; });        
    
                var arc = d3.svg.arc()
                .outerRadius(radius - 20)
                .innerRadius(radius - 100);

                var arcOver = d3.svg.arc()
                .outerRadius(radius - 10)
                .innerRadius(0);
            
                d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieBelow.jsp<%=args%>&filter="+parametro, function(error, data) { 
                    
                    var svg = d3.select("#pieBelow").append("svg")
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


      
                    function change() {
                        pieBelow(this.value, cont);
                        var value = this.value;
                        pie.value(function(d) { return d[value]; }); // change the value function
                        path = path.data(pie); // compute the new angles
                        path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                    }
  
                    function arcTween(a) {
                        var i = d3.interpolate(this._current, a);
                        this._current = i(0);
                        return function(t) {
                            return arc(i(t));
                        };}
      
                    var gl = svg.selectAll(".arcOver")
                    .data(pie(data))
                    .enter().append("g")
                    .attr("class", "arcOver")
                    .style("visibility","hidden");
            
                    gl.append("path")
                    .attr("d", arcOver)
                    .style("fill-opacity", "0.3")
                    .style("fill", function(d) { return d.data.color; });

                    var tooltips = svg.select("#pieBelow")
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
                        return "<strong>"+d.data.label+"</strong><br>Respuestas : "+d.data.value2;
                    });       
        
        
                    var g = svg.selectAll(".arc")
                    .data(pie(data))
                    .enter().append("g")
                    .attr("class", "arc")
                    .on("click", function(d) {
                        var filter = d.data.label; 
                        for (var i=0; i<filter.length; i++) {
                            if (filter.charAt(i)=="á") filter = filter.replace(/á/,"a"); 
                            if (filter.charAt(i)=="é") filter = filter.replace(/é/,"e"); 
                            if (filter.charAt(i)=="í") filter = filter.replace(/í/,"i"); 
                            if (filter.charAt(i)=="ó") filter = filter.replace(/ó/,"o"); 
                            if (filter.charAt(i)=="ú") filter = filter.replace(/ú/,"u"); 
                        }
              
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "socialTopicTopBellow").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral=all";
                        document.location.href = url;
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
         
                    var total;      
                    var xArray = new Array();

            
                    if(cont == 0){
                        for (var i = 0; i < data.length; i++) {                                   
                            xArray.push(data[i].valor);                                            
                        }  
                        if(xArray.length==1){
                            for (var x = data.length-1; x < data.length; x++) {  
                                var to;
                                to = data[x].valor;
                  
                                var paraPositives= document.createElement("p");   
                                var paraNegatives= document.createElement("p");   
                                var paraNeutrals= document.createElement("p");   

                                var nodPositives = document.createTextNode(to.positivos);
                                var nodNegatives= document.createTextNode(to.negativos);
                                var nodNeutrals = document.createTextNode(to.neutros);
                                
                                paraPositives.appendChild(nodPositives);
                                paraNegatives.appendChild(nodNegatives);
                                paraNeutrals.appendChild(nodNeutrals);

                                var element = document.getElementById("grafSentiPosBellow");                            
                                element.appendChild(paraPositives);
                                var element1 = document.getElementById("grafSentiNegBellow");                          
                                element1.appendChild(paraNegatives);
                                var element2 = document.getElementById("grafSentiNeuBellow");                            
                                element2.appendChild(paraNeutrals);
                                break;
                                cont++;
                            }  
                    
                        }
                        if(xArray.length!=1){                      
                   
                            for (var x = data.length-1; x < data.length; x++) {  
                                var to;
                                to = data[x].valor;
                  
                                var paraPositives= document.createElement("p");   
                                var paraNegatives= document.createElement("p");   
                                var paraNeutrals= document.createElement("p");   

                                var nodPositives = document.createTextNode(to.positivos);
                                var nodNegatives= document.createTextNode(to.negativos);
                                var nodNeutrals = document.createTextNode(to.neutros);
                                
                                paraPositives.appendChild(nodPositives);
                                paraNegatives.appendChild(nodNegatives);
                                paraNeutrals.appendChild(nodNeutrals);

                                var element = document.getElementById("grafSentiPosBellow");                            
                                element.appendChild(paraPositives);
                                var element1 = document.getElementById("grafSentiNegBellow");                          
                                element1.appendChild(paraNegatives);
                                var element2 = document.getElementById("grafSentiNeuBellow");                            
                                element2.appendChild(paraNeutrals);
                                break;
                                cont++;
                            }    
                        }
                   
             
                    }    
                });
                
            }
    
            pieBelow('all', '0');
    
        </script>
    </div><!--div top ten-->









</div> <!--div de graficador-->


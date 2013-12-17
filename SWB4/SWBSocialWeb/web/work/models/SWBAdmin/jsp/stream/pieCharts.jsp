<%-- 
    Document   : pieChar
    Created on : 08-ago-2013, 11:41:35
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

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
    if (semObj.getGenericInstance() instanceof Descriptiveable) {
        title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(lang);
    }
%>

<!DOCTYPE html>
<meta charset="utf-8">
<style type="text/css">


    body {
        font: 10px sans-serif;
    }

    .arc path {
        stroke: #fff;
    }
    body {
        font: 10px sans-serif;
    }

    .axis path, .axis line {
        fill: none;
        stroke: #FF8000;
        shape-rendering: crispEdges;
    }
    .bar {
        fill: orange;
    }

    .bar:hover {
        fill: orangered ;
    }

    .bar_neutrals {
        fill: orange;
    }

    .bar_neutrals:hover {
        fill: #D8D8D8 ;
    }

    .bar_negatives {
        fill: orange;
    }

    .bar_negatives:hover {
        fill: orangered ;
    }


    .bar_positives {
        fill: #86c440;
    }

    .bar_positives:hover{
        fill: #86c440 ;
    }

    .x.axis path {
        display: none;
    }

    .d3-tip {
        line-height: 1;
        font-weight: bold;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;
    }

    .txt {
        font: 10px sans-serif;
        color: #pink;

    }

    form {

        right: 10px;
        top: 10px;
    }

    input {
        margin: 0 7px;
    }
    /* Creates a small triangle extender for the tooltip 
    .d3-tip:after {
        box-sizing: border-box;
        display: inline;
        font-size: 10px;
        width: 100%;
        line-height: 1;
        color: rgba(0, 0, 0, 0.8);
        content: "\25BC";
        position: absolute;
        text-align: center;
    }
    */
    /* Style northward tooltips differently */
    .d3-tip.n:after {
        margin: -1px 0 0 0;
        top: 100%;
        left: 0;
    }


</style>


<head>
    <style type="text/css">         
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>
    <script type="text/javascript" >
        function post()
        {
            alert('entro');
            var content='';
                
            
            var url='<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>';
            var strToEval='dojo.xhrPost({form: url,timeout: 3000,content: {'+ content +' }})';
               
            var xhrArgs = {
                form: url,
                handleAs: "text",
                load: function(data){
                    //dojo.byId("response").innerHTML = "Form posted.";
                    alert('respuesta: '+data);
                    funcion();
                },
                error: function(error){
                    alert('error'+error);
                    // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                    // docs server.
                    //dojo.byId("response").innerHTML = "Form posted.";
                }
            }
                
            var deferred = dojo.xhrPost(xhrArgs);
                
                
        }
            
        function sendform(id, funcion)
        {
            alert('entro al send form');
            var _form = dojo.byId(id);
                
            var xhrArgs = {
                form: _form,
                handleAs: "text",
                load: function(data){
                    //dojo.byId("response").innerHTML = "Form posted.";
                    alert('respuesta: '+data);
                    funcion();
                },
                error: function(error){
                    alert('error'+error);
                    // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                    // docs server.
                    //dojo.byId("response").innerHTML = "Form posted.";
                }
            }
                
            var deferred = dojo.xhrPost(xhrArgs);
                
            return deferred;
        }
    
            
    
    </script>

</head>





<div id="pieGenderParent">
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("gender", lang)%></h1>
    <div class="barra">
        <a id="hrefGender" href="<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div>
    <div id="pieGender">

    </div>
    <div class="clear"></div>
    <div>
        <input type="radio" name="gender" id="gender" value="all" checked />  Todos<br>
        <input type="radio" name="gender" id="gender" value="male" /> Masculino<br>
        <input type="radio" name="gender" id="gender" value="female" /> Femenino<br>
        <input type="radio" name="gender" id="gender" value="nodefine" /> No definido<br>
    </div>
    <div id="pieGenderInfo">          
    </div>
</div>

<script src="http://d3js.org/d3.v3.min.js"></script>   

<script>    
    function pieGender(parametro, cont){   
        document.getElementById('pieGender').innerHTML="";
        var val = document.querySelector('input[name="gender"]:checked').value;
        //alert('val'+val);

        document.getElementById("hrefGender").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     

        console.log('THE PARAM:' + parametro);        
        var xArray = new Array();
        var color = d3.scale.category10();
        var width = 760,
        height = 300,
        offset = 20,
        radius = Math.min(width, height) / 2;


        var pie = d3.layout.pie()
        //.sort(null)
        .value(function(d) { console.log('gender value'+d.value2); return d.value2; });    
    
    
        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);

        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);
        



        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieGender.jsp<%=args%>&filter="+parametro, function(error, data) {
            
            
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
                para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("pieGender");
                element.appendChild(para);           
                element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("pieGender").removeAttribute("style");
            var svg = d3.select("#pieGender").append("svg")
            .attr("width", width)
            .attr("height", height)
            .append("g")
            // .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");
            .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
        
            var path = svg.datum(data).selectAll("path")
            .data(pie)
            .enter().append("path")
            .attr("fill", function(d, i) { return d.data.color; })
            .attr("d", arc)
            .each(function(d) { this._current = d; }); // store the initial angles

                   
            d3.selectAll("input[name=gender]")
            .on("change", change);
      
            function change() {
                console.log('entro a change');
                pieGender(this.value, cont);
                var value = this.value;
                //clearTimeout(timeout);
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

            var tooltips = svg.select("#pieGender")
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
                alert('filter'+filter);
                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
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

            for (var i = 0; i < data.length; i++) {               
                xArray.push(data[i].label2);                                            
            }  
        
            var total;
            
            
            if(cont == 0){
                for (var x = 0; x < data.length; x++) {  
                    total = data[x].label3;
                    var paraTital = document.createElement("p");   
                    var node = document.createTextNode( total );
                    paraTital.appendChild(node);
                    var element=document.getElementById("pieGenderInfo");
                    element.appendChild(paraTital);
                    break;
                } 
                                          
                for (var j = 0; j <xArray.length ; j++) {   
                    var para = document.createElement("p");                                  
                    var node = document.createTextNode( xArray[j] );
                    para.appendChild(node);
                    var element=document.getElementById("pieGenderInfo");
                    element.appendChild(para);
                }
            
                cont ++;
            }
        
         
        });
                
    }
    
    pieGender('all', '0');
    
</script>

<div id="pieEducationParent">
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("education", lang)%></h1>
    <div class="barra">
        <a id="hrefEducation" href="<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div>
    <div id="pieEducation" >
    </div>
    <div class="clear"></div>
    <div>
        <form id="formEducation" name="formEducation">
            <input type="radio" name="education" value="all" checked> Todos<br>
            <input type="radio" name="education" value="secundaria" > Secundaria<br>
            <input type="radio" name="education" value="mediosuperior" > Medio Superior<br>
            <input type="radio" name="education" value="graduado" > Graduado<br>
            <input type="radio" name="education" value="undefined" > No definido<br>
        </form>
    </div>
    <div id="pieEducationInfo">          
    </div>
</div>


<script>
    function pieEducation(parametro, cont){   
        document.getElementById('pieEducation').innerHTML="";
        var val = document.querySelector('input[name="education"]:checked').value;
        document.getElementById("hrefEducation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
        console.log('THE PARAM:' + parametro);
        var educationArray = new Array();
        var color = d3.scale.category10();
        var width = 760,
        height = 300,
        offset = 20,
        radius = Math.min(width, height) / 2;


        var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { console.log('eduaction value : '+d.value2); return d.value2; });    
    
    
        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);

        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);
        

                    
        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieEducation.jsp<%=args%>&filter="+parametro, function(error, data) {
            
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
                para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("pieEducation");
                element.appendChild(para);           
                element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("pieEducation").removeAttribute("style");

            var svg = d3.select("#pieEducation").append("svg")
            .attr("width", width)
            .attr("height", height)
            .append("g")
            .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");
            var path = svg.datum(data).selectAll("path")
            .data(pie)
            .enter().append("path")
            .attr("fill", function(d, i) { return d.data.color; })
            .attr("d", arc)
            .each(function(d) { this._current = d; }); // store the initial angles
      
      
            d3.selectAll("input[name=education]")
            .on("change", change);
            
            //console.log('d3.selectAll("input")'+d3.selectAll("input[name=education]"));
      
            function change() {
                console.log('entro a change');
                pieEducation(this.value, cont);
                var value = this.value;
                //clearTimeout(timeout);
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
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });


            var tooltips = svg.select("#pieEducation")
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
                return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";
            });       
        
        
            var g = svg.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arc")
            .on("click", function(d) {
                var filter = d.data.label;            
                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
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
            if(cont == 0){
                for (var x = 0; x < data.length; x++) {  
                    total = data[x].label3;
                    var paraTital = document.createElement("p");   
                    var node = document.createTextNode( total );
                    paraTital.appendChild(node);
                    var element=document.getElementById("pieEducationInfo");
                    element.appendChild(paraTital);
                    break;
                } 
        
                for (var i = 0; i < data.length; i++) {               
                    educationArray.push(data[i].label2);                                            
                }  
                                          
                for (var j = 0; j <educationArray.length ; j++) {   
                    var para = document.createElement("p");                                  
                    var node = document.createTextNode( educationArray[j] );
                    para.appendChild(node);
                    var element=document.getElementById("pieEducationInfo");
                    element.appendChild(para);
                    cont++;
                }      
            }
                                  
        });       
    }
    
    pieEducation('all', '0');
                
</script>

<div  id="pieRelationParent">
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("statusRelation", lang)%></h1>
    <div class="barra">
        <a id="hrefRelation" href="<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div> 

    <div id="pieRelationShipStatus">
    </div>
    <div class="clear"></div>

    <div>
        <form id="formEducation" name="formRelation">
            <input type="radio" name="relation" value="all" checked> Todos<br>
            <input type="radio" name="relation" value="single" > Soltero(a)<br>
            <input type="radio" name="relation" value="married" > Casado(a)<br>
            <input type="radio" name="relation" value="widowed" > Viudo(a)<br>
            <input type="radio" name="relation" value="divorced" > Divorciado(a)<br>
            <input type="radio" name="relation" value="undefined" > No definido<br>
        </form>
    </div>
    <div id="pieRelationInfo">          
    </div>
</div>
<script>
    function pieRelation(parametro, cont){   
        document.getElementById('pieRelationShipStatus').innerHTML="";
        var val = document.querySelector('input[name="relation"]:checked').value;
        document.getElementById("hrefRelation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;

        console.log('THE PARAM:' + parametro);
        var relationArray = new Array();
        var width = 760,
        height = 300,
        radius = Math.min(width, height) / 2;

        var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.value2; });
    
        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);
    
        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);

    



        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieRelationShipStatus.jsp<%=args%>&filter="+parametro, function(error, data) {
            
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
                para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("pieRelationShipStatus");
                element.appendChild(para);           
                element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("pieRelationShipStatus").removeAttribute("style");
            
            var svg = d3.select("#pieRelationShipStatus").append("svg")
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

                   
            d3.selectAll("input[name=relation]")
            .on("change", change);
      
            function change() {
                console.log('entro a change');
                pieRelation(this.value, cont);
                var value = this.value;
                //clearTimeout(timeout);
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
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });

            var tooltips = svg.select("#pieRelationShipStatus")
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
                return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
            });       
        
        
            var g = svg.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arc")
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
            .on("click", function(d) {
                var filter = d.data.label;            
                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                document.location.href = url;
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
        
            if(cont == 0){
                var total;
                for (var x = 0; x < data.length; x++) {  
                    total = data[x].label3;
                    var paraTital = document.createElement("p");   
                    var node = document.createTextNode( total );
                    paraTital.appendChild(node);
                    var element=document.getElementById("pieRelationInfo");
                    element.appendChild(paraTital);
                    break;
                } 
                        
                        
                for (var i = 0; i < data.length; i++) {               
                    relationArray.push(data[i].label2);                                            
                }  
                                          
                for (var j = 0; j <relationArray.length ; j++) {   
                    var para = document.createElement("p");                                  
                    var node = document.createTextNode( relationArray[j] );
                    para.appendChild(node);
                    var element=document.getElementById("pieRelationInfo");
                    element.appendChild(para);
                } 
                cont++;
            }
        
        });
    
    }
    
    pieRelation('all', '0');
                

</script>

<div id="lifeStageParent">
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("lifeStage", lang)%></h1>
    <div class="barra">
        <a  id="hrefLife" href="<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div> 
    <div id="profileLifeStage">      
    </div>
    <div class="clear"></div>
    <div>
        <form id="formEducation" name="formRelation">
            <input type="radio" name="life" value="all" checked> Todos <br>
            <input type="radio" name="life" value="child" > NiÒo(a)<br>
            <input type="radio" name="life" value="young" > Joven<br>
            <input type="radio" name="life" value="teenAge" > Adolescente<br>
            <input type="radio" name="life" value="youngAdult" > Adulto Joven<br>
            <input type="radio" name="life" value="adult" > Adulto<br>
            <input type="radio" name="life" value="thirdAge" > Tercera Edad<br>
            <input type="radio" name="life" value="nodefine" > No definido<br>
        </form>
    </div>
    <div id="pieLifeStageInfo">          
    </div>
</div>
<script>
    
    function pieLifeStage(parametro, cont){   
        document.getElementById('profileLifeStage').innerHTML="";
        console.log('THE PARAM:' + parametro);
 
        var val = document.querySelector('input[name="life"]:checked').value;
        document.getElementById("hrefLife").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     

        var width = 760,
        height = 300,
        radius = Math.min(width, height) / 2;
        var lifeStageArray = new Array();
    
        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);

    
        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);
     
        
        var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.value2; });

     

        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieLifeStage.jsp<%=args%>&filter="+parametro, function(error, data) {
            
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
                para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("profileLifeStage");
                element.appendChild(para);           
                element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("profileLifeStage").removeAttribute("style");
            
            var svg = d3.select("#profileLifeStage").append("svg")
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

                   
            d3.selectAll("input[name=life]")
            .on("change", change);
      
            function change() {
                console.log('entro a change');
                pieLifeStage(this.value, cont);
                var value = this.value;
                //clearTimeout(timeout);
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
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });

            var tooltips = svg.select("#profileLifeStage")
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
                return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";
            });       
        
        
            var g = svg.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arc")
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
            .on("click", function(d) {
                var filter = d.data.label;            
                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                document.location.href = url;
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
            
            if(cont == 0){
                for (var x = 0; x < data.length; x++) {  
                    total = data[x].label3;
                    var paraTital = document.createElement("p");   
                    var node = document.createTextNode( total );
                    paraTital.appendChild(node);
                    var element=document.getElementById("pieLifeStageInfo");
                    element.appendChild(paraTital);
                    break;
                } 
           
                        
                for (var i = 0; i < data.length; i++) {               
                    lifeStageArray.push(data[i].label2);                                            
                }  
                                          
                for (var j = 0; j <lifeStageArray.length ; j++) {   
                    var para = document.createElement("p");                                  
                    var node = document.createTextNode( lifeStageArray[j] );
                    para.appendChild(node);
                    var element=document.getElementById("pieLifeStageInfo");
                    element.appendChild(para);
                }
                cont++;
            }
        });
    
    }
    
    pieLifeStage('all', '0');
</script>


<div id="profileGeoLocationParent">
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("location", lang)%></h1>
    <div class="barra">
        <a id="hrefGeo" href="<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div> 
    <div id="profileGeoLocation">

    </div>
    <div class="clear"></div>
    <%
        ArrayList list = new ArrayList();
        list.add("Baja California");
        list.add("Baja California Sur");
        list.add("Campeche");
        list.add("Colima");
        list.add("Distrito Federal");
        list.add("Durango");
        list.add("Guanajuato");
        list.add("Guerrero");
        list.add("Hidalgo");
        list.add("Jalisco");
        list.add("Estado de MÈxico");
        list.add("Michoac·n");
        list.add("Morelos");
        list.add("Nayarit");
        list.add("Nuevo LeÛn");
        list.add("Oaxaca");
        list.add("Puebla");
        list.add("QuerÈtaro");
        list.add("Quintana Roo");
        list.add("San Luis PotosÌ");
        list.add("Sinaloa");
        list.add("Sonora");
        list.add("Tabasco");
        list.add("Tamaulipas");
        list.add("Tlaxcala");
        list.add("Veracruz");
        list.add("Yucat·n");
        list.add("Zacatecas");
        list.add("No definido");
    %>
    <div>
        <input type="radio" name="geo" value="all" checked="">Todos<br>
        <%
            Iterator i = list.iterator();
            while (i.hasNext()) {
                String value = i.next().toString();
        %>
        <input type="radio" name="geo" value="<%=value%>"><%=value%><br>
        <%
            }
        %>
    </div>
    <div id="pieGeolocationInfo">          
    </div>
</div>
<script>
    function pieGeo(parametro, cont){
        document.getElementById('profileGeoLocation').innerHTML="";
        console.log('THE PARAM GEO:' + parametro);
        
        
        var acentosP = "√¿¡ƒ¬»…À ÃÕœŒ“”÷‘Ÿ⁄‹€„‡·‰‚ËÈÎÍÏÌÔÓÚÛˆÙ˘˙¸˚—Ò«Á";
        var originalP = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

        for (var i=0; i<acentosP.length; i++) {
            parametro = parametro.replace(acentosP.charAt(i), originalP.charAt(i));
        }
 
        var val = document.querySelector('input[name="geo"]:checked').value;
        document.getElementById("hrefGeo").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
        var width = 760,
        height = 300,
        radius = Math.min(width, height) / 2;
        var geoArray = new Array();

        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);

        var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.value2; });

        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);
    
   

        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieProfileGeoLocation.jsp<%=args%>&filter="+parametro, function(error, data) {
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
                para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("profileGeoLocation");
                element.appendChild(para);           
                element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("profileGeoLocation").removeAttribute("style");
            
            var svg = d3.select("#profileGeoLocation").append("svg")
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

                   
            d3.selectAll("input[name=geo]")
            .on("change", change);
      
            function change() {
                console.log('entro a change');
                pieGeo(this.value, cont);
                var value = this.value;
                //clearTimeout(timeout);
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
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });

            var tooltips = svg.select("#profileGeoLocation")
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
                return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
            });       
        
        
            var g = svg.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arc")
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
            .on("click", function(d) {
                var filter = d.data.label;  
                var acentos = "√¿¡ƒ¬»…À ÃÕœŒ“”÷‘Ÿ⁄‹€„‡·‰‚ËÈÎÍÏÌÔÓÚÛˆÙ˘˙¸˚—Ò«Á";
                var original = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

                for (var i=0; i<acentos.length; i++) {
                    filter = filter.replace(acentos.charAt(i), original.charAt(i));
                }

                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                document.location.href = url;
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
            if(cont  == 0){
                for (var x = 0; x < data.length; x++) {  
                    total = data[x].label3;
                    var paraTital = document.createElement("p");   
                    var node = document.createTextNode( total );
                    paraTital.appendChild(node);
                    var element=document.getElementById("pieGeolocationInfo");
                    element.appendChild(paraTital);
                    break;
                }       
                        
                for (var i = 0; i < data.length; i++) {               
                    geoArray.push(data[i].label2);                                            
                }  
                                          
                for (var j = 0; j <geoArray.length ; j++) {   
                    var para = document.createElement("p");                                  
                    var node = document.createTextNode( geoArray[j] );
                    para.appendChild(node);
                    var element=document.getElementById("pieGeolocationInfo");
                    element.appendChild(para);
            
                }
                cont++;
            }
        
        });
    
    }
        
    pieGeo('all', '0'); 
    
    

</script>
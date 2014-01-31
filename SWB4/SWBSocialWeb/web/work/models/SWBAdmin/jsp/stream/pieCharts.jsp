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
<head>
    <style type="text/css">         
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>   
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


<div id="graficador">
    <div id="pieGenderParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("gender", lang)%></h1>
            <a id="hrefGender" href="<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>
        <div id="pieGender">
        </div>
        <div class="grafOptions">            
            <div>
                <input  id="todogen"  type="radio" name="gender" value="all" checked >  
                <label title="Todos" for="todogen">Todos</label>
                <div id="allGender">
                </div>
            </div>
            <div>
                <input id="masc"  type="radio" name="gender" value="male" > 
                <label title="Masculino" for="masc">Masculino</label>
                <div id="mascGender">
                </div>
            </div>
            <div>
                <input id="feme" type="radio" name="gender"  value="female" > 
                <label title="Femenino" for="feme">Femenino</label>
                <div id="femGender">    
                </div>
            </div>
            <div>
                <input id="indegen" type="radio" name="gender"  value="nodefine" >
                <label title="No definido" for="indegen">No definido</label>
                <div id="nodefGender">    
                </div>
            </div>          
        </div>
          <div class="clear"></div>   
    </div>
    <script>    
        function pieGender(parametro, cont){   
            document.getElementById('pieGender').innerHTML="";
            var val = document.querySelector('input[name="gender"]:checked').value;

            document.getElementById("hrefGender").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var xArray = new Array();
            var color = d3.scale.category10();
            var width = 760,
            height = 300,
            offset = 20,
            radius = Math.min(width, height) / 2;

            var pie = d3.layout.pie()      
            .value(function(d) { console.log('gender valuepieCharts'+d.value2); return d.value2; });        
    
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
                    var element=document.getElementById("pieGender");
                    element.appendChild(node);   
                   // element.style="line-height: 250px; font-family:verdana; text-align: center; font-size: 10pt; color: orange;vertical-align: middle;"//padding-bottom: 115px; padding-top:115px"
                    return;    
                }
                
                var svg = d3.select("#pieGender").append("svg")
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

                   
                d3.selectAll("input[name=gender]")
                .on("change", change);
      
                function change() {
                    console.log('entro a change');
                    pieGender(this.value, cont);
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

                //Obtenemos los valores para los radios

        
                var total;           
            
                if(cont == 0){
                    for (var i = 0; i < data.length; i++) {         
                        xArray.push(data[i].valor);                                            
                    }  
                    for (var x = 0; x < data.length; x++) {  
                        total = data[x].label3;
                        //console.log("total"+total);
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            
                        var nodePositives = document.createTextNode( total.positivos );
                        var nodeNegatives= document.createTextNode( total.negativos );
                        var nodeNeutrals = document.createTextNode( total.neutros );
                            
                            
                        paraPositives.appendChild(nodePositives);
                        paraNegatives.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutrals);
                        var element =   document.getElementById("allGender");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    } 
                                          
                    for (var j = 0; j <xArray.length ; j++) {   
                        var paraPositive = document.createElement("p");                                  
                        var paraNegative = document.createElement("p");                                  
                        var paraNeutrals = document.createElement("p");                                  
                       
                        
                        var myJSONObject = xArray[j];
                        
                        var nodePositives = document.createTextNode(myJSONObject.positivos);
                        var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                        var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                        paraPositive.appendChild(nodePositives);
                        paraNegative.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutros);
                        var element; 
                            
                        if(j==0){
                            element =   document.getElementById("mascGender");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==1){
                            element =   document.getElementById("femGender");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==2){
                            element =   document.getElementById("nodefGender");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }
                        
                    }            
                    cont++;
                }       
         
            });
                
        }
    
        pieGender('all', '0');
    
    </script>


    <div id="pieEducationParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("education", lang)%></h1>                
            <a id="hrefEducation" href="<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div>
        <div id="pieEducation" >
        </div>
        <div class="grafOptions">
            <form id="formEducation" name="formEducation">
                <div>
                    <input id="todoedu" type="radio" name="education" value="all" checked>
                    <label title="Todos" for="todoedu">Todos</label>
                    <div id="todoEduDiv"></div>
                </div>
                <div>
                    <input id="secu" type="radio" name="education" value="secundaria" >
                    <label title="Secundaria" for="secu">Secundaria</label>
                    <div id="secuEduDiv"></div>
                </div>
                <div>
                    <input id="medi" type="radio" name="education" value="mediosuperior" > 
                    <label title="Medio" for="medi">Medio Superior</label>
                    <div id="medioEduDiv"></div>
                </div>
                <div>
                    <input id="inge" type="radio" name="education" value="graduado" >
                    <label title="Graduado" for="inge">Graduado</label>
                    <div id="graduadoEduDiv"></div>
                </div>
                <div>
                    <input id="indeedu" type="radio" name="education" value="undefined" >
                    <label title="No definido" for="indeedu">No definido</label>
                    <div id="nodefinidoEduDiv"></div>
                </div>
            </form>
        </div>
        <div class="clear"></div>   
    </div>
    <script>
        function pieEducation(parametro, cont){   
            document.getElementById('pieEducation').innerHTML="";
            var val = document.querySelector('input[name="education"]:checked').value;
            document.getElementById("hrefEducation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
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
                    var element=document.getElementById("pieEducation");
                    element.appendChild(para);           
                   // element.style="line-height: 250px; font-family:verdana; text-align: center; font-size: 10pt; color: orange;vertical-align: middle;"//padding-bottom: 115px; padding-top:115px"
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
            
               function change() {
                    console.log('entro a change');
                    pieEducation(this.value, cont);
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
              
                for (var i = 0; i < data.length; i++) {               
                    educationArray.push(data[i].valor);                                            
                } 
                    
                var total;
                if(cont == 0){
                    var pPostives= document.createElement("p"); 
                    var pNegatives= document.createElement("p"); 
                    var pNeutrals= document.createElement("p"); 
                    
                    
                    for (var x = 0; x < data.length; x++) {  
                        total = data[x].label3;                                 
 
                        var nodePositives = document.createTextNode( total.positivos );
                        var nodeNegatives = document.createTextNode( total.negativos );
                        var nodeNeutrals = document.createTextNode( total.neutros );
                        
                        pPostives.appendChild(nodePositives);
                        pNegatives.appendChild(nodeNegatives);
                        pNeutrals.appendChild(nodeNeutrals);
                        
                        var element=document.getElementById("todoEduDiv");
                        element.appendChild(pPostives);
                        element.appendChild(pNegatives);
                        element.appendChild(pNeutrals);
                        break;
                    } 
        
                  
                                          
                    for (var j = 0; j <educationArray.length ; j++) {   
                        var paraPositives = document.createElement("p");    
                        var paraNegatives = document.createElement("p");    
                        var paraNeutrals = document.createElement("p");    
                        var myJSONObject = educationArray[j];
                        var nodePositives = document.createTextNode(myJSONObject.positivos);
                        var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                        var nodeNeutros = document.createTextNode(myJSONObject.neutros );
                        
                        
                        paraPositives.appendChild(nodePositives);
                        paraNegatives.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutros);
                        var element;
                        if(j==0){
                            element = document.getElementById("secuEduDiv");
                            element.appendChild(paraPositives);
                            element.appendChild(paraNegatives);
                            element.appendChild(paraNeutrals);
                    
                        }else if(j==1){
                            element = document.getElementById("medioEduDiv");
                            element.appendChild(paraPositives);
                            element.appendChild(paraNegatives);
                            element.appendChild(paraNeutrals);
                    
                        }else if(j==2){
                            element = document.getElementById("graduadoEduDiv");
                            element.appendChild(paraPositives);
                            element.appendChild(paraNegatives);
                            element.appendChild(paraNeutrals);
                    
                        }else if(j==3){
                            element = document.getElementById("nodefinidoEduDiv");
                            element.appendChild(paraPositives);
                            element.appendChild(paraNegatives);
                            element.appendChild(paraNeutrals);                    
                        }
                       
                    }      
                    cont++;
                }
                                  
            });       
        }
    
        pieEducation('all', '0');
                
    </script>

    <div  id="pieRelationParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("statusRelation", lang)%></h1>
            <a id="hrefRelation" href="<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div> 
        <div id="pieRelationShipStatus">
        </div>        
        <div class="grafOptions">
            <form id="formRelation" name="formRelation">
                <div>
                    <input  id="todorel"type="radio" name="relation" value="all" checked>
                    <label title="Todos" for="todorel">Todos</label>
                    <div id="rAll"></div>
                </div>
                <div>
                    <input id="solt" type="radio" name="relation" value="single">
                    <label title="Soltero(a)" for="solt">Soltero(a)</label>
                    <div id="rSingle"></div>
                </div>
                <div>
                    <input id="casa" type="radio" name="relation" value="married">
                    <label title="Casado(a)" for="casa">Casado(a)</label>
                    <div id="rMarried"></div>
                </div>
                <div>
                    <input id="viud" type="radio" name="relation" value="widowed">
                    <label title="Viudo(a)" for="viud">Viudo(a)</label>
                    <div id="rWidowed"></div>
                </div>
                <div>
                    <input id="divo" type="radio" name="relation" value="divorced">
                    <label title="Divorciado(a)" for="divo">Divorciado(a)</label>
                    <div id="rDivorced"></div>
                </div>
                <div>
                    <input id="inderel" type="radio" name="relation" value="undefined">
                    <label title="No Definido" for="inderel">Todos</label>
                    <div id="rUndefined"></div>
                </div>
            </form>
        </div>
        <div class="clear"></div>
    </div>
    <script>
        function pieRelation(parametro, cont){   
            document.getElementById('pieRelationShipStatus').innerHTML="";
            var val = document.querySelector('input[name="relation"]:checked').value;
            document.getElementById("hrefRelation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;

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
                    var element=document.getElementById("pieRelationShipStatus");
                    element.appendChild(para);           
                    //element.style="line-height: 250px; font-family:verdana; text-align: center; font-size: 10pt; color: orange;vertical-align: middle;"//padding-bottom: 115px; padding-top:115px"
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
                    
                    for (var i = 0; i < data.length; i++) {               
                        relationArray.push(data[i].valor);                                            
                    }                      
                    var total;
                    
                    for (var x = 0; x < data.length; x++) {  
                        total = data[x].label3;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            
                        var nodePositives = document.createTextNode( total.positivos );
                        var nodeNegatives= document.createTextNode( total.negativos );
                        var nodeNeutrals = document.createTextNode( total.neutros );
                            
                            
                        paraPositives.appendChild(nodePositives);
                        paraNegatives.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutrals);
                        var element =   document.getElementById("rAll");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }                 
                        
              
                                          
                    for (var j = 0; j <relationArray.length ; j++) {   
                        var paraPositive = document.createElement("p");                                  
                        var paraNegative = document.createElement("p");                                  
                        var paraNeutrals = document.createElement("p");                                  
                       
                        
                        var myJSONObject = relationArray[j];
                        
                        var nodePositives = document.createTextNode(myJSONObject.positivos);
                        var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                        var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                        paraPositive.appendChild(nodePositives);
                        paraNegative.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutros);
                        var element; 
                            
                        if(j==0){
                            element =   document.getElementById("rSingle");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==1){
                            element =   document.getElementById("rMarried");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==2){
                            element =   document.getElementById("rWidowed");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==3){
                            element =   document.getElementById("rDivorced");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==4){
                            element =   document.getElementById("rUndefined");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }
                        
                    } 
                    cont++;
                }
        
            });
    
        }
    
        pieRelation('all', '0');
                

    </script>

    <div id="lifeStageParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("lifeStage", lang)%></h1>
            <a  id="hrefLife" href="<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div> 
        <div id="profileLifeStage">      
        </div>
        <div class="grafOptions">
            <form id="formEducation" name="formRelation">
                <div>
                    <input id="todolif" type="radio" name="life" value="all" checked="">
                    <label title="Todos" for="todolif">Todos</label>
                    <div id="lAll"></div>                        
                </div>
                <div>
                    <input id="nino" type="radio" name="life" value="child">
                    <label title="NiÒo(a): 0-12 aÒos" for="nino">NiÒo(a): 0-12 aÒos</label>
                    <div id="lChild"></div>                        
                </div>
                <div>
                    <input id="adol" type="radio" name="life" value="teenAge">
                    <label title="Adolescente: 13-18 aÒos" for="adol">Adolescente: 13-18 aÒos</label>
                    <div id="lTeenAge"></div>                        
                </div>
                <div>
                    <input id="jove" type="radio" name="life" value="young">
                    <label title="Joven: 19-30 aÒos" for="jove">Joven: 19-30 aÒos</label>
                    <div id="lYoung"></div>                        
                </div>
                <div>
                    <input id="adjo" type="radio" name="life" value="youngAdult">
                    <label title="Adulto joven: 31-45 aÒos" for="adjo">Adulto joven: 31-45 aÒos</label>
                    <div id="lYoungAdult"></div>                        
                </div>
                <div>
                    <input id="adul" type="radio" name="life" value="adult">
                    <label title="Adulto: 46-65 aÒos" for="adul">Adulto: 46-65 aÒos</label>
                    <div id="lAdul"></div>                        
                </div>
                <div>
                    <input id="terc" type="radio" name="life" value="thirdAge">
                    <label title="Tercera edad: +66 aÒos" for="terc">Tercera edad: +66 aÒos</label>
                    <div id="lThirdAge"></div>                        
                </div>
                <div>
                    <input id="indelif" type="radio" name="life" value="nodefine">
                    <label title="No definido " for="indelif">No definido </label>
                    <div id="lNodefine"></div>                        
                </div>
            </form>
        </div>
        <div class="clear"></div>
    </div>
        
    <script>
    
        function pieLifeStage(parametro, cont){   
            document.getElementById('profileLifeStage').innerHTML="";

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
                    var element=document.getElementById("profileLifeStage");
                    element.appendChild(para);           
                    //element.style="line-height: 250px; font-family:verdana; text-align: center; font-size: 10pt; color: orange;vertical-align: middle;"//padding-bottom: 115px; padding-top:115px"
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
                        //console.log("total"+total);
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            
                        var nodePositives = document.createTextNode( total.positivos );
                        var nodeNegatives= document.createTextNode( total.negativos );
                        var nodeNeutrals = document.createTextNode( total.neutros );

                            
                        paraPositives.appendChild(nodePositives);
                        paraNegatives.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutrals);
                        var element =   document.getElementById("lAll");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    } 
           
                        
                    for (var i = 0; i < data.length; i++) {               
                        lifeStageArray.push(data[i].valor);                                            
                    }  
                                          
                    for (var j = 0; j <lifeStageArray.length ; j++) {   
                        var paraPositive = document.createElement("p");                                  
                        var paraNegative = document.createElement("p");                                  
                        var paraNeutrals = document.createElement("p");                                  
                       
                        
                        var myJSONObject = lifeStageArray[j];
                        
                        var nodePositives = document.createTextNode(myJSONObject.positivos);
                        var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                        var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                        paraPositive.appendChild(nodePositives);
                        paraNegative.appendChild(nodeNegatives);
                        paraNeutrals.appendChild(nodeNeutros);
                        var element; 
                            
                        if(j==0){
                            element =   document.getElementById("lChild");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==1){
                            element =   document.getElementById("lTeenAge");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==2){
                            element =   document.getElementById("lYoung");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==3){
                            element =   document.getElementById("lYoungAdult");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==4){
                            element =   document.getElementById("lAdul");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==5){
                            element =   document.getElementById("lThirdAge");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }else if(j==6){
                            element =   document.getElementById("lNodefine");                            
                            element.appendChild(paraPositive);
                            element.appendChild(paraNegative);
                            element.appendChild(paraNeutrals);
                        }
                        cont++;
                    }
                 
                }
            });
    
        }
    
        pieLifeStage('all', '0');
    </script>

    <div id="profileGeoLocationParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("location", lang)%></h1>
            <a id="hrefGeo" href="<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
        </div> 
        <div id="profileGeoLocation">
        </div>
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
        <div class="grafOptions">
            <div>
                <input id="todogeo" type="radio" name="geo" value="all" checked="">
                <label title="Todos" for="todogeo">Todos</label>
                <div id="todogeoDiv"></div>
            </div>
            <div>
                <input id="indegeo" type="radio" name="geo" value="No definido">
                <label title="No definido" for="indegeo">No definido</label>
                <div id="indegeoDiv"></div>
            </div>
            <div class="geoselect">
                <%
                    Iterator i = list.iterator();
                    while (i.hasNext()) {
                        String value = i.next().toString();
                %>
                <input id="bcn" type="radio" name="geo" value="<%=value%>">
                <label for="bcn"><%=value%></label>
                <%
                    }
                %>
            </div>
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
                    var element=document.getElementById("profileGeoLocation");
                    element.appendChild(node);           
                   // element.style="line-height: 250px; font-family:verdana; text-align: center; font-size: 10pt; color: orange;vertical-align: middle;"//padding-bottom: 115px; padding-top:115px"
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
        
               
                if(cont  == 0){
                    for (var x = 0; x < data.length; x++) {  
                        var to;
                        to = data[x].label3;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            

                        var nodPositives = document.createTextNode(to.positivos);
                        var nodNegatives= document.createTextNode(to.negativos);
                        var nodNeutrals = document.createTextNode(to.neutros);
                            
                        paraPositives.appendChild(nodPositives);
                        paraNegatives.appendChild(nodNegatives);
                        paraNeutrals.appendChild(nodNeutrals);
                        
                        var element = document.getElementById("todogeoDiv");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }       
                        
                    for (var i = 0; i < data.length; i++) {        
                        if(data[i].label==="No definido"){
                           
                            var pPositives= document.createElement("p");   
                            var pNegatives= document.createElement("p");   
                            var pNeutrals= document.createElement("p");   
                            var myJSONObject = data[i].valor; 
                        
                            var nodePositives = document.createTextNode(myJSONObject.positivos);
                            var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                            var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                            pPositives.appendChild(nodePositives);
                            pNegatives.appendChild(nodeNegatives);
                            pNeutrals.appendChild(nodeNeutros);
                        
                            var element=document.getElementById("indegeoDiv");
                            element.appendChild(pPositives);
                            element.appendChild(pNegatives);
                            element.appendChild(pNeutrals);
                        }                       
                    }  
                    cont++;
                }
        
            });
    
        }  
        
        pieGeo('all', '0'); 
    </script>

</div>
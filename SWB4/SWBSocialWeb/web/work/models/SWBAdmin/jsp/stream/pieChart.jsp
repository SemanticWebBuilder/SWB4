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
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<!DOCTYPE html>
<%
    SWBResourceURL urlRender = paramRequest.getRenderUrl();
    SWBResourceURL url = paramRequest.getRenderUrl();
    WebSite wsite = paramRequest.getWebPage().getWebSite();

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


    Stream stream = null;
    SocialTopic socialTopic = null;
    ArrayList socialNetworks = new ArrayList();

    if (semObj.getGenericInstance() instanceof Stream) {
        stream = (Stream) semObj.getGenericInstance();
        socialNetworks = SWBSocialUtil.sparql.getStreamSocialNetworks(stream);

    } else if (semObj.getGenericInstance() instanceof SocialTopic) {
        socialTopic = (SocialTopic) semObj.getGenericInstance();
        socialNetworks = SWBSocialUtil.sparql.getSocialTopicSocialNetworks(socialTopic);


    }
    String action = paramRequest.getAction();
    //System.out.println("action:::::"+action);

    String title = "";
    if (semObj.getGenericInstance() instanceof Descriptiveable) {
        title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(lang);
    }

    ArrayList listMeses = new ArrayList();
    listMeses.add("Enero");
    listMeses.add("Febrero");
    listMeses.add("Marzo");
    listMeses.add("Abril");
    listMeses.add("Mayo");
    listMeses.add("Junio");
    listMeses.add("Julio");
    listMeses.add("Agosto");
    listMeses.add("Septiembre");
    listMeses.add("Octubre");
    listMeses.add("Noviembre");
    listMeses.add("Diciembre");
    Iterator iMeses = listMeses.iterator();

    ArrayList listAnio = new ArrayList();
    listAnio.add("2013");
    listAnio.add("2012");

    Iterator iAnio = listAnio.iterator();


%>
<!DOCTYPE html>
<meta charset="utf-8">
<style>

    body {
        font: 10px sans-serif;
    }

    .arc path {
        stroke: #fff;
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


    /* Style northward tooltips differently */
    .d3-tip.n:after {
        margin: -1px 0 0 0;
        top: 100%;
        left: 0;
    }

    .units {
        line-height: 1;
        font-weight: bold;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;

    }

</style>
<body onload="lanzar();">
<head>

    <script>
        function lanzar()
        {
    
            var submitBtn = document.getElementById('mostrarGraficaOculto');
         
            if(submitBtn){
               
                submitBtn.click();
            }

        }

    </script>
</head>
<style type="text/css">         
    @import "/swbadmin/css/swbsocial.css";          
    html, body, #main{
        overflow: auto;
    }
</style>


<div >
    <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("sentimentProm", lang)%>: <%=title%></h1>
    <div class="barra">
        <a href="<%=urlRender.setMode("exportExcel").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("type", "").setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div>
    <div id="pieChart">
    </div>
</div>

<script src="http://d3js.org/d3.v3.min.js"></script>    
<script>
    function pieChart(){  
        var color = d3.scale.category10();
        var width = 760,
        height = 400,
        offset = 20,
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


    
    
        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/ObjsentimentData.jsp<%=args%>", function(error, data) {


            var svgSen = d3.select("#pieChart").append("svg")
            .attr("width", width)
            .attr("height", height)
            .append("g")
            // .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");
            .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
        
            var path = svgSen.datum(data).selectAll("path")
            .data(pie)
            .enter().append("path")
            .attr("fill", function(d, i) { return d.data.color; })
            .attr("d", arc)
            .each(function(d) { this._current = d; }); // store the initial angles
            
            
            var gl= svgSen.selectAll(".arcOver")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arcOver")
            .style("visibility","hidden");
            
            gl.append("path")
            .attr("d", arcOver)
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });

            var tooltips = svgSen.select("#pieChart")
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
        
        
        
            var g = svgSen.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arc")
            .on("click", function(d) {
                var filter =d.data.label; 
                var url = "<%=urlRender.setMode("exportExcel").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter;
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

            svgSen
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
        
        });
    }
    pieChart();


</script>

<br><br><br><br>
<div id="pieSocialNetwork">
    <h1>Redes Sociales</h1>
    <div class="barra">
        <a id="hrefGender" href="<%=urlRender.setMode("exportExcel").setParameter("type", "socialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" class="excel">Exportar excel</a>
    </div>
    <div id="pieNetworkSocial">

    </div>
    <div class="clear"></div>
    <div>
        <%
            Iterator i = socialNetworks.iterator();
        %>

        <input  type="radio" name="socialNetwork" id="socialNetwork" value="all" checked />  Todos<br>

        <%
            while (i.hasNext()) {
                SemanticObject sO = (SemanticObject) i.next();
                SocialNetwork sN = (SocialNetwork) sO.getGenericInstance();
                System.out.println("SOCIAL" + sN.getURI());
        %>         
        <input type="radio" name="socialNetwork" id="socialNetwork" value="<%=sN.getTitle()%>" /> <%=sN.getTitle()%><br>
        <%
            }
        %>
    </div>
    <!--<div id="pieGenderInfo">          
    </div>-->
</div>

<script src="http://d3js.org/d3.v3.min.js"></script>   

<script>    
    function pieNetworkSocial(parametro, cont){   
        document.getElementById('pieNetworkSocial').innerHTML="";
        var val = document.querySelector('input[name="socialNetwork"]:checked').value;
        //alert('val'+val);

        document.getElementById("hrefGender").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "socialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     

        console.log('THE PARAM:' + parametro);        
        var xArray = new Array();
        var color = d3.scale.category10();
        var width = 760,
        height = 400,
        offset = 20,
        radius = Math.min(width, height) / 2;
     

        var pie = d3.layout.pie()
        //.sort(null)
        .value(function(d) { console.log('PieChartgender value'+d.value2); return d.value2; });    
    
    
        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);

        var arcOver = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);
        



        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieSocialNetwork.jsp<%=args%>&filter="+parametro, function(error, data) {
            
            d3.select("#amazingViz").remove();
            if(data==""){
                var para = document.createElement("p");                                  
                var node = document.createTextNode( "Sin datos para procesar" );
                para.appendChild(node);                   
               // para.style="letter-spacing: 5px;"
                //  para.style="text-shadow: grey 5px -5px 2px; ;letter-spacing: 5px;"                   
                var element=document.getElementById("pieNetworkSocial");
                element.appendChild(para);           
               // element. style="font-family:verdana; text-align: center; font-size: 20pt; color: orange;vertical-align: middle;padding-bottom: 115px; padding-top:115px;"
                //  element.style="opacity:0.3;position:center;left:50;width:50;height:200px;background-color:#8AC007"             
                return;
    
            }

            document.getElementById("pieNetworkSocial").removeAttribute("style");
            //d3.select("svg") .remove();
            //d3.select("#pieNetworkSocial").remove();
            
            var svg = d3.select("#pieNetworkSocial").append("svg")
            .attr("id", "amazingViz")
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

                   
            d3.selectAll("input[name=socialNetwork]")
            .on("change", change);
      
            function change() {
                var opciones =  document.getElementsByName("socialNetwork");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = true;
                }
                pieNetworkSocial(this.value, cont);
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

            var tooltips = svg.select("#pieNetworkSocial")
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
                //alert('filter'+filter);
                var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "socialNetwork").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
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
        
            var opciones =  document.getElementsByName("socialNetwork");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = false;
            }
        });
                
    }
    
    pieNetworkSocial('all', '2');
    
</script>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" ></script>
<script type="text/javascript" >
    function postHtml(url, tagid)
    {
            
        dojo.xhrPost({
            url: url,
            load: function(response)
            {
                var tag=dojo.byId(tagid);
                if(tag){
                    var pan=dojo.byId(tagid);
                    //alert("-"+tagid+"-"+tag+"-"+pan+"-");
                    if(pan && pan.attr)
                    {
                        pan.attr('content',response);
                    }else
                    {
                        tag.innerHTML = response;
                    }
                }else {
                    alert("No existe ningún elemento con id " + tagid);
                }
                return response;
            },
            error: function(response)
            {
                if(dojo.byId(tagid)) {
                    dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
                }else {
                    alert("No existe ningún elemento con id " + tagid);
                }
                return response;
            },
            handleAs: "text"
        });
    }
            
    function mostrar(selected){
           
        var div  ;
        if(selected ==  1){
            div = document.getElementById('divAnual');
            div.style.display='block';
                
            var mensual =  document.getElementById('divAnualMensual');
            mensual.style.display='none';
                
        }else {
            div = document.getElementById('divAnualMensual');                
            div.style.display='block';
                
            var anual = document.getElementById('divAnual');
            anual.style.display='none';
                
                
        }
            
            
    }
    function valid(id){
        if(id ==1){    
            var selectAnio=  document.getElementById("selectAnio").value;
            if(selectAnio== ""){
                alert('Seleccione el año');
                return;
            }
        }else{                  
            var selectAnio2=  document.getElementById("selectAnio2").value;
            var selectMes = document.getElementById("selectMes").value;
            if(selectAnio2== ""){
                alert('Seleccione el año');
                return;
            }
            if(selectMes== ""){
                alert('Seleccione el mes');
                return;
            }
        }                   
    }
</script>

<h1>Mensajes recibidos de :<%=title%></h1>
<div class="pub-redes">
    <p class="titulo">Tipo de filtro que desea:</p> 
    <form name="formgraphBar" id="formgraphBar" dojoType="dijit.form.Form" method="post" enctype="multipart/form-data" action="">
        <table>
            <tr>
            <select name="select_sh" id="select_sh"  dojoType="dijit.form.Select" onchange="mostrar(document.formgraphBar.select_sh[document.formgraphBar.select_sh.selectedIndex].value);">
                <option value="0">--Seleccione--</option>
                <option value="1" selected="">Anual</option>
                <option value="2">Mensual</option>
            </select>
            </tr>
        </table>
    </form>

    <div id="divAnual" class="pub-redes"  style="display:none;">
        <p class="titulo">Seleccione:</p>      
        <div id="graphBardivd"  >
            <form name="formgraphBarAnio" id="formgraphBarAnio" dojoType="dijit.form.Form" method="post" enctype="multipart/form-data" action="">
                <table>
                    <tr>
                    <select name="selectAnio" id="selectAnio">
                        <option value=""><---Seleccione el año----></option>
                        <%
                            while (iAnio.hasNext()) {
                                String anio = (String) iAnio.next();
                        %>
                        <option value="<%=anio%>"><%=anio%></option>
                        <%}%>                            
                    </select>
                    </tr>
                    <tr>
                    <input id="mostrarGraficaOculto"  type="hidden" value="Mostrar" onclick="postHtml('<%=urlRender.setMode("showGraphBar")%>&selectedAnio='+escape(document.formgraphBarAnio.selectAnio[document.formgraphBarAnio.selectAnio.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>', 'showgraphBar');">
                    <input id="mostrarGraficaR"  type="button" value="Mostrar" onclick="javascript:valid('1');postHtml('<%=urlRender.setMode("showGraphBar")%>&selectedAnio='+escape(document.formgraphBarAnio.selectAnio[document.formgraphBarAnio.selectAnio.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>', 'showgraphBar');">
                    </tr>
                </table>
            </form>
        </div>
    </div>


    <div id="divAnualMensual" style="display:none;">
        <p class="titulo">Seleccione:</p>      
        <form type="dijit.form.Form" id="createPost" name="createPost" action="" method="post" >
            <input type="hidden" id="suri" name="suri" value="<%=URLEncoder.encode(suri)%>" >
            <table>
                <tr>
                    <td>
                        <select name="selectAnio2" id="selectAnio2">
                            <option value=""><---Seleccione el año----></option>
                            <option value="2012">2012</option>
                            <option value="2013">2013</option>
                            <option value="2014">2014</option>
                            <option value="2015">2015</option>                                                   
                        </select>
                    </td>
                    <td>
                        <select name="selectMes" id="selectMes">
                            <option value=""><---Seleccione el mes----></option>
                            <%
                                int valuemonth = 1;
                                while (iMeses.hasNext()) {
                                    String mes = (String) iMeses.next();
                            %>
                            <option value="<%=valuemonth%>"><%=mes%></option>
                            <%
                                    valuemonth++;
                                }
                            %>                            
                        </select>
                    </td>
                    <td></td>
                    <td>
                        <input   type="button" value="Mostrar" onclick="javascript:valid('2');postHtml('<%=urlRender.setMode("showGraphBar")%>&selectAnio='+escape(document.createPost.selectAnio2[document.createPost.selectAnio2.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>&selectMes='+escape(document.createPost.selectMes[document.createPost.selectMes.selectedIndex].value) +'', 'showgraphBar');">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


</div>
<div id="selectgraphBar" dojoType="dijit.layout.ContentPane">
</div>               


<div id="showgraphBar" dojoType="dijit.layout.ContentPane" title="Preguntas" selected="true">
</div>

</body>



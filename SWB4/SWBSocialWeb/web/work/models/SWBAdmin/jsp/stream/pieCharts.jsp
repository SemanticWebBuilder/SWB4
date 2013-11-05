<%-- 
    Document   : pieChar
    Created on : 08-ago-2013, 11:41:35
    Author     : jorge.jimenez
--%>
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
<style>

    body {
        font: 10px sans-serif;
    }

    .arc path {
        stroke: #fff;
    }

</style>


<head>
    <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>
    <script type="text/javascript" >
        function postHtml(url, tagid)
        {
            dojo.xhrPost({
                url: url,
                load: function(response)
                {
                    var tag=dojo.byId(tagid);
                    if(tag){
                        var pan=dijit.byId(tagid);
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
            
    
    </script>
</head>




    
                <div id="pieGenderParent">
                    <div id="pieGender">
                        <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("gender", lang)%></h1>
                    </div>
                </div>
                <script src="http://d3js.org/d3.v3.min.js"></script>   
                <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>

                <script>
                    var xArray = new Array();
                    var color = d3.scale.category10();
                    var width = 760,
                    height = 300,
                    offset = 20,
                    radius = Math.min(width, height) / 2;


                    var pie = d3.layout.pie()
                    .sort(null)
                    .value(function(d) { return d.value2; });    
    
    
                    var arc = d3.svg.arc()
                    .outerRadius(radius - 15)
                    .innerRadius(0);

                    var arcOver = d3.svg.arc()
                    .outerRadius(radius - 5)
                    .innerRadius(radius-15);
        

                    var svg = d3.select("#pieGender").append("svg")
                    .attr("width", width)
                    .attr("height", height)
                    .append("g")
                    .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");

                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieGender.jsp<%=args%>", function(error, data) {

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
                        .append("span")
                        .html(function(d) {                
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value2+"%";
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
                                          
                        for (var j = 0; j <xArray.length ; j++) {   
                            var para = document.createElement("p");                                  
                            var node = document.createTextNode( xArray[j] );
                            para.appendChild(node);
                            var element=document.getElementById("pieGenderParent");
                            element.appendChild(para);
                        }
                    });
                    
                    

                </script>
           
                <div id="pieEducationParent" class="swbform">
                    <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("education", lang)%></h1>
                    <div id="pieEducation">                  

                    </div>
                </div>
                <script>

                    var educationArray = new Array();
                    var color = d3.scale.category10();
                    var width = 760,
                    height = 300,
                    offset = 20,
                    radius = Math.min(width, height) / 2;


                    var pieE = d3.layout.pie()
                    .sort(null)
                    .value(function(d) { return d.value2; });    
    
    
                    var arc = d3.svg.arc()
                    .outerRadius(radius - 15)
                    .innerRadius(0);

                    var arcOver = d3.svg.arc()
                    .outerRadius(radius - 10 )
                    .innerRadius(0);
        

                    var svgE = d3.select("#pieEducation").append("svg")
                    .attr("width", width)
                    .attr("height", height)
                    .append("g")
                    .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");
                    
                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieEducation.jsp<%=args%>", function(error, data) {

                        var gl = svgE.selectAll(".arcOver")
                        .data(pieE(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.6")
                        .style("fill", function(d) { return d.data.color; });


                        var tooltips = svgE.select("#pieEducation")
                        .data(pie(data))
                        .enter().append("div")
                        .attr("class","chartToolTip")
                        .style("display", "none")
                        .style("position", "absolute")
                        .style("z-index", "10");

                        tooltips.append("p")
                        .append("span")
                        .html(function(d) {                
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value2+"%";;
                        });       
        
        
                        var g = svgE.selectAll(".arc")
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

                        svgE
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
                            educationArray.push(data[i].label2);                                            
                        }  
                                          
                        for (var j = 0; j <educationArray.length ; j++) {   
                            var para = document.createElement("p");                                  
                            var node = document.createTextNode( educationArray[j] );
                            para.appendChild(node);
                            var element=document.getElementById("pieEducationParent");
                            element.appendChild(para);
                        }                      
                                  
                    });              
                
                </script>
            
                <div  id="pieRelationParent">
                    <div id="pieRelationShipStatus">
                        <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("relationShip", lang)%></h1>
                    </div>
                </div>
                <script>
                    var relationArray = new Array();
                    var width = 760,
                    height = 300,
                    radius = Math.min(width, height) / 2;

                    var arcR = d3.svg.arc()
                    .outerRadius(radius - 10)
                    .innerRadius(0);

                    var pieR = d3.layout.pie()
                    .sort(null)
                    .value(function(d) { return d.value2; });

                    var svgR = d3.select("#pieRelationShipStatus").append("svg")
                    .attr("width", width)
                    .attr("height", height)
                    .append("g")
                    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieRelationShipStatus.jsp<%=args%>", function(error, data) {

                        var gl = svgR.selectAll(".arcOver")
                        .data(pieE(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.6")
                        .style("fill", function(d) { return d.data.color; });

                        var tooltips = svgR.select("#pieRelationShipStatus")
                        .data(pie(data))
                        .enter().append("div")
                        .attr("class","chartToolTip")
                        .style("display", "none")
                        .style("position", "absolute")
                        .style("z-index", "10");

                        tooltips.append("p")
                        .append("span")
                        .html(function(d) {                
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value2+"%";;
                        });       
        
        
                        var g = svgR.selectAll(".arc")
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

                        svgR
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
                            relationArray.push(data[i].label2);                                            
                        }  
                                          
                        for (var j = 0; j <relationArray.length ; j++) {   
                            var para = document.createElement("p");                                  
                            var node = document.createTextNode( relationArray[j] );
                            para.appendChild(node);
                            var element=document.getElementById("pieRelationParent");
                            element.appendChild(para);
                        } 
        
                    });

                </script>
           
                <div id="profileGeoLocationParent">
                    <div id="profileGeoLocation">
                        <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("location", lang)%></h1>
                    </div>
                </div>
                <script>

                    var width = 760,
                    height = 300,
                    radius = Math.min(width, height) / 2;
                    var geoArray = new Array();

                    var arcGeo = d3.svg.arc()
                    .outerRadius(radius - 10)
                    .innerRadius(0);

                    var pieGeo = d3.layout.pie()
                    .sort(null)
                    .value(function(d) { return d.value2; });

                    var svgGeo = d3.select("#profileGeoLocation").append("svg")
                    .attr("width", width)
                    .attr("height", height)
                    .append("g")
                    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieProfileGeoLocation.jsp<%=args%>", function(error, data) {

                        var gl = svgGeo.selectAll(".arcOver")
                        .data(pieE(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.6")
                        .style("fill", function(d) { return d.data.color; });

                        var tooltips = svgGeo.select("#profileGeoLocation")
                        .data(pie(data))
                        .enter().append("div")
                        .attr("class","chartToolTip")
                        .style("display", "none")
                        .style("position", "absolute")
                        .style("z-index", "10");

                        tooltips.append("p")
                        .append("span")
                        .html(function(d) {                
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value2+"%";;
                        });       
        
        
                        var g = svgGeo.selectAll(".arc")
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

                        svgGeo
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
                            geoArray.push(data[i].label2);                                            
                        }  
                                          
                        for (var j = 0; j <geoArray.length ; j++) {   
                            var para = document.createElement("p");                                  
                            var node = document.createTextNode( geoArray[j] );
                            para.appendChild(node);
                            var element=document.getElementById("profileGeoLocationParent");
                            element.appendChild(para);
                        } 
        
                    });

                </script>
            
                <div id="lifeStageParent">
                    <div id="profileLifeStage">
                        <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("lifeStage", lang)%></h1>
                    </div>
                </div>
                <script>

                    var width = 760,
                    height = 300,
                    radius = Math.min(width, height) / 2;
                    var lifeStageArray = new Array();
                    var arcLife = d3.svg.arc()
                    .outerRadius(radius - 10)
                    .innerRadius(0);

                    var pieLife = d3.layout.pie()
                    .sort(null)
                    .value(function(d) { return d.value2; });

                    var svgLife = d3.select("#profileLifeStage").append("svg")
                    .attr("width", width)
                    .attr("height", height)
                    .append("g")
                    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                    d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieLifeStage.jsp<%=args%>", function(error, data) {

                        var gl = svgLife.selectAll(".arcOver")
                        .data(pieE(data))
                        .enter().append("g")
                        .attr("class", "arcOver")
                        .style("visibility","hidden");
            
                        gl.append("path")
                        .attr("d", arcOver)
                        .style("fill-opacity", "0.6")
                        .style("fill", function(d) { return d.data.color; });

                        var tooltips = svgLife.select("#profileGeoLocation")
                        .data(pie(data))
                        .enter().append("div")
                        .attr("class","chartToolTip")
                        .style("display", "none")
                        .style("position", "absolute")
                        .style("z-index", "10");

                        tooltips.append("p")
                        .append("span")
                        .html(function(d) {                
                            return "<strong>"+d.data.label+"</strong><br>"+d.data.value2+"%";
                        });       
        
        
                        var g = svgLife.selectAll(".arc")
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

                        svgLife
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
                            lifeStageArray.push(data[i].label2);                                            
                        }  
                                          
                        for (var j = 0; j <lifeStageArray.length ; j++) {   
                            var para = document.createElement("p");                                  
                            var node = document.createTextNode( lifeStageArray[j] );
                            para.appendChild(node);
                            var element=document.getElementById("lifeStageParent");
                            element.appendChild(para);
                        }         
                    });
                </script>
            




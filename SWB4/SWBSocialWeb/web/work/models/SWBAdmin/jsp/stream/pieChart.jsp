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
    args += "&idModel="+ idModel;

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
            
        function valid(id){
            if(id ==1){    
                var selectAnio=  document.getElementById("selectAnio").value;
                if(selectAnio== ""){
                    alert('Seleccione el año')
                    return;
                }
            }else{                  
                var selectAnio2=  document.getElementById("selectAnio2").value;
                var selectMes = document.getElementById("selectMes").value;
                if(selectAnio2== ""){
                    alert('Seleccione el año')
                    return;
                }
                if(selectMes== ""){
                    alert('Seleccione el mes')
                    return;
                }
            }                   
        }
    </script>
</head>

<fieldset>
    <div id="pieChart">
        <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("sentimentProm", lang)%>: <%=title%></h1>
    </div>
    <script src="http://d3js.org/d3.v3.min.js"></script>    
    <script>

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
        .outerRadius(radius - 10 )
        .innerRadius(0);
        

        var svg = d3.select("#pieChart").append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");

        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/ObjsentimentData.jsp<%=args%>", function(error, data) {

            var gl = svg.selectAll(".arcOver")
            .data(pie(data))
            .enter().append("g")
            .attr("class", "arcOver")
            .style("visibility","hidden");
            
            gl.append("path")
            .attr("d", arcOver)
            .style("fill-opacity", "0.6")
            .style("fill", function(d) { return d.data.color; });

            var tooltips = svg.select("#pieChart")
            .data(pie(data))
            .enter().append("div")
            .attr("class","chartToolTip")
            .style("display", "none")
            .style("position", "absolute")
            .style("z-index", "10");

            tooltips.append("p")
            .append("span")
            .html(function(d) {                
                return "<strong>"+d.data.label+"</strong><br>"+d.data.value2;
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
        
        });
        


    </script>


    <h1>Mensajes recibidos de :<%=title%></h1>
    <div class="pub-redes">
        <p class="titulo">Tipo de filtro que desea:</p> 
        <form name="formgraphBar" id="formgraphBar" dojoType="dijit.form.Form" method="post" enctype="multipart/form-data" action="">
            <table>
                <tr>

                <select name="select_sh" id="select_sh"  dojoType="dijit.form.Select" onchange="javascript:postHtml('<%=urlRender.setMode("InfographBar")%>?selected='+escape(document.formgraphBar.select_sh[document.formgraphBar.select_sh.selectedIndex].value)+'&suri=<%=URLEncoder.encode(suri)%>', 'selectgraphBar');">
                    <option value="0">--Seleccione--</option>
                    <option value="1">Anual</option>
                    <option value="2">Mensual</option>
                </select>
                </tr>
            </table>
        </form>
    </div>
    <div id="selectgraphBar" dojoType="dijit.layout.ContentPane">
    </div>
</fieldset>



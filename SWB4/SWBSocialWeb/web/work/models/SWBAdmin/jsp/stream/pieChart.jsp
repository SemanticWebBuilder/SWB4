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

%>

<%
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
    args += "&lang=" + lang;

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
<body>

    <h1><%=SWBSocialUtil.Util.getStringFromGenericLocale("sentimentProm", lang)%>: <%=title%></h1>

    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script>

        var width = 960,
        height = 500,
        radius = Math.min(width, height) / 2;

        var arc = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);

        var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.value2; });

        var svg = d3.select("body").append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/ObjsentimentData.jsp<%=args%>", function(error, data) {

            var g = svg.selectAll(".arc")
            .data(pie(data))
            .enter().append("g")
            .attr("class", function(d) { return d.data.chartclass; }); 

            g.append("path")
            .attr("d", arc)
            .style("fill", function(d) { return d.data.color; });

            g.append("text")
            .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
            .attr("dy", ".35em")
            .style("text-anchor", "middle")
            .text(function(d) { return d.data.label+"("+d.data.value1+"/"+d.data.value2+"%)" });

        });

    </script>


<html>
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
                        alert('Debe seleccionar el año')
                        return;
                    }
                }else{
                  
                    var selectAnio2=  document.getElementById("selectAnio2").value;
                    var selectMes = document.getElementById("selectMes").value;
                    if(selectAnio2== ""){
                        alert('Debe seleccionar el año')
                        return;
                    }
                    if(selectMes== ""){
                        alert('Debe seleccionar el mes')
                        return;
                    }
                }
           
               
            }
        </script>
    </head>

    <body>

        <h1>Mensajes recibidos de : <%=title%></h1>
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
</body>
</html>
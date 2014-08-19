<%-- 
    Document   : streamComparator
    Created on : 18/08/2014, 12:10:26 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.social.Stream"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.SocialSite"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.apache.commons.validator.UrlValidator"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="static org.semanticwb.social.admin.resources.ImportFanPages.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>


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
    
</head>
<div dojoType="dijit.layout.ContentPane">
<%
    String suri = request.getParameter("suri");
    SocialSite socialSite = (SocialSite) SemanticObject.createSemanticObject(suri).createGenericInstance();
    System.out.println("THIS IS THE GLOBAL REPORT");
    Iterator<Stream> streams = socialSite.listStreams();
    
    String[] selectedStreams = request.getParameterValues("streams");        
    
%>


<form name="compairStreams" id="compairStreams" action="<%=paramRequest.getRenderUrl().setParameter("suri", suri).setParameter("doView", "1")%>" onsubmit="submitForm('compairStreams'); try{document.getElementById('csLoadingStreams').style.display='inline';}catch(noe){}; return false;">
<table width="50%" border="0px">            
   <tr>
       <td colspan="3" style="text-align: center;" class="titulo">
           <div id="msj-eliminar">
                 <div class="bloque bloque2" style="margin-left: 20%;">
                    <p class="bloqtit">Streams de la marca</p>
                    <select name="streams" multiple size="5" class="bloqsel">
<%
                while(streams.hasNext()){
                    Stream st = streams.next();                    
%>
                      <option value="<%=st.getEncodedURI()%>"><%=st.getTitle()%></option>
<%
                    }
%>
                    </select>
                </div>                    
            </div>           
       </td>
   </tr>
</table>
<div style="width:50%;" align="center">
    <button dojoType="dijit.form.Button" type="submit">Comparar Streams</button>
</div>
</form>

<div align="center"><span id="csLoadingStreams" style="width: 100px; display: none" align="center"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/loading.gif"/></span></div>

<%
if(selectedStreams != null && selectedStreams.length>0){
    System.out.print("GENERATING REPORT!!");
    streams = socialSite.listStreams();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    String lang = paramRequest.getUser().getLanguage();
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
            //alert('entro');
            var content='';               
            
            var url='<%=renderURL.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>';
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
            //alert('entro al send form');
            var _form = dojo.byId(id);
                
            var xhrArgs = {
                form: _form,
                handleAs: "text",
                load: function(data){
                    //dojo.byId("response").innerHTML = "Form posted.";
                    //alert('respuesta: '+data);
                    funcion();
                },
                error: function(error){
                    //alert('error'+error);
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
    <div id="pieGenderParent" style="width:250px; height: 250px;">
        <div class="grafTit">
            <h1><%="G&eacute;nero"%></h1>
            <a id="hrefGender" href=""  onclick="return confirm('Desea exportar a excel')" class="excel">Exportar excel</a>
        </div>
        <div id="pieGenderComparator">
        </div>        
        <div class="clear"></div>   
    </div>
            

</div>
            
<script>    
        function pieGender(){   
            document.getElementById('pieGenderComparator').innerHTML="";
            var val = "";

            document.getElementById("hrefGender").href= "<%=renderURL.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
          
            var width = 200,
            height = 200,
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
            
            data = [{"value2":"5.33","color":"#FF0000","value1":"106","valor":{"positivos":"8","negativos":"4","neutros":"94"},"label":"Masculino","label2":"Masculino: 106     -     Positivos : 8 Negativos :4 Neutros : 94","chartclass":"possClass","label3":{"positivos":"640","negativos":"183","neutros":"1166"}},{"value2":"1.06","color":"#838383","value1":"21","valor":{"positivos":"10","negativos":"1","neutros":"10"},"label":"Femenino","label2":"Femenino: 21     -     Positivos : 10 Negativos :1 Neutros : 10","chartclass":"possClass","label3":"Total de Post: 1989"},{"value2":"93.61","color":"#838383","value1":"1862","valor":{"positivos":"622","negativos":"178","neutros":"1062"},"label":"No definido","label2":"No definido: 1862     -     Positivos  : 622 Negativos  :178 Neutros : 1062","chartclass":"possClass","label3":"Total de Post: 1989"}];
            //d3.json("<%//=SWBPlatform.getContextPath()%>/work/models/<%//=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieGender.jsp<%=""%>&filter="+parametro, function(error, data) {
                    
               
                var svg = d3.select("#pieGenderComparator").append("svg")
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

                var tooltips = svg.select("#pieGenderComparator")
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
                    if(confirm('Desea exportar a excel')){
                        var filter = d.data.label; 
                        var url = "<%=renderURL.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
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
            //});
                
        }
    
        pieGender();//only the first load the last param is true
    
    </script>
<%
}
%>
</div>
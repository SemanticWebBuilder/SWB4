<%-- 
    Document   : reporter.jso
    Created on : 09-oct-2013, 10:41:08
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


<%
    /*
     Enumeration enParamNames=request.getParameterNames();
     while(enParamNames.hasMoreElements())
     {
     String paramName=(String)enParamNames.nextElement(); 
     System.out.println("paramName:"+paramName+",value:"+request.getParameter(paramName));
     }*/

    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    User user = paramRequest.getUser();
    String title = "";
    if (semObj.getGenericInstance() instanceof Descriptiveable) {
        title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(user.getLanguage());
    }
    SWBResourceURL url = paramRequest.getRenderUrl();
    url.setParameter("suri", suri);
    url.setParameter("doView", "1");
    url.setAction("showChart");
    String gender = "";
    String schoolGrade = "";
    String slifeStage = "";
    String sentimentalRelationShip = "";
    String scountryState = "";
    String sinceDate = "";
    String toDate = "";
    if (paramRequest.getAction() != null && paramRequest.getAction().equals("showChart")) {
        gender = request.getParameter("gender");
        schoolGrade = request.getParameter("schoolGrade");
        slifeStage = request.getParameter("lifeStage");
        sentimentalRelationShip = request.getParameter("sentimentalRelationShip");
        scountryState = request.getParameter("countryState");
        sinceDate = request.getParameter("sinceDate");
        toDate = request.getParameter("toDate");
    }
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
    <!--<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/schedule.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/fileUpload.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/jsp/reports/tagcanvas.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>-->
    <script type="text/javascript">
        dojo.require("dijit.form.DateTextBox");
    </script>
    <script type="text/javascript">
        function showPie(name){
            var name = name;
            document.getElementById(name).action = '<%=url%>';
            document.getElementById(name).submit();
        }
        function exportExcel(name){ 
            document.getElementById(name).action = '<%=url.setMode("export").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("objUri", semObj.getURI()).setParameter("lang", paramRequest.getUser().getLanguage())%>';
            document.getElementById(name).submit();
        }
           
    </script>
    <script type="text/javascript">
        dojo.require("dijit.form.DateTextBox");
    </script>
</head>
<body class="soria">
    <div id="reporteador" class="swbform">
        <p class="promediosen"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("sentimentProm", user.getLanguage())%>:<span> <%=title%></span></p>
        <form id="<%=semObj.getSemanticClass().getClassId()%>/reporterFilter" name="<%=semObj.getSemanticClass().getClassId()%>/reporterFilter" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=url%>" method="post" onsubmit="submitForm('<%=semObj.getSemanticClass().getClassId()%>/reporterFilter');return false;"> 
            <div class="combosFilter">
                <div id="genero-box">
                    <label for="gen">Genero</label>
                    <select name="gender">
                        <option value="all">Todos</option>
                        <option value="<%=SocialNetworkUser.USER_GENDER_MALE%>" <%=gender.equals("" + SocialNetworkUser.USER_GENDER_MALE) ? "selected" : ""%>>Hombre</option> 
                        <option value="<%=SocialNetworkUser.USER_GENDER_FEMALE%>" <%=gender.equals("" + SocialNetworkUser.USER_GENDER_FEMALE) ? "selected" : ""%>>Mujer</option>
                        <option value="<%=SocialNetworkUser.USER_GENDER_UNDEFINED%>" <%=gender.equals("" + SocialNetworkUser.USER_GENDER_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>
                <div id="estudios-box">
                    <label for="school">Estudios</label>
                    <select name="schoolGrade">
                        <option value="all">Todos</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL%>" <%=schoolGrade.equals("" + SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) ? "selected" : ""%>>Secundaria, Preparatoria</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_COLLEGE%>" <%=schoolGrade.equals("" + SocialNetworkUser.USER_EDUCATION_COLLEGE) ? "selected" : ""%>>Universidad</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_GRADUATE%>" <%=schoolGrade.equals("" + SocialNetworkUser.USER_EDUCATION_GRADUATE) ? "selected" : ""%>>PostGrado</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_UNDEFINED%>" <%=schoolGrade.equals("" + SocialNetworkUser.USER_EDUCATION_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>
                <div id="etapa-box">
                    <label for="life">Etapa</label>
                    <select name="lifeStage">
                        <option value="all">Todos</option>
                       
                        <%
                            Iterator<LifeStage> itLifeStages = SWBComparator.sortByCreated(LifeStage.ClassMgr.listLifeStages(SWBContext.getAdminWebSite()));
                            while (itLifeStages.hasNext()) {
                                LifeStage lifeStage = itLifeStages.next();
                        %>
                        <option value="<%=lifeStage.getId()%>" <%=slifeStage.equals(lifeStage.getId()) ? "selected" : ""%>><%=lifeStage.getTitle(user.getLanguage())%></option>
                        <%
                            }
                        %>
                         <option value="noDefinido" <%=slifeStage.equals("noDefinido") ? "selected" : ""%>>No definido</option>
                    </select>
                </div>
                <div id="statuslove-box">
                    <label for="sentimental">Estado civil</label>
                    <select name="sentimentalRelationShip">
                        <option value="all">Todos</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_SINGLE%>" <%=sentimentalRelationShip.equals("" + SocialNetworkUser.USER_RELATION_SINGLE) ? "selected" : ""%>>Soltero</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_MARRIED%>" <%=sentimentalRelationShip.equals("" + SocialNetworkUser.USER_RELATION_MARRIED) ? "selected" : ""%>>Casado</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_DIVORCED%>" <%=sentimentalRelationShip.equals("" + SocialNetworkUser.USER_RELATION_DIVORCED) ? "selected" : ""%>>Divorciado</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_WIDOWED%>" <%=sentimentalRelationShip.equals("" + SocialNetworkUser.USER_RELATION_WIDOWED) ? "selected" : ""%>>Viudo</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_UNDEFINED%>" <%=sentimentalRelationShip.equals("" + SocialNetworkUser.USER_RELATION_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>

                <div id="estado-box">
                    <label for="country">Estado</label>
                    <select name="countryState">
                        <option value="all">Todos</option>
                        <%
                            Iterator<org.semanticwb.social.Country> itCountries = org.semanticwb.social.Country.ClassMgr.listCountries(SWBContext.getAdminWebSite());
                            while (itCountries.hasNext()) {
                                org.semanticwb.social.Country country = itCountries.next();
                                Iterator<CountryState> itCountryStates = CountryState.ClassMgr.listCountryStateByCountry(country, SWBContext.getAdminWebSite());
                                while (itCountryStates.hasNext()) {
                                    CountryState countryState = itCountryStates.next();
                        %>
                        <option value="<%=countryState.getId()%>" <%=scountryState.equals(countryState.getId()) ? "selected" : ""%>><%=country.getTitle()%>/<%=countryState.getTitle()%></option>
                        <%
                                }
                            }
                        %>    
                         <option value="estadonoDefinido" <%=slifeStage.equals("estadonoDefinido") ? "selected" : ""%>>No definido</option>
                    </select>
                </div>

                <div id="mapa-fecha">
                    <label>Del día</label>
                    <input type="text" name="sinceDate" id="sinceDate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true">
                    <label for="toDate"> al día:</label>
                    <input type="text" name="toDate" id="toDate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true">

                </div>
                <div id="mapa-buscar">
                    <button dojoType="dijit.form.Button" type="submit" onclick="showPie('<%=semObj.getSemanticClass().getClassId()%>/reporterFilter');return false; "><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                    <button dojoType="dijit.form.Button" type="submit"  onclick="exportExcel('<%=semObj.getSemanticClass().getClassId()%>/reporterFilter');return false;">Exportar Excel </button>
                </div>
            </div>
        </form>
    </div>
    <%
        if (paramRequest.getAction().equals("showChart")) {
            String args = "?objUri=" + semObj.getEncodedURI();
            String lang = paramRequest.getUser().getLanguage();
            args += "&lang=" + lang;
            args += "&gender=" + gender;
            args += "&schoolGrade=" + schoolGrade;
            args += "&slifeStage=" + slifeStage;
            args += "&sentimentalRelationShip=" + sentimentalRelationShip;
            args += "&scountryState=" + scountryState;
            args += "&sinceDate=" + sinceDate;
            args += "&toDate=" + toDate;
    %>
    <script src="http://d3js.org/d3.v3.min.js"></script>

    <script>

        var width = 960,
        height = 500,
        radius = Math.min(width, height) / 2;

        var arc = d3.svg.arc()
        .outerRadius(radius - 20)
        .innerRadius(radius - 100);
        
        var arcOver = d3.svg.arc()
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

        d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/reporterChartData.jsp<%=args%>", function(error, data) {

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
                return "<strong>"+d.data.label+"</strong><br>"+d.data.value1+"/"+d.data.value2+"%" ;           
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
                        
            svg.append("text")
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
    <%
        }
    %>

</body>
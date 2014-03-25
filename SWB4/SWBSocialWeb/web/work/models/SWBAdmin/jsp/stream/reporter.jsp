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

<%!
    public static boolean isAllSelected(String params[]){
        boolean isAllSelected = false;
        if(params == null){//If the param is null then use all
            isAllSelected = true;
        }else{
            for(int i = 0; i < params.length ; i++){
                if(params[i].equalsIgnoreCase("all")){
                    isAllSelected = true;
                }
            }
        }
        return isAllSelected;
    }

    public static void getParams(String params[], HashMap<String,String> hm){
        if(isAllSelected(params)){
            hm.put("all", "all");
        }else{
            for(int i = 0 ; i < params.length ; i++){
                hm.put(params[i], params[i]);
            }
        }
    }
%>

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
    HashMap <String,String> genderP = new HashMap<String,String>();
    HashMap <String,String> schoolGradeP = new HashMap<String,String>();
    HashMap <String,String> slifeStageP = new HashMap<String,String>();
    HashMap <String,String> sentimentalRelationShipP = new HashMap<String,String>();
    HashMap <String,String> scountryStateP = new HashMap<String,String>();    
    String sinceDate = "";
    String toDate = "";
    if (paramRequest.getAction() != null && paramRequest.getAction().equals("showChart")) {
        getParams(request.getParameterValues("gender"), genderP);
        getParams(request.getParameterValues("schoolGrade"), schoolGradeP);
        getParams(request.getParameterValues("lifeStage"), slifeStageP);
        getParams(request.getParameterValues("sentimentalRelationShip"), sentimentalRelationShipP);
        getParams(request.getParameterValues("countryState"), scountryStateP);        
        sinceDate = request.getParameter("sinceDate");               
        toDate = request.getParameter("toDate");        
        /*System.out.println(genderP.toString());
        System.out.println(schoolGradeP.toString());
        System.out.println(slifeStageP.toString());
        System.out.println(sentimentalRelationShipP.toString());
        System.out.println(scountryStateP.toString());*/
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
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/schedule.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/fileUpload.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/jsp/reports/tagcanvas.js" ></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>
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
                <div id="genero-box" style="height: 70px;">
                    <label for="gen">G&eacute;nero</label>
                    <select name="gender" multiple>
                        <option value="all" <%=genderP.containsKey("all") || genderP.size() == 0 ? "selected" : ""%>>Todos</option>
                        <option value="<%=SocialNetworkUser.USER_GENDER_MALE%>" <%=genderP.containsKey("" + SocialNetworkUser.USER_GENDER_MALE) ? "selected" : ""%>>Hombre</option> 
                        <option value="<%=SocialNetworkUser.USER_GENDER_FEMALE%>" <%=genderP.containsKey("" + SocialNetworkUser.USER_GENDER_FEMALE) ? "selected" : ""%>>Mujer</option>
                        <option value="<%=SocialNetworkUser.USER_GENDER_UNDEFINED%>" <%=genderP.containsKey("" + SocialNetworkUser.USER_GENDER_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>
                <div id="estudios-box" style="height: 70px;">
                    <label for="school">Estudios</label>
                    <select name="schoolGrade" multiple>
                        <option value="all" <%=schoolGradeP.containsKey("all") || schoolGradeP.size() == 0 ? "selected" : ""%>>Todos</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL%>" <%=schoolGradeP.containsKey("" + SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) ? "selected" : ""%>>Secundaria, Preparatoria</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_COLLEGE%>" <%=schoolGradeP.containsKey("" + SocialNetworkUser.USER_EDUCATION_COLLEGE) ? "selected" : ""%>>Universidad</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_GRADUATE%>" <%=schoolGradeP.containsKey("" + SocialNetworkUser.USER_EDUCATION_GRADUATE) ? "selected" : ""%>>PostGrado</option>
                        <option value="<%=SocialNetworkUser.USER_EDUCATION_UNDEFINED%>" <%=schoolGradeP.containsKey("" + SocialNetworkUser.USER_EDUCATION_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>
                <div id="etapa-box" style="height: 70px;">
                    <label for="life">Etapa</label>
                    <select name="lifeStage" multiple>
                        <option value="all" <%=slifeStageP.containsKey("all") || slifeStageP.size() == 0 ? "selected" : ""%>>Todos</option>
                       
                        <%
                            Iterator<LifeStage> itLifeStages = SWBComparator.sortByCreated(LifeStage.ClassMgr.listLifeStages(SWBContext.getGlobalWebSite()));
                            while (itLifeStages.hasNext()) {
                                LifeStage lifeStage = itLifeStages.next();
                                String lifeStageTitle = lifeStage.getTitle(user.getLanguage()) == null ? lifeStage.getTitle() : lifeStage.getTitle(user.getLanguage());
                        %>
                        <option value="<%=lifeStage.getId()%>" <%=slifeStageP.containsKey(lifeStage.getId()) ? "selected" : ""%>><%=lifeStageTitle%></option>
                        <%
                            }
                        %>
                         <option value="noDefinido" <%=slifeStageP.containsKey("noDefinido") ? "selected" : ""%>>No definido</option>
                    </select>
                </div>
                <div id="statuslove-box" style="height: 70px;">
                    <label for="sentimental">Estado civil</label>
                    <select name="sentimentalRelationShip" multiple>
                        <option value="all" <%=sentimentalRelationShipP.containsKey("all") || genderP.size() == 0 ? "selected" : ""%>>Todos</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_SINGLE%>" <%=sentimentalRelationShipP.containsKey("" + SocialNetworkUser.USER_RELATION_SINGLE) ? "selected" : ""%>>Soltero</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_MARRIED%>" <%=sentimentalRelationShipP.containsKey("" + SocialNetworkUser.USER_RELATION_MARRIED) ? "selected" : ""%>>Casado</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_DIVORCED%>" <%=sentimentalRelationShipP.containsKey("" + SocialNetworkUser.USER_RELATION_DIVORCED) ? "selected" : ""%>>Divorciado</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_WIDOWED%>" <%=sentimentalRelationShipP.containsKey("" + SocialNetworkUser.USER_RELATION_WIDOWED) ? "selected" : ""%>>Viudo</option>
                        <option value="<%=SocialNetworkUser.USER_RELATION_UNDEFINED%>" <%=sentimentalRelationShipP.containsKey("" + SocialNetworkUser.USER_RELATION_UNDEFINED) ? "selected" : ""%>>Indefinido</option>
                    </select>
                </div>

                <div id="estado-box" style="height: 130px;">
                    <label for="country">Estado</label>
                    <select name="countryState" multiple="" size="8">
                        <option value="all" <%=scountryStateP.containsKey("all") || scountryStateP.size() == 0 ? "selected" : ""%>>Todos</option>
                        <%
                            Iterator<org.semanticwb.social.Country> itCountries = org.semanticwb.social.Country.ClassMgr.listCountries(SWBContext.getGlobalWebSite());
                            while (itCountries.hasNext()) {
                                org.semanticwb.social.Country country = itCountries.next();
                                Iterator<CountryState> itCountryStates = CountryState.ClassMgr.listCountryStateByCountry(country, SWBContext.getGlobalWebSite());
%>
                                <optgroup label="<%=country.getTitle()%>">
<%
                                while (itCountryStates.hasNext()) {
                                    CountryState countryState = itCountryStates.next();
                        %>
                        <option value="<%=countryState.getId()%>" <%=scountryStateP.containsKey(countryState.getId()) ? "selected" : ""%>><%//=country.getId()%><%=countryState.getTitle()%></option>
                        <%
                                }
%>
                                </optgroup>
<%
                            }
                        %>    
                         <option value="estadonoDefinido" <%=scountryStateP.containsKey("estadonoDefinido") ? "selected" : ""%>>No definido</option>
                    </select>
                </div>

                <div id="mapa-fecha">
                    <label>Del día</label>
                    <input type="text" name="sinceDate" id="sinceDate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=sinceDate%>">
                    <label for="toDate"> al día:</label>
                    <input type="text" name="toDate" id="toDate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=toDate%>">

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
            String genderParams[] = request.getParameterValues("gender");
            String schoolGradeParams[] = request.getParameterValues("schoolGrade");
            String slifeStageParams[] = request.getParameterValues("lifeStage");
            String sentimentalRelationShipParams[] = request.getParameterValues("sentimentalRelationShip");
            String scountryStateParams[] = request.getParameterValues("countryState");
            String genderReport = "";
            String schoolReport = "";
            String slifeStateReport = "";
            String sentimentalRelReport = "";
            String scountryReport = "";

            if(isAllSelected(genderParams)){
                genderReport = "&gender=all";
            }else{
                for(int i = 0; i < genderParams.length; i++){
                    genderReport += "&gender=" + genderParams[i];
                }
            }

            if(isAllSelected(schoolGradeParams)){
                schoolReport = "&schoolGrade=all";
            }else{
                for(int i = 0; i < schoolGradeParams.length; i++){
                    schoolReport += "&schoolGrade=" + schoolGradeParams[i];
                }
            }

            if(isAllSelected(slifeStageParams)){
                slifeStateReport = "&slifeStage=all";
            }else{
                for(int i = 0; i < slifeStageParams.length; i++){
                    slifeStateReport += "&slifeStage=" + slifeStageParams[i];
                }
            }

            if(isAllSelected(sentimentalRelationShipParams)){
                sentimentalRelReport = "&sentimentalRelationShip=all";
            }else{
                for(int i = 0; i < sentimentalRelationShipParams.length; i++){
                    sentimentalRelReport += "&sentimentalRelationShip=" + sentimentalRelationShipParams[i];
                }
            }

            if(isAllSelected(scountryStateParams)){
                scountryReport = "&scountryState=all";
            }else{
                for(int i = 0; i < scountryStateParams.length; i++){
                    scountryReport += "&scountryState=" + scountryStateParams[i];
                }
            }


            /*System.out.println(genderReport);
            System.out.println(schoolReport);
            System.out.println(slifeStateReport);
            System.out.println(sentimentalRelReport);
            System.out.println(scountryReport);*/

            String args = "?objUri=" + semObj.getEncodedURI();
            String lang = paramRequest.getUser().getLanguage();
            args += "&lang=" + lang;
            args += genderReport;
            args += schoolReport;
            args += slifeStateReport;
            args += sentimentalRelReport;
            args += scountryReport;
            args += "&sinceDate=" + sinceDate;
            args += "&toDate=" + toDate;
    %>

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
<%-- 
    Document   : postInByHour
    Created on : 25/04/2014, 12:09:44 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%> 
<%@page import="java.util.Calendar"%> 

<%!

    /**
    * 
    * @param stream
    * @param a date in the format yyyy-mm-dd
    * @return the posts created some day.
    */
   public static ArrayList getPostInByStreamAndDay(org.semanticwb.social.Stream stream, String date)
   {
       System.out.println("entrando por los datos!");
       if(date == null || date.isEmpty()){
           return null;
       }
       String query=
          "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
          "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>\n" +
          "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
          "\n";

          query+="select ?semObj" +"\n";
          query+=
          "where {\n" +
          " ?semObj social:postInStream <"+ stream.getURI()+">. \n" + 
          " ?semObj social:pi_createdInSocialNet ?postInCreated. \n" +
          " FILTER regex(?postInCreated, \"" + date + "\", \"i\") \n" +
          "  }\n";

          WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
          return SWBSocial.executeQueryArraySemObj(query, wsite);
    }
        
    JSONArray getObject(SemanticObject semObj, String lang, HttpServletRequest request) throws Exception {
        Iterator<PostIn> itObjPostIns = null;
        String fullDate = "";
        ArrayList aListFilter = new ArrayList();
        String selectedAnio = request.getParameter("selectedAnio") == null ? "" : request.getParameter("selectedAnio");
        String selectedMes = request.getParameter("selectedMes") == null ? "" : request.getParameter("selectedMes");
        String selectedDia = request.getParameter("selectedDia") == null ? "" : request.getParameter("selectedDia");
        
        if(selectedAnio.trim().isEmpty() || selectedMes.trim().isEmpty() || selectedDia.trim().isEmpty()){
            JSONArray node = new JSONArray();
            return node;
        }
        fullDate += selectedAnio + "-" + (selectedMes.length() == 1 ? "0" + selectedMes : selectedMes) +
                 "-" + (selectedDia.length() == 1 ? "0" + selectedDia : selectedDia);
        
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            aListFilter = getPostInByStreamAndDay(stream, fullDate);            

        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            //aListFilter = getPostInByStreamAndDay(stream, date);
            //SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            //itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        
        if (aListFilter.size() == 0) {
            JSONArray node = new JSONArray();
            return node;
        }
        
        java.util.Date date = null;
        Calendar calendario = GregorianCalendar.getInstance();

        JSONArray node = new JSONArray();

        int nPostIn = 0;
        // Get the number of days in that month 
        int days = 24;
        int neutrals_ = 0, positives_ = 0, negatives_ = 0;
        int[][] dias = new int[days][7];
            
        for(int i = 0; i < aListFilter.size(); i++){
            SemanticObject sobj =(SemanticObject) aListFilter.get(i);
            PostIn postIn = (PostIn)sobj.createGenericInstance();
            
            if (postIn.getPostSentimentalType() == 0) {
                neutrals_++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positives_++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negatives_++;
            }
            if (postIn.getPi_createdInSocialNet() != null) {
                date = postIn.getPi_createdInSocialNet();
            }
            nPostIn++;

            calendario.setTime(date);

            int year = calendario.get(Calendar.YEAR);
            int comMonth = calendario.get(Calendar.MONTH);
            int hourIndex = calendario.get(Calendar.HOUR_OF_DAY);

            dias[hourIndex][0] += 1;
            dias[hourIndex][1] += neutrals_;
            dias[hourIndex][2] += negatives_;
            dias[hourIndex][3] += positives_;
            dias[hourIndex][4] = comMonth;
            dias[hourIndex][5] = year;
            dias[hourIndex][6] = calendario.get(Calendar.DAY_OF_MONTH);

            neutrals_ = 0;
            negatives_ = 0;
            positives_ = 0;
        }


        int d = 1;
        for (int idx = 0; idx < dias.length; idx++) {
            if (d <= days) {
                Object elem = dias[idx][0];
                int neutrals_s = dias[idx][1];
                int negatives_s = dias[idx][2];
                int positives_s = dias[idx][3];
                int mes = dias[idx][4];
                int year = dias[idx][5];
                int dia = dias[idx][6];

                JSONObject node1 = new JSONObject();
                node1.put("day", dia);
                node1.put("month", (idx + 1));
                node1.put("month2", mes);
                node1.put("year", year);
                node1.put("neutrals", neutrals_s);
                node1.put("positives", positives_s);
                node1.put("negatives", negatives_s);
                node1.put("post", elem);
                node1.put("chartclass", "possClass");
                node1.put("x", days);
                double totalPost = (.50) * (nPostIn);
                node1.put("totalPost", nPostIn + totalPost);
                node1.put("typeX", "Horas");
                node.put(node1);
                d++;
            }
        }

        System.out.println("THE NODE:" + node);
        return node;

    }


%>

<%    

    System.out.println("graficando......");
    System.out.println("objUri" + request.getParameter("objUri"));
    System.out.println("data:" + request.getParameter("getGraphData"));
    
    String selectedAnio1 = request.getParameter("selectedAnio");
    String selectedMes1 = request.getParameter("selectedMes");
    String selectedDia1 = request.getParameter("selectedDia");
    System.out.println("params:" + " " + selectedAnio1 + "-" + selectedMes1 + "-" + selectedDia1 +"-");
    if (request.getParameter("objUri") != null && request.getParameter("getGraphData") != null) {        
        SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        out.println(getObject(semObj, lang, request));
        return;
    }else{//Prints the html to allocate the graph and calls itself to load the data of the graph
%>

<%
    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    System.out.println(semObj);
    if (semObj == null) {
        return;
    }

    String args = "?objUri=" + semObj.getEncodedURI();

    String selectedAnio = request.getParameter("selectedAnio");
    String selectedMes = request.getParameter("selectedMes");
    String selectedDia = request.getParameter("selectedDia");
    args += "&selectedAnio=" + selectedAnio;
    args += "&selectedMes=" + selectedMes;
    args += "&selectedDia=" + selectedDia;
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");    
    SWBResourceURL urlRender = paramRequest.getRenderUrl();
%>


<html>
    <head>
        <script src="http://d3js.org/d3.v3.min.js"></script>
        <script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.min.js"></script>
        <script language="javascript" type="text/javascript">
            function resizeIframe() {
                var iframe =  window.parent.document.getElementById('inneriframe1');              
                var container = document.getElementById('chart');     
                iframe.style.height = container.offsetHeight + 'px';             
            }
        </script>
        <meta charset="utf-8">
        <style type="text/css">
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
                fill: orange ;
            }

            .bar_neutrals {
                fill: #FFD700;
            }

            .bar_neutrals:hover {
                fill: #FFD700 ;
            }

            .bar_negatives {
                fill: #FF0000;
            }

            .bar_negatives:hover {
                fill: #FF0000;
            }


            .bar_positives {
                fill: #008000;
            }

            .bar_positives:hover{
                fill: #008000 ;
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

            /* Creates a small triangle extender for the tooltip */
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

            /* Style northward tooltips differently */
            .d3-tip.n:after {
                margin: -1px 0 0 0;
                top: 100%;
                left: 0;
            }


        </style>
    </head>
    <body onload="resizeIframe();javascript:valid('1');">        

        <div id="chart"></div>
        <script type="text/javascript" >

            var margin = {top: 20, right: 20, bottom: 30, left: 40},
            width = 960 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

            var x = d3.scale.ordinal().rangeRoundBands([1, width]);
            var y = d3.scale.linear().range([height, 0]);
            
            var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom");
            //.tickSize(0);

            var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");
                          
            var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .html(function(d) {
                return "<strong>Numero de post:</strong> <span style='color:#FFFFFF'>" + d.post + "</span><br>\n\
                            <strong>Neutros:</strong>  <span style='color:#D8D8D8'>" + d.neutrals + "</span><br>  \n\
                            <strong>Positivos:</strong>  <span style='color:#04B431'>" + d.positives + "</span><br> \n\
                            <strong>Negativos:</strong>  <span style='color:red'>" + d.negatives + "</span><br>           ";
            })
            //});        //var data = [[1,100],[6,20],[20, 50]];

            var xArray = new Array();

            //d3.json("<%//=SWBPlatform.getContextPath()%>/work/models/<%//=SWBContext.getAdminWebSite().getId()%>/jsp/stream/InfoGraphBar.jsp<%//=args%>", function(error, data) {
            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/postInByHour.jsp<%=args%>&getGraphData=true", function(error, data) {
                //function(d) { alert('entro');return x(data.day);}
                // alert(data[1].x);
                var typeX ;
                var neutrals ;
                var positives;
                var negatives; 
                
                for (i = 0; i < data[1].x; i++) {               
                    xArray.push(data[i].month)                    
                    y.domain([0,data[i].totalPost]);               
                    typeX =  data[i].typeX;
                   
                }  
                x.domain(xArray);                                      

                var svg = d3.select("#chart").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
         
                svg.call(tip);
                svg.append("g")
                .attr("class", "axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Numero de Post");           

        
                svg.append("g")
                .attr("class", "axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .append("text")
                .attr("x", 910)
                .attr("dx", ".20em")
                .style('text-anchor','end')
                .text(typeX);
             
             

               
                svg.selectAll("bar")
                .data(data)
                .enter().append("rect")
                .attr("class",function(d) { 
                    var neutrals = d.neutrals;
                    var positives = d.positives;
                    var negatives = d.negatives;
                    if(positives>negatives && positives>neutrals){
                        return "bar_positives"; 
                    }else if(negatives>neutrals){                        
                        return "bar_negatives";                        
                    }else {
                        return "bar_neutrals";                        
                    }        
                    
                })
                .attr("x", function(d) { return x(d.month);})
                .attr("width", x.rangeBand() )
                .attr("y", function(d) {return y(d.post);})
                .attr("height", function(d) {return height - y(d.post);}) 
                .on('mouseover', tip.show)
                .on('mouseout', tip.hide)
                .on("click", function(d) {                   
                    var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "graphBar").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri)%>&selectedAnio="+d.year+"&selectMes="+d.month+"&selectAnio="+d.year+"&selectDay="+d.day+"&selectMonth2="+d.month2;
                    document.location.href = url;
                }) 
   
            });

        </script>

</html>



<%    
    }
%>
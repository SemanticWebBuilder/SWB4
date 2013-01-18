<%-- 
    Document   : panel4
    Created on : 21-oct-2012, 20:10:15
    Author     : javier.solis.g
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.domotic.model.DomContext"%>
<%@page import="org.semanticwb.base.util.FilterRule"%>
<%@page import="java.util.Collections"%>
<%@page import="org.semanticwb.domotic.model.DomGateway"%>
<%@page import="org.semanticwb.domotic.model.DomDeviceStat"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.domotic.model.DomGroup"%>
<%@page import="org.semanticwb.domotic.model.DomDevice"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.domotic.model.SWB4DContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public void printGroup(DomGroup grp, JspWriter out, String separator, String lang) throws IOException
    {
        String title = grp.getDisplayTitle(lang);
        String name = grp.getShortURI();
        int val = grp.getStatus();
        out.println("                    <li>");
        out.println("                        <a href=\"#p_" + name + "\">" + separator + " " + title + "</a>");
        out.println("                        <div data-role=\"fieldcontain\" style=\"margin-top: -42px; float: right; margin-right: 50px;\">");
        out.println("                            <select name=\"" + name + "\" data-role=\"slider\" onChange=\"changeStat(this.name,this.value)\">");
        out.println("                                <option value=\"0\" " + ((val == 0) ? "selected" : "") + ">OFF</option>");
        out.println("                                <option value=\"16\" " + ((val > 0) ? "selected" : "") + ">ON</option>");
        out.println("                            </select>");
        out.println("                        </div>");
        out.println("                    </li>");

        Iterator it = grp.listChildGroups();
        while (it.hasNext())
        {
            DomGroup hgrp = (DomGroup) it.next();
            printGroup(hgrp, out, "...." + separator, lang);
        }
    }

    public void printDevice(DomDevice dev, JspWriter out, String separator, String lang) throws IOException
    {
        String title = dev.getDisplayTitle(lang);
        String name = dev.getShortURI();
        int val = dev.getStatus();
        out.println("                    <li>");
        out.println("                        <a href_=\"#p_" + name + "\">" + separator + " " + title + "</a>");
        if(dev.isDimerizable())
        {
            out.println("                        <div data-role=\"fieldcontain\" style=\"width: 700px; margin-top: -34px; float: right; margin-right: -200px;\">");
            out.println("                           <input type=\"range\" name=\""+name+"\" value=\""+val+"\" min=\"0\" max=\"16\" data-highlight=\"true\" class=\"ui-hidden-accessible ui-input-text ui-body-c ui-corner-all ui-shadow-inset ui-slider-input\">");        
            out.println("                        </div>");
            out.println("                        <div data-role=\"fieldcontain\" style=\"margin-top: -45px; float: right; margin-right: 50px;\">");
        }else
        {
            out.println("                        <div data-role=\"fieldcontain\" style=\"margin-top: -42px; float: right; margin-right: 50px;\">");
        }
        out.println("                            <select name=\"" + name + "\" data-role=\"slider\" onChange=\"changeStat(this.name,this.value)\">");
        out.println("                                <option value=\"0\" " + ((val == 0) ? "selected" : "") + ">OFF</option>");
        out.println("                                <option value=\"16\" " + ((val > 0) ? "selected" : "") + ">ON</option>");
        out.println("                            </select>");
        out.println("                        </div>");
        out.println("                    </li>");
    }

    public void printContext(DomContext cnt, JspWriter out, String separator, String lang) throws IOException
    {
        String title = cnt.getDisplayTitle(lang);
        String name = cnt.getShortURI();
        boolean val = cnt.isActive();
        out.println("                    <li>");
        out.println("                        <a href_=\"#p_" + name + "\">" + separator + " " + title + "</a>");
        out.println("                        <div data-role=\"fieldcontain\" style=\"margin-top: -42px; float: right; margin-right: 50px;\">");
        out.println("                            <select name=\"" + name + "\" data-role=\"slider\" onChange=\"changeStat(this.name,this.value)\">");
        out.println("                                <option value=\"false\" " + ((!val) ? "selected" : "") + ">OFF</option>");
        out.println("                                <option value=\"true\" " + ((val) ? "selected" : "") + ">ON</option>");
        out.println("                            </select>");
        out.println("                        </div>");
        out.println("                    </li>");
    }
%>
<!DOCTYPE html> 
<html> 
    <head> 
        <title>My Page</title> 
<%
    String contx=SWBPlatform.getContextPath();
    String ws=request.getParameter("ws");
    if(ws==null)ws="true";
    String userAgent = request.getHeader("User-Agent");
    String httpAccept = request.getHeader("Accept");
    if(userAgent!=null)userAgent=userAgent.toLowerCase();
    else userAgent="";
    if(userAgent.indexOf("iphone")>-1 || userAgent.indexOf("ipod")>-1)
    {
        out.println("        <meta name=\"viewport\" content=\"width=800px\">");
    }
    else
    {
        out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
    }
%>        
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
        <style>
            #voice_ {
                font-size: 25px;
                width: 25px;
                height: 25px;
                cursor:pointer;
                border: none;
                position: absolute;
                margin-left: 5px;
                outline: none;
                background: transparent;
            }            
        </style>    
        <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
        <script type="text/javascript">            
            
            function changeStat(suri,stat)
            {
                //if(Dom.socket==null)
                //{
                    $.post("<%=contx%>/processCommand.jsp", { suri: suri, stat: stat },
                    function(data) {
                        //alert(data);
                    });                   
                //}else
                //{
                //    Dom.sendMessage(suri+" "+stat);
                //}
            }         
        
            function processData(txt)
            {
                var voice = document.getElementById('voice');
                $.post("<%=contx%>/processCommand.jsp", { data:txt }, function(data) {
                        voice.value=data;
                });                   
            }
        </script>
        <script type="text/javascript">
            var Dom = {};

            Dom.socket = null;

            Dom.connect = (function(host) {
                if ('WebSocket' in window && <%=ws%>) {
                    Dom.socket = new WebSocket(host);
                } else if ('MozWebSocket' in window && <%=ws%>) {
                    Dom.socket = new MozWebSocket(host);
                } else 
                {
                    Console.log('Error: WebSocket is not supported by this browser.');
                    //alert('Error: WebSocket is not supported by this browser.');
                    
                    setInterval(function()
                    {
                        $.post("<%=contx%>/processCommand.jsp", { gmsg: "true" },
                        function(data) {
                            var params=data.toString().split(/\r?\n/);
                            for(x=0;x<params.length;x++)
                            {
                                if(params[x].length>0)
                                {
                                    //alert(x+""+params[x].length);
                                    Dom.receveMessage(params[x]);
                                }
                            }
                        });                         
                    },2000);
                    
                    return;
                }

                Dom.socket.onopen = function () {
                    Console.log('Info: WebSocket connection opened.');
                };

                Dom.socket.onclose = function () {
                    Console.log('Info: WebSocket closed.');
                };

                Dom.socket.onmessage = function (message) {
                    Console.log(message.data);
                    Dom.receveMessage(message.data);
                };
            });

            Dom.initialize = function() {
                if (window.location.protocol == 'http:') {
                    Dom.connect('ws://' + window.location.host + '<%=contx%>/websocket/dom');
                } else {
                    Dom.connect('wss://' + window.location.host + '<%=contx%>/websocket/dom');
                }
                
                $("input[data-type='range']").live("slidestop", function(event) {
                    //Console.log(this.name+" "+this.value);
                    changeStat(this.name,this.value);
                });
            };

            Dom.sendMessage = (function(message) {
                Dom.socket.send(message);
                //alert(message);
            });
            
            Dom.receveMessage = (function(message) {
                var params=message.toString().split(" ");
                var val=params[1];
                if(val!="0" && val!="true" && val!="false")val="16";
                $("select[name='"+params[0]+"']").val(val).slider('refresh'); 
                $("input[name='"+params[0]+"']").val(params[1]).slider('refresh'); 
            });

            var Console = {};

            Console.log = (function(message) {
                console.log(message);
            });

            Dom.initialize();
            
        </script>        
    </head> 
    <body>         
        <%
            String lang = "es";
            WebSite site = SWBContext.getWebSite("dom");
            SWB4DContext domotic = SWB4DContext.getInstance(site);
            SWB4DContext.getServer().setModel(site);
        %>

        <%          //Home
            {
        %>        
        <div data-role="page" id="home">

            <div data-role="header">
                <h1>SWB4Domotic</h1>
            </div><!-- /header -->

            <div data-role="content">	
                <ul data-role="listview" data-inset="true" data-filter="true">
                    <%
                        Iterator<DomContext> it1 = DomContext.ClassMgr.listDomContexts(site);
                        if (it1.hasNext())
                        {
                    %>                    
                    <li data-role="list-divider">Contextos</li>
                    <%
                            while (it1.hasNext())
                            {
                                DomContext cnt = it1.next();
                                printContext(cnt, out, "", lang);
                            }
                        }

                        Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(site);
                        if (it2.hasNext())
                        {
                    %>                    
                    <li data-role="list-divider">Grupos</li>
                    <%
                            while (it2.hasNext())
                            {
                                DomGroup grp = it2.next();
                                if (grp.getParentGroup() == null)
                                {
                                    printGroup(grp, out, "", lang);
                                }
                            }
                        }

                        Iterator<DomDevice> it3 = SWBUtils.Collections.filterIterator(DomDevice.ClassMgr.listDomDevices(site), new FilterRule()
                        {

                            @Override
                            public boolean filter(Object obj)
                            {
                                DomDevice dev = (DomDevice) obj;
                                //if (dev.getDomGroup() != null)
                                //{
                                //    return true;
                                //}
                                return false;
                            }
                        }).iterator();
                        if (it3.hasNext())
                        {
                    %>       
                    <li data-role="list-divider">Dispositivos</li>
                    <%
                            while (it3.hasNext())
                            {
                                DomDevice dev = it3.next();
                                printDevice(dev, out, "", lang);
                            }
                        }
                    %>   
                </ul>
                
        <input id="voice" type="text" x-webkit-speech />
        <script lang="javascript">
            var voice = document.getElementById('voice');
            voice.onwebkitspeechchange = function(e) {                
                processData(voice.value);
            };    
        </script>
                

            </div><!-- /content -->


        </div><!-- /page -->        
        <%
            }
        %>

        <%
            Iterator it = DomGroup.ClassMgr.listDomGroups(site);

            while (it.hasNext())
            {
                DomGroup grpp = (DomGroup) it.next();
                String grpTitle = grpp.getDisplayTitle(lang);

        %>

        <div data-role="page" id="p_<%=grpp.getShortURI()%>">

            <div data-role="header">
                <h1><%=grpTitle%></h1>
            </div><!-- /header -->

            <div data-role="content">	
                <a href="#" data-role="button" data-rel="back">Regresar</a>
            </div><!-- /content -->

        </div><!-- /page -->
        <%
            }
        %>         



    </body>
</html>

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
        String desc = grp.getDisplayDescription(lang);
        String name = grp.getShortURI();
        boolean val = !(grp.getStatus()==0);
        
        out.println("                    <li>");
        out.println("                        <a href=\"#p_" + name + "\">");
        //out.println("                        <h3 class=\"ui-li-heading\">");
        out.println("                        " + separator + " " + title);
        //out.println("                        </h3>");
        //out.println("                        <p class=\"ui-li-desc\">"+ separator+ separator + desc+"</p>");
        
        out.println("                        <div data-role_=\"fieldcontain\" style=\"top: 50%; margin-top: -22px; position:absolute; right:10px;\">");
        out.println("                            <select name=\"" + name + "\" data-role=\"slider\" onChange=\"changeStat(this.name,this.value)\">");
        out.println("                                <option value=\"0\" " + ((!val) ? "selected" : "") + ">OFF</option>");
        out.println("                                <option value=\"16\" " + ((val) ? "selected" : "") + ">ON</option>");
        out.println("                            </select>");
        out.println("                        </div>");
        out.println("                        </a>");
        out.println("                    </li>");


        //out.println("<a name=\"" + name + "\" href=\"#\" data-icon=\"star\" data-role=\"button\" style=\"text-align:left\" onclick=\"changeStat('"+name+"')\" " + ((val) ? "class=\"ui-btn-active\"" : "") + ">" + separator + " " + title + "</a>");
        
        Iterator it = grp.listChildGroups();
        while (it.hasNext())
        {
            DomGroup hgrp = (DomGroup) it.next();
            printGroup(hgrp, out, "&nbsp;&nbsp;" + separator, lang);
        }
    }

    public void printDevice(DomDevice dev, JspWriter out, String separator, String lang) throws IOException
    {
        String title = dev.getDisplayTitle(lang);
        String desc = dev.getDisplayDescription(lang);        
        String name = dev.getShortURI();
        int val = dev.getStatus();
        out.println("                    <li>");
        out.println("                        <a href_=\"#p_" + name + "\">");
        //out.println("                        <h3 class=\"ui-li-heading\">");
        out.println("                        " + separator + " " + title);
        //out.println("                        </h3>");
        //out.println("                        <p class=\"ui-li-desc\">"+ separator + separator + desc+"</p>");        
        if(dev.isDimerizable())
        {
            out.println("                        <div style=\"margin-top: 0px; margin-left: -10px;\" style_=\"top: 50%; margin-top: -20px; margin-right: 50px; position:absolute; right:0px; width:250px;\">");
            out.println("                           <input type=\"range\" name=\""+name+"\" value=\""+val+"\" min=\"0\" max=\"16\" data-highlight=\"true\" class=\"ui-hidden-accessible ui-input-text ui-body-c ui-corner-all ui-shadow-inset ui-slider-input\">");        
            out.println("                        </div>");
        }
        out.println("                        <div style=\"top: 50%; margin-top: -22px; position:absolute; right:10px;\">");        
        out.println("                            <select name=\"" + name + "\" data-role=\"slider\" onChange=\"changeStat(this.name,this.value)\">");
        out.println("                                <option value=\"0\" " + ((val == 0) ? "selected" : "") + ">OFF</option>");
        out.println("                                <option value=\"16\" " + ((val > 0) ? "selected" : "") + ">ON</option>");
        out.println("                            </select>");
        out.println("                        </div>");
        out.println("                        </a>");
        out.println("                    </li>");
    }

    public void printContext(DomContext cnt, JspWriter out, String separator, String lang) throws IOException
    {
        String title = cnt.getDisplayTitle(lang);
        String name = cnt.getShortURI();
        boolean val = cnt.isActive();
        out.println("<li><a name=\"" + name + "\" href=\"#\" data-icon=\"star\" onclick=\"changeStat('"+name+"','true')\" " + ((val) ? "class=\"ui-btn-active\"" : "") + ">" + title + "</a></li>");
    }
%>
<!DOCTYPE html> 
<html> 
    <head> 
        <title>SWB4Domotic</title> 
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    
    String contx=SWBPlatform.getContextPath();
    String ws=request.getParameter("ws");
    if(ws==null)ws="true";
    String wsp=request.getParameter("wsp");
    if(wsp==null)wsp="false";
    String userAgent = request.getHeader("User-Agent");
    String httpAccept = request.getHeader("Accept");
    if(userAgent!=null)userAgent=userAgent.toLowerCase();
    else userAgent="";
    
    System.out.println(userAgent);
    if(userAgent.indexOf("iphone")>-1 || userAgent.indexOf("ipod")>-1)
    {
        out.println("        <meta name=\"viewport\" content=\"width=800px\">");
    }else if(userAgent.indexOf("android")>-1 && userAgent.indexOf("1mobile")>-1)
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
                if(!stat)
                {
                    stat="16";
                    if($("a[name='"+suri+"']").hasClass("ui-btn-active"))
                    {
                        stat="0";
                    }
                }
                //alert(suri+" "+stat);
                if(Dom.socket==null || <%=wsp%>)
                {
                    $.post("<%=contx%>/processCommand.jsp", { suri: suri, stat: stat },
                    function(data) {
                        //alert(data);
                    });                   
                }else
                {
                    Dom.sendMessage(suri+" "+stat);
                }
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
            
            function desc(obj, content)
            {
                var ret="";
                for (property in obj) {
                    if(content && content==true)
                    {
                        ret+="-->"+property+"="+obj[property]+"\n";
                    }else
                    {
                        ret+="-->"+property+"\n";
                    }
                }
                console.log(ret);
                //alert(ret);
            }              
            
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
                //alert(Number(val)+" "+val);
                if(val!="0" && val!="true" && val!="false")val="16";
                $("select[name='"+params[0]+"']").val(val).slider('refresh'); 
                $("input[name='"+params[0]+"']").val(params[1]).slider('refresh'); 
                
                if(val=="true" || Number(val)>0)$("a[name='"+params[0]+"']").addClass("ui-btn-active"); 
                else $("a[name='"+params[0]+"']").removeClass("ui-btn-active"); 
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

        <div data-role="header">
            <h1>SWB4Domotic</h1>
        </div><!-- /header -->        

        <div data-role="content">	
                
                <div data-role="collapsible" data-collapsed="false" data-inset="false" data-theme="b" data-content-theme="d">
                   <h3>Contextos</h3>
<%                
                        Iterator<DomContext> it1 = DomContext.ClassMgr.listDomContexts(site);
                        if (it1.hasNext())
                        {
%>                                       
                    <div data-role="navbar">
                        <ul>
<%
                            while (it1.hasNext())
                            {
                                DomContext cnt = it1.next();
                                printContext(cnt, out, "", lang);
                            }
%>                            
                        </ul>
                    </div><!-- /navbar -->
                </div>          
<%
                        }
%>

                <table bgcolor="#ffffff" border="0" width="100%" cellspacing="0px" cellpadding="16px">
                    <tr>
                        <td width="40%" valign="top">
                            
                    <%
                        Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(site);
                        if (it2.hasNext())
                        {
                    %>                    
                    
                <ul data-role="listview" data-inset="false" data-filter="false">                    
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
                </ul>
                           
                            
                        </td><td width="60%" valign="top">
                            
                <ul data-role="listview" data-inset="false" data-filter="false">                    
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
                            
                        </td>
                    </tr>
                </table>


                

            </div>
                                
        <input id="voice" type="text" x-webkit-speech />
        <script lang="javascript">
            var voice = document.getElementById('voice');
            voice.onwebkitspeechchange = function(e) {                
                processData(voice.value);
            };    
        </script>                
 

    </body>
</html>

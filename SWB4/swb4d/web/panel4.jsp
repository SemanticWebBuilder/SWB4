<%-- 
    Document   : panel4
    Created on : 21-oct-2012, 20:10:15
    Author     : javier.solis.g
--%>

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
%>
<!DOCTYPE html> 
<html> 
    <head> 
        <title>My Page</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css" />
        <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
        <script type="text/javascript">
            
            function changeStat(suri,stat)
            {
                //alert("suri:"+suri+" stat:"+stat);
                $.post("/processCommand.jsp", { suri: suri, stat: stat },
                function(data) {
                    //alert(data);
                });                   
            }                        
        </script>
    </head> 
    <body> 
        <%
            int c=0;
            WebSite site = SWBContext.getWebSite("dom");
            SWB4DContext domotic = SWB4DContext.getInstance(site);
            SWB4DContext.getServer().setModel(site);

            String sdev = request.getParameter("dev");
            String sgrp = request.getParameter("grp");

            if (sgrp != null)
            {
                DomGroup grpp = DomGroup.ClassMgr.getDomGroup(sgrp, site);
                String grpTitle = grpp.getDisplayTitle("es");

        %>

        <div data-role="page" defaultPageTransition="slide">

            <div data-role="header">
                <h1><%=grpTitle%></h1>
            </div><!-- /header -->

            <div data-role="content">	

                <ul data-role="listview" data-inset="true" data-filter="true">
                    <%
                        Iterator<DomGroup> it2 = grpp.listChildGroups();
                        if (it2.hasNext())
                        {
                    %>
                    <li data-role="list-divider">Grupos</li>
                    <%
                        while (it2.hasNext())
                        {
                            DomGroup grp = it2.next();
                            String title = grp.getDisplayTitle("es");
                            String id = grp.getShortURI();
                    %>
                    <li>
                        <a href="?grp=<%=grp.getId()%>"><%=title%></a>
                        <div data-role="fieldcontain" style="margin-top: -41px; float: right; margin-right: -20px; width: 250px">
                            <fieldset data-role="controlgroup" data-type="horizontal">
                                <input type="radio" name="<%=id%>" id="<%=id%>_<%=++c%>" value="16" onChange="changeStat(this.name,this.value)"/>
                                <label for="<%=id%>_<%=c%>">ON</label>
                                <input type="radio" name="<%=id%>" id="<%=id%>_<%=++c%>" value="0" onChange="changeStat(this.name,this.value)"/>
                                <label for="<%=id%>_<%=c%>">OFF</label>
                            </fieldset>
                        </div>
                    </li>
                    <%
                            }
                        }
                        Iterator<DomDevice> it3 = grpp.listDomDevices();
                        if (it3.hasNext())
                        {

                    %>                
                    <li data-role="list-divider">Dispositivos</li>
                    <%
                        while (it3.hasNext())
                        {
                            DomDevice dev = it3.next();
                            String title = dev.getDisplayTitle("es");
                            String id = dev.getShortURI();
                            int val=dev.getStatus();
                    %>
                    <li>
                        <a href="?dev=<%=dev.getId()%>"><%=title%></a>
                        <div data-role="fieldcontain" style="margin-top: -43px; float: right; margin-right: 50px;">
                            <select name="<%=id%>" id="<%=id%>" data-role="slider" onChange="changeStat(this.name,this.value)">
                                <option value="0" <%=(val==0)?"selected":""%>>OFF</option>
                                <option value="16" <%=(val>0)?"selected":""%>>ON</option>
                            </select> 
                        </div>
                    </li>
                    <%
                            }
                        }
                    %>                       
                </ul>

            </div><!-- /content -->


        </div><!-- /page -->
        <%
        } else
        {
        %>
        <div data-role="page">

            <div data-role="header">
                <h1>SWB4Domotic</h1>
            </div><!-- /header -->

            <div data-role="content">	
                <ul data-role="listview" data-inset="true" data-filter="true">
                    <%
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
                                String title = grp.getDisplayTitle("es");
                                String id = grp.getShortURI();
                    %>
                    <li>
                        <a href="?grp=<%=grp.getId()%>"><%=title%></a>
                        <div data-role="fieldcontain" style="margin-top: -41px; float: right; margin-right: -20px; width: 250px">
                            <fieldset data-role="controlgroup" data-type="horizontal">
                                <input type="radio" name="<%=id%>" id="<%=id%>_<%=++c%>" value="16" onChange="changeStat(this.name,this.value)"/>
                                <label for="<%=id%>_<%=c%>">ON</label>
                                <input type="radio" name="<%=id%>" id="<%=id%>_<%=++c%>" value="0" onChange="changeStat(this.name,this.value)"/>
                                <label for="<%=id%>_<%=c%>">OFF</label>
                            </fieldset>
                        </div>
                    </li>
                    <%
                                }
                            }
                        }
                        Iterator<DomDevice> it3 = SWBUtils.Collections.filterIterator(DomDevice.ClassMgr.listDomDevices(site), new FilterRule()
                        {
                            @Override
                            public boolean filter(Object obj)
                            {
                                DomDevice dev=(DomDevice)obj;
                                if(dev.getDomGroup()!=null)return true;
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
                            String title = dev.getDisplayTitle("es");
                            String id = dev.getShortURI();
                            int val=dev.getStatus();
                    %>
                    <li>
                        <a href="?dev=<%=dev.getId()%>"><%=title%></a>
                        <div data-role="fieldcontain" style="margin-top: -43px; float: right; margin-right: 50px;">
                            <select name="<%=id%>" data-role="slider" onChange="changeStat(this.name,this.value)">
                                <option value="0" <%=(val==0)?"selected":""%>>OFF</option>
                                <option value="16" <%=(val>0)?"selected":""%>>ON</option>
                            </select> 
                        </div>
                    </li>
                    <%
                            }
                        }
                    %>   
                </ul>
                <form>
                    <label for="slider-0">Input slider:</label>
                    <input type="range" name="slider" id="slider-0" value="25" min="0" max="16"  data-mini="false" data-highlight="true"/>

                    <a href="#" data-role="button" data-icon="star">Star button</a>

                </form>        

                <input type="range" name="slider-1" id="slider-full" value="25" min="0" max="100" data-highlight="true" class="ui-hidden-accessible ui-input-text ui-body-c ui-corner-all ui-shadow-inset ui-slider-input">

            </div><!-- /content -->


        </div><!-- /page -->
        <%
            }
        %>        

    </body>
</html>

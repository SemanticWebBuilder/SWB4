<%@page import="java.io.IOException"%>
<%@page import="org.semanticwb.domotic.model.DomGroup"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.domotic.model.SWB4DContext"%>
<%@page import="org.semanticwb.base.util.SFBase64"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.domotic.model.DomDeviceStat"%>
<%@page import="org.semanticwb.domotic.model.DomDevice"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.domotic.model.DomGateway"%>
<%@page import="org.semanticwb.domotic.model.DomContext"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.base.SWBContextBase"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    String processComands(String commands, SWBModel model)
    {
        String ret = commands;
        if (ret != null)
        {
            try{ret=SWBUtils.TEXT.decode(ret, "utf8");}catch(Exception e){e.printStackTrace();}
            if (ret.startsWith("["))
            {
                ret = ret.substring(1, ret.length() - 1);
            }

            boolean found=false;
            
            Iterator<DomDevice> it = DomDevice.ClassMgr.listDomDevices(model);
            while (it.hasNext())
            {
                DomDevice dev = it.next();
                String title=dev.getDisplayTitle("es");
                
                if (title!=null && ret.indexOf(title.toLowerCase()) > -1)
                {
                    if(ret.indexOf("desactiva")>-1 || ret.indexOf("apaga")>-1 || ret.indexOf("desconecta")>-1)
                    {
                        dev.setStatus(0);
                        ret="Dispositivo "+dev.getDisplayTitle("es")+" desactivado";
                    }else if(ret.indexOf("activa")>-1 || ret.indexOf("enciende")>-1 || ret.indexOf("encender")>-1 || ret.indexOf("conecta")>-1)
                    {
                        dev.setStatus(16);
                        ret="Dispositivo \""+dev.getDisplayTitle("es")+"\" activado";
                    }else
                    {
                        if (dev.getStatus() != 0)
                        {
                            dev.setStatus(0);
                            ret="Dispositivo "+dev.getDisplayTitle("es")+" desactivado";
                        } else
                        {                        
                            dev.setStatus(16);
                            ret="Dispositivo \""+dev.getDisplayTitle("es")+"\" activado";
                        }                        
                    }         
                    found=true;                                                  
                    break;
                }
            }
            
            if(!found)
            {
                Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(model);
                while (it.hasNext())
                {
                    DomGroup grp = it2.next();
                    String title=grp.getDisplayTitle("es");

                    if (title!=null && ret.indexOf(title.toLowerCase()) > -1)
                    {
                        if(ret.indexOf("desactiva")>-1 || ret.indexOf("apaga")>-1 || ret.indexOf("desconecta")>-1)
                        {
                            grp.setStatus(0);
                            ret="Dispositivo "+grp.getDisplayTitle("es")+" desactivado";
                        }else if(ret.indexOf("activa")>-1 || ret.indexOf("enciende")>-1 || ret.indexOf("encender")>-1 || ret.indexOf("conecta")>-1)
                        {
                            grp.setStatus(16);
                            ret="Dispositivo \""+grp.getDisplayTitle("es")+"\" activado";
                        }                                        
                        break;
                    }
                }            
            }

        }else ret="";
        
        return ret;
    }
    
    public void listGroups(DomGroup base, JspWriter out, String separator) throws IOException
    {
            Iterator<DomGroup> it = base.listChildGroups();
            while (it.hasNext())
            {
                DomGroup grp = it.next();
                String a1 = "<a href=\"?grp=" + grp.getId() + "&stat=16\">ON</a>";
                String a2 = "<a href=\"?grp=" + grp.getId() + "&stat=0\">OFF</a>";
                out.println(separator+"Grupo: "+grp.getDisplayTitle("es")+" "+a1+" "+a2);
                out.println("<br>");
                Iterator<DomDevice> it2 = grp.listDomDevices();
                while (it2.hasNext())
                {
                    DomDevice dev = it2.next();
                    displyDevice(dev, out, separator);
                }                
                listGroups(grp, out, separator+"--");
            }        
    }
    
    public void displyDevice(DomDevice dev, JspWriter out, String separator) throws IOException
    {
        DomDeviceStat stat = dev.getDomDeviceStat();
        String a1 = "<a href=\"?dev=" + dev.getId() + "\">";
        String a2 = "</a>";
        out.println(separator+"-->Device:" +a1+dev.getDisplayTitle("es")+a2 + " "+stat.getStatus() + " (" + dev.getSerial() + ")" + " (" + dev.getDevId() + "-" + dev.getDevZone() + ")" + " " + stat.getLastUpdate());
        out.println("<br>");        
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SWB4Domotic</title>
        <style>
            #voice {
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
    </head>
    <body>
        <h1>SWB4Domotic</h1>
        <%
            WebSite site = SWBContext.getWebSite("dom");
            SWB4DContext domotic = SWB4DContext.getInstance(site);
            SWB4DContext.getServer().setModel(site);

            String sdev = request.getParameter("dev");
            String sgrp = request.getParameter("grp");
            String sstat = request.getParameter("stat");
            if (sdev != null)
            {
                DomDevice dev = DomDevice.ClassMgr.getDomDevice(sdev, site);
                if (dev.getDomDeviceStat().getStatus() != 0)
                {
                    dev.setStatus(0);
                } else
                {
                    dev.setStatus(16);
                }
            }
            if (sgrp != null)
            {
                DomGroup grp = DomGroup.ClassMgr.getDomGroup(sgrp, site);
                grp.setStatus(Integer.parseInt(sstat));
            }            

            String data = processComands(request.getParameter("data"),site);

            Iterator<DomGateway> it = DomGateway.ClassMgr.listDomGateways(site);
            while (it.hasNext())
            {
                DomGateway gtw = it.next();
                out.println("Gateway:" + gtw.getDisplayTitle("es") + "(" + gtw.getSerial() + ")" + " online:" + (gtw.getConnection() != null));
                out.println("<br>");
                Iterator<DomDevice> it2 = gtw.listDomDevices();
                while (it2.hasNext())
                {
                    DomDevice dev = it2.next();
                    displyDevice(dev, out, "");
                }
            }
            
            out.println("<br>");
                                
            Iterator<DomGroup> it2 = DomGroup.ClassMgr.listDomGroups(site);
            while (it2.hasNext())
            {
                DomGroup grp = it2.next();
                if(grp.getParentGroup()==null)
                {
                    String a1 = "<a href=\"?grp=" + grp.getId() + "&stat=16\">ON</a>";
                    String a2 = "<a href=\"?grp=" + grp.getId() + "&stat=0\">OFF</a>";
                    out.println("Grupo: "+grp.getDisplayTitle("es")+" "+a1+" "+a2);
                    out.println("<br>");
                    listGroups(grp, out, "-->");                          
                }
            }

        %>
        

        <input id="voice" type="text" x-webkit-speech />

        <script lang="javascript">
            var voice = document.getElementById('voice');
            //mike.onfocus = voice.blur;
            voice.onwebkitspeechchange = function(e) {
                //console.log(e); // SpeechInputEvent
                documento.location.href="?data="+encodeURL(voice.value);
                //alert(voice.value);
            };    
        </script>

<%
    out.println("<BR>"+data+"<BR>");
%>        
<a href="?">Recargar</a>
        
    </body>
</html>

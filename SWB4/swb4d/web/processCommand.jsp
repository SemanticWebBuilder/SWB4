<%-- 
    Document   : processCommand
    Created on : 22-oct-2012, 13:40:01
    Author     : javier.solis.g
--%>
<%@page import="org.semanticwb.domotic.model.DomContext"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.domotic.model.SWB4DContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.domotic.model.DomGroup"%>
<%@page import="org.semanticwb.domotic.model.DomDevice"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.SWBModel"%>
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
                while (it2.hasNext())
                {
                    DomGroup grp = it2.next();
                    String title=grp.getDisplayTitle("es");

                    if (title!=null && ret.indexOf(title.toLowerCase()) > -1)
                    {
                        if(ret.indexOf("desactiva")>-1 || ret.indexOf("apaga")>-1 || ret.indexOf("desconecta")>-1)
                        {
                            grp.setStatus(0);
                            ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                        }else if(ret.indexOf("activa")>-1 || ret.indexOf("enciende")>-1 || ret.indexOf("encender")>-1 || ret.indexOf("conecta")>-1)
                        {
                            grp.setStatus(16);
                            ret="Grupo \""+grp.getDisplayTitle("es")+"\" activado";
                        }else
                        {
                            if (grp.getStatus() != 0)
                            {
                                grp.setStatus(0);
                                ret="Grupo "+grp.getDisplayTitle("es")+" desactivado";
                            } else
                            {                        
                                grp.setStatus(16);
                                ret="Grupo \""+grp.getDisplayTitle("es")+"\" activado";
                            }                        
                        }                                          
                        break;
                    }
                }            
            }

        }else ret="";
        
        return ret;
    }
%>
<%
    WebSite site = SWBContext.getWebSite("dom");
    SWB4DContext domotic = SWB4DContext.getInstance(site);
    SWB4DContext.getServer().setModel(site);
    
    String suri = request.getParameter("suri");
    String sstat = request.getParameter("stat");
    if (suri != null)
    {
        String uri=SemanticObject.shortToFullURI(suri);
        GenericObject obj=SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(obj instanceof DomDevice)
        {        
            DomDevice dev = (DomDevice)obj;
            dev.setStatus(Integer.parseInt(sstat));
        }else if(obj instanceof DomGroup)
        {
            DomGroup grp=(DomGroup)obj;
            grp.setStatus(Integer.parseInt(sstat));
        }else if(obj instanceof DomContext)
        {
            DomContext cnt=(DomContext)obj;
            cnt.setActive(Boolean.parseBoolean(sstat));
        }
    }    

    String data = processComands(request.getParameter("data"), site);
    if(data!=null)
    {
        out.println(data);
    }
%>

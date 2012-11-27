/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.portal.SWBSessionObject;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAListActiveUsers.
 * 
 * @author juan.fernandez
 */
public class SWBAListActiveUsers extends GenericResource {
    
    /** The log. */
    private Logger log=SWBUtils.getLogger(SWBAListActiveUsers.class);
    
/**
 * Creates a new instance of SWBAListActiveUsers.
 */
    public SWBAListActiveUsers() {
    }

    /**
     * User view of the WBAListActiveUsers resource.
     * 
     * @param request the input parameters
     * @param response the answer to the user request
     * @param paramRequest a list of objects (topic, user, action, ...)
     * @throws AFException an exception of type AFException
     * @throws IOException an exception of type IOException
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String act=request.getParameter("act");

        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        //if(AFUtils.getEnv("wb/systemMonitor","false").equals("true"))
        if(SWBPlatform.getEnv("swb/usersTrace","false").equals("true"))
        {
            out.println("<table width=100% cellpadding=10 cellspacing=0 border=0>");
            if(act==null)
            {
                out.println("<tr><td colspan='2'>");
                out.println("<div class=\"applet\">");
                out.println("<applet code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                url.setMode("getData");
                out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
                out.println("<param name=\"cgi\" value=\""+url+"\">");
                out.println("<param name=\"reload\" value=\"5\">");
                out.println("</applet>");
                out.println("</div>");
                out.println("</td></tr>");
                out.println("<tr><td colspan='2'><HR size=\"1\" noshade></td></tr>");
                out.println("<tr><td colspan='2' align=right>");
//                out.println("<form action=\""+paramRequest.getRenderUrl()+"\" method=\"post\">");
//                out.println("<input class=\"boton\" type=\"submit\" name=\"list\" value=\""+paramRequest.getLocaleString("listUsers")+"\">");
//                out.println("<input type=hidden name=\"act\" value=\"list\">");
//                out.println("</form>");
                out.println("</td></tr>");


            }
//            else if(act.equals("list"))
//            {
//                //SWBPortal.getUserMgr()
//                Iterator<SWBSessionObject> iteUsers = SWBPortal.getUserMgr().listSessionObjects();
//                out.println("<tr>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHIdentifier")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHUser")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHName")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHEmail")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHLanguage")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHActive")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHCreated")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHLastUpdate")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHRepository")+"</th>");
//                out.println("<th>"+paramRequest.getLocaleString("msgTHSession")+"</th></tr>");
//                String rowColor="";
//                boolean cambiaColor = true;
//                while(iteUsers.hasNext()) {
//                    SWBSessionObject wUser = iteUsers.next();
//                    User rUser = null;
//                    
//                    if(wUser instanceof User)
//                     rUser = (User)wUser;
//                    rowColor="#EFEDEC";
//                    if(!cambiaColor) rowColor="#FFFFFF";
//                    cambiaColor = !(cambiaColor);
//                    out.println("<tr bgcolor=\""+rowColor+"\"  >");
//                    out.println("<td>"+rUser.getId()+"</td>");
//                    out.println("<td>"+stringNullValidate(rUser.getUsrLogin())+"</td>");
//                    out.println("<td>"+stringNullValidate(rUser.getUsrFirstName())+" "+stringNullValidate(rUser.getUsrLastName())+"</td>");
//                    out.println("<td>"+stringNullValidate(rUser.getUsrEmail())+"</td>");
//                    out.println("<td>"+stringNullValidate(rUser.getLanguage())+"</td>");
//                    out.println("<td>"+rUser.isActive()+"</td>");
//                    //TODO: dateFormat()
//                    out.println("<td>"+rUser.getCreated()+"</td>");
//                    out.println("<td>"+rUser.getUpdated()+"</td>");
//                    out.println("<td>"+rUser.getUserRepository().getId()+"</td>");
//                    String strSesion = wUser.getSesid();
//                    if(strSesion.length()>=10) strSesion = strSesion.substring(strSesion.length()-10);
//                    SWBResourceURL urlDetail = paramRequest.getRenderUrl();
//                    urlDetail.setParameter("act","detail");
//                    urlDetail.setParameter("idusr",rUser.getId());
//                    urlDetail.setParameter("sessionid",wUser.getSesid());
//                    urlDetail.setParameter("rep",rUser.getUserRepository().getId());
//
//                    out.println("<td><a href=\""+urlDetail.toString()+"\" title=\""+paramRequest.getLocaleString("msgTitleViewDetail")+"\">"+strSesion+"</a></td>");
//                    out.println("</tr>");
//
//                    rUser=null;
//                    wUser=null;
//                }
//                SWBResourceURL urlendsess = paramRequest.getActionUrl();
//                urlendsess.setParameter("act","closeall");
//                out.println("<tr><td colspan='10' align=right><HR size=\"1\" noshade><input type=button name=\"btn_enddall\" value=\"Cerrar todas las sesiones\" onclick=\"if(confirm('Estás seguro de cerrar todas las sesiones de usuario?')){window.location='"+urlendsess+"';} else {return false;}\"></td></tr>");
//
//            }
//            else
//            {
//                if(act.equals("detail")) {
//                    String idusr = request.getParameter("idusr");
//                    String sessionid = request.getParameter("sessionid");
//                    String rep = request.getParameter("rep");
//                    //System.out.println(idusr+" - "+sessionid+" - "+act);
//                    if(idusr!=null){
//
//                        try{
//                            User wuser = SWBContext.getUserRepository(rep).getUser(sessionid);
//                            HttpSession wusession = SWBPortal.getUserMgr().getUserSession(sessionid, rep);
//
//                            // detalle usuario
//                            if(wusession!=null){
//                                out.println("<tr>");
//                                out.println("<td colspan=2>");
//                                out.println(paramRequest.getLocaleString("msgUserDetailSession"));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgIdentifier"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(wuser.getId());
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgUser"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getUsrLogin()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgName"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getName()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgFirstName"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getUsrFirstName()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgSecondName"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getUsrLastName()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgLastName"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getUsrSecondLastName()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgLanguage"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getLanguage()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgActive"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(wuser.isActive());
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgEmail"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getUsrEmail()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgIP"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(stringNullValidate(wuser.getIp()));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgDevice"));
//                                out.println(":</td>");
//                                out.println("<td >");
//
//                                try
//                                {
//                                    out.println(SWBContext.getGlobalWebSite().getDevice(wuser.getDevice()).getTitle());
//                                }catch(NumberFormatException e)
//                                {
//                                    out.println(wuser.getDevice());
//                                }
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgXML"));
//                                out.println(":</td>");
//                                out.println("<td ><textarea rows=10 cols=80 readonly wrap=hard >");
//                                out.println(wuser.getXML());
//                                out.println("</textarea></td>");
//                                out.println("</tr>");
//
//                                // detalle sesión
//
//                                out.println("<tr>");
//                                out.println("<td colspan=2><HR size=\"1\" noshade>");
//                                out.println(paramRequest.getLocaleString("msgSessionDetail"));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgIdentifier"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(sessionid);
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgInit"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                //TODO: dateFormat()
//                                out.println((new Timestamp(wusession.getCreationTime())));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgLastAccess"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                //TODO: dateFormat()
//                                out.println((new Timestamp(wusession.getLastAccessedTime())));
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgMaxInterval"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                out.println(wusession.getMaxInactiveInterval());
//                                out.println("</td>");
//                                out.println("</tr>");
//                                out.println("<tr>");
//                                out.println("<td  width=150 align=right>");
//                                out.println(paramRequest.getLocaleString("msgCloseUserSession"));
//                                out.println(":</td>");
//                                out.println("<td >");
//                                SWBResourceURL urlClose = paramRequest.getActionUrl();
//                                urlClose.setParameter("sessionid",sessionid);
//                                urlClose.setParameter("rep",rep);
//                                urlClose.setParameter("act","close");
//                                out.println("<a href=\""+urlClose.toString()+"\"  onclick=\"return ( confirm('"+paramRequest.getLocaleString("msgConfirmCloseSessionUser")+"?'));\"><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/eliminar.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgLinkFinish")+"\"></a>");
//                                out.println("</td>");
//                                out.println("</tr>");
//                            }
//                            else{
//
//                                out.println("<tr><td colspan=2 align=left>"+paramRequest.getLocaleString("msgSessionEnd")+"</td></tr>");
//                            }
//                        }
//                        catch(Exception e){
//                            log.error("Error in user and session monitoring. Session are finished.",e);
//
//                        }
//                    }
//                }
//
//                else{
//                    out.println("<tr>");
//                    out.println("<td>");
//                    out.println("</td>");
//                    out.println(paramRequest.getLocaleString("msgAlertMissingData"));
//                    out.println("</tr>");
//                }
//
//            }
            out.println("</table>");
        }else
        {
            out.println(paramRequest.getLocaleString("msgIsNotActive"));
        }
        out.println("</fieldset>");
        out.println("</div>");
    }

    /**
     * Process the requested action of the WBAListActiveUsers resource.
     * 
     * @param request the input parameters
     * @param response the answer to the user request and a list of objects (topic, user, action, ...)
     * @param paramRequest the param request
     * @return the data
     * @throws AFException an exception of type AFException
     * @throws IOException an exception of type IOException
     * @throws SWBResourceException the sWB resource exception
     */
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        String act="";
//        if(request.getParameter("act")!=null) act = request.getParameter("act");
//        if(act.equals("close")){
//            String rep = request.getParameter("rep");
//            String sessionid = request.getParameter("sessionid");
//            SWBPortal.getUserMgr().removeUser(sessionid,rep);
//            response.setRenderParameter("act","");
//        } else if(act.equals("closeall")){
//            Iterator<User> iteUsers = SWBPortal.getUserMgr().getEnumUsers();
//            while(iteUsers.hasNext()) {
//                    User wUser = (User) iteUsers.next();
//                    User rUser = wUser;
//                    String strSesion = wUser.getSesid();
//                    if(!strSesion.equals(request.getSession().getId()))
//                    {
//                        SWBPortal.getUserMgr().removeUser(strSesion,rUser.getRepository());
//                    }
//            }
//            response.setRenderParameter("act","list");
//        }
//    }



    public void getData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();

        SWBMonitor monitor = SWBPortal.getMonitor();

        int max=100;
        int ratio=((monitor.getMonitorRecords().size()-1)/max)+1;
        Vector data=monitor.getAverageMonitorRecords(ratio);

        int labc=data.size()/10;
        if(labc==0)labc=1;

        out.println("GraphType=Lines");
        out.println("ncdata=1");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        out.println("Title="+paramRequest.getLocaleString("msgTotalUsers")+"");
        out.println("SubTitle="+SWBPortal.getUserMgr().getNumberOfSessionObjects()+"");
        Enumeration en=data.elements();
        int x=-1;

        while(en.hasMoreElements())
        {
            SWBMonitor.MonitorRecord mr=(SWBMonitor.MonitorRecord)en.nextElement();
            long musers=mr.getMaxUsers();
            if(x>=0)
            {
                java.util.Date dt=mr.getDate();
                String date=""+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
                out.println("label"+x+"=_"+date+"");
                out.println("data"+x+"="+musers+"");
            }
            x++;
        }
        out.println("ndata="+x+"");
        out.println("color0=237,100,100");
        out.println("color1=229,243,50");
        out.println("color2=150,150,150");
        out.println("barname0="+paramRequest.getLocaleString("msgUsers")+"");
        out.println("zoom=true");
    }

    /**
     * Process the user request.
     * 
     * @param request the input parameters
     * @param response the answer to the user request
     * @param paramRequest a list of objects (topic, user, action, ...)
     * @throws AFException an exception of type AFException
     * @throws IOException an exception of type IOException
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("getData"))
        {
            getData(request,response,paramRequest);
        }else super.processRequest(request,response,paramRequest);
    }

    /**
     * String null validate.
     * 
     * @param obj the obj
     * @return the string
     */
    public String stringNullValidate(Object obj)
    {
        if(obj==null)return "";
        else return obj.toString();
    }
}

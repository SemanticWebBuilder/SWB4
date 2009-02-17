/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;

/** Esta clase despliega un �rbol con su topicmap y sus t�picos, permite ir adentro
 * del �rbola y navegar entre los t�picos. el usuario puede seleccionar el t�pico
 * deseado y regresar� el id del t�pico al programa que lo ejecut�. Es usado por
 * algunos archivos que muestran los reportes y que administran los DNS
 *
 * This class displays a tree with his topicmap and topics, allow go inside tree
 * and navigate between topics. User can select a topic and returns the id topic to
 * the program that called this class. Is used by files that shows reports and file
 * that administrate DNS
 * @author Jorge Alberto Jim�nez
 * @author Carlos Ramos
 * @version 2.0
 */
public class WebSiteSectionTree {
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    
    protected static final String webpath = SWBPlatform.getContextPath();

    /*protected Templates plt;
    protected Transformer trans;
    protected DocumentBuilderFactory dbf=null;
    protected DocumentBuilder db=null;*/
    protected String strRes="";
    protected String strWorkPath="";
    protected Vector vTopic= new Vector();
    protected int intMaxLevel=1;

    /**
     * @param p_selectedsite
     * @param request
     * @param response
     * @param user
     * @param topicrec
     * @param arguments
     * @param topic
     * @throws SWBResourceException
     * @throws IOException
     * @return
     */    
    public String render(String p_selectedsite, HttpServletRequest request, HttpServletResponse response, User user, WebPage topicrec, Map arguments,WebPage topic) throws SWBResourceException, IOException {
        StringBuffer sb_ret = new StringBuffer();
        String strResmaptopic=null;
        String params="";
        String strUrl="";
        String param=null;
        WebSite tm=null;
        WebSite tmredirec=topicrec.getWebSite();

        try {
            if(arguments.get("params")!=null) params="&"+(String)arguments.get("params");

            Enumeration en=request.getParameterNames();
            while (en.hasMoreElements()) {
                param=en.nextElement().toString();
                if (request.getParameter(param)!=null)
                    params+="&"+param+"="+request.getParameter(param);
            }
            strResmaptopic=topicrec.getId();
            WebPage tpid=null;
            Vector vctPath=new Vector();
            int intLevel=4, intWidth=10;
            String idhome=null;

            int widthsize=25;
            String backg="";

            String path1 = webpath + "/swbadmin/icons/";
            System.out.println("path1="+path1);

            if(topic!=null){
                strUrl =(String)SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + topic.getWebSiteId() + "/";
            }

            WebPage tpsite=null;
            if (request.getParameter("reptm")!=null) {
                tm=SWBContext.getWebSite(request.getParameter("reptm"));
                tpsite=tm.getHomePage();
                    
                if(tm!=null && request.getParameter("reptp")!=null && !request.getParameter("reptp").trim().equals("")) {
                    if(tm.getWebPage(request.getParameter("reptp"))!=null) {
                        tpid=tm.getWebPage(request.getParameter("reptp"));
                        vctPath=getMapPath(tpid);
                    }
                }
            }
            
            sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" width=\"100%\">");

            if((tpid!=null && tpid.getId().equals(topic.getId())) || (tpid==null)) {
                backg=" bgcolor=\"#2A5216\"";
                //color="#FFFFFF";
            }

            WebSite tmit = SWBContext.getWebSite(p_selectedsite);

            WebPage tmhome=tmit.getHomePage();
            backg="";
            //color="#666666";
            if(tpid!=null && tpid.getId().equals(tmhome.getId())){
                backg=" bgcolor=\"#B8CFE5\"";
                //color="#FFFFFF";
            }

            sb_ret.append("<tr>");
            sb_ret.append("<td>");
            sb_ret.append("<img border=\"0\" src=\""+path1+"f_site_verde.gif\" />");
            sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" style=\"text-decoration: none\">&nbsp;&nbsp;"+tmit.getId());
            sb_ret.append("</td>");
            sb_ret.append("</tr>");

            if((tm!=null && tpid!=null && !tm.getId().equalsIgnoreCase(tmit.getId())) || (tm==null && tpsite==null) || (tm!=null && tpsite!=null)){
                sb_ret.append("<tr>");
                sb_ret.append("<td>");
                sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">");
                sb_ret.append("<tr>");
                sb_ret.append("<td>");
                sb_ret.append("	<img height=\"5\" width=\"5\" border=\"0\" src=\""+path1+"trans.gif\" />");

                sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"\">");
                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+path1+"trans.gif\" />");
                sb_ret.append("<img border=\"0\" src=\""+path1+"plus.gif\" />");
                sb_ret.append("<img border=\"0\" src=\""+path1+"i_home_verde.gif\" />");
                sb_ret.append("</a>");
                sb_ret.append("</td>");
                sb_ret.append("<td"+backg+">");
                sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;&nbsp;"+tmhome.getDisplayName());
                sb_ret.append("</font>");
                sb_ret.append("</a>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
                sb_ret.append("</table>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
            }

            if( tpsite!=null && tpid!=null && tpid.getWebSiteId().equalsIgnoreCase(tmit.getId()) ) {
                Iterator<WebPage> it=tpsite.listChilds();
                while(it.hasNext()) {
                    intLevel=1;
                    WebPage tp = it.next();
                    if(tp.getId()!=null) {
                        backg="";
                        //color="#666666";
                        if(tpid!=null && tpid.getId().equals(tp.getId())){
                            backg=" bgcolor=\"#B8CFE5\"";
                            //color="#FFFFFF";
                        }
                        sb_ret.append("<tr>");
                        sb_ret.append("<td>");
                        sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">");
                        sb_ret.append("<tr>");
                        sb_ret.append("<td>");
                        sb_ret.append("	<img height=\"20\" width=\"20\" border=\"0\" src=\""+path1+"trans.gif\" />");
                        sb_ret.append("</td>");
                        sb_ret.append("<td width=\"50\">");
                        if(tp.listChilds().hasNext() || vctPath.contains(tp.getId())){
                            if(tpid!=null && (tpid.getId().equals(tp.getId()) || vctPath.contains(tp.getId()))){
                                sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getWebSiteId()+params+"\">");
                                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+path1+"trans.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+path1+"minus.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                                sb_ret.append("</a>");
                            }else{
                                sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"\">");
                                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+path1+"trans.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+path1+"plus.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                                sb_ret.append("</a>");
                            }

                        }
                        else{
                            sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"\">");
                            sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+path1+"trans.gif\" />");
                            sb_ret.append("<img height=\"10\" width=\"15\" border=\"0\" src=\""+path1+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                        }
                        sb_ret.append("</td>");

                        sb_ret.append("<td"+backg+">");
                        sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                        sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;"+tp.getDisplayName()+"</font></a>");
                        sb_ret.append("</td>");
                        sb_ret.append("</tr>");
                        sb_ret.append("</table>");
                        sb_ret.append("</td>");
                        sb_ret.append("</tr>");
                        if((intLevel < intMaxLevel || (tpid!=null && tp.getId().equals(tpid.getId())) ||
                                vctPath.contains(tp.getId())) && tp.listChilds().hasNext()){
                            sb_ret.append(getChilds(response,tmredirec,tmit,tpid, tp, user, vctPath, intLevel+1, intWidth,strResmaptopic,widthsize,params));
                        }
                    }
                }
            }
            sb_ret.append("</table>");
        }
        catch(Exception e) {
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return sb_ret.toString();
    }

    /**
     * @param response
     * @param tmredirec
     * @param tmit
     * @param tpid
     * @param tpc
     * @param user
     * @param vctPath
     * @param intLevel
     * @param intWidth
     * @param topicrec
     * @param widthsize
     * @param params
     * @throws IOException
     * @return
     */    
    protected String getChilds(HttpServletResponse response, WebSite tmredirec, WebSite tmit, WebPage tpid, WebPage tpc, User user, Vector vctPath, int intLevel, int intWidth,String topicrec,int widthsize,String params) throws IOException{
        //PrintWriter out=response.getWriter();
        StringBuffer sb_ret = new StringBuffer();
        String strResmaptopic=null;

        strResmaptopic=topicrec;
        //StringBuffer sbfRet=new StringBuffer();
        try{
            String path1=""+webpath+"wbadmin/icons/";
            String strUrl = SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + tmredirec.getId() + "/";
            Iterator<WebPage> it = tpc.listChilds();

            while(it.hasNext()){
                WebPage tpsub = it.next();
                if(tpsub.getId()!=null && user.haveAccess(tpsub))
                {
                    if(vTopic.contains(tpsub)) break;
                    vTopic.addElement(tpsub);

                    String backg="";
                    //String color="#666666";
                    if(tpid!=null && tpsub.getId().equals(tpid.getId())) {
                        backg=" bgcolor=\"#B8CFE5\"";
                        //color="#FFFFFF";
                    }

                    sb_ret.append("<tr>");
                    sb_ret.append("<td>");
                    sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">");
                    sb_ret.append("<tr>");

                    for(int i=0; i < intLevel-1; i++)
                    {
                        sb_ret.append("<td>");
                        sb_ret.append("<img height=\"5\" width=\""+widthsize+"\" border=\"0\" src=\""+path1+"trans.gif />");
                        sb_ret.append("</td>");
                    }
                    sb_ret.append("<td width=\"30\">");

                    if(isMapParent(tpid, tpsub, vctPath) || vctPath.contains(tpsub.getId())){
                        if((tpid!=null && tpsub.getId().equals(tpid.getId()) || vctPath.contains(tpsub.getId()))){
                            sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getParent().getId()+params+"\">");
                            sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+path1+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+path1+"minus.gif\"/>");
                            sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                        }else{
                            sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"\">");
                            sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+path1+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+path1+"plus.gif\"/>");
                            sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                        }
                    }
                    else{
                        sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"\">");
                        sb_ret.append("<img height=\"10\" width=\""+15+"\" border=\"0\" src=\""+path1+"trans.gif\" />");
                        sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+path1+"trans.gif\" />");
                        sb_ret.append("<img border=\"0\" src=\""+path1+"i_general_verde.gif\" />");
                        sb_ret.append("</a>");
                    }
                    sb_ret.append("<img height=\"10\" width=\""+5+"\" border=\"0\" src=\""+path1+"trans.gif\" />");
                    sb_ret.append("</td>");
                    sb_ret.append("<td"+backg+">");
                    sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                    sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;"+tpsub.getDisplayName()+"</font></a>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("</table>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    if((intLevel < intMaxLevel ||  (tpid!=null && tpsub.getId().equals(tpid.getId())) ||
                            vctPath.contains(tpsub.getId())) && tpsub.listChilds().hasNext()){
                        sb_ret.append(getChilds(response, tmredirec,tmit,tpid, tpsub, user, vctPath, intLevel+1, intWidth,strResmaptopic,widthsize+10,params));
                    }
                    vTopic.removeElement(tpsub);
                }
            }
        }catch(Exception e){
            log.error("Error on method WebSiteSectionTree.render()", e);
        }
        return sb_ret.toString();
    }

    /**
     * Calcula los t�picos padre de un t�pico.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @return    Regresa un objeto Vector que contiene los t�picos padre del t�pico requerido.
     //* @see       infotec.topicmaps.Topic
     */       
    public Vector getMapPath(WebPage tpid)
    {
        Vector vctPath=new Vector();
        if(tpid.getWebSite().getHomePage()!=tpid)
        {
            Iterator<WebPage> aux= tpid.listVirtualParents();
            while(aux.hasNext())
            {
                WebPage tp = aux.next();
                vctPath.addElement(tp.getId());
                aux = tp.listVirtualParents();
                if(tpid.getWebSite().getHomePage()==tp) {
                    break;
                }
            }
        }
        return vctPath;
    }

    /**
     * Calcula si un t�pico tiene t�picos hijo sin referencias c�clicas.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @param     tpsub     El t�pico hijo de acuerdo al nivel de recursividad presente en la generaci�n
     *                      de la estructura jer�rquica.
     * @param     vctPath   El vector que almacena los t�picos padre del t�pico requerido.
     * @return    Regresa si el t�pico solicitado contiene o no t�picos hijo con referencias c�clicas.
     //* @see       infotec.topicmaps.Topic
     */
    public boolean isMapParent(WebPage tpid, WebPage tpsub, Vector vctPath)
    {
        boolean bParent=false;
        Iterator<WebPage> hit=tpsub.listChilds();
        if(hit.hasNext())
        {
            do
            {
                WebPage htp = hit.next();
                if(tpid!=null)
                {
                    if(htp.getId()!=null && !tpid.getId().equals(htp.getId()) && !vctPath.contains(htp.getId()))
                    {
                        bParent=true;
                        break;
                    } 
                }
                else
                {
                    if(htp.getId()!=null && !vctPath.contains(htp.getId()))
                    {
                        bParent=true;
                        break;
                    } 
                }
            } while(hit.hasNext());
        }
        else bParent=false;

        return bParent;
    }
}

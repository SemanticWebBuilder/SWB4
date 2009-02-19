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
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceException;


public class WebSiteSectionTree {
    private static Logger log = SWBUtils.getLogger(WebSiteSectionTree.class);
    
    protected static final String imgPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";

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
    public String render(String p_selectedsite, HttpServletRequest request, User user, WebPage topicrec, Map arguments,WebPage topic, String miurl) throws SWBResourceException, IOException {
        StringBuilder sb_ret = new StringBuilder();
        String strResmaptopic=null;
        String params = "&site="+p_selectedsite;
        String whoOpen = "";
        
        //String strUrl="";
        //String param=null;
        WebSite tm=null;
        //WebSite tmredirec=topicrec.getWebSite();
        
        System.out.println("p_selectedsite="+p_selectedsite);
        System.out.println("topicrec="+topicrec);
        System.out.println("topic="+topic);
        Iterator<String> its=arguments.keySet().iterator();
        while(its.hasNext()) {
            String key=its.next();
            System.out.println("arguments["+key+"]="+arguments.get(key));
        }

        try {
            /*if(arguments.get("params")!=null) {
                params="&"+(String)arguments.get("params");
            }
            System.out.println("antes   params="+params);
            
            Enumeration en=request.getParameterNames();
            while (en.hasMoreElements()) {
                param=en.nextElement().toString();
                if (request.getParameter(param)!=null)
                    params+="&"+param+"="+request.getParameter(param);
            }
            System.out.println("despues params="+params);*/
            
            //strResmaptopic=topicrec.getId();
            WebPage tpid=null;
            Vector vctPath=new Vector();
            int intLevel=4, intWidth=10;
            //String idhome=null;

            int widthsize=10;
            String backg="";

            
            System.out.println("imgPath="+imgPath);

            /*if(topic!=null){
                strUrl = SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + topic.getWebSiteId() + "/";
            }*/
                        
            //String miurl="getHtml('"+url.toString()+",'slave')";

            WebPage tpsite=null;
            if(request.getParameter("reptm")!=null) {
                tm=SWBContext.getWebSite(request.getParameter("reptm"));
                
                if(tm!=null) {                
                    tpsite=tm.getHomePage();

                    if(request.getParameter("reptp")!=null && !request.getParameter("reptp").trim().equals("")) {
                        if(tm.getWebPage(request.getParameter("reptp"))!=null) {
                            tpid=tm.getWebPage(request.getParameter("reptp"));
                            vctPath=getMapPath(tpid);
                        }
                    }
                }
            }
            
            sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" width=\"100%\">");

            /*if((tpid!=null && tpid.getId().equals(topic.getId())) || (tpid==null)) {
                System.out.println("topic si es usado 2");
                backg=" bgcolor=\"#2A5216\"";
                //color="#FFFFFF";
            }*/

            WebSite tmit = SWBContext.getWebSite(p_selectedsite);
            if(tmit==null)System.out.println("tmit nulo");else System.out.println("tmit NO nulo");
            WebPage tmhome=tmit.getHomePage();
            
            //backg="";
            //color="#666666";
                        
            if(tpid!=null && tpid.getId().equals(tmhome.getId())){
                backg=" bgcolor=\"#B8CFE5\"";
                //params.append("&"+tmhome.getId()+"=1");
                //color="#FFFFFF";
            }
            
            System.out.println("tm  ="+tm);
            System.out.println("tpsite="+tpsite);
            System.out.println("tpid  ="+tpid);
            System.out.println("tmit="+tmit);
            System.out.println("tmhome="+tmhome);

            sb_ret.append("<tr>");
            sb_ret.append("<td valign=\"bottom\">");
            sb_ret.append("<img border=\"0\" src=\""+imgPath+"f_site_verde.gif\" />");
            sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"#0000CC\" style=\"text-decoration:none; font-weight:bold\">&nbsp;&nbsp;"+tmit.getId()+"</font>");
            sb_ret.append("</td>");
            sb_ret.append("</tr>");

            //if( (tm!=null && tpit!=null && !tm.getId().equalsIgnoreCase(tmit.getId())) || (tm==null && tpsite==null) || (tm!=null && tpsite!=null) ) {
            /*if( (tm!=null && tmit!=null && !tm.getId().equalsIgnoreCase(tmit.getId())) || (tm==null && tpsite==null) || (tm!=null && tpsite!=null) ) {*/
                sb_ret.append("<tr>");
                sb_ret.append("<td>");
                sb_ret.append("<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">");
                sb_ret.append("<tr>");
                
                sb_ret.append("<td>");
                /*sb_ret.append("<img height=\"10\" width=\"15\" border=\"0\" src=\""+imgPath+"trans.gif\" />");*/
                //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"\">");
                
                String toggleopen = request.getParameter(tmhome.getId())==null ? "0":request.getParameter(tmhome.getId());
                if(toggleopen.equalsIgnoreCase("0")) {
                    whoOpen = "&"+tmhome.getId()+"=1";
                }else {
                    whoOpen = "&"+tmhome.getId()+"=0";
                }               
                sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp="+tmhome.getId()+whoOpen+params+"','slave')\">");
                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                if(toggleopen.equalsIgnoreCase("1")) {
                    sb_ret.append("<img border=\"0\" src=\""+imgPath+"minus.gif\" />");
                }else {
                    sb_ret.append("<img border=\"0\" src=\""+imgPath+"plus.gif\" />");
                }
                sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_home_verde.gif\" />");
                sb_ret.append("</a>");
                sb_ret.append("</td>");
                
                sb_ret.append("<td"+backg+">");
                //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"/?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"&sel=1"+"','slave')\" style=\"text-decoration:none\">");
                sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;&nbsp;"+tmhome.getDisplayName()+"</font>");
                sb_ret.append("</a>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
                sb_ret.append("</table>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
            /*}*/

            //if( tpsite!=null && tpid!=null && tpid.getWebSiteId().equalsIgnoreCase(tmit.getId()) ) {
              if(tpsite!=null) {
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
                        sb_ret.append("	<img height=\"10\" width=\"20\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                        sb_ret.append("</td>");
                        
                        
                        //if(tp.listChilds().hasNext() || vctPath.contains(tp.getId())) {
                        if(tp.listChilds().hasNext()) {
                            
                            toggleopen = request.getParameter(tp.getId())==null ? "0":request.getParameter(tmhome.getId());
                            if(toggleopen.equalsIgnoreCase("0")) {
                                whoOpen = "&"+tp.getId()+"=1";
                            }else {
                                whoOpen = "&"+tp.getId()+"=0";
                            }
                            sb_ret.append("<td width=\"50\">");
                            sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getId()+whoOpen+params+"','slave')\">");
                            sb_ret.append("<img height=\"10\" width=\"20\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                            if(toggleopen.equalsIgnoreCase("1")) {
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"minus.gif\" />");
                            }else {
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"plus.gif\" />");
                            }
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                            sb_ret.append("</td>");
                            
                            sb_ret.append("<td "+backg+">");
                            sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"&sel=1"+"','slave')\" style=\"text-decoration:none\">");
                            sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;"+tp.getDisplayName()+"</font></a>");
                            sb_ret.append("</td>");
                            
                            
                            /*if(tpid!=null && tpid.getId().equalsIgnoreCase(tp.getId())) {                                
                                //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getWebSiteId()+params+"\">");
                                sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getWebSiteId()+params+"','slave')\">");
                                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"minus.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                                sb_ret.append("</a>");
                            }else {
                                //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"\">");
                                sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"','slave')\">");
                                sb_ret.append("<img height=\"10\" width=\"10\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"plus.gif\" />");
                                sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                                sb_ret.append("</a>");
                            }*/

                        }
                        else{
                            sb_ret.append("<td width=\"50\" colspan=\"2\">");
                            //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"\">");
                            sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"','slave')\">");
                            sb_ret.append("<img height=\"10\" width=\"20\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                            //sb_ret.append("<img height=\"10\" width=\"15\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                            sb_ret.append("</td>");
                        }
                        /*sb_ret.append("</td>");

                        sb_ret.append("<td "+backg+">");
                        //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                        sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tp.getId()+params+"&sel=1"+"','slave')\" style=\"text-decoration:none\">");
                        sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;"+tp.getDisplayName()+"</font></a>");
                        sb_ret.append("</td>");*/
                        sb_ret.append("</tr>");
                        sb_ret.append("</table>");
                        sb_ret.append("</td>");
                        sb_ret.append("</tr>");
                        /*if(( (tpid!=null && tp.getId().equals(tpid.getId())) ||
                                vctPath.contains(tp.getId())) && tp.listChilds().hasNext()){
                            sb_ret.append(getChilds(topicrec.getWebSite(),tmit,tpid, tp, user, vctPath, intLevel+1, intWidth,widthsize,params.toString(), miurl));
                        }*/
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
     
     * @param widthsize
     * @param params
     * @throws IOException
     * @return
     */    
    protected String getChilds(WebSite tmredirec, WebSite tmit, WebPage tpid, WebPage tpc, User user, Vector vctPath, int intLevel, int intWidth,int widthsize,String params, String miurl) throws IOException{
        //PrintWriter out=response.getWriter();
        StringBuilder sb_ret = new StringBuilder();
        //String strResmaptopic=null;

        //strResmaptopic=topicrec;
        //StringBuffer sbfRet=new StringBuffer();
        try {            
            //String imgPath = webpath + "/swbadmin/icons/";
            //String strUrl = SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + tmredirec.getId() + "/";
            
            Iterator<WebPage> it = tpc.listChilds();

            while(it.hasNext()){
                WebPage tpsub = it.next();
                //if(tpsub.getId()!=null && user.haveAccess(tpsub))
                if(tpsub.getId()!=null)
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

                    /*for(int i=0; i < intLevel-1; i++) {*/
                        sb_ret.append("<td>");
                        sb_ret.append("<img height=\"5\" width=\""+(widthsize*intLevel-intLevel)+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                        sb_ret.append("</td>");
                    /*}*/
                    sb_ret.append("<td>");

                    if(isMapParent(tpid, tpsub, vctPath) || vctPath.contains(tpsub.getId())){
                        System.out.println("camino 1");
                                
                        if((tpid!=null && tpsub.getId().equals(tpid.getId()) || vctPath.contains(tpsub.getId()))){
                            //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getParent().getId()+params+"\">");
                            sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tpsub.getParent().getId()+params+"','slave')\">");
                            sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"minus.gif\"/>");
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                        }else{
                            //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"\">");
                            sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"','slave')\">");
                            sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"plus.gif\"/>");
                            sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                            sb_ret.append("</a>");
                        }
                    }
                    else{
                        System.out.println("camino 2");
                        
                        //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"\">");
                        sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params+"','slave')\">");
                        sb_ret.append("<img height=\"10\" width=\""+15+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                        sb_ret.append("<img height=\"10\" width=\""+widthsize+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                        sb_ret.append("<img border=\"0\" src=\""+imgPath+"i_general_verde.gif\" />");
                        sb_ret.append("</a>");
                    }
                    sb_ret.append("<img height=\"10\" width=\""+5+"\" border=\"0\" src=\""+imgPath+"trans.gif\" />");
                    sb_ret.append("</td>");
                    sb_ret.append("<td"+backg+">");
                    //sb_ret.append("<a href=\""+strUrl + strResmaptopic + "/?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params.replaceAll("&step=map","")+"&sel=1"+"\" style=\"text-decoration:none\">");
                    sb_ret.append("<a href=\"javascript:getHtml('"+miurl+"?reptm="+tmit.getId()+"&reptp=" + tpsub.getId()+params.replaceAll("&step=map","")+"&sel=1"+"','slave')\" style=\"text-decoration:none\">");
                    sb_ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\" color=\"black\">&nbsp;"+tpsub.getDisplayName()+"</font></a>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("</table>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    if((intLevel < intMaxLevel ||  (tpid!=null && tpsub.getId().equals(tpid.getId())) ||
                            vctPath.contains(tpsub.getId())) && tpsub.listChilds().hasNext()){
                        sb_ret.append(getChilds(tmredirec,tmit,tpid, tpsub, user, vctPath, intLevel+1, intWidth,widthsize+10,params, miurl));
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
        System.out.println("\n\ninicia getMapPath...");
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
        System.out.println("termina getMapPath\n\n");
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

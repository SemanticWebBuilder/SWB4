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


/** WBAFilterResource.java
 *
  * User: Alberto Reyes
 * Date: 5/10/2004
 * Time: 05:03:21 PM
 * To change this template use File | Settings | File Templates.
 */
package org.semanticwb.portal.admin.resources;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.*;

import javax.servlet.http.*;
import javax.servlet.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

import org.semanticwb.model.Portlet;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

/** Recurso para la administarci�n de WebBuilder que permite seleccionar los t�picos
 * en los cuales se mostrar� el recurso seleccionado.
 *
 * WebBuilder administration resource that allows select the topics to show the
 * selected resource.
 */

public class SWBAFilterPortlet extends SWBATree{

    private Logger log = SWBUtils.getLogger(SWBAFilterPortlet.class);
    static final String[] pathValids={"getServer","getTopic","getWebSite"};
    static final String[] namevalids={"node","config","icons","icon","res","events","willExpand"};
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equals("gateway"))
        {
            doGateway(request,response,paramRequest);
        }else super.processRequest(request,response,paramRequest);
    }
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);
        if (!dom.getFirstChild().getNodeName().equals("req"))
        {
            response.sendError(404, request.getRequestURI());
            return;
        }
        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0)
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();

        if (cmd == null)
        {
            response.sendError(404, request.getRequestURI());
            return;            
        }
        String ret="";
        try
        {
            Document res=null;
            if(cmd.equals("update"))
            {
                res = updateFilter(cmd, dom, paramRequest.getUser(), request, response);
            }                        
            else if(cmd.equals("getFilter"))
            {
                res = getFilter(cmd, dom, paramRequest.getUser(), request, response);
            }            
            else
            {
                res = getService(cmd, dom, paramRequest.getUser(), request, response);
            }
            if (res == null)
            {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else
                ret = SWBUtils.XML.domToXml(res, true);
        }catch(Exception e){log.error(e);}
        out.print(new String(ret.getBytes()));
        
    }   
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document add(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        Document doc=null;
        try
        {
            doc=SWBUtils.XML.getNewDocument();
            Element res=doc.createElement("res");
            doc.appendChild(res);            
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
            log.error(e);
        }
        return doc;
    }    
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document updateFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        
        if(src.getElementsByTagName("filter").getLength()>0)
        {
            String id=src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
            String tm=src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue();
            WebSite map=SWBContext.getWebSite(tm);
            Portlet recres=map.getPortlet(id);
            String xml=recres.getXmlConf();
            
            try
            {
                Document docxmlConf=null;
                Element eResource=null;                
                if(xml==null || xml.equals(""))
                {
                    docxmlConf=SWBUtils.XML.getNewDocument();
                    eResource=docxmlConf.createElement("resource");
                    docxmlConf.appendChild(eResource);
                }
                else
                {
                    docxmlConf=SWBUtils.XML.xmlToDom(xml);
                    if(docxmlConf.getElementsByTagName("resource").getLength()>0)
                    {
                        eResource=(Element)docxmlConf.getElementsByTagName("resource").item(0);
                    }
                    else
                    {
                        if(docxmlConf.getElementsByTagName("Resource").getLength()>0)
                        {
                            eResource=(Element)docxmlConf.getElementsByTagName("Resource").item(0);
                        }
                        else
                        {
                            eResource=docxmlConf.createElement("resource");
                            docxmlConf.appendChild(eResource);
                        }
                    }
                }
                NodeList filters=eResource.getElementsByTagName("filter");
                for(int i=0;i<filters.getLength();i++)
                {
                    Element filter=(Element)filters.item(i);
                    filter.getParentNode().removeChild(filter);
                }                
                filters=src.getElementsByTagName("filter");
                for(int i=0;i<filters.getLength();i++)
                {
                    Element filter=(Element)filters.item(i);
                    filter=(Element)docxmlConf.importNode(filter,true);
                    eResource.appendChild(filter);
                }
                recres.setXmlConf(SWBUtils.XML.domToXml(docxmlConf));
                //recres.update();
                Document docresp=SWBUtils.XML.getNewDocument();
                Element filterresp=docresp.createElement("filter");
                Text t=docresp.createTextNode(id);
                filterresp.appendChild(t);
                docresp.appendChild(filterresp);
                return docresp;
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
                log.error(e);
            }
        }
        return null;
    }
    /**
     * @param user
     * @param src
     * @return
     */    
    public Document initTree(User user, Document src)
    {
        Document doc=super.initTree(user,src,true);        
        RevisaNodo(doc.getFirstChild());                 
        return doc;
    }
    /**
     * @param e
     * @return
     */    
    public boolean isNameValid(Element e)
    {       
        
        for(int i=0;i<this.namevalids.length;i++)
        {
            if(e.getNodeName().equals(this.namevalids[i]))
            {
                return true;
            }
        }                
        return false;
    }
    /**
     * @param path
     * @return
     */    
    public boolean isValid(String path)
    {
        if(path==null)
        {
            return true;
        }
        StringTokenizer st=new StringTokenizer(path,".");
        if(st.countTokens()>0)
        {
            String pathinit=st.nextToken();            
            for(int i=0;i<this.pathValids.length;i++)
            {            
                if(pathinit.equals(this.pathValids[i]))
                {
                    return true;
                }
            }
        }
        else
        {            
            return true;
        }
        return false;
    }   
    /**
     * @param ele
     */    
    public void RevisaNodo(Node ele)
    {        
        Vector vnodes=new Vector();
        NodeList nodes=ele.getChildNodes();
        for(int i=0;i<nodes.getLength();i++)
        {
            vnodes.add(nodes.item(i));
        }
        for(int i=0;i<vnodes.size();i++)
        {            
            if(vnodes.elementAt(i) instanceof Element)
            {
                Element e=(Element)vnodes.elementAt(i);
                if(!isNameValid(e) || !isValid(e.getAttribute("reload")))
                {                     
                    ele.removeChild((Node)vnodes.elementAt(i));
                }         
                else
                {
                    RevisaNodo(e);                
                }
            }
            else
            {
                RevisaNodo((Node)vnodes.elementAt(i));
            }
        }     
    }
    /**
     * @param cmd
     * @param src
     * @param user
     * @param request
     * @param response
     * @return
     */    
    public Document getFilter(String cmd,Document src,User user,HttpServletRequest request,HttpServletResponse response)
    {
        String id=src.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
        String tm=src.getElementsByTagName("tm").item(0).getFirstChild().getNodeValue();
        
        WebSite map=SWBContext.getWebSite(tm);
        Portlet recres=map.getPortlet(id);
        String xml=recres.getXmlConf();
        Document docres=null;
        if(xml!=null)
        {
            
            try
            {   
                docres=SWBUtils.XML.getNewDocument();
                Element res=docres.createElement("res");
                docres.appendChild(res);
                Document docconf=SWBUtils.XML.xmlToDom(xml);
                if(docconf!=null)
                {
                    NodeList filters=docconf.getElementsByTagName("filter");
                    for(int i=0;i<filters.getLength();i++)
                    {
                        Element filter=(Element)filters.item(i);
                        filter=(Element)docres.importNode(filter,true);
                        res.appendChild(filter);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
                log.error(e);
            }
        }
        return docres;
    }
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String strWBAction=request.getParameter("act");
        if (strWBAction==null || strWBAction!=null && strWBAction.equals("") || strWBAction!=null && strWBAction.equals("view")) {
            getIniForm(request,response,paramRequest,paramRequest.getUser());
        }
        else if (strWBAction!=null && strWBAction.equals("add")) {
        }
        else if (strWBAction!=null && strWBAction.equals("edit")) {
        }
    }

    /**
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //String strWBAction=request.getParameter("act");
        //System.out.println("Llego aqui");
        String tm=request.getParameter("tm");
        String [] strTopics=request.getParameterValues("tps");
        Document dom=null;
        Element elmRes=null;
        String id="0";
        if (request.getParameter("id")!=null)
            id =request.getParameter("id");
        Portlet recRes=SWBContext.getWebSite(tm).getPortlet(id);
        String strXml=recRes.getXmlConf();
        // Se genera el XML
        if (strXml==null || strXml!=null && strXml.equals("")) {
            try 
            {
                DocumentBuilderFactory dbf=null;
                DocumentBuilder db=null;
                dbf= DocumentBuilderFactory.newInstance();
                db= dbf.newDocumentBuilder();
                dom = db.newDocument ();
                elmRes = dom.createElement ("resource");
                dom.appendChild (elmRes);
                Element filter=dom.createElement("filter");
                elmRes.appendChild(filter);
                String strTm=null;
                String strTp=null;
                if (strTopics!=null) {
                    for(int counter = 0; counter < strTopics.length; counter++) {
                        StringTokenizer st = new StringTokenizer(strTopics[counter],"|");
                        while(st.hasMoreTokens()) {
                            strTm = st.nextToken();
                            strTp = st.nextToken();
                        }
                        dom=setElement(dom, strTm, strTp, "add");
                    }
                }
                recRes.setXmlConf(SWBUtils.XML.domToXml(dom));
                //recRes.update();
                response.setRenderParameter("confirm","added");
            } catch (Exception e) {
                log.error("Error while updating resource with id:"+id+"- SWBAFilterResource:processAction",e);
            }
        }
        else {
            try {
                int intIniIdx=strXml.indexOf("<filter>");
                int intEndIdx=strXml.lastIndexOf("</filter>");
                if (intIniIdx!=-1 && intEndIdx!=-1)
                    strXml=strXml.substring(0,intIniIdx)+strXml.substring(intEndIdx+9,strXml.length());
                dom=SWBUtils.XML.xmlToDom(strXml);
                elmRes = (Element)dom.getFirstChild();
                String strTm=null;
                String strTp=null;
                if (strTopics!=null) {
                    for(int counter = 0; counter < strTopics.length; counter++) {
                        StringTokenizer st = new StringTokenizer(strTopics[counter],"|");
                        while(st.hasMoreTokens()) {
                            strTm = st.nextToken();
                            strTp = st.nextToken();
                        }
                        dom=setElement(dom, strTm, strTp, "update");
                    }
                }
                recRes.setXmlConf(SWBUtils.XML.domToXml(dom));
                response.setRenderParameter("confirm","added");
            }
            catch (Exception e) {
                log.error("Error while updating resource with id:"+id+"- SWBAFilterResource:processAction",e);
            }
        }
        response.setRenderParameter("id",id);
        response.setRenderParameter("tm",tm);
        //System.out.println("Lo guardo");
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#CCCCCC\">");
        out.println("<tr>");
        out.println("<td>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#FFFFFF\">");
        out.println("<tr>");
        out.println("<td>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
        out.println("<tr>");
        out.println("<td><font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#666699\"><img src=\""+SWBPlatform.getContextPath()+"swbadmin/images/ico_agregar.gif\" width=\"17\" height=\"17\"> ");
        out.println("Datos generales del recurso.</font></td>");
        out.println("</tr>");
        out.println("<tr> ");
        out.println("<td bgcolor=\"#979FC3\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#FFFFFF\">");
        out.println("&nbsp;</font></td>");
        out.println("</tr>");
        out.println("<tr> ");
        out.println("<td> ");
        out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td align=\"left\" valign=\"top\" colspan=\"2\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
        out.println("Este recurso no es administrable.");
        out.println("<font></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
    }

    private void getIniForm (HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest,User user) throws IOException{
        PrintWriter out=response.getWriter();
        //String tp=paramRequest.getTopic().getId();
        String tm=request.getParameter("tm");
        WebSite map=SWBContext.getWebSite(tm);
        String id="0";
        if (request.getParameter("id")!=null && !request.getParameter("id").equals("id"))
            id=request.getParameter("id");
        Portlet recRes=null;
        String strConfirm=request.getParameter("confirm");
        if (strConfirm!=null && strConfirm.equals("added")) {
            out.println("<script>");
            out.println("wbStatus('Filter updated');");
            out.println("</script>");
        }
        try {
            recRes = map.getPortlet(id);
            String strXml=recRes.getXmlConf();
            /*out.println("<form name=\"frmIni\" action=\"\">");
            out.println(getJavaScript(paramRequest));
            out.println("<table width=\"95%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\">");
            out.println("<tr>");
            out.println("<td>");
            out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<tr>");
            out.println("<td class=\"valores\">");
            out.println("<table width=\"75%\" border=\"0\" cellpadding=\"4\" cellspacing=\"1\" bgcolor=\"\"> ");
            out.println("<tr bgcolor=\"\" class=\"datos\">");
            out.println("<td align=\"right\">Topico:</td> ");
            out.println("<td><input name=\"tpp\" type=\"text\" size=\"50\" maxlength=\"100\" value=\"\" onFocus=\"send('frmIni')\" onKeyDown=\"javascript:document.frmIni.tpp.blur();\" onKeyUp=\"javascript:document.frmIni.tpp.blur();\"></td> ");
            out.println("</tr> ");
            out.println("<tr>");
            out.println("<td class=\"datos\" colspan=\"2\" align=\"center\">");
            if (request.getParameter("tpp")!=null){
                String valores="";
                String valor=request.getParameter("tpp");
                valores=request.getParameter("valores");
                if (valores!=null)
                    valores+=valor+"*";
                else
                    valores=valor+"*";
                out.println("<input type=\"hidden\" name=\"valores\" value=\""+valores+"\">");
                if (valores!=null) {
                    StringTokenizer st1 = new StringTokenizer(valores,"*");
                    out.println("<select name=\"tps\" size=\"4\" multiple>");
                    while(st1.hasMoreTokens()) {
                        String token1 = st1.nextToken();
                        StringTokenizer st2 = new StringTokenizer(token1,"|");
                        String strType=null;
                        String strTm=null;
                        String strTp=null;
                        while(st2.hasMoreTokens()) {
                            strType = st2.nextToken();
                            strTm=st2.nextToken();
                            strTp=st2.nextToken();
                        }
                        if (strType!=null && strType.equals("Topic"))
                            out.println("<option value=\""+strTm+"|"+strTp+"\">"+SWBContext.getWebSite(strTm).getTopic(strTp).getDisplayName()+"</option> ");
                    }
                    out.println("</select>");
                }
            }
            if ((strXml!=null && !strXml.equals("")) && request.getParameter("tpp")==null) {
                out.println(getComboFilters(strXml));
                out.println("<input type=\"hidden\" name=\"valores\" value=\""+getValores(strXml)+"\">");
            }
            out.println("</td> ");
            out.println("</tr> ");
            out.println("<tr align=\"center\" bgcolor=\"\">");
            out.println("<td colspan=\"2\"> ");
            out.println("<input type=button  class=\"botones\" name=Aceptar onClick='javascript:send(\"save\");' value=Guardar> ");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" onClick='Borra();' value=\"Eliminar\">");
            out.println("<input type=hidden  name=\"act\" value=\"\"> ");
            out.println("<input type=hidden  name=\"id\" value=\""+id+"\"> ");
            out.println("<input type=hidden  name=\"tm\" value=\""+request.getParameter("tm")+"\"> ");
            out.println("</td> ");
            out.println("</tr> ");
            out.println("</table> ");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");*/
            out.println("<div class=\"applet\">");
            out.println("<APPLET id=\"editfilter\" name=\"editfilter\" code=\"applets.filterSection.FilterSection.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"swbadmin/lib/filterSection.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode("gateway");
            url.setCallMethod(url.Call_DIRECT);
            out.println("<PARAM NAME =\"idfilter\" VALUE=\""+request.getParameter("id")+"\">");
            out.println("<PARAM NAME =\"cgipath\" VALUE=\""+url+"\">");            
            out.println("<PARAM NAME =\"locale\" VALUE=\""+user.getLanguage()+"\">");            
            out.println("<PARAM NAME =\"tm\" VALUE=\""+map.getId()+"\">");
            out.println("<PARAM NAME =\"idresource\" VALUE=\""+id+"\">");
            boolean global=false;            
            if(map.getId().equalsIgnoreCase(SWBContext.getGlobalWebSite().getId()))
            {
                global=true;
            }
            out.println("<PARAM NAME =\"isGlobalTM\" VALUE=\""+global+"\">");
            out.println("</APPLET>");
            out.println("</div>");
        } catch (Exception e) {
                log.error("Error while getting resource with id:"+id+"- SWBAFilterResource:getIniForm()",e);
        }
    }

    private String getJavaScript(SWBParamRequest paramRequest) {
        StringBuffer sbRet = new StringBuffer();
        SWBResourceURL urlResAct=paramRequest.getActionUrl();
        sbRet.append("<script type=\"text/javascript\" language=\"JavaScript\">");
        sbRet.append("  function send (_f) {\n");
        sbRet.append("      alert (_f);\n");
        sbRet.append("      var tp=document.frmIni.tpp.value;\n");
        sbRet.append("      if (tp!=undefined && tp!='') {\n");
        sbRet.append("          if (isRepeated())\n");
        sbRet.append("              document.frmIni.tpp.value='';\n");
        sbRet.append("          else\n");
        sbRet.append("              document.frmIni.submit();\n");
        sbRet.append("      }\n");
        sbRet.append("      if (_f=='save' && document.frmIni.tps!=undefined) {\n");
        sbRet.append("          document.frmIni.act.value=_f;\n");
        sbRet.append("          document.frmIni.action='"+urlResAct+"';\n");
        sbRet.append("          selAll('true');\n");
        sbRet.append("          document.frmIni.submit();\n");
        sbRet.append("      }\n");
        sbRet.append("  }\n");

        sbRet.append("  function selAll(_v) {");
        sbRet.append("	    for(var i=0;i<document.frmIni.tps.length;i++)");
        sbRet.append("		    document.frmIni.tps[i].selected=_v;");
        sbRet.append("  }");

        sbRet.append("  function isRepeated() {");
        sbRet.append("	    if (document.frmIni.tps!=undefined)");
        sbRet.append("	    for(var i=0;i<document.frmIni.tps.length;i++)");
        sbRet.append("		    if ('Topic|'+document.frmIni.tps[i].value==document.frmIni.tpp.value)");
        sbRet.append("		        return true;");
        sbRet.append("		return false;");
        sbRet.append("  }");

        sbRet.append("  function Borra() {");
        sbRet.append("      for (var i=0 ; i<document.frmIni.tps.length;i++) {");
        sbRet.append("          if(document.frmIni.tps.options[i].selected==true) {");
        sbRet.append("              document.frmIni.tps.options[i]=null;");
        sbRet.append("              document.frmIni.act.value='save';");
        sbRet.append("              document.frmIni.action='"+urlResAct+"';");
        sbRet.append("              selAll('true');");
        sbRet.append("              document.frmIni.submit();");
        sbRet.append("          }");
        sbRet.append("      }");
        sbRet.append("  }");
        sbRet.append("");
        sbRet.append("</script>");
        return sbRet.toString();
    }

    private Document setElement (Document dom, String tm, String tp, String action){
        Element res = null;
        Element eTm =null;
        Element eTp =null;
        if (action!=null && action.equals("add")) {
            NodeList nodelist = dom.getFirstChild().getChildNodes();
            for (int i=0; i<nodelist.getLength();i++) {
                if (nodelist.item(i).getNodeName().equals("filter")) {
                    res = (Element) nodelist.item(i);
                    eTm = dom.createElement("WebSite");
                    eTm.setAttribute("id",tm);
                    res.appendChild(eTm);
                    eTp = dom.createElement("topic");
                    eTp.setAttribute("id",tp);
                    eTp.setAttribute("childs","true");
                    eTm.appendChild(eTp);
                }
            }
        }
        else if (action!=null && action.equals("update")) {
            res = (Element) dom.getFirstChild();
            Element filter=dom.createElement("filter");
            res.appendChild(filter);
            eTm = dom.createElement("WebSite");
            eTm.setAttribute("id",tm);
            filter.appendChild(eTm);
            eTp = dom.createElement("topic");
            eTp.setAttribute("id",tp);
            eTp.setAttribute("childs","true");
            eTm.appendChild(eTp);
        }
        return dom;
    }

    private Vector getFilters(String strXml) {
        Vector vFilters=new Vector();
        if (strXml!=null && !strXml.equals("")) {
            Document dom=SWBUtils.XML.xmlToDom(strXml);
            NodeList nodelist = dom.getFirstChild().getChildNodes();
            for (int i=0; i<nodelist.getLength();i++) {
                if (nodelist.item(i).getNodeName().equals("filter")) {
                    NodeList nlFilter=nodelist.item(i).getChildNodes();
                    for (int j=0; j<nlFilter.getLength();j++)
                        vFilters.add(nlFilter.item(j).getAttributes().getNamedItem("id").getNodeValue()+"|"+nlFilter.item(j).getFirstChild().getAttributes().getNamedItem("id").getNodeValue());
                }
            }
        }
        return vFilters;
    }

    private String getComboFilters(String strXml) {
        StringBuffer sbRet = new StringBuffer();
        Vector vFilters=getFilters(strXml);
        if (vFilters.size()>0) {
            sbRet.append("<select name=\"tps\" size=\"4\" multiple>");
            for (int i=0;i<vFilters.size();i++) {
                StringTokenizer st = new StringTokenizer((String)vFilters.get(i),"|");
                String strTm=null;
                String strTp=null;
                while(st.hasMoreTokens()) {
                    strTm=st.nextToken();
                    strTp=st.nextToken();
                }
                sbRet.append(" <option value=\""+vFilters.get(i)+"\">"+SWBContext.getWebSite(strTm).getWebPage(strTp).getDisplayName()+"</option> ");
            }
            sbRet.append("</select>");
        }
        return sbRet.toString();
    }

    private String getValores(String strXml) {
        String strValores = "";
        Vector vFilters=getFilters(strXml);
        if (vFilters.size()>0) {
            for (int i=0;i<vFilters.size();i++) {
                strValores+="Topic|"+vFilters.get(i)+"*";
            }
        }
        return strValores;
    }
}

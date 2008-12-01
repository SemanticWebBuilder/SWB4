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


/*
 * Print.java
 *
 * Created on 24 de septiembre de 2002, 03:02 AM
 */

package com.infotec.wb.resources;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.Template;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.semanticwb.portal.util.FileUpload;

/** Objeto que se encarga de desplegar y administrar una interfaz parar imprimir topicos bajo ciertos
 * criterios(configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer an interface to print topics under certain
 * criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N��ez
 * @Modified: Jorge Jiménez
 * @version 1.0
 */
public class Print extends GenericResource 
{
    private static Logger log = SWBUtils.getLogger(Print.class);
    
    String workPath = "";
    String webWorkPath = "/work"; 
    String windowconf="";
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();

    /** 
     * Creates a new instance of Imprimir 
     */
    public Print() {
    }
    
    /**
     * @param base
     */    
    public void setResourceBase(Portlet base)
    {
        try 
        {
            super.setResourceBase(base);
            workPath = (String)SWBPlatform.getWorkPath() +  base.getWorkPath();
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();        
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
    }
    
    
    public void getParams(javax.servlet.http.HttpServletRequest request, SWBResourceURLImp url)
    {
        if(null!=request.getQueryString())
        {
            Map q=SWBUtils.TEXT.parseQueryParams("?"+request.getQueryString());
            if(q!=null)
            {
                Set s=q.keySet();
                if(s!=null)
                {
                    Iterator it=s.iterator();
                    while(it.hasNext())
                    {
                        String key=(String)it.next();
                        if(!key.equals("page"))
                        {
                            url.setParameter(key,(String[])q.get(key));
                        }
                    }
                }
            }
        }        
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        String action = null != request.getParameter("imp_act") && !"".equals(request.getParameter("imp_act").trim()) ? request.getParameter("imp_act").trim() : "imp_step1";
        setWindowConf();
        if("imp_step2".equals(action))  {
            printByPage(request, response, paramRequest); // Imprimir por p�gina o documento completo
        }
        else if("imp_step3".equals(action))  {
            printContent(request, response, paramRequest); // Nueva ventana con el contenido a imprimir
        }
        else
        {
            // Objeto(imagen/bot�n) para invocar la nueva ventana
            StringBuffer ret = new StringBuffer("");
            Portlet base=getResourceBase();

            SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
            url.setResourceBase(base);
            url.setMode(url.Mode_VIEW);
            url.setWindowState(url.WinState_MAXIMIZED);
            url.setTopic(paramRequest.getTopic());
            url.setCallMethod(paramRequest.Call_DIRECT);
            if(request.getParameter("page")!=null && !request.getParameter("page").trim().equals("")) 
            {
                url.setParameter("imp_act","imp_step2");
                url.setParameter("page", request.getParameter("page"));
            }
            else
            {
                url.setParameter("imp_act","imp_step3");
                //url.setParameter("page", "0");
            }
            
            getParams(request, url);
            
            //response.sendRedirect(url.toString());
            String onclick="javascript:window.open('" + url.toString() +"','_newimp','" + getWindowConf() + "'); return false;";

            synchronized (ret)
            {
                if (!"".equals(base.getAttribute("img", "").trim()))
                {
                    ret.append("\n<a href=\""+onclick+"\"><img onClick=\""+ onclick +"\" src=\"");
                    ret.append(webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                    if (!"".equals(base.getAttribute("alt","").trim())) {
                        ret.append(" alt=\"" + base.getAttribute("alt").trim().replaceAll("\"", "&#34;") + "\"");
                    }
                    ret.append(" border=0></a>");
                } 
                else if (!"".equals(base.getAttribute("btntexto", "").trim()))
                {
                    ret.append("\n<form name=\"frmImprimir\" action=\"\">");
                    ret.append("\n<input type=button name=btnImprimir onClick=\""+ onclick +"\" value=");
                    ret.append("\"" + base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;") + "\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
                    }
                    ret.append("\n></form>");
                }
                else
                {
                    ret.append("\n<a href=\""+onclick+"\" onClick=\""+ onclick +"\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
                    }
                    ret.append(">");
                    if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                        ret.append(base.getAttribute("lnktexto").trim());
                    }
                    else {
                        paramRequest.getLocaleString("msgPrint");
                    }
                    ret.append("</a>");
                }
            }
            response.getWriter().print(ret.toString());            
        }
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void printByPage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURLImp url=new SWBResourceURLImp(request, getResourceBase(), paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
        url.setResourceBase(getResourceBase());
        url.setMode(url.Mode_VIEW);
        url.setWindowState(url.WinState_MAXIMIZED);
        url.setTopic(paramRequest.getTopic());
        url.setCallMethod(paramRequest.Call_DIRECT);
        
        getParams(request, url);
        
        url.setParameter("imp_act","imp_step3");
        

        ret.append("\n<script language=\"JavaScript\">");
        ret.append("\nfunction wbPrint(frm)");
        ret.append("\n{");
        ret.append("\n   var pOk=0;");
        ret.append("\n   for(var i=0; i < frm.page.length ;i++)");
        ret.append("\n   {");
        ret.append("\n       if(frm.page[i].checked) pOk=frm.page[i].value;");
        ret.append("\n   }");
        ret.append("\n   window.open('" + url.toString() +"&amp;page='+pOk");
        ret.append(",'_preview','"+ getWindowConf() +"');"); 
        ret.append("\n   window.self.close();");
        ret.append("\n   return true;");
        ret.append("\n}");
        ret.append("\n</script>");
        ret.append("\n<form name=frmPrint>");
        ret.append("\n<table width=\"100%\" height=\"100%\" border=\"1\"><tr><td align=\"center\" valign=\"center\">");
        ret.append("\n<table border=\"0\" cellspacing=\"5\" cellpadding=\"5\">");
        ret.append("\n<tr><td>");
        ret.append("\n<input type=radio  name=page value=\"0\" checked> " + paramRequest.getLocaleString("msgAllDocument"));
        ret.append("\n</td></tr><tr><td>");
        ret.append("\n<input type=radio  name=page value=\"");
        if(request.getParameter("page")!=null && !request.getParameter("page").trim().equals("")) {
            ret.append(request.getParameter("page"));
        }
        else ret.append("1");
        ret.append("\"> "+ paramRequest.getLocaleString("msgCurrentPage"));
        ret.append("\n</td></tr><tr><td align=center>");
        ret.append("\n<input type=button name=btnPrint onClick=\"wbPrint(this.form)\" value=\""+ paramRequest.getLocaleString("btnPrint") +"\">");
        ret.append("\n</td></tr>");
        ret.append("\n</table>");
        ret.append("\n</td></tr></table>");
        ret.append("\n</form>");     
        response.getWriter().print(ret.toString());
        
//        response.setContentType("text/html");
//         base=getResourceBase();
//        if (!"".equals(base.getAttribute("template", "").trim()))
//        {
//            try
//            {
//                Template tpl = TemplateMgr.getInstance().getTemplate(base.getTopicMapId(), Integer.parseInt(base.getAttribute("template").trim()));
//                if(tpl!=null) tpl.build(request, response, response.getWriter(), reqParams.getUser(), reqParams.getTopic(),true,ret.toString());
//            } 
//            catch(Exception e) { AFUtils.log(e,"Error while getting topic to print: "+base.getId() +"-"+ base.getTitle(),true); }
//        }        
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void printContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        response.setContentType("text/html");
        Portlet base=getResourceBase();
        if (!"".equals(base.getAttribute("template", "").trim()))
        {
            try
            {
                Template tpl =paramRequest.getTopic().getWebSite().getTemplate(base.getAttribute("template").trim());
                if(request.getParameter("page")!=null) {
                    request.setAttribute("page",request.getParameter("page"));
                } 
                if(tpl!=null) {
                    ((TemplateImp)tpl).build(request, response, paramRequest.getUser(), paramRequest.getTopic());
                }
            } 
            catch(Exception e) { log.error("Error while getting topic to print: "+base.getId() +"-"+ base.getTitle(), e); }
        }
    }
    
    private void setWindowConf()
    {
        Portlet base=getResourceBase();
        this.windowconf  = "menubar="+ base.getAttribute("menubar", "no").trim();
        this.windowconf += ",toolbar="+ base.getAttribute("toolbar", "no").trim();
        this.windowconf += ",status="+ base.getAttribute("status", "no").trim();
        this.windowconf += ",location="+ base.getAttribute("location", "no").trim();
        this.windowconf += ",directories="+ base.getAttribute("directories", "no").trim();
        this.windowconf += ",scrollbars="+ base.getAttribute("scrollbars", "no").trim();
        this.windowconf += ",resizable="+ base.getAttribute("resizable", "no").trim();
        this.windowconf += ",width="+ base.getAttribute("width", "640").trim();
        this.windowconf += ",height="+ base.getAttribute("height", "480").trim();
        this.windowconf += ",top="+ base.getAttribute("top", "10").trim();
        this.windowconf += ",left="+ base.getAttribute("left", "10").trim();          
    }
    
    /**
     * @return <b>String</b>
     */  
    private String getWindowConf()
    {
        return this.windowconf;
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        Portlet base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String msg=paramRequest.getLocaleString("msgUndefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        
        if(action.equals("add") || action.equals("edit"))  ret.append(getForm(request, paramRequest));
        else if(action.equals("update"))
        {
            FileUpload fup = new FileUpload();
            try
            {
                fup.getFiles(request, response);
                String value = null != fup.getValue("template") && !"".equals(fup.getValue("template").trim()) ? fup.getValue("template").trim() : null;
                if (value!=null)
                {
                    base.setAttribute("template", value);
                    value = null != fup.getValue("noimg") && !"".equals(fup.getValue("noimg").trim()) ? fup.getValue("noimg").trim() : "0";
                    if ("1".equals(value) && !"".equals(base.getAttribute("img", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(workPath + "/" + base.getAttribute("img").trim());
                        base.removeAttribute("img");
                    }
                    else
                    {
                        value = null != fup.getFileName("img") && !"".equals(fup.getFileName("img").trim()) ? fup.getFileName("img").trim() : null;
                        if (value!=null)
                        {
                            String file = admResUtils.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(file, "bmp|jpg|jpeg|gif")){
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>bmp, jpg, jpeg, gif</i>: " + file;
                                }
                                else
                                {
                                    if (admResUtils.uploadFile(base, fup, "img")){
                                        base.setAttribute("img", file);
                                    }
                                    else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }
                            else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        } 
                    } 

                    setAttribute(base, fup, "alt");
                    setAttribute(base, fup, "btntexto");
                    setAttribute(base, fup, "lnktexto");
                    setAttribute(base, fup, "blnstyle");
                    setAttribute(base, fup, "menubar", "yes");
                    setAttribute(base, fup, "toolbar", "yes");
                    setAttribute(base, fup, "status", "yes");
                    setAttribute(base, fup, "location", "yes");
                    setAttribute(base, fup, "directories", "yes");
                    setAttribute(base, fup, "scrollbars", "yes");
                    setAttribute(base, fup, "resizable", "yes");
                    setAttribute(base, fup, "width");
                    setAttribute(base, fup, "height");
                    setAttribute(base, fup, "top");
                    setAttribute(base, fup, "left");

                    base.updateAttributesToDB();                           
                    msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();
                    ret.append(
                        "<script language=\"JavaScript\">\n"+
                        "   alert('"+msg+"');\n"+
                        "   location='"+paramRequest.getRenderUrl().setAction("edit").toString()+"';\n"+
                        "</script>\n"); 
                }
                else msg=paramRequest.getLocaleString("msgTemplateRequired");
            } 
            catch(Exception e) { log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId(); }
        }
        else if(action.equals("remove")) 
        {
            msg=admResUtils.removeResource(base);  
            ret.append(
                "<script language=\"JavaScript\">\n"+
                "   alert('"+msg+"');\n"+
                "</script>\n");             
        }
        response.getWriter().print(ret.toString());
    }
  
    /**
     * @param base
     * @param fup
     * @param att
     */  
    protected void setAttribute(Portlet base, FileUpload fup, String att)
    {
        try
        {
            if(null != fup.getValue(att) && !"".equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else base.removeAttribute(att);        
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }
    
    /**
     * @param base
     * @param fup
     * @param att
     * @param value
     */  
    protected void setAttribute(Portlet base, FileUpload fup, String att, String value)
    {
        try
        {
            if(null != fup.getValue(att) && value.equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else {
                base.removeAttribute(att);
            }        
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }    
    
    /**
     * @param request
     * @param paramsRequest
     * @return <b>String</b>
     */    
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        Portlet base=getResourceBase();
        StringBuffer ret=new StringBuffer("");
        try
        {
            SWBResourceURL url = paramsRequest.getRenderUrl().setAction("update");  
            ret.append("<form name=\"frmResource\" method=\"post\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> \n");            
            ret.append("<div class=box>");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append("<font style=\"color: #428AD4; text-decoration: none; font-family: Verdana; font-size: 12px; font-weight: normal;\">");
            ret.append(paramsRequest.getLocaleString("msgStep1") +"</font>");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">* "+paramsRequest.getLocaleString("msgTemplate")+" (xsl, xslt):</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("\n<select name=template>");
            ret.append("\n<option value=\"\">" + paramsRequest.getLocaleString("msgOption") + "</option>");
            Iterator <Template> itTpl=paramsRequest.getTopic().getWebSite().listTemplates();
            while (itTpl.hasNext())
            {
                Template tpl = itTpl.next();
                ret.append("\n<option value=\"" + tpl.getId() + "\"");
                if (String.valueOf(tpl.getId()).equals(base.getAttribute("template", "").trim())) {
                    ret.append(" selected");
                }
                ret.append(">" + tpl.getTitle() + "</option>");
            }
            ret.append("\n</select>");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgImage")+" (bmp, gif, jpg, jpeg):</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"file\" name=\"img\" size=\"40\" onClick=\"this.form.btntexto.value=''; this.form.lnktexto.value=''\" onChange=\"isFileType(this, 'bmp|jpg|jpeg|gif');\"/>");
            if (!"".equals(base.getAttribute("img", "").trim())) {
                ret.append("<p>"+admResUtils.displayImage(base, base.getAttribute("img").trim(), "img") +"<input type=checkbox name=noimg value=1>" + paramsRequest.getLocaleString("msgCutImage") + " <i>" + base.getAttribute("img").trim() + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgAlt")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=50 name=alt ");
            if (!"".equals(base.getAttribute("alt", "").trim()))  {
                ret.append(" value=\"" + base.getAttribute("alt").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgButton")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=50 name=btntexto ");
            if (!"".equals(base.getAttribute("btntexto", "").trim()))  {
                ret.append(" value=\"" + base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgLink")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=50 name=lnktexto ");
            if (!"".equals(base.getAttribute("lnktexto", "").trim()))  {
                ret.append(" value=\"" + base.getAttribute("lnktexto").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgStyle")+"</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=50 name=blnstyle ");
            if (!"".equals(base.getAttribute("blnstyle", "").trim()))  {
                ret.append(" value=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");            
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append("<br><br><font style=\"color: #428AD4; text-decoration: none; font-family: Verdana; font-size: 12px; font-weight: normal;\">");
            ret.append(paramsRequest.getLocaleString("msgStep2")+"</font>");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append(admResUtils.loadWindowConfiguration(base, paramsRequest));
            ret.append("<tr> \n");
            ret.append("<td colspan=2 align=right> \n");
            ret.append("<br><hr size=1 noshade> \n");
            ret.append("<input type=submit name=btnSave value=" + paramsRequest.getLocaleString("btnSubmit") + " onClick=\"if(jsValida(this.form)) return true; else return false;\" class=boton>&nbsp;");
            ret.append("<input type=reset name=btnReset value=" + paramsRequest.getLocaleString("btnReset") + " class=boton> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td colspan=2 class=datos><br><font style=\"color: #428AD4; font-family: Verdana; font-size: 10px;\"> \n");
            ret.append("* " + paramsRequest.getLocaleString("msgDataRequired"));
            ret.append("</font></td> \n");
            ret.append("</tr> \n");
            ret.append("</table> \n");
            ret.append("</div> \n");
            ret.append("</form> \n");
            ret.append(getScript(request, paramsRequest));
        }
        catch(Exception e) {  log.error(e); }
        return ret.toString();
    }       

    /**
     * @param request
     * @param paramsRequest
     * @return <b>String</b>
     */      
    private String getScript(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuffer ret = new StringBuffer("");
        try
        {
            ret.append("\n<script>");
            ret.append("\nfunction jsValida(pForm)");
            ret.append("\n{");
            ret.append("\n   if(pForm.template.selectedIndex==0 || pForm.template.options[pForm.template.selectedIndex].value=='' || pForm.template.options[pForm.template.selectedIndex].value==' ')");
            ret.append("\n   {");
            ret.append("\n      alert('" + paramsRequest.getLocaleString("msgTemplateRequired") + "');");
            ret.append("\n      pForm.template.focus();");
            ret.append("\n      return false;");
            ret.append("\n   }");
            ret.append("\n   if(!isFileType(pForm.img, 'bmp|jpg|jpeg|gif')) return false;");
            ret.append("\n   if(!isNumber(pForm.width)) return false;");
            ret.append("\n   if(!isNumber(pForm.height)) return false;");
            ret.append("\n   if(!isNumber(pForm.top)) return false;");
            ret.append("\n   if(!isNumber(pForm.left)) return false;");
            ret.append("\n   return true;");
            ret.append("\n}");
            ret.append(admResUtils.loadIsFileType());
            ret.append(admResUtils.loadIsNumber());
            ret.append("\n</script>");    
        }
        catch(Exception e) {  log.error(e); }
        return ret.toString();
    }
 }

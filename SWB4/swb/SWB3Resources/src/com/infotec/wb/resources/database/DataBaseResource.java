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


package com.infotec.wb.resources.database;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.math.BigInteger;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.FileUpload;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.IDGenerator;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.WBFileUpload;
import org.semanticwb.util.Encryptor;
import org.w3c.dom.*;


/**
 * Recurso que permite realizar modificaci�n y consulta a una base de datos.
 *
 * @author Infotec
 * @version 1.1
 */
public class DataBaseResource extends GenericResource 
{
    private static Logger log = SWBUtils.getLogger(DataBaseResource.class);
    protected javax.xml.transform.Templates tpl;
    private javax.xml.transform.Templates tplq; 
    private HashMap args;       // Argumentos del recurso.
    String webWorkPath = "/work";
    private WBAdmResourceUtils resUtil = new WBAdmResourceUtils();
    
    public DataBaseResource() {
    }

    //HashMap arguments=new HashMap();
	
    /**
     * @param base
     */ 
    @Override
    public void setResourceBase(Resource base)
    {
        try 
        { 
            super.setResourceBase(base); 
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(),e);  }
        String queryType=base.getAttribute("queryType","");
        if ("consulta".equals(queryType))
        {
            String format=base.getAttribute("format", "2").trim();
            if ("2".equals(format) && !"".equals(base.getAttribute("filex", "").trim()))
            {
                try 
                {
                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("filex")));
                    //tpl = SWBUtils.XML.loadTemplateXSLT(SWBUtils.IO.getStreamFromString(SWBUtils.IO.getFileFromPath(base.getWorkPath() +"/"+ base.getAttribute("filex"))));
                    base.setAttribute("path", SWBPlatform.getWebWorkPath() +  base.getWorkPath() + "/");
                }
                catch(Exception e) 
                { 
                    base.setAttribute("format", "1");  format="1";
                    log.error("Error while loading resource: "+base.getId() +"-"+ base.getTitle(),e);
                }
            }
            if ("1".equals(format))
            {
                try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/DataBaseResource/DataBaseResource_row.xslt")); }
                catch(Exception e) { log.error("Error while loading default resource template by row: "+base.getId() +"-"+ base.getTitle(),e); }
            }            
            else if ("0".equals(format))
            {
                try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/DataBaseResource/DataBaseResource_col.xslt")); }
                catch(Exception e) { log.error("Error while loading default resource template by column: "+base.getId() +"-"+ base.getTitle(),e); }
            }
        }
        else if("insert".equals(queryType) || "update".equals(queryType) || "search".equals(queryType))
        {
            if(!"".equals(base.getAttribute("template","")))
            {

                try 
                { 
                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                    base.setAttribute("path",  SWBPlatform.getWebWorkPath() +  base.getWorkPath() + "/");
                }
                catch(Exception e) { log.error("Error while loading resource: "+base.getId() +"-"+ base.getTitle(),e); }
            }      
            else
            {
                try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/DataBaseResource/DataBaseResource_"+queryType+".xslt")); }
                catch(Exception e) { log.error("Error while loading default resource template by column: "+base.getId() +"-"+ base.getTitle(),e); }
            }
            if ("search".equals(queryType) && !"".equals(base.getAttribute("tplq", ""))) {
                try {
                    tplq = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("tplq").trim()));
                }catch(Exception e) { 
                    log.error("Error while loading default resource query template : "+base.getId() +"-"+ base.getTitle(),e);
                }
            }else {
                try {
                    tplq = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/DataBaseResource/DataBaseResource_query.xslt"));
                }catch(Exception e) { 
                    log.error("Error while loading default resource query template : "+base.getId() +"-"+ base.getTitle(),e);
                }
            }
        }
        if ("".equals(base.getAttribute("path", "").trim())) base.setAttribute("path", SWBPlatform.getContextPath() + "swbadmin/xsl/DataBaseResource/");
    }


    
    /**
     * Obtiene detalles para la administracion del recurso.
     *
     * @return <b>String</b> HTML de administracion del recurso.
     * 
     * @param request Request asociado a la peticion.
     * @param response Response asociado a la respuesta al cliente.
     * @param paramRequest Parametros asociados a la peticion.
     * 
     * @throws <b>AFException, IOException</b> si ocurre error al administrar el recurso.
     */

     @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        StringBuffer ret = new StringBuffer("");
        Resource base = getResourceBase();
        
        String action = paramRequest.getAction();
        if ("add".equals(action) || "edit".equals(action)) ret.append(getAdminForm(request, paramRequest));
        else if ("type".equals(action)) 
        {
            String queryType="";
            if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                queryType=base.getAttribute("queryType","");
                try 
                {
                    WBFileUpload requestparams = new WBFileUpload();
                    requestparams.getFiles(request);
                    String value=acquireValue(requestparams, "table");
                    if(!"".equals(value)) base.setAttribute("table", value);
                    
                    if (!"consulta".equals(queryType) && !"delete".equals(queryType))
                        setDomMetaData(requestparams, paramRequest);
                }
                catch (Exception e) { log.error(e);}
            }
            else
            {
                if(null != request.getParameter("dbcon") && !"".equals(request.getParameter("dbcon").trim()))
                    base.setAttribute("dbcon", request.getParameter("dbcon").trim());

                queryType = null != request.getParameter("queryType") && !"".equals(request.getParameter("queryType").trim()) ? request.getParameter("queryType").trim() : "";
                if (!"".equals(queryType)) 
                {
                    if(!queryType.equals(base.getAttribute("queryType","")))
                    {
                        base.removeAttribute("filex");
                        base.removeAttribute("template");
                        base.removeAttribute("query");
                        base.removeAttribute("segment");
                        base.removeAttribute("format");
                        base.removeAttribute("table");
                        base.removeAttribute("namekey");
                        base.removeAttribute("sync");
                    }
                    base.setAttribute("queryType", queryType);
                }
                else queryType=base.getAttribute("queryType");
            }
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("update");
            ret.append("<form name=\"frmResource\" enctype=\"multipart/form-data\" onsubmit=\"if(!validate()) return false;\" method=\"POST\" action=\"" + url.toString() + "\">\n");
            ret.append("<div class=\"swbform\"> \n");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \n");

            if ("consulta".equals(queryType))
                ret.append(getConsultaForm(request, paramRequest));
            else if ("insert".equals(queryType))
                ret.append(getInsertForm(request, paramRequest));
            else if ("update".equals(queryType))
                ret.append(getUpdateForm(request, paramRequest));
            else if ("delete".equals(queryType))
                ret.append(getDeleteForm(request, paramRequest));
            else if ("search".equals(queryType))
                ret.append(getSearchForm(request, paramRequest));
            
            ret.append("<tr> \n");
            ret.append("<td colspan=2 align=right> \n");
            ret.append("<br><hr size=1 noshade> \n");
            ret.append("<input type=submit name=btnSave value=\"" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_btnSend") + "\" class=boton> \n");
            url.setAction("edit");
            ret.append("<input type=button name=btnBack onClick=\"location='"+url.toString()+"'\"; value=\"" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_btnBack") + "\" class=boton> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr>");
            ret.append("<td colspan=2 align=left><br>");
            ret.append("<font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#428AD4\" size=\"1\"><b>");
            ret.append("* " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgRequired") + "</b>");
            if ("consulta".equals(queryType))
            {
                ret.append("<BR><BR><b>" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgArgs") + ":</b><BR>");
                ret.append("{userid} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgIdUser") + "<BR>");
                ret.append("{getUserAttribute(x)} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgReturnUserAtrib") + "<BR>");
                ret.append("{topicmap} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgIdSite") + "<BR>");
                ret.append("{topicid} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgIdSection") + "<BR>");
                ret.append("{getArgument(x)} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgReturnArgumentTemplate") + "<BR>");
                ret.append("{setArgument(x)} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSetArgumentTemplate") + "<BR>");
                ret.append("{getParameter(x)} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgReturnArgumentRequest") + "<BR>");
                ret.append("{getParamSearch(x)} - " + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgReturnSearchArgumentRequest"));
            }
            ret.append("<BR></font>");
            ret.append("</td>");
            ret.append("</tr>");
            ret.append("</table> \n");
            ret.append("</div> \n");
            ret.append("</form> \n");
            ret.append("<script> \n");
            ret.append(resUtil.loadTrim());
            ret.append(resUtil.loadIsFileType());
            ret.append("\n</script> \n");            
        } 
        else if ("update".equals(action)) 
        {
            String applet=null;
            try 
            {
                WBFileUpload requestparams = new WBFileUpload();
                requestparams.getFiles(request);
                String queryType = base.getAttribute("queryType","");
                boolean redirect=false;
                if (!"delete".equals(queryType)) 
                {
                    if (!"consulta".equals(queryType))
                        setDomMetaData(requestparams, paramRequest);
                    String value = acquireValue(requestparams, "nofilex");
                    if ("1".equals(value) && !"".equals(base.getAttribute("filex", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + base.getWorkPath() + "/" + base.getAttribute("filex").trim());
                        base.removeAttribute("filex");                        
                        redirect=true;
                    }
                    else
                    {
                        value = null != requestparams.getFileName("filex") && !"".equals(requestparams.getFileName("filex").trim()) ? requestparams.getFileName("filex").trim() : null;
                        if (value!=null)
                        {   
                            String file = resUtil.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!resUtil.isFileType(file, "xsl|xslt|xml"))
                                    base.setAttribute("msgadmin", paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgNoExtensionSopported") +" <i>xsl, xslt, xml</i>: " + file +".");
                                else
                                {
                                    if ("consulta".equals(queryType)) 
                                    {
                                        applet = resUtil.uploadFileParsed(base, requestparams, "filex", request.getSession().getId());
                                        if (applet != null && !applet.trim().equals(""))
                                            base.setAttribute("filex", file);
                                        else base.setAttribute("msgadmin", paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                                    }
                                    else
                                    {
                                        //if (WBResourceUtils.getInstance().uploadFile(base, fUpload, "filex"))
                                        if (resUtil.uploadFile(base, requestparams, "filex"))
                                            base.setAttribute("filex", file);
                                        else base.setAttribute("msgadmin", paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                                    }
                                }
                            }
                            else base.setAttribute("msgadmin", paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                        }
                    }
                    value = acquireValue(requestparams, "notemplate");
                    if ("1".equals(value) && !"".equals(base.getAttribute("template", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + base.getWorkPath() + "/" + base.getAttribute("template").trim());
                        base.removeAttribute("template");    
                        redirect=true;
                    }
                    else
                    {
                        value = null != requestparams.getFileName("template") && !"".equals(requestparams.getFileName("template").trim()) ? requestparams.getFileName("template").trim() : null;
                        if (value!=null)
                        {
                            String file = resUtil.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!resUtil.isFileType(file, "xsl|xslt"))
                                    base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgNoExtensionSopported") +" <i>xsl, xslt</i>: " + file+".");
                                else
                                {
                                    //applet = WBResourceUtils.getInstance().uploadFileParsed(base, fUpload, "template", request.getSession().getId());
                                    applet = resUtil.uploadFileParsed(base, requestparams, "template", request.getSession().getId());
                                    if (applet != null && !applet.trim().equals(""))
                                        base.setAttribute("template", file);
                                    else base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                                }
                            }
                            else base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                        }                      
                    }
                    if ("search".equals(queryType)) {
                        value = acquireValue(requestparams, "notplq");
                        if ("1".equals(value) && !"".equals(base.getAttribute("tplq", "").trim())) {
                            SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + base.getWorkPath() + "/" + base.getAttribute("tplq").trim());
                            base.removeAttribute("tplq");    
                            redirect=true;
                        }
                        else {
                            value = null != requestparams.getFileName("tplq") && !"".equals(requestparams.getFileName("tplq").trim()) ? requestparams.getFileName("tplq").trim() : null;
                            if (value!=null) {
                                String file = resUtil.getFileName(base, value);
                                if (file != null && !file.trim().equals("")) {
                                    if (!resUtil.isFileType(file, "xsl|xslt"))
                                        base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgNoExtensionSopported") +" <i>xsl, xslt</i>: " + file+".");
                                    else {
                                        applet = resUtil.uploadFileParsed(base, requestparams, "tplq", request.getSession().getId());
                                        if (applet != null && !applet.trim().equals(""))
                                            base.setAttribute("tplq", file);
                                        else 
                                            base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                                    }
                                }
                                else base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgWrongChangeFile") +" <i>" + value + "</i>.");
                            }                      
                        }        
                    }
                }
                if ("consulta".equals(queryType)) 
                {
                    /*setAttribute(base, fUpload, "query");
                    setAttribute(base, fUpload, "segment");
                    setAttribute(base, fUpload, "format");*/
                    setAttribute(base, requestparams, "query");
                    setAttribute(base, requestparams, "segment");
                    if("1".equals(acquireValue(requestparams, "nofilex"))) base.getAttribute("format", "1");
                    else setAttribute(base, requestparams, "format");
                }
                else 
                {
                    //setAttribute(base, fUpload, "table");
                    setAttribute(base, requestparams, "table");
                    if (!"search".equals(queryType)) 
                    {
                        setAttribute(base, requestparams, "sync", "on");
                        TableGeneric tbl = new TableGeneric(base.getAttribute("dbcon",""));
                        String nameKey = tbl.getKey(base.getAttribute("table",""));
                        base.setAttribute("namekey", nameKey);
                    }
                }
                base.updateAttributesToDB(); //paramRequest.getUser().getId(), "Resource with identifier "+base.getId()+" was updated successfully ");
                //base.getRecResource().update(paramRequest.getUser().getId(), "Resource with identifier "+base.getId()+" was updated successfully ");
                if(redirect) 
                {
                    SWBResourceURL url=paramRequest.getRenderUrl();
                    url.setMode(paramRequest.Mode_ADMIN);
                    url.setAction("type");
                    response.sendRedirect(url.toString());
                }
                else
                {
                    if(applet!=null && !"".equals(applet.trim())) ret.append(applet);
                    else ret.append(getAdminResume(request, paramRequest));
                }
            }
            catch(Exception e) 
            { 
                log.error(paramRequest.getLocaleString("error_DataBaseResource_doAdmin_msgErrorActiveResource"), e);
                base.setAttribute("msgadmin", base.getAttribute("msgadmin","")+"<br>"+paramRequest.getLocaleString("error_DataBaseResource_doAdmin_msgErrorActiveResource") +" " + base.getId() + ".");
            }
        }
        else if ("remove".equals(action)) {
            ret.append(resUtil.removeResource(base));
        }
        response.getWriter().print(ret.toString());
    }

    private String getAdminResume(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer("");
        Resource base=getResourceBase();    
        
        try
        {
            ret.append("<div class=\"swbform\"> \n");
            ret.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\"> \n");
            ret.append("<tr> \n");
            ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
            ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgResourceUpdateQueryType"));
            ret.append("</td><td class=\"valores\"> \n");
            String queryType = base.getAttribute("queryType","");
            if("consulta".equals(queryType))  ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgQuery"));
            else if("insert".equals(queryType)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgInsert"));
            else if("update".equals(queryType)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgUpdate"));
            else if("delete".equals(queryType)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgDelete"));
            else if("search".equals(queryType)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSearch"));
            ret.append("</td></tr> \n");
            ret.append("<tr> \n");
            ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
            ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgDataBaseName"));
            ret.append("</td><td class=\"valores\"> \n");
            ret.append(base.getAttribute("dbcon",""));
            ret.append("</td></tr> \n");            
            String value=base.getAttribute("query", "").trim();
            if(!"".equals(value))
            {
                ret.append("<tr> \n");
                ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgUpdateResourceNextQuery"));
                ret.append("</td><td class=\"valores\"> \n");
                ret.append(formatQuery(value));
                ret.append("</td></tr> \n");
            }
            value=base.getAttribute("table", "").trim();
            if(!"".equals(value)) {
                if (!"search".equals(base.getAttribute("queryType"))) {
                    ret.append("<tr> \n");
                    ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableUpdate"));
                    ret.append("</td><td class=\"valores\"> \n");
                    ret.append(value);
                    ret.append("</td></tr> \n");
                }else {
                    ret.append("<tr> \n");
                    ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableSearch"));
                    ret.append("</td><td class=\"valores\"> \n");
                    ret.append(value);
                    ret.append("</td></tr> \n");
                }
            }
            value=base.getAttribute("namekey", "").trim();
            if(!"".equals(value))
            {        
                ret.append("<tr> \n");
                ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgKeyUpdate"));
                ret.append("</td><td class=\"valores\"> \n");
                ret.append(value);
                ret.append("</td></tr> \n");
            }
            if("consulta".equals(queryType))
            {
                value=base.getAttribute("format", "2");
                ret.append("<tr> \n");
                ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgPathFileXslt"));
                ret.append("</td><td class=\"valores\"> \n");
                if("2".equals(value))
                {
                    if(tpl!=null && !"".equals(base.getAttribute("filex","").trim())) ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("filex") +"\">" + base.getAttribute("filex") + "</a>");
                    else { base.setAttribute("format", "1"); value="1"; }
                }
                if("1".equals(value)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgByRow"));
                else if("0".equals(value)) ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgByColumn"));
                ret.append("</td></tr> \n");
                ret.append("<tr> \n");
                ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgRows"));
                ret.append("</td><td class=\"valores\"> \n");
                if(!"".equals(base.getAttribute("segment", "").trim())) ret.append(base.getAttribute("segment"));
                else ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgAll"));
                ret.append("</td></tr> \n");
            }
            else
            {
                value=base.getAttribute("filex", "").trim();
                if(!"".equals(value))
                {        
                    ret.append("<tr> \n");
                    ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgInsertDefXml"));
                    ret.append("</td><td class=\"valores\"> \n");
                    ret.append("<a href=\""+ webWorkPath +"/"+ value +"\">" + value + "</a>");
                    ret.append("</td></tr> \n");
                }
                value=base.getAttribute("template", "").trim();
                if(!"".equals(value))
                {        
                    ret.append("<tr> \n");
                    ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgConfigXsl") + ":");
                    ret.append("</td><td class=\"valores\"> \n");
                    ret.append("<a href=\""+ webWorkPath +"/"+ value +"\">" + value + "</a>");
                    ret.append("</td></tr> \n"); 
                }
                if ("search".equals(base.getAttribute("queryType"))) {
                    value=base.getAttribute("tplq", "").trim();
                    if(!"".equals(value)) {        
                        ret.append("<tr> \n");
                        ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                        ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgConfigXslQuery") + ":");
                        ret.append("</td><td class=\"valores\"> \n");
                        ret.append("<a href=\""+ webWorkPath +"/"+ value +"\">" + value + "</a>");
                        ret.append("</td></tr> \n"); 
                    }
                }
            }
            if ("on".equals(base.getAttribute("sync",""))) {
                if ("on".equals(getIntegrityMsg(paramRequest))) {
                    ret.append("<tr> \n");
                    ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSync"));
                    ret.append("</td><td class=\"valores\"> \n");
                    ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgActive"));
                    ret.append("</td></tr> \n");
                }else {
                    if (!"".equals(getIntegrityMsg(paramRequest))) {
                        ret.append("<tr> \n");
                        ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                        ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSync"));
                        ret.append("</td><td class=\"valores\"> \n");
                        ret.append(getIntegrityMsg(paramRequest));
                        ret.append("</td></tr> \n");
                    }else {
                        ret.append("<tr> \n");
                        ret.append("<td width=\"200\" align=\"right\" valign=\"top\" class=\"datos\"> \n");
                        ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSync"));
                        ret.append("</td><td class=\"valores\"> \n");
                        ret.append(paramRequest.getLocaleString("usrmsg_DataBaseResource_doGetAdmResume_msgSemanticExist") + " " + base.getAttribute("table",""));
                        ret.append("</td></tr> \n");
                    }
                }
            }
            value=base.getAttribute("msgadmin", "").trim();
            if(!"".equals(value))
            {
                ret.append("<tr> \n");
                ret.append("<td align=\"justify\" valign=\"top\" class=\"datos\" colspan=\"2\"> \n");
                ret.append(value);
                ret.append("</td></tr> \n");
            }              
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");
            ret.append("<tr> \n");
            ret.append("<td colspan=2 align=right> \n");
            ret.append("<br><hr size=1 noshade> \n");
            ret.append("<form method=\"POST\" action=\"" + url.toString() + "\"> \n");
            ret.append("<input type=hidden name=dbcon  value=\""+base.getAttribute("dbcon","")+"\">");
            ret.append("<input type=hidden name=queryType  value=\""+base.getAttribute("queryType","")+"\">");
            ret.append("<input type=submit name=btnBack value=\"" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_btnBack") + "\" class=boton> \n");
            ret.append("<form> \n");
            ret.append("</td></tr> \n");
            ret.append("</table> \n");
            ret.append("</div> \n");
            base.removeAttribute("msgadmin");
            
        }
        catch(Exception e) { log.error("Error while generating administration resume:getAdminResume() in resource "+base.getId()+".", e); }
        return ret.toString();
    }
    
    protected String formatQuery(String query)
    {
        String[] reserved=new String[]{"SELECT ", " FROM ", " WHERE ", " AND ", " OR ", " GROUP BY ", " ORDER BY "};
        for (int i = 0; i < reserved.length; i++) 
        {
            String word=(String)reserved[i];
            query=query.replaceAll(word.toLowerCase(),"<br>"+word);
        }
        query=query.replaceAll(" distinct ", " DISTINCT ");
        query=query.replaceAll("\\{setArgument","<br>{setArgument");
        return query;
    }
    
    /**
     * @param base
     * @param fup
     * @param att
     */  
    protected void setAttribute(Resource base, FileUpload fup, String att)
    {
        try
        {
            if(null != fup.getValue(att) && !"".equals(fup.getValue(att).trim())) base.setAttribute(att, fup.getValue(att).trim());
            else base.removeAttribute(att);        
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }
    
    /**
     * @param base
     * @param fup
     * @param att
     */  
    protected void setAttribute(Resource base, WBFileUpload fup, String att)
    {
        try
        {
            if (null != fup.getValue(att) && !"".equals(((String)fup.getValue(att).get(0)).trim())) base.setAttribute(att, ((String)fup.getValue(att).get(0)).trim());
            else base.removeAttribute(att);
        }
        catch(Exception e) {  log.error( "Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }
    
    /**
     * @param base
     * @param fup
     * @param att
     * @param value
     */  
    protected void setAttribute(Resource base, FileUpload fup, String att, String value)
    {
        try
        {
            if(null != fup.getValue(att) && value.equals(fup.getValue(att).trim())) base.setAttribute(att, fup.getValue(att).trim());
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
    protected void setAttribute(Resource base, WBFileUpload fup, String att, String value)
    {
        try
        {
            if(null != fup.getValue(att) && value.equals(((String)fup.getValue(att).get(0)).trim())) base.setAttribute(att, ((String)fup.getValue(att).get(0)).trim());
            else base.removeAttribute(att);        
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }
    
    /**
     * @param request
     * @param paramRequest
     */       
    private String getAdminForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Resource base=getResourceBase();
        try
        {
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");
            ret.append("<form method=\"POST\" action=\"" + url.toString() + "\"> \n");
            ret.append("<div class=swbform> \n");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgDataBaseName")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<select size=1 name=\"dbcon\"> \n");
            try 
            { 
                String poolName = new String();
                Properties dbProps = new Properties();
                dbProps.load(getClass().getResourceAsStream("/db.properties")); 
                
                Enumeration propNames = dbProps.propertyNames();
                while (propNames.hasMoreElements()) 
                {
                    String name = (String)propNames.nextElement();
                    if (name.endsWith(".url")) 
                    {
                        poolName = name.substring(0, name.lastIndexOf("."));
                        ret.append("<option value=\""+poolName+"\"");
                        if (poolName.equals(base.getAttribute("dbcon",""))) ret.append(" selected");
                        ret.append(">"+poolName+"</option> \n");
                    }
                }                
            }
            catch (Exception e) { log.error(paramRequest.getLocaleString("error_DataBaseResource_doAdmin_msgErrorFindProperties"), e); }
            ret.append("</select>\n");
            ret.append("</td>");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgQueryType")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<select size=1 name=\"queryType\"> \n");
            String queryType = base.getAttribute("queryType","").trim();
            ret.append("<option value=\"consulta\"");
            if("consulta".equals(queryType)) ret.append(" selected");
            ret.append(">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgQuery") + "</option> \n");
            ret.append("<option value=\"update\"");
            if("update".equals(queryType)) ret.append(" selected");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgUpdate") + "</option> \n");
            ret.append("<option value=\"insert\"");
            if("insert".equals(queryType)) ret.append(" selected");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgInsert") + "</option> \n");
            ret.append("<option value=\"delete\"");
            if("delete".equals(queryType)) ret.append(" selected");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgDelete") + "</option> \n");
            ret.append("<option value=\"search\"");
            if("search".equals(queryType)) ret.append(" selected");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSearch") + "</option> \n");
            ret.append("</select> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td colspan=2 align=right> \n");
            ret.append("<br><hr size=1 noshade> \n");
            ret.append("<input type=submit name=btnSave value=\"" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_btnSend") + "\" class=boton> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n"); 
            ret.append("</table> \n");
            ret.append("</div> \n");
            ret.append("</form> \n");
        }
        catch(Exception e) {  log.error( "Error while generating administration form:getAdminForm() in resource "+base.getId()+".", e); }
        return ret.toString();
    }                
	
    /**
     * @param request
     * @param paramRequest
     */       
    private String getConsultaForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Resource base=getResourceBase();
        try
        {
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">* "+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgQueryLabel")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<textarea rows=5 cols=39 name=\"query\">");
            if(!"".equals(base.getAttribute("query","").trim())) ret.append(base.getAttribute("query").trim());
            ret.append("</textarea> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n");            
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgFormat")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            String value=base.getAttribute("format", "2").trim();
            if("2".equals(value) && (tpl==null || "".equals(base.getAttribute("filex","").trim()))) { base.setAttribute("format", "1"); value="1";}
                
            ret.append("\n<input type=radio name=format value=1 ");
            if ("1".equals(value)) ret.append(" checked");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgByRow") +" ");
            ret.append("\n<input type=radio name=format value=0 ");
            if ("0".equals(value)) ret.append(" checked");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgByColumn") + " ");
            ret.append("\n<input type=radio name=format value=2 ");
            if ("2".equals(value)) ret.append(" checked");
            ret.append(">" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgByTemplate"));
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgPathFileXslt") + "<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xsl, xslt):</font></td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<input type=file size=40 name=filex onChange=\"isFileType(this, 'xsl|xslt');\" onClick=\"document.frmResource.format[2].checked=true;\"> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n"); 
            if ("2".equals(value))
            {
                ret.append("<tr><td></td> \n");
                ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("filex") +"\">" + base.getAttribute("filex") + "</a>");
                ret.append("</td></tr> \n");
                ret.append("<tr><td></td> \n");
                ret.append("<td class=\"valores\"><input type=checkbox name=nofilex value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("filex") + "</i>");
                ret.append("</td></tr> \n");                
            }
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgResult")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<select name=\"segment\"> \n");
            ret.append("<option value=\"\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgAll")+"</option>");
            ret.append("<option value=\"1\"");
            value = base.getAttribute("segment","");
            if("1".equals(value))ret.append(" selected");
            ret.append(">01</option> \n");
            ret.append("<option value=\"2\"");
            if("2".equals(value)) ret.append(" selected");
            ret.append(">02</option> \n");
            ret.append("<option value=\"3\"");
            if("3".equals(value)) ret.append(" selected");
            ret.append(">03</option> \n");
            ret.append("<option value=\"4\"");
            if("4".equals(value)) ret.append(" selected");
            ret.append(">04</option> \n");
            ret.append("<option value=\"5\"");
            if("5".equals(value)) ret.append(" selected");
            ret.append(">05</option> \n");
            ret.append("<option value=\"10\"");
            if("10".equals(value)) ret.append(" selected");
            ret.append(">10</option> \n");
            ret.append("<option value=\"15\"");
            if("15".equals(value)) ret.append(" selected");
            ret.append(">15</option> \n");
            ret.append("<option value=\"20\"");
            if("20".equals(value)) ret.append(" selected");
            ret.append(">20</option> \n");
            ret.append("<option value=\"30\"");
            if("30".equals(value)) ret.append(" selected");
            ret.append(">30</option> \n");
            ret.append("</select> \n");
            ret.append(" "+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgRows")+"\n");
            ret.append("</td>");
            ret.append("</tr>");    
            
            ret.append("<script> \n");
            ret.append("\nfunction validate()");
            ret.append("\n{");
            //String patern = "/^\\s*(\\S*(\\s+\\S+)*)\\s*$/";
            //ret.append("\n   document.frmResource.query.value=document.frmResource.query.value.replace("+ patern + ", \"\");");
            ret.append("\n   replaceChars(document.frmResource.query);");
            ret.append("\n   trim(document.frmResource.query);");
            ret.append("\n   if(document.frmResource.query.value=='')");
            ret.append("\n   {");
            ret.append("\n       alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgQueryRequired") + "');");
            ret.append("\n       document.frmResource.query.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            if ("".equals(base.getAttribute("filex","").trim()))
            {
                ret.append("\n   if(document.frmResource.format[2].checked && document.frmResource.filex.value=='')");
                ret.append("\n   {");
                ret.append("\n       alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTemplateRequired") + "');");
                ret.append("\n       document.frmResource.filex.focus();");
                ret.append("\n       return false;");
                ret.append("\n   }");
            } 
            ret.append("\n   if(!isFileType(document.frmResource.filex, 'xsl|xslt')) return false;");
            ret.append("\n   return true;");
            ret.append("\n}");
            ret.append("\nfunction replaceChars(pIn)");
            ret.append("\n{");
            ret.append("\n   out = \"\\r\"; // replace this");
            ret.append("\n   add = \" \"; // with this");
            ret.append("\n   temp = \"\" + pIn.value; // temporary holder");
            ret.append("\n   while (temp.indexOf(out)>-1)");
            ret.append("\n   {");
            ret.append("\n      pos= temp.indexOf(out);");
            ret.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
            ret.append("\n   }");
            ret.append("\n   out = \"\\n\"; // replace this");
            ret.append("\n   add = \" \"; // with this");
            ret.append("\n   temp = \"\" + temp; // temporary holder");
            ret.append("\n   while (temp.indexOf(out)>-1)");
            ret.append("\n   {");
            ret.append("\n      pos= temp.indexOf(out);");
            ret.append("\n      temp = \"\" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));");
            ret.append("\n   }");
            ret.append("\n   pIn.value = temp;");
            ret.append("\n}");
            ret.append("\n</script> \n");
        }
        catch(Exception e) {  log.error("Error while generating administration form:getConsultaForm() in resource "+base.getId()+".",e); }
        return ret.toString();
    }             
    
    /**
     * @param request
     * @param paramRequest
     */       
    private String getInsertForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Resource base=getResourceBase();
        try
        {
            ret.append(getSearchForm(request, paramRequest));
            if(!"".equals(base.getAttribute("table","")))
            {
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSync") +"</td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=\"checkbox\" name=\"sync\" value=\"on\"");
                if ("on".equals(base.getAttribute("sync","").trim())) ret.append(" checked");
                ret.append("></td>");
                ret.append("</tr>");
            }
        }
        catch(Exception e) {  log.error("Error while generating administration form:getInsertForm() in resource "+base.getId()+".", e); }
        return ret.toString();
    }             
    
    /**
     * @param request
     * @param paramRequest
     */       
    private String getUpdateForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Resource base=getResourceBase();
        try
        {
            ret.append(getDeleteForm(request, paramRequest));
            if (!"".equals(base.getAttribute("table","").trim())) {
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgUpdateDefXml") +"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xml):</font></td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=file size=40 name=filex onChange=\"isFileType(this, 'xml');\"> \n");
                ret.append("</td>");
                ret.append("</tr>");
                if (!"".equals(base.getAttribute("filex","").trim()))
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                    ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("filex").trim() +"\">" + base.getAttribute("filex").trim() + "</a>");
                    ret.append("</td></tr> \n");
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\"><input type=checkbox name=nofilex value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                    ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("filex").trim() + "</i>");
                    ret.append("</td></tr> \n");                
                }
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgConfigXsl") +"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xsl, xslt):</font></td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=file size=40 name=template onChange=\"isFileType(this, 'xsl|xslt');\"> \n");
                ret.append("</td>");
                ret.append("</tr>");
                if (!"".equals(base.getAttribute("template","").trim()))
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                    ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("template").trim() +"\">" + base.getAttribute("template").trim() + "</a>");
                    ret.append("</td></tr> \n");
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\"><input type=checkbox name=notemplate value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                    ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("template").trim() + "</i>");
                    ret.append("</td></tr> \n");
                }
                else
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("msgDefaultTemplate") +" ");
                    ret.append("<a href=\""+ SWBPlatform.getContextPath() + "Swbadmin/xsl/DataBaseResource/DataBaseResource_"+base.getAttribute("queryType","")+".xslt\">DataBaseResource_"+base.getAttribute("queryType","")+".xslt</a>");
                    ret.append("</td></tr> \n");
                }
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgSync") +"</td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=\"checkbox\" name=\"sync\" value=\"on\"");
                if ("on".equals(base.getAttribute("sync","").trim())) ret.append(" checked");
                ret.append("></td>");
                ret.append("</tr>");
            }
            ret.append("<script> \n");
            ret.append("\nfunction validate()");
            ret.append("\n{");
            ret.append("\n   if(document.frmResource.table.options[document.frmResource.table.selectedIndex].value=='')");
            ret.append("\n   {");
            ret.append("\n       alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableRequired") + "');");
            ret.append("\n       document.frmResource.table.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   if(!isFileType(document.frmResource.filex, 'xml')) return false;");
            ret.append("\n   if(!isFileType(document.frmResource.template, 'xsl|xslt')) return false;");
            ret.append("\n   return true;");
            ret.append("\n}");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");    
            ret.append("\nfunction send()");
            ret.append("\n{");
            ret.append("\n   document.frmResource.action='"+url.toString()+"';");
            ret.append("\n   document.frmResource.submit();");
            ret.append("\n}");              
            ret.append("\n</script> \n");            
        }
        catch(Exception e) {  log.error( "Error while generating administration form:getUpdateForm() in resource "+base.getId()+".", e); }
        return ret.toString();
    }  
    
    /**
     * @param request
     * @param paramRequest
     */       
    private String getDeleteForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Resource base=getResourceBase();
        try
        {
            ret.append("<tr> \n");
            if ("search".equals(base.getAttribute("queryType")))
                ret.append("<td class=\"datos\">* "+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableSearch")+"</td> \n");
            else
                ret.append("<td class=\"datos\">* "+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableUpdate")+"</td> \n");
            ret.append("<td class=\"valores\"> \n");
            ret.append("<select size=1 name=\"table\" onChange=\"send();\"> \n");
            ret.append("<option value=\"\">["+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgNotSelected")+"]</option>");
            String value=base.getAttribute("table","").trim();
            TableGeneric tbl = new TableGeneric(base.getAttribute("dbcon",""));
            Iterator it = tbl.getTables();
            while (it.hasNext()) 
            {
                String table = (String)it.next();
                ret.append("<option value=\""+table+"\"");
                if(table.equals(value)) ret.append(" selected");
                ret.append(">"+table+"</option> \n"); 
            }
            ret.append("</select> \n");
            ret.append("</td>");
            ret.append("</tr>");
            if(!"delete".equals(base.getAttribute("queryType","")) && !"".equals(base.getAttribute("table","")) && "".equals(base.getAttribute("filex","").trim()))
                ret.append(getAttributeTable(request, paramRequest));
            if("delete".equals(base.getAttribute("queryType","")))
            {
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode(paramRequest.Mode_ADMIN);
                url.setAction("type");
                ret.append("<script> \n");
                ret.append("\nfunction validate()");
                ret.append("\n{");
                ret.append("\n   if(document.frmResource.table.options[document.frmResource.table.selectedIndex].value=='')");
                ret.append("\n   {");
                ret.append("\n       alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableRequired") + "');");
                ret.append("\n       document.frmResource.table.focus();");
                ret.append("\n       return false;");
                ret.append("\n   }");
                ret.append("\n   return true;");
                ret.append("\n}");
                ret.append("\nfunction send()");
                ret.append("\n{");
                ret.append("\n   document.frmResource.action='"+url.toString()+"';");
                ret.append("\n   document.frmResource.submit();");
                ret.append("\n}");
                ret.append("\n</script> \n");
            }
        }
        catch(Exception e) {  log.error("Error while generating administration form:getDeleteForm() in resource "+base.getId()+".", e); }
        return ret.toString();
    }      
   
    /**
     * @param request
     * @param paramRequest
     */       
    private String getSearchForm(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        Resource base=getResourceBase();
        StringBuffer ret=new StringBuffer("");
        try
        {
            ret.append(getDeleteForm(request, paramRequest));
            if(!"".equals(base.getAttribute("table","")))
            {
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgInsertDefXml") +"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xml):</font></td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=file size=40 name=filex onChange=\"isFileType(this, 'xml');\"> \n");
                ret.append("</td>");
                ret.append("</tr>");
                if (!"".equals(base.getAttribute("filex","").trim()))
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                    ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("filex").trim() +"\">" + base.getAttribute("filex").trim() + "</a>");
                    ret.append("</td></tr> \n");
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\"><input type=checkbox name=nofilex value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                    ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("filex").trim() + "</i>");
                    ret.append("</td></tr> \n");
                }
                ret.append("<tr> \n");
                ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgConfigXsl") +"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xsl, xslt):</font></td> \n");
                ret.append("<td class=\"valores\"> \n");
                ret.append("<input type=file size=40 name=template onChange=\"isFileType(this, 'xsl|xslt');\"> \n");
                ret.append("</td>");
                ret.append("</tr>");
                if (!"".equals(base.getAttribute("template","").trim()))
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                    ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("template").trim() +"\">" + base.getAttribute("template").trim() + "</a>");
                    ret.append("</td></tr> \n");
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\"><input type=checkbox name=notemplate value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                    ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("template").trim() + "</i>");
                    ret.append("</td></tr> \n");
                }
                else
                {
                    ret.append("<tr><td></td> \n");
                    ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("msgDefaultTemplate") +" ");
                    ret.append("<a href=\""+ SWBPlatform.getWorkPath() + "swbadmin/xsl/DataBaseResource/DataBaseResource_"+base.getAttribute("queryType","")+".xslt\">DataBaseResource_"+base.getAttribute("queryType","")+".xslt</a>");
                    ret.append("</td></tr> \n");
                }
                if ("search".equals(base.getAttribute("queryType"))) {
                    ret.append("<tr> \n");
                    ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgConfigXslQuery") +"<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\"> (xsl, xslt):</font></td> \n");
                    ret.append("<td class=\"valores\"> \n");
                    ret.append("<input type=file size=40 name=tplq onChange=\"isFileType(this, 'xsl|xslt');\"> \n");
                    ret.append("</td>");
                    ret.append("</tr>");
                    if (!"".equals(base.getAttribute("tplq","").trim())) {
                        ret.append("<tr><td></td> \n");
                        ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_fileConfig") +" ");
                        ret.append("<a href=\""+ webWorkPath +"/"+ base.getAttribute("tplq").trim() +"\">" + base.getAttribute("tplq").trim() + "</a>");
                        ret.append("</td></tr> \n");
                        ret.append("<tr><td></td> \n");
                        ret.append("<td class=\"valores\"><input type=checkbox name=notplq value=1 onClick=\"if(this.checked==true) this.form.submit();\"> ");
                        ret.append(paramRequest.getLocaleString("msgCutFile") + " <i>" + base.getAttribute("tplq").trim() + "</i>");
                        ret.append("</td></tr> \n");
                    }
                    else {
                        ret.append("<tr><td></td> \n");
                        ret.append("<td class=\"valores\">"+ paramRequest.getLocaleString("msgDefaultTemplate") +" ");
                        ret.append("<a href=\""+ SWBPlatform.getWorkPath() + "swbadmin/xsl/DataBaseResource/DataBaseResource_query.xslt\">DataBaseResource_query.xslt</a>");
                        ret.append("</td></tr> \n");
                    }
                }
            }
            ret.append("<script> \n");
            ret.append("\nfunction validate()");
            ret.append("\n{");
            ret.append("\n   if(document.frmResource.table.options[document.frmResource.table.selectedIndex].value=='')");
            ret.append("\n   {");
            ret.append("\n       alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doAdmin_msgTableRequired") + "');");
            ret.append("\n       document.frmResource.table.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");  
            ret.append("\n   if(!isFileType(document.frmResource.filex, 'xml')) return false;");
            ret.append("\n   if(!isFileType(document.frmResource.template, 'xsl|xslt')) return false;");
            ret.append("\n   return true;");
            ret.append("\n}");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");
            ret.append("\nfunction send()");
            ret.append("\n{");
            ret.append("\n   document.frmResource.action='"+url.toString()+"';");
            ret.append("\n   document.frmResource.submit();");
            ret.append("\n}");            
            ret.append("\n</script> \n");             
        }
        catch(Exception e) {  log.error( "Error while generating administration form:getSearchForm() in resource "+base.getId()+".", e); }
        return ret.toString();
    }      
    
	/**
	 * Regresa documento para la presentacion de registro consultado.
	 *
	 * @return <b>Document</b> Documento de presentacion de registro consultado.
	 * 
	 * @param arguments Registro del que se desea obtener documento.
	 * 
	 * @throws <b>AFException</b> si ocurre error al obtener el documento.
	 */
	private Document getDom(Vector keys, HashMap arguments) throws SWBResourceException {
		Document doc = null;
		try {
			int numcol= 0;
			doc = SWBUtils.XML.getNewDocument();
			Element equery = doc.createElement("QUERY");
			doc.appendChild(equery);
			Element eresult = doc.createElement("result");
			equery.appendChild(eresult);
			Element eheader = doc.createElement("header");
			equery.appendChild(eheader);
			for (int i=0; i<keys.size(); i++) {
				String header = (String)keys.elementAt(i);
				Element ecol = addElem(doc, eheader, "col_name", header);
			}
			equery.setAttribute("ncol", "" + keys.size());
			Element erow = doc.createElement("row");
			eresult.appendChild(erow);
			for (int j=0; j<keys.size(); j++) {
				String columnName = (String)keys.elementAt(j);
				String columnValue = ((Object)arguments.get(columnName)).toString();
				if(columnValue == null)
					columnValue = "";
				Element ecol = addElem(doc, erow, "col", columnValue);
			}
		}catch(Exception e){
			log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.DataBaseResource", "error_DataBaseResource_getDom"), e);
		}
		return doc;
	}
	
    /**
     * Obtiene documento para la presentacion de resultados de la consulta.
     *
     * @return <b>Document</b> Documento de presentacion de resultados de la consulta
     * 
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     * 
     * @throws <b>AFException</b> si ocurre error al obtener el documento.
     */
    private Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException
    {
        HashMap arguments=new HashMap();
        Resource base = getResourceBase();
        String query = parse(base.getAttribute("query"), request, paramRequest,arguments);
        if(query != null)
        {
            Document doc = null;
            String url = new String();
            String primary = new String();
            String primarykey = new String();
            HashMap exclusion = new HashMap();
            HashMap code = new HashMap();
            HttpSession session = request.getSession();
            String pass = "05fe858d86df4b909a8c87cb8d9ad596";
            byte word[] = new BigInteger(pass, 16).toByteArray();
            Encryptor crypto = new Encryptor(word);
            String dbcon = base.getAttribute("dbcon");
            QueryGeneric consulta = new QueryGeneric(dbcon);
            
            if (arguments.containsKey("encode"))
            {
                if (arguments.get("encode").toString().indexOf(',') != -1)
                {
                    String codes[] = arguments.get("encode").toString().split(",");
                    for (int i=0; i<codes.length; i++) 
                        code.put(codes[i].trim(),codes[i].trim());
                }
                else code.put(arguments.get("encode").toString().trim(),arguments.get("encode").toString().trim());
            }
            try 
            {
                doc = SWBUtils.XML.getNewDocument();
                Element equery = doc.createElement("QUERY");
                equery.setAttribute("path", base.getAttribute("path", ""));
                doc.appendChild(equery);
                if (session.getAttribute("key") != null) 
                {
                    String ckey = (String)session.getAttribute("key");
                    if (ckey.indexOf(',') != -1) 
                    {
                        String [] keys = ckey.split(",");
                        for (int i=0; i<keys.length; i++) 
                        {
                            if (request.getParameter(keys[i]) != null)
                                addElem(doc, equery, keys[i], request.getParameter(keys[i]));
                            else addElem(doc, equery, keys[i], (String)session.getAttribute(keys[i]));
                        }
                    }
                    else 
                    {
                        if (request.getParameter(ckey) != null)
                            addElem(doc, equery, ckey, request.getParameter(ckey));
                        else addElem(doc, equery, ckey, (String)session.getAttribute(ckey));
                    }
                }
                HashMap recs = new HashMap();
                Vector results = consulta.getVectorResults(query);
                int col = consulta.getLength();
                equery.setAttribute("ncol", "" + col);
                equery.setAttribute("count", "" + results.size());
                Element eheader = doc.createElement("header");
                equery.appendChild(eheader);
                if (arguments.containsKey("key")) {
                    primarykey = (String)arguments.get("key");
                    if (primarykey.indexOf(',') != -1) {
                        String [] keys = primarykey.split(",");
                        for (int i=0; i<keys.length; i++)
                            exclusion.put(keys[i].trim(), keys[i].trim());
                        for(int x = 1; x <= col; x++) {
                            if (!exclusion.containsKey(consulta.getColumnName(x))) {
                                Element ecol = addElem(doc, eheader, "col_name", consulta.getColumnName(x));
                                equery.setAttribute(consulta.getColumnName(x), "" + x);
                            }
                        }
                    }
                    else 
                    {
                        for(int x = 1; x <= col; x++) 
                        {
                            if (!primarykey.equalsIgnoreCase(consulta.getColumnName(x))) 
                            {
                                Element ecol = addElem(doc, eheader, "col_name", consulta.getColumnName(x));
                                equery.setAttribute(consulta.getColumnName(x), "" + x);
                            }
                            else exclusion.put(primarykey.trim(), primarykey.trim());
                        }
                    }
                }
                else 
                {
                    for(int x = 1; x <= col; x++) 
                    {
                        Element ecol = addElem(doc, eheader, "col_name", consulta.getColumnName(x));
                        equery.setAttribute(consulta.getColumnName(x), "" + x);
                    }
                }
                Element eresult = doc.createElement("result");
                equery.appendChild(eresult);
                String id=String.valueOf(base.getId());
                if(results!=null && results.size() > 0) 
                {
                    String[] offset={"s"+id};
                    String url2=getParameters(request, offset);
                    
                    String start = request.getParameter("s"+id);
                    int seg = results.size();
                    try { seg = Integer.parseInt(base.getAttribute("segment")); } 
                    catch (Exception e) { seg=results.size(); }
                    int cont = 0;
                    int _i = 0; // Contador del segmento
                    int s = 1;  // Start
                    try { s = Integer.parseInt(start); } 
                    catch (Exception e) { s=1; }   

                    for (int _j=0; _j < results.size(); _j++) 
                    {
                        recs = (HashMap)results.get(_j);
                        if(recs!=null)
                        {
                            cont++;
                            if (cont >= s && _i < seg)
                            {
                                _i++;
                                Element erow = doc.createElement("row");
                                eresult.appendChild(erow);
                                for (int j=1; j<=col; j++) 
                                {
                                    if (consulta.getColumnType(j) == Types.LONGVARBINARY) 
                                    {
                                        int k = 0;
                                        String ext = new String();
                                        String fileName = new String();
                                        String columnName = consulta.getColumnName(j);
                                        if (arguments.containsKey(new Integer(j).toString()))
                                        {
                                            fileName = (String)arguments.get(new Integer(j).toString());
                                            fileName = (String)recs.get(fileName.trim());
                                            if (arguments.containsKey(columnName))
                                                ext = (String)arguments.get(columnName);
                                        }
                                        InputStream in = (InputStream)recs.get(columnName);
                                        recs.remove(columnName);					
                                        int size = in.available();
                                        if (size > 0 && !fileName.equals("") && !ext.equals("")) 
                                        {
                                            String pathFile = SWBPlatform.getWorkPath() +
                                            base.getWorkPath() + "/images/" + fileName + "." + ext;
                                            File filex = new File(pathFile);
                                            if (filex.exists()) 
                                                filex.delete();
                                            RandomAccessFile img = new RandomAccessFile(pathFile, "rw");
                                            byte file[]=new byte[size];
                                            while ((k = in.read(file,0,size)) > -1) {
                                                img.write(file,0,k);
                                            }
                                            img.close();
                                        }
                                    }
                                    else 
                                    {
                                        if (consulta.getColumnType(j) == Types.BLOB) 
                                        {
                                            int k = 0;
                                            String ext = new String();
                                            String fileName = new String();
                                            String columnName = consulta.getColumnName(j);
                                            if (arguments.containsKey(new Integer(j).toString()))
                                            {
                                                fileName = (String)arguments.get(new Integer(j).toString());
                                                fileName = (String)recs.get(fileName.trim());
                                                if (arguments.containsKey(columnName))
                                                    ext = (String)arguments.get(columnName);
                                            }
                                            String pathFile = SWBPlatform.getWorkPath() +
                                            base.getWorkPath() + "/images/" + fileName + "." + ext;
                                            File filex = new File(pathFile);
                                            if (filex.exists()) 
                                                filex.delete();
                                            RandomAccessFile img = new RandomAccessFile(pathFile, "rw");
                                            Blob blob = (Blob)recs.get(columnName);
                                            long size = blob.length();
                                            byte file[]=blob.getBytes(1,(int)size);
                                            recs.remove(columnName);					
                                            img.write(file);
                                            img.close();
                                        }
                                    }
                                    String aux = (String)recs.get(consulta.getColumnName(j));
                                    if (consulta.getColumnType(j) == Types.DATE)
                                        aux = this.getDate(aux);
                                    if (code.containsKey(consulta.getColumnName(j)))
                                        aux = crypto.encode(aux);
                                    if (arguments.containsKey("key"))
                                    {
                                        String parametros = new String();
                                        primary = ((String)arguments.get("key"));
                                        if (primary.indexOf(',') != -1) 
                                        {
                                            String [] keys = primary.split(",");
                                            for (int i=0; i<keys.length; i++)
                                            {
                                                parametros += "&" + keys[i].trim() + "=" + crypto.encode((String)recs.get(keys[i].trim()));
                                                session.setAttribute(keys[i].trim(), crypto.encode((String)recs.get(keys[i].trim())));
                                            }
                                            parametros = parametros.substring(1);
                                        }
                                        else 
                                        {
                                            if (recs.get(primary) != null)
                                            {
                                                parametros = primary + "=" + crypto.encode((String)recs.get(primary));
                                                session.setAttribute(primary, crypto.encode((String)recs.get(primary)));
                                            }
                                        }
                                        if (aux != null && parametros != "") 
                                        {
                                            if (arguments.containsKey(consulta.getColumnName(j)))
                                            {
                                                url = (String)arguments.get(consulta.getColumnName(j));
                                                if (!url.equals(""))
                                                    aux = "<a href=\"" + url + "?"+parametros + "\">" + aux + "</a>";
                                            }
                                        }
                                        else aux = "";
                                        if (!exclusion.containsKey(consulta.getColumnName(j)))
                                        { 
                                            Element ecol = addElem(doc, erow, "col", aux);
                                        }
                                    }
                                    else 
                                    {
                                        if (arguments.containsKey(consulta.getColumnName(j)))
                                        {
                                            url = (String)arguments.get(consulta.getColumnName(j));
                                            if (aux != null)
                                                aux = "<a href=\"" + url + "?"+primarykey+"=" + aux + "\">" + aux + "</a>";
                                            else aux = "";
                                            Element ecol = addElem(doc, erow, "col", aux);
                                        }
                                        else 
                                        {
                                            if (arguments.containsKey("check"))
                                            {
                                                session.setAttribute("parameterColumn",consulta.getColumnName(j));
                                                if (arguments.get("check").equals(consulta.getColumnName(j)) && aux != null)
                                                    aux = "<input name=\"_assign\" type=\"checkbox\" value=" + aux + ">";
                                            }
                                            if (aux == null)
                                                aux = "";
                                            Element ecol = addElem(doc, erow, "col", aux);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Element paging=doc.createElement("paging");
                    equery.appendChild(paging);
                    
                    Element ele=doc.createElement("results");
                    ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgResult"));
                    String value=""+s;
                    if(!"1".equals(base.getAttribute("segment","")))
                    {
                        value+=" - ";
                        if (_i > 0) value+=(s + _i - 1);
                        else value+=(s + _i);
                    }
                    value+=" "+paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgOf")+" "+ cont;
                    ele.appendChild(doc.createTextNode(value));
                    paging.appendChild(ele);
                    
                    if (s > 1)
                    {
                        int as = s - seg;
                        if (as < 1) as = 1;
                        value = url2 +"s"+id+"="+ as;
                        ele=doc.createElement("back");
                        ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgBack"));
                        ele.appendChild(doc.createTextNode(value));
                        paging.appendChild(ele);
                    }
                    if (cont > seg)
                    {
                        if("1".equals(base.getAttribute("segment","")))
                        {
                            if(s > 1)
                            {
                                ele=doc.createElement("first");
                                ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgFirst"));
                                value=url2 +"s"+id+"=1";
                                ele.appendChild(doc.createTextNode(value));
                                paging.appendChild(ele);
                            }
                            if (s < results.size()) 
                            {
                                ele=doc.createElement("last");
                                ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgLast"));
                                value=url2 +"s"+id+"="+results.size();
                                ele.appendChild(doc.createTextNode(value));
                                paging.appendChild(ele);
                            }
                        }
                        else
                        {
                            ele=doc.createElement("pages");
                            ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgPages"));
                            int p = (cont + seg - 1) / seg;
                            for (int z = 0; z < p; z++)
                            {
                                Element page=doc.createElement("page");
                                page.setAttribute("caption", String.valueOf((z + 1)));
                                if (s != ((z * seg) + 1)) 
                                {
                                    value=url2 +"s"+id+"="+((z * seg) + 1);
                                    page.appendChild(doc.createTextNode(value));
                                }
                                ele.appendChild(page);
                            }
                            paging.appendChild(ele);
                        }
                    }
                    //if (cont - 1 > s + seg) 
                    if (cont >= s + seg) 
                    {
                        value=url2 +"s"+id+"="+(s + seg);
                        ele=doc.createElement("next");
                        ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgNext"));
                        ele.appendChild(doc.createTextNode(value));
                        paging.appendChild(ele);
                    }
                }
            }
            catch(Exception e) { log.error( paramRequest.getLocaleString("error_DataBaseResource_getDom"), e); }
            return doc;
        }
        return null;
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    private void setDomMetaData(WBFileUpload requestparams, SWBParamRequest paramRequest) throws SWBException, IOException {
        if(requestparams.getValue("key")!=null)
        {
            Iterator names = requestparams.getValue("key").iterator();
            while (names.hasNext()) 
            {
                StringBuffer sb = new StringBuffer();
                String tmpnames = ((String) names.next()).trim();
                sb.append("active=\"" + acquireValue(requestparams, tmpnames + "_active") + "\" ");
                sb.append("label=\"" + acquireValue(requestparams, tmpnames + "_label") + "\" ");
                sb.append("order=\"" + acquireValue(requestparams, tmpnames + "_order") + "\" ");
                sb.append("size=\"" + acquireValue(requestparams, tmpnames + "_size") + "\" ");
                sb.append("message=\"" + acquireValue(requestparams, tmpnames + "_message") + "\" ");
                sb.append("control=\"" + acquireValue(requestparams, tmpnames + "_control") + "\" ");
                sb.append("elements=\"" + acquireElementsValue(requestparams, tmpnames) + "\" ");
                sb.append("required=\"" + acquireValue(requestparams, tmpnames + "_required") + "\" ");
                sb.append("special=\"" + acquireValue(requestparams, tmpnames + "_special") + "\" ");
                getResourceBase().setAttribute(tmpnames, sb.toString());
            }
            getResourceBase().updateAttributesToDB();
        }
    }
    
    private String acquireValue(WBFileUpload request, String key) throws IOException {
        if (null == request.getValue(key)) return "";
        Iterator tmpList = request.getValue(key).iterator();
        return tmpList.hasNext() ? ((String) tmpList.next()).trim() : "";
    }

    private String acquireParameter(WBFileUpload request, String key) throws IOException {
        if (null == request.getValue(key)) return null;
        Iterator tmpList = request.getValue(key).iterator();
        return tmpList.hasNext() ? ((String) tmpList.next()).trim() : "";
    }

    
    private String acquireElementsValue(WBFileUpload request, String key) throws IOException 
    {
        String params = getResourceBase().getAttribute(key,"");
        String ret="";
        if(!"".equals(params)) ret=getAttribute(params, "elements");
        
        String action=acquireValue(request, key+"_element_action");
        if ("edit".equals(action) || "remove".equals(action))
        {
            String id=acquireValue(request, key+"_element_id");    
            if (!"".equals(id))
            {
                String[] elements = ret.split("\\|");
                ret="";
                for(int i=0; i < elements.length ;i++)
                {
                    if(id.equals(String.valueOf(i)))
                    {
                        if("edit".equals(action))
                        {
                            String caption=acquireValue(request, key+"_element_caption");
                            String value=acquireValue(request, key+"_element_value");
                            if(!"".equals(caption) && !"".equals(value)) 
                            {
                                if(i > 0) ret+="|";
                                ret+=caption+","+value;
                            }
                        }
                        else if("remove".equals(action)) continue;
                    }
                    else
                    {    
                        if(ret.length() > 0) ret+="|";
                        ret+=elements[i];
                    }
                }
            }
        }
        else if ("add".equals(action))
        {
            String caption=acquireValue(request, key+"_element_caption");
            String value=acquireValue(request, key+"_element_value");
            if(!"".equals(caption) && !"".equals(value)) 
            {
                if(ret.length()>0) ret+="|";
                ret+=caption+","+value;
            }
        }
        return ret;
    }    
    
    private Hashtable getColumnAttributes() {
        Resource base=getResourceBase();
        Document doc = null;
        try {
            doc = base.getDom();
        } catch (Exception e) {
        }

        Hashtable attColumn = new Hashtable();
        NodeList nl = doc.getFirstChild().getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Element element = (Element) nl.item(i);
            attColumn.put(element.getNodeName(), turnElement2paramkeys(element));
        }
        return attColumn;
    }
    
    private String getAttributeTable(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) {
        int color=0;
        Resource base=getResourceBase();
        StringBuffer out = new StringBuffer();
        Hashtable attColumn = this.getColumnAttributes();
        try {
            out.append("<tr><td colspan=2>");
            out.append("  <table  border=\"0\" cellspacing=\"0\" cellpadding=\"2\">\n" +
                    "    <tr>\n" +
                    "      <td><span class=\"tabla\"><a onClick=\"jsCheck()\" href=\"#\" class=link>"+paramRequest.getLocaleString("active")+"</a></span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("name")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("label")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("order")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("control")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("size")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("message")+"</span></td>\n" +
                    //"      <td><span class=\"tabla\">"+paramRequest.getLocaleString("pattern")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("required")+"</span></td>\n" +
                    "      <td><span class=\"tabla\">"+paramRequest.getLocaleString("special")+"</span></td>\n" +
            "    </tr>\n");
            String ischecked = request.getParameter("ischecked")!=null ? request.getParameter("ischecked") : "false";
            String show = null != request.getParameter("show_elements") && !"".equals(request.getParameter("show_elements").trim()) ? request.getParameter("show_elements").trim() : "";
            TableGeneric tbl = new TableGeneric(base.getAttribute("table"), base.getAttribute("dbcon",""));
            tbl.setSchema(new HashMap());
            tbl.setTables();
            ArrayList keys = toArray(tbl.getKey(base.getAttribute("table")), ",");
            tbl.setColumns();
            Enumeration e = tbl.getColumnsMetaData();
            while (e.hasMoreElements()) {
                HashMap attributes = (HashMap)e.nextElement();
                base.updateAttributesToDB();
                String name = (String)attributes.get("COLUMN_NAME");
                if (name != null) {
                    String isnull = (String)attributes.get("IS_NULLABLE");
                    ParamKeys pktmp = (ParamKeys)attColumn.get(name);
                    if (null == pktmp) 
                        pktmp = new ParamKeys();
                    out.append("<tr bgcolor=\""+(color%2==0?"#ffffff":"#efedec")+"\">\n" + "<input type=hidden name=key value=\"" + name + "\">" +
                            "      <td>\n" +
                            "        <input name=\"" + name + "_active\" type=\"checkbox\" value=\"1\"" +
                            ("1".equalsIgnoreCase(pktmp.getActive()) ? " checked" : "") + ">");
                    if(keys.contains(name)) out.append("<img src=\""+SWBPlatform.getContextPath() + "/swbadmin/xsl/DataBaseResource/images/key.gif\" border=0>\n");
                    out.append("  </td>\n" +
                            "      <td><span class=\"valores\">" + name + "</span></td>\n" +
                            "      <td><div align=\"center\">\n" +
                            "        <input name=\"" + name + "_label\" type=\"text\" size=\"15\" value=\"" + ("1".equalsIgnoreCase(pktmp.getActive()) && "".equals(pktmp.getLabel()) ? name : pktmp.getLabel())  + "\">\n" +
                            "      </div></td>\n" +
                            "      <td><div align=\"center\">\n" +
                            "        <input name=\"" + name + "_order\" type=\"text\" size=\"3\" maxlength=\"3\" value=\"" + pktmp.getOrder() + "\" onBlur=\"isNumber(this);\">\n" +
                            "      </div></td>\n" +
                    "      <td><span class=\"valores\">\n");
                    SWBResourceURL url2=paramRequest.getRenderUrl();
                    url2.setMode(paramRequest.Mode_ADMIN);
                    url2.setAction("type");
                    out.append("<select name=\"" + name + "_control\" onChange=\"this.form.action='"+url2.toString()+"'; this.form.submit()\">\n");
                    out.append("          <option value=\"text\"" + ("text".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Text</option>\n" +
                            "          	  <option value=\"textarea\"" + ("textarea".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Text Area</option>\n" +
                            "          	  <option value=\"password\"" + ("password".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Password</option>\n" +
                            //"             <option value=\"check\"" + ("check".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Check</option>\n" +
                            "             <option value=\"radioset\"" + ("radioset".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Radio</option>\n" +
                            "         	  <option value=\"select\"" + ("select".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Select</option>\n" +
                            "			  <option value=\"label\"" + ("label".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Label</option>\n" +
                            "			  <option value=\"labelset\"" + ("labelset".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Labelset</option>\n" +
                            "         	  <option value=\"file\"" + ("file".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">File</option>\n" + 
                            "          	  <option value=\"hidden\"" + ("hidden".equalsIgnoreCase(pktmp.getControl()) ? " Selected" : "") + ">Hidden</option>\n");
                    out.append("        </select>\n" +
                            "      </span></td>\n" +
                            "      <td><div align=\"center\">\n" +
                            "        <input name=\"" + name + "_size\" type=\"text\" size=\"3\" maxlength=\"3\" value=\"" + pktmp.getSize() +"\" onBlur=\"isNumber(this);\">\n" +
                            "      </div></td>\n" +
                            "      <td><div align=\"center\">\n" +
                            "        <input name=\"" + name + "_message\" type=\"text\" size=\"15\" value=\"" + pktmp.getMessage() + "\">\n" +
                            "      </div></td>\n" +
                            //"      <td><input name=\"" + name + "_pattern\" type=\"text\" size=\"15\"  value=\"" + pktmp.getPattern() + "\"></td>\n" +
                            "      <td><div align=\"center\">\n" +
                            "        <input name=\"" + name + "_required\" type=\"checkbox\" value=\"1\"" +
                            ("1".equalsIgnoreCase(pktmp.getRequired()) || ("1".equalsIgnoreCase(pktmp.getActive()) &&  isnull.equalsIgnoreCase("NO")) ? " checked" : "") + ">\n" +
                            "      </div></td>\n" +
                            "      <td><input name=\"" + name + "_special\" type=\"text\" size=\"15\" value=\"" + pktmp.getSpecial() + "\"></td>\n");
                    if("select".equalsIgnoreCase(pktmp.getControl()) || "check".equalsIgnoreCase(pktmp.getControl()) || "radioset".equalsIgnoreCase(pktmp.getControl()) || "labelset".equalsIgnoreCase(pktmp.getControl()))  {
                        SWBResourceURL url=paramRequest.getRenderUrl();
                        url.setMode(paramRequest.Mode_ADMIN);
                        url.setAction("type");
                        if(!name.equals(show)) 
                            url.setParameter("show_elements", name);
                        out.append("<td>\n" +
                                "               <a href=\"#\" onClick=\"javascript:document.frmResource.action='"+url.toString()+"'; document.frmResource.submit();\"><img src=\""+SWBPlatform.getContextPath()+"swbadmin/images/preview.gif\" border=0></a>\n" +
                        "           </td>\n");
                    }
                    if(name.equals(show)) out.append(getElementsTable(pktmp, paramRequest));
                    color++;
                    out.append("    </tr>\n");
                }
            }
            out.append("</table></td></tr>\n");
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");
            out.append("<script>\n"+
                    "   function jsValidateElement(incaption, invalue)\n"+
                    "   {\n"+
                    "       if(!isNotNull(incaption,\""+paramRequest.getLocaleString("msgDescriptionRequired")+"\")) return false; \n"+
                    "       if(!isNotNull(invalue,\""+paramRequest.getLocaleString("msgValueRequired")+"\")) return false; \n"+
                    "       return true;\n"+
                    "   }\n"+
                    "   function jsLoadElement(inbutton, inid, incaption, invalue, str0, str1, str2)\n"+                
                    "   {\n"+
                    "       inbutton.value='"+paramRequest.getLocaleString("msgUpdate")+"';"+
                    "       inid.value=str0;\n"+
                    "       incaption.value=str1;\n"+
                    "       invalue.value=str2;\n"+
                    "   }\n"+ 
                    "   var ischecked = '"+ischecked+"';\n"+
                    "   function jsCheck()\n"+
                    "   {\n"+
                    "       if (ischecked=='false')\n"+
                    "       {\n"+
                    "           for (var i = 0; i < document.frmResource.length; i++)\n"+
                    "           {\n"+
                    "               if(document.frmResource.elements[i].type=='checkbox' && document.frmResource.elements[i].name.indexOf(\"_active\")!=-1)\n"+
                    "                   document.frmResource.elements[i].checked=true;\n"+
                    "           }\n"+
                    //"           ischecked='true';\n"+
                    "           document.frmResource.action='"+url.toString()+"?ischecked=true';\n"+
                    "       }\n"+
                    "       else\n"+
                    "       {\n"+
                    "           for (var i = 0; i < document.frmResource.length; i++)\n"+
                    "           {\n"+
                    "               if(document.frmResource.elements[i].type=='checkbox' && document.frmResource.elements[i].name.indexOf(\"_active\")!=-1)\n"+
                    "                   document.frmResource.elements[i].checked=false;\n"+
                    "           }\n"+
                    //"           ischecked='false';\n"+
                    "           document.frmResource.action='"+url.toString()+"?ischecked=false';\n"+
                    "       }\n"+
                    "       document.frmResource.submit();\n"+
                    "   }\n"+
                    resUtil.loadTrim()+"\n"+
                    //resUtil.loadIsNotNull()+"\n"+
                    resUtil.loadIsNumber()+"\n"+
            "</script>\n");  
        }catch(Exception e) {
            log.error("Error while generating DOM in resource "+ base.getResourceType().getTitle() +" with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return out.toString();
    }
    
    private String getElementsTable(ParamKeys pktmp, SWBParamRequest paramRequest)
    {
        StringBuffer out = new StringBuffer("");
        if(pktmp!=null)
        {
            String name=pktmp.getName();
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_ADMIN);
            url.setAction("type");
            url.setParameter("show_elements", name);
            try
            {
                out.append("<tr>\n"+
                    "       <td></td><td colspan=9>\n"+
                    "           <table cellspacing=0 cellpadding=0 border=0 width=\"100%\">\n"+
                    "           <tr class=tabla>\n"+
                    "               <td>"+paramRequest.getLocaleString("msgAction")+"</td>\n"+
                    "               <td>"+paramRequest.getLocaleString("msgDescription")+"</td>\n"+
                    "               <td>"+paramRequest.getLocaleString("msgValue")+"</td>\n"+
                    "           </tr>\n");
                if(pktmp.getElements()!=null && !"".equals(pktmp.getElements()))
                {
                    String[] elements = pktmp.getElements().split("\\|");
                    for(int i=0; i < elements.length ;i++)
                    {
                        String obj = elements[i];
                        String[] data=elements[i].split(",");
                        out.append("<tr class=valores>\n"+
                            "           <td>\n"+
                            "             <a href=\"#\" onClick=\"javascript:if(confirm('"+paramRequest.getLocaleString("msgConfirmRemove")+"')) { document.frmResource."+name+"_element_action.value='remove'; document.frmResource."+name+"_element_id.value="+i+"; document.frmResource.action='"+url.toString()+"'; document.frmResource.submit(); } else return false;\">\n"+
                            "                   <img src=\"" + SWBPlatform.getContextPath() + "swbadmin/images/eliminar.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgRemoveElement")+"\"></a>&nbsp;\n"+
                            "             <a href=\"#\" onClick=\"javascript:document.frmResource."+name+"_element_action.value='edit'; jsLoadElement(document.frmResource.btnAdd, document.frmResource."+name+"_element_id, document.frmResource."+name+"_element_caption, document.frmResource."+name+"_element_value, "+i+", '"+data[0]+"', '"+data[1]+"');\">\n"+
                            "                   <img src=\"" +  SWBPlatform.getContextPath() + "wbadmin/images/i_contenido.gif\" border=0 alt=\""+paramRequest.getLocaleString("msgEditElement")+"\"></a>\n"+
                            "           </td> \n"+
                            "           <td>"+data[0]+"</td> \n"+
                            "           <td>"+data[1]+"</td> \n"+
                            "       </tr>");
                    }
                    out.append("<input name=\""+name+"_element_id\" type=\"hidden\">\n");
                }
                out.append("<input name=\""+name+"_element_action\" type=\"hidden\" value=add>\n");
                out.append("<tr class=valores>\n"+
                    "   <td></td>\n"+
                    "   <td valign=bottom><input type=text size=20 name=\""+name+"_element_caption\"></td> \n"+
                    "   <td valign=bottom><input type=text size=20 name=\""+name+"_element_value\">&nbsp;"+
                    "       <input type=button name=btnAdd value=\""+paramRequest.getLocaleString("btnAdd")+"\" onClick=\"if(jsValidateElement(this.form."+name+"_element_caption, this.form."+name+"_element_value)) { this.form.action='"+url.toString()+"'; this.form.submit(); } else return false;\" class=boton></td>\n"+
                    "   </tr>\n"+
                    "           <tr><td colspan=3>&nbsp;</td></tr>\n"+
                    "           </table>\n"+
                    "       </td>\n" +
                    "    </tr>\n");

            } 
            catch(Exception e) {  log.error( "Error while generating elements table in resource "+getResourceBase().getId()+". DataBaseResource:getElementsTable()",e); }
        }
        return out.toString();
    }
    
    private ParamKeys turnElement2paramkeys(Element line) {
        ParamKeys pk = new ParamKeys();
        String params = getResourceBase().getAttribute(line.getNodeName());
        if (params.indexOf("active=") != -1) {
            pk.setName(line.getNodeName()); 
            pk.setActive(getAttribute(params, "active"));
            pk.setLabel(getAttribute(params, "label"));
            pk.setOrder(getAttribute(params, "order"));
            pk.setSize(getAttribute(params, "size"));
            pk.setMessage(getAttribute(params, "message"));
            pk.setControl(getAttribute(params, "control"));
            pk.setElements(getAttribute(params, "elements"));
            pk.setRequired(getAttribute(params, "required"));
            pk.setSpecial(getAttribute(params, "special"));
        }
        return pk;
    }
    
    public String getAttribute(String params, String attribute) {
        int indexInit = params.indexOf(attribute+"=\"");
        String value = params.substring(indexInit+attribute.length()+2);
        int indexEnd = value.indexOf("\"");
        return value.substring(0, indexEnd);
    }
    
    private String getParameters(javax.servlet.http.HttpServletRequest request, String[] offset) 
    {
        StringBuffer sbRet=new StringBuffer();
        sbRet.append("?");
        Enumeration en = request.getParameterNames();
        while(en.hasMoreElements())
        {
            String param = en.nextElement().toString();
            if(offset!=null && offset.length > 0)
            {
                for(int i=0; i < offset.length; i++)
                {
                    String off=(String)offset[i];
                    if(!off.equals(param))
                    {
                        String[] values = request.getParameterValues(param);
                        for (int j = 0; j < values.length; j++)
                            sbRet.append(param + "=" + values[j] + "&");
                    }
                }
            }
        } 
        return sbRet.toString().replaceAll(" ", "%20");
    }

    /**
     * Despliega presentacion del recurso.
     *
     * @return <b>String</b> Presentacion tras cada despliegue del recurso.
     * 
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     * 
     * @throws <b>AFException, IOException</b> si ocurre error al obtener el documento HTML.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        String queryType = getResourceBase().getAttribute("queryType");
        try {
            if (queryType.equals("consulta"))
                doViewQuery(request, response, paramRequest);
            else{
                if (queryType.equals("insert"))
                    doViewInsert(request, response, paramRequest);
                else{
                    if (queryType.equals("update"))
                        doViewUpdate(request, response, paramRequest);
                    else{
                        if (queryType.equals("delete"))
                            doViewDelete(request, response, paramRequest);
                        else {
                            if (queryType.equals("search"))
                                doViewSearch(request, response, paramRequest);
                            else {
                                log.error(paramRequest.getLocaleString("error_DataBaseResource_doView"));
                                response.getWriter().println(paramRequest.getLocaleString("error_DataBaseResource_doView"));
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){ log.error(paramRequest.getLocaleString("error_DataBaseResource_doView"), e); }
    }

    /**
     * Agrega un elemento al documento.
     *
     * @return <b>Element</b> Elemento creado.
     * 
     * @param doc Documento al que se le agregara elemento.
     * @param parent Elemento padre.
     * @param elemName Nombre del elemento.
     * @param elemValue Valor del elemento.
     */
    private Element addElem(Document doc, Element parent, String elemName, String elemValue) 
    {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
        return elem;
    }

    /**
     * Obtiene una consulta sql del argumento que recibe.
     *
     * @return <b>String</b> Consulta sql.
     * 
     * @param query Consulta que se va a realizar.
     * @param request Request asociado a la peticion del cliente.
     * @param paramRequest Parametros asociados a la peticion del cliente.
     */
    public String parse(String query, HttpServletRequest request, SWBParamRequest paramRequest, HashMap arguments) throws SWBResourceException
    {
        if(query == null) return null;
        String ret = "";
        //arguments.clear();
        HttpSession session = request.getSession();
        String pass = "05fe858d86df4b909a8c87cb8d9ad596";
        byte word[] = new BigInteger(pass, 16).toByteArray();
        Encryptor crypto = new Encryptor(word);
        int l = query.length();
        int a = 0;
        int i = 0;
        int f = 0;
        while((i = query.indexOf("{", a)) > -1) 
        {
            ret = ret + query.substring(a, i);
            if((f = query.indexOf("}", i)) > -1) 
            {
                a = f + 1;
                String com = query.substring(i + 1, f);
                if(com.equals("topicmap"))
                    ret = ret + paramRequest.getWebPage().getWebSiteId();
                else if(com.equals("topicid"))
                    ret = ret + paramRequest.getWebPage().getId();
                else if(com.equals("userid"))
                    ret = ret + paramRequest.getUser().getEmail();
                else if(com.startsWith("getUserAttribute(") && com.endsWith(")")) 
                {
                    String aux = com.substring(17, com.length() - 1);
                    ret = ret + paramRequest.getUser().getExtendedAttribute(aux);
                }
                else if(com.startsWith("getParameter(") && com.endsWith(")")) 
                {
                    String aux = com.substring(13, com.length() - 1);
                    String parameter = request.getParameter(aux);
                    if (parameter != null) 
                    {
                        session.setAttribute(aux, parameter);
                        parameter = crypto.decode(parameter);
                        parameter = parameter.trim();
                        if (session.getAttribute("key") != null) 
                        {
                            String key = (String)session.getAttribute("key");
                            aux += "," + key;
                        }
                        session.setAttribute("key", aux);
                    }
                    else 
                    {
                        parameter = (String)session.getAttribute(aux);
                        if(parameter!=null) parameter = crypto.decode(parameter).trim();
                    }
                    ret = ret + parameter;
                }
                else if(com.startsWith("getParamSearch(") && com.endsWith(")")) 
                {
                    String aux = com.substring(15, com.length() - 1);
                    String parameter = request.getParameter(aux);
                    ret = ret + parameter;
                }
                else if(com.startsWith("setArgument(") && com.endsWith(")")) 
                {
                    int size = com.indexOf(','); 
                    if (size == -1)
                        log.error(paramRequest.getLocaleString("error_DataBaseResource_parse_SetParameter"));
                    else 
                    {
                        String key = com.substring(12, size);
                        String value = com.substring(size + 1, com.length() - 1);
                        if (!key.equals(""))
                            if ("encode".equals(key) || "key".equals(key))
                                arguments.put(key, value.trim());
                            else
                                arguments.put(key, value.trim());
                    }
                }
                else if(com.startsWith("getArgument(") && com.endsWith(")")) 
                {
                    String aux = com.substring(12, com.length() - 1);
                    ret = ret + args.get(aux);
                }
                else ret = ret + query.substring(i, f + 1);
            }
            else a = l + 1;
        }
        if(a < l)
            ret = ret + query.substring(a);
        ret = ret.trim();
        return ret;
    }
    
    /**
	 * Regresa la forma que redirige la presentacion del recurso.
	 *
	 * @return <b>String</b> uri.
	 *
	 * @param HttpSession session actual.
	 * @param String uri al que debera dirigirse la presentaci�n del recurso.
	 */
	
    public String getUri(String uri, HttpSession session) {
        String id = new String();
        String key = new String();
        String rel = new String();
        String condition = new String();
        Resource base = getResourceBase();
        String dbcon = base.getAttribute("dbcon");
        String namekey = base.getAttribute("namekey");
        String pass = "05fe858d86df4b909a8c87cb8d9ad596";
        QueryGeneric search = new QueryGeneric(dbcon);
        byte word[] = new BigInteger(pass, 16).toByteArray();
        Encryptor crypto = new Encryptor(word);
        try {
            if (uri != null ) {
                uri += "?";
                if (namekey.indexOf(',') == -1) {
                    id = "SELECT MAX(" + namekey + ") AS " + base.getAttribute("namekey") + " FROM " + base.getAttribute("table");
                    search.setQuery(id);
                    Enumeration e = search.getResults(id);
                    while (e.hasMoreElements()) {
                        HashMap results = (HashMap)e.nextElement();
                        key = (String)results.get(namekey);
                    }
                    uri += namekey + "=" + crypto.encode(key);
                }else {
                    String [] keys = namekey.split(",");
    			    for (int i=0; i<keys.length; i++) {
    			        if (session.getAttribute(keys[i]) != null) {
    			            String attribute = (String)session.getAttribute(keys[i]);
    			            condition += " AND " + keys[i] + "=" + attribute;
    			            attribute = crypto.encode(attribute).trim();
    			            rel += "&" + keys[i] + "=" + attribute;
    			        }else {
    			            id = "SELECT MAX(" + keys[i] + ") AS " + keys[i] + " FROM " + base.getAttribute("table") + " WHERE ";
    			            namekey = keys[i];
    			        }
    			    }
    			    id += condition.substring(5);
                    search.setQuery(id);
                    Enumeration e = search.getResults(id);
                    while (e.hasMoreElements()) {
                        HashMap results = (HashMap)e.nextElement();
                        key = (String)results.get(namekey);
                    }
    			    uri += namekey + "=" + crypto.encode(key) + rel;
                }
                uri += "<form name=frmDesc method=\"POST\"  action=" + uri + ">";
                uri += "<SCRIPT  LANGUAGE=\"JavaScript\"> document.frmDesc.submit();";
                uri += "alert('" + SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.DataBaseResource", "usrmsg_DataBaseResource_doView_msgAlertSucessInsert") + ".')";
                uri += "</SCRIPT>";
                uri += "</form>";
            }else
                uri = new String();
        }catch (Exception e) {
            uri = new String();
        }
        return uri;
    }
    
    /**
     * Despliega presentacion del recurso cuando se le solicita una consulta.
     *
     * @return <b>String</b> HTML de despliegue cada que se solicita una consulta.
     *
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     */
    public void doViewQuery(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        request.getSession().setAttribute("uri", request.getRequestURI());
        Resource base=getResourceBase();
        StringWriter ret=new StringWriter();
        try 
        {
            Document doc = getDom(request, response, paramRequest);
            StreamResult sr = new StreamResult(ret);
            Transformer trans = tpl.newTransformer();
            trans.setOutputProperty("encoding", "ISO-8859-1");
            if(base.getTitle()!=null && !"".equals(base.getTitle().trim())) trans.setParameter("title",base.getTitle());
            if(base.getDescription()!=null && !"".equals(base.getDescription().trim())) trans.setParameter("description",base.getDescription());
            trans.transform(new DOMSource(doc), sr);  
        }
        catch (Exception wb) { log.error( paramRequest.getLocaleString("error_DataBaseResource_getDom"), wb); }
        response.getWriter().println(SWBUtils.XML.replaceXMLTags(ret.toString()));
    }
	
    /**
     * Despliega presentacion del recurso cuando se le solicita una insercion.
     *
     * @return <b>String</b> HTML de despliegue cada que se solicita una insercion.
     *
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     * @throws <b>AFException, IOException</b> si ocurre error al establecer base del recurso.
     */
    public void doViewInsert(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        int step = 0;
        Document docResult = null;
        HashMap data = new HashMap();
        String password = new String();
        Resource base = getResourceBase();
        StringWriter ret = new StringWriter();
        FileUpload fUpload = new FileUpload();				
        HttpSession session = request.getSession();
        String pass = "05fe858d86df4b909a8c87cb8d9ad596";
        byte word[] = new BigInteger(pass, 16).toByteArray();
        Encryptor crypto = new Encryptor(word);
        
        String alert = null != (String)session.getAttribute("alert")  ? ((String)session.getAttribute("alert")).trim() : new String();
        String query = null != (String)session.getAttribute("query")  ? ((String)session.getAttribute("query")).trim() : new String();
        String _msg = null != request.getParameter("_msg")  ? (request.getParameter("_msg")).trim() : new String();
        String _alert = null != request.getParameter("_alert")  ? (request.getParameter("_alert")).trim() : new String();
        session.removeAttribute("alert");
        session.removeAttribute("query");
        
        try { fUpload.getFiles(request, response); }
        catch(IOException e)
        {
            query += "\n <script>alert('" + paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_uploadFiles") + "\n" + e.toString() + ".')</script>";
            log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_uploadFiles"), e);
        }
        try {
            if (session.getAttribute("data") != null && session.getAttribute("table").equals(base.getAttribute("table")))
                data = (HashMap)session.getAttribute("data");
            else
                data = new HashMap();
        }catch (Exception e) {
            data = new HashMap();
        }
        if (session.getAttribute("clear") != null) 
        {
            if (session.getAttribute("clear").equals(base.getAttribute("table")))
                clear(session, base.getAttribute("namekey"));
        }
        if(session.getAttribute("Insert") == null) 
        {
            String key = base.getAttribute("namekey");
            if (fUpload.getValue(key) != null) 
            {
                session.setAttribute(key, fUpload.getValue(key));
                session.setAttribute("Insert", "1");
            }
            else 
            {
                if (request.getParameter("assign") != null)
                    step = 3;
                else 
                {
                    step = 1;
                    session.setAttribute("Insert", "1");
                }
            }
        }
        else
        {
            try 
            {
                String ckey = base.getAttribute("namekey");
                if (ckey.indexOf(',') != -1) 
                {
                    String [] keys = ckey.split(",");
                    for (int i=0; i<keys.length; i++) 
                    {
                        if (fUpload.getValue(keys[i]) != null) 
                        {
                            if (!fUpload.getValue(keys[i]).equals(paramRequest.getLocaleString("usrmsg_DataBaseResource_doViewInsert_msgSelect")))
                                session.setAttribute(keys[i], fUpload.getValue(keys[i]));
                        }
                    }
                }
                else 
                {
                    if (fUpload.getValue(ckey) != null) 
                        session.setAttribute(ckey, fUpload.getValue(ckey));
                }
                if (request.getParameter("assign") != null)
                    step = 3;
                else step = Integer.parseInt((String)session.getAttribute("Insert"));
            }
            catch(Exception e) { log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_getSession"), e); }
        }
        if(request.getMethod().equalsIgnoreCase("get")) 
        {
            step = 1;
            data = new HashMap();
            if (!session.isNew()) {
                HashMap back = new HashMap();
                if (request.getParameter("session") != null) {
                    String ckey = base.getAttribute("namekey");
                    if (ckey.indexOf(',') != -1) {
                        String [] keys = ckey.split(",");
                        for (int i=0; i<keys.length; i++) {
                            if (session.getAttribute(keys[i]) != null)
                                session.removeAttribute(keys[i]);
                            if (request.getParameter(keys[i]) != null) {
                                back.put(keys[i], request.getParameter(keys[i]));
                            }
                        }
                    }else {
                        if (request.getParameter(ckey) != null) {
                            back.put(ckey, request.getParameter(ckey));
                        }
                    }
                }
                Set set = back.keySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String key = (String)it.next();
                    session.setAttribute(key, back.get(key));
                }
            }
        }
        switch(step) {
        case 1:
            session.setAttribute("data", data);
            session.setAttribute("table", base.getAttribute("table"));            
            session.setAttribute("document", docResult);
            if(!"".equals(base.getAttribute("filex", "").trim()))
                docResult = processInit(session, paramRequest);
            else docResult = getDocument(session, paramRequest);
            session.setAttribute("Insert", "2");
            step=2;
            break;
        case 2:
            break;
            
        case 3:
            break;
        default:
            session.setAttribute("Insert", "1");
        }
        if (step == 3) 
            response.sendRedirect(paramRequest.getActionUrl().setAction("3").toString()+getParameters(request, new String[]{""}));
        else
        {
            if(docResult == null) {
                if(!"".equals(base.getAttribute("filex", "").trim()))
                    docResult = processInit(session, paramRequest);
                else docResult = getDocument(session, paramRequest);
            }
            HashMap attrusr = new HashMap(data);
            Node listadoc = docResult.getFirstChild();
            NodeList hijos = listadoc.getChildNodes();
            for(int i = 0; i < hijos.getLength(); i++) {
                Node node = hijos.item(i);
                NamedNodeMap Attrs;
                Attr att;
                if(node.getNodeName().equalsIgnoreCase("hidden")) {
                    Attrs = node.getAttributes();
                    att = (Attr)Attrs.getNamedItem("INCREMENT");
                    if (att != null) {
                        if (att.getNodeValue().equalsIgnoreCase("sequence") || att.getNodeValue().equalsIgnoreCase("hash") || att.getNodeValue().equalsIgnoreCase("random")) {
                            node.appendChild(docResult.createTextNode("_"+att.getNodeValue()));
                            continue;
                        }
                    }
                }     
                if(node.getNodeName().equalsIgnoreCase("text") || node.getNodeName().equalsIgnoreCase("textarea") || node.getNodeName().equalsIgnoreCase("password") || node.getNodeName().equalsIgnoreCase("hidden")) {
                    Attrs = node.getAttributes();
                    att = (Attr)Attrs.getNamedItem("NOMBRE");
                    if (!att.getNodeValue().equals("password")) {
                        if(attrusr.containsKey(att.getValue())) {
                            String convert = ((Object)attrusr.get(att.getValue())).toString();
                            org.w3c.dom.Text tmpelem = docResult.createTextNode(convert);
                            node.appendChild(tmpelem);
                        }
                    }else {
                        org.w3c.dom.Text tmpelem = docResult.createTextNode(password);
                        node.appendChild(tmpelem);
                    }
                }
                if(!node.getNodeName().equalsIgnoreCase("select") && !node.getNodeName().equalsIgnoreCase("radioset"))
                    continue;
                Attrs = node.getAttributes();
                att = (Attr)Attrs.getNamedItem("NOMBRE");
                if(!attrusr.containsKey(att.getValue()))
                    continue;
                String valores = (String)attrusr.get(att.getValue());
                NodeList elementos = node.getChildNodes();
                for(int j = 0; j < elementos.getLength(); j++) {
                    Element etemp = (Element)elementos.item(j);
                    if (valores != null) {
                        if (valores.equals(etemp.getAttribute("VALOR"))) {
                            etemp.setAttribute("INICIAL", "SI");
                        }
                    }
                }
            }
            try {
                StreamResult sr = new StreamResult(ret);
                Transformer trans = tpl.newTransformer();
                trans.setOutputProperty("encoding", "ISO-8859-1");
                trans.setParameter("Sigue",paramRequest.getActionUrl().setAction(String.valueOf(step)));
                trans.setParameter("usrId", paramRequest.getUser().getId());
                if(base.getTitle()!=null && !"".equals(base.getTitle().trim())) trans.setParameter("title",base.getTitle());
                if(base.getDescription()!=null && !"".equals(base.getDescription().trim())) trans.setParameter("description",base.getDescription());
                trans.transform(new DOMSource(docResult), sr);                
            }catch(Exception ex) {
                log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_domTransform"), ex);
            }
        }
        if (_msg.length() > 0) {
            String script = "<SCRIPT LANGUAGE=\"JavaScript\">\n";
            script += "   alert('" + _alert + "')\n";
            script += "</SCRIPT>\n";
            script += "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=";
            script += _msg;
            script += "\">";
            response.getWriter().println(script);
        }else {
            if (query.length()>0) {
                response.getWriter().println(query);
            }else
                response.getWriter().println(alert + "\n" + ret.toString());
        }
    }

	/**
	 * Obtiene documento de la forma para actualizar registro.
	 *
	 * @return <b>Document</b> Documento para construir la forma de actualizaci�n.
	 *
	 */
	private Document getDocument(HttpSession session, SWBParamRequest paramRequest) throws SWBResourceException {
	    Document doc=null;
	    SortedSet order = new TreeSet();
	    Hashtable pkAttVal = new Hashtable();
	    try {
	        Resource base = getResourceBase();
	        doc = SWBUtils.XML.getNewDocument();
	        org.w3c.dom.Element wrkelement = doc.createElement("FORMA");
	        wrkelement.setAttribute("TIPO", "SUBMIT");
	        wrkelement.setAttribute("NOMBRE", base.getAttribute("table"));
	        doc.appendChild(wrkelement);
	        org.w3c.dom.Node wrknode = doc.getFirstChild();
	        Hashtable attColumn = getColumnAttributes();
	        TableGeneric table = new TableGeneric(base.getAttribute("table"), base.getAttribute("dbcon"));
	        table.setColumns();
	        Enumeration meta = table.getColumnsMetaData();
	        while (meta.hasMoreElements()) {
	            HashMap columns = (HashMap)meta.nextElement();
	            String name = (String)columns.get("COLUMN_NAME");
	            ParamKeys pk = (ParamKeys)attColumn.get(name);
	            pk.setDataType((String)columns.get("DATA_TYPE"));
	            pk.setMaxLenght((String)columns.get("COLUMN_SIZE"));
	            if ("NO".equals((String)columns.get("IS_NULLABLE")))
	                pk.setRequired("1");
	            StringBuffer sb = new StringBuffer(pk.getOrder());
	            for (int b = 4 - sb.length(); b > 0 && b < 4; b--) {
	              sb.insert(0, '0');
	            }
	            pkAttVal.put(sb + "__" + pk.getName(), pk);
	            order.add(sb + "__" + pk.getName());
	        }
	        Iterator it = order.iterator();
	        while (it.hasNext()) {
	            String name = (String) it.next();
	            ParamKeys pkeys = (ParamKeys)pkAttVal.get(name);
	            if (pkeys != null) {
	                if (!"".equals(pkeys.getActive())) {
	                    wrkelement = doc.createElement(pkeys.getControl().toUpperCase());
	                    if ("select".equals(pkeys.getControl()) || "radioset".equals(pkeys.getControl()) || "check".equals(pkeys.getControl()) || "labelset".equals(pkeys.getControl()))
	                        setElement(session, doc, wrkelement, pkeys);
	                    wrkelement.setAttribute("NOMBRE", pkeys.getName());
	                    wrkelement.setAttribute("TIPODATO", getTypeData(new Integer(pkeys.getDataType()).intValue()));
	                    wrkelement.setAttribute("ETIQUETA", pkeys.getLabel());
	                    wrkelement.setAttribute("MENSAJE", pkeys.getMessage());
	                    wrkelement.setAttribute("TAMANO", pkeys.getSize());
	                    wrkelement.setAttribute("REQUERIDO", pkeys.getRequired());
	                    setAttributes(wrkelement, pkeys.getSpecial());
	                    wrkelement.setAttribute("TAMANOMAX", pkeys.getMaxLenght());
	                    if ("1".equals(pkeys.getRequired()))
	                        wrkelement.setAttribute("REQUERIDO", "SI");
	                    wrknode.appendChild(wrkelement);
	                }
	            }
	        }
	    }catch(Exception e){
	        log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_domTransform"), e);
	    }
	    return doc;
	}
	
	/**
	 * Obtiene documento de la forma para actualizar registro.
	 *
	 * @return <b>Document</b> Documento para construir la forma de actualizaci�n.
	 *
	 */
	private Document getDocument(HttpSession session, SWBActionResponse response) throws SWBResourceException {
	    Document doc=null;
	    SortedSet order = new TreeSet();
	    Hashtable pkAttVal = new Hashtable();
	    try {
	        Resource base = getResourceBase();
	        doc = SWBUtils.XML.getNewDocument();
	        org.w3c.dom.Element wrkelement = doc.createElement("FORMA");
	        wrkelement.setAttribute("TIPO", "SUBMIT");
	        wrkelement.setAttribute("NOMBRE", base.getAttribute("table"));
	        doc.appendChild(wrkelement);
	        org.w3c.dom.Node wrknode = doc.getFirstChild();
	        Hashtable attColumn = getColumnAttributes();
	        TableGeneric table = new TableGeneric(base.getAttribute("table"), base.getAttribute("dbcon"));
	        table.setColumns();
	        Enumeration meta = table.getColumnsMetaData();
	        while (meta.hasMoreElements()) {
	            HashMap columns = (HashMap)meta.nextElement();
	            String name = (String)columns.get("COLUMN_NAME");
	            ParamKeys pk = (ParamKeys)attColumn.get(name);
	            pk.setDataType((String)columns.get("DATA_TYPE"));
	            pk.setMaxLenght((String)columns.get("COLUMN_SIZE"));
	            if ("NO".equals((String)columns.get("IS_NULLABLE")))
	                pk.setRequired("1");
	            StringBuffer sb = new StringBuffer(pk.getOrder());
	            for (int b = 4 - sb.length(); b > 0 && b < 4; b--) {
	              sb.insert(0, '0');
	            }
	            pkAttVal.put(sb + "__" + pk.getName(), pk);
	            order.add(sb + "__" + pk.getName());
	        }
	        Iterator it = order.iterator();
	        while (it.hasNext()) {
	            String name = (String) it.next();
	            ParamKeys pkeys = (ParamKeys)pkAttVal.get(name);
	            if (pkeys != null) {
	                if (!"".equals(pkeys.getActive())) {
	                    wrkelement = doc.createElement(pkeys.getControl().toUpperCase());
	                    if ("select".equals(pkeys.getControl()) || "radioset".equals(pkeys.getControl()) || "check".equals(pkeys.getControl()) || "labelset".equals(pkeys.getControl()))
	                        setElement(session, doc, wrkelement, pkeys);
	                    wrkelement.setAttribute("NOMBRE", pkeys.getName());
	                    wrkelement.setAttribute("TIPODATO", getTypeData(new Integer(pkeys.getDataType()).intValue()));
	                    wrkelement.setAttribute("ETIQUETA", pkeys.getLabel());
	                    wrkelement.setAttribute("MENSAJE", pkeys.getMessage());
	                    wrkelement.setAttribute("TAMANO", pkeys.getSize());
	                    wrkelement.setAttribute("REQUERIDO", pkeys.getRequired());
	                    setAttributes(wrkelement, pkeys.getSpecial());
	                    wrkelement.setAttribute("TAMANOMAX", pkeys.getMaxLenght());
	                    if ("1".equals(pkeys.getRequired()))
	                        wrkelement.setAttribute("REQUERIDO", "SI");
	                    wrknode.appendChild(wrkelement);
	                }
	            }
	        }
	    }catch(Exception e){
	        log.error( response.getLocaleString("error_DataBaseResource_doViewInsert_domTransform"),e);
	    }
	    return doc;
	}
        
	private void setAttributes(org.w3c.dom.Element wrkelement, String special) {
	    if (special.indexOf("=") != -1) {
	        Hashtable attributes = getAttributes(special);
            Iterator keys = attributes.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String)keys.next();
                if (attributes.get(key) != null)
                    wrkelement.setAttribute(key, (String)attributes.get(key));
            }
	    }
        else
            wrkelement.setAttribute("ESPECIAL", special);
	}
	
	private void setElement(HttpSession session, Document document, org.w3c.dom.Element wrkelement, ParamKeys pkeys) {
	    QueryGeneric category = new QueryGeneric(getResourceBase().getAttribute("dbcon"));
	    if (categoryExist(session, pkeys.getName(), category)) {
            Iterator it = category.getCategory();
            while (it.hasNext()) {
                Element wrkelementset = document.createElement("ELEMENTO");
                Vector container = (Vector)it.next();
                wrkelementset.setAttribute("VALOR", (String)container.elementAt(0));
                wrkelementset.setAttribute("ETIQUETA", (String)container.elementAt(1));
                wrkelement.appendChild(wrkelementset);
            }
        }
	    if (!"".equals(pkeys.getElements())) {
	        String [] elements = pkeys.getElements().split("\\|");
	        for (int i=0; i<elements.length; i++) {
	            Element wrkelementset = document.createElement("ELEMENTO");
	            if (elements[i].indexOf(',') != -1) {
	                String [] element = elements[i].split(",");
	                wrkelementset.setAttribute("ETIQUETA", element[0]);
	                wrkelementset.setAttribute("VALOR", element[1]);
	                if (i==0)
	                    wrkelementset.setAttribute("INICIAL", "SI");
	                wrkelement.appendChild(wrkelementset);
	            }
	        }
	    }   
	}
	
	/**
	 * Obtiene documento de la forma para insertar un nuevo registro.
	 *
	 * @return <b>Document</b> Documento para construir la forma de insercion.
	 *
	 * @param paramRequest Parametros asociados a la peticion del cliente.
	 */
	public Document processInit(HttpSession session, SWBParamRequest paramRequest) throws SWBResourceException {
	    Document docResult;
		boolean flag = false;
		String fileName = new String();
		HashMap category = new HashMap();
		Resource base = getResourceBase();
		String lang = paramRequest.getUser().getLanguage();
		String db = base.getAttribute("dbcon");
		String tbl = base.getAttribute("table");
		String insertdef = base.getAttribute("filex");
		try {
			SWBPlatform.getFileFromWorkPath(insertdef + "." + lang);
			flag = true;
		}catch(Exception e) {
			flag = false;
		}
		if(flag)
			fileName += insertdef + "." + lang;
		else
			fileName += insertdef;
		try {
			QueryGeneric option = new QueryGeneric(db);
			TableGeneric table = new TableGeneric(tbl, db);
			for(Iterator it = table.getColumnNames(); it.hasNext();) {
				String key = (String)it.next();
			    if (categoryExist(session, key, option)) {
					category.put(key, option.getCategory());
					session.setAttribute("init"+key, "");
				}
			}
			Document doc = SWBUtils.XML.xmlToDom(SWBPlatform.getFileFromWorkPath(base.getWorkPath() + "/" + fileName));
			docResult = SWBUtils.XML.getNewDocument();
			FormScanner FScan = new FormScanner(doc, docResult, category);
			FScan.visitDocument();
		}catch(Exception e) {
			log.error(paramRequest.getLocaleString("error_DataBaseResource_processInit"), e);
			return null;
		}
		session.setAttribute("document", docResult);
		return docResult;
	}
	
	public Document processInit(HttpSession session, SWBActionResponse response) throws SWBResourceException {
	    Document docResult;
		boolean flag = false;
		String fileName = new String();
		HashMap category = new HashMap();
		Resource base = getResourceBase();
		String lang = response.getUser().getLanguage();
		String db = base.getAttribute("dbcon");
		String tbl = base.getAttribute("table");
		String insertdef = base.getAttribute("filex");
		try {
			SWBPlatform.getFileFromWorkPath(insertdef + "." + lang);
			flag = true;
		}catch(Exception e) {
			flag = false;
		}
		if(flag)
			fileName += insertdef + "." + lang;
		else
			fileName += insertdef;
		try {
			QueryGeneric option = new QueryGeneric(db);
			TableGeneric table = new TableGeneric(tbl, db);
			for(Iterator it = table.getColumnNames(); it.hasNext();) {
				String key = (String)it.next();
			    if (categoryExist(session, key, option)) {
					category.put(key, option.getCategory());
					session.setAttribute("init"+key, "");
				}
			}
			Document doc = SWBUtils.XML.xmlToDom(SWBPlatform.getFileFromWorkPath(base.getWorkPath() + "/" + fileName));
			docResult = SWBUtils.XML.getNewDocument();
			FormScanner FScan = new FormScanner(doc, docResult, category);
			FScan.visitDocument();
		}catch(Exception e) {
			log.error(response.getLocaleString("error_DataBaseResource_processInit"), e);
			return null;
		}
		session.setAttribute("document", docResult);
		return docResult;
	}
        
	/**
	 * Despliega presentacion del recurso cuando se le solicita una actualizacion.
	 *
	 * @return <b>String</b> HTML de despliegue cada que se solicita una actualizacion.
	 *
	 * @param request Request asociado a la peticion del cliente.
	 * @param response Response asociado a la respuesta del cliente.
	 * @param paramRequest Parametros asociados con la peticion del cliente.
	 * @throws <b>AFException, IOException</b> si ocurre error al establecer base del recurso.
	 */
	public void doViewUpdate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
	{
	    int step = 0;
	    String key = "";
	    String query ="";
	    String recGeneric ="";
	    Document docResult = null;
	    HashMap attrusr = new HashMap();
	    Resource base = getResourceBase();
	    StringWriter ret = new StringWriter();
	    FileUpload fUpload = new FileUpload();
	    String dbcon = base.getAttribute("dbcon");
	    String tableData = base.getAttribute("table");
	    String nameKey = base.getAttribute("namekey");
	    HttpSession session = request.getSession();
	    String pass = "05fe858d86df4b909a8c87cb8d9ad596";
	    byte word[] = new BigInteger(pass, 16).toByteArray();
	    Encryptor crypto = new Encryptor(word);
	    if(session.getAttribute("docResult")!=null)
	    {
	        docResult = (Document)session.getAttribute( "docResult");
	        session.removeAttribute("docResult");
	    }
	    if(session.getAttribute("attrusr")!=null)
	    {
	        attrusr = (HashMap)session.getAttribute( "attrusr");
	        session.removeAttribute("attrusr");
	    }
	    query = null != session.getAttribute("query")  ? ((String)session.getAttribute("query")).trim() : new String();
	    session.removeAttribute("query");          
	    
	    if(session.getAttribute("Update") == null) 
	    {
	        step = 1;
	        session.setAttribute("Update", "1");
	        session.setAttribute("table", tableData);
	    }
	    else 
	    {
	        try 
	        {
	            if (request.getParameter("step") != null)
	                step = 1;
	            else 
	            {
	                if (tableData.equals((String)session.getAttribute("table"))) 
	                {
	                    if (nameKey.indexOf(',') != -1) 
	                    {
	                        String [] keys = nameKey.split(",");
	                        for (int i=0; i<keys.length; i++) 
	                        {
	                            if (request.getParameter(keys[i]) == null)
	                                step = Integer.parseInt((String)session.getAttribute("Update"));
	                            else step = 1;
	                        }
	                    }
	                    else 
	                    {
	                        if (request.getParameter(nameKey) == null)
	                            step = Integer.parseInt((String)session.getAttribute("Update"));
	                        else step = 1;
	                    }
	                }
	                else 
	                {
	                    step = 1;
	                    session.setAttribute("table", tableData);
	                }
	            }
	        }
	        catch(Exception e) { log.error( paramRequest.getLocaleString("error_DataBaseResource_doViewUpdate_getSession"), e); }
	    }
	    if(request.getMethod().equalsIgnoreCase("get")) 
	    {
	        step = 1;
	        session.setAttribute("table", tableData);
	    }
	    switch(step) 
	    {
	    case 1:
	        try 
	        {
	            TableGeneric table = new TableGeneric(tableData, dbcon);
	            if (nameKey.indexOf(',') != -1) {
	                String [] keys = nameKey.split(",");
	                for (int i=0; i<keys.length; i++) {
	                    if (request.getParameter(keys[i]) != null) {
	                        key = crypto.decode(request.getParameter(keys[i]));
	                        key = key.trim();
	                        table.addPrimaryKey(keys[i]);
	                        recGeneric += "|" + key;
	                        session.setAttribute("tbl", table);
	                        session.setAttribute(keys[i], request.getParameter(keys[i]));
	                    }else {
	                        key = (String)session.getAttribute(keys[i]);
	                        key = crypto.decode(key);
	                        key = key.trim();
	                        table.addPrimaryKey(keys[i]);
	                        recGeneric += "|" + key;
	                    }
	                }
	            }else {
	                if (request.getParameter(nameKey) != null) {
	                    key = crypto.decode(request.getParameter(nameKey));
	                    key = key.trim();
	                    table.addPrimaryKey(nameKey);
	                    recGeneric = key;
	                    session.setAttribute("tbl", table);
	                    session.setAttribute(nameKey, request.getParameter(nameKey));
	                }else {
	                    key = (String)session.getAttribute(nameKey);
	                    key = crypto.decode(key);
	                    key = key.trim();
	                    table.addPrimaryKey(nameKey);
	                    recGeneric = key;
	                }
	            }
	            RecordGeneric record = table.getNewRecordGeneric(recGeneric);
	            record.load();
	            attrusr = record.getData();
	            session.setAttribute("record",record);
	            int numcol = table.getLength();
	            for (int i=1; i<numcol+1; i++) {
	                if (table.getColumnType(i) == Types.LONGVARBINARY || table.getColumnType(i) == Types.BLOB)
	                    attrusr.remove(table.getColumnName(i));
	                if (table.getColumnName(i).equals("password")) {
	                    String parameter = (String)attrusr.get(table.getColumnName(i));
	                    if (parameter != null) {
                            try {
                                parameter = SWBUtils.CryptoWrapper.passwordDigest(parameter);
                            } catch (Exception e) {
                            }
	                        
	                        attrusr.put(table.getColumnName(i), parameter);
	                    }
	                }
	                if (table.getColumnType(i) == Types.DATE) {
	                    String parameter = new String();
	                    if (attrusr.get(table.getColumnName(i)) != null) {
	                        parameter = attrusr.get(table.getColumnName(i)).toString();
	                        parameter = getDate(parameter);
	                    }
	                    attrusr.put(table.getColumnName(i), parameter);
	                }
	            }
	        }catch(SQLException e){
	            query = "\n <script>alert('" + paramRequest.getLocaleString("error_DataBaseResource_doViewUpdate_getInitData") + e.toString() + ".')</script>";
	            session.setAttribute("Update", "1");
	            log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewUpdate_getRecordGenerics"), e);
	        }
	        if (!attrusr.isEmpty()) {
	            session.setAttribute("Update", "2");
	            session.setAttribute("data", attrusr);
	            if(!"".equals(base.getAttribute("filex", "").trim()))
	                docResult = processInit(session, paramRequest);
	            else docResult = getDocument(session, paramRequest);
	        }else{
	            query = "<script>alert('" + "Error: " + nameKey + " " + key + " " + paramRequest.getLocaleString("error_DataBaseResource_doViewUpdate_tableDoesExist") + tableData + ".')</script>";
	            session.setAttribute("Update", "1");
	        }
	        break;
	    case 2:
	        break;
	    default:
	        session.setAttribute("Update", "1");
	    break;
	    } 
	    if(docResult == null) {
	        if(!"".equals(base.getAttribute("filex", "").trim()))
	            docResult = processInit(session, paramRequest);
	        else docResult = getDocument(session, paramRequest);
	    }
	    Node listadoc = docResult.getFirstChild();
	    NodeList hijos = listadoc.getChildNodes();
	    for(int i = 0; i < hijos.getLength(); i++) {
	        Node node = hijos.item(i);
	        NamedNodeMap Attrs;
	        Attr att;
	        if(node.getNodeName().equalsIgnoreCase("text") || node.getNodeName().equalsIgnoreCase("textarea") || node.getNodeName().equalsIgnoreCase("password") || node.getNodeName().equalsIgnoreCase("hidden") || node.getNodeName().equalsIgnoreCase("label")) {
	            Attrs = node.getAttributes();
	            att = (Attr)Attrs.getNamedItem("NOMBRE");
	            if(attrusr.containsKey(att.getValue())) {
	                String convert = new String();
	                if (attrusr.get(att.getValue()) != null)
	                    convert = ((Object)attrusr.get(att.getValue())).toString();
	                org.w3c.dom.Text tmpelem = docResult.createTextNode(convert);
	                node.appendChild(tmpelem);
	            }
	        }
	        if(!node.getNodeName().equalsIgnoreCase("select") && !node.getNodeName().equalsIgnoreCase("radioset") && !node.getNodeName().equalsIgnoreCase("labelset"))
	            continue;
	        Attrs = node.getAttributes();
	        att = (Attr)Attrs.getNamedItem("NOMBRE");
	        if(!attrusr.containsKey(att.getValue()))
	            continue;
	        if (attrusr.get(att.getValue()) != null) {
	            String valores = ((Object)attrusr.get(att.getValue())).toString();
	            NodeList elementos = node.getChildNodes();
	            for(int j = 0; j < elementos.getLength(); j++) {
	                Element etemp = (Element)elementos.item(j);
	                if (valores != null) {
	                    if (valores.equals(etemp.getAttribute("VALOR"))) {
	                        etemp.setAttribute("INICIAL", "SI");
	                    }
	                }
	            }
	        }
	    }
	    try {
	        StreamResult sr = new StreamResult(ret);
	        Transformer trans = tpl.newTransformer();
	        trans.setOutputProperty("encoding", "ISO-8859-1");
	        trans.setParameter("Sigue", paramRequest.getActionUrl().toString());
	        trans.setParameter("usrId", paramRequest.getUser().getId());
	        if(base.getTitle()!=null && !"".equals(base.getTitle().trim())) trans.setParameter("title",base.getTitle());
            if(base.getDescription()!=null && !"".equals(base.getDescription().trim())) trans.setParameter("description",base.getDescription());
	        trans.transform(new DOMSource(docResult), sr);                 
	    }
	    catch(Exception ex) {
	        log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewUpdate_domTransform"), ex);
	    }
	    response.getWriter().println(query + ret.toString());
	}
	
    /**
     * Despliega presentacion del recurso cuando se le solicita una eliminacion.
     *
     * @return <b>String</b> HTML de despliegue cada que se solicita una eliminacion.
     *
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     * @throws <b>AFException, IOException</b> si ocurre error al establecer base del recurso.
     */
    public void doViewDelete(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String uri = new String();
        HttpSession session = request.getSession();
        String alert = null != request.getParameter("alert")  ? (request.getParameter("alert")).trim() : new String();
        session.removeAttribute("alert");
        if(alert.length()>0)
        {
            if (session.getAttribute("uri") != null) 
            {
                if (!session.getAttribute("uri").equals("")) 
                {
                    //uri = (String)session.getAttribute("uri");
                    //uri = "<form name=frmDesc method=\"POST\"  action=" + uri + ">";
                    //uri += "<SCRIPT LANGUAGE=\"JavaScript\"> document.frmDesc.submit();";
                    uri += "<SCRIPT LANGUAGE=\"JavaScript\">\n";
                    uri += alert;
                    uri += "</SCRIPT>\n";
                    uri += "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=";
                    uri += (String)session.getAttribute("uri");
                    uri += "\">";
                    //uri += "</form>";
                }
            }
            else uri = "<SCRIPT>" + alert + "</SCRIPT>";
            response.getWriter().println(uri);
        }
        else response.sendRedirect(paramRequest.getActionUrl().toString()+getParameters(request, new String[]{""}));
    }
	
    /**
     * Despliega presentacion del recurso cuando se le solicita una presentaci�n de b�squeda.
     *
     * @return <b>String</b> HTML de despliegue cada que se solicita una insercion.
     *
     * @param request Request asociado a la peticion del cliente.
     * @param response Response asociado a la respuesta del cliente.
     * @param paramRequest Parametros asociados con la peticion del cliente.
     * @throws <b>AFException, IOException</b> si ocurre error al establecer base del recurso.
     */
    public void doViewSearch(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
    throws SWBResourceException, IOException {
        int step = 0;
        Document docResult = null;
        String alert = new String();
        Vector fields = new Vector();
        Vector tables = new Vector();
        Vector conditions = new Vector();
        Resource base = getResourceBase();
        HashMap dataInt = new HashMap();
        HashMap dataString = new HashMap();
        StringWriter ret = new StringWriter();		
        HttpSession session = request.getSession();
        if (request.getParameter("_action") == null) {
            if(!"".equals(base.getAttribute("filex", "").trim()))
                docResult = processInit(session, paramRequest);
            else docResult = getDocument(session, paramRequest);
            HashMap attrusr = new HashMap();
            Node listadoc = docResult.getFirstChild();
            NodeList hijos = listadoc.getChildNodes();
            for(int i = 0; i < hijos.getLength(); i++) {
                Node node = hijos.item(i);
                NamedNodeMap Attrs;
                Attr att;
                if(node.getNodeName().equalsIgnoreCase("text") || node.getNodeName().equalsIgnoreCase("textarea") || node.getNodeName().equalsIgnoreCase("password") || node.getNodeName().equalsIgnoreCase("hidden")) {
                    Attrs = node.getAttributes();
                    att = (Attr)Attrs.getNamedItem("NOMBRE");
                    if(attrusr.containsKey(att.getValue())) {
                        if (attrusr.get(att.getValue()) != null) {
                            String convert = ((Object)attrusr.get(att.getValue())).toString();
                            org.w3c.dom.Text tmpelem = docResult.createTextNode(convert);
                            node.appendChild(tmpelem);
                        }else {
                            org.w3c.dom.Text tmpelem = docResult.createTextNode("");
                            node.appendChild(tmpelem);
                        }
                    }
                }
                if(!node.getNodeName().equalsIgnoreCase("select") && !node.getNodeName().equalsIgnoreCase("radioset"))
                    continue;
                Attrs = node.getAttributes();
                att = (Attr)Attrs.getNamedItem("NOMBRE");
                if(!attrusr.containsKey(att.getValue()))
                    continue;
                String valores = (String)attrusr.get(att.getValue());
                NodeList elementos = node.getChildNodes();
                for(int j = 0; j < elementos.getLength(); j++) {
                    Element etemp = (Element)elementos.item(j);
                    if (valores != null) {
                        if (valores.equals(etemp.getAttribute("VALOR"))) {
                            etemp.setAttribute("INICIAL", "SI");
                        }
                    }
                }
            }
            try {
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode(url.Mode_VIEW);
                url.setParameter("_action", "search");
                StreamResult sr = new StreamResult(ret);
                Transformer trans = tpl.newTransformer();
                trans.setOutputProperty("encoding", "ISO-8859-1");
                trans.setParameter("Sigue",url.toString());
                trans.setParameter("usrId", paramRequest.getUser().getId());
                if(base.getTitle()!=null && !"".equals(base.getTitle().trim())) trans.setParameter("title",base.getTitle());
                if(base.getDescription()!=null && !"".equals(base.getDescription().trim())) trans.setParameter("description",base.getDescription());
                trans.transform(new DOMSource(docResult), sr);                      
            }catch(Exception ex) {
                log.error( paramRequest.getLocaleString("error_DataBaseResource_doViewInsert_domTransform"), ex);
            }
            response.getWriter().println(alert + "\n" + ret.toString());
        }else {
            String dbcon = base.getAttribute("dbcon");
            String tableData = base.getAttribute("table");
            try {
                QueryGeneric option = new QueryGeneric(dbcon);
                TableGeneric table = new TableGeneric(tableData, dbcon);
                RecordGeneric record = new RecordGeneric(table);
                int numcol = table.getLength(); 
                for (int i=1; i<numcol+1; i++) {
                    if (table.getColumnType(i)!=Types.LONGVARBINARY || !(table.getColumnType(i)!=Types.BLOB)) {
                        String columnName = table.getColumnName(i);
                        try {
                            String [] props = ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(getResourceBase().getAttribute("dbcon")+"."+getResourceBase().getAttribute("table")+"."+columnName).split(":");
                            conditions.add(base.getAttribute("table")+"."+columnName+"="+props[1]+"."+columnName);
                            fields.add(props[1] + "." + props[2] + " as " + props[2] + "_" + i);
                            tables.add(props[1]);
                        }catch (Exception e) {
                            fields.add(getResourceBase().getAttribute("table")+"."+columnName);
                        }
                        String parameter = request.getParameter(columnName);
                        if (null != parameter) {
                            if (!"".equals(parameter)) {
                                if (table.getColumnType(i)==Types.DATE) {
                                    if (parameter.indexOf('/') != -1)
                                        parameter = getDate(parameter);
                                }
                                if (table.getColumnType(i)==Types.VARCHAR || table.getColumnType(i)==Types.CHAR || table.getColumnType(i)==Types.DATE || table.getColumnType(i)==Types.TIMESTAMP)
                                    dataString.put(columnName,parameter);
                                if (table.getColumnType(i)==Types.INTEGER || table.getColumnType(i)==Types.DOUBLE || table.getColumnType(i)==Types.FLOAT || table.getColumnType(i)==Types.REAL || table.getColumnType(i)==Types.DECIMAL || table.getColumnType(i)==Types.NUMERIC || table.getColumnType(i)==Types.TINYINT)
                                    dataInt.put(columnName,parameter);
                            }
                        }
                    }
                }
                String query = "";
                String condition = "";
                String partialInt = "";
                String tablesData = "";
                String partialString = "";
                Enumeration en = fields.elements();
                while (en.hasMoreElements())
                    query += "," + (String)en.nextElement();
                Iterator it = tables.iterator();
                while (it.hasNext())
                    tablesData += "," + (String)it.next();
                query = "SELECT " + query.substring(1) + " FROM " + tableData + tablesData + " WHERE";
                it = conditions.iterator();
                while (it.hasNext())
                    condition += " AND " + (String)it.next();
                if (!"".equals(condition))
                    condition = condition.substring(4);
                it = dataString.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String)it.next();
                    partialString += " AND " + base.getAttribute("table") + "." + key + "='" + dataString.get(key) + "'";
                }
                it = dataInt.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String)it.next();
                    partialInt += " AND " + base.getAttribute("table") + "." + key + "=" + dataInt.get(key);
                }
                if (!"".equals(condition))
                    query += condition;
                if (!"".equals(partialString) && !"".equals(condition))
                    query += partialString;
                else {
                    if (!"".equals(partialString))
                        query += partialString.substring(4);
                }
                if (!"".equals(partialInt) && !"".equals(partialString))
                    query += partialInt;
                else {
                    if (!"".equals(partialInt) && !"".equals(condition))
                    query += partialInt;
                    else {
                        if (!"".equals(partialInt))
                            query += partialInt.substring(4);
                    }
                }
                try {
                    Document doc = SWBUtils.XML.getNewDocument();
                    Element equery = doc.createElement("QUERY");
                    equery.setAttribute("path", base.getAttribute("path", ""));
                    doc.appendChild(equery);
                    HashMap recs = new HashMap();
                    Vector results = option.getVectorResults(query);
                    int col = option.getLength();
                    equery.setAttribute("ncol", "" + col);
                    equery.setAttribute("count", "" + results.size());
                    Element eheader = doc.createElement("header");
                    equery.appendChild(eheader);
                    for(int x = 1; x <= col; x++)  {
                        Element ecol = addElem(doc, eheader, "col_name", option.getColumnName(x));
                        equery.setAttribute(option.getColumnName(x), "" + x);
                    }
                    Element eresult = doc.createElement("result");
                    equery.appendChild(eresult);
                    String id=String.valueOf(base.getId());
                    if(results!=null && results.size() > 0) {
                        String[] offset={"s"+id};
                        String url2=getParameters(request, offset);
                        String start = request.getParameter("s"+id);
                        int seg = results.size();
                        try { 
                            seg = Integer.parseInt(getResourceBase().getAttribute("segment","10")); } 
                        catch (Exception e) { seg=results.size(); }
                        int cont = 0;
                        int _i = 0; // Contador del segmento
                        int s = 1;  // Start
                        try { s = Integer.parseInt(start); } 
                        catch (Exception e) { s=1; }   
                        for (int _j=0; _j < results.size(); _j++) {
                            recs = (HashMap)results.get(_j);
                            if(recs!=null) {
                                cont++;
                                if (cont >= s && _i < seg) {
                                    _i++;
                                    Element erow = doc.createElement("row");
                                    eresult.appendChild(erow);
                                    for (int j=1; j<=col; j++) {
                                        String aux = (String)recs.get(option.getColumnName(j));
                                        if (option.getColumnType(j) == Types.DATE)
                                            aux = getDate(aux);
                                        if (aux == null)
                                            aux = "";
                                        Element ecol = addElem(doc, erow, "col", aux);
                                    }
                                }
                            }
                        }
                        Element paging=doc.createElement("paging");
                        equery.appendChild(paging); 
                        Element ele=doc.createElement("results");
                        ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgResult"));
                        String value=""+s;
                        if(!"1".equals(base.getAttribute("segment",""))) {
                            value+=" - ";
                            if (_i > 0) value+=(s + _i - 1);
                            else value+=(s + _i);
                        }
                        value+=" "+paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgOf")+" "+ cont;
                        ele.appendChild(doc.createTextNode(value));
                        paging.appendChild(ele);
                        if (s > 1) {
                            int as = s - seg;
                            if (as < 1) as = 1;
                            value = url2 +"s"+id+"="+ as;
                            ele=doc.createElement("back");
                            ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgBack"));
                            ele.appendChild(doc.createTextNode(value));
                            paging.appendChild(ele);
                        }
                        if (cont > seg) {
                            if("1".equals(base.getAttribute("segment","")))
                            {
                                if(s > 1)
                                {
                                    ele=doc.createElement("first");
                                    ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgFirst"));
                                    value=url2 +"s"+id+"=1";
                                    ele.appendChild(doc.createTextNode(value));
                                    paging.appendChild(ele);
                                }
                                if (s < results.size()) 
                                {
                                    ele=doc.createElement("last");
                                    ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgLast"));
                                    value=url2 +"s"+id+"="+results.size();
                                    ele.appendChild(doc.createTextNode(value));
                                    paging.appendChild(ele);
                                }
                            }
                            else
                            {
                                ele=doc.createElement("pages");
                                ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgPages"));
                                int p = (cont + seg - 1) / seg;
                                for (int z = 0; z < p; z++)
                                {
                                    Element page=doc.createElement("page");
                                    page.setAttribute("caption", String.valueOf((z + 1)));
                                    if (s != ((z * seg) + 1)) 
                                    {
                                        value=url2 +"s"+id+"="+((z * seg) + 1);
                                        page.appendChild(doc.createTextNode(value));
                                    }
                                    ele.appendChild(page);
                                }
                                paging.appendChild(ele);
                            }
                        }
                        if (cont >= s + seg) {
                            value=url2 +"s"+id+"="+(s + seg);
                            ele=doc.createElement("next");
                            ele.setAttribute("caption", paramRequest.getLocaleString("usrmsg_DataBaseResource_getDom_msgNext"));
                            ele.appendChild(doc.createTextNode(value));
                            paging.appendChild(ele);
                        }
                    }
                    StreamResult sr = new StreamResult(ret);
                    Transformer trans = tplq.newTransformer();
                    trans.setOutputProperty("encoding", "ISO-8859-1");
                    trans.transform(new DOMSource(doc), sr);
                    response.getWriter().println(SWBUtils.XML.replaceXMLTags(ret.toString()));
                }catch(Exception e) { 
                    log.error( paramRequest.getLocaleString("error_DataBaseResource_getDom"), e);
                }
            }catch(Exception e) {
                log.error(paramRequest.getLocaleString("error_DataBaseResource_doViewSearch_getParameters"), e);
                alert += "<script>alert('" + paramRequest.getLocaleString("usrmsg_DataBaseResource_doViewSearch_msgAlertgetParameters") + ".')</script>";
            }
        }
    }
	
	/**
     * Indica si el catalogo existe en la base de datos.
     * 
     * @param db Nombre de la conexi�n.
     * @param table Nombre de la tabla que contiene los datos.
     * @param foreignKey Campo de la tabla que representa la clave for�nea.
     * @param session Sesion que reliza la petici�n.
     * @param options Consulta gen�rica de bases de datos.
     * 
     * @return <b>boolean</b> true Si existe la tabla; false En caso contrario.
     */
	public boolean categoryExist(HttpSession session, String foreignKey, QueryGeneric options) {
	    String query = new String();
	    String option = new String();
	    String partial = new String();
	    HashMap data = (HashMap)session.getAttribute("data");
	    try {
	        option = ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(getResourceBase().getAttribute("dbcon")+"."+getResourceBase().getAttribute("table")+"."+foreignKey);
	        String [] keys = option.split(":");
	        if (keys.length < 4) {
	            query = "SELECT " + foreignKey + "," + keys[2] + " FROM " + keys[1];
	        }else {
	            int i=3;
	            query = "SELECT " + foreignKey + "," + keys[2] + " FROM " + keys[1] + " WHERE";
	            while (i<keys.length) {
	                partial += " AND ";
	                partial += keys[i] + "=" + data.get(keys[i]);
	                i += 2;
	            }
	            query += partial.substring(4);
	        }
	        return options.categoryExist(query, keys[0]);
	    }catch (Exception e) {
	        return false;
	    }
	}
    
    /**
     *
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */       
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        Resource base = getResourceBase();
        String queryType = base.getAttribute("queryType");
        String _msg = new String();
        String query = new String();
        String alert = new String();
        String _alert = new String();
        HttpSession session = request.getSession();
        WBFileUpload fUpload = new WBFileUpload();				
        HashMap data = new HashMap();
        String password = new String();
        String pass = "05fe858d86df4b909a8c87cb8d9ad596";
        byte word[] = new BigInteger(pass, 16).toByteArray();
        Encryptor crypto = new Encryptor(word);
        try { fUpload.getFiles(request); }
        catch(IOException e) 
        { 
            query += "\n <script>alert('" + response.getLocaleString("error_DataBaseResource_doViewInsert_uploadFiles") + "\n" + e.toString() + ".')</script>";
            log.error(response.getLocaleString("error_DataBaseResource_doViewInsert_uploadFiles"), e);
        }        

        if ("insert".equals(queryType))
        {
            String action = response.getAction();
            if("2".equals(action)) 
            {
                boolean page = false;
                data = (HashMap)session.getAttribute("data");
                String dbcon = base.getAttribute("dbcon");
                String tableData = base.getAttribute("table");
                try 
                {
                    QueryGeneric option = new QueryGeneric(dbcon);
                    TableGeneric table = new TableGeneric(tableData, dbcon);
                    RecordGeneric record = new RecordGeneric(table);
                    int numcol = table.getLength(); 
                    String value=""; 
                    for (int i=1; i<numcol+1; i++) 
                    {
                        if (table.getColumnType(i)==Types.LONGVARBINARY || table.getColumnType(i)==Types.BLOB) 
                        {
                            String columnName = table.getColumnName(i);
                            value=acquireValue(fUpload, columnName);   
                            if (!"".equals(value)) 
                            {
                                byte[] file=fUpload.getFileData(columnName);
                                if (fUpload.getSize()>0)
                                    data.put(columnName, file);
                            }
                        }
                        else 
                        {
                            String columnName = table.getColumnName(i);
                            String parameter = acquireValue(fUpload, columnName);
                            System.out.println(columnName + " |" + parameter + "|");
                            if (!"".equals(parameter)) {
                                if("_sequence".equalsIgnoreCase(parameter))
                                    parameter=getNextId(columnName);
                                else if("_hash".equalsIgnoreCase(parameter))
                                    parameter=getHash();
                                else if("_random".equalsIgnoreCase(parameter))
                                    parameter=getRandom(columnName);
                                else if (session.getAttribute(columnName) != null)  {
                                    parameter = (String)session.getAttribute(columnName);
                                    parameter = crypto.decode(parameter).trim();
                                }
                            }
                            if (table.getColumnType(i)==Types.TIMESTAMP) 
                            {
                                Timestamp time = new Timestamp(System.currentTimeMillis());
                                parameter = time.toString();
                            }
                            if (columnName.equals("password")) 
                            {
                                if (parameter != null) 
                                {
                                    password = parameter;
                                    parameter = SWBUtils.CryptoWrapper.passwordDigest(parameter);
                                }
                            }
                            if (table.getColumnType(i)==Types.DATE) 
                            {
                                if (parameter != null) 
                                {
                                    if (parameter.indexOf('/') != -1)
                                    parameter = getDate(parameter);
                                }
                            }
                            data.put(columnName, parameter);
                        }
                    }
                    session.setAttribute("data", data);
                    if (page == false) {
                        record.setData(data);
                        record.insert();
                        data.clear();
                        password = "";
                        value=acquireValue(fUpload, "redirect");   
                        if (!"".equals(value))
                        {
                            session.setAttribute("clear", tableData);
                            query = getUri(value, session);
                        }
                        else alert = "<script>alert('" + response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertSucessInsert") + ".')</script>";

                        //TODO:
                        //if (base.getAttribute("sync") != null)
                        //    DBTopicMgr.getInstance().refresh();
                        logWrite(response, session);
                    }
                    else session.setAttribute("Insert", "2");
                }
                catch(Exception e) 
                {
                    if (!"".equals(getException(e, response)))
                        alert += "<script>alert('" + getException(e, response) + ".')</script>";
                    else
                        alert += "<script>alert('" + response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertErrorInsert") + ".')</script>";
                    if (e != null)
                        log.error(response.getLocaleString("error_DataBaseResource_doViewInsert_insertRecord") + " " + base.getId() + " " + e.getMessage());
                    log.error(response.getLocaleString("error_DataBaseResource_doViewInsert_insertRecord") + " " + base.getId(), e);
                    session.setAttribute("Insert", "2");
                }
            }
            else if("3".equals(action)) 
            {
                String values[] = request.getParameterValues("_assign");
                _msg = request.getParameter("redirect");
                if (values != null) 
                {
                    try
                    {
                        TableGeneric table = new TableGeneric(base.getAttribute("table"), base.getAttribute("dbcon"));
                        RecordGeneric record = new RecordGeneric(table);
                        int numcol = table.getLength(); 
                        for (int i=1; i<numcol+1; i++) 
                        {
                            String columnName = table.getColumnName(i);
                            String parameter = request.getParameter(columnName);
                            if (parameter != null) 
                            {
                                if (parameter.equals("assign"))
                                    session.setAttribute("assign", columnName);
                                else
                                {
                                    if (parameter.equals("spec"))
                                        session.setAttribute("spec", columnName);
                                    else 
                                    {
                                        if (parameter.equals("session"))
                                            if (session.getAttribute(columnName) != null)
                                                data.put(columnName, (crypto.decode(session.getAttribute(columnName).toString())).trim());
                                    }
                                }
                            }
                        }
                        if(values != null && values.length > 0) 
                        {
                            for(int i = 0; i < values.length; i++) 
                            {
                                data.put(session.getAttribute("assign"), values[i]);
                                data.put(session.getAttribute("spec"), request.getParameter(values[i]+"_"+session.getAttribute("spec")));
                                record.setData(data);
                                try 
                                {
                                    record.insert();
                                    session.setAttribute((String)session.getAttribute("assign"), crypto.encode(values[i]));
                                    logWrite(response, session);
                                    _alert = response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertSucessInsert");
                                }
                                catch(Exception e)
                                {
                                    log.error(response.getLocaleString("error_DataBaseResource_doViewInsert_getTableGeneric") + " " + getResourceBase().getAttribute("table") + " " + response.getLocaleString("error_DataBaseResource_doViewInsert_recordInsert") + " " +values[i], e);
                                }
                            }
                        }
                        //TODO:
                        //if (base.getAttribute("sync") != null)
                        //    DBTopicMgr.getInstance().refresh();
                    }
                    catch(SQLException sql) 
                    {
                        log.error(response.getLocaleString("error_DataBaseResource_doViewInsert_getTableGeneric" + getResourceBase().getAttribute("table")), sql);
                    }
                }
                else
                    _alert = response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertEmptyInsert");
            }            
        }
        else if ("update".equals(queryType))
        {
            try 
            {
                boolean page = false;
                RecordGeneric prev = (RecordGeneric)session.getAttribute("record");
                prev.load();
                args = prev.getData();
                HashMap attrusr = prev.getData();
                HashMap backusr = new HashMap();
                QueryGeneric option = new QueryGeneric(base.getAttribute("dbcon"));
                TableGeneric table = (TableGeneric)session.getAttribute("tbl");
                RecordGeneric record = new RecordGeneric(table);
                int numcol = table.getLength(); 
                String value=""; 
                for (int i=1; i<numcol+1; i++) 
                {
                    if (table.getColumnType(i)==Types.LONGVARBINARY || table.getColumnType(i)==Types.BLOB) 
                    {
                        String columnName = table.getColumnName(i);
                        value=acquireParameter(fUpload, columnName);   
                        if (value!=null) 
                        {
                            byte[] file=fUpload.getFileData(columnName);
                            if (file.length>0) 
                                attrusr.put(columnName, file);
                            else
                            {
                                if (table.getColumnType(i)==Types.LONGVARBINARY) 
                                {
                                    InputStream in = (InputStream)args.get(columnName);
                                    if (in != null) 
                                    {
                                        int size = in.available();
                                        byte bytes[]=new byte[size];
                                        size = in.read(bytes);
                                        attrusr.put(columnName, bytes);
                                    }
                                }
                                else 
                                {
                                    if (table.getColumnType(i)==Types.BLOB) 
                                    {
                                        Blob blob = (Blob)args.get(columnName);
                                        if (blob != null) 
                                        {
                                            long size = blob.length();
                                            byte bytes[]=blob.getBytes(1,(int)size);
                                            attrusr.put(columnName, bytes);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        String columnName = table.getColumnName(i);
                        String parameter = acquireParameter(fUpload, columnName);   
                        if (table.getColumnName(i).equals("password")) 
                        {
                            if (parameter != null)
                                parameter = SWBUtils.CryptoWrapper.passwordDigest(parameter);
                            else parameter = (String)args.get(columnName);
                        }
                        if (table.getColumnType(i)==Types.DATE) 
                        {
                            if (parameter != null && !"".equals(parameter)) 
                            {
                                backusr.put(columnName, acquireParameter(fUpload, columnName));
                                if (parameter.indexOf('/') != -1)
                                    parameter = getDate(parameter);
                            }
                            else 
                            {
                                if (prev.getData().get(columnName) != null)
                                    backusr.put(columnName, getDate(prev.getData().get(columnName).toString()));
                            }
                        }
                        if (parameter != null)
                            attrusr.put(columnName, parameter);
                        if (table.getColumnType(i)==Types.TIMESTAMP) 
                        {
                            if (parameter == null)
                                attrusr.put(columnName, args.get(columnName));
                            else attrusr.put(columnName, new Timestamp(System.currentTimeMillis()));
                        }
                        if (session.getAttribute(columnName) != null)
                            attrusr.put(columnName, args.get(columnName));
                        if (parameter!=null && args.get(columnName)!=null) 
                        {
                            if (!parameter.equals(args.get(columnName).toString())) 
                            {
                                session.setAttribute("data", attrusr);
                                if (categoryExist(session, columnName, option) && propertyNesting(session, columnName)) 
                                {
                                    args.put(columnName, parameter);
                                    Document docResult=null;
                                    if(!"".equals(base.getAttribute("filex", "").trim()))
                                        docResult = processInit(session, response);
                                    else docResult = getDocument(session, response);
                                    if(docResult!=null) session.setAttribute("docResult", docResult);
                                    page = true;
                                }
                            }
                        }
                    }
                }
                if (page == false) 
                {
                    if (session.getAttribute("page") == null) 
                    {
                        record.setData(attrusr);
                        record.update();
                        session.setAttribute("Update", "2");
                        logWrite(response, session);
                        query = "<script>alert('" + response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertSucessUpdate") + ".')</script>";
                        
                        //TODO:
                        //if (base.getAttribute("sync") != null)
                        //{
                        //    response.getWebPage().getWebSite().removeMergeMap("references");
                        //    DBTopicMgr.getInstance().refresh();
                        //}
                    }
                    else 
                    {
                        session.removeAttribute("page");
                        attrusr = prev.getData();
                    }
                }
                if (!backusr.isEmpty()) {
                    Set keys = backusr.keySet();
                    Iterator it = keys.iterator();
                    while (it.hasNext()) {
                        String column = (String)it.next();
                        attrusr.put(column, backusr.get(column));
                    }
                } 
                session.setAttribute( "attrusr", attrusr);                
            }
            catch(Exception e) 
            {
                if (!"".equals(getException(e, response)))
                    query += "<script>alert('" + getException(e, response) + ".')</script>";
                else
                    query += "<script>alert('" + response.getLocaleString("error_DataBaseResource_doViewUpdate_updateRecord") + " " + base.getId() +  ".')</script>";
                if (e != null)
                    log.error(response.getLocaleString("error_DataBaseResource_doViewUpdate_updateRecord") + " " + base.getId() + " " + e.getMessage());
                log.error(response.getLocaleString("error_DataBaseResource_doViewUpdate_updateRecord") + " " + base.getId(), e);
                session.setAttribute("Update", "1");
            }            
        }
        else if ("delete".equals(queryType))
        {
            int result = 0;
            String key = new String();
            try 
            {
                TableGeneric table = new TableGeneric(base.getAttribute("table"), base.getAttribute("dbcon"));
                String nameKey = base.getAttribute("namekey");
                if (nameKey.indexOf(',') != -1) 
                {
                    String [] keys = nameKey.split(",");
                    for (int i=0; i<keys.length; i++) 
                    {
                        if (request.getParameter(keys[i]) != null) 
                        {
                            key = crypto.decode(request.getParameter(keys[i]));
                            key = key.trim();
                            table.addPrimaryKey(keys[i]);
                            data.put(keys[i], key);
                            session.setAttribute(keys[i],request.getParameter(keys[i]));
                        }
                    }
                }
                else 
                {
                    if (request.getParameter(nameKey) != null) 
                    {
                        key = crypto.decode(request.getParameter(nameKey));
                        key = key.trim();
                        table.addPrimaryKey(nameKey);
                        data.put(nameKey, key);
                        session.setAttribute(nameKey,request.getParameter(nameKey));
                    }
                }
                RecordGeneric record = new RecordGeneric(table);
                record.setData(data);
                result = record.remove();
                if (result != 0) 
                {
                    logWrite(response, session);
                    session.setAttribute("page", "false");
                    alert = "alert('" + response.getLocaleString("usrmsg_DataBaseResource_doViewDelete_msgAlertSucessDelete") + ".')";
                    //TODO:
                    //if (base.getAttribute("sync") != null)
                    //    DBTopicMgr.getInstance().refresh();
                }
                else  alert = "alert('" + response.getLocaleString("usrmsg_DataBaseResource_doViewDelete_msgAlertErrorDelete") + ".')";
            }
            catch(Exception e)
            {
                log.error(response.getLocaleString("error_DataBaseResource_doViewDelete_deleteRecord"), e);
                alert = "alert('" + response.getLocaleString("error_DataBaseResource_doViewDelete_deleteRecord") + ".')";
            }            
        }
        session.setAttribute("alert", alert);
        session.setAttribute("query", query);
        response.setRenderParameter("alert", alert);
        response.setRenderParameter("query", query);
        if (!"".equals(_msg)) {
            response.setRenderParameter("_msg", _msg);
            response.setRenderParameter("_alert", _alert);
        }
    }
    
    /**
	 * Obtiene el mensaje de excepcion para el usuario.
	 *
	 * @return <b>String</b> Mensaje de excepcion.
	 *
	 * @param exception Exception capturado al realizar la operacion sql.
	 */
	public String getException(Exception exception, SWBActionResponse response) throws SWBResourceException {
	    String message = new String();
	    Resource base = getResourceBase();
	    if (!"java.lang.NullPointerException".equals(exception.toString())) {
	        if (exception.getMessage().startsWith("Invalid argument value: Duplicate entry")) {
	            if (new EWUtils().propertyExist(base.getAttribute("dbcon")+"."+base.getAttribute("table")+"."+"duplicate", "catalogos"))
	                message = ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(base.getAttribute("dbcon")+"."+base.getAttribute("table")+"."+"duplicate");
	            else 
	                message = response.getLocaleString("usrmsg_DataBaseResource_doView_msgAlertErrorInsert");
	        }else{
	            if (exception.getMessage().startsWith("Syntax error or access violation: You have an error in your SQL syntax near"))
	                message = response.getLocaleString("usrmsg_DataBaseResource_doView_msgSyntaxError");
	            else {
	                if (exception.getMessage().startsWith("General error: Table"))
	                    message = response.getLocaleString("usrmsg_DataBaseResource_doView_msgErrorTableDoesntExist");
	                else{
	                    if (exception.getMessage().startsWith("java.lang.ClassCastException"))
	                        message = response.getLocaleString("usrmsg_DataBaseResource_doView_msgErrorTableClassCast");
	                    else{
	                        if (exception.getMessage().startsWith("Column not found: Unknown column"))
	                            message = response.getLocaleString("usrmsg_DataBaseResource_doView_msgErrorColumnNotFound");
	                        else {
	                            if (new EWUtils().propertyExist(base.getAttribute("dbcon")+"."+base.getAttribute("table")+"."+exception.getMessage(), "catalogos"))
	            	                message = ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(base.getAttribute("dbcon")+"."+base.getAttribute("table")+"."+exception.getMessage().replaceAll(" ","_"));
	                        }
	                    }
	                }
	            }
	        }
	    }
	    return message;
	}
    
    public void logWrite(SWBParamRequest paramRequest, HttpSession session) {
	    if (new EWUtils().propertyExist(getResourceBase().getAttribute("dbcon")+".log", "catalogos")) {
	        try {
	            TableGeneric log = new TableGeneric(ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(getResourceBase().getAttribute("dbcon")+".log"), getResourceBase().getAttribute("dbcon"));
	            RecordGeneric logrec = new RecordGeneric(log);
	            HashMap logdata = new HashMap();
	            logdata.put("wbuser", paramRequest.getUser().getId());
	            logdata.put("action", getResourceBase().getAttribute("queryType"));
	            if (getResourceBase().getAttribute("queryType").equals("insert") && session.getAttribute("assign") == null)
	                logdata.put("description", getResourceBase().getAttribute("dbcon")+":"+getResourceBase().getAttribute("table")+":"+getLastPK(session));
	            else
	                logdata.put("description", getResourceBase().getAttribute("dbcon")+":"+getResourceBase().getAttribute("table")+":"+getPK(session));
	            logdata.put("date", new Timestamp(System.currentTimeMillis()).toString());
	            logrec.setData(logdata);
	            logrec.insert();
	        }catch (Exception e) {
	            log.error("error while write to log", e);
	        }
	    }
	}

	public void logWrite(SWBActionResponse response, HttpSession session) {
	    if (new EWUtils().propertyExist(getResourceBase().getAttribute("dbcon")+".log", "catalogos")) {
	        try {
	            TableGeneric log = new TableGeneric(ResourceBundle.getBundle("catalogos", Locale.ENGLISH).getString(getResourceBase().getAttribute("dbcon")+".log"), getResourceBase().getAttribute("dbcon"));
	            RecordGeneric logrec = new RecordGeneric(log);
	            HashMap logdata = new HashMap();
	            logdata.put("wbuser", response.getUser().getId());
	            logdata.put("action", getResourceBase().getAttribute("queryType"));
	            if (getResourceBase().getAttribute("queryType").equals("insert") && session.getAttribute("assign") == null)
	                logdata.put("description", getResourceBase().getAttribute("dbcon")+":"+getResourceBase().getAttribute("table")+":"+getLastPK(session));
	            else
	                logdata.put("description", getResourceBase().getAttribute("dbcon")+":"+getResourceBase().getAttribute("table")+":"+getPK(session));
	            logdata.put("date", new Timestamp(System.currentTimeMillis()).toString());
	            logrec.setData(logdata);
	            logrec.insert();
	        }catch (Exception e) {
	            log.error( "error while write to log", e);
	        }
	    }
	} 
    
    public static ArrayList toArray(String str, String regex) 
    {
        ArrayList ret= new ArrayList();
        if(str!=null)
        {
            String[] obj=str.split(regex);
            for(int i=0; i < obj.length; i++)
                ret.add(obj[i]);
        }
        return ret;
    }
    
    public String getTypeData(int typedata) {
        String type = new String();
        switch(typedata) {
        	case java.sql.Types.BIT:							//-7 
        	case 16:											//java.sql.Types.BOOLEAN:
        	    type = "Boolean";
            break;
        	case java.sql.Types.TINYINT:						//-6 
        	    type = "Byte";
        	break;
        	case java.sql.Types.BIGINT:							//-5 
        	    type = "Long";
        	break;
        	case java.sql.Types.LONGVARBINARY:					//-4
        	    type = "BinaryStream";
        	break; 
        	case java.sql.Types.VARBINARY:						//-3 
        	case java.sql.Types.BINARY:							//-2 
        	    type = "Bytes";
        	break;
        	case java.sql.Types.LONGVARCHAR:					//-1 
        	    type = "AsciiStream";
        	break;
        	case java.sql.Types.NULL:							//0
        	    type = "Null";
        	break;
        	case java.sql.Types.CHAR:							//1
        	case java.sql.Types.VARCHAR:						//12
        	    type = "String";
        	break;
        	case java.sql.Types.NUMERIC:						//2
        	case java.sql.Types.DECIMAL:						//3
        	    type = "BigDecimal";;
        	break;
        	case java.sql.Types.INTEGER:						//4
        	    type = "Integer";
        	break;
        	case java.sql.Types.SMALLINT:						//5
        	    type = "Short";
        	break;
        	case java.sql.Types.FLOAT:							//6
        	case java.sql.Types.DOUBLE:							//8
        	    type = "Double";
        	break;
        	case java.sql.Types.REAL:							//7
        	    type = "Float";
        	break;
        	case 9:
        	case java.sql.Types.DATE:							//91
        	    type = "Date";
        	break;
        	case 10:
        	case java.sql.Types.TIME:							//92
        	    type = "Time";
        	break;
        	case 11:
        	case java.sql.Types.TIMESTAMP:						//93
        	    type = "Timestamp";
        	break;
        	case java.sql.Types.BLOB:							//2004 
        	    type = "Blob";
        	break;
        	case java.sql.Types.CLOB:							//2005 
        	    type = "Clob";
        	break;
        	default:
        	    type = ""+typedata;
        	break;
        }
        return type;
    }
    
    /**
	 * Obtiene incremento sobre identificadores num�ricos.
	 *
	 * @return <b>String</b> incremento.
	 * 
	 * @param key Campo de la tabla que contiene el identificador
	 */
	public String getNextId(String key) {
	    String id = "";
	    Resource base = getResourceBase();
	    String max = "select MAX(" + key +") as id from " + base.getAttribute("table");
	    QueryGeneric query = new QueryGeneric(base.getAttribute("dbcon"));
	    Enumeration e = query.getResults(max);
	    while (e.hasMoreElements()) {
	        HashMap results = (HashMap)e.nextElement();
	        id = (String)results.get("id");
	    }
	    if(id==null || (id!=null && id.trim().equals(""))) id="1";
	    Long increment = new Long(new Long(id).longValue() + 1);
	    return increment.toString();
	}       
    
	public String getRandom(String key)  
	{
	    String id="";
            if(key!=null && !"".equals(key.trim()))
            {
                Resource base = getResourceBase();
                IDGenerator idgenerator = new IDGenerator();
                while(true)
                {
                    id=idgenerator.getID();
                    String qry = "SELECT " + key +" FROM " + base.getAttribute("table") +" WHERE "+ key +"="+id;
                    QueryGeneric query = new QueryGeneric(base.getAttribute("dbcon"));
                    Enumeration e = query.getResults(qry);
                    if (!e.hasMoreElements()) break;
                }
            }
            return id;
	}          
        
	public String getHash()  
	{
	    int id=0;
            Resource base = getResourceBase();
            String qry = "SELECT COUNT(*) as counter FROM " + base.getAttribute("table");
            QueryGeneric query = new QueryGeneric(base.getAttribute("dbcon"));
            Enumeration e = query.getResults(qry);
            if (e.hasMoreElements())
            {
                HashMap results = (HashMap)e.nextElement();
                try { id = Integer.parseInt((String)results.get("counter")); }
                catch(Exception ex){ id=0;}
                id=(new String(base.getAttribute("dbcon") + base.getAttribute("table"))).hashCode() + id;
            }
            return String.valueOf(id);
	}
	
    /**
	 * Determina si existe propiedad de anidamiento
	 *
	 * @return <b>boolean</b> true si existe propiedad de anidamiento. false en caso contrario.
	 * 
	 * @param session Sesion que reliza la petici�n.
	 * @param columnName Nombre del campo.
	 */
	public boolean propertyNesting(HttpSession session, String columnName) {
	    boolean property = false;
	    Document docResult = (Document)session.getAttribute("document");
	    Node listadoc = docResult.getFirstChild();
		NodeList hijos = listadoc.getChildNodes();
		for(int i = 0; i < hijos.getLength(); i++) {
			Node node = hijos.item(i);
			NamedNodeMap Attrs;
			Attr att;
			if(!node.getNodeName().equalsIgnoreCase("select") && !node.getNodeName().equalsIgnoreCase("radioset"))
				continue;
			Attrs = node.getAttributes();
			att = (Attr)Attrs.getNamedItem("NOMBRE");
			if (att.getValue().equals(columnName)) {
			    att = (Attr)Attrs.getNamedItem("ANIDADO");
			    if (att != null) {
			        property = true;
			    }
			}
		}
		return property;
	}
    
    /**
	 * Regresa la clave primaria que debera guardarse en el log.
	 *
	 * @return <b>String</b> Clave primaria.
	 *
	 * @param HttpSession session actual.
	 */
    public String getLastPK(HttpSession session) {
        String id = new String();
        String key = new String();
        String condition = new String();
        Resource base = getResourceBase();
        String dbcon = base.getAttribute("dbcon");
        String namekey = base.getAttribute("namekey");
        String pass = "05fe858d86df4b909a8c87cb8d9ad596";
        QueryGeneric search = new QueryGeneric(dbcon);
        byte word[] = new BigInteger(pass, 16).toByteArray();
        Encryptor crypto = new Encryptor(word);
        try {
            if (namekey.indexOf(',') == -1) {
                id = "SELECT MAX(" + namekey + ") AS " + base.getAttribute("namekey") + " FROM " + base.getAttribute("table");
                search.setQuery(id);
                Enumeration e = search.getResults(id);
                while (e.hasMoreElements()) {
                    HashMap results = (HashMap)e.nextElement();
                    key = (String)results.get(namekey);
                }
                namekey += ":"+crypto.encode(key);
            }else {
                String [] keys = namekey.split(",");
    			for (int i=0; i<keys.length; i++) {
    			    if (session.getAttribute(keys[i]) != null) {
    			        String attribute = (String)session.getAttribute(keys[i]);
    			        condition += " AND " + keys[i] + "=" + attribute;
    			    }else {
    			        id = "SELECT MAX(" + keys[i] + ") AS " + keys[i] + " FROM " + base.getAttribute("table") + " WHERE ";
    			        namekey = keys[i];
    			    }
    			}
    			id += condition.substring(5);
                search.setQuery(id);
                Enumeration e = search.getResults(id);
                while (e.hasMoreElements()) {
                    HashMap results = (HashMap)e.nextElement();
                    key = (String)results.get(namekey);
                }
                namekey += ":"+crypto.encode(key);
            }
        }catch (Exception e) {
            return "";
        }
        return namekey;
    }
    
    public Hashtable getAttributes(String special) {
        Hashtable attributes = new Hashtable();
        if (special.indexOf("=") != -1) {
            Iterator it = toArray(special.trim(), " ").iterator();
            while (it.hasNext()) {
                String attribute = (String)it.next();
                if (attribute.indexOf("=") != -1) {
                    Iterator at = toArray(attribute.trim(), "=").iterator();
                    while (at.hasNext()) {
                        String attName = (String)at.next();
                        if (at.hasNext()) {
                            String attValue = ((String)at.next()).replaceAll("\"", "");
                        	attributes.put(attName, attValue);
                        }
                    }
                }
            }
        }
        return attributes;
    }
    
    /**
	 * Regresa el uri al que debera dirigirse la presentacion del recurso.
	 *
	 * @return <b>String</b> uri.
	 *
	 * @param request asociado a la peticion del cliente.
	 */
    public String getUri(String uri) {
        uri += "<form name=frmDesc method=\"POST\" action=" + uri + ">";
        uri += "<SCRIPT  LANGUAGE=\"JavaScript\"> document.frmDesc.submit();";
        uri += "alert('" + SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.DataBaseResource", "usrmsg_DataBaseResource_doView_msgAlertSucessInsert") + ".')";
        uri += "</SCRIPT>";
        uri += "</form>";
        return uri;
    }
    
    /**
	 * Remueve atributos de la sessi�n.
	 *
	 * @param session Sesi�n actual del usuario.
	 * @param ckey Clave(s) primarias de la tabla de base de datos.
	 */
    private void clear(HttpSession session, String ckey) {
	    if (ckey.indexOf(',') != -1) {
	        String [] keys = ckey.split(",");
	        for (int i=0; i<keys.length; i++) {
	            if (session.getAttribute(keys[i]) != null)
	                session.removeAttribute(keys[i]);
	        }
	    }else {
	        if (session.getAttribute(ckey) != null)
	            session.removeAttribute(ckey);
	    }
	}
    
    /**
	 * Recorre el par�metro fecha para regresar la solicitada por el SMBD.
	 *
	 * @return <b>String</b> Fecha solicitada por el SMBD.
	 *
	 * @param fecha captura del formulario.
	 */
    public String getDate(String date) {
        String converse = new String();
        if (date != null) {
            if (date.indexOf('/') != -1) {
                String [] parse = date.split("/");
                converse = parse[2] + "-" + parse[1] + "-" + parse[0];
            }
            if (date.indexOf('-') != -1) {
                String [] parse = date.split("-");
                converse = parse[2] + "/" + parse[1] + "/" + parse[0];
            }
        }
        return converse;
    }
    
	/**
	 * Regresa la clave primaria que debera guardarse en el log.
	 *
	 * @return <b>String</b> Clave primaria.
	 *
	 * @param HttpSession session actual.
	 */
    public String getPK(HttpSession session) {
        String namekey = getResourceBase().getAttribute("namekey");
        try {
            if (namekey.indexOf(',') == -1) {
                if (session.getAttribute(namekey) != null)
                    namekey += ":" + (String)session.getAttribute(namekey);
            }else {
                String [] keys = namekey.split(",");
                namekey = new String();
    			for (int i=0; i<keys.length; i++) {
    			    if (session.getAttribute(keys[i]) != null)
    			        namekey += ":" + keys[i] + ":" + (String)session.getAttribute(keys[i]);
    			}
    			namekey = namekey.substring(1);
            }
        }catch (Exception e) {
            return "";
        }
        return namekey;
    }
	
	/**
	 * Regresa el formato para presentaci�n de tipo dinero.
	 *
	 * @return <b>String</b> money.
	 *
	 * @param cadena que represanta cantidad de un n�mero real.
	 */
    public String getMoney(String count) {
        String temp = new String();
        String cents = new String();
        if (count.indexOf(".") != -1) {
            cents = count.substring(count.indexOf("."));
            temp = count.substring(0, count.indexOf("."));
        }else
            temp = count;
        if (temp.length() < 4)
            temp = temp + cents;
        else {
            switch (temp.length()) {
            	case 4: 
            	    return temp.substring(0,1) + "," + temp.substring(1,temp.length()) + cents;
            	case 5:
            	    return temp.substring(0,2) + "," + temp.substring(2,temp.length()) + cents;
            	case 6:
            	    return temp.substring(0,3) + "," + temp.substring(3,temp.length()) + cents;
            	case 7:
            	    return temp.substring(0,1) + "," + temp.substring(1,4) + "," + temp.substring(4,temp.length()) + cents;
            	case 8:
            	    return temp.substring(0,2) + "," + temp.substring(2,5) + "," + temp.substring(5,temp.length()) + cents;
            	case 9:
            	    return temp.substring(0,3) + "," + temp.substring(3,6) + "," + temp.substring(6,temp.length()) + cents;            		
            }
        }
        return temp;
    }
    
    /**
	 * Verifica si existe la instancia de DBTopics correspondiente.
	 *
	 * @param paramRequest Parametros asociados con la peticion del cliente.
	 * 
	 */
    public String getIntegrityMsg(SWBParamRequest paramRequest) {
        Iterator<Resource> dbresource = paramRequest.getWebPage().getWebSite().listResources();
        while (dbresource.hasNext()) {
            Resource recresource = dbresource.next();
            try {
                ResourceType recresourcetype = recresource.getResourceType();
                String clase = recresourcetype.getClass().getName();
                if (clase.equals("com.infotec.wb.resources.database.DBTopics")) {
                    //Resource resource = new Resource(recresource);
                    if (recresource.getAttribute("table").equals(getResourceBase().getAttribute("table"))) {
                        if (!recresource.isDeleted())
                            return (paramRequest.getLocaleString("usrmsg_DataBaseResource_doGetAdmResume_msgSemanticDelete"));
                        if (!recresource.isActive())
                            return (paramRequest.getLocaleString("usrmsg_DataBaseResource_doGetAdmResume_msgSemanticActive"));
                        return "on";
                    }
                }
            }catch (Exception e) {
                continue;
            }
        }
        return "";
    }
}
class ParamKeys {
    private String Active = "0";
    private String Label = "";
    private String Order = "";
    private String Size = "";
    private String Message = "";
    private String Control = "";
    private String Elements = "";
    private String Required = "0";
    private String Special = "";
    private String Name = "";
    private String Type = "";
    private String Length = "";
    
    public ParamKeys(String active, String label, String order, String size, String message,
            String control, String elements, String required, String special, String name) {
        setActive(active);
        setLabel(label);
        setOrder(order);
        setSize(size);
        setMessage(message);
        setControl(control);
        setElements(elements);
        setRequired(required);
        setSpecial(special);
        setName(name);
    }
    
    public ParamKeys() {
    }
    
    
    public String getLabel() {
        return Label;
    }
    
    public void setLabel(String label) {
        Label = label;
    }
    
    public String getActive() {
        return Active;
    }
    
    public void setActive(String active) {
        Active = active;
    }
    
    public void setDataType(String type) {
        Type = type;
    }
    
    public void setMaxLenght(String length) {
        Length = length;
    }
    
    public String getOrder() {
        return Order;
    }
    
    public void setOrder(String order) {
        Order = order;
    }
    
    public String getSize() {
        return Size;
    }
    
    public void setSize(String size) {
        Size = size;
    }
    
    public String getMessage() {
        return Message;
    }
    
    public void setMessage(String message) {
        Message = message;
    }
    
    public String getControl() {
        return Control;
    }
    
    public void setControl(String control) {
        Control = control;
    }
    
    public String getElements() {
        return Elements;
    }
    
    public void setElements(String elements) {
        Elements = elements;
    }
    
    public String getRequired() {
        return Required;
    }
    
    public void setRequired(String required) {
        Required = required;
    }
    
    public String getSpecial() {
        return Special;
    }
    
    public void setSpecial(String special) {
        Special = special;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getDataType() {
        return Type;
    }
    
    public String getMaxLenght() {
        return Length;
    }
}

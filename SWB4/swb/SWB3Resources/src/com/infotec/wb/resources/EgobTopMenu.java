/*
 * EgobTopMenu.java
 *
 * Created on 20 de junio de 2002, 16:38
 */

package com.infotec.wb.resources;

import com.infotec.appfw.util.AFUtils;
import org.semanticwb.portal.util.FileUpload;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.User;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;

/** Objeto que se encarga de desplegar y administrar un archivo de menu de canales de Egobierno bajo ciertos
 * criterios(configuración de recurso).
 *
 * Object that is in charge to unfold and to administer a file of menu of channels
 * of Egobierno under certain criteria (resource configuration).
 * @author :Jorge Alberto Jiménez Sandoval (JAJS)
 * @version 1.0
 * @see com.infotec.wb.core.db.RecResource
 * @see com.infotec.wb.core.Resource
 */

public class EgobTopMenu extends GenericResource
{
    /**
     * Información del recurso en memoria.
     *
     * @see     com.infotec.wb.core.Resource
     */
    Resource base = null;
    ResourceType recobj = null;
    String menu = null;
    String strRes = "";
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    private static Logger log = SWBUtils.getLogger(EgobTopMenu.class);

  
    /**
     * Asigna la información de la base de datos al recurso.
     *
     * @param     base  La información del recurso en memoria.
     */
    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            super.setResourceBase(base);
            this.base = base;
            strRes = (String) SWBPlatform.getEnv("wb/admresource");
            Document dom = base.getDom();
            if (dom == null) throw new SWBResourceException("Dom nulo");
            NodeList file = dom.getElementsByTagName("file");
            recobj = base.getResourceType();
            String sret = null;
            if (file.getLength() > 0)
            {
                String sfile = file.item(0).getChildNodes().item(0).getNodeValue();
                String resWorkPath=SWBPortal.getWorkPath()+base.getWorkPath()+ "/" + sfile;
                sret = SWBUtils.IO.getFileFromPath(resWorkPath);
                if (sret != null)
                    menu = SWBPortal.UTIL.parseHTML(sret, SWBPortal.getWebWorkPath() + base.getWorkPath() + "/images/");
            }

        } catch (Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Document dom = base.getDom();
        if (dom == null) throw new SWBResourceException("Dom nulo");
        PrintWriter out=response.getWriter();
        out.println(menu);
    }



    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws IOException, SWBResourceException
    {
        PrintWriter out=response.getWriter();
        User user=paramsRequest.getUser();
        String action=paramsRequest.getAction();
        StringBuffer sbfRet = new StringBuffer();
        String webpath = (String) SWBPlatform.getContextPath();
        String strTemplate = null;
        String strAlert = "\n<font color=#FF0000><blink><kbd><code><i>[Alerta]</i></code></kbd></blink></font>";
        String strError = strAlert + " \nError en la definición del recurso <i>" + base.getTitle() + "</i> de tipo Menu con identificador <i>" + base.getId() + "</i>";
        if (user == null || (user != null && !user.isSigned()))
        {
            out.println("<html><body><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">Para tener acceso a la administración del recurso debe estar logeado.</font></body></html>");

        }else
        {
            if (action.equals(paramsRequest.Action_ADD) || action.equals(paramsRequest.Action_EDIT))
            { // Agregar o editar recurso
                if (action.equals("edit"))
                {
                    Document dcmDom = base.getDom();
                    if (dcmDom != null)
                    {
                        try
                        {
                            NodeList ndlTemplate = dcmDom.getElementsByTagName("file");
                            if (ndlTemplate.getLength() > 0) strTemplate = ndlTemplate.item(0).getChildNodes().item(0).getNodeValue().trim();
                        } catch (Exception e)
                        {
                            AFUtils.log(e, "Error en recurso EgobTopMenu al traer el AdmHtml", true);
                        }
                    }
                }
                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#CCCCCC\">");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td>");
                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#FFFFFF\">");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td>");
                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td><font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#666699\"><img src=\"" + webpath + "admin/images/ico_agregar.gif\" width=\"17\" height=\"17\"> ");
                sbfRet.append("\nDatos generales del recurso.</font></td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n<tr> ");
                sbfRet.append("\n<td bgcolor=\"#979FC3\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#FFFFFF\">");
                sbfRet.append("\nProporcione los datos que se solicitan a continuación:</font></td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n<tr> ");
                sbfRet.append("\n<td> ");
                sbfRet.append("\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
                SWBResourceURL urlAdmin=paramsRequest.getRenderUrl();
                urlAdmin.setMode(urlAdmin.Mode_ADMIN);
                urlAdmin.setAction("update");
                sbfRet.append("\n<form name=\"frmResource\" method=\"POST\"  enctype=\"multipart/form-data\" action=\"" + urlAdmin +"\">");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td align=\"right\" valign=\"top\"><a href=\"#\" onClick=\"javascript:openHelp(" + base.getId() + ", 1)\">");
                sbfRet.append("\n* Template</a> <font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"1\">(htm, html):</font></td>");
                sbfRet.append("\n<td align=\"left\" valign=\"top\"><input type=file size=50 name=fileImg onChange=\"isFileType(this, 'htm|html');\">");
                if (strTemplate != null) sbfRet.append("\n<br><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"-1\">Template actual <i>" + strTemplate + "</i></font>");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td colspan=2 align=center>");
                sbfRet.append("\n<input type=\"submit\" name=\"btnSave\" value=\"GUARDAR\" onClick=\"if(jsValida(this.form)) return true; else return false;\" style=\"color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal\">");
                sbfRet.append("\n<input type=\"reset\" name=\"btnReset\" value=\"CANCELAR\" style=\"color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal\">");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n</form>");
                sbfRet.append("\n<tr>");
                sbfRet.append("\n<td colspan=2 align=left><br>");
                sbfRet.append("\n<font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#666699\" size=\"1\"><b>");
                sbfRet.append("\n* Datos requeridos.</b></font>");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n</table>");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n</table>");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n</table>");
                sbfRet.append("\n</td>");
                sbfRet.append("\n</tr>");
                sbfRet.append("\n</table>");
                sbfRet.append("\n<script>");
                sbfRet.append("\nfunction jsValida(pForm)");
                sbfRet.append("\n{");
                if (action.equals(paramsRequest.Action_ADD))
                {
                    sbfRet.append("\n   if(pForm.fileImg.value==null || pForm.fileImg.value=='' || pForm.fileImg.value==' ')");
                    sbfRet.append("\n   {");
                    sbfRet.append("\n       alert('Debe definir un template.');");
                    sbfRet.append("\n       pForm.fileImg.focus();");
                    sbfRet.append("\n       return false;");
                    sbfRet.append("\n   }");
                }
                sbfRet.append("\n   if(!isFileType(pForm.fileImg, 'htm|html')) return false;");
                sbfRet.append("\n   return true;");
                sbfRet.append("\n}");
                sbfRet.append(admResUtils.loadIsFileType());
                sbfRet.append(admResUtils.loadOpenHelp());
                sbfRet.append("\n</script>");
                sbfRet.append(admResUtils.loadHtmlStyles());
            } else
            { // Actualizar recurso
                if (action.equals("update"))
                {
                    String strFileImg = null, strFileParsed = null;
                    boolean bError = false;
                    try
                    {
                        FileUpload fUpload = new FileUpload();
                        fUpload.getFiles(request, response);

                        Document dom = base.getDom();
                        if (dom != null)
                        {
                            NodeList ndlTemplate = dom.getElementsByTagName("file");
                            if (ndlTemplate.getLength() > 0) strTemplate = ndlTemplate.item(0).getChildNodes().item(0).getNodeValue();
                        }

                        if ((fUpload.getFileName("fileImg") != null && !fUpload.getFileName("fileImg").trim().equals("")) ||
                                strTemplate != null)
                        {
                            try
                            {
                                DocumentBuilderFactory dcmBdrFty = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dcmBdr = dcmBdrFty.newDocumentBuilder();
                                Document dcmDom = dcmBdr.newDocument();
                                Element emnRsc = dcmDom.createElement("resource");
                                dcmDom.appendChild(emnRsc);

                                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#CCCCCC\">");
                                sbfRet.append("\n<tr>");
                                sbfRet.append("\n<td>");
                                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" bgcolor=\"#FFFFFF\">");
                                sbfRet.append("\n<tr>");
                                sbfRet.append("\n<td>");
                                sbfRet.append("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
                                sbfRet.append("\n<tr>");
                                sbfRet.append("\n<td><font face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#666699\"><img src=\"" + webpath + "admin/images/ico_agregar.gif\" width=\"17\" height=\"17\"> ");
                                sbfRet.append("\nDatos generales del recurso.</font></td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n<tr> ");
                                sbfRet.append("\n<td bgcolor=\"#979FC3\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#FFFFFF\">");
                                sbfRet.append("\nLos datos registrados se presentan a continuación:</font></td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n<tr> ");
                                sbfRet.append("\n<td> ");
                                sbfRet.append("\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");

                                if (fUpload.getFileName("fileImg") != null && !fUpload.getFileName("fileImg").trim().equals(""))
                                {
                                    sbfRet.append("\n<tr>");
                                    sbfRet.append("\n<td align=\"right\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
                                    sbfRet.append("\nTemplate:</font></td>");
                                    sbfRet.append("\n<td align=\"left\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");

                                    strFileImg = fUpload.getFileName("fileImg");
                                    if (strFileImg != null && !strFileImg.trim().equals(""))
                                    {
                                        String strUpImg = admResUtils.getFileName(base, strFileImg);
                                        if (strUpImg != null && !strUpImg.trim().equals(""))
                                        {
                                            if (!admResUtils.isFileType(strUpImg, "htm|html"))
                                            {
                                                bError = true;
                                                sbfRet.append("\n" + strAlert + " El archivo <i>" + strUpImg + "</i> no corresponde a ninguna de las extensiones soportadas <i>htm, html</i>");
                                            } else
                                            {
                                                strFileParsed = admResUtils.uploadFileParsed(base, fUpload, "fileImg", request.getSession().getId());
                                                if (strFileParsed != null && !strFileParsed.trim().equals(""))
                                                {
                                                    Element emnTemplate = dcmDom.createElement("file");
                                                    emnTemplate.appendChild(dcmDom.createTextNode(strUpImg));
                                                    emnRsc.appendChild(emnTemplate);
                                                    sbfRet.append("\nSe subio con éxito el archivo <i>" + strFileImg + "</i>");
                                                } else
                                                {
                                                    bError = true;
                                                    sbfRet.append("\n" + strAlert + " No se realizarón correctamente los cambios al subir el archivo <i>" + strFileImg + "</i>");
                                                }
                                            }
                                        } else
                                        {
                                            bError = true;
                                            sbfRet.append("\n" + strAlert + " No se realizarón correctamente los cambios al subir el archivo <i>" + strFileImg + "</i>");
                                        }
                                    } else
                                    {
                                        bError = true;
                                        sbfRet.append("\n" + strAlert + " No se realizarón correctamente los cambios al subir el archivo del template");
                                    }
                                    sbfRet.append("\n</font></td>");
                                    sbfRet.append("\n</tr>");
                                } else if (strTemplate != null)
                                {
                                    Element emnTemplate = dcmDom.createElement("file");
                                    emnTemplate.appendChild(dcmDom.createTextNode(strTemplate));
                                    emnRsc.appendChild(emnTemplate);
                                    sbfRet.append(strTemplate);
                                    sbfRet.append("\n<tr>");
                                    sbfRet.append("\n<td align=\"right\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
                                    sbfRet.append("\nTemplate:</font></td>");
                                    sbfRet.append("\n<td align=\"left\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
                                    sbfRet.append(strTemplate + "</font></td>");
                                    sbfRet.append("\n</tr>");
                                }

                                sbfRet.append("\n<tr>");
                                sbfRet.append("\n<td align=\"right\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
                                sbfRet.append("\nEstatus:</font></td>");
                                sbfRet.append("\n<td align=\"left\" valign=\"top\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
                                if (!bError)
                                {
                                    base.setXml(SWBUtils.XML.domToXml(dcmDom));
                                    //base.setUpdated(user.getId(), "Se actualizó correctamente el recurso de tipo EgobTopMenu con id: " + base.getId());
                                    sbfRet.append("\nSe actualizó correctamente el recurso <i>" + base.getTitle() + "</i> de tipo EgobTopMenu con identificador <i>" + base.getId() + "</i>");
                                } else
                                    sbfRet.append("\n" + strError + "\n<!-- Error no setXml -->");
                                sbfRet.append("</font></td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n</table>");
                                sbfRet.append("\n</td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n</table>");
                                sbfRet.append("\n</td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n</table>");
                                sbfRet.append("\n</td>");
                                sbfRet.append("\n</tr>");
                                sbfRet.append("\n</table>");

                                if (strFileParsed != null && !strFileParsed.trim().equals("")) sbfRet.append(strFileParsed);
                            } catch (FactoryConfigurationError fce)
                            {
                                bError = true;
                                sbfRet.append("\n<br>" + strError + "<br>\n<!--FactoryConfigurationError " + fce + "-->");
                            } catch (ParserConfigurationException pce)
                            {
                                bError = true;
                                sbfRet.append("\n<br>" + strError + "<br>\n<!--ParserConfigurationException " + pce + "-->");
                            } catch (DOMException DOMe)
                            {
                                bError = true;
                                sbfRet.append("\n<br>" + strError + "<br>\n<!--DOMException " + DOMe + "-->");
                            }
                        } else
                            sbfRet.append("\n<br>" + strError + "<br>\n<!-- Error no template -->");
                    } catch (Exception wbe)
                    {
                        bError = true;
                        sbfRet.append("\n<br>" + strError + "<br>\n<!--AFException " + wbe + "-->");
                    }
                } else
                { // Eliminar archivos del filesystem relacionados al recurso
                    if (action.equals("remove"))
                        sbfRet.append(admResUtils.removeResource(base));
                    else
                        sbfRet.append("\n<br>" + strAlert + " \nOperación sobre el recurso <i>" + base.getTitle() + "</i> de tipo EgobTopMenu con identificador <i>" + base.getId() + "</i> no definida: <i>" + action + "<i><br>");
                }
            }
        }
        out.println(sbfRet.toString());
    }

 }
    

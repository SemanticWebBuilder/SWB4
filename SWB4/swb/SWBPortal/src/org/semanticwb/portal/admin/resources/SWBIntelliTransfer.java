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

import com.hp.hpl.jena.rdf.model.Model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * Clase que exporta e importa elementos que se escojan de un sitio.
 * 
 * @author jorge.jimenez
 * creación:Agosto 2010
 */
public class SWBIntelliTransfer extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);
    /** The PATH. */
    String PATH = SWBPortal.getWorkPath() + "/";
    /** The WEBPATH. */
    String WEBPATH = SWBPortal.getWebWorkPath() + "/sitetemplates/";
    /** The MODELS. */
    String MODELS = PATH + "models/";
    /** The ZIPDIRECTORY. */
    String ZIPDIRECTORY = PATH + "sitetemplates/";



    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("viewmodel")) {
            doViewModel(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("installmodel")) {
            doInstallModel(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("AdvancedMode")) {
            doAdvancedMode(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Do advanced mode.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAdvancedMode(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        String uri = request.getParameter("wsid");
        WebSite site = SWBContext.getWebSite(uri);
        String path = SWBPortal.getWorkPath() + "/";
        String zipdirectory = path + "sitetemplates/";
        LinkedList lListObjts = null;
        //-------------Generación de archivo rdf del sitio especificado----------------
        File file = null;
        try {
            file = new File(zipdirectory + site.getId() + "_tmp.nt");
            FileOutputStream out = new FileOutputStream(file);
            site.getSemanticObject().getModel().write(out, "N-TRIPLE");
            out.flush();
            out.close();
            lListObjts = parseFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setParameter("tmpFile", file.getAbsolutePath());
        urlAction.setParameter("wsUri", site.getURI());
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<legend>"+ paramRequest.getLocaleString("elements2export")+"</legend>");
        out.println("<form name=\"selectItems\" action=\"" + urlAction.setAction("saveAdvanceMode") + "\" method=\"post\">");
        out.println("<p align=\"center\">");
        out.println("<table width=\"60%\" align=\"left\">");
        if (lListObjts != null && lListObjts.size()>0) {
            Iterator itlhmObjts=lListObjts.iterator();
            while (itlhmObjts.hasNext()) {
                String sObjs = (String) itlhmObjts.next();
                out.println("<tr align=\"left\">");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"swbObjs\" value=\""+sObjs+"\">");
                out.println("</td>");
                out.println("<td>");
                out.println(sObjs);
                out.println();
                out.println("</td>");
                out.println("</tr>");
            }
        }
        out.println("</fieldset>");
        out.println("</p>");
        out.println("<tr><td colspan=\"2\">");
        out.println("<fieldset><span>");
        out.println("<table>");
        out.println("<tr><td>");
        out.println("<input type=\"submit\" name=\"send\" value=\""+paramRequest.getLocaleString("send")+"\"/>");
        out.println("    <input type=\"button\" name=\"CheckAll\" value=\""+paramRequest.getLocaleString("checkAll")+"\" onClick=\"checkAll(document.selectItems.swbObjs)\">");
        out.println("    <input type=\"button\" name=\"UnCheckAll\" value=\""+paramRequest.getLocaleString("uncheckAll")+"\" onClick=\"uncheckAll(document.selectItems.swbObjs)\">");
        out.println("    <input type=\"button\" onclick=\"history.go(-1);\" value=\""+paramRequest.getLocaleString("return")+"\"/>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</div>");

        out.println("<script language=\"javascript\">");
        out.println("function checkAll(field)");
        out.println("{");
        out.println("    for (i = 0; i < field.length; i++)     ");
        out.println("    field[i].checked = true ;    ");
        out.println("}");
        out.println("function uncheckAll(field)");
        out.println("{");
        out.println("for (i = 0; i < field.length; i++)");
        out.println("        field[i].checked = false ;");
        out.println("}");
        out.println("</script>");

    }

    /**
     * Parses the file.
     * 
     * @param file the file
     * @return the linked list
     */
    private LinkedList parseFile(File file) {
        LinkedList<String> lListObjts = new LinkedList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line == null || line.trim().length() == 0) {
                    continue;
                }
                String tokenObj = null;
                int contToken = 0;
                boolean givemeObject = false;
                StringTokenizer strTokens = new StringTokenizer(line, " ", false);
                while (strTokens.hasMoreElements()) {
                    String token = strTokens.nextToken();
                    contToken++;
                    if (token == null ||contToken == 4) {
                        continue;
                    }else if (contToken == 2 && token.endsWith("#type>")) { //Predicado que se require, ahora solo hay que ver cual es el objeto
                        givemeObject = true;
                    } else if (givemeObject && contToken == 3 && token.indexOf("#WebSite>")==-1) {
                        int pos = token.indexOf("#");
                        if (pos > -1) {
                            int pos1 = token.indexOf(">", pos + 1);
                            if (pos1 > -1) {
                                tokenObj = token.substring(pos + 1, pos1);
                            }
                        }
                        givemeObject=false;
                    }
                }
                if (tokenObj!=null) {
                    if (!lListObjts.contains(tokenObj)) {
                        lListObjts.add(tokenObj);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            log.error(e);
        }
        return lListObjts;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            if (request.getParameter("msgKey") != null) {
                out.println("<script type=\"text/javascript\">");
                if (request.getParameter("msgKey").equals("siteCreated")) {
                    out.println("parent.addItemByURI(parent.mtreeStore, null, '" + request.getParameter("wsUri") + "');");
                }
                out.println("parent.showStatus('" + paramRequest.getLocaleString(request.getParameter("msgKey")) + "');");
                out.println("</script>");
            }
            SWBResourceURL url = paramRequest.getRenderUrl();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            StringBuffer strbf = new StringBuffer();
            File file = new File(SWBPortal.getWorkPath() + "/sitetemplates/");
            File[] files = file.listFiles();
            urlAction.setAction("upload");
            //out.println("<iframe id=\"templates\">");
            //out.println("<div id=\"vtemplates\" dojoType=\"dijit.TitlePane\" title=\"Templates existentes de Sitios \" class=\"admViewTemplates\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");


            out.println("<script type=\"text/javascript\">"
                    + "dojo.require(\"dojo.parser\");"
                    + "dojo.require(\"dijit.layout.ContentPane\");"
                    + "dojo.require(\"dijit.form.Form\");"
                    + "dojo.require(\"dijit.form.ValidationTextBox\");"
                    + "dojo.require(\"dijit.form.Button\");"
                    + "</script>");

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramRequest.getLocaleString("existTpls") + "</legend>");
            out.println("<form action=\"" + urlAction.toString() + "\" method=\"post\" enctype='multipart/form-data'>");
            out.println("<table width=\"100%\">");
            out.println("<tr align=\"left\">");
            out.println("<th><b>" + paramRequest.getLocaleString("tpl") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("size") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("date") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("install") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("download") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("delete") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleString("up2comunity") + "</b></th>");
            out.println("</tr>");
            for (int i = 0; i < files.length; i++) {
                File filex = files[i];
                String fileName = filex.getName();
                if (filex.isFile() && fileName.endsWith("_adv.zip")) {
                    int pos = fileName.lastIndexOf(".");
                    if (pos > -1) {
                        fileName = fileName.substring(0, pos);
                    }
                    out.println("<tr align=\"left\"><td>");
                    url.setParameter("zipName", filex.getAbsolutePath());
                    url.setMode("viewmodel");
                    out.println("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + fileName + "</a>");
                    out.println("</td><td>");
                    out.println(filex.length() + " bytes");
                    out.println("</td><td>");
                    Date date = new Date(filex.lastModified());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    out.println(sdf.format(date));
                    out.println("</td></td>");
                    url.setMode("installmodel");
                    url.setAction("form");
                    url.setParameter("fileName", fileName);
                    out.println("<td align=\"center\"><a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconinst.png\" alt=\"" + paramRequest.getLocaleString("install") + "\"/></a></td>");
                    out.println("<td align=\"center\"><a href=\"" + WEBPATH + filex.getName() + "\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/icondesin.png\" alt=\"" + paramRequest.getLocaleString("download") + "\"/></a></td>");
                    urlAction.setParameter("zipName", filex.getAbsolutePath());
                    urlAction.setAction("delete");
                    out.println("<td align=\"center\"><a href=\"" + urlAction.toString() + "\" onclick=\"submitUrl('" + urlAction.toString() + "',this);return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconelim.png\" alt=\"" + paramRequest.getLocaleString("delete") + "\"/></a></td>");
                    out.println("<td align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconsubcom.png\" alt=\"" + paramRequest.getLocaleString("up2comunity") + "\"/></td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset><span align=\"center\">");
            out.println("" + paramRequest.getLocaleString("upload") + "<input type=\"file\" name=\"zipmodel\" value=\"" + paramRequest.getLocaleString("new") + "\"/><br/>");
            //out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id=\"send\" type=\"submit\"dojoType=\"dijit.form.Button\">"+paramRequest.getLocaleString("up")+"</button>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" id=\"send\" value=\"" + paramRequest.getLocaleString("up") + "\"/>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");

            out.println("<div class=\"swbform\" id=\"vsites\" dojoType=\"dijit.TitlePane\" title=\"" + paramRequest.getLocaleString("sites2Save") + "\" open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramRequest.getLocaleString("existTpls") + "</legend>");
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"75%\">");
            //urlAction.setAction("savesite");
            Iterator<WebSite> itws = SWBContext.listWebSites();
            while (itws.hasNext()) {
                WebSite ws = itws.next();
                url.setMode("AdvancedMode");
                url.setParameter("wsid", ws.getId());
                out.println("<tr><td>");
                //urlAction.setParameter("wsid", ws.getId());
                out.println("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + ws.getDisplayTitle(paramRequest.getUser().getLanguage()) + "</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div>");
            out.println("</fieldset>");
            out.println("</div>");

            out.println(strbf.toString());
        } catch (Exception e) {
            log.debug(e);
            e.printStackTrace();
        }
    }

    /**
     * Do view model.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<form>");
        out.println("<table width=\"75%\" border=\"1\">");
        out.println("<tr><td colspan=\"2\" align=\"center\"><b>" + paramRequest.getLocaleString("fileContent") + "  " + request.getParameter("zipName") + ":</b></td></tr>");
        out.println("<tr><td align=\"center\"><b>" + paramRequest.getLocaleString("file") + "</b></td><td align=\"center\"><b>" + paramRequest.getLocaleString("size") + " (bytes)</b></td></tr>");
        for (Iterator<ZipEntry> itfiles = SWBUtils.IO.readZip(request.getParameter("zipName")); itfiles.hasNext();) {
            ZipEntry zen = itfiles.next();
            out.println("<tr><td>" + zen.getName() + "</td><td>" + zen.getSize() + "</td></tr>");
        }
        out.println("<tr><td colspan=\"2\" align=\"center\"><button id=\"send\" dojoType=\"dijit.form.Button\" onClick=\"javascript:history.go(-1);\"/>" + paramRequest.getLocaleString("return") + "</button></td></tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * Do install model.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doInstallModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            if (paramRequest.getAction().equals("form")) {
                try {
                    Iterator<WebSite> itWebSites=SWBContext.listWebSites();
                    urlAction.setAction("install");
                    out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + urlAction.toString() + "\" dojoType=\"dijit.form.Form\" method=\"post\">");
                    out.println("<fieldset>");
                    out.println("<legend> Sitio en el que se desea importar datos</legend>");
                    out.println("<table>");
                    out.append("<tr><td>"+paramRequest.getLocaleString("chooseasite")+":&nbsp;&nbsp;");
                    out.append("<select name=\"wsid\">");
                    while(itWebSites.hasNext())
                    {
                        WebSite wsite=itWebSites.next();
                        out.append("<option value=\""+wsite.getURI()+"\">"+wsite.getTitle()+"</option>");
                    }
                    out.append("</select>");
                    out.println("</td>");
                    out.append("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("<fieldset><span align=\"center\">");
                    out.println("<button dojoType='dijit.form.Button' type=\"submit\" onClick=\"if(!dijit.byId('frmImport1').isValid()) return false;\">" + paramRequest.getLocaleString("send") + "</button>");
                    out.println("<button id=\"send\" dojoType=\"dijit.form.Button\" onClick=\"javascript:history.go(-1);\">" + paramRequest.getLocaleString("return") + "</button>");
                    out.println("</span></fieldset>");
                    out.println("<input type=\"hidden\" name=\"zipName\" value=\"" + request.getParameter("zipName") + "\" />");
                    out.println("</form>");
                } catch (Exception e) {
                    log.debug(e);
                }
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        if (response.getAction().equals("upload")) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fu = new ServletFileUpload(factory);
            try {
                Iterator iter = ((List) fu.parseRequest(request)).iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (!item.isFormField()) {
                        String value = item.getName();
                        value = value.replace("\\", "/");
                        int pos = value.lastIndexOf("/");
                        if (pos > -1) {
                            value = value.substring(pos + 1);
                        }
                        File fichero = new File(ZIPDIRECTORY);
                        if (!fichero.exists()) {
                            fichero.mkdirs();
                        }
                        fichero = new File(ZIPDIRECTORY + value);
                        item.write(fichero);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.debug(e);
            }
        } else if (response.getAction().equals("delete")) {
            File fichero = new File(request.getParameter("zipName"));
            fichero.delete();
        } else if (response.getAction().equals("install")) {
            try {
                String wsid=request.getParameter("wsid");
                WebSite wsite=null;
                if(wsid!=null)
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(wsid);
                    wsite = (WebSite) semObject.createGenericInstance();
                    File zipFile = new File(request.getParameter("zipName"));
                    installAdvZip(wsite, zipFile);
                }

                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("msgKey", "siteCreated");
                response.setRenderParameter("wsUri", wsite.getURI());
            } catch (Exception e) {
                log.error(e);
            }
        } else if (response.getAction().equals("saveAdvanceMode")) {
            try {
                ArrayList aSWBObjs=new ArrayList();
                Enumeration enParams=request.getParameterNames();
                while(enParams.hasMoreElements()){
                    String strParam=(String)enParams.nextElement();
                    if(strParam.equals("swbObjs")){
                        String [] strObjs=request.getParameterValues(strParam);
                        for(int i=0;i<strObjs.length;i++){
                            String strParamValue=strObjs[i];
                            aSWBObjs.add(strParamValue);
                        }
                    }
                }
                //Crea archivo filtrado y genera zip con el mismo y con todo el filesystem de sus objetos
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("wsUri"));
                WebSite wsite = (WebSite) semObject.createGenericInstance();
                createNewFile(new File(request.getParameter("tmpFile")), wsite, aSWBObjs);

                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("msgKey", "siteCreated");
                //response.setRenderParameter("wsUri", website.getURI());
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /**
     * Metodo de instalación de un zip con partes de un sitio.
     * 
     * @param wsite the wsite
     * @param zipFile the zip file
     */
    private void installAdvZip(WebSite wsite, File zipFile)
    {
        String modelspath = SWBPortal.getWorkPath() + "/models/";
        try{
            java.io.File extractTo = new File(modelspath + wsite.getId()+"_i_tmp");
            //Descomprimir zip
            org.semanticwb.SWBUtils.IO.unzip(zipFile, extractTo);
            String siteInfo = SWBUtils.IO.readFileFromZipAsString(zipFile.getAbsolutePath(), "siteInfo.xml");
            String oldIDModel = null, oldNamespace = null;
            Document dom = SWBUtils.XML.xmlToDom(siteInfo);
            Node nodeModel = dom.getFirstChild();
            if (nodeModel.getNodeName().equals("model"))
            {
                NodeList nlChilds = nodeModel.getChildNodes();
                for (int i = 0; i < nlChilds.getLength(); i++)
                {
                    Node node = nlChilds.item(i);
                    if (node.getNodeName().equals("id"))
                    {
                        oldIDModel = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("namespace"))
                    {
                        oldNamespace = node.getFirstChild().getNodeValue();
                    }
                }
            }

            //Se lee el archivo .nt descomprimido y genero HashMap con llave igual a un uri unico del token 1(ej. xxx:template:2 y xxx:template:3)
            //y como valor se pone un ArrayList con todas las lineas que correspondan a ese uri
            LinkedHashMap linkHmapObjs=new LinkedHashMap();
            LinkedHashMap linkedHashMap=new LinkedHashMap(); //En este se pondran los antiguos Uris de Objetos
            //String objUri=null;
            File fileModel = new File(modelspath + wsite.getId()+"_i_tmp" + "/" + oldIDModel + ".nt");
            BufferedReader reader = new BufferedReader(new FileReader(fileModel));

            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line == null || line.trim().length() == 0) {
                    continue;
                }
                int contToken = 0;
                boolean bResourceTypeExist=false;
                boolean bisWebPageHome=false;
                String sobjNew=null;
                String objUri=null;
                StringTokenizer strTokens = new StringTokenizer(line, " ", false);
                while (strTokens.hasMoreElements()) {
                    String token = strTokens.nextToken();
                    contToken++;
                    if (token == null || contToken == 2 ||contToken == 4) {
                        continue;
                    }else {
                        if (contToken == 1) {
                            objUri=token.substring(1, token.length()-1);
                            sobjNew=objUri.replaceAll(oldNamespace, wsite.getNameSpace());
                            //Revisar si es de tipo ResourceType, si es así revisar si no existe ya en la instancia a importar
                            //De ser así, ya no lo crearía, ya que si lo creo se generaria otro ResourceType
                            //diferente al que ya esta, ej. otro ResourceType Banner, Window, etc. Esta es la única Excepción
                            if(objUri.indexOf("#swb_ResourceType:")>-1 || objUri.indexOf("#swb_Language:")>-1 ||
                                 objUri.indexOf("#swb_Device:")>-1 || objUri.indexOf("#swb_Country:")>-1){ //Es de tipo ResourceType
                                if(SemanticObject.createSemanticObject(sobjNew)!=null) {
                                    bResourceTypeExist=true;  //Existe x lo tanto no se creara
                                }
                            }else if(objUri.indexOf("#WebPage:home")>-1){
                                //Si es de tipo webpage:home ya no se generaría ya que supuestamente
                                //Ya debe existir un home en el sitio en el que se va a  importar
                                //bisWebPageHome=true;
                            }
                        }else if(contToken == 3) { //Para el registro de referencias hacia el homePage que estan en el
                            //sitio original y la idea por supuesto es de que pasen igual al sitio a importar(destino)
                            //siempre y cuando se hayan exportado dichas referencias (ej. Templates al home, recursos al home, etc)
                            if(bisWebPageHome) //Si es el token 3 y tiene la bandera bisWebPageHome en true
                            {
                                int pos=token.indexOf("<");
                                if(pos>-1){
                                    int pos1=token.indexOf(">", pos);
                                    if(pos1>-1){
                                        String sobjUriToken3=token.substring(pos+1, pos1);
                                        if(linkedHashMap.containsKey(sobjUriToken3)){
                                            bResourceTypeExist=false;
                                            bisWebPageHome=false;
                                        }else{
                                            if(SemanticObject.createSemanticObject(sobjNew)!=null) {
                                                bResourceTypeExist=true;  //Existe x lo tanto no se creara
                                            }else{
                                                bResourceTypeExist=false;
                                                bisWebPageHome=false;
                                            }
                                        }
                                    }
                                }
                            }
                            if(!bResourceTypeExist)
                            {
                                if(!bisWebPageHome)
                                {
                                    if(!linkHmapObjs.containsKey(objUri)){
                                        ArrayList newArray=new ArrayList();
                                        newArray.add(line);
                                        linkHmapObjs.put(objUri, newArray);

                                        //Revisa si existe el sobjId
                                        if(objUri.indexOf("#WebPage:home")==-1){
                                            //se guarda una relación del uri antiguo con un nuevo uri generado para el sitio en donde se importara
                                            linkedHashMap.put(objUri, givemeUri2Create(sobjNew));
                                        }else{
                                            linkedHashMap.put(objUri, wsite.getHomePage().getURI());
                                        }
                                    }else{ //Agrega la linea a el arraylist
                                        ArrayList alist=(ArrayList)linkHmapObjs.get(objUri);
                                        alist.add(line);
                                        linkHmapObjs.remove(objUri);
                                        linkHmapObjs.put(objUri, alist);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            reader.close();


            
            //Barrer Hash para para crear cada objeto y crear todas sus propiedades en un archivo
            File fileModified=new File(modelspath + wsite.getId()+"_i_tmp" + "/"+wsite.getId()+"_tmp.nt");
            FileOutputStream out = new FileOutputStream(fileModified);
            Iterator<String> itObjs=linkHmapObjs.keySet().iterator();
            while(itObjs.hasNext()){
                String sobjOld=itObjs.next();
                String uri2create=(String)linkedHashMap.get(sobjOld);
                Iterator<String> itLines=((ArrayList)linkHmapObjs.get(sobjOld)).iterator();
                while(itLines.hasNext()){
                    String strLine=itLines.next();
                   
                    //Revisar si hay algun uri en el tercer token que coincida con alguno de los cambiados y que estan en el
                    //hash, de ser así tambien reemplazarlo
                    int contToken=0;
                    StringTokenizer strTokens = new StringTokenizer(strLine, " ", false);
                    while (strTokens.hasMoreElements()) {
                        String token = strTokens.nextToken();
                        contToken++;
                        if (token == null || token.trim().length()==0 || contToken!=3) {
                            continue;
                        }else if (contToken == 3) {
                            int pos=token.indexOf("<");
                            if(pos>-1)
                            {
                                int pos1=token.indexOf(">", pos);
                                if(pos1>-1){
                                    String tmpToken=token.substring(pos+1, pos1);
                                    if(linkedHashMap.containsKey(tmpToken)){
                                        String newObjUri=(String)linkedHashMap.get(tmpToken);
                                        strLine=strLine.replaceAll(tmpToken, newObjUri);
                                    }
                                }
                            }
                        }
                    }
                    if(uri2create!=null){
                        strLine=strLine.replaceAll(sobjOld, uri2create);
                    }
                    strLine=strLine.replaceAll(oldNamespace, wsite.getNameSpace())+"\n";
                    //Reemplazar en Xml de los ResourceFilter, pero puede haber más cosas que se tuvieran que validar
                    if(sobjOld.indexOf("#swb_ResourceFilter:")>-1){
                      if(strLine.indexOf("<topicmap id=\\\""+oldIDModel+"\\\">")>-1){
                            strLine=SWBUtils.TEXT.replaceAll(strLine,"<topicmap id=\\\""+oldIDModel+"\\\">", "<topicmap id=\\\""+wsite.getId()+"\\\">");
                      }
                    }
                    //Al final de todo escribe en el archivo que se cargara al modelo del sitio
                    out.write(strLine.getBytes());
                }
            }
            out.flush();
            out.close();

            //Meter el archivo modificado al modelo del sitio especificado
            Model m = SWBPlatform.getSemanticMgr().loadRDFFileModel(fileModified.getAbsolutePath());
            wsite.getSemanticObject().getModel().getRDFModel().add(m);
            SemanticObject.clearCache();
            //Borro el archivo que subi al modelo del sitio
            //fileModified.delete();

            //Barro el Hashmap que contiene la relación de entre el uri nuevo y el anterior para pasar los archivos
            //de filesystem
            Iterator<String> itRelationSemObjs=linkedHashMap.keySet().iterator();
            while(itRelationSemObjs.hasNext()){
                String sOldSObj=itRelationSemObjs.next();
                String sNewSObj=(String)linkedHashMap.get(sOldSObj);
                SemanticObject semObj=SemanticObject.createSemanticObject(sNewSObj);
                if(semObj!=null){ //Debería siempre entrar a esta opción, de lo contrario no se realizó bien la creación del objeto
                    try{
                        String newSObjPath=semObj.getWorkPath();
                        int pos=sOldSObj.indexOf("#");
                        if(pos>-1){
                            int pos1=sOldSObj.indexOf(":", pos);
                            if(pos1>-1)
                            {
                                String dirOldSObj=sOldSObj.substring(pos+1, pos1);
                                String idOldSobj=sOldSObj.substring(pos1+1);
                                File fileOldSObjPath=new File(modelspath + wsite.getId()+"_i_tmp" + "/"+dirOldSObj+"/"+idOldSobj+"/");
                                if(fileOldSObjPath.isDirectory() && fileOldSObjPath.exists())
                                {
                                    SWBUtils.IO.copyStructure(modelspath + wsite.getId()+"_i_tmp" + "/"+dirOldSObj+"/"+idOldSobj+"/", SWBPortal.getWorkPath() + newSObjPath+"/");
                                }
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        log.error(e);
                    }
                }else{ //No debería irse por este else
                    log.debug("No creo el objeto..."+"sNewSObj:"+sNewSObj+", sOldSObj:"+sOldSObj);
                }
            }
            SWBUtils.IO.removeDirectory(extractTo.getAbsolutePath());
        } catch (Exception e) {
            log.error(e);
        }
    }


    /**
     * Giveme uri2 create.
     * 
     * @param sobj the sobj
     * @return the string
     */
    private String givemeUri2Create(String sobj)
    {
        String uriPart1=null;
        int pos=sobj.lastIndexOf(":");
        if(pos>-1){
            uriPart1=sobj.substring(0, pos);
            String uriPart2=sobj.substring(pos+1);
            return createSemObj(uriPart1, uriPart2);
        }
        return null;
    }

    /**
     * Creates the sem obj.
     * 
     * @param uriPart1 the uri part1
     * @param uriPart2 the uri part2
     * @return the string
     */
    private String createSemObj(String uriPart1, String uriPart2){
        String uriaCrear=null;
        try{
            Integer.parseInt(uriPart2);
            uriaCrear=getNumericSemObjUri(uriPart1, uriPart2, 1);
        }catch(NumberFormatException e){
            uriaCrear=getStringSemObjUri(uriPart1, uriPart2, 0);
        }
        return uriaCrear;
    }


    /**
     * Gets the numeric sem obj uri.
     * 
     * @param uriPart1 the uri part1
     * @param uriPart2 the uri part2
     * @param cont the cont
     * @return the numeric sem obj uri
     */
    private String getNumericSemObjUri(String uriPart1, String uriPart2, int cont){
        int tmpCont=cont+1;
        String uriaCrear=uriPart1+":i"+cont+"_"+uriPart2;
        SemanticObject semObj=SemanticObject.createSemanticObject(uriaCrear);
        if(semObj!=null){ //Ya existe, seguir iterando para encontrar un uri que no exista y pueda crear el semanticObject
            uriaCrear=getNumericSemObjUri(uriPart1, uriPart2, tmpCont);
        }
        return uriaCrear;
    }


    /**
     * Gets the string sem obj uri.
     * 
     * @param uriPart1 the uri part1
     * @param uriPart2 the uri part2
     * @param cont the cont
     * @return the string sem obj uri
     */
    private String getStringSemObjUri(String uriPart1, String uriPart2, int cont){
        String uriaCrear=null;
        if(cont<1) uriaCrear=uriPart1+":"+uriPart2 ;
        else uriaCrear=uriPart1+":i"+cont+"_"+uriPart2;
        int tmpCont=cont+1;

        SemanticObject semObj=SemanticObject.createSemanticObject(uriaCrear);
        if(semObj!=null){ //Ya existe, seguir iterando para encontrar un uri que no exista y pueda crear el semanticObject
            uriaCrear=getStringSemObjUri(uriPart1, uriPart2, tmpCont);
        }
        return uriaCrear;
    }



    /**
     * Crea archivo filtrado y genera zip con el mismo y con todo el filesystem de sus objetos.
     * 
     * @param file the file
     * @param wsite the wsite
     * @param aSWBObjs the a swb objs
     */
    private void createNewFile(File file, WebSite wsite, ArrayList aSWBObjs) {
        try {
            ArrayList aBlackList=new ArrayList();
            String wsid=wsite.getId();
            LinkedList<String> linkedList=new LinkedList();
            String path = SWBPortal.getWorkPath() + "/";
            String zipdirectory = path + "sitetemplates/";
            File filterdFile=new File(zipdirectory+wsid+".nt");
            FileOutputStream out = new FileOutputStream(filterdFile);
            String sitePath = path + "models/"+wsid+"/";
            java.io.File fBase = new File(sitePath);

            //---------Generación de archivo zip de carpeta work de sitio especificado-------------
            String zipFile = zipdirectory + wsid + "_adv.zip";
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            //Graba archivo cualquiera
            zos.setComment("Model File SemanticWebBuilderOS");
            try {
                ZipEntry entry = new ZipEntry("readme.txt");
                zos.putNextEntry(entry);
                zos.write("Advanced Model File SemanticWebBuilderOS".getBytes());
                zos.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //----------Generacion de archivo siteInfo.xml del sitio especificado-----------
            File[] files2add = new File[2];
            File siteInfoFile = new File(zipdirectory + "siteInfo.xml");
            FileOutputStream siteInfoOut = new FileOutputStream(siteInfoFile);
            StringBuffer strbr = new StringBuffer();
            try {
                strbr.append("<model>\n");
                strbr.append("<id>" + wsid + "</id>\n");
                strbr.append("<namespace>" + wsite.getNameSpace() + "</namespace>\n");
                strbr.append("</model>\n");
                siteInfoOut.write(strbr.toString().getBytes("utf-8"));
                siteInfoOut.flush();
                siteInfoOut.close();
            } catch (Exception e) {
                log.debug(e);
            }
            files2add[0]=siteInfoFile;


            //Lectura de archivo temporal para generar archivo filtrado
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            boolean isObject=false;
            while (line != null) {
                line = reader.readLine();
                if (line == null || line.trim().length() == 0) {
                    continue;
                }
                String tokenObjUri=null, StrObj=null;
                int contToken = 0;
                StringTokenizer strTokens = new StringTokenizer(line, " ", false);
                while (strTokens.hasMoreElements()) {
                    String token = strTokens.nextToken();
                    contToken++;
                    if (token == null ||contToken == 4) {
                        continue;
                    }else if (contToken == 1) {
                        tokenObjUri=token.substring(1,token.length()-1);
                    }else if (contToken == 2 && token.endsWith("#type>")) { //Predicado que se require, ahora solo hay que ver cual es el objeto
                        isObject = true;
                    } else if (isObject && contToken == 3) {
                        int pos = token.indexOf("#");
                        if (pos > -1) {
                            int pos1 = token.indexOf(">", pos + 1);
                            if (pos1 > -1) {
                                StrObj = token.substring(pos + 1, pos1);
                                if(!aSWBObjs.contains(StrObj)){
                                    aBlackList.add(tokenObjUri);                                    
                                }
                            }
                        }
                        isObject=false;
                    }
                }
            }
            //Lectura del archivo completo nuevamente para ahora si crear el archivo filtrado con las uris que no 
            //correspondan a las de la lista negra
            reader.close();
            reader = new BufferedReader(new FileReader(file));
            line = "";
            isObject=false;
            while (line != null) {
                line = reader.readLine();
                if (line == null || line.trim().length() == 0) {
                    continue;
                }
                int contToken = 0;
                boolean bWriteLine = true;
                StringTokenizer strTokens = new StringTokenizer(line, " ", false);
                while (strTokens.hasMoreElements()) {
                    String token = strTokens.nextToken();
                    contToken++;
                    if (token == null ||contToken == 4) {
                        continue;
                    }else if(contToken==1 || contToken==3){
                         int pos=token.indexOf("<");
                         if(pos>-1){
                             int pos1=token.indexOf(">", pos);
                             if(pos1>-1){
                                 token=token.substring(pos+1, pos1);
                                 if(aBlackList.contains(token) || token.indexOf("#counter:")>-1 || token.indexOf("#swb_User:")>-1){
                                     bWriteLine=false;
                                 }else{ //Se graba el token (meto a lista Uri de objeto para llevarme su filesystem)
                                     if(!linkedList.contains(token))
                                     {
                                        linkedList.add(token);
                                     }
                                 }
                             }
                         }
                    }
                }
                if(bWriteLine){
                    //Escribe sobre el nuevo archivo
                    line=line+"\n";
                    out.write(line.getBytes());
                }
            }
            reader.close();
            out.flush();
            out.close();
            //Grabado de WorkPath de objetos semanticos en archivo .zip
            addObjWorkPath2Zip(linkedList.iterator(), zos, fBase);
            zos.close();

            //Metemos archivo .nt a zip
            files2add[1]=filterdFile;
            org.semanticwb.SWBUtils.IO.addFilesToExistingZip(new File(zipFile), files2add);
            //Borramos archivos .nt y tmp.nt
            new File(zipdirectory + wsid + ".nt").delete();
            new File(zipdirectory + wsid + "_tmp.nt").delete();
            new File(zipdirectory + "siteInfo.xml").delete();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Grabado de WorkPath de objeto semantico al zip en la exportación.
     * 
     * @param itLinkedList the it linked list
     * @param zos the zos
     * @param fBase the f base
     */
    private void addObjWorkPath2Zip(Iterator<String> itLinkedList, ZipOutputStream zos, File fBase)
    {
        try{
            while(itLinkedList.hasNext())
            {
                String strObjUri=itLinkedList.next();
                SemanticObject semObject = SemanticObject.createSemanticObject(strObjUri);
                if(semObject!=null){
                    String sObjPath=SWBPortal.getWorkPath()+semObject.getWorkPath()+"/";
                    File directory2Zip=new File(sObjPath);
                    if(directory2Zip!=null && directory2Zip.exists()){
                        org.semanticwb.SWBUtils.IO.zip(directory2Zip, fBase, zos);
                    }
                }
            }
        }catch(Exception e){
            log.error(e);
        }
    }

}

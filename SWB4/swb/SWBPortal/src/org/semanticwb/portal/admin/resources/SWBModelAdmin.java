/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.repository.Workspace;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class SWBModelAdmin extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);
    String PATH = SWBPlatform.getWorkPath() + "/";
    String WEBPATH = SWBPlatform.getWebWorkPath() + "/sitetemplates/";
    String MODELS = PATH + "models/";
    String ZIPDIRECTORY = PATH + "sitetemplates/";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("viewmodel")) {
            doViewModel(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("installmodel")) {
            doInstallModel(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            if (request.getParameter("msgKey") != null) {
                out.println("<script type=\"text/javascript\">");
                if(request.getParameter("msgKey").equals("siteCreated")){
                    out.println("parent.addItemByURI(parent.mtreeStore, null, '" + request.getParameter("wsUri") + "');");
                }
                out.println("parent.showStatus('" + paramRequest.getLocaleLogString(request.getParameter("msgKey")) + "');");
                out.println("</script>");
            }
            SWBResourceURL url = paramRequest.getRenderUrl();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            StringBuffer strbf = new StringBuffer();
            File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/");
            File[] files = file.listFiles();
            urlAction.setAction("upload");
            //out.println("<iframe id=\"templates\">");
            //out.println("<div id=\"vtemplates\" dojoType=\"dijit.TitlePane\" title=\"Templates existentes de Sitios \" class=\"admViewTemplates\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramRequest.getLocaleLogString("existTpls") + "</legend>");
            out.println("<form action=\"" + urlAction.toString() + "\" method=\"post\" enctype='multipart/form-data'>");
            out.println("<table width=\"100%\">");
            out.println("<tr align=\"left\">");
            out.println("<th><b>" + paramRequest.getLocaleLogString("tpl") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleLogString("size") + "</b></th>");
            out.println("<th><b>"+paramRequest.getLocaleLogString("install") +"</b></th>");
            out.println("<th><b>"+paramRequest.getLocaleLogString("download") + "</b></th>");
            out.println("<th><b>"+paramRequest.getLocaleLogString("delete") + "</b></th>");
            out.println("<th><b>" + paramRequest.getLocaleLogString("up2comunity") + "</b></th>");
            out.println("</tr>");
            for (int i = 0; i < files.length; i++) {
                File filex = files[i];
                String fileName = filex.getName();
                if (filex.isFile() && fileName.endsWith(".zip")) {
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
                    out.println("</td>");
                    url.setMode("installmodel");
                    url.setAction("form");
                    out.println("<td align=\"center\"><a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconinst.png\" alt=\""+paramRequest.getLocaleLogString("install") + "\"/></a></td>");
                    out.println("<td align=\"center\"><a href=\"" + WEBPATH + filex.getName() + "\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/icondesin.png\" alt=\""+paramRequest.getLocaleLogString("download") + "\"/></a></td>");
                    urlAction.setParameter("zipName", filex.getAbsolutePath());
                    urlAction.setAction("delete");
                    out.println("<td align=\"center\"><a href=\"" + urlAction.toString() + "\" onclick=\"submitUrl('" + urlAction.toString() + "',this);return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconelim.png\" alt=\""+paramRequest.getLocaleLogString("delete") + "\"/></a></td>");
                    out.println("<td align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/iconsubcom.png\" alt=\""+paramRequest.getLocaleLogString("up2comunity") + "\"/></td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset><span align=\"center\">");
            out.println("" + paramRequest.getLocaleLogString("upload") + "<input type=\"file\" name=\"zipmodel\" value=\"" + paramRequest.getLocaleLogString("new") + "\"/><br/>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id=\"send\" dojoType=\"dijit.form.Button\" type=\"submit\">"+paramRequest.getLocaleLogString("up")+"</button>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");

            out.println("<div class=\"swbform\" id=\"vsites\" dojoType=\"dijit.TitlePane\" title=\""+paramRequest.getLocaleLogString("sites2Save")+"\" open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramRequest.getLocaleLogString("existTpls") + "</legend>");
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"75%\">");
            urlAction.setAction("savesite");
            Iterator<WebSite> itws = SWBContext.listWebSites();
            while (itws.hasNext()) {
                WebSite ws = itws.next();
                out.println("<tr><td>");
                urlAction.setParameter("wsid", ws.getId());
                out.println("<a href=\"" + urlAction.toString() + "\" onclick=\"submitUrl('" + urlAction.toString() + "',this);return false;\">" + ws.getTitle() + "</a>");
                out.println("</td></tr>");
            }
            out.println("</table>");
            out.println("</div>");
            out.println("</fieldset>");
            out.println("</div>");

           

            out.println(strbf.toString());
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doViewModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<form>");
        out.println("<table width=\"75%\">");
        out.println("<tr><td><b>" + paramRequest.getLocaleLogString("fileContent") + request.getParameter("zipName") + ":</b><br/><br/><br/></td></tr>");
        for (Iterator<ZipEntry> itfiles = SWBUtils.IO.readZip(request.getParameter("zipName")); itfiles.hasNext();) {
            ZipEntry zen = itfiles.next();
            out.println("<tr><td>" + zen.getName() + "</td></tr>");
        }
        out.println("<tr><td><button id=\"send\" dojoType=\"dijit.form.Button\" value=\"" + paramRequest.getLocaleLogString("return") + "\" onClick=\"javascript:history.go(-1);\"/></td></tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</div>");
    }

    public void doInstallModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            if (paramRequest.getAction().equals("form")) {
                try {
                    urlAction.setAction("install");
                    out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + urlAction.toString() + "\" dojoType=\"dijit.form.Form\" method=\"post\">");
                    out.println("<fieldset>");
                    out.println("<legend>" + paramRequest.getLocaleLogString("newsitedata") + "</legend>");
                    out.println("<table>");
                    out.append("<tr><td>");
                    out.println(paramRequest.getLocaleLogString("msgwsTitle"));
                    out.println("</td>");
                    out.append("<td>");
                    out.println("<input type=\"text\" name=\"wstitle\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Titulo.\" invalidMessage=\"Titulo es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);dijit.byId('swb_create_id').validate()\" trim=\"true\" >");
                    out.println("</td>");
                    out.append("</tr>");
                    out.append("<tr><td>");
                    out.println("ID:");
                    out.println("</td>");
                    out.append("<td>");
                    out.println("<input id=\"swb_create_id\" type=\"text\" name=\"wsid\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject(this.textbox.value);\" invalidMessage=\"Identificador invalido.\" trim=\"true\" >");
                    out.println("</td>");
                    out.append("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("<fieldset><span align=\"center\">");
                    //out.append("<tr>");
                    out.println("<button dojoType='dijit.form.Button' type=\"submit\" onClick=\"if(!dijit.byId('frmImport1').isValid()) return false;\">" + paramRequest.getLocaleLogString("send") + "</button>");
                    out.println("<button id=\"send\" dojoType=\"dijit.form.Button\" onClick=\"javascript:history.go(-1);\">"+paramRequest.getLocaleLogString("return")+"</button>");
                    //out.println("</td></tr>");
                    out.println("</span></fieldset>");
                    
                    out.println("<input type=\"hidden\" name=\"zipName\" value=\"" + request.getParameter("zipName") + "\"");
                    out.println("</form>");
                } catch (Exception e) {
                    log.debug(e);
                }
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private String parseRdfContent(String rdfcontent, String oldName, String newName, String oldID, String newID, String newNS) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.xmlToDom(rdfcontent);
            NodeList nodeList = dom.getElementsByTagName("rdf:Description");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nodeDescr = nodeList.item(i);
                NamedNodeMap nodeMap = nodeDescr.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    String nvalue = nodeMap.item(j).getNodeValue();
                    if (nvalue != null && nvalue.equalsIgnoreCase(newNS + oldID)) {
                        nodeMap.item(j).setNodeValue(newNS + newID); //ver como tengo que poner el newName, si debe ser con minusculas
                        NodeList nlist = nodeDescr.getChildNodes();
                        for (int k = 0; k < nlist.getLength(); k++) {
                            if (nlist.item(k).getNodeName().endsWith("title")) {
                                nlist.item(k).getFirstChild().setNodeValue(newName);
                            }
                        }
                    }
                }
                NodeList nlist = nodeDescr.getChildNodes();
                for (int k = 0; k < nlist.getLength(); k++) {
                    Node node = nlist.item(k);
                    if (node.getPrefix() != null && node.getPrefix().equals(oldID)) {
                        node.setPrefix(newID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SWBUtils.TEXT.replaceFirstIgnoreCase(SWBUtils.XML.domToXml(dom), "xmlns:" + oldID, "xmlns:" + newID);
    }

    /**
     * Metodo sobrado en este momento, pero servira para cuando un submodelo (sitio), tenga mas submodelos (sitios,repositorios)
     * @param node
     * @param smodels
     */
    private void iteraModels(Node node, HashMap smodels) {
        HashMap submodel = new HashMap();
        NodeList nlChildModels = node.getChildNodes();
        for (int j = 0; j < nlChildModels.getLength(); j++) {
            Node nodeSModel = nlChildModels.item(j);
            if (nodeSModel.getNodeName().equals("type")) { //Revisar si existe el submodel en la instancia a importar
                NodeList nlSite = node.getChildNodes();
                for (int k = 0; k < nlSite.getLength(); k++) {
                    if (nlSite.item(k).getNodeName().equals("id")) {
                        smodels.put(nlSite.item(k).getFirstChild().getNodeValue(), submodel);
                    }
                }
            } else if (nodeSModel.getNodeName().equals("id")) {
                submodel.put("id", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("namespace")) {
                submodel.put("namespace", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("title")) {
                submodel.put("title", nodeSModel.getFirstChild().getNodeValue());
            } else if (nodeSModel.getNodeName().equals("description")) {
                submodel.put("description", nodeSModel.getFirstChild().getNodeValue());
            }
        }
    }

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
        } else if (response.getAction().equals("savesite")) {
            try {
                String uri = request.getParameter("wsid");
                WebSite site = SWBContext.getWebSite(uri);
                String path = SWBPlatform.getWorkPath() + "/";
                String modelspath = path + "models/";
                String zipdirectory = path + "sitetemplates/";
                String zipFile = zipdirectory + site.getId() + ".zip";
                //---------Generación de archivo zip de carpeta work de sitio especificado-------------
                java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(zipFile));
                java.io.File directory = new File(modelspath + site.getId() + "/");
                java.io.File base = new File(modelspath);
                org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
                //Graba archivo cualquiera
                zos.setComment("Model File SemanticWebBuilderOS");
                try {
                    ZipEntry entry = new ZipEntry("readme.txt");
                    zos.putNextEntry(entry);
                    zos.write("Model File SemanticWebBuilderOS".getBytes());
                    zos.closeEntry();
                } catch (Exception e) {
                }

                //-------------Generación de archivo rdf del sitio especificado----------------
                try {
                    File file = new File(zipdirectory + site.getId() + ".nt");
                    FileOutputStream out = new FileOutputStream(file);
                    site.getSemanticObject().getModel().write(out,"N-TRIPLE");
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //----------Generación de archivo siteInfo.xml del sitio especificado-----------
                ArrayList aFiles = new ArrayList();
                File file = new File(zipdirectory + "siteInfo.xml");
                FileOutputStream out = new FileOutputStream(file);
                StringBuffer strbr = new StringBuffer();
                try {
                    strbr.append("<model>\n");
                    strbr.append("<id>" + site.getId() + "</id>\n");
                    strbr.append("<namespace>" + site.getNameSpace() + "</namespace>\n");
                    strbr.append("<title>" + site.getTitle() + "</title>\n");
                    strbr.append("<description>" + site.getDescription() + "</description>\n");
                    //--------------Generación de submodelos------------------------------------------------
                    Iterator<SemanticObject> sitSubModels = site.getSemanticObject().listObjectProperties(site.swb_hasSubModel);
                    while (sitSubModels.hasNext()) {
                        SemanticObject sObj = sitSubModels.next();
                        File fileSubModel = new File(zipdirectory + "/" + sObj.getId() + ".nt");
                        FileOutputStream rdfout = new FileOutputStream(fileSubModel);
                        sObj.getModel().write(rdfout,"N-TRIPLE");
                        rdfout.flush();
                        rdfout.close();
                        //Agregar c/archivo .rdf de submodelos a arreglo de archivos
                        aFiles.add(fileSubModel);
                        //graba el directorio work de c/submodelo en archivo zip
                        directory = new File(modelspath + sObj.getId() + "/");
                        org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
                        //Genera datos de c/summodelo en archivo siteInfo.xml
                        strbr.append("<model>\n");
                        strbr.append("<type>" + sObj.getSemanticClass() + "</type>\n");
                        //if(obj.instanceOf(WebSite.sclass)) //Que datos saco si es un rep de usuarios o de documentos y como los parseo despues
                        strbr.append("<id>" + sObj.getId() + "</id>\n");
                        strbr.append("<namespace>" + sObj.getModel().getNameSpace() + "</namespace>\n");
                        strbr.append("<title>" + sObj.getProperty(WebPage.swb_title) + "</title>\n");
                        strbr.append("<description>" + sObj.getProperty(WebPage.swb_description) + "</description>\n");
                        strbr.append("</model>\n");
                    }
                    strbr.append("</model>");
                    out.write(strbr.toString().getBytes("utf-8"));
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    log.debug(e);
                }
                zos.close();


                //--------------Agregar archivo rdf y xml generados a arraylist---------------------
                aFiles.add(new File(zipdirectory + site.getId() + ".nt"));
                aFiles.add(new File(zipdirectory + "siteInfo.xml"));
                //--------------Barrer archivos de arrayList para pasar a arreglo de Files y eliminar---
                File[] files2add = new File[aFiles.size()];
                int cont = 0;
                Iterator<File> itFiles = aFiles.iterator();
                while (itFiles.hasNext()) {
                    File filetmp = itFiles.next();
                    files2add[cont] = filetmp;
                    cont++;
                }
                //Agregar archivos rfd de modelo y submodelos y archivo siteInfo.xml a zip existente
                org.semanticwb.SWBUtils.IO.addFilesToExistingZip(new File(zipFile), files2add);
                itFiles = aFiles.iterator();
                while (itFiles.hasNext()) {
                    File filetmp = itFiles.next();
                    filetmp.delete();
                }

                new File(zipdirectory + site.getId() + ".nt").delete();
                new File(zipdirectory + "siteInfo.xml").delete();


                //Envia mensage de estatus en admin de wb
                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("msgKey", "siteExported");
                response.setRenderParameter("wsUri", uri);

            } catch (Exception e) {
                e.printStackTrace();
                log.debug(e);
            }
        }else if (response.getAction().equals("install")) {
            String siteInfo = SWBUtils.IO.readFileFromZip(request.getParameter("zipName"), "siteInfo.xml");
            String oldIDModel = null, oldNamespace = null, oldTitle = null, oldDescription = null;
            Document dom = SWBUtils.XML.xmlToDom(siteInfo);
            Node nodeModel = dom.getFirstChild();
            if (nodeModel.getNodeName().equals("model")) {
                HashMap smodels = new HashMap();
                NodeList nlChilds = nodeModel.getChildNodes();
                for (int i = 0; i < nlChilds.getLength(); i++) {
                    Node node = nlChilds.item(i);
                    if (node.getNodeName().equals("id")) {
                        oldIDModel = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("namespace")) {
                        oldNamespace = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("title")) {
                        oldTitle = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("description")) {
                        oldDescription = node.getFirstChild().getNodeValue();
                    }
                    if (node.getNodeName().equals("model")) { //Tiene submodelos - un submodelo puede inclusive tener submodelos, esto tiene que ser iterativo
                        iteraModels(node, smodels);
                    }
                }

                String newId = request.getParameter("wsid");
                String newTitle = request.getParameter("wstitle");
                File zip = new File(ZIPDIRECTORY + oldIDModel + ".zip");
                java.io.File extractTo = new File(MODELS + newId);
                //Descomprimir zip
                org.semanticwb.SWBUtils.IO.unzip(zip, extractTo);
                //Mover directorios de modelos a directorio work leyendo rdfs
                File[] fieldsUnziped = extractTo.listFiles();
                for (int i = 0; i < fieldsUnziped.length; i++) {
                    File file = fieldsUnziped[i];
                    if (file.isDirectory()) { //
                        if (file.getName().equals(oldIDModel)) { //Es la carpeta del modelo principal a cargar
                            SWBUtils.IO.copyStructure(file.getAbsolutePath() + "/", extractTo.getAbsolutePath() + "/");
                            SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                        } else {
                            if (file.getName().endsWith("_usr") || file.getName().endsWith("_rep")) {
                                //las carpetas de los submodelos, predefinidos en wb
                                String wbmodelType = "";
                                if (file.getName().endsWith("_usr")) {
                                    wbmodelType = "_usr";
                                }
                                if (file.getName().endsWith("_rep")) {
                                    wbmodelType = "_rep";
                                }

                                SWBUtils.IO.copyStructure(file.getAbsolutePath(), extractTo.getAbsolutePath() + wbmodelType + "/");
                                SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                            } else { //Puede ser un submodelo tipo sitio
                                //TODO
                            }
                        }
                    } else { //TODO:Archivos rdf(modelos) y xml (siteinfo) y readme, eliminarlos
                            String fileName=file.getName();
                            if (fileName.equalsIgnoreCase("siteInfo.xml") || fileName.equals("readme.txt")) { //Archivo siteinfo
                                file.delete();
                            }
                    }
                }
                //Parseo de nombre de NameSpace anteriores por nuevos
                String newNs = "http://www." + newId + ".swb#";
                File fileModel = new File(MODELS + newId + "/" + oldIDModel + ".nt");
                FileInputStream frdfio = new FileInputStream(fileModel);
                String rdfcontent = SWBUtils.IO.readInputStream(frdfio);
                fileModel.delete();

                rdfcontent = rdfcontent.replaceAll(oldNamespace, newNs); //Reempplazar namespace anterior x nuevo
                rdfcontent = rdfcontent.replaceAll(newNs+oldIDModel, newNs+newId); //Reempplazar namespace y id anterior x nuevos

                //Reemplaza ids de repositorios de usuarios y documentos x nuevos
                //rdfcontent = rdfcontent.replaceAll(oldIDModel + "_usr", newId + "_usr");
                //rdfcontent = rdfcontent.replaceAll("http://user." + oldIDModel + ".swb#", "http://user." + newId + ".swb#");
                //rdfcontent = rdfcontent.replaceAll(oldIDModel + "_rep", newId + "_rep");
                //rdfcontent = rdfcontent.replaceAll("http://rep." + oldIDModel + ".swb#", "http://rep." + newId + ".swb#");

                //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
                //rdfcontent = parseRdfContent(rdfcontent, oldTitle, newTitle, oldIDModel, newId, newNs);

                //Mediante inputStream creado generar sitio
                InputStream io = SWBUtils.IO.getStreamFromString(rdfcontent);
                SemanticModel model = SWBPlatform.getSemanticMgr().createModelByRDF(newId, newNs, io, "N-TRIPLE");
                WebSite website = SWBContext.getWebSite(model.getName());
                website.setTitle(newTitle);
                website.setDescription(oldDescription);
                String xmodelID = null, xmodelNS = null, xmodelTitle = null, xmodelDescr = null;
                Iterator smodelsKeys = smodels.keySet().iterator();
                while (smodelsKeys.hasNext()) { // Por c/submodelo que exista
                    String key = (String) smodelsKeys.next();
                    HashMap smodelValues = (HashMap) smodels.get(key);
                    Iterator itkVaues = smodelValues.keySet().iterator();
                    while (itkVaues.hasNext()) {
                        String kvalue = (String) itkVaues.next();
                        if (kvalue.equals("id")) {
                            xmodelID = (String) smodelValues.get(kvalue);
                        }
                        if (kvalue.equals("namespace")) {
                            xmodelNS = (String) smodelValues.get(kvalue);
                        }
                        if (kvalue.equals("title")) {
                            xmodelTitle = (String) smodelValues.get(kvalue);
                        }
                        if (kvalue.equals("description")) {
                            xmodelDescr = (String) smodelValues.get(kvalue);
                        }
                    }
                    //Buscar rdf del submodelo
                    fileModel = new File(MODELS + newId + "/" + xmodelID + ".nt");
                    if (fileModel != null && fileModel.exists()) {
                        frdfio = new FileInputStream(fileModel);
                        String rdfmodel = SWBUtils.IO.readInputStream(frdfio);
                        if (key.endsWith("_usr")) { //Para los submodelos de usuarios
                            int pos = xmodelID.lastIndexOf("_usr");
                            if (pos > -1) {
                                xmodelID = xmodelID.substring(0, pos);
                                rdfmodel = rdfmodel.replaceAll(xmodelID, newId);
                                io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                SWBPlatform.getSemanticMgr().createModelByRDF(newId + "_usr", "http://user." + newId + ".swb#", io, "N-TRIPLE");
                            }
                        }if (key.endsWith("_rep")) { //Para los submodelos de dosumentos
                            //TODO
                        }
                        fileModel.delete();
                    }
                }
                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("msgKey", "siteCreated");
                response.setRenderParameter("wsUri", website.getURI());
            }
        }
    }
}

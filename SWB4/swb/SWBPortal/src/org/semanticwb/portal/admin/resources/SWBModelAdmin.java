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
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
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
            SWBResourceURL url = paramRequest.getRenderUrl();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
            StringBuffer strbf = new StringBuffer();
            File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/");
            File[] files = file.listFiles();
            out.println("<div class=\"swbform\">");
            out.println("<table>");
            urlAction.setAction("upload");
            out.println("<form action=\"" + urlAction.toString() + "\" method=\"post\" enctype='multipart/form-data'>");
            out.println("<tr>");
            out.println("<td colspan=\"2\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>Template</td>");
            out.println("<td>Tamaño</td>");
            out.println("<td>Instalar</td>");
            out.println("<td>Descargar</td>");
            out.println("<td>Eliminar</td>");
            out.println("</tr>");
            for (int i = 0; i < files.length; i++) {
                File filex = files[i];
                String fileName = filex.getName();
                if (filex.isFile() && fileName.endsWith(".zip")) {
                    int pos = fileName.lastIndexOf(".");
                    if (pos > -1) {
                        fileName = fileName.substring(0, pos);
                    }
                    out.println("<tr><td>");
                    url.setParameter("zipName", filex.getAbsolutePath());
                    url.setMode("viewmodel");
                    out.println("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + fileName + "</a>");
                    out.println("</td><td>");
                    out.println(filex.length() + " bytes");
                    out.println("</td>");
                    url.setMode("installmodel");
                    url.setAction("form");
                    out.println("<td><a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">Instalar</a></td>");
                    out.println("<td><a href=\"" + WEBPATH + filex.getName() + "\">Descargar</a></td>");
                    urlAction.setParameter("zipName", filex.getAbsolutePath());
                    urlAction.setAction("delete");
                    out.println("<td><a href=\"" + urlAction.toString() + "\" onclick=\"submitUrl('" + urlAction.toString() + "',this);return false;\">Eliminar</a></td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("<tr><td>Subir Archivo .zip(Modelo)<input type=\"file\" name=\"zipmodel\" value=\"Nuevo\"/></td></tr>");
            out.println("<tr><td><input type=\"submit\" value=\"subir\"/></td></tr>");
            out.println("</form>");
            out.println("</table>");
            out.println("</div>");
            out.println(strbf.toString());
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doViewModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<table>");
        for (Iterator<ZipEntry> itfiles = SWBUtils.IO.readZip(request.getParameter("zipName")); itfiles.hasNext();) {
            ZipEntry zen = itfiles.next();
            out.println("<tr><td>" + zen.getName() + "</td></tr>");
        }
        out.println("</table>");
    }

    public void doInstallModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getRenderUrl();
            if (paramRequest.getAction().equals("form")) {
                try {
                    url.setAction("install2");
                    out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){}return false;\" method=\"post\">");
                    out.println("<fieldset>");
                    out.println("<table>");
                    out.append("<tr><td>");
                    out.println(paramRequest.getLocaleLogString("msgwsTitle"));
                    out.println("</td>");
                    out.append("<td>");
                    out.println("<input type=\"text\" name=\"wstitle\" size=\"30\">");
                    out.println("</td>");
                    out.append("</tr>");
                    out.append("<tr><td>");
                    out.println("ID:");
                    out.println("</td>");
                    out.append("<td>");
                    out.println("<input type=\"text\" name=\"wsid\" size=\"30\">");
                    out.println("</td>");
                    out.append("</tr>");
                    out.println("<td><button dojoType='dijit.form.Button' type=\"submit\">Enviar</button>");
                    out.println("</td></tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("<input type=\"hidden\" name=\"zipName\" value=\"" + request.getParameter("zipName") + "\"");
                    out.println("</form>");
                    out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
                } catch (Exception e) {
                    log.debug(e);
                }
            } else if (paramRequest.getAction().equals("install2")) {
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
                    //Mover directorios de modelos a directorio work leyecdo rdfs
                    File[] fieldsUnziped = extractTo.listFiles();
                    for (int i = 0; i < fieldsUnziped.length; i++) {
                        File file = fieldsUnziped[i];
                        if (file.isDirectory()) { //
                            if (file.getName().equals(oldIDModel)) { //Es la carpeta del modelo a principal a cargar
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
                                }
                            }
                        } else { //TODO:Archivos rdf(modelos) y xml (siteinfo) y readme, eliminarlos
                            if (file.getName().endsWith(".rdf")) { //modelos
                            } else if (file.getName().endsWith(".xml")) { //Archivo siteinfo
                            }
                        }
                    }

                    //Parseo de nombre de NameSpace anteriores por nuevos
                    String newNs = "http://www." + newId + ".swb#";
                    FileInputStream frdfio = new FileInputStream(MODELS + newId + "/" + oldIDModel + ".rdf");
                    String rdfcontent = SWBUtils.IO.readInputStream(frdfio);

                    rdfcontent = rdfcontent.replaceAll(oldNamespace, newNs); //Reempplazar namespace anterior x nuevo

                    //Reemplaza ids de repositorios de usuarios y documentos x nuevos
                    rdfcontent = rdfcontent.replaceAll(oldIDModel + "_usr", newId + "_usr");
                    rdfcontent = rdfcontent.replaceAll("http://user."+oldIDModel + ".swb#", "http://user."+newId + ".swb#");
                    rdfcontent = rdfcontent.replaceAll(oldIDModel + "_rep", newId + "_rep");
                    rdfcontent = rdfcontent.replaceAll("http://rep."+oldIDModel + ".swb#", "http://rep."+newId + ".swb#");

                    //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
                    rdfcontent = parseRdfContent(rdfcontent, oldTitle, newTitle, oldIDModel, newId, newNs);

                    File filex = new File(MODELS + newId + "/" + newId + "_george_site.rdf");
                    FileOutputStream outjx = new FileOutputStream(filex);
                    outjx.write(rdfcontent.getBytes());
                    outjx.flush();
                    outjx.close();

                    //Mediante inputStream creado generar sitio
                    InputStream io = SWBUtils.IO.getStreamFromString(rdfcontent);
                    SemanticModel model = SWBPlatform.getSemanticMgr().createModelByRDF(newId, newNs, io);
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
                        frdfio = new FileInputStream(MODELS + newId + "/" + xmodelID + ".rdf");
                        String rdfmodel = SWBUtils.IO.readInputStream(frdfio);
                        if (key.endsWith("_usr")) { //Para los submodelos de usuarios y de documentos del modelo principal
                            int pos = xmodelID.lastIndexOf("_usr");
                            if (pos > -1) {
                                xmodelID = xmodelID.substring(0, pos);
                                System.out.println("remplaza:"+xmodelID+",newId:"+newId);
                                rdfmodel = rdfmodel.replaceAll(xmodelID, newId);

                                File file = new File(MODELS + newId + "/" + oldIDModel + "_george_usr.rdf");
                                FileOutputStream outj = new FileOutputStream(file);
                                outj.write(rdfmodel.getBytes());
                                outj.flush();
                                outj.close();

                                io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                SWBPlatform.getSemanticMgr().createModelByRDF(xmodelID, "http://user." + newId + ".swb#", io);
                            }
                        }
                    }
                    out.println("<script type=\"text/javascript\">");
                    out.println("hideDialog();");
                    out.println("addItemByURI(mtreeStore, null, '" + website.getURI() + "');");
                    out.println("showStatus('Sitio Creado');");
                    out.println("</script>");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR J:"+e.getMessage());
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
        }
    }
}

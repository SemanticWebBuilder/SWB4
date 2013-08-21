/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.Activity;
import org.semanticwb.process.model.Containerable;
import org.semanticwb.process.model.Documentation;
import org.semanticwb.process.model.Event;
import org.semanticwb.process.model.Gateway;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.Lane;
import org.semanticwb.process.model.ProcessElement;
import org.semanticwb.process.model.SubProcess;

/**
 *
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class DocumentationResource extends GenericAdmResource {

    public static String MOD_UPDATETEXT = "updateText";
    public static String PARAM_TEXT = "txt";
    private static Logger log = SWBUtils.getLogger(DocumentationResource.class);

    void doUpdate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        String idDocumentation = request.getParameter("idDocumentation");
        if (suri != null) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                String txt = request.getParameter(PARAM_TEXT);
                request.setAttribute("suri", suri);
                //TODO: Actualizar el texto del richText
                Documentation doc = Documentation.ClassMgr.getDocumentation(idDocumentation, paramRequest.getWebPage().getWebSite());
                //Guardar el texto de la documentación
                if (doc != null) {
                    doc.setText(txt.trim());
                    if (doc.getText().replace("&nbsp;", "").trim().length() < 62) {
                        doc.setText("<p>" + paramRequest.getLocaleString("hereDoc") + "</p>");
                    }
                    doc.setTextFormat("text/html");
                }
            }
        }
        doView(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        PrintWriter out = response.getWriter();
        if (suri != null) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                //TODO: Desplegar editor
                SWBResourceURL url = paramRequest.getRenderUrl();
                String despliege = getResourceBase().getAttribute("despliegue") != null ? getResourceBase().getAttribute("despliegue") : "Edición";
                if (despliege.equals("Vista")) {
                    url.setMode("viewDocumentation");
                } else {
                    url.setMode("documentation");
                }
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                url.setParameter("suri", request.getParameter("suri"));
                out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\"" + url + "\" style=\"width:100%; height:100%;\" frameborder=\"0\"></iframe>");
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        try {
            if ("documentation".equals(mode)) {
                doProcessDocumentation(request, response, paramRequest);
            } else if (MOD_UPDATETEXT.equals(mode)) {
                doUpdate(request, response, paramRequest);
            } else if ("doExportDocument".equals(mode)) {
                doExportDocument(request, response, paramRequest);
            } else if ("viewDocumentation".equals(mode)) {
                doViewDocumentation(request, response, paramRequest);
            } else {
                super.processRequest(request, response, paramRequest);
            }
        } catch (Exception ex) {
            log.error("Error on processRequest: , " + ex.getMessage());
        }
    }

    public void doProcessDocumentation(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/process/documentation/DocumentationResource.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        String suri = request.getParameter("suri");
        response.setContentType("text/html; charset=UTF-8");
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("suri", request.getParameter("suri"));
            if (suri != null) {
                ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
                Documentation doc = null;
                if (pe.listDocumentations().hasNext()) {
                    doc = pe.getDocumentation();
                } else {
                    //Si no existe documentación, crearla
                    doc = Documentation.ClassMgr.createDocumentation(paramRequest.getWebPage().getWebSite());
                    //Agregar la documentación al elemento
                    doc.setTextFormat("text/html");
                    doc.setText("<p>" + paramRequest.getLocaleString("hereDoc") + "</p>");
                    pe.addDocumentation(doc);
                }
                request.setAttribute("idDocumentation", doc.getId());
            }
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error doProcessDocumentation.jsp, " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void doExportDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ServletException, DocumentException, Exception {
        response.setContentType("text/html; charset=UTF-8");
        OutputStream ou = response.getOutputStream();
        String suri = request.getParameter("suri") != null ? request.getParameter("suri").toString() : "";
        String format = request.getParameter("format") != null ? request.getParameter("format").toString() : "";
        String laneT = paramRequest.getLocaleString("lane") != null ? paramRequest.getLocaleString("lane") : "Lane";
        String activityT = paramRequest.getLocaleString("activity") != null ? paramRequest.getLocaleString("activity") : "Activity";
        String gatewayT = paramRequest.getLocaleString("gateway") != null ? paramRequest.getLocaleString("gateway") : "Gateway";
        String eventT = paramRequest.getLocaleString("event") != null ? paramRequest.getLocaleString("event") : "Event";
        if (!format.equals("") && !suri.equals("")) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                ArrayList lane = new ArrayList();
                ArrayList activity = new ArrayList();
                ArrayList gateway = new ArrayList();
                ArrayList event = new ArrayList();
                Iterator<GraphicalElement> iterator = null;
                GraphicalElement ge = null;
                if (pe instanceof org.semanticwb.process.model.Process) {
                    org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
                    if (format.equals("pdf")) { // is pdf
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + pe.getTitle() + ".pdf\";");
                        iterator = process.listAllContaineds();
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            if (ge instanceof Lane) {
                                lane.add(ge);
                            }
                            if (ge instanceof Activity) {
                                activity.add(ge);
                            }
                            if (ge instanceof Gateway) {
                                gateway.add(ge);
                            }
                            if (ge instanceof Event) {
                                event.add(ge);
                            }
                        }
                        Document doc = new Document(PageSize.A4);
                        File file = new File(SWBPortal.getWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/Resource/" + process.getTitle() + ".pdf");
                        if (file.exists()) {
                            file.delete();
                            file = new File(SWBPortal.getWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/Resource/" + process.getTitle() + ".pdf");
                        }
                        FileOutputStream fileOut = new FileOutputStream(file);
                        //Save on server
                        PdfWriter.getInstance(doc, fileOut);
                        //Show on response
                        PdfWriter.getInstance(doc, ou);
                        doc.open();
                        doc.addAuthor(paramRequest.getUser().getFullName());
                        doc.addCreator(paramRequest.getUser().getFullName());
                        doc.addCreationDate();
                        doc.addTitle(process.getTitle());

                        Image header = Image.getInstance(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/documentation/styles/css/images/logoprocess.png");
                        if (header != null) {
                            header.setAlignment(Chunk.ALIGN_LEFT);
                            header.rectangle(230, 20);
                            doc.add(header);
                        }
                        HTMLWorker hw = new HTMLWorker(doc);
                        //Documentatión from process
                        hw.parse(new StringReader(process.getDocumentation().getText()));
                        //Lane
                        iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + laneT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h3>" + ge.getTitle() + "</h3>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));

                        }
                        //Activity
                        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + activityT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h3>" + ge.getTitle() + "</h3>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                        }
                        //Gateway
                        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + gatewayT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h3>" + ge.getTitle() + "</h3>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                        }
                        //Event
                        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + eventT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h3>" + ge.getTitle() + "</h3>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                        }
                        doc.close();
                    } else { // is html
                        String webPath = SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/documentation/styles/";
                        String basePath = SWBPortal.getWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/Resource/" + pe.getTitle() + "/";
                        File dest = new File(basePath);
                        if (!dest.exists()) {
                            dest.mkdirs();
                        }
                        if (pe instanceof org.semanticwb.process.model.Process) {
                            process = (org.semanticwb.process.model.Process) pe;
                            iterator = process.listContaineds();
                            Iterator<GraphicalElement> itFiles = process.listAllContaineds();
                            while (itFiles.hasNext()) {
                                GraphicalElement geFiles = itFiles.next();
                                if (geFiles instanceof SubProcess) {
                                    createHtmlSubProcess(process, ((SubProcess) geFiles), paramRequest, basePath);
                                }
                            }
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                if (ge instanceof Lane) {
                                    lane.add(ge);
                                }
                                if (ge instanceof Activity) {
                                    activity.add(ge);
                                }
                                if (ge instanceof Gateway) {
                                    gateway.add(ge);
                                }
                                if (ge instanceof Event) {
                                    event.add(ge);
                                }
                            }
                        }
                        SWBUtils.IO.copyStructure(webPath, basePath);
                        String html = "";
                        html += "<link href=\"css/process.css\" rel=\"stylesheet\" type=\"text/css\"/>";
                        html += "<div id=\"contenedor\">"; //Begin contenedor
                        html += "<div id=\"header\" title=\"" + pe.getTitle() + "\">" + pe.getTitle() + "<img src=\"css/images/logoprocess.png\"></div>";

                        html += "<div id=\"menu\">";//Begin menú
                        html += "<ul>";
                        html += "<li><a href=\"#ruta\" title=\"" + paramRequest.getLocaleString("home") + "\">" + paramRequest.getLocaleString("home") + "</a></li>";
                        if (lane.size() > 0) {
                            html += "<li class=\"activity\" title=\"" + laneT + "\">" + laneT + "</li>";
                        }
                        String ref = "";
                        iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            ref = "#" + ge.getURI();
                            if (ge instanceof SubProcess) {
                                ref = ((SubProcess) ge).getTitle() + ".html";
                            }
                            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                        }
                        if (activity.size() > 0) {
                            html += "<li class=\"activity\" title=\"" + activityT + "\">" + activityT + "</li>";
                        }
                        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            ref = "#" + ge.getURI();
                            if (ge instanceof SubProcess) {
                                ref = ((SubProcess) ge).getTitle() + ".html";
                            }
                            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                        }
                        if (gateway.size() > 0) {
                            html += "<li class=\"activity\" title=\"" + gatewayT + "\">" + gatewayT + "</li>";
                        }
                        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            ref = "#" + ge.getURI();
                            if (ge instanceof SubProcess) {
                                ref = ((SubProcess) ge).getTitle() + ".html";
                            }
                            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                        }
                        if (event.size() > 0) {
                            html += "<li class=\"activity\" title=\"" + eventT + "\">" + eventT + "</li>";
                        }
                        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            ref = "#" + ge.getURI();
                            if (ge instanceof SubProcess) {
                                ref = ((SubProcess) ge).getTitle() + ".html";
                            }
                            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                        }
                        html += "</ul>";
                        html += "</div>";//End menú
                        html += "<div id=\"contenido\">";//Begin contenido
                        html += "<div id=\"ruta\">";//Begin ruta
                        html += "<label>" + paramRequest.getLocaleString("theseIn") + ":</label>";
                        html += "<a title=\"" + pe.getTitle() + "\" style=\"cursor: pointer;\" href=\"#\">" + pe.getTitle() + "</a>";
                        html += "</div>";//End ruta
                        String data = process.getData() != null ? process.getData() : "No se ha generado imagen";
                        html += "<div> " + data + "</div>";
                        html += "<div id=\"texto\">";//Begin texto
                        html += pe.getDocumentation().getText();
                        if (lane.size() > 0) {
                            html += "<div class=\"bandeja-combo\" title=\"" + laneT + "\"><strong>" + laneT + "</strong></div>";//Contains Lane
                        }
                        iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
                            html += ge.getDocumentation().getText();
                        }
                        if (activity.size() > 0) {
                            html += "<div class=\"bandeja-combo\" title=\"" + activityT + "\"><strong>" + activityT + "</strong></div>";//Contains Activity
                        }
                        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
                            html += ge.getDocumentation().getText();
                        }
                        if (gateway.size() > 0) {
                            html += "<div class=\"bandeja-combo\" title=\"" + gatewayT + "\"><strong>" + gatewayT + "</strong></div>";//Contains Gateway
                        }
                        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
                            html += ge.getDocumentation().getText();
                        }
                        if (event.size() > 0) {
                            html += "<div class=\"bandeja-combo\" title=\"" + eventT + "\"><strong>" + eventT + "</strong></div>";//Contains Event
                        }
                        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
                            html += ge.getDocumentation().getText();
                        }
                        html += "</div>";//End texto
                        html += "</div>";//End contenedor
                        File index = new File(basePath + "index.html");
                        FileOutputStream out = new FileOutputStream(index);
                        out.write(html.getBytes());
                        out.flush();
                        out.close();
                        //In response for save or view
                        response.setContentType("application/zip");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + pe.getTitle() + ".zip\"");
                        //For create file on server
//                        File zip = new File(SWBPortal.getWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/Resource/" + pe.getTitle() + ".zip");
//                        if (zip.exists()) {
//                            zip.delete();
//                        }
//                        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
                        //Show on response
                        ZipOutputStream zos = new ZipOutputStream(ou);
                        SWBUtils.IO.zip(dest, new File(basePath), zos);
                        zos.flush();
                        zos.close();
                        deleteDerectory(dest);
                    }
                    ou.flush();
                    ou.close();
                }
            }
        }
    }
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void createHtmlSubProcess(org.semanticwb.process.model.Process process, SubProcess subProcess, SWBParamRequest paramRequest, String basePath) throws FileNotFoundException, IOException, SWBResourceException {
        ArrayList activity = new ArrayList();
        ArrayList gateway = new ArrayList();
        ArrayList event = new ArrayList();
        Iterator<GraphicalElement> iterator = subProcess.listContaineds();
        GraphicalElement ge = null;
        Containerable con = null;
        String activityT = paramRequest.getLocaleString("activity") != null ? paramRequest.getLocaleString("activity") : "Activity";
        String gatewayT = paramRequest.getLocaleString("gateway") != null ? paramRequest.getLocaleString("gateway") : "Gateway";
        String eventT = paramRequest.getLocaleString("event") != null ? paramRequest.getLocaleString("event") : "Event";
        String path = "";
        while (iterator.hasNext()) {
            ge = iterator.next();
            if (ge instanceof Activity) {
                activity.add(ge);
            }
            if (ge instanceof Gateway) {
                gateway.add(ge);
            }
            if (ge instanceof Event) {
                event.add(ge);
            }
        }
        String html = "";
        html += "<link href=\"css/process.css\" rel=\"stylesheet\" type=\"text/css\"/>";
        html += "<div id=\"contenedor\">"; //Begin contenedor
        html += "<div id=\"header\" title=\"" + subProcess.getTitle() + "\">" + subProcess.getTitle() + "<img src=\"css/images/logoprocess.png\"></div>";
        html += "<div id=\"menu\">";//Begin menú
        html += "<ul>";
        html += "<li><a href=\"#ruta\" title=\"" + paramRequest.getLocaleString("home") + "\">" + paramRequest.getLocaleString("home") + "</a></li>";
        String ref = "";
        if (activity.size() > 0) {
            html += "<li class=\"activity\" title=\"" + activityT + "\">" + activityT + "</li>";
        }
        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            ref = "#" + ge.getURI();
            if (ge instanceof SubProcess) {
                ref = ((SubProcess) ge).getTitle() + ".html";
            }
            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
        }
        if (gateway.size() > 0) {
            html += "<li class=\"activity\" title=\"" + gatewayT + "\">" + gatewayT + "</li>";
        }
        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            ref = "#" + ge.getURI();
            if (ge instanceof SubProcess) {
                ref = ((SubProcess) ge).getTitle() + ".html";
            }
            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
        }
        if (event.size() > 0) {
            html += "<li class=\"activity\" title=\"" + eventT + "\">" + eventT + "</li>";
        }
        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            ref = "#" + ge.getURI();
            if (ge instanceof SubProcess) {
                ref = ((SubProcess) ge).getTitle() + ".html";
            }
            html += "<li><a href=\"" + ref + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
        }
        html += "</ul>";
        html += "</div>";//End menú
        html += "<div id=\"contenido\">";//Begin contenido
        html += "<div id=\"ruta\">";//Begin ruta
        html += "<label>" + paramRequest.getLocaleString("theseIn") + ":</label>";
        con = subProcess.getContainer();
        while (con != null) {
            path = ((ProcessElement) con).getURI() + "|" + path;
            if (con instanceof SubProcess) {
                con = ((SubProcess) con).getContainer();
            } else {
                con = null;
            }
        }
        String[] urls = path.split("\\|");
        int cont = urls.length;
        for (int i = 0; i < urls.length; i++) {
            ProcessElement peAux = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urls[i]);
            String title = peAux.getTitle();
            String refAux = title + ".html";
            if (i == 0) {
                refAux = "index.html";
            }
            html += "<a href=\"" + refAux + "\">" + title + "</a>";
            if (i < cont) {
                html += "<label> | </label>";
            }
        }
        html += "<a title=\"" + subProcess.getTitle() + "\" style=\"cursor: pointer;\" href=\"#\">" + subProcess.getTitle() + "</a>";
        html += "</div>";//End ruta
        String data = subProcess.getData() != null ? subProcess.getData() : paramRequest.getLocaleString("noImage");
        html += "<div>" + data + "</div>";
        html += "<div id=\"texto\">";//Begin texto
        html += subProcess.getDocumentation().getText();
        if (activity.size() > 0) {
            html += "<div class=\"bandeja-combo\" title=\"" + activityT + "\"><strong>" + activityT + "</strong></div>";//Contains Activity
        }
        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
            html += ge.getDocumentation().getText();
        }
        if (gateway.size() > 0) {
            html += "<div class=\"bandeja-combo\" title=\"" + gatewayT + "\"><strong>" + gatewayT + "</strong></div>";//Contains Gateway
        }
        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
            html += ge.getDocumentation().getText();
        }
        if (event.size() > 0) {
            html += "<div class=\"bandeja-combo\" title=\"" + eventT + "\"><strong>" + eventT + "</strong></div>";//Contains Event
        }
        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
        while (iterator.hasNext()) {
            ge = iterator.next();
            html += "<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>";
            html += ge.getDocumentation().getText();
        }
        html += "</div>";//End texto
        html += "</div>";//End contenedor
        File index = new File(basePath + "/" + subProcess.getTitle() + ".html");
        FileOutputStream out = new FileOutputStream(index);
        out.write(html.getBytes());
        out.flush();
        out.close();
    }

    public static void deleteDerectory(File dir) {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                deleteDerectory(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        dir.delete();
    }

    void doViewDocumentation(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ServletException {
        String path = "/swbadmin/jsp/process/documentation/ViewDocumentation.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("suri", request.getParameter("suri"));
        response.setContentType("text/html; charset=UTF-8");
        rd.include(request, response);
    }
}

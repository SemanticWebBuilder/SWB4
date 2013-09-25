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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.semanticwb.process.model.ConnectionObject;
import org.semanticwb.process.model.Containerable;
import org.semanticwb.process.model.DataObject;
import org.semanticwb.process.model.Documentation;
import org.semanticwb.process.model.Event;
import org.semanticwb.process.model.Gateway;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.Lane;
import org.semanticwb.process.model.ProcessElement;
import org.semanticwb.process.model.SequenceFlow;
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
            ex.printStackTrace();
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
        String dataT = paramRequest.getLocaleString("data") != null ? paramRequest.getLocaleString("data") : "Data";
        if (!format.equals("") && !suri.equals("")) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                ArrayList lane = new ArrayList();
                ArrayList activity = new ArrayList();
                ArrayList gateway = new ArrayList();
                ArrayList event = new ArrayList();
                ArrayList dataob = new ArrayList();
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
                            if (ge instanceof DataObject) {
                                dataob.add(ge);
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
                            hw.parse(new StringReader("<br><h4>" + ge.getTitle() + "</h4>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));

                        }
                        //Activity
                        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + activityT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h4>" + ge.getTitle() + "</h4>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                        }
                        //Gateway
                        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + gatewayT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h4>" + ge.getTitle() + "</h4>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                            //ConnectionObject
                            Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                            while (itConObj.hasNext()) {
                                ConnectionObject connectionObj = itConObj.next();
                                if (connectionObj instanceof SequenceFlow) {
                                    hw.parse(new StringReader("<br><h4>" + connectionObj.getTitle() + "</h4>"));
                                    hw.parse(new StringReader(connectionObj.getDocumentation().getText()));
                                }
                            }
                        }
                        //Event
                        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + eventT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h4>" + ge.getTitle() + "</h4>"));
                            hw.parse(new StringReader(ge.getDocumentation().getText()));
                        }
                        //Data
                        iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                        hw.parse(new StringReader("<br><h1>" + eventT + "</h1>"));
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            hw.parse(new StringReader("<br><h4>" + ge.getTitle() + "</h4>"));
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
                                if (ge instanceof DataObject) {
                                    dataob.add(ge);
                                }
                            }
                        }
//                        SWBUtils.IO.copyStructure(webPath, basePath);
//                        System.out.println("webPath: " + webPath);
//                        System.out.println("basePath: " + basePath);
                        File bootstrap = new File(basePath + "bootstrap/");
                        if (!bootstrap.exists()) {
                            bootstrap.mkdirs();
                        }
                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/utils/bootstrap/", basePath + "/bootstrap/");
                        //Add directory documentation
                        File documentation = new File(basePath + "documentation/");
                        if (!documentation.exists()) {
                            documentation.mkdirs();
                        }
                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/documentation/css/", basePath + "/documentation/");
                        //Add jquery
                        File jquery = new File(basePath + "jquery/");
                        if (!jquery.exists()) {
                            jquery.mkdirs();
                        }
                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/utils/jquery/", basePath + "/jquery/");
                        //Add taskInbox
                        File taskInbox = new File(basePath + "taskInbox/css/");
                        if (!taskInbox.exists()) {
                            taskInbox.mkdirs();
                        }
                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/taskInbox/css/", basePath + "/taskInbox/css/");
                        //Add fontawesome
                        File fontawesome = new File(basePath + "fontawesome/");
                        if (!fontawesome.exists()) {
                            fontawesome.mkdirs();
                        }
                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/utils/fontawesome/", basePath + "/fontawesome/");
                        String html = "";
                        //Index
                        html += "<script type=\"text/javascript\" src=\"bootstrap/bootstrap.min.js\"></script>\n"//Begin imports
                                + "<link href=\"bootstrap/bootstrap.min.css\" rel=\"stylesheet\">\n"
                                + "<link href=\"fontawesome/css/font-awesome.min.css\" rel=\"stylesheet\">\n"
                                + "<link href=\"taskInbox/css/swbp.css\" rel=\"stylesheet\">\n"
                                + "<script type=\"text/javascript\" src=\"jquery/jquery.min.js\"></script>\n"
                                + "<link href=\"documentation/style.css\" rel=\"stylesheet\">\n"
                                + "<script type=\'text/javascript\'> //Activate tooltips\n"
                                + "    $(document).ready(function() {"
                                + "        if ($(\"[data-toggle=tooltip]\").length) {"
                                + "            $(\"[data-toggle=tooltip]\").tooltip();"
                                + "        }"
                                + "        $('body').off('.data-api');"
                                + "    });"
                                + "</script>\n"; //End imports

                        html += "<div class=\"swbp-content-wrapper\">";//Begin wrapper
                        html += "<div class=\"row swbp-header hidden-xs\">\n" //Begin header
                                + "    <a href=\"#\">\n"
                                + "        <div class=\"swbp-brand\"></div>\n"
                                + "    </a>\n"
                                + "</div>\n"
                                + "<nav class=\"swbp-toolbar hidden-xs\" role=\"navigation\">\n"
                                + "<div style=\"text-align: center;\">\n"
                                + "    <ul class=\"swbp-nav\">\n"
                                + "<li><h2><i class=\"icon-gears\" style=\"width: auto;\"></i> " + pe.getTitle() + "</h2></li>"
                                //                                + "        <li class=\"active\">"
                                //                                + "<a href=\"#\"><i class=\"icon-gears\"></i><span>" + pe.getTitle() + "</span></a>\n"
                                //                                + "</li>\n"
                                + "</ul>\n"
                                + "</div>\n"
                                + "</nav>\n"; //End header
                        html += "<div class=\"swbp-user-menu\">";
                        html += "<ul class=\"breadcrumb\">"; //Begin ruta
                        html += "<li class=\"active\">" + process.getTitle() + "</li>"; //Ruta
                        html += "</ul>"; //End ruta
                        html += "</div>";
                        String ref = "";
                        html += "<div class=\"col-lg-2 col-md-2 col-sm-4 hidden-xs\">";//Begin menu
                        //html += "<a href=\"#ruta\" style=\"width: 100%;\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + paramRequest.getLocaleString("home") + "\" class=\"btn btn-success btn-sm swbp-btn-start\"><i class=\"icon-home\"></i>" + paramRequest.getLocaleString("home") + "</a>";//Ruta
                        html += "<div class=\"swbp-left-menu swbp-left-menu-doc\">";//Begin body menu
                        html += "<ul class=\"nav nav-pills nav-stacked\">";
                        if (lane.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#lane\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + laneT + " " + lane.size() + "\">" + laneT + "<span class=\"badge pull-right\">" + lane.size() + "</span></a></li>";
                            iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getURI();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                            }
                        }
                        if (activity.size() > 0) {
                            html += "<li class=\"active\"> <a href=\"#activity\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + activityT + " " + activity.size() + "\">" + activityT + "<span class=\"badge pull-right\">" + activity.size() + "</span></a></li>";
                            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getURI();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                            }
                        }
                        if (gateway.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#gateway\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + gatewayT + " " + gateway.size() + "\">" + gatewayT + "<span class=\"badge pull-right\">" + gateway.size() + "</span></a></li>";
                            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getURI();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                            }
                        }
                        if (event.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#event\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + eventT + " " + event.size() + "\">" + eventT + "<span class=\"badge pull-right\">" + event.size() + "</span></a></li>";
                            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getURI();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                            }
                        }
                        if (dataob.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#dataob\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + dataT + " " + dataob.size() + "\">" + dataT + "<span class=\"badge pull-right\">" + dataob.size() + "</span></a></li>";
                            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getURI();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
                            }
                        }
                        html += "</ul>";
                        html += "</div>";//End body menu
                        html += "</div>";//End menu
                        html += "<div class=\"col-lg-10 col-md-10 col-sm-8\" role=\"main\">";//Begin content
                        html += "<div class=\"contenido\">"; //Begin body content
                        /**
                         * BEGIN IMAGE MODEL
                         */
//                        String data = process.getData() != null ? process.getData() : paramRequest.getLocaleString("noImage");
//                        html += "<div id=\"ruta\">" + data + "</div>"; 
                        /**
                         * END IMAGE MODEL
                         */
                        html += "<div class=\"panel panel-default\">\n"//Documentation Process
                                + "   <div class=\"panel-heading\">\n"
                                + "        <div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("docFromPro") + " " + pe.getTitle() + "</strong></div>\n"
                                + "   </div>\n"
                                + "   <div class=\"panel-body\">\n"
                                + pe.getDocumentation().getText()
                                + "   </div>\n"
                                + "</div>";
                        if (lane.size() > 0) {//Lane
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"lane\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + laneT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>";
                            html += "</div>";
                        }
                        if (activity.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"activity\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + activityT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        if (gateway.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"gateway\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + gatewayT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                                //Begin ConnectionObject
                                Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                                while (itConObj.hasNext()) {
                                    ConnectionObject connectionObj = itConObj.next();
                                    if (connectionObj instanceof SequenceFlow) {
                                        html += "<i class=\"icon-arrow-right\"></i> <h4 id=\"" + connectionObj.getURI() + "\" title=\"" + connectionObj.getTitle() + "\">" + connectionObj.getTitle() + "</h4>";
                                        html += connectionObj.getDocumentation().getText();
                                    }
                                }
                                //End ConnectionObject
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        if (event.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"event\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + eventT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        if (dataob.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"dataob\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + dataT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        html += "</div>";//End body content
                        html += "</div>";//End content
                        html += "</div>";//End wrapper
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
//    public static void copyFile(String sourceFile, String destFile) throws IOException {
//        InputStream inStream = null;
//        OutputStream outStream = null;
//        try {
//
//            File afile = new File(sourceFile);
//            File bfile = new File(destFile);
//            inStream = new FileInputStream(afile);
//            outStream = new FileOutputStream(bfile);
//            byte[] buffer = new byte[1024];
//            int length;
//            //copy the file content in bytes 
//            while ((length = inStream.read(buffer)) > 0) {
//                outStream.write(buffer, 0, length);
//            }
//            inStream.close();
//            outStream.close();
//        } catch (IOException e) {
//            System.out.println("error to copy file " + sourceFile + ", " + e.getMessage());
//        }
//    }
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void createHtmlSubProcess(org.semanticwb.process.model.Process process, SubProcess subProcess, SWBParamRequest paramRequest, String basePath) throws FileNotFoundException, IOException, SWBResourceException {
        ArrayList activity = new ArrayList();
        ArrayList gateway = new ArrayList();
        ArrayList event = new ArrayList();
        ArrayList dataob = new ArrayList();
        Iterator<GraphicalElement> iterator = subProcess.listContaineds();
        GraphicalElement ge = null;
        Containerable con = null;
        String activityT = paramRequest.getLocaleString("activity") != null ? paramRequest.getLocaleString("activity") : "Activity";
        String gatewayT = paramRequest.getLocaleString("gateway") != null ? paramRequest.getLocaleString("gateway") : "Gateway";
        String eventT = paramRequest.getLocaleString("event") != null ? paramRequest.getLocaleString("event") : "Event";
        String dataT = paramRequest.getLocaleString("data") != null ? paramRequest.getLocaleString("data") : "Data";
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
            if (ge instanceof DataObject) {
                dataob.add(ge);
            }
        }

        con = subProcess.getContainer();
        while (con != null) {
            path = ((ProcessElement) con).getURI() + "|" + path;
            if (con instanceof SubProcess) {
                con = ((SubProcess) con).getContainer();
            } else {
                con = null;
            }
        }


        String html = "";
        html += "<script type=\"text/javascript\" src=\"bootstrap/bootstrap.min.js\"></script>\n"//Begin imports
                + "<link href=\"bootstrap/bootstrap.min.css\" rel=\"stylesheet\">\n"
                + "<link href=\"fontawesome/css/font-awesome.min.css\" rel=\"stylesheet\">\n"
                + "<link href=\"taskInbox/css/swbp.css\" rel=\"stylesheet\">\n"
                + "<script type=\"text/javascript\" src=\"jquery/jquery.min.js\"></script>\n"
                + "<link href=\"documentation/style.css\" rel=\"stylesheet\">\n"
                + "<script type=\'text/javascript\'> //Activate tooltips\n"
                + "    $(document).ready(function() {"
                + "        if ($(\"[data-toggle=tooltip]\").length) {"
                + "            $(\"[data-toggle=tooltip]\").tooltip();"
                + "        }"
                + "        $('body').off('.data-api');"
                + "    });"
                + "</script>\n"; //End imports
        html += "<div class=\"swbp-content-wrapper\">";//Begin wrapper
        html += "<div class=\"row swbp-header hidden-xs\">\n" //Begin header
                + "    <a href=\"#\">\n"
                + "        <div class=\"swbp-brand\"></div>\n"
                + "    </a>\n"
                + "</div>\n"
                + "<nav class=\"swbp-toolbar hidden-xs\" role=\"navigation\">\n"
                + "<div style=\"text-align: center;\">\n"
                + "    <ul class=\"swbp-nav\">\n"
                + "<li><h2><i class=\"icon-gears\" style=\"width: auto;\"></i> " + subProcess.getTitle() + "</h2></li>"
                + "</li>\n"
                + "</ul>\n"
                + "</div>\n"
                + "</nav>\n"; //End header
        html += "<div class=\"swbp-user-menu\">";

        /**
         * ********************** BEGIN RUTA*********************************
         */
        html += "<ul class=\"breadcrumb \">\n"; //Begin ruta
        String[] urls = path.split("\\|");
        for (int i = 0; i < urls.length; i++) {
            System.out.println("urls[i]: " + urls[i]);
            ProcessElement peAux = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urls[i]);
            String title = peAux.getTitle();
            String refAux = title + ".html";
            if (i == 0) {
                refAux = "index.html";
            }
            html += "<li><a href=\"" + refAux + "\">" + title + "</a></li>\n";
        }
        html += "<li class=\"active\">" + subProcess.getTitle() + "</li>\n";
        html += "</ul>\n"; //End ruta
        html += "</div>\n";
        /**
         * ********************** END RUTA*********************************
         */
        String ref = "";
        html += "<div class=\"col-lg-2 col-md-2 col-sm-4 hidden-xs\">";//Begin menu
        //html += "<a href=\"#ruta\" style=\"width: 100%;\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + paramRequest.getLocaleString("home") + "\" class=\"btn btn-success btn-sm swbp-btn-start\"><i class=\"icon-home\"></i>" + paramRequest.getLocaleString("home") + "</a>";//Ruta
        html += "<div class=\"swbp-left-menu swbp-left-menu-doc\">";//Begin body menu
        html += "<ul class=\"nav nav-pills nav-stacked\">";
        if (activity.size() > 0) {
            html += "<li class=\"active\"> <a href=\"#activity\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + activityT + " " + activity.size() + "\">" + activityT + "<span class=\"badge pull-right\">" + activity.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getURI();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (gateway.size() > 0) {
            html += "<li class=\"active\"><a href=\"#gateway\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + gatewayT + " " + gateway.size() + "\">" + gatewayT + "<span class=\"badge pull-right\">" + gateway.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getURI();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (event.size() > 0) {
            html += "<li class=\"active\"><a href=\"#event\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + eventT + " " + event.size() + "\">" + eventT + "<span class=\"badge pull-right\">" + event.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getURI();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (dataob.size() > 0) {
            html += "<li class=\"active\"><a href=\"#dataob\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + dataT + " " + dataob.size() + "\">" + dataT + "<span class=\"badge pull-right\">" + dataob.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getURI();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        html += "</ul>";
        html += "</div>";//End body menu
        html += "</div>";//End menu
        html += "<div class=\"col-lg-10 col-md-10 col-sm-8 col-xs-\" role=\"main\">\n";//Begin content
        html += "<div class=\"contenido\">\n"; //Begin body content
        /**
         * BEGIN IMAGE MODEL
         */
//        String data = subProcess.getData() != null ? subProcess.getData() : paramRequest.getLocaleString("noImage");
//        html += "<div id=\"ruta\">" + data + "</div>\n";
        /**
         * BEGIN IMAGE MODEL
         */
        html += subProcess.getDocumentation().getText();
        if (activity.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"activity\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + activityT + "</strong></div></div>\n";
            html += "<div class=\"panel-body\">";
            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        if (gateway.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"gateway\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + gatewayT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
                //Begin ConnectionObject
                Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                while (itConObj.hasNext()) {
                    ConnectionObject connectionObj = itConObj.next();
                    if (connectionObj instanceof SequenceFlow) {
                        html += "<i class=\"icon-arrow-right\"></i><h4 id=\"" + connectionObj.getURI() + "\" title=\"" + connectionObj.getTitle() + "\">" + connectionObj.getTitle() + "</h4>\n";
                        html += connectionObj.getDocumentation().getText();
                    }
                }
                //End ConnectionObject
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        if (event.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"event\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + eventT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        if (dataob.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"dataob\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + dataT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        html += "</div>\n";//End body content
        html += "</div>";//End content
        html += "</div>\n";//End wrapper
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

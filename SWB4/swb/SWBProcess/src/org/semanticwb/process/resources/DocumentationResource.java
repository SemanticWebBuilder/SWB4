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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        response.setContentType("text/html; charset=UTF-8");
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
                url.setParameter("despliege", despliege);
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                url.setParameter("suri", request.getParameter("suri"));
                out.println("<iframe src=\"" + url + "\" style=\"width:100%; height:100%;\" frameborder=\"0\"></iframe>");
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
            } else if (mode.equals("viewModel")) {
                doViewModel(request, response, paramRequest);
            } else {
                super.processRequest(request, response, paramRequest);
            }
        } catch (Exception ex) {
            log.error("Error on processRequest: , " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void doViewModel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = SWBPlatform.getContextPath() + "/swbadmin/jsp/process/documentation/DocumentationModel.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("suri", request.getParameter("suri"));
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error on doViewModel, " + path + ", " + e.getCause());
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
                        //Create model
                        createModel(suri, basePath);

                        if (pe instanceof org.semanticwb.process.model.Process) {
                            process = (org.semanticwb.process.model.Process) pe;
                            iterator = process.listContaineds();
                            Iterator<GraphicalElement> itFiles = process.listAllContaineds();
                            while (itFiles.hasNext()) {
                                GraphicalElement geFiles = itFiles.next();
                                if (geFiles instanceof SubProcess) {
                                    createHtmlSubProcess(process, ((SubProcess) geFiles), paramRequest, basePath, suri);
//                                    createModel(suri, basePath);
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
                        //Add modeler
//                        File modeler = new File(basePath + "modeler/");
//                        if (!modeler.exists()) {
//                            modeler.mkdirs();
//                        }
//                        SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/", basePath + "/modeler/");
                        File modeler = new File(basePath + "modeler/");
                        if (!modeler.exists()) {
                            modeler.mkdirs();
                        }
                        File toolkitFile = new File(basePath + "modeler/toolkit.js");
                        File modelerFile = new File(basePath + "modeler/modeler.js");
                        File css = new File(basePath + "modeler/modelerFrame.css");

                        copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/toolkit.js", basePath + "/modeler/toolkit.js");
                        copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/modeler.js", basePath + "/modeler/modeler.js");
                        copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/images/modelerFrame.css", basePath + "/modeler/modelerFrame.css");


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
                                + "<script type=\"text/javascript\" src=\"modeler/toolkit.js\"></script>\n"
                                + "<script type=\"text/javascript\" src=\"modeler/modeler.js\"></script>\n"
                                + "<link href=\"documentation/style.css\" rel=\"stylesheet\">\n"
                                + "<link href=\"modeler/modelerFrame.css\" rel=\"stylesheet\">"
                                + "<script type=\'text/javascript\'> //Activate tooltips\n"
                                + "    $(document).ready(function() {\n"
                                + "        if ($(\"[data-toggle=tooltip]\").length) {\n"
                                + "            $(\"[data-toggle=tooltip]\").tooltip();\n"
                                + "        }\n"
                                + "        $('body').off('.data-api');"
                                + "    });\n"
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
                                + "<li><h2><i class=\"icon-gears\" style=\"width: auto;\"></i> " + pe.getTitle() + "</h2></li>\n"
                                //                                + "        <li class=\"active\">"
                                //                                + "<a href=\"#\"><i class=\"icon-gears\"></i><span>" + pe.getTitle() + "</span></a>\n"
                                //                                + "</li>\n"
                                + "</ul>\n"
                                + "</div>\n"
                                + "</nav>\n"; //End header
                        html += "<div class=\"swbp-user-menu\">\n";
                        html += "<ul class=\"breadcrumb\">"; //Begin ruta
                        html += "<li class=\"active\">" + process.getTitle() + "</li>\n"; //Ruta
                        html += "</ul>"; //End ruta
                        html += "</div>";
                        String ref = "";
                        html += "<div class=\"col-lg-2 col-md-2 col-sm-4 hidden-xs\">\n";//Begin menu
                        //html += "<a href=\"#ruta\" style=\"width: 100%;\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + paramRequest.getLocaleString("home") + "\" class=\"btn btn-success btn-sm swbp-btn-start\"><i class=\"icon-home\"></i>" + paramRequest.getLocaleString("home") + "</a>";//Ruta
                        html += "<div class=\"swbp-left-menu swbp-left-menu-doc\">";//Begin body menu
                        html += "<ul class=\"nav nav-pills nav-stacked\">";
                        if (lane.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#lanemenu\">" + laneT + "<span class=\"badge pull-right\">" + lane.size() + "</span></a></li>\n";
                            iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\" data-placement=\"bottom\">" + ge.getTitle() + "</a></li>\n";
                            }
                        }
                        if (activity.size() > 0) {
                            html += "<li class=\"active\"> <a href=\"#activitymenu\">" + activityT + "<span class=\"badge pull-right\">" + activity.size() + "</span></a></li>\n";
                            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>\n";
                            }
                        }
                        if (gateway.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#gatewaymenu\">" + gatewayT + "<span class=\"badge pull-right\">" + gateway.size() + "</span></a></li>\n";
                            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>\n";
                            }
                        }
                        if (event.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#eventmenu\">" + eventT + "<span class=\"badge pull-right\">" + event.size() + "</span></a></li>\n";
                            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>\n";
                            }
                        }
                        if (dataob.size() > 0) {
                            html += "<li class=\"active\"><a href=\"#dataobmenu\">" + dataT + "<span class=\"badge pull-right\">" + dataob.size() + "</span></a></li>\n";
                            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                                if (ge instanceof SubProcess) {
                                    ref = ((SubProcess) ge).getTitle() + ".html";
                                }
                                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>\n";
                            }
                        }
                        html += "</ul>\n";
                        html += "</div>\n";//End body menu
                        html += "</div>\n";//End menu
                        html += "<div class=\"col-lg-10 col-md-10 col-sm-8\" role=\"main\">\n";//Begin content
                        html += "<div class=\"contenido\">\n"; //Begin body content
                        /**
                         * BEGIN IMAGE MODEL
                         */
                        String data = process.getData() != null ? process.getData() : paramRequest.getLocaleString("noImage");
//                        html += "<div id=\"ruta\">" + data + "</div>"; 
                        html += "\n<div id=\"ruta\">\n";
                        html += "<div class=\"panel panel-default visible-lg\">\n"
                                + "                    <div class=\"panel-body\">";
                        html += getStyleModel();
                        html += "</div>\n";
                        html += "</div>\n";
                        html += "</div>\n";

                        /**
                         * END IMAGE MODEL
                         */
                        html += "<div class=\"panel panel-default\">\n"//Documentation Process
                                + "   <div class=\"panel-heading\">\n"
                                + "        <div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("docFromPro") + " " + pe.getTitle() + "</strong></div>\n"
                                + "<a href=\"Model_" + pe.getTitle() + ".html\" target=\"_blank\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + paramRequest.getLocaleString("viewModel") + "\" class=\"pull-right icon-fullscreen hidden-lg\"></a>"
                                + "   </div>\n"
                                + "   <div class=\"panel-body\">\n"
                                + pe.getDocumentation().getText()
                                + "   </div>\n"
                                + "</div>";
                        if (lane.size() > 0) {//Lane
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"lanemenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + laneT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>";
                            html += "</div>";
                        }
                        if (activity.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"activitymenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + activityT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        if (gateway.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"gatewaymenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + gatewayT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
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
                            html += "<div id=\"eventmenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + eventT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        if (dataob.size() > 0) {
                            html += "<div class=\"panel panel-default\">";
                            html += "<div id=\"dataobmenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + dataT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>";
                            html += "<div class=\"panel-body\">";
                            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>";
                                html += ge.getDocumentation().getText();
                            }
                            html += "</div>\n";
                            html += "</div>\n";
                        }
                        html += "</div>\n";//End body content
                        html += "</div>\n";//End content
                        html += "</div>\n";//End wrapper

                        html += "<script type=\"text/javascript\">\n";
                        html += "Modeler.init('modeler', {mode: 'view', layerNavigation: false}, callbackHandler);\n"
                                + "    var zoomFactor = 1.1;\n"
                                + "    var panRate = 10;\n";
                        html += "function callbackHandler() {\n";
                        html += "var json;\n";
                        html += "json = '" + data + "';\n";
                        html += "Modeler.loadProcess(json);\n";
                        html += "}\n";
                        html += "Modeler._svgSize = getDiagramSize();\n"
                                + "        fitToScreen();\n";
                        html += "\n"
                                + "\n"
                                + "    function zoomin() {\n"
                                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                                + "        var viewBoxValues = viewBox.split(' ');\n"
                                + "\n"
                                + "        viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                                + "        viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                                + "\n"
                                + "        viewBoxValues[2] /= zoomFactor;\n"
                                + "        viewBoxValues[3] /= zoomFactor;\n"
                                + "\n"
                                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                                + "    }\n"
                                + "\n"
                                + "    function zoomout() {\n"
                                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                                + "        var viewBoxValues = viewBox.split(' ');\n"
                                + "\n"
                                + "        viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                                + "        viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                                + "\n"
                                + "        viewBoxValues[2] *= zoomFactor;\n"
                                + "        viewBoxValues[3] *= zoomFactor;\n"
                                + "\n"
                                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                                + "    }\n"
                                + "\n"
                                + "    function resetZoom() {\n"
                                + "        var el = document.getElementById(\"modeler\");\n"
                                + "        el.setAttribute('viewBox', '0 0 ' + $(\"#modeler\").parent().width() + ' ' + $(\"#modeler\").parent().height());\n"
                                + "        el.setAttribute('width', '1024');\n"
                                + "        el.setAttribute('height', '768');\n"
                                + "    }\n"
                                + "\n"
                                + "    function handlePanning(code) {\n"
                                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                                + "        var viewBoxValues = viewBox.split(' ');\n"
                                + "        viewBoxValues[0] = parseFloat(viewBoxValues[0]);\n"
                                + "        viewBoxValues[1] = parseFloat(viewBoxValues[1]);\n"
                                + "\n"
                                + "        switch (code) {\n"
                                + "            case 'left':\n"
                                + "                viewBoxValues[0] += panRate;\n"
                                + "                break;\n"
                                + "            case 'right':\n"
                                + "                viewBoxValues[0] -= panRate;\n"
                                + "                break;\n"
                                + "            case 'up':\n"
                                + "                viewBoxValues[1] += panRate;\n"
                                + "                break;\n"
                                + "            case 'down':\n"
                                + "                viewBoxValues[1] -= panRate;\n"
                                + "                break;\n"
                                + "        }\n"
                                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                                + "    }\n"
                                + "\n"
                                + "    function getDiagramSize() {\n"
                                + "        var cw = 0;\n"
                                + "        var ch = 0;\n"
                                + "        var fx = null;\n"
                                + "        var fy = null;\n"
                                + "        for (var i = 0; i < ToolKit.contents.length; i++) {\n"
                                + "            var obj = ToolKit.contents[i];\n"
                                + "            if (obj.typeOf && (obj.typeOf(\"GraphicalElement\") || obj.typeOf(\"Pool\"))) {\n"
                                + "                if (obj.layer === ToolKit.layer) {\n"
                                + "                    if (obj.getX() > cw) {\n"
                                + "                        cw = obj.getX();\n"
                                + "                        fx = obj;\n"
                                + "                    }\n"
                                + "\n"
                                + "                    if (obj.getY() > ch) {\n"
                                + "                        ch = obj.getY();\n"
                                + "                        fy = obj;\n"
                                + "                    }\n"
                                + "                }\n"
                                + "            }\n"
                                + "        }\n"
                                + "        cw = cw + fx.getBBox().width;\n"
                                + "        ch = ch + fy.getBBox().height;\n"
                                + "\n"
                                + "        var ret = {w: cw, h: ch};\n"
                                + "        return ret;\n"
                                + "    }\n"
                                + "\n"
                                + "    function fitToScreen() {\n"
                                + "        resetZoom();\n"
                                + "        var ws = $(\"#modeler\").parent().width();\n"
                                + "        var hs = $(\"#modeler\").parent().height();\n"
                                + "        var wi = Modeler._svgSize.w;\n"
                                + "        var hi = Modeler._svgSize.h;\n"
                                + "\n"
                                + "        if (wi > ws || hi > hs) {\n"
                                + "            var el = document.getElementById(\"modeler\");\n"
                                + "            el.setAttribute('viewBox', '0 0 ' + wi + ' ' + hi);\n"
                                + "            el.setAttribute('width', ws);\n"
                                + "            el.setAttribute('height', hs);\n"
                                + "        }\n"
                                + "    }";
                        html += "</script>\n";

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

    public static void copyFile(String sourceFile, String destFile) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File afile = new File(sourceFile);
            File bfile = new File(destFile);
            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);
            byte[] buffer = new byte[1024];
            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            log.error("Error to copy file " + sourceFile + ", " + e.getMessage());
        }
    }
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void createHtmlSubProcess(org.semanticwb.process.model.Process process, SubProcess subProcess, SWBParamRequest paramRequest, String basePath, String suri) throws FileNotFoundException, IOException, SWBResourceException {
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
                + "<script type=\"text/javascript\" src=\"modeler/toolkit.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"modeler/modeler.js\"></script>\n"
                + "<link href=\"documentation/style.css\" rel=\"stylesheet\">\n"
                + "<link href=\"modeler/modelerFrame.css\" rel=\"stylesheet\">"
                + "<script type=\'text/javascript\'> //Activate tooltips\n"
                + "    $(document).ready(function() {\n"
                + "        if ($(\"[data-toggle=tooltip]\").length) {\n"
                + "            $(\"[data-toggle=tooltip]\").tooltip();\n"
                + "        }\n"
                + "        $('body').off('.data-api');"
                + "    });\n"
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
//            System.out.println("urls[i]: " + urls[i]);
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
            html += "<li class=\"active\"> <a href=\"#activitymenu\">" + activityT + "<span class=\"badge pull-right\">" + activity.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (gateway.size() > 0) {
            html += "<li class=\"active\"><a href=\"#gatewaymenu\">" + gatewayT + "<span class=\"badge pull-right\">" + gateway.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (event.size() > 0) {
            html += "<li class=\"active\"><a href=\"#eventmenu\">" + eventT + "<span class=\"badge pull-right\">" + event.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>";
            }
        }
        if (dataob.size() > 0) {
            html += "<li class=\"active\"><a href=\"#dataobmenu\">" + dataT + "<span class=\"badge pull-right\">" + dataob.size() + "</span></a></li>";
            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                ref = "#" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId();
                if (ge instanceof SubProcess) {
                    ref = ((SubProcess) ge).getTitle() + ".html";
                }
                html += "<li><a href=\"" + ref + "\">" + ge.getTitle() + "</a></li>";
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
        String data = subProcess.getData() != null ? subProcess.getData() : paramRequest.getLocaleString("noImage");
        html += "<div id=\"ruta\">";
        html += getStyleModel();
        html += "</div>\n";
        /**
         * END IMAGE MODEL
         */
        html += "<div class=\"panel panel-default\">\n"//Documentation Process
                + "   <div class=\"panel-heading\">\n"
                + "        <div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("docFromSub") + " " + subProcess.getTitle() + "</strong></div>\n"
                + "<a href=\"Model_" + subProcess.getTitle() + ".html\" target=\"_blank\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"" + paramRequest.getLocaleString("viewModel") + "\" class=\"pull-right icon-fullscreen hidden-lg\"></a>"
                + "   </div>\n"
                + "   <div class=\"panel-body\">\n"
                + subProcess.getDocumentation().getText()
                + "   </div>\n"
                + "</div>";


        if (activity.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"activitymenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + activityT + "</strong></div></div>\n";
            html += "<div class=\"panel-body\">";
            iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        if (gateway.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"gatewaymenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + gatewayT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
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
            html += "<div id=\"eventmenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + eventT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        if (dataob.size() > 0) {
            html += "<div class=\"panel panel-default\">\n";
            html += "<div id=\"dataobmenu\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + dataT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>\n";
            html += "<div class=\"panel-body\">\n";
            iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
            while (iterator.hasNext()) {
                ge = iterator.next();
                html += "<h4 id=\"" + ge.getSemanticObject().getSemanticClass().getName() + "" + ge.getId() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>\n";
                html += ge.getDocumentation().getText();
            }
            html += "</div>\n";
            html += "</div>\n";
        }
        html += "</div>\n";//End body content
        html += "</div>";//End content
        html += "</div>\n";//End wrapper


        html += "<script type=\"text/javascript\">\n";
        html += "   Modeler.init('modeler', 'view', callbackHandler);\n";
        html += "   function callbackHandler() {\n";
        html += "       var json;\n";
        html += "       json = '" + data + "';\n";
        html += "       Modeler.loadProcess(json);\n";
        html += "       var obj = Modeler.getGraphElementByURI(null, \"" + suri + "\");\n"
                + "     ToolKit.setLayer(obj.subLayer);";
        html += "}\n";
        html += "</script>\n";

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
        request.setAttribute("despliege", request.getParameter("despliege"));
        response.setContentType("text/html; charset=UTF-8");
        rd.include(request, response);
    }

    public static void createModel(String suri, String basePath) throws FileNotFoundException, IOException {
//        System.out.println("entre createModel: " + suri);
        ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
        String html = "";
        html += "<script type=\"text/javascript\" src=\"bootstrap/bootstrap.min.js\"></script>\n"//Begin imports
                + "<link href=\"bootstrap/bootstrap.min.css\" rel=\"stylesheet\">\n"
                + "<link href=\"fontawesome/css/font-awesome.min.css\" rel=\"stylesheet\">\n"
                + "<link href=\"taskInbox/css/swbp.css\" rel=\"stylesheet\">\n"
                + "<script type=\"text/javascript\" src=\"jquery/jquery.min.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"modeler/toolkit.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"modeler/modeler.js\"></script>\n"
                + "<link href=\"documentation/style.css\" rel=\"stylesheet\">\n"
                + "<link href=\"modeler/modelerFrame.css\" rel=\"stylesheet\">"
                + "<script type=\'text/javascript\'> //Activate tooltips\n"
                + "    $(document).ready(function() {\n"
                + "        if ($(\"[data-toggle=tooltip]\").length) {\n"
                + "            $(\"[data-toggle=tooltip]\").tooltip();\n"
                + "        }\n"
                + "        $('body').off('.data-api');"
                + "    });\n"
                + "</script>\n"; //End imports
        html += "<div class=\"panel panel-default\">\n"
                + "    <div class=\"panel-heading\">\n"
                + "        <div class=\"panel-title text-center\">\n"
                + "            <li class=\"icon-cogs\"></li> " + pe.getTitle() + "\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"panel-body text-center\">\n"
                + "        <ul class=\"list-unstyled list-inline hidden-print visible-lg\">\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Zoom in\" onclick=\"zoomin();\n"
                + "                                return false;\"><i class=\"icon-zoom-in\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Zoom out\" onclick=\"zoomout();\n"
                + "                                return false;\"><i class=\"icon-zoom-out\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Reset zoom\" onclick=\"fitToScreen();\n"
                + "                                return false;\"><i class=\"icon-desktop\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan left\" onclick=\"handlePanning('left');\n"
                + "                                return false;\"><i class=\"icon-arrow-left\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan down\" onclick=\"handlePanning('down');\n"
                + "                                return false;\"><i class=\"icon-arrow-down\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan up\" onclick=\"handlePanning('up');\n"
                + "                                return false;\"><i class=\"icon-arrow-up\"></i></a>\n"
                + "            </li>\n"
                + "            <li>\n"
                + "                <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan right\" onclick=\"handlePanning('right');\n"
                + "                                return false;\"><i class=\"icon-arrow-right\"></i></a>\n"
                + "            </li>\n"
                + "        </ul>\n";
        html += getStyleModel();
        html += "       </div>\n"
                + "</div>";
        html += "<script type=\"text/javascript\">\n";
        html += "Modeler.init('modeler', {mode: 'view', layerNavigation: false}, callbackHandler);\n"
                + "    var zoomFactor = 1.1;\n"
                + "    var panRate = 10;\n";
        html += "function callbackHandler() {\n";
        html += "var json;\n";
        if (pe instanceof org.semanticwb.process.model.Process) {
            html += "json = '" + ((org.semanticwb.process.model.Process) pe).getData() + "';\n";
        }
        if (pe instanceof org.semanticwb.process.model.SubProcess) {
            SubProcess sp = (SubProcess) pe;
            org.semanticwb.process.model.Process process = sp.getProcess();
            html += "json = '" + process.getData() + "';\n";
        }
        html += "Modeler.loadProcess(json);\n";
        if (pe instanceof org.semanticwb.process.model.SubProcess) {
            html += "var obj = Modeler.getGraphElementByURI(null, \"" + suri + "\");\n"
                    + "ToolKit.setLayer(obj.subLayer);";
        }
        html += "}\n";
        html += "Modeler._svgSize = getDiagramSize();\n"
                + "        fitToScreen();\n";
        html += "\n"
                + "\n"
                + "    function zoomin() {\n"
                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "        var viewBoxValues = viewBox.split(' ');\n"
                + "\n"
                + "        viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                + "        viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                + "\n"
                + "        viewBoxValues[2] /= zoomFactor;\n"
                + "        viewBoxValues[3] /= zoomFactor;\n"
                + "\n"
                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "    }\n"
                + "\n"
                + "    function zoomout() {\n"
                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "        var viewBoxValues = viewBox.split(' ');\n"
                + "\n"
                + "        viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                + "        viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                + "\n"
                + "        viewBoxValues[2] *= zoomFactor;\n"
                + "        viewBoxValues[3] *= zoomFactor;\n"
                + "\n"
                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "    }\n"
                + "\n"
                + "    function resetZoom() {\n"
                + "        var el = document.getElementById(\"modeler\");\n"
                + "        el.setAttribute('viewBox', '0 0 ' + $(\"#modeler\").parent().width() + ' ' + $(\"#modeler\").parent().height());\n"
                + "        el.setAttribute('width', '1024');\n"
                + "        el.setAttribute('height', '768');\n"
                + "    }\n"
                + "\n"
                + "    function handlePanning(code) {\n"
                + "        var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "        var viewBoxValues = viewBox.split(' ');\n"
                + "        viewBoxValues[0] = parseFloat(viewBoxValues[0]);\n"
                + "        viewBoxValues[1] = parseFloat(viewBoxValues[1]);\n"
                + "\n"
                + "        switch (code) {\n"
                + "            case 'left':\n"
                + "                viewBoxValues[0] += panRate;\n"
                + "                break;\n"
                + "            case 'right':\n"
                + "                viewBoxValues[0] -= panRate;\n"
                + "                break;\n"
                + "            case 'up':\n"
                + "                viewBoxValues[1] += panRate;\n"
                + "                break;\n"
                + "            case 'down':\n"
                + "                viewBoxValues[1] -= panRate;\n"
                + "                break;\n"
                + "        }\n"
                + "        document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "    }\n"
                + "\n"
                + "    function getDiagramSize() {\n"
                + "        var cw = 0;\n"
                + "        var ch = 0;\n"
                + "        var fx = null;\n"
                + "        var fy = null;\n"
                + "        for (var i = 0; i < ToolKit.contents.length; i++) {\n"
                + "            var obj = ToolKit.contents[i];\n"
                + "            if (obj.typeOf && (obj.typeOf(\"GraphicalElement\") || obj.typeOf(\"Pool\"))) {\n"
                + "                if (obj.layer === ToolKit.layer) {\n"
                + "                    if (obj.getX() > cw) {\n"
                + "                        cw = obj.getX();\n"
                + "                        fx = obj;\n"
                + "                    }\n"
                + "\n"
                + "                    if (obj.getY() > ch) {\n"
                + "                        ch = obj.getY();\n"
                + "                        fy = obj;\n"
                + "                    }\n"
                + "                }\n"
                + "            }\n"
                + "        }\n"
                + "        cw = cw + fx.getBBox().width;\n"
                + "        ch = ch + fy.getBBox().height;\n"
                + "\n"
                + "        var ret = {w: cw, h: ch};\n"
                + "        return ret;\n"
                + "    }\n"
                + "\n"
                + "    function fitToScreen() {\n"
                + "        resetZoom();\n"
                + "        var ws = $(\"#modeler\").parent().width();\n"
                + "        var hs = $(\"#modeler\").parent().height();\n"
                + "        var wi = Modeler._svgSize.w;\n"
                + "        var hi = Modeler._svgSize.h;\n"
                + "\n"
                + "        if (wi > ws || hi > hs) {\n"
                + "            var el = document.getElementById(\"modeler\");\n"
                + "            el.setAttribute('viewBox', '0 0 ' + wi + ' ' + hi);\n"
                + "            el.setAttribute('width', ws);\n"
                + "            el.setAttribute('height', hs);\n"
                + "        }\n"
                + "    }";
        html += "</script>\n";
        File index = new File(basePath + "Model_" + pe.getTitle() + ".html");
        FileOutputStream out = new FileOutputStream(index);
        out.write(html.getBytes());
        out.flush();
        out.close();
    }

    public static String getStyleModel() {
        String style = "<svg id=\"modeler\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"100\" height=\"100\" viewBox=\"0 0 1200 800\" class=\"modeler\">\n"
                + "                <style type=\"text/css\"><![CDATA[\n"
                + "                    /*.resizeBox {\n"
                + "                        stroke:#008000;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;  \n"
                + "                    }*/\n"
                + "                    .task {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .task_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .callactivity {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:4;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .callactivity_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:4;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .eventsubTask {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                        stroke-dasharray:6,4;\n"
                + "                    }\n"
                + "\n"
                + "                    .eventsubTask_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                        stroke-dasharray:6,4;\n"
                + "                    }\n"
                + "                    .startEvent\n"
                + "                    {\n"
                + "                        stroke:#008000;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .startEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateInterruptingEvent\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        stroke-dasharray: 3,3;\n"
                + "                        /*stroke-dashoffset: 5;*/\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateInterruptingEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        stroke-dasharray: 3,3;\n"
                + "                        /*stroke-dashoffset: 5;*/\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .endEvent\n"
                + "                    {\n"
                + "                        stroke:#550000;\n"
                + "                        fill:url(#linearGradientEndEvent);\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .endEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientEndEvent);\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }    \n"
                + "\n"
                + "                    .gateway\n"
                + "                    {\n"
                + "                        stroke:#d4aa00;\n"
                + "                        fill:url(#linearGradientGateway);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .gateway_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientGateway);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowSubLine {\n"
                + "                        fill:none;\n"
                + "                        stroke:#ffffff;\n"
                + "                        stroke-opacity:0.1;\n"
                + "                        stroke-width:8;\n"
                + "                        cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowSubLine_o {\n"
                + "                        fill:none;\n"
                + "                        stroke:#2cff20;\n"
                + "                        stroke-width:8;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .swimlane\n"
                + "                    {\n"
                + "                        fill: #E8E8FF;\n"
                + "                        stroke-width:2;\n"
                + "                        stroke: #ADADAE;\n"
                + "                        fill-opacity:1;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .swimlane_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill: #E8E8FF;\n"
                + "                        stroke-width:2;\n"
                + "                        fill-opacity:1;\n"
                + "                        cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowLine {\n"
                + "                        fill: none;\n"
                + "                        stroke-width: 2;\n"
                + "                        stroke: #000000;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowLine_o {\n"
                + "                        fill: none;\n"
                + "                        stroke-width: 2;\n"
                + "                        stroke: #2cff20;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent1\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .itemAware {\n"
                + "                        fill:url(#linearGradientDataObject);\n"
                + "                        stroke:#666666;\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .itemAware_o {\n"
                + "                        fill:url(#linearGradientDataObject);\n"
                + "                        stroke:#2cff20;\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .transactionSquare {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .group {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifact {\n"
                + "                        fill:none;\n"
                + "                        stroke:#000000;\n"
                + "                        stroke-width:1.5px;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifactRect {\n"
                + "                        fill:none;\n"
                + "                        stroke:none;\n"
                + "                        fill:#E6E6E6;\n"
                + "                        fill-opacity:0.2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifactRect_o {\n"
                + "                        fill:none;\n"
                + "                        fill:#E6E6E6;\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill-opacity:0.2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .group_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .taskMarker\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .pathMarker{\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .startMarker{\n"
                + "                        stroke:#008000;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:2.5;                        \n"
                + "                    }\n"
                + "\n"
                + "                    .startMarker{\n"
                + "                        stroke:#008000;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:2.5;                        \n"
                + "                    }\n"
                + "\n"
                + "                    .startFilledMarker{\n"
                + "                        fill:#008000;\n"
                + "                        stroke:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateMarker{\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:#ffffff;\n"
                + "                        fill-opacity:0.1;\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateFilledMarker {\n"
                + "                        fill:#2c5aa0;\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        stroke-width:1;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .endFilledMarked\n"
                + "                    {\n"
                + "                        fill:#550000;   \n"
                + "                        stroke:#550000;\n"
                + "                        stroke-width:1;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .navPath {\n"
                + "                        fill:#e7e7e7;\n"
                + "                        stroke:gray;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .navPathHidden {\n"
                + "                        fill:#e7e7e7;\n"
                + "                        stroke:gray;\n"
                + "                        stroke-width:1;\n"
                + "                        display:none;\n"
                + "                    }\n"
                + "                    ]]></style>\n"
                + "                <defs id=\"globalDef\">\n"
                + "                <!--Definición de gradientes para las figuras-->\n"
                + "                <linearGradient id=\"linearGradientStartEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"90%\" style=\"stop-color:#ccffaa;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientEndEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"90%\" style=\"stop-color:#e7c1c1;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientTask\" x1=\"100%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#c1d3e1;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientIntermediateEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#87aade;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientGateway\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#FFFDE2;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#FFFAA6;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientDataObject\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#afbac7;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <circle id=\"resizeBox\" cx=\"0\" cy=\"0\" r=\"5\" class=\"resizeBox\"/>\n"
                + "                <!--definición de marcadores para las figuras-->\n"
                + "                <path id=\"errorMarker\" d=\"m 0.5,1051.8622 17.0774,-39.6847 15.0444,21.9792 19.5171,-27.474 L 34.8582,1048.199 19.8137,1029.6795 0.5,1051.8622 z\" transform=\"scale(0.35,0.35) translate(-26, -1030)\" />\n"
                + "                <g id=\"ruleMarker\">\n"
                + "                <path d=\"m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z\" transform=\"scale(0.32,0.32) translate(-23.5,-23.5)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskRuleMarker\" class=\"intermediateFilledMarker\">\n"
                + "                <path d=\"m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z\" transform=\"scale(0.32,0.32) translate(-23.5,-23.5)\"/>\n"
                + "                </g>\n"
                + "                <path id=\"multipleMarker\" d=\"m 0,0 -33.73973,0.37343 -10.78131,-31.973 27.07653,-20.13383 27.51552,19.52961 z\" transform=\"scale(0.35,0.35) translate(17,23)\" />\n"
                + "                <path id=\"parallelMarker\" d=\"m 0,0 0,19.4788 -19.45519,0 0,10.5627 19.45519,0 0,19.4787 10.58626,0 0,-19.4787 19.47871,0 0,-10.5627 -19.47871,0 0,-19.4788 z\" transform=\"scale(0.35,0.35) translate(-5, -25)\"/>\n"
                + "                <path id=\"linkMarker\" d=\"m 0,0 -20.432,-24.2007 0,16.9761 -25.0594,0 0,14.1289 25.0594,0 0,17.332 z\" transform=\"scale(0.35,0.35) translate(23,0)\"/>\n"
                + "                <path id=\"complexMarker\" d=\"m 0,0 0,15.875 -11.25,-11.2187 -2.34375,2.3125 11.25,11.25 -15.90625,0 0,3.3125 15.90625,0 -11.25,11.2187 2.34375,2.3438 11.25,-11.2188 0,15.875 3.28125,0 0,-15.9062 11.25,11.25 2.3125,-2.3438 -11.21875,-11.2187 15.875,0 0,-3.3125 -15.875,0 11.21875,-11.2188 -2.3125,-2.3437 -11.25,11.25 0,-15.9063 z\" transform=\"translate(-1, -20)\"/>\n"
                + "                <path id=\"signalMarker\" d=\"m 0,0 -23.59924,0 -23.59925,0 11.79962,-20.43755 11.79963,-20.43755 11.79962,20.43755 z\" transform=\"scale(0.35,0.35) translate(24,14)\"/>\n"
                + "                <path id=\"scalationMarker\" transform=\"scale(0.35,0.35) translate(0,-26)\" d=\"m 0,0 -21.34041,47.167 21.34041,-17.3811 21.340402,17.3811 -21.340402,-47.167 z\" />\n"
                + "                <path id=\"cancelMarker\" d=\"m 0,0 -18.3627,18.3627 -18.3848004,-18.3848 -3.3366,3.3367 18.3847004,18.3847 -18.3847004,18.3848 3.3366,3.3367 18.3848004,-18.3848 18.3627,18.3627 3.3366,-3.3367 -18.3626,-18.3627 18.3626,-18.3626 z\" transform=\"scale(0.35,0.35) translate(18, -22)\"/>\n"
                + "                <g id=\"manualMarker\" class=\"taskMarker\" transform=\"scale(0.65)\">\n"
                + "                <path d=\"m 0,0 c -0.27155,0 -0.5225,0.072 -0.75179,0.1792 -0.007,0 -0.0189,0 -0.0259,0 -1.25518,0.3798 -2.16186,4.1013 -2.9035,6.1425 -0.22877,0.5536 -0.36294,1.1549 -0.36294,1.7916 l 0,4.7861 c 0,2.6228 2.13932,4.7348 4.79597,4.7348 l 5.0552,0 5.69034,0 7.72539,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -3.11089,0 c 0.0118,-0.043 0.0283,-0.085 0.0389,-0.128 l 4.16081,0 c 0.97664,0 1.76285,-0.7762 1.76285,-1.7404 0,-0.9642 -2.73949,-1.7404 -1.76285,-1.7404 l -4.01823,0 0,-0.064 5.23667,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -5.39221,0 c -0.0116,-0.046 -0.0132,-0.095 -0.0259,-0.1408 l 7.56984,0 c 0.97663,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9642 -0.78621,-1.7404 -1.76284,-1.7404 l -12.18433,0 -0.92031,0 c 0.69854,-0.2311 1.20547,-0.8686 1.20547,-1.638 0,-0.9642 -0.7862,-1.7404 -1.76284,-1.7404 l -10.01967,0 z\" class=\"pathMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageThrowMarker\" transform=\"scale(1.3,1.3) translate(-7.5,-4.5)\" >\n"
                + "                <path d=\"m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                <path d=\"m 0.6,0 6.88839,4.318911 6.88838,-4.318911\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskMessageThrowMarker\" transform=\"scale(1.3,1.3) translate(-7.5,-4.5)\" class=\"intermediateFilledMarker\">\n"
                + "                <path d=\"m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                <path d=\"m 0.6,0 6.88839,4.318911 6.88838,-4.318911\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageCatchMarker\" transform=\"scale(0.35,0.35) translate(-27,-17)\" >\n"
                + "                <rect width=\"52.704407\" height=\"32.573116\" x=\"0\" y=\"0\"/>\n"
                + "                <path d=\"m 0,0 26.30906,16.49536 26.30905,-16.49536\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskMessageCatchMarker\" transform=\"scale(0.35,0.35) translate(-27,-17)\" class=\"intermediateMarker\">\n"
                + "                <rect width=\"52.704407\" height=\"32.573116\" x=\"0\" y=\"0\"/>\n"
                + "                <path d=\"m 0,0 26.30906,16.49536 26.30905,-16.49536\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensaMarker\" transform=\"scale(0.35,0.35) translate(-2,-13)\">\n"
                + "                <path d=\"m 0,0 -28.36636,0 14.18318,-24.56599 z\" transform=\"matrix(0,-1,1,0,0,0)\"/>\n"
                + "                <path d=\"m 0,0 -28.36636,0 14.18318,-24.56599 z\" transform=\"matrix(0,-1,1,0,23,0)\"/>\n"
                + "                </g>\n"
                + "                <filter id=\"dropshadow\" height=\"130%\">\n"
                + "                    <feGaussianBlur in=\"SourceAlpha\" stdDeviation=\"3\"/> \n"
                + "                    <feOffset dx=\"0\" dy=\"0\" result=\"offsetblur\"/>\n"
                + "                    <feComponentTransfer>\n"
                + "                        <feFuncA type=\"linear\" slope=\"0.5\"/>\n"
                + "                    </feComponentTransfer>\n"
                + "                    <feMerge> \n"
                + "                        <feMergeNode/>\n"
                + "                        <feMergeNode in=\"SourceGraphic\"/> \n"
                + "                    </feMerge>\n"
                + "                </filter>\n"
                + "                <g id=\"timerMarker\" transform=\"scale(0.35,0.35) translate(-396,-219)\" >\n"
                + "                <path d=\"m 232.18784,217.1541 a 40.089989,40.089989 0 1 1 -80.17997,0 40.089989,40.089989 0 1 1 80.17997,0 z\" transform=\"matrix(0.6346099,0,0,0.6346099,274.19065,81.345956)\"/>\n"
                + "                <path d=\"m 421.49964,219.1541 -8.76968,0\"/>\n"
                + "                <path d=\"m 396.40906,193.78618 -0.0964,8.76915\"/>\n"
                + "                <path d=\"m 370.76392,218.75252 8.76858,0.13882\"/>\n"
                + "                <path d=\"m 396.04976,244.52342 0.0278,-8.76963\"/>\n"
                + "                <path d=\"m 408.75323,219.1541 -12.86938,0\"/>\n"
                + "                <path d=\"m 401.39526,204.36536 -4.8964,14.70678\"/>\n"
                + "                <path d=\"m 414.26521,201.41346 -6.26889,6.13256\"/>\n"
                + "                <path d=\"m 413.87083,237.28912 -6.13256,-6.26889\"/>\n"
                + "                <path d=\"m 377.99517,236.89474 6.26889,-6.13256\"/>\n"
                + "                <path d=\"m 378.38955,201.01908 6.13256,6.26889\"/>\n"
                + "                </g>\n"
                + "                <g id=\"userMarker\" class=\"taskMarker\" transform=\"scale(0.8) translate(0,-1042)\">\n"
                + "                <path d=\"m 0,1039 c -3.3158023,0 -6,2.8058 -6,6.25 0,2.0045 0.9288788,3.7654 2.34375,4.9062 -3.3002786,1.7745 -5.6842907,5.8793 -6,10.75 l 19.3125,0 c -0.3158218,-4.8705 -2.7074728,-8.9755 -6,-10.75 C 5.0711212,1049.0154 6,1047.2545 6,1045.25 6,1041.8058 3.3158023,1039 0,1039 z\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -377.4453,473.79996 a 9.3826418,9.3826418 0 0 1 -12.52292,0.66553\" transform=\"matrix(0.6398828,0,0,0.66465394,245.76439,734.73647)\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -5.1190626,1060.732 0,-3.8413\" class=\"pathMarker\"/>\n"
                + "                <path d=\"m 5.1190626,1060.732 0,-3.8413\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0.06204402,1039.6046 c -3.10856302,0 -5.66539592,2.4352 -5.68305502,5.5312 0.8963285,-1.0836 1.7094089,-1.6888 3.0835319,-1.6888 2.07094865,0 4.4437605,1.0758 6.2188589,0.7688 0.570252,-0.099 1.2624582,-0.3804 1.939646,-0.7272 -0.6073258,-2.271 -3.0493799,-3.884 -5.55898178,-3.884 z\" style=\"fill:#2c5aa0;stroke-width:1;\" />\n"
                + "                </g>\n"
                + "                <g id=\"serviceMarker\" class=\"taskMarker\" transform=\"scale(0.8)\">\n"
                + "                <path d=\"m 0,0 1.478093,-2.5912 c -0.762317,-0.4662 -1.601697,-0.8229 -2.495483,-1.0366 l -0.767843,2.8793 -2.495483,0 -0.787044,-2.8793 c -0.895236,0.2142 -1.733065,0.5691 -2.495483,1.0366 l 1.487693,2.5721 -1.766033,1.7659 -2.581869,-1.478 c -0.467511,0.7623 -0.822527,1.6003 -1.036583,2.4952 l 2.86981,0.7678 0,2.5048 -2.860209,0.7775 c 0.21606,0.8875 0.562993,1.7267 1.026982,2.4856 l 2.562669,-1.478\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0,0 c -0.989621,0.2366 -1.915788,0.629 -2.758588,1.1457 l 1.644541,2.8433 -1.952235,1.9521 -2.85408,-1.6338 c -0.516803,0.8427 -0.909247,1.769 -1.145882,2.7583 l 3.172388,0.8488 0,2.769 -3.161774,0.8593 c 0.238849,0.9811 0.622356,1.9088 1.135268,2.7477 l 2.832859,-1.6337 1.952235,1.952 -1.633935,2.8539 c 0.84654,0.5219 1.774518,0.918 2.769203,1.1564 l 0.848804,-3.1828 2.758588,0 0.870025,3.1828 c 0.988288,-0.238 1.914849,-0.6289 2.758588,-1.1459 l -1.644542,-2.8431 1.952236,-1.9521 2.864694,1.6337 c 0.515612,-0.8389 0.908032,-1.7642 1.145874,-2.7477 l -3.182994,-0.8593 0,-2.769 3.182994,-0.87 c -0.237717,-0.9884 -0.628065,-1.9158 -1.145874,-2.7583 l -2.854088,1.655 -1.952235,-1.9521 1.633935,-2.8645 c -0.842691,-0.5152 -1.77056,-0.9096 -2.758588,-1.1457 l -0.848804,3.1828 -2.758588,0 z m 2.238708,5.697 c 1.994007,0 3.607392,1.6133 3.607392,3.6072 0,1.9938 -1.613385,3.6176 -3.607392,3.6176 -1.994007,0 -3.607392,-1.6238 -3.607392,-3.6176 0,-1.9939 1.613385,-3.6072 3.607392,-3.6072 z\" class=\"pathMarker\" />\n"
                + "                </g>\n"
                + "                <g id=\"scriptMarker\" class=\"taskMarker\"  transform=\"scale(0.7)\">\n"
                + "                <path d=\"m 0,0 c 3.5628793,2.98 3.5212524,7.9199 0,10.8403 l 0,0.1186 13.5952063,-10e-4 c 4.246065,-3.0502 4.698752,-8.6843 0.03211,-12.4597 -1.719778,-1.441 -2.097829,-3.2691 -2.297848,-5.5453 0,-1.9186 0.795633,-3.6321 2.05209,-4.7945 l -9.1791045,0 c -7.65536286,-0.3702 -8.7288909,8.3174 -4.2024826,11.8414 z\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0,-8 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -0.5,-4.1568 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 2.5,0 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 4.5,4 9.5315768,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 4,8 9.5315768,0\" class=\"pathMarker\" />\n"
                + "                </g>\n"
                + "                <g id=\"subProcessMarker\" class=\"taskMarker\">\n"
                + "                <rect x=\"-7\" y=\"-7\" width=\"14\" height=\"14\" style=\"fill:#ffffff;fill-opacity:0.1;\"/>\n"
                + "                <path d=\"M-5 0 L5 0 M0 -5 L0 5\"/>\n"
                + "                </g>\n"
                + "                <marker id=\"sequenceArrow\" viewBox=\"0 0 12 12\" refX=\"0\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"black\" stroke=\"none\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 0 0 L 10 5 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"messageArrow\" viewBox=\"0 0 12 12\" refX=\"7\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 0 0 L 10 5 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"conditionTail\" viewBox=\"-6 -6 12 12\" refX=\"3\" refY=\"0\" markerUnits=\"userSpaceOnUse\" markerWidth=\"12\" markerHeight=\"12\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <rect x=\"-3\" y=\"-3\" width=\"6\" height=\"6\" transform=\"rotate(-45)\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"defaultTail\" viewBox=\"0 0 12 12\" refX=\"-3\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 5 0 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"messageTail\" viewBox=\"-5 -5 12 12\" refX=\"3\" refY=\"0\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <circle r=\"3\" />\n"
                + "                </marker>\n"
                + "                <!--Definición de eventos iniciales-->\n"
                + "                <circle id=\"startEvent\" r=\"15\" bclass=\"startEvent\" oclass=\"startEvent_o\"/>\n"
                + "                <g id=\"messageStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"timerStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#timerMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" x=\"0\" y=\"0\" class=\"startFilledMarker\" transform=\"scale(1.1)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de eventos intermedios-->\n"
                + "                <g id=\"intermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <circle r=\"15\"/>\n"
                + "                <circle r=\"12\" class=\"intermediateEvent1\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"timerIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#timerMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"cancelIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"linkIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"linkIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de eventos finales-->\n"
                + "                <circle id=\"endEvent\" r=\"15\" bclass=\"endEvent\" oclass=\"endEvent_o\"/>\n"
                + "\n"
                + "                <g id=\"messageEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"cancelationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"terminationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"8\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de compuertas-->\n"
                + "                <rect id=\"gateway\" x=\"-17.5\" y=\"-17.5\" width=\"35\" height=\"35\" bclass=\"gateway\" oclass=\"gateway_o\" transform=\"rotate(45,0,0)\"/>\n"
                + "                <g id=\"exclusiveDataGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(1.3,1.3)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"inclusiveDataGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"exclusiveStartGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" style=\"fill:none;stroke:#d4aa00;stroke-width:4.5;\" transform=\"scale(0.9,0.9)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"eventGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <circle r=\"10\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" style=\"fill:none;stroke:#d4aa00;stroke-width:6;\" transform=\"scale(0.75,0.75)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(1.5,1.5)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelStartGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" style=\"stroke:#d4aa00;fill:none;stroke-width:3.5;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"complexGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#complexMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(0.7,0.7)\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de objetos de datos-->\n"
                + "                <g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <path transform=\"scale(0.7,0.7) translate(-3,-10)\" d=\"m -25,-25 0,74.61135 58.22841,0 0,-55.2509 -19.88426,-19.36045 -38.34415,0 z\"/>\n"
                + "                <path transform=\"scale(0.7,0.7) translate(-2,-10)\" d=\"m 12.5,-24 0,19.5625 20.0601,0\"/>\n"
                + "                </g>\n"
                + "                <g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">\n"
                + "                <path d=\"m 0,0 c -27.75868,0 -50.28125,5.6228 -50.28125,12.5625 0,0.1516 0.0412,0.2871 0.0625,0.4375 l -0.0625,0 0,61.5624 0,0.3125 0.0312,0 c 0.68314,6.7909 22.92187,12.25 50.25,12.25 27.3249498,0 49.53067,-5.4602 50.21875,-12.25 l 0.0312,0 0,-0.3125 0,-61.5624 -0.0312,0 c 0.0212,-0.1501 0.0312,-0.2862 0.0312,-0.4375 0,-6.9397 -22.4913202,-12.5625 -50.25,-12.5625 z\" transform=\"scale(0.6,0.6) translate(22,-24)\"/>\n"
                + "                <path d=\"m 0,0 c 0,6.9397 -22.5028602,12.5654 -50.26153,12.5654 -27.39179,0 -49.73975,-5.4833 -50.25272,-12.33\" transform=\"scale(0.6,0.6) translate(72,-12)\"/>\n"
                + "                </g>	\n"
                + "                <g id=\"dataInput\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <use xlink:href=\"#data\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"-12\" y=\"-17\" style=\"fill:none;stroke:#666666;stroke-width:2.5;\" transform=\"scale(0.8,0.8)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"dataOutput\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <use xlink:href=\"#data\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"-12\" y=\"-17\" style=\"fill:#666666;stroke:none;stroke-width:2.5;\" transform=\"scale(0.8,0.8)\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de tareas-->\n"
                + "                <g id=\"task\" styled=\"stroke:#2c5aa0\">\n"
                + "                <rect x=\"-50\" y=\"-30\" rx=\"10\" ry=\"10\" width=\"100\" height=\"60\" class=\"task\"/>\n"
                + "                </g>\n"
                + "                <g id=\"userTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#userMarker\" transform=\"scale(0.8,0.8) translate(-45, -30)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"serviceTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#serviceMarker\" transform=\"scale(0.8,0.8) translate(-43, -27)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scriptTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scriptMarker\" transform=\"scale(0.7,0.7) translate(-60, -24)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" transform=\"translate(-35, -17)\" style=\"fill:#2c5aa0;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"sendTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" transform=\"translate(-35, -19)\" style=\"fill:#2c5aa0;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"receiveTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" transform=\"translate(-35, -19)\" style=\"fill:none;stroke:#2c5aa0;stroke-width:3;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"manualTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#manualMarker\" transform=\"scale(0.7,0.7) translate(-60, -35)\" style=\"fill:none;stroke:#2c5aa0;stroke-width:4;\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de swimlanes-->\n"
                + "                <g id=\"pool\" bclass=\"swimlane\" oclass=\"swimlane_o\">\n"
                + "                <rect width=\"600\" x=\"-300\" y=\"-100\" height=\"200\" style=\"fill:#E8E8FF;stroke-width:2\"/>\n"
                + "                <path d=\"m -280,-100 l 0,200\" style=\"fill:none;stroke-width:2;\"/>\n"
                + "                </g>\n"
                + "                </defs>\n"
                + "                </svg>";
        return style;
    }
}

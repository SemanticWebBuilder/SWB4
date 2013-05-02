package org.semanticwb.process.resources.reports;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.ProcessInstance;

//itext libraries to write PDF file
public class ResourceReports extends org.semanticwb.process.resources.reports.base.ResourceReportsBase {

    private static org.semanticwb.Logger log = SWBUtils.getLogger(ResourceReports.class);
    SWBParamRequest pRequest = null;

    public ResourceReports() {
    }

    /**
     * Constructs a ResourceReports with a SemanticObject
     *
     * @param base The SemanticObject with the properties for the
     * ResourceReports
     */
    public ResourceReports(org.semanticwb.platform.SemanticObject base) {
        super(base);

    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/process/reports/report.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("pageElements", getPageElements());
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error to load report.jsp " + ex.getMessage());
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, FileNotFoundException {
        try {
            if ("add".equals(paramRequest.getMode())) {
                doAdd(request, response, paramRequest);
            } else if ("addFilter".equals(paramRequest.getMode())) {
                doAddFilter(request, response, paramRequest);
            } else if (paramRequest.getMode().equals(SWBResourceURL.Mode_EDIT)) {
                doEdit(request, response, paramRequest);
            } else if ("viewReport".equals(paramRequest.getMode())) {
                try {
                    doViewReport(request, response, paramRequest);
                } catch (ParseException ex) {
                    log.error(ex);
                }
            } else if (paramRequest.getMode().equals("generate")) {
                try {
                    try {
                        doGenerateReport(request, response, paramRequest);
                    } catch (BadElementException ex) {
                        log.error(ex);
                    } catch (DocumentException ex) {
                        log.error(ex);
                    }
                } catch (ParseException ex) {
                    log.error(ex);
                }
            } else {
                super.processRequest(request, response, paramRequest);
            }
        } catch (ServletException ex) {
            log.error("Error al cargar jsp en processRequest" + ex.getMessage());
        }
    }

    public void doViewReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ParseException {
        String path = "/swbadmin/jsp/process/reports/viewReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("idReport", request.getParameter("idReport"));
        request.setAttribute("pi", getProcessInstances(request, paramRequest));
        pRequest = paramRequest;
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar viewReport.jsp " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ParseException, FileNotFoundException, BadElementException, DocumentException {
        String path = "/swbadmin/jsp/process/reports/viewReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);

        Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
        FileReport fr = FileReport.ClassMgr.createFileReport(paramRequest.getWebPage().getWebSite());
        String extension = request.getParameter("extension").toString();
        fr.setTitle(replaceCaracter(report.getTitle()) + " " + fr.getId() + "." + extension);
        fr.setFileNameReport(report);
        report.addFileReport(fr);
        fr.setExtension(extension);
        fr.setActive(true);
        //Exportar
        request.setAttribute("pi", getProcessInstances(request, paramRequest));
        String name = report.getTitle();
        String title = report.getTitle() + " " + fr.getId();
        exportReport(name, title, extension, report, getProcessInstances(request, paramRequest));
//        System.out.println("se creo reporte...");
//        PrintWriter out = response.getWriter();
//        out.println("Se creo reporte " + fr.getTitle());
        pRequest = paramRequest;
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar viewReport.jsp " + ex.getMessage());
        }
    }
    String[] array = null;
    String des = null;

    private ArrayList<ProcessInstance> getProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) throws ParseException {
        ArrayList<ProcessInstance> t_instances = new ArrayList<ProcessInstance>();
        Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
        String columns = "";
        Iterator<ColumnReport> columnReport = ColumnReport.ClassMgr.listColumnReportByReportName(obj);
        while (columnReport.hasNext()) {
            ColumnReport column = columnReport.next();
            if (!column.getDefaultValue().equals("") || !column.getDefaultValueMax().equals("")) {
                if (columns.length() > 0) {
                    columns += "|";
                }
                columns += column.getId();
            }
        }
        Iterator<ProcessInstance> pi = obj.getProcessName().listProcessInstances();
        int rows = 0;
        int pagingSize = obj.getPagingSize();
        int page = 1;
        String sortType = null;
        if (request.getParameter("sort") != null) {
            sortType = request.getParameter("sort");
        }
        if (request.getParameter("des") != null) {
            des = request.getParameter("des");
            request.setAttribute("des", des);
        }
        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) {
                page = 1;
            }
        }
        if (pagingSize < 5) {
            pagingSize = 5;
        }
        while (pi.hasNext()) {
            ProcessInstance processInstance = pi.next();
            rows++;
            t_instances.add(processInstance);
        }

        if (sortType != null) {
            array = sortType.split("\\|");
            request.setAttribute("sort", sortType);
        }
        if (sortType != null) {
            if (request.getParameter("dataType").equals("isDataTypeProperty")) {
                request.setAttribute("dataType", "isDataTypeProperty");
                Collections.sort(t_instances, processNameComparator);
            } else {
                request.setAttribute("dataType", "isObjectProperty");
                Collections.sort(t_instances, processObjectComparator);
            }
        }
        int maxPages = 1;
        if (rows >= pagingSize) {
            maxPages = (int) Math.ceil((double) rows / pagingSize);
        }
        if (page > maxPages) {
            page = maxPages;
        }
        int sIndex = (page - 1) * pagingSize;
        if (rows > pagingSize && sIndex > rows - 1) {
            sIndex = rows - pagingSize;
        }
        int eIndex = sIndex + pagingSize;
        if (eIndex >= rows) {
            eIndex = rows;
        }
        request.setAttribute("maxPages", maxPages);
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            int control = 0;
            ProcessInstance instance = t_instances.get(i);
            if (columns.length() > 0) {
                String[] columnsid = columns.split("\\|");
                for (int k = 0; k < columnsid.length; k++) {
                    if (!filterInstance(instance, columnsid[k], paramRequest)) {
                        control++;
                    }
                }
                if (control == columnsid.length) {
                    instances.add(instance);
                }
            } else {
                instances.add(instance);
            }
        }
        return instances;
    }

    private boolean filterInstance(ProcessInstance pInstance, String idColumn, SWBParamRequest paramRequest) throws ParseException {
        ColumnReport column = ColumnReport.ClassMgr.getColumnReport(idColumn, paramRequest.getWebPage().getWebSite());
        boolean filter = false;
        SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(column.getNameProperty().substring(column.getNameProperty().indexOf("|") + 1));
        if (spt.isNumeric()) {//Integer
            try {
                if (!column.getDefaultValue().equals("") && !column.getDefaultValueMax().equals("")) {
                    if ((pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt) >= Integer.parseInt(column.getDefaultValue())
                            && pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt) <= Integer.parseInt(column.getDefaultValueMax()))
                            || (pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt) >= Integer.parseInt(column.getDefaultValueMax())
                            && pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt) <= Integer.parseInt(column.getDefaultValue()))) {
                        filter = true;
                    }
                } else if (!column.getDefaultValue().equals("") && column.getDefaultValueMax().equals("")) {
                    if (Integer.parseInt(column.getDefaultValue()) == pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt)) {
                        filter = true;
                    }
                } else if (!column.getDefaultValueMax().equals("") && column.getDefaultValue().equals("")) {
                    if (Integer.parseInt(column.getDefaultValueMax()) == pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getIntProperty(spt)) {
                        filter = true;
                    }
                }
            } catch (NumberFormatException ex) {
                column.setDefaultValue("");
            }
        } else if (spt.isDataTypeProperty()) {//String
            if (column.getDefaultValue().equals(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt))) {
                filter = true;
            }
        } else if (spt.isBoolean()) {//Boolean
            if (Boolean.parseBoolean(column.getDefaultValue()) == pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getBooleanProperty(spt)) {
                filter = true;
            }
        } else if (spt.isDate()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!column.getDefaultValue().equals("") && !column.getDefaultValueMax().equals("")) {
                int start = dateFormat.format(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getDateProperty(spt)).compareTo(dateFormat.format(dateFormat.parse(column.getDefaultValue())));
                int end = dateFormat.format(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getDateProperty(spt)).compareTo(dateFormat.format(dateFormat.parse(column.getDefaultValueMax())));
                if ((start >= 0 && end <= 0) || (start <= 0 && end >= 0)) {
                    filter = true;
                }
            } else if (!column.getDefaultValue().equals("") && column.getDefaultValueMax().equals("")) {
                if (dateFormat.format(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getDateProperty(spt)).equals(dateFormat.format(dateFormat.parse(column.getDefaultValue())))) {
                    filter = true;
                }
            } else if (!column.getDefaultValueMax().equals("") && column.getDefaultValue().equals("")) {
                if (dateFormat.format(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getDateProperty(spt)).equals(dateFormat.format(dateFormat.parse(column.getDefaultValueMax())))) {
                    filter = true;
                }
            }
        } else if (spt.isDateTime()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (spt.isObjectProperty()) {
            if (pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                if (column.getDefaultValue().equals(pInstance.getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt).getDisplayName())) {
                    filter = true;
                }
            }
        }
        return !(filter);
    }
    private Comparator processObjectComparator = new Comparator() {
        String lang = "es";

        public void Comparator(String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
            int it = 0;
            int it1 = 0;
            if (((ProcessInstance) t).getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                it = Integer.parseInt(((ProcessInstance) t).getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt).getId());
            }
            if (((ProcessInstance) t1).getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                it1 = Integer.parseInt(((ProcessInstance) t1).getItemAwareReference().getProcessObject().getSemanticObject().getObjectProperty(spt).getId());
            }
            int ret = 0;
            if (des.equals("des")) {
                if (it > it1) {
                    ret = 1;
                }
            } else {
                if (it1 > it) {
                    ret = 1;
                }
            }
            if (des.equals("des")) {
                if (it < it1) {
                    ret = -1;
                }
            } else {
                if (it1 < it) {
                    ret = -1;
                }
            }
            return ret;
        }
    };
    private Comparator processNameComparator = new Comparator() {
        String lang = "es";

        public void Comparator(String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
            String st = "";
            String st1 = "";
            if (((ProcessInstance) t).getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt) != null) {
                st = ((ProcessInstance) t).getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt).toLowerCase();
            }
            if (((ProcessInstance) t1).getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt) != null) {
                st1 = ((ProcessInstance) t1).getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt).toLowerCase();
            }
            if (des.equals("des")) {
                return st.compareTo(st1);
            } else {
                return st1.compareTo(st);
            }
        }
    };

    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ServletException {
        String path = "/swbadmin/jsp/process/reports/addReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar addReport.jsp " + ex.getMessage());
        }
    }

    public void doAddFilter(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ServletException {
        String path = "/swbadmin/jsp/process/reports/addFilter.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("idReport", request.getParameter("idReport"));
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar addReport.jsp " + ex.getMessage());
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/process/reports/editReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("idReport", request.getParameter("idReport"));
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar editReport.jsp " + ex.getMessage());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException, FileNotFoundException {
        String lang = null;
        if (response.getUser() != null) {
            lang = response.getUser().getLanguage();
            if (lang == null) {
                lang = "es";
            }
        }
        if (response.getAction().equals(SWBResourceURL.Action_ADD)) {
            SWBFormMgr reportMgr = new SWBFormMgr(Report.sclass, response.getWebPage().getWebSite().getSemanticObject(), null);
            reportMgr.clearProperties();
            Iterator<SemanticProperty> propReport = Report.sclass.listProperties();
            while (propReport.hasNext()) {
                SemanticProperty semProp = propReport.next();
                System.out.println("getName: " + semProp.getName());
                reportMgr.addProperty(semProp);
            }
            try {
                SemanticObject sem = reportMgr.processForm(request);
                Report report = (Report) sem.createGenericInstance();
                report.setTitle(replaceCaracter(report.getTitle()));
                response.setRenderParameter("idReport", report.getId());
                response.setMode(SWBResourceURL.Mode_EDIT);
            } catch (FormValidateException ex) {
                log.error("Error " + ex.getMessage());
            }
        } else if (response.getAction().equals(SWBResourceURL.Action_EDIT)) {
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
            SWBFormMgr reportMgr = new SWBFormMgr(report.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            try {
                reportMgr.addProperty(Report.rep_processName);
                reportMgr.processForm(request);
                report.setTitle(replaceCaracter(report.getTitle()));
                response.setRenderParameter("idReport", report.getId());
                response.setMode(SWBResourceURL.Mode_EDIT);
            } catch (FormValidateException ex) {
                log.error("Error " + ex.getMessage());
            }
        } else if (response.getAction().equals("addColumn")) {
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
            String[] items = request.getParameterValues("property");
            for (int i = 0; i < items.length; i++) {
                ColumnReport col = ColumnReport.ClassMgr.createColumnReport(response.getWebPage().getWebSite());
                Iterator<ColumnReport> columna = SWBComparator.sortSortableObject(report.listColumnReports());
                Integer indice = 0;
                while (columna.hasNext()) {
                    columna.next();
                    indice++;
                }
                if (indice != 0) {
                    col.setIndex((indice + 1));
                } else {
                    col.setIndex(1);
                }
                col.setReportName(report);
                col.setColumnVisible(true);
                col.setDefaultValue("");
                col.setDefaultValueMax("");
                report.addColumnReport(col);
                col.setNameProperty(items[i]);
            }
            response.setRenderParameter("idReport", report.getId());
            response.setMode(SWBResourceURL.Mode_EDIT);
        } else if (response.getAction().equals("updateColumn")) {
            Integer indice = 0;
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
            Iterator<ColumnReport> col = SWBComparator.sortSortableObject(report.listColumnReports());
            while (col.hasNext()) {
                ColumnReport cr = col.next();
                indice++;
                if (request.getParameter("delete" + cr.getURI()) != null) {
                    report.removeColumnReport(cr);
                    cr.remove();
                    indice--;
                } else {
                    cr.setIndex(indice);
                    cr.setTitleColumn(request.getParameter("title" + cr.getURI()));
                    if (request.getParameter("enabledOrder" + cr.getURI()) != null) {
                        cr.setEnabledOrder(true);
                    } else {
                        cr.setEnabledOrder(false);
                    }
                    if (request.getParameter("columnVisible" + cr.getURI()) != null) {
                        cr.setColumnVisible(true);
                    } else {
                        cr.setColumnVisible(false);
                    }
                    if (request.getParameter("defaultValue" + cr.getURI()) != null) {
                        if (!request.getParameter("defaultValue" + cr.getURI()).equals("")) {
                            cr.setDefaultValue(request.getParameter("defaultValue" + cr.getURI()));
                        } else {
                            cr.setDefaultValue("");
                        }
                    }
                    if (request.getParameter("defaultValueMax" + cr.getURI()) != null) {
                        if (request.getParameter("defaultValueMax" + cr.getURI()) != null) {
                            if (!request.getParameter("defaultValueMax" + cr.getURI()).equals("")) {
                                cr.setDefaultValueMax(request.getParameter("defaultValueMax" + cr.getURI()));
                            } else {
                                cr.setDefaultValueMax("");
                            }
                        } else {
                            cr.setDefaultValueMax("");
                        }
                    }
                }
                response.setRenderParameter("idReport", report.getId());
                response.setMode(SWBResourceURL.Mode_EDIT);
            }
        } else if (response.getAction().equals("moveUp")) {
            ColumnReport col = ColumnReport.ClassMgr.getColumnReport(request.getParameter("idColumn"), response.getWebPage().getWebSite());
            Integer index = col.getIndex();
            Iterator<ColumnReport> cor = SWBComparator.sortSortableObject(col.getReportName().listColumnReports());
            while (cor.hasNext()) {
                ColumnReport colRep = (ColumnReport) cor.next();
                if (colRep.getIndex() == index) {
                    colRep.setIndex(index - 1);
                }
                if (colRep.getIndex() == (index - 1) && !colRep.getId().equals(col.getId())) {
                    colRep.setIndex(index);
                }
            }
            response.setRenderParameter("idReport", col.getReportName().getId());
            response.setMode(SWBResourceURL.Mode_EDIT);
        } else if (response.getAction().equals("moveDown")) {
            ColumnReport col = ColumnReport.ClassMgr.getColumnReport(request.getParameter("idColumn"), response.getWebPage().getWebSite());
            Integer index = col.getIndex();
            Iterator<ColumnReport> cor = SWBComparator.sortSortableObject(col.getReportName().listColumnReports());
            while (cor.hasNext()) {
                ColumnReport colRep = (ColumnReport) cor.next();
                if (colRep.getIndex() == index) {
                    colRep.setIndex(index + 1);
                }
                if (colRep.getIndex() == (index + 1) && !colRep.getId().equals(col.getId())) {
                    colRep.setIndex(index);
                }
            }
            response.setRenderParameter("idReport", col.getReportName().getId());
            response.setMode(SWBResourceURL.Mode_EDIT);
        } else if (response.getAction().equals("saveReport")) {
//            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
//            FileReport fr = FileReport.ClassMgr.createFileReport(response.getWebPage().getWebSite());
//            System.out.println("the idReport pa: " + request.getParameter("idReport"));
//            System.out.println("the extension pa: " + request.getParameter("extension"));
//            String extension = request.getParameter("extension").toString();
//            fr.setTitle(replaceCaracter(report.getTitle()) + " " + fr.getId() + "." + extension);
//            fr.setFileNameReport(report);
//            report.addFileReport(fr);
//            fr.setExtension(extension);
//            fr.setActive(false);
//            //Exportar
//            String name = report.getTitle();
//            String title = report.getTitle() + " " + fr.getId();
//            CreateReportThread reportThread = new CreateReportThread();
//            try {
//                try {
//                    reportThread.addFileReport(fr, response, name, title, extension, report, getProcessInstances(request, pRequest));
//                } catch (ParseException ex) {
//                    log.error("" + ex);
//                }
//            } catch (BadElementException ex) {
//                log.error("" + ex);
//            } catch (DocumentException ex) {
//                log.error("" + ex);
//            }
//            reportThread.start();
//
//            response.setRenderParameter("idReport", fr.getFileNameReport().getId());
//            response.setMode(SWBResourceURL.Mode_VIEW);
        } else if (SWBResourceURL.Action_REMOVE.equals(response.getAction())) {
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
            removeReport(report);
            report.remove();
            response.setMode(SWBResourceURL.Mode_VIEW);
        } else if (response.getAction().equals("removeFileReport")) {
            FileReport fileReport = FileReport.ClassMgr.getFileReport(request.getParameter("idFileReport"), response.getWebPage().getWebSite());
            fileReport.remove();
            response.setMode(SWBResourceURL.Mode_VIEW);
        } else {
            response.setMode(SWBResourceURL.Mode_VIEW);
        }
    }

    public void exportReport(String name, String title, String extension, Report report, ArrayList<ProcessInstance> pInstances) throws FileNotFoundException, IOException, BadElementException, DocumentException {
        Iterator<ProcessInstance> pi = pInstances.iterator();

        String fileName = SWBPortal.getWorkPath() + report.getWorkPath() + "/" + name;
        fileName = fileName.replace("/", "\\");
        File file = new File(fileName);
        file.mkdirs();
        FileOutputStream fileOut = new FileOutputStream(file + "/" + title + "." + extension);
        int i = 0;
        if (extension.equals("xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Hoja1");
            //Columnas
            HSSFRow row = worksheet.createRow((short) 0);
            Iterator<ColumnReport> columns = SWBComparator.sortSortableObject(report.listColumnReports());
            while (columns.hasNext()) {
                ColumnReport colu = columns.next();
                if (colu.isColumnVisible()) {
                    HSSFCell cellA1 = row.createCell(i);
                    SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                    cellA1.setCellValue(colu.getTitleColumn() == null ? sp.getDisplayName() : colu.getTitleColumn());
                    i++;
                }
            }
            //Registros
            int j = 0;
            while (pi.hasNext()) {
                ProcessInstance pins = (ProcessInstance) pi.next();
                j++;
                HSSFRow rows = worksheet.createRow((short) j);
                int k = 0;
                Iterator<ColumnReport> column = SWBComparator.sortSortableObject(report.listColumnReports());
                while (column.hasNext()) {
                    HSSFCell cell = rows.createCell(k);
                    ColumnReport cr = column.next();
                    if (cr.isColumnVisible()) {
                        String[] array = cr.getNameProperty().split("\\|");
                        ItemAware ite = (ItemAware) SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(array[0]).createGenericInstance();
                        Iterator<ItemAwareReference> iar = pins.listAllItemAwareReferences();
                        int control = 0;
                        while (iar.hasNext()) {
                            ItemAwareReference iarr = iar.next();
                            if (iarr.getItemAware() != null && iarr.getProcessObject() != null) {
                                if (iarr.getItemAware().equals(ite)) {
                                    control++;
                                    SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
                                    if (!spt.isInverseOf() && spt.isDataTypeProperty()) {
                                        if (iarr.getProcessObject().getSemanticObject().getProperty(spt) != null) {
                                            cell.setCellValue(pins.getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt));
                                        } else {
                                            cell.setCellValue("--");
                                        }
                                    } else if (!spt.isInverseOf() && spt.isObjectProperty()) {
                                        if (iarr.getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                                            cell.setCellValue(iarr.getProcessObject().getSemanticObject().getObjectProperty(spt).getDisplayName());
                                        } else {
                                            cell.setCellValue("--");
                                        }
                                    }
                                }
                            }
                        }
                        if (control == 0) {
                            cell.setCellValue("--");
                        }
                        k++;
                    }
                }
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } else if (extension.equals("pdf")) {
            Document document = new Document();
            document.setMargins(1, 1, 1, 1);
            try {
                PdfWriter.getInstance(document, fileOut);
            } catch (DocumentException ex) {
                log.error("error to create " + fileOut + " -- " + ex.getMessage());
            }
            document.open();
            Image foto = Image.getInstance(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/images/bar.png");
            foto.setAlignment(Chunk.ALIGN_CENTER);
            foto.rectangle(230, 10);
            document.add(foto);
            Iterator<ColumnReport> columSize = SWBComparator.sortSortableObject(report.listColumnReports());
            while (columSize.hasNext()) {
                ColumnReport cr = columSize.next();
                if (cr.isColumnVisible()) {
                    i++;
                }
            }
            PdfPTable table = new PdfPTable(i);
            //Columns
            Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(report.listColumnReports());
            while (colum.hasNext()) {
                ColumnReport colu = colum.next();
                if (colu.isColumnVisible()) {
                    SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                    table.addCell(colu.getTitleColumn() == null ? sp.getDisplayName() : colu.getTitleColumn());
                }
            }
            //Registros
            while (pi.hasNext()) {
                ProcessInstance pins = (ProcessInstance) pi.next();
                Iterator<ColumnReport> column = SWBComparator.sortSortableObject(report.listColumnReports());
                while (column.hasNext()) {
                    ColumnReport cr = column.next();
                    if (cr.isColumnVisible()) {
                        String[] array = cr.getNameProperty().split("\\|");
                        ItemAware ite = (ItemAware) SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(array[0]).createGenericInstance();
                        Iterator<ItemAwareReference> iar = pins.listAllItemAwareReferences();
                        int control = 0;
                        while (iar.hasNext()) {
                            ItemAwareReference iarr = iar.next();
                            if (iarr.getItemAware() != null && iarr.getProcessObject() != null) {
                                if (iarr.getItemAware().equals(ite)) {
                                    control++;
                                    SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
                                    if (!spt.isInverseOf() && spt.isDataTypeProperty()) {
                                        if (iarr.getProcessObject().getSemanticObject().getProperty(spt) != null) {
                                            table.addCell(pins.getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt));
                                        } else {
                                            table.addCell("--");
                                        }
                                    } else if (!spt.isInverseOf() && spt.isObjectProperty()) {
                                        if (iarr.getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                                            table.addCell(iarr.getProcessObject().getSemanticObject().getObjectProperty(spt).getDisplayName());
                                        } else {
                                            table.addCell("--");
                                        }
                                    }
                                }

                            }
                        }
                        if (control == 0) {
                            table.addCell("--");
                        }
                    }
                }
            }
            try {
                document.add(table);
            } catch (DocumentException ex) {
                log.error("Error to add table  " + fileOut);
            }
            document.close();
        }
    }

    public static String replaceCaracter(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
}

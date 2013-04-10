package org.semanticwb.process.resources.reports;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ProcessInstance;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.semanticwb.SWBPortal;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
//itext libraries to write PDF file

public class ResourceReports extends org.semanticwb.process.resources.reports.base.ResourceReportsBase {

    private static org.semanticwb.Logger log = SWBUtils.getLogger(ResourceReports.class);
    //HashMap<String, ItemAware> map = null;

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
        String path = "/work/models/SWBAdmin/reports/report.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("pageElements", getPageElements());
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar report.jsp " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            if ("add".equals(paramRequest.getMode())) {
                doAdd(request, response, paramRequest);
            } else if ("addFilter".equals(paramRequest.getMode())) {
                doAddFilter(request, response, paramRequest);
            } else if (paramRequest.getMode().equals(SWBResourceURL.Mode_EDIT)) {
                doEdit(request, response, paramRequest);
            } else if ("viewReport".equals(paramRequest.getMode())) {
                doViewReport(request, response, paramRequest);
            } else {
                super.processRequest(request, response, paramRequest);
            }
        } catch (ServletException ex) {
            log.error("Error al cargar jsp en processRequest" + ex.getMessage());
        }
    }

    public void doViewReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/work/models/SWBAdmin/reports/viewReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("idReport", request.getParameter("idReport"));
        request.setAttribute("pi", getProcessInstances(request, paramRequest));
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar viewReport.jsp " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    String[] array = null;
    String des = null;

    private ArrayList<ProcessInstance> getProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<ProcessInstance> t_instances = new ArrayList<ProcessInstance>();
        Report obj = Report.ClassMgr.getReport(request.getParameter("idReport"), paramRequest.getWebPage().getWebSite());
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
            ProcessInstance instance = t_instances.get(i);
            instances.add(instance);
        }
        return instances;
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
        String path = "/work/models/SWBAdmin/reports/addReport.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al cargar addReport.jsp " + ex.getMessage());
        }
    }

    public void doAddFilter(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ServletException {
        String path = "/work/models/SWBAdmin/reports/addFilter.jsp";
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
        String path = "/work/models/SWBAdmin/reports/editReport.jsp";
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
            try {
                reportMgr.addProperty(Report.rep_processName);
                SemanticObject sem = reportMgr.processForm(request);
                Report report = (Report) sem.createGenericInstance();
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
                response.setRenderParameter("idReport", report.getId());
                response.setMode(SWBResourceURL.Mode_EDIT);
            } catch (FormValidateException ex) {
                log.error("Error " + ex.getMessage());
            }
        } else if (response.getAction().equals("addColumn")) {
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
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
            col.setEnabledOrder(true);
            report.addColumnReport(col);
            col.setNameProperty(request.getParameter("property"));
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
            Report report = Report.ClassMgr.getReport(request.getParameter("idReport"), response.getWebPage().getWebSite());
            FileReport fr = FileReport.ClassMgr.createFileReport(response.getWebPage().getWebSite());
            String extension = request.getParameter("extension").toString();
            fr.setTitle(report.getTitle() + " " + fr.getId() + "." + extension);
            fr.setFileNameReport(report);
            report.addFileReport(fr);
            //Exportar
            String name = report.getTitle();
            String title = report.getTitle() + " " + fr.getId();
            Iterator<ProcessInstance> pi = report.getProcessName().listProcessInstances();
            try {
                exportReport(response, name, title, extension, report, pi);
            } catch (DocumentException de) {
                log.error("Error al crear archivo " + de.getMessage());
            }
            response.setRenderParameter("idReport", fr.getFileNameReport().getId());
            response.setMode(SWBResourceURL.Mode_VIEW);
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

    public void exportReport(SWBActionResponse response, String name, String title, String extension, Report report, Iterator pi) throws FileNotFoundException, IOException, BadElementException, DocumentException {
        String lang = null;
        if (response.getUser() != null) {
            lang = response.getUser().getLanguage();
            if (lang == null) {
                lang = "es";
            }
        }
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
                HSSFCell cellA1 = row.createCell(i);
                SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                cellA1.setCellValue(colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn());
                i++;
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
                    String[] array = cr.getNameProperty().split("\\|");
                    ItemAware ite = (ItemAware) SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(array[0]).createGenericInstance();
                    Iterator<ItemAwareReference> iar = pins.listAllItemAwareReferences();
                    while (iar.hasNext()) {
                        ItemAwareReference iarr = iar.next();
                        if (iarr.getItemAware().equals(ite)) {
                            SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
                            if (!spt.isInverseOf() && spt.isDataTypeProperty()) {
                                if (iarr.getProcessObject().getSemanticObject().getProperty(spt) != null) {
                                    cell.setCellValue(pins.getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt));
                                } else {
                                    cell.setCellValue("--");
                                }
                            } else if (!spt.isInverseOf() && spt.isObjectProperty()) {
                                if (iarr.getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                                    cell.setCellValue(iarr.getProcessObject().getSemanticObject().getObjectProperty(spt).getDisplayName(lang));
                                } else {
                                    cell.setCellValue("--");
                                }
                            }
                        }
                    }
                    k++;
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
                log.error("error al crear " + fileOut + " -- " + ex.getMessage());
            }
            document.open();

            Image foto = Image.getInstance("D:\\Personal\\Process\\swbp\\web\\work\\config\\images\\cabecera-logo.jpg");
            foto.setAlignment(Chunk.ALIGN_CENTER);
            foto.rectangle(230, 20);
            document.add(foto);
            Iterator<ColumnReport> columSize = SWBComparator.sortSortableObject(report.listColumnReports());
            while (columSize.hasNext()) {
                columSize.next();
                i++;
            }
            PdfPTable table = new PdfPTable(i);
            //Columns
            Iterator<ColumnReport> colum = SWBComparator.sortSortableObject(report.listColumnReports());
            while (colum.hasNext()) {
                ColumnReport colu = colum.next();
                SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(colu.getNameProperty().substring(colu.getNameProperty().indexOf("|") + 1));
                table.addCell(colu.getTitleColumn() == null ? sp.getDisplayName(lang) : colu.getTitleColumn());
            }
            //Registros
            org.semanticwb.SWBUtils.Collections.sizeOf(pi);
            while (pi.hasNext()) {
                ProcessInstance pins = (ProcessInstance) pi.next();
                Iterator<ColumnReport> column = SWBComparator.sortSortableObject(report.listColumnReports());
                while (column.hasNext()) {
                    ColumnReport cr = column.next();
                    String[] array = cr.getNameProperty().split("\\|");
                    ItemAware ite = (ItemAware) SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(array[0]).createGenericInstance();
                    Iterator<ItemAwareReference> iar = pins.listAllItemAwareReferences();
                    while (iar.hasNext()) {
                        ItemAwareReference iarr = iar.next();
                        if (iarr.getItemAware().equals(ite)) {
                            SemanticProperty spt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(array[1]);
                            if (!spt.isInverseOf() && spt.isDataTypeProperty()) {
                                if (iarr.getProcessObject().getSemanticObject().getProperty(spt) != null) {
                                    table.addCell(pins.getItemAwareReference().getProcessObject().getSemanticObject().getProperty(spt));
                                } else {
                                    table.addCell("--");
                                }
                            } else if (!spt.isInverseOf() && spt.isObjectProperty()) {
                                if (iarr.getProcessObject().getSemanticObject().getObjectProperty(spt) != null) {
                                    table.addCell(iarr.getProcessObject().getSemanticObject().getObjectProperty(spt).getDisplayName(lang));
                                } else {
                                    table.addCell("--");
                                }
                            }
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
}

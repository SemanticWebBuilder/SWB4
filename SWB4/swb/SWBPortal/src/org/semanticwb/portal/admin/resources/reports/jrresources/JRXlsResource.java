
package org.semanticwb.portal.admin.resources.reports.jrresources;

import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class JRXlsResource extends JRResource {
    public JRXlsResource(String jasperResource, HashMap params, JRDataSource dataSource){
        super(jasperResource, params, dataSource);
    }
    
    public JRXlsResource(String jasperResource, JRDataSource dataSource){
        super(jasperResource, null, dataSource);
    }
    
    protected void formatReport(){
        exporter = new JRXlsExporter();
    }
    
    protected void parametrizeReport(){
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
        exporter.setParameter(JRXlsExporterParameter. IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.xls\"");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();
    }
}

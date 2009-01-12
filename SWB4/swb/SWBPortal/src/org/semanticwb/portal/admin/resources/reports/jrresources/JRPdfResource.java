
package org.semanticwb.portal.admin.resources.reports.jrresources;

import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class JRPdfResource extends JRResource{
    public JRPdfResource(String jasperResource, HashMap params, JRDataSource dataSource){
        super(jasperResource, params, dataSource);
    }
    
    public JRPdfResource(String jasperResource, JRDataSource dataSource){
        super(jasperResource, null, dataSource);
    }
    
    protected void formatReport(){
        exporter = new JRPdfExporter();
    }
    
    protected void parametrizeReport(){
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
        response.setContentType("application/pdf");
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();
    }
}

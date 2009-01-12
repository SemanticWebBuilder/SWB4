
package org.semanticwb.portal.admin.resources.reports.jrresources;

import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.SWBUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

public class JRHtmlResource extends JRResource {
    public JRHtmlResource(String jasperResource, HashMap params, JRDataSource dataSource){
        super(jasperResource, params, dataSource);
    }
    
    public JRHtmlResource(String jasperResource, JRDataSource dataSource){
        super(jasperResource, null, dataSource);
    }
        
    protected void formatReport(){
        exporter = new JRHtmlExporter();        
    }
    
    protected void parametrizeReport(){
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, SWBUtils.getApplicationPath()+"/swbadmin/resources/reports/templates/img/");
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
    }
        
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();
    }
}

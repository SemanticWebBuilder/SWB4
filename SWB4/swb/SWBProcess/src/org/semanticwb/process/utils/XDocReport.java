package org.semanticwb.process.utils;


import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hasdai
 */
public class XDocReport
{
    private static Logger log=SWBUtils.getLogger(XDocReport.class);

    String _name;
    String _templatePath;
    String _outPath;
    IXDocReport report = null;
    IContext context = null;

    public XDocReport(String name, String templatePath, String outPath) {
        _name = name;
        _templatePath = templatePath;
        _outPath = outPath;
        
        try {
            InputStream in = new FileInputStream(templatePath);
            report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
            context = report.createContext();
        } catch (IOException e) {
            log.error(e);
        } catch (XDocReportException e) {
            log.error(e);
        }
    }

    public void addContextObject(String name, Object o) {
        if (context != null) {
            context.put(name, o);
        }
    }

    public void addContextList(String oName, List<String> pNames, List cList) {
        FieldsMetadata metadata = new FieldsMetadata();
        Iterator<String> names_it = pNames.iterator();
        while (names_it.hasNext()) {
            String name = names_it.next();
            metadata.addFieldAsList(name);
        }

        if (report != null) {
            report.setFieldsMetadata(metadata);
            if (context != null) {
                context.put(oName, cList);
            }
        }
    }

    public void generateReport() {
        try {
            if (report != null) {
                OutputStream out = new FileOutputStream(new File(_outPath));
                report.process(context, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            log.error(e);
        } catch (XDocReportException e) {
            log.error(e);
        }
    }
}

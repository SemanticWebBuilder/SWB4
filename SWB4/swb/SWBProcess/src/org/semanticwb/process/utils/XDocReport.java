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
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.SWBPClassMgr;
import org.semanticwb.process.model.StoreArtifact;

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
    IXDocReport report = null;
    IContext context = null;

    public XDocReport(String name, String templatePath) {
        _name = name;
        _templatePath = templatePath;
        
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

    public void generateReport(String outPath) {
        try {
            if (report != null) {
                OutputStream out = new FileOutputStream(new File(outPath));
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

    public void generateReport(OutputStream out) {
        try {
            if (report != null) {
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

/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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

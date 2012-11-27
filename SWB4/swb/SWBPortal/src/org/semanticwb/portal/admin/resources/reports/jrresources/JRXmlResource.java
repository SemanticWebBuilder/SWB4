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
package org.semanticwb.portal.admin.resources.reports.jrresources;

import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXmlExporter;

// TODO: Auto-generated Javadoc
/**
 * The Class JRXmlResource.
 */
public class JRXmlResource extends JRResource {
    
    /**
     * Instantiates a new jR xml resource.
     * 
     * @param jasperResource the jasper resource
     * @param params the params
     * @param dataSource the data source
     */
    public JRXmlResource(String jasperResource, HashMap params, JRBeanCollectionDataSource dataSource){
        super(jasperResource, params, dataSource);
    }
    
    /**
     * Instantiates a new jR xml resource.
     * 
     * @param jasperResource the jasper resource
     * @param dataSource the data source
     */
    public JRXmlResource(String jasperResource, JRBeanCollectionDataSource dataSource){
        super(jasperResource, null, dataSource);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRResource#formatReport()
     */
    protected void formatReport(){
        exporter = new JRXmlExporter();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRResource#parametrizeReport()
     */
    protected void parametrizeReport(){
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRResource#exportReport(javax.servlet.http.HttpServletResponse)
     */
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();
    }
}

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

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class JRResource.
 * 
 * @author Carlos Ramos Inch�ustegui
 */
public abstract class JRResource {    
    
    /** The exporter. */
    protected JRExporter exporter;
    
    /** The virtualized. */
    public boolean virtualized;
    
    /** The jasper resource. */
    private String jasperResource;
    
    /** The params. */
    private HashMap params;
    
    /** The data source. */
    private JRBeanCollectionDataSource dataSource;
    
    /** The locale. */
    private Locale locale;    
    
    /** The jasper report. */
    protected JasperReport jasperReport;
    
    /** The jasper print. */
    protected JasperPrint jasperPrint;    
       
    /**
     * Instantiates a new jR resource.
     * 
     * @param jasperResource the jasper resource
     * @param params the params
     * @param dataSource the data source
     * @param locale the locale
     */
    public JRResource(String jasperResource, HashMap params, JRBeanCollectionDataSource dataSource, Locale locale){
        this.jasperResource = jasperResource;
        this.params = params!=null ? params : new HashMap();
        this.dataSource = dataSource;
        this.locale = locale;
                
        this.params.put(JRParameter.REPORT_LOCALE, this.locale);
        /*ResourceBundle labels = ResourceBundle.getBundle("dailyRepUserTypes_es_MX", this.locale_es_MX);
        this.params.put(JRParameter.REPORT_RESOURCE_BUNDLE, labels);*/

        setVirtualized(true);
    }
    
    /**
     * Instantiates a new jR resource.
     * 
     * @param jasperResource the jasper resource
     * @param params the params
     * @param dataSource the data source
     */
    public JRResource(String jasperResource, HashMap params, JRBeanCollectionDataSource dataSource){
        this.jasperResource = jasperResource;
        this.params = params!=null ? params : new HashMap();
        this.dataSource = dataSource;
        setVirtualized(true);
    }
    
    /**
     * Instantiates a new jR resource.
     * 
     * @param jasperResource the jasper resource
     * @param dataSource the data source
     */
    public JRResource(String jasperResource, JRBeanCollectionDataSource dataSource){
        this(jasperResource, null, dataSource, new Locale("es","MX"));
    }
    
    /**
     * Prepare report.
     * 
     * @return the jR exporter
     * @throws JRException the jR exception
     */
    public final JRExporter prepareReport() throws JRException {
        loadJasperResource();
        fillReport();
        formatReport();
        if(isVirtualized()){
            String tempVirtualizerPath = System.getProperty("user.home") + File.separator + "tmpvirt";
            File tempVirtualizerDir = new File(tempVirtualizerPath);
            if (!tempVirtualizerDir.exists() || !tempVirtualizerDir.isDirectory()) {
                try{
                    tempVirtualizerDir.mkdir();
                    JRFileVirtualizer virtualizer = new JRFileVirtualizer(3, tempVirtualizerPath);             
                    params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);                
                }catch(SecurityException se){
                    se.printStackTrace();
                }
            }
        }
        parametrizeReport();
        return exporter;
    }
    
    /**
     * Load jasper resource.
     * 
     * @throws JRException the jR exception
     */
    protected void loadJasperResource() throws JRException {
//        System.out.println("\ninicio... loadJasperResource 1");
//        System.out.println("jasperResource="+jasperResource+"-----");
        InputStream is = getClass().getResourceAsStream(jasperResource);
//        System.out.println("loadJasperResource 2");
        jasperReport = (JasperReport)JRLoader.loadObject(is);
//        System.out.println("loadJasperResource 3... fin");
    }
    
    /**
     * Fill report.
     * 
     * @throws JRException the jR exception
     */
    protected void fillReport()  throws JRException {
        jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
    } 
    
    /**
     * Format report.
     */
    protected abstract void formatReport();
    
    /**
     * Parametrize report.
     */
    protected abstract void parametrizeReport();

    /**
     * Checks if is virtualized.
     * 
     * @return true, if is virtualized
     */
    public boolean isVirtualized() {
        return virtualized;
    }

    /**
     * Sets the virtualized.
     * 
     * @param virtualized the new virtualized
     */
    public void setVirtualized(boolean virtualized) {
        this.virtualized = virtualized;
    }
    
    /**
     * Export report.
     * 
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JRException the jR exception
     */
    public abstract void exportReport(HttpServletResponse response) throws java.io.IOException, JRException;
    
}

/**  
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
**/ 
 

package org.semanticwb.portal.admin.resources.reports.jrresources;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Carlos Ramos Inch�ustegui
 */
public abstract class JRResource {    
    protected JRExporter exporter;
    public boolean virtualized;
    private String jasperResource;
    private HashMap params;
    private JRDataSource dataSource;
    private Locale locale;    
    
    protected JasperReport jasperReport;
    protected JasperPrint jasperPrint;    
       
    public JRResource(String jasperResource, HashMap params, JRDataSource dataSource, Locale locale){        
        this.jasperResource = jasperResource;
        this.params = params!=null ? params : new HashMap();
        this.dataSource = dataSource;
        this.locale = locale;
                
        this.params.put(JRParameter.REPORT_LOCALE, this.locale);
        /*ResourceBundle labels = ResourceBundle.getBundle("dailyRepUserTypes_es_MX", this.locale_es_MX);
        this.params.put(JRParameter.REPORT_RESOURCE_BUNDLE, labels);*/

        setVirtualized(true);
    }
    
    public JRResource(String jasperResource, HashMap params, JRDataSource dataSource){
        this.jasperResource = jasperResource;
        this.params = params!=null ? params : new HashMap();
        this.dataSource = dataSource;
        setVirtualized(true);
    }
    
    public JRResource(String jasperResource, JRDataSource dataSource){
        this(jasperResource, null, dataSource, new Locale("es","MX"));
    }
    
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
    
    protected void loadJasperResource() throws JRException {
        System.out.println("\ninicio... loadJasperResource 1");
        System.out.println("jasperResource="+jasperResource+"-----");
        InputStream is = getClass().getResourceAsStream(jasperResource);
        System.out.println("loadJasperResource 2");
        jasperReport = (JasperReport)JRLoader.loadObject(is);
        System.out.println("loadJasperResource 3... fin");
    }
    
    protected void fillReport()  throws JRException {
        jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
    } 
    
    protected abstract void formatReport();
    
    protected abstract void parametrizeReport();

    public boolean isVirtualized() {
        return virtualized;
    }

    public void setVirtualized(boolean virtualized) {
        this.virtualized = virtualized;
    }
    
    public abstract void exportReport(HttpServletResponse response) throws java.io.IOException, JRException;
    
}

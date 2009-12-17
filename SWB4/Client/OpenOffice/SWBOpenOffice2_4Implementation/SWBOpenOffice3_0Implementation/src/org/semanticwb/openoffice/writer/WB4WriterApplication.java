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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.writer;

import com.sun.star.beans.PropertyValue;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.semanticwb.openoffice.ErrorLog;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.WBOfficeException;
import org.semanticwb.openoffice.WBException;
import static org.semanticwb.openoffice.util.FileUtil.getPathURL;

/**
 * Class that Wrap an Office Application
 * @author victor.lorenzana * 
 */
public class WB4WriterApplication extends OfficeApplication
{

    private static final String DESKTOP_NOT_FOUND = "The desktop was not found";
    private static final String DESKTOP_PATH = "com.sun.star.frame.Desktop";
    private static final String DOCUMENT_CAN_NOT_BE_OPEN = "The document can not be open";
    private static final String TARGET_BLANK = "_blank";
    private static final String TEXTDOCUMENT_PATH = "com.sun.star.text.TextDocument";
    /**
     * Context to the Open Office Application
     */
    private final XComponentContext m_xContext;

    /**
     * Create a representation of a Writer Application as Office Application
     * @param m_xContext XComponentContext that represent a Writer Application
     * @see XComponentContext
     */
    public WB4WriterApplication(XComponentContext m_xContext)
    {
        this.m_xContext = m_xContext;
    }

    /**
     * Gets the Writer documents opened
     * @return List of OfficeDocument that represent the documents
     * @throws org.semanticwb.openoffice.WBException In case that the Desktop of Office can not be used
     */
    public List<OfficeDocument> getDocuments() throws WBException
    {
        ArrayList<OfficeDocument> documents = new ArrayList<OfficeDocument>();
        XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
        try
        {
            Object desktop = serviceManager.createInstanceWithContext(DESKTOP_PATH, m_xContext);
            XDesktop xdesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
            XEnumerationAccess access = xdesktop.getComponents();
            XEnumeration enumeration = access.createEnumeration();
            while (enumeration.hasMoreElements())
            {
                try
                {
                    Object nextElement = enumeration.nextElement();
                    XComponent document = (XComponent) UnoRuntime.queryInterface(XComponent.class, nextElement);
                    XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(
                            XServiceInfo.class, document);
                    if (xServiceInfo.supportsService(TEXTDOCUMENT_PATH))
                    {
                        WB4Writer writerDocument = new WB4Writer(document);
                        documents.add(writerDocument);
                    }
                }
                catch (NoSuchElementException nse)
                {
                    ErrorLog.log(nse);
                }
            }
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException(DESKTOP_NOT_FOUND, e);
        }
        return documents;
    }

    /**
     * Open a Document in a certain path
     * @param file A File path of a Open Office document to Open
     * @return OfficeDocument opened
     * @throws org.wb.WBException If the document can not be opened
     */
    public final OfficeDocument open(File file) throws WBException
    {
        // Obtener la factoria de servicios de OpenOffice   
        XMultiComponentFactory xMCF = m_xContext.getServiceManager();

        try
        {
            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext(DESKTOP_PATH, m_xContext);
            XDesktop oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = getPathURL(file);
            xCompLoader.loadComponentFromURL(url, TARGET_BLANK, 0, loadProps);            
            return new WB4Writer(m_xContext);
        }
        catch (com.sun.star.io.IOException e)
        {
            JOptionPane.showMessageDialog(null, "e");
            throw new WBOfficeException(DOCUMENT_CAN_NOT_BE_OPEN, e);
        }
        catch (com.sun.star.uno.Exception e)
        {
            JOptionPane.showMessageDialog(null, "e2");
            throw new WBOfficeException(DOCUMENT_CAN_NOT_BE_OPEN, e);
        }

    }
}

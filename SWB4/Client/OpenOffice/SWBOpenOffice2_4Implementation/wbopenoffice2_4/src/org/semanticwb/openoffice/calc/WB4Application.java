/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.calc;

import com.sun.star.beans.PropertyValue;
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
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBOfficeException;

/**
 * 
 * @author victor.lorenzana
 */
public class WB4Application extends OfficeApplication
{

    private final XComponentContext m_xContext;

    public WB4Application(XComponentContext m_xContext)
    {
        this.m_xContext = m_xContext;
    }

    public List<OfficeDocument> getDocuments() throws WBException
    {
        ArrayList<OfficeDocument> documents = new ArrayList<OfficeDocument>();
        XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
        try
        {
            Object desktop = serviceManager.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", m_xContext);
            XDesktop xdesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
            XEnumerationAccess access = xdesktop.getComponents();
            XEnumeration enumeration = access.createEnumeration();
            while (enumeration.hasMoreElements())
            {
                Object nextElement = enumeration.nextElement();
                XComponent document = (XComponent) UnoRuntime.queryInterface(XComponent.class, nextElement);
                XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(
                        XServiceInfo.class, document);
                if (xServiceInfo.supportsService("com.sun.star.sheet.SpreadsheetDocument"))
                {
                    WB4Calc calDocument = new WB4Calc(document);
                    documents.add(calDocument);
                }
            }
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException("Error al obtener el escritorio de Open Office", e);
        }
        return documents;
    }

    public final OfficeDocument open(File file) throws WBException
    {
        // Obtener la factoria de servicios de OpenOffice   
        XMultiComponentFactory xMCF = m_xContext.getServiceManager();

        try
        {
            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", m_xContext);
            XDesktop oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url = "file:///" + file.getPath().replace('\\', '/');
            xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);
            return new WB4Calc(m_xContext);
        }
        catch (com.sun.star.io.IOException e)
        {
            throw new WBOfficeException("No se puede abrir el documento", e);
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException("No se puede abrir el documento", e);
        }

    }
}

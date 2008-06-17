/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.calc;

import com.sun.star.beans.PropertyValue;
import com.sun.star.document.XDocumentInfo;
import com.sun.star.document.XDocumentInfoSupplier;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.*;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XModifiable;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.SaveDocumentFormat;
import org.semanticwb.openoffice.WBAlertException;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.WBOfficeException;

/**
 * Class to wrap a Open Office Calc Document
 * @author victor.lorenzana
 */
public class WB4Calc extends OfficeDocument
{

    public static final String OPENOFFICE_EXTENSION = ".ods";
    public static final String EXCEL_EXTENSION = ".xls";
    public static final String HTML_EXTENSION = ".html";
    private static final String ERROR_NO_SAVE = "No se puede almacenar el documento";
    private static final String FILTER_NAME = "FilterName";
    private static final String OVERRIDE_OPTION = "Overwrite";
    private static final String SCHEMA_FILE = "file:///";    
    private final XComponent document;

    public WB4Calc(XComponent document) throws WBOfficeException
    {        
        this.document = document;
    }

    public WB4Calc(XComponentContext m_xContext) throws WBOfficeException
    {        
        XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
        try
        {
            Object desktop = serviceManager.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", m_xContext);
            XDesktop xdesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
            document = xdesktop.getCurrentComponent();
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException("Error al obtener el escritorio de Open Office", e);
        }
    }

    @Override
    public final String getApplicationVersion()
    {
        return "2.4";
    }

    @Override
    public final List<File> getAttachtments()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final Map<String, String> getCustomProperties() throws WBException
    {
        HashMap<String, String> properties = new HashMap<String, String>();
        /*for (Property property : this.getProperties().getPropertySetInfo().getProperties())
        {
        try
        {
        String value = this.getProperties().getPropertyValue(property.Name).toString();
        properties.put(property.Name, value);
        }
        catch (UnknownPropertyException upe)
        {
        throw new WBOfficeException("No se puede actualizar la información asociada a la publicación del contenido", upe);
        }
        catch (WrappedTargetException wte)
        {
        throw new WBOfficeException("No se puede actualizar la información asociada a la publicación del contenido", wte);
        }
        }*/
        XSpreadsheetDocument xtd =
                (XSpreadsheetDocument) UnoRuntime.queryInterface(XSpreadsheetDocument.class, this.document);

        XDocumentInfoSupplier xdis =
                (XDocumentInfoSupplier) UnoRuntime.queryInterface(XDocumentInfoSupplier.class, xtd);
        XDocumentInfo xdi = xdis.getDocumentInfo();
        short index = xdi.getUserFieldCount();
        for (short i = 0; i < index; i++)
        {
            try
            {
                String name = xdi.getUserFieldName(i);
                String value = xdi.getUserFieldValue(i);
                properties.put(name, value);
            }
            catch (com.sun.star.lang.ArrayIndexOutOfBoundsException aibe)
            {
                throw new WBOfficeException("No se puede actualizar la información asociada a la publicación del contenido", aibe);
            }
        }
        return properties;
    }

    @Override
    public final DocumentType getDocumentType()
    {
        return DocumentType.EXCEL;
    }

    @Override
    public final File getLocalPath() throws WBException
    {
        XModel xtd = (XModel) UnoRuntime.queryInterface(XModel.class, this.document);
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        if (xStorable.hasLocation())
        {
            String path = xtd.getURL();
            if (path.startsWith(SCHEMA_FILE))
            {
                path = path.substring(8);
            }
            return new File(path);
        }
        else
        {
            throw new WBAlertException("El documento no ha sido almacenado todavía");
        }

    }

    @Override
    public final void save() throws WBException
    {
        try
        {
            XModifiable xModified = (XModifiable) UnoRuntime.queryInterface(XModifiable.class, document);
            if (xModified.isModified())
            {
                XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
                if (!xStorable.isReadonly())
                {
                    xStorable.store();
                }
                else
                {
                    throw new WBAlertException("No se puede almacenar el documento por que es de sólo lectura");
                }
            }

        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);

        }
    }

    @Override
    public final File saveAs(File dir, SaveDocumentFormat format) throws WBException
    {
        File result;
        switch (format)
        {
            case HTML:
                result= this.saveAsHtml(dir);
                break;
            case OFFICE_2003:
                result= saveAsOffice2003(dir);
                break;
            default:
                result=saveAsOpenOffice(dir);                
        }
        return result;
    }

    private File saveAsOpenOffice(File dir) throws WBException
    {
        try
        {
            File docFile = this.getLocalPath();
            int index = docFile.getName().lastIndexOf(".");
            String extension = null;
            if (index != -1)
            {
                extension = docFile.getName().substring(index);
            }
            String name = null;
            if (extension == null)
            {
                name = docFile.getName();
            }
            else
            {
                name = docFile.getName().replace(extension, OPENOFFICE_EXTENSION);
            }
            // guarda el documento en .doc en directorio Temporal
            File DocFile = new File(dir.getPath() + File.separator + name);
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "Calc8";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERRIDE_OPTION;
            storeProps[1].Value = true;
            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = SCHEMA_FILE + DocFile.getPath().replace('\\', '/');
            xStorable.storeToURL(url, storeProps);
            return DocFile;
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);
        }
    }

    private File saveAsOffice2003(File dir) throws WBException
    {
        try
        {
            File docFile = this.getLocalPath();
            int index = docFile.getName().lastIndexOf(".");
            String extension = null;
            if (index != -1)
            {
                extension = docFile.getName().substring(index);
            }
            String name = null;
            if (extension == null)
            {
                name = docFile.getName();
            }
            else
            {
                name = docFile.getName().replace(extension, EXCEL_EXTENSION);
            }
            // guarda el documento en .doc en directorio Temporal
            File DocFile = new File(dir.getPath() + File.separator + name);
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "MS Excel 97";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERRIDE_OPTION;
            storeProps[1].Value = true;
            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = SCHEMA_FILE + DocFile.getPath().replace('\\', '/');
            xStorable.storeToURL(url, storeProps);
            return DocFile;
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);
        }
    }

    @Override
    public void save(File file) throws WBException
    {
        try
        {
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "Calc8";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERRIDE_OPTION;
            storeProps[1].Value = true;

            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            String url = SCHEMA_FILE + file.getPath().replace('\\', '/');
            xStorable.storeAsURL(url, storeProps);
        }
        catch (IOException wbe)
        {
            throw new WBOfficeException("No se puede gardar el documento", wbe);
        }
    }

    @Override
    public final File saveAsHtml(File dir) throws WBException
    {

        try
        {
            File docFile = this.getLocalPath();
            if (!(docFile.getName().endsWith(OPENOFFICE_EXTENSION) || docFile.getName().endsWith(EXCEL_EXTENSION)))
            {
                throw new WBAlertException("El documento debe estar en formato de Open Office Calc(" + OPENOFFICE_EXTENSION + ")  o Excel 2000/XP/2003/2007 (" + EXCEL_EXTENSION + ")");
            }
            File HTMLfile;
            if (docFile.getName().endsWith(OPENOFFICE_EXTENSION))
            {
                HTMLfile = new File(dir.getPath() + File.separator + docFile.getName().replace(OPENOFFICE_EXTENSION, HTML_EXTENSION));
                String name = docFile.getName().replace(OPENOFFICE_EXTENSION, EXCEL_EXTENSION);
                // guarda el documento en .doc en directorio Temporal
                File DocFile = new File(dir.getPath() + File.separator + name);
                PropertyValue[] storeProps = new PropertyValue[2];
                storeProps[0] = new PropertyValue();
                storeProps[0].Name = FILTER_NAME;
                storeProps[0].Value = "MS Excel 97";

                storeProps[1] = new PropertyValue();
                storeProps[1].Name = OVERRIDE_OPTION;
                storeProps[1].Value = true;
                XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);

                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                String url = SCHEMA_FILE + DocFile.getPath().replace('\\', '/');
                xStorable.storeToURL(url, storeProps);
            }
            else
            {
                HTMLfile = new File(dir.getPath() + File.separator + docFile.getName().replace(EXCEL_EXTENSION, HTML_EXTENSION));
            }

            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "HTML (StarCalc)";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERRIDE_OPTION;
            storeProps[1].Value = true;


            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = SCHEMA_FILE + HTMLfile.getPath().replace('\\', '/');
            xStorable.storeToURL(url, storeProps);
            return HTMLfile;
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);

        }


    }

    @Override
    public final void saveCustomProperties(Map<String, String> properties) throws WBException
    {
        XSpreadsheetDocument xtd =
                (XSpreadsheetDocument) UnoRuntime.queryInterface(XSpreadsheetDocument.class, this.document);
        XDocumentInfoSupplier xdis =
                (XDocumentInfoSupplier) UnoRuntime.queryInterface(XDocumentInfoSupplier.class, xtd);
        XDocumentInfo xdi = xdis.getDocumentInfo();
        short index = 0;
        // solo puede tener 4 propiedades
        for (String key : properties.keySet())
        {
            String value = properties.get(key);
            try
            {
                xdi.setUserFieldName(index, key);
                xdi.setUserFieldValue(index, value);
            }
            catch (com.sun.star.lang.ArrayIndexOutOfBoundsException aibe)
            {
                throw new WBOfficeException("No se puede actualizar la información asociada a la publicación del contenido", aibe);
            }
            index++;
        }
        this.save();
    }

    public final void prepareHtmlFileToSend(File htmlFile)
    {
    // TODO: Falta implementar    
    }

    public boolean isNewDocument()
    {
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        return !xStorable.hasLocation();
    }

    public boolean isReadOnly()
    {
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        return xStorable.isReadonly();
    }

    public boolean isModified()
    {
        XModifiable xModified = (XModifiable) UnoRuntime.queryInterface(XModifiable.class, document);
        return xModified.isModified();
    }

    public String getDefaultExtension()
    {
        return OPENOFFICE_EXTENSION;
    }
}

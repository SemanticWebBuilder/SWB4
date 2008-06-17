/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.writer;

import com.sun.star.beans.PropertyValue;
import com.sun.star.document.XDocumentInfo;
import com.sun.star.document.XDocumentInfoSupplier;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XModifiable;
import java.io.File;
import java.util.ArrayList;
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
 * Class to Wrap an Open Office Writer Document
 * @author victor.lorenzana
 */
public class WB4Writer extends OfficeDocument
{

    /**
     * The default Open Office Extension OPENOFFICE_EXTENSION=".odt"
     */
    public static final String OPENOFFICE_EXTENSION = ".odt";
    /**
     * The xtension to a Word Document in format XP/2000/2003 WORD_EXTENSION=".doc"
     */
    public static final String WORD_EXTENSION = ".doc";
    /**
     * The extension of a HTML Document HTML_EXTENSION=".html"
     */
    public static final String HTML_EXTENSION = ".html";
    private static final String FILTER_NAME = "FilterName";
    private static final String OVERWRITE = "Overwrite";
    private static final String SCHEMA_FILE = "file:///";
    /**
     * The Open Office Writer Document that Wraps
     */
    private final XComponent document;

    public WB4Writer(XComponent document) throws WBOfficeException
    {
        this.document = document;
    }

    /**
     * Contructor to obtain the current document
     * @param m_xContext Conext to the Office Application
     * @throws org.wb.WBOfficeException Error if there is not current document or the Desktop can not be obtained
     */
    public WB4Writer(XComponentContext m_xContext) throws WBOfficeException
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

    /*public XPropertySet getProperties()
    {
    XTextDocument xtd =
    (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, this.document);
    XDocumentInfoSupplier xdis =
    (XDocumentInfoSupplier) UnoRuntime.queryInterface(XDocumentInfoSupplier.class, xtd);
    XDocumentInfo xdi = xdis.getDocumentInfo();        
    XPropertySet xps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xdi);        
    return xps;
    }*/
    /**
     *  Gets the Application Version of Open Office
     * @return The version of Open Office Application     * 
     */
    @Override
    public String getApplicationVersion()
    {

        return "2.4";
    }

    /**
     * Gets the files in the actual document
     * @return List of Files in the actual document
     */
    @Override
    public List<File> getAttachtments()
    {
        List<File> attachments = new ArrayList<File>();
        // TODO:
        return attachments;
    }

    /**
     * Gets the Custom Properties of a Open Office Document (called User Information)
     * @return A Map with the CustomProperties
     * @throws org.wb.WBException In case of number of properties are more than 4
     */
    @Override
    public Map<String, String> getCustomProperties() throws WBException
    {
        HashMap<String, String> properties = new HashMap<String, String>();
        XTextDocument xtd =
                (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, this.document);
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

    /**
     * Gets the type of Open Office document
     * @return Returns always DocumentType.WORD
     * @see DocumentType
     */
    @Override
    public DocumentType getDocumentType()
    {
        return DocumentType.WORD;
    }

    /**
     * Gets the path to the Open Document file
     * @return A File with the path to the current Open Office Document
     * @throws org.wb.WBException If is not possible to get the path
     */
    @Override
    public File getLocalPath() throws WBException
    {

        XTextDocument xtd = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, this.document);
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

    /**
     * Save the current document
     * @throws org.wb.WBException In case the document is read only
     */
    @Override
    public void save() throws WBException
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
            throw new WBOfficeException("No se puede almacenar el documento : save", ioe);

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
            storeProps[0].Value = "Writer8";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
            storeProps[1].Value = true;

            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            String url = SCHEMA_FILE + file.getPath().replace('\\', '/');
            xStorable.storeAsURL(url, storeProps);
        }
        catch (IOException wbe)
        {
            throw new WBOfficeException("No se puede guardar el documento", wbe);
        }
    }

    /**
     * Save the document with a format
     * @param dir Directory to save
     * @param format Format to use
     * @return The new path of the file
     * @throws org.wb.WBException If the format is not supported
     * @see SaveDocumentFormat
     */
    @Override
    public File saveAs(File dir, SaveDocumentFormat format) throws WBException
    {
        File result;
        switch (format)
        {
            case HTML:
                result = this.saveAsHtml(dir);
                break;
            case OFFICE_2003:
                result = saveAsOffice2003(dir);
                break;
            default:
                result = saveAsOpenOffice(dir);
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
            storeProps[0].Value = "Writer8";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
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
            throw new WBOfficeException("No se puede almacenar el documento : saveAsOpenOffice", ioe);
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
                name = docFile.getName().replace(extension, WORD_EXTENSION);
            }
            // guarda el documento en .doc en directorio Temporal
            File DocFile = new File(dir.getPath() + File.separator + name);
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "MS Word 97";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
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
            throw new WBOfficeException("No se puede almacenar el documento : saveAsOffice2003", ioe);
        }
    }

    /**
     * Save the document in HTML format
     * @param dir Directory to save
     * @return The new path of the file
     * @throws org.wb.WBException If is nor possible to save the document
     */
    @Override
    public File saveAsHtml(File dir) throws WBException
    {
        try
        {
            File docFile = this.getLocalPath();
            if (!(docFile.getName().endsWith(OPENOFFICE_EXTENSION) || docFile.getName().endsWith(".doc")))
            {
                throw new WBAlertException("El documento debe estar en formato de Open Office Writer (" + OPENOFFICE_EXTENSION + ")  o Word 2000/XP/2003/2007 con extensión (" + WORD_EXTENSION + ")");
            }
            File HTMLfile;
            if (docFile.getName().endsWith(OPENOFFICE_EXTENSION))
            {
                HTMLfile = new File(dir.getPath() + File.separator + docFile.getName().replace(OPENOFFICE_EXTENSION, HTML_EXTENSION));
                String name = docFile.getName().replace(OPENOFFICE_EXTENSION, WORD_EXTENSION);
                // guarda el documento en .doc en directorio Temporal
                File DocFile = new File(dir.getPath() + File.separator + name);
                PropertyValue[] storeProps = new PropertyValue[2];
                storeProps[0] = new PropertyValue();
                storeProps[0].Name = FILTER_NAME;
                storeProps[0].Value = "MS Word 97";

                storeProps[1] = new PropertyValue();
                storeProps[1].Name = OVERWRITE;
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
                HTMLfile = new File(dir.getPath() + File.separator + docFile.getName().replace(WORD_EXTENSION, HTML_EXTENSION));
            }

            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = "HTML (StarWriter)";

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
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
            throw new WBOfficeException("No se puede almacenar el documento : saveAsHTML", ioe);
        }
    }

    /**
     * Save the custom properties to the Office Document
     * @param properties Properties to Save
     * @throws org.wb.WBException If is not posible to save the doument or the properties are more that 4
     */
    @Override
    public void saveCustomProperties(Map<String, String> properties) throws WBException
    {
        XTextDocument xtd =
                (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, this.document);
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

    /**
     * Prepare a HTML document to be published
     * @param htmlFile Path to the HTML file (This file must be generated by OpenOffice Writer)
     */
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

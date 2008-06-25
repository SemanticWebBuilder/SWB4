/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.writer;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.document.XDocumentInfo;
import com.sun.star.document.XDocumentInfoSupplier;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XModifiable;
import com.sun.star.util.XPropertyReplace;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.ErrorLog;
import org.semanticwb.openoffice.NoHasLocationException;
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
    private static final String OPENOFFICE_EXTENSION = ".odt";
    /**
     * The xtension to a Word Document in format XP/2000/2003 WORD_EXTENSION=".doc"
     */
    private static final String WORD_EXTENSION = ".doc";
    /**
     * The extension of a HTML Document HTML_EXTENSION=".html"
     */
    private static final String HTML_EXTENSION = ".html";
    private static final String FILTER_NAME = "FilterName";
    private static final String HYPERLINK_VALUE = "HyperLinkURL";
    private static final String OVERWRITE = "Overwrite";
    private static final String SCHEMA_FILE = "file:///";
    /**
     * The Open Office Writer Document that Wraps
     */
    private final XComponent document;

    /**
     * Create a representation of a Writer Document
     * @param document Representation of a Writer Document
     * @see XComponent
     */
    public WB4Writer(XComponent document)
    {
        this.document = document;
    }

    /**
     * Create a representation of a Writer Document
     * @param m_xContext Context of Writer Application
     * @throws org.semanticwb.openoffice.WBOfficeException If the Desktop can not be used
     * @see XComponentContext
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

    /**
     * Gets the Application version string, allways returns 2.4
     * @return Application version String     * 
     */
    @Override
    public String getApplicationVersion()
    {

        return "2.4";
    }

    /**
     * Gets all the files in the document
     * @return List of files in the document
     * @throws org.semanticwb.openoffice.NoHasLocationException The document has not saved before
     */
    @Override
    public final List<File> getAllAttachments() throws NoHasLocationException
    {
        List<File> attachments = new ArrayList<File>();
        try
        {
            XSearchable xSearchable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, this.document);
            XSearchDescriptor xUrlSearchDesc = xSearchable.createSearchDescriptor();

            PropertyValue[] aSearchArgs = new PropertyValue[1];
            aSearchArgs[0] = new PropertyValue();
            aSearchArgs[0].Name = HYPERLINK_VALUE;

            XPropertyReplace xPropSear = (XPropertyReplace) UnoRuntime.queryInterface(XPropertyReplace.class, xUrlSearchDesc);
            xPropSear.setSearchAttributes(aSearchArgs);
            xPropSear.setValueSearch(false);
            Object linkResult = xSearchable.findFirst(xUrlSearchDesc);
            while (linkResult != null)
            {
                try
                {
                    XTextRange xTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, linkResult);
                    XTextCursor xTextCursor = xTextRange.getText().createTextCursorByRange(xTextRange);
                    XPropertySet xPropSet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTextCursor);
                    Object hiperlink = xPropSet.getPropertyValue(HYPERLINK_VALUE);
                    attachments.addAll(this.addLink(hiperlink.toString()));
                    linkResult = xSearchable.findNext(xTextRange.getEnd(), xUrlSearchDesc);
                }
                catch (com.sun.star.uno.Exception ukpe)
                {
                    ErrorLog.log(ukpe);
                }

            }
        }
        catch (com.sun.star.uno.Exception ukpe)
        {
            ErrorLog.log(ukpe);
        }

        return attachments;
    }

    /**
     * Gets al the custom properties of the document
     * @return A Map of custum properties
     * @throws org.semanticwb.openoffice.WBException If the list of properties are more that four
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
     * Gets the type of document
     * @return DocumentType.WORD
     * @see DocumentType
     */
    @Override
    public DocumentType getDocumentType()
    {
        return DocumentType.WORD;
    }

   /**
     * Gets the path of the fisical document
     * @return A File with the fisical path of the document
     * @throws org.semanticwb.openoffice.NoHasLocationException If the document has not been saved
     */
    @Override
    public File getLocalPath() throws NoHasLocationException
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
            throw new NoHasLocationException();
        }
    }

    /**
     * Save the document
     * @throws org.semanticwb.openoffice.WBException If the document has not been saved before
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

    /**
     * Save the document in default format
     * @param file The full path of the file, overwrite the document if exists
     * @throws org.semanticwb.openoffice.WBException If can not be saved
     * @throws IllegalArgumentException If the path is a directory
     */
    @Override
    public void save(File file) throws WBException
    {
        if (file.isDirectory())
        {
            throw new IllegalArgumentException();
        }
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
     * Save the document in selected a format
     * @param dir The path of the file
     * @param format The SaveDocumentFormat to use
     * @return a File with the full path of the new document
     * @throws org.semanticwb.openoffice.WBException If the document can not be saved
     * @throws IllegalArgumentException If the parameter is a file, must be a directory
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

    /**
     * Save the couemnt in Open Office format (.ods)
     * @param dir The directory to save the document
     * @return the full path of the new document
     * @throws org.semanticwb.openoffice.WBException If the document can not be saved
     * @throws IllegalArgumentException If the parameter is a file, must be a directory
     */
    private File saveAsOpenOffice(File dir) throws WBException
    {
        if(dir.isFile())
        {
            throw new IllegalArgumentException();
        }
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

    /**
     * Save the document in Office 2003 format
     * @param dir File Path directory to save the cocument
     * @return The full path of the new file
     * @throws org.semanticwb.openoffice.WBException
     * @throws IllegalArgumentException If the parameter is a file
     */
    private File saveAsOffice2003(File dir) throws WBException
    {
        if(dir.isFile())
        {
            throw new IllegalArgumentException();
        }
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
     * Save the document in Html format
     * @param dir Directory to save the document
     * @return The full path of the document
     * @throws org.semanticwb.openoffice.WBException If the document can not be saved
     * @throws IllegalArgumentException If the File is a file and not a directory
     */
    @Override
    public File saveAsHtml(File dir) throws WBException
    {
        if (dir.isFile())
        {
            throw new IllegalArgumentException();
        }
        try
        {
            File docFile = this.getLocalPath();           
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
     * Save the properties in custom properties in the document
     * @param properties Properties to save
     * @throws org.semanticwb.openoffice.WBException if the properties are more than four
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
     * Prepare the html to be published
     * @param htmlFile The full path of the Html document exported by the application
     * @throws IllegalArgumentException If the path is a directory
     */
    public final void prepareHtmlFileToSend(File htmlFile)
    {
        if (htmlFile.isDirectory())
        {
            throw new IllegalArgumentException();
        }
    // TODO: Falta implementar    
    }

    /**
     * Gets is the document is new, it means that the document has not been saved before
     * @return True if the document is new, false otherwise
     */
    public boolean isNewDocument()
    {
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        return !xStorable.hasLocation();
    }

    /**
     * Gets if the document is readonly or not
     * @return True if the document is readonly or not
     */
    public boolean isReadOnly()
    {
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        return xStorable.isReadonly();
    }

    /**
     * Gets if the document has been modified and can be saves
     * @return True if the document has been modified, false otherwise
     */
    public boolean isModified()
    {
        XModifiable xModified = (XModifiable) UnoRuntime.queryInterface(XModifiable.class, document);
        return xModified.isModified();
    }

    /**
     * Gets the Default extension used by the application
     * @return A string with the default extension, allways returns .odt
     */
    public String getDefaultExtension()
    {
        return OPENOFFICE_EXTENSION;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.impress;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.document.XDocumentInfo;
import com.sun.star.document.XDocumentInfoSupplier;
import com.sun.star.drawing.XDrawPage;
import com.sun.star.drawing.XDrawPages;
import com.sun.star.drawing.XDrawPagesSupplier;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapes;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XTextField;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XModifiable;
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
import static org.semanticwb.openoffice.util.FileUtil.saveContent;
import static org.semanticwb.openoffice.util.FileUtil.loadResourceAsString;
import static org.semanticwb.openoffice.util.FileUtil.loadFileAsString;
import static org.semanticwb.openoffice.util.FileUtil.getExtension;
import static org.semanticwb.openoffice.util.FileUtil.getFileFromURL;
import static org.semanticwb.openoffice.util.FileUtil.getPathURL;

/**
 * Class to Wrap a Open Office Impress Document
 * @author victor.lorenzana
 */
public class WB4Impress extends OfficeDocument
{
    private static final String ERROR_DOCUMENT_NOT_MODIFIED = "The document has not been modified";
    private static final String ERROR_DOCUMENT_NOT_SAVED_BEFORE = "The document has not been saved before";
    private static final String ERROR_DOCUMENT_NOT_FOUND = "There is not a document active in the desktop";
    private static final String OVERRIDE_OPTION = "Overwrite";
    private static final String APPLICATION_VERSION = "2.4";
    private static final String DESKTOP_PATH = "com.sun.star.frame.Desktop";
    private static final String ERROR_DESKTOP_NOT_FOUND = "The desktop was not found";
    private static final String HTML_EXPORT_FORMAT = "impress_html_Export";
    private static final String IMPRESS_FORMAT = "Impress8";
    private static final String INDEXOFBOUNDERROR = "There was an error saving custom properties";
    private static final String ERROR_DOCUMENT_READ_ONLY = "The document is read only";
    private static final String ERROR_NO_SAVE = "The document can not be saved";
    private static final String OPENOFFICE_EXTENSION = ".odp";
    private static final String POWERPOINT_97_FORMAT = "MS PowerPoint 97";
    private static final String PPT_EXTENSION = ".ppt";
    private static final String HTML_EXTENSION = ".html";
    private static final String FILTER_NAME = "FilterName";
    private static final String OVERWRITE = "Overwrite";
    private static final String SLIDE_PREFIX = "img";
    private static final String frameContentHTML;
    private static final String ContentHTML;
    private static final String fullscreenHTML;
    private static final String outline;
    private static final String script;
    private final XComponent document;

    static
    {
        frameContentHTML = loadResourceAsString(WB4Impress.class, "frame.html");
        outline = loadResourceAsString(WB4Impress.class, "outline.html");
        ContentHTML = loadResourceAsString(WB4Impress.class, "content.html");
        fullscreenHTML = loadResourceAsString(WB4Impress.class, "fullscreen.html");
        script = loadResourceAsString(WB4Impress.class, "script.js");
    }

    /**
     * Create a representation of a Impress Document
     * @param document Representation of a Impress Document
     * @see XComponent
     */
    public WB4Impress(XComponent document)
    {
        this.document = document;        
    }

    /**
     * Create a representation of a Impress Document
     * @param m_xContext Context of Impress Application
     * @throws org.semanticwb.openoffice.WBOfficeException If the Desktop can not be used
     * @see XComponentContext
     */
    public WB4Impress(XComponentContext m_xContext) throws WBOfficeException
    {
        XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
        try
        {
            Object desktop = serviceManager.createInstanceWithContext(
                    DESKTOP_PATH, m_xContext);
            XDesktop xdesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
            document = xdesktop.getCurrentComponent();
            if(document==null)
            {
                throw new WBOfficeException(ERROR_DOCUMENT_NOT_FOUND);
            }
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException(ERROR_DESKTOP_NOT_FOUND, e);
        }        
    }

    /**
     * Gets the Application version string, allways returns 2.4
     * @return Application version String      
     */
    @Override
    public final String getApplicationVersion()
    {
        return APPLICATION_VERSION;
    }

    /**
     * Gets the number of presentation slides that contains the presentation
     * @param xComponent Impress Document
     * @return Number of presentation slides
     */
    private static int getDrawPageCount(XComponent xComponent)
    {
        XDrawPagesSupplier xDrawPagesSupplier =
                (XDrawPagesSupplier) UnoRuntime.queryInterface(
                XDrawPagesSupplier.class, xComponent);
        XDrawPages xDrawPages = xDrawPagesSupplier.getDrawPages();
        return xDrawPages.getCount();
    }

    /**
     * Gets the Slide ina presentation
     * @param xComponent Impress Document
     * @param nIndex Indes of presentation
     * @return XDrawPage(Slide) found
     * @throws com.sun.star.lang.IndexOutOfBoundsException
     * @throws com.sun.star.lang.WrappedTargetException
     */
    private static XDrawPage getDrawPageByIndex(XComponent xComponent, int nIndex)
            throws com.sun.star.lang.IndexOutOfBoundsException,
            WrappedTargetException
    {
        XDrawPagesSupplier xDrawPagesSupplier =
                (XDrawPagesSupplier) UnoRuntime.queryInterface(
                XDrawPagesSupplier.class, xComponent);
        XDrawPages xDrawPages = xDrawPagesSupplier.getDrawPages();
        return (XDrawPage) UnoRuntime.queryInterface(XDrawPage.class, xDrawPages.getByIndex(nIndex));
    }

    /**
     * Gets all the files in a shape
     * @param xShape The shape to search
     * @return List of files found
     * @throws org.semanticwb.openoffice.NoHasLocationException
     * @see XShape
     */
    private List<File> getAttachtments(XShape xShape) throws NoHasLocationException
    {
        List<File> attachments = new ArrayList<File>();
        XTextRange textRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, xShape);
        XEnumerationAccess xParaEA = (XEnumerationAccess) UnoRuntime.queryInterface(XEnumerationAccess.class, textRange);
        XEnumeration xParaEnum = xParaEA.createEnumeration();
        while (xParaEnum.hasMoreElements())
        {
            try
            {
                Object aPortionObj = xParaEnum.nextElement();
                XEnumerationAccess xPortionEA = (XEnumerationAccess) UnoRuntime.queryInterface(XEnumerationAccess.class, aPortionObj);
                XEnumeration xPortionEnum = xPortionEA.createEnumeration();
                while (xPortionEnum.hasMoreElements())
                {
                    try
                    {
                        XTextRange xRange = (XTextRange) UnoRuntime.queryInterface(com.sun.star.text.XTextRange.class, xPortionEnum.nextElement());
                        XPropertySet xPropSet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xRange);
                        Object oTextfield = xPropSet.getPropertyValue("TextField");
                        XTextField xTextField = (XTextField) UnoRuntime.queryInterface(XTextField.class, oTextfield);
                        if (xTextField != null)
                        {
                            xPropSet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTextField);
                            String path = xPropSet.getPropertyValue("URL").toString();
                            attachments.addAll(this.addLink(path));
                        }
                    }
                    catch (com.sun.star.uno.Exception uke)
                    {
                        ErrorLog.log(uke);
                    }
                }
            }
            catch (com.sun.star.uno.Exception nse)
            {
                ErrorLog.log(nse);
            }
        }
        return attachments;
    }

    /**
     * Gets all the files into a Presentation Slide
     * @param xDrawPage Presentation Slide to search
     * @return List of files found
     * @throws com.sun.star.lang.IndexOutOfBoundsException
     * @throws com.sun.star.container.NoSuchElementException
     * @throws com.sun.star.lang.WrappedTargetException
     * @throws com.sun.star.beans.UnknownPropertyException
     * @throws org.semanticwb.openoffice.NoHasLocationException
     * @see XDrawPage
     */
    private List<File> getAttachtments(XDrawPage xDrawPage)
            throws com.sun.star.lang.IndexOutOfBoundsException,
            NoSuchElementException, WrappedTargetException,
            UnknownPropertyException, NoHasLocationException
    {
        List<File> attachments = new ArrayList<File>();
        XShapes xShapes = (XShapes) UnoRuntime.queryInterface(XShapes.class, xDrawPage);
        int shapes = xShapes.getCount();

        for (int iShape = 0;
                iShape < shapes;
                iShape++)
        {
            Object oShape = xShapes.getByIndex(iShape);
            XShape xShape = (XShape) UnoRuntime.queryInterface(XShape.class, oShape);
            attachments.addAll(this.getAttachtments(xShape));
        }




        return attachments;
    }

    /**
     * Gets all the files in the document
     * @return List of files in the document
     * @throws org.semanticwb.openoffice.NoHasLocationException The document has not saved before, and the document has hyperlinks relatives to the document
     */
    @Override
    public final List<File> getAllAttachments()
    {
        List<File> attachments = new ArrayList<File>();
        int pages = getDrawPageCount(document);
        for (int i = 0; i < pages; i++)
        {
            try
            {
                XDrawPage xDrawPage = getDrawPageByIndex(document, i);
                attachments.addAll(getAttachtments(xDrawPage));
            }
            catch (Exception iobe)
            {
                ErrorLog.log(iobe);
            }

        }
        return attachments;
    }

    /**
     * Gets al the custom properties of the document
     * @return A Map of custum properties
     * @throws org.semanticwb.openoffice.WBException If the list of properties are more that four
     */
    @Override
    public final Map<String, String> getCustomProperties() 
    {
        HashMap<String, String> properties = new HashMap<String, String>();

        XDocumentInfoSupplier xdis =
                (XDocumentInfoSupplier) UnoRuntime.queryInterface(XDocumentInfoSupplier.class, document);
        XDocumentInfo xdi = xdis.getDocumentInfo();        
        for (short i = 0; i < xdi.getUserFieldCount(); i++)
        {
            try
            {
                String name = xdi.getUserFieldName(i);
                String value = xdi.getUserFieldValue(i);
                properties.put(name, value);
            }
            catch (com.sun.star.lang.ArrayIndexOutOfBoundsException aibe)
            {                
                ErrorLog.log(aibe);
            }
        }
        return properties;
    }

    /**
     * Gets the type of document
     * @return DocumentType.PPT
     * @see DocumentType
     */
    @Override
    public final DocumentType getDocumentType()
    {
        return DocumentType.PPT;
    }

    /**
     * Gets the path of the fisical document
     * @return A File with the fisical path of the document
     * @throws org.semanticwb.openoffice.NoHasLocationException If the document has not been saved
     */
    @Override
    public final File getLocalPath()
            throws NoHasLocationException
    {

        XModel xtd = (XModel) UnoRuntime.queryInterface(XModel.class, this.document);
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
        if (xStorable.hasLocation())
        {
            return getFileFromURL(xtd.getURL());
        }
        else
        {
            throw new NoHasLocationException();
        }
    }

    /**
     * Save the document
     * @throws org.semanticwb.openoffice.WBException If the document has not been saved before, if the document has not been modified, or if the document is read only
     */
    @Override
    public final void save() throws WBException
    {
        try
        {
            XModifiable xModified = (XModifiable) UnoRuntime.queryInterface(XModifiable.class, document);
            if (xModified.isModified())
            {
                XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
                if (xStorable.hasLocation())
                {
                    if (!xStorable.isReadonly())
                    {
                        xStorable.store();
                    }
                    else
                    {
                        throw new WBAlertException(ERROR_DOCUMENT_READ_ONLY);
                    }
                }
                else
                {
                    throw new WBAlertException(ERROR_DOCUMENT_NOT_SAVED_BEFORE);
                }
            }
            else
            {
                throw new WBAlertException(ERROR_DOCUMENT_NOT_MODIFIED);
            }
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);

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
    public final File saveAs(
            File dir, SaveDocumentFormat format) throws WBException
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
     * Save the couemnt in Open Office format (.odp)
     * @param dir The directory to save the document
     * @return the full path of the new document
     * @throws org.semanticwb.openoffice.WBException If the document can not be saved
     * @throws IllegalArgumentException If the parameter is a file, must be a directory
     */
    private File saveAsOpenOffice(File dir) throws WBException
    {
        if (dir.isDirectory())
        {
            throw new IllegalArgumentException();
        }
        try
        {
            File docFile = this.getLocalPath();
            String extension = getExtension(docFile);
            String name = null;
            if (extension == null)
            {
                name = docFile.getName();
            }
            else
            {
                name = docFile.getName().replace(extension, OPENOFFICE_EXTENSION);
            }
            File DocFile = new File(dir.getPath() + File.separatorChar + name);
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = IMPRESS_FORMAT;

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
            storeProps[1].Value = true;
            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = getPathURL(DocFile);

            xStorable.storeToURL(url, storeProps);
            return DocFile;
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);
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
        if (dir.isDirectory())
        {
            throw new IllegalArgumentException();
        }
        try
        {
            File docFile = this.getLocalPath();
            String extension = getExtension(docFile);
            String name = null;
            if (extension == null)
            {
                name = docFile.getName();
            }
            else
            {
                name = docFile.getName().replace(extension, PPT_EXTENSION);
            }
// guarda el documento en .doc en directorio Temporal
            File DocFile = new File(dir.getPath() + File.separatorChar + name);
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = POWERPOINT_97_FORMAT;

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
            storeProps[1].Value = true;
            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = getPathURL(DocFile);
            xStorable.storeToURL(url, storeProps);
            return DocFile;
        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);
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
        try
        {
            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = IMPRESS_FORMAT;

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERWRITE;
            storeProps[1].Value = true;

            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            String url = getPathURL(file);

            xStorable.storeAsURL(url, storeProps);
        }
        catch (IOException wbe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, wbe);
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
    public final File saveAsHtml(File dir) throws WBException
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
                HTMLfile = new File(dir.getPath() + File.separatorChar + docFile.getName().replace(OPENOFFICE_EXTENSION, HTML_EXTENSION));
                String name = docFile.getName().replace(OPENOFFICE_EXTENSION, PPT_EXTENSION);
                // guarda el documento en .doc en directorio Temporal
                File DocFile = new File(dir.getPath() + File.separatorChar + name);
                PropertyValue[] storeProps = new PropertyValue[2];
                storeProps[0] = new PropertyValue();
                storeProps[0].Name = FILTER_NAME;
                storeProps[0].Value = POWERPOINT_97_FORMAT;

                storeProps[1] = new PropertyValue();
                storeProps[1].Name = OVERWRITE;
                storeProps[1].Value = true;
                XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                String url = getPathURL(DocFile);
                xStorable.storeToURL(url, storeProps);
            }
            else
            {
                HTMLfile = new File(dir.getPath() + File.separatorChar + docFile.getName().replace(PPT_EXTENSION, HTML_EXTENSION));
            }

            PropertyValue[] storeProps = new PropertyValue[2];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = FILTER_NAME;
            storeProps[0].Value = HTML_EXPORT_FORMAT;

            storeProps[1] = new PropertyValue();
            storeProps[1].Name = OVERRIDE_OPTION;
            storeProps[1].Value = true;

            XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String url = getPathURL(HTMLfile);
            xStorable.storeToURL(url, storeProps);
            return HTMLfile;

        }
        catch (IOException ioe)
        {
            throw new WBOfficeException(ERROR_NO_SAVE, ioe);

        }

    }

    /**
     * Save the properties in custom properties in the document
     * @param properties Properties to save
     * @throws org.semanticwb.openoffice.WBException if the properties are more than four
     */
    @Override
    public final void saveCustomProperties(
            Map<String, String> properties) throws WBException
    {
        XDocumentInfoSupplier xdis =
                (XDocumentInfoSupplier) UnoRuntime.queryInterface(XDocumentInfoSupplier.class, document);

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
                throw new WBOfficeException(INDEXOFBOUNDERROR, aibe);
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
        saveContentasImg0(htmlFile);
        saveContent(ContentHTML, htmlFile);
        createFrameFile(htmlFile);
        saveButtons(htmlFile.getParentFile());
        appendTextToScript(htmlFile.getParentFile());
        createOutline(htmlFile);
        saveFullScreen(htmlFile.getParentFile());
        changeSlides(htmlFile.getParentFile());


    // TODO: Falta implementar    
    }

    private void changeSlides(File dir)
    {
        for (File file : this.getFiles(dir))
        {
            if (file.getName().startsWith(SLIDE_PREFIX) && file.getName().endsWith(HTML_EXTENSION))
            {
                int posInit = file.getName().indexOf(SLIDE_PREFIX);
                int posEnd = file.getName().indexOf(HTML_EXTENSION);
                String number = file.getName().substring(posInit + 3, posEnd);
                try
                {
                    StringBuilder content = new StringBuilder("<html><head><script src=script.js></script>\r\n");
                    content.append("<script><!--\r\n");
                    content.append("if( !IsNts() ) Redirect( \"PPTSld\" );\r\n");
                    content.append("//--></script>\r\n");
                    content.append("</head> <body onclick=\"DocumentOnClick()\" onresize=\"_RSW()\" onload=\"LoadSld()\"");
                    content.append("onkeypress=\"_KPH()\">");
                    content.append("<div id=SlideObj class=sld>");
                    content.append("<center><img src=\"img" + number + ".jpg\"></center></div></body></html>");
                    saveContent(content, file);
                }
                catch (Exception ex)
                {
                    ErrorLog.log(ex);
                }
            }

        }
    }

    private void appendTextToScript(File dir)
    {
        if (dir.isFile())
        {
            throw new IllegalArgumentException();
        }
        List<File> files = this.getFiles(dir);
        StringBuilder builder = new StringBuilder("var gMainDoc=new Array(");
        for (File file : files)
        {
            if (file.getName().startsWith(SLIDE_PREFIX) && file.getName().endsWith(HTML_EXTENSION))
            {
                builder.append("new hrefList(\"" + file.getName() + "\",1,-1,1),");
            }
        }
        if (builder.charAt(builder.length() - 1) == ',')
        {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(");\r\n");
        builder.append(script);
        File scriptFile = new File(dir.getPath() + File.separatorChar + "script.js");
        saveContent(builder, scriptFile);
    }

    private void saveFullScreen(File dir)
    {
        if (dir.isFile())
        {
            throw new IllegalArgumentException();
        }
        File fullscreen = new File(dir.getPath() + File.separatorChar + "fullscreen.html");
        saveContent(fullscreenHTML, fullscreen);
    }

    private void saveButtons(File dir)
    {
        if (dir.isFile())
        {
            throw new IllegalArgumentException();
        }
        File buttons = new File(dir.getPath() + File.separatorChar + "buttons.gif");
        saveContent(WB4Impress.class, "buttons.gif", buttons);
    }

    private List<String> getSlidesTitles()
    {
        ArrayList<String> titles = new ArrayList<String>();
        int pages = getDrawPageCount(document);
        for (int i = 0; i < pages; i++)
        {
            try
            {
                XDrawPage xDrawPage = getDrawPageByIndex(document, i);
                XPropertySet props = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xDrawPage);
                titles.add(props.getPropertyValue("LinkDisplayName").toString());
            }
            catch (com.sun.star.uno.Exception ex)
            {
                ErrorLog.log(ex);
            }
        }
        return titles;
    }

    private String fixSubEsquema(String subSquema)
    {
        String target = " target=\"PPTSld\" ";
        int posInit = subSquema.indexOf("<a");
        if (posInit != -1)
        {
            subSquema = subSquema.substring(0, posInit + 2) + target + subSquema.substring(posInit + 2);
        }
        return subSquema;
    }

    private String getSubSquema(int iSlide, File htmlfile)
    {
        File dir = htmlfile.getParentFile();
        String subSquema = "";
        File textSquema = new File(dir.getPath() + File.separatorChar + "text" + iSlide + HTML_EXTENSION);
        if (textSquema.exists())
        {
            try
            {
                String content = loadFileAsString(textSquema);
                int posInit = content.indexOf("<ul>");
                int posFin = content.indexOf("</ul>");
                if (posInit != -1 && posFin != -1)
                {
                    subSquema = content.substring(posInit, posFin + 5);
                }
                subSquema = fixSubEsquema(subSquema);
            }
            catch (Exception ex)
            {
                ErrorLog.log(ex);
            }
        }
        subSquema = subSquema.replace("<h2>", "");
        subSquema = subSquema.replace("</h2>", "");
        return subSquema;
    }

    private String getSquema(File htmlfile)
    {
        StringBuilder esquema = new StringBuilder("");
        int i = 0;
        for (String title : this.getSlidesTitles())
        {
            esquema.append("<tr onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"Follow(this)\"\r\n");
            esquema.append("style='cursor:hand'><td align=right valign=top><div class=sldNum><font size=2>" + (i + 1) + "</font></div></td><td width=\"100%\">\r\n");
            esquema.append("<div id=PPTP" + (i + 1) + " class=PTxt><font size=2><a href=\"javascript:GoToSld('img" + i + ".html');\" id=PPTL" + (i + 1) + ">" + title + "</a></font></div>\r\n");
            esquema.append("<div id=PPTC" + (i + 1) + " class=CTxt style='display:none'><font size=2>\r\n");
            String subSquema = getSubSquema(i, htmlfile);
            esquema.append(subSquema);
            esquema.append("</font></div></td></tr>\r\n");
            i++;
        }
        return esquema.toString();
    }

    private void saveContentasImg0(File htmlFile)
    {
        try
        {
            File img0 = new File(htmlFile.getParent() + File.separatorChar + "img0.html");
            saveContent(htmlFile, img0);
        }
        catch (Exception ex)
        {
            ErrorLog.log(ex);
        }
    }

    private void createOutline(File htmlFile)
    {
        String esquema = getSquema(htmlFile);
        if (htmlFile.isDirectory())
        {
            throw new IllegalArgumentException();
        }

        File outlineFile = new File(htmlFile.getParentFile().getPath() + File.separatorChar + "outline.html");
        if (outlineFile.exists())
        {
            outlineFile.delete();
        }
        String newOutLine = outline.replace("[esquema]", esquema);
        newOutLine=newOutLine.replace("[file]", htmlFile.getName());
        saveContent(newOutLine, outlineFile);
    }

    private void createFrameFile(File htmlFile)
    {
        if (htmlFile.isDirectory())
        {
            throw new IllegalArgumentException();
        }
        String htmlFrame = frameContentHTML.replace("[file]", htmlFile.getName());
        byte[] framecont = htmlFrame.getBytes();
        File frameFile = new File(htmlFile.getParentFile().getPath() + File.separatorChar + "frame.html");
        saveContent(framecont, frameFile);
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
     * @return A string with the default extension, allways returns .odp
     */
    public String getDefaultExtension()
    {
        return OPENOFFICE_EXTENSION;
    }
}

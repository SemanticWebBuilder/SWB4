/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogAddLink;
import org.semanticwb.openoffice.ui.dialogs.DialogContentInformation;
import org.semanticwb.openoffice.ui.dialogs.DialogHistory;
import org.semanticwb.openoffice.ui.dialogs.DialogSaveDocument;
import org.semanticwb.openoffice.ui.wizard.PagContenido;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectTypeToShow;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.openoffice.util.ExcelFileFilter;
import org.semanticwb.openoffice.util.PPTFileFilter;
import org.semanticwb.openoffice.util.WordFileFilter;
import org.semanticwb.xmlrpc.Attachment;
import static org.semanticwb.openoffice.util.FileUtil.copyFile;
import static java.lang.Integer.MIN_VALUE;

/**
 * An Office documents is an abstraction of a document that can be published
 * @author victor.lorenzana
 */
public abstract class OfficeDocument
{
    
    private static final String CONTENT_ID_NAME = "contentID";
    // By default the content is not published
    private int contentID = MIN_VALUE;

    static
    {
        Locale.setDefault(new Locale("es"));
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch ( Exception ue )
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
    }

    public final IOpenOfficeDocument getOfficeDocumentProxy() throws WBException
    {
        return OfficeApplication.getOfficeDocumentProxy();
    }
    protected OfficeDocument()
    {

    }

    protected final void setupDocument()
    {
        String contentId = this.getCustomProperties().get(CONTENT_ID_NAME);
        contentID = OfficeApplication.setupDocument(contentId);        
    }
    private static final String TITLE = "Asistente de publicación";

    /**
     * Gets the Application Version
     * @return String with the number of version
     */
    public abstract String getApplicationVersion();

    /**
     * Get true if the OfficeDocument is new, has not saved before
     * @return true if is new document, false otherwise
     */
    public abstract boolean isNewDocument();

    /**
     * Gets true if the document is readonly
     * @return true is the document is readonly, false otherwise
     */
    public abstract boolean isReadOnly();

    /**
     * Gets true if the document is modified y can be saved
     * @return true is the document is modified, false otherwise
     */
    public abstract boolean isModified();

    /**
     * Gets the path of the fisical document
     * @return A File with the fisical path of the document
     * @throws org.semanticwb.openoffice.NoHasLocationException If the document has not been saved before
     */
    public abstract File getLocalPath() throws NoHasLocationException;

    /**
     * Gets all the files in the document
     * @return List of files in the document
     * @throws org.semanticwb.openoffice.NoHasLocationException The document has not saved before     * 
     */
    protected abstract List<File> getAllAttachments() throws NoHasLocationException;

    /**
     * Gets al the custom properties of the document
     * @return A Map of custum properties
     * @throws org.semanticwb.openoffice.WBException If the list of properties are more that four
     */
    public abstract Map<String, String> getCustomProperties();

    /**
     * Save the properties in custom properties in the document
     * @param properties Properties to save
     * @throws org.semanticwb.openoffice.WBException if the properties are more than four
     */
    public abstract void saveCustomProperties(Map<String, String> properties) throws WBException;

    /**
     * Save the document in Html format
     * @param dir Directory to save the document
     * @return The full path of the document
     * @throws org.semanticwb.openoffice.WBException If the document can not be saved
     * @throws IllegalArgumentException If the File is a file and not a directory
     */
    public abstract File saveAsHtml(File dir) throws WBException;

    public abstract File saveAs(File dir, SaveDocumentFormat format) throws WBException;

    public abstract void save() throws WBException;

    public abstract void save(File file) throws WBException;

    public abstract String getDefaultExtension();

    public abstract DocumentType getDocumentType();

    public abstract void prepareHtmlFileToSend(File htmlFile);

    protected static File getFile(URI uri)
    {
        File file = new File(uri.getPath());
        return file;
    }

    public Set<File> getMisssingAttachtments() throws NoHasLocationException
    {
        Set<File> attachments = new HashSet<File>();
        for ( File file : this.getAllAttachments() )
        {
            if ( file.exists() )
            {
                attachments.add(file);
            }
        }
        return attachments;
    }

    public Set<File> getNotMissingAttachtments() throws NoHasLocationException
    {
        Set<File> attachments = new HashSet<File>();
        for ( File file : this.getAllAttachments() )
        {
            if ( file.exists() )
            {
                attachments.add(file);
            }
        }
        return attachments;
    }

    protected List<File> addLink(String path) throws NoHasLocationException
    {
        List<File> attachments = new ArrayList<File>();
        try
        {
            URI uri = new URI(path);
            if ( uri.getScheme() == null || uri.getScheme().equalsIgnoreCase("file") )
            {
                if ( uri.isAbsolute() )
                {
                    attachments.add(getFile(uri));
                }
                else
                {
                    URI base = new URI("file:///" + this.getLocalPath().getPath().replace('\\', '/'));
                    URI resolved = base.resolve(uri);
                    attachments.add(getFile(resolved));
                }
            }
        }
        catch ( URISyntaxException use )
        {
            ErrorLog.log(use);
        }

        return attachments;
    }

    public final void delete()
    {

    }

    public final void deleteAssociation()
    {
    /*try
    {
    HashMap<String, String> properties = new HashMap<String, String>();
    document.saveCustomProperties(properties);
    }
    catch (WBAlertException wba)
    {
    }
    catch (WBOfficeException wboe)
    {
    }
    catch (WBException wbe)
    {
    }*/
    }

    public final void showDocumentInSite()
    {

    }

    public final void showChanges()
    {
        DialogHistory dialog = new DialogHistory(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public final static void showDocumentInfo()
    {
        DialogContentInformation dialog = new DialogContentInformation(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    final void deleteTemporalDirectory(File dir)
    {
        File[] files = dir.listFiles();
        if ( files != null )
        {
            for ( File file : files )
            {
                if ( file.isFile() )
                {
                    file.delete();
                }
                else
                {
                    deleteTemporalDirectory(file);
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    public final File saveOnGuidDirectoryAsHtml(String guid) throws WBException
    {
        File file = new File(this.getLocalPath().getParentFile().getPath() + File.separator + guid);
        file = this.saveAsHtml(file);
        return file;

    }

    /**
     * 
     * @return
     */
    private String getGuid()
    {
        String guid = java.util.UUID.randomUUID().toString().replace('-', '_');
        return guid;
    }

    /**
     * 
     * @param guid
     * @return
     * @throws org.semanticwb.openoffice.WBException
     */
    private final File saveHtmlPrepareAndGetFiles(String guid) throws WBException
    {
        File file = this.saveOnGuidDirectoryAsHtml(guid);
        for ( File attatchment : this.getNotMissingAttachtments() )
        {
            copyFile(attatchment, file.getParentFile());
        }
        this.prepareHtmlFileToSend(file);
        return file;
    }

    /**
     * Create a zip with all the files asociated to the content to be sent to teh server, this zip conatins attchements and files generated by the html generation
     * @return A File with the path to the zip file
     * @throws org.semanticwb.openoffice.WBException If is not posible to create the zipfile, or the document is new and contains relatives attchaments
     */
    public File createZipFile() throws WBException
    {
        File tempotalDir = null;
        File tempotalZipFile = null;
        try
        {
            String guid = getGuid();
            File fileHtml = saveHtmlPrepareAndGetFiles(guid);
            tempotalDir = fileHtml.getParentFile();
            tempotalZipFile = new File(tempotalDir.getParentFile().getPath() + File.separatorChar + guid + ".zip");
            FileOutputStream fout = new FileOutputStream(tempotalZipFile);
            ZipOutputStream zipFile = new ZipOutputStream(fout);
            BufferedInputStream origin = null;
            int BUFFER_SIZE = 2048;
            byte[] data = new byte[BUFFER_SIZE];
            for ( File file : getFiles(tempotalDir) )
            {
                ZipEntry entry = new ZipEntry(file.getName());
                zipFile.putNextEntry(entry);
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                int count;
                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1)
                {
                    zipFile.write(data, 0, count);
                }
                // Close the source file
                origin.close();
                fi.close();
            }
            zipFile.close();
            fout.close();
            deleteTemporalDirectory(tempotalDir);
            return tempotalZipFile;
        }
        catch ( FileNotFoundException fnfe )
        {
            throw new WBException("No se puede crear el archivo zip", fnfe);
        }
        catch ( IOException ioe )
        {
            throw new WBException("No se puede crear el archivo zip", ioe);
        }
    }

    /**
     * Gets the files en a directory
     * @param dir Directory to search
     * @return List of Files into the directory
     */
    protected final List<File> getFiles(File dir)
    {
        ArrayList<File> attachments = new ArrayList<File>();
        for ( File file : dir.listFiles() )
        {
            if ( file.isFile() )
            {
                attachments.add(file);
            }
            else
            {
                List<File> files = getFiles(file);
                attachments.addAll(files);
            }
        }
        return attachments;
    }

    /**
     * Indicate if the document has been published
     * @return true if the document has a 
     */
    public final boolean isPublicated()
    {
        boolean isPublicated = false;
        if ( this.contentID == MIN_VALUE )
        {
            isPublicated = false;
        }
        else
        {
            isPublicated = true;
        }
        return isPublicated;
    }

    private final boolean showSaveDialog(OfficeDocument document)
    {
        boolean result = false;
        DialogSaveDocument fileChooser = new DialogSaveDocument(new JFrame(), true);
        if ( document.getDocumentType() == DocumentType.WORD )
        {
            fileChooser.setFileFilter(new WordFileFilter());
        }
        if ( document.getDocumentType() == DocumentType.PPT )
        {
            fileChooser.setFileFilter(new PPTFileFilter());
        }
        if ( document.getDocumentType() == DocumentType.EXCEL )
        {
            fileChooser.setFileFilter(new ExcelFileFilter());
        }
        fileChooser.setVisible(true);
        boolean isCanceled = fileChooser.isCanceled();
        if ( !isCanceled )
        {
            File file = fileChooser.getSelectedFile();
            saveDocument(file);
        }
        return result;
    }

    public final void publish()
    {
        if ( isReadOnly() )
        {
            JOptionPane.showMessageDialog(null, "El documento es de sólo lectura, por lo cuál no puede ser publicado", TITLE, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if ( OfficeApplication.tryLogin() )
            {
                boolean canbepublished = false;
                if ( isNewDocument() )
                {
                    canbepublished = showSaveDialog(this);
                }
                else
                {
                    canbepublished = true;
                }

                if ( canbepublished )
                {
                    if ( isPublicated() )
                    {
                        try
                        {
                            if ( isModified() )
                            {
                                save();
                            }
                            updateContent();
                        }
                        catch ( WBException e )
                        {
                            JOptionPane.showMessageDialog(null, e.getMessage(), TITLE, JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        PublishResultProducer resultProducer = new PublishResultProducer(this);
                        Class[] clazz;
                        switch ( getDocumentType() )
                        {
                            case WORD:
                                clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class, SelectTypeToShow.class};
                                break;
                            case EXCEL:
                                clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class};
                                break;
                            case PPT:
                                clazz = new Class[]{TitleAndDescription.class, SelectPage.class, PagContenido.class};
                                break;
                            default:
                                clazz = new Class[]{TitleAndDescription.class, SelectPage.class};
                                break;

                        }
                        Wizard wiz = WizardPage.createWizard(TITLE, clazz, resultProducer);
                        wiz.show();
                    }
                }
            }
        }
    }  

    void SaveContentId(int contentId) throws WBException
    {
        this.contentID = contentId;
        HashMap<String, String> values = new HashMap<String, String>();
        values.put(CONTENT_ID_NAME, String.valueOf(contentId));
        saveCustomProperties(values);
    }

    private final boolean saveDocument(File file)
    {
        boolean result = false;
        if ( getDocumentType() == DocumentType.WORD )
        {
            if ( !file.getName().endsWith(getDefaultExtension()) )
            {
                file = new File(file.getPath() + getDefaultExtension());
            }
        }
        if ( file.exists() )
        {
            int resultOption = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿Desea sobre escribir?", TITLE, JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if ( resultOption != JOptionPane.NO_OPTION )
            {
                try
                {
                    save(file);
                    result = true;
                }
                catch ( WBException wbe )
                {
                    JOptionPane.showMessageDialog(null, wbe.getMessage(), TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return result;
    }

    private void changeActive(boolean active) throws WBException
    {
        File zipFile = this.createZipFile();
        getOfficeDocumentProxy().addAttachment(new Attachment(zipFile, zipFile.getName()));
        getOfficeDocumentProxy().setActive(this.contentID, active);
    }

    private boolean isActive() throws WBException
    {
        return getOfficeDocumentProxy().getActive(this.contentID);
    }

    private void changeDescription(String description) throws WBException
    {
        getOfficeDocumentProxy().setDescription(this.contentID, description);
    }

    private void changeTitle(String title) throws WBException
    {
        getOfficeDocumentProxy().setTitle(this.contentID, title);
    }

    private void updateContent() throws WBException
    {
        File zipFile = this.createZipFile();
        getOfficeDocumentProxy().addAttachment(new Attachment(zipFile, zipFile.getName()));
        try
        {
            getOfficeDocumentProxy().updateContent(this.contentID);
        }
        catch(WBException e)
        {
            throw e;
        }
        finally
        {
            if(zipFile!=null && zipFile.exists())
            {
                deleteTemporalDirectory(zipFile.getParentFile());
            }
        }
    }

    public final void addRule()
    {

    }

    public final void addLink()
    {
        DialogAddLink dialog = new DialogAddLink(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}

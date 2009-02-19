/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
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
import org.semanticwb.openoffice.ui.dialogs.DialogContentInformation;
import org.semanticwb.openoffice.ui.dialogs.DialogHistory;
import org.semanticwb.openoffice.ui.dialogs.DialogSaveDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogUpdateContent;
import org.semanticwb.openoffice.ui.wizard.PublishVersion;
import org.semanticwb.openoffice.ui.wizard.SelectCategory;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectTitle;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.openoffice.ui.wizard.ViewProperties;
import org.semanticwb.openoffice.util.ExcelFileFilter;
import org.semanticwb.openoffice.util.PPTFileFilter;
import org.semanticwb.openoffice.util.WordFileFilter;
//import org.semanticwb.xmlrpc.HttpException;
//import org.semanticwb.xmlrpc.XmlRpcException;
import org.semanticwb.xmlrpc.HttpException;
import org.semanticwb.xmlrpc.XmlRpcException;
import static org.semanticwb.openoffice.util.FileUtil.copyFile;

/**
 * An Office documents is an abstraction of a document that can be published
 * @author victor.lorenzana
 */
public abstract class OfficeDocument
{

    private static final String TITLE_SAVE_CONTENT_SITE = "Asistente para guardar contenido";
    public static final String CONTENT_ID_NAME = "contentID";
    public static final String WORKSPACE_ID_NAME = "workspaceID";
    private static final String TITLE_VERIFY = "Verificación de contenido";
    // By default the content is not published
    private String contentID = null;
    private String workspaceID = null;


    static
    {
        Locale.setDefault(new Locale("es"));
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ue)
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

    private final boolean setupDocument()
    {
        boolean setupDocument = false;
        String contentId = this.getCustomProperties().get(CONTENT_ID_NAME);
        String workspace = this.getCustomProperties().get(WORKSPACE_ID_NAME);
        try
        {
            contentID = OfficeApplication.setupDocument(workspace, contentId);
            setupDocument = true;
        }
        catch (XmlRpcException e)
        {
            if (e.getCause() != null && e.getCause() instanceof ConnectException)
            {
                JOptionPane.showMessageDialog(null,
                        "No se puede verificar la existencia del contenido en el sitio, al paracer el sitio al que intenta conectarse no esta disponible.", TITLE_VERIFY, JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        "No se puede verificar la existencia del contenido en el sitio, la causa es:\r\n" + e.getLocalizedMessage(), TITLE_VERIFY, JOptionPane.WARNING_MESSAGE);
            }
            OfficeApplication.logOff();
            ErrorLog.log(e);
        }
        catch (HttpException e)
        {
            if (e.getCode() == 404)
            {
                JOptionPane.showMessageDialog(null,
                        "No se puede verificar la existencia del contenido en el sitio, al paracer el sitio al que intenta conectarse no tiene habilitada la función de publicación de contenidos.", TITLE_VERIFY, JOptionPane.ERROR_MESSAGE);

            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        "No se puede verificar la existencia del contenido en el sitio, la causa es:\r\n" + e.getLocalizedMessage(), TITLE_VERIFY, JOptionPane.ERROR_MESSAGE);

            }
            OfficeApplication.logOff();
            ErrorLog.log(e);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    e.getLocalizedMessage(), TITLE_VERIFY, JOptionPane.ERROR_MESSAGE);
            ErrorLog.log(e);
            OfficeApplication.logOff();
        }
        return setupDocument;
    }

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

    public abstract String getPublicationExtension();

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

    public abstract void insertLink(String url, String text);

    protected static File getFile(URI uri)
    {
        File file = new File(uri.getPath());
        return file;
    }

    public Set<File> getMisssingAttachtments() throws NoHasLocationException
    {
        Set<File> attachments = new HashSet<File>();
        for (File file : this.getAllAttachments())
        {
            if (file.exists())
            {
                attachments.add(file);
            }
        }
        return attachments;
    }

    public Set<File> getNotMissingAttachtments() throws NoHasLocationException
    {
        Set<File> attachments = new HashSet<File>();
        for (File file : this.getAllAttachments())
        {
            if (file.exists())
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
            if (uri.getScheme() == null || uri.getScheme().equalsIgnoreCase("file"))
            {
                if (uri.isAbsolute())
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
        catch (URISyntaxException use)
        {
            ErrorLog.log(use);
        }

        return attachments;
    }

    public final void delete()
    {
        if (OfficeApplication.tryLogin() && setupDocument())
        {
            if (isPublicated())
            {
                int res = JOptionPane.showConfirmDialog(null, "¿Desea borrar el contenido?", "Borrado de contenido", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION)
                {
                    contentID = this.getCustomProperties().get(CONTENT_ID_NAME);
                    String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
                    try
                    {
                        IOpenOfficeDocument doc = OfficeApplication.getOfficeDocumentProxy();
                        doc.delete(repositoryName, contentID);
                        deleteAssociation(false);
                        JOptionPane.showMessageDialog(null, "¡Se ha borrado el contenido!", "Borrado de contenido", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void deleteAssociation()
    {
        deleteAssociation(true);
    }

    public final void deleteAssociation(boolean showMessage)
    {
        try
        {
            HashMap<String, String> properties = new HashMap<String, String>();
            properties.put("Information 4", "");
            properties.put("Information 3", "");
            properties.put("Information 2", "");
            properties.put("Information 1", "");
            saveCustomProperties(properties);
            if (showMessage)
            {
                JOptionPane.showMessageDialog(null, "¡Se ha borrado la asociación de publicación de contenidos!", "Borrado de asociación de contenido", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (WBAlertException wba)
        {
        }
        catch (WBOfficeException wboe)
        {
        }
        catch (WBException wbe)
        {
        }
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

    public final void showDocumentInfo()
    {
        if (OfficeApplication.tryLogin())
        {
            Map<String, String> properties = this.getCustomProperties();
            String contentId = properties.get(CONTENT_ID_NAME);
            String rep = properties.get(WORKSPACE_ID_NAME);
            if (contentId == null || rep == null)
            {
                deleteAssociation(false);
                int resp = JOptionPane.showConfirmDialog(null, "El contenido no ha sido publicado.\r\n¿Desea publicar el contenido?", "Mostrar información del contenido", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION)
                {
                    saveToSite();
                }
            }
            properties = this.getCustomProperties();
            contentId = properties.get(CONTENT_ID_NAME);
            rep = properties.get(WORKSPACE_ID_NAME);
            if (contentId != null && rep != null)
            {
                DialogContentInformation dialog = new DialogContentInformation(new javax.swing.JFrame(), true, contentId, rep, this);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "El contenido no ha sido publicado.", "Mostrar información del contenido", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    final void deleteTemporalDirectory(File dir)
    {
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
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
        String path = this.getLocalPath().getParentFile().getPath() + File.separator + guid;
        path = path.replace("%20", " ");
        File file = new File(path);
        file = this.saveAsHtml(file);
        return file;

    }

    /**
     * 
     * @return
     */
    private String createGuid()
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
        for (File attatchment : this.getNotMissingAttachtments())
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
            String guid = createGuid();
            File fileHtml = saveHtmlPrepareAndGetFiles(guid);
            tempotalDir = fileHtml.getParentFile();
            String path = tempotalDir.getParentFile().getPath() + File.separatorChar + guid + ".zip";
            path = path.replace("%20", " ");
            tempotalZipFile = new File(path);

            FileOutputStream fout = new FileOutputStream(tempotalZipFile);
            ZipOutputStream zipFile = new ZipOutputStream(fout);
            BufferedInputStream origin = null;
            int BUFFER_SIZE = 2048;
            byte[] data = new byte[BUFFER_SIZE];
            for (File file : getFiles(tempotalDir))
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
        catch (FileNotFoundException fnfe)
        {
            deleteTemporalDirectory(tempotalDir);
            throw new WBException("No se puede crear el archivo zip\r\n" + fnfe.getLocalizedMessage(), fnfe);

        }
        catch (IOException ioe)
        {
            deleteTemporalDirectory(tempotalDir);
            throw new WBException("No se puede crear el archivo zip\r\n" + ioe.getLocalizedMessage(), ioe);

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
        for (File file : dir.listFiles())
        {
            if (file.isFile())
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
        String id = this.getCustomProperties().get(OfficeDocument.CONTENT_ID_NAME);
        String rep = this.getCustomProperties().get(OfficeDocument.WORKSPACE_ID_NAME);
        if (id != null && rep != null && !rep.isEmpty() && !id.isEmpty())
        {
            this.contentID = id;
            this.workspaceID = rep;
            isPublicated = true;
        }
        else
        {
            isPublicated = false;
        }
        return isPublicated;
    }

    private final boolean showSaveDialog(OfficeDocument document)
    {
        boolean result = false;
        DialogSaveDocument fileChooser = new DialogSaveDocument(new JFrame(), true);
        if (document.getDocumentType() == DocumentType.WORD)
        {
            fileChooser.setFileFilter(new WordFileFilter());
        }
        if (document.getDocumentType() == DocumentType.PPT)
        {
            fileChooser.setFileFilter(new PPTFileFilter());
        }
        if (document.getDocumentType() == DocumentType.EXCEL)
        {
            fileChooser.setFileFilter(new ExcelFileFilter());
        }
        fileChooser.setVisible(true);
        boolean isCanceled = fileChooser.isCanceled();
        if (!isCanceled)
        {
            File file = fileChooser.getSelectedFile();
            result = saveDocument(file);
        }
        return result;
    }

    public final void publish(String title, String description)
    {
        if (isPublicated())
        {
            contentID = this.getCustomProperties().get(CONTENT_ID_NAME);
            String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
            PublishContentToWebPageResultProducer resultProducer = new PublishContentToWebPageResultProducer(contentID, repositoryName, title, description);
            WizardPage[] clazz = new WizardPage[]
            {
                new SelectPage(), new PublishVersion(contentID, repositoryName)
            };
            Wizard wiz = WizardPage.createWizard("Asistente de publicación de contenido en página web", clazz, resultProducer);
            wiz.show();
        }
    }

    public final void publish()
    {
        if (isPublicated())
        {
            contentID = this.getCustomProperties().get(CONTENT_ID_NAME);
            String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
            PublishContentToWebPageResultProducer resultProducer = new PublishContentToWebPageResultProducer(contentID, repositoryName);
            WizardPage[] clazz = new WizardPage[]
            {
                new TitleAndDescription(false), new SelectPage(), new PublishVersion(contentID, repositoryName), new ViewProperties(repositoryName, contentID)
            };
            Wizard wiz = WizardPage.createWizard("Asistente de publicación de contenido en página web", clazz, resultProducer);
            wiz.show();
        }
    }

    public final void saveToSite()
    {
        if (isReadOnly())
        {
            JOptionPane.showMessageDialog(null, "El documento es de sólo lectura, por lo cuál no puede ser publicado", TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if (OfficeApplication.tryLogin() && setupDocument())
            {
                boolean canbepublished = false;
                if (isNewDocument())
                {
                    canbepublished = showSaveDialog(this);
                }
                else
                {
                    canbepublished = true;
                }
                if (canbepublished)
                {
                    try
                    {
                        if (isPublicated() && OfficeApplication.getOfficeDocumentProxy().exists(this.workspaceID, this.contentID))
                        {
                            try
                            {
                                if (isModified())
                                {
                                    save();
                                }
                                try
                                {
                                    updateContent();
                                }
                                catch (Exception e)
                                {
                                    JOptionPane.showMessageDialog(null, "No se puede actualizar el contenido la causa es: " + e.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
                                    ErrorLog.log(e);
                                }
                            }
                            catch (WBException e)
                            {
                                JOptionPane.showMessageDialog(null, e.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
                                ErrorLog.log(e);
                            }
                        }
                        else
                        {
                            PublishResultProducer resultProducer = new PublishResultProducer(this);
                            Class[] clazz;
                            switch (getDocumentType())
                            {
                                case WORD:
                                    clazz = new Class[]
                                            {
                                                //TitleAndDescription.class, SelectCategory.class, PagContenido.class, SelectTypeToShow.class
                                                SelectCategory.class, TitleAndDescription.class
                                            };
                                    break;
                                case EXCEL:
                                    clazz = new Class[]
                                            {
                                                SelectCategory.class, TitleAndDescription.class
                                            };
                                    break;
                                case PPT:
                                    clazz = new Class[]
                                            {
                                                SelectCategory.class, TitleAndDescription.class
                                            };
                                    break;
                                default:
                                    clazz = new Class[]
                                            {
                                                TitleAndDescription.class, SelectCategory.class
                                            };
                                    break;

                            }
                            Wizard wiz = WizardPage.createWizard(TITLE_SAVE_CONTENT_SITE, clazz, resultProducer);
                            wiz.show();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void SaveContentId(String contentId, String workspaceId) throws WBException
    {
        this.contentID = contentId;
        HashMap<String, String> values = new HashMap<String, String>();
        values.put(CONTENT_ID_NAME, contentId);
        values.put(WORKSPACE_ID_NAME, workspaceId);
        saveCustomProperties(values);
    }

    private final boolean saveDocument(File file)
    {
        boolean result = false;
        if (getDocumentType() == DocumentType.WORD)
        {
            if (!file.getName().endsWith(getDefaultExtension()))
            {
                file = new File(file.getPath() + getDefaultExtension());
            }
        }
        if (file.exists())
        {
            int resultOption = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿Desea sobre escribir?", TITLE_SAVE_CONTENT_SITE, JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (resultOption != JOptionPane.NO_OPTION)
            {
                try
                {
                    save(file);
                    result = true;
                }
                catch (WBException wbe)
                {
                    JOptionPane.showMessageDialog(null, wbe.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
        {
            try
            {
                save(file);
                result = true;
            }
            catch (WBException wbe)
            {
                JOptionPane.showMessageDialog(null, wbe.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    private void updateContent() throws Exception
    {
        String workspace = this.getCustomProperties().get(WORKSPACE_ID_NAME);
        DialogUpdateContent summary = new DialogUpdateContent(new Frame(), true, workspace, contentID, this);
        summary.setLocationRelativeTo(null);
        summary.setAlwaysOnTop(true);
        summary.setVisible(true);
    }

    public final void addRule()
    {
    }

    public final void insertLink()
    {
        AddLinkProducer resultProducer = new AddLinkProducer(this);
        WizardPage[] clazz = new WizardPage[]
        {
            new SelectPage(), new SelectTitle()
        };
        Wizard wiz = WizardPage.createWizard("Asistente de inserción de liga de página", clazz, resultProducer);
        wiz.show();
    }
}

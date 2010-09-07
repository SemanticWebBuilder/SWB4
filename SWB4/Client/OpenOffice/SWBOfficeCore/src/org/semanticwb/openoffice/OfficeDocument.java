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
package org.semanticwb.openoffice;

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
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.office.interfaces.ContentInfo;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogContentInformation;
import org.semanticwb.openoffice.ui.dialogs.DialogDocumentDetail;
import org.semanticwb.openoffice.ui.dialogs.DialogDocumentsAuthorize;
import org.semanticwb.openoffice.ui.dialogs.DialogHistory;
import org.semanticwb.openoffice.ui.dialogs.DialogInsertLinkDoc;
import org.semanticwb.openoffice.ui.dialogs.DialogSaveDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogUpdateContent;
import org.semanticwb.openoffice.ui.wizard.ContentProperties;
import org.semanticwb.openoffice.ui.wizard.PublishPage;
import org.semanticwb.openoffice.ui.wizard.PublishVersion;
import org.semanticwb.openoffice.ui.wizard.SelectCategory;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectSiteInsertLink;
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

    public static final String DOT = ".";
    private static final String CONTENT_KEY = "content";
    private static final String DFILEENCODING = "Dfile.encoding";
    private static final String EMPTY_STRING = "";
    private static final String FILE = "file";
    private static final String FILE_SCHEMA = FILE + "://";
    private static final String TITLE_SAVE_CONTENT_SITE = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_PARA_GUARDAR_CONTENIDO");
    public static final String CONTENT_ID_NAME = "contentID";
    public static final String WORKSPACE_ID_NAME = "workspaceID";
    private static final String TITLE_VERIFY = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("VERIFICACIÓN_DE_CONTENIDO");
    private static final String NL = "\r\n";
    private static final String TOPICID_KEY = "topicid";
    private static final String TOPICMAP_KEY = "topicmap";
    private static final String UTF8 = "utf-8";
    private static final String ZIP_EXTENSION = ".zip";
    // By default the content is not published
    private String contentID = null;
    private String workspaceID = null;

    static
    {
        System.setProperty("wizard.sidebar.image", "org/semanticwb/openoffice/ui/icons/sidebar.png");
        System.setProperty("WizardDisplayer.default", "org.semanticwb.openoffice.util.WBWizardDisplayerImpl");
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
                        java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_VERIFICAR_LA_EXISTENCIA_DEL_CONTENIDO_EN_EL_SITIO,_AL_PARACER_EL_SITIO_AL_QUE_INTENTA_CONECTARSE_NO_ESTA_DISPONIBLE."), TITLE_VERIFY, JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_VERIFICAR_LA_EXISTENCIA_DEL_CONTENIDO_EN_EL_SITIO,_LA_CAUSA_ES:") + NL + e.getLocalizedMessage(), TITLE_VERIFY, JOptionPane.WARNING_MESSAGE);
            }
            OfficeApplication.logOff();
            ErrorLog.log(e);
        }
        catch (HttpException e)
        {
            if (e.getCode() == 404)
            {
                JOptionPane.showMessageDialog(null,
                        java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_VERIFICAR_LA_EXISTENCIA_DEL_CONTENIDO_EN_EL_SITIO,_AL_PARACER_EL_SITIO_AL_QUE_INTENTA_CONECTARSE_NO_TIENE_HABILITADA_LA_FUNCIÓN_DE_PUBLICACIÓN_DE_CONTENIDOS."), TITLE_VERIFY, JOptionPane.ERROR_MESSAGE);

            }
            else
            {
                JOptionPane.showMessageDialog(null,
                        java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_VERIFICAR_LA_EXISTENCIA_DEL_CONTENIDO_EN_EL_SITIO,_LA_CAUSA_ES:") + NL + e.getLocalizedMessage(), TITLE_VERIFY, JOptionPane.ERROR_MESSAGE);

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

    public abstract String getSelectedText();
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

    public abstract String[] getLinks();

    public abstract int getCountImages();

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
            if (!file.exists())
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
            if (uri.getScheme() == null || uri.getScheme().equalsIgnoreCase(FILE))
            {
                if (uri.isAbsolute())
                {
                    attachments.add(getFile(uri));
                }
                else
                {
                    URI base = new URI(FILE_SCHEMA + this.getLocalPath().getPath().replace('\\', '/'));
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
                contentID = this.getCustomProperties().get(CONTENT_ID_NAME);                
                String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
                boolean canModify=false;
                try
                {
                    canModify=OfficeApplication.getOfficeDocumentProxy().canModify(repositoryName,contentID);
                }
                catch(Exception e)
                {
                    e.printStackTrace();                    
                }
                if(!canModify)
                {
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("No_tiene_permisos_para_borrar_el_contenido"),java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("BORRADO_DE_CONTENIDO"),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¿DESEA_BORRAR_EL_CONTENIDO?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("BORRADO_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.YES_OPTION)
                {
                    
                    try
                    {
                        IOpenOfficeDocument doc = OfficeApplication.getOfficeDocumentProxy();
                        doc.delete(repositoryName, contentID);
                        deleteAssociation(false);
                        JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¡SE_HA_BORRADO_EL_CONTENIDO!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("BORRADO_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
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
            properties.put(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("INFORMATION_4"), EMPTY_STRING);
            properties.put(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("INFORMATION_3"), EMPTY_STRING);
            properties.put(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("INFORMATION_2"), EMPTY_STRING);
            properties.put(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("INFORMATION_1"), EMPTY_STRING);
            saveCustomProperties(properties);
            if (showMessage)
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¡SE_HA_BORRADO_LA_ASOCIACIÓN_DE_PUBLICACIÓN_DE_CONTENIDOS!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("BORRADO_DE_ASOCIACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
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
        if (OfficeApplication.tryLogin())
        {
            DialogHistory dialog = new DialogHistory(new javax.swing.JFrame(), true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public static final void showContentsInFlow()
    {
        if (OfficeApplication.tryLogin())
        {
            DialogDocumentsAuthorize dialogDocumentsAuthorize = new DialogDocumentsAuthorize();
            dialogDocumentsAuthorize.setVisible(true);
        }
    }

    public final void showDocumentDetail()
    {
        DialogDocumentDetail dlg = new DialogDocumentDetail(this);
        dlg.setVisible(true);
    }

    public final void showDocumentInfo()
    {
        if (OfficeApplication.tryLogin())
        {
            if (isOldVersion())
            {
                int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_ESTA_PUBLICADO_EN_UNA_VERSIÓN_ANTERIOR,_¿DESEA_QUE_SE_VERIFIQUE_SI_EXISTE_EN_EL_SITIO_ACTUAL?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.YES_OPTION)
                {
                    String contentid = this.getCustomProperties().get(CONTENT_KEY);
                    String topicid = this.getCustomProperties().get(TOPICID_KEY);
                    String topicmap = this.getCustomProperties().get(TOPICMAP_KEY);
                    try
                    {
                        ContentInfo info = OfficeApplication.getOfficeDocumentProxy().existContentOldVersion(contentid, topicmap, topicid);
                        if (info != null)
                        {
                            res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_SE_ENCUENTRA_EN_EL_SITIO,_¿DESEA_CONVERTIR_EL_DOCUMENTO_A_VERSIÓN_4?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (res == JOptionPane.YES_OPTION)
                            {
                                cleanContentProperties();
                                saveContentId(info.id, info.respositoryName);
                                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¡EL_DOCUMENTO_SE_HA_CONVERTIDO_A_VERSIÓN_4,_PUEDE_CONTINUAR!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.QUESTION_MESSAGE);
                            }
                            if (res == JOptionPane.CANCEL_OPTION)
                            {
                                return;
                            }
                        }
                        else
                        {
                            res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_NO_EXISTE_EN_EL_SITIO_ACTUAL,_POR_LO_CUÁL_NO_SE_PUEDE_CONVERTIR,_¿DESEA_CONTINUAR?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (res == JOptionPane.NO_OPTION)
                            {
                                return;
                            }

                        }
                    }
                    catch (Exception e)
                    {
                        ErrorLog.log(e);
                    }
                }
                if (res == JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                saveToSite();
                return;
            }
            Map<String, String> properties = this.getCustomProperties();
            String contentId = properties.get(CONTENT_ID_NAME);
            String rep = properties.get(WORKSPACE_ID_NAME);
            if (contentId == null || rep == null)
            {

                deleteAssociation(false);
                int resp = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_CONTENIDO_NO_HA_SIDO_PUBLICADO.") + NL + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¿DESEA_PUBLICAR_EL_CONTENIDO?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
                DialogContentInformation dialog = new DialogContentInformation(contentId, rep, this);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_CONTENIDO_NO_HA_SIDO_PUBLICADO."), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
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
            String path = tempotalDir.getParentFile().getPath() + File.separatorChar + guid + ZIP_EXTENSION;
            String encode = System.getenv(DFILEENCODING);
            if (encode == null || encode.equals(EMPTY_STRING))
            {
                encode = UTF8;
            }
            path = java.net.URLDecoder.decode(path, encode);
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
            throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_CREAR_EL_ARCHIVO_ZIP") + NL + EMPTY_STRING + fnfe.getLocalizedMessage(), fnfe);

        }
        catch (IOException ioe)
        {
            deleteTemporalDirectory(tempotalDir);
            throw new WBException(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_CREAR_EL_ARCHIVO_ZIP") + NL + EMPTY_STRING + ioe.getLocalizedMessage(), ioe);

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
        DialogSaveDocument fileChooser = new DialogSaveDocument();
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

    public final void publish(String title, String description, String siteid)
    {
        if (isPublicated())
        {
            if (OfficeApplication.tryLogin())
            {
                contentID = this.getCustomProperties().get(CONTENT_ID_NAME);
                String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
                try
                {
                    if(!OfficeApplication.getOfficeDocumentProxy().exists(repositoryName, contentID))
                    {
                        return;
                    }
                    WebSiteInfo[] sites = OfficeApplication.getOfficeApplicationProxy().getSites();
                    if (sites == null || sites.length <= 0)
                    {
                        JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("no_hay_sitios"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_PUBLICACIÓN_DE_CONTENIDO_EN_PÁGINA_WEB"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                PublishContentToWebPageResultProducer resultProducer = new PublishContentToWebPageResultProducer(contentID, repositoryName, title, description);
                WizardPage[] clazz = new WizardPage[]
                {
                    new PublishPage(siteid, this), new PublishVersion(contentID, repositoryName), new ViewProperties(repositoryName, contentID)
                };
                Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_PUBLICACIÓN_DE_CONTENIDO_EN_PÁGINA_WEB"), clazz, resultProducer);
                wiz.show();
            }
        }
    }

    public final void publish()
    {
        if (isPublicated())
        {
            contentID = this.getCustomProperties().get(CONTENT_ID_NAME);
            String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
            String siteid = null;
            try
            {
                if(!OfficeApplication.getOfficeDocumentProxy().exists(repositoryName, contentID))
                {
                    return;
                }
                for (RepositoryInfo orepository : OfficeApplication.getOfficeApplicationProxy().getRepositories())
                {
                    if (orepository.name != null && orepository.name.equals(repositoryName))
                    {
                        siteid = orepository.siteInfo.id;
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                WebSiteInfo[] sites = OfficeApplication.getOfficeApplicationProxy().getSites();
                if (sites == null || sites.length <= 0)
                {
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("no_hay_sitios"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_PUBLICACIÓN_DE_CONTENIDO_EN_PÁGINA_WEB"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            PublishContentToWebPageResultProducer resultProducer = new PublishContentToWebPageResultProducer(contentID, repositoryName);
            WizardPage[] clazz = new WizardPage[]
            {
                new TitleAndDescription(false), new PublishPage(siteid, this), new PublishVersion(contentID, repositoryName), new ViewProperties(repositoryName, contentID)
            };
            Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_PUBLICACIÓN_DE_CONTENIDO_EN_PÁGINA_WEB"), clazz, resultProducer);
            wiz.show();
        }
    }

    private void cleanContentProperties()
    {
        this.deleteAssociation(false);
    }

    private boolean validateFiles()
    {
        String[] choices = new String[3];
        choices[0] = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("YES");
        choices[1] = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("YES_TO_ALL");
        choices[2] = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO");
        try
        {
            Set<File> files = this.getMisssingAttachtments();
            if (files.size() > 0)
            {
                for (File file : files)
                {
                    int selection = JOptionPane.showOptionDialog(
                            null,
                            "El archivo incrustado en el contenido con ruta " + file.getCanonicalPath() + " no se encontró,¿Desea continuar?", TITLE_SAVE_CONTENT_SITE,
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            choices,
                            choices[0]);
                    switch (selection)
                    {
                        case 0: //Si
                            break;
                        case 1: //Si All
                            return true;
                        case 2: //No
                            return false;
                    }
                }
                return true;
            }
            else
            {
                return true;
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error al validar archivos incrustados" + NL + "Detalle: " + e.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.OK_OPTION | JOptionPane.ERROR);
            ErrorLog.log(e);
            return false;
        }


    }

    public final void saveToSite()
    {
        if (isReadOnly())
        {
            JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_ES_DE_SÓLO_LECTURA,_POR_LO_CUÁL_NO_PUEDE_SER_PUBLICADO"), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if (OfficeApplication.tryLogin() && setupDocument())
            {
                if (isOldVersion())
                {
                    int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_ESTA_PUBLICADO_EN_UNA_VERSIÓN_ANTERIOR,_¿DESEA_QUE_SE_VERIFIQUE_SI_EXISTE_EN_EL_SITIO_ACTUAL?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION)
                    {
                        String contentid = this.getCustomProperties().get(CONTENT_KEY);
                        String topicid = this.getCustomProperties().get(TOPICID_KEY);
                        String topicmap = this.getCustomProperties().get(TOPICMAP_KEY);
                        try
                        {
                            ContentInfo info = OfficeApplication.getOfficeDocumentProxy().existContentOldVersion(contentid, topicmap, topicid);
                            if (info != null)
                            {
                                res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_SE_ENCUENTRA_EN_EL_SITIO,_¿DESEA_CONVERTIR_EL_DOCUMENTO_A_VERSIÓN_4?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (res == JOptionPane.YES_OPTION)
                                {
                                    cleanContentProperties();
                                    saveContentId(info.id, info.respositoryName);
                                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¡EL_DOCUMENTO_SE_HA_CONVERTIDO_A_VERSIÓN_4,_PUEDE_CONTINUAR!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.QUESTION_MESSAGE);
                                }
                                if (res == JOptionPane.CANCEL_OPTION)
                                {
                                    return;
                                }
                            }
                            else
                            {
                                res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_DOCUMENTO_NO_EXISTE_EN_EL_SITIO_ACTUAL,_POR_LO_CUÁL_NO_SE_PUEDE_CONVERTIR,_¿DESEA_CONTINUAR?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (res == JOptionPane.NO_OPTION)
                                {
                                    return;
                                }

                            }
                        }
                        catch (Exception e)
                        {
                            ErrorLog.log(e);
                        }
                    }
                    if (res == JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                }
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
                        if (!validaNombre(this.getLocalPath()))
                        {
                            return;
                        }
                    }
                    catch (Exception e)
                    {
                    }

                    if (!validateFiles())
                    {
                        return;
                    }
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
                                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("NO_SE_PUEDE_ACTUALIZAR_EL_CONTENIDO_LA_CAUSA_ES:_") + " " + e.getMessage(), TITLE_SAVE_CONTENT_SITE, JOptionPane.ERROR_MESSAGE);
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
                            WizardPage[] pages = new WizardPage[]
                            {
                                new SelectCategory(false), new TitleAndDescription(true), new ContentProperties()
                            };
                            Wizard wiz = WizardPage.createWizard(TITLE_SAVE_CONTENT_SITE, pages, resultProducer);

                            
                            //Wizard wiz = WizardPage.createWizard(TITLE_SAVE_CONTENT_SITE, pages, resultProducer);
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

    public static boolean validaNombre(File file)
    {
        String name = file.getName();
        int pos = name.indexOf(DOT);
        if (pos != -1)
        {
            name = file.getName().substring(0, pos);
        }
        try
        {
            name = java.net.URLDecoder.decode(name, UTF8);
        }
        catch (Exception e)
        {
        }
        if (name.length() > 40)
        {
            JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_NOMBRE_DEL_ARCHIVO_ES_MAYOR_A_40_CARACTERES"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("VALIDACIÓN_DE_NOMBRE_DE_ARCHIVO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return false;
        }
        char[] letras = name.toCharArray();
        for (int i = 0; i < letras.length; i++)
        {
            char letra = letras[i];
            if (Character.isWhitespace(letra))
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_ESPACIOS"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("VALIDACIÓN_DE_NOMBRE_DE_ARCHIVO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else if (!(Character.isDigit(letra) || Character.isLetter(letra)))
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_CARACTERES_NO_VÁLIDOS:") + letra, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("VALIDACIÓN_DE_NOMBRE_DE_ARCHIVO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else if (letra > 123)
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_CARACTERES_NO_VÁLIDOS:") + letra, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("VALIDACIÓN_DE_NOMBRE_DE_ARCHIVO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    void saveContentId(String contentId, String workspaceId) throws WBException
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
            int resultOption = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("EL_ARCHIVO_YA_EXISTE,_¿DESEA_SOBRE_ESCRIBIR?"), TITLE_SAVE_CONTENT_SITE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
        if (workspace != null && OfficeApplication.tryLogin())
        {
            boolean canModify=false;
            try
            {
                String repositoryName = this.getCustomProperties().get(WORKSPACE_ID_NAME);
                canModify=OfficeApplication.getOfficeDocumentProxy().canModify(repositoryName,contentID);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(!canModify)
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("No_tiene_permisos_para_borrar_el_contenido"),java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("BORRADO_DE_CONTENIDO"),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return;
            }
            DialogUpdateContent summary = new DialogUpdateContent(workspace, contentID, this);
            summary.setVisible(true);
        }
    }

    public final void addRule()
    {
    }

    public final void insertLink()
    {
        if (this.isReadOnly())
        {
            JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("¡EL_DOCUMENTO_ES_DE_SÓLO_LECTURA!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("INSERTAR_LIGA_A_PÁGINA"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (OfficeApplication.tryLogin())
        {
            AddLinkProducer resultProducer = new AddLinkProducer(this);
            String selectedText=this.getSelectedText();
            if(selectedText==null)
            {
                WizardPage[] clazz = new WizardPage[]
                {
                    new SelectSiteInsertLink(), new SelectTitle()
                };
                Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_INSERCIÓN_DE_LIGA_DE_PÁGINA"), clazz, resultProducer);
                wiz.show();
            }
            else
            {
                WizardPage[] clazz = new WizardPage[]
                {
                    new SelectSiteInsertLink(), new SelectTitle(selectedText)
                };
                Wizard wiz = WizardPage.createWizard(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/OfficeDocument").getString("ASISTENTE_DE_INSERCIÓN_DE_LIGA_DE_PÁGINA"), clazz, resultProducer);
                wiz.show();
            }
            
        }
    }

    private boolean isOldVersion()
    {
        Map<String, String> properties = this.getCustomProperties();
        if (properties.containsKey(CONTENT_KEY) && properties.containsKey(TOPICID_KEY) && properties.containsKey(TOPICMAP_KEY))
        {
            String contentid = this.getCustomProperties().get(CONTENT_KEY);
            String topicid = this.getCustomProperties().get(TOPICID_KEY);
            String topicmap = this.getCustomProperties().get(TOPICMAP_KEY);
            if (contentid == null || topicmap == null || topicid == null)
            {
                return false;
            }
            if (contentid.equals(EMPTY_STRING) || topicmap.equals(EMPTY_STRING) || topicid.equals(EMPTY_STRING))
            {
                return false;
            }
            return true;
        }
        return false;
    }
    public void insertLinkDoc()
    {
        if (OfficeApplication.tryLogin())
        {
            DialogInsertLinkDoc dialog=new DialogInsertLinkDoc(this);
            dialog.setVisible(true);
        }
    }
}

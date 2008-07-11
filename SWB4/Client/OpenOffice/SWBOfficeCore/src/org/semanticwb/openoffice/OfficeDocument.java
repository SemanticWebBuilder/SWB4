/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.semanticwb.openoffice.util.FileUtil.copyFile;

/**
 * An Office documents is an abstraction of a document that can be published
 * @author victor.lorenzana
 */
public abstract class OfficeDocument
{

    //private final static int BUFFER_SIZE = 1024;

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
            if (uri.getScheme()==null || uri.getScheme().equalsIgnoreCase("file"))
            {
                if (uri.isAbsolute())
                {
                    attachments.add(getFile(uri));
                }
                else
                {
                    URI base = new URI(this.getLocalPath().getPath());
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

    public final void deleteTemporalDirectory(File dir)
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
        File file = new File(this.getLocalPath().getParentFile().getPath() + File.separator + guid);
        file = this.saveAsHtml(file);
        return file;

    }

    public String getGuid()
    {
        String guid = java.util.UUID.randomUUID().toString().replace('-', '_');
        return guid;
    }

    public final File saveHtmlPrepareAndGetFiles(String guid) throws WBException
    {
        File file = this.saveOnGuidDirectoryAsHtml(guid);
        for (File attatchment : this.getNotMissingAttachtments())
        {
            copyFile(attatchment, file.getParentFile());
        }
        this.prepareHtmlFileToSend(file);
        return file;
    }

    


    /*public byte[] getZipFile() throws WBException
    {
    File tempotalFile = null;
    File tempotalZipFile = null;
    try
    {
    String guid = getGuid();
    List<File> files = saveHtmlPrepareAndGetFiles(guid);
    tempotalFile = new File(this.getLocalPath().getParentFile().getPath() + File.separator + guid);
    tempotalZipFile = new File(this.getLocalPath().getParentFile().getPath() + File.separator + guid + ".zip");
    FileOutputStream fout = new FileOutputStream(tempotalZipFile);
    ZipOutputStream zipFile = new ZipOutputStream(fout);
    BufferedInputStream origin = null;
    byte[] data = new byte[BUFFER_SIZE];
    for (File file : files)
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
    FileInputStream fin = new FileInputStream(tempotalZipFile);
    byte[] zip = new byte[(int) tempotalZipFile.length()];
    fin.read(zip);
    fin.close();
    return zip;
    }
    catch (FileNotFoundException fnfe)
    {
    throw new WBException("No se puede crear el archivo zip", fnfe);
    }
    catch (IOException ioe)
    {
    throw new WBException("No se puede crear el archivo zip", ioe);
    }
    finally
    {
    if (tempotalZipFile != null)
    {
    tempotalZipFile.delete();
    }
    if (tempotalFile != null)
    {
    this.deleteTemporalDirectory(tempotalFile);
    }
    }
    }*/
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
        return false;
    }
}

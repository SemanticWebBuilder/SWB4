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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeDocument
{

    private final static int BUFFER_SIZE = 1024;

    public abstract String getApplicationVersion();

    public abstract boolean isNewDocument();

    public abstract boolean isReadOnly();

    public abstract boolean isModified();

    public abstract File getLocalPath() throws WBException;

    public abstract List<File> getAttachtments();

    public abstract Map<String, String> getCustomProperties() throws WBException;

    public abstract void saveCustomProperties(Map<String, String> properties) throws WBException;

    public abstract File saveAsHtml(File dir) throws WBException;

    public abstract File saveAs(File dir, SaveDocumentFormat format) throws WBException;

    public abstract void save() throws WBException;

    public abstract void save(File file) throws WBException;

    public abstract String getDefaultExtension();

    public abstract DocumentType getDocumentType();

    public abstract void prepareHtmlFileToSend(File htmlFile);

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
        File dir = new File(this.getLocalPath().getParentFile().getPath() + File.separator + guid);
        return this.saveAsHtml(dir);
    }

    public String getGuid()
    {
        String guid = java.util.UUID.randomUUID().toString().replace('-', '_');
        return guid;
    }

    public final List<File> saveHtmlPrepareAndGetFiles(String guid) throws WBException
    {
        File file = this.saveOnGuidDirectoryAsHtml(guid);
        this.prepareHtmlFileToSend(file);
        return this.getFiles(file.getParentFile());

    }

    public byte[] getZipFile() throws WBException
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

    }

    private final List<File> getFiles(File dir)
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

    public final int getVersion()
    {
        return 0;
    }

    public final int getLastVersion()
    {
        return 0;
    }

    public final boolean hasbeenPublicated()
    {
        return false;
    }

    public final void refreshInformation()
    {

    }
}

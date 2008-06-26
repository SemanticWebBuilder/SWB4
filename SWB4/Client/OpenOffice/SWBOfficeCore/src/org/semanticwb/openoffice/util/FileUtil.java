/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.semanticwb.openoffice.ErrorLog;

/**
 *
 * @author victor.lorenzana
 */
public final class FileUtil
{

    private static final String SCHEMA_FILE = "file:///";
    private static byte[] buffer = new byte[2048];

    private FileUtil()
    {

    }

    public static File getFileFromURL(String path)
    {
        if (path.startsWith(SCHEMA_FILE))
        {
            path = path.substring(8);
        }
        if(path.indexOf('\\')!=-1)
        {
            path=path.replace('\\', '/');
        }
        return new File(path);
    }

    public static String getPathURL(File file)
    {
        String path=file.getAbsolutePath();
        if(File.separatorChar!='/')
        {
            path=path.replace(File.separatorChar, '/');
        }
        String url = SCHEMA_FILE + path;
        return url;
    }

    public static String getExtension(File file)
    {
        String extension = null;
        int index = file.getName().lastIndexOf(".");
        if (index != -1)
        {
            extension = file.getName().substring(index);
        }
        return extension;
    }

    public static String loadFileAsString(File file)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            FileInputStream in = new FileInputStream(file);
            int read = in.read(buffer);
            while (read != -1)
            {
                builder.append(new String(buffer, 0, read, "UTF-8"));
                read = in.read(buffer);
            }
            in.close();
        }
        catch (Exception fnfe)
        {
            ErrorLog.log(fnfe);
        }
        return builder.toString();
    }

    public static String loadResourceAsString(Class clazz, String resource)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            InputStream in = clazz.getResourceAsStream(resource);
            int read = in.read(buffer);
            while (read != -1)
            {
                builder.append(new String(buffer, 0, read, "UTF-8"));
                read = in.read(buffer);
            }
            in.close();
        }
        catch (IOException ioe)
        {
            ErrorLog.log(ioe);
        }
        return builder.toString();
    }

    public static void saveContent(Class clazz, String resource, File file)
    {
        InputStream in = clazz.getResourceAsStream(resource);
        saveContent(in, file);
    }

    public static void saveContent(StringBuilder content, File file)
    {
        saveContent(content.toString(), file);
    }

    public static void saveContent(String content, File file)
    {
        saveContent(content.getBytes(), file);
    }

    public static void copyFile(File attachment, File dir)
    {
        File newFile = new File(dir.getPath() + "/" + attachment.getName());
        if (!newFile.exists())
        {
            try
            {
                FileInputStream in = new FileInputStream(attachment);
                FileOutputStream out = new FileOutputStream(newFile);
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                in.close();
                out.close();
            }
            catch (Exception ex)
            {
                ErrorLog.log(ex);
            }
        }
    }

    public static void saveContent(File target, File dest)
    {
        try
        {
            FileInputStream in = new FileInputStream(target);
            saveContent(in, dest);
        }
        catch (FileNotFoundException fnfe)
        {
            ErrorLog.log(fnfe);
        }
    }

    public static void saveContent(InputStream in, File file)
    {
        try
        {

            FileOutputStream out = new FileOutputStream(file);
            int read = in.read(buffer);
            while (read != -1)
            {
                out.write(buffer);
                read = in.read(buffer);
            }
            in.close();
            out.close();
        }
        catch (Exception ex)
        {
            ErrorLog.log(ex);
        }
    }

    public static void saveContent(byte[] content, File file)
    {
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            out.write(content);
            out.close();
        }
        catch (Exception ex)
        {
            ErrorLog.log(ex);
        }
    }
}

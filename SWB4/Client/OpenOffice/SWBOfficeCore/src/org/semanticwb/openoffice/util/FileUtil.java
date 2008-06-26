/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.semanticwb.openoffice.ErrorLog;

/**
 *
 * @author victor.lorenzana
 */
public final class FileUtil
{
    private FileUtil()
    {
        
    }
    public static void saveContent(StringBuilder content, File file)
    {
        saveContent(content.toString(), file);
    }

    public static void saveContent(String content, File file)
    {
        saveContent(content.getBytes(), file);
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
            byte[] buffer = new byte[2048];
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

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
                ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
            ErrorLog.log(ex);
        }
    }
}

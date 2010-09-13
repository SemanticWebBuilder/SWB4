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
 

package org.semanticwb.util;

import java.io.*;
import java.util.zip.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class JarFile.
 */
public class JarFile
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(JarFile.class);

    /** The path. */
    private String path;

    /** The exists. */
    private boolean exists;
    
    /** The is dir. */
    private boolean isDir;
    
    /** The length. */
    private long length;
    
    /** The last modified. */
    private long lastModified;
    
    /** The in. */
    //private InputStream in=null;
    
    /** The f. */
    private File f=null;
    
    /** The zip path. */
    private String zipPath=null;

    /** The zip path. */
    private byte[] cache=null;

    /**
     * Instantiates a new jar file.
     * 
     * @param path the path
     */
    public JarFile(String path)
    {
        this.setPath(path);
    }    

    /**
     * Instantiates a new jar file.
     * 
     * @param f the f
     * @param zipPath the zip path
     */
    public JarFile(ZipEntry f, String zipPath)
    {
        this.zipPath=zipPath;
        this.path = "/"+f.getName();
        exists=true;
        if(exists)
        {
            isDir=f.isDirectory();
            if(!isDir)
            {
                length=f.getSize();
                lastModified=f.getTime();
            }
            loadCache();
        }else
        {

        }            
    }

    /**
     * Load cache.
     */
    private void loadCache()
    {
        try
        {
            InputStream in=getInputStream();
            ByteArrayOutputStream out=new ByteArrayOutputStream((int)length);
            SWBUtils.IO.copyStream(in, out);
            cache=out.toByteArray();
        }catch(Exception e){log.error(e);}
    }

    /**
     * Gets the input stream.
     * 
     * @return the input stream
     */
    public InputStream getInputStream()
    {
        try
        {
            if(zipPath!=null)
            {
                return getClass().getResourceAsStream(path);
                  //FileInputStream fis=new FileInputStream(zipPath);
                  //BufferedInputStream bis=new BufferedInputStream(fis);
                  //return new ZipInputStream(bis);                    
            }else
            {
                return new FileInputStream(f);
            }
        }catch(Exception e)
        {
            return null;
        }
    }


    /**
     * Gets the cache.
     * 
     * @return the cache
     */
    public byte[] getCache()
    {
        return cache;
    }

    /**
     * Exists.
     * 
     * @return true, if successful
     */
    public boolean exists()
    {
        return exists;
    }

    /**
     * Checks if is directory.
     * 
     * @return true, if is directory
     */
    public boolean isDirectory()
    {
        return isDir;
    }    

    /**
     * Gets the path.
     * 
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Length.
     * 
     * @return the long
     */
    public long length()
    {
        return length;
    }

    /**
     * Last modified.
     * 
     * @return the long
     */
    public long lastModified()
    {
        return lastModified;
    }    

    /**
     * Sets the path.
     * 
     * @param p the new path
     */
    public void setPath(String p)
    {
        this.path = p;
        f=new File(SWBUtils.getApplicationPath()+path);
        exists=f.exists();
        if(exists)
        {
            isDir=f.isDirectory();
            if(!isDir)
            {
                length=f.length();
                lastModified=f.lastModified();
            }
        }else
        {

        }
    }

    /**
     * Checks for cache.
     * 
     * @return true, if successful
     */
    public boolean hasCache()
    {
        return cache!=null;
    }
}    
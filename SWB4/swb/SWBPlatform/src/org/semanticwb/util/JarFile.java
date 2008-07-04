
package org.semanticwb.util;

import java.io.*;
import java.util.zip.*;
import org.semanticwb.SWBUtils;

public class JarFile
{
    private String path;

    private boolean exists;
    private boolean isDir;
    private long length;
    private long lastModified;
    private InputStream in=null;
    private File f=null;
    private String zipPath=null;


    public JarFile(String path)
    {
        this.setPath(path);
    }    

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
        }else
        {

        }            
    }

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

    public boolean exists()
    {
        return exists;
    }

    public boolean isDirectory()
    {
        return isDir;
    }    

    public String getPath()
    {
        return path;
    }

    public long length()
    {
        return length;
    }

    public long lastModified()
    {
        return lastModified;
    }    

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
}    
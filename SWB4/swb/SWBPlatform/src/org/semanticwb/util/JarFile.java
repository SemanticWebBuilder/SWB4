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
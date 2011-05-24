package org.semanticwb.process.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.VersionInfo;


public class RepositoryFile extends org.semanticwb.process.model.base.RepositoryFileBase 
{
    private static Logger log=SWBUtils.getLogger(RepositoryFile.class);

    public RepositoryFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Almacena el archivo en la ruta predefinida del RepositoryFile,
     * Si no existe ninguna version crea una nueva
     * Si existe una version anterior agrega una nueva versión
     * @param name
     * @param out
     * @param comment
     * @param bigVersionInc
     */
    public OutputStream storeFile(String name, String comment, boolean bigVersionInc) throws FileNotFoundException
    {
        VersionInfo v=VersionInfo.ClassMgr.createVersionInfo(getProcessSite());
        v.setVersionFile(name);
        if(comment!=null)
        {
            v.setVersionComment(comment);
        }
        VersionInfo vl = getLastVersion();
        String sver="1.0";
        int ver=1;
        if(vl!=null)
        {
            vl.setNextVersion(v);
            v.setPreviousVersion(vl);
            ver=vl.getVersionNumber();
            sver=vl.getVersionValue();

            float f=Float.valueOf(sver);
            if(bigVersionInc)f=(int)f+1;
            else f=f+0.1F;
            sver=""+f;
            ver++;
        }
        v.setVersionNumber(ver);
        v.setVersionValue(sver);
        setActualVersion(v);
        setLastVersion(v);

        File file=new File(SWBPortal.getWorkPath()+getWorkPath()+"/"+ver);
        file.mkdirs();
        return  new FileOutputStream(SWBPortal.getWorkPath()+getWorkPath()+"/"+ver+"/"+name);
    }

    /**
     * Almacena el archivo en la ruta predefinida del RepositoryFile,
     * Si no existe ninguna version crea una nueva
     * Si existe una version anterior agrega una nueva versión
     * @param name
     * @param out
     * @param comment
     * @param bigVersionInc
     */
    public void storeFile(String name, InputStream in, String comment, boolean bigVersionInc)
    {
        try
        {
            OutputStream out=storeFile(name, comment, bigVersionInc);
            SWBUtils.IO.copyStream(in, out);
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}

package org.semanticwb.process.model;

import org.semanticwb.model.VersionInfo;


public class RepositoryURL extends org.semanticwb.process.model.base.RepositoryURLBase 
{
    public RepositoryURL(org.semanticwb.platform.SemanticObject base)
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
    public void storeFile(String name, String comment, boolean bigVersionInc) 
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

    }

}

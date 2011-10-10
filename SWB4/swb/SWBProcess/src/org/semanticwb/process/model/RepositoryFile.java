/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

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
